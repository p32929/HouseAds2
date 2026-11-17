# HouseAds2

An Android library for promoting your own apps inside your own apps. It shows a banner, a full-screen list of your apps (an interstitial) and a rating dialog. The ads come from a JSON file you host yourself, so you can change them any time without shipping an update. There is no ad network, no tracking and no revenue share.

(Version 1 is here: https://github.com/p32929/MyHouseAdsAndroid)

## Features

1. **Banner ad** for one of your apps, with its icon, name, description and a Get button.
2. **Interstitial** with a list of all your apps, plus Share, Rate and Exit buttons. A good fit for the back button.
3. **Rating dialog** that sends 5-star users to the Play Store and asks everyone else for feedback by email.
4. **Rotating banners** that switch to the next app on a timer you choose.
5. **Change the ads without an update:** edit the JSON file and every install picks it up.
6. **Follows your app's look:** Material 3, light and dark mode. If your app uses a Material 3 theme, the ads use your colours.
7. **Small:** no image or networking library is added. It only depends on Material Components and RecyclerView.

## Screenshots

<img src="docs/screenshots/light.png" width="300"> <img src="docs/screenshots/dark.png" width="300">

## Install

1. Add JitPack to `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

2. Add the dependency:

```kotlin
dependencies {
    implementation("com.github.p32929:HouseAds2:2.0.0")
}
```

Needs Android 5.0 (API 21) or newer.

## The JSON file

Host a JSON array anywhere that serves plain text, for example a GitHub file opened with "Raw". Each app has four fields:

```json
[
  {
    "appIcon": "https://example.com/icon.png",
    "appName": "My Other App",
    "appDescription": "One short line about the app",
    "url": "https://play.google.com/store/apps/details?id=com.example.other"
  }
]
```

1. `appIcon`: link to a square icon image.
2. `appName`: the app's name.
3. `appDescription`: one or two short lines.
4. `url`: where a tap goes, usually the Play Store page.

If the list includes the app that is showing the ads, that entry is hidden automatically.

## Usage

### Kotlin

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var houseAds: HouseAds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pass a LinearLayout to get a banner as soon as the ads load.
        houseAds = HouseAds(this, "https://your.site/ads.json", findViewById(R.id.adsLl))
        houseAds.autoChangeBannerAds(60)           // next app every 60 seconds
        houseAds.setFeedbackEmail("you@example.com") // turns on the rating dialog
        houseAds.shuffleBeforeShowingDialog()

        onBackPressedDispatcher.addCallback(this) { houseAds.showInterAds() }
    }
}
```

### Java

```java
HouseAds houseAds = new HouseAds(this, "https://your.site/ads.json", linearLayout);
houseAds.autoChangeBannerAds(60);
houseAds.setFeedbackEmail("you@example.com");

// Later, for example on back press:
houseAds.showInterAds();
```

### All methods

1. `HouseAds(context, url)` or `HouseAds(context, url, linearLayout)`: loads the ads. With a layout, a banner is added when they arrive.
2. `putBannerAds(linearLayout)`: shows a banner in a layout later.
3. `autoChangeBannerAds(seconds)`: switches the banner to the next app on a timer. It stops when the Activity closes.
4. `showInterAds()`: shows the list of your apps with Share, Rate and Exit.
5. `showRateDialog()`: shows the rating dialog. Without a feedback email it opens the Play Store directly.
6. `setFeedbackEmail(email)`: where feedback from 1 to 4 star ratings goes.
7. `setSharePreString(text)`: text placed before your Play Store link when sharing.
8. `shareApp()`: opens the share sheet with your app's link.
9. `shuffleBeforeShowingDialog()`: shows the apps in a random order in the interstitial.
10. `setListener(listener)`: lets you add your own views above the app list in the interstitial.

Check the `app` module for a full example.

## Migrating from 1.x

The main calls are the same, so most apps only need to change the version number.

1. **Minimum Android version** is now 5.0 (API 21), up from 4.0.3.
2. **Picasso is no longer included.** If your app used Picasso through this library, add it to your own dependencies.
3. **`setMenInBlack()` does nothing now.** The dialogs follow your app's theme, light or dark. It is kept so old code still compiles.
4. **Load errors no longer show a Toast** to your users. They are written to Logcat with the tag `HouseAds`.
5. **Removed helper classes:** `FayazSP`, `JsonObjectGetter` and `JsonObjectGetListener` were internal helpers and are gone.
6. **`MyAdView` is now a `FrameLayout`** instead of a `RelativeLayout`.
7. **Exit** in the interstitial still closes the Activity, as before.

## License

MIT License — Copyright (c) 2019 Fayaz Bin Salam. See [LICENSE](LICENSE) for the full text.

## Contributing

Contributions are very welcome, thank you! Before you start on a pull request, please open an issue and tell me what you would like to add or change. That way we can agree on it first, and your time is not wasted on something that might not fit. Thanks for helping make HouseAds2 better.

## You might also like

<a href="https://openstore.pfay.workers.dev/cards/p32929?n=4&amp;repo=HouseAds2"><picture><source media="(prefers-color-scheme: dark)" srcset="https://openstore.pfay.workers.dev/cards/p32929.svg?n=4&amp;repo=HouseAds2&amp;theme=dark"><img src="https://openstore.pfay.workers.dev/cards/p32929.svg?n=4&amp;repo=HouseAds2&amp;theme=light" width="100%" alt="More apps by p32929 on OpenStore"></picture></a>

<p><a href="https://p32929.github.io/share/?repo=HouseAds2"><picture><source media="(prefers-color-scheme: dark)" srcset="https://p32929.github.io/readme/footer-share-dark.svg"><img src="https://p32929.github.io/readme/footer-share-light.svg" width="50%" align="top" alt="Share this project with your friends in one click"></picture></a><a href="https://p32929.github.io/support/"><picture><source media="(prefers-color-scheme: dark)" srcset="https://p32929.github.io/readme/footer-support-dark.svg"><img src="https://p32929.github.io/readme/footer-support-light.svg" width="50%" align="top" alt="If you like my work and want to support me, feel free to support"></picture></a><br><a href="https://openstore.pfay.workers.dev/add"><picture><source media="(prefers-color-scheme: dark)" srcset="https://p32929.github.io/readme/footer-join-dark.svg"><img src="https://p32929.github.io/readme/footer-join-light.svg" width="100%" align="top" alt="Put your own apps on OpenStore: free, your listing lives in your own repo"></picture></a></p>
