package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.HalClientMonitor;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceGetAuthenticatorIdClient extends HalClientMonitor {
  public final Map mAuthenticatorIds;

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public int getProtoEnum() {
    return 5;
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void unableToStart() {}

  public FaceGetAuthenticatorIdClient(
      Context context,
      Supplier supplier,
      int i,
      String str,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      Map map) {
    super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
    this.mAuthenticatorIds = map;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    startHalOperation();
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    try {
      if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
        SemFaceServiceExImpl.getInstance().daemonGetAuthenticatorId();
      } else {
        long currentTimeMillis = System.currentTimeMillis();
        Slog.w("FaceGetAuthenticatorIdClient", "getAuthenticatorId START");
        ((AidlSession) getFreshDaemon()).getSession().getAuthenticatorId();
        Slog.w(
            "FaceGetAuthenticatorIdClient",
            "getAuthenticatorId FINISH ("
                + (System.currentTimeMillis() - currentTimeMillis)
                + "ms)");
      }
    } catch (RemoteException e) {
      Slog.e("FaceGetAuthenticatorIdClient", "Remote exception", e);
    }
  }

  public void onAuthenticatorIdRetrieved(long j) {
    this.mAuthenticatorIds.put(Integer.valueOf(getTargetUserId()), Long.valueOf(j));
    this.mCallback.onClientFinished(this, true);
  }
}
