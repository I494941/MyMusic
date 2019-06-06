package com.wjf.mymusic.ui.myDemo.coordinatorLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.BaseBean;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.base.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @创建者 wjf
 * @创建时间 2019/6/6 16:43
 * @描述 ${TODO}
 */
public class CoordinatorLayoutActvity extends BaseToolbarActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private ArrayList<BaseBean> mList = new ArrayList<>();
    private BaseAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_coordinatorlayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initRecyclerview();
        initList();
    }

    private void initRecyclerview() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setFocusable(false);//去焦点，否则RecyclerView显示在最上面
        mRv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));//分割线
        mAdapter = new BaseAdapter(R.layout.item_tv, mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> showShortToast("点击:" + mList.get(position).getName() + mList.get(position).getSex()));

        mRv.setAdapter(mAdapter);
    }

    private void initList() {
        for (int i = 0; i < 20; i++) {
            BaseBean bean = new BaseBean();
            bean.setAge(i);
            bean.setSex(i % 2);
            mList.add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }
}
