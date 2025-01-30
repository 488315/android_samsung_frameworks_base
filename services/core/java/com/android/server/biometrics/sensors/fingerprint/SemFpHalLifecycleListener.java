package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemFpHalLifecycleListener {
    void onHalStarted(ServiceProvider serviceProvider);

    default void onHalStop(ServiceProvider serviceProvider) {
    }
}
