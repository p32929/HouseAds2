package p32929.myhouseads2lib

import android.widget.LinearLayout

/** Lets you add your own views above the app list when the interstitial opens. */
fun interface InterListener {
    fun onShow(linearLayoutAboveList: LinearLayout)
}
