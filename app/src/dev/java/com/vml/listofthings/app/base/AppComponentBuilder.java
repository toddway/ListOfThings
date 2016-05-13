package com.vml.listofthings.app.base;

import android.content.Context;

import com.vml.listofthings.data.MockThingService;
import com.vml.listofthings.data.RxUtil;
import com.vml.listofthings.data.di.DataModule;
import com.vml.listofthings.data.retrofit.ServiceFactory;
import com.vml.listofthings.data.things.ThingService;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tway on 5/12/16.
 */
public class AppComponentBuilder {
    public static AppComponent build(Context context) {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(context))
                .domainModule(new DomainModule())
                .dataModule(new DataModule(context.getCacheDir(), new RxUtil(Schedulers.newThread(), AndroidSchedulers.mainThread())){
                    @Override
                    public ThingService provideThingService(ServiceFactory serviceFactory) {
                        return new MockThingService();
                    }
                })
                .build();
    }
}
