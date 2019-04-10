package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.base;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.BaseBean;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class BaseAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public BaseAdapter(int layoutResId, @Nullable List<BaseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.tv, item.getName() + ":呵呵,年龄:" + item.getAge() + ",性别:" + (0 == item.getSex() ? "女" : "男"));
    }
}
