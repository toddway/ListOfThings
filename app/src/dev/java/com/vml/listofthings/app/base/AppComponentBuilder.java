package com.vml.listofthings.app.base;

import android.content.Context;

import com.vml.listofthings.app.di.AppComponent;
import com.vml.listofthings.app.di.AppModule;
import com.vml.listofthings.app.di.DaggerAppComponent;
import com.vml.listofthings.data.di.DomainModule;
import com.vml.listofthings.data.DevDataModule;
import com.vml.listofthings.data.base.RxUtil;

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
                .dataModule(
                        new DevDataModule(
                                context.getCacheDir(),
                                new RxUtil(Schedulers.newThread(), AndroidSchedulers.mainThread())
                        )
                )
                .build();
    }
}
