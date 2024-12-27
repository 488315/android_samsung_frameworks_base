package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.InternalEnumerateClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;

public class FingerprintInternalEnumerateClient extends InternalEnumerateClient {
    @Override // com.android.server.biometrics.sensors.InternalEnumerateClient
    public final int getModality() {
        return 1;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            if (new SemFpBaseRequestClient(
                                    this.mContext,
                                    this.mBiometricContext,
                                    this.mLazyDaemon,
                                    null,
                                    null,
                                    this.mSensorId,
                                    0,
                                    "FingerprintRequestClient",
                                    false,
                                    11,
                                    0,
                                    null,
                                    null)
                            .startWithoutScheduler()
                    < 0) {
                this.mCallback.onClientFinished(this, false);
                return;
            }
        } catch (NullPointerException | UnsupportedOperationException e) {
            Slog.w(
                    "FingerprintInternalEnumerateClient",
                    "canUseEnumerateOperation: " + e.getMessage());
        }
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.enumerateEnrollments();
        } catch (RemoteException | NullPointerException e2) {
            Slog.e(
                    "FingerprintInternalEnumerateClient",
                    "Remote exception when requesting enumerate",
                    e2);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
