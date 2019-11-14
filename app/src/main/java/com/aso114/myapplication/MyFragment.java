package com.aso114.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aso114.discover.model.DiscoverAppModel;
import com.aso114.discover.view.DiscoverFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class MyFragment extends DiscoverFragment {

    @Override
    public void setAd(ViewGroup container, ImageView imgDefault) {
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl(false,new DiscoverAppModel("","","com.waipo.food.release",3,"http://appstore.huawei.com/app/C100030273"));
            }
        });
    }


    @Override
    public View getCustomTitleView(ViewGroup parentView) {
        return null;
    }

    protected HashMap<String, String> getParams(String adType) {
        String keyId = "100015";
        String secretKey = "2e8aac1d66abfa758db7cfe7d91d4776";
        String channel = "ali";
        //设置请求参数
        HashMap<String, String> paramsMap = new HashMap<>(16);
        paramsMap.put("f", "adsense");
        //固定传getAdInfo
        paramsMap.put("h", "getAdMenu");
        //默认传1，如需改动会通知
        paramsMap.put("v", "1");
        paramsMap.put("market", channel);
        //1、开屏广告 2、应用中推荐广告
        paramsMap.put("adType", adType);
        paramsMap.put("appkey", keyId);
        //获取系统当前时间
        paramsMap.put("t", TimeUtils.getNowString());
        //参数加密
        try {
            String signature = MD5Util.getSignature(MD5Util.getSign(paramsMap), secretKey);
            paramsMap.put("sign", signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramsMap;
    }

    @Override
    public void initData() {
        OkGo.<String>get("http://api.sjsj.cn/index.php").params(getParams("1")).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    String body = response.body();
                    JSONObject json = new JSONObject(body);
                    int code = json.optInt("code");
                    if (code == 200) {
                        JSONArray data = json.optJSONArray("data");
                        if (data != null) {
                            List<DiscoverAppModel> list = new Gson().fromJson(data.toString(), new TypeToken<List<DiscoverAppModel>>() {
                            }.getType());
                            setAppList(list);
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
    }

}
