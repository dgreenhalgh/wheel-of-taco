package com.dgreenhalgh.android.wheeloftaco;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;


public class WheelActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WheelFragment();
    }
}
