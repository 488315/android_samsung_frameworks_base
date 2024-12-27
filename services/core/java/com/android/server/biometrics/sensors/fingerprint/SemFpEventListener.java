package com.android.server.biometrics.sensors.fingerprint;

public interface SemFpEventListener {
    default void onGestureEvent(int i) {}

    default void onSpenEvent(int i) {}
}
