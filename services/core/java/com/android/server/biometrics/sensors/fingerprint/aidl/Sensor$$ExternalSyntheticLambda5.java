package com.android.server.biometrics.sensors.fingerprint.aidl;

import java.util.function.Supplier;

public final /* synthetic */ class Sensor$$ExternalSyntheticLambda5 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Sensor$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                return ((FingerprintProvider) obj).getHalInstance();
            default:
                return ((AidlSession) Sensor.this.mLazySession.get()).mSession;
        }
    }
}
