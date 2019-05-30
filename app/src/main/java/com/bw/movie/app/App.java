package com.bw.movie.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/10 14:07
 * @Description:
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //头参的上下文
        mContext = getApplicationContext();

        /**
         *  全局捕获异常初始化全局调用
         * */
        //CrashHandler.getInstance().init(this);
        initFrescoTu();

    }

    private void initFrescoTu() {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("kkk")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())
                .setMaxCacheSize(10*1024)
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        //初始化
        Fresco.initialize(this,imagePipelineConfig);
    }


    //调用上下文的方法
    public static Context getApplication(){
        return mContext;
    }

}
