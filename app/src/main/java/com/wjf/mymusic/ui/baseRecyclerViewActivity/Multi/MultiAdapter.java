package com.wjf.mymusic.ui.baseRecyclerViewActivity.Multi;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiBean, BaseViewHolder> {

    private OnItemClickedListener mOnItemClickedListener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiAdapter(List<MultiBean> data, OnItemClickedListener onItemClickedListener) {
        super(data);
        mOnItemClickedListener = onItemClickedListener;
        addItemType(MultiBean.TYPE_1, R.layout.item_title_tv);
        addItemType(MultiBean.TYPE_2, R.layout.item_recycler);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiBean item) {
        switch (helper.getItemViewType()) {
            case MultiBean.TYPE_1:
                helper.setText(R.id.tv, item.getName())
                        .addOnClickListener(R.id.ll);
                break;
            case MultiBean.TYPE_2:
                RecyclerView rv = helper.getView(R.id.rv_item);
                rv.setLayoutManager(new GridLayoutManager(mContext, 3));
                rv.setNestedScrollingEnabled(false);//禁止滑动
                rv.setFocusable(false);//去焦点，否则RecyclerView显示在最上面
                MultiItemAdapter adapter = new MultiItemAdapter(R.layout.item_tv, item.getItemBeans());
                rv.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mOnItemClickedListener.onItemClick(helper.getAdapterPosition(), position);
                    }
                });
                break;
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int fpos, int spos);
    }
}
