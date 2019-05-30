package com.bw.movie.fmk.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.zjh.base.BaseActivity;
import com.bw.movie.zjh.utils.statusbar.StatusBarWindowTop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentActivity extends BaseActivity {

    private Unbinder bind;
    @BindView(R.id.movie_framelayout)
    FrameLayout movie_framelayout;
    @BindView(R.id.movie_viewpager)
    CustomViewPager movie_viewpager;
    @BindView(R.id.movie_group)
    RadioGroup movie_group;

    private FragmentOne homeFragment;
    private FragmentTwo cinemaFragment;
    private FragmentThree miniFragment;
    private List<Fragment> fragments;

    @Override
    public int bindLayout() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initView() {
        //透明状态栏
        StatusBarWindowTop.setStatusBarFullTransparent(this);

        bind = ButterKnife.bind(this);
        fragments = new ArrayList<>();
        homeFragment = new FragmentOne();
        cinemaFragment = new FragmentTwo();
        miniFragment = new FragmentThree();
        fragments.add(homeFragment);
        fragments.add(cinemaFragment);
        fragments.add(miniFragment);
    }

    @Override
    protected void initData() {
        //适配fragment
        movie_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        //滑动状态
        movie_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中时
            @Override
            public void onPageSelected(int position) {
                movie_group.check(movie_group.getChildAt(position).getId());
            }

            //滑动状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //选择监听事件
        movie_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_movie_home:
                        movie_viewpager.setCurrentItem(0);
                        break;
                    case R.id.rb_movie_cinema:
                        movie_viewpager.setCurrentItem(1);
                        break;
                    case R.id.rb_movie_my:
                        movie_viewpager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
