package com.bw.movie.fmk.mvp.m;

import android.util.Log;

import com.bw.movie.fmk.bean.BannerBean;
import com.bw.movie.fmk.bean.DianYingPingLunBean;
import com.bw.movie.fmk.bean.DianYingYuGaoBean;
import com.bw.movie.fmk.bean.DianZanBean;
import com.bw.movie.fmk.bean.GouPiaoBean;
import com.bw.movie.fmk.bean.GuanZhuBean;
import com.bw.movie.fmk.bean.LoginBean;
import com.bw.movie.fmk.bean.PaiQiBeam;
import com.bw.movie.fmk.bean.QuXiaoGuanZhuBean;
import com.bw.movie.fmk.bean.RMenBean;
import com.bw.movie.fmk.bean.RYingBean;
import com.bw.movie.fmk.bean.ShangYingBean;
import com.bw.movie.fmk.bean.TianJiaPingLunBean;
import com.bw.movie.fmk.bean.TouXiangBean;
import com.bw.movie.fmk.bean.XiaDanBean;
import com.bw.movie.fmk.bean.XiangQingZhuYeBean;
import com.bw.movie.fmk.bean.XiuGaiBean;
import com.bw.movie.fmk.bean.YongHuBean;
import com.bw.movie.fmk.bean.ZhiFuBean;
import com.bw.movie.fmk.bean.ZhuBean;
import com.bw.movie.fmk.util.Api;
import com.bw.movie.fmk.util.RetroFitUtil;
import com.bw.movie.fmk.util.Url;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/9 16:28
 * @Description:
 */
public class MyModel {

    public MyCallBack myCallBack;


