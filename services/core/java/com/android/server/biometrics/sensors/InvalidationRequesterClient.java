package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.IInvalidationCallback;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InvalidationRequesterClient extends BaseClientMonitor {
    public final BiometricManager mBiometricManager;
    public final AnonymousClass1 mInvalidationCallback;
    public final BiometricUtils mUtils;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.biometrics.sensors.InvalidationRequesterClient$1] */
    public InvalidationRequesterClient(
            Context context,
            int i,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            BiometricUtils biometricUtils) {
        super(
                context,
                null,
                null,
                i,
                context.getOpPackageName(),
                0,
                i2,
                biometricLogger,
                biometricContext);
        this.mInvalidationCallback =
                new IInvalidationCallback
                        .Stub() { // from class:
                                  // com.android.server.biometrics.sensors.InvalidationRequesterClient.1
                    public final void onCompleted() {
                        InvalidationRequesterClient invalidationRequesterClient =
                                InvalidationRequesterClient.this;
                        invalidationRequesterClient.mUtils.setInvalidationInProgress(
                                invalidationRequesterClient.mContext,
                                invalidationRequesterClient.mTargetUserId,
                                false);
                        InvalidationRequesterClient invalidationRequesterClient2 =
                                InvalidationRequesterClient.this;
                        invalidationRequesterClient2.mCallback.onClientFinished(
                                invalidationRequesterClient2, true);
                    }
                };
        this.mBiometricManager =
                (BiometricManager) context.getSystemService(BiometricManager.class);
        this.mUtils = biometricUtils;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 14;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        this.mUtils.setInvalidationInProgress(this.mContext, this.mTargetUserId, true);
        this.mBiometricManager.invalidateAuthenticatorIds(
                this.mTargetUserId, this.mSensorId, this.mInvalidationCallback);
    }
}
