package com.android.server.biometrics.sensors;

import android.content.Context;
import android.os.IBinder;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

import java.util.function.Supplier;

public abstract class StopUserClient extends HalClientMonitor {
    private final UserStoppedCallback mUserStoppedCallback;

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
