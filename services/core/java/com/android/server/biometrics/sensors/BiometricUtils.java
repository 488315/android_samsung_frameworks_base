package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;

import java.util.List;

public interface BiometricUtils {
    void addBiometricForUser(Context context, int i, BiometricAuthenticator.Identifier identifier);

    List getBiometricsForUser(Context context, int i);

    void removeBiometricForUser(Context context, int i, int i2);

    void setInvalidationInProgress(Context context, int i, boolean z);
}
