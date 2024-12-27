package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.RemovalClient;

import java.util.Map;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FaceRemovalClient extends RemovalClient {
    public final int[] mBiometricIds;

    public FaceRemovalClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int[] iArr,
            int i,
            String str,
            BiometricUtils biometricUtils,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            Map map) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                str,
                biometricUtils,
                i2,
                biometricLogger,
                biometricContext,
                map);
        this.mBiometricIds = iArr;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            if (SemFaceServiceExImpl.getInstance().isUsingSehAPI()) {
                SemFaceServiceExImpl.getInstance().daemonRemove(this.mBiometricIds);
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                Slog.w("FaceRemovalClient", "removeEnrollments START");
                ((AidlSession) this.mLazyDaemon.get())
                        .mSession.removeEnrollments(this.mBiometricIds);
                Slog.w(
                        "FaceRemovalClient",
                        "removeEnrollments FINISH ("
                                + (System.currentTimeMillis() - currentTimeMillis)
                                + "ms)");
            }
        } catch (RemoteException e) {
            Slog.e("FaceRemovalClient", "Remote exception when requesting remove", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
