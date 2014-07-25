package com.dgreenhalgh.android.wheeloftaco;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Restaurant {

    private static final String JSON_NAME = "name";
    private static final String JSON_WHEEL_SLICE_COLOR = "wheel_slice_color";

    private String mName;
    private int mWheelSliceColor;

    public Restaurant() {
        Random rand = new Random();
        mWheelSliceColor = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public Restaurant(JSONObject json) throws JSONException {
        mName = json.getString(JSON_NAME);
        mWheelSliceColor = json.getInt(JSON_WHEEL_SLICE_COLOR);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getWheelSliceColor() {
        return mWheelSliceColor;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, mName);
        json.put(JSON_WHEEL_SLICE_COLOR, mWheelSliceColor);
        return json;
    }
}
