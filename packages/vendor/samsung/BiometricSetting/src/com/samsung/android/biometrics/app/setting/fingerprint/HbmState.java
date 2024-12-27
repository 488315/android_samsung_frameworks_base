package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.PowerManager;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;

public abstract class HbmState {
    public HbmController.AnonymousClass1 mProvider;

    public abstract String getTag();

    public abstract void handleDisplayStateChanged(int i);

    public final void setDisplayStateLimit(boolean z) {
        DisplayStateManager displayStateManager = HbmController.this.mDisplayStateManager;
        DisplayStateManager.Injector injector = displayStateManager.mInjector;
        Context context = displayStateManager.mContext;
        if (injector.mPartialWakeLock == null) {
            PowerManager.WakeLock newWakeLock =
                    ((PowerManager) context.getSystemService(PowerManager.class))
                            .newWakeLock(1, "BSS_DisplayStateManager:P");
            injector.mPartialWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(false);
        }
        injector.mPartialWakeLock.acquire(2000L);
        displayStateManager.mIsLimitedDisplayInProgress.set(true);
        if (z) {
            displayStateManager.mBgHandler.removeMessages(2);
            displayStateManager.mBgHandler.sendEmptyMessageDelayed(1, 0L);
        } else {
            displayStateManager.mBgHandler.removeMessages(1);
            displayStateManager.mBgHandler.sendEmptyMessageDelayed(2, 0L);
        }
    }

    public abstract void turnOffHbm();

    public abstract void turnOnHbm();

    public void stop() {}
}
