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

/* loaded from: classes.dex */
public final class FaceAuthenticator extends IBiometricAuthenticator.Stub {
  public final IFaceService mFaceService;
  public final int mSensorId;

  public FaceAuthenticator(IFaceService iFaceService, int i) {
    this.mFaceService = iFaceService;
    this.mSensorId = i;
  }

  public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, String str) {
    return this.mFaceService.createTestSession(this.mSensorId, iTestSessionCallback, str);
  }

  public SensorPropertiesInternal getSensorProperties(String str) {
    return this.mFaceService.getSensorProperties(this.mSensorId, str);
  }

  public byte[] dumpSensorServiceStateProto(boolean z) {
    return this.mFaceService.dumpSensorServiceStateProto(this.mSensorId, z);
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
      boolean z2) {
    this.mFaceService.prepareForAuthentication(
        z,
        iBinder,
        j,
        iBiometricSensorReceiver,
        new FaceAuthenticateOptions.Builder()
            .setUserId(i)
            .setSensorId(this.mSensorId)
            .setOpPackageName(str)
            .build(),
        j2,
        i2,
        z2);
  }

  public void startPreparedClient(int i) {
    this.mFaceService.startPreparedClient(this.mSensorId, i);
  }

  public void cancelAuthenticationFromService(IBinder iBinder, String str, long j) {
    this.mFaceService.cancelAuthenticationFromService(this.mSensorId, iBinder, str, j);
  }

  public boolean isHardwareDetected(String str) {
    return this.mFaceService.isHardwareDetected(this.mSensorId, str);
  }

  public boolean hasEnrolledTemplates(int i, String str) {
    return this.mFaceService.hasEnrolledFaces(this.mSensorId, i, str);
  }

  public void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) {
    this.mFaceService.invalidateAuthenticatorId(this.mSensorId, i, iInvalidationCallback);
  }

  public int getLockoutModeForUser(int i) {
    return this.mFaceService.getLockoutModeForUser(this.mSensorId, i);
  }

  public long getAuthenticatorId(int i) {
    return this.mFaceService.getAuthenticatorId(this.mSensorId, i);
  }

  public void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) {
    this.mFaceService.resetLockout(iBinder, this.mSensorId, i, bArr, str);
  }
}
