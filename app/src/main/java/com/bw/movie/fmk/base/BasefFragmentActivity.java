package com.bw.movie.fmk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/9 16:31
 * @Description:
 */
public abstract class BasefFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        initData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initData();

    //findViewByid
    protected <T extends View> T fvd (int resId){
        return findViewById(resId);
    }

}
