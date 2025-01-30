package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.os.IBinder;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ErrorConsumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class SemFpSensorTestClient extends SemFpBaseRequestClient implements ErrorConsumer {
  public boolean mAlreadyCancelled;

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient,
            // com.android.server.biometrics.sensors.BaseClientMonitor
  public boolean interruptsPrecedingClients() {
    return true;
  }

  public SemFpSensorTestClient(
      Context context,
      BiometricContext biometricContext,
      Supplier supplier,
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      int i) {
    super(
        context,
        biometricContext,
        supplier,
        iBinder,
        clientMonitorCallbackConverter,
        i,
        0,
        "FingerprintSensorTestClient",
        true,
        3,
        0,
        null,
        null);
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void cancel() {
    if (this.mAlreadyCancelled) {
      Slog.w("FingerprintSensorTestClient", "Cancel was already requested");
    } else {
      stopHalOperation();
      this.mAlreadyCancelled = true;
    }
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void cancelWithoutStarting(ClientMonitorCallback clientMonitorCallback) {
    Slog.d("FingerprintSensorTestClient", "cancelWithoutStarting: " + this);
    handleOnError();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient,
            // com.android.server.biometrics.sensors.fingerprint.SemFpRequestCommands
  public void onRequestResult(int i) {
    super.onRequestResult(i);
    if (i == 10009) {
      this.mCallback.onClientFinished(this, true);
    }
  }

  @Override // com.android.server.biometrics.sensors.ErrorConsumer
  public void onError(int i, int i2) {
    handleOnError();
  }

  public final void handleOnError() {
    super.onRequestResult(FrameworkStatsLog.CPU_TIME_PER_UID);
    this.mCallback.onClientFinished(this, false);
  }
}
