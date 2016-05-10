package com.vml.listofthings.core.things;

import rx.Observable;

/**
 * Created by tway on 5/10/16.
 */
public class GetThingInteractor {
    ThingRepository repository;

    public GetThingInteractor(ThingRepository repository) {
        this.repository = repository;
    }

    public Observable<ThingEntity> get(String id) {
        return repository.getThing(id);
    }
}
