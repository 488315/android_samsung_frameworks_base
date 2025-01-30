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

/* loaded from: classes.dex */
public final class FingerprintAuthenticator extends IBiometricAuthenticator.Stub {
    public final IFingerprintService mFingerprintService;
    public final int mSensorId;

    public FingerprintAuthenticator(IFingerprintService iFingerprintService, int i) {
        this.mFingerprintService = iFingerprintService;
        this.mSensorId = i;
    }

    public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, String str) {
        return this.mFingerprintService.createTestSession(this.mSensorId, iTestSessionCallback, str);
    }

    public SensorPropertiesInternal getSensorProperties(String str) {
        return this.mFingerprintService.getSensorProperties(this.mSensorId, str);
    }

    public byte[] dumpSensorServiceStateProto(boolean z) {
        return this.mFingerprintService.dumpSensorServiceStateProto(this.mSensorId, z);
    }

    public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, long j2, int i2, boolean z2) {
        this.mFingerprintService.prepareForAuthentication(iBinder, j, iBiometricSensorReceiver, new FingerprintAuthenticateOptions.Builder().setSensorId(this.mSensorId).setUserId(i).setOpPackageName(str).build(), j2, i2, z2);
    }

    public void startPreparedClient(int i) {
        this.mFingerprintService.startPreparedClient(this.mSensorId, i);
    }

    public void cancelAuthenticationFromService(IBinder iBinder, String str, long j) {
        this.mFingerprintService.cancelAuthenticationFromService(this.mSensorId, iBinder, str, j);
    }

    public boolean isHardwareDetected(String str) {
        return this.mFingerprintService.isHardwareDetected(this.mSensorId, str);
    }

    public boolean hasEnrolledTemplates(int i, String str) {
        return this.mFingerprintService.hasEnrolledFingerprints(this.mSensorId, i, str);
    }

    public int getLockoutModeForUser(int i) {
        return this.mFingerprintService.getLockoutModeForUser(this.mSensorId, i);
    }

    public void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) {
        this.mFingerprintService.invalidateAuthenticatorId(this.mSensorId, i, iInvalidationCallback);
    }

    public long getAuthenticatorId(int i) {
        return this.mFingerprintService.getAuthenticatorId(this.mSensorId, i);
    }

    public void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) {
        this.mFingerprintService.resetLockout(iBinder, this.mSensorId, i, bArr, str);
    }
}
