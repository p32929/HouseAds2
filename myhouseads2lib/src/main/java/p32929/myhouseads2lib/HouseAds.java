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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HouseAds {

    // Timer
    public static Handler handler = new Handler();
    private static Runnable mRunnable;
    private String TAG = this.getClass().getSimpleName();
    //
    private String countSP = "countSP";
    private String feedbackEmail = "";
    private String sharePreString = "Check out this Amazing Android App: ";
    private int rate = 0;
    //
    private Context context;
    private ArrayList<MyAd> adArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayout;
    private InterListener listener;
    private int bannerCount = 0;
    private MyAdView currentAdView;
    boolean shuffleDialogAds = false;
    boolean menInBlack = false;

    public HouseAds(final Context context, String url) {
        this.context = context;
        FayazSP.init(context);

        new JsonObjectGetter(context, url, new JsonObjectGetListener() {
            @Override
            public void onSuccess(MyAd[] myAds) {
                adArrayList = new ArrayList<>(Arrays.asList(myAds));
                removeSameAppAds();
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
                removeSameAppAds();
                putBannerAds();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    public void doSomethingAfter(double seconds, Runnable runnable) {
        handler.removeCallbacks(mRunnable);
        mRunnable = runnable;
        handler.postDelayed(runnable, (long) (seconds * 1000));
    }

    private void putBannerAds() {
        currentAdView = new MyAdView(context, adArrayList.get(bannerCount));
        if (linearLayout != null)
            linearLayout.addView(currentAdView);

        incrementAndSaveCounter();
    }

    public void putBannerAds(LinearLayout linearLayoutAds) {
        this.linearLayout = linearLayoutAds;
        currentAdView = new MyAdView(context, adArrayList.get(bannerCount));
        if (linearLayout != null)
            linearLayout.addView(currentAdView);

        incrementAndSaveCounter();
    }

    private void incrementAndSaveCounter() {
        bannerCount++;
        if (bannerCount == adArrayList.size()) {
            bannerCount = 0;
        }

        FayazSP.put(countSP, bannerCount);
    }

    public void autoChangeBannerAds(final int intervalSeconds) {
        doSomethingAfter(intervalSeconds, new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Changing Banner: " + bannerCount);
                if (linearLayout != null) {
                    linearLayout.removeView(currentAdView);
                    putBannerAds();
                    autoChangeBannerAds(intervalSeconds);
                }
            }
        });
    }

    private void removeSameAppAds() {
        for (int i = 0; i < adArrayList.size(); i++) {
            if (adArrayList.get(i).getUrl().contains(context.getPackageName())) {
                adArrayList.remove(i);
                break;
            }
        }
    }

    public void setListener(InterListener listener) {
        this.listener = listener;
    }

    public void setFeedbackEmail(String feedbackEmail) {
        this.feedbackEmail = feedbackEmail;
    }

    public void setSharePreString(String sharePreString) {
        this.sharePreString = sharePreString;
    }

    public void showInterAds() {
        if (shuffleDialogAds)
            Collections.shuffle(adArrayList);

        View view = LayoutInflater.from(context).inflate(R.layout.ads_list, null);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerviewAdapter(adArrayList, context);
        recyclerView.setAdapter(adapter);
        LinearLayout linearLayoutAboveList = view.findViewById(R.id.layoutAboveList);

        String title = adArrayList.size() == 0 ? "THANK YOU" : "RECOMMENDED APPS";

        final AlertDialog dialog = new AlertDialog
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
                        showRateDialog();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity) context).finish();
                    }
                }).create();

        if (menInBlack) {
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.primaryText));
                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.primaryText));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.primaryText));
                }
            });
        }

        dialog.show();

        if (listener != null) {
            linearLayoutAboveList.removeAllViews();
            listener.onShow(linearLayoutAboveList);
        }
    }

    public void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sharePreString + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void showRateDialog() {
        if (feedbackEmail.isEmpty()) {
            rateApp();
        } else {
            rate = 1;
            View dialogView = LayoutInflater.from(context).inflate(R.layout.rating_dialog_layout, null);
            RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
            final EditText editTextFeedback = dialogView.findViewById(R.id.feecbackEt);
            final Button button = dialogView.findViewById(R.id.submitBtn);

            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .show();

            if (menInBlack) {
                button.setTextColor(context.getResources().getColor(R.color.primaryText));
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rate > 4) {
                        rateApp();
                    } else {
                        sendEmailFeedback(editTextFeedback.getText().toString());
                    }
                    dialog.dismiss();
                }
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    rate = (int) v;
                    if (v > 4) {
                        editTextFeedback.setVisibility(View.GONE);
                    } else {
                        editTextFeedback.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void sendEmailFeedback(String feedback) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "" + feedbackEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + context.getApplicationInfo().loadLabel(context.getPackageManager()).toString() + " feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "" + feedback);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
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

    public void shuffleBeforeShowingDialog() {
        shuffleDialogAds = true;
    }

    public void setMenInBlack(boolean menInBlack) {
        this.menInBlack = menInBlack;
    }
}
