package com.vml.listofthings.app.thingdetail;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by tway on 5/12/16.
 */
public class ThingDetailUtil {

    public static void launch(Activity fromActivity, String id) {
        Intent intent = new Intent(fromActivity, ThingDetailActivity.class);
        intent.putExtra("id", id);
        fromActivity.startActivity(intent);
    }

    public static String id(Intent intent) {
        return intent.getStringExtra("id");
    }
}
