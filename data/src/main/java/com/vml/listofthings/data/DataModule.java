package com.vml.listofthings.data;

import com.toddway.shelf.Shelf;

import java.io.File;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tway on 5/10/16.
 */
@Module
public class DataModule {

    File cacheDir;

    public DataModule(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    @Provides
    public Shelf provideShelf() {
        return new Shelf(cacheDir);
    }

    @Provides
    public ThingService provideThingService() {
        return new MockThingService();
    }

    @Provides
    public ThingRepositoryImpl provideThingRepository(ThingService thingService, Shelf shelf) {
        return new ThingRepositoryImpl(thingService, shelf);
    }
}
