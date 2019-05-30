package com.bw.movie.fmk.mvp.p;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/9 17:11
 * @Description:
 */
public interface PInterface {

    //登录
    public interface PInterfaceDengLu {
        void getDengLu(HashMap<String, String> map);
        void onDsply();
    }
    //注册
    public interface PInterfaceZhuCe {
        void getZhuCe(HashMap<String, String> map);
        void onDsply();
    }

    //轮播
    public interface PInterfaceLunBo {
        void getLunBo(String url);
        void onDsply();
    }

    //热门电影
    public interface PInterfaceRMen {
        void getRMen(String url);
        void onDsply();
    }

    //正在热映
    public interface PInterfacegetRYing {
        void getRYing(String url);
        void onDsply();
    }

    //即将上映
    public interface PInterfacegetShangYing {
        void getShangYing(String url);
        void onDsply();
    }

    //根据电影ID查询电影信息,详情主页
    public interface PInterfacegetXiangQingZhuYe {
        void getXiangQingZhuYe(String url, HashMap<String, String> map);
        void onDsply();
    }

    //电影预告
    public interface PInterfacegetDianYingYuGao {
        void getDianYingYuGao(String url, HashMap<String, String> map);
        void onDsply();
    }

    //电影关注
    public interface PInterfacegetDianYingGuanZhu {
        void getDianYingGuanZhu(String url, HashMap<String, String> map);
        void onDsply();
    }

    //取消电影关注
    public interface PInterfacegetQuXiaoDianYingGuanZhu {
        void getQuXiaoDianYingGuanZhu(String url, HashMap<String, String> map);
        void onDsply();
    }

    //电影评论
    public interface PInterfacegetDianYingPingLun {
        void getDianYingPingLun(String url, HashMap<String, String> map);
        void onDsply();
    }

    //电影评论点赞
    public interface PInterfacegetDianZan {
        void getDianZan(String url, HashMap<String, String> map);
        void onDsply();
    }

    //添加评论
    public interface PInterfacegetTianJIaPingLun {
        void getTianJIaPingLun(String url, HashMap<String, String> map);
        void onDsply();
    }

    //购票
    public interface PInterfacegetGouPiao {
        void getGouPiao(String url, HashMap<String, String> map);
        void onDsply();
    }

    //根据电影ID和影院ID查询电影排期列表
    public interface PInterfacegetDianYingPaiQi {
        void getDianYingPaiQi(String url, HashMap<String, String> map);
        void onDsply();
    }


    //购票下单
    public interface PInterfacegetXiaDan {
        void getXiaDan(String url, HashMap<String, String> map);
        void onDsply();
    }

    //支付
    public interface PInterfacegetZhiFu {
        void getZhiFu(String url, HashMap<String, String> map);
        void onDsply();
    }

    //头像
    public interface PInterfacegetTouXiang {
        void getTouXiang(String url, MultipartBody.Part file);
        void onDsply();
    }

    //用户信息
    public interface PInterfacegetYongHu {
        void getYongHu(String url);
        void onDsply();
    }

    //修改密码
    public interface PInterfacegetXiuGai {
        void getXiuGai(String url, HashMap<String, String> map);
        void onDsply();
    }

    //微信登陆
    public interface PInterfacegetWeiXinDengLu {
        void getWeiXinDengLu(String url, HashMap<String, String> map);
        void onDsply();
    }

}
