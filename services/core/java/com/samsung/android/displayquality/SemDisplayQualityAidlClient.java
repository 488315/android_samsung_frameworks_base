package com.samsung.android.displayquality;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
