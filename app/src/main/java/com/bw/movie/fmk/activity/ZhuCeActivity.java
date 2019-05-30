package com.bw.movie.fmk.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.ZhuBean;
import com.bw.movie.fmk.jiami.EncryptUtil;
import com.bw.movie.fmk.mvp.p.MyPenster;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.bw.movie.fmk.util.ZhengZeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZhuCeActivity extends BasefActivity implements VInterface.VInterfaceZhuCe {

    private EditText nicheng;
    private EditText xing;
    private EditText chusheng;
    private EditText zhuphone;
    private EditText youxiang;
    private EditText zhupwd;
    private Button zhuce;
    private PInterface.PInterfaceZhuCe pInterfaceZhuCe;
    private HashMap<String, String> map;
    private List<String> zhuBean = new ArrayList<>();
    private TextView yiyou;
    private EditText zhupwdque;
    private String encrypt;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_zhu_ce;
    }

    @Override
    protected void initView() {
        nicheng = fvd(R.id.nicheng);
        xing = fvd(R.id.xing);
        chusheng = fvd(R.id.chusheng);
        zhuphone = fvd(R.id.zhuphone);
        youxiang = fvd(R.id.youxiang);
        zhupwd = fvd(R.id.zhupwd);
        zhupwdque = fvd(R.id.zhupwdque);
        zhuce = fvd(R.id.zhuce);
        yiyou = fvd(R.id.yiyou);

    }

    //数据
    @Override
    protected void initData() {
        pInterfaceZhuCe = new MyPenster(this);

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //昵称
                String mnicheng = nicheng.getText().toString();
                //性别
                String mxing = xing.getText().toString();
                //出生日期
                String mchusheng = chusheng.getText().toString();
                //手机号
                String mzhuphone = zhuphone.getText().toString();

                 /*
                   保存邮箱数据，传到我的页面,用户信息
                */
                //保存数据
                SharedPreferences sp = getSharedPreferences("youxiang", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name", youxiang.getText().toString().trim());
                Log.e("tag","youxiang=="+youxiang);
                edit.commit();

                //邮箱
                String myouxiang = youxiang.getText().toString();
                //密码
                String mzhupwd = zhupwd.getText().toString();

                //加密
                encrypt = EncryptUtil.encrypt(mzhupwd);

                //确认密码
                String mzhupwdque = zhupwdque.getText().toString();

                if (ZhengZeUtil.isNull(mnicheng)){
                    Toast.makeText(ZhuCeActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isNull(mxing)){
                    Toast.makeText(ZhuCeActivity.this, "性别不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isNull(mchusheng)){
                    Toast.makeText(ZhuCeActivity.this, "出生日期不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isNull(mzhuphone)){
                    Toast.makeText(ZhuCeActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isNull(myouxiang)){
                    Toast.makeText(ZhuCeActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isNull(mzhupwd)){
                    Toast.makeText(ZhuCeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isNull(mzhupwdque)){
                    Toast.makeText(ZhuCeActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isUserName(mnicheng)){
                    Toast.makeText(ZhuCeActivity.this, "用户名不规范", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isMobile(mnicheng)){
                    Toast.makeText(ZhuCeActivity.this, "手机号不规范", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isEmail(mnicheng)){
                    Toast.makeText(ZhuCeActivity.this, "邮箱不规范", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isPassword(mnicheng)){
                    Toast.makeText(ZhuCeActivity.this, "密码不规范", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (ZhengZeUtil.isPassword(mzhupwdque)){
                    Toast.makeText(ZhuCeActivity.this, "确认密码不规范", Toast.LENGTH_SHORT).show();
                    return ;
                }

                getUrl(mzhuphone,mnicheng,mxing,mchusheng,myouxiang,encrypt);
            }
        });

        yiyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhuCeActivity.this,LogActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void  getUrl(String mzhuphone,String mnicheng,String mxing,String mchusheng,String myouxiang,String encrypt){
        map = new HashMap<>();
//        String phone1 = zhuphone.getText().toString();
//        String pwd1 = zhupwd.getText().toString();
        map.put("nickName",mnicheng);
        map.put("pwd2",encrypt);
        map.put("sex",mxing);
        map.put("birthday",mchusheng);
        map.put("email",myouxiang);
        map.put("phone",mzhuphone);
        map.put("pwd",encrypt);
        pInterfaceZhuCe.getZhuCe(map);
    }

    @Override
    public void showZhuCe(Object object) {
        ZhuBean zhuBean2 = (ZhuBean)object;
        String message = zhuBean2.getMessage();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        zhuBean.add(message);
        if (zhuBean2.getStatus().equals("0000")){
            Intent intent = new Intent(ZhuCeActivity.this, LogActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pInterfaceZhuCe.onDsply();
        pInterfaceZhuCe = null;
    }
}
