package com.android.server.biometrics.sensors;

import android.content.Context;
import android.os.IBinder;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

import java.util.function.Supplier;

public abstract class StartUserClient extends HalClientMonitor {
    protected final UserStartedCallback mUserStartedCallback;

    public interface UserStartedCallback {
        void onUserStarted(int i, int i2, Object obj);
    }

    public StartUserClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            int i,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            UserStartedCallback userStartedCallback) {
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
        this.mUserStartedCallback = userStartedCallback;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 17;
    }
}
