package com.wjf.mymusic.ui.myDemo.baseRecyclerViewActivity.base;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by wjf on 2019/1/21.
 */
public class BaseRecyclerViewActivity extends BaseToolbarActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private ArrayList<BaseAdapterBean> mList = new ArrayList<>();
    private BaseAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void initViewsAndEvents() {
        initRecyclerview();
        initList();
    }

    private void initRecyclerview() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setFocusable(false);//去焦点，否则RecyclerView显示在最上面
        //mRv.setNestedScrollingEnabled(false);//去滑动
        mRv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));//分割线
        mAdapter = new BaseAdapter(R.layout.item_tv, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showShortToast("点击:" + mList.get(position).getName() + mList.get(position).getSex());
            }
        });
        mRv.setAdapter(mAdapter);
    }

    private void initList() {
        for (int i = 0; i < 20; i++) {
            BaseAdapterBean bean = new BaseAdapterBean();
            bean.setName("姓名");
            bean.setSex(i);
            mList.add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }
}
