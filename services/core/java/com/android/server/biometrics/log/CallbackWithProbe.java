package com.android.server.biometrics.log;

import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;

public final class CallbackWithProbe implements ClientMonitorCallback {
    public final ALSProbe mProbe;
    public final boolean mStartWithClient;

    public CallbackWithProbe(ALSProbe aLSProbe, boolean z) {
        this.mProbe = aLSProbe;
        this.mStartWithClient = z;
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
        this.mProbe.destroy();
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
        if (this.mStartWithClient) {
            this.mProbe.enable();
        }
    }
}
