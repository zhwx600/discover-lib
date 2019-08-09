package com.aso114.myapplication;

import android.app.Application;

import com.aso114.discover.DiscoverLib;
import com.lm.brushsdk.LmEntry;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DiscoverLib.initialize(this);
    }
}
