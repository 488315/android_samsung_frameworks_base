package com.android.server.biometrics.sensors.face.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.InvalidationClient;

public final class FaceInvalidationClient extends InvalidationClient {
    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("FaceInvalidationClient", "invalidateAuthenticatorId START");
            ((AidlSession) this.mLazyDaemon.get()).mSession.invalidateAuthenticatorId();
            Slog.w(
                    "FaceInvalidationClient",
                    "invalidateAuthenticatorId FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (RemoteException e) {
            Slog.e("FaceInvalidationClient", "Remote exception", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
