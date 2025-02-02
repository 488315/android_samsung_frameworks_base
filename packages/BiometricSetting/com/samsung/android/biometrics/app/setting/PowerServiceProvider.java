package com.samsung.android.biometrics.app.setting;

/* loaded from: classes.dex */
public interface PowerServiceProvider {
    void acquireWakeLock(long j);

    boolean isPowerSaveMode();

    void releaseWakeLock();
}
