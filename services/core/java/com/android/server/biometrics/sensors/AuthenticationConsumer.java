package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricAuthenticator;

import java.util.ArrayList;

public interface AuthenticationConsumer {
    boolean canIgnoreLockout();

    void onAuthenticated(
            BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList);
}
