package com.davor.carpoolingapp.Models;

public class Vehicle {
    Integer id;
    String model;
    String brand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Vehicle() {
    }

    public Vehicle(Integer id, String model, String brand) {
        this.id = id;
        this.model = model;
        this.brand = brand;
    }
}
