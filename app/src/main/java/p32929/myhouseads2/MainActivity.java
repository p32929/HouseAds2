package p32929.myhouseads2;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import p32929.myhouseads2lib.HouseAds;

public class MainActivity extends AppCompatActivity {

    HouseAds houseAds;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.adsLl);

        houseAds = new HouseAds(this,
                "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json",
                linearLayout
        );
        houseAds.setFeedbackEmail("p32929@gmail.com");
        houseAds.autoChangeBannerAds(60);
        houseAds.setMenInBlack(true);
        houseAds.shuffleBeforeShowingDialog();
    }

    @Override
    public void onBackPressed() {
        houseAds.showInterAds();
    }
}
