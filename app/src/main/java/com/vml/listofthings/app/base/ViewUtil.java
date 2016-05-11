package com.vml.listofthings.app.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by tway on 5/11/16.
 */
public class ViewUtil {

    public static void initToolbar(AppCompatActivity activity, int toolbarId, int colorId) {
        Toolbar toolbar = (Toolbar) activity.findViewById(toolbarId);
        if (toolbar != null) activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            if (colorId != -1 && colorId != Color.TRANSPARENT) {
                actionBar.setBackgroundDrawable(new ColorDrawable(colorId));
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
