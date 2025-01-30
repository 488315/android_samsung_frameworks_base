package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;
import android.widget.Toast;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class SemFingerprintServiceExtImpl
    implements SemFpHalLifecycleListener,
        SemFpAuthenticationListener,
        SemBiometricDisplayMonitor.Callback {
  public static final boolean DEBUG = Utils.DEBUG;
  SemFpAodController mAodController;
  public long mCaptureStartedTime;
  public final Context mContext;
  public SemBiometricDisplayBrightnessMonitor mDisplayBrightnessMonitor;
  public SemBiometricDisplayStateMonitor mDisplayStateMonitor;
  public final SemFpEnrollSessionMonitor mEnrollSessionMonitor;
  public SemFpProtectiveFilmGuide mFpProtectiveFilmGuide;
  public SemFpGestureManager mGestureManager;
  public final Handler mHandler;
  public Pair mIFAAFlag;
  public long mIconDrawnTime;
  public final Injector mInjector;
  public final AtomicBoolean mIsBouncer;
  public boolean mIsScreenOn;
  public int mLatestSensorStatus;
  public SemFpLocalHbmOpticalController mLocalHbmController;
  public final Object mLockForAodCtrl;
  BroadcastReceiver mPersistentBroadCastReceiver;
  public final FingerprintServiceRegistry mRegistry;
  public ISemBiometricSysUiDisplayBrightnessCallback mSysUiDisplayBrightnessCallback;
  public ISemBiometricSysUiDisplayStateCallback mSysUiDisplayStateCallback;

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalLifecycleListener
  public void onHalStarted(ServiceProvider serviceProvider) {}

  public class Injector {
    public int getUserOrWorkProfileId(Context context, int i) {
      return Utils.getUserOrWorkProfileId(context, i);
    }

    public SemUdfpsHelper getUdfpsHelper() {
      return SemUdfpsHelper.getInstance();
    }

    public SemUdfpsTspManager getUdfpsTspManager() {
      return SemUdfpsTspManager.get();
    }

    public SemBiometricSysUiManager getSysUiManager() {
      return SemBiometricSysUiManager.get();
    }

    public SemBioAnalyticsManager getBioAnalyticsManager() {
      return SemBioAnalyticsManager.getInstance();
    }

    public SemFpGestureManager createGestureManager(
        Context context, ServiceProvider serviceProvider) {
      return new SemFpGestureManager(context, serviceProvider);
    }

    public SemFpSpenConstraintHandler createSpenConstraintHandler(Context context, Pair pair) {
      return new SemFpSpenConstraintHandler(context, pair);
    }

    public SemFpProtectiveFilmGuide createProtectiveFilmGuide(
        Context context, ServiceProvider serviceProvider) {
      return new SemFpProtectiveFilmGuide(context, serviceProvider);
    }

    public SemFpOneHandModeMonitor createOneHandModeMonitor(
        Context context, ServiceProvider serviceProvider) {
      return new SemFpOneHandModeMonitor(context, serviceProvider);
    }

    public SemBiometricDisplayStateMonitor createDisplayStateMonitor(Context context) {
      return new SemBiometricDisplayStateMonitor(context, SemFpMainThread.get().getHandler());
    }

    public SemBiometricDisplayBrightnessMonitor createDisplayBrightnessMonitor(Context context) {
      return new SemBiometricDisplayBrightnessMonitor(context, SemFpMainThread.get().getHandler());
    }

    public SemFpEnrollSessionMonitor createEnrollSessionMonitor(
        Context context, SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor) {
      return new SemFpEnrollSessionMonitor(semBiometricDisplayStateMonitor);
    }

    public SemFpLocalHbmOpticalController createLocalHbmController(
        SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor) {
      return new SemFpLocalHbmOpticalController(
          SemFpMainThread.get().getHandler(), semBiometricDisplayStateMonitor);
    }
  }

  public SemFingerprintServiceExtImpl(
      Context context, FingerprintServiceRegistry fingerprintServiceRegistry) {
    this(context, fingerprintServiceRegistry, new Injector());
  }

  public SemFingerprintServiceExtImpl(
      Context context, FingerprintServiceRegistry fingerprintServiceRegistry, Injector injector) {
    this.mIsBouncer = new AtomicBoolean(false);
    this.mLockForAodCtrl = new Object();
    this.mContext = context;
    this.mHandler = new Handler(SemFpMainThread.get().getLooper());
    this.mRegistry = fingerprintServiceRegistry;
    this.mInjector = injector;
    this.mDisplayStateMonitor = injector.createDisplayStateMonitor(context);
    if (SemBiometricFeature.FP_FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS) {
      this.mDisplayBrightnessMonitor = injector.createDisplayBrightnessMonitor(context);
    }
    this.mEnrollSessionMonitor =
        injector.createEnrollSessionMonitor(context, this.mDisplayStateMonitor);
    if (SemBiometricFeature.FP_FEATURE_LOCAL_HBM) {
      this.mLocalHbmController = injector.createLocalHbmController(this.mDisplayStateMonitor);
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
  public void onAuthenticationAcquire(int i, int i2, int i3, int i4) {
    if (i3 == 6 && i4 == 10002) {
      this.mCaptureStartedTime = SystemClock.elapsedRealtime();
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
  public void onStartDisplayState(int i, int i2, int i3) {
    ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback =
        this.mSysUiDisplayStateCallback;
    if (iSemBiometricSysUiDisplayStateCallback != null) {
      try {
        iSemBiometricSysUiDisplayStateCallback.onStart(i, i2, i3);
      } catch (RemoteException unused) {
        Slog.w("FingerprintService.Ext", "onStartDisplayState: failed to invoke onStartCallback");
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
  public void onFinishDisplayState(int i, int i2, int i3) {
    ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback =
        this.mSysUiDisplayStateCallback;
    if (iSemBiometricSysUiDisplayStateCallback != null) {
      try {
        iSemBiometricSysUiDisplayStateCallback.onFinish(i, i2, i3);
      } catch (RemoteException unused) {
        Slog.w("FingerprintService.Ext", "onFinishDisplayState: failed to invoke onFinishCallback");
      }
    }
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
  public void onBrightnessChanged(float f) {
    ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback =
        this.mSysUiDisplayBrightnessCallback;
    if (iSemBiometricSysUiDisplayBrightnessCallback != null) {
      try {
        iSemBiometricSysUiDisplayBrightnessCallback.onBrightnessChanged(f);
      } catch (RemoteException unused) {
        Slog.w(
            "FingerprintService.Ext",
            "onBrightnessChanged: failed to invoke onBrightnessChangedCallback");
      }
    }
  }

  public Pair canUseFingerprint(boolean z, boolean z2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (z && !z2) {
      try {
        ServiceProvider serviceProvider = getServiceProvider();
        if (serviceProvider != null) {
          BaseClientMonitor semGetCurrentClient = serviceProvider.semGetCurrentClient();
          if (semGetCurrentClient instanceof AuthenticationClient) {
            AuthenticationClient authenticationClient = (AuthenticationClient) semGetCurrentClient;
            if (authenticationClient.isBiometricPrompt()
                && authenticationClient.semHasPromptPrivilegedAttr(8)
                && Utils.isBackground(authenticationClient.getOwnerString())) {
              return new Pair(5, 0);
            }
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      if (Utils.isOneHandMode(this.mContext)) {
        if (z && !z2) {
          Toast.makeText(
                  this.mContext,
                  this.mHandler.getLooper(),
                  this.mContext.getResources().getText(17042713),
                  0)
              .show();
        }
        return new Pair(8, 5001);
      }
      if (Utils.isSmartViewDisplayWithFitToAspectRatio(this.mContext)) {
        if (z && !z2) {
          Context context = this.mContext;
          Toast.makeText(
                  this.mContext,
                  this.mHandler.getLooper(),
                  context.getString(17042714, context.getString(17042711)),
                  0)
              .show();
        }
        return new Pair(8, 5003);
      }
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return new Pair(0, 0);
  }

  public void onAllAuthenticatorsRegistered() {
    if (DEBUG) {
      Slog.i("FingerprintService.Ext", "onAllAuthenticatorsRegistered: ");
    }
    for (ServiceProvider serviceProvider : this.mRegistry.getProviders()) {
      serviceProvider.semAddHalLifecycleListener(this);
      serviceProvider.semAddAuthenticationListener(this);
      this.mEnrollSessionMonitor.onProviderRegistered(serviceProvider);
    }
  }

  public void onBootPhase(int i) {
    if (DEBUG) {
      Slog.i("FingerprintService.Ext", "onBootPhase: " + i);
    }
    if (i == 550) {
      onBootActivityManagerReady();
    } else if (i == 600) {
      onBootThirdPartyAppsCanStart();
    } else if (i == 1000) {
      onBootBootCompleted();
    }
  }

  public final void onBootActivityManagerReady() {
    SemBiometricDisplayBrightnessMonitor semBiometricDisplayBrightnessMonitor;
    this.mDisplayStateMonitor.start();
    this.mDisplayStateMonitor.registerCallback(this);
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
        && (semBiometricDisplayBrightnessMonitor = this.mDisplayBrightnessMonitor) != null) {
      semBiometricDisplayBrightnessMonitor.start();
      this.mDisplayBrightnessMonitor.registerCallback(this);
    }
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      this.mInjector.getUdfpsTspManager().onBootActivityManagerReady(this.mContext);
      this.mInjector.getUdfpsHelper().onBootActivityManagerReady(this.mContext);
    }
  }

  public final void onBootThirdPartyAppsCanStart() {
    registerPersistentBroadcast();
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      startGestureManagerIfNeeded((ServiceProvider) singleProvider.second);
      startSpenConstrainHandlerIfNeeded(singleProvider);
      startWirelessPowerMonitorIfNeeded((ServiceProvider) singleProvider.second);
      startOneHandModeMonitorIfNeeded((ServiceProvider) singleProvider.second);
      startProtectiveFilmGuideIfNeeded((ServiceProvider) singleProvider.second);
      startScreenNotifierIfNeeded((ServiceProvider) singleProvider.second);
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
        this.mInjector
            .getUdfpsHelper()
            .getOpticalSensorHelper()
            .onBootThirdPartyAppsCanStart(
                this.mContext,
                ((ServiceProvider) singleProvider.second)
                    .semGetOpticalBrightnessConfigs(((Integer) singleProvider.first).intValue()));
      }
      new SemFpResetLockoutDispatcher(singleProvider).start();
    }
  }

  public final void onBootBootCompleted() {
    Iterator it = this.mRegistry.getAllProperties().iterator();
    while (it.hasNext()) {
      FingerprintUtils.getInstance(((FingerprintSensorPropertiesInternal) it.next()).sensorId)
          .onBootComplete();
    }
    BiometricContext.getInstance(this.mContext)
        .ensureBiometricContextListener(this.mDisplayStateMonitor);
  }

  public final void startSpenConstrainHandlerIfNeeded(Pair pair) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      if (SemBiometricFeature.FP_FEATURE_SENSOR_LIMITATION_SPEN_CHARGER
          || SemBiometricFeature.FP_FEATURE_TSP_BLOCK) {
        this.mInjector.createSpenConstraintHandler(this.mContext, pair).start();
      }
    }
  }

  public final void startWirelessPowerMonitorIfNeeded(ServiceProvider serviceProvider) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_LIMITATION_WIRELESS_CHARGER) {
      new SemFpWirelessPowerMonitor(this.mContext, serviceProvider).start();
    }
  }

  public final void startOneHandModeMonitorIfNeeded(ServiceProvider serviceProvider) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      this.mInjector.createOneHandModeMonitor(this.mContext, serviceProvider).start();
    }
  }

  public final void startGestureManagerIfNeeded(ServiceProvider serviceProvider) {
    if (SemBiometricFeature.FP_FEATURE_GESTURE_MODE) {
      SemFpGestureManager createGestureManager =
          this.mInjector.createGestureManager(this.mContext, serviceProvider);
      this.mGestureManager = createGestureManager;
      createGestureManager.start();
    }
  }

  public final void startProtectiveFilmGuideIfNeeded(ServiceProvider serviceProvider) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
      SemFpProtectiveFilmGuide createProtectiveFilmGuide =
          this.mInjector.createProtectiveFilmGuide(this.mContext, serviceProvider);
      this.mFpProtectiveFilmGuide = createProtectiveFilmGuide;
      createProtectiveFilmGuide.startMonitoring();
      this.mFpProtectiveFilmGuide.updateGuideStatus(0);
    }
  }

  public final void startScreenNotifierIfNeeded(ServiceProvider serviceProvider) {
    new SemFpScreenStatusNotifier(this.mContext, serviceProvider).start();
  }

  public final ServiceProvider getServiceProvider() {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      return (ServiceProvider) singleProvider.second;
    }
    return null;
  }

  public final Pair getSingleProvider() {
    return this.mRegistry.getSingleProvider();
  }

  public final void registerPersistentBroadcast() {
    if (this.mPersistentBroadCastReceiver != null) {
      return;
    }
    this.mPersistentBroadCastReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("FingerprintService.Ext", "onReceive : " + action);
            if ("android.intent.action.USER_REMOVED".equals(action)) {
              SemFingerprintServiceExtImpl.this.handleActionUserRemoved(intent);
              return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)
                && !SemFingerprintServiceExtImpl.this.mDisplayStateMonitor.isInteractive()) {
              SemFingerprintServiceExtImpl.this.handleActionScreenOff();
            } else if ("android.intent.action.SCREEN_ON".equals(action)
                && SemFingerprintServiceExtImpl.this.mDisplayStateMonitor.isInteractive()) {
              SemFingerprintServiceExtImpl.this.handleActionScreenOn();
            }
          }
        };
    registerIntentForUserRemoved();
    registerIntentForScreenOnOffAndTspClientIfNeeded();
  }

  public final void handleActionUserRemoved(Intent intent) {
    int userOrWorkProfileId;
    SemFpProtectiveFilmGuide semFpProtectiveFilmGuide;
    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
    Slog.i("FingerprintService.Ext", "ACTION_USER_REMOVED: " + intExtra);
    if (intExtra == -10000
        || (userOrWorkProfileId = this.mInjector.getUserOrWorkProfileId(this.mContext, intExtra))
            == 0) {
      return;
    }
    Iterator it = this.mRegistry.getProviders().iterator();
    while (it.hasNext()) {
      ((ServiceProvider) it.next()).onUserRemoved(userOrWorkProfileId);
    }
    if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC
        || (semFpProtectiveFilmGuide = this.mFpProtectiveFilmGuide) == null) {
      return;
    }
    semFpProtectiveFilmGuide.onUserRemoved(userOrWorkProfileId);
  }

  public final void handleActionScreenOff() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      this.mInjector.getUdfpsTspManager().screenOff();
    }
  }

  public final void handleActionScreenOn() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      this.mInjector.getUdfpsTspManager().screenOn();
      unregisterAodController();
    }
  }

  public final void registerIntentForUserRemoved() {
    Utils.registerBroadcastAsUser(
        this.mContext,
        this.mPersistentBroadCastReceiver,
        new IntentFilter("android.intent.action.USER_REMOVED"),
        UserHandle.ALL,
        this.mHandler);
  }

  public final void registerIntentForScreenOnOffAndTspClientIfNeeded() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC && !Utils.isFactoryBinary()) {
      this.mInjector.getUdfpsTspManager().enable(0);
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.SCREEN_ON");
      intentFilter.addAction("android.intent.action.SCREEN_OFF");
      Utils.registerBroadcastAsUser(
          this.mContext,
          this.mPersistentBroadCastReceiver,
          intentFilter,
          UserHandle.ALL,
          this.mHandler);
    }
  }

  public boolean hasFeature(int i) {
    if (i == 1) {
      return SemBiometricFeature.FP_FEATURE_GESTURE_MODE;
    }
    if (i == 2) {
      return SemBiometricFeature.FP_FEATURE_SWIPE_ENROLL;
    }
    if (i != 3) {
      return false;
    }
    return SemBiometricFeature.FP_FEATURE_WOF_OPTION_DEFAULT_OFF;
  }

  public int runSensorTest(
      int i,
      IBinder iBinder,
      int i2,
      int i3,
      ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider == null) {
      return -2;
    }
    serviceProvider.semScheduleSensorTest(
        i, iBinder, i2, i3, new ClientMonitorCallbackConverter(iSemFingerprintRequestCallback));
    return 0;
  }

  public int getSensorTestResult(byte[] bArr) {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      return ((ServiceProvider) singleProvider.second)
          .semRequest(((Integer) singleProvider.first).intValue(), 19, 0, null, bArr);
    }
    return -2;
  }

  public void setScreenStatusFromKeyguard(final boolean z) {
    Slog.i("FingerprintService.Ext", "setScreenStatusFromKeyguard: " + z);
    SemBiometricSysUiManager.get().sendCommand(115, z ? 1 : 0, null);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SemFingerprintServiceExtImpl.this.lambda$setScreenStatusFromKeyguard$0(z);
          }
        });
    if (z) {
      unregisterAodController();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setScreenStatusFromKeyguard$0(boolean z) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC && !this.mIsScreenOn && z) {
      handleQcomForceQDB();
    }
    this.mIsScreenOn = z;
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      if (z) {
        this.mInjector.getUdfpsTspManager().screenOn();
      } else {
        this.mInjector.getUdfpsTspManager().screenOff();
      }
    }
  }

  public void forceCBGE() {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      ((ServiceProvider) singleProvider.second)
          .semRequest(((Integer) singleProvider.first).intValue(), 21);
    }
  }

  public int requestProcessFIDO(int i, byte[] bArr, byte[] bArr2) {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      return ((ServiceProvider) singleProvider.second)
          .semProcessFidoCommand(((Integer) singleProvider.first).intValue(), i, bArr, bArr2);
    }
    return -2;
  }

  public int getRemainingLockoutTime(int i) {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null) {
      return serviceProvider.semGetRemainingLockoutTime(i);
    }
    return 0;
  }

  public boolean canChangeDeviceColorMode() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      return this.mInjector.getUdfpsHelper().getOpticalSensorHelper().canChangeDeviceColorMode();
    }
    return true;
  }

  public int getSecurityLevel() {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null) {
      return serviceProvider.semGetSecurityLevel();
    }
    return 1;
  }

  public String[] getUserIdList() {
    byte[] bArr;
    int semRequest;
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider == null
        || (semRequest =
                ((ServiceProvider) singleProvider.second)
                    .semRequest(
                        ((Integer) singleProvider.first).intValue(),
                        12,
                        0,
                        null,
                        (bArr = new byte[256])))
            <= 0) {
      return null;
    }
    return new String(bArr, 0, semRequest, StandardCharsets.UTF_8)
        .split(XmlUtils.STRING_ARRAY_SEPARATOR);
  }

  public String getDaemonVersion() {
    ServiceProvider serviceProvider = getServiceProvider();
    return serviceProvider != null ? serviceProvider.semGetDaemonSdkVersion() : "";
  }

  public String getSensorInfo(int i) {
    ServiceProvider serviceProvider = getServiceProvider();
    return serviceProvider != null ? serviceProvider.semGetSensorInfo(i, false) : "";
  }

  public void registerAodController(ISemFingerprintAodController iSemFingerprintAodController) {
    Slog.d("FingerprintService.Ext", "registerAodController: " + Binder.getCallingPid());
    synchronized (this.mLockForAodCtrl) {
      if (this.mAodController == null) {
        this.mAodController = new SemFpAodController();
      }
      this.mAodController.registerController(iSemFingerprintAodController);
    }
  }

  public void unregisterAodController() {
    Slog.d("FingerprintService.Ext", "unregisterAodController: " + Binder.getCallingPid());
    synchronized (this.mLockForAodCtrl) {
      SemFpAodController semFpAodController = this.mAodController;
      if (semFpAodController != null) {
        semFpAodController.unregisterController();
        this.mAodController = null;
      }
    }
  }

  public String getTrustAppVersion() {
    String str;
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      byte[] bArr = new byte[50];
      int semRequest =
          ((ServiceProvider) singleProvider.second)
              .semRequest(((Integer) singleProvider.first).intValue(), 10000, 0, null, bArr);
      if (semRequest > 0) {
        str = Utils.byteArrayToString(bArr, semRequest);
        if (Utils.DEBUG) {
          Slog.d("FingerprintService.Ext", "TrustApp Version: " + str);
        }
        return TextUtils.emptyIfNull(str);
      }
    }
    str = null;
    return TextUtils.emptyIfNull(str);
  }

  public void updateTrustApp(
      String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) {
    if (DEBUG) {
      Slog.v("FingerprintService.Ext", "updateTrustApp: [" + str + "]");
    }
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider != null) {
      ((ServiceProvider) singleProvider.second)
          .semScheduleUpdateTrustApp(
              ((Integer) singleProvider.first).intValue(),
              str,
              new ClientMonitorCallbackConverter(iSemFingerprintRequestCallback),
              str2);
    }
  }

  public int startRequest(
      IBinder iBinder,
      int i,
      byte[] bArr,
      byte[] bArr2,
      int i2,
      int i3,
      ISemFingerprintRequestCallback iSemFingerprintRequestCallback,
      String str,
      int i4) {
    int i5;
    long j;
    long elapsedRealtime;
    Slog.d(
        "FingerprintService.Ext",
        "startRequest(" + i + ") called from " + str + ", " + i2 + ", " + i3);
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider == null) {
      Slog.w("FingerprintService.Ext", "startRequest: No provider");
      return -1;
    }
    if (i == 1010) {
      if (!(((ServiceProvider) singleProvider.second).semGetCurrentClient()
          instanceof AuthenticationClient)) {
        return 0;
      }
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
        j = this.mCaptureStartedTime + 10000;
        elapsedRealtime = SystemClock.elapsedRealtime();
      } else if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
        j = this.mIconDrawnTime + 10000;
        elapsedRealtime = SystemClock.elapsedRealtime();
      } else {
        i5 = 0;
        return Math.max(i5, 0);
      }
      i5 = (int) (j - elapsedRealtime);
      return Math.max(i5, 0);
    }
    Utils.checkPermission(this.mContext, "android.permission.MANAGE_FINGERPRINT");
    return ((ServiceProvider) singleProvider.second)
        .semRequest(((Integer) singleProvider.first).intValue(), i, i2, bArr, bArr2);
  }

  public Rect getUdfpsSensorArea(int i, int i2, Point point) {
    if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      return new Rect();
    }
    if (i == 0) {
      return this.mInjector.getUdfpsHelper().getFodSensorAreaRectForKeyguard(this.mContext);
    }
    return this.mInjector.getUdfpsHelper().getFodSensorAreaRect(this.mContext, i2, point);
  }

  public int getSensorAreaMarginFromBottomForFod() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE
        && (getCurrentClient() instanceof AcquisitionClient)) {
      return this.mInjector.getUdfpsHelper().getSensorAreaMarginFromBottomForFod(this.mContext);
    }
    return 0;
  }

  public void moveSensorIconInDisplay() {
    BaseClientMonitor currentClient = getCurrentClient();
    if ((currentClient instanceof AuthenticationClient)
        && ((AuthenticationClient) currentClient).isKeyguard()) {
      this.mInjector
          .getSysUiManager()
          .sendCommandIfSessionExist(
              114,
              0,
              this.mInjector.getUdfpsHelper().getSensorIconRandomPos(this.mContext, new Bundle()));
      this.mInjector.getBioAnalyticsManager().fpInsertLog("FPMV", null, -1, 2);
    }
  }

  public void setBouncerScreen(int i) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      this.mIsBouncer.set(i == 1);
      this.mInjector.getSysUiManager().sendCommandIfSessionExist(117, i, null);
    }
  }

  public IBinder addMaskView(final IBinder iBinder, final String str) {
    Slog.i("FingerprintService.Ext", "addMaskView: " + str);
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      SemBiometricSysUiManager.get().cancelUnbind();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
              SemFingerprintServiceExtImpl.this.lambda$addMaskView$1(iBinder, str);
            }
          });
    }
    return iBinder;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addMaskView$1(IBinder iBinder, String str) {
    this.mInjector.getUdfpsHelper().getOpticalSensorHelper().addMaskView(iBinder, str);
  }

  public int removeMaskView(final IBinder iBinder, String str) {
    Slog.i("FingerprintService.Ext", "removeMaskView: " + str);
    if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      return 0;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            SemFingerprintServiceExtImpl.this.lambda$removeMaskView$2(iBinder);
          }
        });
    return 0;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeMaskView$2(IBinder iBinder) {
    this.mInjector.getUdfpsHelper().getOpticalSensorHelper().removeMaskView(iBinder);
  }

  public boolean isTemplateDbCorrupted() {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    return singleProvider != null
        && ((ServiceProvider) singleProvider.second)
                .semRequest(((Integer) singleProvider.first).intValue(), 11, 0, null, new byte[5])
            == -1;
  }

  public int getSensorStatus() {
    if (SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
      return 100040;
    }
    Pair singleProvider = this.mRegistry.getSingleProvider();
    int semRequest =
        singleProvider != null
            ? ((ServiceProvider) singleProvider.second)
                .semRequest(((Integer) singleProvider.first).intValue(), 6)
            : -2;
    this.mLatestSensorStatus = semRequest;
    this.mInjector.getBioAnalyticsManager().fpInsertLogSensorStatus(semRequest);
    return semRequest;
  }

  public boolean openTrustAppSession() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              SemFingerprintServiceExtImpl.this.lambda$openTrustAppSession$3();
            }
          });
    }
    for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal :
        this.mRegistry.getAllProperties()) {
      if (this.mEnrollSessionMonitor.isEnrollSession(
          fingerprintSensorPropertiesInternal.sensorId)) {
        this.mEnrollSessionMonitor.revokeChallenge(fingerprintSensorPropertiesInternal.sensorId);
      }
    }
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider == null) {
      return false;
    }
    serviceProvider.semOpenTzSession();
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$openTrustAppSession$3() {
    this.mInjector.getSysUiManager().sendCommand(118, 0, null);
  }

  public boolean pauseEnroll(int i) {
    ServiceProvider serviceProvider = (ServiceProvider) this.mRegistry.getProviderForSensor(i);
    if (serviceProvider == null) {
      return true;
    }
    serviceProvider.pauseEnroll(i);
    return true;
  }

  public boolean resumeEnroll(int i) {
    ServiceProvider serviceProvider = (ServiceProvider) this.mRegistry.getProviderForSensor(i);
    if (serviceProvider == null) {
      return true;
    }
    serviceProvider.resumeEnroll(i);
    return true;
  }

  public void setFodStrictMode(boolean z) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      Slog.d("FingerprintService.Ext", "setFodStrictMode: " + z);
      this.mInjector.getUdfpsTspManager().setStrictMode(z);
    }
  }

  public int setCalibrationMode(final IBinder iBinder, final int i, final String str) {
    Slog.d("FingerprintService.Ext", "setCalibrationMode: " + i + " from " + str);
    if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      return 0;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            SemFingerprintServiceExtImpl.this.lambda$setCalibrationMode$4(iBinder, str, i);
          }
        });
    return 0;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setCalibrationMode$4(IBinder iBinder, String str, int i) {
    this.mInjector
        .getUdfpsHelper()
        .getOpticalSensorHelper()
        .setOpticalCalibrationMode(iBinder, str, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerDisplayStateCallback$5(
      ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) {
    this.mSysUiDisplayStateCallback = iSemBiometricSysUiDisplayStateCallback;
  }

  public int registerDisplayStateCallback(
      final ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) {
    SemFpMainThread.get()
        .getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                SemFingerprintServiceExtImpl.this.lambda$registerDisplayStateCallback$5(
                    iSemBiometricSysUiDisplayStateCallback);
              }
            });
    SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor = this.mDisplayStateMonitor;
    if (semBiometricDisplayStateMonitor != null) {
      return semBiometricDisplayStateMonitor.getLogicalDisplayState();
    }
    return 0;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterDisplayStateCallback$6() {
    this.mSysUiDisplayStateCallback = null;
  }

  public void unregisterDisplayStateCallback() {
    SemFpMainThread.get()
        .getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                SemFingerprintServiceExtImpl.this.lambda$unregisterDisplayStateCallback$6();
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerDisplayBrightnessCallback$7(
      ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) {
    this.mSysUiDisplayBrightnessCallback = iSemBiometricSysUiDisplayBrightnessCallback;
  }

  public int registerDisplayBrightnessCallback(
      final ISemBiometricSysUiDisplayBrightnessCallback
          iSemBiometricSysUiDisplayBrightnessCallback) {
    SemFpMainThread.get()
        .getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda4
              @Override // java.lang.Runnable
              public final void run() {
                SemFingerprintServiceExtImpl.this.lambda$registerDisplayBrightnessCallback$7(
                    iSemBiometricSysUiDisplayBrightnessCallback);
              }
            });
    SemBiometricDisplayBrightnessMonitor semBiometricDisplayBrightnessMonitor =
        this.mDisplayBrightnessMonitor;
    if (semBiometricDisplayBrightnessMonitor != null) {
      return semBiometricDisplayBrightnessMonitor.getBrightness();
    }
    return 0;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterDisplayBrightnessCallback$8() {
    this.mSysUiDisplayBrightnessCallback = null;
  }

  public void unregisterDisplayBrightnessCallback() {
    SemFpMainThread.get()
        .getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                SemFingerprintServiceExtImpl.this.lambda$unregisterDisplayBrightnessCallback$8();
              }
            });
  }

  /* JADX WARN: Multi-variable type inference failed */
  public int handleBioSysUiRequest(int i, int i2, long j, String str) {
    Slog.d(
        "FingerprintService.Ext",
        "handleBioSysUiRequest: " + i + ", " + i2 + ", " + j + ", " + str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    int i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    i3 = 0;
    try {
      if (i != 100) {
        boolean z = true;
        switch (i) {
          case 1:
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
              SemUdfpsOpticalHelper opticalSensorHelper =
                  this.mInjector.getUdfpsHelper().getOpticalSensorHelper();
              if (i2 != 1) {
                z = false;
              }
              opticalSensorHelper.setDisplayStateLimit(z);
              break;
            }
            break;
          case 2:
            if (i2 != 1) {
              z = false;
            }
            requestDozeMode(z);
            break;
          case 3:
            if (i2 != 1) {
              z = false;
            }
            requestDozeHLPM(z);
            break;
          case 4:
            handleBioSysTspControl(i2, str);
            break;
          case 5:
            handleBioSysHwLightSourceControl(i2);
            break;
          case 6:
            i3 = this.mIsBouncer.get();
            break;
          case 7:
            handleBioSysOpticalControl(i2, j);
            break;
          case 8:
            handleBioSysDvfsControl(i2, j);
            break;
          case 9:
            handleTouchEvent(i2, j);
            break;
          case 10:
            requestAodGone();
            break;
          case 11:
            this.mIconDrawnTime = j;
            break;
          case 12:
            SemBiometricSysUiManager sysUiManager = this.mInjector.getSysUiManager();
            if (i2 != 1) {
              z = false;
            }
            sysUiManager.keepBindService(z);
            break;
          case 13:
            SemFpLocalHbmOpticalController semFpLocalHbmOpticalController =
                this.mLocalHbmController;
            if (semFpLocalHbmOpticalController != null) {
              semFpLocalHbmOpticalController.handleLocalHbm(i2, false);
              break;
            }
            break;
          default:
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i3;
        }
      } else {
        handleQcomForceQDB();
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return i3;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final void requestDozeMode(boolean z) {
    synchronized (this.mLockForAodCtrl) {
      if (this.mAodController == null) {
        this.mAodController = new SemFpAodController();
      }
      if (z) {
        this.mAodController.turnOnDozeMode();
      } else {
        this.mAodController.turnOffDozeMode();
      }
    }
  }

  public final void requestDozeHLPM(boolean z) {
    synchronized (this.mLockForAodCtrl) {
      if (this.mAodController == null) {
        this.mAodController = new SemFpAodController();
      }
      if (z) {
        this.mAodController.turnOnDozeHlpmMode();
      } else {
        this.mAodController.turnOffDozeHlpmMode();
      }
    }
  }

  public final void requestAodGone() {
    synchronized (this.mLockForAodCtrl) {
      SemFpAodController semFpAodController = this.mAodController;
      if (semFpAodController != null) {
        semFpAodController.hideAodScreen();
      }
    }
  }

  public final void handleBioSysTspControl(int i, String str) {
    if (i != 1) {
      if (i == 2) {
        this.mInjector.getUdfpsTspManager().setHalfMode(true);
      }
    } else {
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC && TextUtils.equals(str, "BSS")) {
        this.mInjector.getUdfpsTspManager().doNotEnterHalfMode();
      }
      this.mInjector.getUdfpsTspManager().setHalfMode(false);
    }
  }

  public final void handleBioSysHwLightSourceControl(int i) {
    if (SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
      if (i == 1) {
        this.mInjector.getUdfpsHelper().getOpticalSensorHelper().turnOnHwLightMode();
      } else {
        this.mInjector.getUdfpsHelper().getOpticalSensorHelper().turnOffHwLightMode();
      }
    }
  }

  public final void handleBioSysDvfsControl(int i, long j) {
    if (i == 1) {
      acquireDvfs(j <= 0 ? 2000 : (int) j);
    } else {
      releaseDvfs();
    }
  }

  public final void handleBioSysOpticalControl(int i, long j) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      if (i == 1) {
        this.mInjector.getUdfpsHelper().getOpticalSensorHelper().restoreFunctionForLightSource(j);
      } else if (i == 0) {
        this.mInjector.getUdfpsHelper().getOpticalSensorHelper().disableFunctionForLightSource();
      }
    }
  }

  public boolean isEnrollSession(int i) {
    return this.mEnrollSessionMonitor.isEnrollSession(i);
  }

  public void revokeChallengeInternally(int i) {
    this.mEnrollSessionMonitor.revokeChallenge(i);
  }

  public void setFlagForIFAA(int i, String str) {
    Slog.d("FingerprintService.Ext", "setFlagForIFAA: " + i + ", " + str);
    if (TextUtils.isEmpty(str)) {
      return;
    }
    this.mIFAAFlag = new Pair(str, Integer.valueOf(i));
  }

  public void applyAndClearIFAAFlag(String str, Bundle bundle) {
    Pair pair = this.mIFAAFlag;
    if (pair == null || !((String) pair.first).contentEquals(str)) {
      return;
    }
    Slog.i("FingerprintService.Ext", "IFAA: " + str);
    bundle.putInt("EXTRA_KEY_AUTH_FLAG", ((Integer) this.mIFAAFlag.second).intValue());
    this.mIFAAFlag = null;
  }

  public boolean isTpaCommand(String[] strArr) {
    return !Build.IS_USER && strArr.length > 2 && "--tpa".equals(strArr[0]);
  }

  public void handleTpaCommand(PrintWriter printWriter, final String[] strArr) {
    if ("mode".equals(strArr[1])) {
      final boolean equals = "1".equals(strArr[2]);
      SemFpMainThread.get()
          .getHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                  SemFingerprintServiceExtImpl.this.lambda$handleTpaCommand$9(equals);
                }
              });
      printWriter.println("tpa mode: " + equals);
      return;
    }
    if ("update".equals(strArr[1])) {
      SemFpMainThread.get()
          .getHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                  SemFingerprintServiceExtImpl.this.lambda$handleTpaCommand$10();
                }
              });
      printWriter.println("tpa mode: update action");
    } else if (KnoxVpnFirewallHelper.CMD.equals(strArr[1])) {
      SemFpMainThread.get()
          .getHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                  SemFingerprintServiceExtImpl.this.lambda$handleTpaCommand$11(strArr);
                }
              });
      printWriter.println("tpa mode: setTpaRequestCommands");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleTpaCommand$9(boolean z) {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null) {
      serviceProvider.semSetTpaHalEnabled(z);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleTpaCommand$10() {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null) {
      serviceProvider.semUpdateTpaAction();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleTpaCommand$11(String[] strArr) {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null) {
      serviceProvider.semSetTpaRequestCommandAction(new String[] {strArr[2], strArr[3]});
    }
  }

  public void dump(PrintWriter printWriter) {
    try {
      printWriter.println(" latest sensor status : " + this.mLatestSensorStatus);
      Iterator it = this.mRegistry.getAllProperties().iterator();
      while (it.hasNext()) {
        printWriter.println(
            " Max Template : "
                + ((FingerprintSensorPropertiesInternal) it.next()).maxEnrollmentsPerUser);
      }
      SemFpGestureManager semFpGestureManager = this.mGestureManager;
      if (semFpGestureManager != null) {
        semFpGestureManager.dump(printWriter);
      }
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
        this.mInjector.getUdfpsHelper().dump(printWriter, getSingleProvider());
      }
      SemBioLoggingManager.get().fpDump(printWriter);
    } catch (Exception e) {
      Slog.w("FingerprintService.Ext", "dump: " + e.getMessage());
    }
  }

  public final void handleTouchEvent(int i, long j) {
    Pair singleProvider = this.mRegistry.getSingleProvider();
    if (singleProvider == null) {
      return;
    }
    BaseClientMonitor semGetCurrentClient =
        ((ServiceProvider) singleProvider.second).semGetCurrentClient();
    long requestId = semGetCurrentClient == null ? 0L : semGetCurrentClient.getRequestId();
    if (i != 2) {
      if (i == 1) {
        ((ServiceProvider) singleProvider.second)
            .onPointerUp(
                requestId, ((Integer) singleProvider.first).intValue(), new PointerContext());
      }
    } else {
      ((ServiceProvider) singleProvider.second)
          .onPointerDown(
              requestId, ((Integer) singleProvider.first).intValue(), new PointerContext());
      if (semGetCurrentClient instanceof AuthenticationClient) {
        SemBioLoggingManager.get()
            .fpSetOpticalInfo((int) requestId, (int) (j >> 16), (int) (j & 65535));
      }
    }
  }

  public void acquireDvfs(int i) {
    Slog.d("FingerprintService.Ext", "acquireDvfs: " + i);
    SemBiometricBoostingManager.getInstance().acquireFingerprintDvfs(this.mContext, i);
  }

  public void releaseDvfs() {
    SemBiometricBoostingManager.getInstance().release(this.mContext, 2);
  }

  public final BaseClientMonitor getCurrentClient() {
    ServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null) {
      return serviceProvider.semGetCurrentClient();
    }
    return null;
  }

  public final void handleQcomForceQDB() {
    Pair singleProvider;
    if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC
        || (singleProvider = this.mRegistry.getSingleProvider()) == null) {
      return;
    }
    ((ServiceProvider) singleProvider.second)
        .semRequest(((Integer) singleProvider.first).intValue(), 34);
  }
}
