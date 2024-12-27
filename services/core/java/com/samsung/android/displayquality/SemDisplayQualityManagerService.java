package com.samsung.android.displayquality;

import android.content.Context;
import android.os.Build;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
        StringBuilder sb = new StringBuilder("platform:");
        sb.append(str2);
        sb.append(" outdoor:");
        sb.append(z);
        sb.append(" adaptiveSync:");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(TAG, sb, z2);
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
