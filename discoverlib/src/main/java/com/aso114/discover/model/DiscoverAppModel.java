package com.aso114.discover.model;

import com.google.gson.annotations.SerializedName;

public class DiscoverAppModel {
    private String litpic;//app图标
    private String title;//app名称
    @SerializedName("package")
    private String packageName;//包名
    // 1-360 2-应用宝 3-华为 4-小米 5-vivo
    private int marketType;//市场名称
    private String linkUrl;//app在市场的地址

    public DiscoverAppModel(String litpic, String title, String packageName, int marketType, String linkUrl) {
        this.litpic = litpic;
        this.title = title;
        this.packageName = packageName;
        this.marketType = marketType;
        this.linkUrl = linkUrl;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getMarketType() {
        return marketType;
    }

    public void setMarketType(int marketType) {
        this.marketType = marketType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
