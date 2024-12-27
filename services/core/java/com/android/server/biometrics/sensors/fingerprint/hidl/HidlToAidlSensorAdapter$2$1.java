package com.android.server.biometrics.sensors.fingerprint.hidl;

import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.StopUserClient;

public final class HidlToAidlSensorAdapter$2$1 extends StopUserClient {
    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        onUserStopped();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        throw null;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
        getCallback().onClientFinished(this, false);
    }
}
