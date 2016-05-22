package com.vml.listofthings.app.di;

import com.vml.listofthings.core.environment.GetEnvironmentInteractor;
import com.vml.listofthings.core.environment.GetEnvironmentsInteractor;
import com.vml.listofthings.core.environment.SetEnvironmentInteractor;
import com.vml.listofthings.core.things.GetThingInteractor;
import com.vml.listofthings.core.things.GetThingListInteractor;
import com.vml.listofthings.data.base.DataUtil;
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

    @Provides
    GetEnvironmentInteractor providGetEnvironmentInteractor(DataUtil dataUtil) {
        return new GetEnvironmentInteractor(dataUtil);
    }

    @Provides
    SetEnvironmentInteractor provideSetEnvironmentInteractor(DataUtil dataUtil) {
        return new SetEnvironmentInteractor(dataUtil);
    }

    @Provides
    GetEnvironmentsInteractor provideGetEnvironmentsInteractor(DataUtil dataUtil) {
        return new GetEnvironmentsInteractor(dataUtil);
    }
}
