package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.IBiometricService;
import android.os.Handler;
import android.os.ServiceManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.sensors.UserAwareBiometricScheduler;

/* loaded from: classes.dex */
public class SemFpUserAwareScheduler extends UserAwareBiometricScheduler {
  public SemFpUserAwareScheduler(
      String str,
      GestureAvailabilityDispatcher gestureAvailabilityDispatcher,
      UserAwareBiometricScheduler.CurrentUserRetriever currentUserRetriever,
      UserAwareBiometricScheduler.UserSwitchCallback userSwitchCallback) {
    super(
        str,
        new Handler(SemFpMainThread.get().getLooper()),
        SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE ? 2 : 3,
        gestureAvailabilityDispatcher,
        IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")),
        currentUserRetriever,
        userSwitchCallback);
  }

  @Override // com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler,
            // com.android.server.biometrics.sensors.BiometricScheduler
  public void reset() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpUserAwareScheduler$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SemFpUserAwareScheduler.this.onUserStopped();
          }
        });
    super.reset();
  }
}