    public void getpostDeng(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.e("tab","string=="+string);
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(string, LoginBean.class);
//                            JSONObject object=new JSONObject(string);
                           // String m = object.getString("message");

                           // Log.e("tab","model=="+m);
                            myCallBack.succer(loginBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void getpostZhu(String url, final Map<String,String> map){
         RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
         Api api = inRetroFitUtil.setRtrofit(Api.class);
         api.getpost(url,map)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Action1<ResponseBody>() {
                     @Override
                     public void call(ResponseBody responseBody) {
                         try {
                             String string = responseBody.string();
                             Log.e("tab","string=="+string);
                             Gson gson = new Gson();
                             ZhuBean zhuBean = gson.fromJson(string, ZhuBean.class);
                             myCallBack.succer(zhuBean);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 });
     }

     //轮播图
     public void getBanner(){
         RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
         Api api = inRetroFitUtil.setRtrofit(Api.class);
         api.getapi(Url.LUNBOU)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Action1<ResponseBody>() {
                     @Override
                     public void call(ResponseBody responseBody) {
                         try {
                             String string = responseBody.string();
                             Gson gson = new Gson();
                             BannerBean bannerBean = gson.fromJson(string, BannerBean.class);
                             Log.e("tag","轮播M=="+bannerBean);
                             myCallBack.succer(bannerBean);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 });
     }

    //热门电影
    public void getRMenDianYing(){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi(Url.RMENDIANYING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            RMenBean rMenBean = gson.fromJson(string, RMenBean.class);
                            myCallBack.succer(rMenBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //正在热映
    public void getZhengZaiRYing(){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi(Url.ZHENGZAIRYING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            RYingBean rYingBean = gson.fromJson(string, RYingBean.class);
                            myCallBack.succer(rYingBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //正在热映
    public void getJiJangShangYing(){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi(Url.JIJANGSAHNGYING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            ShangYingBean shangYingBean = gson.fromJson(string, ShangYingBean.class);
                            myCallBack.succer(shangYingBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //根据电影ID查询电影信息,详情主页
    public void getXiangQingZhuYe(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            XiangQingZhuYeBean xiangQingZhuYeBean = gson.fromJson(string, XiangQingZhuYeBean.class);
                            myCallBack.succer(xiangQingZhuYeBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //电影详情
    public void getDianYingYuGao(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            DianYingYuGaoBean xiangQingBean = gson.fromJson(string, DianYingYuGaoBean.class);
                            myCallBack.succer(xiangQingBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //电影关注
    public void getDianYingGuanZhu(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.e("tag", "call: "+string );
                            Gson gson = new Gson();
                            GuanZhuBean guanZhuBean = gson.fromJson(string, GuanZhuBean.class);
                            myCallBack.succer(guanZhuBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //取消电影关注
    public void getQuXiaoDianYingGuanZhu(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.e("tag", "call: "+string );
                            Gson gson = new Gson();
                            QuXiaoGuanZhuBean quXiaoGuanZhuBean = gson.fromJson(string, QuXiaoGuanZhuBean.class);
                            myCallBack.succer(quXiaoGuanZhuBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //查看电影评论
    public void getChaKanDianYingPingLun(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            DianYingPingLunBean dianYingPingLunBean = gson.fromJson(string, DianYingPingLunBean.class);
                            Log.e("tagggg", "call: "+dianYingPingLunBean.getMessage() );
                            myCallBack.succer(dianYingPingLunBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //电影评论点赞
    public void getPingLunDianZan(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            DianZanBean dianZanBean = gson.fromJson(string, DianZanBean.class);
                            Log.e("tagggg", "call: "+dianZanBean.getMessage() );
                            myCallBack.succer(dianZanBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //添加电影评论
    public void getTianJIaPingLun(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            TianJiaPingLunBean tianJiaPingLunBean = gson.fromJson(string, TianJiaPingLunBean.class);
                            myCallBack.succer(tianJiaPingLunBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //购票
    public void getGouPiao(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            GouPiaoBean gouPiaoBean = gson.fromJson(string, GouPiaoBean.class);
                            myCallBack.succer(gouPiaoBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //根据电影ID和影院ID查询电影排期列表
    public void getPaiQi(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi2(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            PaiQiBeam paiQiBeam = gson.fromJson(string, PaiQiBeam.class);
                            Log.e("tab","paiQiBeam"+paiQiBeam);
                            myCallBack.succer(paiQiBeam);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //购票下单
    public void getXiaDan(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            XiaDanBean xiaDanBean = gson.fromJson(string, XiaDanBean.class);
                            myCallBack.succer(xiaDanBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //微信支付
    public void getZhiFu(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            ZhiFuBean zhiFuBean = gson.fromJson(string, ZhiFuBean.class);
                            myCallBack.succer(zhiFuBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //头像
    public void getTouXiang(String url, final MultipartBody.Part file){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost2(url,file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            TouXiangBean touXiangBean = gson.fromJson(string, TouXiangBean.class);
                            myCallBack.succer(touXiangBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //用户信息
    public void getYongHu(){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getapi(Url.YONGHU_XINXI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            YongHuBean yongHuBean = gson.fromJson(string, YongHuBean.class);
                            myCallBack.succer(yongHuBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //修改密码
    public void getxiugai(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            XiuGaiBean xiuGaiBean = gson.fromJson(string, XiuGaiBean.class);
                            myCallBack.succer(xiuGaiBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //微信登陆
    public void getwexindenglu(String url, final Map<String,String> map){
        RetroFitUtil inRetroFitUtil = RetroFitUtil.getInRetroFitUtil();
        Api api = inRetroFitUtil.setRtrofit(Api.class);
        api.getpost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Gson gson = new Gson();
                            LoginBean loginBean2 = gson.fromJson(string, LoginBean.class);
                            myCallBack.succer(loginBean2);
                            Log.e("tab","M走了");
                            Log.e("tab","loginBean2=="+loginBean2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void setMyModel(MyCallBack myCallBack) {
            this.myCallBack = myCallBack;
    }

    //内部接口
    public interface MyCallBack{
        public void succer(Object object);
    }
}
