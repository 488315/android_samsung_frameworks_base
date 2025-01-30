package com.android.server.biometrics;

import android.content.Context;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.security.KeyStore;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricFrameworkStatsLogger;
import com.android.server.biometrics.log.OperationContextExt;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/* loaded from: classes.dex */
public final class AuthSession implements IBinder.DeathRecipient {
  public int mAuthenticatedSensorId;
  public long mAuthenticatedTimeMs;
  public SparseArray mAuthenticationResults;
  public final BiometricContext mBiometricContext;
  final BiometricFrameworkStatsLogger mBiometricFrameworkStatsLogger;
  public boolean mCancelled;
  public final ClientDeathReceiver mClientDeathReceiver;
  public final IBiometricServiceReceiver mClientReceiver;
  public final Context mContext;
  public BiometricSensor mCurrentSensor;
  public final boolean mDebugEnabled;
  public int mErrorEscrow;
  public final List mFingerprintSensorProperties;
  public final KeyStore mKeyStore;
  public final String mOpPackageName;
  public final long mOperationId;
  public final PreAuthInfo mPreAuthInfo;
  final PromptInfo mPromptInfo;
  public final Random mRandom;
  public final long mRequestId;
  final IBiometricSensorReceiver mSensorReceiver;
  public int[] mSensors;
  public long mStartTimeMs;
  public int mState;
  public final SemBiometricSysUiWrapper mStatusBarService;
  final IBiometricSysuiReceiver mSysuiReceiver;
  final IBinder mToken;
  public byte[] mTokenEscrow;
  public final int mUserId;
  public int mVendorCodeEscrow;

  public class AuthenticationResult {
    public byte[] challengeToken;

    /* renamed from: id */
    public int f1649id;
    public String name;

    public AuthenticationResult() {}
  }

  public interface ClientDeathReceiver {
    void onClientDied();
  }

  public AuthSession(
      Context context,
      BiometricContext biometricContext,
      SemBiometricSysUiWrapper semBiometricSysUiWrapper,
      IBiometricSysuiReceiver iBiometricSysuiReceiver,
      KeyStore keyStore,
      Random random,
      ClientDeathReceiver clientDeathReceiver,
      PreAuthInfo preAuthInfo,
      IBinder iBinder,
      long j,
      long j2,
      int i,
      IBiometricSensorReceiver iBiometricSensorReceiver,
      IBiometricServiceReceiver iBiometricServiceReceiver,
      String str,
      PromptInfo promptInfo,
      boolean z,
      List list) {
    this(
        context,
        biometricContext,
        semBiometricSysUiWrapper,
        iBiometricSysuiReceiver,
        keyStore,
        random,
        clientDeathReceiver,
        preAuthInfo,
        iBinder,
        j,
        j2,
        i,
        iBiometricSensorReceiver,
        iBiometricServiceReceiver,
        str,
        promptInfo,
        z,
        list,
        BiometricFrameworkStatsLogger.getInstance());
  }

