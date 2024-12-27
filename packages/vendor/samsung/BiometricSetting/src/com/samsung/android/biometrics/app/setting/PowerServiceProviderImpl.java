package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public final class PowerServiceProviderImpl implements PowerServiceProvider {
    public final PowerManager.WakeLock mDrawWakeLock;
    public final PowerManager.WakeLock mPartialWakeLock;
    public final PowerManager mPm;

    public PowerServiceProviderImpl(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mPm = powerManager;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "BiometricUI:P");
        this.mPartialWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(128, "BiometricUI:D");
        this.mDrawWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
    }

    @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
    public final void acquireWakeLock(long j) {
        Log.d("BSS_PMS", "acquireWakeLock: time = " + j);
        try {
            this.mPartialWakeLock.acquire(j);
        } catch (Exception e) {
            Log.e("BSS_PMS", "acquireWakeLock: p=" + e.getMessage());
        }
        try {
            this.mDrawWakeLock.acquire(j);
        } catch (Exception e2) {
            Log.e("BSS_PMS", "acquireWakeLock: d=" + e2.getMessage());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
    public final boolean isPowerSaveMode() {
        return this.mPm.isPowerSaveMode();
    }

    @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
    public final void releaseWakeLock() {
        try {
            if (this.mPartialWakeLock.isHeld()) {
                this.mPartialWakeLock.release();
            }
        } catch (Exception e) {
            Log.e("BSS_PMS", "releaseWakeLock: p=" + e.getMessage());
        }
        try {
            if (this.mDrawWakeLock.isHeld()) {
                this.mDrawWakeLock.release();
            }
        } catch (Exception e2) {
            Log.e("BSS_PMS", "releaseWakeLock: d=" + e2.getMessage());
        }
    }
}
