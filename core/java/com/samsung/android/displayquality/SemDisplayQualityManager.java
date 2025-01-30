package com.samsung.android.displayquality;

import android.util.Slog;

/* loaded from: classes5.dex */
public final class SemDisplayQualityManager {
  private static final String TAG = "SemDisplayQualityManager";
  private static final boolean mEnabled = SemDisplayQualityFeature.ENABLED;
  private static final boolean mSupportOutdoor =
      SemDisplayQualityFeature.OUTDOOR_VISIBILITY_SUPPORT;
  private final ISemDisplayQualityManager mService;

  public SemDisplayQualityManager(ISemDisplayQualityManager service) {
    if (service == null) {
      Slog.m113d(TAG, "In Constructor Stub-Service(ISemDisplayQualityManager) is null");
    }
    this.mService = service;
  }

  public void enhanceDisplayOutdoorVisibilityByLux(int lux) {
    if (!mEnabled || !mSupportOutdoor) {
      return;
    }
    ISemDisplayQualityManager iSemDisplayQualityManager = this.mService;
    if (iSemDisplayQualityManager == null) {
      Slog.m115e(TAG, "SemDisplayQualityManagerService is null");
      return;
    }
    try {
      iSemDisplayQualityManager.enhanceDisplayOutdoorVisibilityByLux(lux);
    } catch (Exception e) {
      Slog.m116e(TAG, "enhanceOutdoorVisibilityByLux", e);
    }
  }

  private void onError(Exception e) {
    Slog.m116e(TAG, "Error SemDisplayQualityManager", e);
  }
}
