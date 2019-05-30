package com.bw.movie.zjh.utils.mvp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bw.movie.app.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 9:01
 * desc   :
 * version: 1.0
 */
public class RetrofitManager {
    private BaseApis baseApis;
    /***
     * 使用单例
     */
    private static volatile RetrofitManager retrofitManager;
    public static RetrofitManager getInstance(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                if (retrofitManager == null){
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }


    /***
     * 日志拦截器，超时处理
     */
    private RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求
                        Request request = chain.request();
                        //取出sp中存入的userid, sessionid
                        SharedPreferences sharedPreferences = App.getApplication().getSharedPreferences("token", Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("userId", "");
                        String sessionId = sharedPreferences.getString("sessionId", "");
                        //重新构造请求
                        Request.Builder requestBuild = request.newBuilder();
                        //放入请求参数
                        requestBuild.method(request.method(), request.body());
                        //添加userid, sessionid
                        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sessionId)){
                            requestBuild.addHeader("userId", userId);
                            requestBuild.addHeader("sessionId", sessionId);
                        }
                        //打包
                        Request response = requestBuild.build();
                        return chain.proceed(response);
                    }
                });

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Apis.BASE_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);

    }
    /***
     * 处理map集合的类型
     */
    public Map<String, RequestBody> generatrRequestBody(Map<String, String> requestDataMap){
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    /*
     *     put   请求===================
     * */
    public void put(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }
    /*
     *     delete   请求===================
     * */
    public void delete(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.delete(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    /**
     * get请求***************************************
     */
    public void get(String url,Map<String, String> map, HttpListener listener) {
        baseApis.get(url, map)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener));
    }

    /**
     * 上传文件post请求****************************
     */
    public void postFormBody(String url, Map<String, String> map, Map<String, RequestBody> mapBody, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (mapBody == null){
            mapBody = new HashMap<>();
        }
        baseApis.postFormBody(url, map, mapBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     * post请求******************************
     */
    public void post(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /*
    *    表单
    *  */
    public void postLogin(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.postLogin(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    private Observer getObserver(final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listener != null){
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if (listener != null){
                        listener.onSuccess(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null){
                        listener.onFail(e.getMessage());
                    }
                }
            }

        };
        return observer;
    }


    /***
     * 成员变量
     */
    HttpListener mListener;
    /***
     * set方法
     */
    public void result(HttpListener listener) {
        this.mListener = listener;
    }
    /***
     * 定义接口
     */
    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }

}
