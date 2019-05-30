package com.bw.movie.fmk.fragment.TabLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.fmk.adapter.TabThreeAdapter;
import com.bw.movie.fmk.base.BasefFragment;
import com.bw.movie.fmk.bean.ShangYingBean;
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
public class TabLayoutThree extends BasefFragment implements VInterface.VInterfacegetShangYing {

    private RecyclerView tab_one_rec;
    public List<ShangYingBean.ResultBean> shangYingBean = new ArrayList<>();
    private TabThreeAdapter tabThreeAdapter;
    private PInterface.PInterfacegetShangYing pInterfacegetShangYing;

    //布局
    @Override
    protected int initLayout() {
        return R.layout.tab_one_layout;
    }

    //控件
    @Override
    protected void ininView() {
        tab_one_rec = fvd(R.id.tab_one_rec);
        pInterfacegetShangYing = new MyPenster(this);
    }

    //数据
    @Override
    protected void initData() {

        //布局管理利器
        LinearLayoutManager linear2 = new LinearLayoutManager(getActivity());
        linear2.setOrientation(LinearLayoutManager.VERTICAL);
        tab_one_rec.setLayoutManager(linear2);

        pInterfacegetShangYing.getShangYing(null);

        //适配器
        tabThreeAdapter = new TabThreeAdapter(shangYingBean,getActivity());
        tab_one_rec.setAdapter(tabThreeAdapter);
    }

    @Override
    public void showShangYing(Object object) {
        ShangYingBean shangYingBean2 = (ShangYingBean) object;
        List<ShangYingBean.ResultBean> result = shangYingBean2.getResult();
        shangYingBean.addAll(result);
        tabThreeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pInterfacegetShangYing.onDsply();
        pInterfacegetShangYing=null;
    }
}