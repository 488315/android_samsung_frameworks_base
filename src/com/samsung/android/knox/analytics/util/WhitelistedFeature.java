package com.samsung.android.knox.analytics.util;

/* loaded from: classes6.dex */
public class WhitelistedFeature {
    private EnableApi mEnableApi;
    private String mFeature;

    public enum EnableApi {
        ALL,
        GET
    }

    WhitelistedFeature(String str, Integer num) {
        this.mFeature = str;
        this.mEnableApi = fromEnableType(num);
    }

    private static EnableApi fromEnableType(Integer num) {
        if (num == null) {
            return EnableApi.ALL;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return EnableApi.ALL;
        }
        if (iIntValue != 2) {
            return null;
        }
        return EnableApi.GET;
    }

    public boolean hasFeatureName(String str) {
        return this.mFeature.equals(str);
    }

    public EnableApi getEnableApi() {
        return this.mEnableApi;
    }
}
