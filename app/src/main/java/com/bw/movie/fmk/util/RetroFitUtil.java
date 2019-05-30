package com.bw.movie.fmk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.bw.movie.app.App;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/9 15:57
 * @Description:
 */
public class RetroFitUtil {

    public static RetroFitUtil retroFitUtil;
    public Retrofit retrofit;
    public Api api;
    private final OkHttpClient okHttpClient;

    //拦截器
    private RetroFitUtil(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new httplog());

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File file = new File(Environment.getExternalStorageDirectory(),"ttttt");

        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .cache(new Cache(file,10*1024*1024))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        SharedPreferences sharedPreferences = App.getApplication().getSharedPreferences("fmkcan", Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("userId", "");
                        String sessionId = sharedPreferences.getString("sessionId", "");
                        Request request = chain.request().newBuilder()
                                .addHeader("userId", userId)
                                .addHeader("sessionId", sessionId)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
    }


    //打印
    private class httplog implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            //Log.e("tab1","拦截=="+message);
        }
    }

    //构造方法私有化
    public Retrofit getRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.TOU)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    //单例模式
    public static synchronized RetroFitUtil getInRetroFitUtil(){
        if (retroFitUtil==null){
            retroFitUtil = new RetroFitUtil();
        }
        return retroFitUtil;
    }


    //网络判断
    public static boolean isNetworkConnected(Context context){

        //判断参数是否为空
        if (context!=null){
            //获取连接管理器
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取网络状态
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            //判断网络是否可用
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public <T>T setRtrofit(Class<T> tClass){
        return getRetrofit().create(tClass);
    }
}
