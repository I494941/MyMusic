package com.wjf.mymusic.ui.themeActivity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wjf.mymusic.R;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.ui.themeActivity.bean.ThemeInfo;

import java.util.List;

/**
 * Created by wjf on 2019/1/17.
 */
public class ThemeAdapter extends BaseQuickAdapter<ThemeInfo, BaseViewHolder> {

    private int mSelectTheme;

    public ThemeAdapter(int layoutResId, @Nullable List<ThemeInfo> data, int selectTheme) {
        super(layoutResId, data);
        mSelectTheme = selectTheme;
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeInfo item) {

        helper.setBackgroundRes(R.id.rl_theme_item, mSelectTheme == Constants.THEME_SIZE - 1 ? R.drawable.selector_layout_night : R.drawable.selector_layout_day)
                .setBackgroundRes(R.id.tv_theme_select, mSelectTheme == Constants.THEME_SIZE - 1 ? R.drawable.shape_theme_btn_night : R.drawable.shape_theme_btn_day)
                .setText(R.id.tv_theme_name, item.getName())
                .setTextColor(R.id.tv_theme_name, mContext.getResources().getColor(item.getColor()))
                .setText(R.id.tv_theme_select, item.isSelect() ? "使用中" : "使用")
                .setTextColor(R.id.tv_theme_select, item.isSelect() ? mContext.getResources().getColor(item.getColor()) : mContext.getResources().getColor(R.color.grey500));

        RoundedImageView roundedImageView = helper.getView(R.id.iv_theme);
        roundedImageView.setBackgroundResource(item.getColor());
        if (item.isSelect())
            roundedImageView.setImageResource(R.drawable.tick);
        else
            roundedImageView.setImageBitmap(null);
    }

    public void setSelectTheme(int selectTheme) {
        mSelectTheme = selectTheme;
        notifyDataSetChanged();
    }
}
