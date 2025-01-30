package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemFpEnrollmentListener {
  default void onEnrollAcquire(int i, int i2, int i3, int i4) {}

  default void onEnrollError(int i, int i2, int i3) {}

  default void onEnrollFinished(int i, int i2) {}

  void onEnrollStarted(int i, int i2);
}
