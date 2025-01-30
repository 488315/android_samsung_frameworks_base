package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.SynchronousUserSwitchObserver;
import android.app.TaskStackListener;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.TypedArray;
import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.biometrics.common.CommonProps;
import android.hardware.biometrics.common.ComponentInfo;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.biometrics.fingerprint.SensorLocation;
import android.hardware.biometrics.fingerprint.SensorProps;
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
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.InvalidationRequesterClient;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.SemUpdateTrustAppClient;
import com.android.server.biometrics.sensors.SensorList;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;
import com.android.server.biometrics.sensors.fingerprint.PowerPressHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter;
import com.android.server.biometrics.sensors.fingerprint.SemFpChallengeListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpEventListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalLifecycleListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpMainThread;
import com.android.server.biometrics.sensors.fingerprint.SemFpPauseResumeHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpProviderEx;
import com.android.server.biometrics.sensors.fingerprint.SemFpResetLockoutListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpSensorTestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpUserAwareScheduler;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener;
import com.android.server.biometrics.sensors.fingerprint.ServiceProvider;
import com.android.server.biometrics.sensors.fingerprint.Udfps;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;

/* loaded from: classes.dex */
public class FingerprintProvider implements IBinder.DeathRecipient, ServiceProvider {
  public AuthSessionCoordinator mAuthSessionCoordinator;
  public final BiometricContext mBiometricContext;
  public final BiometricStateCallback mBiometricStateCallback;
  public final SemFpCallbackCenter mCallbackCenter;
  public final Context mContext;
  public IFingerprint mDaemon;
  public final String mHalInstanceName;
  public boolean mIsHalStarted;
  public final LockoutResetDispatcher mLockoutResetDispatcher;
  public final SemFpProviderEx mProviderEx;
  public ISehFingerprint mSehFingerprint;
  public ISidefpsController mSidefpsController;
  public boolean mTestHalEnabled;
  public boolean mTpaHalModeEnabled;
  public SemTpaTestHal mTpaTestHal;
  public IUdfpsOverlay mUdfpsOverlay;
  public IUdfpsOverlayController mUdfpsOverlayController;
  public final AtomicLong mRequestCounter = new AtomicLong(0);
  public final ArrayList mLifecycleListeners = new ArrayList();
  final SensorList mFingerprintSensors = new SensorList(ActivityManager.getService());
  public final Handler mHandler = new Handler(SemFpMainThread.get().getLooper());
  public final ActivityTaskManager mActivityTaskManager = ActivityTaskManager.getInstance();
  public final BiometricTaskStackListener mTaskStackListener = new BiometricTaskStackListener();

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpProtoMetrics(int i, FileDescriptor fileDescriptor) {}

  public final class BiometricTaskStackListener extends TaskStackListener
      implements ActivityManager.SemProcessListener {
    public void onProcessDied(int i, int i2) {}

    public BiometricTaskStackListener() {}

    public void onTaskStackChanged() {
      FingerprintProvider.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$BiometricTaskStackListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintProvider.BiometricTaskStackListener.this.lambda$onTaskStackChanged$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskStackChanged$0() {
      for (int i = 0; i < FingerprintProvider.this.mFingerprintSensors.size(); i++) {
        BaseClientMonitor currentClient =
            ((Sensor) FingerprintProvider.this.mFingerprintSensors.valueAt(i))
                .getScheduler()
                .getCurrentClient();
        if (!(currentClient instanceof AuthenticationClient)) {
          Slog.e(
              FingerprintProvider.this.getTag(), "Task stack changed for client: " + currentClient);
        } else if (!Utils.isKeyguard(
                FingerprintProvider.this.mContext, currentClient.getOwnerString())
            && !Utils.isSystem(FingerprintProvider.this.mContext, currentClient.getOwnerString())
            && !((AuthenticationClient) currentClient).semIsAllowedBackgroundAuthentication()
            && Utils.isBackground(currentClient.getOwnerString())
            && !currentClient.isAlreadyDone()) {
          Slog.e(
              FingerprintProvider.this.getTag(),
              "Stopping background authentication, currentClient: " + currentClient);
          ((Sensor) FingerprintProvider.this.mFingerprintSensors.valueAt(i))
              .getScheduler()
              .lambda$cancelAuthenticationOrDetection$3(
                  currentClient.getToken(), currentClient.getRequestId());
        }
      }
    }

    public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
      Slog.d(
          FingerprintProvider.this.getTag(),
          "onForegroundActivitiesChanged: pid = "
              + i
              + ", uid = "
              + i2
              + ", foregroundActivities = "
              + z);
      onTaskStackChanged();
    }
  }

