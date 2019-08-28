package com.aso114.myapplication;

import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/11/28.
 */

public class MD5Util {
    /***
     * 使用JAVA实现PHP中hash_hmac 函数
     *
     * @param data
     * 待加密的数据
     * @param key
     * 加密使用的key
     */
    private static final String HMAC_SHA1 = "HmacMD5";

    public static String getSignature(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        // 根据给定的字节数组构造一个密钥。
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(data.getBytes());

        String hexBytes = byte2hex(rawHmac);
        return hexBytes;
    }

    private static String byte2hex(final byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
            stmp = (Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    /**
     * map按key排序,拼接成String
     *
     * @param map
     * @return
     */
    public static String getSign(Map<String, String> map) {
        String sign = "";
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> resultMap = sortMapByKey(map);
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            sign += entry.getKey() + "=" + URLEncoder.encode(entry.getValue());
        }
        return sign.toLowerCase();
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 比较器类
     */
    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}
