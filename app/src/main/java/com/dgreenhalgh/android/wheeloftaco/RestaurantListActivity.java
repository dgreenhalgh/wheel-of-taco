package com.dgreenhalgh.android.wheeloftaco;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.dgreenhalgh.android.wheeloftaco.R;

public class RestaurantListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RestaurantListFragment();
    }
}
