package com.davor.carpoolingapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.davor.carpoolingapp.Models.Attendee;
import com.davor.carpoolingapp.Models.Rating;
import com.davor.carpoolingapp.Models.Trip;
import com.davor.carpoolingapp.Models.User;
import com.davor.carpoolingapp.Models.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CarpoolingApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USER = "User";
    private static final String TABLE_VEHICLE = "Vehicle";
    private static final String TABLE_TRIP = "Trip";
    private static final String TABLE_ATTENDEE = "Attendee";
    private static final String TABLE_RATING = "Rating";

    // User table columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_IS_DRIVER = "isDriver";
    private static final String COLUMN_USER_VEHICLE_ID = "vehicleId";

    // Vehicle table columns
    private static final String COLUMN_VEHICLE_ID = "id";
    private static final String COLUMN_VEHICLE_MODEL = "model";
    private static final String COLUMN_VEHICLE_BRAND = "brand";

    // Trip table columns
    private static final String COLUMN_TRIP_ID = "id";
    private static final String COLUMN_TRIP_DRIVER_ID = "driverId";
    private static final String COLUMN_TRIP_DESTINATION = "destination";
    private static final String COLUMN_TRIP_LAT_START = "latStart";
    private static final String COLUMN_TRIP_LON_START = "lonStart";
    private static final String COLUMN_TRIP_LAT_FINISH = "latFinish";
    private static final String COLUMN_TRIP_LON_FINISH = "lonFinish";
    private static final String COLUMN_TRIP_START = "start";
    private static final String COLUMN_TRIP_PRICE = "price";

    // Attendee table columns
    private static final String COLUMN_ATTENDEE_USER_ID = "userId";
    private static final String COLUMN_ATTENDEE_TRIP_ID = "tripId";
    // Rating table columns
    private static final String COLUMN_RATING_USER_ID = "userId";
    private static final String COLUMN_RATING_RATED_USER_ID = "ratedUserId";
    private static final String COLUMN_RATING_VALUE = "value";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_USERNAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_IS_DRIVER + " INTEGER, " +
                COLUMN_USER_VEHICLE_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USER_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLE + "(" + COLUMN_VEHICLE_ID + "))");

        // Create Vehicle table
        db.execSQL("CREATE TABLE " + TABLE_VEHICLE + " (" +
                COLUMN_VEHICLE_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_VEHICLE_MODEL + " TEXT, " +
                COLUMN_VEHICLE_BRAND + " TEXT)");

        // Create Trip table
        db.execSQL("CREATE TABLE " + TABLE_TRIP + " (" +
                COLUMN_TRIP_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TRIP_DRIVER_ID + " TEXT, " +
                COLUMN_TRIP_DESTINATION + " TEXT, " +
                COLUMN_TRIP_LAT_START + " REAL, " +
                COLUMN_TRIP_LON_START + " REAL, " +
                COLUMN_TRIP_LAT_FINISH + " REAL, " +
                COLUMN_TRIP_LON_FINISH + " REAL, " +
                COLUMN_TRIP_START + " TEXT, " +
                COLUMN_TRIP_PRICE + " REAL, " +
                "FOREIGN KEY(" + COLUMN_TRIP_DRIVER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");

        // Create Attendee table
        db.execSQL("CREATE TABLE " + TABLE_ATTENDEE + " (" +
                COLUMN_ATTENDEE_USER_ID + " TEXT, " +
                COLUMN_ATTENDEE_TRIP_ID + " INTEGER, " +
                "PRIMARY KEY (" + COLUMN_ATTENDEE_USER_ID + ", " + COLUMN_ATTENDEE_TRIP_ID + "), " +
                "FOREIGN KEY(" + COLUMN_ATTENDEE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_ATTENDEE_TRIP_ID + ") REFERENCES " + TABLE_TRIP + "(" + COLUMN_TRIP_ID + "))");

        // Create Rating table
        db.execSQL("CREATE TABLE " + TABLE_RATING + " (" +
                COLUMN_RATING_USER_ID + " TEXT, " +
                COLUMN_RATING_RATED_USER_ID + " TEXT, " +
                COLUMN_RATING_VALUE + " INTEGER, " +
                "PRIMARY KEY (" + COLUMN_RATING_USER_ID + ", " + COLUMN_RATING_RATED_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_RATING_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_RATING_RATED_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE);
        onCreate(db);
    }

    // Insert example data
    public void insertExampleData() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert into Vehicle
        ContentValues vehicleValues = new ContentValues();
        vehicleValues.put(COLUMN_VEHICLE_ID, 1);
        vehicleValues.put(COLUMN_VEHICLE_MODEL, "Civic");
        vehicleValues.put(COLUMN_VEHICLE_BRAND, "Honda");
        db.insert(TABLE_VEHICLE, null, vehicleValues);

        // Insert into User
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_USER_ID, "hYcWYh2eKkUxeb8C5RkfMI9Xq0g1");
        userValues.put(COLUMN_USER_USERNAME, "zoki");
        userValues.put(COLUMN_USER_EMAIL, "zoki@mail.com");
        userValues.put(COLUMN_USER_IS_DRIVER, 1);
        userValues.put(COLUMN_USER_VEHICLE_ID, 1);
        db.insert(TABLE_USER, null, userValues);

        // Insert into Trip
        ContentValues tripValues = new ContentValues();
        tripValues.put(COLUMN_TRIP_ID, 1);
        tripValues.put(COLUMN_TRIP_DRIVER_ID, "hYcWYh2eKkUxeb8C5RkfMI9Xq0g1");
        tripValues.put(COLUMN_TRIP_DESTINATION, "City Center");
        tripValues.put(COLUMN_TRIP_LAT_START, 42.054908);
        tripValues.put(COLUMN_TRIP_LON_START,  21.458220);
        tripValues.put(COLUMN_TRIP_LAT_FINISH, 50);
        tripValues.put(COLUMN_TRIP_LON_FINISH, 50);
        tripValues.put(COLUMN_TRIP_START, "2024-12-08T10:00:00");
        tripValues.put(COLUMN_TRIP_PRICE, 10.5);
        db.insert(TABLE_TRIP, null, tripValues);

        // Insert into Attendee
        ContentValues attendeeValues = new ContentValues();
        attendeeValues.put(COLUMN_ATTENDEE_USER_ID, "tode123");
        attendeeValues.put(COLUMN_ATTENDEE_TRIP_ID, 1);
        db.insert(TABLE_ATTENDEE, null, attendeeValues);

        db.close();
    }
    // CRUD Operations for User
    public long addUser(String id, String username, String email, boolean isDriver, Integer vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, id);
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_IS_DRIVER, isDriver ? 1 : 0);
        values.put(COLUMN_USER_VEHICLE_ID, vehicleId);
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result;
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public int updateUser(String id, String username, String email, boolean isDriver, Integer vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_IS_DRIVER, isDriver ? 1 : 0);
        values.put(COLUMN_USER_VEHICLE_ID, vehicleId);
        int rowsAffected = db.update(TABLE_USER, values, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Get a user by ID
    @RequiresApi(api = Build.VERSION_CODES.O)
    public User getUser(String id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{id});

            if (cursor != null && cursor.moveToFirst()) {
                User user = new User(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                        null,
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_IS_DRIVER)) == 1,
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_VEHICLE_ID)),
                        null, // Vehicle object can be fetched separately
                        null,  // List of Trips can be fetched separately
                        null // List of trips that the user is driving
                );

                if(user.getDriver()){
                    user.setTripsDriving(getAllTripsByDriverId(id));
                    List<Rating> ratings = this.getRatingsByRatedUserId(id);
                    int rating = 0;
                    for(Rating r : ratings){
                        rating += r.getValue();
                    }
                    rating=rating/((ratings.size()==0)?1:ratings.size());
                    user.setRating(rating);
                    if(user.getVehicleId()!=null){
                        user.setVehicle(getVehicleById(user.getVehicleId()));
                    }
                }

                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }


    // CRUD Operations for Vehicle
    public long addVehicle( String brand, String model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VEHICLE_MODEL, model);
        values.put(COLUMN_VEHICLE_BRAND, brand);
        long result = db.insert(TABLE_VEHICLE, null, values);
        db.close();
        return result;
    }
    public Vehicle getVehicleById(Integer id) {
        Vehicle vehicle = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_VEHICLE + " WHERE " + COLUMN_VEHICLE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
             vehicle = new Vehicle(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_BRAND))
            );
        }
        cursor.close();
        db.close();
        return vehicle;
    }
    public List<String> getAllVehicles() {
        List<String> vehicles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VEHICLE, null);
        if (cursor.moveToFirst()) {
            do {
                vehicles.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vehicles;
    }

    public int updateVehicle(int id, String model, String brand) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VEHICLE_MODEL, model);
        values.put(COLUMN_VEHICLE_BRAND, brand);
        int rowsAffected = db.update(TABLE_VEHICLE, values, COLUMN_VEHICLE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteVehicle(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLE, COLUMN_VEHICLE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // CRUD Operations for Trip
    public long addTrip(String driverId, String destination, double latStart, double lonStart, double latFinish, double lonFinish, String start, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_DRIVER_ID, driverId);
        values.put(COLUMN_TRIP_DESTINATION, destination);
        values.put(COLUMN_TRIP_LAT_START, latStart);
        values.put(COLUMN_TRIP_LON_START, lonStart);
        values.put(COLUMN_TRIP_LAT_FINISH, latFinish);
        values.put(COLUMN_TRIP_LON_FINISH, lonFinish);
        values.put(COLUMN_TRIP_START, start);
        values.put(COLUMN_TRIP_PRICE, price);
        long result = db.insert(TABLE_TRIP, null, values);
        db.close();
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Trip> getAllTripsByDriverId(String id) {
        List<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TRIP + " WHERE " + COLUMN_TRIP_DRIVER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor.moveToFirst()) {
            do {
                // Use the constructor to create a new Trip
                Integer tripId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRIP_ID));
                String driverId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DRIVER_ID));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DESTINATION));
                Double latStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_START));
                Double lonStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_START));
                Double latFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_FINISH));
                Double lonFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_FINISH));
                String startDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_START));
                LocalDateTime start = null;
                if (startDateTime != null) {
                    start = LocalDateTime.parse(startDateTime);
                }
                Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_PRICE));

                // Create a new Trip object using the constructor
                Trip trip = new Trip(tripId, driverId,null,null, destination, latStart, lonStart, latFinish, lonFinish, start, price);
                trips.add(trip);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return trips;
    }

    @SuppressLint("NewApi")
    public ArrayList<Trip> getAllTripsWithDriverAndVehicle() {
        ArrayList<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "t." + COLUMN_TRIP_ID + " AS trip_id, " +
                "t." + COLUMN_TRIP_DRIVER_ID + ", " +
                "t." + COLUMN_TRIP_DESTINATION + ", " +
                "t." + COLUMN_TRIP_LAT_START + ", " +
                "t." + COLUMN_TRIP_LON_START + ", " +
                "t." + COLUMN_TRIP_LAT_FINISH + ", " +
                "t." + COLUMN_TRIP_LON_FINISH + ", " +
                "t." + COLUMN_TRIP_START + ", " +
                "t." + COLUMN_TRIP_PRICE + ", " +
                "u." + COLUMN_USER_ID + " AS user_id, " +
                "u." + COLUMN_USER_USERNAME + ", " +
                "u." + COLUMN_USER_EMAIL + ", " +
                "u." + COLUMN_USER_IS_DRIVER + ", " +
                "u." + COLUMN_USER_VEHICLE_ID + ", " +
                "v." + COLUMN_VEHICLE_ID + " AS vehicle_id, " +
                "v." + COLUMN_VEHICLE_MODEL + ", " +
                "v." + COLUMN_VEHICLE_BRAND +
                " FROM " + TABLE_TRIP + " t " +
                "JOIN " + TABLE_USER + " u ON t." + COLUMN_TRIP_DRIVER_ID + " = u." + COLUMN_USER_ID + " " +
                "JOIN " + TABLE_VEHICLE + " v ON u." + COLUMN_USER_VEHICLE_ID + " = v." + COLUMN_VEHICLE_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Extract Trip details
                Integer tripId = cursor.getInt(cursor.getColumnIndexOrThrow("trip_id"));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DESTINATION));
                Double latStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_START));
                Double lonStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_START));
                Double latFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_FINISH));
                Double lonFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_FINISH));
                String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_START));
                Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_PRICE));

                // Create Trip object and use setters
                Trip trip = new Trip();
                trip.setId(tripId);
                trip.setDestination(destination);
                trip.setLatStart(latStart);
                trip.setLonStart(lonStart);
                trip.setLatFinish(latFinish);
                trip.setLonFinish(lonFinish);
                trip.setStart(LocalDateTime.parse(start)); // Assuming the start date is in ISO format
                trip.setPrice(price);

                // Create User (Driver) object and use setters
                User driver = new User();
                driver.setId(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                driver.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME)));
                driver.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
                List<Rating> ratings = this.getRatingsByRatedUserId(driver.getId());
                int rating = 0;
                for(Rating r : ratings){
                    rating+=r.getValue();
                }
                rating=rating/((ratings.size()==0)?1:ratings.size());
                driver.setRating(rating);
                driver.setDriver(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_IS_DRIVER)) == 1);
                driver.setVehicleId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_VEHICLE_ID)));

                // Create Vehicle object for Driver and use setters
                Vehicle vehicle = new Vehicle();
                vehicle.setId(cursor.getInt(cursor.getColumnIndexOrThrow("vehicle_id")));
                vehicle.setModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)));
                vehicle.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_BRAND)));

                // Set Driver and Vehicle to the Trip
                driver.setVehicle(vehicle);
                trip.setDriver(driver);

                trips.add(trip);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return trips;
    }
    @SuppressLint("NewApi")
    public Trip getTripById(int tripId) {
        Trip trip = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "t." + COLUMN_TRIP_ID + " AS trip_id, " +
                "t." + COLUMN_TRIP_DRIVER_ID + ", " +
                "t." + COLUMN_TRIP_DESTINATION + ", " +
                "t." + COLUMN_TRIP_LAT_START + ", " +
                "t." + COLUMN_TRIP_LON_START + ", " +
                "t." + COLUMN_TRIP_LAT_FINISH + ", " +
                "t." + COLUMN_TRIP_LON_FINISH + ", " +
                "t." + COLUMN_TRIP_START + ", " +
                "t." + COLUMN_TRIP_PRICE + ", " +
                "u." + COLUMN_USER_ID + " AS user_id, " +
                "u." + COLUMN_USER_USERNAME + ", " +
                "u." + COLUMN_USER_EMAIL + ", " +
                "u." + COLUMN_USER_IS_DRIVER + ", " +
                "u." + COLUMN_USER_VEHICLE_ID + ", " +
                "v." + COLUMN_VEHICLE_ID + " AS vehicle_id, " +
                "v." + COLUMN_VEHICLE_MODEL + ", " +
                "v." + COLUMN_VEHICLE_BRAND +
                " FROM " + TABLE_TRIP + " t " +
                "JOIN " + TABLE_USER + " u ON t." + COLUMN_TRIP_DRIVER_ID + " = u." + COLUMN_USER_ID + " " +
                "JOIN " + TABLE_VEHICLE + " v ON u." + COLUMN_USER_VEHICLE_ID + " = v." + COLUMN_VEHICLE_ID +
                " WHERE t." + COLUMN_TRIP_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tripId)});

        if (cursor.moveToFirst()) {
            // Extract Trip details
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DESTINATION));
            Double latStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_START));
            Double lonStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_START));
            Double latFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_FINISH));
            Double lonFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_FINISH));
            String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_START));
            Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_PRICE));

            // Create Trip object and use setters
            trip = new Trip();
            trip.setId(tripId);
            trip.setDestination(destination);
            trip.setLatStart(latStart);
            trip.setLonStart(lonStart);
            trip.setLatFinish(latFinish);
            trip.setLonFinish(lonFinish);
            trip.setStart(LocalDateTime.parse(start)); // Assuming the start date is in ISO format
            trip.setPrice(price);

            // Create User (Driver) object and use setters
            User driver = new User();
            driver.setId(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
            driver.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME)));
            driver.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
            List<Rating> ratings = this.getRatingsByRatedUserId(driver.getId());
            int rating = 0;
            for(Rating r : ratings){
                rating+=r.getValue();
            }
            rating=rating/((ratings.size()==0)?1:ratings.size());
            driver.setRating(rating);
            driver.setDriver(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_IS_DRIVER)) == 1);
            driver.setVehicleId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_VEHICLE_ID)));

            // Create Vehicle object for Driver and use setters
            Vehicle vehicle = new Vehicle();
            vehicle.setId(cursor.getInt(cursor.getColumnIndexOrThrow("vehicle_id")));
            vehicle.setModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)));
            vehicle.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_BRAND)));

            // Set Driver and Vehicle to the Trip
            driver.setVehicle(vehicle);
            trip.setDriver(driver);

            List<User> attendees = getUsersByTripId(tripId);
            trip.setAttendees(attendees);
        }

        cursor.close();
        db.close();
        return trip;
    }




    public int updateTrip(int id, String driverId, String destination, double latStart, double lonStart, double latFinish, double lonFinish, String start, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_DRIVER_ID, driverId);
        values.put(COLUMN_TRIP_DESTINATION, destination);
        values.put(COLUMN_TRIP_LAT_START, latStart);
        values.put(COLUMN_TRIP_LON_START, lonStart);
        values.put(COLUMN_TRIP_LAT_FINISH, latFinish);
        values.put(COLUMN_TRIP_LON_FINISH, lonFinish);
        values.put(COLUMN_TRIP_START, start);
        values.put(COLUMN_TRIP_PRICE, price);
        int rowsAffected = db.update(TABLE_TRIP, values, COLUMN_TRIP_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteTrip(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIP, COLUMN_TRIP_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // CRUD Operations for Attendee
    @SuppressLint("NewApi")
    public List<User> getUsersByTripId(int tripId) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get user IDs for the given trip ID
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ATTENDEE + " WHERE " + COLUMN_ATTENDEE_TRIP_ID + " = ?",
                new String[]{String.valueOf(tripId)}
        );

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATTENDEE_USER_ID));

                // Fetch the user details using userId
                 User user = this.getUser(userId);
                if (user != null) {
                    users.add(user);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return users;
    }
    public long addAttendee(String userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTENDEE_USER_ID, userId);
        values.put(COLUMN_ATTENDEE_TRIP_ID, tripId);
        long result = db.insert(TABLE_ATTENDEE, null, values);
        db.close();
        return result;
    }

    public List<Attendee> getAllAttendeesByUserId(String userId) {
        List<Attendee> attendees = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ATTENDEE  + " WHERE " + COLUMN_ATTENDEE_USER_ID + " =?", new String[]{userId});
        if (cursor.moveToFirst()) {
            do {
                Attendee attendee = new Attendee(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATTENDEE_USER_ID)),null,cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ATTENDEE_TRIP_ID)),null);
                attendee.setTrip(this.getTripById(attendee.getTripId()));
                attendees.add(attendee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return attendees;
    }

    public void deleteAttendee(String userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATTENDEE, COLUMN_ATTENDEE_USER_ID + "=? AND " + COLUMN_ATTENDEE_TRIP_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(tripId)});
        db.close();
    }

    //CRUD for Rating
    public List<Rating> getRatingsByRatedUserId(String ratedUserId) {
        List<Rating> ratings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select all rows with the given ratedUserId
        String query = "SELECT * FROM " + TABLE_RATING + " WHERE " + COLUMN_RATING_RATED_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{ratedUserId});

        if (cursor.moveToFirst()) {
            do {
                // Create a Rating object for each row
                String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RATING_USER_ID));
                String ratedUser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RATING_RATED_USER_ID));
                int value = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RATING_VALUE));

                ratings.add(new Rating(userId, ratedUser, value));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ratings;
    }
    public boolean insertRating(String userId, String ratedUserId, int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Add data to the ContentValues object
        values.put(COLUMN_RATING_USER_ID, userId);
        values.put(COLUMN_RATING_RATED_USER_ID, ratedUserId);
        values.put(COLUMN_RATING_VALUE, value);

        // Insert the data into the Rating table
        long result = db.insert(TABLE_RATING, null, values);
        db.close();

        // Return true if insertion was successful, false otherwise
        return result != -1;
    }

}
