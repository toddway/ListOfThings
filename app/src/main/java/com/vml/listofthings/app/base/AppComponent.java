package com.vml.listofthings.app.base;

import com.vml.listofthings.app.thingdetail.ThingDetailActivity;
import com.vml.listofthings.app.thinglist.MainActivity;
import com.vml.listofthings.data.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tway on 5/10/16.
 */
@Singleton
@Component(modules = {AppModule.class, DomainModule.class, DataModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(ThingDetailActivity thingDetailActivity);

}
