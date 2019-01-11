package com.wjf.mymusic.ui.startActivity;

import com.wjf.mymusic.base.BasePresenter;
import com.wjf.mymusic.base.BaseView;

/**
 * Created by ws on 2017/10/27 0027.
 */

public interface StartContract {

    interface View extends BaseView {

        void getBingPicSucc(String str);
    }

    interface Presenter extends BasePresenter {

        void getBingPic();
    }
}
