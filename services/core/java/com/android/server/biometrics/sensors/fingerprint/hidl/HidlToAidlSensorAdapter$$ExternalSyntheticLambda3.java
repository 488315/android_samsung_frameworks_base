package com.android.server.biometrics.sensors.fingerprint.hidl;

import java.util.function.Supplier;

public final /* synthetic */ class HidlToAidlSensorAdapter$$ExternalSyntheticLambda3
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HidlToAidlSensorAdapter f$0;

    public /* synthetic */ HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(
            HidlToAidlSensorAdapter hidlToAidlSensorAdapter, int i) {
        this.$r8$classId = i;
        this.f$0 = hidlToAidlSensorAdapter;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        HidlToAidlSensorAdapter hidlToAidlSensorAdapter = this.f$0;
        switch (i) {
            case 0:
                return hidlToAidlSensorAdapter.getSession();
            case 1:
                return Integer.valueOf(hidlToAidlSensorAdapter.mCurrentUserId);
            case 2:
                return hidlToAidlSensorAdapter.getIBiometricsFingerprint();
            case 3:
                return hidlToAidlSensorAdapter.getSession().mSession;
            default:
                return Integer.valueOf(hidlToAidlSensorAdapter.mCurrentUserId);
        }
    }
}
