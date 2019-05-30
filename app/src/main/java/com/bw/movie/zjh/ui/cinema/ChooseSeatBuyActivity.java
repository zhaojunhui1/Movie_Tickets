package com.bw.movie.zjh.ui.cinema;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.ArrayMap;
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
import com.bw.movie.app.App;
import com.bw.movie.zjh.beans.cinema.BuyMovieTicketBean;
import com.bw.movie.zjh.beans.cinema.PayTicketBean;
import com.bw.movie.zjh.ui.cinema.seak.SeatTable;
import com.bw.movie.zjh.utils.md5.MDUtils;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.util.Apis;
import com.bw.movie.zjh.utils.mvp.view.IView;
import com.bw.movie.zjh.utils.statusbar.StatusBarWindowTop;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.bw.movie.zjh.utils.config.Config.APP_ID;

public class ChooseSeatBuyActivity extends Activity implements IView {

    private IPresenterImpl iPresenter;
    private Unbinder bind;
    @BindView(R.id.cinema_name)
    TextView cinema_name;
    @BindView(R.id.cinema_address)
    TextView cinema_address;
    @BindView(R.id.movie_name)
    TextView movie_name;
    @BindView(R.id.datatime)
    TextView datatime;
    @BindView(R.id.hall)
    TextView hall;

    @BindView(R.id.seat_Table)  //座位View
            SeatTable seat_Table;
    @BindView(R.id.priceAll)
    TextView priceAll;      //合计
    private PopupWindow mPop;
    private RadioButton radio_weixin;   //微信支付
    private RadioButton radio_zifubao;  //支付宝支付
    private RelativeLayout pay_submit;  //提交支付
    private TextView type_priceAll;     //支付消费总价
    private String hallName;
    private String scheduleId;
    private int amount = 0;
    private double price;
    private double priceall = 0;
    private int payType;
    private String orderId;

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    String result = (String) msg.obj;
                    Toast.makeText(ChooseSeatBuyActivity.this, result, Toast.LENGTH_LONG).show();
                }
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_buy);

        initView();
    }

    /*
     *  初始化View
     * */
    private void initView() {
        //透明状态栏
        StatusBarWindowTop.setStatusBarFullTransparent(this);
        bind = ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        //排期id
        scheduleId = getIntent().getStringExtra("id");
        //显示排期情况
        cinema_name.setText(getIntent().getStringExtra("name"));
        cinema_address.setText(getIntent().getStringExtra("address"));
        movie_name.setText(getIntent().getStringExtra("moviename"));
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        datatime.setText(format + " " + getIntent().getStringExtra("begin") + "-" + getIntent().getStringExtra("end"));
        price = getIntent().getDoubleExtra("price", 0.0);
        hallName = getIntent().getStringExtra("hall");
        this.hall.setText(hallName);

        initSeat();
        payChooseType();  //支付类型页
    }

    /*
     *   绑定  点击事件
     *    1. √ 确认
     *    2. × 取消
     *    3.
     *    4.
     * */
    @OnClick({R.id.ok, R.id.no})
    public void seatClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                //购票下单 、展示付款的选择框
                initBuyTicket();
                break;
            case R.id.no:
                onCreate(null);  //取消选择并刷新

                break;
            default:
                break;
        }
    }


    /*
     *   购票下单
     * */
    private void initBuyTicket() {
        SharedPreferences sharedPreferences = App.getApplication().getSharedPreferences("token", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        String s = MDUtils.MD5(userId + scheduleId + amount + "movie");

        Map<String, String> map = new ArrayMap<>();
        map.put("scheduleId", scheduleId);
        map.put("amount", amount + "");
        map.put("sign", s);
        iPresenter.postLoginPresenterData(Apis.BUY_MOVIE_TICKET, map, BuyMovieTicketBean.class);
        Log.e("tab","mapzjh=="+map);
    }

    /*
     * 支付选择的popwindow
     * 微信，支付宝
     * */
    private void payChooseType() {
        View view = View.inflate(this, R.layout.pay_choose_type_view, null);
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
    private void initChoose() {
        //显示支付方式总价
        type_priceAll.setText("微信支付" + priceall + "元");
        radio_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_priceAll.setText("微信支付" + priceall + "元");
            }
        });
        radio_zifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_priceAll.setText("支付宝支付" + priceall + "元");
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
                goBuyTicket(payType);
            }
        });

    }

    /*
     *  购票支付
     *  ￥￥￥￥￥￥
     * */
    private void goBuyTicket(int payType) {
        Map<String, String> map1 = new HashMap<>();
        map1.put("payType", payType + "");
        map1.put("orderId", orderId);
        Log.e("pay", "类型"+payType +"订单号"+orderId);
        iPresenter.postLoginPresenterData(Apis.MOVIE_TICKET_PAY, map1, PayTicketBean.class);
    }


    /*
     *  电影院选座
     * */
    private void initSeat() {
        seat_Table.setScreenName(hallName + "荧幕");//设置屏幕名称
        seat_Table.setMaxSelected(3);//设置最多选中
        // 监听座位
        seat_Table.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                // 隐藏 排  或  列
                /*if(row == 4) {
                    return false;
                }*/
                return true;
            }

            // 已售的座位 (几排几列)
            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            //  选中的 排  列
            @Override
            public void checked(int row, int column) {
                //显示下单页
                amount++;
                priceall = price * amount;
                //调用显示
                SpannableString spannableString = changTVsize(priceall + "");
                priceAll.setText(spannableString);
            }

            //  未选中的 排  列
            @Override
            public void unCheck(int row, int column) {
                amount--;
                priceall = price * amount;
                //调用显示
                SpannableString spannableString = changTVsize(priceall + "");
                priceAll.setText(spannableString);
            }

            // 选中座位的文字颜色 (已默认白色)
            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        /*   总排数，   总列数   */
        seat_Table.setData(7, 8);
    }

    //价格样式
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /*
     *  回调函数
     * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof BuyMovieTicketBean) {
            BuyMovieTicketBean buyMovieTicketBean = (BuyMovieTicketBean) data;
            Toast.makeText(this, buyMovieTicketBean.getMessage(), Toast.LENGTH_SHORT).show();
            if (buyMovieTicketBean.getStatus().equals("0000")) {
                Toast.makeText(this, "选择支付方式", Toast.LENGTH_SHORT).show();
                orderId = buyMovieTicketBean.getOrderId();   //订单号
                //弹出popwindow
                mPop.showAtLocation(this.findViewById(R.id.seat_linearlayout), Gravity.BOTTOM, 0, 0);
                initChoose();   //选择微信/支付宝
            }

        } else if (data instanceof PayTicketBean) {
            final PayTicketBean payTicketBean = (PayTicketBean) data;
            Log.e("pay", "支付宝"+payTicketBean.getResult());
            if (payTicketBean.getStatus().equals("0000")) {
                //微信支付
                try {
                    IWXAPI api = WXAPIFactory.createWXAPI(ChooseSeatBuyActivity.this, APP_ID);
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
                        PayTask alipay = new PayTask(ChooseSeatBuyActivity.this);
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
     *  内存处理
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
    }

}
