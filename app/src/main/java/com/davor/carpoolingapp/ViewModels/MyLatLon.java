package com.davor.carpoolingapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyLatLon extends ViewModel {
    private MutableLiveData<Double> myLat = new MutableLiveData<>();
    private MutableLiveData<Double> myLon = new MutableLiveData<>();

    public MutableLiveData<Double> getMyLat() {
        return myLat;
    }

    public void setMyLat(Double value) {
        myLat.setValue(value);
    }

    public MutableLiveData<Double> getMyLon() {
        return myLon;
    }

    public void setMyLon(Double value) {
        myLon.setValue(value);
    }
}
