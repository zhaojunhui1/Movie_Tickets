package com.bw.movie.zjh.ui.cinema.my;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.zjh.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/13 19:44
 * desc   :  我的关注
 * version: 1.0
 */
public class MyLookActivity extends BaseActivity {

    @BindView(R.id.cinema_group)
    RadioGroup cinema_group;
    @BindView(R.id.cinema_pager)
    ViewPager cinema_pager;
    private Unbinder bind;
    private List<Fragment> fragments;
    private MyLikeMovieFragment movieFragment;
    private MyLikeCinemaFragment cinemaFragment;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_look;
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        fragments = new ArrayList<>();
        movieFragment = new MyLikeMovieFragment();
        cinemaFragment = new MyLikeCinemaFragment();
        fragments.add(movieFragment);
        fragments.add(cinemaFragment);
    }

    @Override
    protected void initData() {
        //适配fragment
        cinema_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        //viewpager滑动监听
        cinema_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中时
            @Override
            public void onPageSelected(int position) {
                cinema_group.check(cinema_group.getChildAt(position).getId());
            }

            //滑动状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //选择监听事件
        cinema_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cinema_rb1:
                        cinema_pager.setCurrentItem(0);
                        break;
                    case R.id.cinema_rb2:
                        cinema_pager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });

        /*
         *   点击返回
         * */
        findViewById(R.id.preson_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
