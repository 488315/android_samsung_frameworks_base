package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.RevokeChallengeClient;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceRevokeChallengeClient extends RevokeChallengeClient {
    public final long mChallenge;

    public FaceRevokeChallengeClient(Context context, Supplier supplier, IBinder iBinder, int i, String str, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, long j) {
        super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
        this.mChallenge = j;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        try {
            if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
                SemFaceServiceExImpl.getInstance().daemonRevokeChallenge(this.mChallenge);
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                Slog.w("FaceRevokeChallengeClient", "revokeChallenge START");
                ((AidlSession) getFreshDaemon()).getSession().revokeChallenge(this.mChallenge);
                Slog.w("FaceRevokeChallengeClient", "revokeChallenge FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
            }
        } catch (RemoteException e) {
            Slog.e("FaceRevokeChallengeClient", "Unable to revokeChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    public void onChallengeRevoked(int i, int i2, long j) {
        this.mCallback.onClientFinished(this, j == this.mChallenge);
    }
}
