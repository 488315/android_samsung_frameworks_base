package com.android.server.biometrics.sensors.fingerprint;

public interface SemFpHalLifecycleListener {
    default void onHalStarted(ServiceProvider serviceProvider) {}
}
