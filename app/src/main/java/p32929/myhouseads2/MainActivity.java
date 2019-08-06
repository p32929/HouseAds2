package p32929.myhouseads2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import p32929.myhouseads2lib.HouseAds;

public class MainActivity extends AppCompatActivity {

    HouseAds houseAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        houseAds = new HouseAds(this,
                "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json");
    }

    @Override
    public void onBackPressed() {
        houseAds.showInterAds();
    }
}
