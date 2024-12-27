package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface BiometricUtils {
    void addBiometricForUser(Context context, int i, BiometricAuthenticator.Identifier identifier);

    List getBiometricsForUser(Context context, int i);

    void removeBiometricForUser(Context context, int i, int i2);

    void setInvalidationInProgress(Context context, int i, boolean z);
}
