package com.vml.listofthings.di;

import com.vml.listofthings.GetThingInteractorTest;
import com.vml.listofthings.GetThingListInteractorTest;
import com.vml.listofthings.app.di.DomainModule;
import com.vml.listofthings.data.RxUtil;
import com.vml.listofthings.data.di.DataModule;
import com.vml.listofthings.data.MockThingService;
import com.vml.listofthings.data.retrofit.ServiceFactory;
import com.vml.listofthings.data.things.ThingService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tway on 5/13/16.
 */

@Singleton
@Component(modules = {DomainModule.class, DataModule.class})
public interface TestDomainComponent {

    void inject(GetThingListInteractorTest getThingListInteractorTest);

    void inject(GetThingInteractorTest getThingInteractorTest);

    final class Builder {
        public static TestDomainComponent build() {
            return DaggerTestDomainComponent
                    .builder()
                    .domainModule(new DomainModule())
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
