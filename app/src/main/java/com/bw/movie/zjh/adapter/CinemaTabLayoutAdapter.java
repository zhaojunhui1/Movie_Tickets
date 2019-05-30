package com.bw.movie.zjh.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bw.movie.zjh.ui.cinema.DetailsCallFragment;
import com.bw.movie.zjh.ui.cinema.DetailsDetailsFragment;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 16:37
 * desc   :
 * version: 1.0
 */
public class CinemaTabLayoutAdapter extends FragmentPagerAdapter {

    private String[] menus = new String[]{"详情","评论"};

    public CinemaTabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new DetailsDetailsFragment();
            case 1:
                return new DetailsCallFragment();
            default:
                return new DetailsDetailsFragment();
        }
    }

    @Override
    public int getCount() {
        return menus.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return menus[position];
    }
}
