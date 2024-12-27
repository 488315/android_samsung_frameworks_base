package com.samsung.android.displayquality;

import android.content.Context;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Slog;
import com.samsung.android.displayquality.ISemDisplayQualityManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public final class SemDisplayAdaptiveSyncManager {
    public static final int RESULT_ERROR = 1;
    public static final int RESULT_NO_SUPPORT = 2;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "SemDisplayAdaptiveSyncManager";
    private static final boolean mEnabled = SemDisplayQualityFeature.ENABLED;
    private static final boolean mSupportAdaptiveSync = SemDisplayQualityFeature.ADAPTIVE_SYNC_SUPPORT;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Result {
    }

    private SemDisplayAdaptiveSyncManager() {
    }

    public static int setAdaptiveSyncEnabled(boolean enabled) {
        IBinder b = ServiceManager.getService(Context.SEM_DISPLAY_QUALITY_SERVICE);
        ISemDisplayQualityManager service = ISemDisplayQualityManager.Stub.asInterface(b);
        if (!mEnabled || !mSupportAdaptiveSync) {
            Slog.e(TAG, "SemDisplayAdaptiveSyncManager is not supported");
            return 2;
        }
        if (service == null) {
            Slog.e(TAG, "SemDisplayAdaptiveSyncManagerService is null");
            return 1;
        }
        try {
            service.setAdaptiveSync(enabled);
            return 0;
        } catch (Exception e) {
            Slog.e(TAG, "setAdaptiveSync", e);
            return 1;
        }
    }
}
