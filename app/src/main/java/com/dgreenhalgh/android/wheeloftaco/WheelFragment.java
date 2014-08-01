package com.dgreenhalgh.android.wheeloftaco;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PieChart;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;

public class WheelFragment extends Fragment {

    private TextView mResultTextView;
    private Point mSelectionPoint;

    private GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mGestureDetector = new GestureDetector(getActivity(), new SwipeGestureDetector());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wheel, parent, false);

        mResultTextView = (TextView)view.findViewById(R.id.result_text_view);
        mResultTextView.setText(R.string.result_text_view_default);

        LinearLayout wheelContainer = (LinearLayout)view.findViewById(R.id.chart);
        GraphicalView wheelView = RestaurantWheelView.getNewInstance(getActivity());
        wheelContainer.addView(wheelView);
        wheelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        mSelectionPoint = new Point(wheelContainer.getWidth() / 2, wheelContainer.getY() + 5);
        // TODO: figure out selection

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

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d("wheelFrag", "down");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            Log.d("wheelFrag", "e1: " + event1 + ", e2: " + event2 + ", x: " + velocityX + ", y: " + velocityY);
            return true;
        }
    }
}
