package com.android.systemui.util;

import android.os.ServiceManager;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class SafeUIState {
    private static final String TAG = "SafeUIState";
    private static int sSafeMode = -1;

    /* JADX WARN: Multi-variable type inference failed */
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
