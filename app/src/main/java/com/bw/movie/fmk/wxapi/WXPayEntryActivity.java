package com.bw.movie.fmk.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final String APP_ID = "wxb3852e6a6b7d9516";
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);

        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
        Intent intent1 = getIntent();
        String appId = intent1.getStringExtra("appId");
        String partnerId = intent1.getStringExtra("partnerId");
        String prepayId = intent1.getStringExtra("prepayId");
        String packageValue = intent1.getStringExtra("packageValue");
        String nonceStr = intent1.getStringExtra("nonceStr");
        String sign = intent1.getStringExtra("sign");
        String timeStamp = intent1.getStringExtra("timeStamp");
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId =partnerId;
        request.prepayId= prepayId;
        request.packageValue = packageValue;
        request.nonceStr= nonceStr;
        request.timeStamp= timeStamp;
        request.sign= sign;
        api.sendReq(request);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_tip);
            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
            builder.show();
//            Intent intent = new Intent(WXPayEntryActivity.this,WXPayEntryActivity.class);
//            startActivity(intent);
        }
    }
    }
