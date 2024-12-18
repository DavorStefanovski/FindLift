package com.davor.carpoolingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.davor.carpoolingapp.Models.Attendee;
import com.davor.carpoolingapp.Models.Trip;
import com.davor.carpoolingapp.Models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

public class TripDetails extends AppCompatActivity implements OnMapReadyCallback {
    Trip trip;
    DBHelper dbHelper;
    Button join, ratebtn;
    EditText rate;
    Integer id;
    String userId;
    LinearLayout rateLayout;
    Boolean itsMe;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        userId =  intent.getStringExtra("userId");
        trip = dbHelper.getTripById(id);

        // Initialize Views
        TextView tvDestination = findViewById(R.id.tvDestination);
        TextView tvDriver = findViewById(R.id.tvDriver);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvStartTime = findViewById(R.id.tvStartTime);
        TextView tvAttendees = findViewById(R.id.tvAttendees);
        TextView tvModel = findViewById(R.id.tvModel);
        TextView tvBrand = findViewById(R.id.tvBrand);

        // Populate Data

        tvDestination.setText("Destination: " + trip.getDestination());
        tvDriver.setText("Driver: " + trip.getDriver().getUsername());
        tvPrice.setText("Price: $" + trip.getPrice());
        tvStartTime.setText("Start Time: " + trip.getStart().toString());
        tvModel.setText("Model: "+trip.getDriver().getVehicle().getModel());
        tvBrand.setText("Brand: "+trip.getDriver().getVehicle().getBrand());
        String attendees = "";
        for(User a : trip.getAttendees()){
            attendees = attendees.concat(a.getUsername()+" ");
        }
        tvAttendees.setText("Attendees: " + attendees);
        itsMe = trip.getDriver().getId().equals(userId);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        boolean joined = false;
        for(User u: trip.getAttendees()){
            if(u.getId().equals(userId))
                joined = true;
        }
        rateLayout = findViewById(R.id.rateLayout);
        join = findViewById(R.id.btnJoin);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addAttendee(userId, trip.getId());
                finish();
                startActivity(getIntent());
            }
        });
        rate = findViewById(R.id.etRating);
        ratebtn = findViewById(R.id.btnSubmitRating);
        ratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate.getText().toString().isEmpty()){
                    Toast.makeText(TripDetails.this, "Please enter a rating value", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHelper.insertRating(userId,trip.getDriver().getId(),Integer.parseInt(rate.getText().toString()));
                finish();
                startActivity(getIntent());
            }
        });
        if(!itsMe){
        if(joined){
            rateLayout.setVisibility(View.VISIBLE);
            join.setVisibility(View.GONE);
        }else{
            rateLayout.setVisibility(View.GONE);
            join.setVisibility(View.VISIBLE);
        }}else{
            rateLayout.setVisibility(View.GONE);
            join.setVisibility(View.GONE);
        }


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng startPoint = new LatLng(trip.getLatStart(), trip.getLonStart());
        LatLng endPoint = new LatLng(trip.getLatFinish(), trip.getLonFinish());

        // Add markers with custom colors
        googleMap.addMarker(new MarkerOptions()
                .position(startPoint)
                .title("Start Point")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); // Green marker

        googleMap.addMarker(new MarkerOptions()
                .position(endPoint)
                .title("End Point")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); // Red marker

        // Draw a line (polyline) between the two points
        googleMap.addPolyline(new PolylineOptions()
                .add(startPoint, endPoint)
                .width(5) // Width of the line
                .color(android.graphics.Color.BLUE)); // Color of the line

        // Move the camera to the start point and adjust the zoom level
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 10));
    }

}