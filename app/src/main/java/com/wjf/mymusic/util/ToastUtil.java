package com.wjf.mymusic.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    /**
     * 强大的吐司，能够连续弹的吐司
     *
     * @param message
     */
    public static void show(Context context, String message) {
        if (TextUtils.isEmpty(message))
            return;
        else if (message.toLowerCase().contains("failed to connect to"))
            message = "服务器正在维护中，请稍后重试";
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);//如果不为空，则直接改变当前toast的文本
        }
        toast.show();
    }
}
