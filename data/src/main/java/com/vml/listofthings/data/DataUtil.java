package com.vml.listofthings.data;

import com.toddway.shelf.Shelf;
import com.vml.listofthings.core.SessionEntity;

/**
 * Created by tway on 3/17/16.
 */
public class DataUtil {
    protected RxUtil rxUtil;
    protected Shelf shelf;

    public DataUtil(RxUtil rxUtil, Shelf shelf) {
        this.rxUtil = rxUtil;
        this.shelf = shelf;
    }

    public Shelf shelf() {
        return shelf;
    }

    public RxUtil getRxUtil() {
        return rxUtil;
    }

    public void clearCache() {
        String environmentKey = getEnvironmentKey();
        shelf().clear("");
        if (environmentKey != null) shelf.item("environment").put(environmentKey);
    }

    public void setEnvironmentKey(String key) {
        shelf.item("environment").put(key);
    }

    public String getEnvironmentKey() {
        return shelf().item("environment").get(String.class);
    }

    public String getAuthString() {
        SessionEntity session = shelf().item("session").get(SessionEntity.class);
        return session == null  ? null : session.getAuthString();
    }

    public String getRefreshToken() {
        SessionEntity session = shelf().item("session").get(SessionEntity.class);
        return session == null  ? null : session.refresh_token;
    }
}
