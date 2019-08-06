package p32929.myhouseads2lib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyAdView extends RelativeLayout {

    Context context;
    ImageView imageView;
    TextView textViewName, textViewDescription;
    MyAd myAd;

    public MyAdView(final Context context, final MyAd myAd) {
        super(context);
        this.context = context;
        this.myAd = myAd;

        inflate(context, R.layout.rv_layout, this);
        imageView = findViewById(R.id.app_image);
        textViewName = findViewById(R.id.app_name);
        textViewDescription = findViewById(R.id.app_desc);

        this.setBackgroundColor(Color.parseColor("#F5F5F5"));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(myAd.getUrl()));
                context.startActivity(browse);
            }
        });

        setValues();
    }

    public void setValues() {
        Picasso.get().load(myAd.getAppIcon()).placeholder(R.drawable.ic_android_grey600_48dp).into(imageView);
        textViewName.setText("" + myAd.getAppName());
        textViewDescription.setText("" + myAd.getAppDescription());
    }
}
