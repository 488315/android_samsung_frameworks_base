package com.android.server.biometrics.sensors.fingerprint;

public interface SemFpEnrollmentListener {
    default void onEnrollAcquire(int i, int i2) {}

    default void onEnrollFinished(int i, int i2) {}

    void onEnrollStarted(int i, int i2);
}
