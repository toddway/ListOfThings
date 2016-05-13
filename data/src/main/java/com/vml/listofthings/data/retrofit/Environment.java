package com.vml.listofthings.data.retrofit;

public class Environment {
    public String key;
    public String label;
    public String baseServiceUrl;
    public String baseWebsiteUrl;
    public int delayInMillis = 0;


    public String getKey() {
        return key;
    }

    public String getBaseServiceUrl() {
        return baseServiceUrl;
    }

    public String getBaseWebsiteUrl() {
        return baseWebsiteUrl;
    }

    public String getLabel() {
        return label;
    }

    public static Environment createStub(String key, String label, int delay) {
        Environment e = new Environment();
        e.key = key;
        e.label = label;
        e.delayInMillis = delay;
        return e;
    }

    public static Environment create(String key, String label, String baseServiceUrl) {
        return create(key, label, baseServiceUrl, null);
    }

    public static Environment create(String key, String label, String baseServiceUrl, String baseWebsiteUrl) {
        Environment e = new Environment();
        e.key = key;
        e.label = label;
        e.baseServiceUrl = baseServiceUrl;
        e.baseWebsiteUrl = baseWebsiteUrl;
        return e;
    }
}
