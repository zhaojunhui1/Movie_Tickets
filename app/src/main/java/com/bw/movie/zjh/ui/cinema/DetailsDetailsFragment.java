package com.bw.movie.zjh.ui.cinema;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.cinema.CinemaMessage;
import com.bw.movie.zjh.beans.cinema.DetailsDetailsBean;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.util.Apis;
import com.bw.movie.zjh.utils.mvp.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 16:41
 * desc   :   影院详情
 * version: 1.0
 */
public class DetailsDetailsFragment extends BaseFragment implements IView {
    private IPresenterImpl iPresenter;
    private String cinemaId;
    private TextView details_location, details_phone, details_subway, details_bus, details_self;

    @Override
    protected int setView() {
        return R.layout.fragment_details_details;
    }

    /*
    *  初始化
    * */
    @Override
    protected void init(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Toast.makeText(getActivity(), cinemaId, Toast.LENGTH_SHORT).show();

        iPresenter = new IPresenterImpl(this);
        details_location = view.findViewById(R.id.details_location);
        details_phone = view.findViewById(R.id.details_phone);
        details_subway = view.findViewById(R.id.details_subway);
        details_bus = view.findViewById(R.id.details_bus);
        details_self = view.findViewById(R.id.details_self);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Map<String, String> map = new ArrayMap<>();
        map.put("cinemaId", cinemaId);
        iPresenter.getPresenterData(Apis.INFORMATION_GET, map, DetailsDetailsBean.class);
    }

    /*
     *  eventbus接收数据
     * */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void mCinemaEvent(CinemaMessage cinemaMessage) {
        cinemaId = cinemaMessage.getCinemaId();
    }

    /*
    *   回调函数
    * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof DetailsDetailsBean){
            DetailsDetailsBean detailsBean = (DetailsDetailsBean) data;
            details_location.setText(detailsBean.getResult().getAddress());
            details_phone.setText(detailsBean.getResult().getPhone());
            details_subway.setText(detailsBean.getResult().getVehicleRoute());
          /*  details_bus.setText(detailsBean.getResult());
            details_self.setText(detailsBean.getResult());*/

        }
    }

    /*
    *   内存管理
    * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
