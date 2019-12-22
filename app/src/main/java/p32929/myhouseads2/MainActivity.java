package p32929.myhouseads2;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import p32929.myhouseads2lib.HouseAds;
import p32929.myhouseads2lib.InterListener;

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
        houseAds.setListener(new InterListener() {
            @Override
            public void onShow(LinearLayout linearLayoutAboveList) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText("HELLO");
                linearLayoutAboveList.addView(textView);
            }
        });
    }

    @Override
    public void onBackPressed() {
        houseAds.showInterAds();
    }
}
