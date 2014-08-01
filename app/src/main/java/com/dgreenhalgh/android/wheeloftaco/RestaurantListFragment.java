package com.dgreenhalgh.android.wheeloftaco;



import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
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
        View view = super.onCreateView(inflater, parent, savedInstanceState);

        ListView listView = (ListView)view.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.restaurant_list_item_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_delete_restaurant:
                        RestaurantAdapter adapter = (RestaurantAdapter)getListAdapter();
                        RestaurantHelper restaurantHelper = RestaurantHelper.get(getActivity());
                        for(int i = adapter.getCount() - 1; i >= 0; i--) {
                            if(getListView().isItemChecked(i)) {
                                restaurantHelper.deleteRestaurant(adapter.getItem(i));
                            }
                        }
                        mode.finish();
                        adapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        return view;
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

            final Restaurant restaurant = getItem(position);

            TextView restaurantNameTextView
                    = (TextView)convertView.findViewById(R.id.restaurant_list_item_titleTextView);
            restaurantNameTextView.setText(restaurant.getName());
            CheckBox restaurantSelectableCheckBox
                    = (CheckBox)convertView.findViewById(R.id.restaurant_list_item_restaurantSelectableTextView);
            restaurantSelectableCheckBox.setChecked(restaurant.isSelectable());
            restaurantSelectableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    restaurant.setSelectable(isChecked);
                }
            });

            return convertView;
        }
    }
}
