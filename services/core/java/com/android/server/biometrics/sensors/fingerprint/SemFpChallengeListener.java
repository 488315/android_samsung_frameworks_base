package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemFpChallengeListener {
    void onChallengeGenerated(int i, long j, Runnable runnable);

    void onChallengeRevoked(int i);
}
