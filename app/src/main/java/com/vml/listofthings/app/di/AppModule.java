package com.vml.listofthings.app.di;

import android.content.Context;

import com.vml.listofthings.BuildConfig;
import com.vml.listofthings.app.settings.SettingsPresenter;
import com.vml.listofthings.app.thingdetail.ThingDetailPresenter;
import com.vml.listofthings.app.thinglist.ThingListPresenter;
import com.vml.listofthings.core.environment.GetEnvironmentInteractor;
import com.vml.listofthings.core.environment.GetEnvironmentsInteractor;
import com.vml.listofthings.core.environment.SetEnvironmentInteractor;
import com.vml.listofthings.core.things.GetThingInteractor;
import com.vml.listofthings.core.things.GetThingListInteractor;

import javax.inject.Named;
import javax.inject.Singleton;

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

//        try { // this fails when isInEditMode, but we don't care so we wrap in try/catch
//            LeakCanary.install((Application) appContext.getApplicationContext());
//        } catch (Exception e) {}
    }

    @Provides @Singleton
    @Named("aboutSummary")
    String provideAboutSummary() {
        return BuildConfig.VERSION_NAME + " (" + BuildConfig.GIT_SHA + ")";
    }

    @Provides
    ThingListPresenter provideThingListPresenter(GetThingListInteractor getThingListInteractor) {
        return new ThingListPresenter(getThingListInteractor);
    }

    @Provides
    ThingDetailPresenter provideThingDetailPresenter(GetThingInteractor getThingInteractor) {
        return new ThingDetailPresenter(getThingInteractor);
    }

    @Provides
    SettingsPresenter provideSettingsPresenter(GetEnvironmentInteractor getEnvironmentInteractor, SetEnvironmentInteractor setEnvironmentInteractor, GetEnvironmentsInteractor getEnvironmentsInteractor) {
        return new SettingsPresenter(getEnvironmentsInteractor, setEnvironmentInteractor, getEnvironmentInteractor);
    }
}
