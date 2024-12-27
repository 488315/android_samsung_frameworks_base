package com.android.server.biometrics.sensors.face.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.GenerateChallengeClient;

public final class FaceGenerateChallengeClient extends GenerateChallengeClient {
    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
                SemFaceServiceExImpl.getInstance().daemonGenerateChallenge();
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                Slog.w("FaceGenerateChallengeClient", "generateChallenge START");
                ((AidlSession) this.mLazyDaemon.get()).mSession.generateChallenge();
                Slog.w(
                        "FaceGenerateChallengeClient",
                        "generateChallenge FINISH ("
                                + (System.currentTimeMillis() - currentTimeMillis)
                                + "ms)");
            }
        } catch (RemoteException e) {
            Slog.e("FaceGenerateChallengeClient", "Unable to generateChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
