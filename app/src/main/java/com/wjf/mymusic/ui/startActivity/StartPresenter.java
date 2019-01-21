package com.wjf.mymusic.ui.startActivity;

import com.wjf.mymusic.R;
import com.wjf.mymusic.http.HttpManager;
import com.wjf.mymusic.http.NoNetWorkException;
import com.wjf.mymusic.util.HttpUtil;
import com.wjf.mymusic.util.LogUtil;
import com.wjf.mymusic.util.ToastUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by ws on 2017/11/9 0009.
 */
//@ActivityScoped
public class StartPresenter implements StartContract.Presenter {

    private StartContract.View mView;
    private StartActivity mActivity;

    public StartPresenter(StartContract.View view, StartActivity activity) {
        mView = view;
        mActivity = activity;
    }

    @Override
    public void getBingPic() {
        new HttpManager(mActivity).Builder()
                .baseUrl(HttpUtil.requestBingPic)
                .build()
                .get(new DisposableObserver<String>() {

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("111111111111111111111", "s = " + s);
                        mView.getBingPicSucc(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof NoNetWorkException)
                            ToastUtil.show(mActivity, mActivity.getString(R.string.no_network_exception));
                        else
                            ToastUtil.show(mActivity, e.getMessage());
                        mView.getBingPicFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
