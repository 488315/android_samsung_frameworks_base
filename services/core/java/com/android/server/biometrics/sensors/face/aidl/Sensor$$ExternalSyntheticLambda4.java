package com.android.server.biometrics.sensors.face.aidl;

import java.util.function.Supplier;

public final /* synthetic */ class Sensor$$ExternalSyntheticLambda4 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Sensor$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                return ((FaceProvider) obj).getHalInstance();
            default:
                return ((AidlSession) Sensor.this.mLazySession.get()).mSession;
        }
    }
}
