/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer/Novate
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.wjf.mymusic.http;


import android.content.Context;
import com.wjf.mymusic.util.LogUtil;
import com.wjf.mymusic.util.NetworkUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * BaseInterceptor，use set okhttp call header
 */
public class BaseInterceptor<T> implements Interceptor {
    private Context mContext;
    private Map<String, T> mHeaders;

    public BaseInterceptor(Map<String, T> headers, Context context) {
        this.mContext = context;
        this.mHeaders = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            throw new NoNetWorkException();
        }
        Request request = chain.request();
        LogUtil.e("http_request", "intercept: request header=" + request.url() + "/\r body=" + request.body());
        Request.Builder builder = request.newBuilder();
        if (mHeaders != null && mHeaders.size() > 0) {
            Set<String> keys = mHeaders.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, mHeaders.get(headerKey) == null ? "" : (String) mHeaders.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }
}
