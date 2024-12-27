package com.android.systemui.util;

import android.os.ServiceManager;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;

public class SafeUIState {
    private static final String TAG = "SafeUIState";
    private static int sSafeMode = -1;

    public static boolean isSysUiSafeModeEnabled() {
        int i;
        if (sSafeMode == -1) {
            try {
                i = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")).isSysUiSafeModeEnabled();
            } catch (Exception e) {
                Slog.e(TAG, "SAFEMODE Exception occurs! " + e.getMessage());
                i = 0;
            }
            sSafeMode = i;
        }
        return sSafeMode == 1;
    }
}
