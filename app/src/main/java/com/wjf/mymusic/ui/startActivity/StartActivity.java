package com.wjf.mymusic.ui.startActivity;

import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseAppCompatActivity;

/**
 * Created by wjf on 2019/1/11.
 */
public class StartActivity extends BaseAppCompatActivity implements StartContract.View {

    @BindView(R.id.iv_bing)
    ImageView mIvBing;

    private StartPresenter mPrensenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViewsAndEvents() {
        mPrensenter = new StartPresenter(this, this);
        mPrensenter.getBingPic();
    }

    @Override
    public void getBingPicSucc(String str) {
        Glide.with(StartActivity.this).load(str).into(mIvBing);
    }

//    public void SSS() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(CommonConfig.BASE_URL)//基础URL 建议以 / 结尾
//                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
//                .build();
//        ApiService rxjavaService = retrofit.create(ApiService.class);
//        rxjavaService.get("北京")
//                .subscribeOn(Schedulers.io())//IO线程加载数据
//                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
//                .subscribe(new Subscriber<WeatherEntity>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(WeatherEntity weatherEntity) {
//                        Log.e("TAG", "response == " + weatherEntity.getData().getGanmao());
//                    }
//                });
//    }
}
