package p32929.myhouseads2lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class HouseAds {

    private String TAG = this.getClass().getSimpleName();
    public static int intervalSeconds = 60;
    //
    private String countSP = "countSP";
    //
    private Context context;
    private ArrayList<MyAd> adArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayout;
    private int bannerCount = 0;
    private MyAdView currentAdView;

    public HouseAds(final Context context, String url) {
        this.context = context;

        new JsonObjectGetter(context, url, new JsonObjectGetListener() {
            @Override
            public void onSuccess(MyAd[] myAds) {
                adArrayList = new ArrayList<>(Arrays.asList(myAds));
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    public HouseAds(final Context context, String url, final LinearLayout linearLayout) {
        this.context = context;
        this.linearLayout = linearLayout;
        FayazSP.init(context);
        bannerCount = FayazSP.getInt(countSP, 0);

        new JsonObjectGetter(context, url, new JsonObjectGetListener() {
            @Override
            public void onSuccess(MyAd[] myAds) {
                adArrayList = new ArrayList<>(Arrays.asList(myAds));
                putBannerAds();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private void putBannerAds() {
        currentAdView = new MyAdView(context, adArrayList.get(bannerCount));
        if (linearLayout != null)
            linearLayout.addView(currentAdView);

        startBannerChangeTimer();
    }

    private void incrementAndSaveCounter() {
        bannerCount++;
        if (bannerCount == adArrayList.size()) {
            bannerCount = 0;
        }

        FayazSP.put(countSP, bannerCount);
    }

    private void startBannerChangeTimer() {
        incrementAndSaveCounter();

        doSomethingAfter(intervalSeconds, new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Changing Banner: " + bannerCount);
                if (linearLayout != null) {
                    linearLayout.removeView(currentAdView);
                    currentAdView = new MyAdView(context, adArrayList.get(bannerCount));
                    linearLayout.addView(currentAdView);
                    startBannerChangeTimer();
                }
            }
        });
    }

    public void removeSameAppAds() {
        for (int i = 0; i < adArrayList.size(); i++) {
            if (adArrayList.get(i).getUrl().contains(context.getPackageName())) {
                adArrayList.remove(i);
                break;
            }
        }
    }

    public void showInterAds() {
        View view = LayoutInflater.from(context).inflate(R.layout.ads_list, null);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerviewAdapter(adArrayList, context);
        recyclerView.setAdapter(adapter);

        String title = adArrayList.size() == 0 ? "Thank you" : "RECOMMENDED APPS";

        new AlertDialog
                .Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shareApp();
                    }
                })
                .setNeutralButton("Rate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rateApp();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity) context).finish();
                    }
                })
                .show();
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Amazing Android App: https://play.google.com/store/apps/details?id=" + context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    // Timer
    public static Handler handler = new Handler();
    private static Runnable mRunnable;

    public static void doSomethingAfter(double seconds, Runnable runnable) {
        handler.removeCallbacks(mRunnable);
        mRunnable = runnable;
        handler.postDelayed(runnable, (long) (seconds * 1000));
    }
}
