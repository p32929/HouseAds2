package p32929.myhouseads2lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    //
    private Context context;
    private ArrayList<MyAd> adArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;

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
                        shareApp(context);
                    }
                })
                .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity) context).finish();
                    }
                })
                .show();
    }

    public void shareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Amazing Android App: https://play.google.com/store/apps/details?id=" + context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}
