package com.android.server.locksettings;

import android.hardware.biometrics.BiometricManager;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ServiceManager;
import android.service.gatekeeper.IGateKeeperService;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.widget.VerifyCredentialResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class BiometricDeferredQueue {
  public BiometricManager mBiometricManager;
  public FaceManager mFaceManager;
  public FaceResetLockoutTask mFaceResetLockoutTask;
  public FingerprintManager mFingerprintManager;
  public final Handler mHandler;
  public final SyntheticPasswordManager mSpManager;
  public final FaceResetLockoutTask.FinishCallback mFaceFinishCallback =
      new FaceResetLockoutTask
          .FinishCallback() { // from class:
                              // com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda0
        @Override // com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask.FinishCallback
        public final void onFinished() {
          BiometricDeferredQueue.this.lambda$new$0();
        }
      };
  public final ArrayList mPendingResetLockoutsForFingerprint = new ArrayList();
  public final ArrayList mPendingResetLockoutsForFace = new ArrayList();
  public final ArrayList mPendingResetLockouts = new ArrayList();

  public class UserAuthInfo {
    public final byte[] gatekeeperPassword;
    public final int userId;

    public UserAuthInfo(int i, byte[] bArr) {
      this.userId = i;
      this.gatekeeperPassword = bArr;
    }
  }

  public class FaceResetLockoutTask implements FaceManager.GenerateChallengeCallback {
    public FaceManager faceManager;
    public FinishCallback finishCallback;
    public List pendingResetLockuts;
    public Set sensorIds;
    public SyntheticPasswordManager spManager;

    public interface FinishCallback {
      void onFinished();
    }

    public FaceResetLockoutTask(
        FinishCallback finishCallback,
        FaceManager faceManager,
        SyntheticPasswordManager syntheticPasswordManager,
        Set set,
        List list) {
      this.finishCallback = finishCallback;
      this.faceManager = faceManager;
      this.spManager = syntheticPasswordManager;
      this.sensorIds = set;
      this.pendingResetLockuts = list;
    }

    public void onGenerateChallengeResult(int i, int i2, long j) {
      if (!this.sensorIds.contains(Integer.valueOf(i))) {
        Slog.e("BiometricDeferredQueue", "Unknown sensorId received: " + i);
        return;
      }
      for (UserAuthInfo userAuthInfo : this.pendingResetLockuts) {
        Slog.d(
            "BiometricDeferredQueue",
            "Resetting face lockout for sensor: " + i + ", user: " + userAuthInfo.userId);
        byte[] requestHatFromGatekeeperPassword =
            BiometricDeferredQueue.requestHatFromGatekeeperPassword(
                this.spManager, userAuthInfo, j);
        if (requestHatFromGatekeeperPassword != null) {
          this.faceManager.resetLockout(i, userAuthInfo.userId, requestHatFromGatekeeperPassword);
        }
      }
      this.sensorIds.remove(Integer.valueOf(i));
      this.faceManager.revokeChallenge(i, i2, j);
      if (this.sensorIds.isEmpty()) {
        Slog.d("BiometricDeferredQueue", "Done requesting resetLockout for all face sensors");
        this.finishCallback.onFinished();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    this.mFaceResetLockoutTask = null;
  }

  public BiometricDeferredQueue(
      SyntheticPasswordManager syntheticPasswordManager, Handler handler) {
    this.mSpManager = syntheticPasswordManager;
    this.mHandler = handler;
  }

  public void systemReady(
      FingerprintManager fingerprintManager,
      FaceManager faceManager,
      BiometricManager biometricManager) {
    this.mFingerprintManager = fingerprintManager;
    this.mFaceManager = faceManager;
    this.mBiometricManager = biometricManager;
  }

  public void addPendingLockoutResetForUser(final int i, final byte[] bArr) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            BiometricDeferredQueue.this.lambda$addPendingLockoutResetForUser$1(i, bArr);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addPendingLockoutResetForUser$1(int i, byte[] bArr) {
    FaceManager faceManager = this.mFaceManager;
    if (faceManager != null && faceManager.hasEnrolledTemplates(i)) {
      Slog.d("BiometricDeferredQueue", "Face addPendingLockoutResetForUser: " + i);
      this.mPendingResetLockoutsForFace.add(new UserAuthInfo(i, bArr));
    }
    FingerprintManager fingerprintManager = this.mFingerprintManager;
    if (fingerprintManager != null && fingerprintManager.hasEnrolledFingerprints(i)) {
      Slog.d("BiometricDeferredQueue", "Fingerprint addPendingLockoutResetForUser: " + i);
      this.mPendingResetLockoutsForFingerprint.add(new UserAuthInfo(i, bArr));
    }
    if (this.mBiometricManager != null) {
      Slog.d("BiometricDeferredQueue", "Fingerprint addPendingLockoutResetForUser: " + i);
      this.mPendingResetLockouts.add(new UserAuthInfo(i, bArr));
    }
  }

  public void processPendingLockoutResets() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            BiometricDeferredQueue.this.lambda$processPendingLockoutResets$2();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$processPendingLockoutResets$2() {
    if (!this.mPendingResetLockoutsForFace.isEmpty()) {
      Slog.d("BiometricDeferredQueue", "Processing pending resetLockout for face");
      processPendingLockoutsForFace(new ArrayList(this.mPendingResetLockoutsForFace));
      this.mPendingResetLockoutsForFace.clear();
    }
    if (!this.mPendingResetLockoutsForFingerprint.isEmpty()) {
      Slog.d("BiometricDeferredQueue", "Processing pending resetLockout for fingerprint");
      processPendingLockoutsForFingerprint(new ArrayList(this.mPendingResetLockoutsForFingerprint));
      this.mPendingResetLockoutsForFingerprint.clear();
    }
    if (this.mPendingResetLockouts.isEmpty()) {
      return;
    }
    Slog.d("BiometricDeferredQueue", "Processing pending resetLockouts(Generic)");
    processPendingLockoutsGeneric(new ArrayList(this.mPendingResetLockouts));
    this.mPendingResetLockouts.clear();
  }

  public final void processPendingLockoutsForFingerprint(List list) {
    FingerprintManager fingerprintManager = this.mFingerprintManager;
    if (fingerprintManager != null) {
      for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal :
          fingerprintManager.getSensorPropertiesInternal()) {
        if (!fingerprintSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
          Iterator it = list.iterator();
          while (it.hasNext()) {
            this.mFingerprintManager.resetLockout(
                fingerprintSensorPropertiesInternal.sensorId,
                ((UserAuthInfo) it.next()).userId,
                null);
          }
        } else if (fingerprintSensorPropertiesInternal.resetLockoutRequiresChallenge) {
          Slog.w(
              "BiometricDeferredQueue",
              "No fingerprint HAL interface requires HAT with challenge, sensorId: "
                  + fingerprintSensorPropertiesInternal.sensorId);
        } else {
          Iterator it2 = list.iterator();
          while (it2.hasNext()) {
            UserAuthInfo userAuthInfo = (UserAuthInfo) it2.next();
            Slog.d(
                "BiometricDeferredQueue",
                "Resetting fingerprint lockout for sensor: "
                    + fingerprintSensorPropertiesInternal.sensorId
                    + ", user: "
                    + userAuthInfo.userId);
            byte[] requestHatFromGatekeeperPassword =
                requestHatFromGatekeeperPassword(this.mSpManager, userAuthInfo, 0L);
            if (requestHatFromGatekeeperPassword != null) {
              this.mFingerprintManager.resetLockout(
                  fingerprintSensorPropertiesInternal.sensorId,
                  userAuthInfo.userId,
                  requestHatFromGatekeeperPassword);
            }
          }
        }
      }
    }
  }

  public final void processPendingLockoutsForFace(List list) {
    if (this.mFaceManager != null) {
      if (this.mFaceResetLockoutTask != null) {
        Slog.w(
            "BiometricDeferredQueue",
            "mFaceGenerateChallengeCallback not null, previous operation may be stuck");
      }
      List<FaceSensorPropertiesInternal> sensorPropertiesInternal =
          this.mFaceManager.getSensorPropertiesInternal();
      ArraySet arraySet = new ArraySet();
      Iterator it = sensorPropertiesInternal.iterator();
      while (it.hasNext()) {
        arraySet.add(Integer.valueOf(((FaceSensorPropertiesInternal) it.next()).sensorId));
      }
      this.mFaceResetLockoutTask =
          new FaceResetLockoutTask(
              this.mFaceFinishCallback, this.mFaceManager, this.mSpManager, arraySet, list);
      for (FaceSensorPropertiesInternal faceSensorPropertiesInternal : sensorPropertiesInternal) {
        if (faceSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
          Iterator it2 = list.iterator();
          while (it2.hasNext()) {
            UserAuthInfo userAuthInfo = (UserAuthInfo) it2.next();
            if (faceSensorPropertiesInternal.resetLockoutRequiresChallenge) {
              Slog.d(
                  "BiometricDeferredQueue",
                  "Generating challenge for sensor: "
                      + faceSensorPropertiesInternal.sensorId
                      + ", user: "
                      + userAuthInfo.userId);
              this.mFaceManager.generateChallenge(
                  faceSensorPropertiesInternal.sensorId,
                  userAuthInfo.userId,
                  this.mFaceResetLockoutTask);
            } else {
              Slog.d(
                  "BiometricDeferredQueue",
                  "Resetting face lockout for sensor: "
                      + faceSensorPropertiesInternal.sensorId
                      + ", user: "
                      + userAuthInfo.userId);
              byte[] requestHatFromGatekeeperPassword =
                  requestHatFromGatekeeperPassword(this.mSpManager, userAuthInfo, 0L);
              if (requestHatFromGatekeeperPassword != null) {
                this.mFaceManager.resetLockout(
                    faceSensorPropertiesInternal.sensorId,
                    userAuthInfo.userId,
                    requestHatFromGatekeeperPassword);
              }
            }
          }
        } else {
          Iterator it3 = list.iterator();
          while (it3.hasNext()) {
            UserAuthInfo userAuthInfo2 = (UserAuthInfo) it3.next();
            Slog.d(
                "BiometricDeferredQueue",
                "Resetting face lockout for sensor: "
                    + faceSensorPropertiesInternal.sensorId
                    + ", user: "
                    + userAuthInfo2.userId);
            this.mFaceManager.resetLockout(
                faceSensorPropertiesInternal.sensorId, userAuthInfo2.userId, (byte[]) null);
          }
        }
      }
    }
  }

  public final void processPendingLockoutsGeneric(List list) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      UserAuthInfo userAuthInfo = (UserAuthInfo) it.next();
      Slog.d(
          "BiometricDeferredQueue", "Resetting biometric lockout for user: " + userAuthInfo.userId);
      byte[] requestHatFromGatekeeperPassword =
          requestHatFromGatekeeperPassword(this.mSpManager, userAuthInfo, 0L);
      if (requestHatFromGatekeeperPassword != null) {
        this.mBiometricManager.resetLockout(userAuthInfo.userId, requestHatFromGatekeeperPassword);
      }
    }
  }

  public static byte[] requestHatFromGatekeeperPassword(
      SyntheticPasswordManager syntheticPasswordManager, UserAuthInfo userAuthInfo, long j) {
    VerifyCredentialResponse verifyChallengeInternal =
        syntheticPasswordManager.verifyChallengeInternal(
            getGatekeeperService(), userAuthInfo.gatekeeperPassword, j, userAuthInfo.userId);
    if (verifyChallengeInternal == null) {
      Slog.wtf("BiometricDeferredQueue", "VerifyChallenge failed, null response");
      return null;
    }
    if (verifyChallengeInternal.getResponseCode() != 0) {
      Slog.wtf(
          "BiometricDeferredQueue",
          "VerifyChallenge failed, response: " + verifyChallengeInternal.getResponseCode());
      return null;
    }
    if (verifyChallengeInternal.getGatekeeperHAT() == null) {
      Slog.e("BiometricDeferredQueue", "Null HAT received from spManager");
    }
    return verifyChallengeInternal.getGatekeeperHAT();
  }

  public static synchronized IGateKeeperService getGatekeeperService() {
    synchronized (BiometricDeferredQueue.class) {
      IBinder service = ServiceManager.getService("android.service.gatekeeper.IGateKeeperService");
      if (service == null) {
        Slog.e("BiometricDeferredQueue", "Unable to acquire GateKeeperService");
        return null;
      }
      return IGateKeeperService.Stub.asInterface(service);
    }
  }
}
