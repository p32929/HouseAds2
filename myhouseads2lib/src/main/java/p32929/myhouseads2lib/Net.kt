package p32929.myhouseads2lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.LruCache
import android.view.ContextThemeWrapper
import android.widget.ImageView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/** Tiny networking + icon loading, so the library needs no Picasso/OkHttp. */
internal object Net {
    private val pool = Executors.newFixedThreadPool(3)
    private val main = Handler(Looper.getMainLooper())

    // ponytail: memory cache only, icons are re-downloaded on next launch; add a disk cache if that matters
    private val icons = object : LruCache<String, Bitmap>(4 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap) = value.byteCount
    }

    private fun open(url: String): HttpURLConnection =
        (URL(url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 10_000
            readTimeout = 15_000
            instanceFollowRedirects = true
        }

    fun fetchAds(url: String, done: (List<MyAd>?, Exception?) -> Unit) {
        pool.execute {
            try {
                val text = open(url).run { try { inputStream.bufferedReader().readText() } finally { disconnect() } }
                val array = JSONArray(text)
                val ads = (0 until array.length()).map {
                    val o = array.getJSONObject(it)
                    MyAd(o.getString("appIcon"), o.getString("appName"), o.getString("appDescription"), o.getString("url"))
                }
                main.post { done(ads, null) }
            } catch (e: Exception) {
                main.post { done(null, e) }
            }
        }
    }

    fun loadIcon(url: String, into: ImageView) {
        into.tag = url
        icons.get(url)?.let { into.setImageBitmap(it); return }
        into.setImageResource(R.drawable.house_ads_placeholder)
        pool.execute {
            val bitmap = try {
                open(url).run { try { BitmapFactory.decodeStream(inputStream) } finally { disconnect() } }
            } catch (e: Exception) {
                null
            } ?: return@execute
            icons.put(url, bitmap)
            main.post { if (into.tag == url) into.setImageBitmap(bitmap) }
        }
    }

    fun post(delayMs: Long, block: Runnable) = main.postDelayed(block, delayMs)
    fun cancel(block: Runnable) = main.removeCallbacks(block)
}

/** Use the host's Material 3 theme when it has one, otherwise a DayNight Material 3 fallback. */
internal fun themed(context: Context): Context {
    val value = android.util.TypedValue()
    val isMaterial3 = context.theme.resolveAttribute(com.google.android.material.R.attr.colorSurfaceContainer, value, true)
    return if (isMaterial3) context else ContextThemeWrapper(context, R.style.HouseAds_Theme)
}
