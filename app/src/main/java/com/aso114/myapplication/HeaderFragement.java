package com.aso114.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.aso114.discover.model.DiscoverAppModel;
import com.aso114.discover.view.DiscoverHeaderFragment;

import java.util.ArrayList;
import java.util.List;

public class HeaderFragement extends DiscoverHeaderFragment {
    @Override
    public List<DiscoverAppModel> getAppModels() {
        List<DiscoverAppModel> apps = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0)
                apps.add(new DiscoverAppModel("", "菜谱", "com.meichu.food.release",2, ""));
            else
                apps.add(new DiscoverAppModel("", "文件管家", "4012901", 1, ""));
        }
        return apps;
    }

    @Override
    public View getCustomTitleView(ViewGroup parentView) {
        return null;
    }

    @Override
    public View getHeaderView(RecyclerView rv) {
        return getLayoutInflater().inflate(R.layout.custom_view, rv, false);
    }
}
