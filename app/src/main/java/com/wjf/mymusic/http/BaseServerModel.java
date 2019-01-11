package com.wjf.mymusic.http;

import com.wjf.mymusic.util.CommonConfig;

/**
 * Created by ws on 2017/10/25 0025.
 */

public class BaseServerModel<T> extends BaseSimpleServerModel {
    public T obj;

    public boolean isSuccess() {
        return res == CommonConfig.SERVER_SUCCESS_CODE;
    }
}
