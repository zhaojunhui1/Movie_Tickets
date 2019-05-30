package com.bw.movie.fmk.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.fmk.bean.LoginBean;
import com.bw.movie.fmk.mvp.p.MyPensterTwo;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler,VInterface.VInterfacegetWeiXinDeng {
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private PInterface.PInterfacegetWeiXinDengLu pInterfacegetWeiXinDengLu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pInterfacegetWeiXinDengLu = new MyPensterTwo(this);
        Log.e("tab","pInterfacegetWeiXinDengLu=="+pInterfacegetWeiXinDengLu);
        regToWx();
    }
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(),this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH){
            //Log.e("tag","微信登录");
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            String code = authResp.code;
            HashMap<String,String> map = new HashMap<>();
            map.put("code",code);
            pInterfacegetWeiXinDengLu.getWeiXinDengLu(null,map);
            Log.e("tab","V走了");
            Log.e("tab","code=="+map);
        }
    }

    @Override
    public void getWeiXinDeng(Object object) {
        LoginBean loginBean = (LoginBean)object;
        if (loginBean.getMessage().equals("登陆成功")){
            Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            int userId = loginBean.getResult().getUserId();
            String sessionId = loginBean.getResult().getSessionId();
            long birthday = loginBean.getResult().getUserInfo().getBirthday();
            String headPic = loginBean.getResult().getUserInfo().getHeadPic();
            String nickName = loginBean.getResult().getUserInfo().getNickName();
            String phone = loginBean.getResult().getUserInfo().getPhone();
            int sex = loginBean.getResult().getUserInfo().getSex();

            SharedPreferences sp=getSharedPreferences("config",MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("userId", String.valueOf(userId));
            edit.putString("sessionId",sessionId);
            edit.putLong("birthday",birthday);
            edit.putString("headPic",headPic);
            edit.putString("nickName",nickName);
            edit.putString("phone",phone);
            edit.putInt("sex",sex);
            edit.commit();
//            Intent intentss = new Intent(WXEntryActivity.this,HomeActivity.class);
//            startActivity(intentss);
        }else{
            Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfacegetWeiXinDengLu.onDsply();
        pInterfacegetWeiXinDengLu=null;
    }
}
