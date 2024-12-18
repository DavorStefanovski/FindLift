package com.davor.carpoolingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.davor.carpoolingapp.Models.Trip;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import android.Manifest;
import android.widget.TextView;

public class HomeFragment extends Fragment implements  RecyclerViewInterface {
    private static final Double EARTH_RADIUS_KM = 6371.0;
    ArrayList<Trip> trips;
    DBHelper dbHelper;
    RecyclerView recyclerView;
    EditText destinationFilter;
    SeekBar radius;
    HomeAdapter adapter;
    TextView radiusValue;
    FloatingActionButton dashboardButton;
    FloatingActionButton logoutButton;
    TextView appUsername;

    // Create an ActivityResultLauncher for the permission request
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new DBHelper(getActivity());
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radius = view.findViewById(R.id.radiusSeekBar);
        radius.setMin(0);
        radius.setMax(50);
        radius.setProgress(50);
        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                initializeTrips(destinationFilter.getText().toString(), Double.valueOf(progress));
                String value = String.valueOf(progress);
                radiusValue.setText(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        destinationFilter = view.findViewById(R.id.filterDestination);
        destinationFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String finalText = s.toString();
                initializeTrips(finalText, Double.valueOf(radius.getProgress()));

            }
        });
        dashboardButton = view.findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driverId = ((MainActivity) getActivity()).currentUser.getId();
                Intent intent = new Intent(getActivity(), DriverDashboard.class);
                intent.putExtra("driverId", driverId);
                startActivity(intent);
            }
        });
        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).signout();
            }
        });
        trips = new ArrayList<Trip>();
        recyclerView = view.findViewById(R.id.recyclerhome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter(getContext(),trips, this);
        recyclerView.setAdapter(adapter);
        radiusValue = view.findViewById(R.id.radiusValue);
        initializeTrips("",50.00);
        if(((MainActivity) getActivity()).currentUser.getDriver()){
            dashboardButton.setVisibility(View.VISIBLE);
        }
        appUsername = view.findViewById(R.id.appUsername);
        appUsername.setText("Hi "+((MainActivity) getActivity()).currentUser.getUsername());
    }
    private void initializeTrips(String destination, Double radius) {
//        dbHelper.insertExampleData();
        trips = dbHelper.getAllTripsWithDriverAndVehicle();
        trips = new ArrayList<>(trips.stream()
                .filter(trip -> {
                    // Check if destination is a substring of the trip's destination
                    boolean isDestinationMatch = (destination.isEmpty())?true:trip.getDestination().toLowerCase().contains(destination.toLowerCase());
                    // Calculate the distance between the given location and the trip's start location
                    Double distance = calDistance(((MainActivity) getActivity()).getMyLat(), ((MainActivity) getActivity()).getMyLon(), trip.getLatStart(), trip.getLonStart());
                    // Check if the distance is within the given radius
                    boolean isWithinRadius = distance <= radius;
                    // Return true only if both conditions are met
                    return isDestinationMatch && isWithinRadius;
                })
                .collect(Collectors.toList()));
        adapter.updateTrips(trips);
    }
    private Double calDistance(Double sLat, Double sLon, Double fLat, Double fLon) {
        // Convert latitude and longitude from degrees to radians
        double startLatRad = Math.toRadians(sLat);
        double startLonRad = Math.toRadians(sLon);
        double finalLatRad = Math.toRadians(fLat);
        double finalLonRad = Math.toRadians(fLon);

        // Haversine formula
        double deltaLat = finalLatRad - startLatRad;
        double deltaLon = finalLonRad - startLonRad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(startLatRad) * Math.cos(finalLatRad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance
        return EARTH_RADIUS_KM * c; // Distance in kilometers
    }

    @Override
    public void onItemClick(int position) {
        Trip trip = trips.get(position);
        Intent intent = new Intent(getActivity(),TripDetails.class);
        intent.putExtra("id", trip.getId());
        intent.putExtra("userId", ((MainActivity) getActivity()).currentUser.getId());
        startActivity(intent);
    }
}