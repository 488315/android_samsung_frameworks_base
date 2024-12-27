package com.android.server.biometrics;

import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

import java.util.function.Predicate;

public final /* synthetic */ class AuthSession$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((FingerprintSensorPropertiesInternal) obj).isAnySidefpsType();
    }
}
