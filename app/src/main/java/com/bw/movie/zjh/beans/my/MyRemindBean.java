package com.bw.movie.zjh.beans.my;

import java.util.List;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/23 20:53
 * desc   :
 * version: 1.0
 */
public class MyRemindBean {

    /**
     * result : [{"content":"您已购买电影票,请尽快完成支付,以免影响到您的观影","id":17768,"pushTime":1558085645000,"status":0,"title":"系统通知","userId":12580},{"content":"您已购买电影票,请尽快完成支付,以免影响到您的观影","id":17741,"pushTime":1558011299000,"status":0,"title":"系统通知","userId":12580},{"content":"感谢您注册维度账号,小维希望您使用我们的产品能够获得快乐~","id":15453,"pushTime":1555123368000,"status":0,"title":"系统通知","userId":12580}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * content : 您已购买电影票,请尽快完成支付,以免影响到您的观影
         * id : 17768
         * pushTime : 1558085645000
         * status : 0
         * title : 系统通知
         * userId : 12580
         */

        private String content;
        private int id;
        private long pushTime;
        private int status;
        private String title;
        private int userId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getPushTime() {
            return pushTime;
        }

        public void setPushTime(long pushTime) {
            this.pushTime = pushTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
