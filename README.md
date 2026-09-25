# HouseAds2

A library (V2) for cross-promoting your own Android apps inside your own Android apps — banner ads, interstitial ads and a rating dialog, all driven by a JSON file you host yourself. No ad network, no revenue share, no tracking.

(V1 here: https://github.com/p32929/MyHouseAdsAndroid)

## Features

- **Banner + interstitial house ads** served from a JSON file you control
- **Change the ads without shipping an update** — edit the JSON, every install picks it up
- **Rating dialog** that asks for feedback by email before sending unhappy users to the Play Store
- **Auto-rotating banners** on a timer you set
- **No third-party SDK** — it just fetches your JSON and draws views

## Screenshot:
### Banner Ad

<img src="https://user-images.githubusercontent.com/6418354/62546571-856a2d00-b885-11e9-87e4-d3dbe24435f1.jpg" width="350">

### Interstitial ad:

<img src="https://user-images.githubusercontent.com/6418354/62546573-8602c380-b885-11e9-89be-12ba2643fa55.jpg" width="350">

### Rating Dialog:

<img src="https://user-images.githubusercontent.com/6418354/64273036-57e6d100-cf62-11e9-847e-25db88fc2d9d.jpg" width="350">

## How to use:
### Steps:
* Initialize
* Show ads

## Installation
Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency

```gradle
dependencies {
	        implementation 'com.github.p32929:HouseAds2:1.0.0.16'
	}
```

## Code Example:
### Initialization:

If you just want to show interstitial ads , initialize like this:

```java
HouseAds houseAds = new HouseAds(this,
                "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json"
        );
```

If you want to show both interstitial ads and banner ads, initialize like this:

```java
HouseAds houseAds = new HouseAds(this,
                "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json",
                linearLayout
        );
```

To show interstitial ad on back press:

```java
houseAds.showInterAds();
```
The banner ad will be added on the json data load.

If you want to change the banner ads every `n` seconds:

```java
houseAds.autoChangeBannerAds(seconds);
```

If you want to show a Rating Dialog before sending the user to the Google Play Store, you need to set a feedback email using `setFeedbackEmail` method like this:

```java
houseAds.setFeedbackEmail("p32929@gmail.com");
```

The JSON data should be like this:
```json
[
	{
		"appIcon": "https://lh3.googleusercontent.com/vvp6Ev33xPerWBBOtIRyAHl7k5c4wQ72hGwG3EU4JC-BTjfvb5DSJDeMABaRg0fy_KI",
		"appName": "Sleepy - Phone Addiction Controller",
		"appDescription": "The smartest app to reduce phone addiction",
		"url": "https://play.google.com/store/apps/details?id=p32929.lazyphone"
	},
	{
		"appIcon": "https://lh3.googleusercontent.com/EPW9ERgwRYA47SsgLCiA0vF7irRoPIhS0tA3JdIrgQ5-xmaqVBJKt69-jLW1JAtgD6w",
		"appName": "Best CGPA Calculator",
		"appDescription": "Best CGPA Calculator app for Android",
		"url": "https://play.google.com/store/apps/details?id=p32929.cgpa_calculator2"
	},
	{
		"appIcon": "https://lh3.googleusercontent.com/xtHAhxY3kp0O2hDSgLIQlXvreSQsHV-6ApulPeLJ-j4QrjyXD_OQfYdk88hbFYad8q0",
		"appName": "Buy Sell BD",
		"appDescription": "Buy-Sell everything easily in Bangladesh",
		"url": "https://play.google.com/store/apps/details?id=p32929.buysellbd"
	},
	{
		"appIcon": "https://lh3.googleusercontent.com/ILrto9KguXlr-clbhYKUonpnHszgNp_N5Qvc8_u9QaM71oaqNKcYql2sV3sqZ7nqzsc",
		"appName": "Desplash",
		"appDescription": "Beautiful automatic wallpapers from Unsplash",
		"url": "https://play.google.com/store/apps/details?id=p32929.desplash"
	},
	{
		"appIcon": "https://lh3.googleusercontent.com/ni-lk3b2nTMZyC7r1wYBW6cm9eYqGAN-t6fXV-kD0lSyw1lO3kenC1We71fZjSryvFII",
		"appName": "The Great Postman",
		"appDescription": "Best REST API Client for Android",
		"url": "https://play.google.com/store/apps/details?id=p32929.greatpostman"
	},
	{
		"appIcon": "https://lh3.googleusercontent.com/X8mRJd7JajvDXoW7NaluKt2IvC3osH6czkTPoWeSYJ96U2LWLKT4xiFgvNouwf-UvEk",
		"appName": "ExIn",
		"appDescription": "A very simple Income-Expense tracker",
		"url": "https://play.google.com/store/apps/details?id=p32929.exin"
	}
]
```

Check the app example in the project. Hope, you will enjoy using the library :)

