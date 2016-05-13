package com.vml.listofthings.data.di;

import com.toddway.shelf.Shelf;
import com.vml.listofthings.data.DataUtil;
import com.vml.listofthings.data.RxUtil;
import com.vml.listofthings.data.retrofit.Environment;
import com.vml.listofthings.data.retrofit.ServiceFactory;
import com.vml.listofthings.data.things.ThingRepositoryImpl;
import com.vml.listofthings.data.things.ThingService;

import java.io.File;
import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tway on 5/10/16.
 */
@Module
public class DataModule {

    File cacheDir;
    RxUtil rxUtil;

    public DataModule(File cacheDir, RxUtil rxUtil) {
        this.cacheDir = cacheDir;
        this.rxUtil = rxUtil;
    }

    @Provides
    public Shelf provideShelf() {
        return new Shelf(cacheDir);
    }

    @Provides @Singleton
    DataUtil provideDataUtil(Shelf shelf) {
        return new DataUtil(rxUtil, shelf);
    }

    @Provides
    protected ServiceFactory provideServiceFactory(DataUtil dataUtil) {
        Environment e = Environment.create("stage", "Stage", "https://example.com", "https://example.com");
        return new ServiceFactory(Arrays.asList(e), dataUtil);
    }


    @Provides @Singleton
    public ThingService provideThingService(ServiceFactory serviceFactory) {
        return serviceFactory.create(ThingService.class);
    }

    @Provides
    public ThingRepositoryImpl provideThingRepository(ThingService thingService, DataUtil dataUtil) {
        return new ThingRepositoryImpl(thingService, dataUtil);
    }
}
