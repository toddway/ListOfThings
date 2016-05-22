package com.vml.listofthings.data.base;

import com.toddway.shelf.Shelf;
import com.vml.listofthings.core.environment.SessionEntity;
import com.vml.listofthings.core.environment.Environment;
import com.vml.listofthings.core.environment.GetEnvironmentInteractor;
import com.vml.listofthings.core.environment.GetEnvironmentsInteractor;
import com.vml.listofthings.core.environment.SetEnvironmentInteractor;

import java.util.List;

/**
 * Created by tway on 3/17/16.
 */
public class DataUtil implements GetEnvironmentInteractor.Repository, SetEnvironmentInteractor.Repository, GetEnvironmentsInteractor.Repository{
    protected RxUtil rxUtil;
    protected Shelf shelf;
    protected List<Environment> environments;
    protected Environment environment;

    public DataUtil(RxUtil rxUtil, Shelf shelf, List<Environment> environments) {
        this.rxUtil = rxUtil;
        this.shelf = shelf;
        this.environments = environments;
    }

    public RxUtil rxUtil() {
        return rxUtil;
    }

    public Shelf shelf() {
        return shelf;
    }

    public void clearShelf() {
        String key = getEnvironmentKey();
        shelf().clear("");
        if (key != null) setEnvironmentKey(key);
    }

    @Override
    public Environment getEnvironment() {
        if (environment == null) environment = Environment.get(getEnvironments(), getEnvironmentKey());
        return environment;
    }

    @Override
    public List<Environment> getEnvironments() {
        return environments;
    }

    @Override
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
