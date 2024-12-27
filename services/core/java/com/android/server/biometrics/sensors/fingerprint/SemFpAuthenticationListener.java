package com.android.server.biometrics.sensors.fingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface SemFpAuthenticationListener {
    default void onAuthenticationAcquire(int i, int i2, int i3) {
    }

    default void onAuthenticationFinished(int i, int i2) {
    }

    default void onAuthenticationResult(int i) {
    }

    default void onAuthenticationStarted(int i, int i2) {
    }
}
