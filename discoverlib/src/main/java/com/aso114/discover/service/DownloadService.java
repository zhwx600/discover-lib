package com.aso114.discover.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.aso114.discover.utils.AppUtils;
import com.aso114.discover.utils.FileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

public class DownloadService extends IntentService {
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getExtras().getString("url");
        String fName="";
        if (url.contains("/")){
            fName=url.substring(url.lastIndexOf("/")+1,url.length());
        }else
            fName=url;
//        File file = FileUtils.isFileExist(fName);
//        if (file!=null){
//            AppUtils.installApp(file);
//            return;
//        }
        OkGo.<File>get(url).tag(this).execute(new FileCallback(FileUtils.getAdDownloadFile().getAbsolutePath(),fName) {
            @Override
            public void onSuccess(Response<File> response) {
                File file = response.body();
                AppUtils.installApp(file);
                stopSelf();
            }
        });
    }

}
