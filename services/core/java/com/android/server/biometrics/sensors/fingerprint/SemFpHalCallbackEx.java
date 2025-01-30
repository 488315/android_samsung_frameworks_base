package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemFpHalCallbackEx {
  void onAcquire(int i, int i2, int i3, int i4, int i5);

  void onAuthenticated(int i, int i2, int i3);

  void onChallengeGenerated(int i, int i2, long j);

  void onChallengeRevoked(int i, int i2, long j);

  void onEnrolled(int i, int i2);

  void onError(int i, int i2, int i3, int i4, int i5);

  void onGestureEvent(int i, int i2);

  void onSpenEvent(int i, int i2);
}
