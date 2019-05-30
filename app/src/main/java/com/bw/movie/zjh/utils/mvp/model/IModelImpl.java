package com.bw.movie.zjh.utils.mvp.model;
import com.bw.movie.zjh.utils.mvp.callback.MyCallBack;
import com.bw.movie.zjh.utils.mvp.util.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 10:16
 * desc   :
 * version: 1.0
 */
public class IModelImpl implements IModel {

    /*
     *   get  请求方式
     *  */
    @Override
    public void getModelData(String url, final Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFails(e.getMessage());
                    }
                }
            }
            @Override
            public void onFail(String error) {
                if (myCallBack != null){
                    myCallBack.OnFails(error);
                }
            }
        });
    }

    /*
     *  post 请求方式
     * */
    @Override
    public void postModelData(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFails(e.getMessage());
                    }
                }
            }
            @Override
            public void onFail(String error) {
                if (myCallBack != null){
                    myCallBack.OnFails(error);
                }
            }
        });
    }

    /*
     *  put  请求方式
     * */
    @Override
    public void putModelData(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().put(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFails(e.getMessage());
                    }
                }
            }
            @Override
            public void onFail(String error) {
                if (myCallBack != null){
                    myCallBack.OnFails(error);
                }
            }
        });
    }

    /*
     *  delete 请求方式
     * */
    @Override
    public void deleteModelData(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().delete(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFails(e.getMessage());
                    }
                }
            }
            @Override
            public void onFail(String error) {
                if (myCallBack != null){
                    myCallBack.OnFails(error);
                }
            }
        });
    }

    /*
     *   上传文件
     * */
    @Override
    public void mBodyRequest(String url, Map<String, String> map, Map<String, RequestBody> mapBody, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().postFormBody(url, map, mapBody, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFails(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.OnFails(error);
                }
            }
        });
    }

    /*
     *   post 表单请求
     * */
    @Override
    public void postLoginModelRequest(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().postLogin(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFails(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.OnFails(error);
                }
            }
        });
    }

}
