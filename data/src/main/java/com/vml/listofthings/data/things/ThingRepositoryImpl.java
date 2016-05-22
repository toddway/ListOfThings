package com.vml.listofthings.data.things;

import com.toddway.shelf.Shelf;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.core.things.ThingRepository;
import com.vml.listofthings.data.base.DataUtil;

import rx.Observable;

/**
 * Created by tway on 5/10/16.
 */
public class ThingRepositoryImpl implements ThingRepository {

    ThingService thingService;
    DataUtil dataUtil;
    Shelf shelf;

    public ThingRepositoryImpl(ThingService thingService, DataUtil dataUtil) {
        this.thingService = thingService;
        this.dataUtil = dataUtil;
        this.shelf = dataUtil.shelf();
    }

    @Override
    public Observable<ThingEntity[]> getThingList() {
        return thingService
                .getThingList()
                .compose(shelf.item("thingList").cacheThenNew(ThingEntity[].class))
                .compose(dataUtil.rxUtil().applyCommonSchedulers());
    }

    @Override
    public Observable<ThingEntity> getThing(String id) {
        return getThingList()
                .skipWhile(list -> list == null)
                .flatMap(Observable::from)
                .first(t -> id.equals(t.getId()));
    }

    @Override
    public Observable<ThingEntity[]> getNewThingList() {
        return thingService
                .getThingList()
                .compose(shelf.item("thingList").newOnly(ThingEntity[].class))
                .compose(dataUtil.rxUtil().applyCommonSchedulers());
    }
}
