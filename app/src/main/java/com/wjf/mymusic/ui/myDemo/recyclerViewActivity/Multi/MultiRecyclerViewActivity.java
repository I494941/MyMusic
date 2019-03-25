package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.ui.myDemo.BaseBean;
import com.wjf.mymusic.util.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by wjf on 2019/1/21.
 */
public class MultiRecyclerViewActivity extends BaseToolbarActivity implements OnRefreshLoadMoreListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private ArrayList<BaseBean> mList = new ArrayList<>();
    private MultiAdapter mAdapter;

    private int pageNum = 0;
    private int totalPage = 2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_smartrefreshlayout_recycler;
    }

    @Override
    protected void initViewsAndEvents() {
        initRecyclerview();
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        initList();
    }

    private void initRecyclerview() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setFocusable(false);//去焦点，否则RecyclerView显示在最上面
        mRv.setNestedScrollingEnabled(false);//去滑动
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

        RelativeLayout rlEmpty = (RelativeLayout) getLayoutInflater().inflate(R.layout.empty_view, null);
        TextView tvEmpty = rlEmpty.findViewById(R.id.tv_empty);
        SpannableStringBuilder style = new SpannableStringBuilder("没有你要的结果，扯淡了吧");
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 8, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(20, true), 8, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvEmpty.setText(style);
        rlEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("哈哈哈哈哈哈哈哈哈");
            }
        });
        mAdapter.setEmptyView(rlEmpty);

        mRv.setAdapter(mAdapter);
    }

    private void initList() {
        if (pageNum == 0)
            mList.clear();

        for (int i = pageNum * Constants.PAGE_SIZE; i < (pageNum + 1) * Constants.PAGE_SIZE; i++) {
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

        if (mList.size() > 0)
            mRefreshLayout.setEnableLoadMore(true);
        else
            mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (pageNum < totalPage) {
            pageNum++;
            initList();
        } else
            showShortToast("已无更多信息！");
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 0;
        initList();
        refreshLayout.finishRefresh();
    }
}
