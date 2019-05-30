package com.bw.movie.fmk.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.PaiQiAdapter;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.PaiQiBeam;
import com.bw.movie.fmk.bean.XiangQingZhuYeBean;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.MyPensterTwo;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GouMaiActivity extends BasefActivity implements VInterface.VInterfacegetXiangQingZhuYe ,VInterface.VInterfacegetDianYingPaiQi{

    private TextView goumaiName,goumaiDizhi;
    private SimpleDraweeView goumaiImage;
    private ImageView goumaiFan;
    private TextView goumaichuanname,goumaileixing,goumaidaoyan,goumaishichang,goumaichandi;
    private RecyclerView goumaiRecy;
    private PInterface.PInterfacegetXiangQingZhuYe pInterfacegetXiangQingZhuYe;
    private List<XiangQingZhuYeBean.ResultBean> xiangQingZhuYeBean = new ArrayList<>();
    //根据电影ID和影院ID查询电影排期列表
    private List<PaiQiBeam.ResultBean> paiQiBeam = new ArrayList<>();
    private PInterface.PInterfacegetDianYingPaiQi pInterfacegetDianYingPaiQi;
    private PaiQiAdapter paiQiAdapter;

    //布局
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gou_mai;
    }

    //控件
    @Override
    protected void initView() {
        goumaiName = fvd(R.id.goumai_name);
        goumaiDizhi = fvd(R.id.goumai_dizhi);
        goumaiImage = fvd(R.id.goumai_Image);
        goumaichuanname = fvd(R.id.goumai_chuan_name);
        goumaileixing = fvd(R.id.goumai_leixing);
        goumaidaoyan = fvd(R.id.goumai_daoyan);
        goumaishichang = fvd(R.id.goumai_shichang);
        goumaichandi = fvd(R.id.goumai_chandi);
        goumaiRecy = fvd(R.id.goumai_recy);
        goumaiFan = fvd(R.id.goumai_fan);
        pInterfacegetXiangQingZhuYe = new MyPenster(this);
        pInterfacegetDianYingPaiQi = new MyPensterTwo(this);
    }

    //数据
    @Override
    protected void initData() {
       /******** 接收GouPiaoActivity传来的值 ********/
        String movieIdgou = getIntent().getStringExtra("movieIdgou");
        HashMap<String,String> map = new HashMap<>();
        map.put("movieId", movieIdgou);
        pInterfacegetXiangQingZhuYe.getXiangQingZhuYe(null,map);

        String ying = getIntent().getStringExtra("ying");
        goumaiName.setText(ying);
        String di = getIntent().getStringExtra("di");
        goumaiDizhi.setText(di);

        //根据电影ID和影院ID查询电影排期列表
        init();

    }

    private void init() {
        //布局管理器
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        goumaiRecy.setLayoutManager(linear);
        //适配器
        paiQiAdapter = new PaiQiAdapter(paiQiBeam,GouMaiActivity.this);
        goumaiRecy.setAdapter(paiQiAdapter);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("movieId", "3");
        map2.put("cinemasId","2");
        pInterfacegetDianYingPaiQi.getDianYingPaiQi(null,map2);


        //返回
        goumaiFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(GouMaiActivity.this,GouPiaoActivity.class);
//                startActivity(intent);
            }
        });

    }





    @Override
    public void showXiangQingZhuYe(Object object) {
        XiangQingZhuYeBean xiangQingZhuYeBean2 = (XiangQingZhuYeBean)object;
        XiangQingZhuYeBean.ResultBean result = xiangQingZhuYeBean2.getResult();

        goumaiImage.setImageURI(result.getImageUrl());
        goumaichuanname.setText(result.getName());
        goumaidaoyan.setText(result.getDirector());
        goumaishichang.setText(result.getDuration());
        goumaichandi.setText(result.getPlaceOrigin());

        xiangQingZhuYeBean.add(result);
    }

    //根据电影ID和影院ID查询电影排期列表
    @Override
    public void getDianYingPaiQi(Object object) {
         PaiQiBeam paiQiBeam2 = (PaiQiBeam)object;
         List<PaiQiBeam.ResultBean> result = paiQiBeam2.getResult();
         paiQiBeam.addAll(result);
         Log.e("tab","paiQiBeam"+paiQiBeam);
    }

    //内存优化
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfacegetXiangQingZhuYe.onDsply();
        pInterfacegetXiangQingZhuYe=null;
    }

}
