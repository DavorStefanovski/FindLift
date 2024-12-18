package com.davor.carpoolingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.davor.carpoolingapp.Models.User;
import com.davor.carpoolingapp.ViewModels.MyLatLon;
import com.davor.carpoolingapp.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.davor.carpoolingapp.R;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Double myLat;
    Double myLon;
    public Double getMyLat(){
        return myLat;
    }
    public Double getMyLon(){
        return myLon;
    }
    FusedLocationProviderClient fusedLocationProviderClient;
    User currentUser;
    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        dbHelper = new DBHelper(getApplicationContext());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentUser = dbHelper.getUser(user.getUid());
        }
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        activityMainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.home){
                replaceFragment(new HomeFragment());
            }else if(item.getItemId()==R.id.mytrips){
                replaceFragment(new MyTripsFragment());
            }
            return true;
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLastLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Log.e("log1", "Location permission denied.");
            }
        }
    }
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            myLat = location.getLatitude();
                            myLon = location.getLongitude();
                            replaceFragment(new HomeFragment());
                            Log.d("Location", "Lat: " + myLat + ", Lon: " + myLon);
                        } else {
                            Log.e("Location", "No last known location available.");
                        }
                    })
                    .addOnFailureListener(e -> Log.e("LocationError", "Error getting location: " + e.getMessage()));
        } else {
            Log.e("Permission", "Location permission not granted.");
        }
    }
    public void signout(){
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}