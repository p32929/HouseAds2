package p32929.myhouseads2;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import p32929.myhouseads2lib.HouseAds;

/** Not used by the demo. It only proves the 1.x Java calls still compile against 2.0.0. */
class JavaUsage {
    static void use(Activity activity, LinearLayout banner) {
        HouseAds interOnly = new HouseAds(activity, MainActivity.ADS_URL);
        HouseAds houseAds = new HouseAds(activity, MainActivity.ADS_URL, banner);
        houseAds.setFeedbackEmail("me@example.com");
        houseAds.setSharePreString("Try this: ");
        houseAds.autoChangeBannerAds(60);
        houseAds.shuffleBeforeShowingDialog();
        houseAds.setListener(above -> {
            TextView text = new TextView(activity);
            text.setText("Thanks for using the app");
            above.addView(text);
        });
        houseAds.showInterAds();
        houseAds.showRateDialog();
        interOnly.putBannerAds(banner);
    }
}
