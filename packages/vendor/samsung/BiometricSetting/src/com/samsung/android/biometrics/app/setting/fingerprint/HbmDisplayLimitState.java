package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.util.Singleton;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class HbmDisplayLimitState extends HbmState
        implements HbmListener, DisplayStateManager.LimitDisplayStateCallback {
    public static final AnonymousClass1 sInstance = new AnonymousClass1();

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmDisplayLimitState$1, reason: invalid class name */
    public final class AnonymousClass1 extends Singleton {
        public final Object create() {
            return new HbmDisplayLimitState();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "DisplayLimitState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {
        if (i == 2) {
            HbmController.this.mHbmProvider.turnOnHBM();
            HbmController.this.setCurrentState(
                    (HbmDisplayOnState) HbmDisplayOnState.sInstance.get());
        } else if (i == 1) {
            HbmController.this.mHbmProvider.turnOffHBM();
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
        if (z) {
            return;
        }
        HbmController.this.mDisplayStateManager.turnOffDoze("BSS_HbmDisplayLimitState");
        HbmController.this.setCurrentState((HbmDisplayOffState) HbmDisplayOffState.sInstance.get());
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void stop() {
        if (HbmController.this.mDisplayStateManager.mCurrentStateLogical == 2) {
            setDisplayStateLimit(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {
        Log.i("BSS_HbmDisplayLimitState", "turnOffHbm");
        HbmController.this.mHbmProvider.turnOffHBM();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {
        Log.i("BSS_HbmDisplayLimitState", "turnOnHbm");
        HbmController.this.mDisplayStateManager.turnOnDoze("BSS_HbmDisplayLimitState");
        HbmController.this.mHbmProvider.turnOnHBM();
    }
}
