package com.bw.movie.zjh.beans.cinema;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 19:03
 * desc   :  影院详情
 * version: 1.0
 */
public class DetailsDetailsBean {

    /**
     * result : {"address":"西城区前门大街大栅栏街36号","businessHoursContent":"星期一 至 星期日   早07:00:00 - 晚06:30:00","commentTotal":0,"distance":0,"followCinema":2,"id":2,"logo":"http://172.17.8.100/images/movie/logo/dgl.jpg","name":"大观楼电影院","phone":"010-63083312","vehicleRoute":"公交线路：空调52路 → k3路公交，全程约12.8公里\r\n\r\n1、从大观楼步行约460米,到达大观楼站\r\n\r\n2、乘坐空调52路,经过6站, 到达艺术剧院站（也可乘坐124路）\r\n\r\n3、乘坐k3路公交,经过12站, 到达官渡区医院站（也可乘坐26路）\r\n\r\n4、步行约350米,到达关街利民店"}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
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

    public static class ResultBean {
        /**
         * address : 西城区前门大街大栅栏街36号
         * businessHoursContent : 星期一 至 星期日   早07:00:00 - 晚06:30:00
         * commentTotal : 0
         * distance : 0
         * followCinema : 2
         * id : 2
         * logo : http://172.17.8.100/images/movie/logo/dgl.jpg
         * name : 大观楼电影院
         * phone : 010-63083312
         * vehicleRoute : 公交线路：空调52路 → k3路公交，全程约12.8公里

         1、从大观楼步行约460米,到达大观楼站

         2、乘坐空调52路,经过6站, 到达艺术剧院站（也可乘坐124路）

         3、乘坐k3路公交,经过12站, 到达官渡区医院站（也可乘坐26路）

         4、步行约350米,到达关街利民店
         */

        private String address;
        private String businessHoursContent;
        private int commentTotal;
        private int distance;
        private int followCinema;
        private int id;
        private String logo;
        private String name;
        private String phone;
        private String vehicleRoute;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBusinessHoursContent() {
            return businessHoursContent;
        }

        public void setBusinessHoursContent(String businessHoursContent) {
            this.businessHoursContent = businessHoursContent;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(int followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVehicleRoute() {
            return vehicleRoute;
        }

        public void setVehicleRoute(String vehicleRoute) {
            this.vehicleRoute = vehicleRoute;
        }
    }
}
