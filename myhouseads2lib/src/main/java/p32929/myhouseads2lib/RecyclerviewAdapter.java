package p32929.myhouseads2lib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    ArrayList<MyAd> myAds = new ArrayList<>();
    Context context;

    public RecyclerviewAdapter(ArrayList<MyAd> myAds, Context context) {
        this.myAds = myAds;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {
        final MyAd ma = myAds.get(position);

        holder.textViewName.setText("" + ma.getAppName());
        holder.textViewDescription.setText("" + ma.getAppDescription());
        holder.textViewName.setSelected(true);

        Picasso.get().load(ma.getAppIcon()).placeholder(R.drawable.ic_android_grey600_48dp).into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(ma.getUrl()));
                context.startActivity(browse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName, textViewDescription;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.app_image);
            textViewName = itemView.findViewById(R.id.app_name);
            textViewDescription = itemView.findViewById(R.id.app_desc);
            relativeLayout = itemView.findViewById(R.id.rv_rl);
        }
    }
}
