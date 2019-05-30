package com.bw.movie.zjh.ui.cinema.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.activity.XiuGaiMiMaActivity;
import com.bw.movie.fmk.base.BasefActivity;
import com.bw.movie.fmk.bean.YongHuBean;
import com.bw.movie.fmk.fragment.FragmentActivity;
import com.bw.movie.fmk.mvp.p.MyPensterTwo;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/13 19:40
 * desc   :  我的资料
 * version: 1.0
 */
public class MyPresonActivity extends BasefActivity implements VInterface.VInterfacegetYongHu {

    private SimpleDraweeView wode_tou;
    private TextView wode_name;
    private TextView wode_xing;
    private TextView wode_sheng;
    private TextView wode_shou;
    private TextView wode_you;
    private TextView wode_shi;
    private List<YongHuBean.ResultBean> yongHuBean = new ArrayList<>();
    private PInterface.PInterfacegetYongHu pInterfacegetYongHu;
    private String message;
    private YongHuBean.ResultBean result;
    private String status;
    private ImageView yong_fan;
    private ImageView wode_chong;

    public MyPresonActivity() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_preson;
    }

    @Override
    protected void initView() {
        wode_tou = fvd(R.id.wode_tou);//头像
        wode_name = fvd(R.id.wode_name);//昵称
        wode_xing = fvd(R.id.wode_xing);//性别
        wode_sheng = fvd(R.id.wode_sheng);//出生日期
        wode_shou = fvd(R.id.wode_shou);//手机号

        wode_you = fvd(R.id.wode_you);//邮箱

        yong_fan = fvd(R.id.yong_fan);//返回

        //修改密码
        wode_chong = fvd(R.id.wode_chong);

        //跳转修改密码
        wode_chong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPresonActivity.this, XiuGaiMiMaActivity.class);
                startActivity(intent);
            }
        });

        //跳转我的
        yong_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPresonActivity.this, FragmentActivity.class);
                startActivity(intent);
            }
        });

        /*
          取出从ZhuCeActivity保存的邮箱数据
         */
        //取出数据
        SharedPreferences sp1 = getSharedPreferences("youxiang", 0);
        String name = sp1.getString("name", "");
        wode_you.setText(name);
        Log.e("tag", "wode_you==" + wode_you);

        wode_shi = fvd(R.id.wode_shi);//上次登录时间
        pInterfacegetYongHu = new MyPensterTwo(this);
    }

    @Override
    protected void initData() {
        pInterfacegetYongHu.getYongHu(null);
        Log.e("tab", "pInterfacegetYongHu==" + pInterfacegetYongHu);
    }

    @Override

    public void getYongHu(Object object) {
        YongHuBean yongHuBean2 = (YongHuBean) object;
        /*
          记住
          YongHuBean.ResultBean result = yongHuBean2.getResult();
         */
        YongHuBean.ResultBean result = yongHuBean2.getResult();

        String headPic = result.getHeadPic();//头像
        wode_tou.setImageURI(Uri.parse(headPic));

        String nickName = result.getNickName();//昵称
        wode_name.setText(nickName);

        int sex = result.getSex();//性别
        wode_xing.setText(sex + "");

        long birthday = result.getBirthday();//生日
        wode_sheng.setText(birthday + "");

        String phone = result.getPhone();//手机号
        wode_shou.setText(phone);

        long lastLoginTime = result.getLastLoginTime();//登陆时间
        wode_shi.setText(lastLoginTime + "");

        yongHuBean.add(result);


    }
}