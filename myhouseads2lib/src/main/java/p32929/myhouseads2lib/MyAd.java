package p32929.myhouseads2lib;

import android.os.Parcel;
import android.os.Parcelable;

public class MyAd implements Parcelable {
    private String appIcon;
    private String appName;
    private String appDescription;
    private String url;

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected MyAd(Parcel in) {
        appIcon = in.readString();
        appName = in.readString();
        appDescription = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appIcon);
        dest.writeString(appName);
        dest.writeString(appDescription);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MyAd> CREATOR = new Parcelable.Creator<MyAd>() {
        @Override
        public MyAd createFromParcel(Parcel in) {
            return new MyAd(in);
        }

        @Override
        public MyAd[] newArray(int size) {
            return new MyAd[size];
        }
    };
}