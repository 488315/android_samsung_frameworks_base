package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.GenerateChallengeClient;

public final class FingerprintGenerateChallengeClient extends GenerateChallengeClient {
    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.generateChallenge();
        } catch (RemoteException e) {
            Slog.e("FingerprintGenerateChallengeClient", "Unable to generateChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
