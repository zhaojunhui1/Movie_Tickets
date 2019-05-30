package com.bw.movie.zjh.utils.mvp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/16 10:34
 * desc   :  * 获取手机网络状态
 * -1:无网络   0:数据网络;  1:wifi网络
 * version: 1.0
 */
public class NetStartUtil {
    public static int getNetType(Context context){
        int mStart = -1;  // 无网络状态
        //获取android系统提供的服务, 转换成链接管理类,这个类专门处理链接相关的东西
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo封装了网络链接的信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            return mStart;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI){
            mStart = 1;
        }
        if (type == ConnectivityManager.TYPE_MOBILE){
            mStart = 0;
        }
        return mStart;
    }
}
