package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public final class HbmController implements DisplayStateManager.LimitDisplayStateCallback, HbmListener {
    private final DisplayStateManager mDisplayStateManager;

    /* renamed from: mH */
    @VisibleForTesting
    final Handler f26mH;
    private boolean mHasHbmRequest;
    private long mHbmOperatingTime;
    private final HbmProvider mHbmProvider;

    @VisibleForTesting
    HbmState mState = HbmInitState.get();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmController$1 */
    final class C02381 implements Provider {
        C02381() {
        }
    }

    public interface Provider {
    }

    public HbmController(Context context, DisplayStateManager displayStateManager, HbmProvider hbmProvider) {
        this.f26mH = new Handler(context.getMainLooper());
        this.mHbmProvider = hbmProvider;
        this.mDisplayStateManager = displayStateManager;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        if (Utils.DEBUG) {
            if (z) {
                this.mHbmOperatingTime = SystemClock.elapsedRealtime();
            } else {
                Log.w("BSS_HbmController", "[[[[[ HBM operating time = " + (SystemClock.elapsedRealtime() - this.mHbmOperatingTime) + " ms ]]]]]");
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
        this.f26mH.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.HbmController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HbmController hbmController = HbmController.this;
                boolean z2 = z;
                hbmController.getClass();
                if (Utils.DEBUG) {
                    Log.d("BSS_HbmController", "onLimitDisplayStateChanged: " + z2 + ", " + hbmController.mState.getTag());
                }
                Object obj = hbmController.mState;
                if (obj instanceof DisplayStateManager.LimitDisplayStateCallback) {
                    ((DisplayStateManager.LimitDisplayStateCallback) obj).onLimitDisplayStateChanged(z2);
                }
            }
        });
    }

    @VisibleForTesting
    void setCurrentState(HbmState hbmState) {
        Log.i("BSS_HbmController", "setCurrentState : " + this.mState.getTag() + " -> " + hbmState.getTag());
        this.mState = hbmState;
        if (this.mHasHbmRequest || hbmState == HbmDisplayOnState.get()) {
            return;
        }
        this.mState.turnOffHbm();
    }

    public final void start() {
        C02381 c02381 = new C02381();
        HbmDisplayOnState.get().setProvider(c02381);
        HbmDisplayOffState.get().setProvider(c02381);
        HbmDisplayLimitState.get().setProvider(c02381);
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager.isOnState()) {
            this.mState = HbmDisplayOnState.get();
        } else {
            this.mState = HbmDisplayOffState.get();
        }
        Log.d("BSS_HbmController", "initState: " + this.mState.getTag());
        displayStateManager.registerHbmListener(this);
        displayStateManager.registerLimitDisplayStateCallback(this);
        if (Utils.Config.FEATURE_SUPPORT_DISPLAY_SEAMLESS_MODE) {
            displayStateManager.acquireRefreshRateForSeamlessMode();
        }
    }

    public final void stop() {
        this.mState.stop();
        setCurrentState(HbmInitState.get());
        this.mHbmProvider.turnOffHBM();
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        displayStateManager.unregisterHbmListener(this);
        displayStateManager.unregisterLimitDisplayStateCallback(this);
        if (Utils.Config.FEATURE_SUPPORT_DISPLAY_SEAMLESS_MODE) {
            displayStateManager.releaseRefreshRateForSeamlessMode();
        }
    }

    public final void turnOffHbm() {
        Log.d("BSS_HbmController", "turnOffHbm: current state is " + this.mState.getTag());
        this.mHasHbmRequest = false;
        this.mState.turnOffHbm();
    }

    public final void turnOnHbm() {
        Log.d("BSS_HbmController", "turnOnHbm: current state is " + this.mState.getTag());
        this.mHasHbmRequest = true;
        this.mState.turnOnHbm();
    }
}
