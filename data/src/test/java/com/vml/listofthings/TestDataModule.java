package com.vml.listofthings;

import com.vml.listofthings.data.DataModule;
import com.vml.listofthings.data.MockThingService;
import com.vml.listofthings.data.ThingService;

import java.io.File;

/**
 * Created by tway on 5/10/16.
 */
public class TestDataModule extends DataModule {
    public TestDataModule() {
        super(new File("/tmp"));
    }

    @Override
    public ThingService provideThingService() {
        return new MockThingService();
    }
}
