package com.wjf.mymusic.http;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ws on 2017/3/21 0021.
 */

public interface ApiService {

    @GET()
    Observable<String> get(@Url String url, @QueryMap Map<String, String> params);

    @GET()
    Observable<String> get(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, String> params);

    @POST()
    Observable<String> post(@Url String url);

    @Multipart
    @POST()
    Observable<String> upload(@Url String url, @Part List<MultipartBody.Part> files);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);
}
