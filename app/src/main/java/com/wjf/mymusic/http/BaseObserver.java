package com.wjf.mymusic.http;

import android.content.Context;
import com.google.gson.internal.$Gson$Types;
import com.wjf.mymusic.ui.startActivity.BaseBean;
import com.wjf.mymusic.util.CommonConfig;
import com.wjf.mymusic.util.GsonUtil;
import com.wjf.mymusic.util.LogUtil;
import com.wjf.mymusic.util.ToastUtil;
import io.reactivex.observers.DisposableObserver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseObserver<T> extends DisposableObserver<String> {

    private static final String TAG = "BaseObserver";
    private Context mContext;
    private Type mType;

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }

        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        //return parameterizedType.getActualTypeArguments()[0];
    }

    protected BaseObserver(Context context) {
        mType = getSuperclassTypeParameter(getClass());
        this.mContext = context;
    }

    /*protected BaseObserver() {
        mType = getSuperclassTypeParameter(getClass());
    }*/

    @Override
    public void onNext(String s) {
        //LogUtil.e("onNext s", "s=" + s);
        try {
            try {

            } catch (Exception e) {
                LogUtil.e(TAG, s);
            }
            T t = parseParameterizedType(s, (ParameterizedType) mType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG + mType.toString(), "onError:" + e.getMessage());
        if (e instanceof NoNetWorkException) {
            ToastUtil.show(mContext, "当前网络不可用！");
        } else {
            e.printStackTrace();
            ToastUtil.show(mContext, e.getMessage());
        }
        onHandleError(e.getMessage());
    }

    @Override
    public void onComplete() {
        //LogUtil.e(TAG, "onComplete");
    }

    private T parseParameterizedType(String json, ParameterizedType type) throws Exception {
        if (type == null)
            return null;
        if (json == null)
            return null;
        Type rawType = type.getRawType();                     // 泛型的实际类型

        //LogUtil.e("11111111111111"," = " + );
        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数
        if (rawType != BaseServerModel.class) {
            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
            if (rawType == BaseBean.class) {
                BaseBean baseBean = new BaseBean();
                baseBean.setStr(json);
                return (T) baseBean;
            } else {
                T t = GsonUtil.fromJson(json, type);
                if (t instanceof BaseServerModel) {
                    BaseServerModel baseServerModel = (BaseServerModel) t;
                    if (CommonConfig.SERVER_SUCCESS_CODE == baseServerModel.res) {
                        onHandleSuccess(t);
                    } else {
                        onHandleFailure(baseServerModel.resMsg);
                    }
                }
                return t;
            }
        } else {
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonCallback<BaseServerModel<Void>>(this)
                BaseSimpleServerModel simpleServerModel = GsonUtil.fromJson(json, BaseSimpleServerModel.class);
                //noinspection unchecked
                T t = (T) simpleServerModel.toBaseServerModel();
                if (CommonConfig.SERVER_SUCCESS_CODE == simpleServerModel.res) {
                    onHandleSuccess(t);
                } else {
                    onHandleFailure(simpleServerModel.resMsg);
                }
                return t;
            } else {
                // 泛型格式如下： new JsonCallback<BaseServerModel<内层JavaBean>>(this)
                BaseServerModel baseServerModel = GsonUtil.fromJson(json, type);
                //noinspection unchecked
                T t = (T) baseServerModel;
                //一般来说服务器会和客户端约定一个code表示成功，其余的表示失败，这里根据实际情况修改
                if (CommonConfig.SERVER_SUCCESS_CODE == baseServerModel.res) {
                    //noinspection unchecked
                    //return (T) baseServerModel;
                    onHandleSuccess(t);
                } else {
                    //直接将服务端的错误信息抛出，onError中可以获取
                    //throw new IllegalStateException(baseServerModel.resMsg);
                    onHandleFailure(baseServerModel.resMsg);
                }
                return t;
            }
        }
    }
    //protected abstract void onHandleSuccess(E e);

    protected abstract void onHandleSuccess(T t);

    protected void onHandleFailure(String msg) {
        ToastUtil.show(mContext, msg);
    }

    protected void onHandleError(String msg) {
        //        LogUtil.e("onHandleError", msg);
    }
}
