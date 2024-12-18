package com.davor.carpoolingapp.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Trip implements Serializable {
    Integer id;
    String driverId;
    User driver;
    List<User> attendees;
    String destination;
    Double latStart;
    Double lonStart;
    Double latFinish;
    Double lonFinish;
    LocalDateTime start;
    Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getLatStart() {
        return latStart;
    }

    public void setLatStart(Double latStart) {
        this.latStart = latStart;
    }

    public Double getLonStart() {
        return lonStart;
    }

    public void setLonStart(Double lonStart) {
        this.lonStart = lonStart;
    }

    public Double getLatFinish() {
        return latFinish;
    }

    public void setLatFinish(Double latFinish) {
        this.latFinish = latFinish;
    }

    public Double getLonFinish() {
        return lonFinish;
    }

    public void setLonFinish(Double lonFinish) {
        this.lonFinish = lonFinish;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public Trip(Integer id, String driverId, User driver, List<User> attendees, String destination, Double latStart, Double lonStart, Double latFinish, Double lonFinish, LocalDateTime start, Double price) {
        this.id = id;
        this.driverId = driverId;
        this.driver = driver;
        this.attendees = attendees;
        this.destination = destination;
        this.latStart = latStart;
        this.lonStart = lonStart;
        this.latFinish = latFinish;
        this.lonFinish = lonFinish;
        this.start = start;
        this.price = price;
    }

    public Trip() {
    }
}
