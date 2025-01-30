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
import com.android.server.biometrics.sensors.RevokeChallengeClient;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FingerprintRevokeChallengeClient extends RevokeChallengeClient {
  public FingerprintRevokeChallengeClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      int i,
      String str,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext) {
    super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    long elapsedRealtime = SystemClock.elapsedRealtime();
    try {
      ((IBiometricsFingerprint) getFreshDaemon()).postEnroll();
      this.mCallback.onClientFinished(this, true);
    } catch (RemoteException e) {
      Slog.e("FingerprintRevokeChallengeClient", "revokeChallenge/postEnroll failed", e);
      this.mCallback.onClientFinished(this, false);
    }
    if (Utils.DEBUG) {
      Slog.i(
          "FingerprintRevokeChallengeClient",
          "postEnroll FP_FINISH (" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms)");
    }
  }
}
