package com.vml.listofthings.app.thingdetail;

import android.app.Activity;
import android.os.Bundle;

import com.vml.listofthings.R;
import com.vml.listofthings.app.base.BaseActivity;

public class ThingDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
        setToolbarTitle("...");
    }

    public static void launch(Activity fromActivity, String id) {
        //TODO: pass id
        launch(fromActivity, ThingDetailActivity.class);
    }
}
