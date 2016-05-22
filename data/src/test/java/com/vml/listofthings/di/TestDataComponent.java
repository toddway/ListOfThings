package com.vml.listofthings.di;

import com.vml.listofthings.ThingRepositoryTest;
import com.vml.listofthings.data.base.RxUtil;
import com.vml.listofthings.data.di.DataModule;
import com.vml.listofthings.data.MockThingService;
import com.vml.listofthings.data.base.ServiceFactory;
import com.vml.listofthings.data.things.ThingService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tway on 5/10/16.
 */
@Singleton
@Component(modules = {DataModule.class})
public interface TestDataComponent {

    void inject(ThingRepositoryTest thingRepositoryTest);

    final class Builder {
        public static TestDataComponent build() {
            return DaggerTestDataComponent
                    .builder()
                    .dataModule(new DataModule(new File("/tmp"), new RxUtil()) {
                        @Override
                        public ThingService provideThingService(ServiceFactory serviceFactory) {
                            return new MockThingService();
                        }
                    })
                    .build();
        }
    }
}
