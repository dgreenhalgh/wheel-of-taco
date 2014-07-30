package com.dgreenhalgh.android.wheeloftaco;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WheelFragment extends Fragment {

    private TextView mResultTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wheel, parent, false);

        mResultTextView = (TextView)view.findViewById(R.id.result_text_view);
        mResultTextView.setText(R.string.result_text_view_default);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.restaurant, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_add_restaurant:
                Intent addRestaurantIntent = new Intent(getActivity(), RestaurantActivity.class);
                startActivity(addRestaurantIntent);
                return true;
            case R.id.menu_edit_restaurants:
                Intent editRestaurantIntent  = new Intent(getActivity(), RestaurantListActivity.class);
                startActivity(editRestaurantIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RestaurantHelper.get(getActivity()).saveRestaurants();
    }

    public void drawWheel() {

    }

}
