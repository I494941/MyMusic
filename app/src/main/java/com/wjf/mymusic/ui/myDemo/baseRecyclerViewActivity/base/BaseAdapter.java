package com.wjf.mymusic.ui.myDemo.baseRecyclerViewActivity.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class BaseAdapter extends BaseQuickAdapter<BaseAdapterBean, BaseViewHolder> {


    public BaseAdapter(int layoutResId, @Nullable List<BaseAdapterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseAdapterBean item) {
        helper.setText(R.id.tv, item.getName() + item.getSex());
    }
}
