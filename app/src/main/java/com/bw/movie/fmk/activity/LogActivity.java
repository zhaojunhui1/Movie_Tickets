package com.bw.movie.fmk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.LoginBean;
import com.bw.movie.fmk.fragment.FragmentActivity;
import com.bw.movie.fmk.jiami.EncryptUtil;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.bw.movie.fmk.wxapi.WXEntryActivity;
import com.bw.movie.zjh.utils.statusbar.StatusBarWindowTop;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;

public class LogActivity extends BasefActivity implements VInterface.VInterfaceDengLu {

    private EditText phone;
    private EditText pwd;
    private ImageView yanjing;
    private CheckBox jizhu;
    private TextView kuai_zhu;
    private Button denglu;
    private PInterface.PInterfaceDengLu pInterfaceDengLu;
    private HashMap<String, String> map;
    private boolean b = true;
    private SharedPreferences sp;
    private TextView tiaoguo;
    private ImageView weixin;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_log;
    }

    @Override
    protected void initView() {
        StatusBarWindowTop.setStatusBarFullTransparent(LogActivity.this);

        phone = fvd(R.id.phone);

        pwd = fvd(R.id.pwd);
        yanjing = fvd(R.id.yanjing);
        jizhu = fvd(R.id.jizhu);
        kuai_zhu = fvd(R.id.kuai_zhu);
        denglu = fvd(R.id.denglu);
        tiaoguo = fvd(R.id.tiaoguo);
        weixin = fvd(R.id.weixin);
        pInterfaceDengLu = new MyPenster(this);

        //微信
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogActivity.this,WXEntryActivity.class);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
                startActivity(intent);
            }
        });

        //微信登陆
        regToWx();

        inio();
    }

    //微信登陆
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    private void inio() {
        tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogActivity.this,FragmentActivity.class);
                startActivity(intent);
            }
        });
    }

    //数据
    @Override
    protected void initData() {

        sp = getSharedPreferences("info",MODE_PRIVATE);
        boolean flag = sp.getBoolean("flag", false);
        if (flag){
            String phone1 = sp.getString("phone", "");
            String pwd1 = sp.getString("pwd", "");
            jizhu.setChecked(flag);
            phone.setText(phone1);
            pwd.setText(pwd1);
        }else {
            phone.setText("");
            pwd.setText("");
            jizhu.setChecked(false);
        }

         /*
           保存密码数据，传到修改页面
         */
        //保存数据
//        SharedPreferences sp1 = getSharedPreferences("jiupwd", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("pwd2", pwd.getText().toString().trim());
//        Log.e("tag","pwd=="+pwd);
//        edit.commit();

        //记住密码
        denglu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                map = new HashMap<>();
                String phone1 = phone.getText().toString();

                String pwd1 = pwd.getText().toString();
                String encrypt = EncryptUtil.encrypt(pwd1);
                SharedPreferences.Editor edit = sp.edit();
                if (jizhu.isChecked()){

                    edit.putString("phone",phone1);
                    edit.putString("pwd", pwd1);
                    edit.putBoolean("flag",true);
                    edit.commit();
                }else {
                    edit.clear();
                }
                edit.apply();
                map.put("phone",phone1);
                map.put("pwd",encrypt);
                pInterfaceDengLu.getDengLu(map);
            }
        });

        //快速注册
        kuai_zhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogActivity.this, ZhuCeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //设置密码的显示和隐藏
        yanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (b){
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    b = false ;
                }else {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    b = true ;
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showDengLu(Object object) {

//        String object1 = (String)object;
//        Log.e("tab","message=="+object1);
        LoginBean loginBean = (LoginBean)object;
        String message = loginBean.getMessage();
        //Log.e("tab","message=="+message);
        LoginBean.ResultBean result = loginBean.getResult();

        //List<GreendaoBean> greendaoBeans = App.daoSession.loadAll();
        Toast.makeText(this,message+"",Toast.LENGTH_LONG).show();
        if (message.equals("登陆成功")){

            //传入头参
            SharedPreferences sharedPreferences = getSharedPreferences("fmkcan", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userId", loginBean.getResult().getUserId() + "");
            editor.putString("sessionId", loginBean.getResult().getSessionId());
            editor.commit();

            SharedPreferences sharedPreferences1 = getSharedPreferences("token", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.putString("userId", loginBean.getResult().getUserId() + "");
            editor1.putString("sessionId", loginBean.getResult().getSessionId());
            editor1.commit();

            Log.e("UserId", loginBean.getResult().getUserId() + "");

            Intent intent = new Intent(this, FragmentActivity.class);

            intent.putExtra("nickName",loginBean.getResult().getUserInfo().getNickName());
            intent.putExtra("headPic",loginBean.getResult().getUserInfo().getHeadPic());
            intent.putExtra("phone",loginBean.getResult().getUserInfo().getPhone());
            intent.putExtra("birthday",loginBean.getResult().getUserInfo().getBirthday());
            intent.putExtra("sex",loginBean.getResult().getUserInfo().getSex());
            intent.putExtra("lastLoginTime",loginBean.getResult().getUserInfo().getLastLoginTime());

            startActivity(intent);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LogActivity.this).toBundle());
            overridePendingTransition(R.anim.activity_dong,R.anim.activity_dong_tui);


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfaceDengLu.onDsply();
        pInterfaceDengLu = null;
    }
}