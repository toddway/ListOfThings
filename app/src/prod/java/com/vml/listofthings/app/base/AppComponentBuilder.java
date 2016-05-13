package com.vml.listofthings.app.base;

import android.content.Context;

import com.vml.listofthings.data.DataModule;

/**
 * Created by tway on 5/12/16.
 */
public class AppComponentBuilder {
    public static AppComponent build(Context context) {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(context))
                .domainModule(new DomainModule())
                .dataModule(new DataModule(context.getCacheDir()))
                .build();
    }
}
