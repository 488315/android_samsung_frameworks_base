package com.android.server.biometrics;

import android.R;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.trust.ITrustManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.provider.Settings;
import android.security.KeyStore;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.DumpUtils;
import com.android.server.SystemService;
import com.android.server.biometrics.log.BiometricContext;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class BiometricService extends SystemService {
  AuthSession mAuthSession;
  public final BiometricContext mBiometricContext;
  BiometricStrengthController mBiometricStrengthController;
  public final DevicePolicyManager mDevicePolicyManager;
  public final List mEnabledOnKeyguardCallbacks;
  public final Handler mHandler;
  final IBiometricService.Stub mImpl;
  public final Injector mInjector;
  KeyStore mKeyStore;
  public final Random mRandom;
  public final Supplier mRequestCounter;
  public final ArrayList mSensors;
  final SettingObserver mSettingObserver;
  SemBiometricSysUiWrapper mStatusBarService;
  ITrustManager mTrustManager;
  public final UserManager mUserManager;

  class InvalidationTracker {
    public final IInvalidationCallback mClientCallback;
    public final Set mSensorsPendingInvalidation = new ArraySet();

    public static InvalidationTracker start(
        Context context,
        ArrayList arrayList,
        int i,
        int i2,
        IInvalidationCallback iInvalidationCallback) {
      return new InvalidationTracker(context, arrayList, i, i2, iInvalidationCallback);
    }

    public InvalidationTracker(
        Context context,
        ArrayList arrayList,
        int i,
        int i2,
        IInvalidationCallback iInvalidationCallback) {
      this.mClientCallback = iInvalidationCallback;
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        final BiometricSensor biometricSensor = (BiometricSensor) it.next();
        if (biometricSensor.f1650id != i2
            && Utils.isAtLeastStrength(biometricSensor.oemStrength, 15)) {
          try {
          } catch (RemoteException e) {
            Slog.e("BiometricService", "Remote Exception", e);
          }
          if (biometricSensor.impl.hasEnrolledTemplates(i, context.getOpPackageName())) {
            Slog.d(
                "BiometricService",
                "Requesting authenticatorId invalidation for sensor: " + biometricSensor.f1650id);
            synchronized (this) {
              this.mSensorsPendingInvalidation.add(Integer.valueOf(biometricSensor.f1650id));
            }
            try {
              biometricSensor.impl.invalidateAuthenticatorId(
                  i,
                  new IInvalidationCallback
                      .Stub() { // from class:
                                // com.android.server.biometrics.BiometricService.InvalidationTracker.1
                    public void onCompleted() {
                      InvalidationTracker.this.onInvalidated(biometricSensor.f1650id);
                    }
                  });
            } catch (RemoteException e2) {
              Slog.d("BiometricService", "RemoteException", e2);
            }
          } else {
            continue;
          }
        }
      }
      synchronized (this) {
        if (this.mSensorsPendingInvalidation.isEmpty()) {
          try {
            Slog.d("BiometricService", "No sensors require invalidation");
            this.mClientCallback.onCompleted();
          } catch (RemoteException e3) {
            Slog.e("BiometricService", "Remote Exception", e3);
          }
        }
      }
    }

    public void onInvalidated(int i) {
      synchronized (this) {
        this.mSensorsPendingInvalidation.remove(Integer.valueOf(i));
        Slog.d(
            "BiometricService",
            "Sensor "
                + i
                + " invalidated, remaining size: "
                + this.mSensorsPendingInvalidation.size());
        if (this.mSensorsPendingInvalidation.isEmpty()) {
          try {
            this.mClientCallback.onCompleted();
          } catch (RemoteException e) {
            Slog.e("BiometricService", "Remote Exception", e);
          }
        }
      }
    }
  }

  public class SettingObserver extends ContentObserver {
    public final Uri BIOMETRIC_APP_ENABLED;
    public final Uri BIOMETRIC_FP_BIO_STAR_ENABLED;
    public final Uri BIOMETRIC_KEYGUARD_ENABLED;
    public final Uri FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION;
    public final Uri FACE_UNLOCK_APP_ENABLED;
    public final Uri FACE_UNLOCK_KEYGUARD_ENABLED;
    public final Map mBiometricEnabledForApps;
    public final Map mBiometricEnabledOnKeyguard;
    public final List mCallbacks;
    public final ContentResolver mContentResolver;
    public final Map mFaceAlwaysRequireConfirmation;
    public final boolean mUseLegacyFaceOnlySettings;

    public SettingObserver(Context context, Handler handler, List list) {
      super(handler);
      this.FACE_UNLOCK_KEYGUARD_ENABLED = Settings.Secure.getUriFor("face_unlock_keyguard_enabled");
      this.FACE_UNLOCK_APP_ENABLED = Settings.Secure.getUriFor("face_unlock_app_enabled");
      this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION =
          Settings.Secure.getUriFor("face_unlock_always_require_confirmation");
      this.BIOMETRIC_KEYGUARD_ENABLED = Settings.Secure.getUriFor("biometric_keyguard_enabled");
      this.BIOMETRIC_APP_ENABLED = Settings.Secure.getUriFor("biometric_app_enabled");
      this.BIOMETRIC_FP_BIO_STAR_ENABLED =
          Settings.Secure.getUriFor("sem_biometric_fp_bio_start_enabled");
      this.mBiometricEnabledOnKeyguard = new HashMap();
      this.mBiometricEnabledForApps = new HashMap();
      this.mFaceAlwaysRequireConfirmation = new HashMap();
      this.mContentResolver = context.getContentResolver();
      this.mCallbacks = list;
      this.mUseLegacyFaceOnlySettings =
          Build.VERSION.DEVICE_INITIAL_SDK_INT <= 29
              && context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")
              && !context.getPackageManager().hasSystemFeature("android.hardware.fingerprint");
      updateContentObserver();
    }

    public void updateContentObserver() {
      this.mContentResolver.unregisterContentObserver(this);
      if (this.mUseLegacyFaceOnlySettings) {
        this.mContentResolver.registerContentObserver(
            this.FACE_UNLOCK_KEYGUARD_ENABLED, false, this, -1);
        this.mContentResolver.registerContentObserver(
            this.FACE_UNLOCK_APP_ENABLED, false, this, -1);
      } else {
        this.mContentResolver.registerContentObserver(
            this.BIOMETRIC_KEYGUARD_ENABLED, false, this, -1);
        this.mContentResolver.registerContentObserver(this.BIOMETRIC_APP_ENABLED, false, this, -1);
      }
      this.mContentResolver.registerContentObserver(
          this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION, false, this, -1);
      boolean z = SemBiometricFeature.FEATURE_LOGGING_MODE;
      this.mContentResolver.registerContentObserver(
          this.BIOMETRIC_FP_BIO_STAR_ENABLED, false, this, -1);
      SemBioLoggingManager.get()
          .setFpBioStar(
              Settings.Secure.getInt(this.mContentResolver, "sem_biometric_fp_bio_start_enabled", 0)
                  != 0);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
      if (this.FACE_UNLOCK_KEYGUARD_ENABLED.equals(uri)) {
        this.mBiometricEnabledOnKeyguard.put(
            Integer.valueOf(i),
            Boolean.valueOf(
                Settings.Secure.getIntForUser(
                        this.mContentResolver, "face_unlock_keyguard_enabled", 1, i)
                    != 0));
        if (i != ActivityManager.getCurrentUser() || z) {
          return;
        }
        notifyEnabledOnKeyguardCallbacks(i);
        return;
      }
      if (this.FACE_UNLOCK_APP_ENABLED.equals(uri)) {
        this.mBiometricEnabledForApps.put(
            Integer.valueOf(i),
            Boolean.valueOf(
                Settings.Secure.getIntForUser(
                        this.mContentResolver, "face_unlock_app_enabled", 1, i)
                    != 0));
        return;
      }
      if (this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION.equals(uri)) {
        this.mFaceAlwaysRequireConfirmation.put(
            Integer.valueOf(i),
            Boolean.valueOf(
                Settings.Secure.getIntForUser(
                        this.mContentResolver, "face_unlock_always_require_confirmation", 0, i)
                    != 0));
        return;
      }
      if (this.BIOMETRIC_KEYGUARD_ENABLED.equals(uri)) {
        this.mBiometricEnabledOnKeyguard.put(
            Integer.valueOf(i),
            Boolean.valueOf(
                Settings.Secure.getIntForUser(
                        this.mContentResolver, "biometric_keyguard_enabled", 1, i)
                    != 0));
        if (i != ActivityManager.getCurrentUser() || z) {
          return;
        }
        notifyEnabledOnKeyguardCallbacks(i);
        return;
      }
      if (this.BIOMETRIC_APP_ENABLED.equals(uri)) {
        this.mBiometricEnabledForApps.put(
            Integer.valueOf(i),
            Boolean.valueOf(
                Settings.Secure.getIntForUser(this.mContentResolver, "biometric_app_enabled", 1, i)
                    != 0));
      } else if (this.BIOMETRIC_FP_BIO_STAR_ENABLED.equals(uri)) {
        SemBioLoggingManager.get()
            .setFpBioStar(
                Settings.Secure.getIntForUser(
                        this.mContentResolver, "sem_biometric_fp_bio_start_enabled", 0, i)
                    != 0);
      }
    }

    public boolean getEnabledOnKeyguard(int i) {
      if (!this.mBiometricEnabledOnKeyguard.containsKey(Integer.valueOf(i))) {
        if (this.mUseLegacyFaceOnlySettings) {
          onChange(true, this.FACE_UNLOCK_KEYGUARD_ENABLED, i);
        } else {
          onChange(true, this.BIOMETRIC_KEYGUARD_ENABLED, i);
        }
      }
      return ((Boolean) this.mBiometricEnabledOnKeyguard.get(Integer.valueOf(i))).booleanValue();
    }

    public boolean getEnabledForApps(int i) {
      if (!this.mBiometricEnabledForApps.containsKey(Integer.valueOf(i))) {
        if (this.mUseLegacyFaceOnlySettings) {
          onChange(true, this.FACE_UNLOCK_APP_ENABLED, i);
        } else {
          onChange(true, this.BIOMETRIC_APP_ENABLED, i);
        }
      }
      return ((Boolean)
              this.mBiometricEnabledForApps.getOrDefault(Integer.valueOf(i), Boolean.TRUE))
          .booleanValue();
    }

    public boolean getConfirmationAlwaysRequired(int i, int i2) {
      if (i != 8) {
        return false;
      }
      if (!this.mFaceAlwaysRequireConfirmation.containsKey(Integer.valueOf(i2))) {
        onChange(true, this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION, i2);
      }
      return ((Boolean) this.mFaceAlwaysRequireConfirmation.get(Integer.valueOf(i2)))
          .booleanValue();
    }

    public void notifyEnabledOnKeyguardCallbacks(int i) {
      List list = this.mCallbacks;
      for (int i2 = 0; i2 < list.size(); i2++) {
        ((EnabledOnKeyguardCallback) list.get(i2))
            .notify(
                ((Boolean)
                        this.mBiometricEnabledOnKeyguard.getOrDefault(
                            Integer.valueOf(i), Boolean.TRUE))
                    .booleanValue(),
                i);
      }
    }
  }

  public final class EnabledOnKeyguardCallback implements IBinder.DeathRecipient {
    public final IBiometricEnabledOnKeyguardCallback mCallback;

    public EnabledOnKeyguardCallback(
        IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
      this.mCallback = iBiometricEnabledOnKeyguardCallback;
      try {
        iBiometricEnabledOnKeyguardCallback.asBinder().linkToDeath(this, 0);
      } catch (RemoteException e) {
        Slog.w("BiometricService", "Unable to linkToDeath", e);
      }
    }

    public void notify(boolean z, int i) {
      try {
        this.mCallback.onChanged(z, i);
      } catch (DeadObjectException e) {
        Slog.w("BiometricService", "Death while invoking notify", e);
        BiometricService.this.mEnabledOnKeyguardCallbacks.remove(this);
      } catch (RemoteException e2) {
        Slog.w("BiometricService", "Failed to invoke onChanged", e2);
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Slog.e("BiometricService", "Enabled callback binder died");
      BiometricService.this.mEnabledOnKeyguardCallbacks.remove(this);
    }
  }

  /* renamed from: com.android.server.biometrics.BiometricService$1 */
  public class C08211 extends IBiometricSensorReceiver.Stub {
    public final /* synthetic */ long val$requestId;

    public C08211(long j) {
      this.val$requestId = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSemAuthenticationSucceeded$0(
        long j, int i, byte[] bArr, Bundle bundle) {
      BiometricService.this.handleAuthenticationSucceeded(j, i, bArr, bundle);
    }

    public void onSemAuthenticationSucceeded(final int i, final byte[] bArr, final Bundle bundle) {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08211.this.lambda$onSemAuthenticationSucceeded$0(
                  j, i, bArr, bundle);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAuthenticationSucceeded$1(long j, int i, byte[] bArr) {
      BiometricService.this.handleAuthenticationSucceeded(j, i, bArr, null);
    }

    public void onAuthenticationSucceeded(final int i, final byte[] bArr) {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08211.this.lambda$onAuthenticationSucceeded$1(j, i, bArr);
            }
          });
    }

    public void onAuthenticationFailed(final int i) {
      Slog.v("BiometricService", "onAuthenticationFailed");
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08211.this.lambda$onAuthenticationFailed$2(j, i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAuthenticationFailed$2(long j, int i) {
      BiometricService.this.handleAuthenticationRejected(j, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$3(long j, int i, int i2, int i3, int i4) {
      BiometricService.this.handleAuthenticationTimedOut(j, i, i2, i3, i4);
    }

    public void onError(final int i, final int i2, final int i3, final int i4) {
      if (i3 == 3) {
        Handler handler = BiometricService.this.mHandler;
        final long j = this.val$requestId;
        handler.post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                BiometricService.C08211.this.lambda$onError$3(j, i, i2, i3, i4);
              }
            });
      } else {
        Handler handler2 = BiometricService.this.mHandler;
        final long j2 = this.val$requestId;
        handler2.post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                BiometricService.C08211.this.lambda$onError$4(j2, i, i2, i3, i4);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$4(long j, int i, int i2, int i3, int i4) {
      BiometricService.this.handleOnError(j, i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$5(long j, int i, int i2, int i3) {
      BiometricService.this.handleOnAcquired(j, i, i2, i3);
    }

    public void onAcquired(final int i, final int i2, final int i3) {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08211.this.lambda$onAcquired$5(j, i, i2, i3);
            }
          });
    }
  }

  public final IBiometricSensorReceiver createBiometricSensorReceiver(long j) {
    return new C08211(j);
  }

  /* renamed from: com.android.server.biometrics.BiometricService$2 */
  public class C08222 extends SemBiometricSysUiReceiver {
    public final /* synthetic */ long val$requestId;

    public C08222(long j) {
      this.val$requestId = j;
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onDialogDismissed(final int i, final byte[] bArr) {
      BiometricService.this.mStatusBarService.endSession(2);
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onDialogDismissed$0(j, i, bArr);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDialogDismissed$0(long j, int i, byte[] bArr) {
      BiometricService.this.handleOnDismissed(j, i, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTryAgainPressed$1(long j) {
      BiometricService.this.handleOnTryAgainPressed(j);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onTryAgainPressed() {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onTryAgainPressed$1(j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeviceCredentialPressed$2(long j) {
      BiometricService.this.handleOnDeviceCredentialPressed(j);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onDeviceCredentialPressed() {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onDeviceCredentialPressed$2(j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemEvent$3(long j, int i) {
      BiometricService.this.handleOnSystemEvent(j, i);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onSystemEvent(final int i) {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onSystemEvent$3(j, i);
            }
          });
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onDialogAnimatedIn(final boolean z) {
      BiometricService.this.mStatusBarService.startSession(2);
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onDialogAnimatedIn$4(j, z);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDialogAnimatedIn$4(long j, boolean z) {
      BiometricService.this.handleOnDialogAnimatedIn(j, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStartFingerprintNow$5(long j) {
      BiometricService.this.handleOnStartFingerprintNow(j);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onStartFingerprintNow() {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onStartFingerprintNow$5(j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onModalitySwitched$6(int i) {
      BiometricService.this.handleOnSwitchingSensorPressed(i);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onModalitySwitched(final int i) {
      BiometricService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onModalitySwitched$6(i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSysUiError$7(long j, int i, int i2) {
      BiometricService.this.handleOnErrorFromSysUi(j, i, i2);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiReceiver
    public void onSysUiError(final int i, final int i2) {
      Handler handler = BiometricService.this.mHandler;
      final long j = this.val$requestId;
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.C08222.this.lambda$onSysUiError$7(j, i, i2);
            }
          });
    }
  }

  public final IBiometricSysuiReceiver createSysuiReceiver(long j) {
    return new C08222(j);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createClientDeathReceiver$1(final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            BiometricService.this.lambda$createClientDeathReceiver$0(j);
          }
        });
  }

  public final AuthSession.ClientDeathReceiver createClientDeathReceiver(final long j) {
    return new AuthSession
        .ClientDeathReceiver() { // from class:
                                 // com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda3
      @Override // com.android.server.biometrics.AuthSession.ClientDeathReceiver
      public final void onClientDied() {
        BiometricService.this.lambda$createClientDeathReceiver$1(j);
      }
    };
  }

  public final class BiometricServiceWrapper extends IBiometricService.Stub {
    public BiometricServiceWrapper() {}

    public ITestSession createTestSession(
        int i, ITestSessionCallback iTestSessionCallback, String str) {
      super.createTestSession_enforcePermission();
      Iterator it = BiometricService.this.mSensors.iterator();
      while (it.hasNext()) {
        BiometricSensor biometricSensor = (BiometricSensor) it.next();
        if (biometricSensor.f1650id == i) {
          return biometricSensor.impl.createTestSession(iTestSessionCallback, str);
        }
      }
      Slog.e("BiometricService", "Unknown sensor for createTestSession: " + i);
      return null;
    }

    public List getSensorProperties(String str) {
      super.getSensorProperties_enforcePermission();
      ArrayList arrayList = new ArrayList();
      Iterator it = BiometricService.this.mSensors.iterator();
      while (it.hasNext()) {
        arrayList.add(
            SensorPropertiesInternal.from(
                ((BiometricSensor) it.next()).impl.getSensorProperties(str)));
      }
      return arrayList;
    }

    public void onReadyForAuthentication(final long j, final int i) {
      super.onReadyForAuthentication_enforcePermission();
      BiometricService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$BiometricServiceWrapper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.BiometricServiceWrapper.this.lambda$onReadyForAuthentication$0(j, i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReadyForAuthentication$0(long j, int i) {
      BiometricService.this.handleOnReadyForAuthentication(j, i);
    }

    public long authenticate(
        final IBinder iBinder,
        final long j,
        final int i,
        final IBiometricServiceReceiver iBiometricServiceReceiver,
        final String str,
        final PromptInfo promptInfo) {
      super.authenticate_enforcePermission();
      if (iBinder == null
          || iBiometricServiceReceiver == null
          || str == null
          || promptInfo == null) {
        Slog.e("BiometricService", "Unable to authenticate, one or more null arguments");
        return -1L;
      }
      if (!Utils.isValidAuthenticatorConfig(promptInfo)) {
        throw new SecurityException("Invalid authenticator configuration");
      }
      Utils.combineAuthenticatorBundles(promptInfo);
      if (promptInfo.isUseDefaultTitle() && TextUtils.isEmpty(promptInfo.getTitle())) {
        promptInfo.setTitle(BiometricService.this.getContext().getString(17042606));
      }
      if (promptInfo.isUseDefaultSubtitle() && TextUtils.isEmpty(promptInfo.getSubtitle())) {
        promptInfo.setSubtitle(
            BiometricService.this
                .getContext()
                .getString(R.string.config_defaultDockManagerPackageName));
      }
      final long longValue = ((Long) BiometricService.this.mRequestCounter.get()).longValue();
      BiometricService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$BiometricServiceWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.BiometricServiceWrapper.this.lambda$authenticate$1(
                  iBinder, longValue, j, i, iBiometricServiceReceiver, str, promptInfo);
            }
          });
      return longValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticate$1(
        IBinder iBinder,
        long j,
        long j2,
        int i,
        IBiometricServiceReceiver iBiometricServiceReceiver,
        String str,
        PromptInfo promptInfo) {
      BiometricService.this.handleAuthenticate(
          iBinder, j, j2, i, iBiometricServiceReceiver, str, promptInfo);
    }

    public void cancelAuthentication(IBinder iBinder, String str, final long j) {
      super.cancelAuthentication_enforcePermission();
      SomeArgs obtain = SomeArgs.obtain();
      obtain.arg1 = iBinder;
      obtain.arg2 = str;
      obtain.arg3 = Long.valueOf(j);
      BiometricService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.BiometricService$BiometricServiceWrapper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              BiometricService.BiometricServiceWrapper.this.lambda$cancelAuthentication$2(j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAuthentication$2(long j) {
      BiometricService.this.handleCancelAuthentication(j);
    }

    public int canAuthenticate(String str, int i, int i2, int i3) {
      super.canAuthenticate_enforcePermission();
      Slog.d(
          "BiometricService",
          "canAuthenticate: User=" + i + ", Caller=" + i2 + ", Authenticators=" + i3);
      if (!Utils.isValidAuthenticatorConfig(i3)) {
        throw new SecurityException("Invalid authenticator configuration");
      }
      try {
        return BiometricService.this.createPreAuthInfo(str, i, i3).getCanAuthenticateResult();
      } catch (RemoteException e) {
        Slog.e("BiometricService", "Remote exception", e);
        return 1;
      }
    }

    public boolean hasEnrolledBiometrics(int i, String str) {
      super.hasEnrolledBiometrics_enforcePermission();
      try {
        Iterator it = BiometricService.this.mSensors.iterator();
        while (it.hasNext()) {
          if (((BiometricSensor) it.next()).impl.hasEnrolledTemplates(i, str)) {
            return true;
          }
        }
        return false;
      } catch (RemoteException e) {
        Slog.e("BiometricService", "Remote exception", e);
        return false;
      }
    }

    public synchronized void registerAuthenticator(
        int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) {
      super.registerAuthenticator_enforcePermission();
      Slog.d("BiometricService", "Registering ID: " + i + " Modality: " + i2 + " Strength: " + i3);
      if (iBiometricAuthenticator == null) {
        throw new IllegalArgumentException(
            "Authenticator must not be null. Did you forget to modify the core/res/res/values/xml"
                + " overlay for config_biometric_sensors?");
      }
      if (i3 != 15 && i3 != 255 && i3 != 4095) {
        throw new IllegalStateException("Unsupported strength");
      }
      Iterator it = BiometricService.this.mSensors.iterator();
      while (it.hasNext()) {
        if (((BiometricSensor) it.next()).f1650id == i) {
          throw new IllegalStateException("Cannot register duplicate authenticator");
        }
      }
      BiometricService biometricService = BiometricService.this;
      biometricService.mSensors.add(
          new BiometricSensor(
              biometricService.getContext(),
              i,
              i2,
              i3,
              iBiometricAuthenticator) { // from class:
                                         // com.android.server.biometrics.BiometricService.BiometricServiceWrapper.1
            @Override // com.android.server.biometrics.BiometricSensor
            public boolean confirmationAlwaysRequired(int i4) {
              return BiometricService.this.mSettingObserver.getConfirmationAlwaysRequired(
                  this.modality, i4);
            }

            @Override // com.android.server.biometrics.BiometricSensor
            public boolean confirmationSupported() {
              return Utils.isConfirmationSupported(this.modality);
            }
          });
      BiometricService.this.mBiometricStrengthController.updateStrengths();
    }

    public void registerEnabledOnKeyguardCallback(
        IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
      super.registerEnabledOnKeyguardCallback_enforcePermission();
      BiometricService.this.mEnabledOnKeyguardCallbacks.add(
          BiometricService.this.new EnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback));
      try {
        Iterator it = BiometricService.this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
          int i = ((UserInfo) it.next()).id;
          iBiometricEnabledOnKeyguardCallback.onChanged(
              BiometricService.this.mSettingObserver.getEnabledOnKeyguard(i), i);
        }
      } catch (RemoteException e) {
        Slog.w("BiometricService", "Remote exception", e);
      }
    }

    public void invalidateAuthenticatorIds(
        int i, int i2, IInvalidationCallback iInvalidationCallback) {
      super.invalidateAuthenticatorIds_enforcePermission();
      InvalidationTracker.start(
          BiometricService.this.getContext(),
          BiometricService.this.mSensors,
          i,
          i2,
          iInvalidationCallback);
    }

    public long[] getAuthenticatorIds(int i) {
      super.getAuthenticatorIds_enforcePermission();
      ArrayList arrayList = new ArrayList();
      Iterator it = BiometricService.this.mSensors.iterator();
      while (it.hasNext()) {
        BiometricSensor biometricSensor = (BiometricSensor) it.next();
        try {
          boolean hasEnrolledTemplates =
              biometricSensor.impl.hasEnrolledTemplates(
                  i, BiometricService.this.getContext().getOpPackageName());
          long authenticatorId = biometricSensor.impl.getAuthenticatorId(i);
          if (hasEnrolledTemplates
              && Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15)) {
            arrayList.add(Long.valueOf(authenticatorId));
          } else {
            Slog.d(
                "BiometricService",
                "Sensor "
                    + biometricSensor
                    + ", sensorId "
                    + biometricSensor.f1650id
                    + ", hasEnrollments: "
                    + hasEnrolledTemplates
                    + " cannot participate in Keystore operations");
          }
        } catch (RemoteException e) {
          Slog.e("BiometricService", "RemoteException", e);
        }
      }
      long[] jArr = new long[arrayList.size()];
      for (int i2 = 0; i2 < arrayList.size(); i2++) {
        jArr[i2] = ((Long) arrayList.get(i2)).longValue();
      }
      return jArr;
    }

    public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) {
      super.resetLockoutTimeBound_enforcePermission();
      if (!Utils.isAtLeastStrength(
          BiometricService.this.getSensorForId(i).getCurrentStrength(), 15)) {
        Slog.w(
            "BiometricService",
            "Sensor: " + i + " is does not meet the required strength to request resetLockout");
        return;
      }
      Slog.d("BiometricService", "resetLockoutTimeBound: " + str + ", " + i + ", " + i2);
      Iterator it = BiometricService.this.mSensors.iterator();
      while (it.hasNext()) {
        BiometricSensor biometricSensor = (BiometricSensor) it.next();
        if (biometricSensor.f1650id != i) {
          try {
            SensorPropertiesInternal sensorProperties =
                biometricSensor.impl.getSensorProperties(
                    BiometricService.this.getContext().getOpPackageName());
            boolean z = sensorProperties.resetLockoutRequiresHardwareAuthToken;
            boolean z2 = z && !sensorProperties.resetLockoutRequiresChallenge;
            boolean z3 = !z;
            if (z2 || z3) {
              Slog.d(
                  "BiometricService",
                  "resetLockout from: "
                      + i
                      + ", for: "
                      + biometricSensor.f1650id
                      + ", userId: "
                      + i2);
              biometricSensor.impl.resetLockout(iBinder, str, i2, bArr);
            }
          } catch (RemoteException e) {
            Slog.e("BiometricService", "Remote exception", e);
          }
        }
      }
    }

    public void resetLockout(int i, byte[] bArr) {
      super.resetLockout_enforcePermission();
      StringBuilder sb = new StringBuilder();
      sb.append("resetLockout(userId=");
      sb.append(i);
      sb.append(", hat=");
      sb.append(bArr == null ? "null " : "present");
      sb.append(")");
      Slog.d("BiometricService", sb.toString());
      BiometricService.this
          .mBiometricContext
          .getAuthSessionCoordinator()
          .resetLockoutFor(i, 15, -1L);
    }

    public int getCurrentStrength(int i) {
      super.getCurrentStrength_enforcePermission();
      Iterator it = BiometricService.this.mSensors.iterator();
      while (it.hasNext()) {
        BiometricSensor biometricSensor = (BiometricSensor) it.next();
        if (biometricSensor.f1650id == i) {
          return biometricSensor.getCurrentStrength();
        }
      }
      Slog.e("BiometricService", "Unknown sensorId: " + i);
      return 0;
    }

    public int getCurrentModality(String str, int i, int i2, int i3) {
      super.getCurrentModality_enforcePermission();
      Slog.d(
          "BiometricService",
          "getCurrentModality: User=" + i + ", Caller=" + i2 + ", Authenticators=" + i3);
      if (!Utils.isValidAuthenticatorConfig(i3)) {
        throw new SecurityException("Invalid authenticator configuration");
      }
      try {
        return ((Integer)
                BiometricService.this
                    .createPreAuthInfo(str, i, i3)
                    .getPreAuthenticateStatus()
                    .first)
            .intValue();
      } catch (RemoteException e) {
        Slog.e("BiometricService", "Remote exception", e);
        return 0;
      }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    public int getSupportedModalities(int i) {
      super.getSupportedModalities_enforcePermission();
      Slog.d("BiometricService", "getSupportedModalities: Authenticators=" + i);
      if (!Utils.isValidAuthenticatorConfig(i)) {
        throw new SecurityException("Invalid authenticator configuration");
      }
      int isCredentialRequested = Utils.isCredentialRequested(i);
      if (Utils.isBiometricRequested(i)) {
        int publicBiometricStrength = Utils.getPublicBiometricStrength(i);
        Iterator it = BiometricService.this.mSensors.iterator();
        while (it.hasNext()) {
          BiometricSensor biometricSensor = (BiometricSensor) it.next();
          if (Utils.isAtLeastStrength(
              biometricSensor.getCurrentStrength(), publicBiometricStrength)) {
            isCredentialRequested =
                (isCredentialRequested == true ? 1 : 0) | biometricSensor.modality;
          }
        }
      }
      return isCredentialRequested;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpPermission(
          BiometricService.this.getContext(), "BiometricService", printWriter)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          try {
          } catch (RemoteException e) {
            Slog.e("BiometricService", "Remote exception", e);
          }
          if (strArr.length > 0) {
            if ("--proto".equals(strArr[0])) {
              boolean z = true;
              if (strArr.length <= 1 || !"--clear-scheduler-buffer".equals(strArr[1])) {
                z = false;
              }
              Slog.d("BiometricService", "ClearSchedulerBuffer: " + z);
              ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
              AuthSession authSession = BiometricService.this.mAuthSession;
              protoOutputStream.write(
                  1159641169922L, authSession != null ? authSession.getState() : 0);
              Iterator it = BiometricService.this.mSensors.iterator();
              while (it.hasNext()) {
                protoOutputStream.write(
                    2246267895809L,
                    ((BiometricSensor) it.next()).impl.dumpSensorServiceStateProto(z));
              }
              protoOutputStream.flush();
            }
          }
          BiometricService.this.dumpInternal(printWriter);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public PromptInfo semGetPromptInfo(int i) {
      super.semGetPromptInfo_enforcePermission();
      AuthSession authSession = BiometricService.this.mAuthSession;
      if (authSession == null || !authSession.containsCookie(i)) {
        return null;
      }
      return authSession.mPromptInfo;
    }
  }

  public final PreAuthInfo createPreAuthInfo(String str, int i, int i2) {
    PromptInfo promptInfo = new PromptInfo();
    promptInfo.setAuthenticators(i2);
    return PreAuthInfo.create(
        this.mTrustManager,
        this.mDevicePolicyManager,
        this.mSettingObserver,
        this.mSensors,
        i,
        promptInfo,
        str,
        false,
        getContext());
  }

  public class Injector {
    public IActivityManager getActivityManagerService() {
      return ActivityManager.getService();
    }

    public ITrustManager getTrustManager() {
      return ITrustManager.Stub.asInterface(ServiceManager.getService("trust"));
    }

    public IStatusBarService getStatusBarService() {
      return IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
    }

    public SettingObserver getSettingObserver(Context context, Handler handler, List list) {
      return new SettingObserver(context, handler, list);
    }

    public KeyStore getKeyStore() {
      return KeyStore.getInstance();
    }

    public boolean isDebugEnabled(Context context, int i) {
      return Utils.isDebugEnabled(context, i);
    }

    public void publishBinderService(
        BiometricService biometricService, IBiometricService.Stub stub) {
      biometricService.publishBinderService("biometric", stub);
    }

    public BiometricStrengthController getBiometricStrengthController(
        BiometricService biometricService) {
      return new BiometricStrengthController(biometricService);
    }

    public DevicePolicyManager getDevicePolicyManager(Context context) {
      return (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    public List getFingerprintSensorProperties(Context context) {
      FingerprintManager fingerprintManager;
      if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")
          && (fingerprintManager =
                  (FingerprintManager) context.getSystemService(FingerprintManager.class))
              != null) {
        return fingerprintManager.getSensorPropertiesInternal();
      }
      return new ArrayList();
    }

    public Supplier getRequestGenerator() {
      final AtomicLong atomicLong = new AtomicLong(0L);
      return new Supplier() { // from class:
                              // com.android.server.biometrics.BiometricService$Injector$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
          Long lambda$getRequestGenerator$0;
          lambda$getRequestGenerator$0 =
              BiometricService.Injector.lambda$getRequestGenerator$0(atomicLong);
          return lambda$getRequestGenerator$0;
        }
      };
    }

    public static /* synthetic */ Long lambda$getRequestGenerator$0(AtomicLong atomicLong) {
      return Long.valueOf(atomicLong.incrementAndGet());
    }

    public BiometricContext getBiometricContext(Context context) {
      return BiometricContext.getInstance(context);
    }

    public UserManager getUserManager(Context context) {
      return (UserManager) context.getSystemService(UserManager.class);
    }

    public SemBiometricSysUiWrapper createSysUiWrapper(
        Context context, BiometricContext biometricContext, IntFunction intFunction) {
      return new SemBiometricSysUiWrapper(
          context, biometricContext, getStatusBarService(), intFunction);
    }
  }

  public BiometricService(Context context) {
    this(context, new Injector());
  }

  public BiometricService(Context context, Injector injector) {
    super(context);
    this.mRandom = new Random();
    this.mSensors = new ArrayList();
    Handler handler = new Handler(Looper.getMainLooper());
    this.mHandler = handler;
    this.mInjector = injector;
    this.mDevicePolicyManager = injector.getDevicePolicyManager(context);
    this.mImpl = new BiometricServiceWrapper();
    ArrayList arrayList = new ArrayList();
    this.mEnabledOnKeyguardCallbacks = arrayList;
    this.mSettingObserver = injector.getSettingObserver(context, handler, arrayList);
    this.mRequestCounter = injector.getRequestGenerator();
    this.mBiometricContext = injector.getBiometricContext(context);
    this.mUserManager = injector.getUserManager(context);
    SemBiometricSysUiManager.newInstance(context);
    try {
      injector
          .getActivityManagerService()
          .registerUserSwitchObserver(
              new UserSwitchObserver() { // from class:
                                         // com.android.server.biometrics.BiometricService.3
                public void onUserSwitchComplete(int i) {
                  BiometricService.this.mSettingObserver.updateContentObserver();
                  BiometricService.this.mSettingObserver.notifyEnabledOnKeyguardCallbacks(i);
                }
              },
              BiometricService.class.getName());
    } catch (RemoteException e) {
      Slog.e("BiometricService", "Failed to register user switch observer", e);
    }
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    this.mKeyStore = this.mInjector.getKeyStore();
    this.mStatusBarService =
        this.mInjector.createSysUiWrapper(
            getContext(),
            this.mBiometricContext,
            new IntFunction() { // from class:
                                // com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda0
              @Override // java.util.function.IntFunction
              public final Object apply(int i) {
                Integer lambda$onStart$2;
                lambda$onStart$2 = BiometricService.this.lambda$onStart$2(i);
                return lambda$onStart$2;
              }
            });
    this.mTrustManager = this.mInjector.getTrustManager();
    this.mInjector.publishBinderService(this, this.mImpl);
    BiometricStrengthController biometricStrengthController =
        this.mInjector.getBiometricStrengthController(this);
    this.mBiometricStrengthController = biometricStrengthController;
    biometricStrengthController.startListening();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Integer lambda$onStart$2(int i) {
    Iterator it = this.mSensors.iterator();
    while (it.hasNext()) {
      BiometricSensor biometricSensor = (BiometricSensor) it.next();
      if (biometricSensor.f1650id == i) {
        return Integer.valueOf(biometricSensor.modality);
      }
    }
    return 0;
  }

  public final boolean isStrongBiometric(int i) {
    Iterator it = this.mSensors.iterator();
    while (it.hasNext()) {
      BiometricSensor biometricSensor = (BiometricSensor) it.next();
      if (biometricSensor.f1650id == i) {
        return Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15);
      }
    }
    Slog.e("BiometricService", "Unknown sensorId: " + i);
    return false;
  }

  public final AuthSession getAuthSessionIfCurrent(long j) {
    AuthSession authSession = this.mAuthSession;
    if (authSession == null || authSession.getRequestId() != j) {
      return null;
    }
    return authSession;
  }

  public final void handleAuthenticationSucceeded(long j, int i, byte[] bArr, Bundle bundle) {
    Slog.v("BiometricService", "handleAuthenticationSucceeded(), sensorId: " + i);
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.e("BiometricService", "handleAuthenticationSucceeded: AuthSession is null");
    } else {
      authSessionIfCurrent.onAuthenticationSucceeded(i, isStrongBiometric(i), bArr, bundle);
    }
  }

  public final void handleAuthenticationRejected(long j, int i) {
    Slog.v("BiometricService", "handleAuthenticationRejected()");
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleAuthenticationRejected: AuthSession is not current");
    } else {
      authSessionIfCurrent.onAuthenticationRejected(i);
    }
  }

  public final void handleAuthenticationTimedOut(long j, int i, int i2, int i3, int i4) {
    Slog.v(
        "BiometricService",
        "handleAuthenticationTimedOut(), sensorId: "
            + i
            + ", cookie: "
            + i2
            + ", error: "
            + i3
            + ", vendorCode: "
            + i4);
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleAuthenticationTimedOut: AuthSession is not current");
    } else {
      authSessionIfCurrent.onAuthenticationTimedOut(i, i2, i3, i4);
    }
  }

  public final void handleOnError(long j, int i, int i2, int i3, int i4) {
    Slog.d(
        "BiometricService",
        "handleOnError() sensorId: "
            + i
            + ", cookie: "
            + i2
            + ", error: "
            + i3
            + ", vendorCode: "
            + i4);
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnError: AuthSession is not current");
      return;
    }
    try {
      if (authSessionIfCurrent.onErrorReceived(i, i2, i3, i4)) {
        Slog.d("BiometricService", "handleOnError: AuthSession finished");
        AuthSession authSession = this.mAuthSession;
        if (authSession != null) {
          authSession.destroy();
        }
        this.mAuthSession = null;
      }
    } catch (RemoteException e) {
      Slog.e("BiometricService", "RemoteException", e);
    }
  }

  public final void handleOnAcquired(long j, int i, int i2, int i3) {
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "onAcquired: AuthSession is not current");
    } else {
      authSessionIfCurrent.onAcquired(i, i2, i3);
    }
  }

  public final void handleOnDismissed(long j, int i, byte[] bArr) {
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.e("BiometricService", "onDismissed: " + i + ", AuthSession is not current");
      return;
    }
    authSessionIfCurrent.onDialogDismissed(i, bArr);
    AuthSession authSession = this.mAuthSession;
    if (authSession != null) {
      authSession.destroy();
    }
    this.mAuthSession = null;
  }

  public final void handleOnTryAgainPressed(long j) {
    Slog.d("BiometricService", "onTryAgainPressed");
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnTryAgainPressed: AuthSession is not current");
    } else {
      authSessionIfCurrent.onTryAgainPressed();
    }
  }

  public final void handleOnDeviceCredentialPressed(long j) {
    Slog.d("BiometricService", "onDeviceCredentialPressed");
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnDeviceCredentialPressed: AuthSession is not current");
    } else {
      authSessionIfCurrent.onDeviceCredentialPressed();
    }
  }

  public final void handleOnSystemEvent(long j, int i) {
    Slog.d("BiometricService", "onSystemEvent: " + i);
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnSystemEvent: AuthSession is not current");
    } else {
      authSessionIfCurrent.onSystemEvent(i);
    }
  }

  /* renamed from: handleClientDied, reason: merged with bridge method [inline-methods] */
  public final void lambda$createClientDeathReceiver$0(long j) {
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleClientDied: AuthSession is not current");
      return;
    }
    Slog.e("BiometricService", "Session: " + authSessionIfCurrent);
    if (authSessionIfCurrent.onClientDied()) {
      AuthSession authSession = this.mAuthSession;
      if (authSession != null) {
        authSession.destroy();
      }
      this.mAuthSession = null;
    }
  }

  public final void handleOnDialogAnimatedIn(long j, boolean z) {
    Slog.d("BiometricService", "handleOnDialogAnimatedIn");
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnDialogAnimatedIn: AuthSession is not current");
    } else {
      authSessionIfCurrent.onDialogAnimatedIn(z);
    }
  }

  public final void handleOnStartFingerprintNow(long j) {
    Slog.d("BiometricService", "handleOnStartFingerprintNow");
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnStartFingerprintNow: AuthSession is not current");
    } else {
      authSessionIfCurrent.onStartFingerprint();
    }
  }

  public final void handleOnReadyForAuthentication(long j, int i) {
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleOnReadyForAuthentication: AuthSession is not current");
    } else {
      authSessionIfCurrent.onCookieReceived(i);
    }
  }

  public final void handleAuthenticate(
      final IBinder iBinder,
      final long j,
      final long j2,
      final int i,
      final IBiometricServiceReceiver iBiometricServiceReceiver,
      final String str,
      final PromptInfo promptInfo) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            BiometricService.this.lambda$handleAuthenticate$3(
                i, promptInfo, str, j, iBinder, j2, iBiometricServiceReceiver);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleAuthenticate$3(
      int i,
      PromptInfo promptInfo,
      String str,
      long j,
      IBinder iBinder,
      long j2,
      IBiometricServiceReceiver iBiometricServiceReceiver) {
    try {
      PreAuthInfo create =
          PreAuthInfo.create(
              this.mTrustManager,
              this.mDevicePolicyManager,
              this.mSettingObserver,
              this.mSensors,
              i,
              promptInfo,
              str,
              promptInfo.isDisallowBiometricsIfPolicyExists(),
              getContext());
      Pair preAuthenticateStatus = create.getPreAuthenticateStatus();
      Slog.d(
          "BiometricService",
          "handleAuthenticate: modality("
              + preAuthenticateStatus.first
              + "), status("
              + preAuthenticateStatus.second
              + "), preAuthInfo: "
              + create
              + " requestId: "
              + j
              + " promptInfo.isIgnoreEnrollmentState: "
              + promptInfo.isIgnoreEnrollmentState());
      if (((Integer) preAuthenticateStatus.second).intValue() != 0
          && ((Integer) preAuthenticateStatus.second).intValue() != 18) {
        iBiometricServiceReceiver.onError(
            ((Integer) preAuthenticateStatus.first).intValue(),
            ((Integer) preAuthenticateStatus.second).intValue(),
            0);
        return;
      }
      if (create.credentialRequested
          && create.credentialAvailable
          && create.eligibleSensors.isEmpty()) {
        promptInfo.setAuthenticators(32768);
      }
      authenticateInternal(iBinder, j, j2, i, iBiometricServiceReceiver, str, promptInfo, create);
    } catch (RemoteException e) {
      Slog.e("BiometricService", "Remote exception", e);
    }
  }

  public final void authenticateInternal(
      IBinder iBinder,
      long j,
      long j2,
      int i,
      IBiometricServiceReceiver iBiometricServiceReceiver,
      String str,
      PromptInfo promptInfo,
      PreAuthInfo preAuthInfo) {
    Slog.d("BiometricService", "Creating authSession with authRequest: " + preAuthInfo);
    if (this.mAuthSession != null) {
      Slog.w("BiometricService", "Existing AuthSession: " + this.mAuthSession);
      this.mAuthSession.onCancelAuthSession(true);
      this.mAuthSession.destroy();
      this.mAuthSession = null;
    }
    AuthSession authSession =
        new AuthSession(
            getContext(),
            this.mBiometricContext,
            this.mStatusBarService,
            createSysuiReceiver(j),
            this.mKeyStore,
            this.mRandom,
            createClientDeathReceiver(j),
            preAuthInfo,
            iBinder,
            j,
            j2,
            i,
            createBiometricSensorReceiver(j),
            iBiometricServiceReceiver,
            str,
            promptInfo,
            this.mInjector.isDebugEnabled(getContext(), i),
            this.mInjector.getFingerprintSensorProperties(getContext()));
    this.mAuthSession = authSession;
    try {
      authSession.goToInitialState();
    } catch (RemoteException e) {
      Slog.e("BiometricService", "RemoteException", e);
    }
  }

  public final void handleCancelAuthentication(long j) {
    AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
    if (authSessionIfCurrent == null) {
      Slog.w("BiometricService", "handleCancelAuthentication: AuthSession is not current");
      return;
    }
    if (authSessionIfCurrent.onCancelAuthSession(false)) {
      Slog.d("BiometricService", "handleCancelAuthentication: AuthSession finished");
      AuthSession authSession = this.mAuthSession;
      if (authSession != null) {
        authSession.destroy();
      }
      this.mAuthSession = null;
    }
  }

  public final BiometricSensor getSensorForId(int i) {
    Iterator it = this.mSensors.iterator();
    while (it.hasNext()) {
      BiometricSensor biometricSensor = (BiometricSensor) it.next();
      if (biometricSensor.f1650id == i) {
        return biometricSensor;
      }
    }
    return null;
  }

  public final void dumpInternal(PrintWriter printWriter) {
    printWriter.println("Legacy Settings: " + this.mSettingObserver.mUseLegacyFaceOnlySettings);
    printWriter.println();
    printWriter.println("Sensors:");
    Iterator it = this.mSensors.iterator();
    while (it.hasNext()) {
      printWriter.println(" " + ((BiometricSensor) it.next()));
    }
    printWriter.println();
    printWriter.println("CurrentSession: " + this.mAuthSession);
    printWriter.println();
    SemBioLoggingManager.get().bpDump(printWriter);
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    super.onBootPhase(i);
    if (i == 1000) {
      SemBioFgThread.get()
          .execute(
              new Runnable() { // from class:
                               // com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  BiometricService.this.initBiometricsTimestamp();
                }
              });
      SemBioAnalyticsManager.getInstance().onBootComplete(getContext());
    }
  }

  public final void initBiometricsTimestamp() {
    long currentTimeMillis = System.currentTimeMillis();
    UserManager userManager = UserManager.get(getContext());
    Slog.i(
        "BiometricService",
        "initBiometricsTimestamp: user length = " + userManager.getUsers().size());
    Iterator it = userManager.getUsers().iterator();
    while (it.hasNext()) {
      int userOrWorkProfileId =
          Utils.getUserOrWorkProfileId(getContext(), ((UserInfo) it.next()).id);
      long longDb =
          Utils.getLongDb(
              getContext(), "biometrics_strong_enroll_timestamp", true, -1L, userOrWorkProfileId);
      boolean z = Utils.DEBUG;
      if (z) {
        Slog.i("BiometricService", "timestamp db = [" + userOrWorkProfileId + "]: " + longDb);
      }
      if (longDb == -1) {
        long j = 0;
        if (Utils.isAutoTime(getContext())) {
          Iterator it2 = this.mSensors.iterator();
          while (true) {
            if (!it2.hasNext()) {
              break;
            }
            BiometricSensor biometricSensor = (BiometricSensor) it2.next();
            if (Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), 15)) {
              try {
                if (biometricSensor.impl.hasEnrolledTemplates(
                    userOrWorkProfileId, "BiometricService")) {
                  if (Utils.DEBUG) {
                    Slog.d(
                        "BiometricService",
                        "timestamp authenticator id: " + biometricSensor.f1650id);
                  }
                  j = System.currentTimeMillis();
                }
              } catch (Exception e) {
                Slog.w("BiometricService", "initBiometricsTimestamp: " + e.getMessage());
              }
            }
          }
        } else if (z) {
          Slog.d("BiometricService", "initBiometricsTimestamp: No auto time");
        }
        Utils.putLongDb(
            getContext(), "biometrics_strong_enroll_timestamp", true, j, userOrWorkProfileId);
      }
    }
    Slog.i(
        "BiometricService",
        "initBiometricsTimestamp: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }

  public final void handleOnSwitchingSensorPressed(int i) {
    AuthSession authSession = this.mAuthSession;
    if (authSession == null) {
      Slog.e("BiometricService", "handleOnSwitchingSensorPressed: AuthSession is null");
    } else {
      authSession.onSwitchingSensorPressed(i);
    }
  }

  public final void handleOnErrorFromSysUi(long j, int i, int i2) {
    AuthSession authSession = this.mAuthSession;
    if (authSession == null) {
      Slog.i("BiometricService", "handleOnErrorFromSysUi: AuthSession is null");
      return;
    }
    if (i == 3) {
      i2 = 2;
    } else if (i != 10) {
      i2 = 5;
    }
    authSession.setErrorValue(i2, 0);
    handleOnDismissed(j, 5, null);
  }
}
