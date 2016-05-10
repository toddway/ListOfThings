package com.vml.listofthings.data;

import com.vml.listofthings.core.things.ThingEntity;

import java.util.Arrays;

import rx.Observable;

/**
 * Created by tway on 5/10/16.
 */
public class MockThingService implements ThingService {
    @Override
    public Observable<ThingEntity[]> getThingList() {
        return Observable.fromCallable(this::getMockThingList);
    }

    ThingEntity[] getMockThingList() {
        return (ThingEntity[]) Arrays.asList(
                ThingEntity.create("1", "title 1", "summary 1"),
                ThingEntity.create("2", "title 2", "summary 2"),
                ThingEntity.create("3", "title 3", "summary 3")
        ).toArray();
    }
}
