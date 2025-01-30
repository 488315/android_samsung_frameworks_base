package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.RevokeChallengeClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpMainThread;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FingerprintRevokeChallengeClient extends RevokeChallengeClient {
    public final long mChallenge;
    public final Runnable mRevokeWatchdog;

    public FingerprintRevokeChallengeClient(Context context, Supplier supplier, IBinder iBinder, int i, String str, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, long j) {
        super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
        this.mChallenge = j;
        this.mRevokeWatchdog = new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRevokeChallengeClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FingerprintRevokeChallengeClient.this.lambda$new$0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        onChallengeRevoked(getSensorId(), getTargetUserId(), 0L);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        try {
            ((AidlSession) getFreshDaemon()).getSession().revokeChallenge(this.mChallenge);
            getHandler().postDelayed(this.mRevokeWatchdog, 1000L);
        } catch (RemoteException e) {
            Slog.e("FingerprintRevokeChallengeClient", "Unable to revokeChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    public void onChallengeRevoked(int i, int i2, long j) {
        this.mCallback.onClientFinished(this, j == this.mChallenge);
        getHandler().removeCallbacks(this.mRevokeWatchdog);
    }

    public Handler getHandler() {
        return SemFpMainThread.get().getHandler();
    }
}
