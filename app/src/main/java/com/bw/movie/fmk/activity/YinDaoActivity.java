package com.bw.movie.fmk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.YinDaoAdapter;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.zjh.utils.statusbar.StatusBarWindowTop;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class YinDaoActivity extends BasefActivity {

    private ViewPager pager;
    private RadioGroup radioGroup;
    private ArrayList<View> data;
    private Timer timer;
    private int i;
    private SharedPreferences sp;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 300:
                    i--;
                    if (i<0) {
                        timer.cancel();
                        //存值
                        sp.edit().putBoolean("flag", true).commit();
                        startActivity(new Intent(YinDaoActivity.this, MainActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        handler.removeCallbacksAndMessages(null);
                        finish();
                    }
                    }
        }
    };


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_yin_dao;
    }

    @Override
    protected void initView() {
        StatusBarWindowTop.setStatusBarFullTransparent(YinDaoActivity.this);
        pager = fvd(R.id.pager);
        radioGroup = fvd(R.id.radioGroup);

    }

    @Override
    protected void initData() {

        //隐藏

        data = new ArrayList<View>();

        LayoutInflater lf = LayoutInflater.from(YinDaoActivity.this);
        View view0 = lf.inflate(R.layout.yde_yi, null);
        View view1 = lf.inflate(R.layout.yde_er, null);
        View view2 = lf.inflate(R.layout.yde_san, null);
        View view3 = lf.inflate(R.layout.yde_si, null);
        data.add(view0);
        data.add(view1);
        data.add(view2);
        data.add(view3);



        //加间距
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams
                (RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);

        //设置键间距
        params.leftMargin = 10;

        for (int i = 0; i < data.size(); i++)
        {
            //设置小圆点
            RadioButton radioButton = new RadioButton(YinDaoActivity.this);
           // radioButton.setButtonDrawable(R.drawable.select);
            radioGroup.addView(radioButton, params);
        }

        //适配器
        YinDaoAdapter yinDaoAdapter = new YinDaoAdapter(data, YinDaoActivity.this);
        pager.setAdapter(yinDaoAdapter);

        radioGroup.check(radioGroup.getChildAt(0).getId());

        //页面滑动，小圆点跟着走
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radioGroup.check(radioGroup.getChildAt(i).getId());
                if (i== data.size()-1){
                    times();
                }
                else{
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        sp = getSharedPreferences("key",MODE_PRIVATE);
        boolean flag = sp.getBoolean("flag", false);
        if (flag){
            Intent intent = new Intent(YinDaoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            handler.sendEmptyMessageDelayed(0,1000);
        }
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
