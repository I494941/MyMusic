package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.BaseBean;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class MultiAdapter extends BaseMultiItemQuickAdapter<BaseBean, BaseViewHolder> {

    private OnItemClickedListener mOnItemClickedListener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiAdapter(List<BaseBean> data, OnItemClickedListener onItemClickedListener) {
        super(data);
        mOnItemClickedListener = onItemClickedListener;
        addItemType(BaseBean.TYPE_1, R.layout.item_title_tv);
        addItemType(BaseBean.TYPE_2, R.layout.item_recycler);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BaseBean item) {
        switch (helper.getItemViewType()) {
            case BaseBean.TYPE_1:
                helper.setText(R.id.tv, item.getName())
                        .addOnClickListener(R.id.ll);
                break;
            case BaseBean.TYPE_2:
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
