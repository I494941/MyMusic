package com.wjf.mymusic.http;

import java.io.Serializable;

public class BaseSimpleServerModel implements Serializable {

    public int res;
    public String resMsg;
    public String systime;

    public BaseServerModel toBaseServerModel() {
        BaseServerModel baseServerModel = new BaseServerModel();
        baseServerModel.res = res;
        baseServerModel.resMsg = resMsg;
        return baseServerModel;
    }
}
