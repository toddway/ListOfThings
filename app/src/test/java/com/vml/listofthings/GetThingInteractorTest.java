package com.vml.listofthings;

import com.vml.listofthings.core.things.GetThingInteractor;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.di.TestDomainComponent;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

/**
 * Created by tway on 5/13/16.
 */
public class GetThingInteractorTest {

    @Inject GetThingInteractor getThingInteractor;

    @Before
    public void beforeEach() {
        TestDomainComponent.Builder.build().inject(this);
    }

    @Test
    public void testGetThingInteractor() {
        TestSubscriber<ThingEntity> subscriber = new TestSubscriber<>();
        getThingInteractor.get("5").subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

}
