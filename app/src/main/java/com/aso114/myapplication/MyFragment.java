package com.aso114.myapplication;

import com.aso114.discover.model.DiscoverAppModel;
import com.aso114.discover.model.EMarket;
import com.aso114.discover.view.DiscoverFragment;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends DiscoverFragment {
    @Override
    public List<DiscoverAppModel> getAppModels() {
        List<DiscoverAppModel> apps = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0)
                apps.add(new DiscoverAppModel(0, "菜谱", "com.meichu.food.release", EMarket.YINGYONGBAO, ""));
            else
                apps.add(new DiscoverAppModel(0, "文件管家", "4012901", EMarket.QH360, ""));
        }
        return apps;
    }
}
