package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.BaseBean;
import com.wjf.mymusic.util.GlideUtil;
import com.wjf.mymusic.util.LogUtil;
import com.wjf.mymusic.util.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjf on 2019/2/1.
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyHolder> {

    private Context mContext;
    private List<BaseBean> mList;
    private int mSpan;

    public StaggeredAdapter(Context context, List<BaseBean> list, int span) {
        mContext = context;
        mList = list;
        mSpan = span;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_iv_tv, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        //        GlideUtil.setImage(mContext, mList.get(position).getImgurl(), holder.mIv, R.mipmap.ic_launcher);
        holder.mTv.setText("图片:" + (position + 1));

        Glide.with(mContext)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .load(mList.get(position).getImgurl())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();

                        //得到控件的高度
                        ViewGroup.LayoutParams lpIv = holder.mIv.getLayoutParams();
                        //设置高度
                        lpIv.height = height > width ? (width > ScreenUtil.getWidth(mContext) / mSpan ? height * ScreenUtil.getWidth(mContext) / mSpan / width : height) :
                                height * ScreenUtil.getWidth(mContext) / mSpan / width;
                        holder.mIv.setLayoutParams(lpIv);
                        GlideUtil.setImage(mContext, mList.get(position).getImgurl(), holder.mIv, R.mipmap.ic_launcher);

                        LogUtil.e("onResourceReady", "position = " + (position + 1)
                                + ",width = " + width
                                + ",height = " + height
                                + ",lpIv.height = " + lpIv.height
                        );
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.tv)
        TextView mTv;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}