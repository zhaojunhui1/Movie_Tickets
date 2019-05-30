package com.bw.movie.fmk.util;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/9 15:58
 * @Description:
 */
public class Url {


    //http://mobile.bwstudent.com
    //http://172.17.8.100/

    public static final String TOU = "http://mobile.bwstudent.com/";

    //http://mobile.bwstudent.com/
    //http://172.17.8.100/

    //登录，http://172.17.8.100/movieApi/user/v1/login
    public static String DENGLU = "movieApi/user/v1/login";

    //注册，http://172.17.8.100/movieApi/user/v1/registerUser
    public static String ZHUCE = "movieApi/user/v1/registerUser";

    //轮播，//http://172.17.8.100/movieApi/movie/v1/findMovieListByCinemaId?cinemaId=5
    public static String LUNBOU="movieApi/movie/v1/findMovieListByCinemaId?cinemaId=5";

    //热门电影 http://172.17.8.100/movieApi/movie/v1/findHotMovieList
    public static String RMENDIANYING="movieApi/movie/v1/findHotMovieList?page=1&count=10";

    //正在热映 http://172.17.8.100/movieApi/movie/v1/findReleaseMovieList?page=1&count=10
    public static String ZHENGZAIRYING="movieApi/movie/v1/findReleaseMovieList?page=1&count=10";

    //即将上映 http://172.17.8.100/movieApi/movie/v1/findComingSoonMovieList?page=1&count=10
    public static String JIJANGSAHNGYING="movieApi/movie/v1/findComingSoonMovieList?page=1&count=10";

    //电影ID查询电影信息，http://172.17.8.100/movieApi/movie/v1/findMoviesById?movieId=12
    public static String DIANYINGID="movieApi/movie/v1/findMoviesById";

    //电影预告，http://172.17.8.100/movieApi/movie/v1/findMoviesDetail?movieId=12
    public static String DIANYINGYUGAO="movieApi/movie/v1/findMoviesDetail";

    //关注电影，http://172.17.8.100/movieApi/movie/v1/verify/followMovie
    public static String GUANZHUDIANYING="movieApi/movie/v1/verify/followMovie";

    //取消关注，http://172.17.8.100/movieApi/movie/v1/verify/cancelFollowMovie
    public static String QUXIAOGUANZHU="movieApi/movie/v1/verify/cancelFollowMovie";

    //查看电影评论，http://172.17.8.100/movieApi/movie/v1/findAllMovieComment?movieId=1&page=1&count=5
    public static String DIANYINGPINGLUN="movieApi/movie/v1/findAllMovieComment";

    //评论点赞，http://172.17.8.100/movieApi/movie/v1/verify/movieCommentGreat?commentId=1
    public static String PINGLUNDIANZAN="movieApi/movie/v1/verify/movieCommentGreat";

    //添加用户对影片的评论，http://172.17.8.100/movieApi/movie/v1/verify/movieComment
    public static String YINGPIANPINGLUN="movieApi/movie/v1/verify/movieComment";

    //购票，http://172.17.8.100/movieApi/movie/v1/findCinemasListByMovieId?movieId=1
    public static String DIANYINGGOUPIAO="movieApi/movie/v1/findCinemasListByMovieId";

    //根据电影ID和影院ID查询电影排期列表，http://172.17.8.100/movieApi/movie/v1/findMovieScheduleList?cinemasId=2&movieId=3
    public static String PAIQILEIBIAO="movieApi/movie/v1/findMovieScheduleList";

    //根据影院ID查询该影院下即将上映的电影列表,http://172.17.8.100/movieApi/movie/v1/findSoonMovieByCinemaId
    public static String YINGYUANID="movieApi/movie/v1/findSoonMovieByCinemaId";

    //根据电影ID和影院ID查询电影排期列表，http://172.17.8.100/movieApi/movie/v1/findMovieScheduleList
    public static String DIANYINGID_YINGYUANID="movieApi/movie/v1/findMovieScheduleList";

    //购票下单，http://172.17.8.100/movieApi/movie/v1/verify/buyMovieTicket
    public static String GOUPIAO_XIADAN="movieApi/movie/v1/verify/buyMovieTicket";

    //支付 http://172.17.8.100/movieApi/movie/v1/verify/pay
    public static String WEIXIN_ZHIFU="movieApi/movie/v1/verify/pay";

    //用户购票记录查询列表，http://172.17.8.100/movieApi/user/v1/verify/findUserBuyTicketRecordList
    public static String GOUPIAO_JILU="movieApi/user/v1/verify/findUserBuyTicketRecordList";

    //上传用户头像，http://172.17.8.100/movieApi/user/v1/verify/uploadHeadPic
    public static String TOUXIANG="movieApi/user/v1/verify/uploadHeadPic";

    //查询用户信息，http://172.17.8.100/movieApi/user/v1/verify/getUserInfoByUserId
    public static String YONGHU_XINXI="movieApi/user/v1/verify/getUserInfoByUserId";

    //重置密码，http://172.17.8.100/movieApi/user/v1/verify/modifyUserPwd
    public static String XIUGAI_MIMA="movieApi/user/v1/verify/modifyUserPwd";

    //微信登陆，http://172.17.8.100/movieApi/user/v1/weChatBindingLogin
    public static String WEIXN_DENGLU="movieApi/user/v1/weChatBindingLogin";


}
