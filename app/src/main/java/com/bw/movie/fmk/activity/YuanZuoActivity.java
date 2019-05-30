package com.bw.movie.fmk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.app.App;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.XiaDanBean;
import com.bw.movie.fmk.bean.ZhiFuBean;
import com.bw.movie.fmk.mvp.p.MyPensterTwo;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.bw.movie.fmk.wxapi.WXPayEntryActivity;

import com.bw.movie.zjh.utils.md5.MDUtils;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YuanZuoActivity extends BasefActivity implements VInterface.VInterfacegetXiaDan ,VInterface.VInterfacegetZhiFu{

    private TextView yuanKaiTime;
    private TextView yuanJaiTime;
    private TextView yuanTing;
    private SeatTable seatView;
    private String gou_name;
    private TextView yuanJiage ,zhi_qian;//选座时的价格,和支付时的价格
    //private double price;
    private double priceall = 0;
    private ImageView yuanOk;
    private TextView zhi_queren;
    private ImageView zhi_xia;
    private int payType;
    private String orderId;
    private PInterface.PInterfacegetXiaDan pInterfacegetXiaDan;
    //下单
    private List<String> xiaDanBean = new ArrayList<>();
    //支付
    private List<String> zhiFuBean = new ArrayList<>();
    private double gou_price;
    private RadioButton xuan_zifubao;
    private RadioButton xuan_weixin;
    private String scheduleId;
    private HashMap<String, String> map;
    private int amount = 0;
    private PInterface.PInterfacegetZhiFu pInterfacegetZhiFu;

    //payType=1，微信支付
    private static final int WEIXIN_ZHIFU=1;
    //payType=2，支付宝支付
    private static final int ZHIFUBAO_ZHIFU=2;

    private static final String APP_ID = "wxb3852e6a6b7d9516";

    private IWXAPI api;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WEIXIN_ZHIFU:
                    //PayResp
                    break;
            }
        }
    };
    private PayReq req;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_yuan_zuo;
    }

    @Override
    protected void initView() {

        yuanKaiTime = fvd(R.id.yuan_kai_time);
        yuanJaiTime = fvd(R.id.yuan_jie_time);
        yuanTing = fvd(R.id.yuan_ting);
        seatView = fvd(R.id.seatView);
        yuanJiage = fvd(R.id.yuan_jiage);
        yuanOk = fvd(R.id.yuan_ok);
        pInterfacegetXiaDan = new MyPensterTwo(this);
        pInterfacegetZhiFu = new MyPensterTwo(this);

        //View绘制选坐
        xuanzuo();
        //购票下单
        XiaDan();
        //支付
        zhifu();
    }

    @Override
    protected void initData() {
         /********* 接收PaiQiAdapter的值 *********/

        gou_name = getIntent().getStringExtra("gou_name");
        yuanTing.setText(gou_name);

        String gou_beginTime = getIntent().getStringExtra("gou_beginTime");
        yuanKaiTime.setText(gou_beginTime);

        String gou_endTime = getIntent().getStringExtra("gou_endTime");
        yuanJaiTime.setText(gou_endTime);

        gou_price = getIntent().getDoubleExtra("gou_price",0.0);
        //yuanJiage.setText(gou_price+"");
        Log.e("tab","gou_price=="+ gou_price);
    }

    //View绘制选坐
    private void xuanzuo() {
        // seatView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatView.setMaxSelected(3);//设置最多选中

        seatView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            //价格
            @Override
            public void checked(int row, int column) {
                amount++;
                Log.e("tab","amount2=="+amount);
                priceall = gou_price * amount;
                //调用显示
                SpannableString spannableString = changTVsize(priceall + "");
                yuanJiage.setText(spannableString);
            }

            @Override
            public void unCheck(int row, int column) {
                amount--;
                Log.e("tab","amount3=="+amount);
                priceall = gou_price * amount;
                //调用显示
                SpannableString spannableString = changTVsize(priceall + "");
                yuanJiage.setText(spannableString);
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }
        });
        seatView.setData(10,15);
    }

    //购票下单
    private void XiaDan() {

        //排期Id
        scheduleId = getIntent().getStringExtra("scheduleId");

        SharedPreferences fmkcan = App.getApplication().getSharedPreferences("fmkcan", Context.MODE_PRIVATE);

        String userId = fmkcan.getString("userId", "");
        String s = MDUtils.MD5(userId+scheduleId+1+"movie");

        map = new HashMap<>();
        map.put("scheduleId",scheduleId);
        map.put("amount",1+"");
        map.put("sign",s);
       // pInterfacegetXiaDan.getXiaDan(null,map);
    }

    //下单
    @Override
    public void getXiaDan(Object object) {
        XiaDanBean xiaDanBean2 = (XiaDanBean)object;
        String message = xiaDanBean2.getMessage();
        orderId = xiaDanBean2.getOrderId();
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        xiaDanBean.add(message);
    }

    //点击跳转支付
    private void zhifu() {

        yuanOk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(YuanZuoActivity.this);
                dialog.setContentView(R.layout.activity_zhi_fu_tan);

                zhi_xia = dialog.findViewById(R.id.zhi_xia);
                //  zhi_queren = dialog.findViewById(R.id.zhi_queren);
                zhi_qian = dialog.findViewById(R.id.zhi_qian);
                xuan_weixin = dialog.findViewById(R.id.xuan_weixin);
                xuan_zifubao = dialog.findViewById(R.id.xuan_zifubao);
                //总计价格赋值给支付的价格
                //zhi_qian.setText(zhi_qian);

                pInterfacegetXiaDan.getXiaDan(null,map);

                zhi_qian.setText("微信支付"+priceall+"元");

                xuan_weixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payType=1;
                        zhi_qian.setText("微信支付"+priceall+"元");
                    }
                });

                xuan_zifubao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payType=2;
                        zhi_qian.setText("支付宝支付"+priceall+"元");
                    }
                });

                zhi_qian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("tab", "zhi_qian=="+zhi_qian);
                        if (xuan_weixin.isChecked()){
                            payType=1;
                            HashMap<String,String> zhimap = new HashMap<>();
                            zhimap.put("payType","1");
                            zhimap.put("orderId",orderId);
                            pInterfacegetZhiFu.getZhiFu(null,zhimap);
                            Log.e("tab", "zhimap=="+zhimap);

                        }
                        if (xuan_zifubao.isChecked()){
                            payType=2;
                        }
                    }
                });

                //退出
                zhi_xia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    //支付
    @Override
    public void getZhiFu(Object object) {
        //req = new PayReq();
        ZhiFuBean zhiFuBean2 = (ZhiFuBean)object;
        String appId = zhiFuBean2.getAppId();
        String partnerId = zhiFuBean2.getPartnerId();
        String prepayId = zhiFuBean2.getPrepayId();
        String nonceStr = zhiFuBean2.getNonceStr();
        String timeStamp = zhiFuBean2.getTimeStamp();
        String packageValue = zhiFuBean2.getPackageValue();
        String sign = zhiFuBean2.getSign();
        Intent intent = new Intent(YuanZuoActivity.this,WXPayEntryActivity.class);
        intent.putExtra("appId",appId);
        intent.putExtra("partnerId",partnerId);
        intent.putExtra("prepayId",prepayId);
        intent.putExtra("nonceStr",nonceStr);
        intent.putExtra("timeStamp",timeStamp);
        intent.putExtra("packageValue",packageValue);
        intent.putExtra("sign",sign);
        startActivity(intent);
        // req.extData = "app data";
//        api = WXAPIFactory.createWXAPI(this, APP_ID);
//        api.sendReq(req);
//        Log.e("tab", "req=="+req);
    }

    //价格样式
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    //内存优化
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfacegetXiaDan.onDsply();
        pInterfacegetXiaDan=null;
    }
}
