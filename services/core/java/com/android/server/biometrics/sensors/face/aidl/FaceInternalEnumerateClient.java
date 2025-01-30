package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.InternalEnumerateClient;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceInternalEnumerateClient extends InternalEnumerateClient {
  public FaceInternalEnumerateClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      int i,
      String str,
      List list,
      BiometricUtils biometricUtils,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext) {
    super(
        context,
        supplier,
        iBinder,
        i,
        str,
        list,
        biometricUtils,
        i2,
        biometricLogger,
        biometricContext);
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    try {
      long currentTimeMillis = System.currentTimeMillis();
      Slog.w("FaceInternalEnumerateClient", "enumerateEnrollments START");
      ((AidlSession) getFreshDaemon()).getSession().enumerateEnrollments();
      Slog.w(
          "FaceInternalEnumerateClient",
          "enumerateEnrollments FINISH ("
              + (System.currentTimeMillis() - currentTimeMillis)
              + "ms)");
    } catch (RemoteException e) {
      Slog.e("FaceInternalEnumerateClient", "Remote exception when requesting enumerate", e);
      this.mCallback.onClientFinished(this, false);
    }
  }
}
