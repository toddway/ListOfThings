package com.vml.listofthings.data;

import com.vml.listofthings.data.base.DataUtil;
import com.vml.listofthings.core.environment.Environment;
import com.vml.listofthings.data.base.RxUtil;
import com.vml.listofthings.data.base.ServiceFactory;
import com.vml.listofthings.data.di.DataModule;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by tway on 5/22/16.
 */
public class DevDataModule extends DataModule {
    public DevDataModule(File cacheDir, RxUtil rxUtil) {
        super(cacheDir, rxUtil);
    }

    @Override
    public List<Environment> provideEnvironments() {
        return Arrays.asList(
                Environment.create("mock", "Mocks", null, null),
                Environment.create("stage", "Stage", "https://example.com", "https://example.com")
        );
    }

    @Override
    public ServiceFactory provideServiceFactory(DataUtil dataUtil) {
        return new DevServiceFactory(dataUtil, RestAdapter.LogLevel.FULL);
    }
}
