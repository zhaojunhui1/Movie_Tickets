package com.bw.movie.fmk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.bw.movie.R;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.zjh.utils.statusbar.StatusBarWindowTop;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BasefActivity {
   // private TextView miao;
    private SharedPreferences sp;
    private int time = 3;
    private Timer timer;
    private int i;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 300:
                    i--;
                    if (i<0) {
                        timer.cancel();
                        startActivity(new Intent(MainActivity.this, LogActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        handler.removeCallbacksAndMessages(null);
                        finish();
                    }
            }
        }
    };

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0) {
//                if (time > 0) {
//                    time--;
//                    miao.setText(time + "s");
//                    handler.sendEmptyMessageDelayed(0, 1000);
//                }
//            }
//        }
//    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        StatusBarWindowTop.setStatusBarFullTransparent(MainActivity.this);
    }


    @Override
    protected void initData() {
            times();
    }

    private void times() {
        i=3;
        timer=new Timer();

        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(300);
            }
        };

        timer.schedule(timerTask,1,1000);
    }


}
