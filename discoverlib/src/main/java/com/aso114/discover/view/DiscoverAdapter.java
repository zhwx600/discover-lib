package com.aso114.discover.view;

import android.widget.ImageView;

import com.aso114.discover.model.DiscoverAppModel;
import com.aso114.discover.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class DiscoverAdapter extends BaseQuickAdapter<DiscoverAppModel, BaseViewHolder> {

    public DiscoverAdapter() {
        super(R.layout.item_rv_discover);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverAppModel item) {
        helper.setText(R.id.tvName, item.getAppName());
        helper.<ImageView>getView(R.id.img).setImageResource(item.getImgRes());
    }
}
