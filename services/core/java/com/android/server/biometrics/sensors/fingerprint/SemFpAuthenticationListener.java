package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemFpAuthenticationListener {
  default void onAuthenticationAcquire(int i, int i2, int i3, int i4) {}

  default void onAuthenticationError(int i, int i2, int i3) {}

  default void onAuthenticationFinished(int i, int i2) {}

  default void onAuthenticationResult(int i, int i2, int i3) {}

  default void onAuthenticationStarted(int i, int i2) {}
}
