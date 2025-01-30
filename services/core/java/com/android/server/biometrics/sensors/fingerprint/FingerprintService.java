package com.android.server.biometrics.sensors.fingerprint;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintServiceReceiver;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IFingerprintClientActiveCallback;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.fingerprint.ISidefpsController;
import android.hardware.fingerprint.IUdfpsOverlay;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.DumpUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.SystemService;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;
import com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21;
import com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.google.android.collect.Lists;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FingerprintService extends SystemService {
  public final Supplier mAidlInstanceNameSupplier;
  public final AppOpsManager mAppOps;
  public final Object mAuthenticationSyncLock;
  public final BiometricContext mBiometricContext;
  public final BiometricStateCallback mBiometricStateCallback;
  public final Function mFingerprintProvider;
  public final GestureAvailabilityDispatcher mGestureAvailabilityDispatcher;
  public final Handler mHandler;
  public final LockPatternUtils mLockPatternUtils;
  public final LockoutResetDispatcher mLockoutResetDispatcher;
  public final FingerprintServiceRegistry mRegistry;
  public final SemFingerprintServiceExtImpl mServiceExtImpl;
  final IFingerprintService.Stub mServiceWrapper;

  /* renamed from: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1 */
  public class C08931 extends IFingerprintService.Stub {
    public C08931() {}

    public ITestSession createTestSession(
        int i, ITestSessionCallback iTestSessionCallback, String str) {
      super.createTestSession_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for createTestSession, sensorId: " + i);
        return null;
      }
      return serviceProvider.createTestSession(i, iTestSessionCallback, str);
    }

    public byte[] dumpSensorServiceStateProto(int i, boolean z) {
      super.dumpSensorServiceStateProto_enforcePermission();
      ProtoOutputStream protoOutputStream = new ProtoOutputStream();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider != null) {
        serviceProvider.dumpProtoState(i, protoOutputStream, z);
      }
      protoOutputStream.flush();
      return protoOutputStream.getBytes();
    }

    public List getSensorPropertiesInternal(String str) {
      if (FingerprintService.this
              .getContext()
              .checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL")
          != 0) {
        Utils.checkPermission(
            FingerprintService.this.getContext(), "android.permission.TEST_BIOMETRIC");
      }
      return FingerprintService.this.mRegistry.getAllProperties();
    }

    public FingerprintSensorPropertiesInternal getSensorProperties(int i, String str) {
      super.getSensorProperties_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w(
            "FingerprintService",
            "No matching sensor for getSensorProperties, sensorId: " + i + ", caller: " + str);
        return null;
      }
      return serviceProvider.getSensorProperties(i);
    }

    public void generateChallenge(
        IBinder iBinder,
        int i,
        int i2,
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        String str) {
      super.generateChallenge_enforcePermission();
      if (Utils.DEBUG) {
        Slog.i("FingerprintService", "generateChallenge: " + i + ", " + i2 + ", " + str);
      }
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "No matching sensor for generateChallenge, sensorId: " + i);
        return;
      }
      serviceProvider.scheduleGenerateChallenge(i, i2, iBinder, iFingerprintServiceReceiver, str);
    }

    public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) {
      super.revokeChallenge_enforcePermission();
      if (Utils.DEBUG) {
        Slog.i("FingerprintService", "revokeChallenge: " + i + ", " + i2 + ", " + str);
      }
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "No matching sensor for revokeChallenge, sensorId: " + i);
        return;
      }
      serviceProvider.scheduleRevokeChallenge(i, i2, iBinder, str, j);
    }

    public long enroll(
        IBinder iBinder,
        byte[] bArr,
        int i,
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        String str,
        int i2) {
      super.enroll_enforcePermission();
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FingerprintService", "Null provider for enroll");
        return -1L;
      }
      Slog.i("FingerprintService", "enroll: [" + i + "] from pkg=" + str);
      if (bArr == null || bArr.length == 0) {
        return -1L;
      }
      Pair canUseFingerprint =
          FingerprintService.this.mServiceExtImpl.canUseFingerprint(false, false);
      if (((Integer) canUseFingerprint.first).intValue() != 0) {
        try {
          iFingerprintServiceReceiver.onError(
              ((Integer) canUseFingerprint.first).intValue(),
              ((Integer) canUseFingerprint.second).intValue());
          return 0L;
        } catch (RemoteException e) {
          e.printStackTrace();
          return 0L;
        }
      }
      return ((ServiceProvider) singleProvider.second)
          .scheduleEnroll(
              ((Integer) singleProvider.first).intValue(),
              iBinder,
              bArr,
              Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i),
              iFingerprintServiceReceiver,
              str,
              i2);
    }

    public void cancelEnrollment(IBinder iBinder, long j) {
      super.cancelEnrollment_enforcePermission();
      Slog.i("FingerprintService", "cancelEnrollment: " + iBinder);
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FingerprintService", "Null provider for cancelEnrollment");
      } else {
        ((ServiceProvider) singleProvider.second)
            .cancelEnrollment(((Integer) singleProvider.first).intValue(), iBinder, j);
      }
    }

    public long authenticate(
        IBinder iBinder,
        long j,
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
      Pair pair;
      int callingUid = Binder.getCallingUid();
      int callingPid = Binder.getCallingPid();
      int callingUserId = UserHandle.getCallingUserId();
      String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
      String attributionTag = fingerprintAuthenticateOptions.getAttributionTag();
      int userId = fingerprintAuthenticateOptions.getUserId();
      Slog.i("FingerprintService", "authenticate: [" + userId + "] from pkg=" + opPackageName);
      if (!FingerprintService.this.canUseFingerprint(
          opPackageName, attributionTag, true, callingUid, callingPid, callingUserId)) {
        Slog.w("FingerprintService", "Authenticate rejecting package: " + opPackageName);
        return -1L;
      }
      boolean isKeyguard = Utils.isKeyguard(FingerprintService.this.getContext(), opPackageName);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      if (isKeyguard) {
        try {
          if (Utils.isUserEncryptedOrLockdown(FingerprintService.this.mLockPatternUtils, userId)) {
            EventLog.writeEvent(1397638484, "79776455");
            Slog.e("FingerprintService", "Authenticate invoked when user is encrypted or lockdown");
            return -1L;
          }
        } finally {
        }
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      boolean z =
          FingerprintService.this
                  .getContext()
                  .checkCallingPermission("android.permission.MANAGE_FINGERPRINT")
              != 0;
      int i = isKeyguard ? 1 : 3;
      if (fingerprintAuthenticateOptions.getSensorId() == -1) {
        pair = FingerprintService.this.mRegistry.getSingleProvider();
      } else {
        Utils.checkPermission(
            FingerprintService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
        pair =
            new Pair(
                Integer.valueOf(fingerprintAuthenticateOptions.getSensorId()),
                (ServiceProvider)
                    FingerprintService.this.mRegistry.getProviderForSensor(
                        fingerprintAuthenticateOptions.getSensorId()));
      }
      if (pair == null) {
        Slog.w("FingerprintService", "Null provider for authenticate");
        return -1L;
      }
      fingerprintAuthenticateOptions.setSensorId(((Integer) pair.first).intValue());
      FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal =
          (FingerprintSensorPropertiesInternal)
              ((ServiceProvider) pair.second)
                  .getSensorProperties(fingerprintAuthenticateOptions.getSensorId());
      if (!isKeyguard
          && !Utils.isSettings(FingerprintService.this.getContext(), opPackageName)
          && fingerprintSensorPropertiesInternal != null
          && (fingerprintSensorPropertiesInternal.isAnyUdfpsType()
              || fingerprintSensorPropertiesInternal.isAnySidefpsType())) {
        try {
          return authenticateWithPrompt(
              j,
              fingerprintSensorPropertiesInternal,
              callingUid,
              callingUserId,
              iFingerprintServiceReceiver,
              opPackageName,
              fingerprintAuthenticateOptions.isIgnoreEnrollmentState());
        } catch (PackageManager.NameNotFoundException e) {
          Slog.e("FingerprintService", "Invalid package", e);
          return -1L;
        }
      }
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        VirtualDeviceManagerInternal virtualDeviceManagerInternal =
            (VirtualDeviceManagerInternal)
                FingerprintService.this.getLocalService(VirtualDeviceManagerInternal.class);
        if (virtualDeviceManagerInternal != null) {
          virtualDeviceManagerInternal.onAuthenticationPrompt(callingUid);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return ((ServiceProvider) pair.second)
            .scheduleAuthenticate(
                iBinder,
                j,
                0,
                new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
                fingerprintAuthenticateOptions,
                z,
                i,
                isKeyguard);
      } finally {
      }
    }

    public final long authenticateWithPrompt(
        long j,
        final FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal,
        int i,
        final int i2,
        final IFingerprintServiceReceiver iFingerprintServiceReceiver,
        String str,
        boolean z) {
      Context uiContext = FingerprintService.this.getUiContext();
      Context createPackageContextAsUser =
          uiContext.createPackageContextAsUser(str, 0, UserHandle.getUserHandleForUid(i));
      Executor mainExecutor = uiContext.getMainExecutor();
      return new BiometricPrompt.Builder(createPackageContextAsUser)
          .setTitle(uiContext.getString(R.string.config_defaultFieldClassificationService))
          .setSubtitle(uiContext.getString(R.string.launch_warning_replace))
          .setNegativeButton(
              uiContext.getString(R.string.cancel),
              mainExecutor,
              new DialogInterface
                  .OnClickListener() { // from class:
                                       // com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                  FingerprintService.C08931.lambda$authenticateWithPrompt$0(
                      iFingerprintServiceReceiver, dialogInterface, i3);
                }
              })
          .setIsForLegacyFingerprintManager(fingerprintSensorPropertiesInternal.sensorId)
          .setIgnoreEnrollmentState(z)
          .build()
          .authenticateForOperation(
              new CancellationSignal(),
              mainExecutor,
              new BiometricPrompt
                  .AuthenticationCallback() { // from class:
                                              // com.android.server.biometrics.sensors.fingerprint.FingerprintService.1.1
                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationError(int i3, CharSequence charSequence) {
                  try {
                    if (FingerprintUtils.isKnownErrorCode(i3)) {
                      iFingerprintServiceReceiver.onError(i3, 0);
                    } else {
                      iFingerprintServiceReceiver.onError(8, i3);
                    }
                  } catch (RemoteException e) {
                    Slog.e("FingerprintService", "Remote exception in onAuthenticationError()", e);
                  }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationSucceeded(
                    BiometricPrompt.AuthenticationResult authenticationResult) {
                  try {
                    iFingerprintServiceReceiver.onAuthenticationSucceeded(
                        new Fingerprint("", 0, 0L),
                        i2,
                        fingerprintSensorPropertiesInternal.sensorStrength == 2);
                  } catch (RemoteException e) {
                    Slog.e(
                        "FingerprintService", "Remote exception in onAuthenticationSucceeded()", e);
                  }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationFailed() {
                  try {
                    iFingerprintServiceReceiver.onAuthenticationFailed();
                  } catch (RemoteException e) {
                    Slog.e("FingerprintService", "Remote exception in onAuthenticationFailed()", e);
                  }
                }

                public void onAuthenticationAcquired(int i3) {
                  try {
                    if (FingerprintUtils.isKnownAcquiredCode(i3)) {
                      iFingerprintServiceReceiver.onAcquired(i3, 0);
                    } else {
                      iFingerprintServiceReceiver.onAcquired(6, i3);
                    }
                  } catch (RemoteException e) {
                    Slog.e(
                        "FingerprintService", "Remote exception in onAuthenticationAcquired()", e);
                  }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationHelp(int i3, CharSequence charSequence) {
                  onAuthenticationAcquired(i3);
                }
              },
              j);
    }

    public static /* synthetic */ void lambda$authenticateWithPrompt$0(
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        DialogInterface dialogInterface,
        int i) {
      try {
        iFingerprintServiceReceiver.onError(10, 0);
      } catch (RemoteException e) {
        Slog.e("FingerprintService", "Remote exception in negative button onClick()", e);
      }
    }

    public long detectFingerprint(
        IBinder iBinder,
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
      super.detectFingerprint_enforcePermission();
      String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
      Slog.i(
          "FingerprintService",
          "detectFingerprint: ["
              + fingerprintAuthenticateOptions.getUserId()
              + "] from pkg="
              + opPackageName);
      if (!Utils.isKeyguard(FingerprintService.this.getContext(), opPackageName)) {
        Slog.w(
            "FingerprintService",
            "detectFingerprint called from non-sysui package: " + opPackageName);
        return -1L;
      }
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FingerprintService", "Null provider for detectFingerprint");
        return -1L;
      }
      fingerprintAuthenticateOptions.setSensorId(((Integer) singleProvider.first).intValue());
      return ((ServiceProvider) singleProvider.second)
          .scheduleFingerDetect(
              iBinder,
              new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
              fingerprintAuthenticateOptions,
              1);
    }

    public void prepareForAuthentication(
        IBinder iBinder,
        long j,
        IBiometricSensorReceiver iBiometricSensorReceiver,
        FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
        long j2,
        int i,
        boolean z) {
      super.prepareForAuthentication_enforcePermission();
      if (Utils.DEBUG) {
        Slog.i("FingerprintService", "prepareForAuthentication: " + i);
      }
      int userOrWorkProfileId =
          Utils.getUserOrWorkProfileId(
              FingerprintService.this.getContext(), fingerprintAuthenticateOptions.getUserId());
      FingerprintAuthenticateOptions copyOptions =
          userOrWorkProfileId != fingerprintAuthenticateOptions.getUserId()
              ? FingerprintUtils.copyOptions(userOrWorkProfileId, fingerprintAuthenticateOptions)
              : fingerprintAuthenticateOptions;
      Pair canUseFingerprint =
          FingerprintService.this.mServiceExtImpl.canUseFingerprint(true, false);
      if (((Integer) canUseFingerprint.first).intValue() != 0) {
        try {
          iBiometricSensorReceiver.onError(
              copyOptions.getSensorId(),
              i,
              ((Integer) canUseFingerprint.first).intValue(),
              ((Integer) canUseFingerprint.second).intValue());
          return;
        } catch (RemoteException e) {
          e.printStackTrace();
          return;
        }
      }
      ServiceProvider serviceProvider =
          (ServiceProvider)
              FingerprintService.this.mRegistry.getProviderForSensor(copyOptions.getSensorId());
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for prepareForAuthentication");
      } else {
        serviceProvider.scheduleAuthenticate(
            iBinder,
            j,
            i,
            new ClientMonitorCallbackConverter(iBiometricSensorReceiver),
            copyOptions,
            j2,
            true,
            2,
            z);
      }
    }

    public void startPreparedClient(int i, int i2) {
      super.startPreparedClient_enforcePermission();
      if (Utils.DEBUG) {
        Slog.i("FingerprintService", "startPreparedClient: " + i2);
      }
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for startPreparedClient");
      } else {
        serviceProvider.startPreparedClient(i, i2);
      }
    }

    public void cancelAuthentication(IBinder iBinder, String str, String str2, long j) {
      int callingUid = Binder.getCallingUid();
      int callingPid = Binder.getCallingPid();
      int callingUserId = UserHandle.getCallingUserId();
      Slog.i("FingerprintService", "cancelAuthentication: " + str);
      synchronized (FingerprintService.this.mAuthenticationSyncLock) {
        if (!FingerprintService.this.canUseFingerprint(
            str, str2, false, callingUid, callingPid, callingUserId)) {
          Slog.w("FingerprintService", "cancelAuthentication rejecting package: " + str);
          return;
        }
        Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
        if (singleProvider == null) {
          Slog.w("FingerprintService", "Null provider for cancelAuthentication");
        } else {
          ((ServiceProvider) singleProvider.second)
              .cancelAuthentication(((Integer) singleProvider.first).intValue(), iBinder, j);
        }
      }
    }

    public void cancelFingerprintDetect(IBinder iBinder, String str, long j) {
      super.cancelFingerprintDetect_enforcePermission();
      Slog.i("FingerprintService", "cancelFingerprintDetect: " + str + ", " + j);
      if (!Utils.isKeyguard(FingerprintService.this.getContext(), str)) {
        Slog.w(
            "FingerprintService", "cancelFingerprintDetect called from non-sysui package: " + str);
        return;
      }
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FingerprintService", "Null provider for cancelFingerprintDetect");
      } else {
        ((ServiceProvider) singleProvider.second)
            .cancelAuthentication(((Integer) singleProvider.first).intValue(), iBinder, j);
      }
    }

    public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) {
      super.cancelAuthenticationFromService_enforcePermission();
      Slog.d("FingerprintService", "cancelAuthenticationFromService, sensorId: " + i);
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for cancelAuthenticationFromService");
      } else {
        serviceProvider.cancelAuthentication(i, iBinder, j);
      }
    }

    public void remove(
        IBinder iBinder,
        int i,
        int i2,
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        String str) {
      super.remove_enforcePermission();
      Slog.d("FingerprintService", "remove: " + i2 + ", " + str);
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FingerprintService", "Null provider for remove");
      } else {
        ((ServiceProvider) singleProvider.second)
            .scheduleRemove(
                ((Integer) singleProvider.first).intValue(),
                iBinder,
                iFingerprintServiceReceiver,
                i,
                i2,
                str);
      }
    }

    public void removeAll(
        IBinder iBinder,
        int i,
        final IFingerprintServiceReceiver iFingerprintServiceReceiver,
        String str) {
      super.removeAll_enforcePermission();
      Slog.d("FingerprintService", "removeAll: " + i + ", " + str);
      IFingerprintServiceReceiver iFingerprintServiceReceiver2 =
          new FingerprintServiceReceiver() { // from class:
                                             // com.android.server.biometrics.sensors.fingerprint.FingerprintService.1.2
            public final int numSensors;
            public int sensorsFinishedRemoving = 0;

            {
              this.numSensors =
                  C08931
                      .this
                      .getSensorPropertiesInternal(
                          FingerprintService.this.getContext().getOpPackageName())
                      .size();
            }

            public void onRemoved(Fingerprint fingerprint, int i2) {
              if (i2 == 0) {
                this.sensorsFinishedRemoving++;
                Slog.d(
                    "FingerprintService",
                    "sensorsFinishedRemoving: "
                        + this.sensorsFinishedRemoving
                        + ", numSensors: "
                        + this.numSensors);
                if (this.sensorsFinishedRemoving == this.numSensors) {
                  iFingerprintServiceReceiver.onRemoved((Fingerprint) null, 0);
                }
              }
            }
          };
      for (ServiceProvider serviceProvider : FingerprintService.this.mRegistry.getProviders()) {
        Iterator it = serviceProvider.getSensorProperties().iterator();
        while (it.hasNext()) {
          serviceProvider.scheduleRemoveAll(
              ((FingerprintSensorPropertiesInternal) it.next()).sensorId,
              iBinder,
              iFingerprintServiceReceiver2,
              i,
              str);
        }
      }
    }

    public void addLockoutResetCallback(
        IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) {
      super.addLockoutResetCallback_enforcePermission();
      Slog.d("FingerprintService", "addLockoutResetCallback: " + str);
      FingerprintService.this.mLockoutResetDispatcher.addCallback(
          iBiometricServiceLockoutResetCallback, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(
        FileDescriptor fileDescriptor,
        FileDescriptor fileDescriptor2,
        FileDescriptor fileDescriptor3,
        String[] strArr,
        ShellCallback shellCallback,
        ResultReceiver resultReceiver) {
      new FingerprintShellCommand(FingerprintService.this.getContext(), FingerprintService.this)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpPermission(
          FingerprintService.this.getContext(), "FingerprintService", printWriter)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          if (FingerprintService.this.mServiceExtImpl.isTpaCommand(strArr)) {
            FingerprintService.this.mServiceExtImpl.handleTpaCommand(printWriter, strArr);
            return;
          }
          if (strArr.length > 1 && "--proto".equals(strArr[0]) && "--state".equals(strArr[1])) {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
            for (ServiceProvider serviceProvider :
                FingerprintService.this.mRegistry.getProviders()) {
              Iterator it = serviceProvider.getSensorProperties().iterator();
              while (it.hasNext()) {
                serviceProvider.dumpProtoState(
                    ((FingerprintSensorPropertiesInternal) it.next()).sensorId,
                    protoOutputStream,
                    false);
              }
            }
            protoOutputStream.flush();
          } else if (strArr.length > 0 && "--proto".equals(strArr[0])) {
            for (ServiceProvider serviceProvider2 :
                FingerprintService.this.mRegistry.getProviders()) {
              Iterator it2 = serviceProvider2.getSensorProperties().iterator();
              while (it2.hasNext()) {
                serviceProvider2.dumpProtoMetrics(
                    ((FingerprintSensorPropertiesInternal) it2.next()).sensorId, fileDescriptor);
              }
            }
          } else {
            for (ServiceProvider serviceProvider3 :
                FingerprintService.this.mRegistry.getProviders()) {
              for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal :
                  serviceProvider3.getSensorProperties()) {
                printWriter.println(
                    "Dumping for sensorId: "
                        + fingerprintSensorPropertiesInternal.sensorId
                        + ", provider: "
                        + serviceProvider3.getClass().getSimpleName());
                StringBuilder sb = new StringBuilder();
                sb.append("Fps state: ");
                sb.append(FingerprintService.this.mBiometricStateCallback.getBiometricState());
                printWriter.println(sb.toString());
                serviceProvider3.dumpInternal(
                    fingerprintSensorPropertiesInternal.sensorId, printWriter);
                printWriter.println();
              }
            }
            FingerprintService.this.mServiceExtImpl.dump(printWriter);
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public boolean isHardwareDetectedDeprecated(String str, String str2) {
      if (!FingerprintService.this.canUseFingerprint(
          str,
          str2,
          false,
          Binder.getCallingUid(),
          Binder.getCallingPid(),
          UserHandle.getCallingUserId())) {
        return false;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
        if (singleProvider == null) {
          Slog.w(
              "FingerprintService",
              "Null provider for isHardwareDetectedDeprecated, caller: " + str);
          return false;
        }
        return ((ServiceProvider) singleProvider.second)
            .isHardwareDetected(((Integer) singleProvider.first).intValue());
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isHardwareDetected(int i, String str) {
      super.isHardwareDetected_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for isHardwareDetected, caller: " + str);
        return false;
      }
      return serviceProvider.isHardwareDetected(i);
    }

    public void rename(int i, int i2, String str) {
      super.rename_enforcePermission();
      if (Utils.isCurrentUserOrProfile(FingerprintService.this.getContext(), i2)) {
        Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
        if (singleProvider == null) {
          Slog.w("FingerprintService", "Null provider for rename");
        } else {
          ((ServiceProvider) singleProvider.second)
              .rename(((Integer) singleProvider.first).intValue(), i, i2, str);
        }
      }
    }

    public List getEnrolledFingerprints(int i, String str, String str2) {
      if (!FingerprintService.this.canUseFingerprint(
          str,
          str2,
          false,
          Binder.getCallingUid(),
          Binder.getCallingPid(),
          UserHandle.getCallingUserId())) {
        return Collections.emptyList();
      }
      if (i != UserHandle.getCallingUserId()) {
        Utils.checkPermission(
            FingerprintService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
      }
      return FingerprintService.this.getEnrolledFingerprintsDeprecated(
          Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i), str);
    }

    public boolean hasEnrolledFingerprintsDeprecated(int i, String str, String str2) {
      if (!FingerprintService.this.canUseFingerprint(
          str,
          str2,
          false,
          Binder.getCallingUid(),
          Binder.getCallingPid(),
          UserHandle.getCallingUserId())) {
        return false;
      }
      if (i != UserHandle.getCallingUserId()) {
        Utils.checkPermission(
            FingerprintService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
      }
      return !FingerprintService.this
          .getEnrolledFingerprintsDeprecated(
              Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i), str)
          .isEmpty();
    }

    public boolean hasEnrolledFingerprints(int i, int i2, String str) {
      super.hasEnrolledFingerprints_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider != null) {
        return serviceProvider
                .getEnrolledFingerprints(
                    i, Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i2))
                .size()
            > 0;
      }
      Slog.w("FingerprintService", "Null provider for hasEnrolledFingerprints, caller: " + str);
      return false;
    }

    public int getLockoutModeForUser(int i, int i2) {
      super.getLockoutModeForUser_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "getLockoutModeForUser: " + i + ", " + i2);
      }
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for getLockoutModeForUser");
        return 0;
      }
      return serviceProvider.getLockoutModeForUser(i, i2);
    }

    public void invalidateAuthenticatorId(
        int i, int i2, IInvalidationCallback iInvalidationCallback) {
      super.invalidateAuthenticatorId_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "invalidateAuthenticatorId: " + i + ", " + i2);
      }
      int userOrWorkProfileId =
          Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i2);
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for invalidateAuthenticatorId");
      } else {
        serviceProvider.scheduleInvalidateAuthenticatorId(
            i, userOrWorkProfileId, iInvalidationCallback);
      }
    }

    public long getAuthenticatorId(int i, int i2) {
      super.getAuthenticatorId_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "getAuthenticatorId: " + i + ", " + i2);
      }
      int userOrWorkProfileId =
          Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i2);
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for getAuthenticatorId");
        return 0L;
      }
      return serviceProvider.getAuthenticatorId(i, userOrWorkProfileId);
    }

    public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) {
      super.resetLockout_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "resetLockout: " + i + ", " + i2 + ", " + str);
      }
      int userOrWorkProfileId =
          Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i2);
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "Null provider for resetLockout, caller: " + str);
        return;
      }
      serviceProvider.scheduleResetLockout(i, userOrWorkProfileId, bArr);
    }

    public boolean isClientActive() {
      super.isClientActive_enforcePermission();
      return FingerprintService.this.mGestureAvailabilityDispatcher.isAnySensorActive();
    }

    public void addClientActiveCallback(
        IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
      super.addClientActiveCallback_enforcePermission();
      FingerprintService.this.mGestureAvailabilityDispatcher.registerCallback(
          iFingerprintClientActiveCallback);
    }

    public void removeClientActiveCallback(
        IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
      super.removeClientActiveCallback_enforcePermission();
      FingerprintService.this.mGestureAvailabilityDispatcher.removeCallback(
          iFingerprintClientActiveCallback);
    }

    public void registerAuthenticators(final List list) {
      super.registerAuthenticators_enforcePermission();
      FingerprintService.this.mRegistry.registerAll(
          new Supplier() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
              List lambda$registerAuthenticators$1;
              lambda$registerAuthenticators$1 =
                  FingerprintService.C08931.this.lambda$registerAuthenticators$1(list);
              return lambda$registerAuthenticators$1;
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List lambda$registerAuthenticators$1(List list) {
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      String[] strArr = (String[]) FingerprintService.this.mAidlInstanceNameSupplier.get();
      if (strArr != null) {
        arrayList2.addAll(Lists.newArrayList(strArr));
      }
      FingerprintService fingerprintService = FingerprintService.this;
      arrayList.addAll(
          fingerprintService.getAidlProviders(
              Utils.filterAvailableHalInstances(fingerprintService.getContext(), arrayList2)));
      if (arrayList.isEmpty()) {
        arrayList.addAll(FingerprintService.this.getHidlProviders(list));
      }
      return arrayList;
    }

    public void addAuthenticatorsRegisteredCallback(
        IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) {
      super.addAuthenticatorsRegisteredCallback_enforcePermission();
      FingerprintService.this.mRegistry.addAllRegisteredCallback(
          iFingerprintAuthenticatorsRegisteredCallback);
    }

    public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) {
      super.registerBiometricStateListener_enforcePermission();
      FingerprintService.this.mBiometricStateCallback.registerBiometricStateListener(
          iBiometricStateListener);
    }

    public void onPointerDown(long j, int i, PointerContext pointerContext) {
      super.onPointerDown_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "No matching provider for onFingerDown, sensorId: " + i);
        return;
      }
      serviceProvider.onPointerDown(j, i, pointerContext);
    }

    public void onPointerUp(long j, int i, PointerContext pointerContext) {
      super.onPointerUp_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "No matching provider for onFingerUp, sensorId: " + i);
        return;
      }
      serviceProvider.onPointerUp(j, i, pointerContext);
    }

    public void onUiReady(long j, int i) {
      super.onUiReady_enforcePermission();
      ServiceProvider serviceProvider =
          (ServiceProvider) FingerprintService.this.mRegistry.getProviderForSensor(i);
      if (serviceProvider == null) {
        Slog.w("FingerprintService", "No matching provider for onUiReady, sensorId: " + i);
        return;
      }
      serviceProvider.onUiReady(j, i);
    }

    public void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) {
      super.setUdfpsOverlayController_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "setUdfpsOverlayController: " + Binder.getCallingPid());
      }
    }

    public void setSidefpsController(ISidefpsController iSidefpsController) {
      super.setSidefpsController_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "setSidefpsController: " + Binder.getCallingPid());
      }
    }

    public void setUdfpsOverlay(IUdfpsOverlay iUdfpsOverlay) {
      super.setUdfpsOverlay_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "setUdfpsOverlay: " + Binder.getCallingPid());
      }
    }

    public void onPowerPressed() {
      super.onPowerPressed_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "onPowerPressed");
      }
      Iterator it = FingerprintService.this.mRegistry.getProviders().iterator();
      while (it.hasNext()) {
        ((ServiceProvider) it.next()).onPowerPressed();
      }
    }

    public void scheduleWatchdog() {
      super.scheduleWatchdog_enforcePermission();
      if (Utils.DEBUG) {
        Slog.d("FingerprintService", "scheduleWatchdog");
      }
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        Slog.w("FingerprintService", "Null provider for scheduling watchdog");
      } else {
        ((ServiceProvider) singleProvider.second)
            .scheduleWatchdog(((Integer) singleProvider.first).intValue());
      }
    }

    public long semAuthenticate(
        IBinder iBinder,
        long j,
        IFingerprintServiceReceiver iFingerprintServiceReceiver,
        FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
        Bundle bundle) {
      boolean semHasPrivilegedFlag;
      Pair pair;
      boolean z;
      int userOrWorkProfileId;
      int callingUid = Binder.getCallingUid();
      int callingPid = Binder.getCallingPid();
      int callingUserId = UserHandle.getCallingUserId();
      String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
      String attributionTag = fingerprintAuthenticateOptions.getAttributionTag();
      int userId = fingerprintAuthenticateOptions.getUserId();
      Slog.i("FingerprintService", "semAuthenticate: [" + userId + "] from pkg=" + opPackageName);
      Bundle bundle2 = bundle == null ? new Bundle() : bundle;
      synchronized (FingerprintService.this.mAuthenticationSyncLock) {
        if (!FingerprintService.this.canUseFingerprint(
            opPackageName, attributionTag, true, callingUid, callingPid, callingUserId)) {
          Slog.w("FingerprintService", "Authenticate rejecting package: " + opPackageName);
          return -1L;
        }
        FingerprintService.this.mServiceExtImpl.applyAndClearIFAAFlag(opPackageName, bundle2);
        boolean isKeyguard = Utils.isKeyguard(FingerprintService.this.getContext(), opPackageName);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          boolean z2 = bundle2.getBoolean("EXTRA_KEY_ALLOW_EVEN_IF_ENCRYPTED_OR_LOCKDOWN");
          if (isKeyguard
              && Utils.isUserEncryptedOrLockdown(FingerprintService.this.mLockPatternUtils, userId)
              && !z2) {
            EventLog.writeEvent(1397638484, "79776455");
            Slog.e("FingerprintService", "Authenticate invoked when user is encrypted or lockdown");
            return -1L;
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          Pair canUseFingerprint =
              FingerprintService.this.mServiceExtImpl.canUseFingerprint(true, isKeyguard);
          if (((Integer) canUseFingerprint.first).intValue() != 0) {
            try {
              iFingerprintServiceReceiver.onError(
                  ((Integer) canUseFingerprint.first).intValue(),
                  ((Integer) canUseFingerprint.second).intValue());
            } catch (RemoteException e) {
              e.printStackTrace();
            }
            return 0L;
          }
          boolean z3 =
              (Utils.hasPermission(
                          FingerprintService.this.getContext(),
                          "com.samsung.android.permission.FINGERPRINT_PRIVILEGED")
                      || Utils.hasPermission(
                          FingerprintService.this.getContext(),
                          "android.permission.MANAGE_BIOMETRIC")
                      || Utils.hasPermission(
                          FingerprintService.this.getContext(),
                          "com.samsung.android.permission.BIOMETRICS_PRIVILEGED"))
                  ? false
                  : true;
          if (z3) {
            bundle2.remove("sem_privileged_attr");
            semHasPrivilegedFlag = false;
          } else {
            semHasPrivilegedFlag = FingerprintUtils.semHasPrivilegedFlag(bundle2, 4);
          }
          int i = isKeyguard ? 1 : 3;
          if (fingerprintAuthenticateOptions.getSensorId() == -1) {
            pair = FingerprintService.this.mRegistry.getSingleProvider();
          } else {
            Utils.checkPermission(
                FingerprintService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
            pair =
                new Pair(
                    Integer.valueOf(fingerprintAuthenticateOptions.getSensorId()),
                    (ServiceProvider)
                        FingerprintService.this.mRegistry.getProviderForSensor(
                            fingerprintAuthenticateOptions.getSensorId()));
          }
          if (pair == null) {
            Slog.w("FingerprintService", "Null provider for authenticate");
            return -1L;
          }
          fingerprintAuthenticateOptions.setSensorId(((Integer) pair.first).intValue());
          if (isKeyguard
              && FingerprintService.this.mServiceExtImpl.isEnrollSession(
                  fingerprintAuthenticateOptions.getSensorId())) {
            FingerprintService.this.mServiceExtImpl.revokeChallengeInternally(
                fingerprintAuthenticateOptions.getSensorId());
          }
          FingerprintAuthenticateOptions copyOptions =
              (isKeyguard
                      || userId
                          == (userOrWorkProfileId =
                              Utils.getUserOrWorkProfileId(
                                  FingerprintService.this.getContext(), userId)))
                  ? fingerprintAuthenticateOptions
                  : FingerprintUtils.copyOptions(
                      userOrWorkProfileId, fingerprintAuthenticateOptions);
          ServiceProvider serviceProvider = (ServiceProvider) pair.second;
          ClientMonitorCallbackConverter clientMonitorCallbackConverter =
              new ClientMonitorCallbackConverter(iFingerprintServiceReceiver);
          if (!isKeyguard && !semHasPrivilegedFlag) {
            z = false;
            return serviceProvider.semScheduleAuthenticate(
                iBinder, j, 0, clientMonitorCallbackConverter, copyOptions, z3, i, z, bundle2);
          }
          z = true;
          return serviceProvider.semScheduleAuthenticate(
              iBinder, j, 0, clientMonitorCallbackConverter, copyOptions, z3, i, z, bundle2);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public int semGetMaxEnrollmentNumber() {
      FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal =
          (FingerprintSensorPropertiesInternal)
              FingerprintService.this.mRegistry.getAllProperties().get(0);
      if (fingerprintSensorPropertiesInternal == null) {
        Slog.w("FingerprintService", "Null prop");
        return 4;
      }
      return fingerprintSensorPropertiesInternal.maxEnrollmentsPerUser;
    }

    public boolean semHasFeature(int i) {
      return FingerprintService.this.mServiceExtImpl.hasFeature(i);
    }

    public void semForceCBGE() {
      super.semForceCBGE_enforcePermission();
      FingerprintService.this.mServiceExtImpl.forceCBGE();
    }

    public boolean semIsEnrollSession() {
      super.semIsEnrollSession_enforcePermission();
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return FingerprintService.this.mServiceExtImpl.isEnrollSession(
          ((Integer) singleProvider.first).intValue());
    }

    public boolean semIsTemplateDbCorrupted() {
      super.semIsTemplateDbCorrupted_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.isTemplateDbCorrupted();
    }

    public int semGetSensorStatus() {
      return FingerprintService.this.mServiceExtImpl.getSensorStatus();
    }

    public boolean semPauseEnroll() {
      super.semPauseEnroll_enforcePermission();
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return FingerprintService.this.mServiceExtImpl.pauseEnroll(
          ((Integer) singleProvider.first).intValue());
    }

    public boolean semResumeEnroll() {
      super.semResumeEnroll_enforcePermission();
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return false;
      }
      return FingerprintService.this.mServiceExtImpl.resumeEnroll(
          ((Integer) singleProvider.first).intValue());
    }

    public boolean semOpenSession() {
      super.semOpenSession_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.openTrustAppSession();
    }

    public String semGetSensorInfo() {
      super.semGetSensorInfo_enforcePermission();
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      return singleProvider == null
          ? ""
          : FingerprintService.this.mServiceExtImpl.getSensorInfo(
              ((Integer) singleProvider.first).intValue());
    }

    public String[] semGetUserIdList() {
      super.semGetUserIdList_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.getUserIdList();
    }

    public String semGetDaemonVersion() {
      super.semGetDaemonVersion_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.getDaemonVersion();
    }

    public int semRunSensorTest(
        IBinder iBinder,
        int i,
        int i2,
        ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
      super.semRunSensorTest_enforcePermission();
      Pair singleProvider = FingerprintService.this.mRegistry.getSingleProvider();
      if (singleProvider == null) {
        return -1;
      }
      return FingerprintService.this.mServiceExtImpl.runSensorTest(
          ((Integer) singleProvider.first).intValue(),
          iBinder,
          i,
          i2,
          iSemFingerprintRequestCallback);
    }

    public int semGetSensorTestResult(byte[] bArr) {
      super.semGetSensorTestResult_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.getSensorTestResult(bArr);
    }

    public int semSetScreenStatus(int i) {
      super.semSetScreenStatus_enforcePermission();
      FingerprintService.this.mServiceExtImpl.setScreenStatusFromKeyguard(i == 0);
      return 0;
    }

    public int semShowBouncerScreen(int i) {
      super.semShowBouncerScreen_enforcePermission();
      FingerprintService.this.mServiceExtImpl.setBouncerScreen(i);
      return 0;
    }

    public IBinder semAddMaskView(IBinder iBinder, String str) {
      super.semAddMaskView_enforcePermission();
      return (iBinder == null || str == null)
          ? iBinder
          : FingerprintService.this.mServiceExtImpl.addMaskView(iBinder, str);
    }

    public int semRemoveMaskView(IBinder iBinder, String str) {
      super.semRemoveMaskView_enforcePermission();
      if (iBinder == null || str == null) {
        return -1;
      }
      return FingerprintService.this.mServiceExtImpl.removeMaskView(iBinder, str);
    }

    public void semRegisterAodController(
        IBinder iBinder, ISemFingerprintAodController iSemFingerprintAodController) {
      super.semRegisterAodController_enforcePermission();
      FingerprintService.this.mServiceExtImpl.registerAodController(iSemFingerprintAodController);
    }

    public void semUnregisterAodController(IBinder iBinder) {
      super.semUnregisterAodController_enforcePermission();
      FingerprintService.this.mServiceExtImpl.unregisterAodController();
    }

    public Rect semGetSensorAreaInDisplay(int i, int i2, Point point) {
      return FingerprintService.this.mServiceExtImpl.getUdfpsSensorArea(i, i2, point);
    }

    public int semGetIconBottomMargin() {
      return FingerprintService.this.mServiceExtImpl.getSensorAreaMarginFromBottomForFod();
    }

    public void semMoveSensorIconInDisplay(int i, int i2) {
      super.semMoveSensorIconInDisplay_enforcePermission();
      FingerprintService.this.mServiceExtImpl.moveSensorIconInDisplay();
    }

    public int semGetSecurityLevel() {
      super.semGetSecurityLevel_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.getSecurityLevel();
    }

    public String semGetTrustAppVersion() {
      super.semGetTrustAppVersion_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.getTrustAppVersion();
    }

    public void semUpdateTrustApp(
        String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) {
      super.semUpdateTrustApp_enforcePermission();
      FingerprintService.this.mServiceExtImpl.updateTrustApp(
          str, iSemFingerprintRequestCallback, str2);
    }

    public int semBioSysUiRequest(int i, int i2, long j, String str) {
      super.semBioSysUiRequest_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.handleBioSysUiRequest(i, i2, j, str);
    }

    public int semRegisterDisplayStateCallback(
        ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) {
      super.semRegisterDisplayStateCallback_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.registerDisplayStateCallback(
          iSemBiometricSysUiDisplayStateCallback);
    }

    public void semUnregisterDisplayStateCallback() {
      super.semUnregisterDisplayStateCallback_enforcePermission();
      FingerprintService.this.mServiceExtImpl.unregisterDisplayStateCallback();
    }

    public int semRegisterDisplayBrightnessCallback(
        ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) {
      super.semRegisterDisplayBrightnessCallback_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.registerDisplayBrightnessCallback(
          iSemBiometricSysUiDisplayBrightnessCallback);
    }

    public void semUnregisterDisplayBrightnessCallback() {
      super.semUnregisterDisplayBrightnessCallback_enforcePermission();
      FingerprintService.this.mServiceExtImpl.unregisterDisplayBrightnessCallback();
    }

    public void semGetSensorData(Bundle bundle) {
      super.semGetSensorData_enforcePermission();
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
        SemUdfpsHelper.getInstance().getInDisplaySensorArea(bundle);
      } else if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_SIDE) {
        FingerprintUtils.semGetSideSensorPosition(bundle);
      }
    }

    public void semSetFodStrictMode(boolean z) {
      super.semSetFodStrictMode_enforcePermission();
      FingerprintService.this.mServiceExtImpl.setFodStrictMode(z);
    }

    public int semSetCalibrationMode(IBinder iBinder, int i, String str) {
      super.semSetCalibrationMode_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.setCalibrationMode(iBinder, i, str);
    }

    public int semProcessFido(int i, byte[] bArr, byte[] bArr2, String str) {
      if (!Build.IS_ENG && !Build.IS_USERDEBUG) {
        Utils.checkPermission(
            FingerprintService.this.getContext(),
            "com.samsung.android.permission.REQUEST_PROCESS_FIDO");
      }
      Slog.d("FingerprintService", "process FIDO: " + i + ", " + str);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return FingerprintService.this.mServiceExtImpl.requestProcessFIDO(i, bArr, bArr2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int semGetRemainingLockoutTime(int i) {
      super.semGetRemainingLockoutTime_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.getRemainingLockoutTime(
          Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i));
    }

    public boolean semCanChangeDeviceColorMode() {
      super.semCanChangeDeviceColorMode_enforcePermission();
      return FingerprintService.this.mServiceExtImpl.canChangeDeviceColorMode();
    }

    public void semSetFlagForIFAA(int i, String str) {
      super.semSetFlagForIFAA_enforcePermission();
      FingerprintService.this.mServiceExtImpl.setFlagForIFAA(i, str);
    }

    public int semRequest(
        IBinder iBinder,
        int i,
        byte[] bArr,
        byte[] bArr2,
        int i2,
        int i3,
        String str,
        ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
      return FingerprintService.this.mServiceExtImpl.startRequest(
          iBinder,
          i,
          bArr,
          bArr2,
          i2,
          Utils.getUserOrWorkProfileId(FingerprintService.this.getContext(), i3),
          iSemFingerprintRequestCallback,
          str,
          Binder.getCallingPid());
    }
  }

  public FingerprintService(Context context) {
    this(
        context,
        BiometricContext.getInstance(context),
        new Supplier() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda0
          @Override // java.util.function.Supplier
          public final Object get() {
            IBiometricService lambda$new$0;
            lambda$new$0 = FingerprintService.lambda$new$0();
            return lambda$new$0;
          }
        },
        new Supplier() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda1
          @Override // java.util.function.Supplier
          public final Object get() {
            String[] lambda$new$1;
            lambda$new$1 = FingerprintService.lambda$new$1();
            return lambda$new$1;
          }
        },
        null);
  }

  public static /* synthetic */ IBiometricService lambda$new$0() {
    return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric"));
  }

  public static /* synthetic */ String[] lambda$new$1() {
    return ServiceManager.getDeclaredInstances(IFingerprint.DESCRIPTOR);
  }

  public FingerprintService(
      Context context,
      BiometricContext biometricContext,
      Supplier supplier,
      Supplier supplier2,
      Function function) {
    super(context);
    this.mAuthenticationSyncLock = new Object();
    C08931 c08931 = new C08931();
    this.mServiceWrapper = c08931;
    this.mBiometricContext = biometricContext;
    this.mAidlInstanceNameSupplier = supplier2;
    this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    this.mGestureAvailabilityDispatcher = new GestureAvailabilityDispatcher();
    this.mLockoutResetDispatcher = new LockoutResetDispatcher(context);
    this.mLockPatternUtils = new LockPatternUtils(context);
    this.mBiometricStateCallback = new BiometricStateCallback(UserManager.get(context));
    this.mFingerprintProvider =
        function == null
            ? new Function() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda4
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                FingerprintProvider lambda$new$2;
                lambda$new$2 = FingerprintService.this.lambda$new$2((String) obj);
                return lambda$new$2;
              }
            }
            : function;
    this.mHandler = new Handler(SemFpMainThread.get().getLooper());
    FingerprintServiceRegistry fingerprintServiceRegistry =
        new FingerprintServiceRegistry(c08931, supplier);
    this.mRegistry = fingerprintServiceRegistry;
    fingerprintServiceRegistry.addAllRegisteredCallback(
        new IFingerprintAuthenticatorsRegisteredCallback
            .Stub() { // from class:
                      // com.android.server.biometrics.sensors.fingerprint.FingerprintService.2
          public void onAllAuthenticatorsRegistered(List list) {
            FingerprintService.this.mBiometricStateCallback.start(
                FingerprintService.this.mRegistry.getProviders());
            FingerprintService.this.mServiceExtImpl.onAllAuthenticatorsRegistered();
          }
        });
    this.mServiceExtImpl = new SemFingerprintServiceExtImpl(context, fingerprintServiceRegistry);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ FingerprintProvider lambda$new$2(String str) {
    String str2 = IFingerprint.DESCRIPTOR + "/" + str;
    IFingerprint asInterface =
        IFingerprint.Stub.asInterface(
            Binder.allowBlocking(ServiceManager.waitForDeclaredService(str2)));
    if (asInterface != null) {
      try {
        return new FingerprintProvider(
            getContext(),
            this.mBiometricStateCallback,
            asInterface.getSensorProps(),
            str,
            this.mLockoutResetDispatcher,
            this.mGestureAvailabilityDispatcher,
            this.mBiometricContext);
      } catch (RemoteException unused) {
        Slog.e("FingerprintService", "Remote exception in getSensorProps: " + str2);
        return null;
      }
    }
    Slog.e("FingerprintService", "Unable to get declared service: " + str2);
    return null;
  }

  public final List getHidlProviders(List list) {
    Fingerprint21 newInstance;
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal =
          (FingerprintSensorPropertiesInternal) it.next();
      if ((Build.IS_USERDEBUG || Build.IS_ENG)
          && getContext().getResources().getBoolean(R.bool.allow_test_udfps)
          && Settings.Secure.getIntForUser(
                  getContext().getContentResolver(),
                  "com.android.server.biometrics.sensors.fingerprint.test_udfps.enable",
                  0,
                  -2)
              != 0) {
        newInstance =
            Fingerprint21UdfpsMock.newInstance(
                getContext(),
                this.mBiometricStateCallback,
                fingerprintSensorPropertiesInternal,
                this.mLockoutResetDispatcher,
                this.mGestureAvailabilityDispatcher,
                BiometricContext.getInstance(getContext()));
      } else {
        newInstance =
            Fingerprint21.newInstance(
                getContext(),
                this.mBiometricStateCallback,
                fingerprintSensorPropertiesInternal,
                this.mHandler,
                this.mLockoutResetDispatcher,
                this.mGestureAvailabilityDispatcher);
      }
      arrayList.add(newInstance);
    }
    return arrayList;
  }

  public final List getAidlProviders(List list) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      FingerprintProvider fingerprintProvider =
          (FingerprintProvider) this.mFingerprintProvider.apply(str);
      Slog.i(
          "FingerprintService",
          "Adding AIDL provider: " + str + ", provider = " + fingerprintProvider);
      if (fingerprintProvider != null) {
        arrayList.add(fingerprintProvider);
      }
    }
    Slog.i("FingerprintService", "AidlProviders = " + arrayList.size());
    return arrayList;
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("fingerprint", this.mServiceWrapper);
  }

  public final List getEnrolledFingerprintsDeprecated(int i, String str) {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider == null) {
      Slog.w(
          "FingerprintService",
          "Null provider for getEnrolledFingerprintsDeprecated, caller: " + str);
      return Collections.emptyList();
    }
    return ((ServiceProvider) singleProvider.second)
        .getEnrolledFingerprints(((Integer) singleProvider.first).intValue(), i);
  }

  public final boolean canUseFingerprint(
      String str, String str2, boolean z, int i, int i2, int i3) {
    if (getContext().checkCallingPermission("android.permission.USE_FINGERPRINT") != 0) {
      Utils.checkPermission(getContext(), "android.permission.USE_BIOMETRIC");
    }
    if (Binder.getCallingUid() == 1000 || Utils.isKeyguard(getContext(), str)) {
      return true;
    }
    if (!Utils.isCurrentUserOrProfile(getContext(), i3)) {
      Slog.w("FingerprintService", "Rejecting " + str + "; not a current user or profile");
      return false;
    }
    if (!checkAppOps(i, str, str2)) {
      Slog.w("FingerprintService", "Rejecting " + str + "; permission denied");
      return false;
    }
    if (!z || Utils.isForeground(i, i2)) {
      return true;
    }
    Slog.w("FingerprintService", "Rejecting " + str + "; not in foreground");
    return false;
  }

  public final boolean checkAppOps(int i, String str, String str2) {
    return this.mAppOps.noteOpNoThrow(78, i, str, str2, (String) null) == 0
        || this.mAppOps.noteOpNoThrow(55, i, str, str2, (String) null) == 0;
  }

  public void syncEnrollmentsNow() {
    Utils.checkPermissionOrShell(getContext(), "android.permission.MANAGE_FINGERPRINT");
    if (Utils.isVirtualEnabled(getContext())) {
      Slog.i("FingerprintService", "Sync virtual enrollments");
      int currentUser = ActivityManager.getCurrentUser();
      final CountDownLatch countDownLatch =
          new CountDownLatch(this.mRegistry.getProviders().size());
      for (ServiceProvider serviceProvider : this.mRegistry.getProviders()) {
        Iterator it = serviceProvider.getSensorProperties().iterator();
        while (it.hasNext()) {
          serviceProvider.scheduleInternalCleanup(
              ((FingerprintSensorPropertiesInternal) it.next()).sensorId,
              currentUser,
              new ClientMonitorCallback() { // from class:
                                            // com.android.server.biometrics.sensors.fingerprint.FingerprintService.3
                @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                  countDownLatch.countDown();
                  if (z) {
                    return;
                  }
                  Slog.e("FingerprintService", "Sync virtual enrollments failed");
                }
              },
              true);
        }
      }
      try {
        countDownLatch.await(3L, TimeUnit.SECONDS);
      } catch (Exception e) {
        Slog.e("FingerprintService", "Failed to wait for sync finishing", e);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBootPhase$3(int i) {
    this.mServiceExtImpl.onBootPhase(i);
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(final int i) {
    SemFpMainThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                FingerprintService.this.lambda$onBootPhase$3(i);
              }
            });
  }

  @Override // com.android.server.SystemService
  public void onUserUnlocked(final SystemService.TargetUser targetUser) {
    SemFpMainThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                FingerprintService.lambda$onUserUnlocked$4(SystemService.TargetUser.this);
              }
            });
  }

  public static /* synthetic */ void lambda$onUserUnlocked$4(SystemService.TargetUser targetUser) {
    Slog.d("FingerprintService", "onUserUnlocked: " + targetUser.toString());
  }

  @Override // com.android.server.SystemService
  public void onUserStarting(final SystemService.TargetUser targetUser) {
    SemFpMainThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                FingerprintService.lambda$onUserStarting$5(SystemService.TargetUser.this);
              }
            });
  }

  public static /* synthetic */ void lambda$onUserStarting$5(SystemService.TargetUser targetUser) {
    Slog.d("FingerprintService", "onUserStarting: " + targetUser.toString());
  }

  @Override // com.android.server.SystemService
  public void onUserStopping(final SystemService.TargetUser targetUser) {
    SemFpMainThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda6
              @Override // java.lang.Runnable
              public final void run() {
                FingerprintService.lambda$onUserStopping$6(SystemService.TargetUser.this);
              }
            });
  }

  public static /* synthetic */ void lambda$onUserStopping$6(SystemService.TargetUser targetUser) {
    Slog.d("FingerprintService", "onUserStopping: " + targetUser.toString());
  }
}
