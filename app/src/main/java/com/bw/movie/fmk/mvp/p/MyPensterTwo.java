package com.bw.movie.fmk.mvp.p;

import android.util.Log;

import com.bw.movie.fmk.mvp.m.MyModel;
import com.bw.movie.fmk.mvp.v.VInterface;
import com.bw.movie.fmk.util.Url;

import java.util.HashMap;

import okhttp3.MultipartBody;


/**
 * @Auther: 付明锟
 * @Date: 2019/5/20 16:43
 * @Description:
 */

public class MyPensterTwo<T> implements PInterface.PInterfacegetDianYingPaiQi ,PInterface.PInterfacegetXiaDan ,PInterface.PInterfacegetZhiFu
  ,PInterface.PInterfacegetTouXiang ,PInterface.PInterfacegetYongHu,PInterface.PInterfacegetXiuGai,PInterface.PInterfacegetWeiXinDengLu{


    private MyModel myModel;
    public T tt;

    public MyPensterTwo(T tt) {
        myModel = new MyModel();
        this.tt = tt;
    }

    //根据电影ID和影院ID查询电影排期列表
    @Override
    public void getDianYingPaiQi(String url, HashMap<String, String> map) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetDianYingPaiQi)tt).getDianYingPaiQi(object);
            }
        });
        myModel.getPaiQi(Url.DIANYINGID_YINGYUANID,map);
        Log.e("tab","myModel"+map);
    }


    //电影下单
    @Override
    public void getXiaDan(String url, HashMap<String, String> map) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetXiaDan)tt).getXiaDan(object);
            }
        });
        myModel.getXiaDan(Url.GOUPIAO_XIADAN,map);
    }

    //支付
    @Override
    public void getZhiFu(String url, HashMap<String, String> map) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetZhiFu)tt).getZhiFu(object);
            }
        });
        myModel.getZhiFu(Url.WEIXIN_ZHIFU,map);
    }

    //头像
    @Override
    public void getTouXiang(String url, MultipartBody.Part file) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetTouXiang)tt).getTouXiang(object);
            }
        });
        myModel.getTouXiang(Url.TOUXIANG,file);
        Log.e("tab","myModel=="+file);
    }

    //用户信息
    @Override
    public void getYongHu(String url) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetYongHu)tt).getYongHu(object);
            }
        });
       myModel.getYongHu();
    }

    //修改密码
    @Override
    public void getXiuGai(String url ,HashMap<String, String> map) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetXiuGai)tt).getXiuGai(object);
            }
        });
        myModel.getxiugai(Url.XIUGAI_MIMA,map);
        Log.e("tag","getxiugai=="+map);
    }

    //微信登陆
    @Override
    public void getWeiXinDengLu(String url ,HashMap<String, String> map) {
        myModel.setMyModel(new MyModel.MyCallBack() {
            @Override
            public void succer(Object object) {
                ((VInterface.VInterfacegetWeiXinDeng)tt).getWeiXinDeng(object);
            }
        });
        myModel.getwexindenglu(Url.WEIXN_DENGLU,map);
        Log.e("tab","P走了");
        Log.e("tab","P=="+map);
    }

    //内存优化

    @Override
    public void onDsply() {
         if (tt!=null){
             tt=null;
         }
    }
}
