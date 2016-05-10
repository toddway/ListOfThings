package com.vml.listofthings;

import com.vml.listofthings.data.DataModule;

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
                    .dataModule(new TestDataModule())
                    .build();
        }
    }
}
