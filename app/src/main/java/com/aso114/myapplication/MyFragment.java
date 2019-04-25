package com.aso114.myapplication;

import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    @Override
    public void setAd(ViewGroup adContainer) {
        adContainer.setBackgroundColor(Color.RED);
    }

    @Override
    public View getCustomTitleView(ViewGroup parentView) {
        View view = getLayoutInflater().inflate(R.layout.custom_view, parentView, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点击标题栏", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
