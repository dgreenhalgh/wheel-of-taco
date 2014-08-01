package com.dgreenhalgh.android.wheeloftaco;

import android.content.Context;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;

public class RestaurantWheelView extends GraphicalView {

    private static ArrayList<Restaurant> mRestaurants;

    public RestaurantWheelView(Context context, AbstractChart chart) {
        super(context, chart);
    }

    public static GraphicalView getNewInstance(Context context) {
        return ChartFactory.getPieChartView(context, getDataSet(context), getWheelRenderer(context));
    }

    private static DefaultRenderer getWheelRenderer(Context context) {
        mRestaurants = RestaurantHelper.get(context).getRestaurants();

        DefaultRenderer wheelRenderer = new DefaultRenderer();
        for(Restaurant restaurant : mRestaurants) {
            if(restaurant.isSelectable()) {
                SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
                simpleSeriesRenderer.setColor(restaurant.getWheelSliceColor());
                wheelRenderer.addSeriesRenderer(simpleSeriesRenderer);
            }
        }

        wheelRenderer.setShowLabels(false);
        wheelRenderer.setShowLegend(false);
        wheelRenderer.setPanEnabled(false);

        return wheelRenderer;
    }

    private static CategorySeries getDataSet(Context context) {
        mRestaurants = RestaurantHelper.get(context).getRestaurants();

        CategorySeries restaurantSeries = new CategorySeries("Restaurants");
        for(Restaurant restaurant : mRestaurants) {
            if(restaurant.isSelectable()) {
                restaurantSeries.add(restaurant.getName(), 1); // 1 is so that every restaurant has equal weight. Could maybe add weighting in the future?
            }
        }

        return restaurantSeries;
    }
}
