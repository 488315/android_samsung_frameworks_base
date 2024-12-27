package com.android.server.biometrics.sensors.fingerprint;

public interface SemFpAuthenticationListener {
    default void onAuthenticationAcquire(int i, int i2, int i3) {}

    default void onAuthenticationFinished(int i, int i2) {}

    default void onAuthenticationResult(int i) {}

    default void onAuthenticationStarted(int i, int i2) {}
}
