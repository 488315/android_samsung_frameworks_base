package com.samsung.android.displayquality;

import android.util.Slog;

/* loaded from: classes6.dex */
public final class SemDisplayQualityManager {
    private static final String TAG = "SemDisplayQualityManager";
    private static final boolean mEnabled = SemDisplayQualityFeature.ENABLED;
    private static final boolean mSupportOutdoor = SemDisplayQualityFeature.OUTDOOR_VISIBILITY_SUPPORT;
    private final ISemDisplayQualityManager mService;

    public SemDisplayQualityManager(ISemDisplayQualityManager iSemDisplayQualityManager) {
        if (iSemDisplayQualityManager == null) {
            Slog.d(TAG, "In Constructor Stub-Service(ISemDisplayQualityManager) is null");
        }
        this.mService = iSemDisplayQualityManager;
    }

    public void enhanceDisplayOutdoorVisibilityByLux(int i) {
        if (mEnabled && mSupportOutdoor) {
            ISemDisplayQualityManager iSemDisplayQualityManager = this.mService;
            if (iSemDisplayQualityManager == null) {
                Slog.e(TAG, "SemDisplayQualityManagerService is null");
                return;
            }
            try {
                iSemDisplayQualityManager.enhanceDisplayOutdoorVisibilityByLux(i);
            } catch (Exception e) {
                Slog.e(TAG, "enhanceOutdoorVisibilityByLux", e);
            }
        }
    }

    private void onError(Exception exc) {
        Slog.e(TAG, "Error SemDisplayQualityManager", exc);
    }
}
