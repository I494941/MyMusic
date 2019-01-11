package com.wjf.mymusic.http;

import android.content.Context;
import com.wjf.mymusic.widget.LoadingDialog;

/**
 * BaseDialogObserver
 * Created by ws on 2017/6/23.
 */
public abstract class BaseDialogObserver<T> extends BaseObserver<T> {
    private LoadingDialog mLoadingDialog;
    private Context mContext;

    protected BaseDialogObserver(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLoadingDialog == null)
            mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setTitle(null);
        mLoadingDialog.show();
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
