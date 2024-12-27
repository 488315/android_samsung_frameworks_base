package com.samsung.android.displayquality;

import android.util.Slog;

public class SemDisplayQualityAidlClient {
    public static final int RESULT_DISABLED = 0;
    public static final int RESULT_ENABLED = 1;
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_NO_SUPPORT = 3;
    private static final String TAG = "SemDisplayQualityAidlClientDummy";

    public SemDisplayQualityAidlClient() {
        Slog.d(TAG, "SemDisplayQualityAidlClient dummy");
    }

    public int setOutdoorVisibilityEnhancerEnabled(boolean z) {
        Slog.d(TAG, "setOutdoorVisibilityEnhancerEnabled dummy");
        return 0;
    }
}
