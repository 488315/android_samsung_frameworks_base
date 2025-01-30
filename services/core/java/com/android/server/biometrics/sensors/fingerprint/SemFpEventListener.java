package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemFpEventListener {
    default void onError(int i, int i2, int i3, int i4) {
    }

    default void onGestureEvent(int i, int i2) {
    }

    default void onSpenEvent(int i, int i2) {
    }
}