## License

MIT License — Copyright (c) 2019 Fayaz Bin Salam. See [LICENSE](LICENSE) for the full text.

## Contributing

Contributions are warmly welcomed and greatly appreciated! Whether it's a bug fix, new feature, or improvement, your input helps make this project better for everyone.

Before submitting a pull request, please:

1. Create an issue describing the feature or bug fix you'd like to work on
2. Wait for discussion and approval to ensure alignment with project goals
3. Fork the repository and create your feature branch
4. Submit your pull request with a clear description of changes

This approach helps avoid duplicate efforts and ensures smooth collaboration. Thank you for considering contributing!

## Share

Sharing this repository with your friends is just one click away from here

[![facebook](https://user-images.githubusercontent.com/6418354/179013321-ac1d1452-0689-493f-9066-940cf2302b6e.png)](https://www.facebook.com/sharer/sharer.php?u=https://github.com/p32929/HouseAds2/)
[![twitter](https://user-images.githubusercontent.com/6418354/179013351-7d8d6d1c-4ce2-46ab-bef8-4c4765a1b888.png)](https://twitter.com/intent/tweet?url=https://github.com/p32929/HouseAds2/)
[![tumblr](https://user-images.githubusercontent.com/6418354/179013343-3111f55a-3b90-40c7-8487-9777348672b0.png)](https://www.tumblr.com/share?v=3&u=https://github.com/p32929/HouseAds2/)
[![pocket](https://user-images.githubusercontent.com/6418354/179013334-b095c45f-becf-49f4-9ee1-5a731a9b1f85.png)](https://getpocket.com/save?url=https://github.com/p32929/HouseAds2/)
[![pinterest](https://user-images.githubusercontent.com/6418354/179013331-44cd9206-11b1-4b65-becb-5863b61c828f.png)](https://pinterest.com/pin/create/button/?url=https://github.com/p32929/HouseAds2/)
[![reddit](https://user-images.githubusercontent.com/6418354/179013338-7416ae3f-73ba-4522-86e1-1374d7082d22.png)](https://www.reddit.com/submit?url=https://github.com/p32929/HouseAds2/)
[![linkedin](https://user-images.githubusercontent.com/6418354/179013327-ca7b7102-1da8-4b1c-858f-1a6e5f21bd70.png)](https://www.linkedin.com/shareArticle?mini=true&url=https://github.com/p32929/HouseAds2/)
[![whatsapp](https://user-images.githubusercontent.com/6418354/179013353-f477fa0b-3e6f-4138-a357-c9991b23ff88.png)](https://api.whatsapp.com/send?text=https://github.com/p32929/HouseAds2/)

---

## Support

If this saved you time, you can buy me a coffee — it keeps these projects maintained and free. Other payment options: https://p32929.github.io/SendMoney2MeV1/

[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20me%20a%20coffee-%E2%98%95-FFDD00?style=for-the-badge&logo=buymeacoffee&logoColor=black)](https://www.buymeacoffee.com/p32929)

<!-- hire-block -->

---

## 💼 Using this at a company?

I do fixed-price delivery work on my own projects. One invoice, one date, no hourly billing:

| | |
|---|---|
| **White-label build** — this project rebranded, extended and deployed as yours | **$6,500** · 3 weeks |
| **Custom app from scratch** on my own stack, signed and auto-updating | **$12,500** · 6 weeks |
| **Production-hardening sprint** — 72 hours on this project, for your load and your security review | **$999** |
| **Ongoing capacity** — one project-week of my time reserved every month | **$9,000 / month** |

Full details → **[p32929.github.io/hire](https://p32929.github.io/hire/)** · Email **[fayazdevinbox@uberip.com](mailto:fayazdevinbox@uberip.com)** — scoping and quotes are free and I answer within one business day.
