package com.bw.movie.zjh.ui.cinema;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.zjh.adapter.CinemaCallAdapter;
import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.cinema.CinemaCallBean;
import com.bw.movie.zjh.beans.cinema.CinemaCallLikeBean;
import com.bw.movie.zjh.beans.cinema.CinemaMessage;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 16:41
 * desc   :  影院评论
 * version: 1.0
 */
public class DetailsCallFragment extends BaseFragment implements IView {
    private IPresenterImpl iPresenter;
    private SmartRefreshLayout call_refreshLayout;
    private RecyclerView call_recyclerView;
    private int page = 1;
    private CinemaCallAdapter mAdapter;
    private String cinemaId;

    @Override
    protected int setView() {
        return R.layout.fragment_details_call;
    }

    @Override
    protected void init(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        iPresenter = new IPresenterImpl(this);
        call_refreshLayout = view.findViewById(R.id.call_refreshLayout);
        call_recyclerView = view.findViewById(R.id.call_recyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        page = 1;
        //适配器
        mAdapter = new CinemaCallAdapter(getActivity());
        call_recyclerView.setAdapter(mAdapter);
        // 点赞
        isGreat();

        //刷新方法
        call_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                joinApi(page);
                refreshlayout.finishRefresh(2000);
            }
        });
        //加载更多
        call_refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
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
        call_refreshLayout.setRefreshHeader(new WaveSwipeHeader(getActivity()));
        //设置 Footer 为 球脉冲
        call_refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    /*
    *   拼接接口
    *  */
    private void joinApi(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId);
        map.put("page", page + "");
        map.put("count", Config.COUNT_NUMBER_HOME + "");
        iPresenter.getPresenterData(Apis.QUERYCOMMENT_GET, map, CinemaCallBean.class);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        call_recyclerView.setLayoutManager(linearLayoutManager);
    }

    /*
     *   点赞
     * */
    private void isGreat() {
       mAdapter.setCallListener(new CinemaCallAdapter.CallListener() {
           @Override
           public void OnGreat(int id, boolean isGreat) {
               Map<String, String> map = new HashMap<>();
               map.put("commentId", id + "");
               if (isGreat) {
                   iPresenter.postLoginPresenterData(Apis.CALLWATCHFUL_POST, map, CinemaCallLikeBean.class);
               }
               mAdapter.notifyDataSetChanged();
           }
       });
    }

    /*
     *  eventbus接收数据
     * */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void mCinemaEvent(CinemaMessage cinemaMessage) {
        cinemaId = cinemaMessage.getCinemaId();
    }

    /*
     *  回调函数
     * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof CinemaCallBean){
            CinemaCallBean cinemaCallBean = (CinemaCallBean) data;
            if (page == 1){
                mAdapter.setDatas(cinemaCallBean.getResult());
            }else {
                mAdapter.addDatas(cinemaCallBean.getResult());
            }

        }else if (data instanceof CinemaCallLikeBean){
            CinemaCallLikeBean cinemaCallLikeBean = (CinemaCallLikeBean) data;
            Toast.makeText(getActivity(), cinemaCallLikeBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *  内存处理
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
