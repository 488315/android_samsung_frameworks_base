package com.android.server.biometrics.log;

import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;

/* loaded from: classes.dex */
public class CallbackWithProbe implements ClientMonitorCallback {
  public final Probe mProbe;
  public final boolean mStartWithClient;

  public CallbackWithProbe(Probe probe, boolean z) {
    this.mProbe = probe;
    this.mStartWithClient = z;
  }

  @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
  public void onClientStarted(BaseClientMonitor baseClientMonitor) {
    if (this.mStartWithClient) {
      this.mProbe.enable();
    }
  }

  @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
  public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
    this.mProbe.destroy();
  }

  public Probe getProbe() {
    return this.mProbe;
  }
}
