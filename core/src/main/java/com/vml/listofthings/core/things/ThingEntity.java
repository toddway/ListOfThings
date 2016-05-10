package com.vml.listofthings.core.things;

/**
 * Created by tway on 5/10/16.
 */
public class ThingEntity {
    String id;
    String title;
    String summary;

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public static ThingEntity create(String id, String title, String summary) {
        ThingEntity thingEntity = new ThingEntity();
        thingEntity.id = id;
        thingEntity.title = title;
        thingEntity.summary = summary;
        return thingEntity;
    }
}
