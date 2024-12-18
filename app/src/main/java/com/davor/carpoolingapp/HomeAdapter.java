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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Trip> tripArrayList;

    public HomeAdapter(Context context, ArrayList<Trip> tripArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.tripArrayList = tripArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    public void updateTrips(ArrayList<Trip> newTrips) {
        this.tripArrayList = newTrips;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trip_item,parent,false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trip trip = tripArrayList.get(position);
        holder.destination.setText(trip.getDestination());
        holder.username.setText(String.format("Driver: %s", trip.getDriver().getUsername()));
        holder.rating.setText(String.format(" Rating: %s", trip.getDriver().getRating().toString()));
        holder.price.setText(String.format("Price: %s$", trip.getPrice().toString()));
        holder.startTime.setText(trip.getStart().toString());

    }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView destination, username, rating, price, startTime;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            destination = itemView.findViewById(R.id.destination);
            username = itemView.findViewById(R.id.username);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
            startTime = itemView.findViewById(R.id.start_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
