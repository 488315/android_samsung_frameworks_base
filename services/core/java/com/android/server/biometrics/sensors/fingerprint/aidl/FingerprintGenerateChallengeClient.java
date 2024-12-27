package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.GenerateChallengeClient;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
