package com.aso114.discover;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * 全局配置
 */
public class DiscoverLib {
    public static final String MARKET_YINGYONGBAO = "https://sj.qq.com/myapp/detail.htm?apkName=";
    public static final String MARKET_360 = "http://zhushou.360.cn/detail/index/soft_id/";

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static long DEFAULT_MILLISECONDS = 15 * 1000L;

    /**
     * 必须在application调用
     *
     * @param isHttp 是否需要初始化http
     */
    public static void initialize(Application app, boolean isHttp) {
        if (context != null)
            return;
        context = app.getApplicationContext();
        if (isHttp)
            initHttp(app);
    }

    /**
     * 必须在application调用
     */
    public static void initialize(Application app) {
        initialize(app, true);
    }


    private static void initHttp(Application c) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        // 全局的读取超时时间
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // 全局的写入超时时间
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // 全局的连接超时时间
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", "okhttp-okgo/jeasonlzy");
        OkGo.getInstance().init(c)
                .addCommonHeaders(headers).setOkHttpClient(builder.build())
                .setRetryCount(0);
    }

    /**
     * 全局实例
     */
    public static Context getContext() {
        if (context == null)
            try {
                throw new Exception("DiscoverLib  not initialize");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return context;
    }

    /**
     * 获取屏幕宽度
     */
    public static int screenWidth() {
        DisplayMetrics appDisplayMetrics = getContext().getResources().getDisplayMetrics();
        return appDisplayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int screenHeight() {
        DisplayMetrics appDisplayMetrics = getContext().getResources().getDisplayMetrics();
        return appDisplayMetrics.heightPixels;
    }

    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        else
            toast.setText(msg);
        toast.show();
    }
}
