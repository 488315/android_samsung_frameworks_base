package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricAuthenticator;

public interface EnumerateConsumer {
    void onEnumerationResult(BiometricAuthenticator.Identifier identifier, int i);
}
