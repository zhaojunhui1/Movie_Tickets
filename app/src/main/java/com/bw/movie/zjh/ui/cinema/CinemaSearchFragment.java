package com.bw.movie.zjh.ui.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.zjh.adapter.SearchRecommendAdapter;
import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.cinema.CinemaMessage;
import com.bw.movie.zjh.beans.cinema.LikeCinemaBean;
import com.bw.movie.zjh.beans.cinema.SearchBean;
import com.bw.movie.zjh.beans.cinema.SearchRecommendBean;
import com.bw.movie.zjh.beans.cinema.UnLikeCinemaBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/17 11:53
 * desc   :
 * version: 1.0
 */
public class CinemaSearchFragment extends BaseFragment implements IView {
    private IPresenterImpl iPresenter;
    private Unbinder bind;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int page = 0;
    private SearchRecommendAdapter mAdapter;
    private String searchName;

    @Override
    protected int setView() {
        return R.layout.fragment_cinema_search;
    }

    @Override
    protected void init(View view) {
        iPresenter = new IPresenterImpl(this);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        bind = ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        page = 1;
        //适配器
        mAdapter = new SearchRecommendAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        isLikeCinema();

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
                page ++;
                joinApi(page);
                refreshlayout.finishLoadmore(2000);
            }
        });
        joinApi(page);
        //全屏水滴
        refreshLayout.setRefreshHeader(new WaveSwipeHeader(getActivity()));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    /*
     *   拼接
     * */
    private void joinApi(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("count", Config.COUNT_NUMBER_HOME + "");
        map.put("cinemaName", searchName);
        iPresenter.getPresenterData(Apis.QUERY_CINEMA_GET, map, SearchRecommendBean.class);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }

    /*
     *   点点关注
     * */
    private void isLikeCinema() {
        mAdapter.setSearchRecommendListener(new SearchRecommendAdapter.SearchRecommendListener() {
            // 关注
            @Override
            public void onLike(int id, boolean isLike) {
                Map<String, String> map = new HashMap<>();
                map.put("cinemaId", id + "");
                if (isLike) {
                    iPresenter.getPresenterData(Apis.LOVECINEMA_GET, map, LikeCinemaBean.class);
                } else {
                    iPresenter.getPresenterData(Apis.NOLOVECINEMA_GET, map, UnLikeCinemaBean.class);
                }
                mAdapter.notifyDataSetChanged();
            }

            //点击条目进入详情
            @Override
            public void OnClick(String id, String logo, String name, String address) {
                CinemaMessage cinemaMessage = new CinemaMessage();
                cinemaMessage.setCinemaId(id);
                cinemaMessage.setLogo(logo);
                cinemaMessage.setName(name);
                cinemaMessage.setAddress(address);
                EventBus.getDefault().postSticky(cinemaMessage);
                startActivity(new Intent(getActivity(), CinemaDetailsActivity.class));
            }
        });
    }

    /*
       搜索关键字
    * */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void search(SearchBean searchBean){
        searchName = searchBean.getSearch();
        Toast.makeText(getActivity(), searchName+"", Toast.LENGTH_SHORT).show();
    }


    /*
    *  回调接口
    * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof SearchRecommendBean) {
            SearchRecommendBean searchRecommendBean = (SearchRecommendBean) data;
            if (page == 1) {
                mAdapter.setDatas(searchRecommendBean.getResult());
            } else {
                mAdapter.addDatas(searchRecommendBean.getResult());
            }

        }else if (data instanceof LikeCinemaBean) {
            LikeCinemaBean likeCinemaBean = (LikeCinemaBean) data;
            Toast.makeText(getActivity(), likeCinemaBean.getMessage(), Toast.LENGTH_SHORT).show();
            joinApi(page);
        } else if (data instanceof UnLikeCinemaBean) {
            UnLikeCinemaBean unLikeCinemaBean = (UnLikeCinemaBean) data;
            Toast.makeText(getActivity(), unLikeCinemaBean.getMessage(), Toast.LENGTH_SHORT).show();
            joinApi(page);
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
        bind.unbind();
    }
}
