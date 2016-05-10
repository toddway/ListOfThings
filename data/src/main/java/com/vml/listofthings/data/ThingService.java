package com.vml.listofthings.data;

import com.vml.listofthings.core.things.ThingEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by tway on 5/10/16.
 */
public interface ThingService {

    @GET("/thing/list")
    Observable<ThingEntity[]> getThingList();
}
