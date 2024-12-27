package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.fingerprint.Fingerprint;

import java.util.function.Function;

public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda3
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((Fingerprint) obj).getBiometricId());
    }
}