  public AuthSession(
      Context context,
      BiometricContext biometricContext,
      SemBiometricSysUiWrapper semBiometricSysUiWrapper,
      IBiometricSysuiReceiver iBiometricSysuiReceiver,
      KeyStore keyStore,
      Random random,
      ClientDeathReceiver clientDeathReceiver,
      PreAuthInfo preAuthInfo,
      IBinder iBinder,
      long j,
      long j2,
      int i,
      IBiometricSensorReceiver iBiometricSensorReceiver,
      IBiometricServiceReceiver iBiometricServiceReceiver,
      String str,
      PromptInfo promptInfo,
      boolean z,
      List list,
      BiometricFrameworkStatsLogger biometricFrameworkStatsLogger) {
    this.mState = 0;
    this.mAuthenticatedSensorId = -1;
    Slog.d("BiometricService/AuthSession", "Creating AuthSession with: " + preAuthInfo);
    this.mContext = context;
    this.mBiometricContext = biometricContext;
    this.mStatusBarService = semBiometricSysUiWrapper;
    this.mSysuiReceiver = iBiometricSysuiReceiver;
    this.mKeyStore = keyStore;
    this.mRandom = random;
    this.mClientDeathReceiver = clientDeathReceiver;
    this.mPreAuthInfo = preAuthInfo;
    this.mToken = iBinder;
    this.mRequestId = j;
    this.mOperationId = j2;
    this.mUserId = i;
    this.mSensorReceiver = iBiometricSensorReceiver;
    this.mClientReceiver = iBiometricServiceReceiver;
    this.mOpPackageName = str;
    this.mPromptInfo = promptInfo;
    this.mDebugEnabled = z;
    this.mFingerprintSensorProperties = list;
    this.mCancelled = false;
    this.mBiometricFrameworkStatsLogger = biometricFrameworkStatsLogger;
    try {
      iBiometricServiceReceiver.asBinder().linkToDeath(this, 0);
    } catch (RemoteException unused) {
      Slog.w("BiometricService/AuthSession", "Unable to link to death");
    }
    setSensorsToStateUnknown();
    this.mAuthenticationResults = new SparseArray(1);
    this.mStatusBarService.openSession(iBinder, j, iBiometricSysuiReceiver);
    SemBioLoggingManager.get().bpStart((int) j, str);
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {
    Slog.e("BiometricService/AuthSession", "Binder died, session: " + this);
    this.mClientDeathReceiver.onClientDied();
  }

  public final int getEligibleModalities() {
    return this.mPreAuthInfo.getEligibleModalities();
  }

  public final void setSensorsToStateUnknown() {
    for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
      Slog.v(
          "BiometricService/AuthSession",
          "set to unknown state sensor: " + biometricSensor.f1650id);
      biometricSensor.goToStateUnknown();
    }
  }

  public final void setSensorsToStateWaitingForCookie(BiometricSensor biometricSensor) {
    biometricSensor.goToStateWaitingForCookie(
        isConfirmationRequired(biometricSensor),
        this.mToken,
        this.mOperationId,
        this.mUserId,
        this.mSensorReceiver,
        this.mOpPackageName,
        this.mRequestId,
        this.mRandom.nextInt(2147483646) + 1,
        this.mPromptInfo.isAllowBackgroundAuthentication());
    this.mCurrentSensor = biometricSensor;
  }

  public void goToInitialState() {
    PreAuthInfo preAuthInfo = this.mPreAuthInfo;
    if (preAuthInfo.credentialAvailable && preAuthInfo.eligibleSensors.isEmpty()) {
      this.mState = 9;
      int[] iArr = new int[0];
      this.mSensors = iArr;
      this.mStatusBarService.showAuthenticationDialog(
          this.mPromptInfo,
          this.mSysuiReceiver,
          iArr,
          true,
          false,
          this.mUserId,
          this.mOperationId,
          this.mOpPackageName,
          this.mRequestId);
      return;
    }
    if (!this.mPreAuthInfo.eligibleSensors.isEmpty()) {
      setSensorsToStateWaitingForCookie(getFirstPrioritySensor(this.mPreAuthInfo.eligibleSensors));
      this.mState = 1;
      return;
    }
    throw new IllegalStateException("No authenticators requested");
  }

