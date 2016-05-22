package com.vml.listofthings.data.base;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by tway on 8/21/15.
 */
public class ServiceFactory {

    protected DataUtil dataUtil;
    RestAdapter.LogLevel logLevel;

    public ServiceFactory(DataUtil dataUtil, RestAdapter.LogLevel logLevel) {
        this.dataUtil = dataUtil;
        this.logLevel = logLevel;
    }

    public <T> T create(Class<T> serviceType) {
        RestAdapter restAdapter = getNetAdapter(
                dataUtil.getEnvironment().getBaseServiceUrl(),
                authRequestInterceptor(dataUtil),
                logLevel
        );
        return restAdapter.create(serviceType);
    }

    private static RestAdapter getNetAdapter(String baseUrl, RequestInterceptor requestInterceptor, RestAdapter.LogLevel logLevel) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setErrorHandler(new NetworkErrorHandler())
                .setLogLevel(logLevel)
                .setEndpoint(baseUrl);

        if (requestInterceptor != null) builder.setRequestInterceptor(requestInterceptor);
        return builder.build();
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
