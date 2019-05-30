package com.bw.movie.fmk.fragment.TabLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.TabTwoAdapter;
import com.bw.movie.fmk.base.BasefFragment;
import com.bw.movie.fmk.bean.RYingBean;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 19:45
 * @Description:
 */
public class TabLayoutTwo extends BasefFragment implements VInterface.VInterfaceRYing {

    private RecyclerView tab_one_rec;
    public List<RYingBean.ResultBean> ryingbean = new ArrayList<>();
    private TabTwoAdapter tabTwoAdapter;
    private PInterface.PInterfacegetRYing pInterfacegetRYing;

    //布局
    @Override
    protected int initLayout() {
        return R.layout.tab_one_layout;
    }

    //控件
    @Override
    protected void ininView() {
        tab_one_rec = fvd(R.id.tab_one_rec);
        pInterfacegetRYing = new MyPenster(this);
    }

    //数据
    @Override
    protected void initData() {

        //布局管理利器
        LinearLayoutManager linear2 = new LinearLayoutManager(getActivity());
        linear2.setOrientation(LinearLayoutManager.VERTICAL);
        tab_one_rec.setLayoutManager(linear2);

        pInterfacegetRYing.getRYing(null);

        //适配器
        tabTwoAdapter = new TabTwoAdapter(ryingbean,getActivity());
        tab_one_rec.setAdapter(tabTwoAdapter);
    }

    @Override
    public void showRYing(Object object) {
        RYingBean rYingBean2 = (RYingBean) object;
        List<RYingBean.ResultBean> result = rYingBean2.getResult();
        ryingbean.addAll(result);
        tabTwoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pInterfacegetRYing.onDsply();
        pInterfacegetRYing=null;
    }


}