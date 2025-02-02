package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.util.Singleton;

/* loaded from: classes.dex */
public final class HbmDisplayOnState extends HbmState implements HbmListener {
    private static final Singleton<HbmDisplayOnState> sInstance = new C02411();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmDisplayOnState$1 */
    final class C02411 extends Singleton<HbmDisplayOnState> {
        protected final Object create() {
            return new HbmDisplayOnState();
        }
    }

    public static HbmDisplayOnState get() {
        return (HbmDisplayOnState) sInstance.get();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "DisplayOnState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {
        if (i == 1) {
            turnOffHbm();
            setState(HbmDisplayOffState.get());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {
        HbmProvider hbmProvider;
        Log.i("BSS_HbmDisplayOnState", "handleHbmOff");
        hbmProvider = HbmController.this.mHbmProvider;
        hbmProvider.turnOffHBM();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {
        HbmProvider hbmProvider;
        Log.i("BSS_HbmDisplayOnState", "handleHbmOn");
        hbmProvider = HbmController.this.mHbmProvider;
        hbmProvider.turnOnHBM();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
    }
}
