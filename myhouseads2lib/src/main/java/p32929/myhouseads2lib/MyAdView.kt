package p32929.myhouseads2lib

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

/** A banner showing one app. Tapping it opens the app's link. */
class MyAdView(context: Context, val myAd: MyAd) : FrameLayout(themed(context)) {

    init {
        inflate(getContext(), R.layout.house_ads_item, this)
        bind(this, myAd)
    }

    fun setValues() = bind(this, myAd)

    internal companion object {
        fun bind(view: View, ad: MyAd) {
            Net.loadIcon(ad.appIcon, view.findViewById<ImageView>(R.id.app_image))
            view.findViewById<TextView>(R.id.app_name).text = ad.appName
            view.findViewById<TextView>(R.id.app_desc).text = ad.appDescription
            val open = View.OnClickListener { openLink(view.context, ad.url) }
            view.findViewById<View>(R.id.rv_rl).setOnClickListener(open)
            view.findViewById<View>(R.id.app_get).setOnClickListener(open)
        }

        fun openLink(context: Context, url: String) {
            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: ActivityNotFoundException) {
                // No browser or store on this device; nothing sensible to do.
            }
        }
    }
}
