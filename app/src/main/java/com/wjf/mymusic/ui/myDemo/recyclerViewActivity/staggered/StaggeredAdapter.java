package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.BaseBean;
import com.wjf.mymusic.util.GlideUtil;
import com.wjf.mymusic.util.ScreenUtil;

import java.util.List;

/**
 * Created by wjf on 2019/2/1.
 */
public class StaggeredAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    private int mSpan;

    public StaggeredAdapter(int layoutResId, @Nullable List<BaseBean> data, int span) {
        super(layoutResId, data);
        mSpan = span;
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

        helper.setText(R.id.tv, "图片:" + (helper.getAdapterPosition() + 1));

        final ImageView iv = helper.getView(R.id.iv);


        //        GlideUtil.setImage(mContext, item.getImgurl(), iv, R.mipmap.ic_launcher);
        //        //得到控件的高度
        //        ViewGroup.LayoutParams lpIv = iv.getLayoutParams();
        //        //设置高度
        //        lpIv.height = item.getImgHeight();
        //        iv.setLayoutParams(lpIv);


        final String url = item.getImgurl();
        GlideUtil.setImageResource(mContext, R.mipmap.ic_launcher, iv);//设置默认图片，防止图片加载失败(图片加载失败，随机加载其他图片(可能是加载上一个图片))
        Glide.with(mContext)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();

                        //int h = 0;
                        //if (height > width)
                        //    h = width > ScreenUtil.getWidth(mContext) / mSpan ? height * ScreenUtil.getWidth(mContext) / mSpan / width : height;
                        //else
                        //    h = height * ScreenUtil.getWidth(mContext) / mSpan / width;

                        //得到控件的高度
                        ViewGroup.LayoutParams lpIv = iv.getLayoutParams();
                        //设置高度
                        lpIv.height = height > width ? (width > ScreenUtil.getWidth(mContext) / mSpan ? height * ScreenUtil.getWidth(mContext) / mSpan / width : height) :
                                height * ScreenUtil.getWidth(mContext) / mSpan / width;
                        iv.setLayoutParams(lpIv);
                        GlideUtil.setImage(mContext, url, iv, R.mipmap.ic_launcher);
                    }
                });
    }
}