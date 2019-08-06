package p32929.myhouseads2lib;

public class MyAd {
    private String appIcon;
    private String appName;
    private String appDescription;
    private String url;

    public MyAd(String appIcon, String appName, String appDescription, String url) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.appDescription = appDescription;
        this.url = url;
    }

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
}