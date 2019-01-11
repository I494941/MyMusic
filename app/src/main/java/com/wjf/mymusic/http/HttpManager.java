package com.wjf.mymusic.http;

import android.content.Context;
import com.wjf.mymusic.util.CommonConfig;
import com.wjf.mymusic.util.SSLSocketFactoryUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by wangshuai on 2018/3/26 0026 13:15.
 */

public class HttpManager {
    private Context mContext;
    private ApiService mApiService;
    private Builder mBuilder;
    RxApiManager mRxApiManager;

    public HttpManager(Context context) {
        this.mContext = context;
        mRxApiManager = new RxApiManager();
        initRetrofitClient();
    }

    public Builder Builder() {
        if (mBuilder == null)
            mBuilder = new Builder();
        mBuilder.params.clear();
        mBuilder.aesParams.clear();
        return mBuilder;
    }

    private void initRetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CommonConfig.HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CommonConfig.HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new BaseInterceptor<>(null, mContext))
                .sslSocketFactory(SSLSocketFactoryUtil.createSSLSocketFactory(), SSLSocketFactoryUtil.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtil.TrustAllHostnameVerifier())
                .build();
        mApiService = createRetrofitClient(okHttpClient).create(ApiService.class);
    }

    private Retrofit createRetrofitClient(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(CommonConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void get(DisposableObserver<String> observer) {
        Observable<String> observable;
        if (!mBuilder.params.isEmpty())
            observable = mApiService.get(mBuilder.url, mBuilder.params);
        else
            observable = mApiService.get(mBuilder.url);

        request(observable, observer);
    }

    public void post(DisposableObserver<String> observer) {
        Observable<String> observable;
        if (!mBuilder.params.isEmpty())
            observable = mApiService.post(mBuilder.url, mBuilder.params);
        else
            observable = mApiService.post(mBuilder.url);

        request(observable, observer);
    }

    public void upload(List<MultipartBody.Part> files, DisposableObserver<String> observer) {
        Observable<String> observable = mApiService.upload(mBuilder.url, files);
        request(observable, observer);
    }

    private void request(Observable<String> observable, DisposableObserver<String> observer) {
        mRxApiManager.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public class Builder {
        private String url;
        private Map<String, String> params = new HashMap<>();
        private Map<String, String> aesParams = new HashMap<>();

        public Builder baseUrl(String url) {
            this.url =  url;
            return this;
        }

        public Builder url(String url) {
            this.url = CommonConfig.BASE_URL_SERVER + url;
            return this;
        }

        public Builder params(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params.putAll(params);
            return this;
        }

        public Builder aesParams(String key, String value) {
            this.aesParams.put(key, value);
            return this;
        }

        public Builder aesParams(Map<String, String> params) {
            this.aesParams.putAll(params);
            return this;
        }

        public HttpManager build() {
            return HttpManager.this;
        }
    }
}
