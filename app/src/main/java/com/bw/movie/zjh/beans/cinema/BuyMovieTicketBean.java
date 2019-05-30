package com.bw.movie.zjh.beans.cinema;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/16 20:59
 * desc   :
 * version: 1.0
 */
public class BuyMovieTicketBean {

    /**
     * orderId : 20190516205458921
     * message : 下单成功
     * status : 0000
     */

    private String orderId;
    private String message;
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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
}
