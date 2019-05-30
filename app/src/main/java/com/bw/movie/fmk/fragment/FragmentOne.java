package com.bw.movie.fmk.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.fmk.activity.XiangQingActivity;
import com.bw.movie.fmk.adapter.BannerAdapter;
import com.bw.movie.fmk.adapter.RMenDianYingAdapter;
import com.bw.movie.fmk.adapter.RYingAdapter;
import com.bw.movie.fmk.adapter.ShangYingAdapter;
import com.bw.movie.fmk.base.BasefFragment;
import com.bw.movie.fmk.bean.BannerBean;
import com.bw.movie.fmk.bean.RMenBean;
import com.bw.movie.fmk.bean.RYingBean;
import com.bw.movie.fmk.bean.ShangYingBean;
import com.bw.movie.fmk.fragment.TabLayout.TabLayoutActivity;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.RecyclerCoverFlow;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/12 18:42
 * @Description:
 */
public class FragmentOne extends BasefFragment implements VInterface.VInterfaceLunBo,VInterface.VInterfaceRYing,VInterface.VInterfacegetShangYing,VInterface.VInterfaceRMen {

    //轮播
    public List<BannerBean.ResultBean> bannerBeans = new ArrayList<>();
    //热门电影
    public List<RMenBean.ResultBean> rmenBean = new ArrayList<>();
    //正在热映
    public List<RYingBean.ResultBean> ryYingBean = new ArrayList<>();
    //即将上映
    public List<ShangYingBean.ResultBean> shangyingBean = new ArrayList<>();
    private PInterface.PInterfaceLunBo pInterfaceLunBo;
    private RecyclerCoverFlow lunb;
    private BannerAdapter bannerAdapter;
    private RecyclerView rmen_id;
    private RMenDianYingAdapter rMenDianYingAdapter;
    private PInterface.PInterfaceRMen pInterfaceRMen;
    private RecyclerView rmen2_id;
    private PInterface.PInterfacegetRYing pInterfaceRYing;
    private RYingAdapter ryingAdapter;
    private PInterface.PInterfacegetShangYing pInterfacegetShangYing;
    private ShangYingAdapter shangYingAdapter;
    private RecyclerView rmen3_id;
    private SimpleDraweeView emen_you;
    private SimpleDraweeView eying_you;
    private SimpleDraweeView jijiang_you;
    private RelativeLayout fragmone_dong;
    private ImageView fragmone_sou;
    private AutoTransition mSet;
    private boolean isExpand = true;

    //布局
    @Override
    protected int initLayout() {
        return R.layout.fragment_one;
    }

