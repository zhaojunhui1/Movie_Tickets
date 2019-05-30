package com.bw.movie.zjh.utils.mvp.util;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 9:11
 * desc   :
 * version: 1.0
 */
public class Apis {
    //  主机名
    //   内网主机名
    public static final String BASE_URL = "http://172.17.8.100/";
    //   外网主机
    //public static final String BASE_URL = "http://mobile.bwstudent.com/";

    //6. 查询用户关注的影片列表
    public static final String MY_LIKE_MOVIE_GET = "movieApi/movie/v1/verify/findMoviePageList";

    // 13. 电影评论点赞
    public static final String CALL_LIKE_POPS = "movieApi/movie/v1/verify/movieCommentGreat";
    // 14.  根据影院ID查询该影院当前排期的电影列表
    public static final String CINEMA_ARRANGE_WORK_GET = "movieApi/movie/v1/findMovieListByCinemaId";
    // 15.  根据电影ID和影院ID查询电影排期列表
    public static final String MOVIE_SCHEDULE_GET = "movieApi/movie/v1/findMovieScheduleList";
    // 16.  根据电影ID查询当前排片该电影的影院列表
    public static final String IN_MOVIEID_CHOOSE_CINEMA_GET = "movieApi/movie/v1/findCinemasListByMovieId";

    //17. 购票下单
    public static final String BUY_MOVIE_TICKET = "movieApi/movie/v1/verify/buyMovieTicket";
    //18. 支付
    public static final String MOVIE_TICKET_PAY = "movieApi/movie/v1/verify/pay";
    // 19. 根据影院ID查询该影院下即将上映的电影列表
    public static final String CINEMA_DOEN_MOVIE_GET = "movieApi/movie/v1/findSoonMovieByCinemaId";


    /*
     * 二. 影院
     *   1.查询推荐影院
     *   2.查询附近影院
     *   3.查询电影信息明细
     *   4.根据电影名称模糊查询电影院
     *   5.查询用户关注的影院信息
     *   6.关注影院
     *   7.取消关注影院
     *   8.查询影院用户评论列表
     *   9.影院评论
     *   10.影院评论点赞
     * */
    //  1.推荐影院
    public static final String RECOMMEND_GET = "movieApi/cinema/v1/findRecommendCinemas";
    //  2. 附近影院
    public static final String NEARBY_GET = "movieApi/cinema/v1/findNearbyCinemas";
    //  3. 查询电影信息明细 / 电影详情
    public static final String INFORMATION_GET = "movieApi/cinema/v1/findCinemaInfo";
    //  4. 根据电影名称模糊查询电影院
    public static final String QUERY_CINEMA_GET = "movieApi/cinema/v1/findAllCinemas";
    //  5.  查询用户关注的影院信息
    public static final String MY_LIKE_CINEMA_GET = "movieApi/cinema/v1/verify/findCinemaPageList";
    //  6. 关注影院
    public static final String LOVECINEMA_GET = "movieApi/cinema/v1/verify/followCinema";
    //  7. 取消关注
    public static final String NOLOVECINEMA_GET = "movieApi/cinema/v1/verify/cancelFollowCinema";
    //  8. 查询影院用户评论列表
    public static final String QUERYCOMMENT_GET = "movieApi/cinema/v1/findAllCinemaComment";
    //  9.
    public static final String CALLCINEMA_POST = "";
    //  10. 影院评论点赞
    public static final String CALLWATCHFUL_POST = "movieApi/cinema/v1/verify/cinemaCommentGreat";



    /*
    *   我的页面
    *  */

    // 8.用户签到
    public static final String MY_USER_SIGNIN_GET = "movieApi/user/v1/verify/userSignIn";
    //9. 用户购票记录查询
    public static final String MY_FOODED_GET = "movieApi/user/v1/verify/findUserBuyTicketRecordList";


    //1. 意见反馈
    public static final String MY_BACE_MESSAGE_POST = "movieApi/tool/v1/verify/recordFeedBack";
    //2. 新版本
    public static final String MY_NEW_APP_GET = "movieApi/tool/v1/findNewVersion";
    // 3.系统消息
    public static final String MY_REMIND_GET = "movieApi/tool/v1/verify/findAllSysMsgList";
    // 4.读取状态修改
    public static final String MY_READ_STATUS_GET = "movieApi/tool/v1/verify/changeSysMsgStatus";
    // 5. 未读消息数量
    public static final String MY_UNREAD_NUM_GET = "movieApi/tool/v1/verify/findUnreadMessageCount";

}
