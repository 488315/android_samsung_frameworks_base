package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.util.Singleton;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;

/* loaded from: classes.dex */
public final class HbmDisplayLimitState extends HbmState implements HbmListener, DisplayStateManager.LimitDisplayStateCallback {
    private static final Singleton<HbmDisplayLimitState> sInstance = new C02391();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmDisplayLimitState$1 */
    final class C02391 extends Singleton<HbmDisplayLimitState> {
        protected final Object create() {
            return new HbmDisplayLimitState(0);
        }
    }

    /* synthetic */ HbmDisplayLimitState(int i) {
        this();
    }

    public static HbmDisplayLimitState get() {
        return (HbmDisplayLimitState) sInstance.get();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "DisplayLimitState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {
        HbmProvider hbmProvider;
        HbmProvider hbmProvider2;
        if (i == 2) {
            hbmProvider2 = HbmController.this.mHbmProvider;
            hbmProvider2.turnOnHBM();
            setState(HbmDisplayOnState.get());
        } else if (i == 1) {
            hbmProvider = HbmController.this.mHbmProvider;
            hbmProvider.turnOffHBM();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        if (z) {
            Log.d("BSS_HbmDisplayLimitState", "onHbmChanged: enabled");
        } else {
            setDisplayStateLimit(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.LimitDisplayStateCallback
    public final void onLimitDisplayStateChanged(boolean z) {
        DisplayStateManager displayStateManager;
        if (z) {
            return;
        }
        displayStateManager = HbmController.this.mDisplayStateManager;
        displayStateManager.turnOffDoze("BSS_HbmDisplayLimitState");
        setState(HbmDisplayOffState.get());
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void stop() {
        DisplayStateManager displayStateManager;
        displayStateManager = HbmController.this.mDisplayStateManager;
        if (displayStateManager.getDisplayStateLogical() == 2) {
            setDisplayStateLimit(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {
        HbmProvider hbmProvider;
        Log.i("BSS_HbmDisplayLimitState", "turnOffHbm");
        hbmProvider = HbmController.this.mHbmProvider;
        hbmProvider.turnOffHBM();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {
        DisplayStateManager displayStateManager;
        HbmProvider hbmProvider;
        Log.i("BSS_HbmDisplayLimitState", "turnOnHbm");
        displayStateManager = HbmController.this.mDisplayStateManager;
        displayStateManager.turnOnDoze("BSS_HbmDisplayLimitState");
        hbmProvider = HbmController.this.mHbmProvider;
        hbmProvider.turnOnHBM();
    }

    private HbmDisplayLimitState() {
    }
}
