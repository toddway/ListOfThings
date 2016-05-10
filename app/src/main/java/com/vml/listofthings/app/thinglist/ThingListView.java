package com.vml.listofthings.app.thinglist;

import com.vml.listofthings.app.base.BaseView;
import com.vml.listofthings.core.things.ThingEntity;

import java.util.List;

/**
 * Created by tway on 5/10/16.
 */
public interface ThingListView extends BaseView {
    void populateThings(List<ThingEntity> thingEntities);
}
