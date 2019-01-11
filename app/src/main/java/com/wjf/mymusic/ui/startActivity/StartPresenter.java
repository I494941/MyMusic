package com.wjf.mymusic.ui.startActivity;

import com.wjf.mymusic.http.BaseDialogObserver;
import com.wjf.mymusic.http.HttpManager;
import com.wjf.mymusic.util.HttpUtil;
import com.wjf.mymusic.util.LogUtil;

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
                .get(new BaseDialogObserver<BaseBean>(mActivity) {

                    @Override
                    protected void onHandleSuccess(BaseBean baseBean) {

                        LogUtil.e("111111111111111111"," = " + baseBean.getStr());
                        mView.getBingPicSucc(baseBean.getStr());
                    }
                });
    }
}
