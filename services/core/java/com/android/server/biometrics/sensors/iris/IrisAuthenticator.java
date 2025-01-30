package com.android.server.biometrics.sensors.iris;

import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.iris.IIrisService;
import android.os.IBinder;

/* loaded from: classes.dex */
public final class IrisAuthenticator extends IBiometricAuthenticator.Stub {
  public final IIrisService mIrisService;

  public void cancelAuthenticationFromService(IBinder iBinder, String str, long j) {}

  public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, String str) {
    return null;
  }

  public byte[] dumpSensorServiceStateProto(boolean z) {
    return null;
  }

  public long getAuthenticatorId(int i) {
    return 0L;
  }

  public int getLockoutModeForUser(int i) {
    return 0;
  }

  public SensorPropertiesInternal getSensorProperties(String str) {
    return null;
  }

  public boolean hasEnrolledTemplates(int i, String str) {
    return false;
  }

  public void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) {}

  public boolean isHardwareDetected(String str) {
    return false;
  }

  public void prepareForAuthentication(
      boolean z,
      IBinder iBinder,
      long j,
      int i,
      IBiometricSensorReceiver iBiometricSensorReceiver,
      String str,
      long j2,
      int i2,
      boolean z2) {}

  public void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) {}

  public void startPreparedClient(int i) {}

  public IrisAuthenticator(IIrisService iIrisService, int i) {
    this.mIrisService = iIrisService;
  }
}
