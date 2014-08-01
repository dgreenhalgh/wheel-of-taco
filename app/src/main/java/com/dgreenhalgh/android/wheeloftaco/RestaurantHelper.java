package com.dgreenhalgh.android.wheeloftaco;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class RestaurantHelper {
    private static final String TAG = "RestaurantAdapter";
    private static final String FILENAME = "restaurants.json";

    private ArrayList<Restaurant> mRestaurants;
    private WheelOfTacoJSONSerializer mSerializer;

    private static RestaurantHelper sRestaurantHelper;
    private Context mAppContext;

    private RestaurantHelper(Context appContext) {
        mAppContext = appContext;
        mSerializer = new WheelOfTacoJSONSerializer(mAppContext, FILENAME);

        mRestaurants = loadRestaurants();
    }

    public static RestaurantHelper get(Context context) {
        if(sRestaurantHelper == null) {
            sRestaurantHelper = new RestaurantHelper(context.getApplicationContext());
        }

        return sRestaurantHelper;
    }

    public void addRestaurant(Restaurant restaurant) {
        if(!mRestaurants.contains(restaurant) && !restaurant.getName().equals("")) { // TODO: Compare by restaurant name
            mRestaurants.add(restaurant);
            saveRestaurants();
        }
    }

    public void deleteRestaurant(Restaurant restaurant) {
        mRestaurants.remove(restaurant);
        saveRestaurants();
    }

    public boolean saveRestaurants() {
        try {
            mSerializer.saveRestaurants(mRestaurants);
            return true;
        } catch(Exception e) {
            Log.e(TAG, "Error saving restaurants: ", e);
            return false;
        }
    }

    public ArrayList<Restaurant> loadRestaurants() {
        try {
            mRestaurants = mSerializer.loadRestaurants();
        } catch(Exception e) {
            mRestaurants = new ArrayList<Restaurant>();
            Log.e(TAG, "Error loading restaurants: ", e);
        }

        return mRestaurants;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return mRestaurants;
    }
}
