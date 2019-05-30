package com.bw.movie.fmk.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.JuZhaoAdapter;
import com.bw.movie.fmk.adapter.XiangQingAdapter;
import com.bw.movie.fmk.adapter.YingPingAdapter;
import com.bw.movie.fmk.adapter.YuGaoAdapter;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.DianYingPingLunBean;
import com.bw.movie.fmk.bean.DianYingYuGaoBean;
import com.bw.movie.fmk.bean.DianZanBean;
import com.bw.movie.fmk.bean.GuanZhuBean;
import com.bw.movie.fmk.bean.TianJiaPingLunBean;
import com.bw.movie.fmk.bean.XiangQingZhuYeBean;
import com.bw.movie.fmk.fragment.FragmentActivity;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class XiangQingActivity extends BasefActivity implements VInterface.VInterfacegetXiangQingZhuYe,VInterface.VInterfaceYuGao,VInterface.VInterfacegetDianYingGuanZhu,VInterface.VInterfacegetDianYingPingLun
 ,VInterface.VInterfacegetPingLunDianZan ,VInterface.VInterfacegetPTianJIaPingLun {

    private SimpleDraweeView xiangqing_guanzhu;
    private TextView xiangqing_xiang;
    private TextView xiangqing_yugao;
    private TextView xiangqing_juzhao;
    private TextView xiangqing_yingping;
    private SimpleDraweeView xiangqing_fan;
    private TextView xiangqing_gou;
    private TextView xiangqing_name;
    private SimpleDraweeView xaingqing_image;
    //电影关注
    private List<GuanZhuBean> guanZhuBean = new ArrayList<>();
    //主页
    private List<XiangQingZhuYeBean.ResultBean> xiangQingZhuYeBean = new ArrayList<>();
    //电影预告
    private List<DianYingYuGaoBean.ResultBean.ShortFilmListBean> dianyingYuGaoBean = new ArrayList<>();
    //电影剧照
    private List<String> dianyingJuZhaoBean = new ArrayList<>();
    //电影评论
    private List<DianYingPingLunBean.ResultBean> dianYingPingLunBean = new ArrayList<>();
    //评论点赞
    private List<String> dianZanBeans = new ArrayList<>();
    //评论回复
    private List<String> tianJiaPingLunBean = new ArrayList<>();
    private PInterface.PInterfacegetXiangQingZhuYe pInterfacegetXiangQingZhuYe;
    private TextView dianying_xiangqing;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow1;
    private PopupWindow popupWindow2;
    private SimpleDraweeView xiangqing_pop_image;
    private TextView xiangqing_pop_leixing;
    private TextView xiangqing_pop_daoyan;
    private TextView xiangqing_pop_shichang;
    private TextView xiangqing_pop_chandi;
    private TextView xiangqing_pop_juqing;
    private PInterface.PInterfacegetDianYingYuGao pInterfacegetDianYingYuGao;
    private RecyclerView yugao_pop_recy;
    private RecyclerView juzao_pop_rec;
    private YuGaoAdapter yuGaoAdapter;
    private List<DianYingYuGaoBean.ResultBean.ShortFilmListBean> shortFilmList;
    private JuZhaoAdapter juZhaoAdapter;
    private XRecyclerView xiang_pop_xrec;
    private XiangQingAdapter xiangQingAdapter;
    private HashMap<String, String> mapxiangqing;
    private int page = 1;
    private ImageView juzhao_pop_xia;
    private ImageView xiangqing_pop_xia;
    private ImageView yugao_pop_xia;
    private XRecyclerView yingping_pop_rec;
    private ImageView yingping_pop_xia;
    private PopupWindow popupWindow3;
    private YingPingAdapter yingpinAdapter;
    private PInterface.PInterfacegetDianYingPingLun pInterfacegetDianYingPingLun;
    private HashMap<String, String> mapLun;
    private String idxiang;
    private String name;
    private PInterface.PInterfacegetDianZan pInterfacegetDianZan;
    private PInterface.PInterfacegetTianJIaPingLun pInterfacegetTianJIaPingLun;
    private ImageView tianjia_pinglun;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_xiang_qing;
    }

    @Override
    protected void initView() {

        //左上角，电影详情
        dianying_xiangqing = fvd(R.id.dianying_xiangqing);
        //右上角，电影关注
        xiangqing_guanzhu = fvd(R.id.xiangqing_guanzhu);
        //电影详情
        xiangqing_xiang = fvd(R.id.xiangqing_xiang);
        //预告
        xiangqing_yugao = fvd(R.id.xiangqing_yugao);
        //剧照
        xiangqing_juzhao = fvd(R.id.xiangqing_juzhao);
        //影评
        xiangqing_yingping = fvd(R.id.xiangqing_yingping);
        //返回
        xiangqing_fan = fvd(R.id.xiangqing_fan);
        //购票
        xiangqing_gou = fvd(R.id.xiangqing_gou);
        xiangqing_name = fvd(R.id.xiangqing_name);
        xaingqing_image = fvd(R.id.xaingqing_image);
        pInterfacegetXiangQingZhuYe = new MyPenster(this);
        pInterfacegetDianYingYuGao = new MyPenster(this);
        pInterfacegetDianYingPingLun = new MyPenster(this);
        pInterfacegetDianZan = new MyPenster(this);
        pInterfacegetTianJIaPingLun = new MyPenster(this);

         //电影关注
         initguanzhu();

         //电影详情
         initpop();

         //电影预告
         initpopyugao();

         //电影剧照
         initpopjuzhao();

        //电影评价
        initpoppingjia();

    }


    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("id");

        HashMap<String,String> map = new HashMap<>();
        map.put("movieId",id);

        pInterfacegetXiangQingZhuYe.getXiangQingZhuYe(null,map);

        //点击跳转
        xiangqing_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XiangQingActivity.this,FragmentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //点击购票
        xiangqing_gou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /******* 带值跳转到GouPiaoActivity *******/
                String gouid = getIntent().getStringExtra("id");

                HashMap<String,String> mapgou = new HashMap<>();
                mapgou.put("movieId", gouid);

                Intent intent = new Intent(XiangQingActivity.this,GouPiaoActivity.class);
                intent.putExtra("gou", name);
                intent.putExtra("id",gouid);
                startActivity(intent);
                finish();
            }
        });
    }

    //电影关注
    private void initguanzhu() {
        xiangqing_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //电影关注
    @Override
    public void getDianYingGuanZhu(Object object) {
//        GuanZhuBean guanZhuBeans = (GuanZhuBean)object;
//        String message = guanZhuBeans.getMessage();
//        guanZhuBean.add(message);
    }

    //电影
    @Override
    public void showXiangQingZhuYe(Object object) {

        XiangQingZhuYeBean xiangQingZhuYeBeans = (XiangQingZhuYeBean)object;
        XiangQingZhuYeBean.ResultBean result = xiangQingZhuYeBeans.getResult();

        //首页赋值
        name = result.getName();
        xiangqing_name.setText(name);
        xaingqing_image.setImageURI(result.getImageUrl());

        //详情的赋值
        xiangqing_pop_image.setImageURI(result.getImageUrl());
        xiangqing_pop_daoyan.setText(result.getDirector());
        xiangqing_pop_shichang.setText(result.getDuration());
        xiangqing_pop_chandi.setText(result.getPlaceOrigin());
        xiangqing_pop_juqing.setText(result.getSummary());

        xiangQingZhuYeBean.add(result);
        xiangQingAdapter.notifyDataSetChanged();
    }

    //电影预告,剧照
    @Override
    public void getYuGao(Object object) {
        DianYingYuGaoBean dianYingYuGaoBeans = (DianYingYuGaoBean)object;
        shortFilmList = dianYingYuGaoBeans.getResult().getShortFilmList();

        Log.e("tab","shortFilmList=="+ shortFilmList.get(0).getVideoUrl());

        dianyingYuGaoBean.addAll(shortFilmList);
       // yuGaoAdapter.setaa(dianYingYuGaoBeans.getResult().getShortFilmList());
        yuGaoAdapter.notifyDataSetChanged();

        //剧照
        DianYingYuGaoBean.ResultBean result = dianYingYuGaoBeans.getResult();
        List<String> posterList = result.getPosterList();
      //  Log.e("tab","posterList=="+ posterList);

//        for (int j = 0; j < posterList.size(); j++) {
//
//            String[] split = posterList.get(j).split(",");
//
//        }

        dianyingJuZhaoBean.addAll(posterList);

        juZhaoAdapter.notifyDataSetChanged();
    }

    //电影评论
    @Override
    public void getDianYingPingLun(Object object) {
        DianYingPingLunBean dianYingPingLunBean2 = (DianYingPingLunBean)object;
        List<DianYingPingLunBean.ResultBean> result4 = dianYingPingLunBean2.getResult();
        dianYingPingLunBean.addAll(result4);
        Log.e("tab","dianYingPingLunBean=="+object);

        yingpinAdapter.notifyDataSetChanged();
    }

    //电影评论点赞
    @Override
    public void getPingLunDianZan(Object object) {
        DianZanBean dianZanBean2 = (DianZanBean)object;
        String message = dianZanBean2.getMessage();
        dianZanBeans.add(message);

        Toast.makeText(this, dianZanBean2.getMessage(), Toast.LENGTH_SHORT).show();

        yingpinAdapter.notifyDataSetChanged();
    }

    //添加电影评论
    @Override
    public void getTianJIaPingLun(Object object) {
         TianJiaPingLunBean tianJiaPingLunBean2 = (TianJiaPingLunBean)object;
         String message = tianJiaPingLunBean2.getMessage();
         tianJiaPingLunBean.add(message);
         yingpinAdapter.notifyDataSetChanged();
    }

    //pop,详情
    private void initpop() {

        View view = LayoutInflater.from(this).inflate(R.layout.pop_window, null, false);

        xiangqing_pop_image = view.findViewById(R.id.xiangqing_pop_image);

       // xiangqing_pop_leixing = view.findViewById(R.id.xiangqing_pop_leixing);
        xiangqing_pop_daoyan = view.findViewById(R.id.xiangqing_pop_daoyan);
        xiangqing_pop_shichang = view.findViewById(R.id.xiangqing_pop_shichang);
        xiangqing_pop_chandi = view.findViewById(R.id.xiangqing_pop_chandi);

        xiangqing_pop_juqing = view.findViewById(R.id.xiangqing_pop_juqing);

        xiang_pop_xrec = view.findViewById(R.id.xiang_pop_xrec);
        xiangqing_pop_xia = view.findViewById(R.id.xiangqing_pop_xia);

//         popupWindow= new PopupWindow
//                (view,getWindowManager().getDefaultDisplay().getWidth(),3200);

        //第一个参数是popWindow中的View,第二个是宽度，第三个是高度，第四个是能否获取焦点
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置popwindow是否能响应外界事件
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow.setFocusable(true);
        //实例化一个颜色
        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.colorpop));
        //弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);

        xiangqing_pop_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //电影详情
        xiangqing_xiang.setOnClickListener(new View.OnClickListener() {

            @Override
                    public void onClick(View v) {

                        idxiang = getIntent().getStringExtra("id");

                        mapxiangqing = new HashMap<>();
                        mapxiangqing.put("movieId", idxiang);

                        pInterfacegetXiangQingZhuYe.getXiangQingZhuYe(null, mapxiangqing);
                        //动画
                        popupWindow.setAnimationStyle(R.style.pop_animation);
                        popupWindow.showAtLocation(v,Gravity.BOTTOM,0,210);
                       // popupWindow.showAsDropDown(v, 50, 190);
                    }
                });

        //上拉，下拉
        xiang_pop_xrec.setPullRefreshEnabled(true);
        xiang_pop_xrec.setLoadingMoreEnabled(true);

        //布局管理器
        LinearLayoutManager xxiangqing = new LinearLayoutManager(this);
        xxiangqing.setOrientation(LinearLayoutManager.VERTICAL);
        xiang_pop_xrec.setLayoutManager(xxiangqing);

        //适配器
        xiangQingAdapter = new XiangQingAdapter(xiangQingZhuYeBean,XiangQingActivity.this);
        xiang_pop_xrec.setAdapter(xiangQingAdapter);

        xiang_pop_xrec.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xiangQingZhuYeBean.clear();
                page = 1;
                pInterfacegetXiangQingZhuYe.getXiangQingZhuYe(null,mapxiangqing);
                xiang_pop_xrec.refreshComplete();
                xiangQingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                //加载更多数据
                for (int i = 0; i < 0; i++) {
                    page++;
                    pInterfacegetXiangQingZhuYe.getXiangQingZhuYe(null,mapxiangqing);
                    xiang_pop_xrec.loadMoreComplete();
                }
               xiangQingAdapter.notifyDataSetChanged();
            }
        });


        //电影预告
        xiangqing_yugao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");

                HashMap<String,String> map = new HashMap<>();
                map.put("movieId",id);

                pInterfacegetDianYingYuGao.getDianYingYuGao(null,map);

                //动画
                popupWindow.setAnimationStyle(R.style.pop_animation);
               // popupWindow.showAtLocation(v,Gravity.BOTTOM,0,210);
                popupWindow.showAsDropDown(v,Gravity.BOTTOM,600,0);
            }
        });

        popupWindow.dismiss();
    }


    //pop,预告
    private void initpopyugao() {

        View view = LayoutInflater.from(this).inflate(R.layout.pop_window_yugao, null, false);

        yugao_pop_recy = view.findViewById(R.id.yugao_pop_recy);
        yugao_pop_xia = view.findViewById(R.id.yugao_pop_xia);

        //布局管理器
        LinearLayoutManager linear2 = new LinearLayoutManager(this);
        linear2.setOrientation(LinearLayoutManager.VERTICAL);
        yugao_pop_recy.setLayoutManager(linear2);

        //适配器
        yuGaoAdapter = new YuGaoAdapter(dianyingYuGaoBean,XiangQingActivity.this);
        yugao_pop_recy.setAdapter(yuGaoAdapter);

        //第一个参数是popWindow中的View,第二个是宽度，第三个是高度，第四个是能否获取焦点
        popupWindow1 = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置popwindow是否能响应外界事件
        popupWindow1.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow1.setFocusable(true);
        //实例化一个颜色
        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.colorpop));
        //弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);

        yugao_pop_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow1.dismiss();
            }
        });

        //电影预告
        xiangqing_yugao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");

                HashMap<String,String> map = new HashMap<>();
                map.put("movieId",id);

                pInterfacegetDianYingYuGao.getDianYingYuGao(null,map);
                //动画
                popupWindow1.setAnimationStyle(R.style.pop_animation);
                popupWindow1.showAtLocation(v,Gravity.BOTTOM,0,210);
            }
        });

        popupWindow1.dismiss();
    }

    //剧照
    private void initpopjuzhao() {

        View view = LayoutInflater.from(this).inflate(R.layout.pop_window_juzhao, null, false);

        juzao_pop_rec = view.findViewById(R.id.juzao_pop_rec);
        juzhao_pop_xia = view.findViewById(R.id.juzhao_pop_xia);

        //布局管理器
        StaggeredGridLayoutManager stag = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        juzao_pop_rec.setLayoutManager(stag);

        juzao_pop_rec.setLayoutManager(stag);

        //适配器
        juZhaoAdapter = new JuZhaoAdapter(dianyingJuZhaoBean,XiangQingActivity.this);
        juzao_pop_rec.setAdapter(juZhaoAdapter);

        //第一个参数是popWindow中的View,第二个是宽度，第三个是高度，第四个是能否获取焦点
        popupWindow2 = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置popwindow是否能响应外界事件
        popupWindow2.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow2.setFocusable(true);
        //实例化一个颜色
        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.colorpop));
        //弹出窗体的背景
        popupWindow2.setBackgroundDrawable(dw);

        juzhao_pop_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow2.dismiss();
            }
        });

        //电影剧照
        xiangqing_juzhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");

                HashMap<String,String> map = new HashMap<>();
                map.put("movieId",id);

                pInterfacegetDianYingYuGao.getDianYingYuGao(null,map);
                //动画
                popupWindow2.setAnimationStyle(R.style.pop_animation);
                popupWindow2.showAtLocation(v,Gravity.BOTTOM,0,210);
            }
        });

        popupWindow2.dismiss();
    }


    //电影评价
    private void initpoppingjia() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_window_yingping, null, false);

        yingping_pop_rec = view.findViewById(R.id.yingping_pop_rec);
        yingping_pop_xia = view.findViewById(R.id.yingping_pop_xia);
        tianjia_pinglun = view.findViewById(R.id.tianjia_pinglun);

        //添加评论
        tianjia_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tab","添加评论");
                String id = getIntent().getStringExtra("id");
                HashMap<String,String> mapTian = new HashMap<>();
                mapTian.put("movieId",id);
                mapTian.put("commentId",id);
                mapTian.put("commentContent","555");
                Log.e("tab","mapTian"+mapTian);
                pInterfacegetTianJIaPingLun.getTianJIaPingLun(null,mapTian);
            }
        });


        //布局管理器
        LinearLayoutManager linear4 = new LinearLayoutManager(this);
        linear4.setOrientation(LinearLayoutManager.VERTICAL);
        yingping_pop_rec.setLayoutManager(linear4);

        //适配器
        yingpinAdapter = new YingPingAdapter(dianYingPingLunBean,XiangQingActivity.this);
        yingping_pop_rec.setAdapter(yingpinAdapter);

        //第一个参数是popWindow中的View,第二个是宽度，第三个是高度，第四个是能否获取焦点
        popupWindow3 = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置popwindow是否能响应外界事件
        popupWindow3.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow3.setFocusable(true);
        //实例化一个颜色
        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.colorpop));
        //弹出窗体的背景
        popupWindow3.setBackgroundDrawable(dw);

        yingping_pop_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow3.dismiss();
            }
        });

        //点赞
        yingpinAdapter.setOnItem(new YingPingAdapter.OnItem() {
            @Override
            public void onClick(int position, int commentId) {
                String id = getIntent().getStringExtra("id");
                HashMap<String,String> mapZan = new HashMap<>();
                mapZan.put("commentId",id);
                Log.e("tab","点赞点击了");

                pInterfacegetDianZan.getDianZan(null, mapZan);
            }
        });

        //电影评价
        xiangqing_yingping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");

                mapLun = new HashMap<>();
                mapLun.put("movieId",id);
                mapLun.put("page","1");
                mapLun.put("count","5");
                pInterfacegetDianYingPingLun.getDianYingPingLun(null, mapLun);
                Log.e("tab","pInterfacegetDianYingPingLun=="+pInterfacegetDianYingPingLun);
                //动画
                popupWindow3.setAnimationStyle(R.style.pop_animation);
                popupWindow3.showAtLocation(v,Gravity.BOTTOM,0,210);
            }
        });


        //上拉，下拉
        yingping_pop_rec.setPullRefreshEnabled(true);
        yingping_pop_rec.setLoadingMoreEnabled(true);

        yingping_pop_rec.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xiangQingZhuYeBean.clear();
                page = 1;
                pInterfacegetDianYingPingLun.getDianYingPingLun(null,mapLun);
                yingping_pop_rec.refreshComplete();
                yingpinAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                //模拟加载更多数据
                    for (int i = 0; i < dianYingPingLunBean.size(); i++) {
                        page++;
                        pInterfacegetDianYingPingLun.getDianYingPingLun(null,mapLun);
                    }
                    yingpinAdapter.notifyDataSetChanged();
            }
        });
        popupWindow3.dismiss();
    }

    //内存优化
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfacegetXiangQingZhuYe.onDsply();
        pInterfacegetXiangQingZhuYe=null;

        pInterfacegetDianYingYuGao.onDsply();
        pInterfacegetDianYingYuGao=null;

        pInterfacegetDianYingPingLun.onDsply();
        pInterfacegetDianYingPingLun=null;

        pInterfacegetTianJIaPingLun.onDsply();
        pInterfacegetTianJIaPingLun=null;
    }
}
