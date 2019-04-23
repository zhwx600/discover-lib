package com.aso114.discover.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.aso114.discover.DiscoverLib;

import java.io.File;

public class FileUtils {
    public static File getAdDownloadFile() {
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "ADdownload");
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    public static File isFileExist(String fName) {
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "ADdownload");
        if (!f.exists())
            return null;
        for (File file : f.listFiles()) {
            if (TextUtils.equals(file.getName(), fName)) {
                return file;
            }
        }
        return null;
    }

}

