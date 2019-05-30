package com.bw.movie.zjh.utils.mvp.presenter;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 09:03
 * desc   :
 * version: 1.0
 */
public interface IPresenter {
    /**
     *  get   请求方式
     */
    void getPresenterData(String url, Map<String, String> map, Class clazz);
    /**
     *  post   请求方式
     */
    void postPresenterData(String url, Map<String, String> map, Class clazz);
    /**
     *  put   请求方式
     */
    void putPresenterData(String url, Map<String, String> map, Class clazz);
    /**
     *  delete   请求方式
     */
    void deletePresenterData(String url, Map<String, String> map, Class clazz);
    /**
     * body体  请求方式
     */
    void bodyPresenterData(String url, Map<String, String> map, Map<String, RequestBody> mapBody, Class clazz);


    /**
     * body体  请求方式
     */
    void postLoginPresenterData(String url, Map<String, String> map, Class clazz);

}
