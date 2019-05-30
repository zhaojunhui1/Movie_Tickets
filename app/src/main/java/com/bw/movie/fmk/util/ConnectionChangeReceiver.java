package com.bw.movie.fmk.util;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/17 14:28
 * @Description:
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    //标记的作用是为了避免对话框弹出多次
    private boolean isConnect = false;//标记是否连接
    private boolean isGPRS = false;//标记连接方式是移动网络
    private boolean isWifi = false;//标记连接方式是wifi

    @Override
    public void onReceive(final Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        //无网络状态
        if(networkInfo == null || !networkInfo.isConnected()){
            if(isConnect){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("已关闭移动数据");
                alertDialog.setMessage("您可以在“设置”中为此应用打开移动数据。");
                alertDialog.setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                });

               // LogHelp.i("dialog","dialog1");
                isGPRS = false;
                isWifi = false;
                isConnect = false;
                alertDialog.show();
            }
        }else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            if(!isGPRS){
                //只有GPRS，没有wifi的状态
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("流量使用提示");
                alertDialog.setMessage("当前网络无Wi-Fi，继续播放可能会被运营商收取流量费用");
                alertDialog.setPositiveButton("继续播放", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("停止播放", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
               // LogHelp.i("dialog", "dialog2");
                isWifi = false;
                isGPRS = true;
                isConnect = true;
                alertDialog.show();
            }
        }else if(networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            if(!isWifi){
               // LogHelp.i("dialog", "dialog3");
                isGPRS = false;
                isConnect = true;
                isWifi = true;
            }
        }
    }
}