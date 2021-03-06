package com.vml.listofthings;

import com.vml.listofthings.core.things.GetThingListInteractor;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.data.base.DataUtil;
import com.vml.listofthings.di.TestDataComponent;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

/**
 * Created by tway on 5/13/16.
 */
public class GetThingListInteractorTest {

    @Inject GetThingListInteractor getThingListInteractor;
    @Inject DataUtil dataUtil;

    @Before
    public void beforeEach() {
        TestDataComponent.Builder.build().inject(this);
        dataUtil.clearShelf();
    }

    @Test
    public void testGetThingListInteractor() {
        TestSubscriber<ThingEntity[]> subscriber = new TestSubscriber<>();
        getThingListInteractor.get().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        for (ThingEntity[] thingEntities : subscriber.getOnNextEvents()) {
            System.out.println(thingEntities);
        }
    }

}
