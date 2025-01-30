package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.GenerateChallengeClient;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FingerprintGenerateChallengeClient extends GenerateChallengeClient {
  public long mChallenge;

  public FingerprintGenerateChallengeClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      int i,
      String str,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext) {
    super(
        context,
        supplier,
        iBinder,
        clientMonitorCallbackConverter,
        i,
        str,
        i2,
        biometricLogger,
        biometricContext);
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    if (getListener() == null) {
      Slog.w("FingerprintGenerateChallengeClient", "Client not listening");
      this.mCallback.onClientFinished(this, false);
      return;
    }
    long elapsedRealtime = SystemClock.elapsedRealtime();
    try {
      long preEnroll = ((IBiometricsFingerprint) getFreshDaemon()).preEnroll();
      if (Utils.DEBUG) {
        Slog.i(
            "FingerprintGenerateChallengeClient",
            "preEnroll FP_FINISH (" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms)");
      }
      this.mChallenge = preEnroll;
      try {
        getListener().onChallengeGenerated(getSensorId(), getTargetUserId(), preEnroll);
        this.mCallback.onClientFinished(this, true);
      } catch (RemoteException e) {
        Slog.e("FingerprintGenerateChallengeClient", "Remote exception", e);
        this.mCallback.onClientFinished(this, false);
      }
    } catch (RemoteException e2) {
      Slog.e("FingerprintGenerateChallengeClient", "preEnroll failed", e2);
      this.mCallback.onClientFinished(this, false);
    }
  }

  public long getChallenge() {
    return this.mChallenge;
  }
}
