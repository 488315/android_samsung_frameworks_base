package com.android.server.biometrics;

import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthSession$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((FingerprintSensorPropertiesInternal) obj).sensorId);
            case 1:
                BiometricSensor biometricSensor = (BiometricSensor) obj;
                return Boolean.valueOf(
                        biometricSensor.mCookie != 0 && biometricSensor.mSensorState == 2);
            case 2:
                return Boolean.TRUE;
            case 3:
                BiometricSensor biometricSensor2 = (BiometricSensor) obj;
                return Boolean.valueOf(
                        (biometricSensor2.modality == 2 || biometricSensor2.mCookie == 0)
                                ? false
                                : true);
            default:
                return Boolean.valueOf(((BiometricSensor) obj).modality == 2);
        }
    }
}
