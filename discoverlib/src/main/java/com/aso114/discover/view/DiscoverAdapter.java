package com.aso114.discover.view;

import android.widget.ImageView;

import com.aso114.discover.model.DiscoverAppModel;
import com.aso114.discover.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class DiscoverAdapter extends BaseQuickAdapter<DiscoverAppModel, BaseViewHolder> {

    public DiscoverAdapter() {
        super(R.layout.item_rv_discover);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverAppModel item) {
        helper.setText(R.id.tvName, item.getTitle());
        Glide.with(mContext).load(item.getLitpic()).into(helper.<ImageView>getView(R.id.img));
    }
}
