package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.BaseBean;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class MultiItemAdapter extends BaseQuickAdapter<BaseBean.ItemBean, BaseViewHolder> {


    public MultiItemAdapter(int layoutResId, @Nullable List<BaseBean.ItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean.ItemBean item) {
        helper.setText(R.id.tv, item.getName())
                .setBackgroundRes(R.id.tv, R.drawable.gray_border_no_corner_bg);
    }
}
