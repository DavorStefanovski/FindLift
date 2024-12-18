package com.davor.carpoolingapp.Models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    String id;
    String username;
    String email;
    Integer rating;
    Boolean isDriver;
    Integer vehicleId;
    Vehicle vehicle;
    List<Trip> trips;
    List<Trip> tripsDriving;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = mail;
    }

    public Boolean getDriver() {
        return isDriver;
    }

    public void setDriver(Boolean driver) {
        isDriver = driver;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getTripsDriving() {
        return tripsDriving;
    }

    public void setTripsDriving(List<Trip> tripsDriving) {
        this.tripsDriving = tripsDriving;
    }

    public User() {
    }

    public User(String id, String username, String email, Integer rating, Boolean isDriver, Integer vehicleId, Vehicle vehicle, List<Trip> trips, List<Trip> tripsDriving) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.isDriver = isDriver;
        this.vehicleId = vehicleId;
        this.vehicle = vehicle;
        this.trips = trips;
        this.tripsDriving = tripsDriving;
    }
}