    //控件
    @Override
    protected void ininView() {
        lunb = fvd(R.id.lubo);
        rmen_id = fvd(R.id.rmen_id);
        rmen2_id = fvd(R.id.rmen2_id);
        rmen3_id = fvd(R.id.rmen3_id);
        //热门电影
        emen_you = fvd(R.id.emen_you);
        //正在热映
        eying_you = fvd(R.id.eying_you);
        //即将上映
        jijiang_you = fvd(R.id.jijiang_you);

        //轮播图
        pInterfaceLunBo = new MyPenster(this);
        //热门电影
        pInterfaceRMen = new MyPenster(this);
        //正在热映
        pInterfaceRYing = new MyPenster(this);
        //即将上映
        pInterfacegetShangYing = new MyPenster(this);

        /*
          搜索，动画
         */
        fragmone_dong = fvd(R.id.fragmone_dong);
        fragmone_sou = fvd(R.id.fragmone_sou);

        //点击
        fragmone_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand == true) {
                    zhankai();
                    isExpand = false;
                } else {
                    suofang();
                    isExpand = true;
                }
            }
        });

        //展开
        zhankai();

        //缩放
        suofang ();
    }

    /*
      展开
      */
    private void zhankai(){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fragmone_dong.getLayoutParams();
        layoutParams.width = dip2px(getActivity(), 330);
        fragmone_dong.setLayoutParams(layoutParams);
        //开始动画
        beginDelayedTransition(fragmone_dong);
    }

    /*
     缩放
     */
    private void suofang() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fragmone_dong.getLayoutParams();
        layoutParams.width = dip2px(getActivity(), 330);
        fragmone_dong.setLayoutParams(layoutParams);
        //开始动画
        beginDelayedTransition(fragmone_dong);
    }

    //开始动画
    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    //px转换dp
    public static int dip2px(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    //数据
    @Override
    protected void initData() {

        //点击，跳转TabLayout
        initTabLayout();

        /****      轮播图         ****/

        pInterfaceLunBo.getLunBo(null);

        //适配器
        bannerAdapter = new BannerAdapter(bannerBeans,getActivity());
        lunb.setAdapter(bannerAdapter);

        //让轮播图显示中间的图片
         //lunb.smoothScrollToPosition();
        //自定义接口回调，点击图片使它展示到中间
        bannerAdapter.setOnItem(new BannerAdapter.OnItem() {
            @Override
            public void onClick(int position) {
                lunb.smoothScrollToPosition(position);
            }
        });


        /*                   热门电影               */

        //实例化布局管理器
        LinearLayoutManager linear = new LinearLayoutManager(getActivity());
        linear.setOrientation(LinearLayoutManager.HORIZONTAL);
        rmen_id.setLayoutManager(linear);

        pInterfaceRMen.getRMen(null);

        //适配器
        rMenDianYingAdapter = new RMenDianYingAdapter(rmenBean,getActivity());
        rmen_id.setAdapter(rMenDianYingAdapter);

        //条目点击
        rMenDianYingAdapter.setOnItem(new RMenDianYingAdapter.OnItem() {
            @Override
            public void onClick(int position, String movieId) {
                Intent intent = new Intent(getActivity(),XiangQingActivity.class);
                intent.putExtra("id",movieId);
                startActivity(intent);
            }
        });


        /*                   正在热映             */

        //实例化布局管理器
        LinearLayoutManager linear2 = new LinearLayoutManager(getActivity());
        linear2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rmen2_id.setLayoutManager(linear2);

        pInterfaceRYing.getRYing(null);

        //适配器
        ryingAdapter = new RYingAdapter(ryYingBean,getActivity());
        rmen2_id.setAdapter(ryingAdapter);

        //条目点击
        ryingAdapter.setOnItem(new RYingAdapter.OnItem() {
            @Override
            public void onClick(int position, String movieId) {
                Intent intent = new Intent(getActivity(),XiangQingActivity.class);
                intent.putExtra("id",movieId);
                startActivity(intent);
            }

        });

        /*                   即将上映             */

        //实例化布局管理器
        LinearLayoutManager linear3 = new LinearLayoutManager(getActivity());
        linear3.setOrientation(LinearLayoutManager.HORIZONTAL);
        rmen3_id.setLayoutManager(linear3);

        pInterfacegetShangYing.getShangYing(null);

        //适配器
        shangYingAdapter = new ShangYingAdapter(shangyingBean,getActivity());
        rmen3_id.setAdapter(shangYingAdapter);

        //条目点击
        shangYingAdapter.setOnItem(new ShangYingAdapter.OnItem() {
            @Override
            public void onClick(int position, String movieId) {
                Intent intent = new Intent(getActivity(),XiangQingActivity.class);
                intent.putExtra("id",movieId);
                startActivity(intent);
            }
        });
    }

    //点击，跳转TabLayout
    private void initTabLayout() {
        emen_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TabLayoutActivity.class));
            }
        });

        eying_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabLayoutActivity.class);
                intent.putExtra("2",22);
                startActivity(intent);
            }
        });

        jijiang_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabLayoutActivity.class);
                intent.putExtra("3",33);
                startActivity(intent);
            }
        });
    }

    //轮播
    @Override
    public void showLunBo(Object object) {

        BannerBean bannerBean = (BannerBean) object;
        List<BannerBean.ResultBean> result = bannerBean.getResult();
        bannerBeans.addAll(result);
        bannerAdapter.notifyDataSetChanged();

        rMenDianYingAdapter.notifyDataSetChanged();

        Log.e("taggg","轮播V=="+bannerBean.getMessage());
    }

    //热门电影
    @Override
    public void showRMen(Object object) {

        RMenBean rMenBean2 = (RMenBean) object;
        List<RMenBean.ResultBean> result = rMenBean2.getResult();
        rmenBean.addAll(result);
        rMenDianYingAdapter.notifyDataSetChanged();

    }

    //正在热映
    @Override
    public void showRYing(Object object) {
        RYingBean ryingBean2 = (RYingBean) object;
        List<RYingBean.ResultBean> result = ryingBean2.getResult();
        ryYingBean.addAll(result);
        ryingAdapter.notifyDataSetChanged();
    }

    //即将上映
    @Override
    public void showShangYing(Object object) {
        ShangYingBean shangYingBean2 = (ShangYingBean) object;
        List<ShangYingBean.ResultBean> result = shangYingBean2.getResult();
        shangyingBean.addAll(result);
        shangYingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //轮播,热门电影
        pInterfaceLunBo.onDsply();
        pInterfaceLunBo=null;
        //热门电影
        pInterfaceRMen.onDsply();
        pInterfaceRMen=null;
        //正在热映
        pInterfaceRYing.onDsply();
        pInterfaceRYing=null;
        //即将上映
        pInterfacegetShangYing.onDsply();
        pInterfacegetShangYing=null;
    }

}
