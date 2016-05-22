package com.vml.listofthings.core.environment;

import java.util.List;

public class Environment {
    public String key;
    public String label;
    public String baseServiceUrl;
    public String baseWebsiteUrl;


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

    public boolean isMock() {
        return getKey() == null || getKey().contains("mock");
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

    public static Environment get(List<Environment> list, String key) {
        if (list == null || list.isEmpty()) {
            return new Environment();
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            try {
                for (Environment e : list)
                    if (e.getKey().equals(key))
                        return e;
            } catch (Exception e) {}
            return list.get(0); //if key not found, default to first environment
        }
    }
}
