package com.vml.listofthings.data;

import com.toddway.shelf.Shelf;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.core.things.ThingRepository;

import rx.Observable;

/**
 * Created by tway on 5/10/16.
 */
public class ThingRepositoryImpl implements ThingRepository {

    ThingService thingService;
    Shelf shelf;

    public ThingRepositoryImpl(ThingService thingService, Shelf shelf) {
        this.thingService = thingService;
        this.shelf = shelf;
    }

    @Override
    public Observable<ThingEntity[]> getThingList() {
        return thingService
                .getThingList()
                .compose(shelf.item("thingList").cacheThenNew(ThingEntity[].class));
    }

    @Override
    public Observable<ThingEntity> getThing(String id) {
        return getThingList()
                .skipWhile(list -> list == null)
                .flatMap(Observable::from)
                .first(t -> {
                    return id.equals(t.getId());
                });
    }
}
