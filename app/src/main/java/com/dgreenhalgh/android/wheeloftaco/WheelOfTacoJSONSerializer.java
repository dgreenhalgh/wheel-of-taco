package com.dgreenhalgh.android.wheeloftaco;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class WheelOfTacoJSONSerializer {

    private Context mContext;
    private String mFilename;

    public WheelOfTacoJSONSerializer(Context context, String filename) {
        mContext = context;
        mFilename = filename;
    }

    public void saveRestaurants(ArrayList<Restaurant> restaurants) throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        for(Restaurant restaurant : restaurants) {
            jsonArray.put(restaurant.toJSON());
        }

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonArray.toString());
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }


    public ArrayList<Restaurant> loadRestaurants() throws IOException, JSONException {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        BufferedReader reader = null;

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for(int iRestaurant = 0; iRestaurant < jsonArray.length(); iRestaurant++) {
                restaurants.add(new Restaurant(jsonArray.getJSONObject(iRestaurant)));
            }
        } catch(FileNotFoundException fne) {

        } finally {
            if(reader != null) {
                if(reader != null) {
                    reader.close();
                }
            }
        }

        return restaurants;
    }
}
