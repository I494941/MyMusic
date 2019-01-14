package com.wjf.mymusic.ui.startActivity;

import com.wjf.mymusic.http.HttpManager;
import com.wjf.mymusic.util.HttpUtil;
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
                        mView.getBingPicSucc(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.getBingPicComplete();
                    }
                });
    }
}
