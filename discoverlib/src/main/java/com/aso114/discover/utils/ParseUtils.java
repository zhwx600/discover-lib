package com.aso114.discover.utils;

import com.aso114.discover.DiscoverLib;

public class ParseUtils {
    /**
     * 截取360apk下载链接
     */
    public static String parse360DownloadUrl(String html) {
        if (html.contains("url=")) {
            int i = html.indexOf("url=");
            String substring = html.substring(i);
            if (substring.contains(".apk")) {
                int i1 = substring.indexOf(".apk");
                return substring.substring(4, i1 + 4);
            } else {
                DiscoverLib.showToast("链接解析错误");
            }
        } else {
            DiscoverLib.showToast("链接解析错误");
        }
        return null;
    }

    /**
     * 获取360市场apk文件名
     */
    public static String parseFileNameFrom360(String url) {
//        http://shouji.360tpcdn.com/190328/7a96fd3f86acaf3c5246a240c774fbdb/com.cyp.fm_20190326.apk
        if (url.contains("/")) {
            int i = url.lastIndexOf("/");
            return url.substring(i + 1);
        }
        return String.format("%d.apk", System.currentTimeMillis());
    }
    /**
     * 获取应用宝市场apk文件名
     */
    public static String parseFileNameFromYingyongbao(String url) {
        if (url.contains("fsname=")) {
            int i = url.lastIndexOf("fsname=");
            String substring = url.substring(i + 7);
            if (substring.contains(".apk")) {
                int i1 = substring.indexOf(".apk");
                return substring.substring(0, i1 + 4);
            } else if (substring.contains("&")) {
                int i1 = substring.indexOf("&");
                return substring.substring(0, i1);
            }
        }
        return String.format("%d.apk", System.currentTimeMillis());
    }


}
