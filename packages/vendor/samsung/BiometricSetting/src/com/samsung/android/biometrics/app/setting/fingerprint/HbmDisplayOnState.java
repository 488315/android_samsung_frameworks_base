package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.util.Singleton;

public final class HbmDisplayOnState extends HbmState implements HbmListener {
    public static final AnonymousClass1 sInstance = new AnonymousClass1();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmDisplayOnState$1, reason: invalid class name */
    public final class AnonymousClass1 extends Singleton {
        public final Object create() {
            return new HbmDisplayOnState();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "DisplayOnState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {
        if (i == 1) {
            turnOffHbm();
            HbmController.this.setCurrentState(
                    (HbmDisplayOffState) HbmDisplayOffState.sInstance.get());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {
        Log.i("BSS_HbmDisplayOnState", "handleHbmOff");
        HbmController.this.mHbmProvider.turnOffHBM();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {
        Log.i("BSS_HbmDisplayOnState", "handleHbmOn");
        HbmController.this.mHbmProvider.turnOnHBM();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {}
}
