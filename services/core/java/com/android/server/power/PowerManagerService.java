package com.android.server.power;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.app.SynchronousUserSwitchObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.SystemSensorManager;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.BatteryManagerInternal;
import android.os.BatterySaverPolicyConfig;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
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
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.service.dreams.DreamManagerInternal;
import android.sysprop.CrashRecoveryProperties;
import android.sysprop.InitProperties;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.LongArray;
import android.util.Pair;
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
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.LockGuard;
import com.android.server.RescueParty;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.BatteryStatsService;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.deviceidle.Flags;
import com.android.server.display.DisplayManagerService$BinderService$$ExternalSyntheticOutline0;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LightsService;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.HqmDataDispatcher;
import com.android.server.power.LowPowerStandbyController;
import com.android.server.power.Notifier;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerService;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.SmartStayController;
import com.android.server.power.batterysaver.BatterySaverController;
import com.android.server.power.batterysaver.BatterySaverPolicy;
import com.android.server.power.batterysaver.BatterySaverStateMachine;
import com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda0;
import com.android.server.power.batterysaver.BatterySavingStats;
import com.android.server.power.feature.PowerManagerFlags;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.hardware.secinputdev.ISemInputDeviceManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerManagerService extends SystemService implements Watchdog.Monitor {
    public static boolean sQuiescent;
    public AbuseWakeLockDetector mAbuseWakeLockDetector;
    public final AnonymousClass1 mAbuseWakeLockDetectorCallback;
    public final AnonymousClass1 mAdaptiveScreenOffTimeoutCallbacks;
    public AdaptiveScreenOffTimeoutController mAdaptiveScreenOffTimeoutController;
    public int mAlpmHlpmMode;
    public boolean mAlwaysOnEnabled;
    public final AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    public final AmbientDisplaySuppressionController mAmbientDisplaySuppressionController;
    public boolean mAodMode;
    public final AttentionDetector mAttentionDetector;
    public LightsService.LightImpl mAttentionLight;
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
    public final BatterySaverStateMachine mBatterySaverStateMachine;
    public final boolean mBatterySaverSupported;
    public float mBatterySavingAdjustBrightnessFactor;
    public long mBatterySavingScreenOffTimeoutSetting;
    public IBatteryStats mBatteryStats;
    public final BinderService mBinderService;
    public boolean mBootCompleted;
    public final SuspendBlockerImpl mBootingSuspendBlocker;
    public boolean mBrightWhenDozingConfig;
    public int mBrightnessLimitByCoverConfig;
    public final AnonymousClass6 mBrightnessLimitRunnable;
    public final Injector.AnonymousClass1 mClock;
    public final Constants mConstants;
    public final Context mContext;
    public boolean mCoverAuthReady;
    public Runnable mCoverAuthReadyRunnable;
    public CoverManager mCoverManager;
    public final AnonymousClass10 mCoverStateListener;
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
    public final AnonymousClass3 mDisplayPowerCallbacks;
    public final SuspendBlockerImpl mDisplaySuspendBlocker;
    public int mDockState;
    public boolean mDoubleTapWakeEnabled;
    public boolean mDozeAfterScreenOff;
    public int mDozeScreenBrightnessOverrideFromDreamManager;
    public float mDozeScreenBrightnessOverrideFromDreamManagerFloat;
    public int mDozeScreenStateOverrideFromDreamManager;
    public int mDozeScreenStateOverrideReasonFromDreamManager;
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
    public final AnonymousClass6 mEnsureTransitionToDozingReleaser;
    public boolean mEnsureTransitionToDozingReleaserRunning;
    public final boolean mFeatureCoverSysfs;
    public final PowerManagerFlags mFeatureFlags;
    public final FoldGracePeriodProvider mFoldGracePeriodProvider;
    public boolean mForceSetHalInteractiveMode;
    public boolean mForceSlowChange;
    public boolean mForceSuspendActive;
    public ForegroundPackageObserver mForegroundPackageObserver;
    public int mForegroundProfile;
    public boolean mFreezingScreenBrightness;
    public boolean mGoToSleepPreventionEnabled;
    public boolean mHalAutoSuspendModeEnabled;
    public boolean mHalInteractiveModeEnabled;
    public final Handler mHandler;
    public final Handler mHandlerPmsMisc;
    public boolean mHbmBlock;
    public final ArrayList mHdrBrightnessLimitLocks;
    public int mHdrBrightnessLimitPeriod;
    public final AnonymousClass6 mHdrBrightnessLimitRunnable;
    public boolean mHdrBrightnessLimitRunning;
    public int mHdrBrightnessUpperLimit;
    public boolean mHoldingBootingSuspendBlocker;
    public boolean mHoldingDisplaySuspendBlocker;
    public boolean mHoldingWakeLockSuspendBlocker;
    public final InattentiveSleepWarningController mInattentiveSleepWarningOverlayController;
    public final Injector mInjector;
    public boolean mInterceptedKeyForProximity;
    public boolean mInterceptedPowerKeyForProximity;
    public Intent mInternalDisplayOffByPowerKeyIntent;
    public boolean mIsCoverClosed;
    public boolean mIsDualViewMode;
    public final AtomicBoolean mIsPowerBoostInvokedFromLocal;
    public boolean mIsPowered;
    public final AtomicBoolean mIsUserActivityInvoked;
    public final AtomicBoolean mIsUserActivityNativeInvoked;
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
    public int mLastRequestedLimitationOfHdrBrightness;
    public long mLastScreenBrightnessBoostTime;
    public int mLastScreenTimeout;
    public long mLastUserActivityStateListenerCalledTime;
    public int mLastUserActivityStateListenerState;
    public int mLastWakeUpReason;
    public long mLastWarningAboutUserActivityPermission;
    public boolean mLightDeviceIdleMode;
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
    public final PowerManagerService$Injector$$ExternalSyntheticLambda0 mPermissionCheckerWrapper;
    public int mPlugType;
    public WindowManagerPolicy mPolicy;
    public final AnonymousClass1 mPowerGroupWakefulnessChangeListener;
    public final SparseArray mPowerGroups;
    public final PowerHistorian mPowerHistorian;
    public final Injector.AnonymousClass1 mPowerPropertiesWrapper;
    public final AnonymousClass6 mPrintWakeLockAperiodicallyRunnable;
    public final SparseArray mProfilePowerState;
    public boolean mProximityPositive;
    public SettingsObserver mQuickTilesSettingsObserver;
    public boolean mRequestWaitForNegativeProximity;
    public boolean mSafeMode;
    public boolean mSandmanScheduled;
    public boolean mScreenBrightnessBoostInProgress;
    public final float mScreenBrightnessDefault;
    public final float mScreenBrightnessDim;
    public final float mScreenBrightnessDoze;
    public float mScreenBrightnessForClearCoverConfig;
    public final float mScreenBrightnessMaximum;
    public final float mScreenBrightnessMinimum;
    public float mScreenBrightnessOverrideFromWindowManager;
    public String mScreenBrightnessOverridePackageFromWindowManager;
    public float mScreenBrightnessScaleFactor;
    public ScreenCurtainController mScreenCurtainController;
    public long mScreenDimDurationOverrideFromSQD;
    public long mScreenDimDurationOverrideFromWindowManager;
    public final int mScreenExtendedBrightnessMaximum;
    public String mScreenOffReason;
    public int mScreenOffTimeoutForDex;
    public long mScreenOffTimeoutSetting;
    public ScreenOnKeeper mScreenOnKeeper;
    public int mScreenOnOffCount;
    public String mScreenOnReason;
    public ScreenTimeoutOverridePolicy mScreenTimeoutOverridePolicy;
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
    public final SuspendBlockerMonitor mSuspendBlockerMonitor;
    public final ArrayList mSuspendBlockers;
    public boolean mSuspendWhenScreenOffDueToProximityConfig;
    public final Injector.AnonymousClass1 mSystemProperties;
    public boolean mSystemReady;
    public int mTargetBrightnessForHdrLimit;
    public int mTargetBrightnessForLimit;
    public boolean mTheaterModeEnabled;
    public final SparseArray mUidState;
    public boolean mUidsChanged;
    public boolean mUidsChanging;
    public boolean mUpdatePowerStateInProgress;
    public boolean mUseAdaptiveScreenOffTimeout;
    public final boolean mUseAutoSuspend;
    public boolean mUseKeepScreenOn;
    public boolean mUseScreenCurtain;
    public Intent mUserActivityIntent;
    public final ArrayList mUserActivityStateListenerListeners;
    public final AnonymousClass6 mUserActivityStateListenerTimeoutRunnable;
    public final AnonymousClass6 mUserActivityTask;
    public long mUserActivityTimeoutForDexOverrideFromWindowManager;
    public long mUserActivityTimeoutOverrideFromWindowManager;
    public int mUserActivityTimeoutSetting;
    public final AnonymousClass6 mUserActivityTimeoutTask;
    public int mUserId;
    public boolean mUserInactiveOverrideFromWindowManager;
    public int mWakeLockSummary;
    public final SuspendBlockerImpl mWakeLockSuspendBlocker;
    public final ArrayList mWakeLocks;
    public final ArrayList mWakeUpPreventionLocks;
    public boolean mWakeUpWhenPluggedOrUnpluggedConfig;
    public boolean mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig;
    public boolean mWakefulnessChanging;
    public int mWakefulnessRaw;
    public final ArrayList mWakelockBlacklistAppid;
    public WirelessChargerDetector mWirelessChargerDetector;
    public WirelessChargerMotionDetector mWirelessChargerMotionDetector;
    public long mlastSetCoverTypeTime;
    public String mlastUpdateCoverStateReason;
    public long mlastUpdateCoverStateTime;
    public String mlastUpdateCoverTypeReason;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static final IntArray DEFAULT_DISPLAY_GROUP_IDS = IntArray.wrap(new int[]{0});
    public static final boolean SEC_USE_FACTORY_BINARY = FactoryTest.isFactoryBinary();
    public static final Uri DEX_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.PowerManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public /* synthetic */ AnonymousClass1() {
        }

        public void onAbuseWakeLockAdded(final List list) {
            Slog.d("PowerManagerService", "abuse wakelock package : " + String.join(", ", list));
            PowerManagerService powerManagerService = PowerManagerService.this;
            powerManagerService.mWakeLocks.stream().filter(new Predicate() { // from class: com.android.server.power.PowerManagerService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    PowerManagerService.WakeLock wakeLock = (PowerManagerService.WakeLock) obj;
                    return PowerManagerService.isScreenLock(wakeLock) && list.contains(wakeLock.mPackageName);
                }
            }).forEach(new PowerManagerService$$ExternalSyntheticLambda10(2, this));
            powerManagerService.mDirty |= 1;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class BatteryReceiver extends BroadcastReceiver {
        public BatteryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                powerManagerService.mDirty |= 256;
                powerManagerService.updatePowerStateLocked();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class BinderService extends IPowerManager.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final PowerManagerShellCommand mShellCommand;

        public BinderService(Context context) {
            this.mShellCommand = new PowerManagerShellCommand(context, this);
        }

        public final void acquireLowPowerStandbyPorts(IBinder iBinder, List list) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SET_LOW_POWER_STANDBY_PORTS", "acquireLowPowerStandbyPorts");
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.mLowPowerStandbyController.acquireStandbyPorts(iBinder, PowerManager.LowPowerStandbyPortDescription.fromParcelable(list), callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void acquireWakeLock(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3, int i2, IWakeLockCallback iWakeLockCallback) {
            WorkSource workSource2;
            int i3;
            int i4;
            WorkSource workSource3;
            boolean z;
            long clearCallingIdentity;
            int i5;
            String str4;
            int i6;
            PackageManager packageManager;
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            if (str2 == null) {
                throw new IllegalArgumentException("packageName must not be null");
            }
            PowerManager.validateWakeLockParameters(i, str);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            if ((i & 64) != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            }
            if ((i & 256) != 0) {
                if (!PowerManagerService.this.mFeatureFlags.mEarlyScreenTimeoutDetectorFlagState.isEnabled()) {
                    throw new IllegalArgumentException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "Acquiring an unsupported wake lock: flags=", ", tag=", str));
                }
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SCREEN_TIMEOUT_OVERRIDE", null);
            }
            if (workSource == null || workSource.isEmpty()) {
                workSource2 = null;
            } else {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", null);
                workSource2 = workSource;
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (CoreRune.SYSFW_APP_SPEG && (packageManager = PowerManagerService.this.mContext.getPackageManager()) != null && packageManager.isSpeg(Binder.getCallingUid())) {
                Slog.d("SPEG", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(callingUid, "App ", str2, ":", " tries to acquire wake lock. Ignore"));
                return;
            }
            if ((Integer.MIN_VALUE & i) != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
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
                if ((268435456 & i) != 0 && ((i5 = 65535 & i) == 6 || i5 == 10 || i5 == 26)) {
                    if (workSource3 == null || workSource3.isEmpty()) {
                        str4 = str2;
                        i6 = i3;
                    } else {
                        WorkSource.WorkChain firstNonEmptyWorkChain = PowerManagerService.getFirstNonEmptyWorkChain(workSource3);
                        if (firstNonEmptyWorkChain != null) {
                            str4 = firstNonEmptyWorkChain.getAttributionTag();
                            i6 = firstNonEmptyWorkChain.getAttributionUid();
                        } else {
                            str4 = workSource3.getName(0) != null ? workSource3.getName(0) : str2;
                            i6 = workSource3.get(0);
                        }
                    }
                    PowerManagerService.this.mInjector.getClass();
                    INotificationManager service = NotificationManager.getService();
                    if (service != null) {
                        try {
                            z = service.isAlertsAllowed(str4, i6, str, i);
                        } catch (RemoteException e) {
                            Slog.e("PowerManagerService", "Failed to call NotificationManager : " + e);
                        }
                        boolean z2 = z;
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        PowerManagerService.m794$$Nest$macquireWakeLockInternal(PowerManagerService.this, iBinder, i2, i, str, str2, workSource3, str3, i3, i4, iWakeLockCallback, z2);
                        return;
                    }
                }
                PowerManagerService.m794$$Nest$macquireWakeLockInternal(PowerManagerService.this, iBinder, i2, i, str, str2, workSource3, str3, i3, i4, iWakeLockCallback, z2);
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            z = true;
            boolean z22 = z;
            clearCallingIdentity = Binder.clearCallingIdentity();
        }

        public final void acquireWakeLockAsync(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) {
            acquireWakeLock(iBinder, i, str, str2, workSource, str3, -1, null);
        }

        public final void acquireWakeLockWithUid(IBinder iBinder, int i, String str, String str2, int i2, int i3, IWakeLockCallback iWakeLockCallback) {
            acquireWakeLock(iBinder, i, str, str2, new WorkSource(i2 < 0 ? Binder.getCallingUid() : i2), null, i3, iWakeLockCallback);
        }

        public final void addAdaptiveScreenOffTimeoutConfig(List list) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "addAdaptiveScreenOffTimeoutConfig: mSafeMode: " + PowerManagerService.this.mSafeMode);
                    } else {
                        AdaptiveScreenOffTimeoutController adaptiveScreenOffTimeoutController = powerManagerService.mAdaptiveScreenOffTimeoutController;
                        if (adaptiveScreenOffTimeoutController != null) {
                            adaptiveScreenOffTimeoutController.addAdaptiveScreenOffTimeoutConfigLocked(PowerManager.AdaptiveScreenOffTimeoutConfig.fromParcelable(list));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean areAutoPowerSaveModesEnabled() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.mContext.getResources().getBoolean(R.bool.config_enableDefaultHdrConversionPassthrough);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void boostScreenBrightness(long j) {
            PowerManagerService.this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            StringBuilder sb = new StringBuilder("[api] boostScreenBrightness: ");
            sb.append(j);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
            PowerManagerService.this.mClock.getClass();
            if (j > SystemClock.uptimeMillis()) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Event time ", j, " cannot be newer than ");
                m.append(uptimeMillis);
                Slog.e("PowerManagerService", m.toString());
                throw new IllegalArgumentException("event time must not be in the future");
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m795$$Nest$mboostScreenBrightnessInternal(PowerManagerService.this, j, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void crash(final String str) {
            StringBuilder sb = new StringBuilder("[api] crash: ");
            sb.append(str);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.getClass();
                Thread thread = new Thread() { // from class: com.android.server.power.PowerManagerService.5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("PowerManagerService.crash()");
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        throw new RuntimeException(str);
                    }
                };
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    int i = Slog.$r8$clinit;
                    android.util.Slog.wtf("PowerManagerService", e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(PowerManagerService.this.mContext, "PowerManagerService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                int i = 0;
                boolean z = strArr.length == 0;
                int length = strArr.length;
                while (true) {
                    try {
                        if (i >= length) {
                            PowerManagerService.this.dumpInternal(printWriter, z);
                            break;
                        }
                        String str = strArr[i];
                        if (str.equals("--proto")) {
                            PowerManagerService.m798$$Nest$mdumpProto(PowerManagerService.this, fileDescriptor);
                            break;
                        }
                        if (!z && str.equals("CRITICAL")) {
                            z = true;
                        }
                        i++;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }

        public final void forceLowPowerStandbyActive(boolean z) {
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "forceLowPowerStandbyActive");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LowPowerStandbyController lowPowerStandbyController = PowerManagerService.this.mLowPowerStandbyController;
                synchronized (lowPowerStandbyController.mLock) {
                    lowPowerStandbyController.mForceActive = z;
                    lowPowerStandbyController.updateActiveLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean forceSuspend() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.m799$$Nest$mforceSuspendInternal(PowerManagerService.this, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getActiveLowPowerStandbyPorts() {
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "getActiveLowPowerStandbyPorts");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManager.LowPowerStandbyPortDescription.toParcelable(PowerManagerService.this.mLowPowerStandbyController.getActiveStandbyPorts());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getAdaptiveScreenOffTimeoutConfig() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "getAdaptiveScreenOffTimeoutConfig: mSafeMode: " + PowerManagerService.this.mSafeMode);
                        return null;
                    }
                    AdaptiveScreenOffTimeoutController adaptiveScreenOffTimeoutController = powerManagerService.mAdaptiveScreenOffTimeoutController;
                    if (adaptiveScreenOffTimeoutController == null) {
                        return null;
                    }
                    return PowerManager.AdaptiveScreenOffTimeoutConfig.toParcelable(adaptiveScreenOffTimeoutController.getAdaptiveScreenOffTimeoutConfigLocked());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final ParcelDuration getBatteryDischargePrediction() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (PowerManagerService.this.mLock) {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mIsPowered) {
                        return null;
                    }
                    synchronized (powerManagerService.mEnhancedDischargeTimeLock) {
                        PowerManagerService.this.mClock.getClass();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        if (!isEnhancedDischargePredictionValidLocked(elapsedRealtime)) {
                            return new ParcelDuration(PowerManagerService.this.mBatteryStats.computeBatteryTimeRemaining());
                        }
                        return new ParcelDuration(PowerManagerService.this.mEnhancedDischargeTimeElapsed - elapsedRealtime);
                    }
                }
            } catch (RemoteException unused) {
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final float getBrightnessConstraint(int i) {
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

        public final float getCurrentBrightness(boolean z) {
            StringBuilder sb = new StringBuilder("[api] getCurrentBrightness: ratio: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            return z ? ((DisplayManager) PowerManagerService.this.mContext.getSystemService("display")).getBrightness(0) : BrightnessSynchronizer.brightnessFloatToInt(r3);
        }

        public final BatterySaverPolicyConfig getFullPowerSavePolicy() {
            BatterySaverPolicyConfig build;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mBatterySaverSupported) {
                    BatterySaverStateMachine batterySaverStateMachine = powerManagerService.mBatterySaverStateMachine;
                    synchronized (batterySaverStateMachine.mLock) {
                        build = batterySaverStateMachine.mBatterySaverController.getPolicyLocked();
                    }
                } else {
                    build = new BatterySaverPolicyConfig.Builder().build();
                }
                return build;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getLastShutdownReason() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.getLastShutdownReasonInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getLastSleepReason() {
            int i;
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    i = powerManagerService.mLastGlobalSleepReason;
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final long getLastUserActivityTime(int i) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            Slog.d("PowerManagerService", "[api] getLastUserActivityTime: userId: " + i);
            if (((ProfilePowerState) PowerManagerService.this.mProfilePowerState.get(i)) != null) {
                return ((ProfilePowerState) PowerManagerService.this.mProfilePowerState.get(i)).mLastUserActivityTime;
            }
            Slog.e("PowerManagerService", "error on getLastUserActivityTime: userId: " + i);
            return -1L;
        }

        public final IPowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() {
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "getLowPowerStandbyPolicy");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManager.LowPowerStandbyPolicy.toParcelable(PowerManagerService.this.mLowPowerStandbyController.getPolicy());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final String getPackageNameOnScreenCurtain() {
            String str;
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "getPackageNameOnScreenCurtain: mSafeMode: " + PowerManagerService.this.mSafeMode);
                        return "";
                    }
                    ScreenCurtainController screenCurtainController = powerManagerService.mScreenCurtainController;
                    if (screenCurtainController == null) {
                        return "";
                    }
                    synchronized (screenCurtainController) {
                        Slog.d("ScreenCurtainController", "getPackageNameOnScreenCurtainLocked: " + screenCurtainController.mPackageNameOnScreenCurtain);
                        str = screenCurtainController.mPackageNameOnScreenCurtain;
                    }
                    return str;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int getPowerSaveModeTrigger() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.Global.getInt(PowerManagerService.this.mContext.getContentResolver(), "automatic_power_save_mode", 0);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final PowerSaveState getPowerSaveState(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                PowerSaveState batterySaverPolicy = powerManagerService.mBatterySaverSupported ? powerManagerService.mBatterySaverStateMachine.mBatterySaverController.mBatterySaverPolicy.getBatterySaverPolicy(i) : new PowerSaveState.Builder().build();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return batterySaverPolicy;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final String[] getWakeLockPackageList() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            HashSet hashSet = new HashSet();
            synchronized (PowerManagerService.this.mLock) {
                try {
                    Iterator it = PowerManagerService.this.mWakeLocks.iterator();
                    while (it.hasNext()) {
                        hashSet.add(((WakeLock) it.next()).mPackageName);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (String[]) hashSet.stream().toArray(new PowerManagerService$BinderService$$ExternalSyntheticLambda1());
        }

        public final void goToSleep(long j, int i, int i2) {
            Slog.d("PowerManagerService", "[api] goToSleep" + PowerManagerUtil.callerInfoToString(false) + " eventTime = " + j);
            PowerManagerService.m800$$Nest$mgoToSleepInternal(PowerManagerService.this, PowerManagerService.DEFAULT_DISPLAY_GROUP_IDS, j, i, i2);
        }

        public final void goToSleepWithDisplayId(int i, long j, int i2, int i3) {
            IntArray wrap;
            Slog.d("PowerManagerService", "[api] goToSleepWithDisplayId" + PowerManagerUtil.callerInfoToString(false) + " for displayId = " + i + " eventTime = " + j);
            if (i == -1) {
                wrap = PowerManagerService.this.mDisplayManagerInternal.getDisplayGroupIds();
            } else {
                DisplayInfo displayInfo = PowerManagerService.this.mDisplayManagerInternal.getDisplayInfo(i);
                Preconditions.checkArgument(displayInfo != null, "display ID(%d) doesn't exist", new Object[]{Integer.valueOf(i)});
                int i4 = displayInfo.displayGroupId;
                if (i4 == -1) {
                    throw new IllegalArgumentException("invalid display group ID");
                }
                wrap = IntArray.wrap(new int[]{i4});
            }
            PowerManagerService.m800$$Nest$mgoToSleepInternal(PowerManagerService.this, wrap, j, i2, i3);
        }

        public final boolean isAmbientDisplayAvailable() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.mAmbientDisplayConfiguration.ambientDisplayAvailable();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isAmbientDisplaySuppressed() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isAmbientDisplaySuppressedForToken(String str) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Set set = PowerManagerService.this.mAmbientDisplaySuppressionController.mSuppressionTokens;
                Objects.requireNonNull(str);
                return set.contains(Pair.create(str, Integer.valueOf(callingUid)));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) {
            boolean z;
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_SUPPRESSION", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (isAmbientDisplayAvailable()) {
                    Set set = PowerManagerService.this.mAmbientDisplaySuppressionController.mSuppressionTokens;
                    Objects.requireNonNull(str);
                    if (set.contains(Pair.create(str, Integer.valueOf(i)))) {
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

        public final boolean isBatteryDischargePredictionPersonalized() {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (PowerManagerService.this.mEnhancedDischargeTimeLock) {
                    try {
                        PowerManagerService.this.mClock.getClass();
                        z = isEnhancedDischargePredictionValidLocked(SystemClock.elapsedRealtime()) && PowerManagerService.this.mEnhancedDischargePredictionIsPersonalized;
                    } finally {
                    }
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isBatterySaverSupported() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.mBatterySaverSupported;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDeviceIdleMode() {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    z = powerManagerService.mDeviceIdleMode;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDisplayInteractive(int i) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.m804$$Nest$misInteractiveInternal(PowerManagerService.this, i, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDozeAfterScreenOff() {
            boolean z;
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", new StringBuilder("[api] isDozeAfterScreenOff "), false);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    z = powerManagerService.mDozeAfterScreenOff;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isEnhancedDischargePredictionValidLocked(long j) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            long j2 = powerManagerService.mLastEnhancedDischargeTimeUpdatedElapsed;
            return j2 > 0 && j < powerManagerService.mEnhancedDischargeTimeElapsed && j - j2 < 1800000;
        }

        public final boolean isExemptFromLowPowerStandby() {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.mLowPowerStandbyController.isPackageExempt(callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isFeatureAllowedInLowPowerStandby(String str) {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LowPowerStandbyController lowPowerStandbyController = PowerManagerService.this.mLowPowerStandbyController;
                synchronized (lowPowerStandbyController.mLock) {
                    try {
                        z = true;
                        if (lowPowerStandbyController.mSupportedConfig) {
                            if (lowPowerStandbyController.isEnabled() && !lowPowerStandbyController.getPolicy().getAllowedFeatures().contains(str)) {
                                z = false;
                            }
                        }
                    } finally {
                    }
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isInteractive() {
            boolean isInteractive;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
                synchronized (powerManagerService.mLock) {
                    isInteractive = PowerManagerInternal.isInteractive(powerManagerService.getGlobalWakefulnessLocked());
                }
                return isInteractive;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isInteractiveForDisplay(int i) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.m803$$Nest$misInteractiveForDisplayInternal(PowerManagerService.this, i, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isLightDeviceIdleMode() {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    z = powerManagerService.mLightDeviceIdleMode;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isLowPowerStandbyEnabled() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.this.mLowPowerStandbyController.isEnabled();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isLowPowerStandbySupported() {
            boolean z;
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "isLowPowerStandbySupported");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LowPowerStandbyController lowPowerStandbyController = PowerManagerService.this.mLowPowerStandbyController;
                synchronized (lowPowerStandbyController.mLock) {
                    z = lowPowerStandbyController.mSupportedConfig;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isPowerSaveMode() {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mBatterySaverSupported) {
                    if (powerManagerService.mBatterySaverStateMachine.mBatterySaverController.isEnabled()) {
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

        public final boolean isReasonAllowedInLowPowerStandby(int i) {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LowPowerStandbyController lowPowerStandbyController = PowerManagerService.this.mLowPowerStandbyController;
                synchronized (lowPowerStandbyController.mLock) {
                    try {
                        z = true;
                        if (lowPowerStandbyController.isEnabled()) {
                            if ((lowPowerStandbyController.getPolicy().getAllowedReasons() & i) == 0) {
                                z = false;
                            }
                        }
                    } finally {
                    }
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isScreenBrightnessBoosted() {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    z = powerManagerService.mScreenBrightnessBoostInProgress;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isScreenCurtainEnabled() {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "isScreenCurtainEnabled: mSafeMode: " + PowerManagerService.this.mSafeMode);
                        return false;
                    }
                    ScreenCurtainController screenCurtainController = powerManagerService.mScreenCurtainController;
                    if (screenCurtainController == null) {
                        return false;
                    }
                    return screenCurtainController.mScreenCurtainEnabled;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isScreenCurtainEntryAvailable() {
            boolean z;
            boolean z2;
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "isScreenCurtainEntryAvailable: mSafeMode: " + PowerManagerService.this.mSafeMode);
                        return false;
                    }
                    ScreenCurtainController screenCurtainController = powerManagerService.mScreenCurtainController;
                    if (screenCurtainController == null) {
                        return false;
                    }
                    screenCurtainController.getClass();
                    try {
                        z = new SemGameManager().isForegroundGame();
                    } catch (Exception unused) {
                        z = false;
                    }
                    if (screenCurtainController.mTelephonyManager.getCallState() != 0) {
                        z2 = true;
                        boolean z3 = z && !z2;
                        StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("isScreenCurtainAvailableLocked: ", z3, ", game=", z, ", callActive=");
                        m.append(z2);
                        Slog.d("ScreenCurtainController", m.toString());
                        return z3;
                    }
                    z2 = false;
                    if (z) {
                    }
                    StringBuilder m2 = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("isScreenCurtainAvailableLocked: ", z3, ", game=", z, ", callActive=");
                    m2.append(z2);
                    Slog.d("ScreenCurtainController", m2.toString());
                    return z3;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isWakeLockLevelSupported(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PowerManagerService.m805$$Nest$misWakeLockLevelSupportedInternal(PowerManagerService.this, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void nap(long j) {
            PowerManagerService.this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            if (j > uptimeMillis) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Event time ", j, " cannot be newer than ");
                m.append(uptimeMillis);
                Slog.e("PowerManagerService", m.toString());
                throw new IllegalArgumentException("event time must not be in the future");
            }
            Slog.d("PowerManagerService", "[api] nap" + PowerManagerUtil.callerInfoToString(true) + " eventTime = " + j);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.napInternal(callingUid, j, false);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            this.mShellCommand.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void reboot(boolean z, String str, boolean z2) {
            PowerManagerService.this.mShutdownOrRebootCaller = PowerManagerService.m816$$Nest$smcallerInfoWithProcessName();
            StringBuilder sb = new StringBuilder();
            sb.append(" reason: " + str);
            sb.append(" confirm: " + z);
            sb.append(" wait: " + z2);
            sb.append(PowerManagerService.this.mShutdownOrRebootCaller);
            Slog.dk("[api] " + sb.toString());
            if (str == null) {
                str = PowerManagerService.this.mShutdownOrRebootCaller;
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            if ("recovery".equals(str) || "recovery-update".equals(str)) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
            }
            ShutdownCheckPoints.INSTANCE.recordCheckPointInternal(Binder.getCallingPid(), str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m813$$Nest$mshutdownOrRebootInternal(PowerManagerService.this, 1, z, str, z2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void rebootSafeMode(boolean z, boolean z2) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            ShutdownCheckPoints.INSTANCE.recordCheckPointInternal(Binder.getCallingPid(), "safemode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m813$$Nest$mshutdownOrRebootInternal(PowerManagerService.this, 2, z, "safemode", z2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseLowPowerStandbyPorts(IBinder iBinder) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SET_LOW_POWER_STANDBY_PORTS", "releaseLowPowerStandbyPorts");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.mLowPowerStandbyController.releaseStandbyPorts(iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseWakeLock(IBinder iBinder, int i) {
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m808$$Nest$mreleaseWakeLockInternal(PowerManagerService.this, iBinder, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseWakeLockAsync(IBinder iBinder, int i) {
            releaseWakeLock(iBinder, i);
        }

        public final void removeAdaptiveScreenOffTimeoutConfig(List list) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "removeAdaptiveScreenOffTimeoutConfig: mSafeMode: " + PowerManagerService.this.mSafeMode);
                    } else {
                        AdaptiveScreenOffTimeoutController adaptiveScreenOffTimeoutController = powerManagerService.mAdaptiveScreenOffTimeoutController;
                        if (adaptiveScreenOffTimeoutController != null) {
                            adaptiveScreenOffTimeoutController.removeAdaptiveScreenOffTimeoutConfigLocked(list);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean setAdaptivePowerSaveEnabled(boolean z) {
            boolean z2;
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setAdaptivePowerSaveEnabled");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                boolean z3 = false;
                if (powerManagerService.mBatterySaverSupported) {
                    BatterySaverStateMachine batterySaverStateMachine = powerManagerService.mBatterySaverStateMachine;
                    synchronized (batterySaverStateMachine.mLock) {
                        batterySaverStateMachine.mLastAdaptiveBatterySaverChangedExternallyElapsed = SystemClock.elapsedRealtime();
                        BatterySaverController batterySaverController = batterySaverStateMachine.mBatterySaverController;
                        boolean z4 = batterySaverController.mAdaptiveEnabledRaw;
                        if (z4 != z) {
                            if (z4 != z) {
                                PowerManager.invalidatePowerSaveModeCaches();
                                batterySaverController.mAdaptiveEnabledRaw = z;
                            }
                            boolean z5 = batterySaverController.mFullEnabledRaw;
                            BatterySaverPolicy batterySaverPolicy = batterySaverController.mBatterySaverPolicy;
                            if (z5 ? batterySaverPolicy.setPolicyLevel(2) : batterySaverController.mAdaptiveEnabledRaw ? batterySaverPolicy.setPolicyLevel(1) : batterySaverPolicy.setPolicyLevel(0)) {
                                batterySaverController.mHandler.postStateChanged(11, true);
                                z2 = true;
                            }
                        }
                        z2 = false;
                    }
                    if (z2) {
                        z3 = true;
                    }
                }
                return z3;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
            boolean z;
            boolean adaptivePolicyLocked;
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setAdaptivePowerSavePolicy");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mBatterySaverSupported) {
                    BatterySaverStateMachine batterySaverStateMachine = powerManagerService.mBatterySaverStateMachine;
                    synchronized (batterySaverStateMachine.mLock) {
                        batterySaverStateMachine.mLastAdaptiveBatterySaverChangedExternallyElapsed = SystemClock.elapsedRealtime();
                        adaptivePolicyLocked = batterySaverStateMachine.mBatterySaverController.setAdaptivePolicyLocked(batterySaverPolicyConfig);
                    }
                    if (adaptivePolicyLocked) {
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

        public final void setAttentionLight(boolean z, int i) {
            StringBuilder sb = new StringBuilder("[api] setAttentionLight: on: ");
            sb.append(z);
            sb.append(" color: ");
            sb.append(i);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    try {
                        if (powerManagerService.mSystemReady) {
                            LightsService.LightImpl lightImpl = powerManagerService.mAttentionLight;
                            if (lightImpl != null) {
                                lightImpl.setFlashing(i, 2, z ? 3 : 0, 0);
                            }
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setAutoBrightnessLimit(int i, int i2, boolean z) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", null);
            if (i < -1 || i2 < -1 || (i > i2 && i2 >= 0)) {
                StringBuilder sb = new StringBuilder("[api] setAutoBrightnessLimit : invalid Limits : ");
                sb.append(i);
                sb.append(", ");
                sb.append(i2);
                DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
                return;
            }
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService.this.mLastAutoBrightnessLimitTime = SystemClock.uptimeMillis();
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mAutoBrightnessLowerLimit != i || powerManagerService.mAutoBrightnessUpperLimit != i2) {
                    Slog.d("PowerManagerService", "[api] setAutoBrightnessLimit : lowerLimit " + i + "   upperLimit : " + i2 + "   slowChange : " + z + PowerManagerUtil.callerInfoToString(true));
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    powerManagerService2.mAutoBrightnessLowerLimit = i;
                    powerManagerService2.mAutoBrightnessUpperLimit = i2;
                    powerManagerService2.mAutoBrightnessLimitLastCaller = PowerManagerUtil.callerInfoToString(false);
                    PowerManagerService powerManagerService3 = PowerManagerService.this;
                    powerManagerService3.mForceSlowChange = z;
                    powerManagerService3.mDirty = powerManagerService3.mDirty | 32;
                    powerManagerService3.updatePowerStateLocked();
                }
            }
        }

        public final void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z) {
            PowerManagerService powerManagerService;
            PowerManagerService.this.mClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.BATTERY_PREDICTION") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setBatteryDischargePrediction");
            }
            long millis = parcelDuration.getDuration().toMillis();
            Preconditions.checkArgumentPositive(millis, "Given time remaining is not positive: " + millis);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (PowerManagerService.this.mLock) {
                    powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mIsPowered) {
                        throw new IllegalStateException("Discharge prediction can't be set while the device is charging");
                    }
                }
                synchronized (powerManagerService.mEnhancedDischargeTimeLock) {
                    long j = PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed;
                    if (j > elapsedRealtime) {
                        return;
                    }
                    long max = Math.max(0L, 60000 - (elapsedRealtime - j));
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    powerManagerService2.mEnhancedDischargeTimeElapsed = millis + elapsedRealtime;
                    powerManagerService2.mEnhancedDischargePredictionIsPersonalized = z;
                    powerManagerService2.mLastEnhancedDischargeTimeUpdatedElapsed = elapsedRealtime;
                    powerManagerService2.mNotifier.mHandler.sendEmptyMessageDelayed(4, max);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setCoverType(int i) {
            Slog.d("PowerManagerService", "[api] setCoverType: " + i);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService.this.mCoverType = i;
                long uptimeMillis = SystemClock.uptimeMillis();
                PowerManagerService powerManagerService = PowerManagerService.this;
                powerManagerService.mlastSetCoverTypeTime = uptimeMillis;
                powerManagerService.mlastUpdateCoverTypeReason = "setCoverType = " + i;
                PowerManagerService powerManagerService2 = PowerManagerService.this;
                powerManagerService2.mDirty = powerManagerService2.mDirty | 32;
                powerManagerService2.updatePowerStateLocked();
            }
        }

        public final void setDozeAfterScreenOff(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setDozeAfterScreenOff: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                synchronized (powerManagerService.mLock) {
                    powerManagerService.mDozeAfterScreenOff = z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setDynamicPowerSaveHint(boolean z, int i) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.POWER_SAVER", "updateDynamicPowerSavings");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ContentResolver contentResolver = PowerManagerService.this.mContext.getContentResolver();
                boolean putInt = Settings.Global.putInt(contentResolver, "dynamic_power_savings_disable_threshold", i);
                if (putInt) {
                    putInt &= Settings.Global.putInt(contentResolver, "dynamic_power_savings_enabled", z ? 1 : 0);
                }
                return putInt;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setEarlyWakeUp(boolean z) {
            Slog.d("PowerManagerService", "[api] setEarlyWakeUp: enable: " + z);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    powerManagerService.mLastWakeUpReason = 0;
                    if (powerManagerService.isWakeUpPreventionNeededLocked()) {
                        return;
                    }
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    powerManagerService2.mDirty |= 32;
                    powerManagerService2.mEarlyWakeUp = z;
                    powerManagerService2.updatePowerStateLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setFreezingScreenBrightness(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setFreezingScreenBrightness: freezing: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (z != powerManagerService.mFreezingScreenBrightness) {
                    powerManagerService.mFreezingScreenBrightness = z;
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                }
            }
        }

        public final boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
            boolean z;
            boolean fullPolicyLocked;
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setFullPowerSavePolicy");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mBatterySaverSupported) {
                    BatterySaverStateMachine batterySaverStateMachine = powerManagerService.mBatterySaverStateMachine;
                    synchronized (batterySaverStateMachine.mLock) {
                        fullPolicyLocked = batterySaverStateMachine.mBatterySaverController.setFullPolicyLocked(batterySaverPolicyConfig);
                    }
                    if (fullPolicyLocked) {
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

        public final void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", null);
            int max = Math.max(PowerManagerService.this.mScreenExtendedBrightnessMaximum, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            if (i != -1 && (i < 0 || i > max)) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "setHdrBrightnessLimit : invalid upperLimit"));
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m810$$Nest$msetHdrBrightnessLimitInternal(PowerManagerService.this, iBinder, i, i2, callingUid, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setLCDFlashMode(boolean z, IBinder iBinder) {
            StringBuilder sb = new StringBuilder("[api] setLCDFlashMode: enable: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            PowerManagerService.this.setLCDFlashModeInternal(iBinder, z);
        }

        public final void setLowPowerStandbyActiveDuringMaintenance(boolean z) {
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyActiveDuringMaintenance");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.mLowPowerStandbyController.setActiveDuringMaintenance(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setLowPowerStandbyEnabled(boolean z) {
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyEnabled");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.mLowPowerStandbyController.setEnabled(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setLowPowerStandbyPolicy(IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyPolicy");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.mLowPowerStandbyController.setPolicy(PowerManager.LowPowerStandbyPolicy.fromParcelable(lowPowerStandbyPolicy));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setMasterBrightnessLimit(int i, int i2, int i3) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", null);
            int max = Math.max(PowerManagerService.this.mScreenExtendedBrightnessMaximum, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            if (i != -1 && (i < 0 || i > max)) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "setMasterBrightnessLimit : invalid lowerLimit : "));
            }
            if (i2 != -1 && (i2 < 0 || i2 > max)) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "setMasterBrightnessLimit : invalid upperLimit"));
            }
            if (i != -1 && i2 != -1 && i > i2) {
                throw new IllegalArgumentException(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setMasterBrightnessLimit : lowerLimit ", " > upperLimit "));
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    powerManagerService.mMasterBrightnessLowerLimit = i;
                    powerManagerService.mMasterBrightnessUpperLimit = i2;
                    powerManagerService.mMasterBrightnessLimitPeriod = i3;
                    powerManagerService.mMasterBrightnessLimitLastCaller = PowerManagerUtil.callerInfoToString(false);
                    Slog.d("PowerManagerService", "[api] setMasterBrightnessLimit : lowerLimit : " + i + "  upperLimit : " + i2 + "  mMasterBrightnessLimitPeriod : " + powerManagerService.mMasterBrightnessLimitPeriod + PowerManagerUtil.callerInfoToString(false));
                    if (powerManagerService.mMasterBrightnessLimitPeriod > 0) {
                        powerManagerService.mMasterBrightnessLimitRunning = true;
                        if (i2 == -1) {
                            i2 = powerManagerService.mScreenExtendedBrightnessMaximum;
                        }
                        powerManagerService.mTargetBrightnessForLimit = i2;
                        powerManagerService.mLastRequestedLimitationOfBrightness = powerManagerService.getStartingLimitationOfBrightness(i2, powerManagerService.mLastRequestedLimitationOfBrightness);
                        powerManagerService.mHandler.removeCallbacks(powerManagerService.mBrightnessLimitRunnable);
                        Handler handler = powerManagerService.mHandler;
                        AnonymousClass6 anonymousClass6 = powerManagerService.mBrightnessLimitRunnable;
                        powerManagerService.mClock.getClass();
                        handler.postAtTime(anonymousClass6, SystemClock.uptimeMillis());
                    } else {
                        powerManagerService.mMasterBrightnessLimitRunning = false;
                        powerManagerService.mLastRequestedLimitationOfBrightness = i2;
                        powerManagerService.mHandler.removeCallbacks(powerManagerService.mBrightnessLimitRunnable);
                        powerManagerService.mDirty |= 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setPowerBoost(int i, int i2) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (powerManagerService.mSystemReady) {
                powerManagerService.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                Slog.d("PowerManagerService", "[api] setPowerBoost(B) boost:" + i + ", durationMs:" + i2 + ", caller" + PowerManagerUtil.callerInfoToString(true));
                PowerManagerService.this.mNativeWrapper.getClass();
                PowerManagerService.nativeSetPowerBoost(i, i2);
            }
        }

        public final void setPowerMode(int i, boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (powerManagerService.mSystemReady) {
                powerManagerService.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                PowerManagerService.this.setPowerModeInternal(i, z);
            }
        }

        public final boolean setPowerModeChecked(int i, boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (!powerManagerService.mSystemReady) {
                return false;
            }
            powerManagerService.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            return PowerManagerService.this.setPowerModeInternal(i, z);
        }

        public final boolean setPowerSaveModeEnabled(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setPowerSaveModeEnabled: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m811$$Nest$msetLowPowerModeInternal(PowerManagerService.this, z);
                return true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setProximityDebounceTime(IBinder iBinder, int i, int i2) {
            PackageManager packageManager;
            Slog.d("PowerManagerService", "[api] setProximityDebounceTime: Positive = " + i + ", Negative = " + i2);
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            if (CoreRune.SYSFW_APP_SPEG && (packageManager = PowerManagerService.this.mContext.getPackageManager()) != null && packageManager.isSpeg(Binder.getCallingUid())) {
                Slog.d("SPEG", "App " + Binder.getCallingUid() + " tries to setProximityDebounceTime. Ignore");
                return;
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                WakeLock wakeLock = (WakeLock) powerManagerService.mWakeLocks.get(powerManagerService.findWakeLockIndexLocked(iBinder));
                wakeLock.mProximityPositiveDebounce = i;
                wakeLock.mProximityNegativeDebounce = i2;
                powerManagerService.mDirty |= 1;
                powerManagerService.updatePowerStateLocked();
            }
        }

        public final void setScreenBrightnessScaleFactor(float f, IBinder iBinder) {
            StringBuilder sb = new StringBuilder("[api] setScreenBrightnessScaleFactor: scaleFactor: ");
            sb.append(f);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            if (f != -1.0f && f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                throw new IllegalArgumentException("setScreenBrightnessScaleFactor: scaleFactor:" + f);
            }
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (f != powerManagerService.mScreenBrightnessScaleFactor) {
                    powerManagerService.mScreenBrightnessScaleFactor = f;
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                }
            }
        }

        public final void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("[api] setScreenCurtainEnabled: enable: ", " state: ", z);
            m.append(Display.stateToString(i));
            m.append(" token: ");
            m.append(iBinder);
            m.append(PowerManagerUtil.callerInfoToString(true));
            Slog.d("PowerManagerService", m.toString());
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                setScreenCurtainEnabledInternal(callingUid, i, iBinder, z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setScreenCurtainEnabledInternal(int i, int i2, IBinder iBinder, boolean z) {
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    if (powerManagerService.mSafeMode) {
                        Slog.d("PowerManagerService", "setScreenCurtainEnabledInternal: mSafeMode: " + PowerManagerService.this.mSafeMode);
                    } else {
                        ScreenCurtainController screenCurtainController = powerManagerService.mScreenCurtainController;
                        if (screenCurtainController != null) {
                            screenCurtainController.setScreenCurtainEnabledLocked(i, i2, iBinder, z);
                            PowerManagerService powerManagerService2 = PowerManagerService.this;
                            powerManagerService2.mDirty |= 32;
                            powerManagerService2.updatePowerStateLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setStayOnSetting(int i) {
            Slog.d("PowerManagerService", "[api] setStayOnSetting: " + i + PowerManagerUtil.callerInfoToString(true));
            int callingUid = Binder.getCallingUid();
            if (callingUid != 0) {
                Context context = PowerManagerService.this.mContext;
                if (!Settings.checkAndNoteWriteSettingsOperation(context, callingUid, Settings.getPackageNameForUid(context, callingUid), null, true)) {
                    Slog.d("PowerManagerService", "[api] setStayOnSetting: checkAndNoteWriteSettingsOperation() false");
                    return;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Settings.Global.putInt(PowerManagerService.this.mContext.getContentResolver(), "stay_on_while_plugged_in", i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void shutdown(boolean z, String str, boolean z2) {
            PowerManagerService.this.mShutdownOrRebootCaller = PowerManagerService.m816$$Nest$smcallerInfoWithProcessName();
            StringBuilder sb = new StringBuilder(" shutdown");
            sb.append(" reason: " + str);
            sb.append(" confirm: " + z);
            sb.append(" wait: " + z2);
            sb.append(PowerManagerService.this.mShutdownOrRebootCaller);
            Slog.dk("[api] " + sb.toString());
            if (str == null) {
                str = PowerManagerService.this.mShutdownOrRebootCaller;
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            ShutdownCheckPoints.INSTANCE.recordCheckPointInternal(Binder.getCallingPid(), str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m813$$Nest$mshutdownOrRebootInternal(PowerManagerService.this, 0, z, str, z2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void suppressAmbientDisplay(String str, boolean z) {
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_DREAM_STATE", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.this.mAmbientDisplaySuppressionController.suppress(callingUid, str, z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void switchForceLcdBacklightOffState() {
            Slog.d("PowerManagerService", "[api] switchForceLcdBacklightOffState()");
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = ((PowerGroup) PowerManagerService.this.mPowerGroups.get(0)).mDisplayPowerRequest;
            synchronized (PowerManagerService.this.mLock) {
                try {
                    if (displayPowerRequest.forceLcdBacklightOffEnabled) {
                        displayPowerRequest.forceLcdBacklightOffEnabled = false;
                    } else {
                        displayPowerRequest.forceLcdBacklightOffEnabled = true;
                    }
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateCoverState(boolean z) {
            Slog.d("PowerManagerService", "[api] updateCoverState: closed: " + z);
            synchronized (PowerManagerService.this.mLock) {
                try {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    powerManagerService.mIsCoverClosed = z;
                    if (!powerManagerService.mCoverAuthReady) {
                        powerManagerService.mCoverAuthReady = true;
                        powerManagerService.mHandler.removeCallbacks(powerManagerService.mCoverAuthReadyRunnable);
                        PowerManagerService.this.mCoverAuthReadyRunnable = null;
                    }
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    powerManagerService2.mDirty |= 32;
                    powerManagerService2.mClock.getClass();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    PowerManagerService powerManagerService3 = PowerManagerService.this;
                    powerManagerService3.mlastUpdateCoverStateTime = uptimeMillis;
                    powerManagerService3.mlastUpdateCoverStateReason = "updateCoverState called on closed = " + z;
                    PowerManagerService.this.userActivityNoUpdateLocked(0, 0, 1000, uptimeMillis);
                    PowerManagerService.this.updatePowerStateLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback) {
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m814$$Nest$mupdateWakeLockCallbackInternal(PowerManagerService.this, iBinder, iWakeLockCallback, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void updateWakeLockUids(IBinder iBinder, int[] iArr) {
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

        public final void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) {
            updateWakeLockUids(iBinder, iArr);
        }

        public final void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) {
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            if (workSource == null || workSource.isEmpty()) {
                workSource = null;
            } else {
                PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", null);
            }
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManagerService.m815$$Nest$mupdateWakeLockWorkSourceInternal(PowerManagerService.this, iBinder, workSource, str, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void userActivity(int i, long j, int i2, int i3) {
            if (PowerManagerService.this.mIsUserActivityInvoked.compareAndSet(false, true)) {
                StringBuilder sb = new StringBuilder("[api] userActivity : ");
                sb.append(PowerManager.userActivityEventToString(i2));
                sb.append(i3 == 0 ? "" : ",");
                sb.append(PowerManager.userActivityFlagsToString(i3));
                sb.append(PowerManagerUtil.callerInfoToString(true));
                sb.append(" displayId=");
                sb.append(i);
                sb.append(" eventTime=");
                sb.append(j);
                Slog.d("PowerManagerService", sb.toString());
                PowerManagerService powerManagerService = PowerManagerService.this;
                Handler handler = powerManagerService.mHandlerPmsMisc;
                PowerManagerService$LocalService$$ExternalSyntheticLambda0 powerManagerService$LocalService$$ExternalSyntheticLambda0 = new PowerManagerService$LocalService$$ExternalSyntheticLambda0(1, this);
                powerManagerService.mClock.getClass();
                handler.postAtTime(powerManagerService$LocalService$$ExternalSyntheticLambda0, SystemClock.uptimeMillis() + 1000);
            }
            PowerManagerService.this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            if (PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0 && PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.USER_ACTIVITY") != 0) {
                synchronized (PowerManagerService.this.mLock) {
                    try {
                        PowerManagerService powerManagerService2 = PowerManagerService.this;
                        if (uptimeMillis >= powerManagerService2.mLastWarningAboutUserActivityPermission + 300000) {
                            powerManagerService2.mLastWarningAboutUserActivityPermission = uptimeMillis;
                            Slog.w("PowerManagerService", "Ignoring call to PowerManager.userActivity() because the caller does not have DEVICE_POWER or USER_ACTIVITY permission.  Please fix your app!   pid=" + Binder.getCallingPid() + " uid=" + Binder.getCallingUid());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            }
            if (j <= uptimeMillis) {
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    PowerManagerService.this.userActivityInternal(i, i2, i3, callingUid, j);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Event cannot be newer than the current time (now=", uptimeMillis, ", eventTime=");
            m.append(j);
            m.append(", displayId=");
            m.append(i);
            m.append(", event=");
            m.append(PowerManager.userActivityEventToString(i2));
            m.append(", flags=");
            m.append(i3);
            m.append(")");
            String sb2 = m.toString();
            int i4 = Slog.$r8$clinit;
            android.util.Slog.wtf("PowerManagerService", sb2);
        }

        public final void wakeUp(long j, int i, String str, String str2) {
            PowerManagerService.this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            if (j > uptimeMillis) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Event time ", j, " cannot be newer than ");
                m.append(uptimeMillis);
                Slog.e("PowerManagerService", m.toString());
                throw new IllegalArgumentException("event time must not be in the future");
            }
            Slog.d("PowerManagerService", "[api] wakeUp" + PowerManagerUtil.callerInfoToString(false) + " eventTime = " + j);
            PowerManagerService powerManagerService = PowerManagerService.this;
            StringBuilder sb = new StringBuilder(" wakeUp: ");
            sb.append(i == 0 ? PowerManagerUtil.callerInfoToString(true) : PowerManagerUtil.callerInfoToString(false));
            powerManagerService.mScreenOnReason = sb.toString();
            PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (PowerManagerService.this.mLock) {
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    if (powerManagerService2.mBootCompleted || !PowerManagerService.sQuiescent) {
                        powerManagerService2.wakePowerGroupLocked((PowerGroup) powerManagerService2.mPowerGroups.get(0), j, i, str, callingUid, str2, callingUid, false);
                    } else {
                        powerManagerService2.mDirty |= 4096;
                        powerManagerService2.updatePowerStateLocked();
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Clock {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Constants extends ContentObserver {
        public boolean NO_CACHED_WAKE_LOCKS;
        public final KeyValueListParser mParser;
        public ContentResolver mResolver;

        public Constants(Handler handler) {
            super(handler);
            this.NO_CACHED_WAKE_LOCKS = true;
            this.mParser = new KeyValueListParser(',');
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("  Settings power_manager_constants:");
            printWriter.print("    ");
            printWriter.print("no_cached_wake_locks");
            printWriter.print("=");
            printWriter.println(this.NO_CACHED_WAKE_LOCKS);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            updateConstants();
        }

        public final void updateConstants() {
            synchronized (PowerManagerService.this.mLock) {
                try {
                    this.mParser.setString(Settings.Global.getString(this.mResolver, "power_manager_constants"));
                } catch (IllegalArgumentException e) {
                    Slog.e("PowerManagerService", "Bad alarm manager settings", e);
                }
                this.NO_CACHED_WAKE_LOCKS = this.mParser.getBoolean("no_cached_wake_locks", true);
                Slog.d("PowerManagerService", "[api] updateConstants: NO_CACHED_WAKE_LOCKS: " + this.NO_CACHED_WAKE_LOCKS);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStateListener implements DeviceStateManager.DeviceStateCallback {
        public int mDeviceState = -1;

        public DeviceStateListener() {
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            int i;
            int identifier = deviceState.getIdentifier();
            if (this.mDeviceState != identifier) {
                this.mDeviceState = identifier;
                synchronized (PowerManagerService.this.mLock) {
                    try {
                        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                            PowerManagerService powerManagerService = PowerManagerService.this;
                            int i2 = powerManagerService.mDualScreenPolicy;
                            if (identifier == 0 || identifier == 1) {
                                i = 1;
                            } else if (identifier == 2 || identifier == 3) {
                                i = 0;
                            } else {
                                i = -1;
                                if (identifier != 4) {
                                }
                            }
                            powerManagerService.mDualScreenPolicy = i;
                            if (i != i2) {
                                Slog.d("PowerManagerService", "onStateChanged: mDualScreenPolicy=" + PowerManagerService.this.mDualScreenPolicy);
                                PowerManagerService.this.mForceSetHalInteractiveMode = true;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                PowerManagerService powerManagerService2 = PowerManagerService.this;
                powerManagerService2.mClock.getClass();
                powerManagerService2.userActivityInternal(0, 6, 0, 1000, SystemClock.uptimeMillis());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayGroupPowerChangeListener implements DisplayManagerInternal.DisplayGroupListener {
        public DisplayGroupPowerChangeListener() {
        }

        public final void onDisplayGroupAdded(int i) {
            synchronized (PowerManagerService.this.mLock) {
                try {
                    if (PowerManagerService.this.mPowerGroups.contains(i)) {
                        Slog.e("PowerManagerService", "Tried to add already existing group:" + i);
                        return;
                    }
                    Slog.d("PowerManagerService", "onDisplayGroupAdded: groupId=" + i);
                    boolean z = i == 0;
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    AnonymousClass1 anonymousClass1 = powerManagerService.mPowerGroupWakefulnessChangeListener;
                    Notifier notifier = powerManagerService.mNotifier;
                    DisplayManagerInternal displayManagerInternal = powerManagerService.mDisplayManagerInternal;
                    powerManagerService.mClock.getClass();
                    PowerGroup powerGroup = new PowerGroup(i, anonymousClass1, notifier, displayManagerInternal, z, SystemClock.uptimeMillis());
                    PowerManagerService.this.mPowerGroups.append(i, powerGroup);
                    PowerManagerService.this.onPowerGroupEventLocked(0, powerGroup);
                    PowerManagerService.this.mHandlerPmsMisc.post(new PowerManagerService$DisplayGroupPowerChangeListener$$ExternalSyntheticLambda0(this, i, 1));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onDisplayGroupChanged(int i) {
            synchronized (PowerManagerService.this.mLock) {
                try {
                    if (!PowerManagerService.this.mPowerGroups.contains(i)) {
                        Slog.e("PowerManagerService", "Tried to change non-existent group: " + i);
                    } else {
                        Slog.d("PowerManagerService", "onDisplayGroupChanged: groupId=" + i);
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        powerManagerService.onPowerGroupEventLocked(2, (PowerGroup) powerManagerService.mPowerGroups.get(i));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onDisplayGroupRemoved(int i) {
            synchronized (PowerManagerService.this.mLock) {
                try {
                    if (i == 0) {
                        int i2 = Slog.$r8$clinit;
                        android.util.Slog.wtf("PowerManagerService", "Tried to remove default display group: " + i);
                        return;
                    }
                    if (!PowerManagerService.this.mPowerGroups.contains(i)) {
                        Slog.e("PowerManagerService", "Tried to remove non-existent group:" + i);
                    } else {
                        Slog.d("PowerManagerService", "onDisplayGroupRemoved: groupId=" + i);
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        powerManagerService.onPowerGroupEventLocked(1, (PowerGroup) powerManagerService.mPowerGroups.get(i));
                        PowerManagerService.this.mHandlerPmsMisc.post(new PowerManagerService$DisplayGroupPowerChangeListener$$ExternalSyntheticLambda0(this, i, 0));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DockReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PowerManagerService this$0;

        public /* synthetic */ DockReceiver(PowerManagerService powerManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = powerManagerService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mLock) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        Slog.d("PowerManagerService", "[api] DockReceiver: onReceive: DockState: " + this.this$0.mDockState + " -> " + intExtra);
                        PowerManagerService powerManagerService = this.this$0;
                        if (powerManagerService.mDockState != intExtra) {
                            powerManagerService.mDockState = intExtra;
                            powerManagerService.mDirty |= 1024;
                            powerManagerService.updatePowerStateLocked();
                        }
                    }
                    return;
                default:
                    Slog.d("PowerManagerService", "[api] DreamReceiver: onReceive: " + intent.getAction());
                    synchronized (this.this$0.mLock) {
                        this.this$0.scheduleSandmanLocked();
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DreamManagerStateListener implements DreamManagerInternal.DreamManagerStateListener {
        public DreamManagerStateListener() {
        }

        public final void onKeepDreamingWhenUnpluggingChanged(boolean z) {
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService.this.mKeepDreamingWhenUnplugging = z;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ForegroundProfileObserver extends SynchronousUserSwitchObserver {
        public ForegroundProfileObserver() {
        }

        public final void onForegroundProfileSwitch(int i) {
            Slog.d("PowerManagerService", "[api] onForegroundProfileSwitch: " + i);
            PowerManagerService.this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                powerManagerService.mForegroundProfile = i;
                powerManagerService.maybeUpdateForegroundProfileLastActivityLocked(uptimeMillis);
            }
        }

        public final void onUserSwitching(int i) {
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService.this.mUserId = i;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HdrBrightnessLimitLock implements IBinder.DeathRecipient {
        public int mBrightnessLimitPeriod;
        public final IBinder mLock;
        public final int mPid;
        public final int mUid;
        public int mUpperLimit;

        public HdrBrightnessLimitLock(IBinder iBinder, int i, int i2, int i3, int i4) {
            this.mLock = iBinder;
            this.mUpperLimit = i;
            this.mBrightnessLimitPeriod = i2;
            this.mUid = i3;
            this.mPid = i4;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException unused) {
                Slog.w("PowerManagerService", "Failed to link HdrBrightnessLimitLock.mLock");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                this.mLock.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
                Slog.w("PowerManagerService", "Failed to unlink HdrBrightnessLimitLock.mLock");
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    powerManagerService.mHdrBrightnessLimitLocks.remove(this);
                    powerManagerService.calculateHdrBrightnessLimitLocked(-1, 200);
                    if (powerManagerService.mHdrBrightnessLimitPeriod > 0) {
                        int i = powerManagerService.mHdrBrightnessUpperLimit;
                        if (i == -1) {
                            i = powerManagerService.mScreenExtendedBrightnessMaximum;
                        }
                        powerManagerService.mTargetBrightnessForHdrLimit = i;
                        int startingLimitationOfBrightness = powerManagerService.getStartingLimitationOfBrightness(i, powerManagerService.mLastRequestedLimitationOfHdrBrightness);
                        powerManagerService.mLastRequestedLimitationOfHdrBrightness = startingLimitationOfBrightness;
                        int i2 = powerManagerService.mTargetBrightnessForHdrLimit;
                        if (i2 == startingLimitationOfBrightness) {
                            powerManagerService.mHdrBrightnessLimitRunning = false;
                            powerManagerService.mHdrBrightnessUpperLimit = i2;
                            powerManagerService.mHandler.removeCallbacks(powerManagerService.mHdrBrightnessLimitRunnable);
                            powerManagerService.mDirty |= 32;
                            powerManagerService.updatePowerStateLocked();
                        } else {
                            powerManagerService.mHdrBrightnessLimitRunning = true;
                            powerManagerService.mHandler.removeCallbacks(powerManagerService.mHdrBrightnessLimitRunnable);
                            Handler handler = powerManagerService.mHandler;
                            AnonymousClass6 anonymousClass6 = powerManagerService.mHdrBrightnessLimitRunnable;
                            powerManagerService.mClock.getClass();
                            handler.postAtTime(anonymousClass6, SystemClock.uptimeMillis());
                        }
                    } else {
                        powerManagerService.mHdrBrightnessLimitRunning = false;
                        powerManagerService.mLastRequestedLimitationOfHdrBrightness = powerManagerService.mHdrBrightnessUpperLimit;
                        powerManagerService.mHandler.removeCallbacks(powerManagerService.mHdrBrightnessLimitRunnable);
                        powerManagerService.mDirty |= 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String toString() {
            return " mUpperLimit: " + this.mUpperLimit + " mBrightnessLimitPeriod: " + this.mBrightnessLimitPeriod + " mUid: " + this.mUid + " mPid: " + this.mPid;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.power.PowerManagerService$Injector$1, reason: invalid class name */
        public final class AnonymousClass1 implements SystemPropertiesWrapper, Clock, PowerPropertiesWrapper {
            @Override // com.android.server.power.SystemPropertiesWrapper
            public String get(String str, String str2) {
                return SystemProperties.get(str, str2);
            }
        }

        public static SuspendBlockerImpl createSuspendBlocker(PowerManagerService powerManagerService, String str) {
            Objects.requireNonNull(powerManagerService);
            SuspendBlockerImpl suspendBlockerImpl = powerManagerService.new SuspendBlockerImpl(str);
            powerManagerService.mSuspendBlockers.add(suspendBlockerImpl);
            return suspendBlockerImpl;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LCDFlashModeLock implements IBinder.DeathRecipient {
        public final IBinder binder;

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
        public final void binderDied() {
            synchronized (PowerManagerService.this.mLock) {
                Slog.d("PowerManagerService", "LCDFlashModeLock : binderDied");
                PowerManagerService.this.setLCDFlashModeInternal(this.binder, false);
                PowerManagerService.this.mLCDFlashModeLock = null;
                Slog.d("PowerManagerService", "mLCDFlashModeLock set to null");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class LocalService extends PowerManagerInternal {
        public LocalService() {
        }

        public final void disableWakeUpPrevention(IBinder iBinder, boolean z) {
            StringBuilder sb = new StringBuilder("[api] disableWakeUpPrevention: needWakeUp: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService.m797$$Nest$mdisableWakeUpPreventionInternal(PowerManagerService.this, iBinder, z);
        }

        public final void enableWakeUpPrevention(IBinder iBinder) {
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", new StringBuilder("[api] enableWakeUpPrevention"), false);
            if (iBinder == null) {
                throw new IllegalArgumentException("lock must not be null");
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    int size = powerManagerService.mWakeUpPreventionLocks.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            i = -1;
                            break;
                        } else if (((WakeUpPreventionLock) powerManagerService.mWakeUpPreventionLocks.get(i)).mBinder == iBinder) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (i != -1) {
                        Slog.d("PowerManagerService", "enableWakeUpPreventionInternal: sameRequest");
                        return;
                    }
                    powerManagerService.mClock.getClass();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    powerManagerService.mWakeUpPreventionLocks.add(powerManagerService.new WakeUpPreventionLock(iBinder, uptimeMillis));
                    powerManagerService.mScreenOffReason = " wakeUpPrevention enabled";
                    for (int i2 = 0; i2 < powerManagerService.mPowerGroups.size(); i2++) {
                        powerManagerService.sleepPowerGroupLocked((PowerGroup) powerManagerService.mPowerGroups.valueAt(i2), uptimeMillis, 26, 1000);
                    }
                } finally {
                }
            }
        }

        public final void finishUidChanges() {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    powerManagerService.mUidsChanging = false;
                    if (powerManagerService.mUidsChanged) {
                        powerManagerService.updateWakeLockDisabledStatesLocked();
                        powerManagerService.mUidsChanged = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final PowerManager.SleepData getLastGoToSleep() {
            PowerManager.SleepData sleepData;
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                sleepData = new PowerManager.SleepData(powerManagerService.mLastGlobalSleepTime, powerManagerService.mLastGlobalSleepReason);
            }
            return sleepData;
        }

        public final int getLastUserActivityState() {
            int i;
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                i = powerManagerService.mLastUserActivityStateListenerState;
            }
            return i;
        }

        public final PowerManager.WakeData getLastWakeup() {
            PowerManager.WakeData wakeData;
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                wakeData = new PowerManager.WakeData(powerManagerService.mLastGlobalWakeTime, powerManagerService.mLastGlobalWakeReason, powerManagerService.mLastGlobalWakeTimeRealtime - powerManagerService.mLastGlobalSleepTimeRealtime);
            }
            return wakeData;
        }

        public final PowerSaveState getLowPowerState(int i) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            return powerManagerService.mBatterySaverSupported ? powerManagerService.mBatterySaverStateMachine.mBatterySaverController.mBatterySaverPolicy.getBatterySaverPolicy(i) : new PowerSaveState.Builder().build();
        }

        public final boolean interceptPowerKeyDown(KeyEvent keyEvent) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (!powerManagerService.mProximityPositive || powerManagerService.mInterceptedPowerKeyForProximity) {
                        return false;
                    }
                    powerManagerService.mDisplayManagerInternal.ignoreProximitySensorUntilChanged();
                    powerManagerService.mInterceptedPowerKeyForProximity = true;
                    return true;
                } finally {
                }
            }
        }

        public final boolean isAmbientDisplaySuppressed() {
            return PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed();
        }

        public final boolean isInternalDisplayOff() {
            boolean z;
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                z = !PowerManagerInternal.isInteractive(((PowerGroup) powerManagerService.mPowerGroups.get(0)).mWakefulness);
            }
            return z;
        }

        public final boolean isProximityPositive() {
            boolean z;
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                z = powerManagerService.mProximityPositive;
            }
            return z;
        }

        public final void nap(long j, boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
            powerManagerService.napInternal(1000, j, z);
        }

        public final void registerLowPowerModeObserver(PowerManagerInternal.LowPowerModeListener lowPowerModeListener) {
            StringBuilder sb = new StringBuilder("[api] registerLowPowerModeObserver: ");
            sb.append(lowPowerModeListener);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (!powerManagerService.mBatterySaverSupported) {
                Slog.w("PowerManagerService", "Battery saver is not supported, no low power mode observer registered");
                return;
            }
            BatterySaverController batterySaverController = powerManagerService.mBatterySaverStateMachine.mBatterySaverController;
            synchronized (batterySaverController.mLock) {
                batterySaverController.mListeners.add(lowPowerModeListener);
            }
        }

        public final void registerUserActivityStateListener(PowerManagerInternal.UserActivityStateListener userActivityStateListener) {
            Slog.d("PowerManagerService", "registerUserActivityStateListener: " + userActivityStateListener);
            if (userActivityStateListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                powerManagerService.mUserActivityStateListenerListeners.add(userActivityStateListener);
            }
        }

        public final boolean setDeviceIdleMode(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setDeviceIdleMode: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mDeviceIdleMode == z) {
                        return false;
                    }
                    powerManagerService.mDeviceIdleMode = z;
                    powerManagerService.updateWakeLockDisabledStatesLocked();
                    powerManagerService.setPowerModeInternal(8, powerManagerService.mDeviceIdleMode || powerManagerService.mLightDeviceIdleMode);
                    if (z) {
                        EventLog.writeEvent(34004, "power");
                    } else {
                        EventLog.writeEvent(34007, "power");
                    }
                    return true;
                } finally {
                }
            }
        }

        public final void setDeviceIdleTempWhitelist(int[] iArr) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    powerManagerService.mDeviceIdleTempWhitelist = iArr;
                    if (powerManagerService.doesIdleStateBlockWakeLocksLocked()) {
                        powerManagerService.updateWakeLockDisabledStatesLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setDeviceIdleWhitelist(int[] iArr) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    powerManagerService.mDeviceIdleWhitelist = iArr;
                    if (powerManagerService.doesIdleStateBlockWakeLocksLocked()) {
                        powerManagerService.updateWakeLockDisabledStatesLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setDozeOverrideFromDreamManager(int i, int i2, int i3) {
            Slog.d("PowerManagerService", "[api] setDozeOverrideFromDreamManager: screenState: " + Display.stateToString(i) + " screenBrightness: " + i3);
            if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 6) {
                i = 0;
            }
            if (i3 < -1 || i3 > 255) {
                i3 = -1;
            }
            PowerManagerService.m809$$Nest$msetDozeOverrideFromDreamManagerInternal(PowerManagerService.this, i, i2, i3, false);
        }

        public final void setDozeOverrideFromDreamManager(int i, int i2, int i3, int i4, boolean z) {
            Slog.d("PowerManagerService", "[api] setDozeOverrideFromDreamManager: screenState: " + Display.stateToString(i) + " mode: 0x" + Integer.toHexString(i4) + " screenBrightness: " + i3 + " shouldWait: " + z);
            if (!PowerManagerService.SEC_USE_FACTORY_BINARY) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mAlpmHlpmMode != i4) {
                    if (i4 == -1) {
                        Slog.d("PowerManagerService", "setDozeModeBySysfs:  value is -1 , we ignore it. ");
                    } else {
                        powerManagerService.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        powerManagerService.mAlpmHlpmMode = i4;
                        String num = Integer.toString(i4);
                        PowerManagerUtil.fileWriteString("/sys/class/lcd/panel/alpm", num);
                        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                            PowerManagerUtil.fileWriteString("/sys/class/lcd/panel1/alpm", num);
                        }
                    }
                }
            }
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
            if (i3 < -1 || i3 > PowerManagerService.this.mScreenExtendedBrightnessMaximum) {
                i3 = -1;
            }
            PowerManagerService.m809$$Nest$msetDozeOverrideFromDreamManagerInternal(PowerManagerService.this, i, i2, i3, z);
        }

        public final void setDrawWakeLockOverrideFromSidekick(boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mDrawWakeLockOverrideFromSidekick != z) {
                        Slog.d("PowerManagerService", "[api] setDrawWakeLockOverrideFromSidekickInternal()" + PowerManagerUtil.callerInfoToString(false));
                        powerManagerService.mDrawWakeLockOverrideFromSidekick = z;
                        powerManagerService.mDirty = powerManagerService.mDirty | 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setGoToSleepPrevention(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setGoToSleepPrevention: enabled: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            PowerManagerService.this.mGoToSleepPreventionEnabled = z;
        }

        public final void setHbmBlock(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setHbmBlock: enabled: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, true);
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mHbmBlock == z) {
                        Slog.d("PowerManagerService", "setHbmBlockInternal: already " + z);
                    } else {
                        powerManagerService.mHbmBlock = z;
                        Slog.d("PowerManagerService", "setHbmBlockInternal: " + z);
                        powerManagerService.mDirty = powerManagerService.mDirty | 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } finally {
                }
            }
        }

        public final boolean setLightDeviceIdleMode(boolean z) {
            StringBuilder sb = new StringBuilder("[api] setLightDeviceIdleMode: ");
            sb.append(z);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", sb, false);
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mLightDeviceIdleMode == z) {
                        return false;
                    }
                    powerManagerService.mLightDeviceIdleMode = z;
                    if (!powerManagerService.mDeviceIdleMode) {
                        Flags.disableWakelocksInLightIdle();
                    }
                    powerManagerService.setPowerModeInternal(8, powerManagerService.mDeviceIdleMode || powerManagerService.mLightDeviceIdleMode);
                    return true;
                } finally {
                }
            }
        }

        public final void setLowPowerStandbyActive(boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mLowPowerStandbyActive != z) {
                        powerManagerService.mLowPowerStandbyActive = z;
                        powerManagerService.updateWakeLockDisabledStatesLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setLowPowerStandbyAllowlist(int[] iArr) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    powerManagerService.mLowPowerStandbyAllowlist = iArr;
                    if (powerManagerService.mLowPowerStandbyActive) {
                        powerManagerService.updateWakeLockDisabledStatesLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setMaximumScreenOffTimeoutFromDeviceAdmin(int i, long j) {
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "[api] setMaximumScreenOffTimeoutFromDeviceAdmin: userId: ", j, " timeMs: "), true);
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (i < 0) {
                powerManagerService.getClass();
                android.util.Slog.wtf("PowerManagerService", "Attempt to set screen off timeout for invalid user: " + i);
                return;
            }
            synchronized (powerManagerService.mLock) {
                try {
                    if (i == 0) {
                        powerManagerService.mMaximumScreenOffTimeoutFromDeviceAdmin = j;
                    } else {
                        if (j != Long.MAX_VALUE && j != 0) {
                            ProfilePowerState profilePowerState = (ProfilePowerState) powerManagerService.mProfilePowerState.get(i);
                            if (profilePowerState != null) {
                                profilePowerState.mScreenOffTimeout = j;
                            } else {
                                SparseArray sparseArray = powerManagerService.mProfilePowerState;
                                powerManagerService.mClock.getClass();
                                sparseArray.put(i, new ProfilePowerState(i, j, SystemClock.uptimeMillis()));
                                powerManagerService.updateKnoxTimeoutValue(i);
                                powerManagerService.mDirty |= 1;
                            }
                        }
                        powerManagerService.mProfilePowerState.delete(i);
                    }
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setMaximumScreenOffTimeoutFromKnox(int i, long j) {
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "[api] setMaximumScreenOffTimeoutFromKnox: userId: ", j, " timeMs: "), false);
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (i < 0) {
                powerManagerService.getClass();
                android.util.Slog.wtf("PowerManagerService", "Attempt to set screen off timeout for invalid user: " + i);
                return;
            }
            synchronized (powerManagerService.mLock) {
                try {
                    ProfilePowerState profilePowerState = (ProfilePowerState) powerManagerService.mProfilePowerState.get(i);
                    if (profilePowerState != null) {
                        Slog.d("PowerManagerService", "profile timeout changed : " + profilePowerState.mScreenOffTimeoutForUser + " -> " + j);
                        profilePowerState.mScreenOffTimeoutForUser = j;
                    } else {
                        SparseArray sparseArray = powerManagerService.mProfilePowerState;
                        powerManagerService.mClock.getClass();
                        sparseArray.put(i, new ProfilePowerState(i, 0, j, SystemClock.uptimeMillis()));
                        powerManagerService.mDirty |= 1;
                    }
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setPowerBoost(int i, int i2) {
            if (PowerManagerService.this.mIsPowerBoostInvokedFromLocal.compareAndSet(false, true)) {
                DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "[api] setPowerBoost(L) boost:", ", durationMs:", ", caller"), true);
                PowerManagerService powerManagerService = PowerManagerService.this;
                Handler handler = powerManagerService.mHandlerPmsMisc;
                PowerManagerService$LocalService$$ExternalSyntheticLambda0 powerManagerService$LocalService$$ExternalSyntheticLambda0 = new PowerManagerService$LocalService$$ExternalSyntheticLambda0(0, this);
                powerManagerService.mClock.getClass();
                handler.postAtTime(powerManagerService$LocalService$$ExternalSyntheticLambda0, SystemClock.uptimeMillis() + 5000);
            }
            PowerManagerService.this.mNativeWrapper.getClass();
            PowerManagerService.nativeSetPowerBoost(i, i2);
        }

        public final void setPowerMode(int i, boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
            powerManagerService.setPowerModeInternal(i, z);
        }

        public final void setScreenBrightnessOverrideFromWindowManager(float f, String str) {
            if (f < FullScreenMagnificationGestureHandler.MAX_SCALE || f > 1.0f) {
                f = Float.NaN;
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (!BrightnessSynchronizer.floatEquals(powerManagerService.mScreenBrightnessOverrideFromWindowManager, f)) {
                        Slog.d("PowerManagerService", "[api] setScreenBrightnessOverrideFromWindowManagerInternal: brightness: " + PowerManagerUtil.brightnessToString(f) + ", packageName: " + str);
                        powerManagerService.mScreenBrightnessOverrideFromWindowManager = f;
                        powerManagerService.mScreenBrightnessOverridePackageFromWindowManager = str;
                        powerManagerService.mDirty = powerManagerService.mDirty | 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setScreenDimDurationOverrideFromSqd(boolean z) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (!z) {
                        powerManagerService.mScreenDimDurationOverrideFromSQD = -1L;
                    } else if (powerManagerService.mScreenDimDurationOverrideFromSQD == -1) {
                        long attentiveTimeoutLocked = powerManagerService.getAttentiveTimeoutLocked();
                        long screenOffTimeoutLocked = powerManagerService.getScreenOffTimeoutLocked(powerManagerService.getSleepTimeoutLocked(attentiveTimeoutLocked), attentiveTimeoutLocked);
                        long uptimeMillis = SystemClock.uptimeMillis();
                        powerManagerService.mScreenDimDurationOverrideFromSQD = (((PowerGroup) powerManagerService.mPowerGroups.get(0)).mLastUserActivityTime + screenOffTimeoutLocked) - uptimeMillis;
                        Slog.d("PowerManagerService", "[api] setScreenDimDurationOverrideFromSqdInternal: enabled: " + powerManagerService.mScreenDimDurationOverrideFromSQD + " = " + ((PowerGroup) powerManagerService.mPowerGroups.get(0)).mLastUserActivityTime + " + " + screenOffTimeoutLocked + " - " + uptimeMillis);
                    }
                    Slog.d("PowerManagerService", "[api] setScreenDimDurationOverrideFromSqdInternal: mScreenDimDurationOverrideFromSQD: " + powerManagerService.mScreenDimDurationOverrideFromSQD);
                    powerManagerService.mDirty = powerManagerService.mDirty | 32;
                    powerManagerService.updatePowerStateLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setScreenDimDurationOverrideFromWindowManager(long j) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mScreenDimDurationOverrideFromWindowManager != j) {
                        Slog.d("PowerManagerService", "[api] setScreenDimDurationOverrideFromWindowManagerInternal: timeoutMillis: " + j);
                        powerManagerService.mScreenDimDurationOverrideFromWindowManager = j;
                        powerManagerService.mDirty = powerManagerService.mDirty | 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setUserActivityTimeoutForDexOverrideFromWindowManager(long j) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mUserActivityTimeoutForDexOverrideFromWindowManager != j) {
                        Slog.d("PowerManagerService", "[api] setUserActivityTimeoutForDexOverrideFromWindowManagerInternal: timeoutMillis: " + j);
                        powerManagerService.mUserActivityTimeoutForDexOverrideFromWindowManager = j;
                        powerManagerService.mDirty = powerManagerService.mDirty | 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setUserActivityTimeoutOverrideFromWindowManager(long j) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    if (powerManagerService.mUserActivityTimeoutOverrideFromWindowManager != j) {
                        Slog.d("PowerManagerService", "[api] setUserActivityTimeoutOverrideFromWindowManagerInternal: timeoutMillis: " + j);
                        powerManagerService.mUserActivityTimeoutOverrideFromWindowManager = j;
                        EventLog.writeEvent(27391, j);
                        powerManagerService.mDirty |= 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setUserInactiveOverrideFromWindowManager() {
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", new StringBuilder("[api] setUserInactiveOverrideFromWindowManager()"), false);
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                powerManagerService.mUserInactiveOverrideFromWindowManager = true;
                powerManagerService.mDirty |= 4;
                powerManagerService.updatePowerStateLocked();
            }
        }

        public final void setWakeLockBlackListEnableDisable(int i, boolean z) {
            synchronized (PowerManagerService.this.mLock) {
                try {
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
                        PowerManagerService.this.mWakelockBlacklistAppid.remove(PowerManagerService.this.mWakelockBlacklistAppid.indexOf(Integer.valueOf(i)));
                    }
                    Iterator it = PowerManagerService.this.mWakeLocks.iterator();
                    boolean z2 = false;
                    while (it.hasNext()) {
                        WakeLock wakeLock = (WakeLock) it.next();
                        if (UserHandle.getAppId(wakeLock.mOwnerUid) == i && PowerManagerService.this.setWakeLockDisabledStateLocked(wakeLock)) {
                            if (wakeLock.mDisabled) {
                                PowerManagerService.this.notifyWakeLockReleasedLocked(wakeLock, -1);
                            } else {
                                PowerManagerService.this.notifyWakeLockAcquiredLocked(wakeLock);
                            }
                            z2 = true;
                        }
                    }
                    if (z2) {
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        powerManagerService.mDirty |= 1;
                        powerManagerService.updatePowerStateLocked();
                    } else {
                        Slog.i("PowerManagerService", "[PWL] can not change appid =  " + i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean setWakeLockDisabledStateForChinaModelLocked(WakeLock wakeLock) {
            int i;
            boolean z;
            long j;
            String str;
            boolean z2 = false;
            if ((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1) {
                if (wakeLock.mDisabledByFreecess) {
                    i = 8;
                    z = true;
                } else {
                    i = 0;
                    z = false;
                }
                if (wakeLock.mDisabled != z) {
                    wakeLock.mDisabled = z;
                    wakeLock.mDisableReason = i;
                    z2 = true;
                }
                Slog.i("PowerManagerService", "[PWL] find out: " + wakeLock + "  disabled -> ");
                if (z2) {
                    if (wakeLock.mNotifiedAcquired) {
                        PowerManagerService.this.mClock.getClass();
                        j = SystemClock.uptimeMillis() - wakeLock.mAcquireTime;
                    } else {
                        j = 0;
                    }
                    Long valueOf = Long.valueOf(j);
                    StringBuilder sb = new StringBuilder();
                    sb.append(wakeLock.mTag);
                    sb.append(":");
                    sb.append(wakeLock.mPackageName);
                    if (wakeLock.mDisabled) {
                        str = "(disabled:" + WakeLock.m824$$Nest$mgetDisableReasonString(wakeLock) + ")";
                    } else {
                        str = "(enabled)";
                    }
                    sb.append(str);
                    EventLog.writeEvent(2729, "DIS", valueOf, sb.toString());
                }
            }
            return z2;
        }

        public final int setWakeLockEnableDisable(int i, boolean z) {
            boolean z2;
            synchronized (PowerManagerService.this.mLock) {
                try {
                    Slog.i("PowerManagerService", "[PWL] SetWakeLockEnableDisable uid = " + i + " , disable= " + z);
                    Iterator it = PowerManagerService.this.mWakeLocks.iterator();
                    boolean z3 = false;
                    while (it.hasNext()) {
                        WakeLock wakeLock = (WakeLock) it.next();
                        if (wakeLock.mOwnerUid != i || wakeLock.mDisabledByFreecess == z) {
                            WorkSource workSource = wakeLock.mWorkSource;
                            if (workSource != null) {
                                int size = workSource.size();
                                ActiveMusicRecordFilter activeMusicRecordFilter = ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE;
                                synchronized (activeMusicRecordFilter.mSilentAudioDetectionMap) {
                                    try {
                                        boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                                        z2 = MARsPolicyManager.isChinaPolicyEnabled() && activeMusicRecordFilter.mSlientAudioApp.contains(Integer.valueOf(i));
                                    } finally {
                                    }
                                }
                                if (!z2 || size != 1 || wakeLock.mWorkSource.getUid(0) != i) {
                                    for (int i2 = 0; i2 < size; i2++) {
                                        if (wakeLock.mWorkSource.get(i2) == i && wakeLock.mDisabled != z) {
                                            Slog.i("PowerManagerService", "[PWL] find out in ws: " + wakeLock + "  disabled -> " + z);
                                            return 2;
                                        }
                                    }
                                } else if (setWakeLockDisabledStateForChinaModelLocked(wakeLock)) {
                                    if (wakeLock.mDisabled) {
                                        PowerManagerService.this.notifyWakeLockReleasedLocked(wakeLock, -1);
                                    } else {
                                        PowerManagerService.this.notifyWakeLockAcquiredLocked(wakeLock);
                                    }
                                    z3 = true;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            wakeLock.mDisabledByFreecess = z;
                            if (PowerManagerService.this.setWakeLockDisabledStateLocked(wakeLock)) {
                                if (wakeLock.mDisabled) {
                                    PowerManagerService.this.notifyWakeLockReleasedLocked(wakeLock, -1);
                                } else {
                                    PowerManagerService.this.notifyWakeLockAcquiredLocked(wakeLock);
                                }
                                z3 = true;
                            }
                            if (z3) {
                                Slog.i("PowerManagerService", "[PWL] find out: " + wakeLock + "  disabled -> " + z);
                            }
                        }
                    }
                    if (z3) {
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        powerManagerService.mDirty |= 1;
                        powerManagerService.updatePowerStateLocked();
                        return 1;
                    }
                    Slog.i("PowerManagerService", "[PWL] can not change uid =  " + i);
                    return 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void startUidChanges() {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                powerManagerService.mUidsChanging = true;
            }
        }

        public final void uidActive(int i) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    UidState uidState = (UidState) powerManagerService.mUidState.get(i);
                    if (uidState == null) {
                        uidState = new UidState(i);
                        uidState.mProcState = 19;
                        powerManagerService.mUidState.put(i, uidState);
                    }
                    uidState.mActive = true;
                    if (uidState.mNumWakeLocks > 0) {
                        powerManagerService.handleUidStateChangeLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void uidGone(int i) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    int indexOfKey = powerManagerService.mUidState.indexOfKey(i);
                    if (indexOfKey >= 0) {
                        UidState uidState = (UidState) powerManagerService.mUidState.valueAt(indexOfKey);
                        uidState.mProcState = 20;
                        uidState.mActive = false;
                        powerManagerService.mUidState.removeAt(indexOfKey);
                        if (!powerManagerService.doesIdleStateBlockWakeLocksLocked()) {
                            if (powerManagerService.mLowPowerStandbyActive) {
                            }
                        }
                        if (uidState.mNumWakeLocks > 0) {
                            powerManagerService.handleUidStateChangeLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void uidIdle(int i) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    UidState uidState = (UidState) powerManagerService.mUidState.get(i);
                    if (uidState != null) {
                        uidState.mActive = false;
                        if (uidState.mNumWakeLocks > 0) {
                            powerManagerService.handleUidStateChangeLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unregisterUserActivityStateListener(PowerManagerInternal.UserActivityStateListener userActivityStateListener) {
            Slog.d("PowerManagerService", "unregisterUserActivityStateListener: " + userActivityStateListener);
            if (userActivityStateListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                powerManagerService.mUserActivityStateListenerListeners.remove(userActivityStateListener);
            }
        }

        public final void updateProfileActivityTimeFromKnox(int i, long j) {
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("PowerManagerService", UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "[api] updateProfileActivityTimeFromKnox: userId: ", j, " timeMs: "), false);
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    ProfilePowerState profilePowerState = (ProfilePowerState) powerManagerService.mProfilePowerState.get(i);
                    if (profilePowerState == null || !SemPersonaManager.isKnoxId(i)) {
                        return;
                    }
                    if (j > profilePowerState.mLastUserActivityTime) {
                        Slog.d("PowerManagerService", "updated ProfilePowerState for Workspace");
                        profilePowerState.mLastUserActivityTime = j;
                    }
                } finally {
                }
            }
        }

        public final void updateUidProcState(int i, int i2) {
            PowerManagerService powerManagerService = PowerManagerService.this;
            synchronized (powerManagerService.mLock) {
                try {
                    UidState uidState = (UidState) powerManagerService.mUidState.get(i);
                    if (uidState == null) {
                        uidState = new UidState(i);
                        powerManagerService.mUidState.put(i, uidState);
                    }
                    boolean z = uidState.mProcState <= 11;
                    uidState.mProcState = i2;
                    if (uidState.mNumWakeLocks > 0) {
                        if (!powerManagerService.doesIdleStateBlockWakeLocksLocked() && !powerManagerService.mLowPowerStandbyActive) {
                            if (!uidState.mActive) {
                                if (z != (i2 <= 11)) {
                                    powerManagerService.handleUidStateChangeLocked();
                                }
                            }
                        }
                        powerManagerService.handleUidStateChangeLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean wasDeviceIdleFor(long j) {
            return PowerManagerService.this.wasDeviceIdleForInternal(j);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NativeWrapper {
        public final void nativeAcquireSuspendBlocker(String str) {
            PowerManagerService.nativeAcquireSuspendBlocker(str);
        }

        public final boolean nativeForceSuspend() {
            return PowerManagerService.nativeForceSuspend();
        }

        public final void nativeInit(PowerManagerService powerManagerService) {
            powerManagerService.nativeInit();
        }

        public final void nativeReleaseSuspendBlocker(String str) {
            PowerManagerService.nativeReleaseSuspendBlocker(str);
        }

        public final void nativeSetAutoSuspend(boolean z) {
            PowerManagerService.nativeSetAutoSuspend(z);
        }

        public final void nativeSetInteractiveAsync(boolean z, int i) {
            PowerManagerService.nativeSetInteractiveAsync(z, i);
        }

        public final void nativeSetPowerBoost(int i, int i2) {
            PowerManagerService.nativeSetPowerBoost(i, i2);
        }

        public final boolean nativeSetPowerMode(int i, boolean z) {
            return PowerManagerService.nativeSetPowerMode(i, z);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface PermissionCheckerWrapper {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PowerManagerHandlerCallback implements Handler.Callback {
        public PowerManagerHandlerCallback() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            int i = 0;
            switch (message.what) {
                case 1:
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    powerManagerService.mAbuseWakeLockDetector.handleAbuseWakelockWhenUserActivityChanged(powerManagerService.mWakeLocks);
                    PowerManagerService.m802$$Nest$mhandleUserActivityTimeout(PowerManagerService.this);
                    return true;
                case 2:
                    PowerManagerService.m801$$Nest$mhandleSandman(PowerManagerService.this, message.arg1);
                    return true;
                case 3:
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    synchronized (powerManagerService2.mLock) {
                        powerManagerService2.mDirty |= 2048;
                        powerManagerService2.updatePowerStateLocked();
                    }
                    return true;
                case 4:
                    PowerManagerService powerManagerService3 = PowerManagerService.this;
                    synchronized (powerManagerService3.mLock) {
                        try {
                            powerManagerService3.mClock.getClass();
                            long uptimeMillis = SystemClock.uptimeMillis();
                            powerManagerService3.mNotifyLongDispatched = uptimeMillis;
                            long j = uptimeMillis - 60000;
                            int size = powerManagerService3.mWakeLocks.size();
                            long j2 = Long.MAX_VALUE;
                            while (i < size) {
                                WakeLock wakeLock = (WakeLock) powerManagerService3.mWakeLocks.get(i);
                                if ((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1 && wakeLock.mNotifiedAcquired && !wakeLock.mNotifiedLong) {
                                    long j3 = wakeLock.mAcquireTime;
                                    if (j3 < j) {
                                        powerManagerService3.notifyWakeLockLongStartedLocked(wakeLock);
                                    } else {
                                        long j4 = j3 + 60000;
                                        if (j4 < j2) {
                                            j2 = j4;
                                        }
                                    }
                                }
                                i++;
                            }
                            powerManagerService3.mNotifyLongScheduled = 0L;
                            powerManagerService3.mHandler.removeMessages(4);
                            if (j2 != Long.MAX_VALUE) {
                                powerManagerService3.mNotifyLongNextCheck = j2;
                                powerManagerService3.mNotifyLongScheduled = j2;
                                Handler handler = powerManagerService3.mHandler;
                                Message obtainMessage = handler.obtainMessage(4);
                                obtainMessage.setAsynchronous(true);
                                handler.sendMessageAtTime(obtainMessage, j2);
                            } else {
                                powerManagerService3.mNotifyLongNextCheck = 0L;
                            }
                        } finally {
                        }
                    }
                    return true;
                case 5:
                    PowerManagerService powerManagerService4 = PowerManagerService.this;
                    synchronized (powerManagerService4.mLock) {
                        powerManagerService4.mDirty |= EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                        powerManagerService4.updatePowerStateLocked();
                    }
                    return true;
                case 6:
                    PowerManagerService powerManagerService5 = PowerManagerService.this;
                    int i2 = message.arg1;
                    synchronized (powerManagerService5.mLock) {
                        try {
                            for (int size2 = powerManagerService5.mWakeLocks.size() - 1; size2 >= 0; size2--) {
                                WakeLock wakeLock2 = (WakeLock) powerManagerService5.mWakeLocks.get(size2);
                                if ((wakeLock2.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 256) {
                                    powerManagerService5.removeWakeLockNoUpdateLocked(wakeLock2, size2, i2);
                                    i = 1;
                                }
                            }
                            if (i != 0) {
                                powerManagerService5.updatePowerStateLocked();
                            }
                        } finally {
                        }
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface PowerPropertiesWrapper {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProfilePowerState {
        public long mLastUserActivityTime;
        public boolean mLockingNotified;
        public long mScreenOffTimeout;
        public long mScreenOffTimeoutForUser;
        public final int mUserId;
        public int mWakeLockSummary;

        public ProfilePowerState(int i, int i2, long j, long j2) {
            Slog.d("PowerManagerService", "new ProfilePowerState: " + i + ", " + j);
            this.mUserId = i;
            this.mScreenOffTimeout = Long.MAX_VALUE;
            this.mScreenOffTimeoutForUser = j;
            this.mLastUserActivityTime = j2;
        }

        public ProfilePowerState(int i, long j, long j2) {
            Slog.d("PowerManagerService", "new ProfilePowerState: " + i + ", " + j);
            this.mUserId = i;
            this.mScreenOffTimeout = j;
            this.mLastUserActivityTime = j2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PowerManagerService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ SettingsObserver(PowerManagerService powerManagerService, Handler handler, int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = powerManagerService;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 1:
                    super.onChange(z);
                    PowerManagerService powerManagerService = this.this$0;
                    SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
                    powerManagerService.handleDexScreenOffTimeoutChange();
                    break;
                default:
                    super.onChange(z);
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            switch (this.$r8$classId) {
                case 0:
                    Slog.d("PowerManagerService", "[api] SettingsObserver: onChange: " + uri);
                    synchronized (this.this$0.mLock) {
                        this.this$0.handleSettingsChangedLocked();
                    }
                    return;
                case 1:
                default:
                    super.onChange(z, uri);
                    return;
                case 2:
                    Slog.d("PowerManagerService", "QuickTilesSettingsObserver: onChange: " + uri);
                    synchronized (this.this$0.mLock) {
                        this.this$0.updateQuickTilesSettingsLocked();
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SuspendBlockerImpl {
        public final boolean mIsShowLog;
        public final String mName;
        public final int mNameHash;
        public final ArrayMap mOpenReferenceTimes = new ArrayMap();
        public int mReferenceCount;

        public SuspendBlockerImpl(String str) {
            this.mName = str;
            this.mIsShowLog = str.equals("PowerManagerService.Broadcasts") || str.equals("PowerManagerService.Display");
            this.mNameHash = str.hashCode();
        }

        public final void acquire(String str) {
            synchronized (this) {
                try {
                    LongArray longArray = (LongArray) this.mOpenReferenceTimes.get(str);
                    if (longArray == null) {
                        longArray = new LongArray(2);
                        this.mOpenReferenceTimes.put(str, longArray);
                    }
                    longArray.add(System.currentTimeMillis());
                    int i = this.mReferenceCount + 1;
                    this.mReferenceCount = i;
                    if (i == 1) {
                        if (this.mIsShowLog) {
                            Slog.d("PowerManagerService", "[PWL] sb acquire: " + this.mName);
                        }
                        Trace.asyncTraceForTrackBegin(131072L, "SuspendBlockers", this.mName, this.mNameHash);
                        NativeWrapper nativeWrapper = PowerManagerService.this.mNativeWrapper;
                        String str2 = this.mName;
                        nativeWrapper.getClass();
                        PowerManagerService.nativeAcquireSuspendBlocker(str2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void finalize() {
            String str = this.mName;
            try {
                if (this.mReferenceCount != 0) {
                    int i = Slog.$r8$clinit;
                    android.util.Slog.wtf("PowerManagerService", "Suspend blocker \"" + str + "\" was finalized without being released!");
                    this.mReferenceCount = 0;
                    PowerManagerService.this.mNativeWrapper.getClass();
                    PowerManagerService.nativeReleaseSuspendBlocker(str);
                    Trace.asyncTraceForTrackEnd(131072L, "SuspendBlockers", this.mNameHash);
                }
            } finally {
                super.finalize();
            }
        }

        public final void release() {
            release("unknown");
        }

        public final void release(String str) {
            synchronized (this) {
                try {
                    LongArray longArray = (LongArray) this.mOpenReferenceTimes.get(str);
                    if (longArray != null && longArray.size() > 0) {
                        longArray.remove(longArray.size() - 1);
                        if (longArray.size() == 0) {
                            this.mOpenReferenceTimes.remove(str);
                        }
                    }
                    int i = this.mReferenceCount - 1;
                    this.mReferenceCount = i;
                    if (i == 0) {
                        if (this.mIsShowLog) {
                            Slog.d("PowerManagerService", "[PWL] sb release: " + this.mName);
                        }
                        NativeWrapper nativeWrapper = PowerManagerService.this.mNativeWrapper;
                        String str2 = this.mName;
                        nativeWrapper.getClass();
                        PowerManagerService.nativeReleaseSuspendBlocker(str2);
                        if (Trace.isTagEnabled(131072L)) {
                            Trace.asyncTraceForTrackEnd(131072L, "SuspendBlockers", this.mNameHash);
                        }
                    } else if (i < 0) {
                        String str3 = "Suspend blocker \"" + this.mName + "\" was released without being acquired!";
                        Throwable th = new Throwable();
                        int i2 = Slog.$r8$clinit;
                        android.util.Slog.wtf("PowerManagerService", str3, th);
                        this.mReferenceCount = 0;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final String toString() {
            String sb;
            synchronized (this) {
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
            return sb;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidState {
        public boolean mActive;
        public int mNumWakeLocks;
        public int mProcState;
        public final int mUid;

        public UidState(int i) {
            this.mUid = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class UserSwitchedReceiver extends BroadcastReceiver {
        public UserSwitchedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Slog.d("PowerManagerService", "[api] UserSwitchedReceiver: onReceive: UserHandle: " + ActivityManager.getCurrentUser() + " -> " + intent.getIntExtra("android.intent.extra.user_handle", 0));
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                powerManagerService.setQuickPanelBrightnessBarDisabled(powerManagerService.mLastBrightnessOverrideState);
                ScreenOnKeeper screenOnKeeper = PowerManagerService.this.mScreenOnKeeper;
                if (screenOnKeeper != null) {
                    screenOnKeeper.getClass();
                    Slog.d("ScreenOnKeeper", "onUserSwitched");
                    if (screenOnKeeper.mIsScreenOnKeeperEnabled) {
                        screenOnKeeper.disableScreenOnKeeper();
                        screenOnKeeper.notifyScreenOnKeeperDisabledLocked(2);
                    }
                }
                PowerManagerService.this.handleSettingsChangedLocked();
                PowerManagerService.m812$$Nest$msetScreenOffTimeoutForBatterySaver(PowerManagerService.this);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

        /* renamed from: -$$Nest$mgetDisableReasonString, reason: not valid java name */
        public static String m824$$Nest$mgetDisableReasonString(WakeLock wakeLock) {
            StringBuilder sb = new StringBuilder();
            if ((wakeLock.mDisableReason & 1) != 0) {
                sb.append(" nocached");
            }
            if ((wakeLock.mDisableReason & 2) != 0) {
                sb.append(" idlemode");
            }
            if ((wakeLock.mDisableReason & 4) != 0) {
                sb.append(" lowpwrst");
            }
            if ((wakeLock.mDisableReason & 8) != 0) {
                sb.append(" freecess");
            }
            if ((wakeLock.mDisableReason & 16) != 0) {
                sb.append(" gmsalarm");
            }
            return sb.toString();
        }

        public WakeLock(IBinder iBinder, int i, int i2, String str, String str2, WorkSource workSource, String str3, int i3, int i4, UidState uidState, IWakeLockCallback iWakeLockCallback) {
            this.mLock = iBinder;
            this.mDisplayId = i;
            this.mFlags = i2;
            this.mTag = str;
            this.mPackageName = str2;
            SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
            this.mWorkSource = workSource != null ? new WorkSource(workSource) : null;
            this.mHistoryTag = str3;
            this.mOwnerUid = i3;
            this.mOwnerPid = i4;
            this.mUidState = uidState;
            this.mCallback = iWakeLockCallback;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException unused) {
                throw new IllegalArgumentException("Wakelock.mLock is already dead.");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                this.mLock.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                int i = Slog.$r8$clinit;
                android.util.Slog.wtf("PowerManagerService", "Failed to unlink Wakelock.mLock", e);
            }
            PowerManagerService powerManagerService = PowerManagerService.this;
            SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
            powerManagerService.getClass();
            Slog.d("PowerManagerService", "[api] handleWakeLockDeath : release WakeLock : " + this);
            synchronized (powerManagerService.mLock) {
                try {
                    int indexOf = powerManagerService.mWakeLocks.indexOf(this);
                    if (indexOf < 0) {
                        return;
                    }
                    powerManagerService.removeWakeLockNoUpdateLocked(this, indexOf, -1);
                    powerManagerService.updatePowerStateLocked();
                } finally {
                }
            }
        }

        public final void dumpDebug(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(2246267895855L);
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

        public final Integer getPowerGroupId() {
            int i;
            PowerManagerService powerManagerService = PowerManagerService.this;
            if (!powerManagerService.mSystemReady || (i = this.mDisplayId) == -1) {
                return -1;
            }
            DisplayInfo displayInfo = powerManagerService.mDisplayManagerInternal.getDisplayInfo(i);
            if (displayInfo != null) {
                return Integer.valueOf(displayInfo.displayGroupId);
            }
            return null;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            int i = this.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
            sb.append(i != 1 ? i != 6 ? i != 10 ? i != 26 ? i != 32 ? i != 64 ? i != 128 ? i != 256 ? "???                              " : "SCREEN_TIMEOUT_OVERRIDE_WAKE_LOCK" : "DRAW_WAKE_LOCK                   " : "DOZE_WAKE_LOCK                   " : "PROXIMITY_SCREEN_OFF_WAKE_LOCK   " : "FULL_WAKE_LOCK                   " : "SCREEN_BRIGHT_WAKE_LOCK          " : "SCREEN_DIM_WAKE_LOCK             " : "PARTIAL_WAKE_LOCK                ");
            sb.append(" '");
            sb.append(this.mTag);
            sb.append("'");
            int i2 = this.mFlags;
            String str = (268435456 & i2) != 0 ? " ACQUIRE_CAUSES_WAKEUP" : "";
            if ((i2 & 536870912) != 0) {
                str = str.concat(" ON_AFTER_RELEASE");
            }
            if ((this.mFlags & Integer.MIN_VALUE) != 0) {
                str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " SYSTEM_WAKELOCK");
            }
            sb.append(str);
            if (this.mDisabled) {
                sb.append(" DISABLED");
            }
            if (this.mNotifiedAcquired) {
                sb.append(" ACQ=");
                long j = this.mAcquireTime;
                PowerManagerService.this.mClock.getClass();
                TimeUtils.formatDuration(j - SystemClock.uptimeMillis(), sb);
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

        public final void updateProperties(int i, int i2, int i3, IWakeLockCallback iWakeLockCallback, WorkSource workSource, String str, String str2, String str3) {
            if (!this.mPackageName.equals(str2)) {
                throw new IllegalStateException(BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("Existing wake lock package name changed: "), this.mPackageName, " to ", str2));
            }
            if (this.mOwnerUid != i2) {
                throw new IllegalStateException("Existing wake lock uid changed: " + this.mOwnerUid + " to " + i2);
            }
            if (this.mOwnerPid != i3) {
                throw new IllegalStateException("Existing wake lock pid changed: " + this.mOwnerPid + " to " + i3);
            }
            this.mFlags = i;
            this.mTag = str;
            SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
            this.mWorkSource = workSource != null ? new WorkSource(workSource) : null;
            this.mHistoryTag = str3;
            this.mCallback = iWakeLockCallback;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WakeUpPreventionLock implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final long mLastRequestedTime;
        public final String mTag = PowerManagerUtil.callerInfoToString(false);

        public WakeUpPreventionLock(IBinder iBinder, long j) {
            this.mBinder = iBinder;
            this.mLastRequestedTime = j;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (PowerManagerService.this.mLock) {
                try {
                    Slog.d("PowerManagerService", "WakeUpPreventionLock: binderDied: " + this);
                    if (PowerManagerService.this.mWakeUpPreventionLocks.indexOf(this) < 0) {
                        return;
                    }
                    PowerManagerService.m797$$Nest$mdisableWakeUpPreventionInternal(PowerManagerService.this, this.mBinder, false);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(" (lock:" + Objects.hashCode(this.mBinder));
            sb.append(", t:" + this.mLastRequestedTime + ")");
            StringBuilder sb2 = new StringBuilder(" ");
            sb2.append(this.mTag);
            sb.append(sb2.toString());
            return sb.toString();
        }
    }

    /* renamed from: -$$Nest$macquireWakeLockInternal, reason: not valid java name */
    public static void m794$$Nest$macquireWakeLockInternal(PowerManagerService powerManagerService, IBinder iBinder, int i, int i2, String str, String str2, WorkSource workSource, String str3, int i3, int i4, IWakeLockCallback iWakeLockCallback, boolean z) {
        String str4;
        WakeLock wakeLock;
        boolean z2;
        boolean z3;
        synchronized (powerManagerService.mLock) {
            if (i != -1) {
                try {
                    DisplayInfo displayInfo = powerManagerService.mSystemReady ? powerManagerService.mDisplayManagerInternal.getDisplayInfo(i) : null;
                    if (displayInfo == null) {
                        int i5 = Slog.$r8$clinit;
                        android.util.Slog.wtf("PowerManagerService", "Tried to acquire wake lock for invalid display: " + i);
                        return;
                    }
                    if (!displayInfo.hasAccess(i3)) {
                        throw new SecurityException("Caller does not have access to display");
                    }
                } finally {
                }
            }
            int findWakeLockIndexLocked = powerManagerService.findWakeLockIndexLocked(iBinder);
            boolean z4 = false;
            if (findWakeLockIndexLocked >= 0) {
                wakeLock = (WakeLock) powerManagerService.mWakeLocks.get(findWakeLockIndexLocked);
                if (wakeLock.mFlags == i2 && wakeLock.mTag.equals(str)) {
                    if (Objects.equals(wakeLock.mWorkSource, workSource) && wakeLock.mOwnerUid == i3) {
                        if (wakeLock.mOwnerPid == i4) {
                            z3 = true;
                            str4 = "[api] acquire WakeLock ";
                            z4 = z3;
                            z2 = false;
                        }
                        powerManagerService.notifyWakeLockChangingLocked(wakeLock, i2, str, str2, i3, i4, workSource, str3, iWakeLockCallback);
                        wakeLock.updateProperties(i2, i3, i4, iWakeLockCallback, workSource, str, str2, str3);
                        z3 = false;
                        str4 = "[api] acquire WakeLock ";
                        z4 = z3;
                        z2 = false;
                    }
                    powerManagerService.notifyWakeLockChangingLocked(wakeLock, i2, str, str2, i3, i4, workSource, str3, iWakeLockCallback);
                    wakeLock.updateProperties(i2, i3, i4, iWakeLockCallback, workSource, str, str2, str3);
                    z3 = false;
                    str4 = "[api] acquire WakeLock ";
                    z4 = z3;
                    z2 = false;
                }
                powerManagerService.notifyWakeLockChangingLocked(wakeLock, i2, str, str2, i3, i4, workSource, str3, iWakeLockCallback);
                wakeLock.updateProperties(i2, i3, i4, iWakeLockCallback, workSource, str, str2, str3);
                z3 = false;
                str4 = "[api] acquire WakeLock ";
                z4 = z3;
                z2 = false;
            } else {
                UidState uidState = (UidState) powerManagerService.mUidState.get(i3);
                if (uidState == null) {
                    uidState = new UidState(i3);
                    uidState.mProcState = 20;
                    powerManagerService.mUidState.put(i3, uidState);
                }
                UidState uidState2 = uidState;
                uidState2.mNumWakeLocks++;
                str4 = "[api] acquire WakeLock ";
                WakeLock wakeLock2 = powerManagerService.new WakeLock(iBinder, i, i2, str, str2, workSource, str3, i3, i4, uidState2, iWakeLockCallback);
                powerManagerService.mWakeLocks.add(wakeLock2);
                powerManagerService.setWakeLockDisabledStateLocked(wakeLock2);
                wakeLock = wakeLock2;
                z2 = true;
            }
            if (z4) {
                wakeLock.mConsecutiveAcquireCount++;
            } else {
                int i6 = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
                if (i6 == 6 || i6 == 10 || i6 == 26 || i6 == 32 || i6 == 64 || i6 == 128) {
                    Slog.d("PowerManagerService", str4 + wakeLock);
                }
            }
            powerManagerService.applyWakeLockFlagsOnAcquireLocked(wakeLock, z);
            powerManagerService.mDirty |= 1;
            powerManagerService.updatePowerStateLocked();
            if (z2) {
                powerManagerService.notifyWakeLockAcquiredLocked(wakeLock);
            }
        }
    }

    /* renamed from: -$$Nest$mboostScreenBrightnessInternal, reason: not valid java name */
    public static void m795$$Nest$mboostScreenBrightnessInternal(PowerManagerService powerManagerService, long j, int i) {
        synchronized (powerManagerService.mLock) {
            try {
                if (powerManagerService.mSystemReady && powerManagerService.getGlobalWakefulnessLocked() != 0 && j >= powerManagerService.mLastScreenBrightnessBoostTime) {
                    Slog.i("PowerManagerService", "Brightness boost activated (uid " + i + ")...");
                    powerManagerService.mLastScreenBrightnessBoostTime = j;
                    powerManagerService.mScreenBrightnessBoostInProgress = true;
                    powerManagerService.mDirty = powerManagerService.mDirty | 2048;
                    powerManagerService.userActivityNoUpdateLocked((PowerGroup) powerManagerService.mPowerGroups.get(0), j, 0, 0, i);
                    powerManagerService.updatePowerStateLocked();
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mdisableAbusiveWakeLockInternal, reason: not valid java name */
    public static void m796$$Nest$mdisableAbusiveWakeLockInternal(PowerManagerService powerManagerService, IBinder iBinder, boolean z) {
        synchronized (powerManagerService.mLock) {
            try {
                int findWakeLockIndexLocked = powerManagerService.findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked >= 0) {
                    WakeLock wakeLock = (WakeLock) powerManagerService.mWakeLocks.get(findWakeLockIndexLocked);
                    if (wakeLock.mDisabled != z) {
                        wakeLock.mDisabled = z;
                        if (z) {
                            powerManagerService.notifyWakeLockReleasedLocked(wakeLock, -1);
                        } else {
                            powerManagerService.notifyWakeLockAcquiredLocked(wakeLock);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mdisableWakeUpPreventionInternal, reason: not valid java name */
    public static void m797$$Nest$mdisableWakeUpPreventionInternal(PowerManagerService powerManagerService, IBinder iBinder, boolean z) {
        synchronized (powerManagerService.mLock) {
            try {
                int size = powerManagerService.mWakeUpPreventionLocks.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        i = -1;
                        break;
                    } else if (((WakeUpPreventionLock) powerManagerService.mWakeUpPreventionLocks.get(i)).mBinder == iBinder) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i == -1) {
                    Slog.d("PowerManagerService", "disableWakeUpPreventionInternal: aleadyDisabled");
                    return;
                }
                powerManagerService.mWakeUpPreventionLocks.remove(i);
                if (z && powerManagerService.mWakeUpPreventionLocks.isEmpty()) {
                    powerManagerService.mScreenOnReason = " wakeUpPrevention disabled";
                    PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.get(0);
                    powerManagerService.mClock.getClass();
                    powerManagerService.wakePowerGroupLocked(powerGroup, SystemClock.uptimeMillis(), 115, "android.server.power:WAKE_UP_PREVENTION_DISABLED", 1000, powerManagerService.mContext.getOpPackageName(), 1000, false);
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mdumpProto, reason: not valid java name */
    public static void m798$$Nest$mdumpProto(PowerManagerService powerManagerService, FileDescriptor fileDescriptor) {
        WirelessChargerDetector wirelessChargerDetector;
        powerManagerService.getClass();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        synchronized (powerManagerService.mLock) {
            try {
                Constants constants = powerManagerService.mConstants;
                constants.getClass();
                long start = protoOutputStream.start(1146756268033L);
                protoOutputStream.write(1133871366145L, constants.NO_CACHED_WAKE_LOCKS);
                protoOutputStream.end(start);
                protoOutputStream.write(1120986464258L, powerManagerService.mDirty);
                protoOutputStream.write(1159641169923L, powerManagerService.getGlobalWakefulnessLocked());
                protoOutputStream.write(1133871366148L, powerManagerService.mWakefulnessChanging);
                protoOutputStream.write(1133871366149L, powerManagerService.mIsPowered);
                protoOutputStream.write(1159641169926L, powerManagerService.mPlugType);
                protoOutputStream.write(1120986464263L, powerManagerService.mBatteryLevel);
                protoOutputStream.write(1120986464313L, powerManagerService.mDreamsBatteryLevelDrain);
                protoOutputStream.write(1159641169929L, powerManagerService.mDockState);
                protoOutputStream.write(1133871366154L, powerManagerService.mStayOn);
                protoOutputStream.write(1133871366155L, powerManagerService.mProximityPositive);
                protoOutputStream.write(1133871366156L, powerManagerService.mBootCompleted);
                protoOutputStream.write(1133871366157L, powerManagerService.mSystemReady);
                synchronized (powerManagerService.mEnhancedDischargeTimeLock) {
                    protoOutputStream.write(1112396529716L, powerManagerService.mEnhancedDischargeTimeElapsed);
                    protoOutputStream.write(1112396529717L, powerManagerService.mLastEnhancedDischargeTimeUpdatedElapsed);
                    protoOutputStream.write(1133871366198L, powerManagerService.mEnhancedDischargePredictionIsPersonalized);
                }
                protoOutputStream.write(1133871366158L, powerManagerService.mHalAutoSuspendModeEnabled);
                protoOutputStream.write(1133871366159L, powerManagerService.mHalInteractiveModeEnabled);
                long start2 = protoOutputStream.start(1146756268048L);
                protoOutputStream.write(1133871366145L, (powerManagerService.mWakeLockSummary & 1) != 0);
                protoOutputStream.write(1133871366146L, (powerManagerService.mWakeLockSummary & 2) != 0);
                long j = 1133871366147L;
                protoOutputStream.write(1133871366147L, (powerManagerService.mWakeLockSummary & 4) != 0);
                protoOutputStream.write(1133871366148L, (powerManagerService.mWakeLockSummary & 8) != 0);
                protoOutputStream.write(1133871366149L, (powerManagerService.mWakeLockSummary & 16) != 0);
                protoOutputStream.write(1133871366150L, (powerManagerService.mWakeLockSummary & 32) != 0);
                protoOutputStream.write(1133871366151L, (powerManagerService.mWakeLockSummary & 64) != 0);
                protoOutputStream.write(1133871366152L, (powerManagerService.mWakeLockSummary & 128) != 0);
                protoOutputStream.end(start2);
                protoOutputStream.write(1112396529681L, powerManagerService.mNotifyLongScheduled);
                protoOutputStream.write(1112396529682L, powerManagerService.mNotifyLongDispatched);
                protoOutputStream.write(1112396529683L, powerManagerService.mNotifyLongNextCheck);
                int i = 0;
                while (i < powerManagerService.mPowerGroups.size()) {
                    PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.valueAt(i);
                    long start3 = protoOutputStream.start(2246267895828L);
                    protoOutputStream.write(1120986464262L, powerGroup.mGroupId);
                    long j2 = powerGroup.mUserActivitySummary;
                    protoOutputStream.write(1133871366145L, (j2 & 1) != 0);
                    protoOutputStream.write(1133871366146L, (j2 & 2) != 0);
                    protoOutputStream.write(j, (j2 & 4) != 0);
                    protoOutputStream.write(1112396529668L, powerGroup.mLastUserActivityTime);
                    protoOutputStream.write(1112396529669L, powerGroup.mLastUserActivityTimeNoChangeLights);
                    protoOutputStream.end(start3);
                    i++;
                    j = 1133871366147L;
                }
                protoOutputStream.write(1133871366165L, powerManagerService.mRequestWaitForNegativeProximity);
                protoOutputStream.write(1133871366166L, powerManagerService.mSandmanScheduled);
                protoOutputStream.write(1133871366168L, powerManagerService.mBatteryLevelLow);
                protoOutputStream.write(1133871366169L, powerManagerService.mLightDeviceIdleMode);
                protoOutputStream.write(1133871366170L, powerManagerService.mDeviceIdleMode);
                for (int i2 : powerManagerService.mDeviceIdleWhitelist) {
                    protoOutputStream.write(2220498092059L, i2);
                }
                for (int i3 : powerManagerService.mDeviceIdleTempWhitelist) {
                    protoOutputStream.write(2220498092060L, i3);
                }
                protoOutputStream.write(1133871366199L, powerManagerService.mLowPowerStandbyActive);
                protoOutputStream.write(1112396529693L, powerManagerService.mLastGlobalWakeTime);
                protoOutputStream.write(1112396529694L, powerManagerService.mLastGlobalSleepTime);
                protoOutputStream.write(1112396529697L, powerManagerService.mLastInteractivePowerHintTime);
                protoOutputStream.write(1112396529698L, powerManagerService.mLastScreenBrightnessBoostTime);
                protoOutputStream.write(1133871366179L, powerManagerService.mScreenBrightnessBoostInProgress);
                protoOutputStream.write(1133871366181L, powerManagerService.mHoldingWakeLockSuspendBlocker);
                protoOutputStream.write(1133871366182L, powerManagerService.mHoldingDisplaySuspendBlocker);
                long start4 = protoOutputStream.start(1146756268071L);
                protoOutputStream.write(1133871366145L, powerManagerService.mDecoupleHalAutoSuspendModeFromDisplayConfig);
                protoOutputStream.write(1133871366146L, powerManagerService.mDecoupleHalInteractiveModeFromDisplayConfig);
                protoOutputStream.write(1133871366147L, powerManagerService.mWakeUpWhenPluggedOrUnpluggedConfig);
                protoOutputStream.write(1133871366148L, powerManagerService.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig);
                protoOutputStream.write(1133871366149L, powerManagerService.mTheaterModeEnabled);
                protoOutputStream.write(1133871366150L, powerManagerService.mSuspendWhenScreenOffDueToProximityConfig);
                protoOutputStream.write(1133871366151L, powerManagerService.mDreamsSupportedConfig);
                protoOutputStream.write(1133871366152L, powerManagerService.mDreamsEnabledByDefaultConfig);
                protoOutputStream.write(1133871366153L, powerManagerService.mDreamsActivatedOnSleepByDefaultConfig);
                protoOutputStream.write(1133871366154L, powerManagerService.mDreamsActivatedOnDockByDefaultConfig);
                protoOutputStream.write(1133871366155L, powerManagerService.mDreamsEnabledOnBatteryConfig);
                protoOutputStream.write(1172526071820L, powerManagerService.mDreamsBatteryLevelMinimumWhenPoweredConfig);
                protoOutputStream.write(1172526071821L, powerManagerService.mDreamsBatteryLevelMinimumWhenNotPoweredConfig);
                protoOutputStream.write(1172526071822L, powerManagerService.mDreamsBatteryLevelDrainCutoffConfig);
                protoOutputStream.write(1133871366159L, powerManagerService.mDreamsEnabledSetting);
                protoOutputStream.write(1133871366160L, powerManagerService.mDreamsActivateOnSleepSetting);
                protoOutputStream.write(1133871366161L, powerManagerService.mDreamsActivateOnDockSetting);
                protoOutputStream.write(1133871366162L, powerManagerService.mDozeAfterScreenOff);
                protoOutputStream.write(1120986464275L, powerManagerService.mMinimumScreenOffTimeoutConfig);
                protoOutputStream.write(1120986464276L, powerManagerService.mMaximumScreenDimDurationConfig);
                protoOutputStream.write(1108101562389L, powerManagerService.mMaximumScreenDimRatioConfig);
                protoOutputStream.write(1120986464278L, powerManagerService.mScreenOffTimeoutSetting);
                protoOutputStream.write(1172526071831L, powerManagerService.mSleepTimeoutSetting);
                protoOutputStream.write(1172526071845L, powerManagerService.mAttentiveTimeoutSetting);
                protoOutputStream.write(1172526071846L, powerManagerService.mAttentiveTimeoutConfig);
                protoOutputStream.write(1172526071847L, powerManagerService.mAttentiveWarningDurationConfig);
                protoOutputStream.write(1120986464280L, Math.min(powerManagerService.mMaximumScreenOffTimeoutFromDeviceAdmin, 2147483647L));
                protoOutputStream.write(1133871366169L, powerManagerService.isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked());
                long start5 = protoOutputStream.start(1146756268058L);
                protoOutputStream.write(1133871366145L, (powerManagerService.mStayOnWhilePluggedInSetting & 1) != 0);
                protoOutputStream.write(1133871366146L, (powerManagerService.mStayOnWhilePluggedInSetting & 2) != 0);
                protoOutputStream.write(1133871366147L, (powerManagerService.mStayOnWhilePluggedInSetting & 4) != 0);
                protoOutputStream.write(1133871366148L, (powerManagerService.mStayOnWhilePluggedInSetting & 8) != 0);
                protoOutputStream.end(start5);
                protoOutputStream.write(1172526071836L, powerManagerService.mScreenBrightnessOverrideFromWindowManager);
                protoOutputStream.write(1176821039133L, powerManagerService.mUserActivityTimeoutOverrideFromWindowManager);
                protoOutputStream.write(1133871366174L, powerManagerService.mUserInactiveOverrideFromWindowManager);
                protoOutputStream.write(1159641169951L, powerManagerService.mDozeScreenStateOverrideFromDreamManager);
                protoOutputStream.write(1133871366180L, powerManagerService.mDrawWakeLockOverrideFromSidekick);
                protoOutputStream.write(1108101562400L, powerManagerService.mDozeScreenBrightnessOverrideFromDreamManager);
                long start6 = protoOutputStream.start(1146756268065L);
                protoOutputStream.write(1108101562372L, powerManagerService.mScreenBrightnessMinimum);
                protoOutputStream.write(1108101562373L, powerManagerService.mScreenBrightnessMaximum);
                protoOutputStream.write(1108101562374L, powerManagerService.mScreenBrightnessDefault);
                protoOutputStream.end(start6);
                protoOutputStream.write(1133871366178L, powerManagerService.mDoubleTapWakeEnabled);
                protoOutputStream.end(start4);
                long attentiveTimeoutLocked = powerManagerService.getAttentiveTimeoutLocked();
                long sleepTimeoutLocked = powerManagerService.getSleepTimeoutLocked(attentiveTimeoutLocked);
                long screenOffTimeoutLocked = powerManagerService.getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
                long screenDimDurationLocked = powerManagerService.getScreenDimDurationLocked(screenOffTimeoutLocked);
                protoOutputStream.write(1172526071859L, attentiveTimeoutLocked);
                protoOutputStream.write(1172526071848L, sleepTimeoutLocked);
                protoOutputStream.write(1120986464297L, screenOffTimeoutLocked);
                protoOutputStream.write(1120986464298L, screenDimDurationLocked);
                protoOutputStream.write(1133871366187L, powerManagerService.mUidsChanging);
                protoOutputStream.write(1133871366188L, powerManagerService.mUidsChanged);
                for (int i4 = 0; i4 < powerManagerService.mUidState.size(); i4++) {
                    UidState uidState = (UidState) powerManagerService.mUidState.valueAt(i4);
                    long start7 = protoOutputStream.start(2246267895853L);
                    int keyAt = powerManagerService.mUidState.keyAt(i4);
                    protoOutputStream.write(1120986464257L, keyAt);
                    protoOutputStream.write(1138166333442L, UserHandle.formatUid(keyAt));
                    protoOutputStream.write(1133871366147L, uidState.mActive);
                    protoOutputStream.write(1120986464260L, uidState.mNumWakeLocks);
                    protoOutputStream.write(1159641169925L, ActivityManager.processStateAmToProto(uidState.mProcState));
                    protoOutputStream.end(start7);
                }
                if (powerManagerService.mBatterySaverSupported) {
                    powerManagerService.mBatterySaverStateMachine.dumpProto(protoOutputStream);
                }
                powerManagerService.mHandler.getLooper().dumpDebug(protoOutputStream, 1146756268078L);
                Iterator it = powerManagerService.mWakeLocks.iterator();
                while (it.hasNext()) {
                    ((WakeLock) it.next()).dumpDebug(protoOutputStream);
                }
                Iterator it2 = powerManagerService.mSuspendBlockers.iterator();
                while (it2.hasNext()) {
                    SuspendBlockerImpl suspendBlockerImpl = (SuspendBlockerImpl) it2.next();
                    suspendBlockerImpl.getClass();
                    long start8 = protoOutputStream.start(2246267895856L);
                    synchronized (suspendBlockerImpl) {
                        protoOutputStream.write(1138166333441L, suspendBlockerImpl.mName);
                        protoOutputStream.write(1120986464258L, suspendBlockerImpl.mReferenceCount);
                    }
                    protoOutputStream.end(start8);
                }
                wirelessChargerDetector = powerManagerService.mWirelessChargerDetector;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (wirelessChargerDetector != null) {
            long start9 = protoOutputStream.start(1146756268081L);
            synchronized (wirelessChargerDetector.mLock) {
                protoOutputStream.write(1133871366145L, wirelessChargerDetector.mPoweredWirelessly);
                protoOutputStream.write(1133871366146L, wirelessChargerDetector.mAtRest);
                long start10 = protoOutputStream.start(1146756268035L);
                protoOutputStream.write(1108101562369L, wirelessChargerDetector.mRestX);
                protoOutputStream.write(1108101562370L, wirelessChargerDetector.mRestY);
                protoOutputStream.write(1108101562371L, wirelessChargerDetector.mRestZ);
                protoOutputStream.end(start10);
                protoOutputStream.write(1133871366148L, wirelessChargerDetector.mDetectionInProgress);
                protoOutputStream.write(1112396529669L, wirelessChargerDetector.mDetectionStartTime);
                protoOutputStream.write(1133871366150L, wirelessChargerDetector.mMustUpdateRestPosition);
                protoOutputStream.write(1120986464263L, wirelessChargerDetector.mTotalSamples);
                protoOutputStream.write(1120986464264L, wirelessChargerDetector.mMovingSamples);
                long start11 = protoOutputStream.start(1146756268041L);
                protoOutputStream.write(1108101562369L, wirelessChargerDetector.mFirstSampleX);
                protoOutputStream.write(1108101562370L, wirelessChargerDetector.mFirstSampleY);
                protoOutputStream.write(1108101562371L, wirelessChargerDetector.mFirstSampleZ);
                protoOutputStream.end(start11);
                long start12 = protoOutputStream.start(1146756268042L);
                protoOutputStream.write(1108101562369L, wirelessChargerDetector.mLastSampleX);
                protoOutputStream.write(1108101562370L, wirelessChargerDetector.mLastSampleY);
                protoOutputStream.write(1108101562371L, wirelessChargerDetector.mLastSampleZ);
                protoOutputStream.end(start12);
            }
            protoOutputStream.end(start9);
        }
        powerManagerService.mLowPowerStandbyController.dumpProto(protoOutputStream);
        protoOutputStream.flush();
    }

    /* renamed from: -$$Nest$mforceSuspendInternal, reason: not valid java name */
    public static boolean m799$$Nest$mforceSuspendInternal(PowerManagerService powerManagerService, int i) {
        boolean nativeForceSuspend;
        synchronized (powerManagerService.mLock) {
            try {
                try {
                    powerManagerService.mForceSuspendActive = true;
                    for (int i2 = 0; i2 < powerManagerService.mPowerGroups.size(); i2++) {
                        PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.valueAt(i2);
                        powerManagerService.mClock.getClass();
                        powerManagerService.sleepPowerGroupLocked(powerGroup, SystemClock.uptimeMillis(), 8, i);
                    }
                    powerManagerService.updateWakeLockDisabledStatesLocked();
                    Slog.i("PowerManagerService", "Force-Suspending (uid " + i + ")...");
                    powerManagerService.mNativeWrapper.getClass();
                    nativeForceSuspend = nativeForceSuspend();
                    if (!nativeForceSuspend) {
                        Slog.i("PowerManagerService", "Force-Suspending failed in native.");
                    }
                    powerManagerService.mForceSuspendActive = false;
                    powerManagerService.updateWakeLockDisabledStatesLocked();
                } catch (Throwable th) {
                    powerManagerService.mForceSuspendActive = false;
                    powerManagerService.updateWakeLockDisabledStatesLocked();
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return nativeForceSuspend;
    }

    /* renamed from: -$$Nest$mgoToSleepInternal, reason: not valid java name */
    public static void m800$$Nest$mgoToSleepInternal(PowerManagerService powerManagerService, IntArray intArray, long j, int i, int i2) {
        powerManagerService.mClock.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        if (j > uptimeMillis) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Event time ", j, " cannot be newer than ");
            m.append(uptimeMillis);
            Slog.e("PowerManagerService", m.toString());
            throw new IllegalArgumentException("event time must not be in the future");
        }
        powerManagerService.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        powerManagerService.mScreenOffReason = " goToSleep: " + PowerManagerUtil.callerInfoToString(false);
        boolean z = (i2 & 1) != 0;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (powerManagerService.mLock) {
                for (int i3 = 0; i3 < intArray.size(); i3++) {
                    try {
                        int i4 = intArray.get(i3);
                        PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.get(i4);
                        if (powerGroup == null) {
                            throw new IllegalArgumentException("power group(" + i4 + ") doesn't exist");
                        }
                        if ((i2 & 2) != 0) {
                            if (powerManagerService.mFoldGracePeriodProvider.isEnabled()) {
                                if ((powerGroup.mWakeLockSummary & 38) == 0) {
                                    ((PhoneWindowManager) powerManagerService.mNotifier.mPolicy).showDismissibleKeyguard();
                                }
                            } else if ((powerGroup.mWakeLockSummary & 38) != 0) {
                            }
                        }
                        if (z) {
                            powerManagerService.sleepPowerGroupLocked(powerGroup, j, i, callingUid);
                        } else {
                            powerManagerService.dozePowerGroupLocked(powerGroup, j, i, callingUid);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: -$$Nest$mhandleSandman, reason: not valid java name */
    public static void m801$$Nest$mhandleSandman(PowerManagerService powerManagerService, int i) {
        boolean z;
        boolean z2;
        synchronized (powerManagerService.mLock) {
            try {
                powerManagerService.mSandmanScheduled = false;
                if (powerManagerService.mPowerGroups.contains(i)) {
                    PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.get(i);
                    int i2 = powerGroup.mWakefulness;
                    if (powerGroup.mIsSandmanSummoned && powerGroup.mReady) {
                        if (!powerManagerService.canDreamLocked(powerGroup) && (!powerGroup.mSupportsSandman || powerGroup.mWakefulness != 3)) {
                            z = false;
                            StringBuilder sb = new StringBuilder("handleSandman : startDreaming: ");
                            sb.append(z);
                            sb.append("  (canDreamLocked: ");
                            sb.append(powerManagerService.canDreamLocked(powerGroup));
                            sb.append("  canDozeLocked: ");
                            sb.append(!powerGroup.mSupportsSandman && powerGroup.mWakefulness == 3);
                            sb.append(")  groupId=");
                            sb.append(i);
                            Slog.d("PowerManagerService", sb.toString());
                            powerGroup.mIsSandmanSummoned = false;
                        }
                        z = true;
                        StringBuilder sb2 = new StringBuilder("handleSandman : startDreaming: ");
                        sb2.append(z);
                        sb2.append("  (canDreamLocked: ");
                        sb2.append(powerManagerService.canDreamLocked(powerGroup));
                        sb2.append("  canDozeLocked: ");
                        sb2.append(!powerGroup.mSupportsSandman && powerGroup.mWakefulness == 3);
                        sb2.append(")  groupId=");
                        sb2.append(i);
                        Slog.d("PowerManagerService", sb2.toString());
                        powerGroup.mIsSandmanSummoned = false;
                    } else {
                        z = false;
                    }
                    DreamManagerInternal dreamManagerInternal = powerManagerService.mDreamManager;
                    if (dreamManagerInternal != null) {
                        if (z) {
                            dreamManagerInternal.stopDream(false, "power manager request before starting dream");
                            StringBuilder sb3 = new StringBuilder("handleSandman : startDream(");
                            sb3.append(i2 == 3);
                            sb3.append(")");
                            Slog.d("PowerManagerService", sb3.toString());
                            powerManagerService.mDreamManager.startDream(i2 == 3, "power manager request");
                        }
                        z2 = powerManagerService.mDreamManager.isDreaming();
                    } else {
                        z2 = false;
                    }
                    if (z && !z2) {
                        Slog.e("PowerManagerService", "handleSandman : startDreaming, but isDreaming false");
                    }
                    if (!PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING) {
                        powerManagerService.mDozeStartInProgress = false;
                    }
                    synchronized (powerManagerService.mLock) {
                        try {
                            if (powerManagerService.mPowerGroups.contains(i)) {
                                if (z && z2) {
                                    powerManagerService.mDreamsBatteryLevelDrain = 0;
                                    if (i2 == 3) {
                                        Slog.i("PowerManagerService", "Dozing...");
                                    } else {
                                        Slog.i("PowerManagerService", "Dreaming...");
                                    }
                                }
                                PowerGroup powerGroup2 = (PowerGroup) powerManagerService.mPowerGroups.get(i);
                                if (!powerGroup2.mIsSandmanSummoned && powerGroup2.mWakefulness == i2) {
                                    powerManagerService.mClock.getClass();
                                    long uptimeMillis = SystemClock.uptimeMillis();
                                    if (i2 == 2) {
                                        if (z2 && powerManagerService.canDreamLocked(powerGroup2)) {
                                            int i3 = powerManagerService.mDreamsBatteryLevelDrainCutoffConfig;
                                            if (i3 < 0 || powerManagerService.mDreamsBatteryLevelDrain <= i3 || powerManagerService.isBeingKeptAwakeLocked(powerGroup2)) {
                                                return;
                                            }
                                            Slog.i("PowerManagerService", "Stopping dream because the battery appears to be draining faster than it is charging.  Battery level drained while dreaming: " + powerManagerService.mDreamsBatteryLevelDrain + "%.  Battery level now: " + powerManagerService.mBatteryLevel + "%.");
                                        }
                                        if (powerManagerService.isItBedTimeYetLocked(powerGroup2)) {
                                            powerManagerService.mScreenOffReason = " dream(timeout)";
                                            if (powerManagerService.isAttentiveTimeoutExpired(powerGroup2, uptimeMillis)) {
                                                powerManagerService.sleepPowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                                            } else {
                                                powerManagerService.dozePowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                                            }
                                        } else {
                                            powerManagerService.mScreenOnReason = " dream end";
                                            powerManagerService.mLastWakeUpReason = 108;
                                            powerManagerService.wakePowerGroupLocked(powerGroup2, uptimeMillis, 13, "android.server.power:DREAM_FINISHED", 1000, powerManagerService.mContext.getOpPackageName(), 1000, false);
                                        }
                                    } else if (i2 == 3) {
                                        if (z2) {
                                            return;
                                        } else {
                                            powerManagerService.sleepPowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                                        }
                                    }
                                    if (z2) {
                                        Slog.d("PowerManagerService", "handleSandman : stopDream");
                                        powerManagerService.mDreamManager.stopDream(false, "power manager request");
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mhandleUserActivityTimeout, reason: not valid java name */
    public static void m802$$Nest$mhandleUserActivityTimeout(PowerManagerService powerManagerService) {
        synchronized (powerManagerService.mLock) {
            try {
                if (powerManagerService.mSmartStayEnabledSetting) {
                    SmartStayController smartStayController = powerManagerService.mSmartStayController;
                    if (smartStayController.mFaceDetected) {
                        Slog.d("SmartStayController", "UserActivityState : poke userActivity (face detected)");
                        smartStayController.mOnFaceDetected.run();
                    }
                    if (smartStayController.mFaceDetectRequested.get()) {
                        smartStayController.mFaceDetectRequested.set(false);
                        smartStayController.mFaceDetected = false;
                    }
                }
                powerManagerService.mDirty |= 4;
                powerManagerService.updatePowerStateLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$misInteractiveForDisplayInternal, reason: not valid java name */
    public static boolean m803$$Nest$misInteractiveForDisplayInternal(PowerManagerService powerManagerService, int i, int i2) {
        boolean isInteractive;
        synchronized (powerManagerService.mLock) {
            try {
                if (powerManagerService.mSystemReady && i != -1) {
                    DisplayInfo displayInfo = powerManagerService.mDisplayManagerInternal.getDisplayInfo(i);
                    int i3 = displayInfo != null ? displayInfo.displayGroupId : -1;
                    int appId = UserHandle.getAppId(i2);
                    if ((UserHandle.isApp(i2) || appId == 1001) && i3 != -1) {
                        int wakefulnessLocked = powerManagerService.getWakefulnessLocked(i3);
                        boolean isInteractive2 = PowerManagerInternal.isInteractive(powerManagerService.getGlobalWakefulnessLocked());
                        isInteractive = PowerManagerInternal.isInteractive(wakefulnessLocked);
                        if (isInteractive2 != isInteractive) {
                            Slog.d("PowerManagerService", "isInteractiveInternal(): uid=" + i2 + ", interactive=" + isInteractive2 + ", interactive(internal)=" + isInteractive);
                        }
                    } else {
                        isInteractive = PowerManagerInternal.isInteractive(powerManagerService.getGlobalWakefulnessLocked());
                    }
                }
                isInteractive = PowerManagerInternal.isInteractive(powerManagerService.getGlobalWakefulnessLocked());
            } finally {
            }
        }
        return isInteractive;
    }

    /* renamed from: -$$Nest$misInteractiveInternal, reason: not valid java name */
    public static boolean m804$$Nest$misInteractiveInternal(PowerManagerService powerManagerService, int i, int i2) {
        boolean z;
        synchronized (powerManagerService.mLock) {
            try {
                DisplayInfo displayInfo = powerManagerService.mDisplayManagerInternal.getDisplayInfo(i);
                z = false;
                if (displayInfo == null) {
                    Slog.w("PowerManagerService", "Did not find DisplayInfo for displayId " + i);
                } else {
                    if (!displayInfo.hasAccess(i2)) {
                        throw new SecurityException("uid " + i2 + " does not have access to display " + i);
                    }
                    PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.get(displayInfo.displayGroupId);
                    if (powerGroup == null) {
                        Slog.w("PowerManagerService", "Did not find PowerGroup for displayId " + i);
                    } else {
                        z = PowerManagerInternal.isInteractive(powerGroup.mWakefulness);
                    }
                }
            } finally {
            }
        }
        return z;
    }

    /* renamed from: -$$Nest$misWakeLockLevelSupportedInternal, reason: not valid java name */
    public static boolean m805$$Nest$misWakeLockLevelSupportedInternal(PowerManagerService powerManagerService, int i) {
        synchronized (powerManagerService.mLock) {
            boolean z = true;
            try {
                if (i != 1 && i != 6 && i != 10 && i != 26) {
                    if (i == 32) {
                        if (!powerManagerService.mSystemReady || !powerManagerService.mDisplayManagerInternal.isProximitySensorAvailable()) {
                            z = false;
                        }
                        return z;
                    }
                    if (i != 64 && i != 128) {
                        if (i != 256) {
                            return false;
                        }
                        if (!powerManagerService.mSystemReady || !powerManagerService.mFeatureFlags.mEarlyScreenTimeoutDetectorFlagState.isEnabled() || powerManagerService.mScreenTimeoutOverridePolicy == null) {
                            z = false;
                        }
                        return z;
                    }
                }
                return true;
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mprintWakeLockLocked, reason: not valid java name */
    public static void m807$$Nest$mprintWakeLockLocked(PowerManagerService powerManagerService) {
        long elapsedRealtime;
        boolean z;
        PowerGroup powerGroup = (PowerGroup) powerManagerService.mPowerGroups.get(0);
        int i = powerGroup.mUserActivitySummary;
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = powerGroup.mDisplayPowerRequest;
        boolean z2 = powerGroup.mReady;
        if (!powerManagerService.mSystemReady || displayPowerRequest.isBrightOrDim()) {
            Slog.i("PowerManagerService", "[PWL] On : " + TimeUtils.formatUptime(powerManagerService.mLastGlobalWakeTime));
            Slog.i("PowerManagerService", "[PWL]  mStayOn: " + powerManagerService.mStayOn + "  mWakeLockSummary & WAKE_LOCK_STAY_AWAKE: " + Integer.toHexString(powerManagerService.mWakeLockSummary & 32) + "  mUserActivitySummary: 0x" + Integer.toHexString(i));
            if ((powerManagerService.mWakeLockSummary & 32) != 0) {
                Iterator it = powerManagerService.mWakeLocks.iterator();
                while (it.hasNext()) {
                    WakeLock wakeLock = (WakeLock) it.next();
                    if (isScreenLock(wakeLock)) {
                        Slog.i("PowerManagerService", "[PWL]  " + wakeLock);
                    }
                }
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder("[PWL] Off : ");
        synchronized (powerManagerService.mLock) {
            try {
                if (PowerManagerInternal.isInteractive(((PowerGroup) powerManagerService.mPowerGroups.get(0)).mWakefulness)) {
                    elapsedRealtime = 0;
                } else {
                    elapsedRealtime = SystemClock.elapsedRealtime();
                }
            } finally {
            }
        }
        sb.append(elapsedRealtime / 1000);
        sb.append("s ago");
        Slog.i("PowerManagerService", sb.toString());
        Iterator it2 = powerManagerService.mSuspendBlockers.iterator();
        while (it2.hasNext()) {
            SuspendBlockerImpl suspendBlockerImpl = (SuspendBlockerImpl) it2.next();
            synchronized (suspendBlockerImpl) {
                z = suspendBlockerImpl.mReferenceCount != 0;
            }
            if (z) {
                Slog.i("PowerManagerService", "[PWL]   " + suspendBlockerImpl);
                if (suspendBlockerImpl.equals(powerManagerService.mWakeLockSuspendBlocker)) {
                    if ((powerManagerService.mWakeLockSummary & (-17)) != 0) {
                        Slog.i("PowerManagerService", "[PWL]     mWakeLockSummary : 0x" + Integer.toHexString(powerManagerService.mWakeLockSummary));
                        if ((powerManagerService.mWakeLockSummary & 1) != 0) {
                            Iterator it3 = powerManagerService.mWakeLocks.iterator();
                            while (it3.hasNext()) {
                                WakeLock wakeLock2 = (WakeLock) it3.next();
                                if ((wakeLock2.mFlags & 129) != 0) {
                                    Slog.i("PowerManagerService", "[PWL]       " + wakeLock2);
                                }
                            }
                        }
                        if ((powerManagerService.mWakeLockSummary & (-2)) != 0) {
                            Iterator it4 = powerManagerService.mWakeLocks.iterator();
                            while (it4.hasNext()) {
                                WakeLock wakeLock3 = (WakeLock) it4.next();
                                if (isScreenLock(wakeLock3)) {
                                    Slog.e("PowerManagerService", "[PWL]       " + wakeLock3);
                                }
                            }
                        }
                    }
                } else if (suspendBlockerImpl.equals(powerManagerService.mDisplaySuspendBlocker)) {
                    if (i != 0) {
                        Slog.i("PowerManagerService", "[PWL]     userActivitySummary : 0x" + Integer.toHexString(i));
                    }
                    if (displayPowerRequest.policy != 0) {
                        Slog.i("PowerManagerService", "[PWL]     displayPowerRequest.policy : " + displayPowerRequest.policy);
                    }
                    if (!z2) {
                        Slog.i("PowerManagerService", "[PWL]     displayReady : " + z2);
                    }
                    if (!powerManagerService.mBootCompleted) {
                        Slog.i("PowerManagerService", "[PWL]     mBootCompleted : " + powerManagerService.mBootCompleted);
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mreleaseWakeLockInternal, reason: not valid java name */
    public static void m808$$Nest$mreleaseWakeLockInternal(PowerManagerService powerManagerService, IBinder iBinder, int i) {
        int i2;
        synchronized (powerManagerService.mLock) {
            try {
                int findWakeLockIndexLocked = powerManagerService.findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked < 0) {
                    return;
                }
                WakeLock wakeLock = (WakeLock) powerManagerService.mWakeLocks.get(findWakeLockIndexLocked);
                int i3 = i & 1;
                if (i3 != 0) {
                    powerManagerService.mRequestWaitForNegativeProximity = true;
                }
                PowerHistorian powerHistorian = powerManagerService.mPowerHistorian;
                powerHistorian.getClass();
                int i4 = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
                if (i4 == 6 || i4 == 10 || i4 == 26) {
                    i2 = i3;
                    long uptimeMillis = SystemClock.uptimeMillis() - wakeLock.mAcquireTime;
                    if (uptimeMillis >= 60000) {
                        powerHistorian.addRecord(0, new PowerHistorian.WakeLockReleaseRecord(wakeLock, uptimeMillis));
                    }
                } else {
                    i2 = i3;
                }
                int i5 = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
                if (i5 == 6 || i5 == 10 || i5 == 26 || i5 == 32 || i5 == 64 || i5 == 128) {
                    Slog.d("PowerManagerService", "[api] release WakeLock " + wakeLock + (i2 != 0 ? " (WAIT_FOR_NO_PROXIMITY)" : ""));
                }
                try {
                    wakeLock.mLock.unlinkToDeath(wakeLock, 0);
                } catch (NoSuchElementException e) {
                    int i6 = Slog.$r8$clinit;
                    android.util.Slog.wtf("PowerManagerService", "Failed to unlink Wakelock.mLock", e);
                }
                if (!wakeLock.mDisabled) {
                    wakeLock.mDisabled = true;
                    wakeLock.mDisableReason = 0;
                }
                powerManagerService.removeWakeLockNoUpdateLocked(wakeLock, findWakeLockIndexLocked, -1);
                powerManagerService.updatePowerStateLocked();
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetDozeOverrideFromDreamManagerInternal, reason: not valid java name */
    public static void m809$$Nest$msetDozeOverrideFromDreamManagerInternal(PowerManagerService powerManagerService, int i, int i2, int i3, boolean z) {
        synchronized (powerManagerService.mLock) {
            try {
                if (powerManagerService.mDozeScreenStateOverrideFromDreamManager == i) {
                    if (powerManagerService.mDozeScreenBrightnessOverrideFromDreamManager == i3) {
                        if (powerManagerService.mShouldWaitForTransitionToAodUi != z) {
                        }
                    }
                }
                powerManagerService.mDozeScreenStateOverrideFromDreamManager = i;
                powerManagerService.mDozeScreenStateOverrideReasonFromDreamManager = i2;
                powerManagerService.mDozeScreenBrightnessOverrideFromDreamManager = i3;
                powerManagerService.mDozeScreenBrightnessOverrideFromDreamManagerFloat = BrightnessSynchronizer.brightnessIntToFloat(i3);
                powerManagerService.mShouldWaitForTransitionToAodUi = z;
                powerManagerService.mDirty |= 32;
                powerManagerService.updatePowerStateLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$msetHdrBrightnessLimitInternal, reason: not valid java name */
    public static void m810$$Nest$msetHdrBrightnessLimitInternal(PowerManagerService powerManagerService, IBinder iBinder, int i, int i2, int i3, int i4) {
        synchronized (powerManagerService.mLock) {
            try {
                Slog.d("PowerManagerService", "[api] setHdrBrightnessLimit : upperLimit : " + i + "  brightnessLimitPeriod : " + i2 + PowerManagerUtil.callerInfoToString(false));
                int size = powerManagerService.mHdrBrightnessLimitLocks.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size) {
                        i5 = -1;
                        break;
                    } else if (((HdrBrightnessLimitLock) powerManagerService.mHdrBrightnessLimitLocks.get(i5)).mLock == iBinder) {
                        break;
                    } else {
                        i5++;
                    }
                }
                if (i != -1) {
                    if (i5 >= 0) {
                        HdrBrightnessLimitLock hdrBrightnessLimitLock = (HdrBrightnessLimitLock) powerManagerService.mHdrBrightnessLimitLocks.get(i5);
                        hdrBrightnessLimitLock.mUpperLimit = i;
                        hdrBrightnessLimitLock.mBrightnessLimitPeriod = i2;
                    } else {
                        powerManagerService.mHdrBrightnessLimitLocks.add(powerManagerService.new HdrBrightnessLimitLock(iBinder, i, i2, i3, i4));
                    }
                } else if (i5 >= 0) {
                    powerManagerService.mHdrBrightnessLimitLocks.remove(i5);
                }
                powerManagerService.calculateHdrBrightnessLimitLocked(i, i2);
                if (powerManagerService.mHdrBrightnessLimitPeriod > 0) {
                    int i6 = powerManagerService.mHdrBrightnessUpperLimit;
                    if (i6 == -1) {
                        i6 = powerManagerService.mScreenExtendedBrightnessMaximum;
                    }
                    powerManagerService.mTargetBrightnessForHdrLimit = i6;
                    int startingLimitationOfBrightness = powerManagerService.getStartingLimitationOfBrightness(i6, powerManagerService.mLastRequestedLimitationOfHdrBrightness);
                    powerManagerService.mLastRequestedLimitationOfHdrBrightness = startingLimitationOfBrightness;
                    if (powerManagerService.mTargetBrightnessForHdrLimit == startingLimitationOfBrightness) {
                        powerManagerService.mHdrBrightnessLimitRunning = false;
                        powerManagerService.mHandler.removeCallbacks(powerManagerService.mHdrBrightnessLimitRunnable);
                        powerManagerService.mDirty |= 32;
                        powerManagerService.updatePowerStateLocked();
                    } else {
                        powerManagerService.mHdrBrightnessLimitRunning = true;
                        powerManagerService.mHandler.removeCallbacks(powerManagerService.mHdrBrightnessLimitRunnable);
                        Handler handler = powerManagerService.mHandler;
                        AnonymousClass6 anonymousClass6 = powerManagerService.mHdrBrightnessLimitRunnable;
                        powerManagerService.mClock.getClass();
                        handler.postAtTime(anonymousClass6, SystemClock.uptimeMillis());
                    }
                } else {
                    powerManagerService.mHdrBrightnessLimitRunning = false;
                    powerManagerService.mLastRequestedLimitationOfHdrBrightness = powerManagerService.mHdrBrightnessUpperLimit;
                    powerManagerService.mHandler.removeCallbacks(powerManagerService.mHdrBrightnessLimitRunnable);
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$msetLowPowerModeInternal, reason: not valid java name */
    public static void m811$$Nest$msetLowPowerModeInternal(PowerManagerService powerManagerService, boolean z) {
        synchronized (powerManagerService.mLock) {
            Slog.d("PowerManagerService", "setLowPowerModeInternal " + z + " mIsPowered=" + powerManagerService.mIsPowered);
            BatterySaverStateMachine batterySaverStateMachine = powerManagerService.mBatterySaverStateMachine;
            batterySaverStateMachine.getClass();
            Slog.d("BatterySaverStateMachine", "setBatterySaverEnabledManually: enabled=" + z);
            synchronized (batterySaverStateMachine.mLock) {
                batterySaverStateMachine.updateStateLocked(z);
            }
        }
    }

    /* renamed from: -$$Nest$msetScreenOffTimeoutForBatterySaver, reason: not valid java name */
    public static void m812$$Nest$msetScreenOffTimeoutForBatterySaver(PowerManagerService powerManagerService) {
        if (!powerManagerService.mBatterySaverSupported || !powerManagerService.mBatterySaverStateMachine.mBatterySaverController.isEnabled() || powerManagerService.mBatterySavingScreenOffTimeoutSetting <= 0 || powerManagerService.mScreenOffTimeoutSetting == 30000) {
            return;
        }
        Slog.d("PowerManagerService", "setScreenOffTimeoutForBatterySaver: backup_screen_off_timeout = " + powerManagerService.mScreenOffTimeoutSetting + ", SCREEN_OFF_TIMEOUT = 30000");
        Settings.System.putIntForUser(powerManagerService.mContext.getContentResolver(), "backup_screen_off_timeout", (int) powerManagerService.mScreenOffTimeoutSetting, -2);
        Settings.System.putIntForUser(powerManagerService.mContext.getContentResolver(), "screen_off_timeout", 30000, -2);
    }

    /* renamed from: -$$Nest$mshutdownOrRebootInternal, reason: not valid java name */
    public static void m813$$Nest$mshutdownOrRebootInternal(PowerManagerService powerManagerService, final int i, final boolean z, final String str, boolean z2) {
        powerManagerService.getClass();
        if ("userspace".equals(str)) {
            if (!PowerManager.isRebootingUserspaceSupportedImpl()) {
                throw new UnsupportedOperationException("Attempted userspace reboot on a device that doesn't support it");
            }
            if (PowerManager.isRebootingUserspaceSupportedImpl()) {
                SystemProperties.set("persist.sys.userspace_reboot.log.should_log", "1");
                SystemProperties.set("sys.userspace_reboot.log.last_started", String.valueOf(SystemClock.elapsedRealtime()));
            } else {
                android.util.Slog.wtf("UserspaceRebootLogger", "noteUserspaceRebootWasRequested: Userspace reboot is not supported.");
            }
        }
        if (powerManagerService.mHandler == null || !powerManagerService.mSystemReady) {
            int i2 = RescueParty.LEVEL_ISRB_BOOT;
            if (!((Boolean) CrashRecoveryProperties.attemptingFactoryReset().orElse(Boolean.FALSE)).booleanValue() && !RescueParty.isRebootPropertySet()) {
                throw new IllegalStateException("Too early to call shutdown() or reboot()");
            }
            lowLevelReboot(str);
        }
        Runnable runnable = new Runnable() { // from class: com.android.server.power.PowerManagerService.4
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (this) {
                    try {
                        if ("silent.sec".equals(str)) {
                            throw new NullPointerException("NPE by silent reset. It's normal operation caused by device care");
                        }
                        int i3 = i;
                        if (i3 == 2) {
                            ShutdownThread.rebootSafeMode(PowerManagerService.this.getUiContext());
                        } else if (i3 == 1) {
                            ShutdownThread.reboot(PowerManagerService.this.getUiContext(), str, z);
                        } else {
                            ShutdownThread.shutdown(PowerManagerService.this.getUiContext(), str, z);
                        }
                    } catch (Throwable th) {
                        throw th;
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

    /* renamed from: -$$Nest$mupdateWakeLockCallbackInternal, reason: not valid java name */
    public static void m814$$Nest$mupdateWakeLockCallbackInternal(PowerManagerService powerManagerService, IBinder iBinder, IWakeLockCallback iWakeLockCallback, int i) {
        synchronized (powerManagerService.mLock) {
            try {
                int findWakeLockIndexLocked = powerManagerService.findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked < 0) {
                    throw new IllegalArgumentException("Wake lock not active: " + iBinder + " from uid " + i);
                }
                WakeLock wakeLock = (WakeLock) powerManagerService.mWakeLocks.get(findWakeLockIndexLocked);
                IWakeLockCallback iWakeLockCallback2 = wakeLock.mCallback;
                if (iWakeLockCallback != iWakeLockCallback2 && (iWakeLockCallback == null || iWakeLockCallback2 == null || iWakeLockCallback.asBinder() != iWakeLockCallback2.asBinder())) {
                    powerManagerService.notifyWakeLockChangingLocked(wakeLock, wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mWorkSource, wakeLock.mHistoryTag, iWakeLockCallback);
                    wakeLock.mCallback = iWakeLockCallback;
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mupdateWakeLockWorkSourceInternal, reason: not valid java name */
    public static void m815$$Nest$mupdateWakeLockWorkSourceInternal(PowerManagerService powerManagerService, IBinder iBinder, WorkSource workSource, String str, int i) {
        synchronized (powerManagerService.mLock) {
            try {
                int findWakeLockIndexLocked = powerManagerService.findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked < 0) {
                    throw new IllegalArgumentException("Wake lock not active: " + iBinder + " from uid " + i);
                }
                WakeLock wakeLock = (WakeLock) powerManagerService.mWakeLocks.get(findWakeLockIndexLocked);
                if (!Objects.equals(wakeLock.mWorkSource, workSource)) {
                    powerManagerService.notifyWakeLockChangingLocked(wakeLock, wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, workSource, str, null);
                    wakeLock.mHistoryTag = str;
                    wakeLock.mWorkSource = workSource != null ? new WorkSource(workSource) : null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$smcallerInfoWithProcessName, reason: not valid java name */
    public static String m816$$Nest$smcallerInfoWithProcessName() {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format("/proc/%d/cmdline", Integer.valueOf(callingPid))));
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
            Slog.e("PowerManagerService", "error on read process name", e);
        }
        return String.format(" (uid: %d pid: %d processName: %s)", Integer.valueOf(callingUid), Integer.valueOf(callingPid), str);
    }

    public PowerManagerService(Context context) {
        this(context, new Injector());
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.power.PowerManagerService$6] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.power.PowerManagerService$6] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.server.power.PowerManagerService$3] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.server.power.PowerManagerService$6] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.power.PowerManagerService$6] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.server.power.PowerManagerService$10] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.server.power.PowerManagerService$6] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.server.power.PowerManagerService$6] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.server.power.PowerManagerService$6] */
    public PowerManagerService(Context context, Injector injector) {
        super(context);
        boolean z;
        this.mUserActivityTimeoutSetting = 0;
        final int i = 6;
        this.mUserActivityTask = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i2 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i3 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i2 == i3) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i4 = i2 + (i2 > i3 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i4;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i4;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i5 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i6 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i5 == i6) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i7 = i5 + (i5 > i6 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i7;
                                powerManagerService5.mHdrBrightnessUpperLimit = i7;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        final int i2 = 7;
        this.mUserActivityTimeoutTask = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i3 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i3) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i4 = i22 + (i22 > i3 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i4;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i4;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i5 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i6 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i5 == i6) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i7 = i5 + (i5 > i6 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i7;
                                powerManagerService5.mHdrBrightnessUpperLimit = i7;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        Object installNewLock = LockGuard.installNewLock(1, false);
        this.mLock = installNewLock;
        this.mSuspendBlockers = new ArrayList();
        this.mWakeLocks = new ArrayList();
        this.mAutoBrightnessLowerLimit = -1;
        this.mAutoBrightnessUpperLimit = -1;
        this.mAutoBrightnessLimitLastCaller = "";
        this.mSuspendBlockerMonitor = SuspendBlockerMonitor.GLOBAL_INSTANCE;
        this.mEnhancedDischargeTimeLock = new Object();
        this.mDockState = 0;
        this.mMaximumScreenOffTimeoutFromDeviceAdmin = Long.MAX_VALUE;
        this.mDefaultDisplayReadyByProximity = true;
        this.mScreenBrightnessOverrideFromWindowManager = Float.NaN;
        this.mScreenBrightnessOverridePackageFromWindowManager = "";
        this.mOverriddenTimeout = -1L;
        this.mUserActivityTimeoutOverrideFromWindowManager = -1L;
        this.mUserActivityTimeoutForDexOverrideFromWindowManager = -1L;
        this.mDozeScreenStateOverrideFromDreamManager = 0;
        this.mDozeScreenStateOverrideReasonFromDreamManager = 0;
        this.mDozeScreenBrightnessOverrideFromDreamManager = -1;
        this.mDozeScreenBrightnessOverrideFromDreamManagerFloat = Float.NaN;
        this.mLastWarningAboutUserActivityPermission = Long.MIN_VALUE;
        this.mDeviceIdleWhitelist = new int[0];
        this.mDeviceIdleTempWhitelist = new int[0];
        this.mLowPowerStandbyAllowlist = new int[0];
        this.mUidState = new SparseArray();
        this.mPowerGroups = new SparseArray();
        this.mUseScreenCurtain = false;
        this.mUseKeepScreenOn = false;
        this.mUseAdaptiveScreenOffTimeout = false;
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
        this.mIsPowerBoostInvokedFromLocal = new AtomicBoolean();
        this.mMasterBrightnessLowerLimit = -1;
        this.mMasterBrightnessUpperLimit = -1;
        this.mMasterBrightnessLimitLastCaller = "";
        this.mLastRequestedLimitationOfBrightness = 485;
        this.mTargetBrightnessForLimit = 0;
        this.mMasterBrightnessLimitRunning = false;
        this.mMasterBrightnessLimitPeriod = 0;
        this.mHdrBrightnessUpperLimit = -1;
        this.mLastRequestedLimitationOfHdrBrightness = 485;
        this.mTargetBrightnessForHdrLimit = 0;
        this.mHdrBrightnessLimitRunning = false;
        this.mHdrBrightnessLimitPeriod = 0;
        this.mHdrBrightnessLimitLocks = new ArrayList();
        this.mBatteryOnline = -1;
        this.mUserActivityStateListenerListeners = new ArrayList();
        this.mDualScreenPolicy = -1;
        this.mPowerHistorian = PowerHistorian.INSTANCE;
        this.mSemInputDeviceManager = null;
        this.mDefaultDisplayState = 0;
        this.mWakeUpPreventionLocks = new ArrayList();
        this.mProfilePowerState = new SparseArray();
        this.mLastAndroidAutoBoostTime = -1L;
        this.mLastNormalTouchBoostTime = -1L;
        this.mLastWakeUpReason = 0;
        this.mAbuseWakeLockDetectorCallback = new AnonymousClass1();
        this.mAdaptiveScreenOffTimeoutCallbacks = new AnonymousClass1();
        this.mDisplayPowerCallbacks = new DisplayManagerInternal.DisplayPowerCallbacks() { // from class: com.android.server.power.PowerManagerService.3
            public final void acquireSuspendBlocker(String str) {
                PowerManagerService.this.mDisplaySuspendBlocker.acquire(str);
                SuspendBlockerMonitor suspendBlockerMonitor = PowerManagerService.this.mSuspendBlockerMonitor;
                suspendBlockerMonitor.mEvents |= 256;
                suspendBlockerMonitor.mDetail = str;
                if (str.contains("unfinished") || str.contains("on state")) {
                    return;
                }
                SuspendBlockerMonitor.addNewCallbackEvent(1, str);
            }

            public final void onDefaultDisplayStateChange(int i3) {
                synchronized (PowerManagerService.this.mLock) {
                    try {
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        powerManagerService.mDefaultDisplayState = i3;
                        if (!powerManagerService.mDefaultDisplayReadyByProximity && i3 == 2) {
                            Slog.d("PowerManagerService", "onDisplayStateChange: mDefaultDisplayReadyByProximity: true");
                            PowerManagerService.this.mDefaultDisplayReadyByProximity = true;
                        }
                        PowerManagerService powerManagerService2 = PowerManagerService.this;
                        powerManagerService2.mDirty |= 8;
                        powerManagerService2.updatePowerStateLocked();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onDisplayStateChange(boolean z2, boolean z3) {
                synchronized (PowerManagerService.this.mLock) {
                    try {
                        PowerManagerService.this.setPowerModeInternal(9, z2);
                        if (z3) {
                            PowerManagerService powerManagerService = PowerManagerService.this;
                            if (!powerManagerService.mDecoupleHalInteractiveModeFromDisplayConfig) {
                                powerManagerService.setHalInteractiveModeLocked(false);
                            }
                            PowerManagerService powerManagerService2 = PowerManagerService.this;
                            if (!powerManagerService2.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
                                powerManagerService2.setHalAutoSuspendModeLocked(true);
                            }
                        } else {
                            PowerManagerService powerManagerService3 = PowerManagerService.this;
                            if (!powerManagerService3.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
                                powerManagerService3.setHalAutoSuspendModeLocked(false);
                            }
                            PowerManagerService powerManagerService4 = PowerManagerService.this;
                            if (!powerManagerService4.mDecoupleHalInteractiveModeFromDisplayConfig) {
                                powerManagerService4.setHalInteractiveModeLocked(true);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onProximityNegative() {
                synchronized (PowerManagerService.this.mLock) {
                    try {
                        if (PowerManagerService.this.mProximityPositive) {
                            Slog.d("PowerManagerService", "DisplayPowerCallbacks : onProximityNegative()");
                            PowerManagerService powerManagerService = PowerManagerService.this;
                            if (powerManagerService.mDefaultDisplayState != 2 && ((PowerGroup) powerManagerService.mPowerGroups.get(0)).mWakefulness == 1) {
                                Slog.d("PowerManagerService", "onProximityNegative: mDisplayDefaultReadyByProximity: false");
                                PowerManagerService.this.mDefaultDisplayReadyByProximity = false;
                            }
                            if (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && PowerManagerService.this.mDualScreenPolicy == 1) {
                                Slog.d("PowerManagerService", "onProximityNegative() ignore DefaultDispalyReady cover screen");
                                PowerManagerService.this.mDefaultDisplayReadyByProximity = true;
                            }
                            if (((PowerGroup) PowerManagerService.this.mPowerGroups.get(0)).mWakefulness == 1) {
                                PowerManagerService powerManagerService2 = PowerManagerService.this;
                                if (!powerManagerService2.mIsDualViewMode) {
                                    powerManagerService2.mNotifier.onScreenStateChangeStartedByProximity(false);
                                }
                            }
                            PowerManagerService powerManagerService3 = PowerManagerService.this;
                            powerManagerService3.mProximityPositive = false;
                            powerManagerService3.mInterceptedPowerKeyForProximity = false;
                            powerManagerService3.mInterceptedKeyForProximity = false;
                            if (((PowerGroup) powerManagerService3.mPowerGroups.get(0)).mWakefulness != 1) {
                                PowerManagerService powerManagerService4 = PowerManagerService.this;
                                powerManagerService4.mLastWakeUpReason = 109;
                                powerManagerService4.mScreenOnReason = " proximity negative";
                                PowerGroup powerGroup = (PowerGroup) powerManagerService4.mPowerGroups.get(0);
                                PowerManagerService.this.mClock.getClass();
                                powerManagerService4.wakePowerGroupLocked(powerGroup, SystemClock.uptimeMillis(), 109, "android.server.power:PROXIMITY", 1000, PowerManagerService.this.mContext.getOpPackageName(), 1000, false);
                            }
                            PowerManagerService powerManagerService5 = PowerManagerService.this;
                            powerManagerService5.mDirty |= 512;
                            PowerGroup powerGroup2 = (PowerGroup) powerManagerService5.mPowerGroups.get(0);
                            PowerManagerService.this.mClock.getClass();
                            powerManagerService5.userActivityNoUpdateLocked(powerGroup2, SystemClock.uptimeMillis(), 0, 0, 1000);
                            PowerManagerService.this.updatePowerStateLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onProximityPositive() {
                synchronized (PowerManagerService.this.mLock) {
                    try {
                        if (PowerManagerService.this.mProximityPositive) {
                            return;
                        }
                        Slog.d("PowerManagerService", "DisplayPowerCallbacks : onProximityPositive()");
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        if (!powerManagerService.mIsDualViewMode) {
                            powerManagerService.mNotifier.onScreenStateChangeStartedByProximity(true);
                        }
                        PowerManagerService powerManagerService2 = PowerManagerService.this;
                        powerManagerService2.mProximityPositive = true;
                        powerManagerService2.mDirty |= 512;
                        powerManagerService2.updatePowerStateLocked();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onStateChanged() {
                synchronized (PowerManagerService.this.mLock) {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    powerManagerService.mDirty |= 8;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            public final void releaseSuspendBlocker(String str) {
                PowerManagerService.this.mDisplaySuspendBlocker.release(str);
                PowerManagerService.this.mSuspendBlockerMonitor.mEvents &= -3841;
                if (str.contains("unfinished") || str.contains("on state")) {
                    return;
                }
                SuspendBlockerMonitor.addNewCallbackEvent(2, str);
            }
        };
        this.mLastUserActivityStateListenerCalledTime = -1L;
        this.mLastUserActivityStateListenerState = 0;
        final int i3 = 0;
        this.mUserActivityStateListenerTimeoutRunnable = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i32 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i32) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i4 = i22 + (i22 > i32 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i4;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i4;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i5 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i6 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i5 == i6) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i7 = i5 + (i5 > i6 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i7;
                                powerManagerService5.mHdrBrightnessUpperLimit = i7;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        final int i4 = 4;
        this.mEnsureTransitionToDozingReleaser = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i4) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i32 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i32) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i42 = i22 + (i22 > i32 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i42;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i42;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i5 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i6 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i5 == i6) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i7 = i5 + (i5 > i6 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i7;
                                powerManagerService5.mHdrBrightnessUpperLimit = i7;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        final int i5 = 5;
        this.mCoverAuthReadyRunnable = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i5) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i32 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i32) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i42 = i22 + (i22 > i32 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i42;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i42;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i52 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i6 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i52 == i6) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i7 = i52 + (i52 > i6 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i7;
                                powerManagerService5.mHdrBrightnessUpperLimit = i7;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        this.mCoverStateListener = new CoverManager.CoverStateListener() { // from class: com.android.server.power.PowerManagerService.10
            public final void onCoverAttachStateChanged(boolean z2) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mFeatureCoverSysfs) {
                    try {
                        if (z2) {
                            CoverState coverState = powerManagerService.mCoverManager.getCoverState();
                            if (coverState != null) {
                                PowerManagerService.this.mCoverType = coverState.getType();
                            }
                        } else {
                            powerManagerService.mCoverType = 2;
                        }
                        Slog.d("PowerManagerService", "onCoverAttachStateChanged: attach: " + z2 + "  mCoverType: " + PowerManagerService.this.mCoverType);
                    } catch (NullPointerException unused) {
                        Slog.d("PowerManagerService", "onCoverAttachStateChanged: mCoverManager returns NULL.");
                    }
                    long uptimeMillis = SystemClock.uptimeMillis();
                    PowerManagerService powerManagerService2 = PowerManagerService.this;
                    powerManagerService2.mlastSetCoverTypeTime = uptimeMillis;
                    powerManagerService2.mlastUpdateCoverTypeReason = "onCoverAttachStateChanged = " + PowerManagerService.this.mCoverType;
                }
            }

            public final void onCoverSwitchStateChanged(boolean z2) {
            }
        };
        final int i6 = 1;
        this.mPrintWakeLockAperiodicallyRunnable = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i6) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i32 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i32) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i42 = i22 + (i22 > i32 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i42;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i42;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i52 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i62 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i52 == i62) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i7 = i52 + (i52 > i62 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i7;
                                powerManagerService5.mHdrBrightnessUpperLimit = i7;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        final int i7 = 2;
        this.mBrightnessLimitRunnable = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i7) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i32 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i32) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i42 = i22 + (i22 > i32 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i42;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i42;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i52 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i62 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i52 == i62) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i72 = i52 + (i52 > i62 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i72;
                                powerManagerService5.mHdrBrightnessUpperLimit = i72;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        final int i8 = 3;
        this.mHdrBrightnessLimitRunnable = new Runnable(this) { // from class: com.android.server.power.PowerManagerService.6
            public final /* synthetic */ PowerManagerService this$0;

            {
                this.this$0 = this;
            }

            private final void run$com$android$server$power$PowerManagerService$8() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mEnsureTransitionToDozingReleaser");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mEnsureTransitionToDozingReleaserRunning = false;
                    powerManagerService.mDozeStartInProgress = false;
                    powerManagerService.updatePowerStateLocked();
                }
            }

            private final void run$com$android$server$power$PowerManagerService$9() {
                synchronized (this.this$0.mLock) {
                    Slog.d("PowerManagerService", "mCoverAuthReady since boot");
                    PowerManagerService powerManagerService = this.this$0;
                    powerManagerService.mCoverAuthReady = true;
                    powerManagerService.mCoverAuthReadyRunnable = null;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i8) {
                    case 0:
                        synchronized (this.this$0.mLock) {
                            try {
                                this.this$0.mLastUserActivityStateListenerState = 0;
                                Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.this$0.mLastUserActivityStateListenerState);
                                Iterator it = this.this$0.mUserActivityStateListenerListeners.iterator();
                                while (it.hasNext()) {
                                    ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.this$0.mLastUserActivityStateListenerState);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        synchronized (this.this$0.mLock) {
                            this.this$0.mHandler.removeCallbacks(this);
                            PowerManagerService.m807$$Nest$mprintWakeLockLocked(this.this$0);
                            PowerManagerService powerManagerService = this.this$0;
                            long j = powerManagerService.mDelayTimePrintWakeLock + 5000;
                            powerManagerService.mDelayTimePrintWakeLock = j;
                            powerManagerService.mDelayTimePrintWakeLock = Math.min(1800000L, j);
                            PowerManagerService powerManagerService2 = this.this$0;
                            powerManagerService2.mHandler.postDelayed(this, powerManagerService2.mDelayTimePrintWakeLock);
                        }
                        return;
                    case 2:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService3 = this.this$0;
                                int i22 = powerManagerService3.mLastRequestedLimitationOfBrightness;
                                int i32 = powerManagerService3.mTargetBrightnessForLimit;
                                if (i22 == i32) {
                                    powerManagerService3.mMasterBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done");
                                    return;
                                }
                                int i42 = i22 + (i22 > i32 ? -1 : 1);
                                powerManagerService3.mLastRequestedLimitationOfBrightness = i42;
                                powerManagerService3.mMasterBrightnessLowerLimit = -1;
                                powerManagerService3.mMasterBrightnessUpperLimit = i42;
                                powerManagerService3.mDirty |= 32;
                                powerManagerService3.updatePowerStateLocked();
                                PowerManagerService powerManagerService4 = this.this$0;
                                Handler handler = powerManagerService4.mHandler;
                                powerManagerService4.mClock.getClass();
                                handler.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mMasterBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 3:
                        synchronized (this.this$0.mLock) {
                            try {
                                PowerManagerService powerManagerService5 = this.this$0;
                                int i52 = powerManagerService5.mLastRequestedLimitationOfHdrBrightness;
                                int i62 = powerManagerService5.mTargetBrightnessForHdrLimit;
                                if (i52 == i62) {
                                    powerManagerService5.mHdrBrightnessLimitRunning = false;
                                    Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done");
                                    return;
                                }
                                int i72 = i52 + (i52 > i62 ? -1 : 1);
                                powerManagerService5.mLastRequestedLimitationOfHdrBrightness = i72;
                                powerManagerService5.mHdrBrightnessUpperLimit = i72;
                                powerManagerService5.mDirty |= 32;
                                powerManagerService5.updatePowerStateLocked();
                                PowerManagerService powerManagerService6 = this.this$0;
                                Handler handler2 = powerManagerService6.mHandler;
                                powerManagerService6.mClock.getClass();
                                handler2.postAtTime(this, SystemClock.uptimeMillis() + this.this$0.mHdrBrightnessLimitPeriod);
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        run$com$android$server$power$PowerManagerService$8();
                        return;
                    case 5:
                        run$com$android$server$power$PowerManagerService$9();
                        return;
                    case 6:
                        PowerManagerService powerManagerService7 = this.this$0;
                        if (powerManagerService7.mNoUserActivitySent && powerManagerService7.mContext != null && ActivityManagerNative.isSystemReady()) {
                            PowerManagerService powerManagerService8 = this.this$0;
                            powerManagerService8.mContext.sendBroadcastAsUser(powerManagerService8.mUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                            this.this$0.mNoUserActivitySent = false;
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mContext == null || !ActivityManagerNative.isSystemReady()) {
                            return;
                        }
                        PowerManagerService powerManagerService9 = this.this$0;
                        powerManagerService9.mContext.sendBroadcastAsUser(powerManagerService9.mNoUserActivityIntent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM");
                        this.this$0.mNoUserActivitySent = true;
                        return;
                }
            }
        };
        this.mContext = context;
        this.mBinderService = new BinderService(context);
        this.mLocalService = new LocalService();
        injector.getClass();
        this.mNativeWrapper = new NativeWrapper();
        this.mSystemProperties = new Injector.AnonymousClass1();
        this.mClock = new Injector.AnonymousClass1();
        this.mFeatureFlags = new PowerManagerFlags();
        this.mInjector = injector;
        ServiceThread m = Watchdog$$ExternalSyntheticOutline0.m(-4, "PowerManagerService", false);
        Handler handler = new Handler(m.getLooper(), new PowerManagerHandlerCallback(), true);
        this.mHandler = handler;
        m.getLooper().setSlowLogThresholdMs(100L, 110L);
        HandlerThread handlerThread = new HandlerThread("pms.misc");
        handlerThread.start();
        this.mHandlerPmsMisc = new Handler(handlerThread.getLooper(), null, true);
        this.mConstants = new Constants(handler);
        this.mFoldGracePeriodProvider = new FoldGracePeriodProvider();
        this.mAmbientDisplayConfiguration = new AmbientDisplayConfiguration(context);
        this.mAmbientDisplaySuppressionController = new AmbientDisplaySuppressionController(anonymousClass1);
        this.mAttentionDetector = new AttentionDetector(new PowerManagerService$$ExternalSyntheticLambda1(this, 0), installNewLock);
        ScreenUndimDetector screenUndimDetector = new ScreenUndimDetector();
        screenUndimDetector.mUndimCounter = 0;
        this.mScreenUndimDetector = screenUndimDetector;
        this.mBatterySaverSupported = true;
        BatterySavingStats batterySavingStats = new BatterySavingStats(installNewLock);
        this.mBatterySaverStateMachine = new BatterySaverStateMachine(installNewLock, context, new BatterySaverController(installNewLock, context, BackgroundThread.get().getLooper(), new BatterySaverPolicy(installNewLock, context, batterySavingStats), batterySavingStats));
        this.mLowPowerStandbyController = new LowPowerStandbyController(context, Looper.getMainLooper(), new LowPowerStandbyController.RealClock(), new LowPowerStandbyController.DeviceConfigWrapper(), new LowPowerStandbyController$$ExternalSyntheticLambda2(), new File(Environment.getDataSystemDirectory(), "low_power_standby_policy.xml"));
        this.mInattentiveSleepWarningOverlayController = new InattentiveSleepWarningController();
        this.mPermissionCheckerWrapper = new PowerManagerService$Injector$$ExternalSyntheticLambda0();
        this.mPowerPropertiesWrapper = new Injector.AnonymousClass1();
        this.mDeviceConfigProvider = new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
        this.mPowerGroupWakefulnessChangeListener = new AnonymousClass1();
        this.mUseAutoSuspend = context.getResources().getBoolean(R.bool.config_user_notification_of_restrictied_mobile_access);
        float f = context.getResources().getFloat(R.dimen.conversation_badge_protrusion_group_expanded_face_pile);
        float f2 = context.getResources().getFloat(R.dimen.conversation_badge_protrusion_group_expanded);
        float f3 = context.getResources().getFloat(R.dimen.conversation_badge_protrusion);
        float f4 = context.getResources().getFloat(R.dimen.conversation_avatar_size);
        float f5 = context.getResources().getFloat(R.dimen.controls_thumbnail_image_max_width);
        this.mScreenExtendedBrightnessMaximum = context.getResources().getInteger(R.integer.config_vibratorControlServiceDumpSizeLimit);
        if (f == -2.0f || f2 == -2.0f || f3 == -2.0f) {
            this.mScreenBrightnessMinimum = BrightnessSynchronizer.brightnessIntToFloat(context.getResources().getInteger(R.integer.config_wakeUpToLastStateTimeoutMillis));
            this.mScreenBrightnessMaximum = BrightnessSynchronizer.brightnessIntToFloat(context.getResources().getInteger(R.integer.config_wait_for_satellite_enabling_response_timeout_millis));
            this.mScreenBrightnessDefault = BrightnessSynchronizer.brightnessIntToFloat(context.getResources().getInteger(R.integer.config_wait_for_datagram_sending_response_timeout_millis));
        } else {
            this.mScreenBrightnessMinimum = f;
            this.mScreenBrightnessMaximum = f2;
            this.mScreenBrightnessDefault = f3;
        }
        if (f4 == -2.0f) {
            this.mScreenBrightnessDoze = BrightnessSynchronizer.brightnessIntToFloat(context.getResources().getInteger(R.integer.config_vibratorControlServiceDumpAggregationTimeMillisLimit));
        } else {
            this.mScreenBrightnessDoze = f4;
        }
        if (f5 == -2.0f) {
            this.mScreenBrightnessDim = BrightnessSynchronizer.brightnessIntToFloat(context.getResources().getInteger(R.integer.config_vibrationWaveformRampStepDuration));
        } else {
            this.mScreenBrightnessDim = f5;
        }
        synchronized (this.mLock) {
            try {
                SuspendBlockerImpl createSuspendBlocker = Injector.createSuspendBlocker(this, "PowerManagerService.Booting");
                this.mBootingSuspendBlocker = createSuspendBlocker;
                this.mWakeLockSuspendBlocker = Injector.createSuspendBlocker(this, "PowerManagerService.WakeLocks");
                SuspendBlockerImpl createSuspendBlocker2 = Injector.createSuspendBlocker(this, "PowerManagerService.Display");
                this.mDisplaySuspendBlocker = createSuspendBlocker2;
                createSuspendBlocker.acquire("unknown");
                this.mHoldingBootingSuspendBlocker = true;
                createSuspendBlocker2.acquire("holding display");
                this.mHoldingDisplaySuspendBlocker = true;
                this.mHalAutoSuspendModeEnabled = false;
                setHalInteractiveModeLocked(true);
                this.mWakefulnessRaw = 1;
                if (!SystemProperties.get("ro.boot.quiescent", "0").equals("1") && !((Boolean) InitProperties.userspace_reboot_in_progress().orElse(Boolean.FALSE)).booleanValue()) {
                    z = false;
                    sQuiescent = z;
                    nativeInit();
                    nativeSetAutoSuspend(false);
                    nativeSetPowerMode(7, true);
                    nativeSetPowerMode(0, false);
                    PowerManager.invalidateIsInteractiveCaches();
                }
                z = true;
                sQuiescent = z;
                nativeInit();
                nativeSetAutoSuspend(false);
                nativeSetPowerMode(7, true);
                nativeSetPowerMode(0, false);
                PowerManager.invalidateIsInteractiveCaches();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static int adjustWakeLockSummary(int i, int i2, int i3) {
        if (i != 3) {
            i2 &= -193;
        }
        if (i == 0 || (i2 & 64) != 0) {
            i2 = (i == 0 && i3 == 4) ? i2 & (-31) : i2 & (-15);
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

    public static boolean isScreenLock(WakeLock wakeLock) {
        int i = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
        return i == 6 || i == 10 || i == 26;
    }

    public static void lowLevelReboot(String str) {
        Slog.d("PowerManagerService", "[api] lowLevelReboot: " + str + PowerManagerUtil.callerInfoToString(true));
        Slog.saveLogAsFile();
        if (str == null) {
            str = "";
        }
        if (str.equals("quiescent")) {
            sQuiescent = true;
            str = "";
        } else if (str.endsWith(",quiescent")) {
            sQuiescent = true;
            str = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(10, 0, str);
        }
        if (str.equals("recovery") || str.equals("recovery-update")) {
            str = "recovery";
        }
        if (sQuiescent) {
            if (!"".equals(str)) {
                str = str.concat(",");
            }
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "quiescent");
        }
        SystemProperties.set("sys.powerctl", "reboot," + str);
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
        android.util.Slog.wtf("PowerManagerService", "Unexpected return from lowLevelReboot!");
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

    public final void addPowerGroupsForNonDefaultDisplayGroupLocked() {
        IntArray displayGroupIds = this.mDisplayManagerInternal.getDisplayGroupIds();
        if (displayGroupIds == null) {
            return;
        }
        for (int i = 0; i < displayGroupIds.size(); i++) {
            int i2 = displayGroupIds.get(i);
            if (i2 != 0) {
                if (this.mPowerGroups.contains(i2)) {
                    Slog.e("PowerManagerService", "Tried to add already existing group:" + i2);
                } else {
                    Notifier notifier = this.mNotifier;
                    DisplayManagerInternal displayManagerInternal = this.mDisplayManagerInternal;
                    this.mClock.getClass();
                    this.mPowerGroups.append(i2, new PowerGroup(i2, this.mPowerGroupWakefulnessChangeListener, notifier, displayManagerInternal, false, SystemClock.uptimeMillis()));
                }
            }
        }
        this.mDirty |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyWakeLockFlagsOnAcquireLocked(com.android.server.power.PowerManagerService.WakeLock r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.applyWakeLockFlagsOnAcquireLocked(com.android.server.power.PowerManagerService$WakeLock, boolean):void");
    }

    public final boolean areAllPowerGroupsReadyLocked() {
        int size = this.mPowerGroups.size();
        for (int i = 0; i < size; i++) {
            if (!((PowerGroup) this.mPowerGroups.valueAt(i)).mReady) {
                return false;
            }
        }
        return true;
    }

    public final void calculateHdrBrightnessLimitLocked(final int i, final int i2) {
        this.mHdrBrightnessLimitLocks.stream().min(Comparator.comparingInt(new PowerManagerService$$ExternalSyntheticLambda9())).ifPresentOrElse(new PowerManagerService$$ExternalSyntheticLambda10(0, this), new Runnable() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                PowerManagerService powerManagerService = PowerManagerService.this;
                int i3 = i;
                int i4 = i2;
                powerManagerService.mHdrBrightnessUpperLimit = i3;
                powerManagerService.mHdrBrightnessLimitPeriod = i4;
            }
        });
    }

    public final boolean canDreamLocked(PowerGroup powerGroup) {
        int i;
        int i2;
        boolean z = this.mDreamsDisabledByAmbientModeSuppressionConfig && this.mAmbientDisplaySuppressionController.isSuppressed();
        if (this.mBootCompleted && !z && getGlobalWakefulnessLocked() == 2 && this.mDreamsSupportedConfig && this.mDreamsEnabledSetting && powerGroup.mDisplayPowerRequest.isBrightOrDim()) {
            int i3 = this.mCoverType;
            if (!((i3 == -1 || i3 == 2) ? false : this.mIsCoverClosed) && ((!PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY || this.mDualScreenPolicy != 1) && (powerGroup.mUserActivitySummary & 7) != 0)) {
                if (isBeingKeptAwakeLocked(powerGroup)) {
                    return true;
                }
                boolean z2 = this.mIsPowered;
                if (!z2 && !this.mDreamsEnabledOnBatteryConfig) {
                    return false;
                }
                if (z2 || (i2 = this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig) < 0 || this.mBatteryLevel >= i2) {
                    return !z2 || (i = this.mDreamsBatteryLevelMinimumWhenPoweredConfig) < 0 || this.mBatteryLevel >= i;
                }
                return false;
            }
        }
        return false;
    }

    public final boolean doesIdleStateBlockWakeLocksLocked() {
        if (this.mDeviceIdleMode) {
            return true;
        }
        if (this.mLightDeviceIdleMode) {
            Flags.disableWakelocksInLightIdle();
        }
        return false;
    }

    public final boolean dozePowerGroupLocked(final PowerGroup powerGroup, long j, final int i, final int i2) {
        if (!this.mSystemReady || !this.mBootCompleted || j < powerGroup.mLastWakeTime || !PowerManagerInternal.isInteractive(powerGroup.mWakefulness)) {
            return false;
        }
        int i3 = powerGroup.mGroupId;
        if (i3 == 0 && this.mGoToSleepPreventionEnabled) {
            Slog.d("PowerManagerService", "goToSleepNoUpdateLocked: GoToSleep prevention is enabled");
            return false;
        }
        if (this.mScreenDimDurationOverrideFromSQD != -1) {
            this.mScreenDimDurationOverrideFromSQD = -1L;
        }
        if (i3 == 2 && ((PowerGroup) this.mPowerGroups.get(0)).mWakefulness == 1) {
            return false;
        }
        if (CoreRune.CARLIFE_DISPLAY_GROUP && i3 == 4) {
            return false;
        }
        Slog.wk("PowerManagerService", "Screen__Off(d) - " + this.mScreenOnOffCount + " : " + this.mScreenOffReason + " (" + PowerManager.sleepReasonToString(i) + ") groupId=" + i3);
        this.mScreenOnReason = "";
        this.mScreenOffReason = "";
        PowerManagerUtil.ScreenOffProfiler screenOffProfiler = PowerManagerUtil.sCurrentScreenOffProfiler;
        if (screenOffProfiler.mGoToSleepStartTime != 0 && !screenOffProfiler.mSaved) {
            Slog.d("PowerManagerService", "need to skip , ignore " + (this.mScreenOnOffCount - 1) + " th OFF profiler ");
        }
        this.mClock.getClass();
        int uptimeMillis = (int) (SystemClock.uptimeMillis() - j);
        screenOffProfiler.clearAll();
        screenOffProfiler.mGoToSleepDiff = uptimeMillis;
        screenOffProfiler.mOrder = this.mScreenOnOffCount;
        screenOffProfiler.mGoToSleepStartTime = SystemClock.uptimeMillis();
        screenOffProfiler.mGoToSleepTimeStr = PowerManagerUtil.getCurrentTimeAsString();
        boolean z = this.mMasterBrightnessLimitRunning;
        Handler handler = this.mHandler;
        if (z) {
            this.mMasterBrightnessLimitRunning = false;
            handler.removeCallbacks(this.mBrightnessLimitRunnable);
            int i4 = this.mTargetBrightnessForLimit;
            this.mLastRequestedLimitationOfBrightness = i4;
            this.mMasterBrightnessLowerLimit = -1;
            this.mMasterBrightnessUpperLimit = i4;
            this.mDirty |= 32;
            updatePowerStateLocked();
            Slog.d("PowerManagerService", "[api] BrightnessLimitRunnable done (complete immediately)");
        }
        if (this.mHdrBrightnessLimitRunning) {
            this.mHdrBrightnessLimitRunning = false;
            handler.removeCallbacks(this.mHdrBrightnessLimitRunnable);
            int i5 = this.mTargetBrightnessForHdrLimit;
            this.mLastRequestedLimitationOfHdrBrightness = i5;
            this.mHdrBrightnessUpperLimit = i5;
            this.mDirty |= 32;
            updatePowerStateLocked();
            Slog.d("PowerManagerService", "[api] HdrBrightnessLimitRunnable done (complete immediately)");
        }
        powerGroup.mLastGoToSleepReason = i;
        this.mHandlerPmsMisc.post(new Runnable() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PowerManagerService powerManagerService = PowerManagerService.this;
                int i6 = i2;
                PowerGroup powerGroup2 = powerGroup;
                int i7 = i;
                SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
                powerManagerService.getClass();
                int i8 = powerGroup2.mGroupId;
                String findForegroundPackageName = powerManagerService.findForegroundPackageName();
                PowerHistorian powerHistorian = powerManagerService.mPowerHistorian;
                powerHistorian.getClass();
                powerHistorian.addRecord(1, new PowerHistorian.WakeUpRecord(i6, i8, findForegroundPackageName, null, i7, false));
            }
        });
        Trace.traceBegin(131072L, "powerOffDisplay");
        try {
            int min = Math.min(26, Math.max(i, 0));
            Slog.i("PowerGroup", "Powering off display group due to " + PowerManager.sleepReasonToString(min) + " (groupId= " + i3 + ", uid= " + i2 + ", millisSinceLastUserActivity=" + (j - Math.max(powerGroup.mLastUserActivityTimeNoChangeLights, powerGroup.mLastUserActivityTime)) + ", lastUserActivityEvent=" + PowerManager.userActivityEventToString(powerGroup.mLastUserActivityEvent) + ")...");
            powerGroup.mIsSandmanSummoned = true;
            powerGroup.setWakefulnessLocked(3, j, i2, min, 0, null, null);
            return true;
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public final boolean dreamPowerGroupLocked(PowerGroup powerGroup, long j, int i, boolean z) {
        if (!this.mBootCompleted || !this.mSystemReady || j < powerGroup.mLastWakeTime) {
            return false;
        }
        if (!z && powerGroup.mWakefulness != 1) {
            return false;
        }
        StringBuilder sb = new StringBuilder("dreamPowerGroup");
        int i2 = powerGroup.mGroupId;
        sb.append(i2);
        Trace.traceBegin(131072L, sb.toString());
        try {
            Slog.i("PowerGroup", "Napping power group (groupId=" + i2 + ", uid=" + i + ")...");
            powerGroup.mIsSandmanSummoned = true;
            powerGroup.setWakefulnessLocked(2, j, i, 0, 0, null, null);
            return true;
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    @NeverCompile
    public final void dumpInternal(PrintWriter printWriter, boolean z) {
        WirelessChargerDetector wirelessChargerDetector;
        printWriter.println("POWER MANAGER (dumpsys power)\n");
        synchronized (this.mLock) {
            try {
                printWriter.println("Power Manager State:");
                this.mConstants.dump(printWriter);
                printWriter.println("  mDirty=0x" + Integer.toHexString(this.mDirty));
                printWriter.println("  mWakefulness=" + PowerManagerInternal.wakefulnessToString(getGlobalWakefulnessLocked()));
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
                    printWriter.println("  mEnhancedDischargeTimeElapsed=" + this.mEnhancedDischargeTimeElapsed);
                    printWriter.println("  mLastEnhancedDischargeTimeUpdatedElapsed=" + this.mLastEnhancedDischargeTimeUpdatedElapsed);
                    printWriter.println("  mEnhancedDischargePredictionIsPersonalized=" + this.mEnhancedDischargePredictionIsPersonalized);
                }
                printWriter.println("  mUseAutoSuspend=" + this.mUseAutoSuspend);
                printWriter.println("  mHalAutoSuspendModeEnabled=" + this.mHalAutoSuspendModeEnabled);
                printWriter.println("  mHalInteractiveModeEnabled=" + this.mHalInteractiveModeEnabled);
                printWriter.println("  mWakeLockSummary=0x" + Integer.toHexString(this.mWakeLockSummary));
                printWriter.print("  mNotifyLongScheduled=");
                long j = this.mNotifyLongScheduled;
                if (j == 0) {
                    printWriter.print("(none)");
                } else {
                    this.mClock.getClass();
                    TimeUtils.formatDuration(j, SystemClock.uptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.print("  mNotifyLongDispatched=");
                long j2 = this.mNotifyLongDispatched;
                if (j2 == 0) {
                    printWriter.print("(none)");
                } else {
                    this.mClock.getClass();
                    TimeUtils.formatDuration(j2, SystemClock.uptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.print("  mNotifyLongNextCheck=");
                long j3 = this.mNotifyLongNextCheck;
                if (j3 == 0) {
                    printWriter.print("(none)");
                } else {
                    this.mClock.getClass();
                    TimeUtils.formatDuration(j3, SystemClock.uptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.println("  mRequestWaitForNegativeProximity=" + this.mRequestWaitForNegativeProximity);
                printWriter.println("  mInterceptedPowerKeyForProximity=" + this.mInterceptedPowerKeyForProximity);
                printWriter.println("  mInterceptedKeyForProximity=" + this.mInterceptedKeyForProximity);
                printWriter.println("  mSandmanScheduled=" + this.mSandmanScheduled);
                printWriter.println("  mBatteryLevelLow=" + this.mBatteryLevelLow);
                printWriter.println("  mLightDeviceIdleMode=" + this.mLightDeviceIdleMode);
                printWriter.println("  mDeviceIdleMode=" + this.mDeviceIdleMode);
                printWriter.println("  mDeviceIdleWhitelist=" + Arrays.toString(this.mDeviceIdleWhitelist));
                printWriter.println("  mDeviceIdleTempWhitelist=" + Arrays.toString(this.mDeviceIdleTempWhitelist));
                printWriter.println("  mLowPowerStandbyActive=" + this.mLowPowerStandbyActive);
                printWriter.println("  mLastWakeTime=" + TimeUtils.formatUptime(this.mLastGlobalWakeTime));
                printWriter.println("  mLastSleepTime=" + TimeUtils.formatUptime(this.mLastGlobalSleepTime));
                printWriter.println("  mLastSleepReason=" + PowerManager.sleepReasonToString(this.mLastGlobalSleepReason));
                printWriter.println("  mLastGlobalWakeTimeRealtime=" + TimeUtils.formatUptime(this.mLastGlobalWakeTimeRealtime));
                printWriter.println("  mLastGlobalSleepTimeRealtime=" + TimeUtils.formatUptime(this.mLastGlobalSleepTimeRealtime));
                printWriter.println("  mLastInteractivePowerHintTime=" + TimeUtils.formatUptime(this.mLastInteractivePowerHintTime));
                printWriter.println("  mLastScreenBrightnessBoostTime=" + TimeUtils.formatUptime(this.mLastScreenBrightnessBoostTime));
                printWriter.println("  mScreenBrightnessBoostInProgress=" + this.mScreenBrightnessBoostInProgress);
                printWriter.println("  mDefaultDisplayReadyByProximity=" + this.mDefaultDisplayReadyByProximity);
                printWriter.println("  mHoldingWakeLockSuspendBlocker=" + this.mHoldingWakeLockSuspendBlocker);
                printWriter.println("  mHoldingDisplaySuspendBlocker=" + this.mHoldingDisplaySuspendBlocker);
                printWriter.println();
                printWriter.println("Settings and Configuration:");
                printWriter.println("  mDecoupleHalAutoSuspendModeFromDisplayConfig=" + this.mDecoupleHalAutoSuspendModeFromDisplayConfig);
                printWriter.println("  mDecoupleHalInteractiveModeFromDisplayConfig=" + this.mDecoupleHalInteractiveModeFromDisplayConfig);
                printWriter.println("  mWakeUpWhenPluggedOrUnpluggedConfig=" + this.mWakeUpWhenPluggedOrUnpluggedConfig);
                printWriter.println("  mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig=" + this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig);
                printWriter.println("  mTheaterModeEnabled=" + this.mTheaterModeEnabled);
                printWriter.println("  mKeepDreamingWhenUnplugging=" + this.mKeepDreamingWhenUnplugging);
                printWriter.println("  mSuspendWhenScreenOffDueToProximityConfig=" + this.mSuspendWhenScreenOffDueToProximityConfig);
                printWriter.println("  mDreamsSupportedConfig=" + this.mDreamsSupportedConfig);
                printWriter.println("  mDreamsEnabledByDefaultConfig=" + this.mDreamsEnabledByDefaultConfig);
                printWriter.println("  mDreamsActivatedOnSleepByDefaultConfig=" + this.mDreamsActivatedOnSleepByDefaultConfig);
                printWriter.println("  mDreamsActivatedOnDockByDefaultConfig=" + this.mDreamsActivatedOnDockByDefaultConfig);
                printWriter.println("  mDreamsEnabledOnBatteryConfig=" + this.mDreamsEnabledOnBatteryConfig);
                printWriter.println("  mDreamsBatteryLevelMinimumWhenPoweredConfig=" + this.mDreamsBatteryLevelMinimumWhenPoweredConfig);
                printWriter.println("  mDreamsBatteryLevelMinimumWhenNotPoweredConfig=" + this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig);
                printWriter.println("  mDreamsBatteryLevelDrainCutoffConfig=" + this.mDreamsBatteryLevelDrainCutoffConfig);
                printWriter.println("  mDreamsEnabledSetting=" + this.mDreamsEnabledSetting);
                printWriter.println("  mDreamsActivateOnSleepSetting=" + this.mDreamsActivateOnSleepSetting);
                printWriter.println("  mDreamsActivateOnDockSetting=" + this.mDreamsActivateOnDockSetting);
                printWriter.println("  mDozeAfterScreenOff=" + this.mDozeAfterScreenOff);
                printWriter.println("  mBrightWhenDozingConfig=" + this.mBrightWhenDozingConfig);
                printWriter.println("  mMinimumScreenOffTimeoutConfig=" + this.mMinimumScreenOffTimeoutConfig);
                printWriter.println("  mMaximumScreenDimDurationConfig=" + this.mMaximumScreenDimDurationConfig);
                printWriter.println("  mMaximumScreenDimRatioConfig=" + this.mMaximumScreenDimRatioConfig);
                printWriter.println("  mAttentiveTimeoutConfig=" + this.mAttentiveTimeoutConfig);
                printWriter.println("  mAttentiveTimeoutSetting=" + this.mAttentiveTimeoutSetting);
                printWriter.println("  mAttentiveWarningDurationConfig=" + this.mAttentiveWarningDurationConfig);
                printWriter.println("  mScreenOffTimeoutSetting=" + this.mScreenOffTimeoutSetting);
                printWriter.println("  mSleepTimeoutSetting=" + this.mSleepTimeoutSetting);
                printWriter.println("  mMaximumScreenOffTimeoutFromDeviceAdmin=" + this.mMaximumScreenOffTimeoutFromDeviceAdmin + " (enforced=" + isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked() + ")");
                StringBuilder sb = new StringBuilder("  mStayOnWhilePluggedInSetting=");
                sb.append(this.mStayOnWhilePluggedInSetting);
                printWriter.println(sb.toString());
                printWriter.println("  mScreenBrightnessOverrideFromWindowManager=" + this.mScreenBrightnessOverrideFromWindowManager);
                printWriter.println("  mUserActivityTimeoutOverrideFromWindowManager=" + this.mUserActivityTimeoutOverrideFromWindowManager);
                printWriter.println("  mUserActivityTimeoutForDexOverrideFromWindowManager=" + this.mUserActivityTimeoutForDexOverrideFromWindowManager);
                printWriter.println("  mUserInactiveOverrideFromWindowManager=" + this.mUserInactiveOverrideFromWindowManager);
                printWriter.println("  mDozeScreenStateOverrideFromDreamManager=" + this.mDozeScreenStateOverrideFromDreamManager);
                printWriter.println("  mDrawWakeLockOverrideFromSidekick=" + this.mDrawWakeLockOverrideFromSidekick);
                printWriter.println("  mDozeScreenBrightnessOverrideFromDreamManager=" + this.mDozeScreenBrightnessOverrideFromDreamManager);
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
                printWriter.println("  mAutoBrightnessLimitLastCaller=" + this.mAutoBrightnessLimitLastCaller);
                printWriter.println("  SEC_FEATURE_USE_GED_DOZE: " + PowerManagerUtil.SEC_FEATURE_USE_GED_DOZE);
                printWriter.println("  SEC_FEATURE_SEAMLESS_AOD: " + PowerManagerUtil.SEC_FEATURE_SEAMLESS_AOD);
                printWriter.println("  SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE: " + PowerManagerUtil.SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE);
                printWriter.println("  SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED: " + PowerManagerUtil.SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED);
                printWriter.println("  SEC_FEATURE_AOD_LOOK_CHARGING_UI: " + PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI);
                printWriter.println("  mAodMode: " + this.mAodMode);
                printWriter.println("  mShouldWaitForTransitionToAodUi: " + this.mShouldWaitForTransitionToAodUi);
                printWriter.println("  SEC_FEATURE_DEX_DUAL_VIEW: " + PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW);
                printWriter.println("  SEC_FEATURE_FOLD_COVER_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY);
                printWriter.println("  SEC_FEATURE_FLIP_COVER_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY);
                printWriter.println("  SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY);
                printWriter.println("  SEC_FEATURE_DUAL_DISPLAY: " + PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY);
                printWriter.println("  SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING: " + PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING);
                long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
                long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
                long screenOffTimeoutLocked = getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
                long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
                printWriter.println();
                printWriter.println("Attentive timeout: " + attentiveTimeoutLocked + " ms");
                printWriter.println("Sleep timeout: " + sleepTimeoutLocked + " ms");
                printWriter.println("Screen off timeout: " + screenOffTimeoutLocked + " ms");
                printWriter.println("Screen dim duration: " + screenDimDurationLocked + " ms");
                printWriter.println("Screen dim duration override: " + this.mScreenDimDurationOverrideFromWindowManager + " ms");
                printWriter.println("SQD Screen dim duration override: " + this.mScreenDimDurationOverrideFromSQD + " ms");
                printWriter.println("Screen off timeout for DeX: " + this.mScreenOffTimeoutForDex + " ms");
                printWriter.println();
                printWriter.println("Smart Stay:");
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
                printWriter.println("  mlastUpdateCoverStateTime: = " + TimeUtils.formatUptime(this.mlastUpdateCoverStateTime));
                printWriter.println("  mFeatureCoverSysfs: " + this.mFeatureCoverSysfs);
                printWriter.println("  mCoverType: " + this.mCoverType + " at " + TimeUtils.formatUptime(this.mlastSetCoverTypeTime));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("  mlastUpdateCoverTypeReason: ");
                sb2.append(this.mlastUpdateCoverTypeReason);
                printWriter.println(sb2.toString());
                printWriter.println("  mScreenBrightnessForClearCoverConfig=" + this.mScreenBrightnessForClearCoverConfig);
                printWriter.println("  mBrightnessLimitByCoverConfig: " + this.mBrightnessLimitByCoverConfig);
                printWriter.println("Master Brightness Limit:");
                printWriter.println("  mMasterBrightnessLowerLimit: " + this.mMasterBrightnessLowerLimit);
                printWriter.println("  mMasterBrightnessUpperLimit: " + this.mMasterBrightnessUpperLimit);
                printWriter.println("  mMasterBrightnessLimitLastCaller: " + this.mMasterBrightnessLimitLastCaller);
                printWriter.println("  mMasterBrightnessLimitPeriod: " + this.mMasterBrightnessLimitPeriod);
                printWriter.println("  mMasterBrightnessLimitRunning: " + this.mMasterBrightnessLimitRunning);
                printWriter.println("  mLastRequestedLimitationOfBrightness: " + this.mLastRequestedLimitationOfBrightness);
                printWriter.println("  mTargetBrightnessForLimit : " + this.mTargetBrightnessForLimit);
                printWriter.println("HDR Brightness Limit");
                printWriter.println("  mHdrBrightnessUpperLimit: " + this.mHdrBrightnessUpperLimit);
                printWriter.println("  mLastRequestedLimitationOfHdrBrightness: " + this.mLastRequestedLimitationOfHdrBrightness);
                printWriter.println("  mTargetBrightnessForHdrLimit: " + this.mTargetBrightnessForHdrLimit);
                printWriter.println("  mHdrBrightnessLimitRunning: " + this.mHdrBrightnessLimitRunning);
                printWriter.println("  mHdrBrightnessLimitPeriod: " + this.mHdrBrightnessLimitPeriod);
                printWriter.println("HDR Brightness Limit Locks: size=" + this.mHdrBrightnessLimitLocks.size());
                Iterator it2 = this.mHdrBrightnessLimitLocks.iterator();
                while (it2.hasNext()) {
                    printWriter.println(" " + ((HdrBrightnessLimitLock) it2.next()));
                }
                printWriter.println();
                printWriter.println("SEC_USE_FACTORY_BINARY: " + SEC_USE_FACTORY_BINARY);
                printWriter.println();
                printWriter.println("Wake Locks: size=" + this.mWakeLocks.size());
                Iterator it3 = this.mWakeLocks.iterator();
                while (it3.hasNext()) {
                    printWriter.println("  " + ((WakeLock) it3.next()));
                }
                printWriter.println();
                printWriter.println("Suspend Blockers: size=" + this.mSuspendBlockers.size());
                Iterator it4 = this.mSuspendBlockers.iterator();
                while (it4.hasNext()) {
                    printWriter.println("  " + ((SuspendBlockerImpl) it4.next()));
                }
                printWriter.println();
                printWriter.println("Display Power: " + this.mDisplayPowerCallbacks);
                printWriter.println();
                printWriter.println("mFreezingScreenBrightness: " + this.mFreezingScreenBrightness);
                this.mPowerHistorian.dump(printWriter);
                printWriter.println();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("screen on profiler V2: ");
                RingBuffer ringBuffer = PowerManagerUtil.sScreenOnProfilers;
                sb3.append(ringBuffer.size());
                printWriter.println(sb3.toString());
                PowerManagerUtil.ScreenOnProfiler[] screenOnProfilerArr = (PowerManagerUtil.ScreenOnProfiler[]) ringBuffer.toArray();
                for (PowerManagerUtil.ScreenOnProfiler screenOnProfiler : screenOnProfilerArr) {
                    printWriter.print(screenOnProfiler.toString(true));
                    printWriter.println();
                }
                printWriter.println();
                StringBuilder sb4 = new StringBuilder();
                sb4.append("screen off profiler V2: ");
                RingBuffer ringBuffer2 = PowerManagerUtil.sScreenOffProfilers;
                sb4.append(ringBuffer2.size());
                printWriter.println(sb4.toString());
                PowerManagerUtil.ScreenOffProfiler[] screenOffProfilerArr = (PowerManagerUtil.ScreenOffProfiler[]) ringBuffer2.toArray();
                for (PowerManagerUtil.ScreenOffProfiler screenOffProfiler : screenOffProfilerArr) {
                    printWriter.print(screenOffProfiler.toString(true));
                    printWriter.println();
                }
                if (this.mBatterySaverSupported) {
                    this.mBatterySaverStateMachine.mBatterySaverController.mBatterySaverPolicy.dump(printWriter);
                    this.mBatterySaverStateMachine.dump(printWriter);
                } else {
                    printWriter.println("Battery Saver: DISABLED");
                }
                this.mAttentionDetector.dump(printWriter);
                printWriter.println();
                int size = this.mProfilePowerState.size();
                printWriter.println("Profile power states: size=" + size);
                for (int i2 = 0; i2 < size; i2++) {
                    ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.valueAt(i2);
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
                    printWriter.println(((PowerGroup) this.mPowerGroups.valueAt(i3)).getSuspendBlockerMonitorInfo());
                }
                printWriter.println("Display Group User Activity:");
                for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
                    PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i4);
                    printWriter.println("  displayGroupId=" + powerGroup.mGroupId);
                    printWriter.println("  userActivitySummary=0x" + Integer.toHexString(powerGroup.mUserActivitySummary));
                    printWriter.println("  lastUserActivityTime=" + TimeUtils.formatUptime(powerGroup.mLastUserActivityTime));
                    printWriter.println("  lastUserActivityTimeNoChangeLights=" + TimeUtils.formatUptime(powerGroup.mLastUserActivityTimeNoChangeLights));
                    printWriter.println("  mWakeLockSummary=0x" + Integer.toHexString(powerGroup.mWakeLockSummary));
                }
                wirelessChargerDetector = this.mWirelessChargerDetector;
                ScreenOnKeeper screenOnKeeper = this.mScreenOnKeeper;
                if (screenOnKeeper != null) {
                    screenOnKeeper.dump(printWriter);
                }
            } finally {
            }
        }
        if (wirelessChargerDetector != null) {
            wirelessChargerDetector.dump(printWriter);
        }
        if (!z) {
            printWriter.println("\n\n ====================== beginning of PMS  log ======================");
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
        synchronized (this.mLock) {
            try {
                ScreenTimeoutOverridePolicy screenTimeoutOverridePolicy = this.mScreenTimeoutOverridePolicy;
                if (screenTimeoutOverridePolicy != null) {
                    screenTimeoutOverridePolicy.dump(printWriter);
                }
            } finally {
            }
        }
        this.mFeatureFlags.dump(printWriter);
    }

    public final String findForegroundPackageName() {
        Context context = this.mContext;
        this.mInjector.getClass();
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        return runningTasks.isEmpty() ? "" : runningTasks.get(0).topActivity.getPackageName();
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

    public final void finishWakefulnessChangeIfNeededLocked() {
        if (this.mWakefulnessChanging && areAllPowerGroupsReadyLocked()) {
            for (int i = 0; i < this.mPowerGroups.size(); i++) {
                PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i);
                if (powerGroup.mSupportsSandman && powerGroup.mWakefulness == 3 && (((PowerGroup) this.mPowerGroups.valueAt(i)).mWakeLockSummary & 64) == 0) {
                    if (PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING && this.mDozeStartInProgress) {
                        setEnsureTransitionToDozingReleaserLocked(true);
                        return;
                    }
                    return;
                }
            }
            this.mDozeStartInProgress = false;
            if (PowerManagerUtil.SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING) {
                setEnsureTransitionToDozingReleaserLocked(false);
            }
            if (getGlobalWakefulnessLocked() == 3 || getGlobalWakefulnessLocked() == 0) {
                this.mClock.getClass();
                long uptimeMillis = this.mOverriddenTimeout - SystemClock.uptimeMillis();
                if (uptimeMillis >= 0) {
                    EventLog.writeEvent(2731, uptimeMillis);
                    this.mOverriddenTimeout = -1L;
                }
            }
            this.mWakefulnessChanging = false;
            Notifier notifier = this.mNotifier;
            for (int i2 = 0; i2 < notifier.mInteractivityByGroupId.size(); i2++) {
                int keyAt = notifier.mInteractivityByGroupId.keyAt(i2);
                Notifier.Interactivity interactivity = (Notifier.Interactivity) notifier.mInteractivityByGroupId.valueAt(i2);
                if (interactivity.isChanging) {
                    interactivity.isChanging = false;
                    notifier.handleLateInteractiveChange(keyAt);
                }
            }
            Notifier.Interactivity interactivity2 = notifier.mGlobalInteractivity;
            if (interactivity2.isChanging) {
                interactivity2.isChanging = false;
                notifier.handleLateGlobalInteractiveChange();
            }
        }
    }

    public final long getAttentiveTimeoutLocked() {
        long j = this.mAttentiveTimeoutSetting;
        if (j <= 0) {
            return -1L;
        }
        return Math.max(j, this.mMinimumScreenOffTimeoutConfig);
    }

    public BinderService getBinderServiceInstance() {
        return this.mBinderService;
    }

    public int getDesiredScreenPolicyLocked(int i) {
        return ((PowerGroup) this.mPowerGroups.get(i)).getDesiredScreenPolicyLocked(sQuiescent, this.mDozeAfterScreenOff, this.mBootCompleted, this.mScreenBrightnessBoostInProgress, this.mBrightWhenDozingConfig, this.mScreenDimDurationOverrideFromWindowManager);
    }

    public int getDreamsBatteryLevelDrain() {
        return this.mDreamsBatteryLevelDrain;
    }

    public int getGlobalWakefulnessLocked() {
        return this.mWakefulnessRaw;
    }

    public int getLastShutdownReasonInternal() {
        this.mSystemProperties.getClass();
        String str = SystemProperties.get("sys.boot.reason", (String) null);
        str.getClass();
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

    public LocalService getLocalServiceInstance() {
        return this.mLocalService;
    }

    public int getPowerGroupSize() {
        int size;
        synchronized (this.mLock) {
            size = this.mPowerGroups.size();
        }
        return size;
    }

    public final long getScreenDimDurationLocked(long j) {
        long j2 = this.mScreenDimDurationOverrideFromWindowManager;
        return ((j2 == -1 || j2 >= j) && this.mScreenDimDurationOverrideFromSQD == -1) ? Math.min(this.mMaximumScreenDimDurationConfig, (long) (j * this.mMaximumScreenDimRatioConfig)) : j2 < j ? Math.max(j2, this.mScreenDimDurationOverrideFromSQD) : this.mScreenDimDurationOverrideFromSQD;
    }

    public final long getScreenOffTimeoutLocked(long j, long j2) {
        long j3 = this.mScreenOffTimeoutSetting;
        if (isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()) {
            j3 = Math.min(j3, this.mMaximumScreenOffTimeoutFromDeviceAdmin);
        }
        AdaptiveScreenOffTimeoutController adaptiveScreenOffTimeoutController = this.mAdaptiveScreenOffTimeoutController;
        if (adaptiveScreenOffTimeoutController != null) {
            long j4 = adaptiveScreenOffTimeoutController.mAdaptiveScreenOffTimeout;
            if (j4 >= 0) {
                j3 = j4;
            }
        }
        long j5 = this.mUserActivityTimeoutOverrideFromWindowManager;
        if (j5 >= 0) {
            j3 = Math.min(j3, j5);
        }
        if (j >= 0) {
            j3 = Math.min(j3, j);
        }
        if (j2 >= 0) {
            j3 = Math.min(j3, j2);
        }
        if (this.mIsDualViewMode) {
            long j6 = this.mScreenOffTimeoutForDex;
            long j7 = this.mUserActivityTimeoutForDexOverrideFromWindowManager;
            if (j7 >= 0) {
                j6 = Math.min(j6, j7);
            }
            j3 = Math.min(j3, j6);
        }
        if (j3 != this.mLastScreenTimeout) {
            Slog.d("PowerManagerService", "getScreenOffTimeoutLocked: " + this.mLastScreenTimeout + " -> " + j3);
            this.mLastScreenTimeout = (int) j3;
        }
        return Math.max(j3, this.mMinimumScreenOffTimeoutConfig);
    }

    public long getScreenOffTimeoutOverrideLocked(long j, long j2) {
        ScreenTimeoutOverridePolicy screenTimeoutOverridePolicy = this.mScreenTimeoutOverridePolicy;
        if (screenTimeoutOverridePolicy == null || (this.mWakeLockSummary & 256) == 0) {
            return j;
        }
        long j3 = screenTimeoutOverridePolicy.mScreenTimeoutOverrideConfig;
        return j3 < 0 ? j : Math.min(j3, j);
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

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0011, code lost:
    
        if (r2.mDualScreenPolicy == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getStartingLimitationOfBrightness(int r3, int r4) {
        /*
            r2 = this;
            android.content.Context r0 = r2.mContext
            java.lang.String r1 = "display"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.hardware.display.DisplayManager r0 = (android.hardware.display.DisplayManager) r0
            boolean r1 = com.android.server.power.PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
            if (r1 == 0) goto L14
            int r2 = r2.mDualScreenPolicy
            r1 = 1
            if (r2 != r1) goto L14
            goto L15
        L14:
            r1 = 0
        L15:
            android.view.Display r2 = r0.getDisplay(r1)
            android.hardware.display.BrightnessInfo r2 = r2.getBrightnessInfo()
            if (r2 == 0) goto L26
            float r2 = r2.adjustedBrightness
            int r2 = com.android.internal.display.BrightnessSynchronizer.brightnessFloatToInt(r2)
            goto L27
        L26:
            r2 = -1
        L27:
            if (r3 >= r4) goto L30
            if (r2 < 0) goto L30
            int r2 = java.lang.Math.max(r3, r2)
            return r2
        L30:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.getStartingLimitationOfBrightness(int, int):int");
    }

    public int getWakefulnessLocked(int i) {
        return ((PowerGroup) this.mPowerGroups.get(i)).mWakefulness;
    }

    public final void handleDexScreenOffTimeoutChange() {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("key", "timeout_dex");
            bundle.putString("def", "600000");
            Bundle call = this.mContext.getContentResolver().call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
            if (call != null) {
                String string = call.getString("timeout_dex");
                int parseInt = Integer.parseInt(string);
                synchronized (this.mLock) {
                    try {
                        if (parseInt != this.mScreenOffTimeoutForDex) {
                            Slog.d("PowerManagerService", "Dex screen off timeout : " + this.mScreenOffTimeoutForDex + " -> " + parseInt);
                            this.mScreenOffTimeoutForDex = parseInt;
                            this.mDirty = this.mDirty | 4;
                            updatePowerStateLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Slog.d("PowerManagerService", "timeout for dex is " + string + " = " + this.mScreenOffTimeoutForDex);
            }
        } catch (IllegalArgumentException unused) {
            Slog.e("PowerManagerService", "IllegalArgumentException :: getDeXSettings timeout_dex");
        }
    }

    public void handleSettingsChangedLocked() {
        updateSettingsLocked$1();
        updatePowerStateLocked();
    }

    public final void handleUidStateChangeLocked() {
        if (this.mUidsChanging) {
            this.mUidsChanged = true;
        } else {
            updateWakeLockDisabledStatesLocked();
        }
    }

    public final boolean isAttentiveTimeoutExpired(PowerGroup powerGroup, long j) {
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        return powerGroup.mGroupId == 0 && attentiveTimeoutLocked >= 0 && j >= powerGroup.mLastUserActivityTime + attentiveTimeoutLocked;
    }

    public final boolean isBeingKeptAwakeLocked(PowerGroup powerGroup) {
        return this.mStayOn || this.mProximityPositive || (powerGroup.mWakeLockSummary & 32) != 0 || (powerGroup.mUserActivitySummary & 3) != 0 || this.mScreenBrightnessBoostInProgress;
    }

    public final boolean isBeingKeptFromInattentiveSleepLocked() {
        return this.mStayOn || this.mScreenBrightnessBoostInProgress || this.mProximityPositive || !this.mBootCompleted;
    }

    public final boolean isItBedTimeYetLocked(PowerGroup powerGroup) {
        if (!this.mBootCompleted) {
            return false;
        }
        this.mClock.getClass();
        return isAttentiveTimeoutExpired(powerGroup, SystemClock.uptimeMillis()) ? !isBeingKeptFromInattentiveSleepLocked() : !isBeingKeptAwakeLocked(powerGroup);
    }

    public final boolean isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked() {
        long j = this.mMaximumScreenOffTimeoutFromDeviceAdmin;
        return j >= 0 && j < Long.MAX_VALUE;
    }

    public final boolean isWakeUpPreventionNeededLocked() {
        boolean z;
        if (!this.mWakeUpPreventionLocks.isEmpty()) {
            Slog.dk("Screen__On : Cancel (wakeUpPreventionEnabled)");
            return true;
        }
        if (this.mLastWakeUpReason == 112) {
            return false;
        }
        if (!this.mProximityPositive || this.mInterceptedPowerKeyForProximity || this.mInterceptedKeyForProximity) {
            z = false;
        } else {
            Slog.dk("Screen__On : Cancel (proximity)");
            z = true;
        }
        if (this.mDualScreenPolicy != 0) {
            int i = this.mCoverType;
            if (((i == 0 || i == 7 || i == 100) ? this.mIsCoverClosed : false) && !this.mIsDualViewMode) {
                Slog.dk("Screen__On : Cancel (coverType: " + this.mCoverType + ")");
                return true;
            }
        }
        return z;
    }

    public final void maybeUpdateForegroundProfileLastActivityLocked(long j) {
        ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.get(this.mForegroundProfile);
        int i = this.mForegroundProfile;
        if (i != 0 && profilePowerState == null && SemPersonaManager.isKnoxId(i)) {
            updateKnoxTimeoutValue(this.mForegroundProfile);
            Slog.d("PowerManagerService", "create ProfilePowerState for Workspace");
        } else {
            if (profilePowerState == null || j <= profilePowerState.mLastUserActivityTime) {
                return;
            }
            profilePowerState.mLastUserActivityTime = j;
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        synchronized (this.mLock) {
        }
    }

    public final void napInternal(int i, long j, boolean z) {
        synchronized (this.mLock) {
            dreamPowerGroupLocked((PowerGroup) this.mPowerGroups.get(0), j, i, z);
        }
    }

    public final void notifyWakeLockAcquiredLocked(WakeLock wakeLock) {
        if (!this.mSystemReady || wakeLock.mDisabled) {
            return;
        }
        wakeLock.mNotifiedAcquired = true;
        Notifier notifier = this.mNotifier;
        int i = wakeLock.mFlags;
        String str = wakeLock.mTag;
        String str2 = wakeLock.mPackageName;
        notifier.onWakeLockAcquired(i, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mCallback, wakeLock.mWorkSource, str, str2, wakeLock.mHistoryTag);
        restartNofifyLongTimerLocked(wakeLock);
    }

    public final void notifyWakeLockChangingLocked(WakeLock wakeLock, int i, String str, String str2, int i2, int i3, WorkSource workSource, String str3, IWakeLockCallback iWakeLockCallback) {
        if (this.mSystemReady && wakeLock.mNotifiedAcquired) {
            int i4 = wakeLock.mFlags;
            if ((i4 & 129) != 0) {
                EventLog.writeEvent(2729, "CHG".concat(i4 == 128 ? "(draw)" : ""), Long.valueOf(SystemClock.uptimeMillis() - wakeLock.mAcquireTime), wakeLock.mTag + ":" + wakeLock.mPackageName);
            }
            Notifier notifier = this.mNotifier;
            int i5 = wakeLock.mFlags;
            String str4 = wakeLock.mTag;
            String str5 = wakeLock.mPackageName;
            int i6 = wakeLock.mOwnerUid;
            int i7 = wakeLock.mOwnerPid;
            WorkSource workSource2 = wakeLock.mWorkSource;
            String str6 = wakeLock.mHistoryTag;
            IWakeLockCallback iWakeLockCallback2 = wakeLock.mCallback;
            int batteryStatsWakeLockMonitorType = notifier.getBatteryStatsWakeLockMonitorType(i5);
            int batteryStatsWakeLockMonitorType2 = notifier.getBatteryStatsWakeLockMonitorType(i);
            if (workSource2 != null && workSource != null && batteryStatsWakeLockMonitorType >= 0 && batteryStatsWakeLockMonitorType2 >= 0) {
                try {
                    notifier.mBatteryStats.noteChangeWakelockFromSource(workSource2, i7, str4, str6, batteryStatsWakeLockMonitorType, workSource, i3, str, str3, batteryStatsWakeLockMonitorType2, i2 == 1000 && (i & 1073741824) != 0);
                } catch (RemoteException unused) {
                }
            } else if (iWakeLockCallback2 != iWakeLockCallback && (iWakeLockCallback2 == null || iWakeLockCallback == null || iWakeLockCallback2.asBinder() != iWakeLockCallback.asBinder())) {
                notifier.onWakeLockReleased(i5, str4, str5, i6, i7, workSource2, str6, null, -1);
                notifier.onWakeLockAcquired(i, i2, i3, iWakeLockCallback, workSource, str, str2, str3);
            } else {
                notifier.onWakeLockReleased(i5, str4, str5, i6, i7, workSource2, str6, iWakeLockCallback2, -1);
                notifier.onWakeLockAcquired(i, i2, i3, iWakeLockCallback, workSource, str, str2, str3);
            }
            notifyWakeLockLongFinishedLocked(wakeLock);
            restartNofifyLongTimerLocked(wakeLock);
        }
    }

    public final void notifyWakeLockLongFinishedLocked(WakeLock wakeLock) {
        if (wakeLock.mNotifiedLong) {
            wakeLock.mNotifiedLong = false;
            Notifier notifier = this.mNotifier;
            String str = wakeLock.mTag;
            int i = wakeLock.mOwnerUid;
            WorkSource workSource = wakeLock.mWorkSource;
            String str2 = wakeLock.mHistoryTag;
            notifier.getClass();
            try {
                if (workSource != null) {
                    notifier.mBatteryStats.noteLongPartialWakelockFinishFromSource(str, str2, workSource);
                    FrameworkStatsLog.write(11, workSource, str, str2, 0);
                } else {
                    notifier.mBatteryStats.noteLongPartialWakelockFinish(str, str2, i);
                    FrameworkStatsLog.write_non_chained(11, i, (String) null, str, str2, 0);
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public final void notifyWakeLockLongStartedLocked(WakeLock wakeLock) {
        if (!this.mSystemReady || wakeLock.mDisabled) {
            return;
        }
        wakeLock.mNotifiedLong = true;
        Notifier notifier = this.mNotifier;
        String str = wakeLock.mTag;
        int i = wakeLock.mOwnerUid;
        WorkSource workSource = wakeLock.mWorkSource;
        String str2 = wakeLock.mHistoryTag;
        notifier.getClass();
        try {
            if (workSource != null) {
                notifier.mBatteryStats.noteLongPartialWakelockStartFromSource(str, str2, workSource);
                FrameworkStatsLog.write(11, workSource, str, str2, 1);
            } else {
                notifier.mBatteryStats.noteLongPartialWakelockStart(str, str2, i);
                FrameworkStatsLog.write_non_chained(11, i, (String) null, str, str2, 1);
            }
        } catch (RemoteException unused) {
        }
    }

    public final void notifyWakeLockReleasedLocked(WakeLock wakeLock, int i) {
        if (this.mSystemReady && wakeLock.mNotifiedAcquired) {
            int i2 = wakeLock.mFlags;
            if ((i2 & 129) != 0) {
                EventLog.writeEvent(2729, "REL".concat(i2 == 128 ? "(draw)" : ""), Long.valueOf(SystemClock.uptimeMillis() - wakeLock.mAcquireTime), wakeLock.mTag + ":" + wakeLock.mPackageName);
            }
            wakeLock.mNotifiedAcquired = false;
            wakeLock.mAcquireTime = 0L;
            this.mNotifier.onWakeLockReleased(wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mWorkSource, wakeLock.mHistoryTag, wakeLock.mCallback, i);
            notifyWakeLockLongFinishedLocked(wakeLock);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        int i2 = 0;
        if (i != 500) {
            if (i == 520) {
                boolean isSafeMode = isSafeMode();
                synchronized (this.mLock) {
                    this.mSafeMode = isSafeMode;
                }
                return;
            }
            if (i == 600) {
                synchronized (this.mLock) {
                    try {
                        i2 = Settings.Global.getInt(getContext().getContentResolver(), "boot_count");
                    } catch (Settings.SettingNotFoundException unused) {
                    }
                    Settings.Global.putInt(getContext().getContentResolver(), "boot_count", i2 + 1);
                }
                return;
            }
            if (i == 1000) {
                synchronized (this.mLock) {
                    try {
                        this.mClock.getClass();
                        long uptimeMillis = SystemClock.uptimeMillis();
                        this.mBootCompleted = true;
                        this.mDirty |= 16;
                        if (this.mBatterySaverSupported) {
                            BatterySaverStateMachine batterySaverStateMachine = this.mBatterySaverStateMachine;
                            batterySaverStateMachine.getClass();
                            batterySaverStateMachine.runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda0(batterySaverStateMachine, 4));
                        }
                        userActivityNoUpdateLocked(0, 0, 1000, uptimeMillis);
                        updatePowerStateLocked();
                        if (sQuiescent) {
                            PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(0);
                            this.mClock.getClass();
                            sleepPowerGroupLocked(powerGroup, SystemClock.uptimeMillis(), 10, 1000);
                        }
                        ((DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(this.mHandler), new DeviceStateListener());
                        this.mHandler.postDelayed(this.mCoverAuthReadyRunnable, 15000L);
                        if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW) {
                            this.mHandlerPmsMisc.post(new PowerManagerService$$ExternalSyntheticLambda1(this, 3));
                        }
                        ScreenCurtainController screenCurtainController = this.mScreenCurtainController;
                        if (screenCurtainController != null) {
                            screenCurtainController.mHandler.post(new ScreenCurtainController$$ExternalSyntheticLambda0(screenCurtainController, 0));
                        }
                    } finally {
                    }
                }
                return;
            }
            return;
        }
        synchronized (this.mLock) {
            this.mSystemReady = true;
            this.mDreamManager = (DreamManagerInternal) getLocalService(DreamManagerInternal.class);
            this.mDisplayManagerInternal = (DisplayManagerInternal) getLocalService(DisplayManagerInternal.class);
            this.mPolicy = (WindowManagerPolicy) getLocalService(WindowManagerPolicy.class);
            this.mBatteryManagerInternal = (BatteryManagerInternal) getLocalService(BatteryManagerInternal.class);
            this.mAttentionDetector.systemReady(this.mContext);
            SystemSensorManager systemSensorManager = new SystemSensorManager(this.mContext, this.mHandler.getLooper());
            this.mBatteryStats = BatteryStatsService.getService();
            Injector injector = this.mInjector;
            Looper mainLooper = Looper.getMainLooper();
            Context context = this.mContext;
            IBatteryStats iBatteryStats = this.mBatteryStats;
            this.mInjector.getClass();
            SuspendBlockerImpl createSuspendBlocker = Injector.createSuspendBlocker(this, "PowerManagerService.Broadcasts");
            WindowManagerPolicy windowManagerPolicy = this.mPolicy;
            Executor executor = BackgroundThread.getExecutor();
            PowerManagerFlags powerManagerFlags = this.mFeatureFlags;
            injector.getClass();
            Notifier notifier = new Notifier(mainLooper, context, iBatteryStats, createSuspendBlocker, windowManagerPolicy, executor, powerManagerFlags);
            this.mNotifier = notifier;
            SparseArray sparseArray = this.mPowerGroups;
            AnonymousClass1 anonymousClass1 = this.mPowerGroupWakefulnessChangeListener;
            DisplayManagerInternal displayManagerInternal = this.mDisplayManagerInternal;
            this.mClock.getClass();
            sparseArray.append(0, new PowerGroup(anonymousClass1, notifier, displayManagerInternal, SystemClock.uptimeMillis()));
            this.mDisplayManagerInternal.registerDisplayGroupListener(new DisplayGroupPowerChangeListener());
            this.mDreamManager.registerDreamManagerStateListener(new DreamManagerStateListener());
            this.mInjector.getClass();
            this.mWirelessChargerDetector = new WirelessChargerDetector(systemSensorManager, Injector.createSuspendBlocker(this, "PowerManagerService.WirelessChargerDetector"), this.mHandler);
            this.mSettingsObserver = new SettingsObserver(this, this.mHandler, 0);
            this.mAttentionLight = ((LightsManager) getLocalService(LightsManager.class)).getLight(5);
            this.mDisableScreenWakeLocksWhileCached = this.mDeviceConfigProvider.mDeviceConfig.getBoolean("display_manager", "disable_screen_wake_locks_while_cached", true);
            this.mDeviceConfigProvider.mDeviceConfig.addOnPropertiesChangedListener("display_manager", BackgroundThread.getExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda12
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    synchronized (powerManagerService.mLock) {
                        powerManagerService.mDisableScreenWakeLocksWhileCached = powerManagerService.mDeviceConfigProvider.mDeviceConfig.getBoolean("display_manager", "disable_screen_wake_locks_while_cached", true);
                        powerManagerService.updateWakeLockDisabledStatesLocked();
                    }
                }
            });
            this.mDisplayManagerInternal.initPowerManagement(this.mDisplayPowerCallbacks, this.mHandler, systemSensorManager);
            addPowerGroupsForNonDefaultDisplayGroupLocked();
            try {
                ActivityManager.getService().registerUserSwitchObserver(new ForegroundProfileObserver(), "PowerManagerService");
            } catch (RemoteException unused2) {
            }
            if (this.mFeatureCoverSysfs) {
                Injector injector2 = this.mInjector;
                Context context2 = this.mContext;
                injector2.getClass();
                CoverManager coverManager = new CoverManager(context2);
                this.mCoverManager = coverManager;
                coverManager.registerListener(this.mCoverStateListener);
            }
            if (this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
                Injector injector3 = this.mInjector;
                Object obj = this.mLock;
                Context context3 = this.mContext;
                BatteryManagerInternal batteryManagerInternal = this.mBatteryManagerInternal;
                AnonymousClass1 anonymousClass12 = new AnonymousClass1();
                injector3.getClass();
                this.mWirelessChargerMotionDetector = new WirelessChargerMotionDetector(obj, context3, batteryManagerInternal, anonymousClass12);
            }
            Injector injector4 = this.mInjector;
            Object obj2 = this.mLock;
            Context context4 = this.mContext;
            AnonymousClass1 anonymousClass13 = this.mAbuseWakeLockDetectorCallback;
            injector4.getClass();
            this.mAbuseWakeLockDetector = new AbuseWakeLockDetector(obj2, context4, anonymousClass13, IWindowManager.Stub.asInterface(ServiceManager.getService("window")));
            Injector injector5 = this.mInjector;
            Object obj3 = this.mLock;
            Context context5 = this.mContext;
            PowerManagerService$$ExternalSyntheticLambda1 powerManagerService$$ExternalSyntheticLambda1 = new PowerManagerService$$ExternalSyntheticLambda1(this, 1);
            injector5.getClass();
            this.mSmartStayController = new SmartStayController(obj3, context5, powerManagerService$$ExternalSyntheticLambda1, new SmartStayController.Injector());
            this.mLowPowerStandbyController.systemReady();
            HqmDataDispatcher.HqmDataDispatcherHolder.INSTANCE = new HqmDataDispatcher(this.mContext, this.mHandlerPmsMisc.getLooper());
            this.mQuickTilesSettingsObserver = new SettingsObserver(this, this.mHandler, 2);
            Injector injector6 = this.mInjector;
            Context context6 = this.mContext;
            injector6.getClass();
            this.mForegroundPackageObserver = new ForegroundPackageObserver((ActivityManager) context6.getSystemService("activity"), ActivityManagerNative.getDefault());
            Injector injector7 = this.mInjector;
            Context context7 = this.mContext;
            Object obj4 = this.mLock;
            Looper looper = this.mHandler.getLooper();
            ISemInputDeviceManager asInterface = ISemInputDeviceManager.Stub.asInterface(ServiceManager.getService("SemInputDeviceManagerService"));
            injector7.getClass();
            this.mScreenCurtainController = new ScreenCurtainController(context7, obj4, looper, asInterface);
            Injector injector8 = this.mInjector;
            AnonymousClass1 anonymousClass14 = this.mAdaptiveScreenOffTimeoutCallbacks;
            injector8.getClass();
            this.mAdaptiveScreenOffTimeoutController = new AdaptiveScreenOffTimeoutController(anonymousClass14);
            updateQuickTilesSettingsLocked();
            readConfigurationLocked();
            updateSettingsLocked$1();
            if (this.mFeatureFlags.mEarlyScreenTimeoutDetectorFlagState.isEnabled()) {
                Context context8 = this.mContext;
                long j = this.mMinimumScreenOffTimeoutConfig;
                PowerManagerService$$ExternalSyntheticLambda14 powerManagerService$$ExternalSyntheticLambda14 = new PowerManagerService$$ExternalSyntheticLambda14(this);
                ScreenTimeoutOverridePolicy screenTimeoutOverridePolicy = new ScreenTimeoutOverridePolicy();
                screenTimeoutOverridePolicy.mLastAutoReleaseReason = -1;
                long integer = context8.getResources().getInteger(R.integer.config_windowOutsetBottom);
                screenTimeoutOverridePolicy.mScreenTimeoutOverrideConfig = integer;
                if (integer < j) {
                    android.util.Slog.w("ScreenTimeoutOverridePolicy", "Screen timeout override is smaller than the minimum timeout : " + integer);
                    screenTimeoutOverridePolicy.mScreenTimeoutOverrideConfig = -1L;
                }
                screenTimeoutOverridePolicy.mPolicyCallback = powerManagerService$$ExternalSyntheticLambda14;
                this.mScreenTimeoutOverridePolicy = screenTimeoutOverridePolicy;
            }
            this.mDirty |= 256;
            updatePowerStateLocked();
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Constants constants = this.mConstants;
        constants.mResolver = contentResolver;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("power_manager_constants"), false, constants);
        constants.updateConstants();
        if (this.mBatterySaverSupported) {
            this.mBatterySaverStateMachine.systemReady();
        }
        final ScreenUndimDetector screenUndimDetector = this.mScreenUndimDetector;
        Context context9 = this.mContext;
        screenUndimDetector.readValuesFromDeviceConfig();
        DeviceConfig.addOnPropertiesChangedListener("attention_manager_service", context9.getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.ScreenUndimDetector$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                ScreenUndimDetector screenUndimDetector2 = ScreenUndimDetector.this;
                screenUndimDetector2.getClass();
                for (String str : properties.getKeyset()) {
                    android.util.Slog.i("ScreenUndimDetector", "onDeviceConfigChange; key=" + str);
                    str.getClass();
                    switch (str) {
                        case "undims_required":
                        case "keep_screen_on_enabled":
                        case "keep_screen_on_for_millis":
                        case "max_duration_between_undims_millis":
                            screenUndimDetector2.readValuesFromDeviceConfig();
                            return;
                        default:
                            android.util.Slog.i("ScreenUndimDetector", "Ignoring change on ".concat(str));
                    }
                }
            }
        });
        screenUndimDetector.mWakeLock = ((PowerManager) context9.getSystemService(PowerManager.class)).newWakeLock(536870922, "UndimDetectorWakeLock");
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("sysui_qs_tiles"), false, this.mQuickTilesSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("sysui_quick_qs_tiles"), false, this.mQuickTilesSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("screensaver_enabled"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("screensaver_activate_on_sleep"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("screensaver_activate_on_dock"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_off_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("sleep_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("attentive_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("stay_on_while_plugged_in"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_auto_brightness_adj"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("theater_mode_on"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("double_tap_to_wake"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("device_demo_mode"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.System.getUriFor("user_activity_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("intelligent_sleep_mode"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("display_outdoor_mode"), false, this.mSettingsObserver, -1);
        setQuickPanelBrightnessBarDisabled(false);
        if (PowerManagerUtil.SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED) {
            contentResolver.registerContentObserver(Settings.System.getUriFor("aod_mode"), false, this.mSettingsObserver, -1);
        }
        contentResolver.registerContentObserver(Settings.Global.getUriFor("sem_power_saving_adjust_brightness_factor"), false, this.mSettingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiver(new BatteryReceiver(), intentFilter, null, this.mHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.DREAMING_STARTED");
        intentFilter2.addAction("android.intent.action.DREAMING_STOPPED");
        this.mContext.registerReceiver(new DockReceiver(this, 1), intentFilter2, null, this.mHandler);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_SWITCHED");
        this.mContext.registerReceiver(new UserSwitchedReceiver(), intentFilter3, null, this.mHandler);
        this.mNoUserActivityIntent = new Intent("com.samsung.android.knox.intent.action.NO_USER_ACTIVITY");
        this.mUserActivityIntent = new Intent("com.samsung.android.knox.intent.action.USER_ACTIVITY");
        this.mNoUserActivitySent = false;
        if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW) {
            SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
            if (semDesktopModeManager != null) {
                semDesktopModeManager.registerListener(new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda15
                    public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                        PowerManagerService powerManagerService = PowerManagerService.this;
                        synchronized (powerManagerService.mLock) {
                            try {
                                boolean z = powerManagerService.mIsDualViewMode;
                                int displayType = semDesktopModeState.getDisplayType();
                                int enabled = semDesktopModeState.getEnabled();
                                powerManagerService.mIsDualViewMode = displayType == 102 && (enabled == 4 || enabled == 3);
                                Slog.d("PowerManagerService", "Dex display type: " + displayType + ", enabled: " + enabled + ", dual view: " + z + " -> " + powerManagerService.mIsDualViewMode);
                                if (z != powerManagerService.mIsDualViewMode && powerManagerService.mPowerGroups.contains(2)) {
                                    if (powerManagerService.mIsDualViewMode) {
                                        powerManagerService.mClock.getClass();
                                        powerManagerService.setWakefulnessLocked(2, 1, SystemClock.uptimeMillis(), 1000, 11, 1000, powerManagerService.mContext.getOpPackageName(), "dex enabled");
                                    } else {
                                        powerManagerService.mClock.getClass();
                                        powerManagerService.setWakefulnessLocked(2, 0, SystemClock.uptimeMillis(), 1000, 12, 1000, powerManagerService.mContext.getOpPackageName(), "dex disabled");
                                    }
                                    powerManagerService.updatePowerStateLocked();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            }
            contentResolver.registerContentObserver(Uri.withAppendedPath(DEX_SETTINGS_URI, "timeout_dex"), true, new SettingsObserver(this, this.mHandlerPmsMisc, 1));
            Intent intent = new Intent("com.samsung.android.server.power.ACTION_INTERNAL_DISPLAY_OFF_BY_POWERKEY");
            this.mInternalDisplayOffByPowerKeyIntent = intent;
            intent.addFlags(1342177280);
        }
        this.mSemInputDeviceManager = (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
        this.mContext.registerReceiver(new DockReceiver(this, 0), BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.DOCK_EVENT"), null, this.mHandler);
        if (PowerManagerUtil.fileExist("/sys/class/lcd/panel/display_on")) {
            return;
        }
        PowerManagerUtil.sCurrentScreenOnProfiler.getClass();
        PowerManagerUtil.ScreenOnProfiler.CHECK_FRAME = false;
        Slog.d("PowerManagerUtil", "disable FrameCheck");
    }

    public final void onPowerGroupEventLocked(int i, PowerGroup powerGroup) {
        this.mWakefulnessChanging = true;
        this.mDirty |= 2;
        int i2 = powerGroup.mGroupId;
        if (i == 1) {
            this.mPowerGroups.delete(i2);
        }
        int globalWakefulnessLocked = getGlobalWakefulnessLocked();
        int recalculateGlobalWakefulnessLocked = recalculateGlobalWakefulnessLocked();
        Injector.AnonymousClass1 anonymousClass1 = this.mClock;
        if (i == 0 && recalculateGlobalWakefulnessLocked == 1) {
            anonymousClass1.getClass();
            userActivityNoUpdateLocked(powerGroup, SystemClock.uptimeMillis(), 0, 0, 1000);
            Notifier notifier = this.mNotifier;
            int i3 = powerGroup.mWakefulness;
            anonymousClass1.getClass();
            notifier.onGroupWakefulnessChangeStarted(i2, i3, 10, SystemClock.uptimeMillis());
        } else if (i == 1) {
            Notifier notifier2 = this.mNotifier;
            notifier2.mInteractivityByGroupId.remove(i2);
            WakefulnessSessionObserver wakefulnessSessionObserver = notifier2.mWakefulnessSessionObserver;
            if (wakefulnessSessionObserver.mPowerGroups.contains(i2)) {
                wakefulnessSessionObserver.mPowerGroups.delete(i2);
            }
        }
        if (globalWakefulnessLocked != recalculateGlobalWakefulnessLocked) {
            int i4 = 11;
            if (recalculateGlobalWakefulnessLocked != 1) {
                if (recalculateGlobalWakefulnessLocked != 3) {
                    i4 = 0;
                } else if (i != 1) {
                    i4 = 12;
                }
            } else if (i == 0) {
                i4 = 10;
            }
            int i5 = i4;
            anonymousClass1.getClass();
            updateGlobalWakefulnessLocked(i5, 1000, this.mContext.getOpPackageName(), VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "groupId: "), 1000, SystemClock.uptimeMillis());
        }
        this.mDirty |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
        updatePowerStateLocked();
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("power", this.mBinderService, false, 1);
        publishLocalService(PowerManagerInternal.class, this.mLocalService);
        Watchdog.getInstance().addMonitor(this);
        Watchdog.getInstance().addThread(this.mHandler);
    }

    public void onUserActivity() {
        synchronized (this.mLock) {
            PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(0);
            this.mClock.getClass();
            powerGroup.mLastUserActivityTime = SystemClock.uptimeMillis();
            powerGroup.mLastUserActivityEvent = 0;
        }
    }

    public void readConfigurationLocked() {
        Resources resources = this.mContext.getResources();
        this.mDecoupleHalAutoSuspendModeFromDisplayConfig = true;
        this.mDecoupleHalInteractiveModeFromDisplayConfig = true;
        this.mWakeUpWhenPluggedOrUnpluggedConfig = true;
        this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig = resources.getBoolean(R.bool.config_allow_ussd_over_ims);
        this.mSuspendWhenScreenOffDueToProximityConfig = true;
        this.mAttentiveTimeoutConfig = resources.getInteger(R.integer.config_bluetooth_idle_cur_ma);
        this.mAttentiveWarningDurationConfig = resources.getInteger(R.integer.config_bluetooth_operating_voltage_mv);
        this.mDreamsSupportedConfig = resources.getBoolean(R.bool.config_earcFeatureDisabled_default);
        this.mDreamsEnabledByDefaultConfig = resources.getBoolean(R.bool.config_eap_sim_based_auth_supported);
        this.mDreamsActivatedOnSleepByDefaultConfig = resources.getBoolean(R.bool.config_dreamsSupported);
        this.mDreamsActivatedOnDockByDefaultConfig = resources.getBoolean(R.bool.config_dreamsOnlyEnabledForDockUser);
        this.mDreamsEnabledOnBatteryConfig = resources.getBoolean(R.bool.config_earcEnabled_userConfigurable);
        this.mDreamsBatteryLevelMinimumWhenPoweredConfig = resources.getInteger(R.integer.config_lightSensorWarmupTime);
        this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig = resources.getInteger(R.integer.config_lidOpenRotation);
        this.mDreamsBatteryLevelDrainCutoffConfig = resources.getInteger(R.integer.config_lidNavigationAccessibility);
        this.mDreamsDisabledByAmbientModeSuppressionConfig = resources.getBoolean(R.bool.config_duplicate_port_omadm_wappush);
        this.mDozeAfterScreenOff = resources.getBoolean(R.bool.config_dozeSupportsAodWallpaper);
        this.mBrightWhenDozingConfig = resources.getBoolean(R.bool.config_callNotificationActionColorsRequireColorized);
        this.mMinimumScreenOffTimeoutConfig = resources.getInteger(R.integer.config_pauseRotationWhenUnfolding_displaySwitchTimeout);
        this.mMaximumScreenDimDurationConfig = 20000L;
        this.mMaximumScreenDimRatioConfig = 0.333f;
        this.mSupportsDoubleTapWakeConfig = resources.getBoolean(R.bool.config_supportsRoundedCornersOnWindows);
        this.mScreenBrightnessForClearCoverConfig = BrightnessSynchronizer.brightnessIntToFloat(resources.getInteger(R.integer.config_virtualKeyQuietTimeMillis));
        this.mBrightnessLimitByCoverConfig = resources.getInteger(R.integer.config_defaultBinderHeavyHitterWatcherBatchSize);
    }

    public final int recalculateGlobalWakefulnessLocked() {
        int i = 0;
        for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
            int i3 = ((PowerGroup) this.mPowerGroups.valueAt(i2)).mWakefulness;
            if (i3 == 1) {
                return 1;
            }
            if (i3 == 2 && (i == 0 || i == 3)) {
                i = 2;
            } else if (i3 == 3 && i == 0) {
                i = 3;
            }
        }
        return i;
    }

    public final void removeWakeLockNoUpdateLocked(WakeLock wakeLock, int i, int i2) {
        this.mWakeLocks.remove(i);
        UidState uidState = wakeLock.mUidState;
        int i3 = uidState.mNumWakeLocks - 1;
        uidState.mNumWakeLocks = i3;
        if (i3 <= 0 && uidState.mProcState == 20) {
            this.mUidState.remove(uidState.mUid);
        }
        notifyWakeLockReleasedLocked(wakeLock, i2);
        if ((wakeLock.mFlags & 536870912) != 0 && isScreenLock(wakeLock)) {
            Slog.d("PowerManagerService", "[api] applyWakeLockFlagsOnReleaseLocked : userActivityNoUpdateLocked is called : " + wakeLock);
            Integer powerGroupId = wakeLock.getPowerGroupId();
            Injector.AnonymousClass1 anonymousClass1 = this.mClock;
            if (powerGroupId == null || powerGroupId.intValue() == -1) {
                anonymousClass1.getClass();
                userActivityNoUpdateLocked(0, 1, wakeLock.mOwnerUid, SystemClock.uptimeMillis());
            } else {
                PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(wakeLock.getPowerGroupId().intValue());
                anonymousClass1.getClass();
                userActivityNoUpdateLocked(powerGroup, SystemClock.uptimeMillis(), 0, 1, wakeLock.mOwnerUid);
            }
        }
        this.mDirty |= 1;
    }

    public final void restartNofifyLongTimerLocked(WakeLock wakeLock) {
        this.mClock.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        wakeLock.mAcquireTime = uptimeMillis;
        if ((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1 && this.mNotifyLongScheduled == 0) {
            long j = uptimeMillis + 60000;
            this.mNotifyLongScheduled = j;
            Handler handler = this.mHandler;
            Message obtainMessage = handler.obtainMessage(4);
            obtainMessage.setAsynchronous(true);
            handler.sendMessageAtTime(obtainMessage, j);
        }
    }

    public final void scheduleSandmanLocked() {
        if (this.mSandmanScheduled) {
            return;
        }
        this.mSandmanScheduled = true;
        for (int i = 0; i < this.mPowerGroups.size(); i++) {
            PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i);
            if (powerGroup.mSupportsSandman) {
                Handler handler = this.mHandler;
                Message obtainMessage = handler.obtainMessage(2);
                obtainMessage.arg1 = powerGroup.mGroupId;
                obtainMessage.setAsynchronous(true);
                this.mClock.getClass();
                handler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
            }
        }
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

    public final void setEnsureTransitionToDozingReleaserLocked(boolean z) {
        Handler handler = this.mHandler;
        if (!z) {
            if (this.mEnsureTransitionToDozingReleaserRunning) {
                handler.removeCallbacks(this.mEnsureTransitionToDozingReleaser);
                this.mEnsureTransitionToDozingReleaserRunning = false;
                return;
            }
            return;
        }
        if (this.mEnsureTransitionToDozingReleaserRunning) {
            return;
        }
        Slog.d("PowerManagerService", "postDelayed mEnsureTransitionToDozingReleaserRunning");
        this.mEnsureTransitionToDozingReleaserRunning = true;
        handler.postDelayed(this.mEnsureTransitionToDozingReleaser, 5000L);
    }

    public final void setHalAutoSuspendModeLocked(boolean z) {
        if (this.mUseAutoSuspend && z != this.mHalAutoSuspendModeEnabled) {
            this.mHalAutoSuspendModeEnabled = z;
            Trace.traceBegin(131072L, "setHalAutoSuspend(" + z + ")");
            try {
                this.mNativeWrapper.getClass();
                nativeSetAutoSuspend(z);
            } finally {
                Trace.traceEnd(131072L);
            }
        }
    }

    public final void setHalInteractiveModeLocked(boolean z) {
        SemInputDeviceManager semInputDeviceManager;
        NativeWrapper nativeWrapper = this.mNativeWrapper;
        if (z != this.mHalInteractiveModeEnabled || this.mForceSetHalInteractiveMode) {
            Slog.d("PowerManagerService", "Setting HAL interactive mode to " + z);
            if (this.mForceSetHalInteractiveMode) {
                Slog.d("PowerManagerService", "setHalInteractiveModeLocked called by dual screen policy change");
                this.mForceSetHalInteractiveMode = false;
            }
            this.mHalInteractiveModeEnabled = z;
            Trace.traceBegin(131072L, "setHalInteractive(" + z + ")");
            if (!z) {
                try {
                    if (this.mProximityPositive && (semInputDeviceManager = this.mSemInputDeviceManager) != null) {
                        semInputDeviceManager.setProxPowerOff(this.mDualScreenPolicy == 1 ? 2 : 1, 1);
                    }
                } catch (Throwable th) {
                    Trace.traceEnd(131072L);
                    throw th;
                }
            }
            nativeWrapper.getClass();
            nativeSetPowerMode(7, z);
            int i = this.mDualScreenPolicy;
            nativeWrapper.getClass();
            nativeSetInteractiveAsync(z, i);
            Trace.traceEnd(131072L);
        }
    }

    public final void setLCDFlashModeInternal(IBinder iBinder, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mLCDFlashModeLock == null) {
                    this.mLCDFlashModeLock = new LCDFlashModeLock(iBinder);
                }
                if (this.mLCDFlashMode != z) {
                    this.mLCDFlashMode = z;
                    this.mDirty |= 32;
                    updatePowerStateLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setPowerModeInternal(int i, boolean z) {
        BatterySaverStateMachine batterySaverStateMachine;
        boolean z2;
        if (i == 5 && z && (batterySaverStateMachine = this.mBatterySaverStateMachine) != null) {
            BatterySaverController batterySaverController = batterySaverStateMachine.mBatterySaverController;
            if (batterySaverController.isPolicyEnabled()) {
                BatterySaverPolicy batterySaverPolicy = batterySaverController.mBatterySaverPolicy;
                synchronized (batterySaverPolicy.mLock) {
                    z2 = batterySaverPolicy.mEffectivePolicyRaw.disableLaunchBoost;
                }
                if (z2) {
                    return false;
                }
            }
        }
        this.mNativeWrapper.getClass();
        return nativeSetPowerMode(i, z);
    }

    public final void setQuickPanelBrightnessBarDisabled(boolean z) {
        Slog.d("PowerManagerService", "Hide QuickPanel Brightness Bar: " + z);
        Settings.System.putStringForUser(this.mContext.getContentResolver(), "pms_notification_panel_brightness_adjustment", z ? this.mScreenBrightnessOverridePackageFromWindowManager : "", -2);
    }

    public final void setUserActivityTimeoutLocked(long j) {
        Slog.d("PowerManagerService", "setUserActivityTimeoutLocked SRUK : now=" + j);
        if (this.mBootCompleted) {
            long j2 = j + this.mUserActivityTimeoutSetting;
            boolean z = this.mNoUserActivitySent;
            Handler handler = this.mHandlerPmsMisc;
            if (z) {
                AnonymousClass6 anonymousClass6 = this.mUserActivityTask;
                handler.removeCallbacks(anonymousClass6);
                handler.post(anonymousClass6);
            }
            AnonymousClass6 anonymousClass62 = this.mUserActivityTimeoutTask;
            handler.removeCallbacks(anonymousClass62);
            handler.postAtTime(anonymousClass62, j2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setWakeLockDisabledStateLocked(com.android.server.power.PowerManagerService.WakeLock r11) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.setWakeLockDisabledStateLocked(com.android.server.power.PowerManagerService$WakeLock):boolean");
    }

    public void setWakefulnessLocked(int i, int i2, long j, int i3, int i4, int i5, String str, String str2) {
        ((PowerGroup) this.mPowerGroups.get(i)).setWakefulnessLocked(i2, j, i3, i4, i5, str, str2);
        this.mInjector.getClass();
        PowerManager.invalidateIsInteractiveCaches();
    }

    public final boolean sleepPowerGroupLocked(PowerGroup powerGroup, long j, int i, int i2) {
        if (!this.mBootCompleted || !this.mSystemReady || j < powerGroup.mLastWakeTime || powerGroup.mWakefulness == 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder("Screen__Off(s) - ");
        sb.append(this.mScreenOnOffCount);
        sb.append(" : ");
        sb.append(this.mScreenOffReason);
        sb.append(" (");
        sb.append(PowerManager.sleepReasonToString(i));
        sb.append(") groupId=");
        int i3 = powerGroup.mGroupId;
        sb.append(i3);
        String sb2 = sb.toString();
        int i4 = Slog.$r8$clinit;
        Slog.i("PowerManagerService", "!@" + sb2);
        this.mScreenOnReason = "";
        this.mScreenOffReason = "";
        Trace.traceBegin(131072L, "sleepPowerGroup");
        try {
            Slog.i("PowerGroup", "Sleeping power group (groupId=" + i3 + ", uid=" + i2 + ", reason=" + PowerManager.sleepReasonToString(i) + ")...");
            powerGroup.mIsSandmanSummoned = true;
            powerGroup.setWakefulnessLocked(0, j, i2, i, 0, null, null);
            return true;
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public final void updateAttentiveStateLocked(int i, long j) {
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        final boolean z = false;
        long j2 = ((PowerGroup) this.mPowerGroups.get(0)).mLastUserActivityTime + attentiveTimeoutLocked;
        long j3 = j2 - this.mAttentiveWarningDurationConfig;
        long attentiveTimeoutLocked2 = getAttentiveTimeoutLocked();
        final InattentiveSleepWarningController inattentiveSleepWarningController = this.mInattentiveSleepWarningOverlayController;
        boolean z2 = inattentiveSleepWarningController.mIsShown;
        Handler handler = inattentiveSleepWarningController.mHandler;
        final boolean z3 = true;
        if (z2) {
            if (getGlobalWakefulnessLocked() == 0) {
                if (inattentiveSleepWarningController.mIsShown) {
                    handler.post(new Runnable() { // from class: com.android.server.power.InattentiveSleepWarningController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InattentiveSleepWarningController inattentiveSleepWarningController2 = InattentiveSleepWarningController.this;
                            boolean z4 = z;
                            inattentiveSleepWarningController2.getClass();
                            try {
                                if (inattentiveSleepWarningController2.mStatusBarService == null) {
                                    inattentiveSleepWarningController2.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                                }
                                inattentiveSleepWarningController2.mStatusBarService.dismissInattentiveSleepWarning(z4);
                            } catch (RemoteException e) {
                                Log.e("InattentiveSleepWarning", "Failed to dismiss inattentive sleep warning", e);
                            }
                        }
                    });
                    inattentiveSleepWarningController.mIsShown = false;
                }
            } else if (attentiveTimeoutLocked2 < 0 || isBeingKeptFromInattentiveSleepLocked() || j < j3) {
                if (inattentiveSleepWarningController.mIsShown) {
                    handler.post(new Runnable() { // from class: com.android.server.power.InattentiveSleepWarningController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InattentiveSleepWarningController inattentiveSleepWarningController2 = InattentiveSleepWarningController.this;
                            boolean z4 = z3;
                            inattentiveSleepWarningController2.getClass();
                            try {
                                if (inattentiveSleepWarningController2.mStatusBarService == null) {
                                    inattentiveSleepWarningController2.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                                }
                                inattentiveSleepWarningController2.mStatusBarService.dismissInattentiveSleepWarning(z4);
                            } catch (RemoteException e) {
                                Log.e("InattentiveSleepWarning", "Failed to dismiss inattentive sleep warning", e);
                            }
                        }
                    });
                    inattentiveSleepWarningController.mIsShown = false;
                }
            }
            z = true;
        }
        if (attentiveTimeoutLocked >= 0) {
            if (z || (i & 19122) != 0) {
                Handler handler2 = this.mHandler;
                handler2.removeMessages(5);
                if (getGlobalWakefulnessLocked() == 0 || isBeingKeptFromInattentiveSleepLocked()) {
                    return;
                }
                if (j < j3) {
                    j2 = j3;
                } else if (j >= j2) {
                    j2 = -1;
                } else if (!inattentiveSleepWarningController.mIsShown) {
                    handler.post(new Runnable() { // from class: com.android.server.power.InattentiveSleepWarningController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            InattentiveSleepWarningController inattentiveSleepWarningController2 = InattentiveSleepWarningController.this;
                            inattentiveSleepWarningController2.getClass();
                            try {
                                if (inattentiveSleepWarningController2.mStatusBarService == null) {
                                    inattentiveSleepWarningController2.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                                }
                                inattentiveSleepWarningController2.mStatusBarService.showInattentiveSleepWarning();
                            } catch (RemoteException e) {
                                Log.e("InattentiveSleepWarning", "Failed to show inattentive sleep warning", e);
                                inattentiveSleepWarningController2.mIsShown = false;
                            }
                        }
                    });
                    inattentiveSleepWarningController.mIsShown = true;
                }
                if (j2 >= 0) {
                    Message obtainMessage = handler2.obtainMessage(5);
                    obtainMessage.setAsynchronous(true);
                    handler2.sendMessageAtTime(obtainMessage, j2);
                }
            }
        }
    }

    public final void updateGlobalWakefulnessLocked(int i, int i2, String str, String str2, int i3, long j) {
        String str3;
        int recalculateGlobalWakefulnessLocked = recalculateGlobalWakefulnessLocked();
        int globalWakefulnessLocked = getGlobalWakefulnessLocked();
        if (globalWakefulnessLocked == recalculateGlobalWakefulnessLocked) {
            return;
        }
        if (recalculateGlobalWakefulnessLocked != 0) {
            Injector.AnonymousClass1 anonymousClass1 = this.mClock;
            if (recalculateGlobalWakefulnessLocked == 1) {
                Slog.i("PowerManagerService", "Waking up from " + PowerManagerInternal.wakefulnessToString(globalWakefulnessLocked) + " (uid=" + i2 + ", reason=" + PowerManager.wakeReasonToString(i) + ", details=" + str2 + ")...");
                this.mLastGlobalWakeTime = j;
                this.mLastGlobalWakeReason = i;
                anonymousClass1.getClass();
                this.mLastGlobalWakeTimeRealtime = SystemClock.elapsedRealtime();
                str3 = "wakeUp";
            } else if (recalculateGlobalWakefulnessLocked == 2) {
                Slog.i("PowerManagerService", "Nap time (uid " + i2 + ")...");
                str3 = "nap";
            } else {
                if (recalculateGlobalWakefulnessLocked != 3) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(recalculateGlobalWakefulnessLocked, "Unexpected wakefulness: "));
                }
                Slog.i("PowerManagerService", "Going to sleep due to " + PowerManager.sleepReasonToString(i) + " (uid " + i2 + ", screenOffTimeout=" + this.mScreenOffTimeoutSetting + ", activityTimeoutWM=" + this.mUserActivityTimeoutOverrideFromWindowManager + ", maxDimRatio=" + this.mMaximumScreenDimRatioConfig + ", maxDimDur=" + this.mMaximumScreenDimDurationConfig + ")...");
                this.mLastGlobalSleepTime = j;
                this.mLastGlobalSleepReason = i;
                anonymousClass1.getClass();
                this.mLastGlobalSleepTimeRealtime = SystemClock.elapsedRealtime();
                this.mDozeStartInProgress = true;
                str3 = "goToSleep";
            }
        } else {
            Slog.i("PowerManagerService", "Sleeping (uid " + i2 + ")...");
            if (globalWakefulnessLocked != 3) {
                this.mLastGlobalSleepTime = j;
                this.mLastGlobalSleepReason = i;
            }
            str3 = "reallyGoToSleep";
        }
        Trace.traceBegin(131072L, str3);
        try {
            this.mInjector.getClass();
            PowerManager.invalidateIsInteractiveCaches();
            this.mWakefulnessRaw = recalculateGlobalWakefulnessLocked;
            this.mWakefulnessChanging = true;
            this.mDirty |= 2;
            this.mDozeStartInProgress &= recalculateGlobalWakefulnessLocked == 3;
            Notifier notifier = this.mNotifier;
            if (notifier != null) {
                notifier.onGlobalWakefulnessChangeStarted(recalculateGlobalWakefulnessLocked, i, j);
            }
            AttentionDetector attentionDetector = this.mAttentionDetector;
            attentionDetector.mWakefulness = recalculateGlobalWakefulnessLocked;
            if (recalculateGlobalWakefulnessLocked != 1) {
                if (attentionDetector.mRequested.get()) {
                    attentionDetector.mAttentionManager.cancelAttentionCheck(attentionDetector.mCallback);
                    attentionDetector.mRequested.set(false);
                }
                attentionDetector.resetConsecutiveExtensionCount();
            }
            if (recalculateGlobalWakefulnessLocked != 0) {
                if (recalculateGlobalWakefulnessLocked == 1) {
                    Notifier notifier2 = this.mNotifier;
                    notifier2.getClass();
                    try {
                        notifier2.mBatteryStats.noteWakeUp(str2, i2);
                        if (str != null) {
                            notifier2.mAppOps.noteOpNoThrow(61, i3, str);
                        }
                    } catch (RemoteException unused) {
                    }
                    FrameworkStatsLog.write(FrameworkStatsLog.DISPLAY_WAKE_REPORTED, i, i2);
                    if (sQuiescent) {
                        this.mDirty |= 4096;
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
                EventLog.writeEvent(2724, i4);
            }
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public final void updateIsPoweredLocked(int i) {
        boolean z;
        int i2;
        SettingsManager settingsManager;
        if ((i & 256) != 0) {
            boolean z2 = this.mIsPowered;
            int i3 = this.mPlugType;
            this.mIsPowered = this.mBatteryManagerInternal.isPowered(15);
            this.mPlugType = this.mBatteryManagerInternal.getPlugType();
            int i4 = this.mBatteryLevel;
            this.mBatteryLevel = this.mBatteryManagerInternal.getBatteryLevel();
            this.mBatteryLevelLow = this.mBatteryManagerInternal.getBatteryLevelLow();
            this.mBatteryLevelCritical = this.mBatteryManagerInternal.getBatteryLevelCritical();
            this.mBatteryOnline = this.mBatteryManagerInternal.getBatteryOnline();
            if (this.mBatteryManagerInternal.getBatteryHealth() != 3 && i4 > 0 && getGlobalWakefulnessLocked() == 2) {
                this.mDreamsBatteryLevelDrain = (i4 - this.mBatteryLevel) + this.mDreamsBatteryLevelDrain;
            }
            if (z2 != this.mIsPowered || i3 != this.mPlugType) {
                StringBuilder sb = new StringBuilder("[api] updateIsPoweredLocked : mIsPowered: ");
                sb.append(this.mIsPowered);
                sb.append(" mPlugType: ");
                BatteryService$$ExternalSyntheticOutline0.m(sb, this.mPlugType, "PowerManagerService");
                this.mDirty |= 64;
                WirelessChargerDetector wirelessChargerDetector = this.mWirelessChargerDetector;
                boolean z3 = this.mIsPowered;
                int i5 = this.mPlugType;
                synchronized (wirelessChargerDetector.mLock) {
                    try {
                        boolean z4 = wirelessChargerDetector.mPoweredWirelessly;
                        if (z3 && i5 == 4) {
                            wirelessChargerDetector.mPoweredWirelessly = true;
                            wirelessChargerDetector.mMustUpdateRestPosition = true;
                            wirelessChargerDetector.startDetectionLocked();
                        } else {
                            wirelessChargerDetector.mPoweredWirelessly = false;
                            if (wirelessChargerDetector.mAtRest) {
                                if (i5 == 0 || i5 == 4) {
                                    wirelessChargerDetector.startDetectionLocked();
                                } else {
                                    wirelessChargerDetector.mMustUpdateRestPosition = false;
                                    wirelessChargerDetector.mAtRest = false;
                                    wirelessChargerDetector.mRestX = FullScreenMagnificationGestureHandler.MAX_SCALE;
                                    wirelessChargerDetector.mRestY = FullScreenMagnificationGestureHandler.MAX_SCALE;
                                    wirelessChargerDetector.mRestZ = FullScreenMagnificationGestureHandler.MAX_SCALE;
                                }
                            }
                        }
                        if (wirelessChargerDetector.mPoweredWirelessly && !z4) {
                            boolean z5 = wirelessChargerDetector.mAtRest;
                        }
                    } finally {
                    }
                }
                WirelessChargerMotionDetector wirelessChargerMotionDetector = this.mWirelessChargerMotionDetector;
                if (wirelessChargerMotionDetector != null) {
                    boolean z6 = this.mIsPowered;
                    if (this.mPlugType == 4) {
                        if (!wirelessChargerMotionDetector.mIsWirelessChargerSContextRegistered) {
                            wirelessChargerMotionDetector.mWirelessChargerSContextManager.registerListener(wirelessChargerMotionDetector.mWirelessChargerSContextListener, 46);
                            wirelessChargerMotionDetector.mIsWirelessChargerSContextRegistered = true;
                        } else if (!wirelessChargerMotionDetector.mIsDeviceMoving && !z2) {
                            android.util.Slog.d("WirelessChargerMotionDetector", "Device is not moved, skip wakeup when connect charger");
                            z = false;
                        }
                    } else if (i3 == 4) {
                        if (wirelessChargerMotionDetector.mIsDeviceMoving) {
                            boolean z7 = wirelessChargerMotionDetector.mIsWirelessChargerSContextRegistered;
                            if (z7 && z7) {
                                wirelessChargerMotionDetector.mWirelessChargerSContextManager.unregisterListener(wirelessChargerMotionDetector.mWirelessChargerSContextListener, 46);
                                wirelessChargerMotionDetector.mIsWirelessChargerSContextRegistered = false;
                            }
                        } else if (!z6) {
                            android.util.Slog.d("WirelessChargerMotionDetector", "Device is not moved, skip wakeup when disconnect charger");
                            wirelessChargerMotionDetector.mPassedWakeupTime = SystemClock.elapsedRealtime();
                            z = false;
                        }
                    }
                    this.mClock.getClass();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    if ((!z2 || !this.mIsPowered) && this.mWakeUpWhenPluggedOrUnpluggedConfig && ((!this.mKeepDreamingWhenUnplugging || getGlobalWakefulnessLocked() != 2 || !z2 || this.mIsPowered) && ((!this.mIsPowered || getGlobalWakefulnessLocked() != 2) && ((!this.mTheaterModeEnabled || this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig) && (((((i2 = this.mPlugType) != 1 || i3 != 2) && (i2 != 2 || i3 != 1)) || this.mBatteryOnline != 99) && (((settingsManager = SettingsManager.getInstance()) == null || settingsManager.getScreenWakeupOnPowerState()) && z)))))) {
                        this.mScreenOnReason = " powered change";
                        wakePowerGroupLocked((PowerGroup) this.mPowerGroups.get(0), uptimeMillis, 3, "android.server.power:PLUGGED:" + this.mIsPowered, 1000, this.mContext.getOpPackageName(), 1000, false);
                    }
                    userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(0), uptimeMillis, 0, 0, 1000);
                }
                z = true;
                this.mClock.getClass();
                long uptimeMillis2 = SystemClock.uptimeMillis();
                if (!z2) {
                }
                this.mScreenOnReason = " powered change";
                wakePowerGroupLocked((PowerGroup) this.mPowerGroups.get(0), uptimeMillis2, 3, "android.server.power:PLUGGED:" + this.mIsPowered, 1000, this.mContext.getOpPackageName(), 1000, false);
                userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.get(0), uptimeMillis2, 0, 0, 1000);
            }
            if (this.mBatterySaverSupported) {
                BatterySaverStateMachine batterySaverStateMachine = this.mBatterySaverStateMachine;
                boolean z8 = this.mIsPowered;
                int i6 = this.mBatteryLevel;
                boolean z9 = this.mBatteryLevelLow;
                synchronized (batterySaverStateMachine.mLock) {
                    try {
                        batterySaverStateMachine.mBatteryStatusSet = true;
                        boolean z10 = batterySaverStateMachine.mIsPowered != z8;
                        boolean z11 = batterySaverStateMachine.mBatteryLevel != i6;
                        boolean z12 = batterySaverStateMachine.mIsBatteryLevelLow != z9;
                        if (z10 || z11 || z12) {
                            batterySaverStateMachine.mIsPowered = z8;
                            batterySaverStateMachine.mBatteryLevel = i6;
                            batterySaverStateMachine.mIsBatteryLevelLow = z9;
                        }
                    } finally {
                    }
                }
            }
            ScreenOnKeeper screenOnKeeper = this.mScreenOnKeeper;
            if (screenOnKeeper != null) {
                boolean z13 = this.mIsPowered;
                boolean z14 = this.mBatteryLevelLow;
                Slog.d("ScreenOnKeeper", "checkBatteryLevelLow: isPowered=" + z13 + ", batteryLevelLow=" + z14);
                if (screenOnKeeper.mIsScreenOnKeeperEnabled && z14 && !z13) {
                    screenOnKeeper.disableScreenOnKeeper();
                    screenOnKeeper.notifyScreenOnKeeperDisabledLocked(1);
                }
            }
        }
    }

    public final void updateKnoxTimeoutValue(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int intForUser = SemPersonaManager.isSecureFolderId(i) ? Settings.System.getIntForUser(contentResolver, "knox_screen_off_timeout", 0, i) : Settings.Secure.getIntForUser(contentResolver, "knox_screen_off_timeout", 0, i);
        ProfilePowerState profilePowerState = (ProfilePowerState) this.mProfilePowerState.get(i);
        if (profilePowerState != null) {
            profilePowerState.mScreenOffTimeoutForUser = intForUser;
            return;
        }
        SparseArray sparseArray = this.mProfilePowerState;
        this.mClock.getClass();
        sparseArray.put(i, new ProfilePowerState(i, 0, intForUser, SystemClock.uptimeMillis()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01fd  */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updatePowerGroupsLocked(int r25) {
        /*
            Method dump skipped, instructions count: 916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.updatePowerGroupsLocked(int):boolean");
    }

    public final void updatePowerStateLocked() {
        int i;
        if (!this.mSystemReady || this.mDirty == 0 || this.mUpdatePowerStateInProgress) {
            return;
        }
        if (!Thread.holdsLock(this.mLock)) {
            int i2 = Slog.$r8$clinit;
            android.util.Slog.wtf("PowerManagerService", "Power manager lock was not held when calling updatePowerStateLocked");
        }
        Trace.traceBegin(131072L, "updatePowerState");
        this.mUpdatePowerStateInProgress = true;
        try {
            updateIsPoweredLocked(this.mDirty);
            if ((this.mDirty & 288) != 0) {
                boolean z = this.mStayOn;
                if (this.mStayOnWhilePluggedInSetting == 0 || isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()) {
                    this.mStayOn = false;
                } else {
                    this.mStayOn = this.mBatteryManagerInternal.isPowered(this.mStayOnWhilePluggedInSetting);
                }
                if (this.mStayOn != z) {
                    this.mDirty |= 128;
                }
            }
            int i3 = this.mDirty & 2048;
            Injector.AnonymousClass1 anonymousClass1 = this.mClock;
            if (i3 != 0 && this.mScreenBrightnessBoostInProgress) {
                anonymousClass1.getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                Handler handler = this.mHandler;
                handler.removeMessages(3);
                long j = this.mLastScreenBrightnessBoostTime;
                if (j > this.mLastGlobalSleepTime) {
                    long j2 = j + 5000;
                    if (j2 > uptimeMillis) {
                        Message obtainMessage = handler.obtainMessage(3);
                        obtainMessage.setAsynchronous(true);
                        handler.sendMessageAtTime(obtainMessage, j2);
                    }
                }
                this.mScreenBrightnessBoostInProgress = false;
                userActivityNoUpdateLocked(0, 0, 1000, uptimeMillis);
            }
            anonymousClass1.getClass();
            long uptimeMillis2 = SystemClock.uptimeMillis();
            int i4 = 0;
            do {
                i = this.mDirty;
                i4 |= i;
                this.mDirty = 0;
                updateWakeLockSummaryLocked(i);
                updateUserActivitySummaryLocked(i, uptimeMillis2);
                updateAttentiveStateLocked(i, uptimeMillis2);
            } while (updateWakefulnessLocked(i));
            updateProfilesLocked(uptimeMillis2);
            boolean updatePowerGroupsLocked = updatePowerGroupsLocked(i4);
            if (((i4 & 82943) != 0 || updatePowerGroupsLocked) && areAllPowerGroupsReadyLocked()) {
                scheduleSandmanLocked();
            }
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
            long j2 = profilePowerState.mScreenOffTimeoutForUser;
            if (j2 > 0 || profilePowerState.mScreenOffTimeout != Long.MAX_VALUE) {
                if (profilePowerState.mLastUserActivityTime + (j2 > 0 ? Math.min(profilePowerState.mScreenOffTimeout, j2) : profilePowerState.mScreenOffTimeout) <= j) {
                    int i2 = profilePowerState.mWakeLockSummary;
                    if ((i2 & 32) == 0 && (!this.mProximityPositive || (i2 & 16) == 0)) {
                        if (!profilePowerState.mLockingNotified) {
                            profilePowerState.mLockingNotified = true;
                            Notifier.NotifierHandler notifierHandler = this.mNotifier.mHandler;
                            Message obtainMessage = notifierHandler.obtainMessage(5);
                            obtainMessage.setAsynchronous(true);
                            obtainMessage.arg1 = profilePowerState.mUserId;
                            notifierHandler.sendMessage(obtainMessage);
                        }
                    }
                }
                profilePowerState.mLockingNotified = false;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.server.power.PowerManagerService$$ExternalSyntheticLambda16] */
    public final void updateQuickTilesSettingsLocked() {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "sysui_qs_tiles");
        String string2 = Settings.Secure.getString(this.mContext.getContentResolver(), "sysui_quick_qs_tiles");
        boolean z = true;
        boolean z2 = (string != null && string.contains("ScreenCurtainTileService")) || (string2 != null && string2.contains("ScreenCurtainTileService"));
        if ((string == null || !string.contains("KeepScreenOnTileService")) && (string2 == null || !string2.contains("KeepScreenOnTileService"))) {
            z = false;
        }
        if (this.mUseScreenCurtain != z2) {
            this.mUseScreenCurtain = z2;
            if (z2) {
                this.mForegroundPackageObserver.addObserver(this.mScreenCurtainController);
            } else {
                this.mForegroundPackageObserver.deleteObserver(this.mScreenCurtainController);
            }
        }
        if (this.mUseKeepScreenOn != z) {
            this.mUseKeepScreenOn = z;
            if (z) {
                Slog.d("PowerManagerService", "start ScreenOnKeeper");
                final PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.get(0);
                Context context = this.mContext;
                Object obj = this.mLock;
                Looper looper = this.mHandlerPmsMisc.getLooper();
                int i = powerGroup.mWakefulness;
                ?? r6 = new IntSupplier() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda16
                    @Override // java.util.function.IntSupplier
                    public final int getAsInt() {
                        return PowerGroup.this.mWakeLockSummary;
                    }
                };
                this.mInjector.getClass();
                this.mScreenOnKeeper = new ScreenOnKeeper(context, obj, looper, i, r6, this.mSystemProperties);
                return;
            }
            Slog.d("PowerManagerService", "stop ScreenOnKeeper");
            ScreenOnKeeper screenOnKeeper = this.mScreenOnKeeper;
            if (screenOnKeeper.mIsScreenOnKeeperEnabled) {
                screenOnKeeper.disableScreenOnKeeper();
                if (screenOnKeeper.mWakeLock.isHeld()) {
                    screenOnKeeper.mWakeLock.release();
                }
            }
            Handler handler = screenOnKeeper.mHandler;
            ScreenOnKeeper$$ExternalSyntheticLambda1 screenOnKeeper$$ExternalSyntheticLambda1 = new ScreenOnKeeper$$ExternalSyntheticLambda1(screenOnKeeper, 0);
            screenOnKeeper.mClock.getClass();
            handler.postAtTime(screenOnKeeper$$ExternalSyntheticLambda1, SystemClock.uptimeMillis());
            this.mScreenOnKeeper = null;
        }
    }

    public final void updateSettingsLocked$1() {
        String str;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDreamsEnabledSetting = Settings.Secure.getIntForUser(contentResolver, "screensaver_enabled", this.mDreamsEnabledByDefaultConfig ? 1 : 0, -2) != 0;
        this.mDreamsActivateOnSleepSetting = Settings.Secure.getIntForUser(contentResolver, "screensaver_activate_on_sleep", this.mDreamsActivatedOnSleepByDefaultConfig ? 1 : 0, -2) != 0;
        this.mDreamsActivateOnDockSetting = Settings.Secure.getIntForUser(contentResolver, "screensaver_activate_on_dock", this.mDreamsActivatedOnDockByDefaultConfig ? 1 : 0, -2) != 0;
        this.mScreenOffTimeoutSetting = Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 15000, -2);
        this.mSleepTimeoutSetting = Settings.Secure.getIntForUser(contentResolver, "sleep_timeout", -1, -2);
        this.mAttentiveTimeoutSetting = Settings.Secure.getIntForUser(contentResolver, "attentive_timeout", this.mAttentiveTimeoutConfig, -2);
        this.mStayOnWhilePluggedInSetting = Settings.Global.getInt(contentResolver, "stay_on_while_plugged_in", 1);
        this.mTheaterModeEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1;
        this.mAlwaysOnEnabled = this.mAmbientDisplayConfiguration.alwaysOnEnabled(-2);
        this.mUserActivityTimeoutSetting = Settings.System.getIntForUser(contentResolver, "user_activity_timeout", 0, -2);
        boolean z = Settings.System.getIntForUser(contentResolver, "intelligent_sleep_mode", 0, -2) != 0;
        this.mSmartStayEnabledSetting = z;
        SmartStayController smartStayController = this.mSmartStayController;
        int i = ((PowerGroup) this.mPowerGroups.get(0)).mWakefulness;
        if (z) {
            if (smartStayController.mSmartStayEnabled) {
                Slog.d("SmartStayController", "SmartStay already started");
            } else {
                Slog.d("SmartStayController", "SmartStay start!");
                smartStayController.mSmartStayEnabled = true;
                smartStayController.mInjector.getClass();
                HandlerThread handlerThread = new HandlerThread("smart stay");
                smartStayController.mSmartStayHandlerThread = handlerThread;
                handlerThread.start();
                smartStayController.mSmartStayHandler = new Handler(smartStayController.mSmartStayHandlerThread.getLooper());
                smartStayController.mWakefulness = i;
            }
        } else if (smartStayController.mSmartStayEnabled) {
            Slog.d("SmartStayController", "SmartStay stop");
            smartStayController.mSmartStayEnabled = false;
            smartStayController.mSmartStayHandler.removeCallbacksAndMessages(null);
            smartStayController.mSmartStayHandlerThread.quit();
            smartStayController.mSmartStayHandlerThread.interrupt();
            smartStayController.mSmartStayHandlerThread = null;
            smartStayController.mFaceDetectRequested.set(false);
            smartStayController.mFaceDetected = false;
        }
        boolean z2 = this.mOutdoorModeSetting;
        boolean z3 = Settings.System.getIntForUser(contentResolver, "display_outdoor_mode", 0, -2) == 1;
        this.mOutdoorModeSetting = z3;
        if (z2 != z3 && z3) {
            this.mClock.getClass();
            this.mLastOutdoorModeEnabledTime = SystemClock.elapsedRealtime();
            Slog.d("PowerManagerService", "[api] updateSettingsLocked : Update OutdoorMode enabled time");
        }
        if (this.mSupportsDoubleTapWakeConfig) {
            boolean z4 = Settings.Secure.getIntForUser(contentResolver, "double_tap_to_wake", 0, -2) != 0;
            if (z4 != this.mDoubleTapWakeEnabled) {
                this.mDoubleTapWakeEnabled = z4;
                this.mNativeWrapper.getClass();
                nativeSetPowerMode(0, z4);
            }
        }
        String str2 = UserManager.isDeviceInDemoMode(this.mContext) ? "1" : "0";
        Injector.AnonymousClass1 anonymousClass1 = this.mSystemProperties;
        anonymousClass1.getClass();
        if (!str2.equals(SystemProperties.get("sys.retaildemo.enabled", (String) null))) {
            anonymousClass1.getClass();
            SystemProperties.set("sys.retaildemo.enabled", str2);
        }
        boolean z5 = PowerManagerUtil.SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED;
        if (z5) {
            this.mAodMode = Settings.System.getIntForUser(contentResolver, "aod_mode", 0, -2) != 0;
        }
        this.mBatterySavingAdjustBrightnessFactor = Settings.Global.getFloat(contentResolver, "sem_power_saving_adjust_brightness_factor", 1.0f);
        this.mBatterySavingScreenOffTimeoutSetting = Settings.Global.getInt(contentResolver, "pms_settings_screen_time_out_enabled", 0);
        StringBuilder sb = new StringBuilder("[api] updateSettingsLocked : mDreamsEnabledSetting: ");
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
        sb.append(" mBatterySavingScreenOffTimeoutSetting: ");
        sb.append(this.mBatterySavingScreenOffTimeoutSetting);
        if (z5) {
            str = " mAodMode: " + this.mAodMode;
        } else {
            str = "";
        }
        sb.append(str);
        Slog.d("PowerManagerService", sb.toString());
        this.mDirty |= 32;
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x00bc, code lost:
    
        if (r7 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00bf, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00cb, code lost:
    
        if (r8 != false) goto L54;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSuspendBlockerLocked() {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.updateSuspendBlockerLocked():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x024a, code lost:
    
        if (r3 != 4) goto L147;
     */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01b9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0210  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateUserActivitySummaryLocked(int r39, long r40) {
        /*
            Method dump skipped, instructions count: 799
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.updateUserActivitySummaryLocked(int, long):void");
    }

    public final void updateWakeLockDisabledStatesLocked() {
        int size = this.mWakeLocks.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            WakeLock wakeLock = (WakeLock) this.mWakeLocks.get(i);
            if (((wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL) == 1 || isScreenLock(wakeLock)) && setWakeLockDisabledStateLocked(wakeLock)) {
                if (wakeLock.mDisabled) {
                    notifyWakeLockReleasedLocked(wakeLock, -1);
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

    /* JADX WARN: Code restructure failed: missing block: B:74:0x0097, code lost:
    
        if (r10 != 256) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x009d, code lost:
    
        if (r9 == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00a1, code lost:
    
        if (r9 == false) goto L48;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWakeLockSummaryLocked(int r13) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.updateWakeLockSummaryLocked(int):void");
    }

    public final boolean updateWakefulnessLocked(int i) {
        if ((i & 20151) == 0) {
            return false;
        }
        this.mClock.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z = false;
        for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
            PowerGroup powerGroup = (PowerGroup) this.mPowerGroups.valueAt(i2);
            if (powerGroup.mWakefulness == 1 && isItBedTimeYetLocked(powerGroup)) {
                z = isAttentiveTimeoutExpired(powerGroup, uptimeMillis) ? sleepPowerGroupLocked(powerGroup, uptimeMillis, 9, 1000) : ((this.mDreamsActivateOnSleepSetting || (this.mDreamsActivateOnDockSetting && this.mDockState != 0)) && powerGroup.mSupportsSandman) ? dreamPowerGroupLocked(powerGroup, uptimeMillis, 1000, false) : dozePowerGroupLocked(powerGroup, uptimeMillis, 2, 1000);
            }
        }
        return z;
    }

    public final void userActivityFromNative(long j, int i, int i2, int i3) {
        if (this.mIsUserActivityNativeInvoked.compareAndSet(false, true)) {
            StringBuilder sb = new StringBuilder("[api] userActivityFromNative : ");
            sb.append(PowerManager.userActivityEventToString(i));
            sb.append(i3 == 0 ? "" : ",");
            sb.append(PowerManager.userActivityFlagsToString(i3));
            sb.append(" displayId=");
            sb.append(i2);
            sb.append(" eventTime=");
            sb.append(j);
            Slog.d("PowerManagerService", sb.toString());
            Handler handler = this.mHandlerPmsMisc;
            PowerManagerService$$ExternalSyntheticLambda1 powerManagerService$$ExternalSyntheticLambda1 = new PowerManagerService$$ExternalSyntheticLambda1(this, 2);
            this.mClock.getClass();
            handler.postAtTime(powerManagerService$$ExternalSyntheticLambda1, SystemClock.uptimeMillis() + 1000);
        }
        userActivityInternal(i2, i, i3, 1000, j);
    }

    public final void userActivityInternal(int i, int i2, int i3, int i4, long j) {
        if ((i3 & 2) != 0) {
            if (this.mLastAndroidAutoBoostTime == -1) {
                this.mLastAndroidAutoBoostTime = j;
            }
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, "userActivityInternal PowerManager.USER_ACTIVITY_FLAG_INDIRECT : 2 (event: ", " flags: ", ") eventTime = ");
            m.append(j);
            m.append(", mLastAndroidAutoBoostTime :");
            m.append(this.mLastAndroidAutoBoostTime);
            Slog.d("PowerManagerService", m.toString());
            if (j - this.mLastAndroidAutoBoostTime > 1900) {
                this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.intent.action.ANDROID_AUTO_BOOSTER"), UserHandle.ALL);
                this.mLastAndroidAutoBoostTime = j;
            }
        }
        synchronized (this.mLock) {
            try {
                if (this.mSystemReady) {
                    if (i == -1) {
                        if (userActivityNoUpdateLocked(i2, i3, i4, j)) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean userActivityNoUpdateLocked(int i, int i2, int i3, long j) {
        boolean z = false;
        for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
            if (userActivityNoUpdateLocked((PowerGroup) this.mPowerGroups.valueAt(i4), j, i, i2, i3)) {
                z = true;
            }
        }
        return z;
    }

    public final boolean userActivityNoUpdateLocked(PowerGroup powerGroup, long j, int i, int i2, int i3) {
        long j2;
        ScreenOnKeeper screenOnKeeper;
        ScreenCurtainController screenCurtainController;
        powerGroup.getClass();
        int i4 = powerGroup.mGroupId;
        if (j < powerGroup.mLastSleepTime || j < powerGroup.mLastWakeTime || !this.mSystemReady) {
            return false;
        }
        Trace.traceBegin(131072L, "userActivity");
        try {
            if (j > this.mLastInteractivePowerHintTime && ((i == 2 && (i2 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) == 0) || ((i == 0 && (i2 & 8192) != 0) || (i == 1 && (32768 & i2) != 0)))) {
                int i5 = (i2 & 4096) != 0 ? -1 : 0;
                try {
                    this.mNativeWrapper.getClass();
                    nativeSetPowerBoost(i5, 0);
                    this.mLastInteractivePowerHintTime = j;
                } catch (Throwable th) {
                    th = th;
                    j2 = 131072;
                    Trace.traceEnd(j2);
                    throw th;
                }
            }
            if ((i == 2 || i == 1) && j - this.mLastNormalTouchBoostTime > 100) {
                SemPerfManager.sendCommandToSsrm("NORMAL_TOUCH_BOOSTER", "TRUE");
                this.mLastNormalTouchBoostTime = j;
            }
            this.mNotifier.onUserActivity(i4, i, i3);
            AttentionDetector attentionDetector = this.mAttentionDetector;
            attentionDetector.getClass();
            if (i == 0 || i == 1 || i == 2 || i == 3) {
                if (attentionDetector.mRequested.get()) {
                    attentionDetector.mAttentionManager.cancelAttentionCheck(attentionDetector.mCallback);
                    attentionDetector.mRequested.set(false);
                }
                attentionDetector.mLastUserActivityTime = j;
                attentionDetector.resetConsecutiveExtensionCount();
            } else if (i == 4) {
                attentionDetector.mConsecutiveTimeoutExtendedCount.incrementAndGet();
            }
            this.mAbuseWakeLockDetector.onUserActivity();
            ScreenTimeoutOverridePolicy screenTimeoutOverridePolicy = this.mScreenTimeoutOverridePolicy;
            if (screenTimeoutOverridePolicy != null && (this.mWakeLockSummary & 256) != 0) {
                if (i == 0) {
                    screenTimeoutOverridePolicy.releaseAllWakeLocks(4);
                } else if (i == 1) {
                    screenTimeoutOverridePolicy.releaseAllWakeLocks(5);
                } else if (i == 2) {
                    screenTimeoutOverridePolicy.releaseAllWakeLocks(6);
                } else if (i == 3) {
                    screenTimeoutOverridePolicy.releaseAllWakeLocks(7);
                } else if (i == 4) {
                    screenTimeoutOverridePolicy.releaseAllWakeLocks(3);
                }
            }
            if (this.mSmartStayEnabledSetting && i4 == 0) {
                SmartStayController smartStayController = this.mSmartStayController;
                smartStayController.mFaceDetectRequested.set(false);
                smartStayController.mFaceDetected = false;
            }
            if (i4 == 0 && (screenCurtainController = this.mScreenCurtainController) != null && !screenCurtainController.mScreenCurtainEnabled && (i == 2 || i == 1)) {
                screenCurtainController.mLastUserActivityTime = SystemClock.elapsedRealtime();
            }
            if (i4 == 0 && (screenOnKeeper = this.mScreenOnKeeper) != null) {
                if (i != 0) {
                    if (i != 2) {
                    }
                    screenOnKeeper.mLastScreenTouchTime = j;
                } else {
                    if ((i2 & 8192) == 0) {
                    }
                    screenOnKeeper.mLastScreenTouchTime = j;
                }
            }
            if (i == 2 && j > this.mLastUserActivityStateListenerCalledTime) {
                this.mLastUserActivityStateListenerCalledTime = j;
                if (this.mLastUserActivityStateListenerState == 0) {
                    this.mLastUserActivityStateListenerState = 1;
                    Slog.d("PowerManagerService", "UserActivityStateListenerState: " + this.mLastUserActivityStateListenerState);
                    Iterator it = this.mUserActivityStateListenerListeners.iterator();
                    while (it.hasNext()) {
                        ((PowerManagerInternal.UserActivityStateListener) it.next()).onChanged(this.mLastUserActivityStateListenerState);
                    }
                }
                Handler handler = this.mHandler;
                handler.removeCallbacks(this.mUserActivityStateListenerTimeoutRunnable);
                AnonymousClass6 anonymousClass6 = this.mUserActivityStateListenerTimeoutRunnable;
                this.mClock.getClass();
                handler.postAtTime(anonymousClass6, SystemClock.uptimeMillis() + 3000);
            }
            if (this.mUserInactiveOverrideFromWindowManager) {
                Slog.d("PowerManagerService", "userActivityNoUpdateLocked: mUserInactiveOverrideFromWindowManager : false");
                this.mUserInactiveOverrideFromWindowManager = false;
                this.mOverriddenTimeout = -1L;
            }
            int i6 = powerGroup.mWakefulness;
            if (i6 == 0 || i6 == 3 || (i2 & 2) != 0) {
                Trace.traceEnd(131072L);
                return false;
            }
            maybeUpdateForegroundProfileLastActivityLocked(j);
            if ((i2 & 1) != 0) {
                if (j > powerGroup.mLastUserActivityTimeNoChangeLights && j > powerGroup.mLastUserActivityTime) {
                    powerGroup.mLastUserActivityTimeNoChangeLights = j;
                    powerGroup.mLastUserActivityEvent = i;
                    int i7 = this.mDirty;
                    this.mDirty = i7 | 4;
                    if (i == 1) {
                        this.mDirty = i7 | 4100;
                    }
                    Trace.traceEnd(131072L);
                    return true;
                }
            } else if (j > powerGroup.mLastUserActivityTime) {
                powerGroup.mLastUserActivityTime = j;
                powerGroup.mLastUserActivityEvent = i;
                int i8 = this.mDirty;
                this.mDirty = i8 | 4;
                if (i == 1) {
                    this.mDirty = i8 | 4100;
                }
                if (this.mUserActivityTimeoutSetting > 0) {
                    if (i == 0) {
                        if ((this.mDirty & 16) != 0) {
                        }
                    }
                    Slog.e("PowerManagerService", "call changes for MDM");
                    setUserActivityTimeoutLocked(j);
                }
                Trace.traceEnd(131072L);
                return true;
            }
            Trace.traceEnd(131072L);
            return false;
        } catch (Throwable th2) {
            th = th2;
            j2 = 131072;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(18:29|(3:34|35|(17:43|44|45|46|(1:48)(1:74)|49|50|(1:52)(1:72)|53|(1:57)|58|(1:62)|63|64|65|66|67))|78|45|46|(0)(0)|49|50|(0)(0)|53|(2:55|57)|58|(2:60|62)|63|64|65|66|67) */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void wakePowerGroupLocked(final com.android.server.power.PowerGroup r19, long r20, int r22, java.lang.String r23, final int r24, java.lang.String r25, int r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.PowerManagerService.wakePowerGroupLocked(com.android.server.power.PowerGroup, long, int, java.lang.String, int, java.lang.String, int, boolean):void");
    }

    public boolean wasDeviceIdleForInternal(long j) {
        boolean z;
        synchronized (this.mLock) {
            long j2 = ((PowerGroup) this.mPowerGroups.get(0)).mLastUserActivityTime + j;
            this.mClock.getClass();
            z = j2 < SystemClock.uptimeMillis();
        }
        return z;
    }
}
