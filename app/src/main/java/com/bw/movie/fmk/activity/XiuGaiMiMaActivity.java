package com.bw.movie.fmk.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.XiuGaiBean;
import com.bw.movie.fmk.jiami.EncryptUtil;
import com.bw.movie.fmk.mvp.p.MyPensterTwo;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XiuGaiMiMaActivity extends BasefActivity implements VInterface.VInterfacegetXiuGai {

    //修改
    private List<String> xiuGaiBean = new ArrayList<>();
    private EditText dang_qian;
    private EditText xin;
    private EditText queren_xin;
    private PInterface.PInterfacegetXiuGai pInterfacegetXiuGai;
    private String encrypt;
    private String encrypt2;
    private String jiuencrypt;
    private Button xiuTiao;
    private HashMap<String, String> map;
    private String jiupwd;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_xiu_gai_mi_ma;
    }

    @Override
    protected void initView() {

         /*
          取出从，登陆页面，保存的密码数据
         */
        //取出数据
//        SharedPreferences sp1 = getSharedPreferences("jiupwd", 0);
//        jiupwd = sp1.getString("pwd2","");
//        dang_qian.setText(jiupwd);
//        Log.e("tag","dang_qian=="+dang_qian);

        dang_qian = fvd(R.id.dang_qian);//旧密码

        xin = fvd(R.id.xin);//新密码

        queren_xin = fvd(R.id.queren_xin);//确认新密码

        xiuTiao = fvd(R.id.xiu_tijao);//提交

        pInterfacegetXiuGai = new MyPensterTwo(this);
    }

    //数据
    @Override
    protected void initData() {

        /*
         提交
         */
        xiuTiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //旧密码
                String dang_qian1 = dang_qian.getText().toString();

                //加密，旧密码
                jiuencrypt = EncryptUtil.encrypt(dang_qian1);

                //新密码
                String xin1 = xin.getText().toString();

                //加密，新密码
                encrypt = EncryptUtil.encrypt(xin1);

                //确认密码
                String mqueren_xin = queren_xin.getText().toString();

                //加密,确认新密码
                encrypt2 = EncryptUtil.encrypt(mqueren_xin);

                map = new HashMap<>();

                map.put("oldPwd",jiuencrypt);//老密码
                map.put("newPwd",encrypt);//新密码
                map.put("newPwd2",encrypt2);//确认新密码

                pInterfacegetXiuGai.getXiuGai(null, map);
                Log.e("tag","pInterfacegetXiuGai=="+map);
            }
        });
    }

    //修改
    @Override
    public void getXiuGai(Object object) {
       XiuGaiBean xiuGaiBean2 = (XiuGaiBean)object;
       String message = xiuGaiBean2.getMessage();
       Toast.makeText(this,message,Toast.LENGTH_LONG).show();
       xiuGaiBean.add(message);
    }
}
