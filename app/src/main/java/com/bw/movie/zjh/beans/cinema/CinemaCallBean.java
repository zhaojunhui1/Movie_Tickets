package com.bw.movie.zjh.beans.cinema;

import java.util.List;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 20:44
 * desc   :
 * version: 1.0
 */
public class CinemaCallBean {

    /**
     * result : [{"commentContent":"好","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-22/20190222170828.unknown","commentId":208,"commentTime":1552542905000,"commentUserId":10875,"commentUserName":"八维123","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"好","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-22/20190222170828.unknown","commentId":207,"commentTime":1552541935000,"commentUserId":10875,"commentUserName":"八维123","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"好","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-22/20190222170828.unknown","commentId":206,"commentTime":1552480271000,"commentUserId":10875,"commentUserName":"八维123","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"好","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-22/20190222170828.unknown","commentId":205,"commentTime":1552480249000,"commentUserId":10875,"commentUserName":"八维123","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"好","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-22/20190222170828.unknown","commentId":204,"commentTime":1552480063000,"commentUserId":10875,"commentUserName":"八维123","greatNum":0,"hotComment":0,"isGreat":0}]
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
         * commentContent : 好
         * commentHeadPic : http://172.17.8.100/images/movie/head_pic/2019-02-22/20190222170828.unknown
         * commentId : 208
         * commentTime : 1552542905000
         * commentUserId : 10875
         * commentUserName : 八维123
         * greatNum : 0
         * hotComment : 0
         * isGreat : 0
         */

        private String commentContent;
        private String commentHeadPic;
        private int commentId;
        private long commentTime;
        private int commentUserId;
        private String commentUserName;
        private int greatNum;
        private int hotComment;
        private int isGreat;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getHotComment() {
            return hotComment;
        }

        public void setHotComment(int hotComment) {
            this.hotComment = hotComment;
        }

        public int getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(int isGreat) {
            this.isGreat = isGreat;
        }
    }
}
