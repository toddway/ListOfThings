package com.vml.listofthings.core.things;


import rx.Observable;

public class GetThingListInteractor {

    ThingRepository repository;

    public GetThingListInteractor(ThingRepository repository) {
        this.repository = repository;
    }

    public Observable<ThingEntity[]> get() {
        return repository.getThingList();
    }

    public Observable<ThingEntity[]> getNew() {
        return repository.getNewThingList();
    }
}
