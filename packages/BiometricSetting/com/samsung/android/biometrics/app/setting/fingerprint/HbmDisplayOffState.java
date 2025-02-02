package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.util.Singleton;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;

/* loaded from: classes.dex */
public final class HbmDisplayOffState extends HbmState implements DisplayStateManager.LimitDisplayStateCallback {
    private static final Singleton<HbmDisplayOffState> sInstance = new C02401();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmDisplayOffState$1 */
    final class C02401 extends Singleton<HbmDisplayOffState> {
        protected final Object create() {
            return new HbmDisplayOffState(0);
        }
    }

    /* synthetic */ HbmDisplayOffState(int i) {
        this();
    }

    public static HbmDisplayOffState get() {
        return (HbmDisplayOffState) sInstance.get();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "DisplayOffState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {
        HbmProvider hbmProvider;
        if (i == 1002 || i == 2) {
            hbmProvider = HbmController.this.mHbmProvider;
            hbmProvider.turnOnHBM();
            setState(HbmDisplayOnState.get());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.LimitDisplayStateCallback
    public final void onLimitDisplayStateChanged(boolean z) {
        if (z) {
            HbmDisplayLimitState.get().turnOnHbm();
            setState(HbmDisplayLimitState.get());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {
        Log.i("BSS_HbmDisplayOffState", "turnOnHbm");
        setDisplayStateLimit(true);
    }

    private HbmDisplayOffState() {
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {
    }
}
