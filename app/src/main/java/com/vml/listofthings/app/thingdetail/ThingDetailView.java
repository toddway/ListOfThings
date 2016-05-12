package com.vml.listofthings.app.thingdetail;

import com.vml.listofthings.app.base.BaseView;
import com.vml.listofthings.core.things.ThingEntity;

/**
 * Created by tway on 5/12/16.
 */
public interface ThingDetailView extends BaseView {
    void populateDetails(ThingEntity thingEntity);
}
