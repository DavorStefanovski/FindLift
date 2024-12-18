package com.davor.carpoolingapp.Models;

import java.io.Serializable;

public class Attendee implements Serializable {
    String userId;
    User user;
    Integer tripId;
    Trip trip;

    public Attendee() {
    }

    public Attendee(String userId, User user, Integer tripId, Trip trip) {
        this.userId = userId;
        this.user = user;
        this.tripId = tripId;
        this.trip = trip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
