package com.davor.carpoolingapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.davor.carpoolingapp.Models.Trip;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDateTime;

public class AddTrip extends AppCompatActivity implements OnMapReadyCallback {
    Button button;
    GoogleMap myMap;
    int yeari, monthi, dayOfMonthi;
    LocalDateTime localDateTime;
    Double startLat, endLat, startLon, endLon;
    Button submitButton;
    DBHelper dbHelper;
    EditText destination, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_trip);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        String driverId = intent.getStringExtra("driverId");
        button = findViewById(R.id.selectStartTimeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addTrip(driverId,destination.getText().toString(),startLat,startLon,endLat,endLon,localDateTime.toString(),Double.valueOf(price.getText().toString()));
                finish();
            }
        });
        destination = findViewById(R.id.destinationInput);
        price = findViewById(R.id.priceInput);

    }
    private void openDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yeari=year;
                monthi=month;
                dayOfMonthi=dayOfMonth;
                openDialogTime();
            }
        }, 2024, 0, 1);
        dialog.show();
    }
    private void openDialogTime(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                localDateTime = LocalDateTime.of(yeari, monthi+1, dayOfMonthi, hourOfDay, minute);
            }
        }, 00, 00, true);
        dialog.show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Set a listener for map clicks
        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            private boolean isStartLocationSet = false;
            private Marker startMarker = null;
            private Marker endMarker = null;

            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (!isStartLocationSet) {
                    // Set the starting location
                    startLat = latLng.latitude;
                    startLon = latLng.longitude;
                    isStartLocationSet = true;

                    // Add a marker for the starting location
                    if (startMarker != null) {
                        startMarker.remove(); // Remove the previous marker if it exists
                    }
                    startMarker = myMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Start Location")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); // Green marker
                } else {
                    // Set the ending location
                    endLat = latLng.latitude;
                    endLon = latLng.longitude;
                    isStartLocationSet = false;

                    // Add a marker for the ending location
                    if (endMarker != null) {
                        endMarker.remove(); // Remove the previous marker if it exists
                    }
                    endMarker = myMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("End Location")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); // Red marker
                }
            }
        });
    }


}