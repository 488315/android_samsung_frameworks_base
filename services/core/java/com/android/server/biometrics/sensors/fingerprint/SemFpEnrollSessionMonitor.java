package com.android.server.biometrics.sensors.fingerprint;

import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.biometrics.SemBiometricFeature;

/* loaded from: classes.dex */
public class SemFpEnrollSessionMonitor
    implements SemFpChallengeListener, SemBiometricDisplayMonitor.Callback {
  public final SemBiometricDisplayStateMonitor mDisplayStateMonitor;
  public final SparseArray mEnrollSessions = new SparseArray();

  public SemFpEnrollSessionMonitor(
      SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor) {
    this.mDisplayStateMonitor = semBiometricDisplayStateMonitor;
  }

  public void onProviderRegistered(ServiceProvider serviceProvider) {
    serviceProvider.semAddChallengeListener(this);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
  public void onDisplayOff() {
    if (SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD) {
      return;
    }
    revokeChallengeInternally();
  }

  public final void revokeChallengeInternally() {
    synchronized (this.mEnrollSessions) {
      for (int i = 0; i < this.mEnrollSessions.size(); i++) {
        Pair pair = (Pair) this.mEnrollSessions.valueAt(i);
        Slog.i(
            "FingerprintService",
            "Revoke challenge due to screen off, " + this.mEnrollSessions.keyAt(i));
        ((Runnable) pair.first).run();
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpChallengeListener
  public void onChallengeGenerated(int i, long j, Runnable runnable) {
    synchronized (this.mEnrollSessions) {
      this.mEnrollSessions.put(i, new Pair(runnable, Long.valueOf(j)));
      if (this.mEnrollSessions.size() == 1) {
        this.mDisplayStateMonitor.registerCallback(this);
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpChallengeListener
  public void onChallengeRevoked(int i) {
    synchronized (this.mEnrollSessions) {
      this.mEnrollSessions.remove(i);
      if (this.mEnrollSessions.size() == 0) {
        this.mDisplayStateMonitor.unregisterCallback(this);
      }
    }
  }

  public void revokeChallenge(int i) {
    synchronized (this.mEnrollSessions) {
      Pair pair = (Pair) this.mEnrollSessions.get(i);
      if (pair != null) {
        ((Runnable) pair.first).run();
      }
    }
  }

  public boolean isEnrollSession(int i) {
    boolean z;
    synchronized (this.mEnrollSessions) {
      z = this.mEnrollSessions.get(i) != null;
    }
    return z;
  }
}
