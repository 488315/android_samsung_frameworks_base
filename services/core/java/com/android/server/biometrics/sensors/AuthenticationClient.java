package com.android.server.biometrics.sensors;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.biometrics.AuthenticateOptions;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.security.KeyStore;
import android.util.EventLog;
import android.util.Slog;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class AuthenticationClient extends AcquisitionClient
    implements AuthenticationConsumer {
  public final ActivityManager mActivityManager;
  public final ActivityTaskManager mActivityTaskManager;
  public final boolean mAllowBackgroundAuthentication;
  public boolean mAuthAttempted;
  public boolean mAuthSuccess;
  public final BiometricManager mBiometricManager;
  public int mDisplayId;
  public boolean mIsBackgroundAuthenticationByVendorPolicy;
  public final boolean mIsRestricted;
  public final boolean mIsStrongBiometric;
  public boolean mIsVerifiedTopActivity;
  public final LockoutTracker mLockoutTracker;
  public final long mOperationId;
  public final AuthenticateOptions mOptions;
  public int mPromptPrivilegedFlags;
  public final boolean mRequireConfirmation;
  public final int mSensorStrength;
  public final boolean mShouldUseLockoutTracker;
  public long mStartTimeMs;
  public int mState;
  public final TaskStackListener mTaskStackListener;
  public boolean mUpdatedAuthenticated;

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public int getProtoEnum() {
    return 3;
  }

  public int handleFailedAttempt(int i) {
    return 0;
  }

  public abstract void handleLifecycleAfterAuth(boolean z);

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public boolean interruptsPrecedingClients() {
    return true;
  }

  public boolean semHasPromptPrivilegedAttr(int i) {
    return false;
  }

  public AuthenticationClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      long j,
      boolean z,
      AuthenticateOptions authenticateOptions,
      int i,
      boolean z2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      boolean z3,
      TaskStackListener taskStackListener,
      LockoutTracker lockoutTracker,
      boolean z4,
      boolean z5,
      int i2) {
    super(
        context,
        supplier,
        iBinder,
        clientMonitorCallbackConverter,
        authenticateOptions.getUserId(),
        authenticateOptions.getOpPackageName(),
        i,
        authenticateOptions.getSensorId(),
        z5,
        biometricLogger,
        biometricContext);
    this.mState = 0;
    this.mAuthSuccess = false;
    this.mIsStrongBiometric = z3;
    this.mOperationId = j;
    this.mRequireConfirmation = z2;
    this.mActivityTaskManager = getActivityTaskManager();
    this.mBiometricManager = (BiometricManager) context.getSystemService(BiometricManager.class);
    this.mTaskStackListener = taskStackListener;
    this.mLockoutTracker = lockoutTracker;
    this.mIsRestricted = z;
    this.mAllowBackgroundAuthentication = z4;
    this.mShouldUseLockoutTracker = lockoutTracker != null;
    this.mSensorStrength = i2;
    this.mOptions = authenticateOptions;
    this.mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
  }

  public long getStartTimeMs() {
    return this.mStartTimeMs;
  }

  public ActivityTaskManager getActivityTaskManager() {
    return ActivityTaskManager.getInstance();
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor,
            // android.os.IBinder.DeathRecipient
  public void binderDied() {
    binderDiedInternal(!isBiometricPrompt());
  }

  public long getOperationId() {
    return this.mOperationId;
  }

  public boolean isRestricted() {
    return this.mIsRestricted;
  }

  public boolean isKeyguard() {
    return Utils.isKeyguard(getContext(), getOwnerString());
  }

  public final boolean isSettings() {
    return Utils.isSettings(getContext(), getOwnerString());
  }

  public AuthenticateOptions getOptions() {
    return this.mOptions;
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public boolean isCryptoOperation() {
    return this.mOperationId != 0;
  }

  @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
  public void onAuthenticated(
      BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList) {
    boolean isBackground;
    boolean z2 = z;
    getLogger()
        .logOnAuthenticated(
            getContext(),
            getOperationContext(),
            z,
            this.mRequireConfirmation,
            getTargetUserId(),
            isBiometricPrompt());
    ClientMonitorCallbackConverter listener = getListener();
    Slog.v(
        "Biometrics/AuthenticationClient",
        "onAuthenticated("
            + z2
            + "), ID:"
            + identifier.getBiometricId()
            + ", Owner: "
            + getOwnerString()
            + ", isBP: "
            + isBiometricPrompt()
            + ", listener: "
            + listener
            + ", requireConfirmation: "
            + this.mRequireConfirmation
            + ", user: "
            + getTargetUserId()
            + ", clientMonitor: "
            + this);
    PerformanceTracker instanceForSensorId =
        PerformanceTracker.getInstanceForSensorId(getSensorId());
    if (isCryptoOperation()) {
      instanceForSensorId.incrementCryptoAuthForUser(getTargetUserId(), z2);
    } else {
      instanceForSensorId.incrementAuthForUser(getTargetUserId(), z2);
    }
    if (this.mAllowBackgroundAuthentication) {
      Slog.w(
          "Biometrics/AuthenticationClient",
          "Allowing background authentication, this is allowed only for platform or test"
              + " invocations");
    }
    if (this.mIsBackgroundAuthenticationByVendorPolicy) {
      isBackground = true;
    } else {
      isBackground =
          (this.mAllowBackgroundAuthentication
                  || this.mIsVerifiedTopActivity
                  || !z2
                  || Utils.isKeyguard(getContext(), getOwnerString())
                  || Utils.isSystem(getContext(), getOwnerString()))
              ? false
              : Utils.isBackground(getOwnerString());
    }
    if (isBackground) {
      Slog.e("Biometrics/AuthenticationClient", "Failing possible background authentication");
      ApplicationInfo applicationInfo = getContext().getApplicationInfo();
      Object[] objArr = new Object[3];
      objArr[0] = "159249069";
      objArr[1] = Integer.valueOf(applicationInfo != null ? applicationInfo.uid : -1);
      objArr[2] = "Attempted background authentication";
      EventLog.writeEvent(1397638484, objArr);
      z2 = false;
    }
    if (z2) {
      if (isBackground) {
        ApplicationInfo applicationInfo2 = getContext().getApplicationInfo();
        Object[] objArr2 = new Object[3];
        objArr2[0] = "159249069";
        objArr2[1] = Integer.valueOf(applicationInfo2 != null ? applicationInfo2.uid : -1);
        objArr2[2] = "Successful background authentication!";
        EventLog.writeEvent(1397638484, objArr2);
      }
      this.mAuthSuccess = true;
      markAlreadyDone();
      unregisterListenersForTaskStack();
      byte[] bArr = new byte[arrayList.size()];
      for (int i = 0; i < arrayList.size(); i++) {
        bArr[i] = ((Byte) arrayList.get(i)).byteValue();
      }
      if (this.mIsStrongBiometric) {
        this.mBiometricManager.resetLockoutTimeBound(
            getToken(), getContext().getOpPackageName(), getSensorId(), getTargetUserId(), bArr);
      }
      if (!isBiometricPrompt() && this.mIsStrongBiometric) {
        int addAuthToken = KeyStore.getInstance().addAuthToken(bArr);
        if (addAuthToken != 1) {
          Slog.d("Biometrics/AuthenticationClient", "Error adding auth token : " + addAuthToken);
        } else {
          Slog.d("Biometrics/AuthenticationClient", "addAuthToken: " + addAuthToken);
        }
      } else {
        Slog.d("Biometrics/AuthenticationClient", "Skipping addAuthToken");
      }
      try {
        if (listener != null) {
          if (this.mIsRestricted && this.mPromptPrivilegedFlags == 0) {
            listener.onAuthenticationSucceeded(
                getSensorId(), null, bArr, getTargetUserId(), this.mIsStrongBiometric);
          }
          listener.onAuthenticationSucceeded(
              getSensorId(), identifier, bArr, getTargetUserId(), this.mIsStrongBiometric);
        } else {
          Slog.e(
              "Biometrics/AuthenticationClient",
              "Received successful auth, but client was not listening");
        }
      } catch (RemoteException e) {
        Slog.e("Biometrics/AuthenticationClient", "Unable to notify listener", e);
        this.mCallback.onClientFinished(this, false);
        return;
      }
    } else if (isBackground) {
      Slog.e("Biometrics/AuthenticationClient", "cancelling due to background auth");
      cancel();
    } else {
      if (this.mShouldUseLockoutTracker && handleFailedAttempt(getTargetUserId()) != 0) {
        markAlreadyDone();
      }
      if (listener != null && this.mShouldVibrate) {
        vibrateError();
      }
      if (listener != null) {
        try {
          listener.onAuthenticationFailed(getSensorId());
        } catch (RemoteException e2) {
          Slog.e("Biometrics/AuthenticationClient", "Unable to notify listener", e2);
          this.mCallback.onClientFinished(this, false);
          return;
        }
      }
    }
    handleLifecycleAfterAuth(z2);
    this.mUpdatedAuthenticated = z2;
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient
  public void onAcquired(int i, int i2) {
    super.onAcquired(i, i2);
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient,
            // com.android.server.biometrics.sensors.ErrorConsumer
  public void onError(int i, int i2) {
    super.onError(i, i2);
    this.mState = 4;
    unregisterListenersForTaskStack();
  }

  @Override // com.android.server.biometrics.sensors.BaseClientMonitor
  public void start(ClientMonitorCallback clientMonitorCallback) {
    super.start(clientMonitorCallback);
    int lockoutStateFor =
        getBiometricContext()
            .getAuthSessionCoordinator()
            .getLockoutStateFor(getTargetUserId(), this.mSensorStrength);
    if (lockoutStateFor != 0) {
      Slog.v(
          "Biometrics/AuthenticationClient",
          "In lockout mode(" + lockoutStateFor + ") ; disallowing authentication");
      onError(lockoutStateFor == 1 ? 7 : 9, 0);
      return;
    }
    TaskStackListener taskStackListener = this.mTaskStackListener;
    if (taskStackListener != null) {
      this.mActivityTaskManager.registerTaskStackListener(taskStackListener);
      ActivityManager.SemProcessListener semProcessListener = this.mTaskStackListener;
      if (semProcessListener instanceof ActivityManager.SemProcessListener) {
        this.mActivityManager.semRegisterProcessListener(semProcessListener);
      }
    }
    Slog.d("Biometrics/AuthenticationClient", "Requesting auth for " + getOwnerString());
    this.mStartTimeMs = System.currentTimeMillis();
    this.mAuthAttempted = true;
    startHalOperation();
  }

  @Override // com.android.server.biometrics.sensors.AcquisitionClient,
            // com.android.server.biometrics.sensors.BaseClientMonitor
  public void cancel() {
    super.cancel();
    unregisterListenersForTaskStack();
  }

  public int getState() {
    return this.mState;
  }

  public boolean wasAuthAttempted() {
    return this.mAuthAttempted;
  }

  public boolean wasAuthSuccessful() {
    return this.mAuthSuccess;
  }

  public int getSensorStrength() {
    return this.mSensorStrength;
  }

  public LockoutTracker getLockoutTracker() {
    return this.mLockoutTracker;
  }

  public int getShowOverlayReason() {
    if (isKeyguard()) {
      return 4;
    }
    if (isBiometricPrompt()) {
      return 3;
    }
    return isSettings() ? 6 : 5;
  }

  @Override // com.android.server.biometrics.sensors.HalClientMonitor,
            // com.android.server.biometrics.sensors.BaseClientMonitor
  public void destroy() {
    super.destroy();
    unregisterListenersForTaskStack();
  }

  public final void unregisterListenersForTaskStack() {
    TaskStackListener taskStackListener = this.mTaskStackListener;
    if (taskStackListener != null) {
      this.mActivityTaskManager.unregisterTaskStackListener(taskStackListener);
      ActivityManager.SemProcessListener semProcessListener = this.mTaskStackListener;
      if (semProcessListener instanceof ActivityManager.SemProcessListener) {
        this.mActivityManager.semUnregisterProcessListener(semProcessListener);
      }
    }
  }

  public boolean semIsAllowedBackgroundAuthentication() {
    return this.mAllowBackgroundAuthentication;
  }

  public void semSetDisplayId(int i) {
    this.mDisplayId = i;
  }

  public void semVerifyTopActivity() {
    this.mIsVerifiedTopActivity = true;
  }

  public void setPromptPrivilegedFlags(int i) {
    this.mPromptPrivilegedFlags = i;
  }

  public int getPromptPrivilegedFlags() {
    return this.mPromptPrivilegedFlags;
  }
}
