package com.bw.movie.fmk.fragment.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.base.BasefActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 19:45
 * @Description:
 */
public class TabLayoutActivity extends BasefActivity {


    private RadioButton tab_rmen;
    private RadioButton tab_rying;
    private RadioButton tab_shangying;
    private RadioGroup tab_group;
    private ViewPager tab_pager;
    private ImageView tab_dingwei;
    private TextView tab_beijing;
    private ImageView fvd;
    private EditText tab_ed_shuru;
    private List<Fragment> fragments;
    private TabLayoutOne tabLayoutOne;
    private TabLayoutTwo TabLayouttwo;
    private TabLayoutThree TabLayoutthree;

    //布局
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tablayout;
    }

    //控件
    @Override
    protected void initView() {

        //定位
        tab_dingwei = fvd(R.id.tab_dingwei);
        tab_beijing = fvd(R.id.tab_beijing);

        //搜索
        fvd = fvd(R.id.tab_sosuo);
        tab_ed_shuru = fvd(R.id.tab_ed_shuru);

        tab_rmen = fvd(R.id.tab_rmen);
        tab_rying = fvd(R.id.tab_rying);
        tab_shangying = fvd(R.id.tab_shangying);

        tab_group = fvd(R.id.tab_group);
        tab_pager = fvd(R.id.tab_pager);
    }

    //数据
    @Override
    protected void initData() {

        fragments = new ArrayList<>();
        tabLayoutOne = new TabLayoutOne();
        TabLayouttwo = new TabLayoutTwo();
        TabLayoutthree = new TabLayoutThree();
        fragments.add(tabLayoutOne);
        fragments.add(TabLayouttwo);
        fragments.add(TabLayoutthree);

        int stringExtra = getIntent().getIntExtra("2",222);
        if (stringExtra==22){

            tab_pager.setCurrentItem(1);
        }

        int stringExtra1 = getIntent().getIntExtra("3",333);
        if (stringExtra1==33){
            tab_pager.setCurrentItem(2);
        }

        //适配fragment
        tab_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        tab_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中时
            @Override
            public void onPageSelected(int position) {
                tab_group.check(tab_group.getChildAt(position).getId());
            }

            //滑动状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //选择监听事件
        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_rmen:
                        tab_pager.setCurrentItem(0);
                        break;
                    case R.id.tab_rying:
                        tab_pager.setCurrentItem(1);
                        break;
                    case R.id.tab_shangying:
                        tab_pager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
