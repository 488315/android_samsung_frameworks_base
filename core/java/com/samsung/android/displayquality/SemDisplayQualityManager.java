package com.samsung.android.displayquality;

import android.util.Slog;

public final class SemDisplayQualityManager {
    private static final String TAG = "SemDisplayQualityManager";
    private static final boolean mEnabled = SemDisplayQualityFeature.ENABLED;
    private static final boolean mSupportOutdoor =
            SemDisplayQualityFeature.OUTDOOR_VISIBILITY_SUPPORT;
    private final ISemDisplayQualityManager mService;

    public SemDisplayQualityManager(ISemDisplayQualityManager service) {
        if (service == null) {
            Slog.d(TAG, "In Constructor Stub-Service(ISemDisplayQualityManager) is null");
        }
        this.mService = service;
    }

    public void enhanceDisplayOutdoorVisibilityByLux(int lux) {
        if (!mEnabled || !mSupportOutdoor) {
            return;
        }
        if (this.mService == null) {
            Slog.e(TAG, "SemDisplayQualityManagerService is null");
            return;
        }
        try {
            this.mService.enhanceDisplayOutdoorVisibilityByLux(lux);
        } catch (Exception e) {
            Slog.e(TAG, "enhanceOutdoorVisibilityByLux", e);
        }
    }

    private void onError(Exception e) {
        Slog.e(TAG, "Error SemDisplayQualityManager", e);
    }
}
