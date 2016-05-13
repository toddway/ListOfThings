package com.vml.listofthings.app.thinglist;

import com.vml.listofthings.core.things.GetThingListInteractor;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.app.base.BasePresenter;

import java.util.Arrays;

/**
 * Created by tway on 5/10/16.
 */
public class ThingListPresenter extends BasePresenter<ThingListView> {

    GetThingListInteractor getThingListInteractor;

    public ThingListPresenter(GetThingListInteractor getThingListInteractor) {
        this.getThingListInteractor = getThingListInteractor;
    }

    @Override
    public void attachView(ThingListView view) {
        super.attachView(view);
        getThingList();
    }

    public void getThingList() {
        addSubscription(getThingListInteractor.get().subscribe(this::presentThings, view::handleGlobalError));
    }

    public void getNewThingList() {
        addSubscription(getThingListInteractor.getNew().subscribe(this::presentThings, view::handleGlobalError));
    }

    void presentThings(ThingEntity[] thingEntities) {
        view.populateThings(Arrays.asList(thingEntities));
    }

}
