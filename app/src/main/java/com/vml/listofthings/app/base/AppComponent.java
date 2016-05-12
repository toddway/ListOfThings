package com.vml.listofthings.app.base;

import android.content.Context;

import com.vml.listofthings.app.thingdetail.ThingDetailActivity;
import com.vml.listofthings.data.DataModule;
import com.vml.listofthings.app.thinglist.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tway on 5/10/16.
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(ThingDetailActivity thingDetailActivity);

    final class Builder {
        static AppComponent appComponent;

        public static AppComponent get(Context context) {
            if (appComponent == null) appComponent = build(context);
            return appComponent;
        }

        public static AppComponent build(Context context) {
            return DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(context))
                    .dataModule(new DataModule(context.getCacheDir()))
                    .build();
        }
    }
}
