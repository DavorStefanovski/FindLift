package com.davor.carpoolingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davor.carpoolingapp.Models.Trip;
import com.davor.carpoolingapp.Models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DriverDashboard extends AppCompatActivity {
    DBHelper dbHelper;
    User driver;
    LinearLayout vehicleLayout;
    Button addVehicle;
    TextView username, rating;
    RecyclerView recyclerView;
    ArrayList<Trip> trips;
    DriverAdapter adapter;
    Button addTripButton;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vehicleLayout = this.findViewById(R.id.vehicleDetailsLayout);
        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        String driverId = intent.getStringExtra("driverId");
        driver = dbHelper.getUser(driverId);
        if(driver.getVehicle()!=null){
            vehicleLayout.setVisibility(View.VISIBLE);
            TextView model = this.findViewById(R.id.vehicleModel);
            model.setText(String.format("Model: %s", driver.getVehicle().getModel()));
            TextView brand = this.findViewById(R.id.vehicleBrand);
            brand.setText(String.format("Brand: %s", driver.getVehicle().getBrand()));
            TextView title = this.findViewById(R.id.addVehicleTitle);
            title.setText("Change Vehicle");
        }
        username = this.findViewById(R.id.userName);
        username.setText(driver.getUsername());
        rating = this.findViewById(R.id.userRating);
        rating.setText(driver.getRating().toString());
        addVehicle = findViewById(R.id.addVehicleButton);
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newmodel = findViewById(R.id.newVehicleModel);
                EditText newbrand = findViewById(R.id.newVehicleBrand);
                Long vehicleId = dbHelper.addVehicle(newmodel.getText().toString(),newbrand.getText().toString());
                dbHelper.updateUser(driver.getId(),driver.getUsername(),driver.getEmail(),driver.getDriver(), Math.toIntExact(vehicleId));
                Intent intent1 = new Intent(getApplicationContext(), DriverDashboard.class);
                intent1.putExtra("driverId",driverId);
                startActivity(intent1);
                finish();

            }
        });
        addTripButton = findViewById(R.id.addTripButton);
        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), AddTrip.class);
                intent1.putExtra("driverId",driverId);
                startActivity(intent1);
            }
        });
        recyclerView = this.findViewById(R.id.recyclerdriver);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new DriverAdapter(getApplicationContext(), (ArrayList<Trip>) driver.getTripsDriving());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}