package com.android.internal.jank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class DisplayRefreshRate {
    public static final int REFRESH_RATE_120_HZ = 5;
    public static final int REFRESH_RATE_240_HZ = 6;
    public static final int REFRESH_RATE_30_HZ = 2;
    public static final int REFRESH_RATE_60_HZ = 3;
    public static final int REFRESH_RATE_90_HZ = 4;
    public static final int UNKNOWN_REFRESH_RATE = 0;
    public static final int VARIABLE_REFRESH_RATE = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshRate {
    }

    private DisplayRefreshRate() {
    }

    public static int getRefreshRate(long frameIntervalNs) {
        long rate = Math.round(1.0E9d / frameIntervalNs);
        if (rate < 50) {
            return 2;
        }
        if (rate < 80) {
            return 3;
        }
        if (rate < 110) {
            return 4;
        }
        if (rate < 180) {
            return 5;
        }
        return 6;
    }
}
