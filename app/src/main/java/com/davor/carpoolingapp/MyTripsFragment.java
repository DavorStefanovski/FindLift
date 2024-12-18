package com.davor.carpoolingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davor.carpoolingapp.Models.Attendee;
import com.davor.carpoolingapp.Models.Trip;

import java.util.ArrayList;
import java.util.List;


public class MyTripsFragment extends Fragment implements RecyclerViewInterface{
    ArrayList<Trip> trips;
    DBHelper dbHelper;
    RecyclerView recyclerView;
    HomeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_trips, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        trips = new ArrayList<>();
        List<Attendee> attendees = dbHelper.getAllAttendeesByUserId(((MainActivity) getActivity()).currentUser.getId());
        for(Attendee a : attendees){
            trips.add(a.getTrip());
        }
        recyclerView = view.findViewById(R.id.recyclertrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter(getContext(),trips, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}