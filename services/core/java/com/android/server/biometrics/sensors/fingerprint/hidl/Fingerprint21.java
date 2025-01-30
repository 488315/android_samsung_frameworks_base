package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.SynchronousUserSwitchObserver;
import android.app.TaskStackListener;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.hardware.biometrics.fingerprint.V2_2.IBiometricsFingerprintClientCallback;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.fingerprint.ISidefpsController;
import android.hardware.fingerprint.IUdfpsOverlay;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserManager;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.SemUpdateTrustAppClient;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;
import com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter;
import com.android.server.biometrics.sensors.fingerprint.SemFpChallengeListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpEventListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalLifecycleListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpPauseResumeHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpProviderEx;
import com.android.server.biometrics.sensors.fingerprint.SemFpRequestCommands;
import com.android.server.biometrics.sensors.fingerprint.SemFpResetLockoutListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpScheduler;
import com.android.server.biometrics.sensors.fingerprint.SemFpSensorTestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener;
import com.android.server.biometrics.sensors.fingerprint.ServiceProvider;
import com.android.server.biometrics.sensors.fingerprint.Udfps;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* loaded from: classes.dex */
public class Fingerprint21 implements IHwBinder.DeathRecipient, ServiceProvider {
  public final ActivityTaskManager mActivityTaskManager;
  public final Map mAuthenticatorIds;
  public final BiometricContext mBiometricContext;
  public final BiometricStateCallback mBiometricStateCallback;
  public final SemFpCallbackCenter mCallbackCenter;
  public final Context mContext;
  public IBiometricsFingerprint mDaemon;
  public final HalResultController mHalResultController;
  public final Handler mHandler;
  public boolean mIsHalStarted;
  public final boolean mIsPowerbuttonFps;
  public final boolean mIsUdfps;
  public final Supplier mLazyDaemon;
  public final LockoutFrameworkImpl.LockoutResetCallback mLockoutResetCallback;
  public final LockoutResetDispatcher mLockoutResetDispatcher;
  public final LockoutFrameworkImpl mLockoutTracker;
  public final SemFpProviderEx mProviderEx;
  public final BiometricScheduler mScheduler;
  public SehTestHal mSehTestHal;
  public final int mSensorId;
  public final FingerprintSensorPropertiesInternal mSensorProperties;
  public ISidefpsController mSidefpsController;
  public final BiometricTaskStackListener mTaskStackListener;
  public boolean mTestHalEnabled;
  public boolean mTpaHalModeEnabled;
  public IUdfpsOverlay mUdfpsOverlay;
  public IUdfpsOverlayController mUdfpsOverlayController;
  public final UserSwitchObserver mUserSwitchObserver;
  public final AtomicLong mRequestCounter = new AtomicLong(0);
  public int mCurrentUserId = -10000;
  public final ArrayList mLifecycleListeners = new ArrayList();

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semSetTpaRequestCommandAction(String[] strArr) {}

  public final class BiometricTaskStackListener extends TaskStackListener
      implements ActivityManager.SemProcessListener {
    public void onProcessDied(int i, int i2) {}

    public BiometricTaskStackListener() {}

    public void onTaskStackChanged() {
      Fingerprint21.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$BiometricTaskStackListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.BiometricTaskStackListener.this.lambda$onTaskStackChanged$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskStackChanged$0() {
      BaseClientMonitor currentClient = Fingerprint21.this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AuthenticationClient)) {
        Slog.e("Fingerprint21", "Task stack changed for client: " + currentClient);
        return;
      }
      if (Utils.isKeyguard(Fingerprint21.this.mContext, currentClient.getOwnerString())
          || Utils.isSystem(Fingerprint21.this.mContext, currentClient.getOwnerString())
          || ((AuthenticationClient) currentClient).semIsAllowedBackgroundAuthentication()
          || !Utils.isBackground(currentClient.getOwnerString())
          || currentClient.isAlreadyDone()) {
        return;
      }
      Slog.e(
          "Fingerprint21", "Stopping background authentication, currentClient: " + currentClient);
      Fingerprint21.this.mScheduler.lambda$cancelAuthenticationOrDetection$3(
          currentClient.getToken(), currentClient.getRequestId());
    }

