package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.RevokeChallengeClient;

import java.util.function.Supplier;

public final class FingerprintRevokeChallengeClient extends RevokeChallengeClient {
    public final long mChallenge;
    public final FingerprintRevokeChallengeClient$$ExternalSyntheticLambda0 mRevokeWatchdog;

    public FingerprintRevokeChallengeClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            int i,
            String str,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            long j) {
        super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
        this.mChallenge = j;
        this.mRevokeWatchdog =
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRevokeChallengeClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintRevokeChallengeClient fingerprintRevokeChallengeClient =
                                FingerprintRevokeChallengeClient.this;
                        fingerprintRevokeChallengeClient.mCallback.onClientFinished(
                                fingerprintRevokeChallengeClient,
                                0 == fingerprintRevokeChallengeClient.mChallenge);
                        fingerprintRevokeChallengeClient
                                .getHandler()
                                .removeCallbacks(fingerprintRevokeChallengeClient.mRevokeWatchdog);
                    }
                };
    }

    public Handler getHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.revokeChallenge(this.mChallenge);
            getHandler().postDelayed(this.mRevokeWatchdog, 1000L);
        } catch (RemoteException e) {
            Slog.e("FingerprintRevokeChallengeClient", "Unable to revokeChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
