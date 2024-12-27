package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricAuthenticator;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface AuthenticationConsumer {
    boolean canIgnoreLockout();

    void onAuthenticated(
            BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList);
}
