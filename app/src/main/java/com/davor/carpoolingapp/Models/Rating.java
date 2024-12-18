package com.davor.carpoolingapp.Models;

public class Rating {
    String userId;
    String ratedUserId;
    Integer value;

    public Rating() {
    }

    public Rating(String userId, String ratedUserId, Integer value) {
        this.userId = userId;
        this.ratedUserId = ratedUserId;
        this.value = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(String ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
