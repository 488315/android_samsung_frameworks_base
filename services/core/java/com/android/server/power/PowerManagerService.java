package com.android.server.power;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.app.SynchronousUserSwitchObserver;
import android.app.compat.CompatChanges;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.SensorManager;
import android.hardware.SystemSensorManager;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.scontext.SContext;
import android.hardware.scontext.SContextEvent;
import android.hardware.scontext.SContextListener;
import android.hardware.scontext.SContextManager;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.BatteryManagerInternal;
import android.os.BatterySaverPolicyConfig;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IPowerManager;
import android.os.IWakeLockCallback;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelDuration;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.service.dreams.DreamManagerInternal;
import android.sysprop.InitProperties;
import android.sysprop.PowerProperties;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.LongArray;
import android.util.PrintWriterPrinter;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IWindowManager;
import android.view.KeyEvent;
import com.android.internal.app.IBatteryStats;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.Preconditions;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.EventLogTags;
import com.android.server.LockGuard;
import com.android.server.RescueParty;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.UserspaceRebootLogger;
import com.android.server.Watchdog;
import com.android.server.am.BatteryStatsService;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessHandler;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LogicalLight;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.batterysaver.BatterySaverController;
import com.android.server.power.batterysaver.BatterySaverPolicy;
import com.android.server.power.batterysaver.BatterySaverStateMachine;
import com.android.server.power.batterysaver.BatterySavingStats;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class PowerManagerService extends SystemService implements Watchdog.Monitor {
  public static boolean sQuiescent;
  public AbuseWakeLockDetector mAbuseWakeLockDetector;
  public final AbuseWakeLockDetector.Callback mAbuseWakeLockDetectorCallback;
  public int mAlpmHlpmMode;
  public boolean mAlwaysOnEnabled;
  public ActivityManagerInternal mAmInternal;
  public final AmbientDisplayConfiguration mAmbientDisplayConfiguration;
  public final AmbientDisplaySuppressionController mAmbientDisplaySuppressionController;
  public final AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback
      mAmbientSuppressionChangedCallback;
  public boolean mAodMode;
  public final AttentionDetector mAttentionDetector;
  public LogicalLight mAttentionLight;
  public int mAttentiveTimeoutConfig;
  public long mAttentiveTimeoutSetting;
  public long mAttentiveWarningDurationConfig;
  public String mAutoBrightnessLimitLastCaller;
  public int mAutoBrightnessLowerLimit;
  public int mAutoBrightnessUpperLimit;
  public int mBatteryLevel;
  public boolean mBatteryLevelCritical;
  public boolean mBatteryLevelLow;
  public BatteryManagerInternal mBatteryManagerInternal;
  public int mBatteryOnline;
  public final BatterySaverController mBatterySaverController;
  public final BatterySaverPolicy mBatterySaverPolicy;
  public final BatterySaverStateMachine mBatterySaverStateMachine;
  public float mBatterySavingAdjustBrightnessFactor;
  public final BatterySavingStats mBatterySavingStats;
  public IBatteryStats mBatteryStats;
  public final BinderService mBinderService;
  public boolean mBootCompleted;
  public final SuspendBlocker mBootingSuspendBlocker;
  public int mBrightnessLimitByCoverConfig;
  public Runnable mBrightnessLimitRunnable;
  public final Clock mClock;
  public final Constants mConstants;
  public final Context mContext;
  public boolean mCoverAuthReady;
  public Runnable mCoverAuthReadyRunnable;
  public CoverManager mCoverManager;
  public CoverManager.CoverStateListener mCoverStateListener;
  public int mCoverType;
  public boolean mDecoupleHalAutoSuspendModeFromDisplayConfig;
  public boolean mDecoupleHalInteractiveModeFromDisplayConfig;
  public boolean mDefaultDisplayReadyByProximity;
  public int mDefaultDisplayState;
  public long mDelayTimePrintWakeLock;
  public final DeviceConfigParameterProvider mDeviceConfigProvider;
  public boolean mDeviceIdleMode;
  public int[] mDeviceIdleTempWhitelist;
  public int[] mDeviceIdleWhitelist;
  public int mDirty;
  public boolean mDisableScreenWakeLocksWhileCached;
  public DisplayManagerInternal mDisplayManagerInternal;
  public final DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
  public final SuspendBlocker mDisplaySuspendBlocker;
  public int mDockState;
  public boolean mDoubleTapWakeEnabled;
  public boolean mDozeAfterScreenOff;
  public int mDozeScreenBrightnessOverrideFromDreamManager;
  public float mDozeScreenBrightnessOverrideFromDreamManagerFloat;
  public int mDozeScreenStateOverrideFromDreamManager;
  public boolean mDozeStartInProgress;
  public boolean mDrawWakeLockOverrideFromSidekick;
  public DreamManagerInternal mDreamManager;
  public boolean mDreamsActivateOnDockSetting;
  public boolean mDreamsActivateOnSleepSetting;
  public boolean mDreamsActivatedOnDockByDefaultConfig;
  public boolean mDreamsActivatedOnSleepByDefaultConfig;
  public int mDreamsBatteryLevelDrain;
  public int mDreamsBatteryLevelDrainCutoffConfig;
  public int mDreamsBatteryLevelMinimumWhenNotPoweredConfig;
  public int mDreamsBatteryLevelMinimumWhenPoweredConfig;
  public boolean mDreamsDisabledByAmbientModeSuppressionConfig;
  public boolean mDreamsEnabledByDefaultConfig;
  public boolean mDreamsEnabledOnBatteryConfig;
  public boolean mDreamsEnabledSetting;
  public boolean mDreamsSupportedConfig;
  public int mDualScreenPolicy;
  public boolean mEarlyWakeUp;
  public boolean mEnhancedDischargePredictionIsPersonalized;
  public long mEnhancedDischargeTimeElapsed;
  public final Object mEnhancedDischargeTimeLock;
  public Runnable mEnsureTransitionToDozingReleaser;
  public boolean mEnsureTransitionToDozingReleaserRunning;
  public boolean mFTAMode;
  public boolean mFeatureCoverSysfs;
  public boolean mForceSetHalInteractiveMode;
  public boolean mForceSlowChange;
  public boolean mForceSuspendActive;
  public int mForegroundProfile;
  public boolean mFreezeBrightnessMode;
  public boolean mFreezingScreenBrightness;
  public boolean mGoToSleepPreventionEnabled;
  public boolean mHalAutoSuspendModeEnabled;
  public boolean mHalInteractiveModeEnabled;
  public final Handler mHandler;
  public Handler mHandlerPmsMisc;
  public final ServiceThread mHandlerThread;
  public HandlerThread mHandlerThreadPmsMisc;
  public boolean mHbmBlock;
  public boolean mHoldingBootingSuspendBlocker;
  public boolean mHoldingDisplaySuspendBlocker;
  public boolean mHoldingWakeLockSuspendBlocker;
  public final InattentiveSleepWarningController mInattentiveSleepWarningOverlayController;
  public final Injector mInjector;
  public boolean mInterceptedKeyForProximity;
  public boolean mInterceptedPowerKeyForProximity;
  public Intent mInternalDisplayOffByPowerKeyIntent;
  public boolean mIsCoverClosed;
  public boolean mIsDeviceMoving;
  public boolean mIsDualViewMode;
  public boolean mIsPowered;
  public AtomicBoolean mIsUserActivityInvoked;
  public AtomicBoolean mIsUserActivityNativeInvoked;
  public boolean mIsWirelessChargerSContextEnabled;
  public boolean mIsWirelessChargerSContextRegistered;
  public boolean mKeepDreamingWhenUnplugging;
  public boolean mLCDFlashMode;
  public LCDFlashModeLock mLCDFlashModeLock;
  public long mLastAndroidAutoBoostTime;
  public long mLastAutoBrightnessLimitTime;
  public boolean mLastBrightnessOverrideState;
  public long mLastEnhancedDischargeTimeUpdatedElapsed;
  public int mLastGlobalSleepReason;
  public long mLastGlobalSleepTime;
  public long mLastGlobalSleepTimeRealtime;
  public int mLastGlobalWakeReason;
  public long mLastGlobalWakeTime;
  public long mLastGlobalWakeTimeRealtime;
  public long mLastInteractivePowerHintTime;
  public long mLastNormalTouchBoostTime;
  public long mLastOutdoorModeEnabledTime;
  public int mLastRequestedLimitationOfBrightness;
  public long mLastScreenBrightnessBoostTime;
  public long mLastScreenOffEventElapsedRealTime;
  public int mLastScreenTimeout;
  public long mLastUserActivityStateListenerCalledTime;
  public int mLastUserActivityStateListenerState;
  public int mLastWakeUpReason;
  public long mLastWarningAboutUserActivityPermission;
  public boolean mLightDeviceIdleMode;
  public LightsManager mLightsManager;
  public final LocalService mLocalService;
  public final Object mLock;
  public boolean mLowPowerStandbyActive;
  public int[] mLowPowerStandbyAllowlist;
  public final LowPowerStandbyController mLowPowerStandbyController;
  public String mMasterBrightnessLimitLastCaller;
  public int mMasterBrightnessLimitPeriod;
  public boolean mMasterBrightnessLimitRunning;
  public int mMasterBrightnessLowerLimit;
  public int mMasterBrightnessUpperLimit;
  public long mMaximumScreenDimDurationConfig;
  public float mMaximumScreenDimRatioConfig;
  public long mMaximumScreenOffTimeoutFromDeviceAdmin;
  public long mMinimumScreenOffTimeoutConfig;
  public final NativeWrapper mNativeWrapper;
  public Intent mNoUserActivityIntent;
  public boolean mNoUserActivitySent;
  public Notifier mNotifier;
  public long mNotifyLongDispatched;
  public long mNotifyLongNextCheck;
  public long mNotifyLongScheduled;
  public boolean mOutdoorModeSetting;
  public long mOverriddenTimeout;
  public long mPassedWakeupTime;
  public final PermissionCheckerWrapper mPermissionCheckerWrapper;
  public int mPlugType;
  public WindowManagerPolicy mPolicy;
  public final PowerGroupWakefulnessChangeListener mPowerGroupWakefulnessChangeListener;
  public final SparseArray mPowerGroups;
  public final PowerHistorian mPowerHistorian;
  public final PowerPropertiesWrapper mPowerPropertiesWrapper;
  public final Runnable mPrintWakeLockAperiodicallyRunnable;
  public final SparseArray mProfilePowerState;
  public boolean mProximityPositive;
  public boolean mRequestWaitForNegativeProximity;
  public boolean mSandmanScheduled;
  public boolean mScreenBrightnessBoostInProgress;
  public final float mScreenBrightnessDefault;
  public final float mScreenBrightnessDim;
  public final float mScreenBrightnessDoze;
  public float mScreenBrightnessForClearCoverConfig;
  public final float mScreenBrightnessMaximum;
  public final float mScreenBrightnessMinimum;
  public float mScreenBrightnessOverrideFromWindowManager;
  public float mScreenBrightnessScaleFactor;
  public ScreenCurtainController mScreenCurtainController;
  public long mScreenDimDurationOverrideFromSQD;
  public long mScreenDimDurationOverrideFromWindowManager;
  public int mScreenExtendedBrightnessMaximum;
  public String mScreenOffReason;
  public int mScreenOffTimeoutForDex;
  public long mScreenOffTimeoutSetting;
  public int mScreenOnOffCount;
  public String mScreenOnReason;
  public final ScreenUndimDetector mScreenUndimDetector;
  public SemInputDeviceManager mSemInputDeviceManager;
  public SettingsObserver mSettingsObserver;
  public boolean mShouldWaitForTransitionToAodUi;
  public String mShutdownOrRebootCaller;
  public long mSleepTimeoutSetting;
  public SmartStayController mSmartStayController;
  public boolean mSmartStayEnabledSetting;
  public boolean mStayOn;
  public int mStayOnWhilePluggedInSetting;
  public boolean mSupportsDoubleTapWakeConfig;
  public SuspendBlockerMonitor mSuspendBlockerMonitor;
  public final ArrayList mSuspendBlockers;
  public boolean mSuspendWhenScreenOffDueToProximityConfig;
  public final SystemPropertiesWrapper mSystemProperties;
  public boolean mSystemReady;
  public int mTargetBrightnessForLimit;
  public boolean mTheaterModeEnabled;
  public final SparseArray mUidState;
  public boolean mUidsChanged;
  public boolean mUidsChanging;
  public boolean mUpdatePowerStateInProgress;
  public Intent mUserActivityIntent;
  public final ArrayList mUserActivityStateListenerListeners;
  public Runnable mUserActivityStateListenerTimeoutRunnable;
  public final UserActivityTask mUserActivityTask;
  public long mUserActivityTimeoutForDexOverrideFromWindowManager;
  public long mUserActivityTimeoutOverrideFromWindowManager;
  public int mUserActivityTimeoutSetting;
  public final UserActivityTimeoutTask mUserActivityTimeoutTask;
  public int mUserId;
  public boolean mUserInactiveOverrideFromWindowManager;
  public int mWakeLockSummary;
  public final SuspendBlocker mWakeLockSuspendBlocker;
  public final ArrayList mWakeLocks;
  public boolean mWakeUpWhenPluggedOrUnpluggedConfig;
  public boolean mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig;
  public boolean mWakefulnessChanging;
  public int mWakefulnessRaw;
  public ArrayList mWakelockBlacklistAppid;
  public WirelessChargerDetector mWirelessChargerDetector;
  public SContextListener mWirelessChargerSContextListener;
  public SContextManager mWirelessChargerSContextManager;
  public long mlastSetCoverTypeTime;
  public String mlastUpdateCoverStateReason;
  public long mlastUpdateCoverStateTime;
  public String mlastUpdateCoverTypeReason;
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
  public static final IntArray DEFAULT_DISPLAY_GROUP_IDS = IntArray.wrap(new int[] {0});
  public static final boolean SEC_USE_FACTORY_BINARY = FactoryTest.isFactoryBinary();
  public static final Uri DEX_SETTINGS_URI =
      Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");

  interface Clock {
    long elapsedRealtime();

    long uptimeMillis();
  }

  interface PermissionCheckerWrapper {
    int checkPermissionForDataDelivery(
        Context context, String str, int i, AttributionSource attributionSource, String str2);
  }

  interface PowerPropertiesWrapper {
    boolean permissionless_turn_screen_on();

    boolean waive_target_sdk_check_for_turn_screen_on();
  }

  public static int adjustWakeLockSummary(int i, int i2, int i3) {
    if (i != 3) {
      i2 &= -193;
    }
    if (i == 0 || (i2 & 64) != 0) {
      i2 &= -15;
      if (i == 0 && i3 == 4) {
        i2 &= -17;
      }
    }
    if (i == 3 && i3 == 4) {
      i2 &= -17;
    }
    if ((i2 & 6) != 0) {
      if (i == 1) {
        i2 |= 33;
      } else if (i == 2) {
        i2 |= 1;
      }
    }
    return (i2 & 128) != 0 ? i2 | 1 : i2;
  }

  public static boolean isScreenLock(int i) {
    int i2 = i & GnssNative.GNSS_AIDING_TYPE_ALL;
    return i2 == 6 || i2 == 10 || i2 == 26;
  }

  public static boolean isValidBrightness(float f) {
    return f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f <= 1.0f;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeAcquireSuspendBlocker(String str);

  /* JADX INFO: Access modifiers changed from: private */
  public static native boolean nativeForceSuspend();

  /* JADX INFO: Access modifiers changed from: private */
  public native void nativeInit();

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeReleaseSuspendBlocker(String str);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeSetAutoSuspend(boolean z);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeSetInteractiveAsync(boolean z, int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeSetPowerBoost(int i, int i2);

  /* JADX INFO: Access modifiers changed from: private */
  public static native boolean nativeSetPowerMode(int i, boolean z);

  public final class DreamManagerStateListener
      implements DreamManagerInternal.DreamManagerStateListener {
    public DreamManagerStateListener() {}

    public void onKeepDreamingWhenUnpluggingChanged(boolean z) {
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.mKeepDreamingWhenUnplugging = z;
      }
    }
  }

  public final class PowerGroupWakefulnessChangeListener implements PowerGroup.PowerGroupListener {
    public PowerGroupWakefulnessChangeListener() {}

    @Override // com.android.server.power.PowerGroup.PowerGroupListener
    public void onWakefulnessChangedLocked(
        int i, int i2, long j, int i3, int i4, int i5, String str, String str2) {
      PowerManagerService.this.mWakefulnessChanging = true;
      PowerManagerService.this.mDirty |= 2;
      if (i2 == 1) {
        int i6 = i3 != 13 ? 0 : 1;
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.userActivityNoUpdateLocked(
            (PowerGroup) powerManagerService.mPowerGroups.get(i), j, 0, i6, i4);
      }
      PowerManagerService.this.mDirty |= 65536;
      if (i == 0) {
        PowerManagerService.this.mNotifier.onInternalDisplayStateChange(
            PowerManagerInternal.isInteractive(i2));
        if (PowerManagerService.this.mIsDualViewMode
            && !PowerManagerInternal.isInteractive(i2)
            && i3 == 4) {
          PowerManagerService.this.mHandlerPmsMisc.postAtTime(
              new Runnable() { // from class:
                               // com.android.server.power.PowerManagerService$PowerGroupWakefulnessChangeListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManagerService.PowerGroupWakefulnessChangeListener.this
                      .lambda$onWakefulnessChangedLocked$0();
                }
              },
              PowerManagerService.this.mClock.uptimeMillis());
        }
        if (PowerManagerService.this.mSmartStayEnabledSetting) {
          PowerManagerService.this.mSmartStayController.onWakefulnessChangeStarted(i2);
        }
        if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN
            && PowerManagerService.this.mScreenCurtainController != null) {
          PowerManagerService.this.mScreenCurtainController.onWakefulnessChangeStarted(i2);
        }
      }
      PowerManagerService.this.mNotifier.onGroupWakefulnessChangeStarted(i, i2, i3, j);
      PowerManagerService.this.updateRelatedPowerGroupWakefulnessLocked(
          i, i2, j, i4, i5, str, str2);
      PowerManagerService.this.updateGlobalWakefulnessLocked(j, i3, i4, i5, str, str2);
      PowerManagerService.this.updatePowerStateLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onWakefulnessChangedLocked$0() {
      PowerManagerService.this.mContext.sendBroadcastAsUser(
          PowerManagerService.this.mInternalDisplayOffByPowerKeyIntent, UserHandle.ALL);
    }
  }

  public final class DisplayGroupPowerChangeListener
      implements DisplayManagerInternal.DisplayGroupListener {
    public DisplayGroupPowerChangeListener() {}

    public void onDisplayGroupAdded(final int i) {
      synchronized (PowerManagerService.this.mLock) {
        if (PowerManagerService.this.mPowerGroups.contains(i)) {
          Slog.m74e("PowerManagerService", "Tried to add already existing group:" + i);
          return;
        }
        Slog.m72d("PowerManagerService", "onDisplayGroupAdded: groupId=" + i);
        PowerGroup powerGroup =
            new PowerGroup(
                i,
                PowerManagerService.this.mPowerGroupWakefulnessChangeListener,
                PowerManagerService.this.mNotifier,
                PowerManagerService.this.mDisplayManagerInternal,
                1,
                false,
                i == 0,
                PowerManagerService.this.mClock.uptimeMillis());
        PowerManagerService.this.mPowerGroups.append(i, powerGroup);
        PowerManagerService.this.onPowerGroupEventLocked(0, powerGroup);
        PowerManagerService.this.mHandlerPmsMisc.post(
            new Runnable() { // from class:
                             // com.android.server.power.PowerManagerService$DisplayGroupPowerChangeListener$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                PowerManagerService.DisplayGroupPowerChangeListener.this
                    .lambda$onDisplayGroupAdded$0(i);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayGroupAdded$0(int i) {
      PowerManagerService.this.mPowerHistorian.onDisplayGroupChanged(true, i);
    }

    public void onDisplayGroupRemoved(final int i) {
      synchronized (PowerManagerService.this.mLock) {
        if (i == 0) {
          Slog.wtf("PowerManagerService", "Tried to remove default display group: " + i);
          return;
        }
        if (!PowerManagerService.this.mPowerGroups.contains(i)) {
          Slog.m74e("PowerManagerService", "Tried to remove non-existent group:" + i);
          return;
        }
        Slog.m72d("PowerManagerService", "onDisplayGroupRemoved: groupId=" + i);
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.onPowerGroupEventLocked(
            1, (PowerGroup) powerManagerService.mPowerGroups.get(i));
        PowerManagerService.this.mHandlerPmsMisc.post(
            new Runnable() { // from class:
                             // com.android.server.power.PowerManagerService$DisplayGroupPowerChangeListener$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                PowerManagerService.DisplayGroupPowerChangeListener.this
                    .lambda$onDisplayGroupRemoved$1(i);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayGroupRemoved$1(int i) {
      PowerManagerService.this.mPowerHistorian.onDisplayGroupChanged(false, i);
    }

    public void onDisplayGroupChanged(int i) {
      synchronized (PowerManagerService.this.mLock) {
        if (!PowerManagerService.this.mPowerGroups.contains(i)) {
          Slog.m74e("PowerManagerService", "Tried to change non-existent group: " + i);
          return;
        }
        Slog.m72d("PowerManagerService", "onDisplayGroupChanged: groupId=" + i);
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.onPowerGroupEventLocked(
            2, (PowerGroup) powerManagerService.mPowerGroups.get(i));
      }
    }
  }

  public final class ForegroundProfileObserver extends SynchronousUserSwitchObserver {
    public ForegroundProfileObserver() {}

    public void onUserSwitching(int i) {
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.mUserId = i;
      }
    }

    public void onForegroundProfileSwitch(int i) {
      Slog.m72d("PowerManagerService", "[api] onForegroundProfileSwitch: " + i);
      long uptimeMillis = PowerManagerService.this.mClock.uptimeMillis();
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.mForegroundProfile = i;
        PowerManagerService.this.maybeUpdateForegroundProfileLastActivityLocked(uptimeMillis);
      }
    }
  }

  public final class ProfilePowerState {
    public long mLastUserActivityTime;
    public boolean mLockingNotified;
    public long mScreenOffTimeout;
    public long mScreenOffTimeoutForUser;
    public final int mUserId;
    public int mWakeLockSummary;

    public ProfilePowerState(int i, long j, long j2) {
      Slog.m72d("PowerManagerService", "new ProfilePowerState: " + i + ", " + j);
      this.mUserId = i;
      this.mScreenOffTimeout = j;
      this.mLastUserActivityTime = j2;
    }

    public ProfilePowerState(int i, long j, long j2, boolean z) {
      Slog.m72d("PowerManagerService", "new ProfilePowerState: " + i + ", " + j);
      this.mUserId = i;
      this.mScreenOffTimeout = z ? Long.MAX_VALUE : j;
      this.mScreenOffTimeoutForUser = z ? j : Long.MAX_VALUE;
      this.mLastUserActivityTime = j2;
    }
  }

  public final class Constants extends ContentObserver {
    public boolean NO_CACHED_WAKE_LOCKS;
    public final KeyValueListParser mParser;
    public ContentResolver mResolver;

    public Constants(Handler handler) {
      super(handler);
      this.NO_CACHED_WAKE_LOCKS = true;
      this.mParser = new KeyValueListParser(',');
    }

    public void start(ContentResolver contentResolver) {
      this.mResolver = contentResolver;
      contentResolver.registerContentObserver(
          Settings.Global.getUriFor("power_manager_constants"), false, this);
      updateConstants();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      updateConstants();
    }

    public final void updateConstants() {
      synchronized (PowerManagerService.this.mLock) {
        try {
          this.mParser.setString(
              Settings.Global.getString(this.mResolver, "power_manager_constants"));
        } catch (IllegalArgumentException e) {
          Slog.m75e("PowerManagerService", "Bad alarm manager settings", e);
        }
        this.NO_CACHED_WAKE_LOCKS = this.mParser.getBoolean("no_cached_wake_locks", true);
        Slog.m72d(
            "PowerManagerService",
            "[api] updateConstants: NO_CACHED_WAKE_LOCKS: " + this.NO_CACHED_WAKE_LOCKS);
      }
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println("  Settings power_manager_constants:");
      printWriter.print("    ");
      printWriter.print("no_cached_wake_locks");
      printWriter.print("=");
      printWriter.println(this.NO_CACHED_WAKE_LOCKS);
    }

    public void dumpProto(ProtoOutputStream protoOutputStream) {
      long start = protoOutputStream.start(1146756268033L);
      protoOutputStream.write(1133871366145L, this.NO_CACHED_WAKE_LOCKS);
      protoOutputStream.end(start);
    }
  }

  public class NativeWrapper {
    public void nativeInit(PowerManagerService powerManagerService) {
      powerManagerService.nativeInit();
    }

    public void nativeAcquireSuspendBlocker(String str) {
      PowerManagerService.nativeAcquireSuspendBlocker(str);
    }

    public void nativeReleaseSuspendBlocker(String str) {
      PowerManagerService.nativeReleaseSuspendBlocker(str);
    }

    public void nativeSetInteractiveAsync(boolean z, int i) {
      PowerManagerService.nativeSetInteractiveAsync(z, i);
    }

    public void nativeSetAutoSuspend(boolean z) {
      PowerManagerService.nativeSetAutoSuspend(z);
    }

    public void nativeSetPowerBoost(int i, int i2) {
      PowerManagerService.nativeSetPowerBoost(i, i2);
    }

    public boolean nativeSetPowerMode(int i, boolean z) {
      return PowerManagerService.nativeSetPowerMode(i, z);
    }

    public boolean nativeForceSuspend() {
      return PowerManagerService.nativeForceSuspend();
    }
  }

  class Injector {
    public Notifier createNotifier(
        Looper looper,
        Context context,
        IBatteryStats iBatteryStats,
        SuspendBlocker suspendBlocker,
        WindowManagerPolicy windowManagerPolicy,
        FaceDownDetector faceDownDetector,
        ScreenUndimDetector screenUndimDetector,
        Executor executor) {
      return new Notifier(
          looper,
          context,
          iBatteryStats,
          suspendBlocker,
          windowManagerPolicy,
          faceDownDetector,
          screenUndimDetector,
          executor);
    }

    public SuspendBlocker createSuspendBlocker(
        PowerManagerService powerManagerService, String str) {
      Objects.requireNonNull(powerManagerService);
      SuspendBlockerImpl suspendBlockerImpl = powerManagerService.new SuspendBlockerImpl(str);
      powerManagerService.mSuspendBlockers.add(suspendBlockerImpl);
      return suspendBlockerImpl;
    }

    public BatterySaverPolicy createBatterySaverPolicy(
        Object obj, Context context, BatterySavingStats batterySavingStats) {
      return new BatterySaverPolicy(obj, context, batterySavingStats);
    }

    public BatterySaverController createBatterySaverController(
        Object obj,
        Context context,
        BatterySaverPolicy batterySaverPolicy,
        BatterySavingStats batterySavingStats) {
      return new BatterySaverController(
          obj, context, BackgroundThread.get().getLooper(), batterySaverPolicy, batterySavingStats);
    }

    public BatterySaverStateMachine createBatterySaverStateMachine(
        Object obj, Context context, BatterySaverController batterySaverController) {
      return new BatterySaverStateMachine(obj, context, batterySaverController);
    }

    public NativeWrapper createNativeWrapper() {
      return new NativeWrapper();
    }

    public WirelessChargerDetector createWirelessChargerDetector(
        SensorManager sensorManager, SuspendBlocker suspendBlocker, Handler handler) {
      return new WirelessChargerDetector(sensorManager, suspendBlocker, handler);
    }

    public AmbientDisplayConfiguration createAmbientDisplayConfiguration(Context context) {
      return new AmbientDisplayConfiguration(context);
    }

    public AmbientDisplaySuppressionController createAmbientDisplaySuppressionController(
        AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback
            ambientDisplaySuppressionChangedCallback) {
      return new AmbientDisplaySuppressionController(ambientDisplaySuppressionChangedCallback);
    }

    public InattentiveSleepWarningController createInattentiveSleepWarningController() {
      return new InattentiveSleepWarningController();
    }

    public SystemPropertiesWrapper createSystemPropertiesWrapper() {
      return new SystemPropertiesWrapper() { // from class:
                                             // com.android.server.power.PowerManagerService.Injector.1
        @Override // com.android.server.power.SystemPropertiesWrapper
        public String get(String str, String str2) {
          return SystemProperties.get(str, str2);
        }

        @Override // com.android.server.power.SystemPropertiesWrapper
        public void set(String str, String str2) {
          SystemProperties.set(str, str2);
        }
      };
    }

    public Clock createClock() {
      return new Clock() { // from class: com.android.server.power.PowerManagerService.Injector.2
        @Override // com.android.server.power.PowerManagerService.Clock
        public long uptimeMillis() {
          return SystemClock.uptimeMillis();
        }

        @Override // com.android.server.power.PowerManagerService.Clock
        public long elapsedRealtime() {
          return SystemClock.elapsedRealtime();
        }
      };
    }

    public Handler createHandler(Looper looper, Handler.Callback callback) {
      return new Handler(looper, callback, true);
    }

    public void invalidateIsInteractiveCaches() {
      PowerManager.invalidateIsInteractiveCaches();
    }

    public LowPowerStandbyController createLowPowerStandbyController(
        Context context, Looper looper) {
      return new LowPowerStandbyController(context, looper);
    }

    public PermissionCheckerWrapper createPermissionCheckerWrapper() {
      return new PermissionCheckerWrapper() { // from class:
                                              // com.android.server.power.PowerManagerService$Injector$$ExternalSyntheticLambda0
        @Override // com.android.server.power.PowerManagerService.PermissionCheckerWrapper
        public final int checkPermissionForDataDelivery(
            Context context, String str, int i, AttributionSource attributionSource, String str2) {
          return PermissionChecker.checkPermissionForDataDelivery(
              context, str, i, attributionSource, str2);
        }
      };
    }

    public PowerPropertiesWrapper createPowerPropertiesWrapper() {
      return new PowerPropertiesWrapper() { // from class:
                                            // com.android.server.power.PowerManagerService.Injector.3
        @Override // com.android.server.power.PowerManagerService.PowerPropertiesWrapper
        public boolean waive_target_sdk_check_for_turn_screen_on() {
          return ((Boolean)
                  PowerProperties.waive_target_sdk_check_for_turn_screen_on().orElse(Boolean.FALSE))
              .booleanValue();
        }

        @Override // com.android.server.power.PowerManagerService.PowerPropertiesWrapper
        public boolean permissionless_turn_screen_on() {
          return ((Boolean) PowerProperties.permissionless_turn_screen_on().orElse(Boolean.FALSE))
              .booleanValue();
        }
      };
    }

    public DeviceConfigParameterProvider createDeviceConfigParameterProvider() {
      return new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
    }

    public CoverManager createCoverManager(Context context) {
      return new CoverManager(context);
    }

    public INotificationManager getNotificationManager() {
      return NotificationManager.getService();
    }

    public ActivityManager getActivityManager(Context context) {
      return (ActivityManager) context.getSystemService("activity");
    }

    public AbuseWakeLockDetector createAbuseWakeLockDetector(
        Object obj, Context context, AbuseWakeLockDetector.Callback callback) {
      return new AbuseWakeLockDetector(
          obj,
          context,
          callback,
          IWindowManager.Stub.asInterface(ServiceManager.getService("window")));
    }

    public SmartStayController createSmartStayController(
        Object obj, Context context, Runnable runnable) {
      return new SmartStayController(obj, context, runnable);
    }

    public ScreenCurtainController createScreenCurtainController(
        Context context, Object obj, Looper looper) {
      return new ScreenCurtainController(context, obj, looper);
    }
  }

  public PowerManagerService(Context context) {
    this(context, new Injector());
  }

  /* JADX WARN: Multi-variable type inference failed */
  public PowerManagerService(Context context, Injector injector) {
    super(context);
    boolean z;
    this.mUserActivityTimeoutSetting = 0;
    this.mUserActivityTask = new UserActivityTask();
    this.mUserActivityTimeoutTask = new UserActivityTimeoutTask();
    Object installNewLock = LockGuard.installNewLock(1);
    this.mLock = installNewLock;
    this.mSuspendBlockers = new ArrayList();
    this.mWakeLocks = new ArrayList();
    this.mAutoBrightnessLowerLimit = -1;
    this.mAutoBrightnessUpperLimit = -1;
    this.mAutoBrightnessLimitLastCaller = "";
    this.mSuspendBlockerMonitor = SuspendBlockerMonitor.getGlobalInstance();
    this.mEnhancedDischargeTimeLock = new Object();
    this.mDockState = 0;
    this.mFTAMode = false;
    this.mMaximumScreenOffTimeoutFromDeviceAdmin = Long.MAX_VALUE;
    this.mDefaultDisplayReadyByProximity = true;
    this.mScreenBrightnessOverrideFromWindowManager = Float.NaN;
    this.mOverriddenTimeout = -1L;
    this.mUserActivityTimeoutOverrideFromWindowManager = -1L;
    this.mUserActivityTimeoutForDexOverrideFromWindowManager = -1L;
    this.mDozeScreenStateOverrideFromDreamManager = 0;
    this.mDozeScreenBrightnessOverrideFromDreamManager = -1;
    this.mDozeScreenBrightnessOverrideFromDreamManagerFloat = Float.NaN;
    this.mLastWarningAboutUserActivityPermission = Long.MIN_VALUE;
    this.mDeviceIdleWhitelist = new int[0];
    this.mDeviceIdleTempWhitelist = new int[0];
    this.mLowPowerStandbyAllowlist = new int[0];
    this.mUidState = new SparseArray();
    this.mPowerGroups = new SparseArray();
    this.mDelayTimePrintWakeLock = -1L;
    this.mIsCoverClosed = false;
    this.mlastUpdateCoverStateReason = "";
    this.mlastUpdateCoverStateTime = -1L;
    this.mlastUpdateCoverTypeReason = "";
    this.mlastSetCoverTypeTime = -1L;
    this.mFeatureCoverSysfs = true;
    this.mCoverManager = null;
    this.mCoverType = 2;
    this.mScreenBrightnessForClearCoverConfig = -1.0f;
    this.mBrightnessLimitByCoverConfig = -1;
    this.mAlpmHlpmMode = -1;
    this.mLCDFlashModeLock = null;
    this.mOutdoorModeSetting = false;
    this.mShutdownOrRebootCaller = "";
    this.mLastScreenTimeout = -1;
    this.mScreenOffTimeoutForDex = Integer.parseInt("600000");
    this.mScreenBrightnessScaleFactor = -1.0f;
    this.mFreezingScreenBrightness = false;
    this.mScreenDimDurationOverrideFromWindowManager = -1L;
    this.mScreenDimDurationOverrideFromSQD = -1L;
    this.mWakelockBlacklistAppid = new ArrayList();
    this.mLastBrightnessOverrideState = false;
    this.mScreenOffReason = "";
    this.mScreenOnReason = "";
    this.mScreenOnOffCount = 0;
    this.mIsUserActivityNativeInvoked = new AtomicBoolean();
    this.mIsUserActivityInvoked = new AtomicBoolean();
    this.mMasterBrightnessLowerLimit = -1;
    this.mMasterBrightnessUpperLimit = -1;
    this.mMasterBrightnessLimitLastCaller = "";
    this.mLastRequestedLimitationOfBrightness = 485;
    this.mTargetBrightnessForLimit = 0;
    this.mMasterBrightnessLimitRunning = false;
    this.mMasterBrightnessLimitPeriod = 0;
    this.mBatteryOnline = -1;
    this.mUserActivityStateListenerListeners = new ArrayList();
    this.mWirelessChargerSContextManager = null;
    this.mIsWirelessChargerSContextEnabled = false;
    this.mIsWirelessChargerSContextRegistered = false;
    this.mIsDeviceMoving = true;
    this.mPassedWakeupTime = 0L;
    this.mDualScreenPolicy = -1;
    this.mPowerHistorian = PowerHistorian.getInstance();
    this.mSemInputDeviceManager = null;
    this.mDefaultDisplayState = 0;
    this.mProfilePowerState = new SparseArray();
    this.mLastAndroidAutoBoostTime = -1L;
    this.mLastNormalTouchBoostTime = -1L;
    this.mLastWakeUpReason = 0;
    this.mAbuseWakeLockDetectorCallback = new C23301();
    this.mDisplayPowerCallbacks =
        new DisplayManagerInternal
            .DisplayPowerCallbacks() { // from class: com.android.server.power.PowerManagerService.2
          public void onStateChanged() {
            synchronized (PowerManagerService.this.mLock) {
              PowerManagerService.this.mDirty |= 8;
              PowerManagerService.this.updatePowerStateLocked();
            }
          }

          public void onProximityPositive() {
            synchronized (PowerManagerService.this.mLock) {
              if (PowerManagerService.this.mProximityPositive) {
                return;
              }
              Slog.m72d("PowerManagerService", "DisplayPowerCallbacks : onProximityPositive()");
              if (!PowerManagerService.this.mIsDualViewMode) {
                PowerManagerService.this.mNotifier.onScreenStateChangeStartedByProximity(true);
              }
              PowerManagerService.this.mProximityPositive = true;
              PowerManagerService.this.mDirty |= 512;
              PowerManagerService.this.updatePowerStateLocked();
            }
          }

          public void onProximityNegative() {
            synchronized (PowerManagerService.this.mLock) {
              if (PowerManagerService.this.mProximityPositive) {
                Slog.m72d("PowerManagerService", "DisplayPowerCallbacks : onProximityNegative()");
                if (PowerManagerService.this.mDefaultDisplayState != 2
                    && ((PowerGroup) PowerManagerService.this.mPowerGroups.get(0))
                            .getWakefulnessLocked()
                        == 1) {
                  Slog.m72d(
                      "PowerManagerService",
                      "onProximityNegative: mDisplayDefaultReadyByProximity: false");
                  PowerManagerService.this.mDefaultDisplayReadyByProximity = false;
                }
                if (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
                    && PowerManagerService.this.mDualScreenPolicy == 1) {
                  Slog.m72d(
                      "PowerManagerService",
                      "onProximityNegative() ignore DefaultDispalyReady cover screen");
                  PowerManagerService.this.mDefaultDisplayReadyByProximity = true;
                }
                if (((PowerGroup) PowerManagerService.this.mPowerGroups.get(0))
                            .getWakefulnessLocked()
                        == 1
                    && !PowerManagerService.this.mIsDualViewMode) {
                  PowerManagerService.this.mNotifier.onScreenStateChangeStartedByProximity(false);
                }
                PowerManagerService.this.mProximityPositive = false;
                PowerManagerService.this.mInterceptedPowerKeyForProximity = false;
                PowerManagerService.this.mInterceptedKeyForProximity = false;
                if (((PowerGroup) PowerManagerService.this.mPowerGroups.get(0))
                        .getWakefulnessLocked()
                    != 1) {
                  PowerManagerService.this.mLastWakeUpReason = 109;
                  PowerManagerService.this.mScreenOnReason = " proximity negative";
                  PowerManagerService powerManagerService = PowerManagerService.this;
                  powerManagerService.wakePowerGroupLocked(
                      (PowerGroup) powerManagerService.mPowerGroups.get(0),
                      PowerManagerService.this.mClock.uptimeMillis(),
                      109,
                      "android.server.power:PROXIMITY",
                      1000,
                      PowerManagerService.this.mContext.getOpPackageName(),
                      1000);
                }
                PowerManagerService.this.mDirty |= 512;
                PowerManagerService powerManagerService2 = PowerManagerService.this;
                powerManagerService2.userActivityNoUpdateLocked(
                    (PowerGroup) powerManagerService2.mPowerGroups.get(0),
                    PowerManagerService.this.mClock.uptimeMillis(),
                    0,
                    0,
                    1000);
                PowerManagerService.this.updatePowerStateLocked();
              }
            }
          }

          public void onDisplayStateChange(boolean z2, boolean z3) {
            synchronized (PowerManagerService.this.mLock) {
              PowerManagerService.this.setPowerModeInternal(9, z2);
              if (z3) {
                if (!PowerManagerService.this.mDecoupleHalInteractiveModeFromDisplayConfig) {
                  PowerManagerService.this.setHalInteractiveModeLocked(false);
                }
                if (!PowerManagerService.this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
                  PowerManagerService.this.setHalAutoSuspendModeLocked(true);
                }
              } else {
                if (!PowerManagerService.this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
                  PowerManagerService.this.setHalAutoSuspendModeLocked(false);
                }
                if (!PowerManagerService.this.mDecoupleHalInteractiveModeFromDisplayConfig) {
                  PowerManagerService.this.setHalInteractiveModeLocked(true);
                }
              }
            }
          }

          public void acquireSuspendBlocker(String str) {
            PowerManagerService.this.mDisplaySuspendBlocker.acquire(str);
            PowerManagerService.this.mSuspendBlockerMonitor.setEventWithDetail(256, str);
          }

          public void releaseSuspendBlocker(String str) {
            PowerManagerService.this.mDisplaySuspendBlocker.release(str);
            PowerManagerService.this.mSuspendBlockerMonitor.clearEventWithDetail(256, str);
          }

          public void onDefaultDisplayStateChange(int i) {
            synchronized (PowerManagerService.this.mLock) {
              PowerManagerService.this.mDefaultDisplayState = i;
              if (!PowerManagerService.this.mDefaultDisplayReadyByProximity && i == 2) {
                Slog.m72d(
                    "PowerManagerService",
                    "onDisplayStateChange: mDefaultDisplayReadyByProximity: true");
                PowerManagerService.this.mDefaultDisplayReadyByProximity = true;
              }
              PowerManagerService.this.mDirty |= 8;
              PowerManagerService.this.updatePowerStateLocked();
            }
          }
        };
    this.mLastUserActivityStateListenerCalledTime = -1L;
    this.mLastUserActivityStateListenerState = 0;
    this.mUserActivityStateListenerTimeoutRunnable =
        new Runnable() { // from class: com.android.server.power.PowerManagerService.5
          @Override // java.lang.Runnable
          public void run() {
            synchronized (PowerManagerService.this.mLock) {
              PowerManagerService.this.mLastUserActivityStateListenerState = 0;
              Slog.m72d(
                  "PowerManagerService",
                  "UserActivityStateListenerState: "
                      + PowerManagerService.this.mLastUserActivityStateListenerState);
              Iterator it = PowerManagerService.this.mUserActivityStateListenerListeners.iterator();
              while (it.hasNext()) {
                ((PowerManagerInternal.UserActivityStateListener) it.next())
                    .onChanged(PowerManagerService.this.mLastUserActivityStateListenerState);
              }
            }
          }
        };
    AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback
        ambientDisplaySuppressionChangedCallback =
            new AmbientDisplaySuppressionController
                .AmbientDisplaySuppressionChangedCallback() { // from class:
                                                              // com.android.server.power.PowerManagerService.6
              @Override // com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback
              public void onSuppressionChanged(boolean z2) {
                synchronized (PowerManagerService.this.mLock) {
                  PowerManagerService.this.onDreamSuppressionChangedLocked(z2);
                }
              }
            };
    this.mAmbientSuppressionChangedCallback = ambientDisplaySuppressionChangedCallback;
    this.mEnsureTransitionToDozingReleaser =
        new Runnable() { // from class: com.android.server.power.PowerManagerService.7
          @Override // java.lang.Runnable
          public void run() {
            synchronized (PowerManagerService.this.mLock) {
              Slog.m72d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
              PowerManagerService.this.mEnsureTransitionToDozingReleaserRunning = false;
              PowerManagerService.this.mDozeStartInProgress = false;
              PowerManagerService.this.updatePowerStateLocked();
            }
          }
        };
    this.mCoverAuthReadyRunnable =
        new Runnable() { // from class: com.android.server.power.PowerManagerService.8
          @Override // java.lang.Runnable
          public void run() {
            synchronized (PowerManagerService.this.mLock) {
              Slog.m72d("PowerManagerService", "mCoverAuthReady since boot");
              PowerManagerService.this.mCoverAuthReady = true;
              PowerManagerService.this.mCoverAuthReadyRunnable = null;
            }
          }
        };
    this.mCoverStateListener =
        new CoverManager
            .CoverStateListener() { // from class: com.android.server.power.PowerManagerService.9
          public void onCoverSwitchStateChanged(boolean z2) {}

          public void onCoverAttachStateChanged(boolean z2) {
            if (PowerManagerService.this.mFeatureCoverSysfs) {
              try {
                if (z2) {
                  CoverState coverState = PowerManagerService.this.mCoverManager.getCoverState();
                  if (coverState != null) {
                    PowerManagerService.this.mCoverType = coverState.getType();
                  }
                } else {
                  PowerManagerService.this.mCoverType = 2;
                }
                Slog.m72d(
                    "PowerManagerService",
                    "onCoverAttachStateChanged: attach: "
                        + z2
                        + "  mCoverType: "
                        + PowerManagerService.this.mCoverType);
              } catch (NullPointerException unused) {
                Slog.m72d(
                    "PowerManagerService",
                    "onCoverAttachStateChanged: mCoverManager returns NULL.");
              }
              PowerManagerService.this.mlastSetCoverTypeTime = SystemClock.uptimeMillis();
              PowerManagerService.this.mlastUpdateCoverTypeReason =
                  "onCoverAttachStateChanged = " + PowerManagerService.this.mCoverType;
            }
          }
        };
    this.mPrintWakeLockAperiodicallyRunnable =
        new Runnable() { // from class: com.android.server.power.PowerManagerService.10
          @Override // java.lang.Runnable
          public void run() {
            synchronized (PowerManagerService.this.mLock) {
              PowerManagerService.this.mHandler.removeCallbacks(this);
              PowerManagerService.this.printWakeLockLocked();
              PowerManagerService.this.mDelayTimePrintWakeLock += 5000;
              PowerManagerService powerManagerService = PowerManagerService.this;
              powerManagerService.mDelayTimePrintWakeLock =
                  Math.min(1800000L, powerManagerService.mDelayTimePrintWakeLock);
              PowerManagerService.this.mHandler.postDelayed(
                  this, PowerManagerService.this.mDelayTimePrintWakeLock);
            }
          }
        };
    this.mBrightnessLimitRunnable =
        new Runnable() { // from class: com.android.server.power.PowerManagerService.11
          @Override // java.lang.Runnable
          public void run() {
            synchronized (PowerManagerService.this.mLock) {
              if (PowerManagerService.this.mLastRequestedLimitationOfBrightness
                  == PowerManagerService.this.mTargetBrightnessForLimit) {
                PowerManagerService.this.mMasterBrightnessLimitRunning = false;
                Slog.m72d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                return;
              }
              PowerManagerService.this.mLastRequestedLimitationOfBrightness +=
                  PowerManagerService.this.mLastRequestedLimitationOfBrightness
                          > PowerManagerService.this.mTargetBrightnessForLimit
                      ? -1
                      : 1;
              PowerManagerService powerManagerService = PowerManagerService.this;
              powerManagerService.updateMasterBrightnessLimitLocked(
                  -1, powerManagerService.mLastRequestedLimitationOfBrightness);
              PowerManagerService.this.mHandler.postAtTime(
                  this,
                  PowerManagerService.this.mClock.uptimeMillis()
                      + PowerManagerService.this.mMasterBrightnessLimitPeriod);
            }
          }
        };
    this.mWirelessChargerSContextListener =
        new SContextListener() { // from class: com.android.server.power.PowerManagerService.12
          public void onSContextChanged(SContextEvent sContextEvent) {
            SContext sContext = sContextEvent.scontext;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (sContext.getType() == 46) {
              int action = sContextEvent.getWirelessChargingDetectionContext().getAction();
              if (action == 0) {
                Slog.m72d("PowerManagerService", "WirelessChargerSContextListener : No Move");
                PowerManagerService.this.mIsDeviceMoving = false;
                return;
              }
              if (action != 1) {
                return;
              }
              Slog.m72d("PowerManagerService", "WirelessChargerSContextListener : Move");
              PowerManagerService powerManagerService = PowerManagerService.this;
              powerManagerService.mIsDeviceMoving = true;
              if (!powerManagerService.mIsWirelessChargerSContextRegistered
                  || powerManagerService.mBatteryManagerInternal.isPowered(4)) {
                return;
              }
              Slog.m72d("PowerManagerService", "SContextListener : Unregister SContextListener");
              PowerManagerService powerManagerService2 = PowerManagerService.this;
              powerManagerService2.mWirelessChargerSContextManager.unregisterListener(
                  powerManagerService2.mWirelessChargerSContextListener, 46);
              PowerManagerService powerManagerService3 = PowerManagerService.this;
              powerManagerService3.mIsWirelessChargerSContextRegistered = false;
              synchronized (powerManagerService3.mLock) {
                if (elapsedRealtime - PowerManagerService.this.mPassedWakeupTime < 300) {
                  Slog.m72d(
                      "PowerManagerService",
                      "WirelessChargerSContextListener : received move lately");
                  PowerManagerService.this.mPassedWakeupTime = 0L;
                  PowerManagerService.this.mDirty |= 64;
                  PowerManagerService.this.mScreenOnReason = " powered change";
                  PowerManagerService powerManagerService4 = PowerManagerService.this;
                  powerManagerService4.wakePowerGroupLocked(
                      (PowerGroup) powerManagerService4.mPowerGroups.get(0),
                      SystemClock.uptimeMillis(),
                      3,
                      "android.server.power:POWER",
                      1000,
                      PowerManagerService.this.mContext.getOpPackageName(),
                      1000);
                  PowerManagerService.this.updatePowerStateLocked();
                }
              }
            }
          }
        };
    this.mContext = context;
    this.mBinderService = new BinderService(context);
    this.mLocalService = new LocalService();
    NativeWrapper createNativeWrapper = injector.createNativeWrapper();
    this.mNativeWrapper = createNativeWrapper;
    SystemPropertiesWrapper createSystemPropertiesWrapper =
        injector.createSystemPropertiesWrapper();
    this.mSystemProperties = createSystemPropertiesWrapper;
    this.mClock = injector.createClock();
    this.mInjector = injector;
    ServiceThread serviceThread = new ServiceThread("PowerManagerService", -4, false);
    this.mHandlerThread = serviceThread;
    serviceThread.start();
    Handler createHandler =
        injector.createHandler(serviceThread.getLooper(), new PowerManagerHandlerCallback());
    this.mHandler = createHandler;
    serviceThread.getLooper().setSlowLogThresholdMs(100L, 110L);
    HandlerThread handlerThread = new HandlerThread("pms.misc");
    this.mHandlerThreadPmsMisc = handlerThread;
    handlerThread.start();
    this.mHandlerPmsMisc = injector.createHandler(this.mHandlerThreadPmsMisc.getLooper(), null);
    this.mConstants = new Constants(createHandler);
    this.mAmbientDisplayConfiguration = injector.createAmbientDisplayConfiguration(context);
    this.mAmbientDisplaySuppressionController =
        injector.createAmbientDisplaySuppressionController(
            ambientDisplaySuppressionChangedCallback);
    this.mAttentionDetector =
        new AttentionDetector(
            new Runnable() { // from class:
                             // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda7
              @Override // java.lang.Runnable
              public final void run() {
                PowerManagerService.this.onUserAttention();
              }
            },
            installNewLock);
    this.mScreenUndimDetector = new ScreenUndimDetector();
    BatterySavingStats batterySavingStats = new BatterySavingStats(installNewLock);
    this.mBatterySavingStats = batterySavingStats;
    BatterySaverPolicy createBatterySaverPolicy =
        injector.createBatterySaverPolicy(installNewLock, context, batterySavingStats);
    this.mBatterySaverPolicy = createBatterySaverPolicy;
    BatterySaverController createBatterySaverController =
        injector.createBatterySaverController(
            installNewLock, context, createBatterySaverPolicy, batterySavingStats);
    this.mBatterySaverController = createBatterySaverController;
    this.mBatterySaverStateMachine =
        injector.createBatterySaverStateMachine(
            installNewLock, context, createBatterySaverController);
    this.mLowPowerStandbyController =
        injector.createLowPowerStandbyController(context, Looper.getMainLooper());
    this.mInattentiveSleepWarningOverlayController =
        injector.createInattentiveSleepWarningController();
    this.mPermissionCheckerWrapper = injector.createPermissionCheckerWrapper();
    this.mPowerPropertiesWrapper = injector.createPowerPropertiesWrapper();
    this.mDeviceConfigProvider = injector.createDeviceConfigParameterProvider();
    this.mPowerGroupWakefulnessChangeListener = new PowerGroupWakefulnessChangeListener();
    float f = context.getResources().getFloat(R.dimen.config_pictureInPictureMaxAspectRatio);
    float f2 =
        context.getResources().getFloat(R.dimen.config_pictureInPictureExpandedVerticalWidth);
    float f3 =
        context.getResources().getFloat(R.dimen.config_pictureInPictureExpandedHorizontalHeight);
    float f4 = context.getResources().getFloat(R.dimen.config_minScalingTouchMajor);
    float f5 = context.getResources().getFloat(R.dimen.config_minScalingSpan);
    this.mScreenExtendedBrightnessMaximum =
        context.getResources().getInteger(R.integer.config_screenTimeoutOverride);
    if (f == -2.0f || f2 == -2.0f || f3 == -2.0f) {
      this.mScreenBrightnessMinimum =
          BrightnessSynchronizer.brightnessIntToFloat(
              context.getResources().getInteger(R.integer.config_screen_rotation_fade_out));
      this.mScreenBrightnessMaximum =
          BrightnessSynchronizer.brightnessIntToFloat(
              context.getResources().getInteger(R.integer.config_screen_rotation_fade_in_delay));
      this.mScreenBrightnessDefault =
          BrightnessSynchronizer.brightnessIntToFloat(
              context.getResources().getInteger(R.integer.config_screen_rotation_fade_in));
    } else {
      this.mScreenBrightnessMinimum = f;
      this.mScreenBrightnessMaximum = f2;
      this.mScreenBrightnessDefault = f3;
    }
    if (f4 == -2.0f) {
      this.mScreenBrightnessDoze =
          BrightnessSynchronizer.brightnessIntToFloat(
              context.getResources().getInteger(R.integer.config_screenBrightnessSettingMinimum));
    } else {
      this.mScreenBrightnessDoze = f4;
    }
    if (f5 == -2.0f) {
      this.mScreenBrightnessDim =
          BrightnessSynchronizer.brightnessIntToFloat(
              context.getResources().getInteger(R.integer.config_screenBrightnessSettingMaximum));
    } else {
      this.mScreenBrightnessDim = f5;
    }
    synchronized (installNewLock) {
      SuspendBlocker createSuspendBlocker =
          injector.createSuspendBlocker(this, "PowerManagerService.Booting");
      this.mBootingSuspendBlocker = createSuspendBlocker;
      this.mWakeLockSuspendBlocker =
          injector.createSuspendBlocker(this, "PowerManagerService.WakeLocks");
      SuspendBlocker createSuspendBlocker2 =
          injector.createSuspendBlocker(this, "PowerManagerService.Display");
      this.mDisplaySuspendBlocker = createSuspendBlocker2;
      if (createSuspendBlocker != null) {
        createSuspendBlocker.acquire();
        this.mHoldingBootingSuspendBlocker = true;
      }
      if (createSuspendBlocker2 != null) {
        createSuspendBlocker2.acquire("holding display");
        this.mHoldingDisplaySuspendBlocker = true;
      }
      this.mHalAutoSuspendModeEnabled = false;
      setHalInteractiveModeLocked(true);
      this.mWakefulnessRaw = 1;
      if (!createSystemPropertiesWrapper.get("ro.boot.quiescent", "0").equals("1")
          && !((Boolean) InitProperties.userspace_reboot_in_progress().orElse(Boolean.FALSE))
              .booleanValue()) {
        z = false;
        sQuiescent = z;
        createNativeWrapper.nativeInit(this);
        createNativeWrapper.nativeSetAutoSuspend(false);
        createNativeWrapper.nativeSetPowerMode(7, true);
        createNativeWrapper.nativeSetPowerMode(0, false);
        injector.invalidateIsInteractiveCaches();
      }
      z = true;
      sQuiescent = z;
      createNativeWrapper.nativeInit(this);
      createNativeWrapper.nativeSetAutoSuspend(false);
      createNativeWrapper.nativeSetPowerMode(7, true);
      createNativeWrapper.nativeSetPowerMode(0, false);
      injector.invalidateIsInteractiveCaches();
    }
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("power", this.mBinderService, false, 9);
    publishLocalService(PowerManagerInternal.class, this.mLocalService);
    Watchdog.getInstance().addMonitor(this);
    Watchdog.getInstance().addThread(this.mHandler);
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i == 500) {
      systemReady();
      return;
    }
    if (i == 600) {
      incrementBootCount();
      return;
    }
    if (i == 1000) {
      synchronized (this.mLock) {
        long uptimeMillis = this.mClock.uptimeMillis();
        this.mBootCompleted = true;
        this.mDirty |= 16;
        this.mBatterySaverStateMachine.onBootCompleted();
        userActivityNoUpdateLocked(uptimeMillis, 0, 0, 1000);
        updatePowerStateLocked();
        if (sQuiescent) {
          sleepPowerGroupLocked(
              (PowerGroup) this.mPowerGroups.get(0), this.mClock.uptimeMillis(), 10, 1000);
        }
        ((DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class))
            .registerCallback(new HandlerExecutor(this.mHandler), new DeviceStateListener());
        this.mHandler.postDelayed(this.mCoverAuthReadyRunnable, 15000L);
        if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW) {
          this.mHandlerPmsMisc.post(
              new Runnable() { // from class:
                               // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManagerService.this.handleDexScreenOffTimeoutChange();
                }
              });
        }
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final void systemReady() {
    Object[] objArr;
    Object[] objArr2;
    Object[] objArr3;
    synchronized (this.mLock) {
      this.mSystemReady = true;
      this.mDreamManager = (DreamManagerInternal) getLocalService(DreamManagerInternal.class);
      this.mDisplayManagerInternal =
          (DisplayManagerInternal) getLocalService(DisplayManagerInternal.class);
      this.mPolicy = (WindowManagerPolicy) getLocalService(WindowManagerPolicy.class);
      this.mBatteryManagerInternal =
          (BatteryManagerInternal) getLocalService(BatteryManagerInternal.class);
      this.mAmInternal = (ActivityManagerInternal) getLocalService(ActivityManagerInternal.class);
      this.mAttentionDetector.systemReady(this.mContext);
      SensorManager systemSensorManager =
          new SystemSensorManager(this.mContext, this.mHandler.getLooper());
      this.mBatteryStats = BatteryStatsService.getService();
      this.mNotifier =
          this.mInjector.createNotifier(
              Looper.getMainLooper(),
              this.mContext,
              this.mBatteryStats,
              this.mInjector.createSuspendBlocker(this, "PowerManagerService.Broadcasts"),
              this.mPolicy,
              null,
              this.mScreenUndimDetector,
              BackgroundThread.getExecutor());
      this.mPowerGroups.append(
          0,
          new PowerGroup(
              1,
              this.mPowerGroupWakefulnessChangeListener,
              this.mNotifier,
              this.mDisplayManagerInternal,
              this.mClock.uptimeMillis()));
      objArr3 = 0;
      objArr2 = 0;
      objArr = 0;
      Object[] objArr4 = 0;
      this.mDisplayManagerInternal.registerDisplayGroupListener(
          new DisplayGroupPowerChangeListener());
      this.mDreamManager.registerDreamManagerStateListener(new DreamManagerStateListener());
      Injector injector = this.mInjector;
      this.mWirelessChargerDetector =
          injector.createWirelessChargerDetector(
              systemSensorManager,
              injector.createSuspendBlocker(this, "PowerManagerService.WirelessChargerDetector"),
              this.mHandler);
      this.mSettingsObserver = new SettingsObserver(this.mHandler);
      LightsManager lightsManager = (LightsManager) getLocalService(LightsManager.class);
      this.mLightsManager = lightsManager;
      this.mAttentionLight = lightsManager.getLight(5);
      updateDeviceConfigLocked();
      this.mDeviceConfigProvider.addOnPropertiesChangedListener(
          BackgroundThread.getExecutor(),
          new DeviceConfig
              .OnPropertiesChangedListener() { // from class:
                                               // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda9
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
              PowerManagerService.this.lambda$systemReady$0(properties);
            }
          });
      this.mDisplayManagerInternal.initPowerManagement(
          this.mDisplayPowerCallbacks, this.mHandler, systemSensorManager);
      addPowerGroupsForNonDefaultDisplayGroupLocked();
      try {
        ActivityManager.getService()
            .registerUserSwitchObserver(new ForegroundProfileObserver(), "PowerManagerService");
      } catch (RemoteException unused) {
      }
      if (this.mFeatureCoverSysfs) {
        CoverManager createCoverManager = this.mInjector.createCoverManager(this.mContext);
        this.mCoverManager = createCoverManager;
        if (createCoverManager != null) {
          createCoverManager.registerListener(this.mCoverStateListener);
        } else {
          Slog.m72d("PowerManagerService", "mCoverManager is null!!!!");
        }
      }
      if (this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
        SContextManager sContextManager =
            (SContextManager) this.mContext.getSystemService("scontext");
        this.mWirelessChargerSContextManager = sContextManager;
        if (sContextManager != null) {
          this.mIsWirelessChargerSContextEnabled = sContextManager.isAvailableService(46);
          Slog.m72d(
              "PowerManagerService",
              "start : (mWirelessChargerSContextManager != null - mIsWirelessChargerSContextEnabled"
                  + " = "
                  + this.mIsWirelessChargerSContextEnabled);
        }
      }
      this.mAbuseWakeLockDetector =
          this.mInjector.createAbuseWakeLockDetector(
              this.mLock, this.mContext, this.mAbuseWakeLockDetectorCallback);
      this.mSmartStayController =
          this.mInjector.createSmartStayController(
              this.mLock,
              this.mContext,
              new Runnable() { // from class:
                               // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManagerService.this.lambda$systemReady$1();
                }
              });
      this.mLowPowerStandbyController.systemReady();
      HqmDataDispatcher.init(this.mContext, this.mHandlerPmsMisc.getLooper());
      if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN) {
        this.mScreenCurtainController =
            this.mInjector.createScreenCurtainController(
                this.mContext, this.mLock, this.mHandler.getLooper());
      }
      readConfigurationLocked();
      updateSettingsLocked();
      this.mDirty |= 256;
      updatePowerStateLocked();
    }
    ContentResolver contentResolver = this.mContext.getContentResolver();
    this.mConstants.start(contentResolver);
    this.mBatterySaverController.systemReady();
    this.mBatterySaverPolicy.systemReady();
    this.mScreenUndimDetector.systemReady(this.mContext);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("screensaver_enabled"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("screensaver_activate_on_sleep"),
        false,
        this.mSettingsObserver,
        -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("screensaver_activate_on_dock"),
        false,
        this.mSettingsObserver,
        -1);
    contentResolver.registerContentObserver(
        Settings.System.getUriFor("screen_off_timeout"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("sleep_timeout"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("attentive_timeout"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("stay_on_while_plugged_in"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.System.getUriFor("screen_auto_brightness_adj"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("theater_mode_on"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("doze_always_on"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Secure.getUriFor("double_tap_to_wake"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("device_demo_mode"), false, this.mSettingsObserver, 0);
    contentResolver.registerContentObserver(
        Settings.System.getUriFor("user_activity_timeout"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.System.getUriFor("intelligent_sleep_mode"), false, this.mSettingsObserver, -1);
    contentResolver.registerContentObserver(
        Settings.System.getUriFor("display_outdoor_mode"), false, this.mSettingsObserver, -1);
    setQuickPanelBrightnessBarDisabled(false);
    if (PowerManagerUtil.SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED) {
      contentResolver.registerContentObserver(
          Settings.System.getUriFor("aod_mode"), false, this.mSettingsObserver, -1);
    }
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("sem_power_saving_adjust_brightness_factor"),
        false,
        this.mSettingsObserver,
        -1);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    intentFilter.setPriority(1000);
    this.mContext.registerReceiver(new BatteryReceiver(), intentFilter, null, this.mHandler);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.DREAMING_STARTED");
    intentFilter2.addAction("android.intent.action.DREAMING_STOPPED");
    this.mContext.registerReceiver(new DreamReceiver(), intentFilter2, null, this.mHandler);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("android.intent.action.USER_SWITCHED");
    this.mContext.registerReceiver(new UserSwitchedReceiver(), intentFilter3, null, this.mHandler);
    this.mNoUserActivityIntent =
        new Intent("com.samsung.android.knox.intent.action.NO_USER_ACTIVITY");
    this.mUserActivityIntent = new Intent("com.samsung.android.knox.intent.action.USER_ACTIVITY");
    this.mNoUserActivitySent = false;
    IntentFilter intentFilter4 = new IntentFilter();
    intentFilter4.addAction("com.sec.factory.app.factorytest.FTA_ON");
    intentFilter4.addAction("com.sec.factory.app.factorytest.FTA_OFF");
    this.mContext.registerReceiver(new FTAModeChangeReceiver(), intentFilter4, null, this.mHandler);
    if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW) {
      SemDesktopModeManager semDesktopModeManager =
          (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
      if (semDesktopModeManager != null) {
        semDesktopModeManager.registerListener(
            new SemDesktopModeManager
                .DesktopModeListener() { // from class:
                                         // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda11
              public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                PowerManagerService.this.lambda$systemReady$2(semDesktopModeState);
              }
            });
      }
      contentResolver.registerContentObserver(
          Uri.withAppendedPath(DEX_SETTINGS_URI, "timeout_dex"),
          true,
          new DexScreenOffTimeoutObserver(this.mHandlerPmsMisc));
      Intent intent =
          new Intent("com.samsung.android.server.power.ACTION_INTERNAL_DISPLAY_OFF_BY_POWERKEY");
      this.mInternalDisplayOffByPowerKeyIntent = intent;
      intent.addFlags(1342177280);
    }
    this.mSemInputDeviceManager =
        (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
    IntentFilter intentFilter5 = new IntentFilter();
    intentFilter5.addAction("android.intent.action.DOCK_EVENT");
    this.mContext.registerReceiver(new DockReceiver(), intentFilter5, null, this.mHandler);
    if (PowerManagerUtil.fileExist("/sys/class/lcd/panel/display_on")) {
      return;
    }
    PowerManagerUtil.sCurrentScreenOnProfiler.disableFrameCheck();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$systemReady$0(DeviceConfig.Properties properties) {
    synchronized (this.mLock) {
      updateDeviceConfigLocked();
      updateWakeLockDisabledStatesLocked();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$systemReady$1() {
    userActivityNoUpdateLocked(
        (PowerGroup) this.mPowerGroups.get(0), this.mClock.uptimeMillis(), 0, 0, 1000);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$systemReady$2(SemDesktopModeState semDesktopModeState) {
    synchronized (this.mLock) {
      boolean z = this.mIsDualViewMode;
      int displayType = semDesktopModeState.getDisplayType();
      int enabled = semDesktopModeState.getEnabled();
      this.mIsDualViewMode = displayType == 102 && (enabled == 4 || enabled == 3);
      Slog.m72d(
          "PowerManagerService",
          "Dex display type: "
              + displayType
              + ", enabled: "
              + enabled
              + ", dual view: "
              + z
              + " -> "
              + this.mIsDualViewMode);
      if (z != this.mIsDualViewMode && this.mPowerGroups.contains(2)) {
        if (this.mIsDualViewMode) {
          setWakefulnessLocked(
              2,
              1,
              this.mClock.uptimeMillis(),
              1000,
              11,
              1000,
              this.mContext.getOpPackageName(),
              "dex enabled");
        } else {
          setWakefulnessLocked(
              2,
              0,
              this.mClock.uptimeMillis(),
              1000,
              12,
              1000,
              this.mContext.getOpPackageName(),
              "dex disabled");
        }
        updatePowerStateLocked();
      }
    }
  }

  public void readConfigurationLocked() {
    Resources resources = this.mContext.getResources();
    this.mDecoupleHalAutoSuspendModeFromDisplayConfig = true;
    this.mDecoupleHalInteractiveModeFromDisplayConfig = true;
    this.mWakeUpWhenPluggedOrUnpluggedConfig = true;
    this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig =
        resources.getBoolean(R.bool.config_allowTheaterModeWakeFromUnplug);
    this.mSuspendWhenScreenOffDueToProximityConfig = true;
    this.mAttentiveTimeoutConfig =
        resources.getInteger(R.integer.config_bg_current_drain_types_to_bg_restricted);
    this.mAttentiveWarningDurationConfig =
        resources.getInteger(R.integer.config_bg_current_drain_types_to_restricted_bucket);
    this.mDreamsSupportedConfig = resources.getBoolean(R.bool.config_disable_all_cb_messages);
    this.mDreamsEnabledByDefaultConfig =
        resources.getBoolean(R.bool.config_disableMenuKeyInLockScreen);
    this.mDreamsActivatedOnSleepByDefaultConfig =
        resources.getBoolean(R.bool.config_device_wfc_ims_available);
    this.mDreamsActivatedOnDockByDefaultConfig =
        resources.getBoolean(R.bool.config_device_vt_available);
    this.mDreamsEnabledOnBatteryConfig = resources.getBoolean(R.bool.config_disableTaskSnapshots);
    this.mDreamsBatteryLevelMinimumWhenPoweredConfig =
        resources.getInteger(R.integer.config_jobSchedulerInactivityIdleThreshold);
    this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig =
        resources.getInteger(R.integer.config_jobSchedulerIdleWindowSlop);
    this.mDreamsBatteryLevelDrainCutoffConfig =
        resources.getInteger(R.integer.config_immersive_mode_confirmation_panic);
    this.mDreamsDisabledByAmbientModeSuppressionConfig =
        resources.getBoolean(R.bool.config_disableLockscreenByDefault);
    this.mDozeAfterScreenOff = resources.getBoolean(R.bool.config_defaultRingtonePickerEnabled);
    this.mMinimumScreenOffTimeoutConfig =
        resources.getInteger(R.integer.config_nightDisplayColorTemperatureMax);
    this.mMaximumScreenDimDurationConfig = 20000L;
    this.mMaximumScreenDimRatioConfig = 0.333f;
    this.mSupportsDoubleTapWakeConfig = resources.getBoolean(R.bool.config_sf_limitedAlpha);
    this.mScreenBrightnessForClearCoverConfig =
        BrightnessSynchronizer.brightnessIntToFloat(
            resources.getInteger(R.integer.config_screen_magnification_multi_tap_adjustment));
    this.mBrightnessLimitByCoverConfig =
        resources.getInteger(R.integer.config_datause_polling_period_sec);
  }

  public final void updateSettingsLocked() {
    String str;
    ContentResolver contentResolver = this.mContext.getContentResolver();
    this.mDreamsEnabledSetting =
        Settings.Secure.getIntForUser(
                contentResolver,
                "screensaver_enabled",
                this.mDreamsEnabledByDefaultConfig ? 1 : 0,
                -2)
            != 0;
    this.mDreamsActivateOnSleepSetting =
        Settings.Secure.getIntForUser(
                contentResolver,
                "screensaver_activate_on_sleep",
                this.mDreamsActivatedOnSleepByDefaultConfig ? 1 : 0,
                -2)
            != 0;
    this.mDreamsActivateOnDockSetting =
        Settings.Secure.getIntForUser(
                contentResolver,
                "screensaver_activate_on_dock",
                this.mDreamsActivatedOnDockByDefaultConfig ? 1 : 0,
                -2)
            != 0;
    this.mScreenOffTimeoutSetting =
        Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 15000, -2);
    this.mSleepTimeoutSetting =
        Settings.Secure.getIntForUser(contentResolver, "sleep_timeout", -1, -2);
    this.mAttentiveTimeoutSetting =
        Settings.Secure.getIntForUser(
            contentResolver, "attentive_timeout", this.mAttentiveTimeoutConfig, -2);
    this.mStayOnWhilePluggedInSetting =
        Settings.Global.getInt(contentResolver, "stay_on_while_plugged_in", 1);
    this.mTheaterModeEnabled =
        Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1;
    this.mAlwaysOnEnabled = this.mAmbientDisplayConfiguration.alwaysOnEnabled(-2);
    this.mUserActivityTimeoutSetting =
        Settings.System.getIntForUser(contentResolver, "user_activity_timeout", 0, -2);
    boolean z =
        Settings.System.getIntForUser(contentResolver, "intelligent_sleep_mode", 0, -2) != 0;
    this.mSmartStayEnabledSetting = z;
    this.mSmartStayController.setSmartStayLocked(
        z, ((PowerGroup) this.mPowerGroups.get(0)).getWakefulnessLocked());
    boolean z2 = this.mOutdoorModeSetting;
    boolean z3 = Settings.System.getIntForUser(contentResolver, "display_outdoor_mode", 0, -2) == 1;
    this.mOutdoorModeSetting = z3;
    if (z2 != z3 && z3) {
      this.mLastOutdoorModeEnabledTime = this.mClock.elapsedRealtime();
      Slog.m72d(
          "PowerManagerService", "[api] updateSettingsLocked : Update OutdoorMode enabled time");
    }
    if (this.mSupportsDoubleTapWakeConfig) {
      boolean z4 = Settings.Secure.getIntForUser(contentResolver, "double_tap_to_wake", 0, -2) != 0;
      if (z4 != this.mDoubleTapWakeEnabled) {
        this.mDoubleTapWakeEnabled = z4;
        this.mNativeWrapper.nativeSetPowerMode(0, z4);
      }
    }
    String str2 = UserManager.isDeviceInDemoMode(this.mContext) ? "1" : "0";
    if (!str2.equals(this.mSystemProperties.get("sys.retaildemo.enabled", null))) {
      this.mSystemProperties.set("sys.retaildemo.enabled", str2);
    }
    boolean z5 = PowerManagerUtil.SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED;
    if (z5) {
      this.mAodMode = Settings.System.getIntForUser(contentResolver, "aod_mode", 0, -2) != 0;
    }
    this.mBatterySavingAdjustBrightnessFactor =
        Settings.Global.getFloat(
            contentResolver, "sem_power_saving_adjust_brightness_factor", 1.0f);
    StringBuilder sb = new StringBuilder();
    sb.append("[api] updateSettingsLocked : mDreamsEnabledSetting: ");
    sb.append(this.mDreamsEnabledSetting);
    sb.append(" mDreamsActivateOnSleepSetting: ");
    sb.append(this.mDreamsActivateOnSleepSetting);
    sb.append(" mDreamsActivateOnDockSetting: ");
    sb.append(this.mDreamsActivateOnDockSetting);
    sb.append(" mScreenOffTimeoutSetting: ");
    sb.append(this.mScreenOffTimeoutSetting);
    sb.append(" mSleepTimeoutSetting: ");
    sb.append(this.mSleepTimeoutSetting);
    sb.append(" mStayOnWhilePluggedInSetting: ");
    sb.append(this.mStayOnWhilePluggedInSetting);
    sb.append(" mTheaterModeEnabled: ");
    sb.append(this.mTheaterModeEnabled);
    sb.append(" mAlwaysOnEnabled: ");
    sb.append(this.mAlwaysOnEnabled);
    sb.append(" mSmartStayEnabledSetting: ");
    sb.append(this.mSmartStayEnabledSetting);
    sb.append(" mDoubleTapWakeEnabled: ");
    sb.append(this.mDoubleTapWakeEnabled);
    sb.append(" retailDemoValue: ");
    sb.append(str2);
    sb.append(" mBatterySavingAdjustBrightnessFactor: ");
    sb.append(this.mBatterySavingAdjustBrightnessFactor);
    if (z5) {
      str = " mAodMode: " + this.mAodMode;
    } else {
      str = "";
    }
    sb.append(str);
    Slog.m72d("PowerManagerService", sb.toString());
    this.mDirty |= 32;
  }

  public void handleSettingsChangedLocked() {
    updateSettingsLocked();
    updatePowerStateLocked();
  }

  public final void updateDeviceConfigLocked() {
    this.mDisableScreenWakeLocksWhileCached =
        this.mDeviceConfigProvider.isDisableScreenWakeLocksWhileCachedFeatureEnabled();
  }

  public final void acquireWakeLockInternal(
      IBinder iBinder,
      int i,
      int i2,
      String str,
      String str2,
      WorkSource workSource,
      String str3,
      int i3,
      int i4,
      IWakeLockCallback iWakeLockCallback,
      boolean z) {
    WakeLock wakeLock;
    boolean z2;
    boolean z3;
    boolean z4;
    synchronized (this.mLock) {
      if (i != -1) {
        DisplayInfo displayInfo =
            this.mSystemReady ? this.mDisplayManagerInternal.getDisplayInfo(i) : null;
        if (displayInfo == null) {
          Slog.wtf("PowerManagerService", "Tried to acquire wake lock for invalid display: " + i);
          return;
        }
        if (!displayInfo.hasAccess(i3)) {
          throw new SecurityException("Caller does not have access to display");
        }
      }
      int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
      if (findWakeLockIndexLocked >= 0) {
        wakeLock = (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked);
        if (wakeLock.hasSameProperties(i2, str, workSource, i3, i4, iWakeLockCallback)) {
          z4 = true;
        } else {
          notifyWakeLockChangingLocked(
              wakeLock, i2, str, str2, i3, i4, workSource, str3, iWakeLockCallback);
          wakeLock.updateProperties(i2, str, str2, workSource, str3, i3, i4, iWakeLockCallback);
          z4 = false;
        }
        z3 = z4;
        z2 = false;
      } else {
        UidState uidState = (UidState) this.mUidState.get(i3);
        if (uidState == null) {
          uidState = new UidState(i3);
          uidState.mProcState = 20;
          this.mUidState.put(i3, uidState);
        }
        UidState uidState2 = uidState;
        uidState2.mNumWakeLocks++;
        WakeLock wakeLock2 =
            new WakeLock(
                iBinder, i, i2, str, str2, workSource, str3, i3, i4, uidState2, iWakeLockCallback);
        this.mWakeLocks.add(wakeLock2);
        setWakeLockDisabledStateLocked(wakeLock2);
        wakeLock = wakeLock2;
        z2 = true;
        z3 = false;
      }
      if (z3) {
        wakeLock.mConsecutiveAcquireCount++;
      } else {
        int i5 = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
        if (i5 == 6 || i5 == 10 || i5 == 26 || i5 == 32 || i5 == 64 || i5 == 128) {
          Slog.m72d("PowerManagerService", "[api] acquire WakeLock " + wakeLock);
        }
      }
      applyWakeLockFlagsOnAcquireLocked(wakeLock, z);
      this.mDirty |= 1;
      updatePowerStateLocked();
      if (z2) {
        notifyWakeLockAcquiredLocked(wakeLock);
      }
    }
  }

  public final void setProximityDebounceTimeInternal(IBinder iBinder, int i, int i2) {
    synchronized (this.mLock) {
      WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked(iBinder));
      wakeLock.mProximityPositiveDebounce = i;
      wakeLock.mProximityNegativeDebounce = i2;
      this.mDirty |= 1;
      updatePowerStateLocked();
    }
  }

  public static boolean isScreenLock(WakeLock wakeLock) {
    int i = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
    return i == 6 || i == 10 || i == 26;
  }

  public static WorkSource.WorkChain getFirstNonEmptyWorkChain(WorkSource workSource) {
    if (workSource.getWorkChains() == null) {
      return null;
    }
    for (WorkSource.WorkChain workChain : workSource.getWorkChains()) {
      if (workChain.getSize() > 0) {
        return workChain;
      }
    }
    return null;
  }

  public final boolean isAcquireCausesWakeupFlagAllowed(String str, int i, int i2) {
    if (str == null) {
      return false;
    }
    if (this.mPermissionCheckerWrapper.checkPermissionForDataDelivery(
            this.mContext,
            "android.permission.TURN_SCREEN_ON",
            i2,
            new AttributionSource(i, str, null),
            "ACQUIRE_CAUSES_WAKEUP for " + str)
        == 0) {
      Slog.m76i("PowerManagerService", "Allowing device wake-up from app " + str);
      return true;
    }
    if (!CompatChanges.isChangeEnabled(216114297L, i)
        && !this.mPowerPropertiesWrapper.waive_target_sdk_check_for_turn_screen_on()) {
      Slog.m76i(
          "PowerManagerService",
          "Allowing device wake-up without android.permission.TURN_SCREEN_ON for " + str);
      return true;
    }
    if (this.mPowerPropertiesWrapper.permissionless_turn_screen_on()) {
      Slog.m72d(
          "PowerManagerService",
          "Device wake-up allowed by debug.power.permissionless_turn_screen_on");
      return true;
    }
    Slog.m79w("PowerManagerService", "Not allowing device wake-up for " + str);
    return false;
  }

  public final void applyWakeLockFlagsOnAcquireLocked(WakeLock wakeLock, boolean z) {
    int i;
    int i2;
    String str;
    String str2;
    if ((wakeLock.mFlags & 268435456) == 0 || !isScreenLock(wakeLock)) {
      return;
    }
    WorkSource workSource = wakeLock.mWorkSource;
    if (workSource != null && !workSource.isEmpty()) {
      WorkSource workSource2 = wakeLock.mWorkSource;
      WorkSource.WorkChain firstNonEmptyWorkChain = getFirstNonEmptyWorkChain(workSource2);
      if (firstNonEmptyWorkChain != null) {
        str2 = firstNonEmptyWorkChain.getAttributionTag();
        i = firstNonEmptyWorkChain.getAttributionUid();
      } else {
        String packageName =
            workSource2.getPackageName(0) != null
                ? workSource2.getPackageName(0)
                : wakeLock.mPackageName;
        i = workSource2.getUid(0);
        str2 = packageName;
      }
      str = str2;
      i2 = -1;
    } else {
      String str3 = wakeLock.mPackageName;
      i = wakeLock.mOwnerUid;
      i2 = wakeLock.mOwnerPid;
      str = str3;
    }
    int i3 = i;
    if (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
        && i3 >= 10000
        && this.mDualScreenPolicy == 1) {
      try {
        if (i3
            != this.mContext.getPackageManager().getPackageUidAsUser("com.android.systemui", 0)) {
          Slog.m73dk(
              "PowerManagerService",
              "Screen__On : Cancel (not allow 3rd-party app on sub screen : " + str + ")");
          return;
        }
      } catch (PackageManager.NameNotFoundException e) {
        Slog.m75e("PowerManagerService", "Unable to resolve SystemUI's UID.", e);
      }
    }
    if (i3 >= 10000 && !z) {
      Slog.m72d(
          "PowerManagerService", "Screen__On : Cancel (notifications are disabled : " + str + ")");
      return;
    }
    if (isWakeUpPreventionNeededForEdgeLightingLocked(wakeLock)) {
      Slog.m72d(
          "PowerManagerService",
          "applyWakeLockFlagsOnAcquireLocked return by isWakeUpPreventionNeededForEdgeLighting");
      return;
    }
    this.mScreenOnReason = " wl: " + wakeLock.mTag;
    Integer powerGroupId = wakeLock.getPowerGroupId();
    if (powerGroupId == null || !isAcquireCausesWakeupFlagAllowed(str, i3, i2)) {
      return;
    }
    if (powerGroupId.intValue() == -1) {
      for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
        wakePowerGroupLocked(
            (PowerGroup) this.mPowerGroups.valueAt(i4),
            this.mClock.uptimeMillis(),
            2,
            wakeLock.mTag,
            i3,
            str,
            i3,
            true);
      }
      return;
    }
    if (this.mPowerGroups.contains(powerGroupId.intValue())) {
      wakePowerGroupLocked(
          (PowerGroup) this.mPowerGroups.get(powerGroupId.intValue()),
          this.mClock.uptimeMillis(),
          2,
          wakeLock.mTag,
          i3,
          str,
          i3,
          true);
    }
  }

  public final void releaseWakeLockInternal(IBinder iBinder, int i) {
    synchronized (this.mLock) {
      int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
      if (findWakeLockIndexLocked < 0) {
        return;
      }
      WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked);
      int i2 = i & 1;
      if (i2 != 0) {
        this.mRequestWaitForNegativeProximity = true;
      }
      this.mPowerHistorian.onWakeLockReleased(wakeLock);
      int i3 = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
      if (i3 == 6 || i3 == 10 || i3 == 26 || i3 == 32 || i3 == 64 || i3 == 128) {
        String str = "";
        if (i2 != 0) {
          str = " (WAIT_FOR_NO_PROXIMITY)";
        }
        Slog.m72d("PowerManagerService", "[api] release WakeLock " + wakeLock + str);
      }
      wakeLock.unlinkToDeath();
      wakeLock.setDisabled(true, 0);
      removeWakeLockLocked(wakeLock, findWakeLockIndexLocked);
    }
  }

  public final void handleWakeLockDeath(WakeLock wakeLock) {
    Slog.m72d("PowerManagerService", "[api] handleWakeLockDeath : release WakeLock : " + wakeLock);
    synchronized (this.mLock) {
      int indexOf = this.mWakeLocks.indexOf(wakeLock);
      if (indexOf < 0) {
        return;
      }
      removeWakeLockLocked(wakeLock, indexOf);
    }
  }

  public final void removeWakeLockLocked(WakeLock wakeLock, int i) {
    this.mWakeLocks.remove(i);
    UidState uidState = wakeLock.mUidState;
    int i2 = uidState.mNumWakeLocks - 1;
    uidState.mNumWakeLocks = i2;
    if (i2 <= 0 && uidState.mProcState == 20) {
      this.mUidState.remove(uidState.mUid);
    }
    notifyWakeLockReleasedLocked(wakeLock);
    applyWakeLockFlagsOnReleaseLocked(wakeLock);
    this.mDirty |= 1;
    updatePowerStateLocked();
  }

  public final void applyWakeLockFlagsOnReleaseLocked(WakeLock wakeLock) {
    if ((wakeLock.mFlags & 536870912) == 0 || !isScreenLock(wakeLock)) {
      return;
    }
    Slog.m72d(
        "PowerManagerService",
        "[api] applyWakeLockFlagsOnReleaseLocked : userActivityNoUpdateLocked is called : "
            + wakeLock);
    if (wakeLock.getPowerGroupId().intValue() != -1) {
      userActivityNoUpdateLocked(
          (PowerGroup) this.mPowerGroups.get(wakeLock.getPowerGroupId().intValue()),
          this.mClock.uptimeMillis(),
          0,
          1,
          wakeLock.mOwnerUid);
    } else {
      userActivityNoUpdateLocked(this.mClock.uptimeMillis(), 0, 1, wakeLock.mOwnerUid);
    }
  }

  public final void updateWakeLockWorkSourceInternal(
      IBinder iBinder, WorkSource workSource, String str, int i) {
    synchronized (this.mLock) {
      int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
      if (findWakeLockIndexLocked < 0) {
        throw new IllegalArgumentException("Wake lock not active: " + iBinder + " from uid " + i);
      }
      WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked);
      if (!wakeLock.hasSameWorkSource(workSource)) {
        notifyWakeLockChangingLocked(
            wakeLock,
            wakeLock.mFlags,
            wakeLock.mTag,
            wakeLock.mPackageName,
            wakeLock.mOwnerUid,
            wakeLock.mOwnerPid,
            workSource,
            str,
            null);
        wakeLock.mHistoryTag = str;
        wakeLock.updateWorkSource(workSource);
      }
    }
  }

  public final void updateWakeLockCallbackInternal(
      IBinder iBinder, IWakeLockCallback iWakeLockCallback, int i) {
    synchronized (this.mLock) {
      int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
      if (findWakeLockIndexLocked < 0) {
        throw new IllegalArgumentException("Wake lock not active: " + iBinder + " from uid " + i);
      }
      WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked);
      if (!isSameCallback(iWakeLockCallback, wakeLock.mCallback)) {
        notifyWakeLockChangingLocked(
            wakeLock,
            wakeLock.mFlags,
            wakeLock.mTag,
            wakeLock.mPackageName,
            wakeLock.mOwnerUid,
            wakeLock.mOwnerPid,
            wakeLock.mWorkSource,
            wakeLock.mHistoryTag,
            iWakeLockCallback);
        wakeLock.mCallback = iWakeLockCallback;
      }
    }
  }

  public final int findWakeLockIndexLocked(IBinder iBinder) {
    int size = this.mWakeLocks.size();
    for (int i = 0; i < size; i++) {
      if (((WakeLock) this.mWakeLocks.get(i)).mLock == iBinder) {
        return i;
      }
    }
    return -1;
  }

  public WakeLock findWakeLockLocked(IBinder iBinder) {
    int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
    if (findWakeLockIndexLocked == -1) {
      return null;
    }
    return (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked);
  }

  public final void notifyWakeLockAcquiredLocked(WakeLock wakeLock) {
    if (!this.mSystemReady || wakeLock.mDisabled) {
      return;
    }
    wakeLock.mNotifiedAcquired = true;
    this.mNotifier.onWakeLockAcquired(
        wakeLock.mFlags,
        wakeLock.mTag,
        wakeLock.mPackageName,
        wakeLock.mOwnerUid,
        wakeLock.mOwnerPid,
        wakeLock.mWorkSource,
        wakeLock.mHistoryTag,
        wakeLock.mCallback);
    restartNofifyLongTimerLocked(wakeLock);
  }

  public final void enqueueNotifyLongMsgLocked(long j) {
    this.mNotifyLongScheduled = j;
    Message obtainMessage = this.mHandler.obtainMessage(4);
    obtainMessage.setAsynchronous(true);
    this.mHandler.sendMessageAtTime(obtainMessage, j);
  }

  public final void restartNofifyLongTimerLocked(WakeLock wakeLock) {
    long uptimeMillis = this.mClock.uptimeMillis();
    wakeLock.mAcquireTime = uptimeMillis;
    if ((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1
        && this.mNotifyLongScheduled == 0) {
      enqueueNotifyLongMsgLocked(uptimeMillis + 60000);
    }
  }

  public final void notifyWakeLockLongStartedLocked(WakeLock wakeLock) {
    if (!this.mSystemReady || wakeLock.mDisabled) {
      return;
    }
    wakeLock.mNotifiedLong = true;
    this.mNotifier.onLongPartialWakeLockStart(
        wakeLock.mTag, wakeLock.mOwnerUid, wakeLock.mWorkSource, wakeLock.mHistoryTag);
  }

  public final void notifyWakeLockLongFinishedLocked(WakeLock wakeLock) {
    if (wakeLock.mNotifiedLong) {
      wakeLock.mNotifiedLong = false;
      this.mNotifier.onLongPartialWakeLockFinish(
          wakeLock.mTag, wakeLock.mOwnerUid, wakeLock.mWorkSource, wakeLock.mHistoryTag);
    }
  }

  public final void notifyWakeLockChangingLocked(
      WakeLock wakeLock,
      int i,
      String str,
      String str2,
      int i2,
      int i3,
      WorkSource workSource,
      String str3,
      IWakeLockCallback iWakeLockCallback) {
    if (this.mSystemReady && wakeLock.mNotifiedAcquired) {
      int i4 = wakeLock.mFlags;
      if ((i4 & 129) != 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("CHG");
        sb.append(i4 == 128 ? "(draw)" : "");
        EventLog.writeEvent(
            2729,
            sb.toString(),
            Long.valueOf(SystemClock.uptimeMillis() - wakeLock.mAcquireTime),
            wakeLock.mTag + XmlUtils.STRING_ARRAY_SEPARATOR + wakeLock.mPackageName);
      }
      this.mNotifier.onWakeLockChanging(
          wakeLock.mFlags,
          wakeLock.mTag,
          wakeLock.mPackageName,
          wakeLock.mOwnerUid,
          wakeLock.mOwnerPid,
          wakeLock.mWorkSource,
          wakeLock.mHistoryTag,
          wakeLock.mCallback,
          i,
          str,
          str2,
          i2,
          i3,
          workSource,
          str3,
          iWakeLockCallback);
      notifyWakeLockLongFinishedLocked(wakeLock);
      restartNofifyLongTimerLocked(wakeLock);
    }
  }

  public final void notifyWakeLockReleasedLocked(WakeLock wakeLock) {
    if (this.mSystemReady && wakeLock.mNotifiedAcquired) {
      int i = wakeLock.mFlags;
      if ((i & 129) != 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("REL");
        sb.append(i == 128 ? "(draw)" : "");
        EventLog.writeEvent(
            2729,
            sb.toString(),
            Long.valueOf(SystemClock.uptimeMillis() - wakeLock.mAcquireTime),
            wakeLock.mTag + XmlUtils.STRING_ARRAY_SEPARATOR + wakeLock.mPackageName);
      }
      wakeLock.mNotifiedAcquired = false;
      wakeLock.mAcquireTime = 0L;
      this.mNotifier.onWakeLockReleased(
          wakeLock.mFlags,
          wakeLock.mTag,
          wakeLock.mPackageName,
          wakeLock.mOwnerUid,
          wakeLock.mOwnerPid,
          wakeLock.mWorkSource,
          wakeLock.mHistoryTag,
          wakeLock.mCallback);
      notifyWakeLockLongFinishedLocked(wakeLock);
    }
  }

  public final boolean isWakeLockLevelSupportedInternal(int i) {
    synchronized (this.mLock) {
      boolean z = true;
      if (i != 1 && i != 6 && i != 10 && i != 26) {
        if (i == 32) {
          if (!this.mSystemReady || !this.mDisplayManagerInternal.isProximitySensorAvailable()) {
            z = false;
          }
          return z;
        }
        if (i != 64 && i != 128) {
          return false;
        }
      }
      return true;
    }
  }

  public final void userActivityFromNative(long j, int i, int i2, int i3) {
    if (this.mIsUserActivityNativeInvoked.compareAndSet(false, true)) {
      StringBuilder sb = new StringBuilder();
      sb.append("[api] userActivityFromNative : ");
      sb.append(PowerManager.userActivityEventToString(i));
      sb.append(i3 == 0 ? "" : ",");
      sb.append(PowerManager.userActivityFlagsToString(i3));
      sb.append(" displayId=");
      sb.append(i2);
      sb.append(" eventTime=");
      sb.append(j);
      Slog.m72d("PowerManagerService", sb.toString());
      this.mHandlerPmsMisc.postAtTime(
          new Runnable() { // from class:
                           // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              PowerManagerService.this.lambda$userActivityFromNative$3();
            }
          },
          this.mClock.uptimeMillis() + 1000);
    }
    userActivityInternal(i2, j, i, i3, 1000);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$userActivityFromNative$3() {
    this.mIsUserActivityNativeInvoked.set(false);
  }

  public final void userActivityInternal(int i, long j, int i2, int i3, int i4) {
    if ((i3 & 2) != 0) {
      if (this.mLastAndroidAutoBoostTime == -1) {
        this.mLastAndroidAutoBoostTime = j;
      }
      Slog.m72d(
          "PowerManagerService",
          "userActivityInternal PowerManager.USER_ACTIVITY_FLAG_INDIRECT : 2 (event: "
              + i2
              + " flags: "
              + i3
              + ") eventTime = "
              + j
              + ", mLastAndroidAutoBoostTime :"
              + this.mLastAndroidAutoBoostTime);
      if (j - this.mLastAndroidAutoBoostTime > 1900) {
        this.mContext.sendBroadcastAsUser(
            new Intent("com.sec.android.intent.action.ANDROID_AUTO_BOOSTER"), UserHandle.ALL);
        this.mLastAndroidAutoBoostTime = j;
      }
    }
    synchronized (this.mLock) {
      if (this.mSystemReady) {
        if (i == -1) {
          if (userActivityNoUpdateLocked(j, i2, i3, i4)) {
            updatePowerStateLocked();
          }
          return;
        }
        DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
        if (displayInfo == null) {
          return;
        }
        int i5 = displayInfo.displayGroupId;
        if (i5 == -1) {
          return;
        }
        if (userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(i5), j, i2, i3, i4)) {
          updatePowerStateLocked();
        }
      }
    }
  }

  public final void napInternal(long j, int i, boolean z) {
    synchronized (this.mLock) {
      dreamPowerGroupLocked((PowerGroup) this.mPowerGroups.get(0), j, i, z);
    }
  }

  public final void onUserAttention() {
    synchronized (this.mLock) {
      if (userActivityNoUpdateLocked(
          (PowerGroup) this.mPowerGroups.get(0), this.mClock.uptimeMillis(), 4, 0, 1000)) {
        updatePowerStateLocked();
      }
    }
  }

  public final boolean userActivityNoUpdateLocked(long j, int i, int i2, int i3) {
    boolean z = false;
    for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
      if (userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.valueAt(i4), j, i, i2, i3)) {
        z = true;
      }
    }
    return z;
  }

  public final boolean userActivityNoUpdateLocked(
      PowerGroup powerGroup, long j, int i, int i2, int i3) {
    ScreenCurtainController screenCurtainController;
    powerGroup.getGroupId();
    if (j < powerGroup.getLastSleepTimeLocked()
        || j < powerGroup.getLastWakeTimeLocked()
        || !this.mSystemReady) {
      return false;
    }
    Trace.traceBegin(131072L, "userActivity");
    try {
      if (j > this.mLastInteractivePowerHintTime
          && ((i == 2 && (i2 & 16384) == 0)
              || ((i == 0 && (i2 & IInstalld.FLAG_FORCE) != 0) || (i == 1 && (32768 & i2) != 0)))) {
        setPowerBoostInternal((i2 & IInstalld.FLAG_USE_QUOTA) != 0 ? -1 : 0, 0);
        this.mLastInteractivePowerHintTime = j;
      }
      if ((i == 2 || i == 1) && j - this.mLastNormalTouchBoostTime > 100) {
        SemPerfManager.sendCommandToSsrm("NORMAL_TOUCH_BOOSTER", "TRUE");
        this.mLastNormalTouchBoostTime = j;
      }
      this.mNotifier.onUserActivity(powerGroup.getGroupId(), i, i3);
      this.mAttentionDetector.onUserActivity(j, i);
      this.mAbuseWakeLockDetector.onUserActivity();
      if (this.mSmartStayEnabledSetting && powerGroup.getGroupId() == 0) {
        this.mSmartStayController.onUserActivity();
      }
      if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN
          && powerGroup.getGroupId() == 0
          && (screenCurtainController = this.mScreenCurtainController) != null
          && !screenCurtainController.isScreenCurtainEnabledLocked()
          && (i == 2 || i == 1)) {
        this.mScreenCurtainController.onUserActivity();
      }
      if (i == 2 && j > this.mLastUserActivityStateListenerCalledTime) {
        this.mLastUserActivityStateListenerCalledTime = j;
        if (this.mLastUserActivityStateListenerState == 0) {
          this.mLastUserActivityStateListenerState = 1;
          Slog.m72d(
              "PowerManagerService",
              "UserActivityStateListenerState: " + this.mLastUserActivityStateListenerState);
          Iterator it = this.mUserActivityStateListenerListeners.iterator();
          while (it.hasNext()) {
            ((PowerManagerInternal.UserActivityStateListener) it.next())
                .onChanged(this.mLastUserActivityStateListenerState);
          }
        }
        this.mHandler.removeCallbacks(this.mUserActivityStateListenerTimeoutRunnable);
        this.mHandler.postAtTime(
            this.mUserActivityStateListenerTimeoutRunnable, this.mClock.uptimeMillis() + 3000);
      }
      if (this.mUserInactiveOverrideFromWindowManager) {
        Slog.m72d(
            "PowerManagerService",
            "userActivityNoUpdateLocked: mUserInactiveOverrideFromWindowManager : false");
        this.mUserInactiveOverrideFromWindowManager = false;
        this.mOverriddenTimeout = -1L;
      }
      int wakefulnessLocked = powerGroup.getWakefulnessLocked();
      if (wakefulnessLocked != 0 && wakefulnessLocked != 3 && (i2 & 2) == 0) {
        maybeUpdateForegroundProfileLastActivityLocked(j);
        if ((i2 & 1) != 0) {
          if (j > powerGroup.getLastUserActivityTimeNoChangeLightsLocked()
              && j > powerGroup.getLastUserActivityTimeLocked()) {
            powerGroup.setLastUserActivityTimeNoChangeLightsLocked(j, i);
            int i4 = this.mDirty | 4;
            this.mDirty = i4;
            if (i == 1) {
              this.mDirty = i4 | IInstalld.FLAG_USE_QUOTA;
            }
            return true;
          }
        } else if (j > powerGroup.getLastUserActivityTimeLocked()) {
          powerGroup.setLastUserActivityTimeLocked(j, i);
          int i5 = this.mDirty | 4;
          this.mDirty = i5;
          if (i == 1) {
            this.mDirty = i5 | IInstalld.FLAG_USE_QUOTA;
          }
          if (this.mUserActivityTimeoutSetting > 0 && (i != 0 || (this.mDirty & 16) != 0)) {
            Slog.m74e("PowerManagerService", "call changes for MDM");
            setUserActivityTimeoutLocked(j);
          }
          return true;
        }
        return false;
      }
      return false;
    } finally {
      Trace.traceEnd(131072L);
    }
  }

  public final boolean isWakeUpPreventionNeededForEdgeLightingLocked(WakeLock wakeLock) {
    INotificationManager notificationManager;
    if (isGloballyInteractiveInternal()) {
      Slog.m72d(
          "PowerManagerService", "isWakeUpPreventionNeededForEdgeLightingLocked - interactive");
      return false;
    }
    int i = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
    if ((i == 6 || i == 10 || i == 26)
        && (notificationManager = this.mInjector.getNotificationManager()) != null) {
      try {
        return notificationManager.dispatchDelayedWakelockAndBlocked(
            wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid);
      } catch (RemoteException e) {
        Slog.m74e("PowerManagerService", "Failed to call NotificationManager : " + e);
      }
    }
    return false;
  }

  public final boolean isWakeUpPreventionNeededLocked() {
    boolean z = false;
    if (this.mLastWakeUpReason == 112) {
      return false;
    }
    if (this.mProximityPositive
        && !this.mInterceptedPowerKeyForProximity
        && !this.mInterceptedKeyForProximity) {
      Slog.m73dk("PowerManagerService", "Screen__On : Cancel (proximity)");
      z = true;
    }
    if (this.mDualScreenPolicy == 0 || !screenBlockedByCoverLocked() || this.mIsDualViewMode) {
      return z;
    }
    Slog.m73dk("PowerManagerService", "Screen__On : Cancel (coverType: " + this.mCoverType + ")");
    return true;
  }

  public final void maybeUpdateForegroundProfileLastActivityLocked(long j) {
    ProfilePowerState profilePowerState =
        (ProfilePowerState) this.mProfilePowerState.get(this.mForegroundProfile);
    int i = this.mForegroundProfile;
    if (i != 0 && profilePowerState == null && SemPersonaManager.isKnoxId(i)) {
      updateKnoxTimeoutValue(this.mForegroundProfile);
      Slog.m72d("PowerManagerService", "create ProfilePowerState for Workspace");
    } else {
      if (profilePowerState == null || j <= profilePowerState.mLastUserActivityTime) {
        return;
      }
      profilePowerState.mLastUserActivityTime = j;
    }
  }

  public final void wakePowerGroupLocked(
      PowerGroup powerGroup, long j, int i, String str, int i2, String str2, int i3) {
    wakePowerGroupLocked(powerGroup, j, i, str, i2, str2, i3, false);
  }

  /* JADX WARN: Can't wrap try/catch for region: R(15:28|(1:71)(2:31|(3:58|59|(14:67|68|34|35|(1:37)(1:56)|38|39|(1:41)(1:54)|42|(1:46)|47|(1:51)|52|53)))|33|34|35|(0)(0)|38|39|(0)(0)|42|(2:44|46)|47|(2:49|51)|52|53) */
  /* JADX WARN: Removed duplicated region for block: B:37:0x0088  */
  /* JADX WARN: Removed duplicated region for block: B:41:0x00d6  */
  /* JADX WARN: Removed duplicated region for block: B:54:0x00e9  */
  /* JADX WARN: Removed duplicated region for block: B:56:0x008b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void wakePowerGroupLocked(
      final PowerGroup powerGroup,
      long j,
      int i,
      String str,
      final int i2,
      String str2,
      int i3,
      boolean z) {
    String str3;
    String str4;
    PowerManagerUtil.ScreenOnProfiler screenOnProfiler;
    String[] split;
    this.mLastWakeUpReason = i;
    if (this.mForceSuspendActive
        || !this.mSystemReady
        || j < powerGroup.getLastSleepTimeLocked()
        || powerGroup.getWakefulnessLocked() == 1) {
      return;
    }
    if (powerGroup.getGroupId() != 2 || this.mIsDualViewMode) {
      if (powerGroup.getGroupId() == 0
          && i == 113
          && this.mProximityPositive
          && !this.mInterceptedKeyForProximity) {
        this.mDisplayManagerInternal.ignoreProximitySensorUntilChanged();
        this.mInterceptedKeyForProximity = true;
      }
      if (isWakeUpPreventionNeededLocked()) {
        this.mLastWakeUpReason = 0;
        return;
      }
      final int i4 = this.mLastWakeUpReason;
      if (i4 == 2 && !z) {
        if (i2 == 1000) {
          try {
          } catch (Exception unused) {
            str3 = "";
          }
          if (str.contains("::")
              && (split = str.split("::", 2)) != null
              && split.length == 2
              && !split[1].isEmpty()) {
            str3 = split[1];
            final String str5 = !str3.isEmpty() ? str2 : str3;
            this.mHandlerPmsMisc.post(
                new Runnable() { // from class:
                                 // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    PowerManagerService.this.lambda$wakePowerGroupLocked$4(
                        i2, powerGroup, i4, str5);
                  }
                });
            int uptimeMillis = (int) (this.mClock.uptimeMillis() - j);
            StringBuilder sb = new StringBuilder();
            sb.append("Screen__On  - ");
            sb.append(this.mScreenOnOffCount);
            sb.append(" : ");
            sb.append(this.mScreenOnReason);
            sb.append(" (");
            sb.append(PowerManager.wakeReasonToString(i));
            sb.append(")");
            if (str3.isEmpty()) {
              str4 = " (" + str3 + ")";
            } else {
              str4 = "";
            }
            sb.append(str4);
            sb.append(" (");
            sb.append(uptimeMillis);
            sb.append("ms) groupId=");
            sb.append(powerGroup.getGroupId());
            Slog.m77ik("PowerManagerService", sb.toString());
            screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
            if (screenOnProfiler.isStarted() && !screenOnProfiler.mSaved) {
              Slog.m72d(
                  "PowerManagerService",
                  "detected fast on off , ignore "
                      + (this.mScreenOnOffCount - 1)
                      + " th ON profiler ");
            }
            screenOnProfiler.clearAll();
            screenOnProfiler.noteWakeupDiff(uptimeMillis);
            screenOnProfiler.noteWakeupStart(this.mScreenOnOffCount);
            this.mScreenOnReason = "";
            this.mScreenOffReason = "";
            this.mScreenOnOffCount++;
            if (!this.mIsDualViewMode && i == 109) {
              this.mNotifier.onScreenStateChangeStartedByProximity(false);
            }
            powerGroup.wakeUpLocked(
                j, i, str, i2, str2, i3, LatencyTracker.getInstance(this.mContext));
          }
        }
      }
      str3 = "";
      if (!str3.isEmpty()) {}
      this.mHandlerPmsMisc.post(
          new Runnable() { // from class:
                           // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              PowerManagerService.this.lambda$wakePowerGroupLocked$4(i2, powerGroup, i4, str5);
            }
          });
      int uptimeMillis2 = (int) (this.mClock.uptimeMillis() - j);
      StringBuilder sb2 = new StringBuilder();
      sb2.append("Screen__On  - ");
      sb2.append(this.mScreenOnOffCount);
      sb2.append(" : ");
      sb2.append(this.mScreenOnReason);
      sb2.append(" (");
      sb2.append(PowerManager.wakeReasonToString(i));
      sb2.append(")");
      if (str3.isEmpty()) {}
      sb2.append(str4);
      sb2.append(" (");
      sb2.append(uptimeMillis2);
      sb2.append("ms) groupId=");
      sb2.append(powerGroup.getGroupId());
      Slog.m77ik("PowerManagerService", sb2.toString());
      screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
      if (screenOnProfiler.isStarted()) {
        Slog.m72d(
            "PowerManagerService",
            "detected fast on off , ignore " + (this.mScreenOnOffCount - 1) + " th ON profiler ");
      }
      screenOnProfiler.clearAll();
      screenOnProfiler.noteWakeupDiff(uptimeMillis2);
      screenOnProfiler.noteWakeupStart(this.mScreenOnOffCount);
      this.mScreenOnReason = "";
      this.mScreenOffReason = "";
      this.mScreenOnOffCount++;
      if (!this.mIsDualViewMode) {
        this.mNotifier.onScreenStateChangeStartedByProximity(false);
      }
      powerGroup.wakeUpLocked(j, i, str, i2, str2, i3, LatencyTracker.getInstance(this.mContext));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$wakePowerGroupLocked$4(
      int i, PowerGroup powerGroup, int i2, String str) {
    this.mPowerHistorian.onScreenOn(
        i, powerGroup.getGroupId(), i2, findForegroundPackageName(), str);
  }

  public final boolean dreamPowerGroupLocked(PowerGroup powerGroup, long j, int i, boolean z) {
    if (this.mBootCompleted && this.mSystemReady) {
      return powerGroup.dreamLocked(j, i, z);
    }
    return false;
  }

  public final boolean dozePowerGroupLocked(
      final PowerGroup powerGroup, long j, final int i, final int i2) {
    if (!this.mSystemReady
        || !this.mBootCompleted
        || j < powerGroup.getLastWakeTimeLocked()
        || !PowerManagerInternal.isInteractive(powerGroup.getWakefulnessLocked())) {
      return false;
    }
    if (powerGroup.getGroupId() == 0 && this.mGoToSleepPreventionEnabled) {
      Slog.m72d("PowerManagerService", "goToSleepNoUpdateLocked: GoToSleep prevention is enabled");
      return false;
    }
    if (this.mScreenDimDurationOverrideFromSQD != -1) {
      this.mScreenDimDurationOverrideFromSQD = -1L;
    }
    if (powerGroup.getGroupId() == 2
        && ((PowerGroup) this.mPowerGroups.get(0)).getWakefulnessLocked() == 1) {
      return false;
    }
    if (CoreRune.CARLIFE_DISPLAY_GROUP && powerGroup.getGroupId() == 4) {
      return false;
    }
    Slog.m77ik(
        "PowerManagerService",
        "Screen__Off(d) - "
            + this.mScreenOnOffCount
            + " : "
            + this.mScreenOffReason
            + " ("
            + PowerManager.sleepReasonToString(i)
            + ") groupId="
            + powerGroup.getGroupId());
    this.mScreenOnReason = "";
    this.mScreenOffReason = "";
    PowerManagerUtil.ScreenOffProfiler screenOffProfiler =
        PowerManagerUtil.sCurrentScreenOffProfiler;
    if (screenOffProfiler.isStarted() && !screenOffProfiler.mSaved) {
      Slog.m72d(
          "PowerManagerService",
          "detected fast on off , ignore " + (this.mScreenOnOffCount - 1) + " th OFF profiler ");
    }
    int uptimeMillis = (int) (this.mClock.uptimeMillis() - j);
    screenOffProfiler.clearAll();
    screenOffProfiler.noteGoToSleepDiff(uptimeMillis);
    screenOffProfiler.noteGoToSleepStart(this.mScreenOnOffCount);
    if (this.mMasterBrightnessLimitRunning) {
      cancelMasterBrightnessLimitAnimationLocked();
    }
    powerGroup.setLastGoToSleepReasonLocked(i);
    this.mHandlerPmsMisc.post(
        new Runnable() { // from class:
                         // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            PowerManagerService.this.lambda$dozePowerGroupLocked$5(i2, powerGroup, i);
          }
        });
    return powerGroup.dozeLocked(j, i2, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$dozePowerGroupLocked$5(int i, PowerGroup powerGroup, int i2) {
    this.mPowerHistorian.onScreenOff(i, powerGroup.getGroupId(), i2, findForegroundPackageName());
  }

  public final boolean sleepPowerGroupLocked(PowerGroup powerGroup, long j, int i, int i2) {
    if (!this.mBootCompleted
        || !this.mSystemReady
        || j < powerGroup.getLastWakeTimeLocked()
        || powerGroup.getWakefulnessLocked() == 0) {
      return false;
    }
    Slog.m77ik(
        "PowerManagerService",
        "Screen__Off(s) - "
            + this.mScreenOnOffCount
            + " : "
            + this.mScreenOffReason
            + " ("
            + PowerManager.sleepReasonToString(i)
            + ") groupId="
            + powerGroup.getGroupId());
    this.mScreenOnReason = "";
    this.mScreenOffReason = "";
    return powerGroup.sleepLocked(j, i2, i);
  }

  public void setWakefulnessLocked(
      int i, int i2, long j, int i3, int i4, int i5, String str, String str2) {
    ((PowerGroup) this.mPowerGroups.get(i)).setWakefulnessLocked(i2, j, i3, i4, i5, str, str2);
    this.mInjector.invalidateIsInteractiveCaches();
  }

  public final void updateGlobalWakefulnessLocked(
      long j, int i, int i2, int i3, String str, String str2) {
    String str3;
    int recalculateGlobalWakefulnessLocked = recalculateGlobalWakefulnessLocked();
    int globalWakefulnessLocked = getGlobalWakefulnessLocked();
    if (globalWakefulnessLocked == recalculateGlobalWakefulnessLocked) {
      return;
    }
    if (recalculateGlobalWakefulnessLocked == 0) {
      Slog.m76i("PowerManagerService", "Sleeping (uid " + i2 + ")...");
      if (globalWakefulnessLocked != 3) {
        this.mLastGlobalSleepTime = j;
        this.mLastGlobalSleepReason = i;
      }
      str3 = "reallyGoToSleep";
    } else if (recalculateGlobalWakefulnessLocked == 1) {
      Slog.m76i(
          "PowerManagerService",
          "Waking up from "
              + PowerManagerInternal.wakefulnessToString(globalWakefulnessLocked)
              + " (uid="
              + i2
              + ", reason="
              + PowerManager.wakeReasonToString(i)
              + ", details="
              + str2
              + ")...");
      this.mLastGlobalWakeTime = j;
      this.mLastGlobalWakeReason = i;
      this.mLastGlobalWakeTimeRealtime = this.mClock.elapsedRealtime();
      str3 = "wakeUp";
    } else if (recalculateGlobalWakefulnessLocked == 2) {
      Slog.m76i("PowerManagerService", "Nap time (uid " + i2 + ")...");
      str3 = "nap";
    } else if (recalculateGlobalWakefulnessLocked == 3) {
      Slog.m76i(
          "PowerManagerService",
          "Going to sleep due to "
              + PowerManager.sleepReasonToString(i)
              + " (uid "
              + i2
              + ", screenOffTimeout="
              + this.mScreenOffTimeoutSetting
              + ", activityTimeoutWM="
              + this.mUserActivityTimeoutOverrideFromWindowManager
              + ", maxDimRatio="
              + this.mMaximumScreenDimRatioConfig
              + ", maxDimDur="
              + this.mMaximumScreenDimDurationConfig
              + ")...");
      this.mLastGlobalSleepTime = j;
      this.mLastGlobalSleepReason = i;
      this.mLastGlobalSleepTimeRealtime = this.mClock.elapsedRealtime();
      this.mDozeStartInProgress = true;
      str3 = "goToSleep";
    } else {
      throw new IllegalArgumentException(
          "Unexpected wakefulness: " + recalculateGlobalWakefulnessLocked);
    }
    Trace.traceBegin(131072L, str3);
    try {
      this.mInjector.invalidateIsInteractiveCaches();
      this.mWakefulnessRaw = recalculateGlobalWakefulnessLocked;
      this.mWakefulnessChanging = true;
      this.mDirty = 2 | this.mDirty;
      this.mDozeStartInProgress &= recalculateGlobalWakefulnessLocked == 3;
      Notifier notifier = this.mNotifier;
      if (notifier != null) {
        notifier.onGlobalWakefulnessChangeStarted(recalculateGlobalWakefulnessLocked, i, j);
      }
      this.mAttentionDetector.onWakefulnessChangeStarted(recalculateGlobalWakefulnessLocked);
      if (recalculateGlobalWakefulnessLocked != 0) {
        if (recalculateGlobalWakefulnessLocked == 1) {
          this.mNotifier.onWakeUp(i, str2, i2, str, i3);
          if (sQuiescent) {
            this.mDirty |= IInstalld.FLAG_USE_QUOTA;
          }
        } else if (recalculateGlobalWakefulnessLocked != 3) {
        }
      }
      if (PowerManagerInternal.isInteractive(globalWakefulnessLocked)) {
        int size = this.mWakeLocks.size();
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
          int i6 = ((WakeLock) this.mWakeLocks.get(i5)).mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
          if (i6 == 6 || i6 == 10 || i6 == 26) {
            i4++;
          }
        }
        EventLogTags.writePowerSleepRequested(i4);
      }
    } finally {
      Trace.traceEnd(131072L);
    }
  }

  public int getGlobalWakefulnessLocked() {
    return this.mWakefulnessRaw;
  }

  public int getWakefulnessLocked(int i) {
    return ((PowerGroup) this.mPowerGroups.get(i)).getWakefulnessLocked();
  }

  public int recalculateGlobalWakefulnessLocked() {
    int i = 0;
    for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
      int wakefulnessLocked = ((PowerGroup) this.mPowerGroups.valueAt(i2)).getWakefulnessLocked();
      if (wakefulnessLocked == 1) {
        return 1;
      }
      if (wakefulnessLocked == 2 && (i == 0 || i == 3)) {
        i = 2;
      } else if (wakefulnessLocked == 3 && i == 0) {
        i = 3;
      }
    }
    return i;
  }

  public void onPowerGroupEventLocked(int i, PowerGroup powerGroup) {
    this.mWakefulnessChanging = true;
    this.mDirty |= 2;
    int groupId = powerGroup.getGroupId();
    if (i == 1) {
      this.mPowerGroups.delete(groupId);
    }
    int globalWakefulnessLocked = getGlobalWakefulnessLocked();
    int recalculateGlobalWakefulnessLocked = recalculateGlobalWakefulnessLocked();
    if (i == 0 && recalculateGlobalWakefulnessLocked == 1) {
      userActivityNoUpdateLocked(powerGroup, this.mClock.uptimeMillis(), 0, 0, 1000);
      this.mNotifier.onGroupWakefulnessChangeStarted(
          groupId, powerGroup.getWakefulnessLocked(), 10, this.mClock.uptimeMillis());
    } else if (i == 1) {
      this.mNotifier.onGroupRemoved(groupId);
    }
    if (globalWakefulnessLocked != recalculateGlobalWakefulnessLocked) {
      int i2 = 11;
      if (recalculateGlobalWakefulnessLocked != 1) {
        if (recalculateGlobalWakefulnessLocked != 3) {
          i2 = 0;
        } else if (i != 1) {
          i2 = 12;
        }
      } else if (i == 0) {
        i2 = 10;
      }
      int i3 = i2;
      updateGlobalWakefulnessLocked(
          this.mClock.uptimeMillis(),
          i3,
          1000,
          1000,
          this.mContext.getOpPackageName(),
          "groupId: " + groupId);
    }
    this.mDirty |= 65536;
    updatePowerStateLocked();
  }

  public final void logSleepTimeoutRecapturedLocked() {
    long uptimeMillis = this.mOverriddenTimeout - this.mClock.uptimeMillis();
    if (uptimeMillis >= 0) {
      EventLogTags.writePowerSoftSleepRequested(uptimeMillis);
      this.mOverriddenTimeout = -1L;
    }
  }

  public final void finishWakefulnessChangeIfNeededLocked() {
    if (this.mWakefulnessChanging && areAllPowerGroupsReadyLocked()) {
      if (getGlobalWakefulnessLocked() == 3 && (this.mWakeLockSummary & 64) == 0) {
        if (PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING && this.mDozeStartInProgress) {
          setEnsureTransitionToDozingReleaserLocked(true);
          return;
        }
        return;
      }
      this.mDozeStartInProgress = false;
      if (PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING) {
        setEnsureTransitionToDozingReleaserLocked(false);
      }
      if (getGlobalWakefulnessLocked() == 3 || getGlobalWakefulnessLocked() == 0) {
        logSleepTimeoutRecapturedLocked();
      }
      this.mWakefulnessChanging = false;
      this.mNotifier.onWakefulnessChangeFinished();
    }
  }

  public final boolean areAllPowerGroupsReadyLocked() {
    int size = this.mPowerGroups.size();
    for (int i = 0; i < size; i++) {
      if (!((PowerGroup) this.mPowerGroups.valueAt(i)).isReadyLocked()) {
        return false;
      }
    }
    return true;
  }

  public final void updatePowerStateLocked() {
    int i;
    if (!this.mSystemReady || this.mDirty == 0 || this.mUpdatePowerStateInProgress) {
      return;
    }
    if (!Thread.holdsLock(this.mLock)) {
      Slog.wtf(
          "PowerManagerService",
          "Power manager lock was not held when calling updatePowerStateLocked");
    }
    Trace.traceBegin(131072L, "updatePowerState");
    this.mUpdatePowerStateInProgress = true;
    try {
      updateIsPoweredLocked(this.mDirty);
      updateStayOnLocked(this.mDirty);
      updateScreenBrightnessBoostLocked(this.mDirty);
      long uptimeMillis = this.mClock.uptimeMillis();
      int i2 = 0;
      do {
        i = this.mDirty;
        i2 |= i;
        this.mDirty = 0;
        updateWakeLockSummaryLocked(i);
        updateUserActivitySummaryLocked(uptimeMillis, i);
        updateAttentiveStateLocked(uptimeMillis, i);
      } while (updateWakefulnessLocked(i));
      updateProfilesLocked(uptimeMillis);
      updateDreamLocked(i2, updatePowerGroupsLocked(i2));
      finishWakefulnessChangeIfNeededLocked();
      updateSuspendBlockerLocked();
    } finally {
      Trace.traceEnd(131072L);
      this.mUpdatePowerStateInProgress = false;
    }
  }

  public final void updateProfilesLocked(long j) {
    int size = this.mProfilePowerState.size();
    for (int i = 0; i < size; i++) {
      ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.valueAt(i);
      if (profilePowerState.mScreenOffTimeoutForUser > 0
          || profilePowerState.mScreenOffTimeout != Long.MAX_VALUE) {
        if (isProfileBeingKeptAwakeLocked(profilePowerState, j)) {
          profilePowerState.mLockingNotified = false;
        } else if (!profilePowerState.mLockingNotified) {
          profilePowerState.mLockingNotified = true;
          this.mNotifier.onProfileTimeout(profilePowerState.mUserId);
        }
      }
    }
  }

  public final boolean isProfileBeingKeptAwakeLocked(ProfilePowerState profilePowerState, long j) {
    long j2;
    long j3 = profilePowerState.mScreenOffTimeoutForUser;
    if (j3 > 0) {
      j2 = Math.min(profilePowerState.mScreenOffTimeout, j3);
    } else {
      j2 = profilePowerState.mScreenOffTimeout;
    }
    if (profilePowerState.mLastUserActivityTime + j2 <= j) {
      int i = profilePowerState.mWakeLockSummary;
      if ((i & 32) == 0 && (!this.mProximityPositive || (i & 16) == 0)) {
        return false;
      }
    }
    return true;
  }

  public final void updateIsPoweredLocked(int i) {
    if ((i & 256) != 0) {
      boolean z = this.mIsPowered;
      int i2 = this.mPlugType;
      this.mIsPowered = this.mBatteryManagerInternal.isPowered(15);
      this.mPlugType = this.mBatteryManagerInternal.getPlugType();
      int i3 = this.mBatteryLevel;
      this.mBatteryLevel = this.mBatteryManagerInternal.getBatteryLevel();
      this.mBatteryLevelLow = this.mBatteryManagerInternal.getBatteryLevelLow();
      this.mBatteryLevelCritical = this.mBatteryManagerInternal.getBatteryLevelCritical();
      this.mBatteryOnline = this.mBatteryManagerInternal.getBatteryOnline();
      boolean z2 = true;
      if (!(this.mBatteryManagerInternal.getBatteryHealth() == 3)
          && i3 > 0
          && getGlobalWakefulnessLocked() == 2) {
        this.mDreamsBatteryLevelDrain += i3 - this.mBatteryLevel;
      }
      if (z != this.mIsPowered || i2 != this.mPlugType) {
        Slog.m72d(
            "PowerManagerService",
            "[api] updateIsPoweredLocked : mIsPowered: "
                + this.mIsPowered
                + " mPlugType: "
                + this.mPlugType);
        this.mDirty = this.mDirty | 64;
        boolean update = this.mWirelessChargerDetector.update(this.mIsPowered, this.mPlugType);
        if (this.mIsWirelessChargerSContextEnabled
            && this.mWirelessChargerSContextManager.isAvailableService(46)) {
          if (this.mPlugType == 4) {
            if (!this.mIsWirelessChargerSContextRegistered) {
              Slog.m72d("PowerManagerService", "updateIsPoweredLocked : Register SContextListener");
              this.mWirelessChargerSContextManager.registerListener(
                  this.mWirelessChargerSContextListener, 46);
              this.mIsWirelessChargerSContextRegistered = true;
            } else if (!this.mIsDeviceMoving && !z) {
              Slog.m72d(
                  "PowerManagerService",
                  "updateIsPoweredLocked : Device is not moved, skip wakeup when connect wireless"
                      + " chager ");
              z2 = false;
            }
          } else if (i2 == 4) {
            if (this.mIsDeviceMoving) {
              if (this.mIsWirelessChargerSContextRegistered) {
                Slog.m72d(
                    "PowerManagerService", "updateIsPoweredLocked : Unregister SContextListener");
                this.mWirelessChargerSContextManager.unregisterListener(
                    this.mWirelessChargerSContextListener, 46);
                this.mIsWirelessChargerSContextRegistered = false;
              }
            } else if (!this.mIsPowered) {
              Slog.m72d(
                  "PowerManagerService",
                  "updateIsPoweredLocked : Device is not moved, skip wakeup when disconnect"
                      + " wireless charger");
              this.mPassedWakeupTime = SystemClock.elapsedRealtime();
              z2 = false;
            }
          }
        }
        long uptimeMillis = this.mClock.uptimeMillis();
        if (shouldWakeUpWhenPluggedOrUnpluggedLocked(z, i2, update) && z2) {
          this.mScreenOnReason = " powered change";
          wakePowerGroupLocked(
              (PowerGroup) this.mPowerGroups.get(0),
              uptimeMillis,
              3,
              "android.server.power:PLUGGED:" + this.mIsPowered,
              1000,
              this.mContext.getOpPackageName(),
              1000);
        }
        userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(0), uptimeMillis, 0, 0, 1000);
      }
      this.mBatterySaverStateMachine.setBatteryStatus(
          this.mIsPowered, this.mBatteryLevel, this.mBatteryLevelLow);
    }
  }

  public final boolean shouldWakeUpWhenPluggedOrUnpluggedLocked(boolean z, int i, boolean z2) {
    if ((!z && this.mIsPowered) || !this.mWakeUpWhenPluggedOrUnpluggedConfig) {
      return false;
    }
    if (this.mKeepDreamingWhenUnplugging
        && getGlobalWakefulnessLocked() == 2
        && z
        && !this.mIsPowered) {
      return false;
    }
    if (this.mIsPowered && getGlobalWakefulnessLocked() == 2) {
      return false;
    }
    if (this.mTheaterModeEnabled && !this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig) {
      return false;
    }
    int i2 = this.mPlugType;
    if (((i2 == 1 && i == 2) || (i2 == 2 && i == 1)) && this.mBatteryOnline == 99) {
      return false;
    }
    SettingsManager settingsManager = SettingsManager.getInstance();
    return settingsManager == null || settingsManager.getScreenWakeupOnPowerState();
  }

  public final void updateStayOnLocked(int i) {
    if ((i & 288) != 0) {
      boolean z = this.mStayOn;
      if (this.mStayOnWhilePluggedInSetting != 0
          && !isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()) {
        this.mStayOn = this.mBatteryManagerInternal.isPowered(this.mStayOnWhilePluggedInSetting);
      } else {
        this.mStayOn = false;
      }
      if (this.mStayOn != z) {
        this.mDirty |= 128;
      }
    }
  }

  public final void updateWakeLockSummaryLocked(int i) {
    if ((i & 65539) != 0) {
      this.mWakeLockSummary = 0;
      int size = this.mProfilePowerState.size();
      for (int i2 = 0; i2 < size; i2++) {
        ((ProfilePowerState) this.mProfilePowerState.valueAt(i2)).mWakeLockSummary = 0;
      }
      for (int i3 = 0; i3 < this.mPowerGroups.size(); i3++) {
        ((PowerGroup) this.mPowerGroups.valueAt(i3)).setWakeLockSummaryLocked(0);
      }
      int size2 = this.mWakeLocks.size();
      int i4 = 0;
      for (int i5 = 0; i5 < size2; i5++) {
        WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(i5);
        Integer powerGroupId = wakeLock.getPowerGroupId();
        if (powerGroupId != null
            && (powerGroupId.intValue() == -1
                || this.mPowerGroups.contains(powerGroupId.intValue()))) {
          PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(powerGroupId.intValue());
          int wakeLockSummaryFlags = getWakeLockSummaryFlags(wakeLock);
          this.mWakeLockSummary |= wakeLockSummaryFlags;
          if (powerGroupId.intValue() != -1) {
            powerGroup.setWakeLockSummaryLocked(
                powerGroup.getWakeLockSummaryLocked() | wakeLockSummaryFlags);
          } else {
            i4 |= wakeLockSummaryFlags;
          }
          for (int i6 = 0; i6 < size; i6++) {
            ProfilePowerState profilePowerState =
                (ProfilePowerState) this.mProfilePowerState.valueAt(i6);
            if (wakeLockAffectsUser(wakeLock, profilePowerState.mUserId)) {
              profilePowerState.mWakeLockSummary |= wakeLockSummaryFlags;
            }
          }
        }
      }
      for (int i7 = 0; i7 < this.mPowerGroups.size(); i7++) {
        PowerGroup powerGroup2 = (PowerGroup) this.mPowerGroups.valueAt(i7);
        powerGroup2.setWakeLockSummaryLocked(
            adjustWakeLockSummary(
                powerGroup2.getWakefulnessLocked(),
                powerGroup2.getWakeLockSummaryLocked() | i4,
                powerGroup2.getLastGoToSleepReasonLocked()));
      }
      this.mWakeLockSummary =
          adjustWakeLockSummary(getGlobalWakefulnessLocked(), this.mWakeLockSummary);
      for (int i8 = 0; i8 < size; i8++) {
        ProfilePowerState profilePowerState2 =
            (ProfilePowerState) this.mProfilePowerState.valueAt(i8);
        profilePowerState2.mWakeLockSummary =
            adjustWakeLockSummary(
                getGlobalWakefulnessLocked(), profilePowerState2.mWakeLockSummary);
      }
    }
  }

  public static int adjustWakeLockSummary(int i, int i2) {
    return adjustWakeLockSummary(i, i2, -1);
  }

  public final int getWakeLockSummaryFlags(WakeLock wakeLock) {
    boolean z = wakeLock.mDisabled;
    if (z) {
      return 0;
    }
    int i = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
    if (i == 1) {
      return 1;
    }
    if (i != 6) {
      if (i != 10) {
        if (i != 26) {
          if (i == 32) {
            return 16;
          }
          int i2 = 64;
          if (i != 64) {
            i2 = 128;
            if (i != 128) {}
          }
          return i2;
        }
        if (!z) {
          return 10;
        }
      } else if (!z) {
        return 2;
      }
    } else if (!z) {
      return 4;
    }
    return 0;
  }

  public final boolean wakeLockAffectsUser(WakeLock wakeLock, int i) {
    if (wakeLock.mWorkSource != null) {
      for (int i2 = 0; i2 < wakeLock.mWorkSource.size(); i2++) {
        if (i == UserHandle.getUserId(wakeLock.mWorkSource.getUid(i2))) {
          return true;
        }
      }
      List workChains = wakeLock.mWorkSource.getWorkChains();
      if (workChains != null) {
        for (int i3 = 0; i3 < workChains.size(); i3++) {
          if (i
              == UserHandle.getUserId(
                  ((WorkSource.WorkChain) workChains.get(i3)).getAttributionUid())) {
            return true;
          }
        }
      }
    }
    return i == UserHandle.getUserId(wakeLock.mOwnerUid);
  }

  /* renamed from: com.android.server.power.PowerManagerService$1 */
  public class C23301 implements AbuseWakeLockDetector.Callback {
    public C23301() {}

    @Override // com.android.server.power.AbuseWakeLockDetector.Callback
    public void onAbuseWakeLockAdded(final List list) {
      Slog.m72d("PowerManagerService", "abuse wakelock package : " + String.join(", ", list));
      PowerManagerService.this.mWakeLocks.stream()
          .filter(
              new Predicate() { // from class:
                                // com.android.server.power.PowerManagerService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$onAbuseWakeLockAdded$0;
                  lambda$onAbuseWakeLockAdded$0 =
                      PowerManagerService.C23301.lambda$onAbuseWakeLockAdded$0(
                          list, (PowerManagerService.WakeLock) obj);
                  return lambda$onAbuseWakeLockAdded$0;
                }
              })
          .forEach(
              new Consumer() { // from class:
                               // com.android.server.power.PowerManagerService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  PowerManagerService.C23301.this.lambda$onAbuseWakeLockAdded$1(
                      (PowerManagerService.WakeLock) obj);
                }
              });
      PowerManagerService powerManagerService = PowerManagerService.this;
      powerManagerService.mDirty = powerManagerService.mDirty | 1;
    }

    public static /* synthetic */ boolean lambda$onAbuseWakeLockAdded$0(
        List list, WakeLock wakeLock) {
      return PowerManagerService.isScreenLock(wakeLock) && list.contains(wakeLock.mPackageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAbuseWakeLockAdded$1(WakeLock wakeLock) {
      PowerManagerService.this.disableAbusiveWakeLockInternal(wakeLock.mLock, true);
    }

    @Override // com.android.server.power.AbuseWakeLockDetector.Callback
    public void onAbuseWakeLockRemoved() {
      Slog.m72d("PowerManagerService", "clear abuse wakelock");
      PowerManagerService.this.mWakeLocks.stream()
          .filter(
              new Predicate() { // from class:
                                // com.android.server.power.PowerManagerService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$onAbuseWakeLockRemoved$2;
                  lambda$onAbuseWakeLockRemoved$2 =
                      PowerManagerService.C23301.lambda$onAbuseWakeLockRemoved$2(
                          (PowerManagerService.WakeLock) obj);
                  return lambda$onAbuseWakeLockRemoved$2;
                }
              })
          .forEach(
              new Consumer() { // from class:
                               // com.android.server.power.PowerManagerService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  PowerManagerService.C23301.this.lambda$onAbuseWakeLockRemoved$3(
                      (PowerManagerService.WakeLock) obj);
                }
              });
      PowerManagerService.this.mDirty |= 1;
    }

    public static /* synthetic */ boolean lambda$onAbuseWakeLockRemoved$2(WakeLock wakeLock) {
      return wakeLock.mDisabled && PowerManagerService.isScreenLock(wakeLock);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAbuseWakeLockRemoved$3(WakeLock wakeLock) {
      PowerManagerService.this.disableAbusiveWakeLockInternal(wakeLock.mLock, false);
    }
  }

  public final void disableAbusiveWakeLockInternal(IBinder iBinder, boolean z) {
    synchronized (this.mLock) {
      int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
      if (findWakeLockIndexLocked >= 0) {
        WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(findWakeLockIndexLocked);
        if (wakeLock.mDisabled != z) {
          wakeLock.mDisabled = z;
          if (z) {
            notifyWakeLockReleasedLocked(wakeLock);
          } else {
            notifyWakeLockAcquiredLocked(wakeLock);
          }
        }
      }
    }
  }

  public void checkForLongWakeLocks() {
    synchronized (this.mLock) {
      long uptimeMillis = this.mClock.uptimeMillis();
      this.mNotifyLongDispatched = uptimeMillis;
      long j = uptimeMillis - 60000;
      int size = this.mWakeLocks.size();
      long j2 = Long.MAX_VALUE;
      for (int i = 0; i < size; i++) {
        WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(i);
        if ((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1
            && wakeLock.mNotifiedAcquired
            && !wakeLock.mNotifiedLong) {
          long j3 = wakeLock.mAcquireTime;
          if (j3 < j) {
            notifyWakeLockLongStartedLocked(wakeLock);
          } else {
            long j4 = j3 + 60000;
            if (j4 < j2) {
              j2 = j4;
            }
          }
        }
      }
      this.mNotifyLongScheduled = 0L;
      this.mHandler.removeMessages(4);
      if (j2 != Long.MAX_VALUE) {
        this.mNotifyLongNextCheck = j2;
        enqueueNotifyLongMsgLocked(j2);
      } else {
        this.mNotifyLongNextCheck = 0L;
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:93:0x00b3, code lost:

     if (r14.isPolicyDimLocked() != false) goto L41;
  */
  /* JADX WARN: Removed duplicated region for block: B:25:0x009c  */
  /* JADX WARN: Removed duplicated region for block: B:29:0x00bd  */
  /* JADX WARN: Removed duplicated region for block: B:39:0x00e7 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:48:0x00fe  */
  /* JADX WARN: Removed duplicated region for block: B:53:0x0112  */
  /* JADX WARN: Removed duplicated region for block: B:56:0x011b  */
  /* JADX WARN: Removed duplicated region for block: B:60:0x0140  */
  /* JADX WARN: Removed duplicated region for block: B:63:0x014c  */
  /* JADX WARN: Removed duplicated region for block: B:85:0x0142  */
  /* JADX WARN: Removed duplicated region for block: B:87:0x0116  */
  /* JADX WARN: Removed duplicated region for block: B:91:0x00e1  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void updateUserActivitySummaryLocked(long j, int i) {
    long j2;
    long j3;
    int i2;
    long j4;
    boolean z;
    int i3;
    boolean z2;
    int i4;
    long j5;
    int i5;
    int i6;
    long j6;
    int i7;
    long j7;
    int i8;
    long j8 = j;
    if ((i & 81959) == 0) {
      return;
    }
    this.mHandler.removeMessages(1);
    long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
    long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
    long screenOffTimeoutLocked =
        getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
    long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
    boolean z3 = this.mUserInactiveOverrideFromWindowManager;
    long j9 = -1;
    int i9 = 0;
    boolean z4 = false;
    while (i9 < this.mPowerGroups.size()) {
      PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i9);
      int groupId = powerGroup.getGroupId();
      long j10 = screenOffTimeoutLocked;
      int i10 = 2;
      if (this.mIsDualViewMode && powerGroup.getGroupId() == 2) {
        j10 = getScreenOffTimeoutForDexLocked();
        j2 = 0;
      } else {
        j2 = screenDimDurationLocked;
      }
      if (powerGroup.getWakefulnessLocked() != 0) {
        j4 = sleepTimeoutLocked;
        long lastUserActivityTimeLocked = powerGroup.getLastUserActivityTimeLocked();
        z = z3;
        long lastUserActivityTimeNoChangeLightsLocked =
            powerGroup.getLastUserActivityTimeNoChangeLightsLocked();
        if (lastUserActivityTimeLocked >= powerGroup.getLastWakeTimeLocked()) {
          j5 = lastUserActivityTimeLocked + j10;
          long j11 = j5 - j2;
          if (j8 < j11) {
            j5 = j11;
            i5 = 1;
          } else if (j8 < j5
              && lastUserActivityTimeLocked >= lastUserActivityTimeNoChangeLightsLocked) {
            i5 = 2;
          }
          if (i5 == 0
              && lastUserActivityTimeNoChangeLightsLocked >= powerGroup.getLastWakeTimeLocked()) {
            j5 = lastUserActivityTimeNoChangeLightsLocked + j10;
            if (j8 < j5) {
              if (powerGroup.isPolicyBrightLocked()) {
                i3 = groupId;
                z2 = true;
                powerGroup.setLastUserActivityTimeLocked(
                    lastUserActivityTimeNoChangeLightsLocked, 1);
                j5 -= j2;
                i10 = 1;
              } else {
                i3 = groupId;
                z2 = true;
              }
              if (i10 != 0) {
                i6 = i10;
                j6 = j5;
              } else if (j4 >= 0) {
                long max =
                    Math.max(lastUserActivityTimeLocked, lastUserActivityTimeNoChangeLightsLocked);
                if (max >= powerGroup.getLastWakeTimeLocked()) {
                  j6 = max + j4;
                  i8 = j8 < j6 ? 4 : i10;
                } else {
                  i8 = i10;
                  j6 = j5;
                }
                i6 = i8;
              } else {
                i7 = 4;
                i6 = 4;
                j6 = -1;
                if (i6 != i7 && z) {
                  if ((i6 & 3) != 0 && this.mOverriddenTimeout == -1) {
                    this.mOverriddenTimeout = j6;
                  }
                  i6 = 4;
                  j6 = -1;
                }
                if ((i6 & 1) != 0 && (powerGroup.getWakeLockSummaryLocked() & 32) == 0) {
                  j6 = this.mAttentionDetector.updateUserActivity(j6, j2);
                }
                if (isAttentiveTimeoutExpired(powerGroup, j8)) {
                  i4 = i6;
                } else {
                  j6 = -1;
                  i4 = 0;
                }
                if (this.mSmartStayEnabledSetting || powerGroup.getGroupId() != 0) {
                  j3 = j2;
                  j7 = j9;
                  i2 = i9;
                  j9 = j6;
                } else {
                  j3 = j2;
                  j7 = j9;
                  i2 = i9;
                  j9 =
                      this.mSmartStayController.updateUserActivity(
                          j, j6, i4, powerGroup.getWakeLockSummaryLocked());
                }
                z4 |= i4 == 0 ? z2 : false;
                if (j7 != -1) {
                  j9 = j9 != -1 ? Math.min(j7, j9) : j7;
                }
              }
              i7 = 4;
              if (i6 != i7) {
                if ((i6 & 3) != 0) {
                  this.mOverriddenTimeout = j6;
                }
                i6 = 4;
                j6 = -1;
              }
              if ((i6 & 1) != 0) {
                j6 = this.mAttentionDetector.updateUserActivity(j6, j2);
              }
              if (isAttentiveTimeoutExpired(powerGroup, j8)) {}
              if (this.mSmartStayEnabledSetting) {}
              j3 = j2;
              j7 = j9;
              i2 = i9;
              j9 = j6;
              z4 |= i4 == 0 ? z2 : false;
              if (j7 != -1) {}
            }
          }
          i3 = groupId;
          z2 = true;
          i10 = i5;
          if (i10 != 0) {}
          i7 = 4;
          if (i6 != i7) {}
          if ((i6 & 1) != 0) {}
          if (isAttentiveTimeoutExpired(powerGroup, j8)) {}
          if (this.mSmartStayEnabledSetting) {}
          j3 = j2;
          j7 = j9;
          i2 = i9;
          j9 = j6;
          z4 |= i4 == 0 ? z2 : false;
          if (j7 != -1) {}
        } else {
          j5 = 0;
        }
        i5 = 0;
        if (i5 == 0) {
          j5 = lastUserActivityTimeNoChangeLightsLocked + j10;
          if (j8 < j5) {}
        }
        i3 = groupId;
        z2 = true;
        i10 = i5;
        if (i10 != 0) {}
        i7 = 4;
        if (i6 != i7) {}
        if ((i6 & 1) != 0) {}
        if (isAttentiveTimeoutExpired(powerGroup, j8)) {}
        if (this.mSmartStayEnabledSetting) {}
        j3 = j2;
        j7 = j9;
        i2 = i9;
        j9 = j6;
        z4 |= i4 == 0 ? z2 : false;
        if (j7 != -1) {}
      } else {
        j3 = j2;
        i2 = i9;
        j4 = sleepTimeoutLocked;
        z = z3;
        i3 = groupId;
        z2 = true;
        i4 = 0;
      }
      screenDimDurationLocked = j3;
      if (powerGroup.getUserActivitySummaryLocked() != i4) {
        int i11 = i3;
        sendUserActivityIntentLocked(i4, screenDimDurationLocked, i11);
        Slog.m72d(
            "PowerManagerService",
            "UserActivityState : "
                + powerGroup.getUserActivitySummaryLocked()
                + " -> "
                + i4
                + " groupId="
                + i11);
        if (i11 == 0) {
          if (i4 == 4 && j9 == -1 && isBeingKeptAwakeLocked(powerGroup)) {
            this.mHandler.post(this.mPrintWakeLockAperiodicallyRunnable);
            powerGroup.setUserActivitySummaryLocked(i4);
            i9 = i2 + 1;
            j8 = j;
            screenOffTimeoutLocked = j10;
            sleepTimeoutLocked = j4;
            z3 = z;
          }
          this.mHandler.removeCallbacks(this.mPrintWakeLockAperiodicallyRunnable);
          powerGroup.setUserActivitySummaryLocked(i4);
          i9 = i2 + 1;
          j8 = j;
          screenOffTimeoutLocked = j10;
          sleepTimeoutLocked = j4;
          z3 = z;
        }
      }
      powerGroup.setUserActivitySummaryLocked(i4);
      i9 = i2 + 1;
      j8 = j;
      screenOffTimeoutLocked = j10;
      sleepTimeoutLocked = j4;
      z3 = z;
    }
    long j12 = j9;
    long nextProfileTimeoutLocked = getNextProfileTimeoutLocked(j);
    long min = nextProfileTimeoutLocked > 0 ? Math.min(j12, nextProfileTimeoutLocked) : j12;
    if (!z4 || min < 0) {
      return;
    }
    scheduleUserInactivityTimeout(min);
  }

  public final void sendUserActivityIntentLocked(int i, final long j, int i2) {
    if (this.mBootCompleted && i2 == 0) {
      final Intent intent =
          new Intent("com.samsung.server.PowerManagerService.action.USER_ACTIVITY");
      if (i != 0) {
        if (i == 1) {
          this.mHandlerPmsMisc.postAtTime(
              new Runnable() { // from class:
                               // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManagerService.this.lambda$sendUserActivityIntentLocked$6(intent);
                }
              },
              this.mClock.uptimeMillis());
          return;
        } else if (i == 2) {
          this.mHandlerPmsMisc.postAtTime(
              new Runnable() { // from class:
                               // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManagerService.this.lambda$sendUserActivityIntentLocked$7(intent, j);
                }
              },
              this.mClock.uptimeMillis());
          return;
        } else if (i != 4) {
          return;
        }
      }
      this.mHandlerPmsMisc.postAtTime(
          new Runnable() { // from class:
                           // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              PowerManagerService.this.lambda$sendUserActivityIntentLocked$8(intent);
            }
          },
          this.mClock.uptimeMillis());
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendUserActivityIntentLocked$6(Intent intent) {
    intent.putExtra("status", 1);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendUserActivityIntentLocked$7(Intent intent, long j) {
    intent.putExtra("status", 2);
    intent.putExtra("time", j);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendUserActivityIntentLocked$8(Intent intent) {
    intent.putExtra("status", 3);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  public final void scheduleUserInactivityTimeout(long j) {
    Message obtainMessage = this.mHandler.obtainMessage(1);
    obtainMessage.setAsynchronous(true);
    this.mHandler.sendMessageAtTime(obtainMessage, j);
  }

  public final void scheduleAttentiveTimeout(long j) {
    Message obtainMessage = this.mHandler.obtainMessage(5);
    obtainMessage.setAsynchronous(true);
    this.mHandler.sendMessageAtTime(obtainMessage, j);
  }

  public final long getNextProfileTimeoutLocked(long j) {
    int size = this.mProfilePowerState.size();
    long j2 = -1;
    for (int i = 0; i < size; i++) {
      ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.valueAt(i);
      long j3 = profilePowerState.mLastUserActivityTime;
      long j4 = profilePowerState.mScreenOffTimeout + j3;
      if (j4 > j && (j2 == -1 || j4 < j2)) {
        j2 = j4;
      }
      long j5 = j3 + profilePowerState.mScreenOffTimeoutForUser;
      if (j5 > j && (j2 == -1 || j5 < j2)) {
        j2 = j5;
      }
    }
    return j2;
  }

  public final void updateAttentiveStateLocked(long j, int i) {
    long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
    long lastUserActivityTimeLocked =
        ((PowerGroup) this.mPowerGroups.get(0)).getLastUserActivityTimeLocked()
            + attentiveTimeoutLocked;
    long j2 = lastUserActivityTimeLocked - this.mAttentiveWarningDurationConfig;
    boolean maybeHideInattentiveSleepWarningLocked = maybeHideInattentiveSleepWarningLocked(j, j2);
    if (attentiveTimeoutLocked >= 0) {
      if (maybeHideInattentiveSleepWarningLocked || (i & 19122) != 0) {
        this.mHandler.removeMessages(5);
        if (getGlobalWakefulnessLocked() == 0 || isBeingKeptFromInattentiveSleepLocked()) {
          return;
        }
        if (j < j2) {
          lastUserActivityTimeLocked = j2;
        } else if (j < lastUserActivityTimeLocked) {
          this.mInattentiveSleepWarningOverlayController.show();
        } else {
          lastUserActivityTimeLocked = -1;
        }
        if (lastUserActivityTimeLocked >= 0) {
          scheduleAttentiveTimeout(lastUserActivityTimeLocked);
        }
      }
    }
  }

  public final boolean maybeHideInattentiveSleepWarningLocked(long j, long j2) {
    long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
    if (!this.mInattentiveSleepWarningOverlayController.isShown()) {
      return false;
    }
    if (getGlobalWakefulnessLocked() == 0) {
      this.mInattentiveSleepWarningOverlayController.dismiss(false);
      return true;
    }
    if (attentiveTimeoutLocked >= 0 && !isBeingKeptFromInattentiveSleepLocked() && j >= j2) {
      return false;
    }
    this.mInattentiveSleepWarningOverlayController.dismiss(true);
    return true;
  }

  public final boolean isAttentiveTimeoutExpired(PowerGroup powerGroup, long j) {
    long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
    return powerGroup.getGroupId() == 0
        && attentiveTimeoutLocked >= 0
        && j >= powerGroup.getLastUserActivityTimeLocked() + attentiveTimeoutLocked;
  }

  public final void handleUserActivityTimeout() {
    synchronized (this.mLock) {
      if (this.mSmartStayEnabledSetting) {
        this.mSmartStayController.checkFaceDetectLocked();
      }
      this.mDirty |= 4;
      updatePowerStateLocked();
    }
  }

  public final void handleAttentiveTimeout() {
    synchronized (this.mLock) {
      this.mDirty |= 16384;
      updatePowerStateLocked();
    }
  }

  public final long getAttentiveTimeoutLocked() {
    long j = this.mAttentiveTimeoutSetting;
    if (j <= 0) {
      return -1L;
    }
    return Math.max(j, this.mMinimumScreenOffTimeoutConfig);
  }

  public final long getSleepTimeoutLocked(long j) {
    long j2 = this.mSleepTimeoutSetting;
    if (j2 <= 0) {
      return -1L;
    }
    if (j >= 0) {
      j2 = Math.min(j2, j);
    }
    return Math.max(j2, this.mMinimumScreenOffTimeoutConfig);
  }

  public final long getScreenOffTimeoutLocked(long j, long j2) {
    long j3 = this.mScreenOffTimeoutSetting;
    if (isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()) {
      j3 = Math.min(j3, this.mMaximumScreenOffTimeoutFromDeviceAdmin);
    }
    long j4 = this.mUserActivityTimeoutOverrideFromWindowManager;
    if (j4 >= 0) {
      j3 = Math.min(j3, j4);
    }
    if (j >= 0) {
      j3 = Math.min(j3, j);
    }
    if (j2 >= 0) {
      j3 = Math.min(j3, j2);
    }
    if (this.mIsDualViewMode) {
      j3 = Math.min(j3, getScreenOffTimeoutForDexLocked());
    }
    if (j3 != this.mLastScreenTimeout) {
      Slog.m72d(
          "PowerManagerService",
          "getScreenOffTimeoutLocked: " + this.mLastScreenTimeout + " -> " + j3);
      this.mLastScreenTimeout = (int) j3;
    }
    return Math.max(j3, this.mMinimumScreenOffTimeoutConfig);
  }

  public final long getScreenOffTimeoutForDexLocked() {
    long j = this.mScreenOffTimeoutForDex;
    long j2 = this.mUserActivityTimeoutForDexOverrideFromWindowManager;
    return j2 >= 0 ? Math.min(j, j2) : j;
  }

  public final long getScreenDimDurationLocked(long j) {
    long j2 = this.mScreenDimDurationOverrideFromWindowManager;
    if ((j2 == -1 || j2 >= j) && this.mScreenDimDurationOverrideFromSQD == -1) {
      return Math.min(
          this.mMaximumScreenDimDurationConfig, (long) (j * this.mMaximumScreenDimRatioConfig));
    }
    if (j2 < j) {
      return Math.max(j2, this.mScreenDimDurationOverrideFromSQD);
    }
    return this.mScreenDimDurationOverrideFromSQD;
  }

  public final boolean updateWakefulnessLocked(int i) {
    if ((i & 20151) == 0) {
      return false;
    }
    long uptimeMillis = this.mClock.uptimeMillis();
    boolean z = false;
    for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
      PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i2);
      if (powerGroup.getWakefulnessLocked() == 1 && isItBedTimeYetLocked(powerGroup)) {
        if (isAttentiveTimeoutExpired(powerGroup, uptimeMillis)) {
          z = sleepPowerGroupLocked(powerGroup, uptimeMillis, 9, 1000);
        } else if (shouldNapAtBedTimeLocked() && powerGroup.supportsSandmanLocked()) {
          z = dreamPowerGroupLocked(powerGroup, uptimeMillis, 1000, false);
        } else {
          z = dozePowerGroupLocked(powerGroup, uptimeMillis, 2, 1000);
        }
      }
    }
    return z;
  }

  public final boolean shouldNapAtBedTimeLocked() {
    return this.mDreamsActivateOnSleepSetting
        || (this.mDreamsActivateOnDockSetting && this.mDockState != 0);
  }

  public final boolean isItBedTimeYetLocked(PowerGroup powerGroup) {
    if (!this.mBootCompleted) {
      return false;
    }
    if (isAttentiveTimeoutExpired(powerGroup, this.mClock.uptimeMillis())) {
      return !isBeingKeptFromInattentiveSleepLocked();
    }
    return !isBeingKeptAwakeLocked(powerGroup);
  }

  public final boolean isBeingKeptAwakeLocked(PowerGroup powerGroup) {
    return this.mStayOn
        || this.mProximityPositive
        || (powerGroup.getWakeLockSummaryLocked() & 32) != 0
        || (powerGroup.getUserActivitySummaryLocked() & 3) != 0
        || this.mScreenBrightnessBoostInProgress;
  }

  public final boolean isBeingKeptFromInattentiveSleepLocked() {
    return this.mStayOn
        || this.mScreenBrightnessBoostInProgress
        || this.mProximityPositive
        || !this.mBootCompleted;
  }

  public final void updateDreamLocked(int i, boolean z) {
    if (((i & 82943) != 0 || z) && areAllPowerGroupsReadyLocked()) {
      scheduleSandmanLocked();
    }
  }

  public final void scheduleSandmanLocked() {
    if (this.mSandmanScheduled) {
      return;
    }
    this.mSandmanScheduled = true;
    for (int i = 0; i < this.mPowerGroups.size(); i++) {
      PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i);
      if (powerGroup.supportsSandmanLocked()) {
        Message obtainMessage = this.mHandler.obtainMessage(2);
        obtainMessage.arg1 = powerGroup.getGroupId();
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(obtainMessage, this.mClock.uptimeMillis());
      }
    }
  }

  public final void handleSandman(int i) {
    boolean z;
    boolean z2;
    synchronized (this.mLock) {
      this.mSandmanScheduled = false;
      if (this.mPowerGroups.contains(i)) {
        PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(i);
        int wakefulnessLocked = powerGroup.getWakefulnessLocked();
        if (powerGroup.isSandmanSummonedLocked() && powerGroup.isReadyLocked()) {
          if (!canDreamLocked(powerGroup) && !canDozeLocked(powerGroup)) {
            z = false;
            Slog.m72d(
                "PowerManagerService",
                "handleSandman : startDreaming: "
                    + z
                    + "  (canDreamLocked: "
                    + canDreamLocked(powerGroup)
                    + "  canDozeLocked: "
                    + canDozeLocked(powerGroup)
                    + ")  groupId="
                    + i);
            powerGroup.setSandmanSummonedLocked(false);
          }
          z = true;
          Slog.m72d(
              "PowerManagerService",
              "handleSandman : startDreaming: "
                  + z
                  + "  (canDreamLocked: "
                  + canDreamLocked(powerGroup)
                  + "  canDozeLocked: "
                  + canDozeLocked(powerGroup)
                  + ")  groupId="
                  + i);
          powerGroup.setSandmanSummonedLocked(false);
        } else {
          z = false;
        }
        DreamManagerInternal dreamManagerInternal = this.mDreamManager;
        if (dreamManagerInternal != null) {
          if (z) {
            dreamManagerInternal.stopDream(false, "power manager request before starting dream");
            StringBuilder sb = new StringBuilder();
            sb.append("handleSandman : startDream(");
            sb.append(wakefulnessLocked == 3);
            sb.append(")");
            Slog.m72d("PowerManagerService", sb.toString());
            this.mDreamManager.startDream(wakefulnessLocked == 3, "power manager request");
          }
          z2 = this.mDreamManager.isDreaming();
        } else {
          z2 = false;
        }
        if (z && !z2) {
          Slog.m74e("PowerManagerService", "handleSandman : startDreaming, but isDreaming false");
        }
        if (!PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING) {
          this.mDozeStartInProgress = false;
        }
        synchronized (this.mLock) {
          if (this.mPowerGroups.contains(i)) {
            if (z && z2) {
              this.mDreamsBatteryLevelDrain = 0;
              if (wakefulnessLocked == 3) {
                Slog.m76i("PowerManagerService", "Dozing...");
              } else {
                Slog.m76i("PowerManagerService", "Dreaming...");
              }
            }
            PowerGroup powerGroup2 = (PowerGroup) this.mPowerGroups.get(i);
            if (!powerGroup2.isSandmanSummonedLocked()
                && powerGroup2.getWakefulnessLocked() == wakefulnessLocked) {
              long uptimeMillis = this.mClock.uptimeMillis();
              if (wakefulnessLocked == 2) {
                if (z2 && canDreamLocked(powerGroup2)) {
                  int i2 = this.mDreamsBatteryLevelDrainCutoffConfig;
                  if (i2 < 0
                      || this.mDreamsBatteryLevelDrain <= i2
                      || isBeingKeptAwakeLocked(powerGroup2)) {
                    return;
                  }
                  Slog.m76i(
                      "PowerManagerService",
                      "Stopping dream because the battery appears to be draining faster than it is"
                          + " charging.  Battery level drained while dreaming: "
                          + this.mDreamsBatteryLevelDrain
                          + "%.  Battery level now: "
                          + this.mBatteryLevel
                          + "%.");
                }
                if (isItBedTimeYetLocked(powerGroup2)) {
                  this.mScreenOffReason = " dream(timeout)";
                  if (isAttentiveTimeoutExpired(powerGroup2, uptimeMillis)) {
                    sleepPowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                  } else {
                    dozePowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                  }
                } else {
                  this.mScreenOnReason = " dream end";
                  this.mLastWakeUpReason = 108;
                  wakePowerGroupLocked(
                      powerGroup2,
                      uptimeMillis,
                      13,
                      "android.server.power:DREAM_FINISHED",
                      1000,
                      this.mContext.getOpPackageName(),
                      1000);
                }
              } else if (wakefulnessLocked == 3) {
                if (z2) {
                  return;
                } else {
                  sleepPowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                }
              }
              if (z2) {
                Slog.m72d("PowerManagerService", "handleSandman : stopDream");
                this.mDreamManager.stopDream(false, "power manager request");
              }
            }
          }
        }
      }
    }
  }

  public final void onDreamSuppressionChangedLocked(boolean z) {
    if (this.mDreamsDisabledByAmbientModeSuppressionConfig) {
      if (!z
          && this.mIsPowered
          && this.mDreamsSupportedConfig
          && this.mDreamsEnabledSetting
          && shouldNapAtBedTimeLocked()
          && isItBedTimeYetLocked((PowerGroup) this.mPowerGroups.get(0))) {
        napInternal(SystemClock.uptimeMillis(), 1000, true);
      } else if (z) {
        this.mDirty |= 32;
        updatePowerStateLocked();
      }
    }
  }

  public final boolean canDreamLocked(PowerGroup powerGroup) {
    int i;
    int i2;
    boolean z =
        this.mDreamsDisabledByAmbientModeSuppressionConfig
            && this.mAmbientDisplaySuppressionController.isSuppressed();
    if (!this.mBootCompleted
        || z
        || getGlobalWakefulnessLocked() != 2
        || !this.mDreamsSupportedConfig
        || !this.mDreamsEnabledSetting
        || !powerGroup.isBrightOrDimLocked()
        || verifiedCoverClosedLocked()
        || ((PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && this.mDualScreenPolicy == 1)
            || (powerGroup.getUserActivitySummaryLocked() & 7) == 0)) {
      return false;
    }
    if (isBeingKeptAwakeLocked(powerGroup)) {
      return true;
    }
    boolean z2 = this.mIsPowered;
    if (!z2 && !this.mDreamsEnabledOnBatteryConfig) {
      return false;
    }
    if (z2
        || (i2 = this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig) < 0
        || this.mBatteryLevel >= i2) {
      return !z2
          || (i = this.mDreamsBatteryLevelMinimumWhenPoweredConfig) < 0
          || this.mBatteryLevel >= i;
    }
    return false;
  }

  public final boolean canDozeLocked(PowerGroup powerGroup) {
    return powerGroup.supportsSandmanLocked() && powerGroup.getWakefulnessLocked() == 3;
  }

  public final void setScreenDimDurationOverrideFromWindowManagerInternal(long j) {
    synchronized (this.mLock) {
      if (this.mScreenDimDurationOverrideFromWindowManager != j) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setScreenDimDurationOverrideFromWindowManagerInternal: timeoutMillis: " + j);
        this.mScreenDimDurationOverrideFromWindowManager = j;
        this.mDirty = this.mDirty | 32;
        updatePowerStateLocked();
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:18:0x0057  */
  /* JADX WARN: Removed duplicated region for block: B:21:0x0060  */
  /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
  /* JADX WARN: Removed duplicated region for block: B:31:0x009c  */
  /* JADX WARN: Removed duplicated region for block: B:38:0x00d4  */
  /* JADX WARN: Removed duplicated region for block: B:60:0x0149  */
  /* JADX WARN: Removed duplicated region for block: B:70:0x01ca  */
  /* JADX WARN: Removed duplicated region for block: B:89:0x00e1  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean updatePowerGroupsLocked(int i) {
    boolean z;
    boolean z2;
    boolean z3;
    float f;
    boolean z4;
    boolean z5;
    boolean updateLocked;
    boolean z6;
    ScreenCurtainController screenCurtainController;
    int i2;
    final PowerManagerService powerManagerService = this;
    boolean areAllPowerGroupsReadyLocked = areAllPowerGroupsReadyLocked();
    boolean z7 = true;
    boolean z8 = false;
    if ((71743 & i) != 0) {
      if ((i & IInstalld.FLAG_USE_QUOTA) != 0) {
        if (areAllPowerGroupsReadyLocked()) {
          sQuiescent = false;
        } else {
          powerManagerService.mDirty |= IInstalld.FLAG_USE_QUOTA;
        }
      }
      int i3 = 0;
      while (i3 < powerManagerService.mPowerGroups.size()) {
        PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.valueAt(i3);
        int groupId = powerGroup.getGroupId();
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequestLocked =
            powerGroup.getDisplayPowerRequestLocked();
        if (!powerManagerService.mBootCompleted) {
          f = powerManagerService.mScreenBrightnessDefault;
        } else if (isValidBrightness(
            powerManagerService.mScreenBrightnessOverrideFromWindowManager)) {
          f = powerManagerService.mScreenBrightnessOverrideFromWindowManager;
          z4 = z7;
          if (z4 != powerManagerService.mLastBrightnessOverrideState) {
            powerManagerService.mLastBrightnessOverrideState = z4;
            powerManagerService.setQuickPanelBrightnessBarDisabled(z4);
          }
          if (!powerManagerService.mCoverAuthReady) {
            powerManagerService.mIsCoverClosed = isCoverClosedBySysfs();
            powerManagerService.mlastUpdateCoverStateReason =
                "isCoverClosedBySysfs called on closed = "
                    + String.valueOf(powerManagerService.mIsCoverClosed);
          }
          if (powerManagerService.mCoverType == 8
              && powerManagerService.mIsCoverClosed
              && !powerManagerService.mBatterySaverStateMachine.isUltraPowerSavingModeEnabled()) {
            f =
                Float.isNaN(powerManagerService.mScreenBrightnessForClearCoverConfig)
                    ? powerManagerService.mScreenBrightnessForClearCoverConfig
                    : 1.0f;
          }
          if (groupId != 0
              && powerManagerService.mLastAutoBrightnessLimitTime
                  > powerManagerService.mDisplayManagerInternal
                      .getLastUserSetScreenBrightnessTime()) {
            displayPowerRequestLocked.autoBrightnessLowerLimit =
                BrightnessSynchronizer.brightnessIntToFloat(
                    powerManagerService.mAutoBrightnessLowerLimit);
            displayPowerRequestLocked.autoBrightnessUpperLimit =
                BrightnessSynchronizer.brightnessIntToFloat(
                    powerManagerService.mAutoBrightnessUpperLimit);
            displayPowerRequestLocked.forceSlowChange = powerManagerService.mForceSlowChange;
            powerManagerService.mForceSlowChange = z8;
          } else {
            displayPowerRequestLocked.autoBrightnessLowerLimit = -1.0f;
            displayPowerRequestLocked.autoBrightnessUpperLimit = -1.0f;
          }
          displayPowerRequestLocked.lastGoToSleepReason = powerGroup.getLastGoToSleepReasonLocked();
          if (!displayPowerRequestLocked.useProximitySensor) {
            displayPowerRequestLocked.proximityPositiveDebounce = getMaxProximityPositiveDebounce();
            displayPowerRequestLocked.proximityNegativeDebounce = getMaxProximityNegativeDebounce();
          } else {
            displayPowerRequestLocked.proximityPositiveDebounce = -1;
            displayPowerRequestLocked.proximityNegativeDebounce = -1;
          }
          displayPowerRequestLocked.screenBrightnessScaleFactor =
              powerManagerService.mScreenBrightnessScaleFactor;
          displayPowerRequestLocked.batteryLevelCritical =
              powerManagerService.mBatteryLevelCritical;
          displayPowerRequestLocked.isPowered = powerManagerService.mIsPowered;
          displayPowerRequestLocked.coverClosed = sensorBlockedByCoverLocked();
          displayPowerRequestLocked.coverType = powerManagerService.mCoverType;
          if (!sensorBlockedByCoverLocked() && brightnessLimitByCoverLocked()) {
            displayPowerRequestLocked.brightnessLimitByCover =
                powerManagerService.mBrightnessLimitByCoverConfig;
          } else {
            displayPowerRequestLocked.brightnessLimitByCover = -1;
          }
          displayPowerRequestLocked.mFTAMode = powerManagerService.mFTAMode;
          z5 = (groupId == 0 || !powerManagerService.mOutdoorModeSetting) ? z8 : z7;
          displayPowerRequestLocked.isOutdoorMode = z5;
          if (z5
              && powerGroup.isPoweringOnLocked()
              && (((i2 = displayPowerRequestLocked.policy) == 0 || i2 == z7)
                  && powerManagerService.mClock.elapsedRealtime()
                          - powerManagerService.mLastOutdoorModeEnabledTime
                      > 900000)) {
            displayPowerRequestLocked.isOutdoorMode = z8;
            powerManagerService.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda6
                  @Override // java.lang.Runnable
                  public final void run() {
                    PowerManagerService.this.lambda$updatePowerGroupsLocked$9();
                  }
                });
          }
          if (groupId == 0) {
            displayPowerRequestLocked.earlyWakeUp = powerManagerService.mEarlyWakeUp;
            powerManagerService.mEarlyWakeUp = z8;
            displayPowerRequestLocked.dualScreenPolicy = powerManagerService.mDualScreenPolicy;
            displayPowerRequestLocked.lastWakeUpReason = powerManagerService.mLastWakeUpReason;
          }
          displayPowerRequestLocked.lcdFlashMode = powerManagerService.mLCDFlashMode;
          displayPowerRequestLocked.hbmBlock = powerManagerService.mHbmBlock;
          displayPowerRequestLocked.freezeBrightnessMode =
              powerManagerService.mFreezeBrightnessMode;
          displayPowerRequestLocked.minBrightness =
              BrightnessSynchronizer.brightnessIntToFloat(
                  powerManagerService.mMasterBrightnessLowerLimit);
          displayPowerRequestLocked.maxBrightness =
              BrightnessSynchronizer.brightnessIntToFloat(
                  powerManagerService.mMasterBrightnessUpperLimit);
          if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN
              && !PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD
              && (screenCurtainController = powerManagerService.mScreenCurtainController) != null) {
            displayPowerRequestLocked.screenCurtainEnabled =
                screenCurtainController.isScreenCurtainEnabledLocked();
          }
          boolean z9 = areAllPowerGroupsReadyLocked;
          int i4 = i3;
          updateLocked =
              powerGroup.updateLocked(
                  f,
                  shouldUseProximitySensorLocked(),
                  shouldBoostScreenBrightness(),
                  powerManagerService.mDozeScreenStateOverrideFromDreamManager,
                  powerManagerService.mDozeScreenBrightnessOverrideFromDreamManagerFloat,
                  powerManagerService.mDrawWakeLockOverrideFromSidekick,
                  powerManagerService.mBatterySaverPolicy.getBatterySaverPolicy(7),
                  sQuiescent,
                  powerManagerService.mDozeAfterScreenOff,
                  powerManagerService.mBootCompleted,
                  powerManagerService.mScreenBrightnessBoostInProgress,
                  powerManagerService.mRequestWaitForNegativeProximity,
                  powerManagerService.mScreenDimDurationOverrideFromWindowManager,
                  powerManagerService.mBatterySavingAdjustBrightnessFactor);
          int wakefulnessLocked = powerGroup.getWakefulnessLocked();
          if (updateLocked != powerGroup.isReadyLocked()) {
            Slog.m72d(
                "PowerManagerService", "displayReady: " + updateLocked + " groupId=" + groupId);
          }
          boolean readyLocked = powerGroup.setReadyLocked(updateLocked);
          boolean isPoweringOnLocked = powerGroup.isPoweringOnLocked();
          if (!updateLocked && readyLocked && isPoweringOnLocked) {
            z6 = true;
            if (wakefulnessLocked == 1) {
              powerGroup.setIsPoweringOnLocked(false);
              powerManagerService = this;
              LatencyTracker.getInstance(powerManagerService.mContext).onActionEnd(5);
              Trace.asyncTraceEnd(131072L, "Screen turning on", groupId);
              int uptimeMillis =
                  (int)
                      (powerManagerService.mClock.uptimeMillis()
                          - powerGroup.getLastPowerOnTimeLocked());
              if (uptimeMillis >= 200) {
                Slog.m79w("PowerManagerService", "Screen on took " + uptimeMillis + " ms");
              }
            } else {
              powerManagerService = this;
            }
          } else {
            powerManagerService = this;
            z6 = true;
          }
          z7 = z6;
          i3 = i4 + 1;
          areAllPowerGroupsReadyLocked = z9;
          z8 = false;
        } else {
          f = Float.NaN;
        }
        z4 = z8;
        if (z4 != powerManagerService.mLastBrightnessOverrideState) {}
        if (!powerManagerService.mCoverAuthReady) {}
        if (powerManagerService.mCoverType == 8) {
          if (Float.isNaN(powerManagerService.mScreenBrightnessForClearCoverConfig)) {}
        }
        if (groupId != 0) {}
        displayPowerRequestLocked.autoBrightnessLowerLimit = -1.0f;
        displayPowerRequestLocked.autoBrightnessUpperLimit = -1.0f;
        displayPowerRequestLocked.lastGoToSleepReason = powerGroup.getLastGoToSleepReasonLocked();
        if (!displayPowerRequestLocked.useProximitySensor) {}
        displayPowerRequestLocked.screenBrightnessScaleFactor =
            powerManagerService.mScreenBrightnessScaleFactor;
        displayPowerRequestLocked.batteryLevelCritical = powerManagerService.mBatteryLevelCritical;
        displayPowerRequestLocked.isPowered = powerManagerService.mIsPowered;
        displayPowerRequestLocked.coverClosed = sensorBlockedByCoverLocked();
        displayPowerRequestLocked.coverType = powerManagerService.mCoverType;
        if (!sensorBlockedByCoverLocked()) {}
        displayPowerRequestLocked.brightnessLimitByCover = -1;
        displayPowerRequestLocked.mFTAMode = powerManagerService.mFTAMode;
        if (groupId == 0) {}
        displayPowerRequestLocked.isOutdoorMode = z5;
        if (z5) {
          displayPowerRequestLocked.isOutdoorMode = z8;
          powerManagerService.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.power.PowerManagerService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManagerService.this.lambda$updatePowerGroupsLocked$9();
                }
              });
        }
        if (groupId == 0) {}
        displayPowerRequestLocked.lcdFlashMode = powerManagerService.mLCDFlashMode;
        displayPowerRequestLocked.hbmBlock = powerManagerService.mHbmBlock;
        displayPowerRequestLocked.freezeBrightnessMode = powerManagerService.mFreezeBrightnessMode;
        displayPowerRequestLocked.minBrightness =
            BrightnessSynchronizer.brightnessIntToFloat(
                powerManagerService.mMasterBrightnessLowerLimit);
        displayPowerRequestLocked.maxBrightness =
            BrightnessSynchronizer.brightnessIntToFloat(
                powerManagerService.mMasterBrightnessUpperLimit);
        if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN) {
          displayPowerRequestLocked.screenCurtainEnabled =
              screenCurtainController.isScreenCurtainEnabledLocked();
        }
        boolean z92 = areAllPowerGroupsReadyLocked;
        int i42 = i3;
        updateLocked =
            powerGroup.updateLocked(
                f,
                shouldUseProximitySensorLocked(),
                shouldBoostScreenBrightness(),
                powerManagerService.mDozeScreenStateOverrideFromDreamManager,
                powerManagerService.mDozeScreenBrightnessOverrideFromDreamManagerFloat,
                powerManagerService.mDrawWakeLockOverrideFromSidekick,
                powerManagerService.mBatterySaverPolicy.getBatterySaverPolicy(7),
                sQuiescent,
                powerManagerService.mDozeAfterScreenOff,
                powerManagerService.mBootCompleted,
                powerManagerService.mScreenBrightnessBoostInProgress,
                powerManagerService.mRequestWaitForNegativeProximity,
                powerManagerService.mScreenDimDurationOverrideFromWindowManager,
                powerManagerService.mBatterySavingAdjustBrightnessFactor);
        int wakefulnessLocked2 = powerGroup.getWakefulnessLocked();
        if (updateLocked != powerGroup.isReadyLocked()) {}
        boolean readyLocked2 = powerGroup.setReadyLocked(updateLocked);
        boolean isPoweringOnLocked2 = powerGroup.isPoweringOnLocked();
        if (!updateLocked) {}
        powerManagerService = this;
        z6 = true;
        z7 = z6;
        i3 = i42 + 1;
        areAllPowerGroupsReadyLocked = z92;
        z8 = false;
      }
      z = areAllPowerGroupsReadyLocked;
      z2 = z7;
      z3 = z8;
      powerManagerService.mRequestWaitForNegativeProximity = z3;
    } else {
      z = areAllPowerGroupsReadyLocked;
      z2 = true;
      z3 = false;
    }
    return (!areAllPowerGroupsReadyLocked() || z) ? z3 : z2;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updatePowerGroupsLocked$9() {
    Slog.m72d("PowerManagerService", "updateDisplayPowerStateLocked: OutdoorMode timed out");
    Settings.System.putIntForUser(
        this.mContext.getContentResolver(), "display_outdoor_mode", 0, -2);
  }

  public final void updateScreenBrightnessBoostLocked(int i) {
    if ((i & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0
        || !this.mScreenBrightnessBoostInProgress) {
      return;
    }
    long uptimeMillis = this.mClock.uptimeMillis();
    this.mHandler.removeMessages(3);
    long j = this.mLastScreenBrightnessBoostTime;
    if (j > this.mLastGlobalSleepTime) {
      long j2 = j + 5000;
      if (j2 > uptimeMillis) {
        Message obtainMessage = this.mHandler.obtainMessage(3);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(obtainMessage, j2);
        return;
      }
    }
    this.mScreenBrightnessBoostInProgress = false;
    userActivityNoUpdateLocked(uptimeMillis, 0, 0, 1000);
  }

  public final boolean shouldBoostScreenBrightness() {
    return this.mScreenBrightnessBoostInProgress;
  }

  public int getDesiredScreenPolicyLocked(int i) {
    return ((PowerGroup) this.mPowerGroups.get(i))
        .getDesiredScreenPolicyLocked(
            sQuiescent,
            this.mDozeAfterScreenOff,
            this.mBootCompleted,
            this.mScreenBrightnessBoostInProgress,
            this.mScreenDimDurationOverrideFromWindowManager);
  }

  public int getDreamsBatteryLevelDrain() {
    return this.mDreamsBatteryLevelDrain;
  }

  public final boolean shouldUseProximitySensorLocked() {
    return (((PowerGroup) this.mPowerGroups.get(0)).getWakeLockSummaryLocked() & 16) != 0;
  }

  public final int getMaxProximityPositiveDebounce() {
    int size = this.mWakeLocks.size();
    int i = -1;
    for (int i2 = 0; i2 < size; i2++) {
      if ((((WakeLock) this.mWakeLocks.get(i2)).mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 32
          && i < ((WakeLock) this.mWakeLocks.get(i2)).mProximityPositiveDebounce) {
        i = ((WakeLock) this.mWakeLocks.get(i2)).mProximityPositiveDebounce;
      }
    }
    return i;
  }

  public final int getMaxProximityNegativeDebounce() {
    int size = this.mWakeLocks.size();
    int i = -1;
    for (int i2 = 0; i2 < size; i2++) {
      if ((((WakeLock) this.mWakeLocks.get(i2)).mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 32
          && i < ((WakeLock) this.mWakeLocks.get(i2)).mProximityNegativeDebounce) {
        i = ((WakeLock) this.mWakeLocks.get(i2)).mProximityNegativeDebounce;
      }
    }
    return i;
  }

  public final void setUserActivityTimeoutLocked(long j) {
    Slog.m72d("PowerManagerService", "setUserActivityTimeoutLocked SRUK : now=" + j);
    if (this.mBootCompleted) {
      long j2 = j + this.mUserActivityTimeoutSetting;
      if (this.mNoUserActivitySent) {
        this.mHandlerPmsMisc.removeCallbacks(this.mUserActivityTask);
        this.mHandlerPmsMisc.post(this.mUserActivityTask);
      }
      this.mHandlerPmsMisc.removeCallbacks(this.mUserActivityTimeoutTask);
      this.mHandlerPmsMisc.postAtTime(this.mUserActivityTimeoutTask, j2);
    }
  }

  public class UserActivityTask implements Runnable {
    public UserActivityTask() {}

    @Override // java.lang.Runnable
    public void run() {
      if (PowerManagerService.this.mNoUserActivitySent
          && PowerManagerService.this.mContext != null
          && ActivityManagerNative.isSystemReady()) {
        PowerManagerService.this.mContext.sendBroadcastAsUser(
            PowerManagerService.this.mUserActivityIntent,
            UserHandle.ALL,
            "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
        PowerManagerService.this.mNoUserActivitySent = false;
      }
    }
  }

  public class UserActivityTimeoutTask implements Runnable {
    public UserActivityTimeoutTask() {}

    @Override // java.lang.Runnable
    public void run() {
      if (PowerManagerService.this.mContext == null || !ActivityManagerNative.isSystemReady()) {
        return;
      }
      PowerManagerService.this.mContext.sendBroadcastAsUser(
          PowerManagerService.this.mNoUserActivityIntent,
          UserHandle.ALL,
          "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
      PowerManagerService.this.mNoUserActivitySent = true;
    }
  }

  public final void updateSuspendBlockerLocked() {
    ScreenCurtainController screenCurtainController;
    boolean z = (this.mWakeLockSummary & 1) != 0;
    boolean needSuspendBlockerLocked = needSuspendBlockerLocked();
    boolean z2 = !needSuspendBlockerLocked;
    boolean shouldEnableInteractiveModeLocked =
        ((PowerGroup) this.mPowerGroups.get(0))
            .shouldEnableInteractiveModeLocked(
                this.mProximityPositive, this.mShouldWaitForTransitionToAodUi);
    if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN
        && PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD
        && PowerManagerUtil.SEC_FEATURE_SUPPORT_LEGACY_MISC_POWER_HAL
        && (screenCurtainController = this.mScreenCurtainController) != null
        && screenCurtainController.isScreenCurtainEnabledLocked()) {
      shouldEnableInteractiveModeLocked = false;
    }
    if (!z2 && this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
      setHalAutoSuspendModeLocked(false);
    }
    if (!this.mBootCompleted && !this.mHoldingBootingSuspendBlocker) {
      this.mBootingSuspendBlocker.acquire();
      this.mHoldingBootingSuspendBlocker = true;
    }
    if (z && !this.mHoldingWakeLockSuspendBlocker) {
      this.mWakeLockSuspendBlocker.acquire();
      this.mHoldingWakeLockSuspendBlocker = true;
    }
    if (needSuspendBlockerLocked && !this.mHoldingDisplaySuspendBlocker) {
      this.mDisplaySuspendBlocker.acquire("holding display");
      this.mHoldingDisplaySuspendBlocker = true;
    }
    if (this.mDecoupleHalInteractiveModeFromDisplayConfig
        && (!shouldEnableInteractiveModeLocked
            || (areAllPowerGroupsReadyLocked() && this.mDefaultDisplayReadyByProximity))) {
      setHalInteractiveModeLocked(shouldEnableInteractiveModeLocked);
    }
    if (this.mBootCompleted && this.mHoldingBootingSuspendBlocker) {
      this.mBootingSuspendBlocker.release();
      this.mHoldingBootingSuspendBlocker = false;
    }
    if (!z && this.mHoldingWakeLockSuspendBlocker) {
      this.mWakeLockSuspendBlocker.release();
      this.mHoldingWakeLockSuspendBlocker = false;
    }
    if (!needSuspendBlockerLocked && this.mHoldingDisplaySuspendBlocker) {
      this.mDisplaySuspendBlocker.release("holding display");
      this.mHoldingDisplaySuspendBlocker = false;
    }
    if (z2 && this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
      setHalAutoSuspendModeLocked(true);
    }
  }

  public final boolean needSuspendBlockerLocked() {
    this.mSuspendBlockerMonitor.clearGlobalEvent();
    if (!areAllPowerGroupsReadyLocked()) {
      this.mSuspendBlockerMonitor.setEvent(1);
      return true;
    }
    if (this.mScreenBrightnessBoostInProgress) {
      this.mSuspendBlockerMonitor.setEvent(2);
      return true;
    }
    if (getGlobalWakefulnessLocked() == 3 && this.mDozeStartInProgress) {
      this.mSuspendBlockerMonitor.setEvent(4);
      return true;
    }
    for (int i = 0; i < this.mPowerGroups.size(); i++) {
      if (((PowerGroup) this.mPowerGroups.valueAt(i))
          .needSuspendBlockerLocked(
              this.mProximityPositive,
              this.mSuspendWhenScreenOffDueToProximityConfig,
              this.mDefaultDisplayState)) {
        this.mSuspendBlockerMonitor.setEvent(8);
        return true;
      }
    }
    return false;
  }

  public final void setHalAutoSuspendModeLocked(boolean z) {
    if (z != this.mHalAutoSuspendModeEnabled) {
      this.mHalAutoSuspendModeEnabled = z;
      Trace.traceBegin(131072L, "setHalAutoSuspend(" + z + ")");
      try {
        this.mNativeWrapper.nativeSetAutoSuspend(z);
      } finally {
        Trace.traceEnd(131072L);
      }
    }
  }

  public final void setHalInteractiveModeLocked(boolean z) {
    SemInputDeviceManager semInputDeviceManager;
    if (z != this.mHalInteractiveModeEnabled || this.mForceSetHalInteractiveMode) {
      Slog.m72d("PowerManagerService", "Setting HAL interactive mode to " + z);
      if (this.mForceSetHalInteractiveMode) {
        Slog.m72d(
            "PowerManagerService",
            "setHalInteractiveModeLocked called by dual screen policy change");
        this.mForceSetHalInteractiveMode = false;
      }
      this.mHalInteractiveModeEnabled = z;
      Trace.traceBegin(131072L, "setHalInteractive(" + z + ")");
      if (!z) {
        try {
          if (this.mProximityPositive
              && (semInputDeviceManager = this.mSemInputDeviceManager) != null) {
            semInputDeviceManager.setProxPowerOff(this.mDualScreenPolicy == 1 ? 2 : 1, 1);
          }
        } finally {
          Trace.traceEnd(131072L);
        }
      }
      this.mNativeWrapper.nativeSetPowerMode(7, z);
      this.mNativeWrapper.nativeSetInteractiveAsync(z, this.mDualScreenPolicy);
    }
  }

  public final boolean isGloballyInteractiveInternal() {
    boolean isInteractive;
    synchronized (this.mLock) {
      isInteractive = PowerManagerInternal.isInteractive(getGlobalWakefulnessLocked());
    }
    return isInteractive;
  }

  public final boolean isInteractiveInternal(int i, int i2) {
    synchronized (this.mLock) {
      DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
      if (displayInfo == null) {
        Slog.m79w("PowerManagerService", "Did not find DisplayInfo for displayId " + i);
        return false;
      }
      if (!displayInfo.hasAccess(i2)) {
        throw new SecurityException("uid " + i2 + " does not have access to display " + i);
      }
      PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(displayInfo.displayGroupId);
      if (powerGroup == null) {
        Slog.m79w("PowerManagerService", "Did not find PowerGroup for displayId " + i);
        return false;
      }
      return PowerManagerInternal.isInteractive(powerGroup.getWakefulnessLocked());
    }
  }

  public final boolean isInteractiveForDisplayInternal(int i, int i2) {
    synchronized (this.mLock) {
      if (this.mSystemReady && i != -1) {
        DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
        int i3 = displayInfo != null ? displayInfo.displayGroupId : -1;
        int appId = UserHandle.getAppId(i2);
        if ((UserHandle.isApp(i2) || appId == 1001) && i3 != -1) {
          int wakefulnessLocked = getWakefulnessLocked(i3);
          boolean isInteractive = PowerManagerInternal.isInteractive(getGlobalWakefulnessLocked());
          boolean isInteractive2 = PowerManagerInternal.isInteractive(wakefulnessLocked);
          if (isInteractive != isInteractive2) {
            Slog.m72d(
                "PowerManagerService",
                "isInteractiveInternal(): uid="
                    + i2
                    + ", interactive="
                    + isInteractive
                    + ", interactive(internal)="
                    + isInteractive2);
          }
          return isInteractive2;
        }
        return PowerManagerInternal.isInteractive(getGlobalWakefulnessLocked());
      }
      return PowerManagerInternal.isInteractive(getGlobalWakefulnessLocked());
    }
  }

  public final boolean setLowPowerModeInternal(boolean z) {
    synchronized (this.mLock) {
      Slog.m72d(
          "PowerManagerService", "setLowPowerModeInternal " + z + " mIsPowered=" + this.mIsPowered);
      this.mBatterySaverStateMachine.setBatterySaverEnabledManually(z);
    }
    return true;
  }

  public final boolean isProximityPositiveInternal() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mProximityPositive;
    }
    return z;
  }

  public final boolean isInternalDisplayOffInternal() {
    boolean z;
    synchronized (this.mLock) {
      z =
          PowerManagerInternal.isInteractive(
                  ((PowerGroup) this.mPowerGroups.get(0)).getWakefulnessLocked())
              ? false
              : true;
    }
    return z;
  }

  public final void setGoToSleepPreventionInternal(boolean z) {
    this.mGoToSleepPreventionEnabled = z;
  }

  public boolean isDeviceIdleModeInternal() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mDeviceIdleMode;
    }
    return z;
  }

  public boolean isLightDeviceIdleModeInternal() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mLightDeviceIdleMode;
    }
    return z;
  }

  public final void handleBatteryStateChangedLocked() {
    this.mDirty |= 256;
    updatePowerStateLocked();
  }

  public final void shutdownOrRebootInternal(
      final int i, final boolean z, final String str, boolean z2) {
    if ("userspace".equals(str)) {
      if (!PowerManager.isRebootingUserspaceSupportedImpl()) {
        throw new UnsupportedOperationException(
            "Attempted userspace reboot on a device that doesn't support it");
      }
      UserspaceRebootLogger.noteUserspaceRebootWasRequested();
    }
    if (this.mHandler == null || !this.mSystemReady) {
      if (RescueParty.isAttemptingFactoryReset()) {
        lowLevelReboot(str);
      } else {
        throw new IllegalStateException("Too early to call shutdown() or reboot()");
      }
    }
    Runnable runnable =
        new Runnable() { // from class: com.android.server.power.PowerManagerService.3
          @Override // java.lang.Runnable
          public void run() {
            synchronized (this) {
              if ("silent.sec".equals(str)) {
                if (FreecessController.getInstance().getFreecessEnabled()) {
                  FreecessHandler.getInstance().sendResetAllStateMsg("SoftReset");
                  try {
                    Thread.sleep(2000L);
                  } catch (InterruptedException e) {
                    Slog.m74e("PowerManagerService", "Failed to sleep");
                    e.printStackTrace();
                  }
                }
                throw new NullPointerException(
                    "NPE by silent reset. It's normal operation caused by device care");
              }
              int i2 = i;
              if (i2 == 2) {
                ShutdownThread.rebootSafeMode(PowerManagerService.this.getUiContext(), z);
              } else if (i2 == 1) {
                ShutdownThread.reboot(PowerManagerService.this.getUiContext(), str, z);
              } else {
                ShutdownThread.shutdown(PowerManagerService.this.getUiContext(), str, z);
              }
            }
          }
        };
    Message obtain = Message.obtain(UiThread.getHandler(), runnable);
    obtain.setAsynchronous(true);
    UiThread.getHandler().sendMessage(obtain);
    if (!z2) {
      return;
    }
    synchronized (runnable) {
      while (true) {
        try {
          runnable.wait();
        } catch (InterruptedException unused) {
        }
      }
    }
  }

  public final void crashInternal(final String str) {
    Thread thread =
        new Thread(
            "PowerManagerService.crash()") { // from class:
                                             // com.android.server.power.PowerManagerService.4
          @Override // java.lang.Thread, java.lang.Runnable
          public void run() {
            throw new RuntimeException(str);
          }
        };
    try {
      thread.start();
      thread.join();
    } catch (InterruptedException e) {
      Slog.wtf("PowerManagerService", e);
    }
  }

  public void setStayOnSettingInternal(int i) {
    Settings.Global.putInt(this.mContext.getContentResolver(), "stay_on_while_plugged_in", i);
  }

  public void setMaximumScreenOffTimeoutFromDeviceAdminInternal(int i, long j) {
    if (i < 0) {
      Slog.wtf("PowerManagerService", "Attempt to set screen off timeout for invalid user: " + i);
      return;
    }
    synchronized (this.mLock) {
      try {
        if (i == 0) {
          this.mMaximumScreenOffTimeoutFromDeviceAdmin = j;
        } else {
          if (j != Long.MAX_VALUE && j != 0) {
            ProfilePowerState profilePowerState =
                (ProfilePowerState) this.mProfilePowerState.get(i);
            if (profilePowerState != null) {
              profilePowerState.mScreenOffTimeout = j;
            } else {
              this.mProfilePowerState.put(
                  i, new ProfilePowerState(i, j, this.mClock.uptimeMillis()));
              updateKnoxTimeoutValue(i);
              this.mDirty |= 1;
            }
          }
          this.mProfilePowerState.delete(i);
        }
        this.mDirty |= 32;
        updatePowerStateLocked();
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public final void updateKnoxTimeoutValue(int i) {
    int intForUser;
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (SemPersonaManager.isSecureFolderId(i)) {
      intForUser = Settings.System.getIntForUser(contentResolver, "knox_screen_off_timeout", 0, i);
    } else {
      intForUser = Settings.Secure.getIntForUser(contentResolver, "knox_screen_off_timeout", 0, i);
    }
    ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.get(i);
    if (profilePowerState != null) {
      profilePowerState.mScreenOffTimeoutForUser = intForUser;
    } else {
      this.mProfilePowerState.put(
          i, new ProfilePowerState(i, intForUser, this.mClock.uptimeMillis(), true));
    }
  }

  public void setMaximumScreenOffTimeoutFromKnoxInternal(int i, long j) {
    if (i < 0) {
      Slog.wtf("PowerManagerService", "Attempt to set screen off timeout for invalid user: " + i);
      return;
    }
    synchronized (this.mLock) {
      ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.get(i);
      if (profilePowerState != null) {
        Slog.m72d(
            "PowerManagerService",
            "profile timeout changed : " + profilePowerState.mScreenOffTimeoutForUser + " -> " + j);
        profilePowerState.mScreenOffTimeoutForUser = j;
      } else {
        this.mProfilePowerState.put(
            i, new ProfilePowerState(i, j, this.mClock.uptimeMillis(), true));
        this.mDirty |= 1;
      }
      this.mDirty |= 32;
      updatePowerStateLocked();
    }
  }

  public void updateProfileActivityTimeFromKnoxInternal(int i, long j) {
    synchronized (this.mLock) {
      ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.get(i);
      if (profilePowerState == null || !SemPersonaManager.isKnoxId(i)) {
        return;
      }
      if (j > profilePowerState.mLastUserActivityTime) {
        Slog.m72d("PowerManagerService", "updated ProfilePowerState for Workspace");
        profilePowerState.mLastUserActivityTime = j;
      }
    }
  }

  public boolean setDeviceIdleModeInternal(boolean z) {
    synchronized (this.mLock) {
      if (this.mDeviceIdleMode == z) {
        return false;
      }
      this.mDeviceIdleMode = z;
      updateWakeLockDisabledStatesLocked();
      setPowerModeInternal(8, this.mDeviceIdleMode || this.mLightDeviceIdleMode);
      if (z) {
        EventLogTags.writeDeviceIdleOnPhase("power");
      } else {
        EventLogTags.writeDeviceIdleOffPhase("power");
      }
      return true;
    }
  }

  public boolean setLightDeviceIdleModeInternal(boolean z) {
    synchronized (this.mLock) {
      if (this.mLightDeviceIdleMode == z) {
        return false;
      }
      this.mLightDeviceIdleMode = z;
      setPowerModeInternal(8, this.mDeviceIdleMode || z);
      return true;
    }
  }

  public void setDeviceIdleWhitelistInternal(int[] iArr) {
    synchronized (this.mLock) {
      this.mDeviceIdleWhitelist = iArr;
      if (this.mDeviceIdleMode) {
        updateWakeLockDisabledStatesLocked();
      }
    }
  }

  public void setDeviceIdleTempWhitelistInternal(int[] iArr) {
    synchronized (this.mLock) {
      this.mDeviceIdleTempWhitelist = iArr;
      if (this.mDeviceIdleMode) {
        updateWakeLockDisabledStatesLocked();
      }
    }
  }

  public void setLowPowerStandbyAllowlistInternal(int[] iArr) {
    synchronized (this.mLock) {
      this.mLowPowerStandbyAllowlist = iArr;
      if (this.mLowPowerStandbyActive) {
        updateWakeLockDisabledStatesLocked();
      }
    }
  }

  public void setLowPowerStandbyActiveInternal(boolean z) {
    synchronized (this.mLock) {
      if (this.mLowPowerStandbyActive != z) {
        this.mLowPowerStandbyActive = z;
        updateWakeLockDisabledStatesLocked();
      }
    }
  }

  public void startUidChangesInternal() {
    synchronized (this.mLock) {
      this.mUidsChanging = true;
    }
  }

  public void finishUidChangesInternal() {
    synchronized (this.mLock) {
      this.mUidsChanging = false;
      if (this.mUidsChanged) {
        updateWakeLockDisabledStatesLocked();
        this.mUidsChanged = false;
      }
    }
  }

  public final void handleUidStateChangeLocked() {
    if (this.mUidsChanging) {
      this.mUidsChanged = true;
    } else {
      updateWakeLockDisabledStatesLocked();
    }
  }

  public void updateUidProcStateInternal(int i, int i2) {
    synchronized (this.mLock) {
      UidState uidState = (UidState) this.mUidState.get(i);
      if (uidState == null) {
        uidState = new UidState(i);
        this.mUidState.put(i, uidState);
      }
      boolean z = true;
      boolean z2 = uidState.mProcState <= 11;
      uidState.mProcState = i2;
      if (uidState.mNumWakeLocks > 0) {
        if (!this.mDeviceIdleMode && !this.mLowPowerStandbyActive) {
          if (!uidState.mActive) {
            if (i2 > 11) {
              z = false;
            }
            if (z2 != z) {
              handleUidStateChangeLocked();
            }
          }
        }
        handleUidStateChangeLocked();
      }
    }
  }

  public void uidGoneInternal(int i) {
    synchronized (this.mLock) {
      int indexOfKey = this.mUidState.indexOfKey(i);
      if (indexOfKey >= 0) {
        UidState uidState = (UidState) this.mUidState.valueAt(indexOfKey);
        uidState.mProcState = 20;
        uidState.mActive = false;
        this.mUidState.removeAt(indexOfKey);
        if ((this.mDeviceIdleMode || this.mLowPowerStandbyActive) && uidState.mNumWakeLocks > 0) {
          handleUidStateChangeLocked();
        }
      }
    }
  }

  public void uidActiveInternal(int i) {
    synchronized (this.mLock) {
      UidState uidState = (UidState) this.mUidState.get(i);
      if (uidState == null) {
        uidState = new UidState(i);
        uidState.mProcState = 19;
        this.mUidState.put(i, uidState);
      }
      uidState.mActive = true;
      if (uidState.mNumWakeLocks > 0) {
        handleUidStateChangeLocked();
      }
    }
  }

  public void uidIdleInternal(int i) {
    synchronized (this.mLock) {
      UidState uidState = (UidState) this.mUidState.get(i);
      if (uidState != null) {
        uidState.mActive = false;
        if (uidState.mNumWakeLocks > 0) {
          handleUidStateChangeLocked();
        }
      }
    }
  }

  public final void updateWakeLockDisabledStatesLocked() {
    int size = this.mWakeLocks.size();
    boolean z = false;
    for (int i = 0; i < size; i++) {
      WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(i);
      if (((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1 || isScreenLock(wakeLock))
          && setWakeLockDisabledStateLocked(wakeLock)) {
        if (wakeLock.mDisabled) {
          notifyWakeLockReleasedLocked(wakeLock);
        } else {
          notifyWakeLockAcquiredLocked(wakeLock);
        }
        z = true;
      }
    }
    if (z) {
      this.mDirty |= 1;
      updatePowerStateLocked();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0037  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean setWakeLockDisabledStateLocked(WakeLock wakeLock) {
    int i;
    boolean z;
    int i2;
    String str;
    int i3;
    int i4;
    int i5;
    if ((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1) {
      int appId = UserHandle.getAppId(wakeLock.mOwnerUid);
      if (appId >= 10000) {
        if (this.mConstants.NO_CACHED_WAKE_LOCKS) {
          if (!this.mForceSuspendActive) {
            UidState uidState = wakeLock.mUidState;
            if (uidState.mActive || (i5 = uidState.mProcState) == 20 || i5 <= 11) {
              z = false;
              i2 = !z ? 1 : 0;
            }
          }
          z = true;
          if (!z) {}
        } else {
          z = false;
          i2 = 0;
        }
        if (this.mDeviceIdleMode) {
          UidState uidState2 = wakeLock.mUidState;
          if (Arrays.binarySearch(this.mDeviceIdleWhitelist, appId) < 0
              && Arrays.binarySearch(this.mDeviceIdleTempWhitelist, appId) < 0
              && (i4 = uidState2.mProcState) != 20
              && i4 > 5) {
            i2 |= 2;
            z = true;
          }
        }
        if (this.mLowPowerStandbyActive) {
          UidState uidState3 = wakeLock.mUidState;
          if (Arrays.binarySearch(this.mLowPowerStandbyAllowlist, wakeLock.mOwnerUid) < 0
              && (i3 = uidState3.mProcState) != 20
              && i3 > 3) {
            i2 |= 4;
            z = true;
          }
        }
        if (wakeLock.mDisabledByFreecess) {
          i2 |= 8;
          z = true;
        }
        if (this.mWakelockBlacklistAppid.contains(Integer.valueOf(appId))) {
          i2 |= 16;
          z = true;
        }
      } else {
        z = false;
        i2 = 0;
      }
      boolean disabled = wakeLock.setDisabled(z, i2);
      if (disabled && (65535 & wakeLock.mFlags) == 1) {
        long uptimeMillis =
            wakeLock.mNotifiedAcquired ? this.mClock.uptimeMillis() - wakeLock.mAcquireTime : 0L;
        Object[] objArr = new Object[3];
        objArr[0] = "DIS";
        objArr[1] = Long.valueOf(uptimeMillis);
        StringBuilder sb = new StringBuilder();
        sb.append(wakeLock.mTag);
        sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
        sb.append(wakeLock.mPackageName);
        if (wakeLock.mDisabled) {
          str = "(disabled:" + wakeLock.getDisableReasonString() + ")";
        } else {
          str = "(enabled)";
        }
        sb.append(str);
        objArr[2] = sb.toString();
        EventLog.writeEvent(2729, objArr);
      }
      return disabled;
    }
    if (!this.mDisableScreenWakeLocksWhileCached || !isScreenLock(wakeLock)) {
      return false;
    }
    int appId2 = UserHandle.getAppId(wakeLock.mOwnerUid);
    UidState uidState4 = wakeLock.mUidState;
    return wakeLock.setDisabled(
        this.mConstants.NO_CACHED_WAKE_LOCKS
            && appId2 >= 10000
            && !uidState4.mActive
            && (i = uidState4.mProcState) != 20
            && i >= 12,
        0);
  }

  public final boolean isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked() {
    long j = this.mMaximumScreenOffTimeoutFromDeviceAdmin;
    return j >= 0 && j < Long.MAX_VALUE;
  }

  public final void setAttentionLightInternal(boolean z, int i) {
    synchronized (this.mLock) {
      if (this.mSystemReady) {
        LogicalLight logicalLight = this.mAttentionLight;
        if (logicalLight != null) {
          logicalLight.setFlashing(i, 2, z ? 3 : 0, 0);
        }
      }
    }
  }

  public final void setDozeAfterScreenOffInternal(boolean z) {
    synchronized (this.mLock) {
      this.mDozeAfterScreenOff = z;
    }
  }

  public final void boostScreenBrightnessInternal(long j, int i) {
    synchronized (this.mLock) {
      if (this.mSystemReady
          && getGlobalWakefulnessLocked() != 0
          && j >= this.mLastScreenBrightnessBoostTime) {
        Slog.m76i("PowerManagerService", "Brightness boost activated (uid " + i + ")...");
        this.mLastScreenBrightnessBoostTime = j;
        this.mScreenBrightnessBoostInProgress = true;
        this.mDirty = this.mDirty | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
        userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(0), j, 0, 0, i);
        updatePowerStateLocked();
      }
    }
  }

  public final boolean isScreenBrightnessBoostedInternal() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mScreenBrightnessBoostInProgress;
    }
    return z;
  }

  public final void handleScreenBrightnessBoostTimeout() {
    synchronized (this.mLock) {
      this.mDirty |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
      updatePowerStateLocked();
    }
  }

  public final void setScreenBrightnessOverrideFromWindowManagerInternal(float f) {
    synchronized (this.mLock) {
      if (!BrightnessSynchronizer.floatEquals(this.mScreenBrightnessOverrideFromWindowManager, f)) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setScreenBrightnessOverrideFromWindowManagerInternal: brightness: "
                + PowerManagerUtil.brightnessToString(f));
        this.mScreenBrightnessOverrideFromWindowManager = f;
        this.mDirty = this.mDirty | 32;
        updatePowerStateLocked();
      }
    }
  }

  public final void setUserInactiveOverrideFromWindowManagerInternal() {
    synchronized (this.mLock) {
      this.mUserInactiveOverrideFromWindowManager = true;
      this.mDirty |= 4;
      updatePowerStateLocked();
    }
  }

  public final void setUserActivityTimeoutOverrideFromWindowManagerInternal(long j) {
    synchronized (this.mLock) {
      if (this.mUserActivityTimeoutOverrideFromWindowManager != j) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setUserActivityTimeoutOverrideFromWindowManagerInternal: timeoutMillis: " + j);
        this.mUserActivityTimeoutOverrideFromWindowManager = j;
        EventLogTags.writeUserActivityTimeoutOverride(j);
        this.mDirty = this.mDirty | 32;
        updatePowerStateLocked();
      }
    }
  }

  public final void setUserActivityTimeoutForDexOverrideFromWindowManagerInternal(long j) {
    synchronized (this.mLock) {
      if (this.mUserActivityTimeoutForDexOverrideFromWindowManager != j) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setUserActivityTimeoutForDexOverrideFromWindowManagerInternal: timeoutMillis: "
                + j);
        this.mUserActivityTimeoutForDexOverrideFromWindowManager = j;
        this.mDirty = this.mDirty | 32;
        updatePowerStateLocked();
      }
    }
  }

  public final void setDozeOverrideFromDreamManagerInternal(int i, int i2, boolean z) {
    synchronized (this.mLock) {
      if (this.mDozeScreenStateOverrideFromDreamManager != i
          || this.mDozeScreenBrightnessOverrideFromDreamManager != i2
          || this.mShouldWaitForTransitionToAodUi != z) {
        this.mDozeScreenStateOverrideFromDreamManager = i;
        this.mDozeScreenBrightnessOverrideFromDreamManager = i2;
        this.mDozeScreenBrightnessOverrideFromDreamManagerFloat =
            BrightnessSynchronizer.brightnessIntToFloat(i2);
        this.mShouldWaitForTransitionToAodUi = z;
        this.mDirty |= 32;
        updatePowerStateLocked();
      }
    }
  }

  public final void setDrawWakeLockOverrideFromSidekickInternal(boolean z) {
    synchronized (this.mLock) {
      if (this.mDrawWakeLockOverrideFromSidekick != z) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setDrawWakeLockOverrideFromSidekickInternal()"
                + PowerManagerUtil.callerInfoToString());
        this.mDrawWakeLockOverrideFromSidekick = z;
        this.mDirty = this.mDirty | 32;
        updatePowerStateLocked();
      }
    }
  }

  public final void setScreenDimDurationOverrideFromSqdInternal(boolean z) {
    synchronized (this.mLock) {
      if (!z) {
        this.mScreenDimDurationOverrideFromSQD = -1L;
      } else if (this.mScreenDimDurationOverrideFromSQD == -1) {
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        long screenOffTimeoutLocked =
            getScreenOffTimeoutLocked(
                getSleepTimeoutLocked(attentiveTimeoutLocked), attentiveTimeoutLocked);
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mScreenDimDurationOverrideFromSQD =
            (((PowerGroup) this.mPowerGroups.get(0)).getLastUserActivityTimeLocked()
                    + screenOffTimeoutLocked)
                - uptimeMillis;
        Slog.m72d(
            "PowerManagerService",
            "[api] setScreenDimDurationOverrideFromSqdInternal: enabled: "
                + this.mScreenDimDurationOverrideFromSQD
                + " = "
                + ((PowerGroup) this.mPowerGroups.get(0)).getLastUserActivityTimeLocked()
                + " + "
                + screenOffTimeoutLocked
                + " - "
                + uptimeMillis);
      }
      Slog.m72d(
          "PowerManagerService",
          "[api] setScreenDimDurationOverrideFromSqdInternal: mScreenDimDurationOverrideFromSQD: "
              + this.mScreenDimDurationOverrideFromSQD);
      this.mDirty = this.mDirty | 32;
      updatePowerStateLocked();
    }
  }

  public final void setPowerBoostInternal(int i, int i2) {
    this.mNativeWrapper.nativeSetPowerBoost(i, i2);
  }

  public final boolean setPowerModeInternal(int i, boolean z) {
    if (i == 5 && z && this.mBatterySaverController.isLaunchBoostDisabled()) {
      return false;
    }
    return this.mNativeWrapper.nativeSetPowerMode(i, z);
  }

  public boolean wasDeviceIdleForInternal(long j) {
    boolean z;
    synchronized (this.mLock) {
      z =
          ((PowerGroup) this.mPowerGroups.get(0)).getLastUserActivityTimeLocked() + j
              < this.mClock.uptimeMillis();
    }
    return z;
  }

  public void onUserActivity() {
    synchronized (this.mLock) {
      ((PowerGroup) this.mPowerGroups.get(0))
          .setLastUserActivityTimeLocked(this.mClock.uptimeMillis(), 0);
    }
  }

  public final boolean forceSuspendInternal(int i) {
    boolean nativeForceSuspend;
    synchronized (this.mLock) {
      try {
        this.mForceSuspendActive = true;
        for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
          sleepPowerGroupLocked(
              (PowerGroup) this.mPowerGroups.valueAt(i2), this.mClock.uptimeMillis(), 8, i);
        }
        updateWakeLockDisabledStatesLocked();
        Slog.m76i("PowerManagerService", "Force-Suspending (uid " + i + ")...");
        nativeForceSuspend = this.mNativeWrapper.nativeForceSuspend();
        if (!nativeForceSuspend) {
          Slog.m76i("PowerManagerService", "Force-Suspending failed in native.");
        }
      } finally {
        this.mForceSuspendActive = false;
        updateWakeLockDisabledStatesLocked();
      }
    }
    return nativeForceSuspend;
  }

  public final void addPowerGroupsForNonDefaultDisplayGroupLocked() {
    IntArray displayGroupIds = this.mDisplayManagerInternal.getDisplayGroupIds();
    if (displayGroupIds == null) {
      return;
    }
    for (int i = 0; i < displayGroupIds.size(); i++) {
      int i2 = displayGroupIds.get(i);
      if (i2 != 0) {
        if (this.mPowerGroups.contains(i2)) {
          Slog.m74e("PowerManagerService", "Tried to add already existing group:" + i2);
        } else {
          this.mPowerGroups.append(
              i2,
              new PowerGroup(
                  i2,
                  this.mPowerGroupWakefulnessChangeListener,
                  this.mNotifier,
                  this.mDisplayManagerInternal,
                  1,
                  false,
                  false,
                  this.mClock.uptimeMillis()));
        }
      }
    }
    this.mDirty |= 65536;
  }

  public static void lowLevelShutdown(String str) {
    Slog.m72d(
        "PowerManagerService",
        "[api] lowLevelShutdown: " + PowerManagerUtil.callerInfoToString(true));
    Slog.saveLogAsFile(true);
    if (str == null) {
      str = "";
    }
    SystemProperties.set("sys.powerctl", "shutdown," + str);
  }

  public static void lowLevelReboot(String str) {
    Slog.m72d(
        "PowerManagerService",
        "[api] lowLevelReboot: " + str + PowerManagerUtil.callerInfoToString(true));
    Slog.saveLogAsFile(true);
    if (str == null) {
      str = "";
    }
    if (str.equals("quiescent")) {
      sQuiescent = true;
      str = "";
    } else if (str.endsWith(",quiescent")) {
      sQuiescent = true;
      str = str.substring(0, (str.length() - 9) - 1);
    }
    if (str.equals("recovery") || str.equals("recovery-update")) {
      str = "recovery";
    }
    if (sQuiescent) {
      if (!"".equals(str)) {
        str = str + ",";
      }
      str = str + "quiescent";
    }
    SystemProperties.set("sys.powerctl", "reboot," + str);
    try {
      Thread.sleep(20000L);
    } catch (InterruptedException unused) {
      Thread.currentThread().interrupt();
    }
    Slog.wtf("PowerManagerService", "Unexpected return from lowLevelReboot!");
  }

  public final void registerUserActivityStateListenerInternal(
      PowerManagerInternal.UserActivityStateListener userActivityStateListener) {
    synchronized (this.mLock) {
      this.mUserActivityStateListenerListeners.add(userActivityStateListener);
    }
  }

  public final void unregisterUserActivityStateListenerInternal(
      PowerManagerInternal.UserActivityStateListener userActivityStateListener) {
    synchronized (this.mLock) {
      this.mUserActivityStateListenerListeners.remove(userActivityStateListener);
    }
  }

  public final int getLastUserActivityStateInternal() {
    int i;
    synchronized (this.mLock) {
      i = this.mLastUserActivityStateListenerState;
    }
    return i;
  }

  @Override // com.android.server.Watchdog.Monitor
  public void monitor() {
    synchronized (this.mLock) {
    }
  }

  @NeverCompile
  public final void dumpInternal(PrintWriter printWriter, boolean z) {
    WirelessChargerDetector wirelessChargerDetector;
    printWriter.println("POWER MANAGER (dumpsys power)\n");
    synchronized (this.mLock) {
      printWriter.println("Power Manager State:");
      this.mConstants.dump(printWriter);
      printWriter.println("  mDirty=0x" + Integer.toHexString(this.mDirty));
      printWriter.println(
          "  mWakefulness="
              + PowerManagerInternal.wakefulnessToString(getGlobalWakefulnessLocked()));
      printWriter.println("  mWakefulnessChanging=" + this.mWakefulnessChanging);
      printWriter.println("  mIsPowered=" + this.mIsPowered);
      printWriter.println("  mPlugType=" + this.mPlugType);
      printWriter.println("  mBatteryLevel=" + this.mBatteryLevel);
      printWriter.println("  mDreamsBatteryLevelDrain=" + this.mDreamsBatteryLevelDrain);
      printWriter.println("  mDockState=" + this.mDockState);
      printWriter.println("  mStayOn=" + this.mStayOn);
      printWriter.println("  mProximityPositive=" + this.mProximityPositive);
      printWriter.println("  mBootCompleted=" + this.mBootCompleted);
      printWriter.println("  mSystemReady=" + this.mSystemReady);
      synchronized (this.mEnhancedDischargeTimeLock) {
        printWriter.println(
            "  mEnhancedDischargeTimeElapsed=" + this.mEnhancedDischargeTimeElapsed);
        printWriter.println(
            "  mLastEnhancedDischargeTimeUpdatedElapsed="
                + this.mLastEnhancedDischargeTimeUpdatedElapsed);
        printWriter.println(
            "  mEnhancedDischargePredictionIsPersonalized="
                + this.mEnhancedDischargePredictionIsPersonalized);
      }
      printWriter.println("  mHalAutoSuspendModeEnabled=" + this.mHalAutoSuspendModeEnabled);
      printWriter.println("  mHalInteractiveModeEnabled=" + this.mHalInteractiveModeEnabled);
      printWriter.println("  mWakeLockSummary=0x" + Integer.toHexString(this.mWakeLockSummary));
      printWriter.print("  mNotifyLongScheduled=");
      long j = this.mNotifyLongScheduled;
      if (j == 0) {
        printWriter.print("(none)");
      } else {
        TimeUtils.formatDuration(j, this.mClock.uptimeMillis(), printWriter);
      }
      printWriter.println();
      printWriter.print("  mNotifyLongDispatched=");
      long j2 = this.mNotifyLongDispatched;
      if (j2 == 0) {
        printWriter.print("(none)");
      } else {
        TimeUtils.formatDuration(j2, this.mClock.uptimeMillis(), printWriter);
      }
      printWriter.println();
      printWriter.print("  mNotifyLongNextCheck=");
      long j3 = this.mNotifyLongNextCheck;
      if (j3 == 0) {
        printWriter.print("(none)");
      } else {
        TimeUtils.formatDuration(j3, this.mClock.uptimeMillis(), printWriter);
      }
      printWriter.println();
      printWriter.println(
          "  mRequestWaitForNegativeProximity=" + this.mRequestWaitForNegativeProximity);
      printWriter.println(
          "  mInterceptedPowerKeyForProximity=" + this.mInterceptedPowerKeyForProximity);
      printWriter.println("  mInterceptedKeyForProximity=" + this.mInterceptedKeyForProximity);
      printWriter.println("  mSandmanScheduled=" + this.mSandmanScheduled);
      printWriter.println("  mBatteryLevelLow=" + this.mBatteryLevelLow);
      printWriter.println("  mLightDeviceIdleMode=" + this.mLightDeviceIdleMode);
      printWriter.println("  mDeviceIdleMode=" + this.mDeviceIdleMode);
      printWriter.println("  mDeviceIdleWhitelist=" + Arrays.toString(this.mDeviceIdleWhitelist));
      printWriter.println(
          "  mDeviceIdleTempWhitelist=" + Arrays.toString(this.mDeviceIdleTempWhitelist));
      printWriter.println("  mLowPowerStandbyActive=" + this.mLowPowerStandbyActive);
      printWriter.println("  mLastWakeTime=" + TimeUtils.formatUptime(this.mLastGlobalWakeTime));
      printWriter.println("  mLastSleepTime=" + TimeUtils.formatUptime(this.mLastGlobalSleepTime));
      printWriter.println(
          "  mLastSleepReason=" + PowerManager.sleepReasonToString(this.mLastGlobalSleepReason));
      printWriter.println(
          "  mLastGlobalWakeTimeRealtime="
              + TimeUtils.formatUptime(this.mLastGlobalWakeTimeRealtime));
      printWriter.println(
          "  mLastGlobalSleepTimeRealtime="
              + TimeUtils.formatUptime(this.mLastGlobalSleepTimeRealtime));
      printWriter.println(
          "  mLastInteractivePowerHintTime="
              + TimeUtils.formatUptime(this.mLastInteractivePowerHintTime));
      printWriter.println(
          "  mLastScreenBrightnessBoostTime="
              + TimeUtils.formatUptime(this.mLastScreenBrightnessBoostTime));
      printWriter.println(
          "  mScreenBrightnessBoostInProgress=" + this.mScreenBrightnessBoostInProgress);
      printWriter.println(
          "  mDefaultDisplayReadyByProximity=" + this.mDefaultDisplayReadyByProximity);
      printWriter.println(
          "  mHoldingWakeLockSuspendBlocker=" + this.mHoldingWakeLockSuspendBlocker);
      printWriter.println("  mHoldingDisplaySuspendBlocker=" + this.mHoldingDisplaySuspendBlocker);
      printWriter.println();
      printWriter.println("Settings and Configuration:");
      printWriter.println(
          "  mDecoupleHalAutoSuspendModeFromDisplayConfig="
              + this.mDecoupleHalAutoSuspendModeFromDisplayConfig);
      printWriter.println(
          "  mDecoupleHalInteractiveModeFromDisplayConfig="
              + this.mDecoupleHalInteractiveModeFromDisplayConfig);
      printWriter.println(
          "  mWakeUpWhenPluggedOrUnpluggedConfig=" + this.mWakeUpWhenPluggedOrUnpluggedConfig);
      printWriter.println(
          "  mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig="
              + this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig);
      printWriter.println("  mTheaterModeEnabled=" + this.mTheaterModeEnabled);
      printWriter.println("  mKeepDreamingWhenUnplugging=" + this.mKeepDreamingWhenUnplugging);
      printWriter.println(
          "  mSuspendWhenScreenOffDueToProximityConfig="
              + this.mSuspendWhenScreenOffDueToProximityConfig);
      printWriter.println("  mDreamsSupportedConfig=" + this.mDreamsSupportedConfig);
      printWriter.println("  mDreamsEnabledByDefaultConfig=" + this.mDreamsEnabledByDefaultConfig);
      printWriter.println(
          "  mDreamsActivatedOnSleepByDefaultConfig="
              + this.mDreamsActivatedOnSleepByDefaultConfig);
      printWriter.println(
          "  mDreamsActivatedOnDockByDefaultConfig=" + this.mDreamsActivatedOnDockByDefaultConfig);
      printWriter.println("  mDreamsEnabledOnBatteryConfig=" + this.mDreamsEnabledOnBatteryConfig);
      printWriter.println(
          "  mDreamsBatteryLevelMinimumWhenPoweredConfig="
              + this.mDreamsBatteryLevelMinimumWhenPoweredConfig);
      printWriter.println(
          "  mDreamsBatteryLevelMinimumWhenNotPoweredConfig="
              + this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig);
      printWriter.println(
          "  mDreamsBatteryLevelDrainCutoffConfig=" + this.mDreamsBatteryLevelDrainCutoffConfig);
      printWriter.println("  mDreamsEnabledSetting=" + this.mDreamsEnabledSetting);
      printWriter.println("  mDreamsActivateOnSleepSetting=" + this.mDreamsActivateOnSleepSetting);
      printWriter.println("  mDreamsActivateOnDockSetting=" + this.mDreamsActivateOnDockSetting);
      printWriter.println("  mDozeAfterScreenOff=" + this.mDozeAfterScreenOff);
      printWriter.println(
          "  mMinimumScreenOffTimeoutConfig=" + this.mMinimumScreenOffTimeoutConfig);
      printWriter.println(
          "  mMaximumScreenDimDurationConfig=" + this.mMaximumScreenDimDurationConfig);
      printWriter.println("  mMaximumScreenDimRatioConfig=" + this.mMaximumScreenDimRatioConfig);
      printWriter.println("  mAttentiveTimeoutConfig=" + this.mAttentiveTimeoutConfig);
      printWriter.println("  mAttentiveTimeoutSetting=" + this.mAttentiveTimeoutSetting);
      printWriter.println(
          "  mAttentiveWarningDurationConfig=" + this.mAttentiveWarningDurationConfig);
      printWriter.println("  mScreenOffTimeoutSetting=" + this.mScreenOffTimeoutSetting);
      printWriter.println("  mSleepTimeoutSetting=" + this.mSleepTimeoutSetting);
      printWriter.println(
          "  mMaximumScreenOffTimeoutFromDeviceAdmin="
              + this.mMaximumScreenOffTimeoutFromDeviceAdmin
              + " (enforced="
              + isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()
              + ")");
      StringBuilder sb = new StringBuilder();
      sb.append("  mStayOnWhilePluggedInSetting=");
      sb.append(this.mStayOnWhilePluggedInSetting);
      printWriter.println(sb.toString());
      printWriter.println(
          "  mScreenBrightnessOverrideFromWindowManager="
              + this.mScreenBrightnessOverrideFromWindowManager);
      printWriter.println(
          "  mUserActivityTimeoutOverrideFromWindowManager="
              + this.mUserActivityTimeoutOverrideFromWindowManager);
      printWriter.println(
          "  mUserActivityTimeoutForDexOverrideFromWindowManager="
              + this.mUserActivityTimeoutForDexOverrideFromWindowManager);
      printWriter.println(
          "  mUserInactiveOverrideFromWindowManager="
              + this.mUserInactiveOverrideFromWindowManager);
      printWriter.println(
          "  mDozeScreenStateOverrideFromDreamManager="
              + this.mDozeScreenStateOverrideFromDreamManager);
      printWriter.println(
          "  mDrawWakeLockOverrideFromSidekick=" + this.mDrawWakeLockOverrideFromSidekick);
      printWriter.println(
          "  mDozeScreenBrightnessOverrideFromDreamManager="
              + this.mDozeScreenBrightnessOverrideFromDreamManager);
      printWriter.println("  mScreenBrightnessMinimum=" + this.mScreenBrightnessMinimum);
      printWriter.println("  mScreenBrightnessMaximum=" + this.mScreenBrightnessMaximum);
      printWriter.println("  mScreenBrightnessDefault=" + this.mScreenBrightnessDefault);
      printWriter.println("  mDoubleTapWakeEnabled=" + this.mDoubleTapWakeEnabled);
      printWriter.println("  mForegroundProfile=" + this.mForegroundProfile);
      printWriter.println("  mUserId=" + this.mUserId);
      printWriter.println("  mLastWakeUpReason: " + this.mLastWakeUpReason);
      printWriter.println("  mOutdoorModeSetting: " + this.mOutdoorModeSetting);
      printWriter.println("  mAutoBrightnessLowerLimit=" + this.mAutoBrightnessLowerLimit);
      printWriter.println("  mAutoBrightnessUpperLimit=" + this.mAutoBrightnessUpperLimit);
      printWriter.println("  mLastAutoBrightnessLimitTime=" + this.mLastAutoBrightnessLimitTime);
      printWriter.println(
          "  mAutoBrightnessLimitLastCaller=" + this.mAutoBrightnessLimitLastCaller);
      printWriter.println(
          "  SEC_FEATURE_USE_GED_DOZE: " + PowerManagerUtil.SEC_FEATURE_USE_GED_DOZE);
      printWriter.println(
          "  SEC_FEATURE_SEAMLESS_AOD: " + PowerManagerUtil.SEC_FEATURE_SEAMLESS_AOD);
      printWriter.println(
          "  SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE: "
              + PowerManagerUtil.SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE);
      printWriter.println(
          "  SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED: "
              + PowerManagerUtil.SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED);
      printWriter.println(
          "  SEC_FEATURE_AOD_LOOK_CHARGING_UI: "
              + PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI);
      printWriter.println("  mAodMode: " + this.mAodMode);
      printWriter.println(
          "  mShouldWaitForTransitionToAodUi: " + this.mShouldWaitForTransitionToAodUi);
      printWriter.println(
          "  SEC_FEATURE_DEX_DUAL_VIEW: " + PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW);
      printWriter.println(
          "  SEC_FEATURE_FOLD_COVER_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY);
      printWriter.println(
          "  SEC_FEATURE_FLIP_COVER_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY);
      printWriter.println(
          "  SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY: "
              + PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY);
      printWriter.println(
          "  SEC_FEATURE_DUAL_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY);
      printWriter.println(
          "  SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING: "
              + PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING);
      long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
      long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
      long screenOffTimeoutLocked =
          getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
      long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
      printWriter.println();
      printWriter.println("Attentive timeout: " + attentiveTimeoutLocked + " ms");
      printWriter.println("Sleep timeout: " + sleepTimeoutLocked + " ms");
      printWriter.println("Screen off timeout: " + screenOffTimeoutLocked + " ms");
      printWriter.println("Screen dim duration: " + screenDimDurationLocked + " ms");
      printWriter.println(
          "Screen dim duration override: "
              + this.mScreenDimDurationOverrideFromWindowManager
              + " ms");
      printWriter.println(
          "SQD Screen dim duration override: " + this.mScreenDimDurationOverrideFromSQD + " ms");
      printWriter.println("Screen off timeout for DeX: " + this.mScreenOffTimeoutForDex + " ms");
      printWriter.println();
      printWriter.println("  mSmartStayEnabledSetting: " + this.mSmartStayEnabledSetting);
      this.mSmartStayController.dumpInternal(printWriter);
      printWriter.println();
      printWriter.println("Wakelock Blacklist App id: size=" + this.mWakelockBlacklistAppid.size());
      Iterator it = this.mWakelockBlacklistAppid.iterator();
      while (it.hasNext()) {
        printWriter.println("  " + ((Integer) it.next()).intValue());
      }
      printWriter.println();
      printWriter.print("UID states (changing=");
      printWriter.print(this.mUidsChanging);
      printWriter.print(" changed=");
      printWriter.print(this.mUidsChanged);
      printWriter.println("):");
      for (int i = 0; i < this.mUidState.size(); i++) {
        UidState uidState = (UidState) this.mUidState.valueAt(i);
        printWriter.print("  UID ");
        UserHandle.formatUid(printWriter, this.mUidState.keyAt(i));
        printWriter.print(": ");
        if (uidState.mActive) {
          printWriter.print("  ACTIVE ");
        } else {
          printWriter.print("INACTIVE ");
        }
        printWriter.print(" count=");
        printWriter.print(uidState.mNumWakeLocks);
        printWriter.print(" state=");
        printWriter.println(uidState.mProcState);
      }
      printWriter.println();
      printWriter.println("Looper state:");
      this.mHandler.getLooper().dump(new PrintWriterPrinter(printWriter), "  ");
      printWriter.println("Clear Cover:");
      printWriter.println("  mIsCoverClosed: " + this.mIsCoverClosed);
      printWriter.println("  mlastUpdateCoverStateReason: " + this.mlastUpdateCoverStateReason);
      printWriter.println(
          "  mlastUpdateCoverStateTime: = "
              + TimeUtils.formatUptime(this.mlastUpdateCoverStateTime));
      printWriter.println("  mFeatureCoverSysfs: " + this.mFeatureCoverSysfs);
      printWriter.println(
          "  mCoverType: "
              + this.mCoverType
              + " at "
              + TimeUtils.formatUptime(this.mlastSetCoverTypeTime));
      StringBuilder sb2 = new StringBuilder();
      sb2.append("  mlastUpdateCoverTypeReason: ");
      sb2.append(this.mlastUpdateCoverTypeReason);
      printWriter.println(sb2.toString());
      printWriter.println(
          "  mScreenBrightnessForClearCoverConfig=" + this.mScreenBrightnessForClearCoverConfig);
      printWriter.println("  mBrightnessLimitByCoverConfig: " + this.mBrightnessLimitByCoverConfig);
      printWriter.println("Master Brightness Limit:");
      printWriter.println("  mMasterBrightnessLowerLimit: " + this.mMasterBrightnessLowerLimit);
      printWriter.println("  mMasterBrightnessUpperLimit: " + this.mMasterBrightnessUpperLimit);
      printWriter.println(
          "  mMasterBrightnessLimitLastCaller: " + this.mMasterBrightnessLimitLastCaller);
      printWriter.println("  mMasterBrightnessLimitPeriod: " + this.mMasterBrightnessLimitPeriod);
      printWriter.println("  mMasterBrightnessLimitRunning: " + this.mMasterBrightnessLimitRunning);
      printWriter.println(
          "  mLastRequestedLimitationOfBrightness: " + this.mLastRequestedLimitationOfBrightness);
      printWriter.println("  mTargetBrightnessForLimit : " + this.mTargetBrightnessForLimit);
      printWriter.println();
      printWriter.println("FTA mode: " + this.mFTAMode);
      printWriter.println("SEC_USE_FACTORY_BINARY: " + SEC_USE_FACTORY_BINARY);
      printWriter.println();
      printWriter.println("Wake Locks: size=" + this.mWakeLocks.size());
      Iterator it2 = this.mWakeLocks.iterator();
      while (it2.hasNext()) {
        printWriter.println("  " + ((WakeLock) it2.next()));
      }
      printWriter.println();
      printWriter.println("Suspend Blockers: size=" + this.mSuspendBlockers.size());
      Iterator it3 = this.mSuspendBlockers.iterator();
      while (it3.hasNext()) {
        printWriter.println("  " + ((SuspendBlocker) it3.next()));
      }
      printWriter.println();
      printWriter.println("Display Power: " + this.mDisplayPowerCallbacks);
      printWriter.println();
      printWriter.println("mFreezingScreenBrightness: " + this.mFreezingScreenBrightness);
      if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN) {
        printWriter.println();
        printWriter.println("mScreenCurtainController: " + this.mScreenCurtainController);
      }
      this.mPowerHistorian.dump(printWriter);
      printWriter.println();
      StringBuilder sb3 = new StringBuilder();
      sb3.append("screen on profiler: ");
      RingBuffer ringBuffer = PowerManagerUtil.sScreenOnProfilers;
      sb3.append(ringBuffer.size());
      printWriter.println(sb3.toString());
      PowerManagerUtil.ScreenOnProfiler[] screenOnProfilerArr =
          (PowerManagerUtil.ScreenOnProfiler[]) ringBuffer.toArray();
      for (PowerManagerUtil.ScreenOnProfiler screenOnProfiler : screenOnProfilerArr) {
        printWriter.print(screenOnProfiler.toString(true));
        printWriter.println();
      }
      printWriter.println();
      StringBuilder sb4 = new StringBuilder();
      sb4.append("screen off profiler: ");
      RingBuffer ringBuffer2 = PowerManagerUtil.sScreenOffProfilers;
      sb4.append(ringBuffer2.size());
      printWriter.println(sb4.toString());
      PowerManagerUtil.ScreenOffProfiler[] screenOffProfilerArr =
          (PowerManagerUtil.ScreenOffProfiler[]) ringBuffer2.toArray();
      for (PowerManagerUtil.ScreenOffProfiler screenOffProfiler : screenOffProfilerArr) {
        printWriter.print(screenOffProfiler.toString(true));
        printWriter.println();
      }
      this.mBatterySaverPolicy.dump(printWriter);
      this.mBatterySaverStateMachine.dump(printWriter);
      this.mAttentionDetector.dump(printWriter);
      printWriter.println();
      int size = this.mProfilePowerState.size();
      printWriter.println("Profile power states: size=" + size);
      for (int i2 = 0; i2 < size; i2++) {
        ProfilePowerState profilePowerState =
            (ProfilePowerState) this.mProfilePowerState.valueAt(i2);
        printWriter.print("  mUserId=");
        printWriter.print(profilePowerState.mUserId);
        printWriter.print(" mScreenOffTimeout=");
        printWriter.print(profilePowerState.mScreenOffTimeout);
        printWriter.print(" mScreenOffTimeoutForUser=");
        printWriter.print(profilePowerState.mScreenOffTimeoutForUser);
        printWriter.print(" mWakeLockSummary=");
        printWriter.print(profilePowerState.mWakeLockSummary);
        printWriter.print(" mLastUserActivityTime=");
        printWriter.print(profilePowerState.mLastUserActivityTime);
        printWriter.print(" mLockingNotified=");
        printWriter.println(profilePowerState.mLockingNotified);
      }
      this.mSuspendBlockerMonitor.dump(printWriter);
      for (int i3 = 0; i3 < this.mPowerGroups.size(); i3++) {
        printWriter.println(
            ((PowerGroup) this.mPowerGroups.valueAt(i3)).getSuspendBlockerMonitorInfo());
      }
      printWriter.println("Display Group User Activity:");
      for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
        PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i4);
        printWriter.println("  displayGroupId=" + powerGroup.getGroupId());
        printWriter.println(
            "  userActivitySummary=0x"
                + Integer.toHexString(powerGroup.getUserActivitySummaryLocked()));
        printWriter.println(
            "  lastUserActivityTime="
                + TimeUtils.formatUptime(powerGroup.getLastUserActivityTimeLocked()));
        printWriter.println(
            "  lastUserActivityTimeNoChangeLights="
                + TimeUtils.formatUptime(powerGroup.getLastUserActivityTimeNoChangeLightsLocked()));
        printWriter.println(
            "  mWakeLockSummary=0x" + Integer.toHexString(powerGroup.getWakeLockSummaryLocked()));
      }
      wirelessChargerDetector = this.mWirelessChargerDetector;
    }
    if (wirelessChargerDetector != null) {
      wirelessChargerDetector.dump(printWriter);
    }
    if (!z) {
      printWriter.println(
          "\n\n ====================== beginning of PMS  log ======================");
      printWriter.println("  Day        Time    Tid Debug      TAG          INFO");
      printWriter.println(" ===================================================================");
      Slog.dump(printWriter);
      printWriter.println(" ======================= end of PMS log ========================");
      printWriter.println(" PMSLog Done !!! \n\n");
    }
    Notifier notifier = this.mNotifier;
    if (notifier != null) {
      notifier.dump(printWriter);
    }
    this.mAmbientDisplaySuppressionController.dump(printWriter);
    this.mLowPowerStandbyController.dump(printWriter);
  }

  public final void dumpProto(FileDescriptor fileDescriptor) {
    WirelessChargerDetector wirelessChargerDetector;
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
    synchronized (this.mLock) {
      this.mConstants.dumpProto(protoOutputStream);
      protoOutputStream.write(1120986464258L, this.mDirty);
      protoOutputStream.write(1159641169923L, getGlobalWakefulnessLocked());
      protoOutputStream.write(1133871366148L, this.mWakefulnessChanging);
      protoOutputStream.write(1133871366149L, this.mIsPowered);
      protoOutputStream.write(1159641169926L, this.mPlugType);
      protoOutputStream.write(1120986464263L, this.mBatteryLevel);
      protoOutputStream.write(1120986464313L, this.mDreamsBatteryLevelDrain);
      protoOutputStream.write(1159641169929L, this.mDockState);
      protoOutputStream.write(1133871366154L, this.mStayOn);
      protoOutputStream.write(1133871366155L, this.mProximityPositive);
      protoOutputStream.write(1133871366156L, this.mBootCompleted);
      protoOutputStream.write(1133871366157L, this.mSystemReady);
      synchronized (this.mEnhancedDischargeTimeLock) {
        protoOutputStream.write(1112396529716L, this.mEnhancedDischargeTimeElapsed);
        protoOutputStream.write(1112396529717L, this.mLastEnhancedDischargeTimeUpdatedElapsed);
        protoOutputStream.write(1133871366198L, this.mEnhancedDischargePredictionIsPersonalized);
      }
      protoOutputStream.write(1133871366158L, this.mHalAutoSuspendModeEnabled);
      protoOutputStream.write(1133871366159L, this.mHalInteractiveModeEnabled);
      long start = protoOutputStream.start(1146756268048L);
      protoOutputStream.write(1133871366145L, (this.mWakeLockSummary & 1) != 0);
      protoOutputStream.write(1133871366146L, (this.mWakeLockSummary & 2) != 0);
      long j = 1133871366147L;
      protoOutputStream.write(1133871366147L, (this.mWakeLockSummary & 4) != 0);
      protoOutputStream.write(1133871366148L, (this.mWakeLockSummary & 8) != 0);
      protoOutputStream.write(1133871366149L, (this.mWakeLockSummary & 16) != 0);
      protoOutputStream.write(1133871366150L, (this.mWakeLockSummary & 32) != 0);
      protoOutputStream.write(1133871366151L, (this.mWakeLockSummary & 64) != 0);
      protoOutputStream.write(1133871366152L, (this.mWakeLockSummary & 128) != 0);
      protoOutputStream.end(start);
      protoOutputStream.write(1112396529681L, this.mNotifyLongScheduled);
      protoOutputStream.write(1112396529682L, this.mNotifyLongDispatched);
      protoOutputStream.write(1112396529683L, this.mNotifyLongNextCheck);
      int i = 0;
      while (i < this.mPowerGroups.size()) {
        PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i);
        long start2 = protoOutputStream.start(2246267895828L);
        protoOutputStream.write(1120986464262L, powerGroup.getGroupId());
        long userActivitySummaryLocked = powerGroup.getUserActivitySummaryLocked();
        protoOutputStream.write(1133871366145L, (userActivitySummaryLocked & 1) != 0);
        protoOutputStream.write(1133871366146L, (userActivitySummaryLocked & 2) != 0);
        protoOutputStream.write(j, (userActivitySummaryLocked & 4) != 0);
        protoOutputStream.write(1112396529668L, powerGroup.getLastUserActivityTimeLocked());
        protoOutputStream.write(
            1112396529669L, powerGroup.getLastUserActivityTimeNoChangeLightsLocked());
        protoOutputStream.end(start2);
        i++;
        j = 1133871366147L;
      }
      protoOutputStream.write(1133871366165L, this.mRequestWaitForNegativeProximity);
      protoOutputStream.write(1133871366166L, this.mSandmanScheduled);
      protoOutputStream.write(1133871366168L, this.mBatteryLevelLow);
      protoOutputStream.write(1133871366169L, this.mLightDeviceIdleMode);
      protoOutputStream.write(1133871366170L, this.mDeviceIdleMode);
      for (int i2 : this.mDeviceIdleWhitelist) {
        protoOutputStream.write(2220498092059L, i2);
      }
      for (int i3 : this.mDeviceIdleTempWhitelist) {
        protoOutputStream.write(2220498092060L, i3);
      }
      protoOutputStream.write(1133871366199L, this.mLowPowerStandbyActive);
      protoOutputStream.write(1112396529693L, this.mLastGlobalWakeTime);
      protoOutputStream.write(1112396529694L, this.mLastGlobalSleepTime);
      protoOutputStream.write(1112396529697L, this.mLastInteractivePowerHintTime);
      protoOutputStream.write(1112396529698L, this.mLastScreenBrightnessBoostTime);
      protoOutputStream.write(1133871366179L, this.mScreenBrightnessBoostInProgress);
      protoOutputStream.write(1133871366181L, this.mHoldingWakeLockSuspendBlocker);
      protoOutputStream.write(1133871366182L, this.mHoldingDisplaySuspendBlocker);
      long start3 = protoOutputStream.start(1146756268071L);
      protoOutputStream.write(1133871366145L, this.mDecoupleHalAutoSuspendModeFromDisplayConfig);
      protoOutputStream.write(1133871366146L, this.mDecoupleHalInteractiveModeFromDisplayConfig);
      protoOutputStream.write(1133871366147L, this.mWakeUpWhenPluggedOrUnpluggedConfig);
      protoOutputStream.write(
          1133871366148L, this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig);
      protoOutputStream.write(1133871366149L, this.mTheaterModeEnabled);
      protoOutputStream.write(1133871366150L, this.mSuspendWhenScreenOffDueToProximityConfig);
      protoOutputStream.write(1133871366151L, this.mDreamsSupportedConfig);
      protoOutputStream.write(1133871366152L, this.mDreamsEnabledByDefaultConfig);
      protoOutputStream.write(1133871366153L, this.mDreamsActivatedOnSleepByDefaultConfig);
      protoOutputStream.write(1133871366154L, this.mDreamsActivatedOnDockByDefaultConfig);
      protoOutputStream.write(1133871366155L, this.mDreamsEnabledOnBatteryConfig);
      protoOutputStream.write(1172526071820L, this.mDreamsBatteryLevelMinimumWhenPoweredConfig);
      protoOutputStream.write(1172526071821L, this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig);
      protoOutputStream.write(1172526071822L, this.mDreamsBatteryLevelDrainCutoffConfig);
      protoOutputStream.write(1133871366159L, this.mDreamsEnabledSetting);
      protoOutputStream.write(1133871366160L, this.mDreamsActivateOnSleepSetting);
      protoOutputStream.write(1133871366161L, this.mDreamsActivateOnDockSetting);
      protoOutputStream.write(1133871366162L, this.mDozeAfterScreenOff);
      protoOutputStream.write(1120986464275L, this.mMinimumScreenOffTimeoutConfig);
      protoOutputStream.write(1120986464276L, this.mMaximumScreenDimDurationConfig);
      protoOutputStream.write(1108101562389L, this.mMaximumScreenDimRatioConfig);
      protoOutputStream.write(1120986464278L, this.mScreenOffTimeoutSetting);
      protoOutputStream.write(1172526071831L, this.mSleepTimeoutSetting);
      protoOutputStream.write(1172526071845L, this.mAttentiveTimeoutSetting);
      protoOutputStream.write(1172526071846L, this.mAttentiveTimeoutConfig);
      protoOutputStream.write(1172526071847L, this.mAttentiveWarningDurationConfig);
      protoOutputStream.write(
          1120986464280L, Math.min(this.mMaximumScreenOffTimeoutFromDeviceAdmin, 2147483647L));
      protoOutputStream.write(
          1133871366169L, isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked());
      long start4 = protoOutputStream.start(1146756268058L);
      protoOutputStream.write(1133871366145L, (this.mStayOnWhilePluggedInSetting & 1) != 0);
      protoOutputStream.write(1133871366146L, (this.mStayOnWhilePluggedInSetting & 2) != 0);
      protoOutputStream.write(1133871366147L, (this.mStayOnWhilePluggedInSetting & 4) != 0);
      protoOutputStream.write(1133871366148L, (this.mStayOnWhilePluggedInSetting & 8) != 0);
      protoOutputStream.end(start4);
      protoOutputStream.write(1172526071836L, this.mScreenBrightnessOverrideFromWindowManager);
      protoOutputStream.write(1176821039133L, this.mUserActivityTimeoutOverrideFromWindowManager);
      protoOutputStream.write(1133871366174L, this.mUserInactiveOverrideFromWindowManager);
      protoOutputStream.write(1159641169951L, this.mDozeScreenStateOverrideFromDreamManager);
      protoOutputStream.write(1133871366180L, this.mDrawWakeLockOverrideFromSidekick);
      protoOutputStream.write(1108101562400L, this.mDozeScreenBrightnessOverrideFromDreamManager);
      long start5 = protoOutputStream.start(1146756268065L);
      protoOutputStream.write(1108101562372L, this.mScreenBrightnessMinimum);
      protoOutputStream.write(1108101562373L, this.mScreenBrightnessMaximum);
      protoOutputStream.write(1108101562374L, this.mScreenBrightnessDefault);
      protoOutputStream.end(start5);
      protoOutputStream.write(1133871366178L, this.mDoubleTapWakeEnabled);
      protoOutputStream.end(start3);
      long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
      long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
      long screenOffTimeoutLocked =
          getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
      long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
      protoOutputStream.write(1172526071859L, attentiveTimeoutLocked);
      protoOutputStream.write(1172526071848L, sleepTimeoutLocked);
      protoOutputStream.write(1120986464297L, screenOffTimeoutLocked);
      protoOutputStream.write(1120986464298L, screenDimDurationLocked);
      protoOutputStream.write(1133871366187L, this.mUidsChanging);
      protoOutputStream.write(1133871366188L, this.mUidsChanged);
      for (int i4 = 0; i4 < this.mUidState.size(); i4++) {
        UidState uidState = (UidState) this.mUidState.valueAt(i4);
        long start6 = protoOutputStream.start(2246267895853L);
        int keyAt = this.mUidState.keyAt(i4);
        protoOutputStream.write(1120986464257L, keyAt);
        protoOutputStream.write(1138166333442L, UserHandle.formatUid(keyAt));
        protoOutputStream.write(1133871366147L, uidState.mActive);
        protoOutputStream.write(1120986464260L, uidState.mNumWakeLocks);
        protoOutputStream.write(
            1159641169925L, ActivityManager.processStateAmToProto(uidState.mProcState));
        protoOutputStream.end(start6);
      }
      this.mBatterySaverStateMachine.dumpProto(protoOutputStream, 1146756268082L);
      this.mHandler.getLooper().dumpDebug(protoOutputStream, 1146756268078L);
      Iterator it = this.mWakeLocks.iterator();
      while (it.hasNext()) {
        ((WakeLock) it.next()).dumpDebug(protoOutputStream, 2246267895855L);
      }
      Iterator it2 = this.mSuspendBlockers.iterator();
      while (it2.hasNext()) {
        ((SuspendBlocker) it2.next()).dumpDebug(protoOutputStream, 2246267895856L);
      }
      wirelessChargerDetector = this.mWirelessChargerDetector;
    }
    if (wirelessChargerDetector != null) {
      wirelessChargerDetector.dumpDebug(protoOutputStream, 1146756268081L);
    }
    this.mLowPowerStandbyController.dumpProto(protoOutputStream, 1146756268088L);
    protoOutputStream.flush();
  }

  public final void incrementBootCount() {
    int i;
    synchronized (this.mLock) {
      try {
        i = Settings.Global.getInt(getContext().getContentResolver(), "boot_count");
      } catch (Settings.SettingNotFoundException unused) {
        i = 0;
      }
      Settings.Global.putInt(getContext().getContentResolver(), "boot_count", i + 1);
    }
  }

  public static WorkSource copyWorkSource(WorkSource workSource) {
    if (workSource != null) {
      return new WorkSource(workSource);
    }
    return null;
  }

  public final void setDozeModeBySysfs(int i) {
    if (this.mAlpmHlpmMode != i) {
      if (i == -1) {
        Slog.m72d("PowerManagerService", "setDozeModeBySysfs:  value is -1 , we ignore it. ");
        return;
      }
      this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
      this.mAlpmHlpmMode = i;
      String num = Integer.toString(i);
      PowerManagerUtil.fileWriteString("/sys/class/lcd/panel/alpm", num);
      if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
        PowerManagerUtil.fileWriteString("/sys/class/lcd/panel1/alpm", num);
      }
    }
  }

  public void setHbmBlockInternal(boolean z) {
    synchronized (this.mLock) {
      if (this.mHbmBlock == z) {
        Slog.m72d("PowerManagerService", "setHbmBlockInternal: already " + z);
        return;
      }
      this.mHbmBlock = z;
      Slog.m72d("PowerManagerService", "setHbmBlockInternal: " + z);
      this.mDirty = this.mDirty | 32;
      updatePowerStateLocked();
    }
  }

  public void setFreezeBrightnessModeInternal(boolean z) {
    synchronized (this.mLock) {
      if (this.mFreezeBrightnessMode != z) {
        Slog.m72d("PowerManagerService", "setFreezeBrightnessModeInternal: " + z);
        this.mFreezeBrightnessMode = z;
        this.mDirty = this.mDirty | 32;
        updatePowerStateLocked();
      }
    }
  }

  final class BatteryReceiver extends BroadcastReceiver {
    public BatteryReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.handleBatteryStateChangedLocked();
      }
    }
  }

  public final class DreamReceiver extends BroadcastReceiver {
    public DreamReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      Slog.m72d("PowerManagerService", "[api] DreamReceiver: onReceive: " + intent.getAction());
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.scheduleSandmanLocked();
      }
    }
  }

  final class UserSwitchedReceiver extends BroadcastReceiver {
    public UserSwitchedReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      Slog.m72d(
          "PowerManagerService",
          "[api] UserSwitchedReceiver: onReceive: UserHandle: "
              + ActivityManager.getCurrentUser()
              + " -> "
              + intent.getIntExtra("android.intent.extra.user_handle", 0));
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.setQuickPanelBrightnessBarDisabled(
            powerManagerService.mLastBrightnessOverrideState);
        PowerManagerService.this.handleSettingsChangedLocked();
      }
    }
  }

  public final class DockReceiver extends BroadcastReceiver {
    public DockReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      synchronized (PowerManagerService.this.mLock) {
        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
        Slog.m72d(
            "PowerManagerService",
            "[api] DockReceiver: onReceive: DockState: "
                + PowerManagerService.this.mDockState
                + " -> "
                + intExtra);
        if (PowerManagerService.this.mDockState != intExtra) {
          PowerManagerService.this.mDockState = intExtra;
          PowerManagerService.this.mDirty |= 1024;
          PowerManagerService.this.updatePowerStateLocked();
        }
      }
    }
  }

  public final class FTAModeChangeReceiver extends BroadcastReceiver {
    public FTAModeChangeReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      Slog.m72d("PowerManagerService", "[api] FTAModeChangeReceiver: onReceive: ");
      String action = intent.getAction();
      synchronized (PowerManagerService.this.mLock) {
        if (action.equals("com.sec.factory.app.factorytest.FTA_ON")) {
          PowerManagerService.this.mFTAMode = true;
          Slog.m72d("PowerManagerService", "FTA mode ON");
        } else if (action.equals("com.sec.factory.app.factorytest.FTA_OFF")) {
          PowerManagerService.this.mFTAMode = false;
          Slog.m72d("PowerManagerService", "FTA mode OFF");
        }
        PowerManagerService.this.mDirty |= 8;
        PowerManagerService.this.updatePowerStateLocked();
      }
    }
  }

  public final class SettingsObserver extends ContentObserver {
    public SettingsObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      Slog.m72d("PowerManagerService", "[api] SettingsObserver: onChange: " + uri);
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.handleSettingsChangedLocked();
      }
    }
  }

  public final class DexScreenOffTimeoutObserver extends ContentObserver {
    public DexScreenOffTimeoutObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      super.onChange(z);
      PowerManagerService.this.handleDexScreenOffTimeoutChange();
    }
  }

  public final void handleDexScreenOffTimeoutChange() {
    try {
      Bundle bundle = new Bundle();
      bundle.putString("key", "timeout_dex");
      bundle.putString("def", "600000");
      Bundle call =
          this.mContext
              .getContentResolver()
              .call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
      if (call != null) {
        String string = call.getString("timeout_dex");
        int parseInt = Integer.parseInt(string);
        synchronized (this.mLock) {
          if (parseInt != this.mScreenOffTimeoutForDex) {
            Slog.m72d(
                "PowerManagerService",
                "Dex screen off timeout : " + this.mScreenOffTimeoutForDex + " -> " + parseInt);
            this.mScreenOffTimeoutForDex = parseInt;
            this.mDirty = this.mDirty | 4;
            updatePowerStateLocked();
          }
        }
        Slog.m72d(
            "PowerManagerService",
            "timeout for dex is " + string + " = " + this.mScreenOffTimeoutForDex);
      }
    } catch (IllegalArgumentException unused) {
      Slog.m74e("PowerManagerService", "IllegalArgumentException :: getDeXSettings timeout_dex");
    }
  }

  public final class PowerManagerHandlerCallback implements Handler.Callback {
    public PowerManagerHandlerCallback() {}

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        PowerManagerService.this.mAbuseWakeLockDetector.handleAbuseWakelockWhenUserActivityChanged(
            PowerManagerService.this.mWakeLocks);
        PowerManagerService.this.handleUserActivityTimeout();
      } else if (i == 2) {
        PowerManagerService.this.handleSandman(message.arg1);
      } else if (i == 3) {
        PowerManagerService.this.handleScreenBrightnessBoostTimeout();
      } else if (i == 4) {
        PowerManagerService.this.checkForLongWakeLocks();
      } else if (i == 5) {
        PowerManagerService.this.handleAttentiveTimeout();
      }
      return true;
    }
  }

  public final void setEnsureTransitionToDozingReleaserLocked(boolean z) {
    if (z) {
      if (this.mEnsureTransitionToDozingReleaserRunning) {
        return;
      }
      Slog.m72d("PowerManagerService", "postDelayed mEnsureTransitionToDozingReleaserRunning");
      this.mEnsureTransitionToDozingReleaserRunning = true;
      this.mHandler.postDelayed(this.mEnsureTransitionToDozingReleaser, 5000L);
      return;
    }
    if (this.mEnsureTransitionToDozingReleaserRunning) {
      this.mHandler.removeCallbacks(this.mEnsureTransitionToDozingReleaser);
      this.mEnsureTransitionToDozingReleaserRunning = false;
    }
  }

  public final boolean isCoverClosedBySysfs() {
    return isSupportCover()
        && "CLOSE".equals(PowerManagerUtil.readFromFile("/sys/class/sec/hall_ic/hall_detect"));
  }

  public final boolean isSupportCover() {
    return this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.cover.flip")
        || this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.cover.sview");
  }

  public final boolean sensorBlockedByCoverLocked() {
    int i = this.mCoverType;
    if (i == -1 || i == 2) {
      if (this.mCoverAuthReady) {
        return false;
      }
      return this.mIsCoverClosed;
    }
    if (i == 9 || i == 10) {
      return false;
    }
    return this.mIsCoverClosed;
  }

  public final boolean screenBlockedByCoverLocked() {
    int i = this.mCoverType;
    if (i == 0 || i == 7 || i == 100) {
      return this.mIsCoverClosed;
    }
    return false;
  }

  public final boolean verifiedCoverClosedLocked() {
    int i = this.mCoverType;
    if (i == -1 || i == 2) {
      return false;
    }
    return this.mIsCoverClosed;
  }

  public final boolean brightnessLimitByCoverLocked() {
    switch (this.mCoverType) {
      case 15:
      case 16:
      case 17:
        return true;
      default:
        return false;
    }
  }

  public final void printWakeLockLocked() {
    PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(0);
    int userActivitySummaryLocked = powerGroup.getUserActivitySummaryLocked();
    DisplayManagerInternal.DisplayPowerRequest displayPowerRequestLocked =
        powerGroup.getDisplayPowerRequestLocked();
    boolean isReadyLocked = powerGroup.isReadyLocked();
    if (!this.mSystemReady || powerGroup.isBrightOrDimLocked()) {
      Slog.m76i(
          "PowerManagerService", "[PWL] On : " + TimeUtils.formatUptime(this.mLastGlobalWakeTime));
      Slog.m76i(
          "PowerManagerService",
          "[PWL]  mStayOn: "
              + this.mStayOn
              + "  mWakeLockSummary & WAKE_LOCK_STAY_AWAKE: "
              + Integer.toHexString(this.mWakeLockSummary & 32)
              + "  mUserActivitySummary: 0x"
              + Integer.toHexString(userActivitySummaryLocked));
      if ((this.mWakeLockSummary & 32) != 0) {
        Iterator it = this.mWakeLocks.iterator();
        while (it.hasNext()) {
          WakeLock wakeLock = (WakeLock) it.next();
          if (isScreenLock(wakeLock)) {
            Slog.m76i("PowerManagerService", "[PWL]  " + wakeLock);
          }
        }
        return;
      }
      return;
    }
    Slog.m76i(
        "PowerManagerService", "[PWL] Off : " + (timeSinceScreenWasLastOn() / 1000) + "s ago");
    Iterator it2 = this.mSuspendBlockers.iterator();
    while (it2.hasNext()) {
      SuspendBlocker suspendBlocker = (SuspendBlocker) it2.next();
      SuspendBlockerImpl suspendBlockerImpl = (SuspendBlockerImpl) suspendBlocker;
      if (suspendBlockerImpl.isHeld()) {
        Slog.m76i("PowerManagerService", "[PWL]   " + suspendBlocker);
        if (suspendBlockerImpl.equals(this.mWakeLockSuspendBlocker)) {
          if ((this.mWakeLockSummary & (-17)) != 0) {
            Slog.m76i(
                "PowerManagerService",
                "[PWL]     mWakeLockSummary : 0x" + Integer.toHexString(this.mWakeLockSummary));
            if ((this.mWakeLockSummary & 1) != 0) {
              Iterator it3 = this.mWakeLocks.iterator();
              while (it3.hasNext()) {
                WakeLock wakeLock2 = (WakeLock) it3.next();
                if ((wakeLock2.mFlags & 129) != 0) {
                  Slog.m76i("PowerManagerService", "[PWL]       " + wakeLock2);
                }
              }
            }
            if ((this.mWakeLockSummary & (-2)) != 0) {
              Iterator it4 = this.mWakeLocks.iterator();
              while (it4.hasNext()) {
                WakeLock wakeLock3 = (WakeLock) it4.next();
                if (isScreenLock(wakeLock3)) {
                  Slog.m74e("PowerManagerService", "[PWL]       " + wakeLock3);
                }
              }
            }
          }
        } else if (suspendBlockerImpl.equals(this.mDisplaySuspendBlocker)) {
          if (userActivitySummaryLocked != 0) {
            Slog.m76i(
                "PowerManagerService",
                "[PWL]     userActivitySummary : 0x"
                    + Integer.toHexString(userActivitySummaryLocked));
          }
          if (displayPowerRequestLocked.policy != 0) {
            Slog.m76i(
                "PowerManagerService",
                "[PWL]     displayPowerRequest.policy : " + displayPowerRequestLocked.policy);
          }
          if (!isReadyLocked) {
            Slog.m76i("PowerManagerService", "[PWL]     displayReady : " + isReadyLocked);
          }
          if (!this.mBootCompleted) {
            Slog.m76i("PowerManagerService", "[PWL]     mBootCompleted : " + this.mBootCompleted);
          }
        }
      }
    }
  }

  public long timeSinceScreenWasLastOn() {
    synchronized (this.mLock) {
      if (PowerManagerInternal.isInteractive(
          ((PowerGroup) this.mPowerGroups.get(0)).getWakefulnessLocked())) {
        return 0L;
      }
      return SystemClock.elapsedRealtime() - this.mLastScreenOffEventElapsedRealTime;
    }
  }

  public final class WakeLock implements IBinder.DeathRecipient {
    public long mAcquireTime;
    public IWakeLockCallback mCallback;
    public int mConsecutiveAcquireCount;
    public int mDisableReason;
    public boolean mDisabled;
    public boolean mDisabledByFreecess;
    public final int mDisplayId;
    public int mFlags;
    public String mHistoryTag;
    public final IBinder mLock;
    public boolean mNotifiedAcquired;
    public boolean mNotifiedLong;
    public final int mOwnerPid;
    public final int mOwnerUid;
    public final String mPackageName;
    public int mProximityNegativeDebounce;
    public int mProximityPositiveDebounce;
    public String mTag;
    public final UidState mUidState;
    public WorkSource mWorkSource;

    public WakeLock(
        IBinder iBinder,
        int i,
        int i2,
        String str,
        String str2,
        WorkSource workSource,
        String str3,
        int i3,
        int i4,
        UidState uidState,
        IWakeLockCallback iWakeLockCallback) {
      this.mLock = iBinder;
      this.mDisplayId = i;
      this.mFlags = i2;
      this.mTag = str;
      this.mPackageName = str2;
      this.mWorkSource = PowerManagerService.copyWorkSource(workSource);
      this.mHistoryTag = str3;
      this.mOwnerUid = i3;
      this.mOwnerPid = i4;
      this.mUidState = uidState;
      this.mCallback = iWakeLockCallback;
      linkToDeath();
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      unlinkToDeath();
      PowerManagerService.this.handleWakeLockDeath(this);
    }

    public final void linkToDeath() {
      try {
        this.mLock.linkToDeath(this, 0);
      } catch (RemoteException unused) {
        throw new IllegalArgumentException("Wakelock.mLock is already dead.");
      }
    }

    public void unlinkToDeath() {
      try {
        this.mLock.unlinkToDeath(this, 0);
      } catch (NoSuchElementException e) {
        Slog.wtf("PowerManagerService", "Failed to unlink Wakelock.mLock", e);
      }
    }

    public boolean setDisabled(boolean z, int i) {
      if (this.mDisabled == z) {
        return false;
      }
      this.mDisabled = z;
      this.mDisableReason = i;
      return true;
    }

    public boolean hasSameProperties(
        int i,
        String str,
        WorkSource workSource,
        int i2,
        int i3,
        IWakeLockCallback iWakeLockCallback) {
      return this.mFlags == i
          && this.mTag.equals(str)
          && hasSameWorkSource(workSource)
          && this.mOwnerUid == i2
          && this.mOwnerPid == i3;
    }

    public void updateProperties(
        int i,
        String str,
        String str2,
        WorkSource workSource,
        String str3,
        int i2,
        int i3,
        IWakeLockCallback iWakeLockCallback) {
      if (!this.mPackageName.equals(str2)) {
        throw new IllegalStateException(
            "Existing wake lock package name changed: " + this.mPackageName + " to " + str2);
      }
      if (this.mOwnerUid != i2) {
        throw new IllegalStateException(
            "Existing wake lock uid changed: " + this.mOwnerUid + " to " + i2);
      }
      if (this.mOwnerPid != i3) {
        throw new IllegalStateException(
            "Existing wake lock pid changed: " + this.mOwnerPid + " to " + i3);
      }
      this.mFlags = i;
      this.mTag = str;
      updateWorkSource(workSource);
      this.mHistoryTag = str3;
      this.mCallback = iWakeLockCallback;
    }

    public boolean hasSameWorkSource(WorkSource workSource) {
      return Objects.equals(this.mWorkSource, workSource);
    }

    public void updateWorkSource(WorkSource workSource) {
      this.mWorkSource = PowerManagerService.copyWorkSource(workSource);
    }

    public Integer getPowerGroupId() {
      if (!PowerManagerService.this.mSystemReady || this.mDisplayId == -1) {
        return -1;
      }
      DisplayInfo displayInfo =
          PowerManagerService.this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
      if (displayInfo != null) {
        return Integer.valueOf(displayInfo.displayGroupId);
      }
      return null;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(getLockLevelString());
      sb.append(" '");
      sb.append(this.mTag);
      sb.append("'");
      sb.append(getLockFlagsString());
      if (this.mDisabled) {
        sb.append(" DISABLED");
      }
      if (this.mNotifiedAcquired) {
        sb.append(" ACQ=");
        TimeUtils.formatDuration(
            this.mAcquireTime - PowerManagerService.this.mClock.uptimeMillis(), sb);
      }
      if (this.mNotifiedLong) {
        sb.append(" LONG");
      }
      sb.append(" (uid=");
      sb.append(this.mOwnerUid);
      if (this.mOwnerPid != 0) {
        sb.append(" pid=");
        sb.append(this.mOwnerPid);
      }
      if (this.mWorkSource != null) {
        sb.append(" ws=");
        sb.append(this.mWorkSource);
      }
      sb.append(" displayId=");
      sb.append(this.mDisplayId);
      sb.append(" lock=");
      sb.append(Integer.toHexString(System.identityHashCode(this.mLock)));
      if (this.mConsecutiveAcquireCount > 0) {
        sb.append(" acqCnt=");
        sb.append(this.mConsecutiveAcquireCount);
      }
      sb.append(")");
      return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
      long start = protoOutputStream.start(j);
      protoOutputStream.write(1159641169921L, this.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL);
      protoOutputStream.write(1138166333442L, this.mTag);
      long start2 = protoOutputStream.start(1146756268035L);
      protoOutputStream.write(1133871366145L, (this.mFlags & 268435456) != 0);
      protoOutputStream.write(1133871366146L, (this.mFlags & 536870912) != 0);
      protoOutputStream.write(1133871366147L, (this.mFlags & Integer.MIN_VALUE) != 0);
      protoOutputStream.end(start2);
      protoOutputStream.write(1133871366148L, this.mDisabled);
      if (this.mNotifiedAcquired) {
        protoOutputStream.write(1112396529669L, this.mAcquireTime);
      }
      protoOutputStream.write(1133871366150L, this.mNotifiedLong);
      protoOutputStream.write(1120986464263L, this.mOwnerUid);
      protoOutputStream.write(1120986464264L, this.mOwnerPid);
      WorkSource workSource = this.mWorkSource;
      if (workSource != null) {
        workSource.dumpDebug(protoOutputStream, 1146756268041L);
      }
      protoOutputStream.end(start);
    }

    public final String getLockLevelString() {
      int i = this.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
      return i != 1
          ? i != 6
              ? i != 10
                  ? i != 26
                      ? i != 32
                          ? i != 64
                              ? i != 128
                                  ? "???                           "
                                  : "DRAW_WAKE_LOCK                "
                              : "DOZE_WAKE_LOCK                "
                          : "PROXIMITY_SCREEN_OFF_WAKE_LOCK"
                      : "FULL_WAKE_LOCK                "
                  : "SCREEN_BRIGHT_WAKE_LOCK       "
              : "SCREEN_DIM_WAKE_LOCK          "
          : "PARTIAL_WAKE_LOCK             ";
    }

    public final String getLockFlagsString() {
      String str = "";
      if ((this.mFlags & 268435456) != 0) {
        str = " ACQUIRE_CAUSES_WAKEUP";
      }
      if ((this.mFlags & 536870912) != 0) {
        str = str + " ON_AFTER_RELEASE";
      }
      if ((this.mFlags & Integer.MIN_VALUE) == 0) {
        return str;
      }
      return str + " SYSTEM_WAKELOCK";
    }

    public final String getDisableReasonString() {
      StringBuilder sb = new StringBuilder();
      if ((this.mDisableReason & 1) != 0) {
        sb.append(" nocached");
      }
      if ((this.mDisableReason & 2) != 0) {
        sb.append(" idlemode");
      }
      if ((this.mDisableReason & 4) != 0) {
        sb.append(" lowpwrst");
      }
      if ((this.mDisableReason & 8) != 0) {
        sb.append(" freecess");
      }
      if ((this.mDisableReason & 16) != 0) {
        sb.append(" gmsalarm");
      }
      return sb.toString();
    }
  }

  public final class SuspendBlockerImpl implements SuspendBlocker {
    public final boolean mIsShowLog;
    public final String mName;
    public final int mNameHash;
    public final ArrayMap mOpenReferenceTimes = new ArrayMap();
    public int mReferenceCount;

    public SuspendBlockerImpl(String str) {
      this.mName = str;
      this.mIsShowLog =
          str.equals("PowerManagerService.Broadcasts") || str.equals("PowerManagerService.Display");
      this.mNameHash = str.hashCode();
    }

    public void finalize() {
      try {
        if (this.mReferenceCount != 0) {
          Slog.wtf(
              "PowerManagerService",
              "Suspend blocker \"" + this.mName + "\" was finalized without being released!");
          this.mReferenceCount = 0;
          PowerManagerService.this.mNativeWrapper.nativeReleaseSuspendBlocker(this.mName);
          Trace.asyncTraceForTrackEnd(131072L, "SuspendBlockers", this.mNameHash);
        }
      } finally {
        super.finalize();
      }
    }

    @Override // com.android.server.power.SuspendBlocker
    public void acquire() {
      acquire("unknown");
    }

    @Override // com.android.server.power.SuspendBlocker
    public void acquire(String str) {
      synchronized (this) {
        recordReferenceLocked(str);
        int i = this.mReferenceCount + 1;
        this.mReferenceCount = i;
        if (i == 1) {
          if (this.mIsShowLog) {
            Slog.m72d("PowerManagerService", "[PWL] sb acquire: " + this.mName);
          }
          Trace.asyncTraceForTrackBegin(131072L, "SuspendBlockers", this.mName, this.mNameHash);
          PowerManagerService.this.mNativeWrapper.nativeAcquireSuspendBlocker(this.mName);
        }
      }
    }

    @Override // com.android.server.power.SuspendBlocker
    public void release() {
      release("unknown");
    }

    @Override // com.android.server.power.SuspendBlocker
    public void release(String str) {
      synchronized (this) {
        removeReferenceLocked(str);
        int i = this.mReferenceCount - 1;
        this.mReferenceCount = i;
        if (i == 0) {
          if (this.mIsShowLog) {
            Slog.m72d("PowerManagerService", "[PWL] sb release: " + this.mName);
          }
          PowerManagerService.this.mNativeWrapper.nativeReleaseSuspendBlocker(this.mName);
          if (Trace.isTagEnabled(131072L)) {
            Trace.asyncTraceForTrackEnd(131072L, "SuspendBlockers", this.mNameHash);
          }
        } else if (i < 0) {
          Slog.wtf(
              "PowerManagerService",
              "Suspend blocker \"" + this.mName + "\" was released without being acquired!",
              new Throwable());
          this.mReferenceCount = 0;
        }
      }
    }

    public String toString() {
      String sb;
      synchronized (this) {
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mName);
        sb2.append(": ref count=");
        sb2.append(this.mReferenceCount);
        sb2.append(" [");
        int size = this.mOpenReferenceTimes.size();
        for (int i = 0; i < size; i++) {
          String str = (String) this.mOpenReferenceTimes.keyAt(i);
          LongArray longArray = (LongArray) this.mOpenReferenceTimes.valueAt(i);
          if (longArray != null && longArray.size() != 0) {
            if (i > 0) {
              sb2.append(", ");
            }
            sb2.append(str);
            sb2.append(": (");
            for (int i2 = 0; i2 < longArray.size(); i2++) {
              if (i2 > 0) {
                sb2.append(", ");
              }
              sb2.append(PowerManagerService.DATE_FORMAT.format(new Date(longArray.get(i2))));
            }
            sb2.append(")");
          }
        }
        sb2.append("]");
        sb = sb2.toString();
      }
      return sb;
    }

    public boolean isHeld() {
      boolean z;
      synchronized (this) {
        z = this.mReferenceCount != 0;
      }
      return z;
    }

    @Override // com.android.server.power.SuspendBlocker
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
      long start = protoOutputStream.start(j);
      synchronized (this) {
        protoOutputStream.write(1138166333441L, this.mName);
        protoOutputStream.write(1120986464258L, this.mReferenceCount);
      }
      protoOutputStream.end(start);
    }

    public final void recordReferenceLocked(String str) {
      LongArray longArray = (LongArray) this.mOpenReferenceTimes.get(str);
      if (longArray == null) {
        longArray = new LongArray();
        this.mOpenReferenceTimes.put(str, longArray);
      }
      longArray.add(System.currentTimeMillis());
    }

    public final void removeReferenceLocked(String str) {
      LongArray longArray = (LongArray) this.mOpenReferenceTimes.get(str);
      if (longArray == null || longArray.size() <= 0) {
        return;
      }
      longArray.remove(longArray.size() - 1);
    }
  }

  public final class UidState {
    public boolean mActive;
    public int mNumWakeLocks;
    public int mProcState;
    public final int mUid;

    public UidState(int i) {
      this.mUid = i;
    }
  }

  final class BinderService extends IPowerManager.Stub {
    public final PowerManagerShellCommand mShellCommand;

    public void systemReboot(String str) {}

    public BinderService(Context context) {
      this.mShellCommand = new PowerManagerShellCommand(context, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(
        FileDescriptor fileDescriptor,
        FileDescriptor fileDescriptor2,
        FileDescriptor fileDescriptor3,
        String[] strArr,
        ShellCallback shellCallback,
        ResultReceiver resultReceiver) {
      this.mShellCommand.exec(
          this,
          fileDescriptor,
          fileDescriptor2,
          fileDescriptor3,
          strArr,
          shellCallback,
          resultReceiver);
    }

    public void acquireWakeLockWithUid(
        IBinder iBinder,
        int i,
        String str,
        String str2,
        int i2,
        int i3,
        IWakeLockCallback iWakeLockCallback) {
      acquireWakeLock(
          iBinder,
          i,
          str,
          str2,
          new WorkSource(i2 < 0 ? Binder.getCallingUid() : i2),
          null,
          i3,
          iWakeLockCallback);
    }

    public void setPowerBoost(int i, int i2) {
      if (PowerManagerService.this.mSystemReady) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", null);
        PowerManagerService.this.setPowerBoostInternal(i, i2);
      }
    }

    public void setPowerMode(int i, boolean z) {
      if (PowerManagerService.this.mSystemReady) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", null);
        PowerManagerService.this.setPowerModeInternal(i, z);
      }
    }

    public boolean setPowerModeChecked(int i, boolean z) {
      if (!PowerManagerService.this.mSystemReady) {
        return false;
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      return PowerManagerService.this.setPowerModeInternal(i, z);
    }

    public void acquireWakeLock(
        IBinder iBinder,
        int i,
        String str,
        String str2,
        WorkSource workSource,
        String str3,
        int i2,
        IWakeLockCallback iWakeLockCallback) {
      WorkSource workSource2;
      int i3;
      int i4;
      WorkSource workSource3;
      boolean z;
      long clearCallingIdentity;
      String str4;
      int i5;
      if (iBinder == null) {
        throw new IllegalArgumentException("lock must not be null");
      }
      if (str2 == null) {
        throw new IllegalArgumentException("packageName must not be null");
      }
      PowerManager.validateWakeLockParameters(i, str);
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WAKE_LOCK", null);
      if ((i & 64) != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", null);
      }
      if (workSource == null || workSource.isEmpty()) {
        workSource2 = null;
      } else {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.UPDATE_DEVICE_STATS", null);
        workSource2 = workSource;
      }
      int callingUid = Binder.getCallingUid();
      int callingPid = Binder.getCallingPid();
      if ((Integer.MIN_VALUE & i) != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", null);
        WorkSource workSource4 = new WorkSource(Binder.getCallingUid(), str2);
        if (workSource2 != null && !workSource2.isEmpty()) {
          workSource4.add(workSource2);
        }
        i3 = Process.myUid();
        workSource3 = workSource4;
        i4 = Process.myPid();
      } else {
        i3 = callingUid;
        i4 = callingPid;
        workSource3 = workSource2;
      }
      try {
        if ((268435456 & i) != 0 && PowerManagerService.isScreenLock(i)) {
          if (workSource3 == null || workSource3.isEmpty()) {
            str4 = str2;
            i5 = i3;
          } else {
            WorkSource.WorkChain firstNonEmptyWorkChain =
                PowerManagerService.getFirstNonEmptyWorkChain(workSource3);
            if (firstNonEmptyWorkChain != null) {
              str4 = firstNonEmptyWorkChain.getAttributionTag();
              i5 = firstNonEmptyWorkChain.getAttributionUid();
            } else {
              str4 = workSource3.getName(0) != null ? workSource3.getName(0) : str2;
              i5 = workSource3.get(0);
            }
          }
          INotificationManager notificationManager =
              PowerManagerService.this.mInjector.getNotificationManager();
          if (notificationManager != null) {
            try {
              z = notificationManager.isAlertsAllowed(str4, i5, str, i);
            } catch (RemoteException e) {
              Slog.m74e("PowerManagerService", "Failed to call NotificationManager : " + e);
            }
            boolean z2 = z;
            clearCallingIdentity = Binder.clearCallingIdentity();
            PowerManagerService.this.acquireWakeLockInternal(
                iBinder, i2, i, str, str2, workSource3, str3, i3, i4, iWakeLockCallback, z2);
            return;
          }
        }
        PowerManagerService.this.acquireWakeLockInternal(
            iBinder, i2, i, str, str2, workSource3, str3, i3, i4, iWakeLockCallback, z2);
        return;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
      z = true;
      boolean z22 = z;
      clearCallingIdentity = Binder.clearCallingIdentity();
    }

    public void acquireWakeLockAsync(
        IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) {
      acquireWakeLock(iBinder, i, str, str2, workSource, str3, -1, null);
    }

    public void releaseWakeLock(IBinder iBinder, int i) {
      if (iBinder == null) {
        throw new IllegalArgumentException("lock must not be null");
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WAKE_LOCK", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.releaseWakeLockInternal(iBinder, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void releaseWakeLockAsync(IBinder iBinder, int i) {
      releaseWakeLock(iBinder, i);
    }

    public void updateWakeLockUids(IBinder iBinder, int[] iArr) {
      WorkSource workSource;
      if (iArr != null) {
        workSource = new WorkSource();
        for (int i : iArr) {
          workSource.add(i);
        }
      } else {
        workSource = null;
      }
      updateWakeLockWorkSource(iBinder, workSource, null);
    }

    public void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) {
      updateWakeLockUids(iBinder, iArr);
    }

    public void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) {
      if (iBinder == null) {
        throw new IllegalArgumentException("lock must not be null");
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WAKE_LOCK", null);
      if (workSource == null || workSource.isEmpty()) {
        workSource = null;
      } else {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.UPDATE_DEVICE_STATS", null);
      }
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.updateWakeLockWorkSourceInternal(
            iBinder, workSource, str, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback) {
      if (iBinder == null) {
        throw new IllegalArgumentException("lock must not be null");
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WAKE_LOCK", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.updateWakeLockCallbackInternal(
            iBinder, iWakeLockCallback, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isWakeLockLevelSupported(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isWakeLockLevelSupportedInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void userActivity(int i, long j, int i2, int i3) {
      if (PowerManagerService.this.mIsUserActivityInvoked.compareAndSet(false, true)) {
        StringBuilder sb = new StringBuilder();
        sb.append("[api] userActivity : ");
        sb.append(PowerManager.userActivityEventToString(i2));
        sb.append(i3 == 0 ? "" : ",");
        sb.append(PowerManager.userActivityFlagsToString(i3));
        sb.append(PowerManagerUtil.callerInfoToString(true));
        sb.append(" displayId=");
        sb.append(i);
        sb.append(" eventTime=");
        sb.append(j);
        Slog.m72d("PowerManagerService", sb.toString());
        PowerManagerService.this.mHandlerPmsMisc.postAtTime(
            new Runnable() { // from class:
                             // com.android.server.power.PowerManagerService$BinderService$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                PowerManagerService.BinderService.this.lambda$userActivity$0();
              }
            },
            PowerManagerService.this.mClock.uptimeMillis() + 1000);
      }
      long uptimeMillis = PowerManagerService.this.mClock.uptimeMillis();
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
                  "android.permission.DEVICE_POWER")
              != 0
          && PowerManagerService.this.mContext.checkCallingOrSelfPermission(
                  "android.permission.USER_ACTIVITY")
              != 0) {
        synchronized (PowerManagerService.this.mLock) {
          if (uptimeMillis
              >= PowerManagerService.this.mLastWarningAboutUserActivityPermission
                  + BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS) {
            PowerManagerService.this.mLastWarningAboutUserActivityPermission = uptimeMillis;
            Slog.m79w(
                "PowerManagerService",
                "Ignoring call to PowerManager.userActivity() because the caller does not have"
                    + " DEVICE_POWER or USER_ACTIVITY permission.  Please fix your app!   pid="
                    + Binder.getCallingPid()
                    + " uid="
                    + Binder.getCallingUid());
          }
        }
        return;
      }
      if (j > uptimeMillis) {
        Slog.wtf(
            "PowerManagerService",
            "Event cannot be newer than the current time (now="
                + uptimeMillis
                + ", eventTime="
                + j
                + ", displayId="
                + i
                + ", event="
                + PowerManager.userActivityEventToString(i2)
                + ", flags="
                + i3
                + ")");
        return;
      }
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.userActivityInternal(i, j, i2, i3, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$userActivity$0() {
      PowerManagerService.this.mIsUserActivityInvoked.set(false);
    }

    public void wakeUp(long j, int i, String str, String str2) {
      long uptimeMillis = PowerManagerService.this.mClock.uptimeMillis();
      if (j > uptimeMillis) {
        Slog.m74e(
            "PowerManagerService", "Event time " + j + " cannot be newer than " + uptimeMillis);
        throw new IllegalArgumentException("event time must not be in the future");
      }
      Slog.m72d(
          "PowerManagerService",
          "[api] wakeUp" + PowerManagerUtil.callerInfoToString() + " eventTime = " + j);
      PowerManagerService powerManagerService = PowerManagerService.this;
      StringBuilder sb = new StringBuilder();
      sb.append(" wakeUp: ");
      sb.append(
          i == 0
              ? PowerManagerUtil.callerInfoToString(true)
              : PowerManagerUtil.callerInfoToString(false));
      powerManagerService.mScreenOnReason = sb.toString();
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (PowerManagerService.this.mLock) {
          if (!PowerManagerService.this.mBootCompleted && PowerManagerService.sQuiescent) {
            PowerManagerService.this.mDirty |= IInstalld.FLAG_USE_QUOTA;
            PowerManagerService.this.updatePowerStateLocked();
          } else {
            PowerManagerService powerManagerService2 = PowerManagerService.this;
            powerManagerService2.wakePowerGroupLocked(
                (PowerGroup) powerManagerService2.mPowerGroups.get(0),
                j,
                i,
                str,
                callingUid,
                str2,
                callingUid);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void goToSleep(long j, int i, int i2) {
      Slog.m72d(
          "PowerManagerService",
          "[api] goToSleep" + PowerManagerUtil.callerInfoToString() + " eventTime = " + j);
      PowerManagerService.this.goToSleepInternal(
          PowerManagerService.DEFAULT_DISPLAY_GROUP_IDS, j, i, i2);
    }

    public void goToSleepWithDisplayId(int i, long j, int i2, int i3) {
      IntArray wrap;
      Slog.m72d(
          "PowerManagerService",
          "[api] goToSleepWithDisplayId"
              + PowerManagerUtil.callerInfoToString()
              + " for displayId = "
              + i
              + " eventTime = "
              + j);
      if (i == -1) {
        wrap = PowerManagerService.this.mDisplayManagerInternal.getDisplayGroupIds();
      } else {
        DisplayInfo displayInfo =
            PowerManagerService.this.mDisplayManagerInternal.getDisplayInfo(i);
        Preconditions.checkArgument(
            displayInfo != null, "display ID(%d) doesn't exist", new Object[] {Integer.valueOf(i)});
        int i4 = displayInfo.displayGroupId;
        if (i4 == -1) {
          throw new IllegalArgumentException("invalid display group ID");
        }
        wrap = IntArray.wrap(new int[] {i4});
      }
      PowerManagerService.this.goToSleepInternal(wrap, j, i2, i3);
    }

    public void nap(long j) {
      long uptimeMillis = PowerManagerService.this.mClock.uptimeMillis();
      if (j > uptimeMillis) {
        Slog.m74e(
            "PowerManagerService", "Event time " + j + " cannot be newer than " + uptimeMillis);
        throw new IllegalArgumentException("event time must not be in the future");
      }
      Slog.m72d(
          "PowerManagerService",
          "[api] nap" + PowerManagerUtil.callerInfoToString(true) + " eventTime = " + j);
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.napInternal(j, callingUid, false);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public float getBrightnessConstraint(int i) {
      if (i == 0) {
        return PowerManagerService.this.mScreenBrightnessMinimum;
      }
      if (i == 1) {
        return PowerManagerService.this.mScreenBrightnessMaximum;
      }
      if (i == 2) {
        return PowerManagerService.this.mScreenBrightnessDefault;
      }
      if (i == 3) {
        return PowerManagerService.this.mScreenBrightnessDim;
      }
      if (i != 4) {
        return Float.NaN;
      }
      return PowerManagerService.this.mScreenBrightnessDoze;
    }

    public boolean isInteractive() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isGloballyInteractiveInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isDisplayInteractive(int i) {
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isInteractiveInternal(i, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isInteractiveForDisplay(int i) {
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isInteractiveForDisplayInternal(i, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean areAutoPowerSaveModesEnabled() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this
            .mContext
            .getResources()
            .getBoolean(R.bool.config_dozeAlwaysOnDisplayAvailable);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isPowerSaveMode() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mBatterySaverController.isEnabled();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public PowerSaveState getPowerSaveState(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mBatterySaverPolicy.getBatterySaverPolicy(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean setPowerSaveModeEnabled(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setPowerSaveModeEnabled: " + z + PowerManagerUtil.callerInfoToString(true));
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.POWER_SAVER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", null);
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.setLowPowerModeInternal(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public BatterySaverPolicyConfig getFullPowerSavePolicy() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mBatterySaverStateMachine.getFullBatterySaverPolicy();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.POWER_SAVER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", "setFullPowerSavePolicy");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mBatterySaverStateMachine.setFullBatterySaverPolicy(
            batterySaverPolicyConfig);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean setDynamicPowerSaveHint(boolean z, int i) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.POWER_SAVER", "updateDynamicPowerSavings");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        ContentResolver contentResolver = PowerManagerService.this.mContext.getContentResolver();
        boolean putInt =
            Settings.Global.putInt(contentResolver, "dynamic_power_savings_disable_threshold", i);
        if (putInt) {
          putInt &=
              Settings.Global.putInt(contentResolver, "dynamic_power_savings_enabled", z ? 1 : 0);
        }
        return putInt;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.POWER_SAVER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", "setAdaptivePowerSavePolicy");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mBatterySaverStateMachine.setAdaptiveBatterySaverPolicy(
            batterySaverPolicyConfig);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean setAdaptivePowerSaveEnabled(boolean z) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.POWER_SAVER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", "setAdaptivePowerSaveEnabled");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mBatterySaverStateMachine.setAdaptiveBatterySaverEnabled(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getPowerSaveModeTrigger() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return Settings.Global.getInt(
            PowerManagerService.this.mContext.getContentResolver(), "automatic_power_save_mode", 0);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z) {
      long elapsedRealtime = PowerManagerService.this.mClock.elapsedRealtime();
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.BATTERY_PREDICTION")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.DEVICE_POWER", "setBatteryDischargePrediction");
      }
      long millis = parcelDuration.getDuration().toMillis();
      Preconditions.checkArgumentPositive(
          millis, "Given time remaining is not positive: " + millis);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (PowerManagerService.this.mLock) {
          if (PowerManagerService.this.mIsPowered) {
            throw new IllegalStateException(
                "Discharge prediction can't be set while the device is charging");
          }
        }
        synchronized (PowerManagerService.this.mEnhancedDischargeTimeLock) {
          if (PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed > elapsedRealtime) {
            return;
          }
          long max =
              Math.max(
                  0L,
                  60000
                      - (elapsedRealtime
                          - PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed));
          PowerManagerService.this.mEnhancedDischargeTimeElapsed = millis + elapsedRealtime;
          PowerManagerService.this.mEnhancedDischargePredictionIsPersonalized = z;
          PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed = elapsedRealtime;
          PowerManagerService.this.mNotifier.postEnhancedDischargePredictionBroadcast(max);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public final boolean isEnhancedDischargePredictionValidLocked(long j) {
      return PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed > 0
          && j < PowerManagerService.this.mEnhancedDischargeTimeElapsed
          && j - PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed < 1800000;
    }

    public ParcelDuration getBatteryDischargePrediction() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (PowerManagerService.this.mLock) {
          if (PowerManagerService.this.mIsPowered) {
            return null;
          }
          synchronized (PowerManagerService.this.mEnhancedDischargeTimeLock) {
            long elapsedRealtime = PowerManagerService.this.mClock.elapsedRealtime();
            if (!isEnhancedDischargePredictionValidLocked(elapsedRealtime)) {
              return new ParcelDuration(
                  PowerManagerService.this.mBatteryStats.computeBatteryTimeRemaining());
            }
            return new ParcelDuration(
                PowerManagerService.this.mEnhancedDischargeTimeElapsed - elapsedRealtime);
          }
        }
      } catch (RemoteException unused) {
        return null;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isBatteryDischargePredictionPersonalized() {
      boolean z;
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (PowerManagerService.this.mEnhancedDischargeTimeLock) {
          z =
              isEnhancedDischargePredictionValidLocked(
                      PowerManagerService.this.mClock.elapsedRealtime())
                  && PowerManagerService.this.mEnhancedDischargePredictionIsPersonalized;
        }
        return z;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isDeviceIdleMode() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isDeviceIdleModeInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isLightDeviceIdleMode() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isLightDeviceIdleModeInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isLowPowerStandbySupported() {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY", "isLowPowerStandbySupported");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mLowPowerStandbyController.isSupported();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isLowPowerStandbyEnabled() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mLowPowerStandbyController.isEnabled();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setLowPowerStandbyEnabled(boolean z) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyEnabled");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mLowPowerStandbyController.setEnabled(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setLowPowerStandbyActiveDuringMaintenance(boolean z) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY",
            "setLowPowerStandbyActiveDuringMaintenance");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mLowPowerStandbyController.setActiveDuringMaintenance(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void forceLowPowerStandbyActive(boolean z) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY", "forceLowPowerStandbyActive");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mLowPowerStandbyController.forceActive(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setLowPowerStandbyPolicy(
        IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyPolicy");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mLowPowerStandbyController.setPolicy(
            PowerManager.LowPowerStandbyPolicy.fromParcelable(lowPowerStandbyPolicy));
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public IPowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY", "getLowPowerStandbyPolicy");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManager.LowPowerStandbyPolicy.toParcelable(
            PowerManagerService.this.mLowPowerStandbyController.getPolicy());
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isExemptFromLowPowerStandby() {
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mLowPowerStandbyController.isPackageExempt(callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isReasonAllowedInLowPowerStandby(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mLowPowerStandbyController.isAllowed(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isFeatureAllowedInLowPowerStandby(String str) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mLowPowerStandbyController.isAllowed(str);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void acquireLowPowerStandbyPorts(IBinder iBinder, List list) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.SET_LOW_POWER_STANDBY_PORTS", "acquireLowPowerStandbyPorts");
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mLowPowerStandbyController.acquireStandbyPorts(
            iBinder, callingUid, PowerManager.LowPowerStandbyPortDescription.fromParcelable(list));
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void releaseLowPowerStandbyPorts(IBinder iBinder) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.SET_LOW_POWER_STANDBY_PORTS", "releaseLowPowerStandbyPorts");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mLowPowerStandbyController.releaseStandbyPorts(iBinder);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public List getActiveLowPowerStandbyPorts() {
      if (PowerManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.DEVICE_POWER")
          != 0) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MANAGE_LOW_POWER_STANDBY", "getActiveLowPowerStandbyPorts");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManager.LowPowerStandbyPortDescription.toParcelable(
            PowerManagerService.this.mLowPowerStandbyController.getActiveStandbyPorts());
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getLastShutdownReason() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.getLastShutdownReasonInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getLastSleepReason() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.getLastSleepReasonInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void reboot(boolean z, String str, boolean z2) {
      PowerManagerService.this.mShutdownOrRebootCaller =
          PowerManagerService.callerInfoWithProcessName();
      StringBuilder sb = new StringBuilder();
      sb.append("[api] ");
      sb.append(
          String.format(" reason: %s", str)
              + String.format(" confirm: %b", Boolean.valueOf(z))
              + String.format(" wait: %b", Boolean.valueOf(z2))
              + PowerManagerService.this.mShutdownOrRebootCaller);
      Slog.m73dk("PowerManagerService", sb.toString());
      if (str == null && Binder.getCallingPid() == Process.myPid()) {
        ShutdownThread.UpdatePoweroffResetReason(new Exception("System server call reboot"));
      }
      if (str == null) {
        str = PowerManagerService.this.mShutdownOrRebootCaller;
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.REBOOT", null);
      if ("recovery".equals(str) || "recovery-update".equals(str)) {
        PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.RECOVERY", null);
      }
      ShutdownCheckPoints.recordCheckPoint(Binder.getCallingPid(), str);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.shutdownOrRebootInternal(1, z, str, z2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void rebootSafeMode(boolean z, boolean z2) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.REBOOT", null);
      ShutdownCheckPoints.recordCheckPoint(Binder.getCallingPid(), "safemode");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.shutdownOrRebootInternal(2, z, "safemode", z2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void shutdown(boolean z, String str, boolean z2) {
      PowerManagerService.this.mShutdownOrRebootCaller =
          PowerManagerService.callerInfoWithProcessName();
      StringBuilder sb = new StringBuilder();
      sb.append("[api] ");
      sb.append(
          " shutdown"
              + String.format(" reason: %s", str)
              + String.format(" confirm: %b", Boolean.valueOf(z))
              + String.format(" wait: %b", Boolean.valueOf(z2))
              + PowerManagerService.this.mShutdownOrRebootCaller);
      Slog.m73dk("PowerManagerService", sb.toString());
      if (str == null && Binder.getCallingPid() == Process.myPid()) {
        ShutdownThread.UpdatePoweroffResetReason(new Exception("System server call shutdown"));
      }
      if (str == null) {
        str = PowerManagerService.this.mShutdownOrRebootCaller;
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.REBOOT", null);
      ShutdownCheckPoints.recordCheckPoint(Binder.getCallingPid(), str);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.shutdownOrRebootInternal(0, z, str, z2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void crash(String str) {
      Slog.m72d(
          "PowerManagerService", "[api] crash: " + str + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.REBOOT", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.crashInternal(str);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setStayOnSetting(int i) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setStayOnSetting: " + i + PowerManagerUtil.callerInfoToString(true));
      int callingUid = Binder.getCallingUid();
      if (callingUid != 0
          && !Settings.checkAndNoteWriteSettingsOperation(
              PowerManagerService.this.mContext,
              callingUid,
              Settings.getPackageNameForUid(PowerManagerService.this.mContext, callingUid),
              null,
              true)) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setStayOnSetting: checkAndNoteWriteSettingsOperation() false");
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.setStayOnSettingInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setAttentionLight(boolean z, int i) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setAttentionLight: on: "
              + z
              + " color: "
              + i
              + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.setAttentionLightInternal(z, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setDozeAfterScreenOff(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setDozeAfterScreenOff: " + z + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.setDozeAfterScreenOffInternal(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isAmbientDisplayAvailable() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.READ_DREAM_STATE", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mAmbientDisplayConfiguration.ambientDisplayAvailable();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void suppressAmbientDisplay(String str, boolean z) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WRITE_DREAM_STATE", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.mAmbientDisplaySuppressionController.suppress(str, callingUid, z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isAmbientDisplaySuppressedForToken(String str) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.READ_DREAM_STATE", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed(
            str, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) {
      boolean z;
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.READ_DREAM_STATE", null);
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.READ_DREAM_SUPPRESSION", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (isAmbientDisplayAvailable()) {
          if (PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed(str, i)) {
            z = true;
            return z;
          }
        }
        z = false;
        return z;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isAmbientDisplaySuppressed() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.READ_DREAM_STATE", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void boostScreenBrightness(long j) {
      long uptimeMillis = PowerManagerService.this.mClock.uptimeMillis();
      Slog.m72d(
          "PowerManagerService",
          "[api] boostScreenBrightness: " + j + PowerManagerUtil.callerInfoToString(true));
      if (j > PowerManagerService.this.mClock.uptimeMillis()) {
        Slog.m74e(
            "PowerManagerService", "Event time " + j + " cannot be newer than " + uptimeMillis);
        throw new IllegalArgumentException("event time must not be in the future");
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.boostScreenBrightnessInternal(j, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isScreenBrightnessBoosted() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.isScreenBrightnessBoostedInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean forceSuspend() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.forceSuspendInternal(callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpPermission(
          PowerManagerService.this.mContext, "PowerManagerService", printWriter)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        boolean z2 = strArr.length == 0;
        for (String str : strArr) {
          if (str.equals("--proto")) {
            z = true;
            break;
          }
          if (!z2 && str.equals("CRITICAL")) {
            z2 = true;
          }
        }
        try {
          if (z) {
            PowerManagerService.this.dumpProto(fileDescriptor);
          } else {
            PowerManagerService.this.dumpInternal(printWriter, z2);
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public List getAmbientDisplaySuppressionTokens() {
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return PowerManagerService.this.mAmbientDisplaySuppressionController.getSuppressionTokens(
            callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void switchForceLcdBacklightOffState() {
      Slog.m72d("PowerManagerService", "[api] switchForceLcdBacklightOffState()");
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      DisplayManagerInternal.DisplayPowerRequest displayPowerRequestLocked =
          ((PowerGroup) PowerManagerService.this.mPowerGroups.get(0))
              .getDisplayPowerRequestLocked();
      synchronized (PowerManagerService.this.mLock) {
        if (displayPowerRequestLocked.forceLcdBacklightOffEnabled) {
          displayPowerRequestLocked.forceLcdBacklightOffEnabled = false;
        } else {
          displayPowerRequestLocked.forceLcdBacklightOffEnabled = true;
        }
        PowerManagerService.this.mDirty |= 32;
        PowerManagerService.this.updatePowerStateLocked();
      }
    }

    public void updateCoverState(boolean z) {
      Slog.m72d("PowerManagerService", "[api] updateCoverState: closed: " + z);
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.mIsCoverClosed = z;
        if (!powerManagerService.mCoverAuthReady) {
          PowerManagerService.this.mCoverAuthReady = true;
          PowerManagerService.this.mHandler.removeCallbacks(
              PowerManagerService.this.mCoverAuthReadyRunnable);
          PowerManagerService.this.mCoverAuthReadyRunnable = null;
        }
        PowerManagerService.this.mDirty |= 32;
        long uptimeMillis = PowerManagerService.this.mClock.uptimeMillis();
        PowerManagerService.this.mlastUpdateCoverStateTime = uptimeMillis;
        PowerManagerService.this.mlastUpdateCoverStateReason =
            "updateCoverState called on closed = " + z;
        PowerManagerService.this.userActivityNoUpdateLocked(uptimeMillis, 0, 0, 1000);
        PowerManagerService.this.updatePowerStateLocked();
      }
    }

    public void setCoverType(int i) {
      Slog.m72d("PowerManagerService", "[api] setCoverType: " + i);
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.mCoverType = i;
        PowerManagerService.this.mlastSetCoverTypeTime = SystemClock.uptimeMillis();
        PowerManagerService.this.mlastUpdateCoverTypeReason = "setCoverType = " + i;
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.mDirty = powerManagerService.mDirty | 32;
        PowerManagerService.this.updatePowerStateLocked();
      }
    }

    public void setAutoBrightnessLimit(int i, int i2, boolean z) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS", null);
      if (i < -1 || i2 < -1 || (i > i2 && i2 >= 0)) {
        Slog.m72d(
            "PowerManagerService",
            "[api] setAutoBrightnessLimit : invalid Limits : "
                + i
                + ", "
                + i2
                + PowerManagerUtil.callerInfoToString(true));
        return;
      }
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.mLastAutoBrightnessLimitTime = SystemClock.uptimeMillis();
        if (PowerManagerService.this.mAutoBrightnessLowerLimit != i
            || PowerManagerService.this.mAutoBrightnessUpperLimit != i2) {
          Slog.m72d(
              "PowerManagerService",
              "[api] setAutoBrightnessLimit : lowerLimit "
                  + i
                  + "   upperLimit : "
                  + i2
                  + "   slowChange : "
                  + z
                  + PowerManagerUtil.callerInfoToString(true));
          PowerManagerService.this.mAutoBrightnessLowerLimit = i;
          PowerManagerService.this.mAutoBrightnessUpperLimit = i2;
          PowerManagerService.this.mAutoBrightnessLimitLastCaller =
              PowerManagerUtil.callerInfoToString();
          PowerManagerService.this.mForceSlowChange = z;
          PowerManagerService powerManagerService = PowerManagerService.this;
          powerManagerService.mDirty = powerManagerService.mDirty | 32;
          PowerManagerService.this.updatePowerStateLocked();
        }
      }
    }

    public void setLCDFlashMode(boolean z, IBinder iBinder) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setLCDFlashMode: enable: " + z + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      PowerManagerService.this.setLCDFlashModeInternal(z, iBinder);
    }

    public void setMasterBrightnessLimit(int i, int i2, int i3) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS", null);
      int max =
          Math.max(
              PowerManagerService.this.mScreenExtendedBrightnessMaximum,
              IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
      if (i != -1 && (i < 0 || i > max)) {
        throw new IllegalArgumentException("setMasterBrightnessLimit : invalid lowerLimit : " + i);
      }
      if (i2 != -1 && (i2 < 0 || i2 > max)) {
        throw new IllegalArgumentException("setMasterBrightnessLimit : invalid upperLimit" + i2);
      }
      if (i != -1 && i2 != -1 && i > i2) {
        throw new IllegalArgumentException(
            "setMasterBrightnessLimit : lowerLimit " + i + " > upperLimit " + i2);
      }
      PowerManagerService.this.setMasterBrightnessLimitInternal(i, i2, i3);
    }

    public void setScreenBrightnessScaleFactor(float f, IBinder iBinder) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setScreenBrightnessScaleFactor: scaleFactor: "
              + f
              + PowerManagerUtil.callerInfoToString());
      if (f != -1.0f && f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        throw new IllegalArgumentException("setScreenBrightnessScaleFactor: scaleFactor:" + f);
      }
      if (iBinder == null) {
        throw new IllegalArgumentException("lock must not be null");
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        if (f != PowerManagerService.this.mScreenBrightnessScaleFactor) {
          PowerManagerService.this.mScreenBrightnessScaleFactor = f;
          PowerManagerService.this.mDirty |= 32;
          PowerManagerService.this.updatePowerStateLocked();
        }
      }
    }

    public void setFreezingScreenBrightness(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setFreezingScreenBrightness: freezing: "
              + z
              + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        if (z != PowerManagerService.this.mFreezingScreenBrightness) {
          PowerManagerService.this.mFreezingScreenBrightness = z;
          PowerManagerService.this.mDirty |= 32;
          PowerManagerService.this.updatePowerStateLocked();
        }
      }
    }

    public float getCurrentBrightness(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] getCurrentBrightness: ratio: " + z + PowerManagerUtil.callerInfoToString());
      return z
          ? ((DisplayManager) PowerManagerService.this.mContext.getSystemService("display"))
              .getBrightness(0)
          : BrightnessSynchronizer.brightnessFloatToInt(r2);
    }

    public void setProximityDebounceTime(IBinder iBinder, int i, int i2) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setProximityDebounceTime: Positive = " + i + ", Negative = " + i2);
      if (iBinder == null) {
        throw new IllegalArgumentException("lock must not be null");
      }
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WAKE_LOCK", null);
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      PowerManagerService.this.setProximityDebounceTimeInternal(iBinder, i, i2);
    }

    public void setEarlyWakeUp(boolean z) {
      Slog.m72d("PowerManagerService", "[api] setEarlyWakeUp: enable: " + z);
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        PowerManagerService.this.mLastWakeUpReason = 0;
        if (PowerManagerService.this.isWakeUpPreventionNeededLocked()) {
          return;
        }
        PowerManagerService.this.mDirty |= 32;
        PowerManagerService.this.mEarlyWakeUp = z;
        PowerManagerService.this.updatePowerStateLocked();
      }
    }

    public long getLastUserActivityTime(int i) {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      Slog.m72d("PowerManagerService", "[api] getLastUserActivityTime: userId: " + i);
      if (((ProfilePowerState) PowerManagerService.this.mProfilePowerState.get(i)) != null) {
        return ((ProfilePowerState) PowerManagerService.this.mProfilePowerState.get(i))
            .mLastUserActivityTime;
      }
      Slog.m74e("PowerManagerService", "error on getLastUserActivityTime: userId: " + i);
      return -1L;
    }

    public String[] getWakeLockPackageList() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      HashSet hashSet = new HashSet();
      synchronized (PowerManagerService.this.mLock) {
        Iterator it = PowerManagerService.this.mWakeLocks.iterator();
        while (it.hasNext()) {
          hashSet.add(((WakeLock) it.next()).mPackageName);
        }
      }
      return (String[])
          hashSet.stream()
              .toArray(
                  new IntFunction() { // from class:
                                      // com.android.server.power.PowerManagerService$BinderService$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                      String[] lambda$getWakeLockPackageList$1;
                      lambda$getWakeLockPackageList$1 =
                          PowerManagerService.BinderService.lambda$getWakeLockPackageList$1(i);
                      return lambda$getWakeLockPackageList$1;
                    }
                  });
    }

    public static /* synthetic */ String[] lambda$getWakeLockPackageList$1(int i) {
      return new String[i];
    }

    public void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setScreenCurtainEnabled: enable: "
              + z
              + " state: "
              + Display.stateToString(i)
              + " token: "
              + iBinder
              + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        PowerManagerService.this.setScreenCurtainEnabledInternal(iBinder, z, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isScreenCurtainEnabled() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        if (PowerManagerService.this.mScreenCurtainController == null) {
          return false;
        }
        return PowerManagerService.this.mScreenCurtainController.isScreenCurtainEnabledLocked();
      }
    }

    public boolean isScreenCurtainEntryAvailable() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        if (PowerManagerService.this.mScreenCurtainController == null) {
          return false;
        }
        return PowerManagerService.this.mScreenCurtainController.isScreenCurtainAvailableLocked();
      }
    }

    public String getPackageNameOnScreenCurtain() {
      PowerManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      synchronized (PowerManagerService.this.mLock) {
        if (PowerManagerService.this.mScreenCurtainController == null) {
          return "";
        }
        return PowerManagerService.this.mScreenCurtainController
            .getPackageNameOnScreenCurtainLocked();
      }
    }
  }

  public final void setScreenCurtainEnabledInternal(IBinder iBinder, boolean z, int i) {
    synchronized (this.mLock) {
      ScreenCurtainController screenCurtainController = this.mScreenCurtainController;
      if (screenCurtainController != null) {
        screenCurtainController.setScreenCurtainEnabledLocked(iBinder, z, i);
        this.mDirty |= 32;
        updatePowerStateLocked();
      }
    }
  }

  public class LCDFlashModeLock implements IBinder.DeathRecipient {
    public IBinder binder;

    public LCDFlashModeLock(IBinder iBinder) {
      this.binder = iBinder;
      if (iBinder != null) {
        try {
          iBinder.linkToDeath(this, 0);
        } catch (RemoteException unused) {
          binderDied();
        }
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      synchronized (PowerManagerService.this.mLock) {
        Slog.m72d("PowerManagerService", "LCDFlashModeLock : binderDied");
        PowerManagerService.this.setLCDFlashModeInternal(false, this.binder);
        PowerManagerService.this.initLCDFlashMode();
      }
    }
  }

  public final void initLCDFlashMode() {
    this.mLCDFlashModeLock = null;
    Slog.m72d("PowerManagerService", "mLCDFlashModeLock set to null");
  }

  public final void setLCDFlashModeInternal(boolean z, IBinder iBinder) {
    synchronized (this.mLock) {
      if (this.mLCDFlashModeLock == null) {
        this.mLCDFlashModeLock = new LCDFlashModeLock(iBinder);
      }
      if (this.mLCDFlashMode != z) {
        this.mLCDFlashMode = z;
        this.mDirty |= 32;
        updatePowerStateLocked();
      }
    }
  }

  public BinderService getBinderServiceInstance() {
    return this.mBinderService;
  }

  public LocalService getLocalServiceInstance() {
    return this.mLocalService;
  }

  public int getLastShutdownReasonInternal() {
    String str = this.mSystemProperties.get("sys.boot.reason", null);
    str.hashCode();
    switch (str) {
      case "shutdown,thermal":
        return 4;
      case "shutdown,battery":
        return 5;
      case "reboot":
      case "reboot,userrequested":
        return 2;
      case "shutdown,userrequested":
        return 3;
      case "shutdown":
        return 1;
      case "shutdown,thermal,battery":
        return 6;
      default:
        return 0;
    }
  }

  public final int getLastSleepReasonInternal() {
    int i;
    synchronized (this.mLock) {
      i = this.mLastGlobalSleepReason;
    }
    return i;
  }

  public final PowerManager.WakeData getLastWakeupInternal() {
    PowerManager.WakeData wakeData;
    synchronized (this.mLock) {
      wakeData =
          new PowerManager.WakeData(
              this.mLastGlobalWakeTime,
              this.mLastGlobalWakeReason,
              this.mLastGlobalWakeTimeRealtime - this.mLastGlobalSleepTimeRealtime);
    }
    return wakeData;
  }

  public final PowerManager.SleepData getLastGoToSleepInternal() {
    PowerManager.SleepData sleepData;
    synchronized (this.mLock) {
      sleepData =
          new PowerManager.SleepData(this.mLastGlobalSleepTime, this.mLastGlobalSleepReason);
    }
    return sleepData;
  }

  public final boolean interceptPowerKeyDownInternal(KeyEvent keyEvent) {
    synchronized (this.mLock) {
      if (!this.mProximityPositive || this.mInterceptedPowerKeyForProximity) {
        return false;
      }
      this.mDisplayManagerInternal.ignoreProximitySensorUntilChanged();
      this.mInterceptedPowerKeyForProximity = true;
      return true;
    }
  }

  public final void goToSleepInternal(IntArray intArray, long j, int i, int i2) {
    if (j > this.mClock.uptimeMillis()) {
      throw new IllegalArgumentException("event time must not be in the future");
    }
    this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
    this.mScreenOffReason = " goToSleep: " + PowerManagerUtil.callerInfoToString();
    boolean z = (i2 & 1) != 0;
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        for (int i3 = 0; i3 < intArray.size(); i3++) {
          int i4 = intArray.get(i3);
          PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(i4);
          if (powerGroup == null) {
            throw new IllegalArgumentException("power group(" + i4 + ") doesn't exist");
          }
          if ((i2 & 2) == 0 || !powerGroup.hasWakeLockKeepingScreenOnLocked()) {
            if (z) {
              sleepPowerGroupLocked(powerGroup, j, i, callingUid);
            } else {
              dozePowerGroupLocked(powerGroup, j, i, callingUid);
            }
          }
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  final class LocalService extends PowerManagerInternal {
    public LocalService() {}

    public void setScreenBrightnessOverrideFromWindowManager(float f) {
      if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f > 1.0f) {
        f = Float.NaN;
      }
      PowerManagerService.this.setScreenBrightnessOverrideFromWindowManagerInternal(f);
    }

    public void setDozeOverrideFromDreamManager(int i, int i2) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setDozeOverrideFromDreamManager: screenState: "
              + Display.stateToString(i)
              + " screenBrightness: "
              + i2);
      if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 6) {
        i = 0;
      }
      if (i2 < -1 || i2 > 255) {
        i2 = -1;
      }
      PowerManagerService.this.setDozeOverrideFromDreamManagerInternal(i, i2, false);
    }

    public void setDozeOverrideFromDreamManager(int i, int i2, int i3, boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setDozeOverrideFromDreamManager: screenState: "
              + Display.stateToString(i)
              + " mode: 0x"
              + Integer.toHexString(i3)
              + " screenBrightness: "
              + i2
              + " shouldWait: "
              + z);
      PowerManagerService.this.setDozeModeBySysfs(i3);
      switch (i) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
          break;
        default:
          i = 0;
          break;
      }
      if (i2 < -1 || i2 > PowerManagerService.this.mScreenExtendedBrightnessMaximum) {
        i2 = -1;
      }
      PowerManagerService.this.setDozeOverrideFromDreamManagerInternal(i, i2, z);
    }

    public void setUserInactiveOverrideFromWindowManager() {
      Slog.m72d(
          "PowerManagerService",
          "[api] setUserInactiveOverrideFromWindowManager()"
              + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.setUserInactiveOverrideFromWindowManagerInternal();
    }

    public void setUserActivityTimeoutOverrideFromWindowManager(long j) {
      PowerManagerService.this.setUserActivityTimeoutOverrideFromWindowManagerInternal(j);
    }

    public void setUserActivityTimeoutForDexOverrideFromWindowManager(long j) {
      PowerManagerService.this.setUserActivityTimeoutForDexOverrideFromWindowManagerInternal(j);
    }

    public void setDrawWakeLockOverrideFromSidekick(boolean z) {
      PowerManagerService.this.setDrawWakeLockOverrideFromSidekickInternal(z);
    }

    public void setMaximumScreenOffTimeoutFromDeviceAdmin(int i, long j) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setMaximumScreenOffTimeoutFromDeviceAdmin: userId: "
              + i
              + " timeMs: "
              + j
              + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.setMaximumScreenOffTimeoutFromDeviceAdminInternal(i, j);
    }

    public void setMaximumScreenOffTimeoutFromKnox(int i, long j) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setMaximumScreenOffTimeoutFromKnox: userId: "
              + i
              + " timeMs: "
              + j
              + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.setMaximumScreenOffTimeoutFromKnoxInternal(i, j);
    }

    public void updateProfileActivityTimeFromKnox(int i, long j) {
      Slog.m72d(
          "PowerManagerService",
          "[api] updateProfileActivityTimeFromKnox: userId: "
              + i
              + " timeMs: "
              + j
              + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.updateProfileActivityTimeFromKnoxInternal(i, j);
    }

    public PowerSaveState getLowPowerState(int i) {
      return PowerManagerService.this.mBatterySaverPolicy.getBatterySaverPolicy(i);
    }

    public void registerLowPowerModeObserver(
        PowerManagerInternal.LowPowerModeListener lowPowerModeListener) {
      Slog.m72d(
          "PowerManagerService",
          "[api] registerLowPowerModeObserver: "
              + lowPowerModeListener
              + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.mBatterySaverController.addListener(lowPowerModeListener);
    }

    public boolean setDeviceIdleMode(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setDeviceIdleMode: " + z + PowerManagerUtil.callerInfoToString());
      return PowerManagerService.this.setDeviceIdleModeInternal(z);
    }

    public boolean setLightDeviceIdleMode(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setLightDeviceIdleMode: " + z + PowerManagerUtil.callerInfoToString());
      return PowerManagerService.this.setLightDeviceIdleModeInternal(z);
    }

    public void setDeviceIdleWhitelist(int[] iArr) {
      PowerManagerService.this.setDeviceIdleWhitelistInternal(iArr);
    }

    public void setDeviceIdleTempWhitelist(int[] iArr) {
      PowerManagerService.this.setDeviceIdleTempWhitelistInternal(iArr);
    }

    public void setLowPowerStandbyAllowlist(int[] iArr) {
      PowerManagerService.this.setLowPowerStandbyAllowlistInternal(iArr);
    }

    public void setLowPowerStandbyActive(boolean z) {
      PowerManagerService.this.setLowPowerStandbyActiveInternal(z);
    }

    public void startUidChanges() {
      PowerManagerService.this.startUidChangesInternal();
    }

    public void finishUidChanges() {
      PowerManagerService.this.finishUidChangesInternal();
    }

    public void updateUidProcState(int i, int i2) {
      PowerManagerService.this.updateUidProcStateInternal(i, i2);
    }

    public void uidGone(int i) {
      PowerManagerService.this.uidGoneInternal(i);
    }

    public void uidActive(int i) {
      PowerManagerService.this.uidActiveInternal(i);
    }

    public void uidIdle(int i) {
      PowerManagerService.this.uidIdleInternal(i);
    }

    public void setPowerBoost(int i, int i2) {
      PowerManagerService.this.setPowerBoostInternal(i, i2);
    }

    public void setPowerMode(int i, boolean z) {
      PowerManagerService.this.setPowerModeInternal(i, z);
    }

    public boolean wasDeviceIdleFor(long j) {
      return PowerManagerService.this.wasDeviceIdleForInternal(j);
    }

    public PowerManager.WakeData getLastWakeup() {
      return PowerManagerService.this.getLastWakeupInternal();
    }

    public PowerManager.SleepData getLastGoToSleep() {
      return PowerManagerService.this.getLastGoToSleepInternal();
    }

    public boolean interceptPowerKeyDown(KeyEvent keyEvent) {
      return PowerManagerService.this.interceptPowerKeyDownInternal(keyEvent);
    }

    public void setScreenDimDurationOverrideFromSqd(boolean z) {
      PowerManagerService.this.setScreenDimDurationOverrideFromSqdInternal(z);
    }

    public void setScreenDimDurationOverrideFromWindowManager(long j) {
      PowerManagerService.this.setScreenDimDurationOverrideFromWindowManagerInternal(j);
    }

    public boolean isInternalDisplayOff() {
      return PowerManagerService.this.isInternalDisplayOffInternal();
    }

    public boolean isProximityPositive() {
      return PowerManagerService.this.isProximityPositiveInternal();
    }

    public void setGoToSleepPrevention(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setGoToSleepPrevention: enabled: " + z + PowerManagerUtil.callerInfoToString());
      PowerManagerService.this.setGoToSleepPreventionInternal(z);
    }

    public int setWakeLockEnableDisable(int i, boolean z) {
      synchronized (PowerManagerService.this.mLock) {
        Slog.m76i(
            "PowerManagerService",
            "[PWL] SetWakeLockEnableDisable uid = " + i + " , disable= " + z);
        Iterator it = PowerManagerService.this.mWakeLocks.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
          WakeLock wakeLock = (WakeLock) it.next();
          if (wakeLock.mOwnerUid == i && wakeLock.mDisabledByFreecess != z) {
            wakeLock.mDisabledByFreecess = z;
            if (PowerManagerService.this.setWakeLockDisabledStateLocked(wakeLock)) {
              if (wakeLock.mDisabled) {
                PowerManagerService.this.notifyWakeLockReleasedLocked(wakeLock);
              } else {
                PowerManagerService.this.notifyWakeLockAcquiredLocked(wakeLock);
              }
              z2 = true;
            }
            if (z2) {
              Slog.m76i(
                  "PowerManagerService", "[PWL] find out: " + wakeLock + "  disabled -> " + z);
            }
          } else {
            WorkSource workSource = wakeLock.mWorkSource;
            if (workSource != null) {
              int size = workSource.size();
              for (int i2 = 0; i2 < size; i2++) {
                if (wakeLock.mWorkSource.get(i2) == i && wakeLock.mDisabled != z) {
                  Slog.m76i(
                      "PowerManagerService",
                      "[PWL] find out in ws: " + wakeLock + "  disabled -> " + z);
                  return 2;
                }
              }
            } else {
              continue;
            }
          }
        }
        if (z2) {
          PowerManagerService.this.mDirty |= 1;
          PowerManagerService.this.updatePowerStateLocked();
          return 1;
        }
        Slog.m76i("PowerManagerService", "[PWL] can not change uid =  " + i);
        return 0;
      }
    }

    public void setWakeLockBlackListEnableDisable(int i, boolean z) {
      synchronized (PowerManagerService.this.mLock) {
        if (z) {
          if (PowerManagerService.this.mWakelockBlacklistAppid.contains(Integer.valueOf(i))) {
            return;
          } else {
            PowerManagerService.this.mWakelockBlacklistAppid.add(Integer.valueOf(i));
          }
        } else {
          if (!PowerManagerService.this.mWakelockBlacklistAppid.contains(Integer.valueOf(i))) {
            return;
          }
          PowerManagerService.this.mWakelockBlacklistAppid.remove(
              PowerManagerService.this.mWakelockBlacklistAppid.indexOf(Integer.valueOf(i)));
        }
        Iterator it = PowerManagerService.this.mWakeLocks.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
          WakeLock wakeLock = (WakeLock) it.next();
          if (UserHandle.getAppId(wakeLock.mOwnerUid) == i
              && PowerManagerService.this.setWakeLockDisabledStateLocked(wakeLock)) {
            if (wakeLock.mDisabled) {
              PowerManagerService.this.notifyWakeLockReleasedLocked(wakeLock);
            } else {
              PowerManagerService.this.notifyWakeLockAcquiredLocked(wakeLock);
            }
            z2 = true;
          }
        }
        if (z2) {
          PowerManagerService.this.mDirty |= 1;
          PowerManagerService.this.updatePowerStateLocked();
        } else {
          Slog.m76i("PowerManagerService", "[PWL] can not change appid =  " + i);
        }
      }
    }

    public void setHbmBlock(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setHbmBlock: enabled: " + z + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.setHbmBlockInternal(z);
    }

    public void setFreezeBrightnessMode(boolean z) {
      Slog.m72d(
          "PowerManagerService",
          "[api] setFreezeBrightnessMode: enabled: "
              + z
              + PowerManagerUtil.callerInfoToString(true));
      PowerManagerService.this.setFreezeBrightnessModeInternal(z);
    }

    public void registerUserActivityStateListener(
        PowerManagerInternal.UserActivityStateListener userActivityStateListener) {
      Slog.m72d(
          "PowerManagerService", "registerUserActivityStateListener: " + userActivityStateListener);
      if (userActivityStateListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      PowerManagerService.this.registerUserActivityStateListenerInternal(userActivityStateListener);
    }

    public void unregisterUserActivityStateListener(
        PowerManagerInternal.UserActivityStateListener userActivityStateListener) {
      Slog.m72d(
          "PowerManagerService",
          "unregisterUserActivityStateListener: " + userActivityStateListener);
      if (userActivityStateListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      PowerManagerService.this.unregisterUserActivityStateListenerInternal(
          userActivityStateListener);
    }

    public int getLastUserActivityState() {
      return PowerManagerService.this.getLastUserActivityStateInternal();
    }

    public void nap(long j, boolean z) {
      PowerManagerService.this.napInternal(j, 1000, z);
    }

    public boolean isAmbientDisplaySuppressed() {
      return PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed();
    }
  }

  public final String findForegroundPackageName() {
    List<ActivityManager.RunningTaskInfo> runningTasks =
        this.mInjector.getActivityManager(this.mContext).getRunningTasks(1);
    return runningTasks.isEmpty() ? "" : runningTasks.get(0).topActivity.getPackageName();
  }

  public static String callerInfoWithProcessName() {
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    String str = null;
    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new FileReader(String.format("/proc/%d/cmdline", Integer.valueOf(callingPid))));
      try {
        String readLine = bufferedReader.readLine();
        if (readLine != null) {
          String[] split = readLine.split("\u0000");
          if (split.length > 0) {
            str = split[0];
          }
        }
        bufferedReader.close();
      } finally {
      }
    } catch (IOException e) {
      Slog.m75e("PowerManagerService", "error on read process name", e);
    }
    return String.format(
        " (uid: %d pid: %d processName: %s)",
        Integer.valueOf(callingUid), Integer.valueOf(callingPid), str);
  }

  public final void setMasterBrightnessLimitInternal(int i, int i2, int i3) {
    synchronized (this.mLock) {
      this.mMasterBrightnessLowerLimit = i;
      this.mMasterBrightnessUpperLimit = i2;
      this.mMasterBrightnessLimitPeriod = i3;
      this.mMasterBrightnessLimitLastCaller = PowerManagerUtil.callerInfoToString();
      Slog.m72d(
          "PowerManagerService",
          "[api] setMasterBrightnessLimit : lowerLimit : "
              + i
              + "  upperLimit : "
              + i2
              + "  mMasterBrightnessLimitPeriod : "
              + this.mMasterBrightnessLimitPeriod
              + PowerManagerUtil.callerInfoToString());
      if (this.mMasterBrightnessLimitPeriod > 0) {
        this.mMasterBrightnessLimitRunning = true;
        if (i2 == -1) {
          i2 = this.mScreenExtendedBrightnessMaximum;
        }
        this.mTargetBrightnessForLimit = i2;
        this.mLastRequestedLimitationOfBrightness = getStartingLimitationOfBrightness();
        this.mHandler.removeCallbacks(this.mBrightnessLimitRunnable);
        this.mHandler.postAtTime(this.mBrightnessLimitRunnable, this.mClock.uptimeMillis());
      } else {
        this.mMasterBrightnessLimitRunning = false;
        this.mLastRequestedLimitationOfBrightness = i2;
        this.mHandler.removeCallbacks(this.mBrightnessLimitRunnable);
        this.mDirty |= 32;
        updatePowerStateLocked();
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:4:0x0011, code lost:

     if (r3.mDualScreenPolicy == 1) goto L8;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int getStartingLimitationOfBrightness() {
    DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
    int i = PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY ? 1 : 0;
    BrightnessInfo brightnessInfo = displayManager.getDisplay(i).getBrightnessInfo();
    int brightnessFloatToInt =
        brightnessInfo != null
            ? BrightnessSynchronizer.brightnessFloatToInt(brightnessInfo.adjustedBrightness)
            : -1;
    int i2 = this.mTargetBrightnessForLimit;
    int i3 = this.mLastRequestedLimitationOfBrightness;
    return (i2 >= i3 || brightnessFloatToInt < 0) ? i3 : Math.max(i2, brightnessFloatToInt);
  }

  public void updateMasterBrightnessLimitLocked(int i, int i2) {
    this.mMasterBrightnessLowerLimit = i;
    this.mMasterBrightnessUpperLimit = i2;
    this.mDirty |= 32;
    updatePowerStateLocked();
  }

  public final void cancelMasterBrightnessLimitAnimationLocked() {
    this.mMasterBrightnessLimitRunning = false;
    this.mHandler.removeCallbacks(this.mBrightnessLimitRunnable);
    int i = this.mTargetBrightnessForLimit;
    this.mLastRequestedLimitationOfBrightness = i;
    updateMasterBrightnessLimitLocked(-1, i);
    Slog.m72d("PowerManagerService", "[api] BrightnessLimitRunnable done (complete immediately)");
  }

  public final void setQuickPanelBrightnessBarDisabled(boolean z) {
    Slog.m72d("PowerManagerService", "Hide QuickPanel Brightness Bar: " + z);
    Settings.System.putIntForUser(
        this.mContext.getContentResolver(),
        "pms_notification_panel_brightness_adjustment",
        !z ? 1 : 0,
        -2);
  }

  public class DeviceStateListener implements DeviceStateManager.DeviceStateCallback {
    public int mDeviceState = -1;

    public DeviceStateListener() {}

    public void onStateChanged(int i) {
      if (this.mDeviceState != i) {
        this.mDeviceState = i;
        synchronized (PowerManagerService.this.mLock) {
          if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
            int i2 = PowerManagerService.this.mDualScreenPolicy;
            PowerManagerService.this.mDualScreenPolicy = PowerManagerUtil.getDualScreenPolicy(i);
            if (PowerManagerService.this.mDualScreenPolicy != i2) {
              Slog.m72d(
                  "PowerManagerService",
                  "onStateChanged: mDualScreenPolicy="
                      + PowerManagerService.this.mDualScreenPolicy);
              PowerManagerService.this.mForceSetHalInteractiveMode = true;
            }
          }
        }
        PowerManagerService powerManagerService = PowerManagerService.this;
        powerManagerService.userActivityInternal(
            0, powerManagerService.mClock.uptimeMillis(), 6, 0, 1000);
      }
    }
  }

  public static boolean isSameCallback(
      IWakeLockCallback iWakeLockCallback, IWakeLockCallback iWakeLockCallback2) {
    if (iWakeLockCallback == iWakeLockCallback2) {
      return true;
    }
    return (iWakeLockCallback == null
            || iWakeLockCallback2 == null
            || iWakeLockCallback.asBinder() != iWakeLockCallback2.asBinder())
        ? false
        : true;
  }

  public final void updateRelatedPowerGroupWakefulnessLocked(
      int i, int i2, long j, int i3, int i4, String str, String str2) {
    if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW
        && i == 0
        && i2 == 1
        && this.mIsDualViewMode
        && this.mPowerGroups.contains(2)) {
      wakePowerGroupLocked((PowerGroup) this.mPowerGroups.get(2), j, 11, str2, i3, str, i4);
      userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(2), j, 0, 0, i3);
    }
    if (CoreRune.CARLIFE_DISPLAY_GROUP && i == 0 && i2 == 1 && this.mPowerGroups.contains(4)) {
      wakePowerGroupLocked((PowerGroup) this.mPowerGroups.get(4), j, 11, str2, i3, str, i4);
      userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(4), j, 0, 0, i3);
    }
  }
}
