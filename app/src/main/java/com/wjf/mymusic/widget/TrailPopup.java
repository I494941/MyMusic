package com.wjf.mymusic.widget;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.wjf.mymusic.R;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by wjf on 2019/4/11.
 */
public class TrailPopup  extends BasePopupWindow {

    public TrailPopup(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_trail_popup);
    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }
}
