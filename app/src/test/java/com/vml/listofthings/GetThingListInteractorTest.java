package com.vml.listofthings;

import com.vml.listofthings.core.things.GetThingListInteractor;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.di.TestDomainComponent;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

/**
 * Created by tway on 5/13/16.
 */
public class GetThingListInteractorTest {

    @Inject GetThingListInteractor getThingListInteractor;

    @Before
    public void beforeEach() {
        TestDomainComponent.Builder.build().inject(this);
    }

    @Test
    public void testGetThingListInteractor() {
        TestSubscriber<ThingEntity[]> subscriber = new TestSubscriber<>();
        getThingListInteractor.get().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        for (ThingEntity[] thingEntities : subscriber.getOnNextEvents()) {
            if (thingEntities != null) System.out.println(thingEntities[0].getTitle());
        }
    }
}
