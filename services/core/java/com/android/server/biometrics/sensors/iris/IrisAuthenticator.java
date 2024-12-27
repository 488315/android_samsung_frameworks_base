package com.android.server.biometrics.sensors.iris;

import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IrisAuthenticator extends IBiometricAuthenticator.Stub {
    public final void cancelAuthenticationFromService(IBinder iBinder, String str, long j) {}

    public final ITestSession createTestSession(
            ITestSessionCallback iTestSessionCallback, String str) {
        return null;
    }

    public final byte[] dumpSensorServiceStateProto(boolean z) {
        return null;
    }

    public final long getAuthenticatorId(int i) {
        return 0L;
    }

    public final int getLockoutModeForUser(int i) {
        return 0;
    }

    public final SensorPropertiesInternal getSensorProperties(String str) {
        return null;
    }

    public final boolean hasEnrolledTemplates(int i, String str) {
        return false;
    }

    public final void invalidateAuthenticatorId(
            int i, IInvalidationCallback iInvalidationCallback) {}

    public final boolean isHardwareDetected(String str) {
        return false;
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
            boolean z4) {}

    public final void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) {}

    public final void startPreparedClient(int i) {}
}
