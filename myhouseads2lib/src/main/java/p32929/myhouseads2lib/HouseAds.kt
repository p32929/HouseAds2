package p32929.myhouseads2lib

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Shows your own apps as house ads: a banner, an interstitial list and a rating dialog.
 * The ads come from a JSON file you host, so you can change them without an app update.
 *
 * @param context the Activity showing the ads
 * @param url the address of your JSON file
 * @param linearLayout optional; when given, a banner is added to it as soon as the ads load
 */
class HouseAds @JvmOverloads constructor(
    private val context: Context,
    url: String,
    private var linearLayout: LinearLayout? = null,
) {
    private val prefs = context.getSharedPreferences("HouseAds", Context.MODE_PRIVATE)
    private var adArrayList: List<MyAd> = emptyList()
    private var loaded = false
    private var currentAdView: View? = null
    private var listener: InterListener? = null
    private var feedbackEmail = ""
    private var sharePreString = "Check out this app: "
    private var shuffleDialogAds = false
    private var timer: Runnable? = null

    init {
        Net.fetchAds(url) { ads, error ->
            if (ads == null) {
                Log.w(TAG, "Could not load house ads from $url", error)
                return@fetchAds
            }
            adArrayList = ads.filterNot { it.url.contains(context.packageName) }
            loaded = true
            putBannerAds()
        }
    }

    /** Runs [runnable] once after [seconds]. Calling it again replaces the previous one. */
    fun doSomethingAfter(seconds: Double, runnable: Runnable) {
        timer?.let(Net::cancel)
        timer = runnable
        Net.post((seconds * 1000).toLong(), runnable)
    }

    /** Shows a banner inside [linearLayoutAds]. If the ads are still loading, it appears when they arrive. */
    fun putBannerAds(linearLayoutAds: LinearLayout) {
        linearLayout = linearLayoutAds
        putBannerAds()
    }

    private fun putBannerAds() {
        val layout = linearLayout ?: return
        if (adArrayList.isEmpty()) return
        val index = prefs.getInt(COUNT_KEY, 0) % adArrayList.size
        currentAdView?.let(layout::removeView)
        currentAdView = MyAdView(context, adArrayList[index]).also(layout::addView)
        prefs.edit().putInt(COUNT_KEY, (index + 1) % adArrayList.size).apply()
    }

    /** Swaps the banner for the next app every [intervalSeconds]. Stops by itself when the Activity closes. */
    fun autoChangeBannerAds(intervalSeconds: Int) {
        doSomethingAfter(intervalSeconds.toDouble(), Runnable {
            val activity = context as? Activity
            if (activity != null && (activity.isFinishing || activity.isDestroyed)) return@Runnable
            putBannerAds()
            autoChangeBannerAds(intervalSeconds)
        })
    }

    fun setListener(listener: InterListener?) {
        this.listener = listener
    }

    /** With an email set, the rating dialog asks unhappy users for feedback before sending anyone to the Play Store. */
    fun setFeedbackEmail(feedbackEmail: String) {
        this.feedbackEmail = feedbackEmail
    }

    /** Text placed before your Play Store link when the user shares the app. */
    fun setSharePreString(sharePreString: String) {
        this.sharePreString = sharePreString
    }

    fun shuffleBeforeShowingDialog() {
        shuffleDialogAds = true
    }

    @Deprecated("The dialogs now follow your app's theme, light or dark. This does nothing.")
    fun setMenInBlack(menInBlack: Boolean) = Unit

    /** Shows the full-screen list of your apps, with Share, Rate and Exit buttons. Good for the back button. */
    fun showInterAds() {
        val ui = themed(context)
        val ads = if (shuffleDialogAds) adArrayList.shuffled() else adArrayList
        val view = LayoutInflater.from(ui).inflate(R.layout.house_ads_list, null)
        view.findViewById<RecyclerView>(R.id.rv).apply {
            layoutManager = LinearLayoutManager(ui)
            adapter = RecyclerviewAdapter(ads, ui)
        }
        val above = view.findViewById<LinearLayout>(R.id.layoutAboveList)

        MaterialAlertDialogBuilder(ui)
            .setTitle(if (ads.isEmpty()) R.string.house_ads_thanks else R.string.house_ads_recommended)
            .setView(view)
            .setPositiveButton(R.string.house_ads_share) { _, _ -> shareApp() }
            .setNeutralButton(R.string.house_ads_rate) { _, _ -> showRateDialog() }
            .setNegativeButton(R.string.house_ads_exit) { _, _ -> (context as? Activity)?.finish() }
            .show()

        listener?.let {
            above.removeAllViews()
            it.onShow(above)
        }
    }

    fun shareApp() {
        val send = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, sharePreString + PLAY_URL + context.packageName)
            .setType("text/plain")
        context.startActivity(Intent.createChooser(send, null))
    }

    /** Asks for a star rating. 5 stars goes to the Play Store; fewer opens an email with the user's feedback. */
    fun showRateDialog() {
        if (feedbackEmail.isEmpty()) return rateApp()

        val ui = themed(context)
        val view = LayoutInflater.from(ui).inflate(R.layout.house_ads_rating, null)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val feedbackLayout = view.findViewById<TextInputLayout>(R.id.feedbackLayout)
        val feedback = view.findViewById<TextInputEditText>(R.id.feecbackEt)
        view.findViewById<TextView>(R.id.rateTitle).text =
            ui.getString(R.string.house_ads_rate_title, context.applicationInfo.loadLabel(context.packageManager))

        val dialog = MaterialAlertDialogBuilder(ui)
            .setView(view)
            .setNegativeButton(R.string.house_ads_not_now, null)
            .setPositiveButton(R.string.house_ads_submit) { _, _ ->
                if (ratingBar.rating >= 5) rateApp() else sendEmailFeedback(feedback.text.toString())
            }
            .show()
        val submit = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        submit.isEnabled = false

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            submit.isEnabled = rating > 0
            feedbackLayout.visibility = if (rating in 0.5f..4.5f) View.VISIBLE else View.GONE
        }
    }

    private fun sendEmailFeedback(feedback: String) {
        val label = context.applicationInfo.loadLabel(context.packageManager)
        val email = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", feedbackEmail, null))
            .putExtra(Intent.EXTRA_SUBJECT, "$label feedback")
            .putExtra(Intent.EXTRA_TEXT, feedback)
        try {
            context.startActivity(Intent.createChooser(email, null))
        } catch (e: ActivityNotFoundException) {
            Log.w(TAG, "No email app", e)
        }
    }

    private fun rateApp() {
        val market = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            context.startActivity(market)
        } catch (e: ActivityNotFoundException) {
            MyAdView.openLink(context, PLAY_URL + context.packageName)
        }
    }

    private companion object {
        const val TAG = "HouseAds"
        const val COUNT_KEY = "countSP"
        const val PLAY_URL = "https://play.google.com/store/apps/details?id="
    }
}
