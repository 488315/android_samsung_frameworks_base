package com.android.systemui.util;

import android.os.ServiceManager;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;

/* loaded from: classes3.dex */
public class SafeUIState {
    private static final String TAG = "SafeUIState";
    private static int sSafeMode = -1;

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isSysUiSafeModeEnabled() {
        int iIsSysUiSafeModeEnabled;
        if (sSafeMode == -1) {
            try {
                iIsSysUiSafeModeEnabled = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")).isSysUiSafeModeEnabled();
            } catch (Exception e) {
                Slog.e(TAG, "SAFEMODE Exception occurs! " + e.getMessage());
                iIsSysUiSafeModeEnabled = 0;
            }
            sSafeMode = iIsSysUiSafeModeEnabled;
        }
        return sSafeMode == 1;
    }
}
