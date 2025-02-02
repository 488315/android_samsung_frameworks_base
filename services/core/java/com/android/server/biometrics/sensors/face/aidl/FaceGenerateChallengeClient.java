package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.GenerateChallengeClient;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceGenerateChallengeClient extends GenerateChallengeClient {
  public FaceGenerateChallengeClient(
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
    try {
      if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
        SemFaceServiceExImpl.getInstance().daemonGenerateChallenge();
      } else {
        long currentTimeMillis = System.currentTimeMillis();
        Slog.w("FaceGenerateChallengeClient", "generateChallenge START");
        ((AidlSession) getFreshDaemon()).getSession().generateChallenge();
        Slog.w(
            "FaceGenerateChallengeClient",
            "generateChallenge FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms)");
      }
    } catch (RemoteException e) {
      Slog.e("FaceGenerateChallengeClient", "Unable to generateChallenge", e);
      this.mCallback.onClientFinished(this, false);
    }
  }

  public void onChallengeGenerated(int i, int i2, long j) {
    try {
      ClientMonitorCallbackConverter listener = getListener();
      if (listener == null) {
        Slog.e("FaceGenerateChallengeClient", "Listener is null in onChallengeGenerated");
        this.mCallback.onClientFinished(this, false);
      } else {
        listener.onChallengeGenerated(i, i2, j);
        this.mCallback.onClientFinished(this, true);
      }
    } catch (RemoteException e) {
      Slog.e("FaceGenerateChallengeClient", "Unable to send challenge", e);
      this.mCallback.onClientFinished(this, false);
    }
  }
}
