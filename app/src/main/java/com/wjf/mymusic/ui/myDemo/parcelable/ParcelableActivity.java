package com.wjf.mymusic.ui.myDemo.parcelable;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.parcelable.bean.Book;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wjf on 2019/4/30.
 */
public class ParcelableActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {
        mTv1.setText("传值ParcelableActivity2");
    }

    @OnClick({R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                ParcelableBean parcelableBean = new ParcelableBean();
                parcelableBean.setI(100);
                parcelableBean.setStr("......");
                parcelableBean.setB(false);

                Book book = new Book();
                book.setName("第一行代码");
                book.setPrice(55.55);
                parcelableBean.getBooks().add(book);
                book = new Book();
                book.setName("第二行代码");
                book.setPrice(66.66);
                parcelableBean.getBooks().add(book);

                Bundle bundle = new Bundle();
                bundle.putParcelable("bean", parcelableBean);
                startActivity(ParcelableActivity2.class, bundle);
                break;
            case R.id.tv2:
                break;
        }
    }
}
