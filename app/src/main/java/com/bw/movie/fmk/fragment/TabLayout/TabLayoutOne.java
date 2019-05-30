package com.bw.movie.fmk.fragment.TabLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.TabOneAdapter;
import com.bw.movie.fmk.base.BasefFragment;
import com.bw.movie.fmk.bean.GuanZhuBean;
import com.bw.movie.fmk.bean.QuXiaoGuanZhuBean;
import com.bw.movie.fmk.bean.RMenBean;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 19:45
 * @Description:
 */
public class TabLayoutOne extends BasefFragment implements VInterface.VInterfaceRMen,VInterface.VInterfacegetDianYingGuanZhu,VInterface.VInterfacegetQuXiaoDianYingGuanZhu {

    private RecyclerView tab_one_rec;
    public List<RMenBean.ResultBean> rmenBean = new ArrayList<>();
    public List<String> guanZhuBean = new ArrayList<>();
    public List<String> quxiaoguanZhuBean = new ArrayList<>();
    private TabOneAdapter tabOneAdapter;
    private PInterface.PInterfaceRMen pInterfaceRMen;
    private PInterface.PInterfacegetDianYingGuanZhu pInterfacegetDianYingGuanZhu;
    private PInterface.PInterfacegetQuXiaoDianYingGuanZhu pInterfacegetQuXiaoDianYingGuanZhu;

    //布局
    @Override
    protected int initLayout() {
        return R.layout.tab_one_layout;
    }

    //控件
    @Override
    protected void ininView() {
        tab_one_rec = fvd(R.id.tab_one_rec);
        pInterfaceRMen = new MyPenster(this);
        pInterfacegetDianYingGuanZhu = new MyPenster(this);
        pInterfacegetQuXiaoDianYingGuanZhu = new MyPenster(this);
    }

    //数据
    @Override
    protected void initData() {

        //布局管理器
        LinearLayoutManager linear2 = new LinearLayoutManager(getActivity());
        linear2.setOrientation(LinearLayoutManager.VERTICAL);
        tab_one_rec.setLayoutManager(linear2);

        pInterfaceRMen.getRMen(null);

        //适配器
        tabOneAdapter = new TabOneAdapter(rmenBean,getActivity());
        tab_one_rec.setAdapter(tabOneAdapter);

        //关注
        initGuanZhu();
    }

    //关注
    private void initGuanZhu() {
        tabOneAdapter.setOnItem(new TabOneAdapter.OnItem() {
            @Override
            public void onGuanLike(int id, boolean isLike) {
                HashMap<String,String> map = new HashMap<>();
                map.put("movieId",id+"");
                if(isLike){
                    pInterfacegetDianYingGuanZhu.getDianYingGuanZhu(null,map);
                }else{
                    pInterfacegetQuXiaoDianYingGuanZhu.getQuXiaoDianYingGuanZhu(null,map);
                }
                tabOneAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showRMen(Object object) {
        RMenBean rMenBean2 = (RMenBean) object;
        List<RMenBean.ResultBean> result = rMenBean2.getResult();
        rmenBean.addAll(result);
        tabOneAdapter.notifyDataSetChanged();
    }

    //关注
    @Override
    public void getDianYingGuanZhu(Object object) {
            GuanZhuBean guanZhuBean2 = (GuanZhuBean)object;
            String message = guanZhuBean2.getMessage();
            guanZhuBean.add(message);
            Toast.makeText(getActivity(), guanZhuBean2.getMessage(), Toast.LENGTH_SHORT).show();

    }

    //取消关注
    @Override
    public void getQuXiaoDianYingGuanZhu(Object object) {
        QuXiaoGuanZhuBean quXiaoGuanZhuBean2 = (QuXiaoGuanZhuBean)object;
        String message = quXiaoGuanZhuBean2.getMessage();

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            quxiaoguanZhuBean.add(message);
            //Toast.makeText(getActivity(), quXiaoGuanZhuBean2.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pInterfaceRMen.onDsply();
        pInterfaceRMen=null;
        //关注
        pInterfacegetDianYingGuanZhu.onDsply();
        pInterfacegetDianYingGuanZhu=null;
        //取消关注
        pInterfacegetQuXiaoDianYingGuanZhu.onDsply();
        pInterfacegetQuXiaoDianYingGuanZhu=null;
    }
}