  public FingerprintProvider(
      Context context,
      BiometricStateCallback biometricStateCallback,
      SensorProps[] sensorPropsArr,
      String str,
      LockoutResetDispatcher lockoutResetDispatcher,
      GestureAvailabilityDispatcher gestureAvailabilityDispatcher,
      BiometricContext biometricContext) {
    int semGetMaxTemplateNumberFromSPF;
    SensorProps[] sensorPropsArr2 = sensorPropsArr;
    this.mContext = context;
    this.mBiometricStateCallback = biometricStateCallback;
    this.mHalInstanceName = str;
    this.mLockoutResetDispatcher = lockoutResetDispatcher;
    this.mBiometricContext = biometricContext;
    this.mAuthSessionCoordinator = biometricContext.getAuthSessionCoordinator();
    List workaroundSensorProps = getWorkaroundSensorProps(context);
    int length = sensorPropsArr2.length;
    int i = 0;
    while (i < length) {
      SensorProps sensorProps = sensorPropsArr2[i];
      final int i2 = sensorProps.commonProps.sensorId;
      ArrayList arrayList = new ArrayList();
      ComponentInfo[] componentInfoArr = sensorProps.commonProps.componentInfo;
      if (componentInfoArr != null) {
        int i3 = 0;
        for (int length2 = componentInfoArr.length; i3 < length2; length2 = length2) {
          ComponentInfo componentInfo = componentInfoArr[i3];
          arrayList.add(
              new ComponentInfoInternal(
                  componentInfo.componentId,
                  componentInfo.hardwareVersion,
                  componentInfo.firmwareVersion,
                  componentInfo.serialNumber,
                  componentInfo.softwareVersion));
          i3++;
          componentInfoArr = componentInfoArr;
        }
      }
      if (SystemProperties.getInt("ro.board.first_api_level", 33) >= 34) {
        semGetMaxTemplateNumberFromSPF = sensorProps.commonProps.maxEnrollmentsPerUser;
      } else {
        semGetMaxTemplateNumberFromSPF = FingerprintUtils.semGetMaxTemplateNumberFromSPF();
      }
      int i4 = semGetMaxTemplateNumberFromSPF;
      CommonProps commonProps = sensorProps.commonProps;
      FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal =
          new FingerprintSensorPropertiesInternal(
              commonProps.sensorId,
              commonProps.sensorStrength,
              i4,
              arrayList,
              sensorProps.sensorType,
              sensorProps.halControlsIllumination,
              true,
              !workaroundSensorProps.isEmpty()
                  ? workaroundSensorProps
                  : (List)
                      Arrays.stream(sensorProps.sensorLocations)
                          .map(
                              new Function() { // from class:
                                               // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda2
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                  SensorLocationInternal lambda$new$0;
                                  lambda$new$0 =
                                      FingerprintProvider.lambda$new$0((SensorLocation) obj);
                                  return lambda$new$0;
                                }
                              })
                          .collect(Collectors.toList()));
      Sensor sensor =
          new Sensor(
              getTag() + "/" + i2,
              this,
              this.mContext,
              this.mHandler,
              fingerprintSensorPropertiesInternal,
              lockoutResetDispatcher,
              gestureAvailabilityDispatcher,
              this.mBiometricContext);
      this.mFingerprintSensors.addSensor(
          i2,
          sensor,
          sensor.getLazySession().get() == null
              ? -10000
              : ((AidlSession) sensor.getLazySession().get()).getUserId(),
          new SynchronousUserSwitchObserver() { // from class:
                                                // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.1
            public void onUserSwitching(int i5) {
              FingerprintProvider.this.scheduleInternalCleanup(i2, i5, null);
            }
          });
      Slog.d(getTag(), "Added: " + fingerprintSensorPropertiesInternal);
      i++;
      sensorPropsArr2 = sensorPropsArr;
    }
    this.mProviderEx =
        new SemFpProviderEx(
            new BiFunction() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda3
              @Override // java.util.function.BiFunction
              public final Object apply(Object obj, Object obj2) {
                String lambda$new$1;
                lambda$new$1 = FingerprintProvider.this.lambda$new$1((Integer) obj, (Integer) obj2);
                return lambda$new$1;
              }
            },
            new BiFunction() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda4
              @Override // java.util.function.BiFunction
              public final Object apply(Object obj, Object obj2) {
                return Integer.valueOf(
                    FingerprintProvider.this.semRequest(
                        ((Integer) obj).intValue(), ((Integer) obj2).intValue()));
              }
            });
    this.mCallbackCenter = new SemFpCallbackCenter(this, this.mHandler);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.getHalInstance();
          }
        });
  }

  public static /* synthetic */ SensorLocationInternal lambda$new$0(SensorLocation sensorLocation) {
    return new SensorLocationInternal(
        sensorLocation.display,
        sensorLocation.sensorLocationX,
        sensorLocation.sensorLocationY,
        sensorLocation.sensorRadius);
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

  public final String getTag() {
    return "FingerprintProvider/" + this.mHalInstanceName;
  }

  public boolean hasHalInstance() {
    if (this.mTestHalEnabled) {
      return true;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(IFingerprint.DESCRIPTOR);
    sb.append("/");
    sb.append(this.mHalInstanceName);
    return ServiceManager.checkService(sb.toString()) != null;
  }

  public synchronized IFingerprint getHalInstance() {
    if (this.mTestHalEnabled) {
      return new TestHal();
    }
    if (this.mTpaHalModeEnabled) {
      return this.mTpaTestHal;
    }
    IFingerprint iFingerprint = this.mDaemon;
    if (iFingerprint != null) {
      return iFingerprint;
    }
    Slog.d(getTag(), "Daemon was null, reconnecting");
    IFingerprint asInterface =
        IFingerprint.Stub.asInterface(
            Binder.allowBlocking(
                ServiceManager.waitForDeclaredService(
                    IFingerprint.DESCRIPTOR + "/" + this.mHalInstanceName)));
    this.mDaemon = asInterface;
    if (asInterface == null) {
      Slog.e(getTag(), "Unable to get daemon");
      return null;
    }
    try {
      asInterface.asBinder().linkToDeath(this, 0);
    } catch (RemoteException e) {
      Slog.e(getTag(), "Unable to linkToDeath", e);
    }
    for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
      int keyAt = this.mFingerprintSensors.keyAt(i);
      scheduleLoadAuthenticatorIds(keyAt);
      scheduleInternalCleanup(keyAt, ActivityManager.getCurrentUser(), null);
      this.mProviderEx.updateCacheDataOfHAL(keyAt);
      handleHalStarted();
    }
    return this.mDaemon;
  }

  public void handleHalStarted() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda30
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$handleHalStarted$2();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleHalStarted$2() {
    this.mIsHalStarted = true;
    dispatchHalStarted();
  }

  public final void scheduleForSensor(int i, BaseClientMonitor baseClientMonitor) {
    if (!this.mFingerprintSensors.contains(i)) {
      throw new IllegalStateException(
          "Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
    }
    ((Sensor) this.mFingerprintSensors.get(i))
        .getScheduler()
        .scheduleClientMonitor(baseClientMonitor);
  }

  public final void scheduleForSensor(
      int i, BaseClientMonitor baseClientMonitor, ClientMonitorCallback clientMonitorCallback) {
    if (!this.mFingerprintSensors.contains(i)) {
      throw new IllegalStateException(
          "Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
    }
    ((Sensor) this.mFingerprintSensors.get(i))
        .getScheduler()
        .lambda$scheduleClientMonitor$1(baseClientMonitor, clientMonitorCallback);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean containsSensor(int i) {
    return this.mFingerprintSensors.contains(i);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public List getSensorProperties() {
    ArrayList arrayList = new ArrayList();
    for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
      arrayList.add(((Sensor) this.mFingerprintSensors.valueAt(i)).getSensorProperties());
    }
    return arrayList;
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public FingerprintSensorPropertiesInternal getSensorProperties(int i) {
    if (this.mFingerprintSensors.size() == 0) {
      return null;
    }
    if (i == -1) {
      return ((Sensor) this.mFingerprintSensors.valueAt(0)).getSensorProperties();
    }
    Sensor sensor = (Sensor) this.mFingerprintSensors.get(i);
    if (sensor != null) {
      return sensor.getSensorProperties();
    }
    return null;
  }

  public final void scheduleLoadAuthenticatorIds(int i) {
    Iterator it = UserManager.get(this.mContext).getAliveUsers().iterator();
    while (it.hasNext()) {
      scheduleLoadAuthenticatorIdsForUser(i, ((UserInfo) it.next()).id);
    }
  }

  public final void scheduleLoadAuthenticatorIdsForUser(final int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda12
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleLoadAuthenticatorIdsForUser$3(i, i2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleLoadAuthenticatorIdsForUser$3(int i, int i2) {
    scheduleForSensor(
        i,
        new FingerprintGetAuthenticatorIdClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            i2,
            this.mContext.getOpPackageName(),
            i,
            createLogger(0, 0),
            this.mBiometricContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getAuthenticatorIds()));
  }

  public void scheduleInvalidationRequest(final int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda34
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleInvalidationRequest$4(i2, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleInvalidationRequest$4(int i, int i2) {
    Context context = this.mContext;
    scheduleForSensor(
        i2,
        new InvalidationRequesterClient(
            context,
            i,
            i2,
            BiometricLogger.ofUnknown(context),
            this.mBiometricContext,
            FingerprintUtils.getInstance(i2)));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleResetLockout(final int i, final int i2, final byte[] bArr) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda14
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleResetLockout$5(i, i2, bArr);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleResetLockout$5(int i, int i2, byte[] bArr) {
    FingerprintResetLockoutClient fingerprintResetLockoutClient =
        new FingerprintResetLockoutClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            i2,
            this.mContext.getOpPackageName(),
            i,
            createLogger(0, 0),
            this.mBiometricContext,
            bArr,
            ((Sensor) this.mFingerprintSensors.get(i)).getLockoutCache(),
            this.mLockoutResetDispatcher,
            Utils.getCurrentStrength(i));
    BaseClientMonitor semGetCurrentClient = semGetCurrentClient();
    if ((semGetCurrentClient instanceof FingerprintAuthenticationClient)
        && ((FingerprintAuthenticationClient) semGetCurrentClient).getState() != 4) {
      fingerprintResetLockoutClient.start(
          new ClientMonitorCallback() { // from class:
                                        // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
              FingerprintProvider.this.mCallbackCenter.onResetLockout(
                  baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
          });
    } else {
      scheduleForSensor(
          i,
          fingerprintResetLockoutClient,
          new ClientMonitorCallback() { // from class:
                                        // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.3
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
              FingerprintProvider.this.mCallbackCenter.onResetLockout(
                  baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
          });
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleGenerateChallenge(
      final int i,
      final int i2,
      final IBinder iBinder,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleGenerateChallenge$6(
                i, iBinder, iFingerprintServiceReceiver, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleGenerateChallenge$6(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int i2,
      String str) {
    scheduleForSensor(
        i,
        new FingerprintGenerateChallengeClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            iBinder,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            i2,
            str,
            i,
            createLogger(0, 0),
            this.mBiometricContext));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleRevokeChallenge(
      final int i, final int i2, final IBinder iBinder, final String str, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda28
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleRevokeChallenge$7(i, iBinder, i2, str, j);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRevokeChallenge$7(
      int i, IBinder iBinder, int i2, String str, long j) {
    scheduleForSensor(
        i,
        new FingerprintRevokeChallengeClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            iBinder,
            i2,
            str,
            i,
            createLogger(0, 0),
            this.mBiometricContext,
            j));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public long scheduleEnroll(
      final int i,
      final IBinder iBinder,
      final byte[] bArr,
      final int i2,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final String str,
      final int i3) {
    final long incrementAndGet = this.mRequestCounter.incrementAndGet();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda27
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleEnroll$8(
                i, iBinder, incrementAndGet, iFingerprintServiceReceiver, i2, bArr, str, i3);
          }
        });
    return incrementAndGet;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleEnroll$8(
      final int i,
      IBinder iBinder,
      long j,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final int i2,
      byte[] bArr,
      String str,
      int i3) {
    FingerprintEnrollClient fingerprintEnrollClient =
        new FingerprintEnrollClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            iBinder,
            j,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            i2,
            bArr,
            str,
            FingerprintUtils.getInstance(i),
            i,
            createLogger(1, 0),
            this.mBiometricContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getSensorProperties(),
            this.mUdfpsOverlayController,
            this.mSidefpsController,
            this.mUdfpsOverlay,
            ((Sensor) this.mFingerprintSensors.get(i)).getSensorProperties().maxEnrollmentsPerUser,
            i3);
    fingerprintEnrollClient.setLazySehFingerprint(
        new FingerprintProvider$$ExternalSyntheticLambda0(this));
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      fingerprintEnrollClient.initForUdfps();
    }
    scheduleForSensor(
        i,
        fingerprintEnrollClient,
        new ClientMonitorCompositeCallback(
            this.mBiometricStateCallback,
            new ClientMonitorCallback() { // from class:
                                          // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.4
              @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
              public void onClientStarted(BaseClientMonitor baseClientMonitor) {
                FingerprintProvider.this.mCallbackCenter.onClientStarted(baseClientMonitor);
              }

              @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
              public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                super.onClientFinished(baseClientMonitor, z);
                if (z) {
                  FingerprintProvider.this.scheduleLoadAuthenticatorIdsForUser(i, i2);
                  FingerprintProvider.this.scheduleInvalidationRequest(i, i2);
                }
                FingerprintProvider.this.mCallbackCenter.onClientFinished(baseClientMonitor, z);
              }
            }));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void cancelEnrollment(final int i, final IBinder iBinder, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda25
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$cancelEnrollment$9(i, iBinder, j);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelEnrollment$9(int i, IBinder iBinder, long j) {
    ((Sensor) this.mFingerprintSensors.get(i)).getScheduler().lambda$cancelEnrollment$2(iBinder, j);
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
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda9
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleFingerDetect$10(
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
    int sensorId = fingerprintAuthenticateOptions.getSensorId();
    scheduleForSensor(
        sensorId,
        new FingerprintDetectClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getLazySession(),
            iBinder,
            j,
            clientMonitorCallbackConverter,
            fingerprintAuthenticateOptions,
            createLogger(2, i),
            this.mBiometricContext,
            this.mUdfpsOverlayController,
            this.mUdfpsOverlay,
            Utils.isStrongBiometric(sensorId)),
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
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda24
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleAuthenticate$11(
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
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      IBinder iBinder,
      final long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      long j2,
      boolean z,
      int i,
      int i2,
      boolean z2) {
    final int userId = fingerprintAuthenticateOptions.getUserId();
    final int sensorId = fingerprintAuthenticateOptions.getSensorId();
    FingerprintAuthenticationClient fingerprintAuthenticationClient =
        new FingerprintAuthenticationClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getLazySession(),
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
            Utils.isStrongBiometric(sensorId),
            this.mTaskStackListener,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getLockoutCache(),
            this.mUdfpsOverlayController,
            this.mSidefpsController,
            this.mUdfpsOverlay,
            z2,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getSensorProperties(),
            this.mHandler,
            Utils.getCurrentStrength(sensorId),
            SystemClock.elapsedRealtimeClock());
    fingerprintAuthenticationClient.setExtraAttribute(new Bundle());
    scheduleForSensor(
        sensorId,
        fingerprintAuthenticationClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.5
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {
            FingerprintProvider.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            FingerprintProvider.this.mAuthSessionCoordinator.authStartedFor(userId, sensorId, j);
            FingerprintProvider.this.mCallbackCenter.onClientStarted(baseClientMonitor);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onBiometricAction(int i3) {
            FingerprintProvider.this.mBiometricStateCallback.onBiometricAction(i3);
            FingerprintProvider.this.mCallbackCenter.onBiometricAction(i3);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z3) {
            FingerprintProvider.this.mBiometricStateCallback.onClientFinished(
                baseClientMonitor, z3);
            FingerprintProvider.this.mAuthSessionCoordinator.authEndedFor(
                userId, Utils.getCurrentStrength(sensorId), sensorId, j, z3);
            FingerprintProvider.this.mCallbackCenter.onClientFinished(baseClientMonitor, z3);
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
  public /* synthetic */ void lambda$startPreparedClient$12(int i, int i2) {
    ((Sensor) this.mFingerprintSensors.get(i)).getScheduler().lambda$startPreparedClient$0(i2);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void startPreparedClient(final int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda22
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$startPreparedClient$12(i, i2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelAuthentication$13(int i, IBinder iBinder, long j) {
    ((Sensor) this.mFingerprintSensors.get(i))
        .getScheduler()
        .lambda$cancelAuthenticationOrDetection$3(iBinder, j);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void cancelAuthentication(final int i, final IBinder iBinder, final long j) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda18
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$cancelAuthentication$13(i, iBinder, j);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleRemove(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int i2,
      int i3,
      String str) {
    scheduleRemoveSpecifiedIds(i, iBinder, new int[] {i2}, i3, iFingerprintServiceReceiver, str);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleRemoveAll(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int i2,
      String str) {
    List biometricsForUser =
        FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
    int[] iArr = new int[biometricsForUser.size()];
    for (int i3 = 0; i3 < biometricsForUser.size(); i3++) {
      iArr[i3] = ((Fingerprint) biometricsForUser.get(i3)).getBiometricId();
    }
    scheduleRemoveSpecifiedIds(i, iBinder, iArr, i2, iFingerprintServiceReceiver, str);
  }

  public final void scheduleRemoveSpecifiedIds(
      final int i,
      final IBinder iBinder,
      final int[] iArr,
      final int i2,
      final IFingerprintServiceReceiver iFingerprintServiceReceiver,
      final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda17
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleRemoveSpecifiedIds$14(
                i, iBinder, iFingerprintServiceReceiver, iArr, i2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleRemoveSpecifiedIds$14(
      int i,
      IBinder iBinder,
      IFingerprintServiceReceiver iFingerprintServiceReceiver,
      int[] iArr,
      int i2,
      String str) {
    scheduleForSensor(
        i,
        new FingerprintRemovalClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            iBinder,
            new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
            iArr,
            i2,
            str,
            FingerprintUtils.getInstance(i),
            i,
            createLogger(4, 0),
            this.mBiometricContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getAuthenticatorIds()),
        this.mBiometricStateCallback);
  }

  public void scheduleInternalCleanup(int i, int i2, ClientMonitorCallback clientMonitorCallback) {
    scheduleInternalCleanup(i, i2, clientMonitorCallback, false);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleInternalCleanup(
      final int i,
      final int i2,
      final ClientMonitorCallback clientMonitorCallback,
      final boolean z) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda15
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleInternalCleanup$15(
                i, i2, z, clientMonitorCallback);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleInternalCleanup$15(
      int i, int i2, boolean z, ClientMonitorCallback clientMonitorCallback) {
    FingerprintInternalCleanupClient fingerprintInternalCleanupClient =
        new FingerprintInternalCleanupClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            i2,
            this.mContext.getOpPackageName(),
            i,
            createLogger(3, 0),
            this.mBiometricContext,
            FingerprintUtils.getInstance(i),
            ((Sensor) this.mFingerprintSensors.get(i)).getAuthenticatorIds());
    if (z) {
      fingerprintInternalCleanupClient.setFavorHalEnrollments();
    }
    scheduleForSensor(
        i,
        fingerprintInternalCleanupClient,
        new ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
  }

  public final BiometricLogger createLogger(int i, int i2) {
    return new BiometricLogger(this.mContext, 1, i, i2);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean isHardwareDetected(int i) {
    return hasHalInstance();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void rename(int i, int i2, int i3, String str) {
    FingerprintUtils.getInstance(i).renameBiometricForUser(this.mContext, i3, i2, str);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public List getEnrolledFingerprints(int i, int i2) {
    return FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public boolean hasEnrollments(int i, int i2) {
    return !getEnrolledFingerprints(i, i2).isEmpty();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleInvalidateAuthenticatorId(
      final int i, final int i2, final IInvalidationCallback iInvalidationCallback) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$scheduleInvalidateAuthenticatorId$16(
                i, i2, iInvalidationCallback);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleInvalidateAuthenticatorId$16(
      int i, int i2, IInvalidationCallback iInvalidationCallback) {
    scheduleForSensor(
        i,
        new FingerprintInvalidationClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getLazySession(),
            i2,
            i,
            createLogger(0, 0),
            this.mBiometricContext,
            ((Sensor) this.mFingerprintSensors.get(i)).getAuthenticatorIds(),
            iInvalidationCallback));
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public int getLockoutModeForUser(int i, int i2) {
    return this.mBiometricContext
        .getAuthSessionCoordinator()
        .getLockoutStateFor(i2, Utils.getCurrentStrength(i));
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public long getAuthenticatorId(int i, int i2) {
    return ((Long)
            ((Sensor) this.mFingerprintSensors.get(i))
                .getAuthenticatorIds()
                .getOrDefault(Integer.valueOf(i2), 0L))
        .longValue();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onPointerDown(long j, int i, final PointerContext pointerContext) {
    semRequest(i, 22, 2, null, null);
    ((Sensor) this.mFingerprintSensors.get(i))
        .getScheduler()
        .getCurrentClientIfMatches(
            j,
            new Consumer() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda23
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                FingerprintProvider.this.lambda$onPointerDown$17(
                    pointerContext, (BaseClientMonitor) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onPointerDown$17(
      PointerContext pointerContext, BaseClientMonitor baseClientMonitor) {
    if (!(baseClientMonitor instanceof Udfps)) {
      Slog.e(getTag(), "onPointerDown received during client: " + baseClientMonitor);
      return;
    }
    ((Udfps) baseClientMonitor).onPointerDown(pointerContext);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onPointerUp(long j, int i, final PointerContext pointerContext) {
    if (this.mFingerprintSensors.contains(i)) {
      semRequest(i, 22, 1, null, null);
      Sensor sensor = (Sensor) this.mFingerprintSensors.get(i);
      if (sensor.getSensorProperties().sensorType == 3) {
        sensor.generateEvent(70001);
      }
      ((Sensor) this.mFingerprintSensors.get(i))
          .getScheduler()
          .getCurrentClientIfMatches(
              j,
              new Consumer() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda21
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  FingerprintProvider.this.lambda$onPointerUp$18(
                      pointerContext, (BaseClientMonitor) obj);
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onPointerUp$18(
      PointerContext pointerContext, BaseClientMonitor baseClientMonitor) {
    if (!(baseClientMonitor instanceof Udfps)) {
      Slog.e(getTag(), "onPointerUp received during client: " + baseClientMonitor);
      return;
    }
    ((Udfps) baseClientMonitor).onPointerUp(pointerContext);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onUiReady(long j, int i) {
    ((Sensor) this.mFingerprintSensors.get(i))
        .getScheduler()
        .getCurrentClientIfMatches(
            j,
            new Consumer() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda7
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                FingerprintProvider.this.lambda$onUiReady$19((BaseClientMonitor) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onUiReady$19(BaseClientMonitor baseClientMonitor) {
    if (!(baseClientMonitor instanceof Udfps)) {
      Slog.e(getTag(), "onUiReady received during client: " + baseClientMonitor);
      return;
    }
    ((Udfps) baseClientMonitor).onUiReady();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onPowerPressed() {
    IBinder.DeathRecipient currentClient;
    for (int i = 0;
        i < this.mFingerprintSensors.size()
            && (currentClient =
                    ((Sensor) this.mFingerprintSensors.valueAt(i))
                        .getScheduler()
                        .getCurrentClient())
                != null;
        i++) {
      if (currentClient instanceof PowerPressHandler) {
        ((PowerPressHandler) currentClient).onPowerPressed();
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpProtoState(int i, ProtoOutputStream protoOutputStream, boolean z) {
    if (this.mFingerprintSensors.contains(i)) {
      ((Sensor) this.mFingerprintSensors.get(i)).dumpProtoState(i, protoOutputStream, z);
    }
  }

  @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
  public void dumpInternal(int i, PrintWriter printWriter) {
    PerformanceTracker instanceForSensorId = PerformanceTracker.getInstanceForSensorId(i);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("service", getTag());
      JSONArray jSONArray = new JSONArray();
      Iterator it = UserManager.get(this.mContext).getUsers().iterator();
      while (it.hasNext()) {
        int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
        int size =
            FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, identifier).size();
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
      Slog.e(getTag(), "dump formatting failure", e);
    }
    printWriter.println(jSONObject);
    printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.getHALDeathCount());
    printWriter.println("---AuthSessionCoordinator logs begin---");
    printWriter.println(this.mBiometricContext.getAuthSessionCoordinator());
    printWriter.println("---AuthSessionCoordinator logs end  ---");
    ((Sensor) this.mFingerprintSensors.get(i)).getScheduler().dump(printWriter);
    this.mProviderEx.dumpInternal(i, printWriter);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public ITestSession createTestSession(
      int i, ITestSessionCallback iTestSessionCallback, String str) {
    return ((Sensor) this.mFingerprintSensors.get(i))
        .createTestSession(iTestSessionCallback, this.mBiometricStateCallback);
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {
    Slog.e(getTag(), "HAL died");
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda13
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$binderDied$20();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$binderDied$20() {
    this.mDaemon = null;
    for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
      Sensor sensor = (Sensor) this.mFingerprintSensors.valueAt(i);
      PerformanceTracker.getInstanceForSensorId(this.mFingerprintSensors.keyAt(i))
          .incrementHALDeathCount();
      sensor.onBinderDied();
    }
    this.mSehFingerprint = null;
    this.mIsHalStarted = false;
    dispatchHalStopped();
  }

  public void setTestHalEnabled(boolean z) {
    this.mTestHalEnabled = z;
  }

  public final List getWorkaroundSensorProps(Context context) {
    SensorLocationInternal parseSensorLocation;
    ArrayList arrayList = new ArrayList();
    TypedArray obtainTypedArray = context.getResources().obtainTypedArray(17236305);
    for (int i = 0; i < obtainTypedArray.length(); i++) {
      int resourceId = obtainTypedArray.getResourceId(i, -1);
      if (resourceId > 0
          && (parseSensorLocation =
                  parseSensorLocation(context.getResources().obtainTypedArray(resourceId)))
              != null) {
        arrayList.add(parseSensorLocation);
      }
    }
    obtainTypedArray.recycle();
    return arrayList;
  }

  public final SensorLocationInternal parseSensorLocation(TypedArray typedArray) {
    if (typedArray == null) {
      return null;
    }
    try {
      return new SensorLocationInternal(
          typedArray.getString(0),
          typedArray.getInt(1, 0),
          typedArray.getInt(2, 0),
          typedArray.getInt(3, 0));
    } catch (Exception e) {
      Slog.w(getTag(), "malformed sensor location", e);
      return null;
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void scheduleWatchdog(int i) {
    Slog.d(getTag(), "Starting watchdog for fingerprint");
    SemFpUserAwareScheduler scheduler = ((Sensor) this.mFingerprintSensors.get(i)).getScheduler();
    if (scheduler == null) {
      return;
    }
    scheduler.startWatchdog();
  }

  public synchronized ISehFingerprint getSehFingerprint() {
    SemTpaTestHal semTpaTestHal;
    if (this.mTpaHalModeEnabled && (semTpaTestHal = this.mTpaTestHal) != null) {
      return semTpaTestHal.getSehFingerprint();
    }
    if (this.mSehFingerprint == null) {
      try {
        IFingerprint halInstance = getHalInstance();
        if (halInstance == null) {
          return null;
        }
        this.mSehFingerprint =
            ISehFingerprint.Stub.asInterface(halInstance.asBinder().getExtension());
      } catch (Exception e) {
        Slog.w(getTag(), "getSehFingerprint: " + e.getMessage());
      }
    }
    return this.mSehFingerprint;
  }

  public TaskStackListener getTaskStackListener() {
    return this.mTaskStackListener;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onUserRemoved(int i) {
    for (int i2 = 0; i2 < this.mFingerprintSensors.size(); i2++) {
      scheduleRemoveAll(
          this.mFingerprintSensors.keyAt(i2),
          new Binder(),
          new FingerprintServiceReceiver(),
          i,
          getTag());
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semGetSecurityLevel() {
    return this.mProviderEx.getSecurityLevel(this.mFingerprintSensors.keyAt(0));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public BaseClientMonitor semGetCurrentClient() {
    if (this.mFingerprintSensors.size() > 0) {
      return ((Sensor) this.mFingerprintSensors.valueAt(0)).getScheduler().getCurrentClient();
    }
    return null;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public String semGetDaemonSdkVersion() {
    return this.mProviderEx.getDaemonSdkVersion(this.mFingerprintSensors.keyAt(0));
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public String semGetSensorInfo(int i, boolean z) {
    if (!z) {
      ((Sensor) this.mFingerprintSensors.get(i)).getScheduler().cancelInterruptableOperation();
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
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda19
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$semScheduleAuthenticate$21(
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
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
      IBinder iBinder,
      final long j,
      ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      long j2,
      boolean z,
      int i,
      int i2,
      boolean z2,
      Bundle bundle) {
    final int userId = fingerprintAuthenticateOptions.getUserId();
    final int sensorId = fingerprintAuthenticateOptions.getSensorId();
    FingerprintAuthenticationClient fingerprintAuthenticationClient =
        new FingerprintAuthenticationClient(
            this.mContext,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getLazySession(),
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
            Utils.isStrongBiometric(sensorId),
            this.mTaskStackListener,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getLockoutCache(),
            this.mUdfpsOverlayController,
            this.mSidefpsController,
            this.mUdfpsOverlay,
            z2,
            ((Sensor) this.mFingerprintSensors.get(sensorId)).getSensorProperties(),
            this.mHandler,
            Utils.getCurrentStrength(sensorId),
            SystemClock.elapsedRealtimeClock());
    fingerprintAuthenticationClient.setExtraAttribute(bundle);
    scheduleForSensor(
        sensorId,
        fingerprintAuthenticationClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.6
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientStarted(BaseClientMonitor baseClientMonitor) {
            FingerprintProvider.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            FingerprintProvider.this.mAuthSessionCoordinator.authStartedFor(userId, sensorId, j);
            FingerprintProvider.this.mCallbackCenter.onClientStarted(baseClientMonitor);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onBiometricAction(int i3) {
            FingerprintProvider.this.mBiometricStateCallback.onBiometricAction(i3);
            FingerprintProvider.this.mCallbackCenter.onBiometricAction(i3);
          }

          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z3) {
            FingerprintProvider.this.mBiometricStateCallback.onClientFinished(
                baseClientMonitor, z3);
            FingerprintProvider.this.mAuthSessionCoordinator.authEndedFor(
                userId, Utils.getCurrentStrength(sensorId), sensorId, j, z3);
            FingerprintProvider.this.mCallbackCenter.onClientFinished(baseClientMonitor, z3);
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semScheduleSensorTest(
      final int i,
      final IBinder iBinder,
      int i2,
      int i3,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda16
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$semScheduleSensorTest$22(
                iBinder, clientMonitorCallbackConverter, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semScheduleSensorTest$22(
      IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i) {
    SemFpSensorTestClient semFpSensorTestClient =
        new SemFpSensorTestClient(
            this.mContext,
            this.mBiometricContext,
            new FingerprintProvider$$ExternalSyntheticLambda0(this),
            iBinder,
            clientMonitorCallbackConverter,
            i);
    if (((Sensor) this.mFingerprintSensors.get(i)) != null) {
      ((Sensor) this.mFingerprintSensors.get(i))
          .getScheduler()
          .scheduleClientMonitor(semFpSensorTestClient);
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semScheduleUpdateTrustApp(
      final int i,
      final String str,
      final ClientMonitorCallbackConverter clientMonitorCallbackConverter,
      final String str2) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda29
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$semScheduleUpdateTrustApp$23(
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
    scheduleForSensor(
        i,
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
                                      // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.7
          @Override // com.android.server.biometrics.sensors.SemUpdateTrustAppClient
          public int sehInstallTAStart() {
            return FingerprintProvider.this.handleRequestCommandWithoutScheduler(
                getSensorId(), 10001, 0, null, null);
          }

          @Override // com.android.server.biometrics.sensors.SemUpdateTrustAppClient
          public int sehInstallTAWrite(byte[] bArr) {
            return FingerprintProvider.this.handleRequestCommandWithoutScheduler(
                getSensorId(), 10001, 1, bArr, null);
          }

          @Override // com.android.server.biometrics.sensors.SemUpdateTrustAppClient
          public int sehInstallTAEnd(byte[] bArr) {
            return FingerprintProvider.this.handleRequestCommandWithoutScheduler(
                getSensorId(), 10001, 2, bArr, null);
          }
        },
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.8
          @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
          public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            if (z) {
              FingerprintProvider.this.mProviderEx.updateCacheDataOfHAL(
                  baseClientMonitor.getSensorId());
            }
          }
        });
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semOpenTzSession() {
    if (this.mFingerprintSensors.size() == 0) {
      if (Utils.DEBUG) {
        throw new IllegalStateException("Unable to use sensor");
      }
    } else {
      final int keyAt = this.mFingerprintSensors.keyAt(0);
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
              FingerprintProvider.this.lambda$semOpenTzSession$24(keyAt);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semOpenTzSession$24(int i) {
    scheduleForSensor(
        i,
        new SemFpBaseRequestClient.Builder(
                this.mContext,
                this.mBiometricContext,
                new FingerprintProvider$$ExternalSyntheticLambda0(this),
                i)
            .setCommand(2)
            .setUserId(((Sensor) this.mFingerprintSensors.get(i)).getCurrentSessionUserId())
            .setUseScheduler()
            .build());
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semProcessFidoCommand(final int i, int i2, byte[] bArr, byte[] bArr2) {
    if (!this.mFingerprintSensors.contains(i)) {
      if (!Utils.DEBUG) {
        return -1;
      }
      throw new IllegalStateException("Unable to use sensor: " + i);
    }
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final SemFpBaseRequestClient build =
        new SemFpBaseRequestClient.Builder(
                this.mContext,
                this.mBiometricContext,
                new FingerprintProvider$$ExternalSyntheticLambda0(this),
                i)
            .setCommand(9)
            .setParam(0)
            .setInputBuffer(bArr)
            .setOutputBuffer(bArr2)
            .setUserId(((Sensor) this.mFingerprintSensors.get(i)).getCurrentSessionUserId())
            .setUseScheduler()
            .build();
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda26
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$semProcessFidoCommand$25(i, build, countDownLatch);
          }
        });
    try {
      countDownLatch.await(2L, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Slog.w(getTag(), "Latch interrupted", e);
    }
    return build.getRequestResult();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$semProcessFidoCommand$25(
      int i, SemFpBaseRequestClient semFpBaseRequestClient, final CountDownLatch countDownLatch) {
    scheduleForSensor(
        i,
        semFpBaseRequestClient,
        new ClientMonitorCallback() { // from class:
                                      // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.9
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
  public void pauseEnroll(final int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda11
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$pauseEnroll$26(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$pauseEnroll$26(int i) {
    if (this.mFingerprintSensors.contains(i)) {
      IBinder.DeathRecipient currentClient =
          ((Sensor) this.mFingerprintSensors.get(i)).getScheduler().getCurrentClient();
      if (currentClient instanceof SemFpPauseResumeHandler) {
        ((SemFpPauseResumeHandler) currentClient).onPause();
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void resumeEnroll(final int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$resumeEnroll$27(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$resumeEnroll$27(int i) {
    if (this.mFingerprintSensors.contains(i)) {
      IBinder.DeathRecipient currentClient =
          ((Sensor) this.mFingerprintSensors.get(i)).getScheduler().getCurrentClient();
      if (currentClient instanceof SemFpPauseResumeHandler) {
        ((SemFpPauseResumeHandler) currentClient).onResume();
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semNotifyTspBlockStateToClient(boolean z) {
    for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
      Sensor sensor = (Sensor) this.mFingerprintSensors.valueAt(i);
      if (sensor.getSensorProperties().isAnyUdfpsType()) {
        IBinder.DeathRecipient currentClient = sensor.getScheduler().getCurrentClient();
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
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onOneHandModeEnabled() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda32
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$onOneHandModeEnabled$28();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onOneHandModeEnabled$28() {
    for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
      BaseClientMonitor currentClient =
          ((Sensor) this.mFingerprintSensors.valueAt(i)).getScheduler().getCurrentClient();
      if (Utils.DEBUG) {
        Slog.d(getTag(), "onOneHandModeEnabled: [" + i + "] = " + currentClient);
      }
      if (currentClient instanceof SemUdfpsConstraintStatusListener) {
        Slog.d(getTag(), "handle OneHandMode, client: " + Utils.getClientName(currentClient));
        ((SemUdfpsConstraintStatusListener) currentClient).onOneHandModeEnabled();
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void onWirelessPowerEnabled() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda10
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$onWirelessPowerEnabled$29();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Multi-variable type inference failed */
  public /* synthetic */ void lambda$onWirelessPowerEnabled$29() {
    for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
      BaseClientMonitor currentClient =
          ((Sensor) this.mFingerprintSensors.valueAt(i)).getScheduler().getCurrentClient();
      if (currentClient instanceof SemUdfpsConstraintStatusListener) {
        Slog.d(getTag(), "handle WirelessPower, client: " + Utils.getClientName(currentClient));
        ((SemUdfpsConstraintStatusListener) currentClient).onWirelessPowerEnabled();
      }
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
        Slog.w(this.getTag(), "semGetOpticalBrightnessConfigs: " + e.getMessage());
      }
    }
    return new String[0];
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public int semGetRemainingLockoutTime(int i) {
    try {
      return ((Sensor) this.mFingerprintSensors.valueAt(0)).getRemainingLockoutTime(i);
    } catch (ArrayIndexOutOfBoundsException e) {
      Slog.w(this.getTag(), "semGetRemainingLockoutTime: " + e.getMessage());
      return 0;
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semUpdateTpaAction() {
    SemTpaTestHal semTpaTestHal;
    if (!this.mTpaHalModeEnabled || (semTpaTestHal = this.mTpaTestHal) == null) {
      return;
    }
    semTpaTestHal.updateAction();
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semSetTpaRequestCommandAction(String[] strArr) {
    SemTpaTestHal semTpaTestHal;
    if (!this.mTpaHalModeEnabled || (semTpaTestHal = this.mTpaTestHal) == null) {
      return;
    }
    semTpaTestHal.setTpaRequestCommandAction(strArr);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semSetTpaHalEnabled(boolean z) {
    int i;
    if (this.mTpaHalModeEnabled == z) {
      return;
    }
    this.mTpaHalModeEnabled = z;
    for (int i2 = 0; i2 < this.mFingerprintSensors.size(); i2++) {
      Sensor sensor = (Sensor) this.mFingerprintSensors.valueAt(i2);
      sensor.setTestHalEnabled(true);
      sensor.setTestHalEnabled(false);
    }
    int intDb = Utils.getIntDb(this.mContext, "biometric_tpa_mode", true, 0);
    if (this.mTpaHalModeEnabled) {
      if (this.mTpaTestHal == null) {
        SemTpaTestHal semTpaTestHal = new SemTpaTestHal(this.mContext);
        this.mTpaTestHal = semTpaTestHal;
        semTpaTestHal.linkToDeath(this);
        this.mTpaTestHal.start();
      }
      i = intDb | 2;
    } else {
      SemTpaTestHal semTpaTestHal2 = this.mTpaTestHal;
      if (semTpaTestHal2 != null) {
        semTpaTestHal2.destroy();
        this.mTpaTestHal = null;
      }
      i = intDb & (-3);
    }
    Utils.putIntDb(this.mContext, "biometric_tpa_mode", true, i);
    for (int i3 = 0; i3 < this.mFingerprintSensors.size(); i3++) {
      scheduleInternalCleanup(
          this.mFingerprintSensors.keyAt(i3), ActivityManager.getCurrentUser(), null);
    }
  }

  public final int handleRequestCommandWithoutScheduler(
      int i, int i2, int i3, byte[] bArr, byte[] bArr2) {
    return new SemFpBaseRequestClient.Builder(
            this.mContext,
            this.mBiometricContext,
            new FingerprintProvider$$ExternalSyntheticLambda0(this),
            i)
        .setCommand(i2)
        .setParam(i3)
        .setInputBuffer(bArr)
        .setOutputBuffer(bArr2)
        .build()
        .startWithoutScheduler();
  }

  public SemFpHalCallbackEx getHalListener() {
    return this.mCallbackCenter;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
  public void semAddHalLifecycleListener(
      final SemFpHalLifecycleListener semFpHalLifecycleListener) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda20
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$semAddHalLifecycleListener$31(
                semFpHalLifecycleListener);
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
    if (getHalInstance() == null || !this.mIsHalStarted) {
      return;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda31
          @Override // java.lang.Runnable
          public final void run() {
            FingerprintProvider.this.lambda$semAddHalLifecycleListener$30(
                semFpHalLifecycleListener);
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
}
