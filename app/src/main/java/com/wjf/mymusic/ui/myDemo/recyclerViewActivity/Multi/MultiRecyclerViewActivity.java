package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.BaseBean;
import com.wjf.mymusic.util.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by wjf on 2019/1/21.
 */
public class MultiRecyclerViewActivity extends BaseToolbarActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private ArrayList<BaseBean> mList = new ArrayList<>();
    private MultiAdapter mAdapter;

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
        mRv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));//分割线
        mAdapter = new MultiAdapter(mList, new MultiAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int fpos, int spos) {
                LogUtil.e("22222222222222222", "fpos = " + fpos + ", spos = " + spos);
                showShortToast("fpos = " + fpos + ", spos = " + spos);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll:
                        LogUtil.e("11111111111111111", "position = " + position);
                        showShortToast("position = " + position);
                        break;
                }
            }
        });
        mRv.setAdapter(mAdapter);
    }

    private void initList() {
        for (int i = 0; i < 20; i++) {
            BaseBean baseBean = new BaseBean();
            baseBean.setName("姓名" + i);
            if (i % 2 == 0)
                baseBean.setItemType(BaseBean.TYPE_1);
            else {
                baseBean.setItemType(BaseBean.TYPE_2);

                ArrayList<BaseBean.ItemBean> itemBeans = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    BaseBean.ItemBean itemBean = new BaseBean.ItemBean();
                    itemBean.setName("性别=" + i + ",年龄=" + j);
                    itemBeans.add(itemBean);
                }
                baseBean.setItemBeans(itemBeans);
            }
            mList.add(baseBean);
        }
        mAdapter.notifyDataSetChanged();
    }
}
