package com.bw.movie.fmk.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.bw.movie.R;
import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.cinema.SearchBean;
import com.bw.movie.zjh.ui.cinema.CinemaNearbyFragment;
import com.bw.movie.zjh.ui.cinema.CinemaRecommendFragment;
import com.bw.movie.zjh.ui.cinema.CinemaSearchFragment;
import com.bw.movie.zjh.utils.location.Const;
import com.bw.movie.zjh.utils.location.PermissionsUtils;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/12 18:42
 * @Description:
 */
public class FragmentTwo extends BaseFragment implements IView {

    private Unbinder bind;
    @BindView(R.id.cinema_group)
    RadioGroup cinema_group;
    @BindView(R.id.cinema_pager)
    ViewPager cinema_pager;
    @BindView(R.id.home_replace_flyt)
    FrameLayout home_replace_flyt;

    @BindView(R.id.location_address)
    TextView location_address;
    @BindView(R.id.edit_value)
    EditText edit_value;
    private IPresenterImpl iPresenter;
    private List<Fragment> fragments;
    private CinemaRecommendFragment recommendFragment;
    private CinemaNearbyFragment nearbyFragment;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected int setView() {
        return R.layout.fragment_two;
    }

    @Override
    protected void init(View view) {
        bind = ButterKnife.bind(this, view);
        iPresenter = new IPresenterImpl(this);

        fragments = new ArrayList<>();
        recommendFragment = new CinemaRecommendFragment();
        nearbyFragment = new CinemaNearbyFragment();
        fragments.add(recommendFragment);
        fragments.add(nearbyFragment);

        /**
         * 定位权限
         * Manifest.permission.ACCESS_FINE_LOCATION
         * Manifest.permission.ACCESS_COARSE_LOCATION
         * 相机相册权限
         * Manifest.permission.CAMERA
         */
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,};
        //创建监听权限的接口对象
        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                Toast.makeText(getContext(), "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void forbitPermissons() {
                Toast.makeText(getContext(), "权限不通过!", Toast.LENGTH_SHORT).show();
            }
        };
        PermissionsUtils.getInstance().chekPermissions(getActivity(), permissions, permissionsResult);

        //初始化定位
        initLocation();
        //开始定位
        startLocation();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //适配fragment
        cinema_pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        //viewpager滑动监听
        cinema_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中时
            @Override
            public void onPageSelected(int position) {
                cinema_group.check(cinema_group.getChildAt(position).getId());
            }

            //滑动状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //选择监听事件
        cinema_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cinema_rb1:
                        cinema_pager.setCurrentItem(0);
                        break;
                    case R.id.cinema_rb2:
                        cinema_pager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });


    }

    /*
     *   点击事件 1.定位  2.搜索
     * */
    @OnClick({R.id.home_location, R.id.tv_search_btn})
    public void mHomeOnClick(View v) {
        switch (v.getId()) {
            case R.id.home_location: //开始定位
                Toast.makeText(getActivity(), "正在定位…", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_search_btn:
                if (edit_value.getText().toString().length() != 0) {
                    initSearchData();
                } else {
                    Toast.makeText(getActivity(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

    }

    /*
     *  点击搜索
     * */
    private void initSearchData() {
        //需要搜索的关键字
        SearchBean searchBean = new SearchBean();
        searchBean.setSearch(edit_value.getText().toString());
        EventBus.getDefault().postSticky(searchBean);
        //进入到搜索页
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_replace_flyt, new CinemaSearchFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 默认的定位参数
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    //sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                // sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                Log.e("LLLLL", result+"");
                //TODO  定位地址
                location_address.setText(location.getAddress());
                Const.LONGITUDE = location.getLongitude();  //经度
                Const.LATITUDE = location.getLatitude();    //纬度
            } else {
                location_address.setText("定位失败，loc is null");
            }
        }
    };

    /*
     *   回到方法
     * */
    @Override
    public void viewDataSuccess(Object data) {

    }

    /**
     * 获取GPS状态的字符串
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }


    /**
     * 开始定位
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /*
     *  内存处理
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
        destroyLocation();
    }

}
