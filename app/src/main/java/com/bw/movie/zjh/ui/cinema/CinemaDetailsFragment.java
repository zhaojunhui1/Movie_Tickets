package com.bw.movie.zjh.ui.cinema;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.zjh.adapter.CinemaTabLayoutAdapter;
import com.bw.movie.zjh.base.BaseFragment;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 14:00
 * desc   :
 * version: 1.0
 */
public class CinemaDetailsFragment extends BaseFragment {

    private TabLayout cinema_tabLayout;
    private ViewPager cinema_pager;
    private ImageView details_down;

    @Override
    protected int setView() {
        return R.layout.fragment_cinema_details;
    }

    @Override
    protected void init(View view) {
        cinema_tabLayout = view.findViewById(R.id.cinema_tabLayout);
        cinema_pager = view.findViewById(R.id.cinema_pager);
        details_down = view.findViewById(R.id.details_down);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        cinema_pager.setAdapter(new CinemaTabLayoutAdapter(getChildFragmentManager()));
        cinema_tabLayout.setupWithViewPager(cinema_pager);

        details_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


}
