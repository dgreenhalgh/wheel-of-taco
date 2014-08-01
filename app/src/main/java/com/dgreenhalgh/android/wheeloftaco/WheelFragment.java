package com.dgreenhalgh.android.wheeloftaco;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.GraphicalView;
import org.achartengine.model.Point;

public class WheelFragment extends Fragment {

    private TextView mResultTextView;
    private LinearLayout mWheelContainer;
    private GraphicalView mWheelView;
    private Point mSelectionPoint;
    private android.graphics.Point mPivotPoint;

    private GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mPivotPoint = new android.graphics.Point();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(mPivotPoint);

        mGestureDetector = new GestureDetector(getActivity(), new SwipeGestureDetector());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wheel, parent, false);

        mResultTextView = (TextView)view.findViewById(R.id.result_text_view);
        mResultTextView.setText(R.string.result_text_view_default);

        mWheelContainer = (LinearLayout)view.findViewById(R.id.chart);

        RestaurantHelper.get(getActivity()).loadRestaurants();
        drawWheel();

        mSelectionPoint = new Point(mPivotPoint.x / 2, 400);
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

    @Override
    public void onResume() {
        super.onResume();
        drawWheel();
    }

    public void drawWheel() {
        if(mWheelView != null) {
            mWheelContainer.removeView(mWheelView);
        }
        mWheelView = RestaurantWheelView.getNewInstance(getActivity());
        mWheelContainer.addView(mWheelView);
        mWheelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

        private float mStartPositionHack = 0;
        private RotateAnimation mWheelSpinAnimation;

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d("wheelFrag", "down");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            Log.d("wheelFrag", "e1: " + event1 + ", e2: " + event2 + ", x: " + velocityX + ", y: " + velocityY);

            float rotationPosition = velocityY / 10;

            mWheelSpinAnimation = new RotateAnimation(mStartPositionHack, rotationPosition, Animation.ABSOLUTE, mPivotPoint.x / 2, Animation.ABSOLUTE, 692);
            mWheelSpinAnimation.setDuration(1000);
            mWheelSpinAnimation.setFillEnabled(true);
            mWheelSpinAnimation.setFillAfter(true);
            mWheelView.startAnimation(mWheelSpinAnimation);
            mStartPositionHack = (mStartPositionHack + rotationPosition) % 360;

            return true;
        }
    }
}
