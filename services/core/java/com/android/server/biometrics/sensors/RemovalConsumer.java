package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricAuthenticator;

public interface RemovalConsumer {
    void onRemoved(BiometricAuthenticator.Identifier identifier, int i);
}
