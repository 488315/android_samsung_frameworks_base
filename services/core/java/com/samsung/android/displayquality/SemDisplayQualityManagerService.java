package com.samsung.android.displayquality;

import android.content.Context;
import android.os.Build;
import android.util.Slog;
import com.samsung.android.displayquality.ISemDisplayQualityManager;

/* loaded from: classes2.dex */
public class SemDisplayQualityManagerService extends ISemDisplayQualityManager.Stub {
    private static final String TAG = "SemDisplayQualityManagerService";
    private final boolean DEBUG;
    private SemDisplayQuality displayQuality;
    private final Context mContext;
    private final boolean mEnabled;
    private final Object mLock;
    private final String mPlatform;
    private final boolean mSupportAdaptiveSync;
    private final boolean mSupportOutdoor;

    public SemDisplayQualityManagerService(Context context) {
        String str = Build.TYPE;
        this.DEBUG = "eng".equals(str) || "userdebug".equals(str);
        this.mEnabled = SemDisplayQualityFeature.ENABLED;
        String str2 = SemDisplayQualityFeature.PLATFORM;
        this.mPlatform = str2;
        boolean z = SemDisplayQualityFeature.OUTDOOR_VISIBILITY_SUPPORT;
        this.mSupportOutdoor = z;
        boolean z2 = SemDisplayQualityFeature.ADAPTIVE_SYNC_SUPPORT;
        this.mSupportAdaptiveSync = z2;
        this.displayQuality = null;
        this.mLock = new Object();
        this.mContext = context;
        this.displayQuality = new SemDisplayQuality(context);
        Slog.d(TAG, "platform:" + str2 + " outdoor:" + z + " adaptiveSync:" + z2);
    }

    public void enhanceDisplayOutdoorVisibilityByLux(int i) {
        synchronized (this.mLock) {
            SemDisplayQuality semDisplayQuality = this.displayQuality;
            if (semDisplayQuality == null || !this.mSupportOutdoor) {
                return;
            }
            try {
                semDisplayQuality.enhanceOutdoorVisibilityByLux(i);
            } catch (Exception e) {
                Slog.e(TAG, "enhanceOutdoorVisibilityByLux", e);
            }
        }
    }

    public void setAdaptiveSync(boolean z) {
        synchronized (this.mLock) {
            SemDisplayQuality semDisplayQuality = this.displayQuality;
            if (semDisplayQuality == null || !this.mSupportAdaptiveSync) {
                return;
            }
            try {
                semDisplayQuality.setAdaptiveSync(z);
            } catch (Exception e) {
                Slog.e(TAG, "setAdaptiveSync", e);
            }
        }
    }
}
