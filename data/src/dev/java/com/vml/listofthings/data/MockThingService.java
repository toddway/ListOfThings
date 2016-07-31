package com.vml.listofthings.data;

import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.data.things.ThingService;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by tway on 5/10/16.
 */
public class MockThingService implements ThingService {
    @Override
    public Observable<ThingEntity[]> getThingList() {
        return Observable
                .fromCallable(this::getMockThingList)
                .delay(2, TimeUnit.SECONDS);
    }

    ThingEntity[] getMockThingList() {
        long timestamp = System.currentTimeMillis();
        int imageCount = 10;
        int total = 300;
        ThingEntity[] things = new ThingEntity[total];
        for (int i = 0; i < total; i++) {
            int j = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
            int imageId = i % imageCount;
            things[i] = ThingEntity.create(
                    "" + j,
                    "Thing " + j,
                    "Details for Thing " + j,
                    "http://lorempixel.com/g/800/800/?" + timestamp + imageId
            );
        }
        return things;
    }
}
