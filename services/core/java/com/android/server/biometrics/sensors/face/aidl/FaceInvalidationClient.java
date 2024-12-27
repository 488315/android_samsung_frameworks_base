package com.android.server.biometrics.sensors.face.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.InvalidationClient;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
