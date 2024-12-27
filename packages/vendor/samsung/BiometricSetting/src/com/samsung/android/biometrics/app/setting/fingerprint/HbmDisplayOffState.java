package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.util.Singleton;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class HbmDisplayOffState extends HbmState
        implements DisplayStateManager.LimitDisplayStateCallback {
    public static final AnonymousClass1 sInstance = new AnonymousClass1();

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmDisplayOffState$1, reason: invalid class name */
    public final class AnonymousClass1 extends Singleton {
        public final Object create() {
            return new HbmDisplayOffState();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "DisplayOffState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {
        if (i == 1002 || i == 2) {
            HbmController.this.mHbmProvider.turnOnHBM();
            HbmController.this.setCurrentState(
                    (HbmDisplayOnState) HbmDisplayOnState.sInstance.get());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.LimitDisplayStateCallback
    public final void onLimitDisplayStateChanged(boolean z) {
        if (z) {
            HbmDisplayLimitState.AnonymousClass1 anonymousClass1 = HbmDisplayLimitState.sInstance;
            ((HbmDisplayLimitState) anonymousClass1.get()).turnOnHbm();
            HbmController.this.setCurrentState((HbmDisplayLimitState) anonymousClass1.get());
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {
        Log.i("BSS_HbmDisplayOffState", "turnOnHbm");
        setDisplayStateLimit(true);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {}
}
