package com.bw.movie.zjh.ui.cinema.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.zjh.adapter.MyFoodedFinishAdapter;
import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.my.MyFoodedBean;
import com.bw.movie.zjh.utils.config.Config;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.util.Apis;
import com.bw.movie.zjh.utils.mvp.view.IView;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/23 11:01
 * desc   :
 * version: 1.0
 */
public class MyFoodedFinishFragment extends BaseFragment implements IView {

    private Unbinder bind;
    private IPresenterImpl iPresenter;
    @BindView(R.id.my_like_recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int page = 0;
    private MyFoodedFinishAdapter mAdapter;

    @Override
    protected int setView() {
        return R.layout.fragment_my_fooded_finish;
    }

    @Override
    protected void init(View view) {
        bind = ButterKnife.bind(this, view);
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        page = 1;
        //适配器
        mAdapter = new MyFoodedFinishAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        //刷新方法
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                joinApi(page);
                refreshlayout.finishRefresh(2000);
            }
        });
        //加载更多
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                joinApi(page);
                refreshlayout.finishLoadmore(2000);
            }
        });
        joinApi(page);
        //设置 Header 为 Material风格
        //refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        //全屏水滴
        refreshLayout.setRefreshHeader(new WaveSwipeHeader(getActivity()));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    /*
     *   p层
     * */
    private void joinApi(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("count", Config.COUNT_NUMBER_HOME + "");
        map.put("status", 2 + "");
        iPresenter.getPresenterData(Apis.MY_FOODED_GET, map, MyFoodedBean.class);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /*
     *  回调函数
     * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof MyFoodedBean) {
            MyFoodedBean myFoodedBean = (MyFoodedBean) data;
            if (page == 1) {
                mAdapter.setDatas(myFoodedBean.getResult());
            } else {
                mAdapter.addDatas(myFoodedBean.getResult());
            }
        }

    }

    /*
     *  内存释放
     * */

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
    }
}
