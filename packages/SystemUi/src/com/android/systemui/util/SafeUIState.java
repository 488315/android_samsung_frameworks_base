package com.android.systemui.util;

import android.os.ServiceManager;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SafeUIState {
    public static int sSafeMode = -1;

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isSysUiSafeModeEnabled() {
        int i;
        if (sSafeMode == -1) {
            try {
                i = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")).isSysUiSafeModeEnabled();
            } catch (Exception e) {
                Slog.e("SafeUIState", "SAFEMODE Exception occurs! " + e.getMessage());
                i = 0;
            }
            sSafeMode = i;
        }
        return sSafeMode == 1;
    }
}
