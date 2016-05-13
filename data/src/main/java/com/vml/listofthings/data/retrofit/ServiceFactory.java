package com.vml.listofthings.data.retrofit;

import com.vml.listofthings.data.DataUtil;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by tway on 8/21/15.
 */
public class ServiceFactory {

    protected DataUtil dataUtil;
    protected List<Environment> environments;
    protected Environment environment;

    public ServiceFactory(List<Environment> environments, DataUtil dataUtil) {
        this.environments = environments;
        this.dataUtil = dataUtil;
        initEnvironment();
    }

    private void initEnvironment() {
        if (getEnvironments().size() == 1) environment = environments.get(0);
        else {
            try {
                String key = dataUtil.getEnvironmentKey();
                for (Environment e : getEnvironments()) {
                    if (e.getKey().equals(key)) environment = e;
                }

                if (environment == null) environment = getEnvironments().iterator().next(); //if key not found, default to first environment
            } catch (Exception e) {}
        }
        if (environment == null) environment = new Environment();
    }

    public Environment getEnvironment() {
        return environment;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public <T> T create(Class<T> serviceType) {
        return create(serviceType, getEnvironment(), authRequestInterceptor(dataUtil));
    }

    private static RestAdapter getNetAdapter(Environment environment, final RequestInterceptor requestInterceptor) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setErrorHandler(new NetworkErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(environment.getBaseServiceUrl());

        if (requestInterceptor != null) builder.setRequestInterceptor(requestInterceptor);
        return builder.build();
    }

    private static <T> T create(Class<T> serviceType, Environment environment, RequestInterceptor requestInterceptor) {
        RestAdapter restAdapter = getNetAdapter(environment, requestInterceptor);
        return restAdapter.create(serviceType);
    }


    private static RequestInterceptor authRequestInterceptor(final DataUtil dataUtil) {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Cache-Control", "no-cache");
                if (dataUtil.getAuthString() != null) {
                    request.addHeader("Authorization", dataUtil.getAuthString());
                }
            }
        };
    }
}
