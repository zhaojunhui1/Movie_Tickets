package com.bw.movie.fmk.bean;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/23 11:22
 * @Description:
 */
public class JiLuBean {

    /**
     * result : [{"amount":1,"beginTime":"17:05:00","cinemaName":"大观楼电影院","createTime":1558581115000,"endTime":"19:03:00","id":3023,"movieName":"西虹市首富","orderId":"20190523111155499","price":0.13,"screeningHall":"3厅","status":1,"userId":12839},{"amount":1,"beginTime":"19:20:00","cinemaName":"大观楼电影院","createTime":1558581092000,"endTime":"21:18:00","id":3022,"movieName":"西虹市首富","orderId":"20190523111132468","price":0.13,"screeningHall":"2号厅","status":1,"userId":12839},{"amount":1,"beginTime":"19:20:00","cinemaName":"大观楼电影院","createTime":1558581089000,"endTime":"21:18:00","id":3021,"movieName":"西虹市首富","orderId":"20190523111129454","price":0.13,"screeningHall":"2号厅","status":1,"userId":12839},{"amount":1,"beginTime":"19:20:00","cinemaName":"大观楼电影院","createTime":1558577016000,"endTime":"21:18:00","id":3015,"movieName":"西虹市首富","orderId":"20190523100335994","price":0.13,"screeningHall":"2号厅","status":1,"userId":12839},{"amount":1,"beginTime":"17:05:00","cinemaName":"大观楼电影院","createTime":1558576994000,"endTime":"19:03:00","id":3014,"movieName":"西虹市首富","orderId":"20190523100314591","price":0.13,"screeningHall":"3厅","status":1,"userId":12839}]
     * message : 请求成功
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
         * amount : 1
         * beginTime : 17:05:00
         * cinemaName : 大观楼电影院
         * createTime : 1558581115000
         * endTime : 19:03:00
         * id : 3023
         * movieName : 西虹市首富
         * orderId : 20190523111155499
         * price : 0.13
         * screeningHall : 3厅
         * status : 1
         * userId : 12839
         */

        private int amount;
        private String beginTime;
        private String cinemaName;
        private long createTime;
        private String endTime;
        private int id;
        private String movieName;
        private String orderId;
        private double price;
        private String screeningHall;
        private int status;
        private int userId;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
