package com.samsung.android.displayquality;

import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Slog;
import vendor.samsung.hardware.displayquality.ISehDisplayQuality;

/* loaded from: classes2.dex */
public class SemDisplayQualityAidlClient {
    private static final String AIDL_CLIENT_NAME = "vendor.samsung.hardware.displayquality.ISehDisplayQuality/default";
    public static final int RESULT_DISABLED = 0;
    public static final int RESULT_ENABLED = 1;
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_NO_SUPPORT = 3;
    private static final String TAG = "SemDisplayQualityAidlClient";
    private ISehDisplayQuality mSehDisplayQuality;

    public SemDisplayQualityAidlClient() {
        this.mSehDisplayQuality = null;
        try {
            IBinder waitForService = ServiceManager.waitForService(AIDL_CLIENT_NAME);
            if (waitForService == null) {
                Slog.e(TAG, "vendor.samsung.hardware.displayquality.ISehDisplayQuality/default getService failed");
            } else {
                ISehDisplayQuality asInterface = ISehDisplayQuality.Stub.asInterface(waitForService);
                this.mSehDisplayQuality = asInterface;
                if (asInterface == null) {
                    Slog.e(TAG, "AIDL asInterface failed");
                } else {
                    Slog.d(TAG, "AIDL asInterface success, version " + this.mSehDisplayQuality.getInterfaceVersion());
                }
            }
        } catch (Exception unused) {
            Slog.e(TAG, "AIDL failed");
        }
    }

    public int setOutdoorVisibilityEnhancerEnabled(boolean z) {
        ISehDisplayQuality iSehDisplayQuality = this.mSehDisplayQuality;
        if (iSehDisplayQuality != null) {
            try {
                return iSehDisplayQuality.setOutdoorVisibilityEnhancerEnabled(z);
            } catch (Exception unused) {
                Slog.e(TAG, "setOutdoorVisibilityEnhancerEnabled failed");
                return 0;
            }
        }
        Slog.e(TAG, "mSehDisplayQuality is null");
        return 0;
    }
}
