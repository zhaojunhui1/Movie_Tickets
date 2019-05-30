package com.bw.movie.zjh.utils.mvp.util;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 9:02
 * desc   :
 * version: 1.0
 */
public interface BaseApis {
    /* get   请求方式*/
    @GET
    Observable<ResponseBody> get(@Url String urlStr, @QueryMap Map<String, String> map);

    /* post  请求方式*/
    @POST
    Observable<ResponseBody> post(@Url String urlStr, @QueryMap Map<String, String> map);

    /*  put  请求方式*/
    @PUT
    Observable<ResponseBody> put(@Url String urlStr, @QueryMap Map<String, String> map);

    /*  delete  请求方式*/
    @DELETE
    Observable<ResponseBody> delete(@Url String urlStr, @QueryMap Map<String, String> map);

    /*
     *   上传文件
     * */
    @POST
    @Multipart
    Observable<ResponseBody> postFormBody(@Url String urlStr, @QueryMap Map<String, String> map, @PartMap Map<String, RequestBody> requestBodyMap);


    /*
     *   表单请求
     *   用于登录、 注册 、点赞
     * */
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postLogin(@Url String url, @FieldMap Map<String, String> map);
}
