package com.vml.listofthings.app.di;

import com.vml.listofthings.core.things.GetThingInteractor;
import com.vml.listofthings.core.things.GetThingListInteractor;
import com.vml.listofthings.data.things.ThingRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tway on 5/13/16.
 */
@Module
public class DomainModule {

    @Provides
    GetThingListInteractor provideGetThingListInteractor(ThingRepositoryImpl thingRepository) {
        return new GetThingListInteractor(thingRepository);
    }

    @Provides
    GetThingInteractor provideGetThingInteractor(ThingRepositoryImpl thingRepository) {
        return new GetThingInteractor(thingRepository);
    }
}
