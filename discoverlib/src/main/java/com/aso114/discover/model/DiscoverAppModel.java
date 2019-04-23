package com.aso114.discover.model;

public class DiscoverAppModel {
    private int imgRes;//app图标
    private String appName;//app名称
    private String packageName;//包名
    /**
     * {@link com.aso114.discover.model.EMarket}
     */
    private EMarket market;//市场名称
    private String marketUrl;//app在市场的地址（备用）

    public DiscoverAppModel(int imgRes, String appName, String packageName, EMarket market, String marketUrl) {
        this.imgRes = imgRes;
        this.appName = appName;
        this.packageName = packageName;
        this.market = market;
        this.marketUrl = marketUrl;
    }

    public EMarket getMarket() {
        return market;
    }

    public void setMarket(EMarket market) {
        this.market = market;
    }

    public String getMarketUrl() {
        return marketUrl;
    }

    public void setMarketUrl(String marketUrl) {
        this.marketUrl = marketUrl;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