    public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
      Slog.d(
          "Fingerprint21",
          "onForegroundActivitiesChanged: pid = "
              + i
              + ", uid = "
              + i2
              + ", foregroundActivities = "
              + z);
      onTaskStackChanged();
    }
  }

  public class HalResultController extends IBiometricsFingerprintClientCallback.Stub {
    public Callback mCallback;
    public final Context mContext;
    public SemFpHalCallbackEx mHalListener;
    public final Handler mHandler;
    public final BiometricScheduler mScheduler;
    public final int mSensorId;

    public interface Callback {
      void onHardwareUnavailable();
    }

    public HalResultController(
        int i, Context context, Handler handler, BiometricScheduler biometricScheduler) {
      this.mSensorId = i;
      this.mContext = context;
      this.mHandler = handler;
      this.mScheduler = biometricScheduler;
    }

    public void setCallback(Callback callback) {
      this.mCallback = callback;
    }

    public void setHalListener(SemFpHalCallbackEx semFpHalCallbackEx) {
      this.mHalListener = semFpHalCallbackEx;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onEnrollResult(final long j, final int i, final int i2, final int i3) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.HalResultController.this.lambda$onEnrollResult$0(i2, i, j, i3);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollResult$0(int i, int i2, long j, int i3) {
      SemFpHalCallbackEx semFpHalCallbackEx;
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof FingerprintEnrollClient)) {
        Slog.e(
            "Fingerprint21",
            "onEnrollResult for non-enroll client: " + Utils.getClientName(currentClient));
        return;
      }
      int targetUserId = currentClient.getTargetUserId();
      FingerprintEnrollClient fingerprintEnrollClient = (FingerprintEnrollClient) currentClient;
      fingerprintEnrollClient.onEnrollResult(
          new Fingerprint(
              FingerprintUtils.getLegacyInstance(this.mSensorId)
                  .getUniqueName(this.mContext, targetUserId),
              i,
              i2,
              j),
          i3);
      if (i3 != 0 || (semFpHalCallbackEx = this.mHalListener) == null) {
        return;
      }
      semFpHalCallbackEx.onEnrolled(this.mSensorId, fingerprintEnrollClient.getTargetUserId());
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onAcquired(long j, int i, int i2) {
      onAcquired_2_2(j, i, i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_2.IBiometricsFingerprintClientCallback
    public void onAcquired_2_2(long j, final int i, final int i2) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.HalResultController.this.lambda$onAcquired_2_2$1(i, i2);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired_2_2$1(int i, int i2) {
      Slog.i("FingerprintService", "handleAcquired: acquiredInfo=" + i + ", vendor=" + i2);
      if (FingerprintUtils.semIsInternalClientFreeEvent(i, i2)) {
        handleInternalClientFreeEvent(i2);
        return;
      }
      if (FingerprintUtils.semIsRequestCommandEvent(i, i2)) {
        handleRequestEvent(i2);
        return;
      }
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
        if (FingerprintUtils.semIsSkipEvent(i, i2)) {
          return;
        } else {
          i2 = FingerprintUtils.semUpdateVendorInfo(i, i2);
        }
      }
      int i3 = i2;
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AcquisitionClient)) {
        Slog.e(
            "Fingerprint21",
            "onAcquired for non-acquisition client: " + Utils.getClientName(currentClient));
        return;
      }
      AcquisitionClient acquisitionClient = (AcquisitionClient) currentClient;
      acquisitionClient.onAcquired(i, i3);
      SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
      if (semFpHalCallbackEx != null) {
        semFpHalCallbackEx.onAcquire(
            this.mSensorId,
            acquisitionClient.getTargetUserId(),
            acquisitionClient.getProtoEnum(),
            i,
            i3);
      }
    }

    public final void handleInternalClientFreeEvent(int i) {
      SemFpHalCallbackEx semFpHalCallbackEx;
      SemFpHalCallbackEx semFpHalCallbackEx2;
      if (i != 20001 && i != 20002) {
        if ((i == 30001 || i == 30002)
            && SemBiometricFeature.FP_FEATURE_SENSOR_LIMITATION_SPEN_CHARGER
            && (semFpHalCallbackEx2 = this.mHalListener) != null) {
          semFpHalCallbackEx2.onSpenEvent(this.mSensorId, i);
          return;
        }
        return;
      }
      if (!SemBiometricFeature.FP_FEATURE_GESTURE_MODE
          || (semFpHalCallbackEx = this.mHalListener) == null) {
        return;
      }
      semFpHalCallbackEx.onGestureEvent(this.mSensorId, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void handleRequestEvent(int i) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof SemFpRequestCommands)) {
        Slog.e(
            "Fingerprint21",
            "SensorTestEvent for non-request client: " + Utils.getClientName(currentClient));
        return;
      }
      ((SemFpRequestCommands) currentClient).onRequestResult(i);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onAuthenticated(
        final long j, final int i, final int i2, final ArrayList arrayList) {
      long j2;
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL && i != 0) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof FingerprintAuthenticationClient) {
          FingerprintAuthenticationClient fingerprintAuthenticationClient =
              (FingerprintAuthenticationClient) currentClient;
          if (fingerprintAuthenticationClient.useEarlyAuthenticationResult()) {
            fingerprintAuthenticationClient.handleEarlyAuthenticationResult();
            j2 = 10;
            this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda2
                  @Override // java.lang.Runnable
                  public final void run() {
                    Fingerprint21.HalResultController.this.lambda$onAuthenticated$2(
                        i, i2, j, arrayList);
                  }
                },
                j2);
          }
        }
      }
      j2 = 0;
      this.mHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.HalResultController.this.lambda$onAuthenticated$2(i, i2, j, arrayList);
            }
          },
          j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onAuthenticated$2(
        int i, int i2, long j, ArrayList arrayList) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof AuthenticationConsumer)) {
        Slog.e(
            "Fingerprint21",
            "onAuthenticated for non-authentication consumer: "
                + Utils.getClientName(currentClient));
        return;
      }
      AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) currentClient;
      boolean z = i != 0;
      Slog.i("FingerprintService", "handleAuthenticated: " + z);
      authenticationConsumer.onAuthenticated(
          new Fingerprint(
              FingerprintUtils.getLegacyInstance(this.mSensorId)
                  .getFingerprintName(this.mContext, i, i2),
              i2,
              i,
              j),
          z,
          arrayList);
      SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
      if (semFpHalCallbackEx != null) {
        semFpHalCallbackEx.onAuthenticated(this.mSensorId, currentClient.getTargetUserId(), i);
      }
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onError(long j, final int i, final int i2) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.HalResultController.this.lambda$onError$3(i, i2);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onError$3(int i, int i2) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      Slog.d(
          "Fingerprint21",
          "handleError, client: "
              + Utils.getClientName(currentClient)
              + ", error: "
              + i
              + ", vendorCode: "
              + i2);
      if (!(currentClient instanceof ErrorConsumer)) {
        Slog.e(
            "Fingerprint21",
            "onError for non-error consumer: " + Utils.getClientName(currentClient));
        return;
      }
      ((ErrorConsumer) currentClient).onError(i, i2);
      if (i == 1) {
        Slog.e("Fingerprint21", "Got ERROR_HW_UNAVAILABLE");
        Callback callback = this.mCallback;
        if (callback != null) {
          callback.onHardwareUnavailable();
        }
      }
      SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
      if (semFpHalCallbackEx != null) {
        semFpHalCallbackEx.onError(
            this.mSensorId, currentClient.getTargetUserId(), currentClient.getProtoEnum(), i, i2);
      }
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onRemoved(final long j, final int i, final int i2, final int i3) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.HalResultController.this.lambda$onRemoved$4(i, i3, i2, j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onRemoved$4(int i, int i2, int i3, long j) {
      Slog.d("Fingerprint21", "Removed, fingerId: " + i + ", remaining: " + i2);
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof RemovalConsumer)) {
        Slog.e(
            "Fingerprint21",
            "onRemoved for non-removal consumer: " + Utils.getClientName(currentClient));
        return;
      }
      ((RemovalConsumer) currentClient).onRemoved(new Fingerprint("", i3, i, j), i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onEnumerate(final long j, final int i, final int i2, final int i3) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              Fingerprint21.HalResultController.this.lambda$onEnumerate$5(i, i3, i2, j);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onEnumerate$5(int i, int i2, int i3, long j) {
      BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
      if (!(currentClient instanceof EnumerateConsumer)) {
        Slog.e(
            "Fingerprint21",
            "onEnumerate for non-enumerate consumer: " + Utils.getClientName(currentClient));
        return;
      }
      Slog.d("Fingerprint21", "onEnumerate, fingerId: " + i + ", remaining: " + i2);
      ((EnumerateConsumer) currentClient).onEnumerationResult(new Fingerprint("", i3, i, j), i2);
    }
  }

  public Fingerprint21(
      Context context,
      BiometricStateCallback biometricStateCallback,
      FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal,
      BiometricScheduler biometricScheduler,
      Handler handler,
      LockoutResetDispatcher lockoutResetDispatcher,
      HalResultController halResultController,
      BiometricContext biometricContext) {
    LockoutFrameworkImpl.LockoutResetCallback lockoutResetCallback =
        new LockoutFrameworkImpl
            .LockoutResetCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.1
          @Override // com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback
          public void onLockoutReset(int i) {
            Fingerprint21.this
                .mBiometricContext
                .getAuthSessionCoordinator()
                .resetLockoutFor(
                    i,
                    Utils.getCurrentStrength(Fingerprint21.this.mSensorProperties.sensorId),
                    -1L);
            Fingerprint21.this.mLockoutResetDispatcher.notifyLockoutResetCallbacks(
                Fingerprint21.this.mSensorProperties.sensorId);
          }
        };
    this.mLockoutResetCallback = lockoutResetCallback;
    SynchronousUserSwitchObserver synchronousUserSwitchObserver =
        new SynchronousUserSwitchObserver() { // from class:
                                              // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.2
          public void onUserSwitching(int i) {
            Fingerprint21.this.scheduleInternalCleanup(i, null);
          }
        };
    this.mUserSwitchObserver = synchronousUserSwitchObserver;
    this.mContext = context;
    this.mBiometricStateCallback = biometricStateCallback;
    this.mBiometricContext = biometricContext;
    this.mSensorProperties = fingerprintSensorPropertiesInternal;
    this.mSensorId = fingerprintSensorPropertiesInternal.sensorId;
    int i = fingerprintSensorPropertiesInternal.sensorType;
    this.mIsUdfps = i == 3 || i == 2;
    this.mIsPowerbuttonFps = i == 4;
    this.mScheduler = biometricScheduler;
    this.mHandler = handler;
    this.mActivityTaskManager = ActivityTaskManager.getInstance();
    this.mTaskStackListener = new BiometricTaskStackListener();
    this.mAuthenticatorIds = Collections.synchronizedMap(new HashMap());
    this.mLazyDaemon =
        new Supplier() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda5
          @Override // java.util.function.Supplier
          public final Object get() {
            return Fingerprint21.this.getDaemon();
          }
        };
    this.mLockoutResetDispatcher = lockoutResetDispatcher;
    this.mLockoutTracker = new LockoutFrameworkImpl(context, lockoutResetCallback);
    this.mHalResultController = halResultController;
    halResultController.setCallback(
        new HalResultController
            .Callback() { // from class:
                          // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda6
          @Override // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.Callback
          public final void onHardwareUnavailable() {
            Fingerprint21.this.lambda$new$0();
          }
        });
    this.mProviderEx =
        new SemFpProviderEx(
            new BiFunction() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda7
              @Override // java.util.function.BiFunction
              public final Object apply(Object obj, Object obj2) {
                String lambda$new$1;
                lambda$new$1 = Fingerprint21.this.lambda$new$1((Integer) obj, (Integer) obj2);
                return lambda$new$1;
              }
            },
            new BiFunction() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda8
              @Override // java.util.function.BiFunction
              public final Object apply(Object obj, Object obj2) {
                return Integer.valueOf(
                    Fingerprint21.this.semRequest(
                        ((Integer) obj).intValue(), ((Integer) obj2).intValue()));
              }
            });
    SemFpCallbackCenter semFpCallbackCenter = new SemFpCallbackCenter(this, handler);
    this.mCallbackCenter = semFpCallbackCenter;
    halResultController.setHalListener(semFpCallbackCenter);
    handler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda9
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.getDaemon();
          }
        });
    try {
      ActivityManager.getService()
          .registerUserSwitchObserver(synchronousUserSwitchObserver, "Fingerprint21");
    } catch (RemoteException unused) {
      Slog.e("Fingerprint21", "Unable to register user switch observer");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    this.mDaemon = null;
    this.mCurrentUserId = -10000;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ String lambda$new$1(Integer num, Integer num2) {
    byte[] bArr = new byte[IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES];
    int semRequest = semRequest(num.intValue(), num2.intValue(), 0, null, bArr);
    return TextUtils.emptyIfNull(
        semRequest > 0
            ? new String(Arrays.copyOf(bArr, semRequest), StandardCharsets.UTF_8)
            : null);
  }

  public static Fingerprint21 newInstance(
      Context context,
      BiometricStateCallback biometricStateCallback,
      FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal,
      Handler handler,
      LockoutResetDispatcher lockoutResetDispatcher,
      GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
    SemFpScheduler semFpScheduler =
        new SemFpScheduler("Fingerprint21", gestureAvailabilityDispatcher);
    return new Fingerprint21(
        context,
        biometricStateCallback,
        fingerprintSensorPropertiesInternal,
        semFpScheduler,
        handler,
        lockoutResetDispatcher,
        new HalResultController(
            fingerprintSensorPropertiesInternal.sensorId, context, handler, semFpScheduler),
        BiometricContext.getInstance(context));
  }

  public void serviceDied(long j) {
    Slog.e("Fingerprint21", "HAL died");
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$serviceDied$2();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$serviceDied$2() {
    PerformanceTracker.getInstanceForSensorId(this.mSensorProperties.sensorId)
        .incrementHALDeathCount();
    this.mDaemon = null;
    this.mCurrentUserId = -10000;
    Object currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof ErrorConsumer) {
      Slog.e("Fingerprint21", "Sending ERROR_HW_UNAVAILABLE for client: " + currentClient);
      ((ErrorConsumer) currentClient).onError(1, 0);
      FrameworkStatsLog.write(148, 1, 1, -1);
    }
    this.mScheduler.recordCrashState();
    this.mScheduler.reset();
    this.mIsHalStarted = false;
    dispatchHalStopped();
  }

  public synchronized IBiometricsFingerprint getDaemon() {
    long j;
    if (this.mTestHalEnabled) {
      if (!SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
        SehTestHal sehTestHal = new SehTestHal(this.mContext, this.mSensorId, false);
        sehTestHal.setNotify(this.mHalResultController);
        return sehTestHal;
      }
      TestHal testHal = new TestHal(this.mContext, this.mSensorId);
      testHal.setNotify(this.mHalResultController);
      return testHal;
    }
    if (this.mTpaHalModeEnabled) {
      return this.mSehTestHal;
    }
    IBiometricsFingerprint iBiometricsFingerprint = this.mDaemon;
    if (iBiometricsFingerprint != null) {
      return iBiometricsFingerprint;
    }
    Slog.d(
        "Fingerprint21",
        "Daemon was null, reconnecting, current operation: " + this.mScheduler.getCurrentClient());
    try {
      if (SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
        this.mDaemon = IBiometricsFingerprint.getService();
      } else {
        this.mDaemon = ISehBiometricsFingerprint.getService();
      }
    } catch (RemoteException e) {
      Slog.e("Fingerprint21", "Failed to get fingerprint HAL", e);
    } catch (NoSuchElementException e2) {
      Slog.w("Fingerprint21", "NoSuchElementException", e2);
    }
    IBiometricsFingerprint iBiometricsFingerprint2 = this.mDaemon;
    if (iBiometricsFingerprint2 == null) {
      Slog.w("Fingerprint21", "Fingerprint HAL not available");
      return null;
    }
    iBiometricsFingerprint2.asBinder().linkToDeath(this, 0L);
    long elapsedRealtime = SystemClock.elapsedRealtime();
    try {
      j = this.mDaemon.setNotify(this.mHalResultController);
    } catch (RemoteException e3) {
      Slog.e("Fingerprint21", "Failed to set callback for fingerprint HAL", e3);
      this.mDaemon = null;
      j = 0;
    }
    Slog.d("Fingerprint21", "Fingerprint HAL ready, HAL ID: " + j);
    if (j != 0) {
      scheduleLoadAuthenticatorIds();
      scheduleInternalCleanup(ActivityManager.getCurrentUser(), null);
      this.mProviderEx.updateCacheDataOfHAL(this.mSensorId);
      handleHalStarted();
    } else {
      Slog.e("Fingerprint21", "Unable to set callback");
      this.mDaemon = null;
    }
    if (Utils.DEBUG) {
      Slog.i(
          "Fingerprint21",
          "getDaemon FP_FINISH (" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms)");
    }
    return this.mDaemon;
  }

  public final void handleHalStarted() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$handleHalStarted$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleHalStarted$3() {
    this.mIsHalStarted = true;
    dispatchHalStarted();
  }

  public IUdfpsOverlayController getUdfpsOverlayController() {
    return this.mUdfpsOverlayController;
  }

  public final void scheduleLoadAuthenticatorIds() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleLoadAuthenticatorIds$4();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleLoadAuthenticatorIds$4() {
    Iterator it = UserManager.get(this.mContext).getAliveUsers().iterator();
    while (it.hasNext()) {
      int i = ((UserInfo) it.next()).id;
      if (!this.mAuthenticatorIds.containsKey(Integer.valueOf(i))) {
        scheduleUpdateActiveUserWithoutHandler(i, true);
      }
    }
  }

  public final void scheduleUpdateActiveUserWithoutHandler(int i) {
    scheduleUpdateActiveUserWithoutHandler(i, false);
  }

  public final void scheduleUpdateActiveUserWithoutHandler(final int i, boolean z) {
    boolean z2 = !getEnrolledFingerprints(this.mSensorProperties.sensorId, i).isEmpty();
    Context context = this.mContext;
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintUpdateActiveUserClient(
            context,
            this.mLazyDaemon,
            i,
            context.getOpPackageName(),
            this.mSensorProperties.sensorId,
            createLogger(0, 0),
            this.mBiometricContext,
            new Supplier() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda11
              @Override // java.util.function.Supplier
              public final Object get() {
                int currentUser;
                currentUser = Fingerprint21.this.getCurrentUser();
                return Integer.valueOf(currentUser);
              }
            },
            z2,
            this.mAuthenticatorIds,
            z),
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.3
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z3) {
            if (z3) {
              Fingerprint21.this.mCurrentUserId = i;
            } else {
              Slog.w(
                  "Fingerprint21",
                  "Failed to change user, still: " + Fingerprint21.this.mCurrentUserId);
            }
          }
        });
  }

  public final int getCurrentUser() {
    return this.mCurrentUserId;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean containsSensor(int i) {
    return this.mSensorProperties.sensorId == i;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public List getSensorProperties() {
    ArrayList arrayList = new ArrayList();
    arrayList.add(this.mSensorProperties);
    return arrayList;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public FingerprintSensorPropertiesInternal getSensorProperties(int i) {
    return this.mSensorProperties;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleResetLockout(final int i, final int i2, byte[] bArr) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda14
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleResetLockout$5(i2, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleResetLockout$5(int i, int i2) {
    Context context = this.mContext;
    FingerprintResetLockoutClient fingerprintResetLockoutClient =
        new FingerprintResetLockoutClient(
            context,
            i,
            context.getOpPackageName(),
            i2,
            createLogger(0, 0),
            this.mBiometricContext,
            this.mLockoutTracker);
    BaseClientMonitor semGetCurrentClient = semGetCurrentClient();
    if ((semGetCurrentClient instanceof FingerprintAuthenticationClient)
        && ((FingerprintAuthenticationClient) semGetCurrentClient).getState() != 4) {
      fingerprintResetLockoutClient.start(
          new ClientMonitorCallback() { // from class:
                                        // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.4
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
              Fingerprint21.this.mCallbackCenter.onResetLockout(
                  baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
          });
    } else {
      this.mScheduler.lambda$scheduleClientMonitor$1(
          fingerprintResetLockoutClient,
          new ClientMonitorCallback() { // from class:
                                        // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.5
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
              Fingerprint21.this.mCallbackCenter.onResetLockout(
                  baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
          });
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleGenerateChallenge(
      int i,
      final int i2,
      final IBinder iBinder,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda32
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleGenerateChallenge$6(
                iBinder, iFingerprintServiceReceiver, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleGenerateChallenge$6(
      IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, int i, String str) {
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintGenerateChallengeClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            i,
            str,
            this.mSensorProperties.sensorId,
            createLogger(0, 0),
            this.mBiometricContext),
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.6
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            if (z) {
              Fingerprint21.this.mCallbackCenter.onChallengeGenerated(
                  baseClientMonitor.getSensorId(),
                  baseClientMonitor.getTargetUserId(),
                  ((FingerprintGenerateChallengeClient) baseClientMonitor).getChallenge());
            }
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleRevokeChallenge(
      int i, final int i2, final IBinder iBinder, final String str, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda15
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleRevokeChallenge$7(iBinder, i2, str, j);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRevokeChallenge$7(
      IBinder iBinder, int i, String str, final long j) {
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintRevokeChallengeClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            i,
            str,
            this.mSensorProperties.sensorId,
            createLogger(0, 0),
            this.mBiometricContext),
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.7
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            if (z) {
              Fingerprint21.this.mCallbackCenter.onChallengeRevoked(
                  baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId(), j);
            }
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public long scheduleEnroll(
      int i,
      final IBinder iBinder,
      final byte[] bArr,
      final int i2,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final String str,
      final int i3) {
    final long incrementAndGet = this.mRequestCounter.incrementAndGet();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleEnroll$8(
                i2, iBinder, incrementAndGet, iFingerprintServiceReceiver, bArr, str, i3);
          }
        });
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleEnroll$8(
      int i,
      IBinder iBinder,
      long j,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      byte[] bArr,
      String str,
      int i2) {
    scheduleUpdateActiveUserWithoutHandler(i);
    FingerprintEnrollClient fingerprintEnrollClient =
        new FingerprintEnrollClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            j,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            i,
            bArr,
            str,
            FingerprintUtils.getLegacyInstance(this.mSensorId),
            0,
            this.mSensorProperties.sensorId,
            createLogger(1, 0),
            this.mBiometricContext,
            this.mUdfpsOverlayController,
            this.mSidefpsController,
            this.mUdfpsOverlay,
            i2);
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      fingerprintEnrollClient.initForUdfps();
    }
    this.mScheduler.lambda$scheduleClientMonitor$1(fingerprintEnrollClient, new C09478());
  }

  /* renamed from: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$8 */
  public class C09478 implements ClientMonitorCallback {
    public C09478() {}

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientStarted(BaseClientMonitor baseClientMonitor) {
      Fingerprint21.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
      Fingerprint21.this.mCallbackCenter.onClientStarted(baseClientMonitor);
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onBiometricAction(int i) {
      Fingerprint21.this.mBiometricStateCallback.onBiometricAction(i);
      Fingerprint21.this.mCallbackCenter.onBiometricAction(i);
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientFinished(final BaseClientMonitor baseClientMonitor, boolean z) {
      Fingerprint21.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
      if (z) {
        Fingerprint21.this.mHandler.postAtFrontOfQueue(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$8$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                Fingerprint21.C09478.this.lambda$onClientFinished$0(baseClientMonitor);
              }
            });
      }
      Fingerprint21.this.mCallbackCenter.onClientFinished(baseClientMonitor, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClientFinished$0(BaseClientMonitor baseClientMonitor) {
      Fingerprint21.this.scheduleUpdateActiveUserWithoutHandler(
          baseClientMonitor.getTargetUserId(), true);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelEnrollment$9(IBinder iBinder, long j) {
    this.mScheduler.lambda$cancelEnrollment$2(iBinder, j);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void cancelEnrollment(int i, final IBinder iBinder, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda25
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$cancelEnrollment$9(iBinder, j);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public long scheduleFingerDetect(
      final IBinder iBinder,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      final int i) {
    final long incrementAndGet = this.mRequestCounter.incrementAndGet();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda31
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleFingerDetect$10(
                fingerprintAuthenticateOptions,
                iBinder,
                incrementAndGet,
                clientMonitorCallbackConverter,
                i);
          }
        });
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleFingerDetect$10(
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      IBinder iBinder,
      long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      int i) {
    scheduleUpdateActiveUserWithoutHandler(fingerprintAuthenticateOptions.getUserId());
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintDetectClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            j,
            clientMonitorCallbackConverter,
            fingerprintAuthenticateOptions,
            createLogger(2, i),
            this.mBiometricContext,
            this.mUdfpsOverlayController,
            this.mUdfpsOverlay,
            Utils.isStrongBiometric(this.mSensorProperties.sensorId)),
        this.mBiometricStateCallback);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleAuthenticate(
      final IBinder iBinder,
      final long j,
      final int i,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      final long j2,
      final boolean z,
      final int i2,
      final boolean z2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda22
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleAuthenticate$11(
                fingerprintAuthenticateOptions,
                iBinder,
                j2,
                clientMonitorCallbackConverter,
                j,
                z,
                i,
                i2,
                z2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleAuthenticate$11(
      final FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      IBinder iBinder,
      final long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      long j2,
      boolean z,
      int i,
      int i2,
      boolean z2) {
    scheduleUpdateActiveUserWithoutHandler(fingerprintAuthenticateOptions.getUserId());
    FingerprintAuthenticationClient fingerprintAuthenticationClient =
        new FingerprintAuthenticationClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            j,
            clientMonitorCallbackConverter,
            j2,
            z,
            fingerprintAuthenticateOptions,
            i,
            false,
            createLogger(2, i2),
            this.mBiometricContext,
            Utils.isStrongBiometric(this.mSensorProperties.sensorId),
            this.mTaskStackListener,
            this.mLockoutTracker,
            this.mUdfpsOverlayController,
            this.mSidefpsController,
            this.mUdfpsOverlay,
            z2,
            this.mSensorProperties,
            Utils.getCurrentStrength(this.mSensorId));
    fingerprintAuthenticationClient.setExtraAttribute(new Bundle());
    this.mScheduler.lambda$scheduleClientMonitor$1(
        fingerprintAuthenticationClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.9
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {
            Fingerprint21.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            Fingerprint21.this
                .mBiometricContext
                .getAuthSessionCoordinator()
                .authStartedFor(
                    fingerprintAuthenticateOptions.getUserId(), Fingerprint21.this.mSensorId, j);
            Fingerprint21.this.mCallbackCenter.onClientStarted(baseClientMonitor);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onBiometricAction(int i3) {
            Fingerprint21.this.mBiometricStateCallback.onBiometricAction(i3);
            Fingerprint21.this.mCallbackCenter.onBiometricAction(i3);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z3) {
            Fingerprint21.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z3);
            Fingerprint21.this
                .mBiometricContext
                .getAuthSessionCoordinator()
                .authEndedFor(
                    fingerprintAuthenticateOptions.getUserId(),
                    Utils.getCurrentStrength(Fingerprint21.this.mSensorId),
                    Fingerprint21.this.mSensorId,
                    j,
                    z3);
            Fingerprint21.this.mCallbackCenter.onClientFinished(baseClientMonitor, z3);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public long scheduleAuthenticate(
      IBinder iBinder,
      long j,
      int i,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      boolean z,
      int i2,
      boolean z2) {
    long incrementAndGet = this.mRequestCounter.incrementAndGet();
    scheduleAuthenticate(
        iBinder,
        j,
        i,
        clientMonitorCallbackConverter,
        fingerprintAuthenticateOptions,
        incrementAndGet,
        z,
        i2,
        z2);
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startPreparedClient$12(int i) {
    this.mScheduler.lambda$startPreparedClient$0(i);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void startPreparedClient(int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda10
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$startPreparedClient$12(i2);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void cancelAuthentication(int i, final IBinder iBinder, final long j) {
    Slog.d("Fingerprint21", "cancelAuthentication, sensorId: " + i);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda12
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$cancelAuthentication$13(iBinder, j);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelAuthentication$13(IBinder iBinder, long j) {
    this.mScheduler.lambda$cancelAuthenticationOrDetection$3(iBinder, j);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleRemove(
      int i,
      final IBinder iBinder,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final int i2,
      final int i3,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda21
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleRemove$14(
                i3, iBinder, iFingerprintServiceReceiver, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRemove$14(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int i2,
      String str) {
    scheduleUpdateActiveUserWithoutHandler(i);
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintRemovalClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            i2,
            i,
            str,
            FingerprintUtils.getLegacyInstance(this.mSensorId),
            this.mSensorProperties.sensorId,
            createLogger(4, 0),
            this.mBiometricContext,
            this.mAuthenticatorIds),
        this.mBiometricStateCallback);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleRemoveAll(
      int i,
      final IBinder iBinder,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final int i2,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda17
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleRemoveAll$15(
                i2, iBinder, iFingerprintServiceReceiver, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRemoveAll$15(
      int i, IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) {
    scheduleUpdateActiveUserWithoutHandler(i);
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintRemovalClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            0,
            i,
            str,
            FingerprintUtils.getLegacyInstance(this.mSensorId),
            this.mSensorProperties.sensorId,
            createLogger(4, 0),
            this.mBiometricContext,
            this.mAuthenticatorIds),
        this.mBiometricStateCallback);
  }

  public final void scheduleInternalCleanup(
      final int i, final ClientMonitorCallback clientMonitorCallback) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$scheduleInternalCleanup$16(i, clientMonitorCallback);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleInternalCleanup$16(
      int i, ClientMonitorCallback clientMonitorCallback) {
    scheduleUpdateActiveUserWithoutHandler(i);
    Context context = this.mContext;
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new FingerprintInternalCleanupClient(
            context,
            this.mLazyDaemon,
            i,
            context.getOpPackageName(),
            this.mSensorProperties.sensorId,
            createLogger(3, 0),
            this.mBiometricContext,
            FingerprintUtils.getLegacyInstance(this.mSensorId),
            this.mAuthenticatorIds),
        clientMonitorCallback);
  }

  public void scheduleInternalCleanup(int i, int i2, ClientMonitorCallback clientMonitorCallback) {
    scheduleInternalCleanup(
        i2,
        new ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleInternalCleanup(
      int i, int i2, ClientMonitorCallback clientMonitorCallback, boolean z) {
    scheduleInternalCleanup(
        i2,
        new ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
  }

  public final BiometricLogger createLogger(int i, int i2) {
    return new BiometricLogger(this.mContext, 1, i, i2);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean isHardwareDetected(int i) {
    return getDaemon() != null;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void rename(int i, final int i2, final int i3, final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda26
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$rename$17(i3, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$rename$17(int i, int i2, String str) {
    FingerprintUtils.getLegacyInstance(this.mSensorId)
        .renameBiometricForUser(this.mContext, i, i2, str);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public List getEnrolledFingerprints(int i, int i2) {
    return FingerprintUtils.getLegacyInstance(this.mSensorId)
        .getBiometricsForUser(this.mContext, i2);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean hasEnrollments(int i, int i2) {
    return !getEnrolledFingerprints(i, i2).isEmpty();
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public int getLockoutModeForUser(int i, int i2) {
    return this.mBiometricContext
        .getAuthSessionCoordinator()
        .getLockoutStateFor(i2, Utils.getCurrentStrength(i));
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public long getAuthenticatorId(int i, int i2) {
    return ((Long) this.mAuthenticatorIds.getOrDefault(Integer.valueOf(i2), 0L)).longValue();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onPointerDown(long j, int i, final PointerContext pointerContext) {
    semRequest(i, 22, 2, null, null);
    this.mScheduler.getCurrentClientIfMatches(
        j,
        new Consumer() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda29
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            Fingerprint21.lambda$onPointerDown$18(pointerContext, (BaseClientMonitor) obj);
          }
        });
  }

  /* JADX WARN: Multi-variable type inference failed */
  public static /* synthetic */ void lambda$onPointerDown$18(
      PointerContext pointerContext, BaseClientMonitor baseClientMonitor) {
    if (!(baseClientMonitor instanceof Udfps)) {
      Slog.w("Fingerprint21", "onFingerDown received during client: " + baseClientMonitor);
      return;
    }
    ((Udfps) baseClientMonitor).onPointerDown(pointerContext);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onPointerUp(long j, int i, final PointerContext pointerContext) {
    semRequest(i, 22, 1, null, null);
    if (getSensorProperties(i).sensorType == 3) {
      this.mHalResultController.onAcquired(0L, 6, 70001);
    }
    this.mScheduler.getCurrentClientIfMatches(
        j,
        new Consumer() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda23
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            Fingerprint21.lambda$onPointerUp$19(pointerContext, (BaseClientMonitor) obj);
          }
        });
  }

  /* JADX WARN: Multi-variable type inference failed */
  public static /* synthetic */ void lambda$onPointerUp$19(
      PointerContext pointerContext, BaseClientMonitor baseClientMonitor) {
    if (!(baseClientMonitor instanceof Udfps)) {
      Slog.w("Fingerprint21", "onFingerDown received during client: " + baseClientMonitor);
      return;
    }
    ((Udfps) baseClientMonitor).onPointerUp(pointerContext);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onUiReady(long j, int i) {
    this.mScheduler.getCurrentClientIfMatches(
        j,
        new Consumer() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda13
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            Fingerprint21.lambda$onUiReady$20((BaseClientMonitor) obj);
          }
        });
  }

  /* JADX WARN: Multi-variable type inference failed */
  public static /* synthetic */ void lambda$onUiReady$20(BaseClientMonitor baseClientMonitor) {
    if (!(baseClientMonitor instanceof Udfps)) {
      Slog.w("Fingerprint21", "onUiReady received during client: " + baseClientMonitor);
      return;
    }
    ((Udfps) baseClientMonitor).onUiReady();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onPowerPressed() {
    Slog.e("Fingerprint21", "onPowerPressed not supported for HIDL clients");
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpProtoState(int i, ProtoOutputStream protoOutputStream, boolean z) {
    long start = protoOutputStream.start(2246267895809L);
    protoOutputStream.write(1120986464257L, this.mSensorProperties.sensorId);
    protoOutputStream.write(1159641169922L, 1);
    if (this.mSensorProperties.isAnyUdfpsType()) {
      protoOutputStream.write(2259152797704L, 0);
    }
    protoOutputStream.write(
        1120986464259L, Utils.getCurrentStrength(this.mSensorProperties.sensorId));
    protoOutputStream.write(1146756268036L, this.mScheduler.dumpProtoState(z));
    Iterator it = UserManager.get(this.mContext).getUsers().iterator();
    while (it.hasNext()) {
      int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
      long start2 = protoOutputStream.start(2246267895813L);
      protoOutputStream.write(1120986464257L, identifier);
      protoOutputStream.write(
          1120986464258L,
          FingerprintUtils.getLegacyInstance(this.mSensorId)
              .getBiometricsForUser(this.mContext, identifier)
              .size());
      protoOutputStream.end(start2);
    }
    protoOutputStream.write(
        1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
    protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
    protoOutputStream.end(start);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpProtoMetrics(int i, FileDescriptor fileDescriptor) {
    PerformanceTracker instanceForSensorId =
        PerformanceTracker.getInstanceForSensorId(this.mSensorProperties.sensorId);
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
    Iterator it = UserManager.get(this.mContext).getUsers().iterator();
    while (it.hasNext()) {
      int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
      long start = protoOutputStream.start(2246267895809L);
      protoOutputStream.write(1120986464257L, identifier);
      protoOutputStream.write(
          1120986464258L,
          FingerprintUtils.getLegacyInstance(this.mSensorId)
              .getBiometricsForUser(this.mContext, identifier)
              .size());
      long start2 = protoOutputStream.start(1146756268035L);
      protoOutputStream.write(1120986464257L, instanceForSensorId.getAcceptForUser(identifier));
      protoOutputStream.write(1120986464258L, instanceForSensorId.getRejectForUser(identifier));
      protoOutputStream.write(1120986464259L, instanceForSensorId.getAcquireForUser(identifier));
      protoOutputStream.write(
          1120986464260L, instanceForSensorId.getTimedLockoutForUser(identifier));
      protoOutputStream.write(
          1120986464261L, instanceForSensorId.getPermanentLockoutForUser(identifier));
      protoOutputStream.end(start2);
      long start3 = protoOutputStream.start(1146756268036L);
      protoOutputStream.write(
          1120986464257L, instanceForSensorId.getAcceptCryptoForUser(identifier));
      protoOutputStream.write(
          1120986464258L, instanceForSensorId.getRejectCryptoForUser(identifier));
      protoOutputStream.write(
          1120986464259L, instanceForSensorId.getAcquireCryptoForUser(identifier));
      protoOutputStream.write(1120986464260L, 0);
      protoOutputStream.write(1120986464261L, 0);
      protoOutputStream.end(start3);
      protoOutputStream.end(start);
    }
    protoOutputStream.flush();
    instanceForSensorId.clear();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleInvalidateAuthenticatorId(
      int i, int i2, IInvalidationCallback iInvalidationCallback) {
    try {
      iInvalidationCallback.onCompleted();
    } catch (RemoteException unused) {
      Slog.e("Fingerprint21", "Failed to complete InvalidateAuthenticatorId");
    }
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpInternal(int i, PrintWriter printWriter) {
    PerformanceTracker instanceForSensorId =
        PerformanceTracker.getInstanceForSensorId(this.mSensorProperties.sensorId);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("service", "Fingerprint21");
      jSONObject.put("isUdfps", this.mIsUdfps);
      jSONObject.put("isPowerbuttonFps", this.mIsPowerbuttonFps);
      JSONArray jSONArray = new JSONArray();
      Iterator it = UserManager.get(this.mContext).getUsers().iterator();
      while (it.hasNext()) {
        int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
        int size =
            FingerprintUtils.getLegacyInstance(this.mSensorId)
                .getBiometricsForUser(this.mContext, identifier)
                .size();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", identifier);
        jSONObject2.put("count", size);
        jSONObject2.put("accept", instanceForSensorId.getAcceptForUser(identifier));
        jSONObject2.put("reject", instanceForSensorId.getRejectForUser(identifier));
        jSONObject2.put("quality", instanceForSensorId.semGetQualityForUser(identifier));
        jSONObject2.put("acquire", instanceForSensorId.getAcquireForUser(identifier));
        jSONObject2.put("lockout", instanceForSensorId.getTimedLockoutForUser(identifier));
        jSONObject2.put(
            "permanentLockout", instanceForSensorId.getPermanentLockoutForUser(identifier));
        jSONObject2.put("acceptCrypto", instanceForSensorId.getAcceptCryptoForUser(identifier));
        jSONObject2.put("rejectCrypto", instanceForSensorId.getRejectCryptoForUser(identifier));
        jSONObject2.put(
            "qualityCrypto", instanceForSensorId.semGetQualityCryptoForUser(identifier));
        jSONObject2.put("acquireCrypto", instanceForSensorId.getAcquireCryptoForUser(identifier));
        SparseArray semGetNoMatchReason = instanceForSensorId.semGetNoMatchReason(identifier);
        if (semGetNoMatchReason != null) {
          for (int i2 = 0; i2 < semGetNoMatchReason.size(); i2++) {
            int keyAt = semGetNoMatchReason.keyAt(i2);
            jSONObject2.put(String.valueOf(keyAt), semGetNoMatchReason.get(keyAt));
          }
        }
        jSONArray.put(jSONObject2);
      }
      jSONObject.put("prints", jSONArray);
    } catch (JSONException e) {
      Slog.e("Fingerprint21", "dump formatting failure", e);
    }
    printWriter.println(jSONObject);
    printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.getHALDeathCount());
    this.mScheduler.dump(printWriter);
    this.mProviderEx.dumpInternal(i, printWriter);
  }

  public void setTestHalEnabled(boolean z) {
    this.mTestHalEnabled = z;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public ITestSession createTestSession(
      int i, ITestSessionCallback iTestSessionCallback, String str) {
    return new BiometricTestSessionImpl(
        this.mContext,
        this.mSensorProperties.sensorId,
        iTestSessionCallback,
        this.mBiometricStateCallback,
        this,
        this.mHalResultController);
  }

  public TaskStackListener getTaskStackListener() {
    return this.mTaskStackListener;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onUserRemoved(int i) {
    scheduleRemoveAll(
        this.mSensorId, new Binder(), new FingerprintServiceReceiver(), i, "Fingerprint21");
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semGetSecurityLevel() {
    return this.mProviderEx.getSecurityLevel(this.mSensorId);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public BaseClientMonitor semGetCurrentClient() {
    return this.mScheduler.getCurrentClient();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public String semGetDaemonSdkVersion() {
    return this.mProviderEx.getDaemonSdkVersion(this.mSensorId);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public String semGetSensorInfo(int i, boolean z) {
    if (!z) {
      ((SemFpScheduler) this.mScheduler).cancelInterruptableOperation();
    }
    return this.mProviderEx.getSensorInfo(i, z);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public long semScheduleAuthenticate(
      final IBinder iBinder,
      final long j,
      final int i,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      final boolean z,
      final int i2,
      final boolean z2,
      final Bundle bundle) {
    final long incrementAndGet = this.mRequestCounter.incrementAndGet();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda30
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semScheduleAuthenticate$21(
                fingerprintAuthenticateOptions,
                iBinder,
                incrementAndGet,
                clientMonitorCallbackConverter,
                j,
                z,
                i,
                i2,
                z2,
                bundle);
          }
        });
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semScheduleAuthenticate$21(
      final FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      IBinder iBinder,
      final long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      long j2,
      boolean z,
      int i,
      int i2,
      boolean z2,
      Bundle bundle) {
    scheduleUpdateActiveUserWithoutHandler(fingerprintAuthenticateOptions.getUserId());
    FingerprintAuthenticationClient fingerprintAuthenticationClient =
        new FingerprintAuthenticationClient(
            this.mContext,
            this.mLazyDaemon,
            iBinder,
            j,
            clientMonitorCallbackConverter,
            j2,
            z,
            fingerprintAuthenticateOptions,
            i,
            false,
            createLogger(2, i2),
            this.mBiometricContext,
            Utils.isStrongBiometric(this.mSensorProperties.sensorId),
            this.mTaskStackListener,
            this.mLockoutTracker,
            this.mUdfpsOverlayController,
            this.mSidefpsController,
            this.mUdfpsOverlay,
            z2,
            this.mSensorProperties,
            Utils.getCurrentStrength(this.mSensorId));
    fingerprintAuthenticationClient.setExtraAttribute(bundle);
    this.mScheduler.lambda$scheduleClientMonitor$1(
        fingerprintAuthenticationClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.10
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {
            Fingerprint21.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            Fingerprint21.this
                .mBiometricContext
                .getAuthSessionCoordinator()
                .authStartedFor(
                    fingerprintAuthenticateOptions.getUserId(), Fingerprint21.this.mSensorId, j);
            Fingerprint21.this.mCallbackCenter.onClientStarted(baseClientMonitor);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onBiometricAction(int i3) {
            Fingerprint21.this.mBiometricStateCallback.onBiometricAction(i3);
            Fingerprint21.this.mCallbackCenter.onBiometricAction(i3);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z3) {
            Fingerprint21.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z3);
            Fingerprint21.this
                .mBiometricContext
                .getAuthSessionCoordinator()
                .authEndedFor(
                    fingerprintAuthenticateOptions.getUserId(),
                    Utils.getCurrentStrength(Fingerprint21.this.mSensorId),
                    Fingerprint21.this.mSensorId,
                    j,
                    z3);
            Fingerprint21.this.mCallbackCenter.onClientFinished(baseClientMonitor, z3);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semScheduleSensorTest(
      int i,
      final IBinder iBinder,
      int i2,
      int i3,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda27
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semScheduleSensorTest$22(
                iBinder, clientMonitorCallbackConverter);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semScheduleSensorTest$22(
      IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter) {
    this.mScheduler.scheduleClientMonitor(
        new SemFpSensorTestClient(
            this.mContext,
            this.mBiometricContext,
            new Fingerprint21$$ExternalSyntheticLambda18(this),
            iBinder,
            clientMonitorCallbackConverter,
            this.mSensorId));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semScheduleUpdateTrustApp(
      final int i,
      final String str,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final String str2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda28
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semScheduleUpdateTrustApp$23(
                clientMonitorCallbackConverter, str2, str, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semScheduleUpdateTrustApp$23(
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      String str,
      String str2,
      int i) {
    this.mScheduler.lambda$scheduleClientMonitor$1(
        new SemUpdateTrustAppClient(
            this.mContext,
            null,
            clientMonitorCallbackConverter,
            str,
            str2,
            i,
            1,
            this
                .mBiometricContext) { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.11
          @Override // com.android.server.biometrics.sensors.SemUpdateTrustAppClient
          public int sehInstallTAStart() {
            return Fingerprint21.this.handleRequestCommandWithoutScheduler(
                getSensorId(), 10001, 0, null, null);
          }

          @Override // com.android.server.biometrics.sensors.SemUpdateTrustAppClient
          public int sehInstallTAWrite(byte[] bArr) {
            return Fingerprint21.this.handleRequestCommandWithoutScheduler(
                getSensorId(), 10001, 1, bArr, null);
          }

          @Override // com.android.server.biometrics.sensors.SemUpdateTrustAppClient
          public int sehInstallTAEnd(byte[] bArr) {
            return Fingerprint21.this.handleRequestCommandWithoutScheduler(
                getSensorId(), 10001, 2, bArr, null);
          }
        },
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.12
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            if (z) {
              Fingerprint21.this.mProviderEx.updateCacheDataOfHAL(baseClientMonitor.getSensorId());
              SemBioAnalyticsManager.getInstance()
                  .fpHalInfo(
                      Fingerprint21.this.mProviderEx.getDaemonSdkVersion(
                          baseClientMonitor.getSensorId()),
                      null);
            }
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semOpenTzSession() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda35
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semOpenTzSession$24();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semOpenTzSession$24() {
    this.mScheduler.scheduleClientMonitor(
        new SemFpBaseRequestClient.Builder(
                this.mContext,
                this.mBiometricContext,
                new Fingerprint21$$ExternalSyntheticLambda18(this),
                this.mSensorId)
            .setCommand(2)
            .setUseScheduler()
            .build());
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semProcessFidoCommand(int i, int i2, byte[] bArr, byte[] bArr2) {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final SemFpBaseRequestClient build =
        new SemFpBaseRequestClient.Builder(
                this.mContext,
                this.mBiometricContext,
                new Fingerprint21$$ExternalSyntheticLambda18(this),
                i)
            .setCommand(9)
            .setParam(0)
            .setInputBuffer(bArr)
            .setOutputBuffer(bArr2)
            .setUseScheduler()
            .build();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda19
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semProcessFidoCommand$25(build, countDownLatch);
          }
        });
    try {
      countDownLatch.await(2L, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Slog.w("Fingerprint21", "Latch interrupted", e);
    }
    return build.getRequestResult();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semProcessFidoCommand$25(
      SemFpBaseRequestClient semFpBaseRequestClient, final CountDownLatch countDownLatch) {
    this.mScheduler.lambda$scheduleClientMonitor$1(
        semFpBaseRequestClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.13
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            countDownLatch.countDown();
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semRequest(int i, int i2) {
    return semRequest(i, i2, 0, null, null);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semRequest(int i, int i2, int i3, byte[] bArr, byte[] bArr2) {
    if (SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
      return 0;
    }
    return handleRequestCommandWithoutScheduler(i, i2, i3, bArr, bArr2);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void pauseEnroll(int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda20
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$pauseEnroll$26();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$pauseEnroll$26() {
    IBinder.DeathRecipient currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof SemFpPauseResumeHandler) {
      ((SemFpPauseResumeHandler) currentClient).onPause();
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void resumeEnroll(int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda16
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$resumeEnroll$27();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$resumeEnroll$27() {
    IBinder.DeathRecipient currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof SemFpPauseResumeHandler) {
      ((SemFpPauseResumeHandler) currentClient).onResume();
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semNotifyTspBlockStateToClient(boolean z) {
    IBinder.DeathRecipient currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof SemFpTspBlockStatusHandler) {
      SemFpTspBlockStatusHandler semFpTspBlockStatusHandler =
          (SemFpTspBlockStatusHandler) currentClient;
      if (z) {
        semFpTspBlockStatusHandler.onTspBlocked();
      } else {
        semFpTspBlockStatusHandler.onTspUnBlocked();
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onOneHandModeEnabled() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda36
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$onOneHandModeEnabled$28();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onOneHandModeEnabled$28() {
    BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof SemUdfpsConstraintStatusListener) {
      Slog.d("Fingerprint21", "handle OneHandMode, client: " + Utils.getClientName(currentClient));
      ((SemUdfpsConstraintStatusListener) currentClient).onOneHandModeEnabled();
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onWirelessPowerEnabled() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda33
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$onWirelessPowerEnabled$29();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onWirelessPowerEnabled$29() {
    BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
    if (currentClient instanceof SemUdfpsConstraintStatusListener) {
      Slog.d(
          "Fingerprint21", "handle WirelessPower, client: " + Utils.getClientName(currentClient));
      ((SemUdfpsConstraintStatusListener) currentClient).onWirelessPowerEnabled();
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public String[] semGetOpticalBrightnessConfigs(int i) {
    byte[] bArr = new byte[256];
    int handleRequestCommandWithoutScheduler =
        handleRequestCommandWithoutScheduler(i, 32, 0, null, bArr);
    if (handleRequestCommandWithoutScheduler > 0) {
      try {
        return new String(bArr, 0, handleRequestCommandWithoutScheduler, StandardCharsets.UTF_8)
            .trim()
            .split(",");
      } catch (Exception e) {
        Slog.w("Fingerprint21", "semGetOpticalBrightnessConfigs: " + e.getMessage());
      }
    }
    return new String[0];
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semGetRemainingLockoutTime(int i) {
    return this.mLockoutTracker.getRemainingLockoutTime(i);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semUpdateTpaAction() {
    SehTestHal sehTestHal;
    if (!this.mTpaHalModeEnabled || (sehTestHal = this.mSehTestHal) == null) {
      return;
    }
    sehTestHal.postUpdateAction();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semSetTpaHalEnabled(boolean z) {
    int i;
    if (this.mTpaHalModeEnabled == z) {
      return;
    }
    this.mTpaHalModeEnabled = z;
    int intDb = Utils.getIntDb(this.mContext, "biometric_tpa_mode", true, 0);
    if (z) {
      if (this.mSehTestHal == null) {
        SehTestHal sehTestHal =
            new SehTestHal(this.mContext, this.mSensorProperties.sensorId, true);
        this.mSehTestHal = sehTestHal;
        sehTestHal.setNotify(this.mHalResultController);
      }
      i = intDb | 2;
    } else {
      SehTestHal sehTestHal2 = this.mSehTestHal;
      if (sehTestHal2 != null) {
        sehTestHal2.destroy();
        this.mSehTestHal = null;
      }
      i = intDb & (-3);
    }
    Utils.putIntDb(this.mContext, "biometric_tpa_mode", true, i);
    scheduleInternalCleanup(
        this.mSensorId,
        0,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.14
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {}

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z2) {
            Fingerprint21.this.scheduleUpdateActiveUserWithoutHandler(0, true);
          }
        });
  }

  public final int handleRequestCommandWithoutScheduler(
      int i, int i2, int i3, byte[] bArr, byte[] bArr2) {
    return new SemFpBaseRequestClient.Builder(
            this.mContext,
            this.mBiometricContext,
            new Fingerprint21$$ExternalSyntheticLambda18(this),
            i)
        .setCommand(i2)
        .setParam(i3)
        .setInputBuffer(bArr)
        .setOutputBuffer(bArr2)
        .build()
        .startWithoutScheduler();
  }

  public final ISehBiometricsFingerprint getSehDaemon() {
    IBiometricsFingerprint daemon = getDaemon();
    if (daemon instanceof ISehBiometricsFingerprint) {
      return (ISehBiometricsFingerprint) daemon;
    }
    return null;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddHalLifecycleListener(
      final SemFpHalLifecycleListener semFpHalLifecycleListener) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda24
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semAddHalLifecycleListener$31(semFpHalLifecycleListener);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semAddHalLifecycleListener$31(
      final SemFpHalLifecycleListener semFpHalLifecycleListener) {
    if (semFpHalLifecycleListener == null
        || this.mLifecycleListeners.contains(semFpHalLifecycleListener)) {
      return;
    }
    this.mLifecycleListeners.add(semFpHalLifecycleListener);
    if (getDaemon() == null || !this.mIsHalStarted) {
      return;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda34
          @Override // java.lang.Runnable
          public final void run() {
            Fingerprint21.this.lambda$semAddHalLifecycleListener$30(semFpHalLifecycleListener);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semAddHalLifecycleListener$30(
      SemFpHalLifecycleListener semFpHalLifecycleListener) {
    semFpHalLifecycleListener.onHalStarted(this);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddEventListener(SemFpEventListener semFpEventListener) {
    this.mCallbackCenter.addEventListener(semFpEventListener);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semRemoveEventListener(SemFpEventListener semFpEventListener) {
    this.mCallbackCenter.removeEventListener(semFpEventListener);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddChallengeListener(SemFpChallengeListener semFpChallengeListener) {
    this.mCallbackCenter.addChallengeListener(semFpChallengeListener);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddAuthenticationListener(
      SemFpAuthenticationListener semFpAuthenticationListener) {
    this.mCallbackCenter.addAuthenticationListener(semFpAuthenticationListener);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddEnrollmentListener(SemFpEnrollmentListener semFpEnrollmentListener) {
    this.mCallbackCenter.addEnrollListener(semFpEnrollmentListener);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddResetLockoutListener(SemFpResetLockoutListener semFpResetLockoutListener) {
    this.mCallbackCenter.addResetLockoutListener(semFpResetLockoutListener);
  }

  public final void dispatchHalStarted() {
    Iterator it = this.mLifecycleListeners.iterator();
    while (it.hasNext()) {
      ((SemFpHalLifecycleListener) it.next()).onHalStarted(this);
    }
  }

  public final void dispatchHalStopped() {
    Iterator it = this.mLifecycleListeners.iterator();
    while (it.hasNext()) {
      ((SemFpHalLifecycleListener) it.next()).onHalStop(this);
    }
  }

  public LockoutFrameworkImpl getLockoutFrameworkImpl() {
    return this.mLockoutTracker;
  }
}
