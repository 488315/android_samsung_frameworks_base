package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.IInvalidationCallback;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import java.util.Map;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class InvalidationClient extends HalClientMonitor implements ErrorConsumer {
    public final Map mAuthenticatorIds;
    public final IInvalidationCallback mInvalidationCallback;

    public InvalidationClient(Context context, Supplier supplier, int i, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, Map map, IInvalidationCallback iInvalidationCallback) {
        super(context, supplier, null, null, i, context.getOpPackageName(), 0, i2, biometricLogger, biometricContext, false);
        this.mAuthenticatorIds = map;
        this.mInvalidationCallback = iInvalidationCallback;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 15;
    }

    public final void onAuthenticatorIdInvalidated(long j) {
        this.mAuthenticatorIds.put(Integer.valueOf(this.mTargetUserId), Long.valueOf(j));
        try {
            this.mInvalidationCallback.onCompleted();
        } catch (RemoteException e) {
            Slog.e("InvalidationClient", "Remote exception", e);
        }
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        try {
            this.mInvalidationCallback.onCompleted();
        } catch (RemoteException e) {
            Slog.e("InvalidationClient", "Remote exception", e);
        }
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
    }
}
