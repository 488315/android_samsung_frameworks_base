package com.android.server.biometrics.sensors.face.aidl;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.InternalEnumerateClient;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class FaceInternalEnumerateClient extends InternalEnumerateClient {
    @Override // com.android.server.biometrics.sensors.InternalEnumerateClient
    public final int getModality() {
        return 4;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("FaceInternalEnumerateClient", "enumerateEnrollments START");
            ((AidlSession) this.mLazyDaemon.get()).mSession.enumerateEnrollments();
            Slog.w(
                    "FaceInternalEnumerateClient",
                    "enumerateEnrollments FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (RemoteException e) {
            Slog.e("FaceInternalEnumerateClient", "Remote exception when requesting enumerate", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
