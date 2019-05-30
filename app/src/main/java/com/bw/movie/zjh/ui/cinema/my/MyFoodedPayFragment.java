package com.bw.movie.zjh.ui.cinema.my;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.zjh.adapter.MyFoodedPayAdapter;
import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.cinema.PayTicketBean;
import com.bw.movie.zjh.beans.my.MyFoodedBean;
import com.bw.movie.zjh.ui.cinema.ChooseSeatBuyActivity;
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
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.bw.movie.zjh.utils.config.Config.APP_ID;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/23 11:01
 * desc   :
 * version: 1.0
 */
public class MyFoodedPayFragment extends BaseFragment implements IView {

    private Unbinder bind;
    private IPresenterImpl iPresenter;
    @BindView(R.id.my_like_recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private PopupWindow mPop;
    private RadioButton radio_weixin;   //微信支付
    private RadioButton radio_zifubao;  //支付宝支付
    private RelativeLayout pay_submit;  //提交支付
    private TextView type_priceAll;     //支付消费总价
    private int page = 0;
    private int payType;
    private MyFoodedPayAdapter mAdapter;

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    String result = (String) msg.obj;
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                }
                default:
                    break;
            }
        }

    };

    @Override
    protected int setView() {
        return R.layout.fragment_my_fooded_pay;
    }

    @Override
    protected void init(View view) {
        bind = ButterKnife.bind(this, view);
        iPresenter = new IPresenterImpl(this);

        payChooseType();  //支付类型页
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        page = 1;
        //适配器
        mAdapter = new MyFoodedPayAdapter(getActivity());
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
        map.put("status", 1 + "");
        iPresenter.getPresenterData(Apis.MY_FOODED_GET, map, MyFoodedBean.class);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //点击付款
        mAdapter.setFoodedPayListener(new MyFoodedPayAdapter.FoodedPayListener() {
            @Override
            public void OnClickPay(String orderId, double priceAll) {
                //弹出popwindow
                mPop.showAtLocation(refreshLayout, Gravity.BOTTOM, 0, 0);
                initChoose(orderId, priceAll);   //选择微信/支付宝
            }
        });
    }

    /*
     * 支付选择的popwindow
     * 微信，支付宝
     * */
    private void payChooseType() {
        View view = View.inflate(getActivity(), R.layout.pay_choose_type_view, null);
        radio_weixin = view.findViewById(R.id.radio_weixin);
        radio_zifubao = view.findViewById(R.id.radio_zifubao);
        pay_submit = view.findViewById(R.id.pay_submit);
        type_priceAll = view.findViewById(R.id.type_priceAll);
        mPop = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        //设置焦点
        mPop.setFocusable(true);
        //设置是否可以触摸
        mPop.setTouchable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        //收回 取消
        view.findViewById(R.id.dismiss_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
            }
        });
    }

    /*
     *  选择支付类型
     *  1. 微信
     *  2. 支付宝
     * */
    private void initChoose(final String orderId, final double priceAll) {
        //显示支付方式总价
        type_priceAll.setText("微信支付" + priceAll + "元");
        radio_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_priceAll.setText("微信支付" + priceAll + "元");
            }
        });
        radio_zifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_priceAll.setText("支付宝支付" + priceAll + "元");
            }
        });

        //点击提交  支付买票
        pay_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_weixin.isChecked()) {    // 微信
                    payType = 1;
                }
                if (radio_zifubao.isChecked()) {    //支付宝
                    payType = 2;
                }
                goBuyTicket(payType, orderId);
            }
        });

    }

    /*
     *  购票支付
     *  ￥￥￥￥￥￥
     * */
    private void goBuyTicket(int payType, String orderId) {
        Map<String, String> map1 = new HashMap<>();
        map1.put("payType", payType + "");
        map1.put("orderId", orderId);
        Log.e("pay", "类型"+payType +"订单号"+orderId);
        iPresenter.postLoginPresenterData(Apis.MOVIE_TICKET_PAY, map1, PayTicketBean.class);
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
        }else if (data instanceof PayTicketBean) {
            final PayTicketBean payTicketBean = (PayTicketBean) data;
            Log.e("pay", "支付宝"+payTicketBean.getResult());
            if (payTicketBean.getStatus().equals("0000")) {
                //微信支付
                try {
                    IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), APP_ID);
                    //拿预支付结果信息给微信
                    PayReq req = new PayReq();
                    req.appId = payTicketBean.getAppId();  //你的微信appid
                    req.partnerId = payTicketBean.getPartnerId();//商户号
                    req.prepayId = payTicketBean.getPrepayId();//预支付交易会话ID
                    req.nonceStr = payTicketBean.getNonceStr();//随机字符串
                    req.timeStamp = payTicketBean.getTimeStamp();//时间戳
                    req.packageValue = payTicketBean.getPackageValue();//扩展字段,这里固定填写Sign=WXPay
                    req.sign = payTicketBean.getSign();  //签名
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    api.registerApp(APP_ID);
                    api.sendReq(req);   //
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("xxx", e.getMessage());
                }

                //支付宝支付
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(payTicketBean.getResult(), true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
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
