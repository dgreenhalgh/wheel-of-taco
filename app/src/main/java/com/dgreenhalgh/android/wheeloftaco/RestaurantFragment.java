package com.dgreenhalgh.android.wheeloftaco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RestaurantFragment extends Fragment {

    public static String EXTRA_RESTAURANT_NAME = "wheeloftaco.RESTAURANT_NAME";

    private EditText mRestaurantNameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        mRestaurantNameEditText = (EditText)view.findViewById(R.id.restaurant_name_edit_text);

        return view;
    }


}