  public final BiometricSensor getFirstPrioritySensor(List list) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      BiometricSensor biometricSensor = (BiometricSensor) it.next();
      if (biometricSensor.modality == 2) {
        return biometricSensor;
      }
    }
    return (BiometricSensor) list.get(0);
  }

  public void onCookieReceived(int i) {
    if (this.mCancelled) {
      Slog.w(
          "BiometricService/AuthSession", "Received cookie but already cancelled (ignoring): " + i);
      return;
    }
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onCookieReceived after successful auth");
      return;
    }
    if (Utils.DEBUG) {
      Slog.d("BiometricService/AuthSession", "onCookieReceived: " + i + ", state = " + this.mState);
    }
    if (i == 0) {
      return;
    }
    Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
    while (it.hasNext()) {
      ((BiometricSensor) it.next()).goToStateCookieReturnedIfCookieMatches(i);
    }
    if (allCookiesReceived()) {
      this.mStartTimeMs = System.currentTimeMillis();
      startAllPreparedSensorsExceptFingerprint();
      if (this.mState != 5) {
        try {
          boolean isConfirmationRequiredByAnyEligibleSensor =
              isConfirmationRequiredByAnyEligibleSensor();
          this.mSensors = new int[this.mPreAuthInfo.eligibleSensors.size()];
          for (int i2 = 0; i2 < this.mPreAuthInfo.eligibleSensors.size(); i2++) {
            this.mSensors[i2] =
                ((BiometricSensor) this.mPreAuthInfo.eligibleSensors.get(i2)).f1650id;
          }
          this.mStatusBarService.showAuthenticationDialog(
              this.mPromptInfo,
              this.mSysuiReceiver,
              this.mSensors,
              this.mPreAuthInfo.shouldShowCredential(),
              isConfirmationRequiredByAnyEligibleSensor,
              this.mUserId,
              this.mOperationId,
              this.mOpPackageName,
              this.mRequestId);
          this.mState = 2;
          return;
        } catch (RemoteException e) {
          Slog.e("BiometricService/AuthSession", "Remote exception", e);
          return;
        }
      }
      this.mState = 3;
      startAllPreparedSensors(
          new Function() { // from class:
                           // com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
              Boolean lambda$onCookieReceived$0;
              lambda$onCookieReceived$0 =
                  AuthSession.lambda$onCookieReceived$0((BiometricSensor) obj);
              return lambda$onCookieReceived$0;
            }
          });
      return;
    }
    Slog.v("BiometricService/AuthSession", "onCookieReceived: still waiting");
  }

  public static /* synthetic */ Boolean lambda$onCookieReceived$0(BiometricSensor biometricSensor) {
    return Boolean.valueOf(
        biometricSensor.getCookie() != 0 && biometricSensor.getSensorState() == 2);
  }

  public final boolean isConfirmationRequired(BiometricSensor biometricSensor) {
    return biometricSensor.confirmationSupported()
        && (biometricSensor.confirmationAlwaysRequired(this.mUserId)
            || this.mPreAuthInfo.confirmationRequested);
  }

  public final boolean isConfirmationRequiredByAnyEligibleSensor() {
    Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
    while (it.hasNext()) {
      if (isConfirmationRequired((BiometricSensor) it.next())) {
        return true;
      }
    }
    return false;
  }

  public static /* synthetic */ Boolean lambda$startAllPreparedSensorsExceptFingerprint$1(
      BiometricSensor biometricSensor) {
    return Boolean.valueOf(
        (biometricSensor.modality == 2 || biometricSensor.getCookie() == 0) ? false : true);
  }

  public final void startAllPreparedSensorsExceptFingerprint() {
    startAllPreparedSensors(
        new Function() { // from class:
                         // com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda5
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            Boolean lambda$startAllPreparedSensorsExceptFingerprint$1;
            lambda$startAllPreparedSensorsExceptFingerprint$1 =
                AuthSession.lambda$startAllPreparedSensorsExceptFingerprint$1(
                    (BiometricSensor) obj);
            return lambda$startAllPreparedSensorsExceptFingerprint$1;
          }
        });
  }

  public static /* synthetic */ Boolean lambda$startAllPreparedFingerprintSensors$2(
      BiometricSensor biometricSensor) {
    return Boolean.valueOf(biometricSensor.modality == 2);
  }

  public final void startAllPreparedFingerprintSensors() {
    startAllPreparedSensors(
        new Function() { // from class:
                         // com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda4
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            Boolean lambda$startAllPreparedFingerprintSensors$2;
            lambda$startAllPreparedFingerprintSensors$2 =
                AuthSession.lambda$startAllPreparedFingerprintSensors$2((BiometricSensor) obj);
            return lambda$startAllPreparedFingerprintSensors$2;
          }
        });
  }

  public final void startAllPreparedSensors(Function function) {
    for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
      if (((Boolean) function.apply(biometricSensor)).booleanValue()) {
        try {
          Slog.v("BiometricService/AuthSession", "Starting sensor: " + biometricSensor.f1650id);
          biometricSensor.startSensor();
        } catch (RemoteException e) {
          Slog.e(
              "BiometricService/AuthSession",
              "Unable to start prepared client, sensor: " + biometricSensor,
              e);
        }
      }
    }
  }

  public static /* synthetic */ Boolean lambda$cancelAllSensors$3(BiometricSensor biometricSensor) {
    return Boolean.TRUE;
  }

  public final void cancelAllSensors() {
    cancelAllSensors(
        new Function() { // from class:
                         // com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda3
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            Boolean lambda$cancelAllSensors$3;
            lambda$cancelAllSensors$3 =
                AuthSession.lambda$cancelAllSensors$3((BiometricSensor) obj);
            return lambda$cancelAllSensors$3;
          }
        });
  }

  public final void cancelAllSensors(Function function) {
    for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
      try {
        if (((Boolean) function.apply(biometricSensor)).booleanValue()) {
          Slog.d("BiometricService/AuthSession", "Cancelling sensorId: " + biometricSensor.f1650id);
          biometricSensor.goToStateCancelling(this.mToken, this.mOpPackageName, this.mRequestId);
        }
      } catch (RemoteException unused) {
        Slog.e("BiometricService/AuthSession", "Unable to cancel authentication");
      }
    }
  }

  public boolean onErrorReceived(int i, int i2, int i3, int i4) {
    Slog.d("BiometricService/AuthSession", "onErrorReceived sensor: " + i + " error: " + i3);
    if (!containsCookie(i2)) {
      Slog.e("BiometricService/AuthSession", "Unknown/expired cookie: " + i2);
      return false;
    }
    for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
      if (biometricSensor.getSensorState() == 3) {
        biometricSensor.goToStoppedStateIfCookieMatches(i2, i3);
      }
    }
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onErrorReceived after successful auth (ignoring)");
      return false;
    }
    this.mErrorEscrow = i3;
    this.mVendorCodeEscrow = i4;
    int sensorIdToModality = sensorIdToModality(i);
    Slog.d("BiometricService/AuthSession", "onErrorReceived: " + this.mState);
    int i5 = this.mState;
    if (i5 != 1) {
      if (i5 == 2 || i5 == 3) {
        boolean z = i3 == 7 || i3 == 9;
        if (isAllowDeviceCredential() && z) {
          this.mState = 9;
          this.mStatusBarService.onBiometricError(sensorIdToModality, i3, i4);
        } else {
          if (i3 == 5) {
            this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
            this.mClientReceiver.onError(sensorIdToModality, i3, i4);
            return true;
          }
          this.mState = 8;
          this.mStatusBarService.onBiometricError(sensorIdToModality, i3, i4);
        }
      } else {
        if (i5 == 4) {
          this.mClientReceiver.onError(sensorIdToModality, i3, i4);
          this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
          return true;
        }
        if (i5 == 9) {
          Slog.d(
              "BiometricService/AuthSession",
              "Biometric canceled, ignoring from state: " + this.mState);
        } else {
          if (i5 == 10) {
            this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
            return true;
          }
          Slog.e("BiometricService/AuthSession", "Unhandled error state, mState: " + this.mState);
        }
      }
    } else if (isAllowDeviceCredential()) {
      this.mPromptInfo.setAuthenticators(
          Utils.removeBiometricBits(this.mPromptInfo.getAuthenticators()));
      this.mState = 9;
      int[] iArr = new int[0];
      this.mSensors = iArr;
      this.mStatusBarService.showAuthenticationDialog(
          this.mPromptInfo,
          this.mSysuiReceiver,
          iArr,
          true,
          false,
          this.mUserId,
          this.mOperationId,
          this.mOpPackageName,
          this.mRequestId);
    } else {
      this.mClientReceiver.onError(sensorIdToModality, i3, i4);
      return true;
    }
    return false;
  }

  public void onAcquired(int i, int i2, int i3) {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onAcquired after successful auth");
      return;
    }
    String acquiredMessageForSensor = getAcquiredMessageForSensor(i, i2, i3);
    Slog.d(
        "BiometricService/AuthSession",
        "sensorId: "
            + i
            + " acquiredInfo: "
            + i2
            + " vendor: "
            + i3
            + " message: "
            + acquiredMessageForSensor);
    if (acquiredMessageForSensor == null || TextUtils.isEmpty(acquiredMessageForSensor)) {
      return;
    }
    try {
      this.mStatusBarService.onBiometricHelp(
          sensorIdToModality(i), i2, i3, acquiredMessageForSensor);
      if (i2 == 6) {
        i2 = i3;
      }
      this.mClientReceiver.onAcquired(i2, acquiredMessageForSensor);
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "Remote exception", e);
    }
  }

  public void onSystemEvent(int i) {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onSystemEvent after successful auth");
    } else if (this.mPromptInfo.isReceiveSystemEvents()) {
      try {
        this.mClientReceiver.onSystemEvent(i);
      } catch (RemoteException e) {
        Slog.e("BiometricService/AuthSession", "RemoteException", e);
      }
    }
  }

  public void onDialogAnimatedIn(boolean z) {
    if (this.mState != 2) {
      Slog.e(
          "BiometricService/AuthSession", "onDialogAnimatedIn, unexpected state: " + this.mState);
      return;
    }
    this.mState = 3;
    if (z) {
      startAllPreparedFingerprintSensors();
    } else {
      Slog.d("BiometricService/AuthSession", "delaying fingerprint sensor start");
    }
  }

  public void onStartFingerprint() {
    int i = this.mState;
    if (i != 2 && i != 3 && i != 4 && i != 8) {
      Slog.w(
          "BiometricService/AuthSession",
          "onStartFingerprint, started from unexpected state: " + this.mState);
    }
    startAllPreparedFingerprintSensors();
  }

  public void onTryAgainPressed() {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onTryAgainPressed after successful auth");
      return;
    }
    if (this.mState != 4) {
      Slog.w("BiometricService/AuthSession", "onTryAgainPressed, state: " + this.mState);
    }
    try {
      BiometricSensor biometricSensor = this.mCurrentSensor;
      if (biometricSensor == null) {
        Slog.w("BiometricService/AuthSession", "onTryAgainPressed: current sensor is null");
      } else {
        setSensorsToStateWaitingForCookie(biometricSensor);
      }
      this.mState = 5;
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "RemoteException: " + e);
    }
  }

  public void onAuthenticationSucceeded(final int i, boolean z, byte[] bArr, Bundle bundle) {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onAuthenticationSucceeded after successful auth");
      return;
    }
    if (!shouldMaintainSessionEvenAfterSuccessfulAuthentication()) {
      this.mAuthenticatedSensorId = i;
    }
    if (z) {
      this.mTokenEscrow = bArr;
    } else if (bArr != null) {
      Slog.w(
          "BiometricService/AuthSession", "Dropping authToken for non-strong biometric, id: " + i);
    }
    AuthenticationResult authenticationResult = new AuthenticationResult();
    if (bundle != null) {
      authenticationResult.name = bundle.getString("KEY_IDENTIFIER_NAME", "");
      authenticationResult.f1649id = bundle.getInt("KEY_BIOMETRICS_ID", 0);
      authenticationResult.challengeToken = bundle.getByteArray("KEY_CHALLENGE_TOKEN");
      this.mAuthenticationResults.put(i, authenticationResult);
    }
    try {
      if (TextUtils.isEmpty(authenticationResult.name)) {
        this.mStatusBarService.onBiometricAuthenticated(sensorIdToModality(i));
      } else {
        this.mStatusBarService.onBiometricAuthenticated(
            sensorIdToModality(i), authenticationResult.name);
      }
      if (!isConfirmationRequiredByAnyEligibleSensor()) {
        this.mState = 7;
      } else {
        this.mAuthenticatedTimeMs = System.currentTimeMillis();
        this.mState = 6;
      }
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "RemoteException", e);
    }
    cancelAllSensors(
        new Function() { // from class:
                         // com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda2
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            Boolean lambda$onAuthenticationSucceeded$4;
            lambda$onAuthenticationSucceeded$4 =
                AuthSession.lambda$onAuthenticationSucceeded$4(i, (BiometricSensor) obj);
            return lambda$onAuthenticationSucceeded$4;
          }
        });
  }

  public static /* synthetic */ Boolean lambda$onAuthenticationSucceeded$4(
      int i, BiometricSensor biometricSensor) {
    return Boolean.valueOf(biometricSensor.f1650id != i);
  }

  public final boolean shouldMaintainSessionEvenAfterSuccessfulAuthentication() {
    return isCheckedEnrollBiometricSession() || isTwoFactorBiometricSession();
  }

  public final boolean isCheckedEnrollBiometricSession() {
    return (this.mPromptInfo.semGetPrivilegedFlag() & 1) != 0;
  }

  public final boolean isTwoFactorBiometricSession() {
    return (this.mPromptInfo.semGetPrivilegedFlag() & 64) != 0;
  }

  public void onAuthenticationRejected(int i) {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onAuthenticationRejected after successful auth");
      return;
    }
    try {
      this.mStatusBarService.onBiometricError(sensorIdToModality(i), 100, 0);
      if (pauseSensorIfSupported(i)) {
        this.mState = 4;
      }
      this.mClientReceiver.onAuthenticationFailed();
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "RemoteException", e);
    }
  }

  public void onAuthenticationTimedOut(int i, int i2, int i3, int i4) {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onAuthenticationTimedOut after successful auth");
      return;
    }
    try {
      this.mStatusBarService.onBiometricError(sensorIdToModality(i), i3, i4);
      pauseSensorIfSupported(i);
      this.mState = 4;
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "RemoteException", e);
    }
  }

  public final boolean pauseSensorIfSupported(final int i) {
    if (sensorIdToModality(i) != 8) {
      return false;
    }
    cancelAllSensors(
        new Function() { // from class:
                         // com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda0
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            Boolean lambda$pauseSensorIfSupported$5;
            lambda$pauseSensorIfSupported$5 =
                AuthSession.lambda$pauseSensorIfSupported$5(i, (BiometricSensor) obj);
            return lambda$pauseSensorIfSupported$5;
          }
        });
    return true;
  }

  public static /* synthetic */ Boolean lambda$pauseSensorIfSupported$5(
      int i, BiometricSensor biometricSensor) {
    return Boolean.valueOf(biometricSensor.f1650id == i);
  }

  public void onDeviceCredentialPressed() {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onDeviceCredentialPressed after successful auth");
    } else {
      cancelAllSensors();
      this.mState = 9;
    }
  }

  public boolean onClientDied() {
    try {
      int i = this.mState;
      if (i == 2 || i == 3) {
        this.mState = 10;
        cancelAllSensors();
        return false;
      }
      this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
      return true;
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "Remote Exception: " + e);
      return true;
    }
  }

  public final boolean hasAuthenticated() {
    return this.mAuthenticatedSensorId != -1;
  }

  public final void logOnDialogDismissed(int i) {
    if (i == 1) {
      long currentTimeMillis = System.currentTimeMillis() - this.mAuthenticatedTimeMs;
      Slog.v(
          "BiometricService/AuthSession",
          "Confirmed! Modality: "
              + statsModality()
              + ", User: "
              + this.mUserId
              + ", IsCrypto: "
              + isCrypto()
              + ", Client: 2, RequireConfirmation: "
              + this.mPreAuthInfo.confirmationRequested
              + ", State: 3, Latency: "
              + currentTimeMillis);
      this.mBiometricFrameworkStatsLogger.authenticate(
          this.mBiometricContext.updateContext(new OperationContextExt(true), isCrypto()),
          statsModality(),
          0,
          2,
          this.mDebugEnabled,
          currentTimeMillis,
          3,
          this.mPreAuthInfo.confirmationRequested,
          this.mUserId,
          -1.0f);
      return;
    }
    long currentTimeMillis2 = System.currentTimeMillis() - this.mStartTimeMs;
    int i2 = i != 2 ? i != 3 ? 0 : 10 : 13;
    Slog.v(
        "BiometricService/AuthSession",
        "Dismissed! Modality: "
            + statsModality()
            + ", User: "
            + this.mUserId
            + ", IsCrypto: "
            + isCrypto()
            + ", Action: 2, Client: 2, Reason: "
            + i
            + ", Error: "
            + i2
            + ", Latency: "
            + currentTimeMillis2);
    if (i2 != 0) {
      this.mBiometricFrameworkStatsLogger.error(
          this.mBiometricContext.updateContext(new OperationContextExt(true), isCrypto()),
          statsModality(),
          2,
          2,
          this.mDebugEnabled,
          currentTimeMillis2,
          i2,
          0,
          this.mUserId);
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:15:0x0040 A[Catch: all -> 0x0095, RemoteException -> 0x0097, TryCatch #1 {RemoteException -> 0x0097, blocks: (B:5:0x000c, B:6:0x0012, B:7:0x0018, B:11:0x0027, B:12:0x0035, B:13:0x003c, B:15:0x0040, B:16:0x0061, B:18:0x0069, B:20:0x0073, B:21:0x007d, B:22:0x008b, B:23:0x005b, B:24:0x0099), top: B:2:0x0005, outer: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:18:0x0069 A[Catch: all -> 0x0095, RemoteException -> 0x0097, TryCatch #1 {RemoteException -> 0x0097, blocks: (B:5:0x000c, B:6:0x0012, B:7:0x0018, B:11:0x0027, B:12:0x0035, B:13:0x003c, B:15:0x0040, B:16:0x0061, B:18:0x0069, B:20:0x0073, B:21:0x007d, B:22:0x008b, B:23:0x005b, B:24:0x0099), top: B:2:0x0005, outer: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:22:0x008b A[Catch: all -> 0x0095, RemoteException -> 0x0097, TryCatch #1 {RemoteException -> 0x0097, blocks: (B:5:0x000c, B:6:0x0012, B:7:0x0018, B:11:0x0027, B:12:0x0035, B:13:0x003c, B:15:0x0040, B:16:0x0061, B:18:0x0069, B:20:0x0073, B:21:0x007d, B:22:0x008b, B:23:0x005b, B:24:0x0099), top: B:2:0x0005, outer: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:23:0x005b A[Catch: all -> 0x0095, RemoteException -> 0x0097, TryCatch #1 {RemoteException -> 0x0097, blocks: (B:5:0x000c, B:6:0x0012, B:7:0x0018, B:11:0x0027, B:12:0x0035, B:13:0x003c, B:15:0x0040, B:16:0x0061, B:18:0x0069, B:20:0x0073, B:21:0x007d, B:22:0x008b, B:23:0x005b, B:24:0x0099), top: B:2:0x0005, outer: #0 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void onDialogDismissed(int i, byte[] bArr) {
    byte[] bArr2;
    logOnDialogDismissed(i);
    try {
      try {
        switch (i) {
          case 1:
          case 4:
            bArr2 = this.mTokenEscrow;
            if (bArr2 == null) {
              Slog.d(
                  "BiometricService/AuthSession",
                  "addAuthToken: " + this.mKeyStore.addAuthToken(bArr2));
            } else {
              Slog.e("BiometricService/AuthSession", "mTokenEscrow is null");
            }
            if (this.mPromptInfo.semGetPrivilegedFlag() == 0) {
              AuthenticationResult authenticationResult = new AuthenticationResult();
              BiometricSensor biometricSensor = this.mCurrentSensor;
              if (biometricSensor != null) {
                authenticationResult =
                    (AuthenticationResult)
                        this.mAuthenticationResults.get(
                            biometricSensor.f1650id, authenticationResult);
              }
              this.mClientReceiver.onSemAuthenticationSucceeded(
                  Utils.getAuthenticationTypeForResult(i),
                  authenticationResult.f1649id,
                  authenticationResult.challengeToken);
              break;
            } else {
              this.mClientReceiver.onAuthenticationSucceeded(
                  Utils.getAuthenticationTypeForResult(i));
              break;
            }
          case 2:
            this.mClientReceiver.onDialogDismissed(i);
            break;
          case 3:
            this.mClientReceiver.onError(getEligibleModalities(), 10, 0);
            break;
          case 5:
          case 6:
            this.mClientReceiver.onError(
                getEligibleModalities(), this.mErrorEscrow, this.mVendorCodeEscrow);
            break;
          case 7:
            if (bArr != null) {
              this.mKeyStore.addAuthToken(bArr);
            } else {
              Slog.e("BiometricService/AuthSession", "credentialAttestation is null");
            }
            bArr2 = this.mTokenEscrow;
            if (bArr2 == null) {}
            if (this.mPromptInfo.semGetPrivilegedFlag() == 0) {}
            break;
          default:
            Slog.w("BiometricService/AuthSession", "Unhandled reason: " + i);
            break;
        }
      } catch (RemoteException e) {
        Slog.e("BiometricService/AuthSession", "Remote exception", e);
      }
      cancelAllSensors();
    } catch (Throwable th) {
      cancelAllSensors();
      throw th;
    }
  }

  public boolean onCancelAuthSession(boolean z) {
    if (hasAuthenticated()) {
      Slog.d("BiometricService/AuthSession", "onCancelAuthSession after successful auth");
      return true;
    }
    this.mCancelled = true;
    int i = this.mState;
    boolean z2 = i == 1 || i == 2 || i == 3;
    cancelAllSensors();
    if (z2 && !z) {
      return false;
    }
    try {
      this.mClientReceiver.onError(getEligibleModalities(), 5, 0);
      this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
      return true;
    } catch (RemoteException e) {
      Slog.e("BiometricService/AuthSession", "Remote exception", e);
      return false;
    }
  }

  public boolean isCrypto() {
    return this.mOperationId != 0;
  }

  public boolean containsCookie(int i) {
    Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
    while (it.hasNext()) {
      if (((BiometricSensor) it.next()).getCookie() == i) {
        return true;
      }
    }
    return false;
  }

  public final boolean isAllowDeviceCredential() {
    return Utils.isCredentialRequested(this.mPromptInfo);
  }

  public boolean allCookiesReceived() {
    int numSensorsWaitingForCookie = this.mPreAuthInfo.numSensorsWaitingForCookie();
    Slog.d("BiometricService/AuthSession", "Remaining cookies: " + numSensorsWaitingForCookie);
    return numSensorsWaitingForCookie == 0;
  }

  public int getState() {
    return this.mState;
  }

  public long getRequestId() {
    return this.mRequestId;
  }

  public final int statsModality() {
    Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
    int i = 0;
    while (it.hasNext()) {
      int i2 = ((BiometricSensor) it.next()).modality;
      if ((i2 & 2) != 0) {
        i |= 1;
      }
      if ((i2 & 4) != 0) {
        i |= 2;
      }
      if ((i2 & 8) != 0) {
        i |= 4;
      }
    }
    return i;
  }

  public final int sensorIdToModality(int i) {
    for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
      if (i == biometricSensor.f1650id) {
        return biometricSensor.modality;
      }
    }
    Slog.e("BiometricService/AuthSession", "Unknown sensor: " + i);
    return 0;
  }

  public final String getAcquiredMessageForSensor(int i, int i2, int i3) {
    int sensorIdToModality = sensorIdToModality(i);
    if (sensorIdToModality == 2) {
      return FingerprintManager.getAcquiredString(this.mContext, i2, i3);
    }
    if (sensorIdToModality != 8) {
      return null;
    }
    return FaceManager.getAuthHelpMessage(this.mContext, i2, i3);
  }

  public String toString() {
    return "State: "
        + this.mState
        + ", cancelled: "
        + this.mCancelled
        + ", isCrypto: "
        + isCrypto()
        + ", PreAuthInfo: "
        + this.mPreAuthInfo
        + ", requestId: "
        + this.mRequestId;
  }

  public void destroy() {
    this.mStatusBarService.closeSession(this.mRequestId);
    SemBioLoggingManager.get().bpStop((int) this.mRequestId, 0, this.mErrorEscrow);
  }

  public void onSwitchingSensorPressed(int i) {
    for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
      try {
        if (biometricSensor.modality == i) {
          setSensorsToStateWaitingForCookie(biometricSensor);
        } else if (biometricSensor.getCookie() != 0) {
          biometricSensor.goToStateCancelling(this.mToken, this.mOpPackageName, this.mRequestId);
          biometricSensor.goToStateUnknown();
        }
        this.mState = 5;
      } catch (RemoteException e) {
        Slog.e(
            "BiometricService/AuthSession",
            "onSwitchingSensorPressed, sensor: " + biometricSensor,
            e);
      }
    }
  }

  public void setErrorValue(int i, int i2) {
    this.mErrorEscrow = i;
    this.mVendorCodeEscrow = i2;
  }
}
