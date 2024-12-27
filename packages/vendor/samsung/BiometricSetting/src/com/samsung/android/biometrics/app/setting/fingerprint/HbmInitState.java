package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Singleton;

public final class HbmInitState extends HbmState {
    public static final AnonymousClass1 sInstance = new AnonymousClass1();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.HbmInitState$1, reason: invalid class name */
    public final class AnonymousClass1 extends Singleton {
        public final Object create() {
            return new HbmInitState();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final String getTag() {
        return "InitState";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOffHbm() {}

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void turnOnHbm() {}

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmState
    public final void handleDisplayStateChanged(int i) {}
}
