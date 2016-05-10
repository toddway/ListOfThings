package com.vml.listofthings.app.base;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.vml.listofthings.core.things.GetThingListInteractor;
import com.vml.listofthings.data.ThingRepositoryImpl;
import com.vml.listofthings.app.thinglist.ThingListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tway on 5/10/16.
 */
@Module
public class AppModule {

    Context appContext;

    public AppModule(Context context) {
        this.appContext = context;

        try { // this fails when isInEditMode, but we don't care so we wrap in try/catch
            LeakCanary.install((Application) appContext.getApplicationContext());
        } catch (Exception e) {}
    }

    @Provides
    ThingListPresenter provideThingListPresenter(ThingRepositoryImpl thingRepository) {
        return new ThingListPresenter(new GetThingListInteractor(thingRepository));
    }
}
