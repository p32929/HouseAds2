## HouseAds2
A library ( V2 ) for cross promoting own apps within own apps - for Android

(V1 here: https://github.com/p32929/MyHouseAdsAndroid)

## Screenshot:
### Banner Ad

![Screenshot_20190806_200048_p32929 myhouseads2](https://user-images.githubusercontent.com/6418354/62546571-856a2d00-b885-11e9-87e4-d3dbe24435f1.jpg)

### Interstitial ad:
![Screenshot_20190806_200441_p32929 myhouseads2](https://user-images.githubusercontent.com/6418354/62546573-8602c380-b885-11e9-89be-12ba2643fa55.jpg)

## How to use:
### Steps:
* Initialize
* Show ads

## Code Example:
### Initialization:

If you just want to show ads list dialog, initialize like this:

```
HouseAds houseAds = new HouseAds(this,
                "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json",
        );
```

If you just want to show ads list dialog and banner ads, initialize like this:
```
HouseAds houseAds = new HouseAds(this,
                "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json",
                linearLayout
        );
```

The banner ad will be added on the json data load.

The JSON data should be like this:
```
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

Hope, you will enjoy using the library :)

## License

```
MIT License

Copyright (c) 2019 Fayaz Bin Salam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```