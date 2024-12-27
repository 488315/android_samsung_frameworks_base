package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.fingerprint.Fingerprint;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda3
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((Fingerprint) obj).getBiometricId());
    }
}
