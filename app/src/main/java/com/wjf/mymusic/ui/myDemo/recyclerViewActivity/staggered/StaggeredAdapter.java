package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.BaseBean;
import com.wjf.mymusic.util.GlideUtil;

import java.util.List;

/**
 * Created by wjf on 2019/2/1.
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyHolder> {

    private Context mContext;
    private List<BaseBean> mList;

    public StaggeredAdapter(Context context, List<BaseBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_iv, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        GlideUtil.setImage(mContext, mList.get(position).getImgurl(), holder.mIv, R.mipmap.ic_launcher);
        holder.mTv.setText(mList.get(position).getName());

//        //得到控件的高度
//        ViewGroup.LayoutParams lpIv = holder.mIv.getLayoutParams();
//        //设置高度
//        lpIv.height = mList.get(position).getImgHeight();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView mIv;
        private TextView mTv;

        public MyHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv);
            mTv = itemView.findViewById(R.id.tv);
        }
    }
}