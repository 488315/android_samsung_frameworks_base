package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.face.ISession;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.StopUserClient;

public final class FaceStopUserClient extends StopUserClient {
    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
                SemFaceServiceExImpl.getInstance().daemonClose();
            } else {
                ((ISession) this.mLazyDaemon.get()).close();
            }
        } catch (RemoteException e) {
            Slog.e("FaceStopUserClient", "Remote exception", e);
            getCallback().onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
