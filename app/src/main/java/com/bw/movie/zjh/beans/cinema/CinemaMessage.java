package com.bw.movie.zjh.beans.cinema;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/13 12:00
 * desc   :  进入推荐， 附近影院详情页
 * version: 1.0
 */
public class CinemaMessage {

    private String cinemaId;
    private String logo;
    private String name;
    private String Address;

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
