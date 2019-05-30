package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/10 14:54
 * @Description:
 */
public class YinDaoAdapter  extends PagerAdapter {
    private ArrayList<View> data;
    private Context context;

    public YinDaoAdapter(ArrayList<View> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(data.get(position));
        return data.get(position);
    }
}
