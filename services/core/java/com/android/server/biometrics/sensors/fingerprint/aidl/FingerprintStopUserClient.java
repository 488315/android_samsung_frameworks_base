package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.ISession;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.StopUserClient;

public final class FingerprintStopUserClient extends StopUserClient {
    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ((ISession) this.mLazyDaemon.get()).close();
        } catch (RemoteException e) {
            Slog.e("FingerprintStopUserClient", "Remote exception", e);
            getCallback().onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
