package com.dgreenhalgh.android.wheeloftaco;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RestaurantAdapter {
    private static final String TAG = "RestaurantAdapter";
    private static final String FILENAME = "restaurants.json";

    private ArrayList<Restaurant> mRestaurants;
    private WheelOfTacoJSONSerializer mSerializer;

    private static RestaurantAdapter sRestaurantAdapter;
    private Context mAppContext;

    private RestaurantAdapter(Context appContext) {
        mAppContext = appContext;
        mSerializer = new WheelOfTacoJSONSerializer(mAppContext, FILENAME);

        try {
            mRestaurants = mSerializer.loadRestaurants();
        } catch(Exception e) {
            mRestaurants = new ArrayList<Restaurant>();
            Log.e(TAG, "Error loading restaurants: ", e);
        }
    }

    public static RestaurantAdapter get(Context context) {
        if(sRestaurantAdapter == null) {
            sRestaurantAdapter = new RestaurantAdapter(context.getApplicationContext());
        }

        return sRestaurantAdapter;
    }

    public void addRestaurant(Restaurant restaurant) {
        mRestaurants.add(restaurant);
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

}
