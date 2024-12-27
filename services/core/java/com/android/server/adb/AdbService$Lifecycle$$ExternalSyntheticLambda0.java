package com.android.server.adb;

import android.util.sysfwutil.Slog;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AdbService$Lifecycle$$ExternalSyntheticLambda0 implements Consumer {
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
