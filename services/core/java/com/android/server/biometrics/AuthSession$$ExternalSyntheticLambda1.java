package com.android.server.biometrics;

import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

import java.util.function.Function;

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
