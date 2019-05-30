package com.bw.movie.zjh.ui.cinema;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.zjh.adapter.CinemaCoverFlowAdapter;
import com.bw.movie.zjh.adapter.CinemaDetailsAdapter;
import com.bw.movie.zjh.base.BaseActivity;
import com.bw.movie.zjh.beans.cinema.CinemaDetailsWorkBean;
import com.bw.movie.zjh.beans.cinema.CinemaMessage;
import com.bw.movie.zjh.beans.cinema.MovieCoverFlowBean;
import com.bw.movie.zjh.ui.cinema.gpsnav.GPSAddressActivity;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.util.Apis;
import com.bw.movie.zjh.utils.mvp.view.IView;
import com.bw.movie.zjh.utils.statusbar.StatusBarWindowTop;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDetailsActivity extends BaseActivity implements IView {

    private Unbinder bind;
    private IPresenterImpl iPresenter;
    @BindView(R.id.cinema_recycleView)
    RecyclerView cinema_recycleView;
    @BindView(R.id.recyclerCoverFlow)
    RecyclerCoverFlow recyclerCoverFlow;
    private CinemaDetailsAdapter mAdapter;
    private CinemaCoverFlowAdapter mFlowAdapter;
    @BindView(R.id.like_image)
    SimpleDraweeView like_image;
    @BindView(R.id.like_name)
    TextView like_name;
    @BindView(R.id.like_address)
    TextView like_address;
    @BindView(R.id.home_progress)
    RelativeLayout home_progress;

    private String moviename;
    private String cinemaId;
    private String logo;
    private String name;
    private String address;
    private List<MovieCoverFlowBean.ResultBean> result;

    @Override
    public int bindLayout() {
        return R.layout.activity_cinema_details;
    }

    @Override
    protected void initView() {
        //透明状态栏
        StatusBarWindowTop.setStatusBarFullTransparent(this);

        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        like_image.setImageURI(logo);
        like_name.setText(name);
        like_address.setText(address);
        //Toast.makeText(this, "影院id:" + cinemaId, Toast.LENGTH_SHORT).show();
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    protected void initData() {
        //电影旋转木马
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId);
        iPresenter.getPresenterData(Apis.CINEMA_ARRANGE_WORK_GET, map, MovieCoverFlowBean.class);
        //电影
        mFlowAdapter = new CinemaCoverFlowAdapter(this);
        recyclerCoverFlow.setAdapter(mFlowAdapter);
        //回调电影的MovieId
        mFlowAdapter.setMovieCoverFlowListener(new CinemaCoverFlowAdapter.MovieCoverFlowListener() {
            @Override
            public void OnMovieClick(String movieid, String movieName) {
                moviename = movieName;
                joinData(movieid);
            }
        });

        //滑动导航
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int i = wm.getDefaultDisplay().getWidth() / result.size();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(home_progress,"translationX", i,i);
                objectAnimator.setDuration(200);
                objectAnimator.start();
            }
        });
    }

    /*
     *  查询电影排期
     * */
    private void joinData(String movieid) {
        //排期
        Map<String, String> map1 = new HashMap<>();
        map1.put("movieId", movieid);
        map1.put("cinemasId", cinemaId);
        iPresenter.getPresenterData(Apis.MOVIE_SCHEDULE_GET, map1, CinemaDetailsWorkBean.class);
        //创建 点击去选坐 适配器
        mAdapter = new CinemaDetailsAdapter(this);
        cinema_recycleView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        cinema_recycleView.setLayoutManager(linearLayoutManager);
        //回调排期id
        mAdapter.setCinemaDetailsListener(new CinemaDetailsAdapter.CinemaDetailsListener() {
            @Override
            public void onNextClick(String scheduleId, String begin, String end, String screenHall, double price) {
                Intent intent = new Intent(CinemaDetailsActivity.this, ChooseSeatBuyActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);

                intent.putExtra("moviename", moviename);
                intent.putExtra("id", scheduleId);
                intent.putExtra("begin", begin);
                intent.putExtra("end", end);
                intent.putExtra("hall", screenHall);
                intent.putExtra("price", price);
                startActivity(intent);
            }

        });

    }

    /*
     *   自定义事件
     *   1.查看影院详情
     *   2.导航
     * */
    @OnClick({R.id.re, R.id.go_address})
    public void mYOnClick(View v) {
        switch (v.getId()) {
            case R.id.re:   //点击查看影院详情
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.details_framelayout, new CinemaDetailsFragment(), null)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.go_address:
                startActivity(new Intent(CinemaDetailsActivity.this, GPSAddressActivity.class));
                break;
            default:
                break;
        }
    }

    /*
     *  eventbus接收数据
     * */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void myCinemaEvent(CinemaMessage message) {
        cinemaId = message.getCinemaId();
        logo = message.getLogo();
        name = message.getName();
        address = message.getAddress();
    }

    /*
     *   回调函数
     * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof MovieCoverFlowBean) {
            MovieCoverFlowBean coverFlowBean = (MovieCoverFlowBean) data;
            result = coverFlowBean.getResult();
            //Toast.makeText(this, "电影"+coverFlowBean.getMessage(), Toast.LENGTH_SHORT).show();
            //旋转木马展示
            mFlowAdapter.setDatas(coverFlowBean.getResult());

        } else if (data instanceof CinemaDetailsWorkBean) {
            CinemaDetailsWorkBean workBean = (CinemaDetailsWorkBean) data;
            Toast.makeText(this, "排期" + workBean.getMessage(), Toast.LENGTH_SHORT).show();
            mAdapter.setDatas(workBean.getResult());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
        EventBus.getDefault().unregister(this);
    }

}
