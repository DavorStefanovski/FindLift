package com.davor.carpoolingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davor.carpoolingapp.Models.Trip;

import java.util.ArrayList;

public class DriverAdapter extends  RecyclerView.Adapter<DriverAdapter.MyViewHolder>{

    Context context;
    ArrayList<Trip> tripArrayList;

    public DriverAdapter(Context context, ArrayList<Trip> tripArrayList) {
        this.context = context;
        this.tripArrayList = tripArrayList;
    }
    public void updateTrips(ArrayList<Trip> newTrips) {
        this.tripArrayList = newTrips;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
    @NonNull
    @Override
    public DriverAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trip_driver_item,parent,false);
        return new DriverAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.MyViewHolder holder, int position) {
        Trip trip = tripArrayList.get(position);
        holder.destination.setText(trip.getDestination());
        holder.price.setText(String.format("You will get: %s$ per attendee", trip.getPrice().toString()));
        holder.startTime.setText(trip.getStart().toString());

    }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView destination, price, startTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destinationdriver);
            price = itemView.findViewById(R.id.pricedriver);
            startTime = itemView.findViewById(R.id.start_timedriver);
        }
    }
}
