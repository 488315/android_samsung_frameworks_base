package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$$ExternalSyntheticLambda1;
import com.samsung.android.biometrics.app.setting.Utils;

import java.util.ArrayList;

public final class HbmController
        implements DisplayStateManager.LimitDisplayStateCallback, HbmListener {
    public final DisplayStateManager mDisplayStateManager;
    final Handler mH;
    public boolean mHasHbmRequest;
    public long mHbmOperatingTime;
    public final HbmProvider mHbmProvider;
    HbmState mState = (HbmInitState) HbmInitState.sInstance.get();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    public HbmController(
            Context context, DisplayStateManager displayStateManager, HbmProvider hbmProvider) {
        this.mH = new Handler(context.getMainLooper());
        this.mHbmProvider = hbmProvider;
        this.mDisplayStateManager = displayStateManager;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        if (Utils.DEBUG) {
            if (z) {
                this.mHbmOperatingTime = SystemClock.elapsedRealtime();
            } else {
                Log.w(
                        "BSS_HbmController",
                        "[[[[[ HBM operating time = "
                                + (SystemClock.elapsedRealtime() - this.mHbmOperatingTime)
                                + " ms ]]]]]");
                this.mHbmOperatingTime = 0L;
            }
        }
        Object obj = this.mState;
        if (obj instanceof HbmListener) {
            ((HbmListener) obj).onHbmChanged(z);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.LimitDisplayStateCallback
    public final void onLimitDisplayStateChanged(final boolean z) {
        this.mH.post(
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.fingerprint.HbmController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        HbmController hbmController = HbmController.this;
                        boolean z2 = z;
                        hbmController.getClass();
                        if (Utils.DEBUG) {
                            Log.d(
                                    "BSS_HbmController",
                                    "onLimitDisplayStateChanged: "
                                            + z2
                                            + ", "
                                            + hbmController.mState.getTag());
                        }
                        Object obj = hbmController.mState;
                        if (obj instanceof DisplayStateManager.LimitDisplayStateCallback) {
                            ((DisplayStateManager.LimitDisplayStateCallback) obj)
                                    .onLimitDisplayStateChanged(z2);
                        }
                    }
                });
    }

    public void setCurrentState(HbmState hbmState) {
        Log.i(
                "BSS_HbmController",
                "setCurrentState : " + this.mState.getTag() + " -> " + hbmState.getTag());
        this.mState = hbmState;
        if (this.mHasHbmRequest
                || hbmState == ((HbmDisplayOnState) HbmDisplayOnState.sInstance.get())) {
            return;
        }
        this.mState.turnOffHbm();
    }

    public final void stop() {
        this.mState.stop();
        setCurrentState((HbmInitState) HbmInitState.sInstance.get());
        this.mHbmProvider.turnOffHBM();
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        ((ArrayList) displayStateManager.mHbmListeners).remove(this);
        displayStateManager.mBgHandler.post(
                new DisplayStateManager$$ExternalSyntheticLambda1(displayStateManager, this, 1));
        if (Utils.Config.FEATURE_SUPPORT_DISPLAY_SEAMLESS_MODE) {
            displayStateManager.releaseRefreshRateForSeamlessMode();
        }
    }
}
