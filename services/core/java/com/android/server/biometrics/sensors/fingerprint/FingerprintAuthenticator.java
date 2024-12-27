package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.IFingerprintService;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintAuthenticator extends IBiometricAuthenticator.Stub {
    public final IFingerprintService mFingerprintService;
    public final int mSensorId;

    public FingerprintAuthenticator(IFingerprintService iFingerprintService, int i) {
        this.mFingerprintService = iFingerprintService;
        this.mSensorId = i;
    }

    public final void cancelAuthenticationFromService(IBinder iBinder, String str, long j) {
        this.mFingerprintService.cancelAuthenticationFromService(this.mSensorId, iBinder, str, j);
    }

    public final ITestSession createTestSession(
            ITestSessionCallback iTestSessionCallback, String str) {
        return this.mFingerprintService.createTestSession(
                this.mSensorId, iTestSessionCallback, str);
    }

    public final byte[] dumpSensorServiceStateProto(boolean z) {
        return this.mFingerprintService.dumpSensorServiceStateProto(this.mSensorId, z);
    }

    public final long getAuthenticatorId(int i) {
        return this.mFingerprintService.getAuthenticatorId(this.mSensorId, i);
    }

    public final int getLockoutModeForUser(int i) {
        return this.mFingerprintService.getLockoutModeForUser(this.mSensorId, i);
    }

    public final SensorPropertiesInternal getSensorProperties(String str) {
        return this.mFingerprintService.getSensorProperties(this.mSensorId, str);
    }

    public final boolean hasEnrolledTemplates(int i, String str) {
        return this.mFingerprintService.hasEnrolledFingerprints(this.mSensorId, i, str);
    }

    public final void invalidateAuthenticatorId(
            int i, IInvalidationCallback iInvalidationCallback) {
        this.mFingerprintService.invalidateAuthenticatorId(
                this.mSensorId, i, iInvalidationCallback);
    }

    public final boolean isHardwareDetected(String str) {
        return this.mFingerprintService.isHardwareDetected(this.mSensorId, str);
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
        this.mFingerprintService.prepareForAuthentication(
                iBinder,
                j,
                iBiometricSensorReceiver,
                new FingerprintAuthenticateOptions.Builder()
                        .setSensorId(this.mSensorId)
                        .setUserId(i)
                        .setOpPackageName(str)
                        .setIsMandatoryBiometrics(z4)
                        .build(),
                j2,
                i2,
                z2,
                z3);
    }

    public final void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) {
        this.mFingerprintService.resetLockout(iBinder, this.mSensorId, i, bArr, str);
    }

    public final void startPreparedClient(int i) {
        this.mFingerprintService.startPreparedClient(this.mSensorId, i);
    }
}
