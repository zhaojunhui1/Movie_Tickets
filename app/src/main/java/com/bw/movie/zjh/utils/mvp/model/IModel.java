package com.bw.movie.zjh.utils.mvp.model;
import com.bw.movie.zjh.utils.mvp.callback.MyCallBack;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 09:30;
 * desc   :
 * version: 1.0
 */
public interface IModel {

    /*
     *   get  请求方式
     * */
    void getModelData(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);
    /*
     *   post  请求方式
     * */
    void postModelData(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);
    /*
     *   put  请求方式
     * */
    void putModelData(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);
    /*
     *   delete  请求方式
     * */
    void deleteModelData(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);

    /*
     *   boby请求体
     * */
    //body
    void mBodyRequest(String url, Map<String, String> map, Map<String, RequestBody> mapBody, Class clazz, MyCallBack myCallBack);


    /*
     *   post 表单请求
     * */
    void postLoginModelRequest(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);


}
