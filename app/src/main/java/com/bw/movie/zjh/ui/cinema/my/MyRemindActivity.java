package com.bw.movie.zjh.ui.cinema.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.zjh.adapter.MyRemindAdapter;
import com.bw.movie.zjh.base.BaseActivity;
import com.bw.movie.zjh.beans.my.MyRemindBean;
import com.bw.movie.zjh.beans.my.UnReadNumBean;
import com.bw.movie.zjh.beans.my.UpDateReadStatusBean;
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
 * date   : 2019/5/13 19:40
 * desc   :  系统消息
 * version: 1.0
 */
public class MyRemindActivity extends BaseActivity implements IView {
    private Unbinder bind;
    private IPresenterImpl iPresenter;
    @BindView(R.id.my_like_recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.unReadNumber)
    TextView unReadNumber;
    private int page = 0;
    private MyRemindAdapter mAdapter;
    private int size;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_remind;
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);

    }

    @Override
    protected void initData() {
        //未读消息数量
        initUnReadNumber();

        page = 1;
        //适配器
        mAdapter = new MyRemindAdapter(this);
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
                int itemCount = mAdapter.getItemCount();
                refreshlayout.finishLoadmore(2000);

            }
        });
        joinApi(page);
        //设置 Header 为 Material风格
        //refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        //全屏水滴
        refreshLayout.setRefreshHeader(new WaveSwipeHeader(this));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        //返回
        findViewById(R.id.preson_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
     *   p层
     * */
    private void joinApi(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("count", Config.COUNT_NUMBER_HOME + "");
        iPresenter.getPresenterData(Apis.MY_REMIND_GET, map, MyRemindBean.class);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //消息状态修改
        mAdapter.setReMindListener(new MyRemindAdapter.ReMindListener() {
            @Override
            public void updataStatus(String id) {
                updataReadStatus(id);
            }
        });

    }

    /*
    *  未读消息数量
    * */
    private void initUnReadNumber() {
        Map<String, String> map1 = new HashMap<>();
        iPresenter.getPresenterData(Apis.MY_UNREAD_NUM_GET, map1, UnReadNumBean.class);
    }

    /*
    *  读取状态修改
    * */
    private void updataReadStatus(String id) {
        Map<String, String> map2 = new HashMap<>();
        map2.put("id", id);
        iPresenter.getPresenterData(Apis.MY_READ_STATUS_GET, map2, UpDateReadStatusBean.class);
    }


    /*
     *   回调函数
     * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof MyRemindBean){
            MyRemindBean myRemindBean = (MyRemindBean) data;
            size = myRemindBean.getResult().size();
            if (page == 1) {
                mAdapter.setDatas(myRemindBean.getResult());
            } else {
                mAdapter.addDatas(myRemindBean.getResult());
            }
        }else if (data instanceof UnReadNumBean){
            UnReadNumBean unReadNumBean = (UnReadNumBean) data;
            unReadNumber.setText("系统消息 ("+unReadNumBean.getCount()+"条未读)");
            initUnReadNumber();
        }else if (data instanceof UpDateReadStatusBean){
            UpDateReadStatusBean readStatusBean = (UpDateReadStatusBean) data;
            Toast.makeText(this, readStatusBean.getMessage(), Toast.LENGTH_SHORT).show();
            joinApi(page);
        }

    }

    /*
     *  内存释放
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
    }

}
