package p32929.myhouseads2

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import p32929.myhouseads2lib.HouseAds

class MainActivity : AppCompatActivity() {

    private lateinit var houseAds: HouseAds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Dark status bar icons on a light theme, light icons on a dark one.
        val night = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) ==
            android.content.res.Configuration.UI_MODE_NIGHT_YES
        androidx.core.view.WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = !night
            isAppearanceLightNavigationBars = !night
        }

        houseAds = HouseAds(this, ADS_URL, findViewById<LinearLayout>(R.id.adsLl))
        houseAds.setFeedbackEmail("p32929@gmail.com")
        houseAds.autoChangeBannerAds(10)
        houseAds.shuffleBeforeShowingDialog()

        findViewById<Button>(R.id.btnInter).setOnClickListener { houseAds.showInterAds() }
        findViewById<Button>(R.id.btnRate).setOnClickListener { houseAds.showRateDialog() }
        findViewById<Button>(R.id.btnShare).setOnClickListener { houseAds.shareApp() }

        onBackPressedDispatcher.addCallback(this) { houseAds.showInterAds() }
    }

    companion object {
        const val ADS_URL = "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json"
    }
}
