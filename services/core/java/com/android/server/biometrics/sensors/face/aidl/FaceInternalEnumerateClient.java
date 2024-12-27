package com.android.server.biometrics.sensors.face.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.InternalEnumerateClient;

public class FaceInternalEnumerateClient extends InternalEnumerateClient {
    @Override // com.android.server.biometrics.sensors.InternalEnumerateClient
    public final int getModality() {
        return 4;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("FaceInternalEnumerateClient", "enumerateEnrollments START");
            ((AidlSession) this.mLazyDaemon.get()).mSession.enumerateEnrollments();
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
