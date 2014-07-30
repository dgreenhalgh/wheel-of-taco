package com.dgreenhalgh.android.wheeloftaco;



import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantListFragment extends ListFragment {

    private ArrayList<Restaurant> mRestaurants;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestaurants = RestaurantHelper.get(getActivity()).getRestaurants();
        RestaurantAdapter adapter = new RestaurantAdapter(mRestaurants);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return super.onCreateView(inflater, parent, savedInstanceState);
    }

    private class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        public RestaurantAdapter(ArrayList<Restaurant> restaurants) {
            super(getActivity(), android.R.layout.simple_list_item_1, restaurants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_restaurant, null);
            }

            Restaurant restaurant = getItem(position);

            TextView restaurantNameTextView
                    = (TextView)convertView.findViewById(R.id.restaurant_list_item_titleTextView);
            restaurantNameTextView.setText(restaurant.getName());
            CheckBox restaurantSelectableCheckBox
                    = (CheckBox)convertView.findViewById(R.id.restaurant_list_item_restaurantSelectableTextView);
            restaurantSelectableCheckBox.setChecked(restaurant.isSelectable());

            return convertView;
        }
    }
}
