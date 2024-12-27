package com.samsung.android.biometrics.app.setting;

public interface PowerServiceProvider {
    void acquireWakeLock(long j);

    boolean isPowerSaveMode();

    void releaseWakeLock();
}
