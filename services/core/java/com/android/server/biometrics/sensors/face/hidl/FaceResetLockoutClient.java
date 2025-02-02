package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.HalClientMonitor;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class FaceResetLockoutClient extends HalClientMonitor {
  public final ArrayList mHardwareAuthToken;

  public abstract void daemonResetLockout(ArrayList arrayList);

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public int getProtoEnum() {
    return 12;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public boolean interruptsPrecedingClients() {
    return false;
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void unableToStart() {}

  public FaceResetLockoutClient(
      Context context,
      Supplier supplier,
      int i,
      String str,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      byte[] bArr) {
    super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
    this.mHardwareAuthToken = new ArrayList();
    if (bArr == null) {
      return;
    }
    for (byte b : bArr) {
      this.mHardwareAuthToken.add(Byte.valueOf(b));
    }
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    startHalOperation();
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor
  public void startHalOperation() {
    try {
      daemonResetLockout(this.mHardwareAuthToken);
      this.mCallback.onClientFinished(this, true);
    } catch (RemoteException e) {
      Slog.e("FaceResetLockoutClient", "Unable to reset lockout", e);
      this.mCallback.onClientFinished(this, false);
    }
  }
}
