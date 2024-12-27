package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.InvalidationClient;

public final class FingerprintInvalidationClient extends InvalidationClient {
    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.invalidateAuthenticatorId();
        } catch (RemoteException e) {
            Slog.e("FingerprintInvalidationClient", "Remote exception", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
