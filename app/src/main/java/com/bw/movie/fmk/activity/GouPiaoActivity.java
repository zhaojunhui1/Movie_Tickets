package com.bw.movie.fmk.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.GouPiaoAdapter;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.GouPiaoBean;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GouPiaoActivity extends BasefActivity implements VInterface.VInterfaceYuGao {

    private TextView goupiao_name;
    private XRecyclerView goupiao_xrec;
    private PInterface.PInterfacegetGouPiao pInterfacegetGouPiao;
    private List<GouPiaoBean.ResultBean> gouPiaoBean = new ArrayList<>();
    private int page=1;
    private GouPiaoAdapter gouPiaoAdapter;
    private HashMap<String,String> mapgou;
    private String gou;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gou_piao;
    }

    //控件
    @Override
    protected void initView() {

        goupiao_name = fvd(R.id.goupiao_name);


        goupiao_xrec = fvd(R.id.goupiao_xrec);
        pInterfacegetGouPiao = new MyPenster(this);
    }

    //数据
    @Override
    protected void initData() {

        //布局管理器
        LinearLayoutManager linear4 = new LinearLayoutManager(this);
        linear4.setOrientation(LinearLayoutManager.VERTICAL);
        goupiao_xrec.setLayoutManager(linear4);

        //适配器
        gouPiaoAdapter = new GouPiaoAdapter(gouPiaoBean,GouPiaoActivity.this);
        goupiao_xrec.setAdapter(gouPiaoAdapter);

        /********* 带值跳转到GouMaiActivity  *********/
        //条目点击
        gouPiaoAdapter.setOnItem(new GouPiaoAdapter.OnItem() {
            @Override
            public void onClick(int position, String movieId,String gou_item_name,String gou_item_dizhi) {
                Intent intent = new Intent(GouPiaoActivity.this,GouMaiActivity.class);
                intent.putExtra("movieIdgou",movieId);
                intent.putExtra("ying",gou_item_name);
                intent.putExtra("di",gou_item_dizhi);
                startActivity(intent);
            }
        });

        /********* 接受XiangQingActivity传来的值 ***********/
        //名字
        gou = getIntent().getStringExtra("gou");
        Log.e("tab","gou"+gou);
        goupiao_name.setText(gou);
        //movieId
        String id = getIntent().getStringExtra("id");

        mapgou = new HashMap<>();
        mapgou.put("movieId",id);
        pInterfacegetGouPiao.getGouPiao(null, mapgou);

        //上拉，下拉
        goupiao_xrec.setPullRefreshEnabled(true);
        goupiao_xrec.setLoadingMoreEnabled(true);

        goupiao_xrec.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                gouPiaoBean.clear();
                page = 1;
                pInterfacegetGouPiao.getGouPiao(null,mapgou);
                goupiao_xrec.refreshComplete();
                gouPiaoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                page++;
                pInterfacegetGouPiao.getGouPiao(null,mapgou);
                goupiao_xrec.loadMoreComplete();gouPiaoAdapter.notifyDataSetChanged();
            }
        });
    }

    //购票
    @Override
    public void getYuGao(Object object) {
        GouPiaoBean gouPiaoBean2 = (GouPiaoBean)object;
        List<GouPiaoBean.ResultBean> result = gouPiaoBean2.getResult();

        gouPiaoBean.addAll(result);
        gouPiaoAdapter.notifyDataSetChanged();
    }

    //内存优化
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfacegetGouPiao.onDsply();
        pInterfacegetGouPiao=null;
    }
}
