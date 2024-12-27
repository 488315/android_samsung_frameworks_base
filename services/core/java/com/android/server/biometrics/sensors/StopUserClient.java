package com.android.server.biometrics.sensors;

import android.content.Context;
import android.os.IBinder;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class StopUserClient extends HalClientMonitor {
    private final UserStoppedCallback mUserStoppedCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface UserStoppedCallback {
        void onUserStopped();
    }

    public StopUserClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            int i,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            UserStoppedCallback userStoppedCallback) {
        super(
                context,
                supplier,
                iBinder,
                null,
                i,
                context.getOpPackageName(),
                0,
                i2,
                biometricLogger,
                biometricContext,
                false);
        this.mUserStoppedCallback = userStoppedCallback;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 16;
    }

    public final void onUserStopped() {
        this.mUserStoppedCallback.onUserStopped();
        getCallback().onClientFinished(this, true);
    }
}
