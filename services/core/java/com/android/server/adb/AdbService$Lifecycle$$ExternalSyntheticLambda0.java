package com.android.server.adb;

import android.util.sysfwutil.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class AdbService$Lifecycle$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AdbService adbService = (AdbService) obj;
        adbService.getClass();
        Slog.d("AdbService", "boot completed");
        AdbDebuggingManager adbDebuggingManager = adbService.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.setAdbEnabled(adbService.mIsAdbUsbEnabled, (byte) 0);
            adbService.mDebuggingManager.setAdbEnabled(adbService.mIsAdbWifiEnabled, (byte) 1);
        }
    }
}
