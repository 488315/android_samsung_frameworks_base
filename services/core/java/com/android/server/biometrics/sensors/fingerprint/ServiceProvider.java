package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Bundle;
import android.os.IBinder;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricServiceProvider;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import java.util.List;

/* loaded from: classes.dex */
public interface ServiceProvider extends BiometricServiceProvider {
  void cancelAuthentication(int i, IBinder iBinder, long j);

  void cancelEnrollment(int i, IBinder iBinder, long j);

  ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str);

  List getEnrolledFingerprints(int i, int i2);

  void onOneHandModeEnabled();

  void onPointerDown(long j, int i, PointerContext pointerContext);

  void onPointerUp(long j, int i, PointerContext pointerContext);

  void onPowerPressed();

  void onUiReady(long j, int i);

  void onUserRemoved(int i);

  void onWirelessPowerEnabled();

  void pauseEnroll(int i);

  void rename(int i, int i2, int i3, String str);

  void resumeEnroll(int i);

  long scheduleAuthenticate(
      IBinder iBinder,
      long j,
      int i,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      boolean z,
      int i2,
      boolean z2);

  void scheduleAuthenticate(
      IBinder iBinder,
      long j,
      int i,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      long j2,
      boolean z,
      int i2,
      boolean z2);

  long scheduleEnroll(
      int i,
      IBinder iBinder,
      byte[] bArr,
      int i2,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      String str,
      int i3);

  long scheduleFingerDetect(
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      int i);

  void scheduleGenerateChallenge(
      int i,
      int i2,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      String str);

  void scheduleInternalCleanup(
      int i, int i2, ClientMonitorCallback clientMonitorCallback, boolean z);

  void scheduleInvalidateAuthenticatorId(
      int i, int i2, IInvalidationCallback iInvalidationCallback);

  void scheduleRemove(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int i2,
      int i3,
      String str);

  void scheduleRemoveAll(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int i2,
      String str);

  void scheduleResetLockout(int i, int i2, byte[] bArr);

  void scheduleRevokeChallenge(int i, int i2, IBinder iBinder, String str, long j);

  default void scheduleWatchdog(int i) {}

  void semAddAuthenticationListener(SemFpAuthenticationListener semFpAuthenticationListener);

  void semAddChallengeListener(SemFpChallengeListener semFpChallengeListener);

  void semAddEnrollmentListener(SemFpEnrollmentListener semFpEnrollmentListener);

  void semAddEventListener(SemFpEventListener semFpEventListener);

  void semAddHalLifecycleListener(SemFpHalLifecycleListener semFpHalLifecycleListener);

  void semAddResetLockoutListener(SemFpResetLockoutListener semFpResetLockoutListener);

  BaseClientMonitor semGetCurrentClient();

  String semGetDaemonSdkVersion();

  String[] semGetOpticalBrightnessConfigs(int i);

  int semGetRemainingLockoutTime(int i);

  int semGetSecurityLevel();

  String semGetSensorInfo(int i, boolean z);

  void semNotifyTspBlockStateToClient(boolean z);

  void semOpenTzSession();

  int semProcessFidoCommand(int i, int i2, byte[] bArr, byte[] bArr2);

  void semRemoveEventListener(SemFpEventListener semFpEventListener);

  int semRequest(int i, int i2);

  int semRequest(int i, int i2, int i3, byte[] bArr, byte[] bArr2);

  long semScheduleAuthenticate(
      IBinder iBinder,
      long j,
      int i,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      boolean z,
      int i2,
      boolean z2,
      Bundle bundle);

  void semScheduleSensorTest(
      int i,
      IBinder iBinder,
      int i2,
      int i3,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter);

  void semScheduleUpdateTrustApp(
      int i,
      String str,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      String str2);

  void semSetTpaHalEnabled(boolean z);

  void semSetTpaRequestCommandAction(String[] strArr);

  void semUpdateTpaAction();

  void startPreparedClient(int i, int i2);
}
