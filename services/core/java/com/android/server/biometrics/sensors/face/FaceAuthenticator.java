package com.android.server.biometrics.sensors.face;

import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.IFaceService;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FaceAuthenticator extends IBiometricAuthenticator.Stub {
    public final IFaceService mFaceService;
    public final int mSensorId;

    public FaceAuthenticator(IFaceService iFaceService, int i) {
        this.mFaceService = iFaceService;
        this.mSensorId = i;
    }

    public final void cancelAuthenticationFromService(IBinder iBinder, String str, long j) {
        this.mFaceService.cancelAuthenticationFromService(this.mSensorId, iBinder, str, j);
    }

    public final ITestSession createTestSession(
            ITestSessionCallback iTestSessionCallback, String str) {
        return this.mFaceService.createTestSession(this.mSensorId, iTestSessionCallback, str);
    }

    public final byte[] dumpSensorServiceStateProto(boolean z) {
        return this.mFaceService.dumpSensorServiceStateProto(this.mSensorId, z);
    }

    public final long getAuthenticatorId(int i) {
        return this.mFaceService.getAuthenticatorId(this.mSensorId, i);
    }

    public final int getLockoutModeForUser(int i) {
        return this.mFaceService.getLockoutModeForUser(this.mSensorId, i);
    }

    public final SensorPropertiesInternal getSensorProperties(String str) {
        return this.mFaceService.getSensorProperties(this.mSensorId, str);
    }

    public final boolean hasEnrolledTemplates(int i, String str) {
        return this.mFaceService.hasEnrolledFaces(this.mSensorId, i, str);
    }

    public final void invalidateAuthenticatorId(
            int i, IInvalidationCallback iInvalidationCallback) {
        this.mFaceService.invalidateAuthenticatorId(this.mSensorId, i, iInvalidationCallback);
    }

    public final boolean isHardwareDetected(String str) {
        return this.mFaceService.isHardwareDetected(this.mSensorId, str);
    }

    public final void prepareForAuthentication(
            boolean z,
            IBinder iBinder,
            long j,
            int i,
            IBiometricSensorReceiver iBiometricSensorReceiver,
            String str,
            long j2,
            int i2,
            boolean z2,
            boolean z3,
            boolean z4) {
        this.mFaceService.prepareForAuthentication(
                z,
                iBinder,
                j,
                iBiometricSensorReceiver,
                new FaceAuthenticateOptions.Builder()
                        .setUserId(i)
                        .setSensorId(this.mSensorId)
                        .setOpPackageName(str)
                        .setIsMandatoryBiometrics(z4)
                        .build(),
                j2,
                i2,
                z2);
    }

    public final void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) {
        this.mFaceService.resetLockout(iBinder, this.mSensorId, i, bArr, str);
    }

    public final void startPreparedClient(int i) {
        this.mFaceService.startPreparedClient(this.mSensorId, i);
    }
}
