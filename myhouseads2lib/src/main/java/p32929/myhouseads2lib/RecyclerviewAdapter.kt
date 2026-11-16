package p32929.myhouseads2lib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/** The app list inside the interstitial. */
class RecyclerviewAdapter(
    private val myAds: List<MyAd>,
    private val context: Context,
) : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.house_ads_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = MyAdView.bind(holder.itemView, myAds[position])

    override fun getItemCount() = myAds.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
