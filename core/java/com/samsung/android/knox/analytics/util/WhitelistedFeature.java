package com.samsung.android.knox.analytics.util;

public class WhitelistedFeature {
    private EnableApi mEnableApi;
    private String mFeature;

    public enum EnableApi {
        ALL,
        GET
    }

    WhitelistedFeature(String feature, Integer enableType) {
        this.mFeature = feature;
        this.mEnableApi = fromEnableType(enableType);
    }

    private static EnableApi fromEnableType(Integer enableType) {
        if (enableType == null) {
            return EnableApi.ALL;
        }
        switch (enableType.intValue()) {
            case 0:
                return EnableApi.ALL;
            case 1:
            default:
                return null;
            case 2:
                return EnableApi.GET;
        }
    }

    public boolean hasFeatureName(String feature) {
        return this.mFeature.equals(feature);
    }

    public EnableApi getEnableApi() {
        return this.mEnableApi;
    }
}
