package com.wjf.mymusic.ui.myDemo.baidumap.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.litepalBean.LocationBean;

import java.util.List;

/**
 * Created by wjf on 2019/4/4.
 */
public class LocationAdapter extends BaseQuickAdapter<LocationBean, BaseViewHolder> {

    public LocationAdapter(int layoutResId, @Nullable List<LocationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationBean item) {

        helper.setText(R.id.tv1, String.valueOf(item.getTime()))
                .setText(R.id.tv2, String.valueOf(item.getLng()))
                .setText(R.id.tv3, String.valueOf(item.getLat()))
                .setText(R.id.tv4, String.valueOf(item.getDistance()));
    }
}
