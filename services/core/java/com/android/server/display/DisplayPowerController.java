package com.android.server.display;

import android.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManagerInternal;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.util.FloatProperty;
import android.util.Log;
import android.util.MathUtils;
import android.util.MutableFloat;
import android.util.MutableInt;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.app.IBatteryStats;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.RingBuffer;
import com.android.server.LocalServices;
import com.android.server.clipboard.ClipboardService;
import com.android.server.display.AdaptiveBrightnessLongtermModelBuilder;
import com.android.server.display.AutomaticBrightnessController;
import com.android.server.display.BrightnessMappingStrategy;
import com.android.server.display.BrightnessSetting;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.HighBrightnessModeController;
import com.android.server.display.RampAnimator;
import com.android.server.display.ScreenOffBrightnessSensorController;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.BrightnessReason;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.display.utils.SensorUtils;
import com.android.server.display.whitebalance.DisplayWhiteBalanceController;
import com.android.server.am.BatteryStatsService;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.HqmDataDispatcher;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.samsung.android.aod.AODManager;
import com.samsung.android.aod.AODManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final class DisplayPowerController implements AutomaticBrightnessController.Callbacks, DisplayPowerControllerInterface {
    public static boolean SAMSUNG_UX_COLOR_FADE_OFF_EFFECT_ENABLED = true;
    public final AdaptiveBrightnessLongtermModelBuilder mAdaptiveBrightnessLongtermModelBuilder;
    public final boolean mAllowAutoBrightnessWhileDozingConfig;
    public AODManagerInternal mAodManagerInternal;
    public boolean mAppliedAutoBrightness;
    public boolean mAppliedBrightnessBoost;
    public boolean mAppliedDimming;
    public boolean mAppliedForceDimming;
    public boolean mAppliedLowPower;
    public boolean mAppliedScreenBrightnessOverride;
    public boolean mAppliedTemporaryAutoBrightnessAdjustment;
    public boolean mAppliedTemporaryBrightness;
    public boolean mAppliedThrottling;
    public float mAutoBrightnessAdjustment;
    public boolean mAutoBrightnessEnabled;
    public AutomaticBrightnessController mAutomaticBrightnessController;
    public boolean mAwakenFromDozingInAutoBrightness;
    public boolean mBatteryLevelCritical;
    public final IBatteryStats mBatteryStats;
    public final DisplayBlanker mBlanker;
    public boolean mBootCompleted;
    public boolean mBrightnessAnimationConsumerInvoked;
    public final boolean mBrightnessBucketsInDozeConfig;
    public boolean mBrightnessChangedByUser;
    public BrightnessConfiguration mBrightnessConfiguration;
    public RingBuffer mBrightnessEventRingBuffer;
    public long mBrightnessRampDecreaseMaxTimeMillis;
    public long mBrightnessRampIncreaseMaxTimeMillis;
    public float mBrightnessRampRateFastDecrease;
    public float mBrightnessRampRateFastIncrease;
    public float mBrightnessRampRateSlowDecrease;
    public float mBrightnessRampRateSlowIncrease;
    public final BrightnessSetting mBrightnessSetting;
    public BrightnessSetting.BrightnessSettingListener mBrightnessSettingListener;
    public final BrightnessThrottler mBrightnessThrottler;
    public float mBrightnessToFollow;
    public final BrightnessTracker mBrightnessTracker;
    public final DisplayManagerInternal.DisplayPowerCallbacks mCallbacks;
    public final ColorDisplayService.ColorDisplayServiceInternal mCdsi;
    public final Clock mClock;
    public final boolean mColorFadeEnabled;
    public final boolean mColorFadeFadesConfig;
    public ObjectAnimator mColorFadeOffAnimator;
    public ObjectAnimator mColorFadeOnAnimator;
    public final Context mContext;
    public boolean mCoverDisplayDemoEnabled;
    public float mCurrentScreenBrightnessSetting;
    public final boolean mDisplayBlanksAfterDozeConfig;
    public DisplayDevice mDisplayDevice;
    public DisplayDeviceConfig mDisplayDeviceConfig;
    public final int mDisplayId;
    public boolean mDisplayReadyLocked;
    public int mDisplayStatsId;
    public boolean mDozing;
    public EarlyWakeUpManager mEarlyWakeUpManager;
    public final boolean mEarlyWakeupEnabled;
    public float mFollowerRampSpeed;
    public float mFollowerRampSpeedAtHbm;
    public boolean mForceSlowChange;
    public boolean mGameAutoBrightnessLocked;
    public final DisplayControllerHandler mHandler;
    public final HighBrightnessModeController mHbmController;
    public final HighBrightnessModeMetadata mHighBrightnessModeMetadata;
    public HqmDataDispatcher mHqmDataDispatcher;
    public BrightnessMappingStrategy mIdleModeBrightnessMapper;
    public boolean mIgnoreProximityUntilChanged;
    public float mInitialAutoBrightness;
    public boolean mInitialAutoBrightnessUpdated;
    public final Injector mInjector;
    public BrightnessMappingStrategy mInteractiveModeBrightnessMapper;
    public final boolean mIsCoverDisplay;
    public boolean mIsDisplayInternal;
    public boolean mIsEarsenseProximity;
    public boolean mIsEnabled;
    public boolean mIsInTransition;
    public boolean mIsProximitySensorOnFoldingSide;
    public boolean mIsRbcActive;
    public boolean mIsSupportedAodMode;
    public long mLastBatteryLevelCriticalTime;
    public long mLastBrightnessConfigurationTime;
    public final BrightnessEvent mLastBrightnessEvent;
    public boolean mLastCoverClosedState;
    public float mLastScreenBrightnessSettingBeforeForceDim;
    public Sensor mLightSensor;
    public final LogicalDisplay mLogicalDisplay;
    public float mMoreFastRampRate;
    public float[] mNitsRange;
    public Consumer mOnBrightnessAnimationConsumer;
    public final Runnable mOnBrightnessChangeRunnable;
    public final Runnable mOnBrightnessModeChangeRunnable;
    public int mOnProximityNegativeMessages;
    public int mOnProximityPositiveMessages;
    public boolean mOnStateChangedPending;
    public float mPendingAutoBrightnessAdjustment;
    public boolean mPendingEarlyWakeUpRequestLocked;
    public boolean mPendingForceSlowChangeLocked;
    public boolean mPendingForceUpdateAb;
    public boolean mPendingRequestChangedLocked;
    public DisplayManagerInternal.DisplayPowerRequest mPendingRequestLocked;
    public float mPendingScreenBrightnessSetting;
    public boolean mPendingScreenOff;
    public ScreenOffUnblocker mPendingScreenOffUnblocker;
    public ScreenOnUnblocker mPendingScreenOnUnblocker;
    public boolean mPendingUpdatePowerStateLocked;
    public boolean mPendingWaitForNegativeProximityLocked;
    public final boolean mPersistBrightnessNitsForDefaultDisplay;
    public DisplayManagerInternal.DisplayPowerRequest mPowerRequest;
    public DisplayPowerState mPowerState;
    public Sensor mProximitySensor;
    public boolean mProximitySensorEnabled;
    public float mProximityThreshold;
    public boolean mResetBrightnessConfiguration;
    public final float mScreenBrightnessDefault;
    public final float mScreenBrightnessDimConfig;
    public final float mScreenBrightnessDozeConfig;
    public final float mScreenBrightnessMinimumDimAmount;
    public String mScreenBrightnessModeSettingName;
    public RampAnimator.DualRampAnimator mScreenBrightnessRampAnimator;
    public final float mScreenExtendedBrightnessRangeMaximum;
    public boolean mScreenOffBecauseOfProximity;
    public long mScreenOffBlockStartRealTime;
    public Sensor mScreenOffBrightnessSensor;
    public ScreenOffBrightnessSensorController mScreenOffBrightnessSensorController;
    public long mScreenOnBlockStartRealTime;
    public SeamlessAodReadyListener mSeamlessAodReadyListener;
    public final SensorManager mSensorManager;
    public final SettingsObserver mSettingsObserver;
    public boolean mShouldResetShortTermModel;
    public ShutdownReceiver mShutdownReceiver;
    public final boolean mSkipScreenOnBrightnessRamp;
    public boolean mStopped;
    public final String mSuspendBlockerIdEarlyWakeup;
    public final String mSuspendBlockerIdOnStateChanged;
    public final String mSuspendBlockerIdProxDebounce;
    public final String mSuspendBlockerIdProxNegative;
    public final String mSuspendBlockerIdProxPositive;
    public final String mSuspendBlockerIdRefreshRate;
    public final String mSuspendBlockerIdUnfinishedBusiness;
    public final String mTag;
    public final BrightnessEvent mTempBrightnessEvent;
    public float mTemporaryAutoBrightnessAdjustment;
    public float mTemporaryScreenBrightness;
    public String mThermalBrightnessThrottlingDataId;
    public boolean mUnfinishedBusiness;
    public String mUniqueDisplayId;
    public boolean mUseAutoBrightness;
    public boolean mUseSoftwareAutoBrightnessConfig;
    public boolean mWaitingForNegativeProximity;
    public final WindowManagerPolicy mWindowManagerPolicy;
    public static final PathInterpolator COLOR_FADE_PATH_INTERPOLATOR = new PathInterpolator(0.45f, 0.18f, 0.35f, 1.0f);
    public static final AccelerateDecelerateInterpolator COLOR_FADE_DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    public static final float[] BRIGHTNESS_RANGE_BOUNDARIES = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 60.0f, 70.0f, 80.0f, 90.0f, 100.0f, 200.0f, 300.0f, 400.0f, 500.0f, 600.0f, 700.0f, 800.0f, 900.0f, 1000.0f, 1200.0f, 1400.0f, 1600.0f, 1800.0f, 2000.0f, 2250.0f, 2500.0f, 2750.0f, 3000.0f};
    public static final int[] BRIGHTNESS_RANGE_INDEX = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
    public static long sLastScreenBrightnessSettingChangedTime = -1;
    public static final int DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION = 180;
    public static final int MAX_AUTO_BRIGHTNESS_TRANSITION_TIME = 60000;
    public final Object mLock = new Object();
    public int mLeadDisplayId = -1;
    public final CachedBrightnessInfo mCachedBrightnessInfo = new CachedBrightnessInfo();
    public int mProximity = -1;
    public int mPendingProximity = -1;
    public long mPendingProximityDebounceTime = -1;
    public int mReportedScreenStateToPolicy = -1;
    public final BrightnessReason mBrightnessReason = new BrightnessReason();
    public final BrightnessReason mBrightnessReasonTemp = new BrightnessReason();
    public float mLastStatsBrightness = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public int mSkipRampState = 0;
    public float mLastUserSetScreenBrightness = Float.NaN;
    public final SparseArray mDisplayBrightnessFollowers = new SparseArray();
    public int mDualScreenPolicy = -1;
    public boolean mNeedPrepareColorFade = false;
    public int mSensorPositiveDebounceDelay = -1;
    public int mSensorNegativeDebounceDelay = -1;
    public boolean mLcdFlashModeEnabled = false;
    public boolean mPassRampAnimation = false;
    public boolean mIsOutdoorModeEnabled = false;
    public boolean mSeamlessAodReady = false;
    public boolean mPendingScreenOnByAodReady = false;
    public float mLastOriginalTarget = Float.NaN;
    public float mLastAutomaticScreenBrightness = Float.NaN;
    public float mLastAmbientLux = Float.NaN;
    public int mActualDisplayState = 0;
    public float mLastNotifiedBrightness = Float.NaN;
    public int mExtraDimStrength = -1;
    public boolean mExtraDimIsActive = false;
    public final PowerHistorian mPowerHistorian = PowerHistorian.getInstance();
    public float mPrevScreenBrightness = Float.NaN;
    public final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() { // from class: com.android.server.display.DisplayPowerController.4
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            PowerManagerUtil.sCurrentScreenOffProfiler.noteCfAnimationStart();
            Slog.m72d(DisplayPowerController.this.mTag, "ColorFade: onAnimationStart");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PowerManagerUtil.sCurrentScreenOffProfiler.noteCfAnimationEnd();
            Slog.m72d(DisplayPowerController.this.mTag, "ColorFade: onAnimationEnd");
            DisplayPowerController.this.sendUpdatePowerState();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
            Slog.m72d(DisplayPowerController.this.mTag, "ColorFade: onAnimationRepeat");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            Slog.m72d(DisplayPowerController.this.mTag, "ColorFade: onAnimationCancel");
        }
    };
    public Runnable mBrightnessAnimationEndRunnable = new Runnable() { // from class: com.android.server.display.DisplayPowerController.5
        @Override // java.lang.Runnable
        public void run() {
            if (!DisplayPowerController.this.mBrightnessAnimationConsumerInvoked || DisplayPowerController.this.mScreenBrightnessRampAnimator.isAnimating()) {
                return;
            }
            DisplayPowerController.this.mBrightnessAnimationConsumerInvoked = false;
            DisplayPowerController.this.mOnBrightnessAnimationConsumer.accept(Boolean.FALSE);
            DisplayPowerController.this.mCallbacks.releaseSuspendBlocker(DisplayPowerController.this.mSuspendBlockerIdRefreshRate);
        }
    };
    public final RampAnimator.Listener mRampAnimatorListener = new RampAnimator.Listener() { // from class: com.android.server.display.DisplayPowerController.6
        @Override // com.android.server.display.RampAnimator.Listener
        public void onAnimationEnd() {
            if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN) {
                DisplayPowerController.this.mHandler.removeCallbacks(DisplayPowerController.this.mBrightnessAnimationEndRunnable);
                DisplayPowerController.this.mHandler.postDelayed(DisplayPowerController.this.mBrightnessAnimationEndRunnable, 200L);
            }
            DisplayPowerController.this.sendUpdatePowerState();
            DisplayPowerController.this.mHandler.sendMessageAtTime(DisplayPowerController.this.mHandler.obtainMessage(12), DisplayPowerController.this.mClock.uptimeMillis());
        }
    };
    public final Runnable mCleanListener = new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            DisplayPowerController.this.sendUpdatePowerState();
        }
    };
    public final Runnable mOnStateChangedRunnable = new Runnable() { // from class: com.android.server.display.DisplayPowerController.8
        @Override // java.lang.Runnable
        public void run() {
            DisplayPowerController.this.mOnStateChangedPending = false;
            DisplayPowerController.this.mCallbacks.onStateChanged();
            DisplayPowerController.this.mCallbacks.releaseSuspendBlocker(DisplayPowerController.this.mSuspendBlockerIdOnStateChanged);
        }
    };
    public final Runnable mOnProximityPositiveRunnable = new Runnable() { // from class: com.android.server.display.DisplayPowerController.9
        @Override // java.lang.Runnable
        public void run() {
            DisplayPowerController displayPowerController = DisplayPowerController.this;
            displayPowerController.mOnProximityPositiveMessages--;
            DisplayPowerController.this.mCallbacks.onProximityPositive();
            DisplayPowerController.this.mCallbacks.releaseSuspendBlocker(DisplayPowerController.this.mSuspendBlockerIdProxPositive);
        }
    };
    public final Runnable mOnProximityNegativeRunnable = new Runnable() { // from class: com.android.server.display.DisplayPowerController.10
        @Override // java.lang.Runnable
        public void run() {
            DisplayPowerController displayPowerController = DisplayPowerController.this;
            displayPowerController.mOnProximityNegativeMessages--;
            DisplayPowerController.this.mCallbacks.onProximityNegative();
            DisplayPowerController.this.mCallbacks.releaseSuspendBlocker(DisplayPowerController.this.mSuspendBlockerIdProxNegative);
        }
    };
    public final SensorEventListener mProximitySensorListener = new SensorEventListener() { // from class: com.android.server.display.DisplayPowerController.11
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (DisplayPowerController.this.mProximitySensorEnabled) {
                long uptimeMillis = DisplayPowerController.this.mClock.uptimeMillis();
                boolean z = false;
                float f = sensorEvent.values[0];
                if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f < DisplayPowerController.this.mProximityThreshold) {
                    z = true;
                }
                Slog.m72d(DisplayPowerController.this.mTag, "[api] onSensorChanged: proximity: " + z);
                DisplayPowerController.this.handleProximitySensorEvent(uptimeMillis, z);
            }
        }
    };

    interface Clock {
        long uptimeMillis();
    }

    public final int convertBrightnessReasonToStatsEnum(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            default:
                return 0;
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAmbientColorTemperatureOverride(float f) {
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setDisplayWhiteBalanceLoggingEnabled(boolean z) {
    }

    public final void updateForceUpdateAbJob() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DisplayPowerController(Context context, Injector injector, DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks, Handler handler, SensorManager sensorManager, DisplayBlanker displayBlanker, LogicalDisplay logicalDisplay, BrightnessTracker brightnessTracker, BrightnessSetting brightnessSetting, Runnable runnable, HighBrightnessModeMetadata highBrightnessModeMetadata, boolean z, Runnable runnable2, Consumer consumer) {
        BrightnessMappingStrategy brightnessMappingStrategy;
        this.mScreenBrightnessModeSettingName = "screen_brightness_mode";
        Injector injector2 = injector != null ? injector : new Injector();
        this.mInjector = injector2;
        this.mClock = injector2.getClock();
        this.mLogicalDisplay = logicalDisplay;
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        this.mDisplayId = displayIdLocked;
        String str = "DisplayPowerController[" + displayIdLocked + "]";
        this.mTag = str;
        this.mHighBrightnessModeMetadata = highBrightnessModeMetadata;
        this.mSuspendBlockerIdUnfinishedBusiness = getSuspendBlockerUnfinishedBusinessId(displayIdLocked);
        this.mSuspendBlockerIdOnStateChanged = getSuspendBlockerOnStateChangedId(displayIdLocked);
        this.mSuspendBlockerIdProxPositive = getSuspendBlockerProxPositiveId(displayIdLocked);
        this.mSuspendBlockerIdProxNegative = getSuspendBlockerProxNegativeId(displayIdLocked);
        this.mSuspendBlockerIdProxDebounce = getSuspendBlockerProxDebounceId(displayIdLocked);
        this.mSuspendBlockerIdRefreshRate = getSuspendBlockerIdRefreshRate(displayIdLocked);
        this.mSuspendBlockerIdEarlyWakeup = getSuspendBlockerIdEarlyWakeup(displayIdLocked);
        this.mDisplayDevice = logicalDisplay.getPrimaryDisplayDeviceLocked();
        String uniqueId = logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId();
        this.mUniqueDisplayId = uniqueId;
        this.mDisplayStatsId = uniqueId.hashCode();
        this.mIsEnabled = logicalDisplay.isEnabledLocked();
        this.mIsInTransition = logicalDisplay.isInTransitionLocked();
        this.mIsDisplayInternal = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked().type == 1;
        DisplayControllerHandler displayControllerHandler = new DisplayControllerHandler(handler.getLooper());
        this.mHandler = displayControllerHandler;
        this.mLastBrightnessEvent = new BrightnessEvent(displayIdLocked);
        this.mTempBrightnessEvent = new BrightnessEvent(displayIdLocked);
        this.mThermalBrightnessThrottlingDataId = logicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        Object[] objArr = 0;
        if (displayIdLocked == 0 || (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && displayIdLocked == 1)) {
            this.mBatteryStats = BatteryStatsService.getService();
        } else {
            this.mBatteryStats = null;
        }
        this.mSettingsObserver = new SettingsObserver(displayControllerHandler);
        this.mCallbacks = displayPowerCallbacks;
        this.mSensorManager = sensorManager;
        this.mWindowManagerPolicy = (WindowManagerPolicy) LocalServices.getService(WindowManagerPolicy.class);
        this.mAodManagerInternal = (AODManagerInternal) LocalServices.getService(AODManagerInternal.class);
        this.mBlanker = displayBlanker;
        this.mContext = context;
        this.mBrightnessSetting = brightnessSetting;
        this.mOnBrightnessChangeRunnable = runnable;
        if (displayIdLocked == 0 && consumer != null) {
            this.mOnBrightnessAnimationConsumer = consumer;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Resources resources = context.getResources();
        this.mScreenBrightnessDozeConfig = clampAbsoluteBrightness(powerManager.getBrightnessConstraint(4));
        this.mScreenBrightnessDimConfig = clampAbsoluteBrightness(powerManager.getBrightnessConstraint(3));
        this.mScreenBrightnessMinimumDimAmount = resources.getFloat(R.dimen.config_minScrollbarTouchTarget);
        float max = Math.max(1.0f, resources.getInteger(R.integer.config_screenTimeoutOverride) / 255.0f);
        this.mScreenExtendedBrightnessRangeMaximum = max;
        this.mScreenBrightnessDefault = clampAbsoluteBrightness(logicalDisplay.getDisplayInfoLocked().brightnessDefault);
        boolean z2 = PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && displayIdLocked == 1;
        this.mIsCoverDisplay = z2;
        if (z2) {
            this.mUseSoftwareAutoBrightnessConfig = resources.getBoolean(R.bool.config_cecSystemAudioModeMutingEnabled_allowed);
            this.mScreenBrightnessModeSettingName = "sub_screen_brightness_mode";
        } else if (displayIdLocked == 0) {
            this.mUseSoftwareAutoBrightnessConfig = resources.getBoolean(R.bool.config_autoPowerModeUseMotionSensor);
        }
        this.mAllowAutoBrightnessWhileDozingConfig = resources.getBoolean(R.bool.config_allowDisablingAssistDisclosure);
        this.mPersistBrightnessNitsForDefaultDisplay = resources.getBoolean(R.bool.config_magnification_area);
        this.mDisplayDeviceConfig = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig();
        loadBrightnessRampRates();
        this.mMoreFastRampRate = max;
        this.mSkipScreenOnBrightnessRamp = false;
        this.mHbmController = createHbmControllerLocked();
        this.mBrightnessThrottler = createBrightnessThrottlerLocked();
        saveBrightnessInfo(getScreenBrightnessSetting());
        loadNitsRange(resources);
        this.mCdsi = null;
        setUpAutoBrightness(resources, handler);
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL && (brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper) != null) {
            this.mBrightnessTracker = null;
            this.mAdaptiveBrightnessLongtermModelBuilder = injector2.getAdaptiveBrightnessLongtermModelBuilder(context, null, brightnessMappingStrategy);
        } else {
            this.mBrightnessTracker = brightnessTracker;
            this.mAdaptiveBrightnessLongtermModelBuilder = null;
        }
        this.mColorFadeEnabled = !ActivityManager.isLowRamDeviceStatic();
        this.mColorFadeFadesConfig = resources.getBoolean(R.bool.config_am_disablePssProfiling);
        this.mDisplayBlanksAfterDozeConfig = resources.getBoolean(R.bool.config_defaultAdasGnssLocationEnabled);
        this.mBrightnessBucketsInDozeConfig = resources.getBoolean(R.bool.config_defaultBatteryPercentageSetting);
        loadProximitySensor();
        loadNitBasedBrightnessSetting();
        this.mBrightnessToFollow = Float.NaN;
        this.mCurrentScreenBrightnessSetting = getScreenBrightnessSettingOnBootPhase();
        this.mAutoBrightnessAdjustment = getAutoBrightnessAdjustmentSetting();
        this.mTemporaryScreenBrightness = Float.NaN;
        this.mPendingScreenBrightnessSetting = Float.NaN;
        this.mTemporaryAutoBrightnessAdjustment = Float.NaN;
        this.mPendingAutoBrightnessAdjustment = Float.NaN;
        this.mBootCompleted = z;
        if (this.mIsDisplayInternal) {
            this.mShutdownReceiver = new ShutdownReceiver();
            displayControllerHandler.post(new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayPowerController.this.lambda$new$0();
                }
            });
        }
        this.mOnBrightnessModeChangeRunnable = runnable2;
        boolean z3 = this.mIsDisplayInternal;
        this.mEarlyWakeupEnabled = z3;
        if (z3) {
            this.mEarlyWakeUpManager = new EarlyWakeUpManager();
        }
        boolean z4 = PowerManagerUtil.SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE && this.mIsDisplayInternal;
        this.mIsSupportedAodMode = z4;
        if (z4) {
            this.mSeamlessAodReadyListener = new SeamlessAodReadyListener();
        }
        this.mUseAutoBrightness = Settings.System.getIntForUser(context.getContentResolver(), this.mScreenBrightnessModeSettingName, 0, -2) == 1;
        Slog.m72d(str, "Create new DPC instance, mDisplayId=" + displayIdLocked + " AutomaticBrightnessController=" + this.mAutomaticBrightnessController + " mInteractiveModeBrightnessMapper=" + this.mInteractiveModeBrightnessMapper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(this.mShutdownReceiver, intentFilter, null, this.mHandler);
    }

    public final void applyReduceBrightColorsSplineAdjustment() {
        this.mHandler.obtainMessage(11).sendToTarget();
        sendUpdatePowerState();
    }

    public final void handleRbcChanged() {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return;
        }
        if ((!automaticBrightnessController.isInIdleMode() && this.mInteractiveModeBrightnessMapper == null) || (this.mAutomaticBrightnessController.isInIdleMode() && this.mIdleModeBrightnessMapper == null)) {
            Log.w(this.mTag, "No brightness mapping available to recalculate splines for this mode");
            return;
        }
        float[] fArr = new float[this.mNitsRange.length];
        int i = 0;
        while (true) {
            float[] fArr2 = this.mNitsRange;
            if (i < fArr2.length) {
                fArr[i] = this.mCdsi.getReduceBrightColorsAdjustedBrightnessNits(fArr2[i]);
                i++;
            } else {
                boolean isReduceBrightColorsActivated = this.mCdsi.isReduceBrightColorsActivated();
                this.mIsRbcActive = isReduceBrightColorsActivated;
                this.mAutomaticBrightnessController.recalculateSplines(isReduceBrightColorsActivated, fArr);
                return;
            }
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public boolean isProximitySensorAvailable() {
        return this.mProximitySensor != null;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public ParceledListSlice getBrightnessEvents(int i, boolean z) {
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            Slog.m74e(this.mTag, "getBrightnessEvents: not supported");
        }
        BrightnessTracker brightnessTracker = this.mBrightnessTracker;
        if (brightnessTracker == null) {
            return null;
        }
        return brightnessTracker.getEvents(i, z);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onSwitchUser(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(14, Integer.valueOf(i)));
    }

    public final void handleOnSwitchUser(int i) {
        BrightnessTracker brightnessTracker;
        handleSettingsChange(true);
        handleBrightnessModeChange();
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL || (brightnessTracker = this.mBrightnessTracker) == null) {
            return;
        }
        brightnessTracker.onSwitchUser(i);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public int getLeadDisplayId() {
        return this.mLeadDisplayId;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightnessToFollow(float f, float f2, float f3) {
        this.mHbmController.onAmbientLuxChange(f3);
        if (f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            this.mBrightnessToFollow = f;
        } else {
            float convertToFloatScale = convertToFloatScale(f2);
            if (isValidBrightnessValue(convertToFloatScale)) {
                this.mBrightnessToFollow = convertToFloatScale;
            } else {
                this.mBrightnessToFollow = f;
            }
        }
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void addDisplayBrightnessFollower(DisplayPowerControllerInterface displayPowerControllerInterface) {
        synchronized (this.mLock) {
            this.mDisplayBrightnessFollowers.append(displayPowerControllerInterface.getDisplayId(), displayPowerControllerInterface);
            sendUpdatePowerStateLocked();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void removeDisplayBrightnessFollower(final DisplayPowerControllerInterface displayPowerControllerInterface) {
        synchronized (this.mLock) {
            this.mDisplayBrightnessFollowers.remove(displayPowerControllerInterface.getDisplayId());
            this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayPowerControllerInterface.this.setBrightnessToFollow(Float.NaN, -1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                }
            }, this.mClock.uptimeMillis());
        }
    }

    public final void clearDisplayBrightnessFollowersLocked() {
        for (int i = 0; i < this.mDisplayBrightnessFollowers.size(); i++) {
            final DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) this.mDisplayBrightnessFollowers.valueAt(i);
            this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayPowerControllerInterface.this.setBrightnessToFollow(Float.NaN, -1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                }
            }, this.mClock.uptimeMillis());
        }
        this.mDisplayBrightnessFollowers.clear();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public ParceledListSlice getAmbientBrightnessStats(int i) {
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            Slog.m74e(this.mTag, "getAmbientBrightnessStats: not supported");
        }
        BrightnessTracker brightnessTracker = this.mBrightnessTracker;
        if (brightnessTracker == null) {
            return null;
        }
        return brightnessTracker.getAmbientBrightnessStats(i);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void persistBrightnessTrackerState() {
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
            if (adaptiveBrightnessLongtermModelBuilder != null) {
                adaptiveBrightnessLongtermModelBuilder.persistAdaptiveBrightnessLongtermModelBuilderState();
                return;
            }
            return;
        }
        BrightnessTracker brightnessTracker = this.mBrightnessTracker;
        if (brightnessTracker != null) {
            brightnessTracker.persistBrightnessTrackerState();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0047 A[Catch: all -> 0x0056, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0008, B:11:0x000d, B:13:0x0011, B:14:0x0016, B:16:0x001a, B:18:0x002f, B:20:0x0033, B:22:0x0037, B:23:0x003a, B:25:0x003e, B:27:0x0042, B:29:0x0047, B:31:0x004d, B:32:0x0052, B:33:0x0054, B:35:0x0023, B:37:0x0029), top: B:3:0x0003 }] */
    @Override // com.android.server.display.DisplayPowerControllerInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestPowerState(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            if (this.mStopped) {
                return true;
            }
            if (!z || this.mPendingWaitForNegativeProximityLocked) {
                z2 = false;
            } else {
                this.mPendingWaitForNegativeProximityLocked = true;
                z2 = true;
            }
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest2 = this.mPendingRequestLocked;
            if (displayPowerRequest2 == null) {
                this.mPendingRequestLocked = new DisplayManagerInternal.DisplayPowerRequest(displayPowerRequest);
            } else {
                if (!displayPowerRequest2.equals(displayPowerRequest)) {
                    this.mPendingRequestLocked.copyFrom(displayPowerRequest);
                }
                if (displayPowerRequest.forceSlowChange && !this.mPendingForceSlowChangeLocked) {
                    this.mPendingForceSlowChangeLocked = true;
                    z2 = true;
                }
                if (displayPowerRequest.earlyWakeUp && !this.mPendingEarlyWakeUpRequestLocked) {
                    this.mPendingEarlyWakeUpRequestLocked = true;
                    z2 = true;
                }
                if (z2) {
                    this.mDisplayReadyLocked = false;
                    if (!this.mPendingRequestChangedLocked) {
                        this.mPendingRequestChangedLocked = true;
                        sendUpdatePowerStateLocked();
                    }
                }
                return this.mDisplayReadyLocked;
            }
            z2 = true;
            if (displayPowerRequest.forceSlowChange) {
                this.mPendingForceSlowChangeLocked = true;
                z2 = true;
            }
            if (displayPowerRequest.earlyWakeUp) {
                this.mPendingEarlyWakeUpRequestLocked = true;
                z2 = true;
            }
            if (z2) {
            }
            return this.mDisplayReadyLocked;
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return null;
        }
        return automaticBrightnessController.getDefaultConfig();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public BrightnessConfiguration getAppliedBackupConfiguration(BrightnessConfiguration brightnessConfiguration) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            return automaticBrightnessController.getAppliedBackupConfig(brightnessConfiguration);
        }
        return null;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onDisplayChanged(final HighBrightnessModeMetadata highBrightnessModeMetadata, int i) {
        this.mLeadDisplayId = i;
        final DisplayDevice primaryDisplayDeviceLocked = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked();
        if (primaryDisplayDeviceLocked == null) {
            Slog.wtf(this.mTag, "Display Device is null in DisplayPowerController for display: " + this.mLogicalDisplay.getDisplayIdLocked());
            return;
        }
        final String uniqueId = primaryDisplayDeviceLocked.getUniqueId();
        final DisplayDeviceConfig displayDeviceConfig = primaryDisplayDeviceLocked.getDisplayDeviceConfig();
        final IBinder displayTokenLocked = primaryDisplayDeviceLocked.getDisplayTokenLocked();
        final DisplayDeviceInfo displayDeviceInfoLocked = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked();
        DisplayInfo displayInfoLocked = this.mLogicalDisplay.getDisplayInfoLocked();
        final int i2 = displayInfoLocked.logicalWidth;
        final int i3 = displayInfoLocked.logicalHeight;
        final boolean isEnabledLocked = this.mLogicalDisplay.isEnabledLocked();
        final boolean isInTransitionLocked = this.mLogicalDisplay.isInTransitionLocked();
        final boolean z = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked() != null && this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked().type == 1;
        final String str = this.mLogicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController.this.lambda$onDisplayChanged$3(primaryDisplayDeviceLocked, uniqueId, displayDeviceConfig, str, displayTokenLocked, displayDeviceInfoLocked, highBrightnessModeMetadata, i2, i3, isEnabledLocked, isInTransitionLocked, z);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$onDisplayChanged$3(DisplayDevice displayDevice, String str, DisplayDeviceConfig displayDeviceConfig, String str2, IBinder iBinder, DisplayDeviceInfo displayDeviceInfo, HighBrightnessModeMetadata highBrightnessModeMetadata, int i, int i2, boolean z, boolean z2, boolean z3) {
        boolean z4;
        boolean z5 = true;
        if (this.mDisplayDevice != displayDevice) {
            this.mDisplayDevice = displayDevice;
            this.mUniqueDisplayId = str;
            this.mDisplayStatsId = str.hashCode();
            this.mDisplayDeviceConfig = displayDeviceConfig;
            this.mThermalBrightnessThrottlingDataId = str2;
            loadFromDisplayDeviceConfig(iBinder, displayDeviceInfo, highBrightnessModeMetadata);
            loadNitBasedBrightnessSetting();
            this.mPowerState.resetScreenState();
            EarlyWakeUpManager earlyWakeUpManager = this.mEarlyWakeUpManager;
            if (earlyWakeUpManager != null) {
                earlyWakeUpManager.invalidateCurrentRequest();
            }
        } else if (!Objects.equals(this.mThermalBrightnessThrottlingDataId, str2)) {
            this.mThermalBrightnessThrottlingDataId = str2;
            this.mBrightnessThrottler.loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(displayDeviceConfig.getThermalBrightnessThrottlingDataMapByThrottlingId(), this.mThermalBrightnessThrottlingDataId, this.mUniqueDisplayId);
        } else {
            if (!PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && this.mHbmController.isResolutionChanged(i, i2)) {
                this.mHbmController.handleResolutionChange(i, i2);
            }
            z4 = false;
            if (this.mIsEnabled == z || this.mIsInTransition != z2) {
                this.mIsEnabled = z;
                this.mIsInTransition = z2;
            } else {
                z5 = z4;
            }
            this.mIsDisplayInternal = z3;
            if (z5) {
                return;
            }
            updatePowerState();
            return;
        }
        z4 = true;
        if (this.mIsEnabled == z) {
        }
        this.mIsEnabled = z;
        this.mIsInTransition = z2;
        this.mIsDisplayInternal = z3;
        if (z5) {
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void stop() {
        synchronized (this.mLock) {
            clearDisplayBrightnessFollowersLocked();
            this.mStopped = true;
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(9), this.mClock.uptimeMillis());
            AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
            if (automaticBrightnessController != null) {
                automaticBrightnessController.stop();
            }
            BrightnessSetting brightnessSetting = this.mBrightnessSetting;
            if (brightnessSetting != null) {
                brightnessSetting.unregisterListener(this.mBrightnessSettingListener);
            }
            this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        }
    }

    public final void loadFromDisplayDeviceConfig(IBinder iBinder, DisplayDeviceInfo displayDeviceInfo, HighBrightnessModeMetadata highBrightnessModeMetadata) {
        int i;
        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && ((i = this.mDisplayId) == 0 || i == 1)) {
            reloadReduceBrightColours();
            this.mHbmController.resetHbmData(displayDeviceInfo.width, displayDeviceInfo.height, iBinder, displayDeviceInfo.uniqueId, this.mDisplayDeviceConfig.getHighBrightnessModeData(), new HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController.2
                @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
                public float getHdrBrightnessFromSdr(float f, float f2) {
                    return DisplayPowerController.this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
                }
            });
            return;
        }
        loadBrightnessRampRates();
        loadProximitySensor();
        loadNitsRange(this.mContext.getResources());
        setUpAutoBrightness(this.mContext.getResources(), this.mHandler);
        reloadReduceBrightColours();
        RampAnimator.DualRampAnimator dualRampAnimator = this.mScreenBrightnessRampAnimator;
        if (dualRampAnimator != null) {
            dualRampAnimator.setAnimationTimeLimits(this.mBrightnessRampIncreaseMaxTimeMillis, this.mBrightnessRampDecreaseMaxTimeMillis);
        }
        this.mHbmController.setHighBrightnessModeMetadata(highBrightnessModeMetadata);
        this.mHbmController.resetHbmData(displayDeviceInfo.width, displayDeviceInfo.height, iBinder, displayDeviceInfo.uniqueId, this.mDisplayDeviceConfig.getHighBrightnessModeData(), new HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController.3
            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
            public float getHdrBrightnessFromSdr(float f, float f2) {
                return DisplayPowerController.this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
            }
        });
        this.mBrightnessThrottler.loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(this.mDisplayDeviceConfig.getThermalBrightnessThrottlingDataMapByThrottlingId(), this.mThermalBrightnessThrottlingDataId, this.mUniqueDisplayId);
    }

    public final void sendUpdatePowerState() {
        synchronized (this.mLock) {
            sendUpdatePowerStateLocked();
        }
    }

    public final void sendUpdatePowerStateLocked() {
        if (this.mStopped || this.mPendingUpdatePowerStateLocked) {
            return;
        }
        this.mPendingUpdatePowerStateLocked = true;
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1), this.mClock.uptimeMillis());
    }

    public final void initialize(int i) {
        DisplayPowerState displayPowerState = this.mInjector.getDisplayPowerState(this.mBlanker, this.mColorFadeEnabled ? new ColorFade(this.mDisplayId) : null, this.mDisplayId, i);
        this.mPowerState = displayPowerState;
        if (this.mColorFadeEnabled) {
            FloatProperty floatProperty = DisplayPowerState.COLOR_FADE_LEVEL;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(displayPowerState, floatProperty, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
            this.mColorFadeOnAnimator = ofFloat;
            ofFloat.setDuration(160L);
            this.mColorFadeOnAnimator.addListener(this.mAnimatorListener);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mPowerState, floatProperty, 1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            this.mColorFadeOffAnimator = ofFloat2;
            ofFloat2.setDuration(320L);
            this.mColorFadeOffAnimator.addListener(this.mAnimatorListener);
        }
        RampAnimator.DualRampAnimator dualRampAnimator = this.mInjector.getDualRampAnimator(this.mPowerState, DisplayPowerState.SCREEN_BRIGHTNESS_FLOAT, DisplayPowerState.SCREEN_SDR_BRIGHTNESS_FLOAT);
        this.mScreenBrightnessRampAnimator = dualRampAnimator;
        dualRampAnimator.setAnimationTimeLimits(this.mBrightnessRampIncreaseMaxTimeMillis, this.mBrightnessRampDecreaseMaxTimeMillis);
        this.mScreenBrightnessRampAnimator.setListener(this.mRampAnimatorListener);
        this.mHqmDataDispatcher = this.mInjector.getHqmDataDispatcher();
        noteScreenState(this.mPowerState.getScreenState());
        noteScreenBrightness(this.mPowerState.getScreenBrightness());
        float convertToAdjustedNits = convertToAdjustedNits(this.mPowerState.getScreenBrightness());
        if (convertToAdjustedNits >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
                if (adaptiveBrightnessLongtermModelBuilder != null) {
                    adaptiveBrightnessLongtermModelBuilder.start(convertToAdjustedNits);
                }
            } else {
                this.mBrightnessTracker.start(convertToAdjustedNits);
            }
        }
        BrightnessSetting.BrightnessSettingListener brightnessSettingListener = new BrightnessSetting.BrightnessSettingListener() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda9
            @Override // com.android.server.display.BrightnessSetting.BrightnessSettingListener
            public final void onBrightnessChanged(float f) {
                DisplayPowerController.this.lambda$initialize$4(f);
            }
        };
        this.mBrightnessSettingListener = brightnessSettingListener;
        this.mBrightnessSetting.registerListener(brightnessSettingListener);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_auto_brightness_adj"), false, this.mSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(this.mScreenBrightnessModeSettingName), false, this.mSettingsObserver, -1);
        if (this.mIsDisplayInternal) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("game_autobrightness_lock"), false, this.mSettingsObserver, -1);
        }
        if (this.mIsCoverDisplay && !PowerManagerUtil.SHIP_BUILD) {
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("cover_screen_demo_mode"), false, this.mSettingsObserver, -1);
        }
        handleBrightnessModeChange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialize$4(float f) {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(10, Float.valueOf(f)), this.mClock.uptimeMillis());
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setUpAutoBrightness(Resources resources, Handler handler) {
        float f;
        float f2;
        HysteresisLevels hysteresisLevels;
        HysteresisLevels hysteresisLevels2;
        float f3;
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController;
        Sensor sensor;
        boolean isAutoBrightnessAvailable = this.mDisplayDeviceConfig.isAutoBrightnessAvailable();
        this.mUseSoftwareAutoBrightnessConfig = isAutoBrightnessAvailable;
        if (isAutoBrightnessAvailable) {
            BrightnessMappingStrategy brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
            if (brightnessMappingStrategy != null) {
                float userLux = brightnessMappingStrategy.getUserLux();
                f = this.mInteractiveModeBrightnessMapper.convertToNits(this.mInteractiveModeBrightnessMapper.getUserBrightness());
                f2 = userLux;
            } else {
                f = -1.0f;
                f2 = -1.0f;
            }
            boolean z = resources.getBoolean(R.bool.config_dreamsSupported);
            this.mInteractiveModeBrightnessMapper = this.mInjector.getInteractiveModeBrightnessMapper(resources, this.mDisplayDeviceConfig, null);
            if (z) {
                this.mIdleModeBrightnessMapper = BrightnessMappingStrategy.createForIdleMode(resources, this.mDisplayDeviceConfig, null);
            }
            BrightnessMappingStrategy brightnessMappingStrategy2 = this.mInteractiveModeBrightnessMapper;
            if (brightnessMappingStrategy2 != null) {
                if (brightnessMappingStrategy2 instanceof BrightnessMappingStrategy.PhysicalMappingStrategy) {
                    PowerManagerUtil.USE_SEC_LONG_TERM_MODEL = true;
                } else {
                    PowerManagerUtil.USE_SEC_LONG_TERM_MODEL = false;
                }
                float fraction = resources.getFraction(R.fraction.config_prescaleAbsoluteVolume_index3, 1, 1);
                int[] intArray = resources.getIntArray(R.array.config_face_acquire_vendor_enroll_ignorelist);
                int[] intArray2 = resources.getIntArray(R.array.config_face_acquire_vendor_keyguard_ignorelist);
                int[] intArray3 = resources.getIntArray(R.array.config_fillBuiltInDisplayCutoutArray);
                int[] intArray4 = resources.getIntArray(R.array.config_foldedDeviceStates);
                HysteresisLevels hysteresisLevels3 = new HysteresisLevels(intArray, intArray2, intArray3, intArray4);
                HysteresisLevels hysteresisLevels4 = new HysteresisLevels(intArray, intArray2, intArray3, intArray4);
                if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
                    int[] intArray5 = resources.getIntArray(R.array.config_fontManagerServiceCerts);
                    int[] intArray6 = resources.getIntArray(R.array.config_forceQueryablePackages);
                    int[] intArray7 = resources.getIntArray(R.array.config_forceSlowJpegModeList);
                    int[] intArray8 = resources.getIntArray(R.array.config_force_cellular_transport_capabilities);
                    HysteresisLevels hysteresisLevels5 = new HysteresisLevels(intArray, intArray2, intArray5, intArray6);
                    hysteresisLevels2 = new HysteresisLevels(intArray, intArray2, intArray7, intArray8);
                    hysteresisLevels = hysteresisLevels5;
                } else {
                    hysteresisLevels = null;
                    hysteresisLevels2 = null;
                }
                long autoBrightnessBrighteningLightDebounce = this.mDisplayDeviceConfig.getAutoBrightnessBrighteningLightDebounce();
                long autoBrightnessDarkeningLightDebounce = this.mDisplayDeviceConfig.getAutoBrightnessDarkeningLightDebounce();
                boolean z2 = resources.getBoolean(R.bool.config_assistTouchGestureEnabledDefault);
                int integer = resources.getInteger(R.integer.config_maxScanTasksForHomeVisibility);
                int integer2 = resources.getInteger(R.integer.config_burnInProtectionMaxRadius);
                if (50 > integer2) {
                    Slog.m79w(this.mTag, "Expected config_autoBrightnessInitialLightSensorRate (50) to be less than or equal to config_autoBrightnessLightSensorRate (" + integer2 + ").");
                }
                loadAmbientLightSensor();
                BrightnessTracker brightnessTracker = this.mBrightnessTracker;
                if (brightnessTracker != null && this.mDisplayId == 0) {
                    brightnessTracker.setLightSensor(this.mLightSensor);
                }
                AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
                if (automaticBrightnessController != null) {
                    automaticBrightnessController.stop();
                }
                if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    float convertToFloatScale = this.mInteractiveModeBrightnessMapper.convertToFloatScale(f);
                    if (convertToFloatScale != Float.NaN) {
                        f3 = convertToFloatScale;
                        this.mAutomaticBrightnessController = this.mInjector.getAutomaticBrightnessController(this, handler.getLooper(), this.mSensorManager, this.mLightSensor, this.mInteractiveModeBrightnessMapper, integer, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f, fraction, integer2, 50, autoBrightnessBrighteningLightDebounce, autoBrightnessDarkeningLightDebounce, z2, hysteresisLevels3, null, hysteresisLevels4, null, this.mContext, this.mHbmController, this.mBrightnessThrottler, this.mIdleModeBrightnessMapper, this.mDisplayDeviceConfig.getAmbientHorizonShort(), this.mDisplayDeviceConfig.getAmbientHorizonLong(), f2, f3, hysteresisLevels, hysteresisLevels2);
                        this.mBrightnessEventRingBuffer = new RingBuffer(BrightnessEvent.class, 100);
                        screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController;
                        if (screenOffBrightnessSensorController != null) {
                            screenOffBrightnessSensorController.stop();
                            this.mScreenOffBrightnessSensorController = null;
                        }
                        loadScreenOffBrightnessSensor();
                        int[] screenOffBrightnessSensorValueToLux = this.mDisplayDeviceConfig.getScreenOffBrightnessSensorValueToLux();
                        sensor = this.mScreenOffBrightnessSensor;
                        if (sensor != null || screenOffBrightnessSensorValueToLux == null) {
                            return;
                        }
                        this.mScreenOffBrightnessSensorController = this.mInjector.getScreenOffBrightnessSensorController(this.mSensorManager, sensor, this.mHandler, new DisplayPowerController$$ExternalSyntheticLambda8(), screenOffBrightnessSensorValueToLux, this.mInteractiveModeBrightnessMapper);
                        return;
                    }
                }
                f3 = -1.0f;
                this.mAutomaticBrightnessController = this.mInjector.getAutomaticBrightnessController(this, handler.getLooper(), this.mSensorManager, this.mLightSensor, this.mInteractiveModeBrightnessMapper, integer, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f, fraction, integer2, 50, autoBrightnessBrighteningLightDebounce, autoBrightnessDarkeningLightDebounce, z2, hysteresisLevels3, null, hysteresisLevels4, null, this.mContext, this.mHbmController, this.mBrightnessThrottler, this.mIdleModeBrightnessMapper, this.mDisplayDeviceConfig.getAmbientHorizonShort(), this.mDisplayDeviceConfig.getAmbientHorizonLong(), f2, f3, hysteresisLevels, hysteresisLevels2);
                this.mBrightnessEventRingBuffer = new RingBuffer(BrightnessEvent.class, 100);
                screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController;
                if (screenOffBrightnessSensorController != null) {
                }
                loadScreenOffBrightnessSensor();
                int[] screenOffBrightnessSensorValueToLux2 = this.mDisplayDeviceConfig.getScreenOffBrightnessSensorValueToLux();
                sensor = this.mScreenOffBrightnessSensor;
                if (sensor != null) {
                    return;
                } else {
                    return;
                }
            }
            this.mUseSoftwareAutoBrightnessConfig = false;
        }
    }

    public final void loadBrightnessRampRates() {
        this.mBrightnessRampRateFastDecrease = this.mDisplayDeviceConfig.getBrightnessRampFastDecrease();
        this.mBrightnessRampRateFastIncrease = this.mDisplayDeviceConfig.getBrightnessRampFastIncrease();
        this.mBrightnessRampRateSlowDecrease = this.mDisplayDeviceConfig.getBrightnessRampSlowDecrease();
        this.mBrightnessRampRateSlowIncrease = this.mDisplayDeviceConfig.getBrightnessRampSlowIncrease();
        this.mBrightnessRampDecreaseMaxTimeMillis = this.mDisplayDeviceConfig.getBrightnessRampDecreaseMaxMillis();
        this.mBrightnessRampIncreaseMaxTimeMillis = this.mDisplayDeviceConfig.getBrightnessRampIncreaseMaxMillis();
    }

    public final void loadNitsRange(Resources resources) {
        DisplayDeviceConfig displayDeviceConfig = this.mDisplayDeviceConfig;
        if (displayDeviceConfig != null && displayDeviceConfig.getNits() != null) {
            this.mNitsRange = this.mDisplayDeviceConfig.getNits();
        } else {
            Slog.m79w(this.mTag, "Screen brightness nits configuration is unavailable; falling back");
            this.mNitsRange = BrightnessMappingStrategy.getFloatArray(resources.obtainTypedArray(17236294));
        }
    }

    public final void reloadReduceBrightColours() {
        ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal = this.mCdsi;
        if (colorDisplayServiceInternal == null || !colorDisplayServiceInternal.isReduceBrightColorsActivated()) {
            return;
        }
        applyReduceBrightColorsSplineAdjustment();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAutomaticScreenBrightnessMode(boolean z) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            if (z) {
                automaticBrightnessController.switchToIdleMode();
            } else {
                automaticBrightnessController.switchToInteractiveScreenBrightnessMode();
            }
        }
    }

    public final void cleanupHandlerThreadAfterStop() {
        setProximitySensorEnabled(false);
        this.mHbmController.stop();
        this.mBrightnessThrottler.stop();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mUnfinishedBusiness) {
            this.mCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
            this.mUnfinishedBusiness = false;
        }
        if (this.mOnStateChangedPending) {
            this.mCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
            this.mOnStateChangedPending = false;
        }
        for (int i = 0; i < this.mOnProximityPositiveMessages; i++) {
            this.mCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        }
        this.mOnProximityPositiveMessages = 0;
        for (int i2 = 0; i2 < this.mOnProximityNegativeMessages; i2++) {
            this.mCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        }
        this.mOnProximityNegativeMessages = 0;
        DisplayPowerState displayPowerState = this.mPowerState;
        reportStats(displayPowerState != null ? displayPowerState.getScreenBrightness() : DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        DisplayPowerState displayPowerState2 = this.mPowerState;
        if (displayPowerState2 != null) {
            displayPowerState2.stop();
            this.mPowerState = null;
        }
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController;
        if (screenOffBrightnessSensorController != null) {
            screenOffBrightnessSensorController.stop();
        }
        ShutdownReceiver shutdownReceiver = this.mShutdownReceiver;
        if (shutdownReceiver != null) {
            this.mContext.unregisterReceiver(shutdownReceiver);
        }
    }

    public final void updatePowerState() {
        Trace.traceBegin(131072L, "DisplayPowerController#updatePowerState");
        updatePowerStateInternal();
        Trace.traceEnd(131072L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x033e  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0494  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x04f4  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x05d5  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x05e4 A[LOOP:0: B:308:0x05de->B:310:0x05e4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:314:0x05fc  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0643  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x064b  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x068c  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x06a4  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x06b2  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x07be  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x084d  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x0856  */
    /* JADX WARN: Removed duplicated region for block: B:456:0x085b  */
    /* JADX WARN: Removed duplicated region for block: B:459:0x086a  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x0881 A[LOOP:1: B:462:0x087b->B:464:0x0881, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:486:0x08ec A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:490:0x08fb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:498:0x09ac  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x09b7  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x09e4  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x09fd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0a24  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x0a35  */
    /* JADX WARN: Removed duplicated region for block: B:519:0x0a49  */
    /* JADX WARN: Removed duplicated region for block: B:522:0x0a50  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0a5b  */
    /* JADX WARN: Removed duplicated region for block: B:539:0x0a82  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0a91  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x0aa5  */
    /* JADX WARN: Removed duplicated region for block: B:554:0x0ab5  */
    /* JADX WARN: Removed duplicated region for block: B:558:0x0ac3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0aca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:574:0x0adc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0b09  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x0b1c  */
    /* JADX WARN: Removed duplicated region for block: B:591:0x0b29  */
    /* JADX WARN: Removed duplicated region for block: B:593:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:594:0x0b1e  */
    /* JADX WARN: Removed duplicated region for block: B:602:0x09bc  */
    /* JADX WARN: Removed duplicated region for block: B:605:0x0852  */
    /* JADX WARN: Removed duplicated region for block: B:620:0x07c0  */
    /* JADX WARN: Removed duplicated region for block: B:636:0x08d6  */
    /* JADX WARN: Removed duplicated region for block: B:641:0x067f  */
    /* JADX WARN: Removed duplicated region for block: B:643:0x0634  */
    /* JADX WARN: Removed duplicated region for block: B:647:0x0609  */
    /* JADX WARN: Removed duplicated region for block: B:648:0x05d7  */
    /* JADX WARN: Removed duplicated region for block: B:650:0x05c7  */
    /* JADX WARN: Removed duplicated region for block: B:667:0x04e4  */
    /* JADX WARN: Removed duplicated region for block: B:695:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:697:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:701:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:702:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:703:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:704:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:726:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0195  */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v20 */
    /* JADX WARN: Type inference failed for: r12v10 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r37v0 */
    /* JADX WARN: Type inference failed for: r37v1 */
    /* JADX WARN: Type inference failed for: r37v2 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v35 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePowerStateInternal() {
        int i;
        boolean z;
        ?? r3;
        int i2;
        boolean z2;
        float f;
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController;
        ?? r12;
        int i3;
        int i4;
        int screenState;
        boolean z3;
        boolean z4;
        float f2;
        int i5;
        boolean z5;
        boolean z6;
        AutomaticBrightnessController automaticBrightnessController;
        int i6;
        boolean z7;
        int i7;
        ?? r37;
        boolean z8;
        BrightnessTracker brightnessTracker;
        boolean z9;
        int i8;
        int i9;
        float f3;
        float f4;
        boolean z10;
        float f5;
        float f6;
        boolean z11;
        float f7;
        float f8;
        boolean z12;
        boolean z13;
        boolean z14;
        float f9;
        boolean z15;
        float f10;
        int i10;
        boolean z16;
        int i11;
        boolean z17;
        float f11;
        float f12;
        int i12;
        boolean z18;
        int i13;
        int i14;
        boolean saveBrightnessInfo;
        float f13;
        int i15;
        BrightnessMappingStrategy brightnessMappingStrategy;
        boolean z19;
        boolean z20;
        boolean z21;
        int i16;
        PowerManagerUtil.ScreenOffProfiler screenOffProfiler;
        BrightnessEvent brightnessEvent;
        RingBuffer ringBuffer;
        int i17;
        boolean z22;
        boolean z23;
        float clampScreenBrightnessForFinal;
        boolean z24;
        float f14;
        float f15;
        float f16;
        int i18;
        float f17;
        boolean z25;
        boolean z26;
        boolean z27;
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest;
        boolean z28;
        boolean z29;
        AutomaticBrightnessController automaticBrightnessController2;
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController2;
        float f18;
        boolean z30;
        float f19;
        boolean z31;
        boolean z32;
        int i19;
        boolean z33;
        EarlyWakeUpManager earlyWakeUpManager;
        this.mBrightnessReasonTemp.set(null);
        this.mTempBrightnessEvent.reset();
        synchronized (this.mLock) {
            if (this.mStopped) {
                return;
            }
            this.mPendingUpdatePowerStateLocked = false;
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest2 = this.mPendingRequestLocked;
            if (displayPowerRequest2 == null) {
                return;
            }
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest3 = this.mPowerRequest;
            if (displayPowerRequest3 == null) {
                this.mPowerRequest = new DisplayManagerInternal.DisplayPowerRequest(this.mPendingRequestLocked);
                updatePendingProximityRequestsLocked();
                DisplayManagerInternal.DisplayPowerRequest displayPowerRequest4 = this.mPowerRequest;
                this.mSensorPositiveDebounceDelay = displayPowerRequest4.proximityPositiveDebounce;
                this.mSensorNegativeDebounceDelay = displayPowerRequest4.proximityNegativeDebounce;
                this.mForceSlowChange = this.mPendingForceSlowChangeLocked;
                this.mPendingForceSlowChangeLocked = false;
                z = this.mPendingEarlyWakeUpRequestLocked;
                this.mPendingEarlyWakeUpRequestLocked = false;
                this.mPendingRequestChangedLocked = false;
                i = 3;
                r3 = true;
            } else if (this.mPendingRequestChangedLocked) {
                int i20 = displayPowerRequest3.policy;
                displayPowerRequest3.copyFrom(displayPowerRequest2);
                updatePendingProximityRequestsLocked();
                DisplayManagerInternal.DisplayPowerRequest displayPowerRequest5 = this.mPowerRequest;
                this.mSensorPositiveDebounceDelay = displayPowerRequest5.proximityPositiveDebounce;
                this.mSensorNegativeDebounceDelay = displayPowerRequest5.proximityNegativeDebounce;
                this.mForceSlowChange |= this.mPendingForceSlowChangeLocked;
                this.mPendingForceSlowChangeLocked = false;
                z = this.mPendingEarlyWakeUpRequestLocked | false;
                this.mPendingEarlyWakeUpRequestLocked = false;
                this.mPendingRequestChangedLocked = false;
                this.mDisplayReadyLocked = false;
                i = i20;
                r3 = false;
            } else {
                i = displayPowerRequest3.policy;
                z = false;
                r3 = false;
            }
            ?? r11 = !this.mDisplayReadyLocked;
            int i21 = this.mActualDisplayState;
            SparseArray clone = this.mDisplayBrightnessFollowers.clone();
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest6 = this.mPowerRequest;
            int i22 = displayPowerRequest6.policy;
            if (i22 == 0) {
                i2 = 1;
                z2 = true;
            } else if (i22 != 1) {
                z2 = false;
                i2 = 2;
            } else {
                i2 = displayPowerRequest6.dozeScreenState;
                if (i2 == 0) {
                    i2 = 3;
                }
                if (this.mAllowAutoBrightnessWhileDozingConfig || isFakeAodAvailable()) {
                    z2 = false;
                } else {
                    f = this.mPowerRequest.dozeScreenBrightness;
                    this.mBrightnessReasonTemp.setReason(2, f);
                    if (Float.isNaN(f)) {
                        f = this.mLastOriginalTarget;
                        this.mBrightnessReasonTemp.setReason(11, f);
                    }
                    z2 = false;
                    screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController;
                    if (screenOffBrightnessSensorController != null) {
                        screenOffBrightnessSensorController.setLightSensorEnabled(this.mUseAutoBrightness && this.mIsEnabled && (i2 == 1 || (i2 == 3 && !this.mAllowAutoBrightnessWhileDozingConfig)) && this.mLeadDisplayId == -1);
                    }
                    if (this.mProximitySensor == null) {
                        if (this.mPowerRequest.useProximitySensor && isProximitySensorValidState()) {
                            setProximitySensorEnabled(true);
                            if (!this.mScreenOffBecauseOfProximity && this.mProximity == 1 && !this.mIgnoreProximityUntilChanged) {
                                this.mScreenOffBecauseOfProximity = true;
                                sendOnProximityPositiveWithWakelock();
                            }
                        } else if (this.mWaitingForNegativeProximity && this.mScreenOffBecauseOfProximity && this.mProximity == 1 && i2 != 1 && isProximitySensorValidState()) {
                            setProximitySensorEnabled(true);
                        } else {
                            setProximitySensorEnabled(false);
                            this.mWaitingForNegativeProximity = false;
                        }
                        if (this.mScreenOffBecauseOfProximity && (this.mProximity != 1 || this.mIgnoreProximityUntilChanged)) {
                            this.mScreenOffBecauseOfProximity = false;
                            sendOnProximityNegativeWithWakelock();
                            r12 = true;
                        }
                        r12 = false;
                    } else {
                        setProximitySensorEnabled(false);
                        this.mWaitingForNegativeProximity = false;
                        this.mIgnoreProximityUntilChanged = false;
                        if (this.mScreenOffBecauseOfProximity) {
                            this.mScreenOffBecauseOfProximity = false;
                            sendOnProximityNegativeWithWakelock();
                            r12 = true;
                        }
                        r12 = false;
                    }
                    if (this.mIsEnabled || this.mIsInTransition || this.mScreenOffBecauseOfProximity) {
                        i2 = 1;
                    }
                    if (this.mDisplayId == 4 && !this.mPowerRequest.coverClosed) {
                        i2 = 1;
                    }
                    if (r3 != false) {
                        initialize(readyToUpdateDisplayState() ? i2 : 0);
                    }
                    int screenState2 = this.mPowerState.getScreenState();
                    if (this.mEarlyWakeupEnabled && (earlyWakeUpManager = this.mEarlyWakeUpManager) != null) {
                        earlyWakeUpManager.update(z, i21);
                    }
                    if (i2 == 1 && this.mPowerRequest.lastGoToSleepReason == 3) {
                        z2 = false;
                    }
                    i3 = this.mDualScreenPolicy;
                    i4 = this.mPowerRequest.dualScreenPolicy;
                    if (i3 != i4) {
                        this.mDualScreenPolicy = i4;
                        if (i4 == 0) {
                            this.mNeedPrepareColorFade = true;
                            if (i3 == -1 && i2 == 1 && !z2) {
                                z2 = true;
                            }
                        }
                        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                            noteScreenState(i2);
                        }
                    }
                    animateScreenStateChange(i2, z2);
                    screenState = this.mPowerState.getScreenState();
                    if (screenState == 1) {
                        this.mBrightnessReasonTemp.setReason(5, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                        f = 0.0f;
                    }
                    if (Float.isNaN(f) && isValidBrightnessValue(this.mBrightnessToFollow)) {
                        f = this.mBrightnessToFollow;
                        this.mBrightnessReasonTemp.setReason(10, f);
                    }
                    if (!Float.isNaN(f) && isValidBrightnessValue(this.mPowerRequest.screenBrightnessOverride)) {
                        f = this.mPowerRequest.screenBrightnessOverride;
                        this.mBrightnessReasonTemp.setReason(6, f);
                        this.mAppliedScreenBrightnessOverride = true;
                    } else {
                        this.mAppliedScreenBrightnessOverride = false;
                    }
                    boolean z34 = (!this.mAllowAutoBrightnessWhileDozingConfig || isFakeAodAvailable()) && Display.isDozeState(screenState);
                    z3 = (this.mUseAutoBrightness || (screenState != 2 && !z34) || !Float.isNaN(f) || this.mAutomaticBrightnessController == null || this.mBrightnessReasonTemp.getReason() == 10 || this.mGameAutoBrightnessLocked || isLightSensorCovered() || !this.mUseSoftwareAutoBrightnessConfig) ? false : true;
                    int i23 = !z3 ? 1 : !this.mUseAutoBrightness && screenState != 2 && !z34 ? 3 : 2;
                    if (this.mAutoBrightnessEnabled != z3) {
                        this.mAutoBrightnessEnabled = z3;
                        if (z3 && (this.mDozing || i == 1)) {
                            this.mInitialAutoBrightnessUpdated = false;
                        }
                    }
                    boolean updateUserSetScreenBrightness = updateUserSetScreenBrightness();
                    if (!BrightnessSynchronizer.floatEquals(this.mTemporaryScreenBrightness, this.mCurrentScreenBrightnessSetting)) {
                        this.mTemporaryScreenBrightness = Float.NaN;
                    }
                    if (!isValidBrightnessValue(this.mTemporaryScreenBrightness)) {
                        f = this.mTemporaryScreenBrightness;
                        this.mAppliedTemporaryBrightness = true;
                        this.mBrightnessReasonTemp.setReason(7, f);
                        z4 = false;
                    } else {
                        z4 = false;
                        this.mAppliedTemporaryBrightness = false;
                    }
                    boolean updateAutoBrightnessAdjustment = updateAutoBrightnessAdjustment();
                    if (Float.isNaN(this.mTemporaryAutoBrightnessAdjustment)) {
                        f2 = this.mTemporaryAutoBrightnessAdjustment;
                        this.mAppliedTemporaryAutoBrightnessAdjustment = true;
                        i5 = 1;
                    } else {
                        f2 = this.mAutoBrightnessAdjustment;
                        this.mAppliedTemporaryAutoBrightnessAdjustment = z4;
                        i5 = 2;
                    }
                    if (!this.mPowerRequest.boostScreenBrightness && f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                        this.mBrightnessReasonTemp.setReason(8, 1.0f);
                        this.mAppliedBrightnessBoost = true;
                        f = 1.0f;
                        z5 = false;
                    } else {
                        z5 = false;
                        this.mAppliedBrightnessBoost = false;
                    }
                    if (this.mResetBrightnessConfiguration) {
                        z6 = false;
                    } else {
                        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                            clearAdaptiveBrightnessLongtermModelBuilder();
                            restartAdaptiveBrightnessLongtermModelBuilderInternal(z5);
                        }
                        z6 = this.mResetBrightnessConfiguration;
                        this.mResetBrightnessConfiguration = z5;
                    }
                    boolean z35 = !Float.isNaN(f) && (updateAutoBrightnessAdjustment || updateUserSetScreenBrightness);
                    automaticBrightnessController = this.mAutomaticBrightnessController;
                    if (automaticBrightnessController == null) {
                        boolean hasUserDataPoints = automaticBrightnessController.hasUserDataPoints();
                        i6 = i5;
                        i7 = i;
                        r37 = r11;
                        z7 = z35;
                        this.mAutomaticBrightnessController.configure(i23, this.mBrightnessConfiguration, this.mLastUserSetScreenBrightness, updateUserSetScreenBrightness, f2, updateAutoBrightnessAdjustment, this.mPowerRequest.policy, this.mShouldResetShortTermModel, z6, this.mDualScreenPolicy);
                        this.mShouldResetShortTermModel = false;
                        z8 = hasUserDataPoints;
                    } else {
                        i6 = i5;
                        z7 = z35;
                        i7 = i;
                        r37 = r11;
                        z8 = false;
                    }
                    this.mHbmController.setAutoBrightnessEnabled((!this.mUseAutoBrightness || (this.mEarlyWakeupEnabled && this.mEarlyWakeUpManager.isEarlyLightSensorEnabled())) ? 1 : 2);
                    brightnessTracker = this.mBrightnessTracker;
                    if (brightnessTracker != null) {
                        BrightnessConfiguration brightnessConfiguration = this.mBrightnessConfiguration;
                        brightnessTracker.setShouldCollectColorSample(brightnessConfiguration != null && brightnessConfiguration.shouldCollectColorSamples());
                    }
                    if (!Float.isNaN(f)) {
                        if (z3) {
                            float rawAutomaticScreenBrightness = this.mAutomaticBrightnessController.getRawAutomaticScreenBrightness();
                            float automaticScreenBrightness = this.mAutomaticBrightnessController.getAutomaticScreenBrightness(this.mTempBrightnessEvent);
                            f18 = this.mAutomaticBrightnessController.getAutomaticScreenBrightnessAdjustment();
                            f = automaticScreenBrightness;
                            f5 = rawAutomaticScreenBrightness;
                        } else {
                            f18 = f2;
                            f5 = f;
                        }
                        if (isValidBrightnessValue(f) || f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                            f = clampScreenBrightness(f);
                            float ambientLux = this.mAutomaticBrightnessController.getAmbientLux();
                            if (f != this.mLastAutomaticScreenBrightness) {
                                this.mLastAutomaticScreenBrightness = f;
                                z30 = true;
                            } else {
                                z30 = false;
                            }
                            if (this.mAppliedAutoBrightness && !updateAutoBrightnessAdjustment && z30) {
                                if (PowerManagerUtil.USE_PERSONAL_AUTO_BRIGHTNESS_V3 || PowerManagerUtil.USE_PERSONAL_AUTO_BRIGHTNESS_V4) {
                                    BrightnessDynamicRampRatePair brightnessDynamicRampRatePair = getBrightnessDynamicRampRatePair(f, ambientLux);
                                    f4 = brightnessDynamicRampRatePair.brightnessRampRateDynamic;
                                    f3 = brightnessDynamicRampRatePair.brightnessRampRateDynamicAtHbm;
                                    f19 = f5;
                                } else {
                                    f19 = f5;
                                    f3 = Float.NaN;
                                    f4 = Float.NaN;
                                }
                                z10 = true;
                            } else {
                                f19 = f5;
                                f3 = Float.NaN;
                                f4 = Float.NaN;
                                z10 = false;
                            }
                            if (this.mCurrentScreenBrightnessSetting != clampAbsoluteBrightness(f)) {
                                z31 = true;
                                z32 = true;
                            } else {
                                z31 = true;
                                z32 = false;
                            }
                            this.mAppliedAutoBrightness = z31;
                            this.mLastAmbientLux = ambientLux;
                            this.mAwakenFromDozingInAutoBrightness = false;
                            this.mBrightnessReasonTemp.setReason(4, f);
                            ScreenOffBrightnessSensorController screenOffBrightnessSensorController3 = this.mScreenOffBrightnessSensorController;
                            i19 = 0;
                            if (screenOffBrightnessSensorController3 != null) {
                                screenOffBrightnessSensorController3.setLightSensorEnabled(false);
                            }
                            f5 = f19;
                            z33 = z32;
                        } else {
                            if (z3) {
                                f = this.mLastOriginalTarget;
                                this.mBrightnessReasonTemp.setReason(11, f);
                                if (this.mDozing) {
                                    this.mAwakenFromDozingInAutoBrightness = true;
                                }
                                f5 = f;
                            }
                            this.mAppliedAutoBrightness = false;
                            i19 = 0;
                            f3 = Float.NaN;
                            f4 = Float.NaN;
                            z33 = false;
                            z10 = false;
                        }
                        if (f2 != f18) {
                            putAutoBrightnessAdjustmentSetting(f18);
                        } else {
                            i6 = i19;
                        }
                        z9 = z33;
                        i9 = i6;
                        i8 = 3;
                    } else {
                        float clampScreenBrightnessForFinal2 = clampScreenBrightnessForFinal(f);
                        this.mAppliedAutoBrightness = false;
                        z9 = false;
                        i8 = 3;
                        i9 = 0;
                        f3 = Float.NaN;
                        f4 = Float.NaN;
                        z10 = false;
                        float f20 = f;
                        f = clampScreenBrightnessForFinal2;
                        f5 = f20;
                    }
                    if (screenState != i8 || screenState == 4) {
                        f6 = f5;
                        z11 = true;
                    } else {
                        f6 = f5;
                        z11 = false;
                    }
                    if (this.mIsSupportedAodMode && Float.isNaN(f) && ((z11 || this.mPendingScreenOnByAodReady) && !isFakeAodAvailable())) {
                        f = this.mLastOriginalTarget;
                        this.mBrightnessReasonTemp.setReason(11, f);
                    }
                    if (Float.isNaN(f) && Display.isDozeState(screenState) && !isFakeAodAvailable()) {
                        float f21 = this.mScreenBrightnessDozeConfig;
                        f = clampScreenBrightness(f21);
                        f6 = f21;
                        this.mBrightnessReasonTemp.setReason(3, f);
                    }
                    f7 = f6;
                    if (Float.isNaN(f) && z3 && (screenOffBrightnessSensorController2 = this.mScreenOffBrightnessSensorController) != null) {
                        f7 = screenOffBrightnessSensorController2.getAutomaticScreenBrightness();
                        if (isValidBrightnessValue(f7)) {
                            f = f7;
                        } else {
                            f = clampScreenBrightness(f7);
                            z9 = this.mCurrentScreenBrightnessSetting != f;
                            this.mBrightnessReasonTemp.setReason(9, f);
                            f7 = f7;
                        }
                    }
                    float f22 = f7;
                    if (Float.isNaN(f)) {
                        f8 = f22;
                        z12 = z9;
                    } else {
                        float f23 = this.mCurrentScreenBrightnessSetting;
                        f = clampScreenBrightness(f23);
                        if (f != this.mCurrentScreenBrightnessSetting) {
                            z9 = true;
                        }
                        z12 = z9;
                        this.mBrightnessReasonTemp.setReason(1, f);
                        if (this.mAppliedForceDimming && !Float.isNaN(this.mLastScreenBrightnessSettingBeforeForceDim)) {
                            f = this.mLastScreenBrightnessSettingBeforeForceDim;
                            this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_FORCE, f);
                        }
                        f8 = f23;
                    }
                    z13 = this.mBatteryLevelCritical;
                    z14 = this.mPowerRequest.batteryLevelCritical;
                    boolean z36 = z8;
                    int i24 = i9;
                    if (z13 != z14) {
                        if (z14) {
                            this.mLastBatteryLevelCriticalTime = SystemClock.uptimeMillis();
                        }
                        this.mBatteryLevelCritical = this.mPowerRequest.batteryLevelCritical;
                    }
                    if (this.mIsDisplayInternal && screenState == 2) {
                        displayPowerRequest = this.mPowerRequest;
                        if (displayPowerRequest.policy == 3 && (!this.mIsCoverDisplay || PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY)) {
                            z28 = !this.mBatteryLevelCritical && this.mLastBatteryLevelCriticalTime > sLastScreenBrightnessSettingChangedTime && !displayPowerRequest.isPowered && (!z3 || ((automaticBrightnessController2 = this.mAutomaticBrightnessController) != null && automaticBrightnessController2.isAmbientLuxValid()));
                            if (!z28 && !this.mAppliedForceDimming) {
                                this.mAppliedForceDimming = true;
                                if (this.mBrightnessReasonTemp.mReason == 4) {
                                    this.mLastScreenBrightnessSettingBeforeForceDim = f;
                                } else {
                                    this.mLastScreenBrightnessSettingBeforeForceDim = this.mCurrentScreenBrightnessSetting;
                                }
                            } else if (z28 && this.mAppliedForceDimming) {
                                this.mAppliedForceDimming = false;
                                if (!z3 && !Float.isNaN(this.mLastScreenBrightnessSettingBeforeForceDim)) {
                                    f = this.mLastScreenBrightnessSettingBeforeForceDim;
                                    this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_FORCE, f);
                                    z12 = true;
                                }
                            } else {
                                z29 = false;
                                if (this.mAppliedForceDimming || f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                                    z15 = z29;
                                    f9 = f;
                                } else {
                                    float max = Math.max(Math.min(f - this.mScreenBrightnessMinimumDimAmount, this.mScreenBrightnessDimConfig), DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                                    this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_USE_QUOTA, max);
                                    z15 = z29;
                                    f9 = max;
                                    z12 = true;
                                }
                                if (this.mBrightnessThrottler.isThrottled()) {
                                    this.mTempBrightnessEvent.setThermalMax(this.mBrightnessThrottler.getBrightnessCap());
                                    f10 = Math.min(f9, this.mBrightnessThrottler.getBrightnessCap());
                                    this.mBrightnessReasonTemp.addModifier(8, f10);
                                    if (this.mAppliedThrottling) {
                                        z27 = true;
                                    } else {
                                        z27 = true;
                                        z10 = false;
                                    }
                                    this.mAppliedThrottling = z27;
                                } else {
                                    if (this.mAppliedThrottling) {
                                        this.mAppliedThrottling = false;
                                    }
                                    f10 = f9;
                                }
                                AutomaticBrightnessController automaticBrightnessController3 = this.mAutomaticBrightnessController;
                                float ambientLux2 = automaticBrightnessController3 == null ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : automaticBrightnessController3.getAmbientLux();
                                float f24 = f3;
                                i10 = 0;
                                while (i10 < clone.size()) {
                                    ((DisplayPowerControllerInterface) clone.valueAt(i10)).setBrightnessToFollow(f8, convertToNits(f8), ambientLux2);
                                    i10++;
                                    updateUserSetScreenBrightness = updateUserSetScreenBrightness;
                                }
                                z16 = updateUserSetScreenBrightness;
                                if (z12) {
                                    i11 = 2;
                                    if (this.mPowerRequest.policy != 2) {
                                        z17 = updateScreenBrightnessSetting(f10);
                                        if (this.mPowerRequest.policy != i11) {
                                            if (f10 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                                                f10 = Math.max(Math.min(f10 - this.mScreenBrightnessMinimumDimAmount, this.mScreenBrightnessDimConfig), DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                                                z26 = true;
                                                this.mBrightnessReasonTemp.addModifier(1, f10);
                                            } else {
                                                z26 = true;
                                            }
                                            if (!this.mAppliedDimming) {
                                                z10 = false;
                                            }
                                            this.mAppliedDimming = z26;
                                        } else if (this.mAppliedDimming) {
                                            this.mAppliedDimming = false;
                                            z10 = false;
                                        }
                                        if (this.mLastOriginalTarget != f10) {
                                            this.mLastOriginalTarget = f10;
                                        }
                                        if (!this.mPowerRequest.lowPowerMode && !this.mAwakenFromDozingInAutoBrightness && !Display.isDozeState(screenState)) {
                                            if (f10 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                                                f10 = Math.max(f10 * Math.min(this.mPowerRequest.screenLowPowerBrightnessFactor, 1.0f), DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                                                this.mBrightnessReasonTemp.addModifier(2, f10);
                                            }
                                            if (this.mAppliedLowPower) {
                                                z25 = true;
                                            } else {
                                                z25 = true;
                                                z10 = false;
                                            }
                                            this.mAppliedLowPower = z25;
                                        } else if (this.mAppliedLowPower) {
                                            this.mAppliedLowPower = false;
                                            z10 = false;
                                        }
                                        float finalBrightness = getFinalBrightness(f10, screenState);
                                        if (this.mForceSlowChange) {
                                            Slog.m72d(this.mTag, "ForceSlowChange is requested, set slowChange as true");
                                            z10 = true;
                                        }
                                        this.mHbmController.onBrightnessChanged(finalBrightness, f9, this.mBrightnessThrottler.getBrightnessMaxReason());
                                        boolean z37 = !this.mAppliedTemporaryBrightness || this.mAppliedTemporaryAutoBrightnessAdjustment;
                                        if (this.mPendingScreenOff) {
                                            if (this.mSkipScreenOnBrightnessRamp) {
                                                i17 = 2;
                                                if (screenState == 2) {
                                                    int i25 = this.mSkipRampState;
                                                    if (i25 == 0 && this.mDozing) {
                                                        this.mInitialAutoBrightness = finalBrightness;
                                                        this.mSkipRampState = 1;
                                                    } else if (i25 == 1 && this.mUseSoftwareAutoBrightnessConfig && !BrightnessSynchronizer.floatEquals(finalBrightness, this.mInitialAutoBrightness)) {
                                                        i17 = 2;
                                                        this.mSkipRampState = 2;
                                                    } else {
                                                        i17 = 2;
                                                        if (this.mSkipRampState == 2) {
                                                            this.mSkipRampState = 0;
                                                        }
                                                    }
                                                } else {
                                                    this.mSkipRampState = 0;
                                                }
                                                boolean z38 = !(screenState == i17 || this.mSkipRampState == 0) || r12 == true;
                                                boolean z39 = !z3 && (!this.mInitialAutoBrightnessUpdated || z6);
                                                boolean z40 = !Display.isDozeState(screenState2) && Display.isOnState(screenState);
                                                boolean z41 = !Display.isDozeState(screenState) && this.mBrightnessReason.mReason == 11;
                                                boolean isDozeState = Display.isDozeState(screenState);
                                                boolean z42 = PowerManagerUtil.SEC_FEATURE_AOD_BRIGHTNESS_ANIM && isDozeState;
                                                if (Display.isDozeState(screenState) || !this.mBrightnessBucketsInDozeConfig) {
                                                    i12 = screenState;
                                                    z22 = false;
                                                } else {
                                                    i12 = screenState;
                                                    z22 = true;
                                                }
                                                if (this.mColorFadeEnabled || this.mPowerState.getColorFadeLevel() != 1.0f) {
                                                    f11 = f9;
                                                    z23 = false;
                                                } else {
                                                    f11 = f9;
                                                    z23 = true;
                                                }
                                                clampScreenBrightnessForFinal = clampScreenBrightnessForFinal(finalBrightness);
                                                z24 = z17;
                                                z18 = z3;
                                                if (this.mHbmController.getHighBrightnessMode() != 2 && (this.mBrightnessReasonTemp.getModifier() & 1) == 0 && (this.mBrightnessReasonTemp.getModifier() & 2) == 0) {
                                                    f14 = this.mHbmController.getHdrBrightnessValue();
                                                    f12 = finalBrightness;
                                                    this.mBrightnessReasonTemp.addModifier(4, f14);
                                                    float f25 = this.mPowerRequest.maxBrightness;
                                                    if (f14 >= f25) {
                                                        this.mBrightnessReasonTemp.addModifier(32, f25);
                                                        f14 = f25;
                                                    }
                                                } else {
                                                    f12 = finalBrightness;
                                                    f14 = clampScreenBrightnessForFinal;
                                                }
                                                float screenBrightness = this.mPowerState.getScreenBrightness();
                                                float sdrScreenBrightness = this.mPowerState.getSdrScreenBrightness();
                                                boolean shouldEnableMoreFastRampRateCase = shouldEnableMoreFastRampRateCase();
                                                boolean z43 = this.mBrightnessReasonTemp.getReason() != 10;
                                                boolean z44 = Math.abs(BrightnessSynchronizer.brightnessFloatToInt(this.mScreenBrightnessRampAnimator.getCurrentValue()) - BrightnessSynchronizer.brightnessFloatToInt(f14)) > 1 && !this.mScreenBrightnessRampAnimator.isAnimating();
                                                if (isValidBrightnessValue(f14) || (f14 == screenBrightness && clampScreenBrightnessForFinal == sdrScreenBrightness)) {
                                                    f15 = 0.0f;
                                                    f16 = Float.NaN;
                                                } else if (z38 || z22 || !z23 || ((z37 && !this.mForceSlowChange) || this.mPassRampAnimation || z39 || z40 || z41 || z42 || ((this.mAppliedScreenBrightnessOverride && this.mBrightnessReasonTemp.isStartBrightnessChanged(this.mBrightnessReason) && !shouldEnableMoreFastRampRateCase && !this.mAppliedDimming) || z15 || z44))) {
                                                    animateScreenBrightness(f14, clampScreenBrightnessForFinal, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                                                    f15 = 0.0f;
                                                    f16 = 0.0f;
                                                } else {
                                                    boolean z45 = f14 > screenBrightness;
                                                    if (z45 && z10) {
                                                        f17 = this.mBrightnessRampRateSlowIncrease;
                                                    } else if (z45 && !z10) {
                                                        f17 = this.mBrightnessRampRateFastIncrease;
                                                    } else if (!z45 && z10) {
                                                        if (Float.isNaN(f4)) {
                                                            f4 = this.mBrightnessRampRateSlowDecrease;
                                                        }
                                                        f17 = f4;
                                                        if (shouldEnableMoreFastRampRateCase) {
                                                            f16 = f24;
                                                        } else {
                                                            f17 = this.mMoreFastRampRate;
                                                            f16 = Float.NaN;
                                                        }
                                                        if (isDozeState) {
                                                            f17 = 0.35f;
                                                        }
                                                        if (z43) {
                                                            f17 = this.mFollowerRampSpeed;
                                                            f16 = this.mFollowerRampSpeedAtHbm;
                                                        }
                                                        if (this.mScreenBrightnessRampAnimator.getTarget() != f14) {
                                                            animateScreenBrightness(f14, clampScreenBrightnessForFinal, f17, f16);
                                                        }
                                                        f15 = f17;
                                                    } else {
                                                        f17 = this.mBrightnessRampRateFastDecrease;
                                                    }
                                                    f24 = Float.NaN;
                                                    if (shouldEnableMoreFastRampRateCase) {
                                                    }
                                                    if (isDozeState) {
                                                    }
                                                    if (z43) {
                                                    }
                                                    if (this.mScreenBrightnessRampAnimator.getTarget() != f14) {
                                                    }
                                                    f15 = f17;
                                                }
                                                for (i18 = 0; i18 < clone.size(); i18++) {
                                                    ((DisplayPowerControllerInterface) clone.valueAt(i18)).setRampSpeedToFollower(f15, f16);
                                                }
                                                if (!this.mScreenBrightnessRampAnimator.isAnimating() && this.mForceSlowChange) {
                                                    this.mForceSlowChange = false;
                                                }
                                                i13 = 32;
                                                i14 = 8;
                                                notifyBrightnessTrackerChanged(f12, z7, z36, z18, z37);
                                                if (!this.mInitialAutoBrightnessUpdated && (this.mAppliedAutoBrightness || z15)) {
                                                    this.mInitialAutoBrightnessUpdated = true;
                                                }
                                                if (z24 && !z7 && (this.mDisplayId == 0 || this.mIsCoverDisplay)) {
                                                    putAutoBrightnessTransitionTime(f15, f16, f14);
                                                }
                                                saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f14);
                                            }
                                            i17 = 2;
                                            if (screenState == i17) {
                                            }
                                            if (z3) {
                                            }
                                            if (Display.isDozeState(screenState2)) {
                                            }
                                            if (Display.isDozeState(screenState)) {
                                            }
                                            boolean isDozeState2 = Display.isDozeState(screenState);
                                            if (PowerManagerUtil.SEC_FEATURE_AOD_BRIGHTNESS_ANIM) {
                                            }
                                            if (Display.isDozeState(screenState)) {
                                            }
                                            i12 = screenState;
                                            z22 = false;
                                            if (this.mColorFadeEnabled) {
                                            }
                                            f11 = f9;
                                            z23 = false;
                                            clampScreenBrightnessForFinal = clampScreenBrightnessForFinal(finalBrightness);
                                            z24 = z17;
                                            z18 = z3;
                                            if (this.mHbmController.getHighBrightnessMode() != 2) {
                                            }
                                            f12 = finalBrightness;
                                            f14 = clampScreenBrightnessForFinal;
                                            float screenBrightness2 = this.mPowerState.getScreenBrightness();
                                            float sdrScreenBrightness2 = this.mPowerState.getSdrScreenBrightness();
                                            boolean shouldEnableMoreFastRampRateCase2 = shouldEnableMoreFastRampRateCase();
                                            boolean z432 = this.mBrightnessReasonTemp.getReason() != 10;
                                            if (Math.abs(BrightnessSynchronizer.brightnessFloatToInt(this.mScreenBrightnessRampAnimator.getCurrentValue()) - BrightnessSynchronizer.brightnessFloatToInt(f14)) > 1) {
                                            }
                                            if (isValidBrightnessValue(f14)) {
                                            }
                                            f15 = 0.0f;
                                            f16 = Float.NaN;
                                            while (i18 < clone.size()) {
                                            }
                                            if (!this.mScreenBrightnessRampAnimator.isAnimating()) {
                                                this.mForceSlowChange = false;
                                            }
                                            i13 = 32;
                                            i14 = 8;
                                            notifyBrightnessTrackerChanged(f12, z7, z36, z18, z37);
                                            if (!this.mInitialAutoBrightnessUpdated) {
                                                this.mInitialAutoBrightnessUpdated = true;
                                            }
                                            if (z24) {
                                                putAutoBrightnessTransitionTime(f15, f16, f14);
                                            }
                                            saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f14);
                                        } else {
                                            f11 = f9;
                                            f12 = finalBrightness;
                                            i12 = screenState;
                                            z18 = z3;
                                            i13 = 32;
                                            i14 = 8;
                                            saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting());
                                        }
                                        if (saveBrightnessInfo && !z37) {
                                            postBrightnessChangeRunnable();
                                        }
                                        if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason) || i24 != 0) {
                                            f13 = f12;
                                            String str = this.mTag;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("Brightness [");
                                            sb.append(f13);
                                            sb.append("] reason changing to: '");
                                            i15 = i24;
                                            sb.append(this.mBrightnessReasonTemp.toString(i15));
                                            sb.append("', previous reason: '");
                                            sb.append(this.mBrightnessReason);
                                            sb.append("'.");
                                            Slog.m78v(str, sb.toString());
                                            this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                                        } else {
                                            if (this.mBrightnessReasonTemp.getReason() == 1 && z16) {
                                                String str2 = this.mTag;
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append("Brightness [");
                                                f13 = f12;
                                                sb2.append(f13);
                                                sb2.append("] manual adjustment.");
                                                Slog.m78v(str2, sb2.toString());
                                            } else {
                                                f13 = f12;
                                            }
                                            i15 = i24;
                                        }
                                        this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                                        this.mTempBrightnessEvent.setBrightness(f13);
                                        this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                                        this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                                        this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                                        this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                                        BrightnessEvent brightnessEvent2 = this.mTempBrightnessEvent;
                                        int flags = brightnessEvent2.getFlags() | (this.mIsRbcActive ? 1 : 0);
                                        if (!this.mPowerRequest.lowPowerMode) {
                                            i13 = 0;
                                        }
                                        brightnessEvent2.setFlags(flags | i13);
                                        BrightnessEvent brightnessEvent3 = this.mTempBrightnessEvent;
                                        ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal = this.mCdsi;
                                        brightnessEvent3.setRbcStrength(colorDisplayServiceInternal == null ? colorDisplayServiceInternal.getReduceBrightColorsStrength() : -1);
                                        this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                                        this.mTempBrightnessEvent.setWasShortTermModelActive(z36);
                                        this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mUseAutoBrightness);
                                        boolean z46 = this.mTempBrightnessEvent.getReason().getReason() != 7 && this.mLastBrightnessEvent.getReason().getReason() == 7;
                                        if ((!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent) && !z46) || i15 != 0) {
                                            this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                                            this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                                            brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                                            brightnessEvent.setAdjustmentFlags(i15);
                                            int flags2 = brightnessEvent.getFlags();
                                            if (!z16) {
                                                i14 = 0;
                                            }
                                            brightnessEvent.setFlags(flags2 | i14);
                                            Slog.m76i(this.mTag, brightnessEvent.toString(false));
                                            if (!z16 || brightnessEvent.getReason().getReason() != 7) {
                                                logBrightnessEvent(brightnessEvent, f11);
                                            }
                                            ringBuffer = this.mBrightnessEventRingBuffer;
                                            if (ringBuffer != null) {
                                                ringBuffer.append(brightnessEvent);
                                            }
                                        }
                                        brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                                        if (brightnessMappingStrategy != null && (brightnessMappingStrategy instanceof BrightnessMappingStrategy.PhysicalMappingStrategy)) {
                                            updateForceUpdateAbJob();
                                        }
                                        z19 = (this.mPendingScreenOnUnblocker == null || (this.mColorFadeEnabled && (this.mColorFadeOnAnimator.isStarted() || this.mColorFadeOffAnimator.isStarted())) || !this.mPowerState.waitUntilClean(this.mCleanListener) || this.mPendingScreenOnByAodReady) ? false : true;
                                        z20 = (z19 || this.mScreenBrightnessRampAnimator.isAnimating()) ? false : true;
                                        int i26 = i12;
                                        if (z19 && i26 != 1 && this.mReportedScreenStateToPolicy == 1) {
                                            setReportedScreenState(2);
                                            this.mWindowManagerPolicy.screenTurnedOn(this.mDisplayId);
                                        }
                                        if (!z20 && !this.mUnfinishedBusiness) {
                                            this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
                                            this.mUnfinishedBusiness = true;
                                        }
                                        boolean z47 = z18 || this.mAutomaticBrightnessController.isAmbientLuxValid();
                                        if (z19 && r37 != false && z47) {
                                            synchronized (this.mLock) {
                                                if (!this.mPendingRequestChangedLocked) {
                                                    this.mDisplayReadyLocked = true;
                                                }
                                            }
                                            sendOnStateChangedWithWakelock();
                                        }
                                        if (z20 && i26 == 1) {
                                            screenOffProfiler = PowerManagerUtil.sCurrentScreenOffProfiler;
                                            if (screenOffProfiler.isStarted() && !screenOffProfiler.mSaved && this.mPowerRequest.lastGoToSleepReason != 3) {
                                                screenOffProfiler.mSaved = true;
                                                Slog.m72d(this.mTag, screenOffProfiler.toString(false));
                                                PowerManagerUtil.sScreenOffProfilers.append(new PowerManagerUtil.ScreenOffProfiler(screenOffProfiler));
                                            }
                                        }
                                        if (z20 || !this.mUnfinishedBusiness) {
                                            z21 = false;
                                        } else {
                                            z21 = false;
                                            this.mUnfinishedBusiness = false;
                                            this.mCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
                                        }
                                        this.mDozing = i26 != 2 ? true : z21;
                                        i16 = this.mPowerRequest.policy;
                                        if (i7 != i16) {
                                            logDisplayPolicyChanged(i16);
                                            return;
                                        }
                                        return;
                                    }
                                } else {
                                    i11 = 2;
                                }
                                z17 = false;
                                if (this.mPowerRequest.policy != i11) {
                                }
                                if (this.mLastOriginalTarget != f10) {
                                }
                                if (!this.mPowerRequest.lowPowerMode) {
                                }
                                if (this.mAppliedLowPower) {
                                }
                                float finalBrightness2 = getFinalBrightness(f10, screenState);
                                if (this.mForceSlowChange) {
                                }
                                this.mHbmController.onBrightnessChanged(finalBrightness2, f9, this.mBrightnessThrottler.getBrightnessMaxReason());
                                if (this.mAppliedTemporaryBrightness) {
                                }
                                if (this.mPendingScreenOff) {
                                }
                                if (saveBrightnessInfo) {
                                    postBrightnessChangeRunnable();
                                }
                                if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                                }
                                f13 = f12;
                                String str3 = this.mTag;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("Brightness [");
                                sb3.append(f13);
                                sb3.append("] reason changing to: '");
                                i15 = i24;
                                sb3.append(this.mBrightnessReasonTemp.toString(i15));
                                sb3.append("', previous reason: '");
                                sb3.append(this.mBrightnessReason);
                                sb3.append("'.");
                                Slog.m78v(str3, sb3.toString());
                                this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                                this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                                this.mTempBrightnessEvent.setBrightness(f13);
                                this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                                this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                                this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                                this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                                BrightnessEvent brightnessEvent22 = this.mTempBrightnessEvent;
                                int flags3 = brightnessEvent22.getFlags() | (this.mIsRbcActive ? 1 : 0);
                                if (!this.mPowerRequest.lowPowerMode) {
                                }
                                brightnessEvent22.setFlags(flags3 | i13);
                                BrightnessEvent brightnessEvent32 = this.mTempBrightnessEvent;
                                ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal2 = this.mCdsi;
                                brightnessEvent32.setRbcStrength(colorDisplayServiceInternal2 == null ? colorDisplayServiceInternal2.getReduceBrightColorsStrength() : -1);
                                this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                                this.mTempBrightnessEvent.setWasShortTermModelActive(z36);
                                this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mUseAutoBrightness);
                                if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                                }
                                if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                                    this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                                    this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                                    brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                                    brightnessEvent.setAdjustmentFlags(i15);
                                    int flags22 = brightnessEvent.getFlags();
                                    if (!z16) {
                                    }
                                    brightnessEvent.setFlags(flags22 | i14);
                                    Slog.m76i(this.mTag, brightnessEvent.toString(false));
                                    if (!z16) {
                                    }
                                    logBrightnessEvent(brightnessEvent, f11);
                                    ringBuffer = this.mBrightnessEventRingBuffer;
                                    if (ringBuffer != null) {
                                    }
                                    brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                                    if (brightnessMappingStrategy != null) {
                                        updateForceUpdateAbJob();
                                    }
                                    if (this.mPendingScreenOnUnblocker == null) {
                                    }
                                    if (z19) {
                                    }
                                    int i262 = i12;
                                    if (z19) {
                                        setReportedScreenState(2);
                                        this.mWindowManagerPolicy.screenTurnedOn(this.mDisplayId);
                                    }
                                    if (!z20) {
                                        this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
                                        this.mUnfinishedBusiness = true;
                                    }
                                    if (z18) {
                                    }
                                    if (z19) {
                                        synchronized (this.mLock) {
                                        }
                                    }
                                    if (z20) {
                                        screenOffProfiler = PowerManagerUtil.sCurrentScreenOffProfiler;
                                        if (screenOffProfiler.isStarted()) {
                                            screenOffProfiler.mSaved = true;
                                            Slog.m72d(this.mTag, screenOffProfiler.toString(false));
                                            PowerManagerUtil.sScreenOffProfilers.append(new PowerManagerUtil.ScreenOffProfiler(screenOffProfiler));
                                        }
                                    }
                                    if (z20) {
                                    }
                                    z21 = false;
                                    this.mDozing = i262 != 2 ? true : z21;
                                    i16 = this.mPowerRequest.policy;
                                    if (i7 != i16) {
                                    }
                                }
                                this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                                this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                                brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                                brightnessEvent.setAdjustmentFlags(i15);
                                int flags222 = brightnessEvent.getFlags();
                                if (!z16) {
                                }
                                brightnessEvent.setFlags(flags222 | i14);
                                Slog.m76i(this.mTag, brightnessEvent.toString(false));
                                if (!z16) {
                                }
                                logBrightnessEvent(brightnessEvent, f11);
                                ringBuffer = this.mBrightnessEventRingBuffer;
                                if (ringBuffer != null) {
                                }
                                brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                                if (brightnessMappingStrategy != null) {
                                }
                                if (this.mPendingScreenOnUnblocker == null) {
                                }
                                if (z19) {
                                }
                                int i2622 = i12;
                                if (z19) {
                                }
                                if (!z20) {
                                }
                                if (z18) {
                                }
                                if (z19) {
                                }
                                if (z20) {
                                }
                                if (z20) {
                                }
                                z21 = false;
                                this.mDozing = i2622 != 2 ? true : z21;
                                i16 = this.mPowerRequest.policy;
                                if (i7 != i16) {
                                }
                            }
                            z29 = true;
                            if (this.mAppliedForceDimming) {
                            }
                            z15 = z29;
                            f9 = f;
                            if (this.mBrightnessThrottler.isThrottled()) {
                            }
                            AutomaticBrightnessController automaticBrightnessController32 = this.mAutomaticBrightnessController;
                            if (automaticBrightnessController32 == null) {
                            }
                            float f242 = f3;
                            i10 = 0;
                            while (i10 < clone.size()) {
                            }
                            z16 = updateUserSetScreenBrightness;
                            if (z12) {
                            }
                            z17 = false;
                            if (this.mPowerRequest.policy != i11) {
                            }
                            if (this.mLastOriginalTarget != f10) {
                            }
                            if (!this.mPowerRequest.lowPowerMode) {
                            }
                            if (this.mAppliedLowPower) {
                            }
                            float finalBrightness22 = getFinalBrightness(f10, screenState);
                            if (this.mForceSlowChange) {
                            }
                            this.mHbmController.onBrightnessChanged(finalBrightness22, f9, this.mBrightnessThrottler.getBrightnessMaxReason());
                            if (this.mAppliedTemporaryBrightness) {
                            }
                            if (this.mPendingScreenOff) {
                            }
                            if (saveBrightnessInfo) {
                            }
                            if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                            }
                            f13 = f12;
                            String str32 = this.mTag;
                            StringBuilder sb32 = new StringBuilder();
                            sb32.append("Brightness [");
                            sb32.append(f13);
                            sb32.append("] reason changing to: '");
                            i15 = i24;
                            sb32.append(this.mBrightnessReasonTemp.toString(i15));
                            sb32.append("', previous reason: '");
                            sb32.append(this.mBrightnessReason);
                            sb32.append("'.");
                            Slog.m78v(str32, sb32.toString());
                            this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                            this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                            this.mTempBrightnessEvent.setBrightness(f13);
                            this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                            this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                            this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                            this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                            BrightnessEvent brightnessEvent222 = this.mTempBrightnessEvent;
                            int flags32 = brightnessEvent222.getFlags() | (this.mIsRbcActive ? 1 : 0);
                            if (!this.mPowerRequest.lowPowerMode) {
                            }
                            brightnessEvent222.setFlags(flags32 | i13);
                            BrightnessEvent brightnessEvent322 = this.mTempBrightnessEvent;
                            ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal22 = this.mCdsi;
                            brightnessEvent322.setRbcStrength(colorDisplayServiceInternal22 == null ? colorDisplayServiceInternal22.getReduceBrightColorsStrength() : -1);
                            this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                            this.mTempBrightnessEvent.setWasShortTermModelActive(z36);
                            this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mUseAutoBrightness);
                            if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                            }
                            if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                            }
                            this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                            this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                            brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                            brightnessEvent.setAdjustmentFlags(i15);
                            int flags2222 = brightnessEvent.getFlags();
                            if (!z16) {
                            }
                            brightnessEvent.setFlags(flags2222 | i14);
                            Slog.m76i(this.mTag, brightnessEvent.toString(false));
                            if (!z16) {
                            }
                            logBrightnessEvent(brightnessEvent, f11);
                            ringBuffer = this.mBrightnessEventRingBuffer;
                            if (ringBuffer != null) {
                            }
                            brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                            if (brightnessMappingStrategy != null) {
                            }
                            if (this.mPendingScreenOnUnblocker == null) {
                            }
                            if (z19) {
                            }
                            int i26222 = i12;
                            if (z19) {
                            }
                            if (!z20) {
                            }
                            if (z18) {
                            }
                            if (z19) {
                            }
                            if (z20) {
                            }
                            if (z20) {
                            }
                            z21 = false;
                            this.mDozing = i26222 != 2 ? true : z21;
                            i16 = this.mPowerRequest.policy;
                            if (i7 != i16) {
                            }
                        }
                    }
                    f9 = f;
                    z15 = false;
                    if (this.mBrightnessThrottler.isThrottled()) {
                    }
                    AutomaticBrightnessController automaticBrightnessController322 = this.mAutomaticBrightnessController;
                    if (automaticBrightnessController322 == null) {
                    }
                    float f2422 = f3;
                    i10 = 0;
                    while (i10 < clone.size()) {
                    }
                    z16 = updateUserSetScreenBrightness;
                    if (z12) {
                    }
                    z17 = false;
                    if (this.mPowerRequest.policy != i11) {
                    }
                    if (this.mLastOriginalTarget != f10) {
                    }
                    if (!this.mPowerRequest.lowPowerMode) {
                    }
                    if (this.mAppliedLowPower) {
                    }
                    float finalBrightness222 = getFinalBrightness(f10, screenState);
                    if (this.mForceSlowChange) {
                    }
                    this.mHbmController.onBrightnessChanged(finalBrightness222, f9, this.mBrightnessThrottler.getBrightnessMaxReason());
                    if (this.mAppliedTemporaryBrightness) {
                    }
                    if (this.mPendingScreenOff) {
                    }
                    if (saveBrightnessInfo) {
                    }
                    if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                    }
                    f13 = f12;
                    String str322 = this.mTag;
                    StringBuilder sb322 = new StringBuilder();
                    sb322.append("Brightness [");
                    sb322.append(f13);
                    sb322.append("] reason changing to: '");
                    i15 = i24;
                    sb322.append(this.mBrightnessReasonTemp.toString(i15));
                    sb322.append("', previous reason: '");
                    sb322.append(this.mBrightnessReason);
                    sb322.append("'.");
                    Slog.m78v(str322, sb322.toString());
                    this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                    this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                    this.mTempBrightnessEvent.setBrightness(f13);
                    this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                    this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                    this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                    this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                    BrightnessEvent brightnessEvent2222 = this.mTempBrightnessEvent;
                    int flags322 = brightnessEvent2222.getFlags() | (this.mIsRbcActive ? 1 : 0);
                    if (!this.mPowerRequest.lowPowerMode) {
                    }
                    brightnessEvent2222.setFlags(flags322 | i13);
                    BrightnessEvent brightnessEvent3222 = this.mTempBrightnessEvent;
                    ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal222 = this.mCdsi;
                    brightnessEvent3222.setRbcStrength(colorDisplayServiceInternal222 == null ? colorDisplayServiceInternal222.getReduceBrightColorsStrength() : -1);
                    this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                    this.mTempBrightnessEvent.setWasShortTermModelActive(z36);
                    this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mUseAutoBrightness);
                    if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                    }
                    if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                    }
                    this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                    this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                    brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                    brightnessEvent.setAdjustmentFlags(i15);
                    int flags22222 = brightnessEvent.getFlags();
                    if (!z16) {
                    }
                    brightnessEvent.setFlags(flags22222 | i14);
                    Slog.m76i(this.mTag, brightnessEvent.toString(false));
                    if (!z16) {
                    }
                    logBrightnessEvent(brightnessEvent, f11);
                    ringBuffer = this.mBrightnessEventRingBuffer;
                    if (ringBuffer != null) {
                    }
                    brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                    if (brightnessMappingStrategy != null) {
                    }
                    if (this.mPendingScreenOnUnblocker == null) {
                    }
                    if (z19) {
                    }
                    int i262222 = i12;
                    if (z19) {
                    }
                    if (!z20) {
                    }
                    if (z18) {
                    }
                    if (z19) {
                    }
                    if (z20) {
                    }
                    if (z20) {
                    }
                    z21 = false;
                    this.mDozing = i262222 != 2 ? true : z21;
                    i16 = this.mPowerRequest.policy;
                    if (i7 != i16) {
                    }
                }
            }
            f = Float.NaN;
            screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController;
            if (screenOffBrightnessSensorController != null) {
            }
            if (this.mProximitySensor == null) {
            }
            if (this.mIsEnabled) {
            }
            i2 = 1;
            if (this.mDisplayId == 4) {
                i2 = 1;
            }
            if (r3 != false) {
            }
            int screenState22 = this.mPowerState.getScreenState();
            if (this.mEarlyWakeupEnabled) {
                earlyWakeUpManager.update(z, i21);
            }
            if (i2 == 1) {
                z2 = false;
            }
            i3 = this.mDualScreenPolicy;
            i4 = this.mPowerRequest.dualScreenPolicy;
            if (i3 != i4) {
            }
            animateScreenStateChange(i2, z2);
            screenState = this.mPowerState.getScreenState();
            if (screenState == 1) {
            }
            if (Float.isNaN(f)) {
                f = this.mBrightnessToFollow;
                this.mBrightnessReasonTemp.setReason(10, f);
            }
            if (!Float.isNaN(f)) {
            }
            this.mAppliedScreenBrightnessOverride = false;
            if (this.mAllowAutoBrightnessWhileDozingConfig) {
            }
            if (this.mUseAutoBrightness) {
            }
            if (!z3) {
            }
            if (this.mAutoBrightnessEnabled != z3) {
            }
            boolean updateUserSetScreenBrightness2 = updateUserSetScreenBrightness();
            if (!BrightnessSynchronizer.floatEquals(this.mTemporaryScreenBrightness, this.mCurrentScreenBrightnessSetting)) {
            }
            if (!isValidBrightnessValue(this.mTemporaryScreenBrightness)) {
            }
            boolean updateAutoBrightnessAdjustment2 = updateAutoBrightnessAdjustment();
            if (Float.isNaN(this.mTemporaryAutoBrightnessAdjustment)) {
            }
            if (!this.mPowerRequest.boostScreenBrightness) {
            }
            z5 = false;
            this.mAppliedBrightnessBoost = false;
            if (this.mResetBrightnessConfiguration) {
            }
            if (Float.isNaN(f)) {
            }
            automaticBrightnessController = this.mAutomaticBrightnessController;
            if (automaticBrightnessController == null) {
            }
            this.mHbmController.setAutoBrightnessEnabled((!this.mUseAutoBrightness || (this.mEarlyWakeupEnabled && this.mEarlyWakeUpManager.isEarlyLightSensorEnabled())) ? 1 : 2);
            brightnessTracker = this.mBrightnessTracker;
            if (brightnessTracker != null) {
            }
            if (!Float.isNaN(f)) {
            }
            if (screenState != i8) {
            }
            f6 = f5;
            z11 = true;
            if (this.mIsSupportedAodMode) {
                f = this.mLastOriginalTarget;
                this.mBrightnessReasonTemp.setReason(11, f);
            }
            if (Float.isNaN(f)) {
                float f212 = this.mScreenBrightnessDozeConfig;
                f = clampScreenBrightness(f212);
                f6 = f212;
                this.mBrightnessReasonTemp.setReason(3, f);
            }
            f7 = f6;
            if (Float.isNaN(f)) {
                f7 = screenOffBrightnessSensorController2.getAutomaticScreenBrightness();
                if (isValidBrightnessValue(f7)) {
                }
            }
            float f222 = f7;
            if (Float.isNaN(f)) {
            }
            z13 = this.mBatteryLevelCritical;
            z14 = this.mPowerRequest.batteryLevelCritical;
            boolean z362 = z8;
            int i242 = i9;
            if (z13 != z14) {
            }
            if (this.mIsDisplayInternal) {
                displayPowerRequest = this.mPowerRequest;
                if (displayPowerRequest.policy == 3) {
                    if (this.mBatteryLevelCritical) {
                    }
                    if (!z28) {
                    }
                    if (z28) {
                    }
                    z29 = false;
                    if (this.mAppliedForceDimming) {
                    }
                    z15 = z29;
                    f9 = f;
                    if (this.mBrightnessThrottler.isThrottled()) {
                    }
                    AutomaticBrightnessController automaticBrightnessController3222 = this.mAutomaticBrightnessController;
                    if (automaticBrightnessController3222 == null) {
                    }
                    float f24222 = f3;
                    i10 = 0;
                    while (i10 < clone.size()) {
                    }
                    z16 = updateUserSetScreenBrightness2;
                    if (z12) {
                    }
                    z17 = false;
                    if (this.mPowerRequest.policy != i11) {
                    }
                    if (this.mLastOriginalTarget != f10) {
                    }
                    if (!this.mPowerRequest.lowPowerMode) {
                    }
                    if (this.mAppliedLowPower) {
                    }
                    float finalBrightness2222 = getFinalBrightness(f10, screenState);
                    if (this.mForceSlowChange) {
                    }
                    this.mHbmController.onBrightnessChanged(finalBrightness2222, f9, this.mBrightnessThrottler.getBrightnessMaxReason());
                    if (this.mAppliedTemporaryBrightness) {
                    }
                    if (this.mPendingScreenOff) {
                    }
                    if (saveBrightnessInfo) {
                    }
                    if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                    }
                    f13 = f12;
                    String str3222 = this.mTag;
                    StringBuilder sb3222 = new StringBuilder();
                    sb3222.append("Brightness [");
                    sb3222.append(f13);
                    sb3222.append("] reason changing to: '");
                    i15 = i242;
                    sb3222.append(this.mBrightnessReasonTemp.toString(i15));
                    sb3222.append("', previous reason: '");
                    sb3222.append(this.mBrightnessReason);
                    sb3222.append("'.");
                    Slog.m78v(str3222, sb3222.toString());
                    this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                    this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                    this.mTempBrightnessEvent.setBrightness(f13);
                    this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                    this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                    this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                    this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                    BrightnessEvent brightnessEvent22222 = this.mTempBrightnessEvent;
                    int flags3222 = brightnessEvent22222.getFlags() | (this.mIsRbcActive ? 1 : 0);
                    if (!this.mPowerRequest.lowPowerMode) {
                    }
                    brightnessEvent22222.setFlags(flags3222 | i13);
                    BrightnessEvent brightnessEvent32222 = this.mTempBrightnessEvent;
                    ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal2222 = this.mCdsi;
                    brightnessEvent32222.setRbcStrength(colorDisplayServiceInternal2222 == null ? colorDisplayServiceInternal2222.getReduceBrightColorsStrength() : -1);
                    this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                    this.mTempBrightnessEvent.setWasShortTermModelActive(z362);
                    this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mUseAutoBrightness);
                    if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                    }
                    if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                    }
                    this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                    this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                    brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                    brightnessEvent.setAdjustmentFlags(i15);
                    int flags222222 = brightnessEvent.getFlags();
                    if (!z16) {
                    }
                    brightnessEvent.setFlags(flags222222 | i14);
                    Slog.m76i(this.mTag, brightnessEvent.toString(false));
                    if (!z16) {
                    }
                    logBrightnessEvent(brightnessEvent, f11);
                    ringBuffer = this.mBrightnessEventRingBuffer;
                    if (ringBuffer != null) {
                    }
                    brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                    if (brightnessMappingStrategy != null) {
                    }
                    if (this.mPendingScreenOnUnblocker == null) {
                    }
                    if (z19) {
                    }
                    int i2622222 = i12;
                    if (z19) {
                    }
                    if (!z20) {
                    }
                    if (z18) {
                    }
                    if (z19) {
                    }
                    if (z20) {
                    }
                    if (z20) {
                    }
                    z21 = false;
                    this.mDozing = i2622222 != 2 ? true : z21;
                    i16 = this.mPowerRequest.policy;
                    if (i7 != i16) {
                    }
                }
            }
            f9 = f;
            z15 = false;
            if (this.mBrightnessThrottler.isThrottled()) {
            }
            AutomaticBrightnessController automaticBrightnessController32222 = this.mAutomaticBrightnessController;
            if (automaticBrightnessController32222 == null) {
            }
            float f242222 = f3;
            i10 = 0;
            while (i10 < clone.size()) {
            }
            z16 = updateUserSetScreenBrightness2;
            if (z12) {
            }
            z17 = false;
            if (this.mPowerRequest.policy != i11) {
            }
            if (this.mLastOriginalTarget != f10) {
            }
            if (!this.mPowerRequest.lowPowerMode) {
            }
            if (this.mAppliedLowPower) {
            }
            float finalBrightness22222 = getFinalBrightness(f10, screenState);
            if (this.mForceSlowChange) {
            }
            this.mHbmController.onBrightnessChanged(finalBrightness22222, f9, this.mBrightnessThrottler.getBrightnessMaxReason());
            if (this.mAppliedTemporaryBrightness) {
            }
            if (this.mPendingScreenOff) {
            }
            if (saveBrightnessInfo) {
            }
            if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
            }
            f13 = f12;
            String str32222 = this.mTag;
            StringBuilder sb32222 = new StringBuilder();
            sb32222.append("Brightness [");
            sb32222.append(f13);
            sb32222.append("] reason changing to: '");
            i15 = i242;
            sb32222.append(this.mBrightnessReasonTemp.toString(i15));
            sb32222.append("', previous reason: '");
            sb32222.append(this.mBrightnessReason);
            sb32222.append("'.");
            Slog.m78v(str32222, sb32222.toString());
            this.mBrightnessReason.set(this.mBrightnessReasonTemp);
            this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
            this.mTempBrightnessEvent.setBrightness(f13);
            this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
            this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
            this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
            this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
            BrightnessEvent brightnessEvent222222 = this.mTempBrightnessEvent;
            int flags32222 = brightnessEvent222222.getFlags() | (this.mIsRbcActive ? 1 : 0);
            if (!this.mPowerRequest.lowPowerMode) {
            }
            brightnessEvent222222.setFlags(flags32222 | i13);
            BrightnessEvent brightnessEvent322222 = this.mTempBrightnessEvent;
            ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal22222 = this.mCdsi;
            brightnessEvent322222.setRbcStrength(colorDisplayServiceInternal22222 == null ? colorDisplayServiceInternal22222.getReduceBrightColorsStrength() : -1);
            this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
            this.mTempBrightnessEvent.setWasShortTermModelActive(z362);
            this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mUseAutoBrightness);
            if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
            }
            if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
            }
            this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
            this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
            brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
            brightnessEvent.setAdjustmentFlags(i15);
            int flags2222222 = brightnessEvent.getFlags();
            if (!z16) {
            }
            brightnessEvent.setFlags(flags2222222 | i14);
            Slog.m76i(this.mTag, brightnessEvent.toString(false));
            if (!z16) {
            }
            logBrightnessEvent(brightnessEvent, f11);
            ringBuffer = this.mBrightnessEventRingBuffer;
            if (ringBuffer != null) {
            }
            brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
            if (brightnessMappingStrategy != null) {
            }
            if (this.mPendingScreenOnUnblocker == null) {
            }
            if (z19) {
            }
            int i26222222 = i12;
            if (z19) {
            }
            if (!z20) {
            }
            if (z18) {
            }
            if (z19) {
            }
            if (z20) {
            }
            if (z20) {
            }
            z21 = false;
            this.mDozing = i26222222 != 2 ? true : z21;
            i16 = this.mPowerRequest.policy;
            if (i7 != i16) {
            }
        }
    }

    public final boolean shouldEnableMoreFastRampRateCase() {
        if (!this.mBrightnessReasonTemp.hasModifier(1) && this.mBrightnessReason.hasModifier(1)) {
            return true;
        }
        BrightnessReason brightnessReason = this.mBrightnessReasonTemp;
        return (brightnessReason.mReason == 4 && this.mBrightnessReason.mReason != 4) || brightnessReason.isReasonChanged(this.mBrightnessReason, 6) || this.mBrightnessReasonTemp.isModifierChanged(this.mBrightnessReason, 4);
    }

    @Override // com.android.server.display.AutomaticBrightnessController.Callbacks
    public void updateBrightness() {
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void ignoreProximitySensorUntilChanged() {
        this.mHandler.sendEmptyMessage(8);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, boolean z) {
        this.mHandler.obtainMessage(5, z ? 1 : 0, brightnessConfiguration == null ? 1 : 0, brightnessConfiguration).sendToTarget();
        this.mPendingForceUpdateAb = false;
        if (brightnessConfiguration == null || !"sec-backup".equals(brightnessConfiguration.getDescription())) {
            this.mBrightnessChangedByUser = false;
        }
        this.mLastBrightnessConfigurationTime = SystemClock.elapsedRealtime();
        this.mHandler.removeMessages(17);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(17), ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryBrightness(float f) {
        this.mHandler.obtainMessage(6, Float.floatToIntBits(f), 0).sendToTarget();
        if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            updateLastBrightnessSettingChangedTime();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryBrightnessForSlowChange(float f, boolean z) {
        this.mHandler.obtainMessage(6, Float.floatToIntBits(f), z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mHandler.obtainMessage(7, Float.floatToIntBits(f), 0).sendToTarget();
        updateLastBrightnessSettingChangedTime();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public BrightnessInfo getBrightnessInfo() {
        BrightnessInfo brightnessInfo;
        synchronized (this.mCachedBrightnessInfo) {
            CachedBrightnessInfo cachedBrightnessInfo = this.mCachedBrightnessInfo;
            brightnessInfo = new BrightnessInfo(cachedBrightnessInfo.brightness.value, cachedBrightnessInfo.adjustedBrightness.value, cachedBrightnessInfo.brightnessMin.value, cachedBrightnessInfo.brightnessMax.value, cachedBrightnessInfo.hbmMode.value, cachedBrightnessInfo.hbmTransitionPoint.value, cachedBrightnessInfo.brightnessMaxReason.value);
        }
        return brightnessInfo;
    }

    public final boolean saveBrightnessInfo(float f) {
        return saveBrightnessInfo(f, f);
    }

    public final boolean saveBrightnessInfo(float f, float f2) {
        boolean checkAndSetInt;
        synchronized (this.mCachedBrightnessInfo) {
            float min = Math.min(this.mHbmController.getCurrentBrightnessMin(), this.mBrightnessThrottler.getBrightnessCap());
            float min2 = Math.min(this.mHbmController.getCurrentBrightnessMax(), this.mBrightnessThrottler.getBrightnessCap());
            CachedBrightnessInfo cachedBrightnessInfo = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat = cachedBrightnessInfo.checkAndSetFloat(cachedBrightnessInfo.brightness, f) | false;
            CachedBrightnessInfo cachedBrightnessInfo2 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat2 = checkAndSetFloat | cachedBrightnessInfo2.checkAndSetFloat(cachedBrightnessInfo2.adjustedBrightness, f2);
            CachedBrightnessInfo cachedBrightnessInfo3 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat3 = checkAndSetFloat2 | cachedBrightnessInfo3.checkAndSetFloat(cachedBrightnessInfo3.brightnessMin, min);
            CachedBrightnessInfo cachedBrightnessInfo4 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat4 = checkAndSetFloat3 | cachedBrightnessInfo4.checkAndSetFloat(cachedBrightnessInfo4.brightnessMax, min2);
            CachedBrightnessInfo cachedBrightnessInfo5 = this.mCachedBrightnessInfo;
            boolean checkAndSetInt2 = checkAndSetFloat4 | cachedBrightnessInfo5.checkAndSetInt(cachedBrightnessInfo5.hbmMode, this.mHbmController.getHighBrightnessMode());
            CachedBrightnessInfo cachedBrightnessInfo6 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat5 = checkAndSetInt2 | cachedBrightnessInfo6.checkAndSetFloat(cachedBrightnessInfo6.hbmTransitionPoint, this.mHbmController.getTransitionPoint());
            CachedBrightnessInfo cachedBrightnessInfo7 = this.mCachedBrightnessInfo;
            checkAndSetInt = checkAndSetFloat5 | cachedBrightnessInfo7.checkAndSetInt(cachedBrightnessInfo7.brightnessMaxReason, this.mBrightnessThrottler.getBrightnessMaxReason());
            if (checkAndSetInt) {
                Slog.m72d(this.mTag, "saveBrightnessInfo: brt:" + f + " adjBrt:" + f2 + " min:" + min + " max:" + min2 + " hbm:" + this.mHbmController.getHighBrightnessMode() + " tp:" + this.mHbmController.getTransitionPoint() + " throttler:" + this.mBrightnessThrottler.getBrightnessMaxReason());
            }
        }
        return checkAndSetInt;
    }

    public void postBrightnessChangeRunnable() {
        this.mHandler.post(this.mOnBrightnessChangeRunnable);
    }

    public final HighBrightnessModeController createHbmControllerLocked() {
        DisplayDeviceConfig displayDeviceConfig = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig();
        IBinder displayTokenLocked = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayTokenLocked();
        String uniqueId = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId();
        DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = displayDeviceConfig != null ? displayDeviceConfig.getHighBrightnessModeData() : null;
        DisplayInfo displayInfoLocked = this.mLogicalDisplay.getDisplayInfoLocked();
        return new HighBrightnessModeController(this.mHandler, displayInfoLocked.logicalWidth, displayInfoLocked.logicalHeight, displayTokenLocked, uniqueId, Math.min(((PowerManager) this.mContext.getSystemService(PowerManager.class)).getBrightnessConstraint(0), this.mScreenBrightnessDimConfig), this.mScreenExtendedBrightnessRangeMaximum, highBrightnessModeData, new HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController.7
            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
            public float getHdrBrightnessFromSdr(float f, float f2) {
                return DisplayPowerController.this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
            }
        }, new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController.this.lambda$createHbmControllerLocked$5();
            }
        }, this.mHighBrightnessModeMetadata, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createHbmControllerLocked$5() {
        sendUpdatePowerState();
        postBrightnessChangeRunnable();
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.update();
        }
    }

    public final BrightnessThrottler createBrightnessThrottlerLocked() {
        return new BrightnessThrottler(this.mHandler, new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController.this.lambda$createBrightnessThrottlerLocked$6();
            }
        }, this.mUniqueDisplayId, this.mLogicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId, this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig().getThermalBrightnessThrottlingDataMapByThrottlingId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createBrightnessThrottlerLocked$6() {
        sendUpdatePowerState();
        postBrightnessChangeRunnable();
    }

    public final void blockScreenOn() {
        if (this.mPendingScreenOnUnblocker == null) {
            Trace.asyncTraceBegin(131072L, "Screen on blocked", 0);
            this.mPendingScreenOnUnblocker = new ScreenOnUnblocker();
            this.mScreenOnBlockStartRealTime = SystemClock.elapsedRealtime();
            Slog.m76i(this.mTag, "Blocking screen on until initial contents have been drawn.");
            PowerManagerUtil.sCurrentScreenOnProfiler.noteWmsStart();
        }
    }

    public final void unblockScreenOn() {
        if (this.mPendingScreenOnUnblocker != null) {
            this.mPendingScreenOnUnblocker = null;
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mScreenOnBlockStartRealTime;
            Slog.m76i(this.mTag, "Unblocked screen on after " + elapsedRealtime + " ms");
            Trace.asyncTraceEnd(131072L, "Screen on blocked", 0);
            PowerManagerUtil.sCurrentScreenOnProfiler.noteWmsEnd();
        }
    }

    public final void blockScreenOff() {
        if (this.mPendingScreenOffUnblocker == null) {
            Trace.asyncTraceBegin(131072L, "Screen off blocked", 0);
            this.mPendingScreenOffUnblocker = new ScreenOffUnblocker();
            this.mScreenOffBlockStartRealTime = SystemClock.elapsedRealtime();
            Slog.m76i(this.mTag, "Blocking screen off");
        }
    }

    public final void unblockScreenOff() {
        if (this.mPendingScreenOffUnblocker != null) {
            this.mPendingScreenOffUnblocker = null;
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mScreenOffBlockStartRealTime;
            Slog.m76i(this.mTag, "Unblocked screen off after " + elapsedRealtime + " ms");
            Trace.asyncTraceEnd(131072L, "Screen off blocked", 0);
        }
    }

    public final boolean setScreenState(int i) {
        return setScreenState(i, false);
    }

    public final boolean setScreenState(int i, boolean z) {
        int i2;
        boolean z2 = i == 1;
        if (this.mPowerState.getScreenState() != i || this.mReportedScreenStateToPolicy == -1) {
            boolean z3 = this.mPowerState.getScreenState() == 3 || this.mPowerState.getScreenState() == 4;
            if (this.mIsSupportedAodMode) {
                if (z3 && i == 2 && !this.mSeamlessAodReady) {
                    Slog.m72d(this.mTag, "setScreenState(): mSeamlessAodReady : false AOD");
                    if (!this.mPendingScreenOnByAodReady) {
                        this.mAodManagerInternal.screenTurningOn(this.mSeamlessAodReadyListener);
                        this.mPendingScreenOnByAodReady = true;
                    }
                    return false;
                }
                this.mSeamlessAodReady = false;
                this.mPendingScreenOnByAodReady = false;
            }
            if (z2 && !this.mScreenOffBecauseOfProximity) {
                int i3 = this.mReportedScreenStateToPolicy;
                if (i3 == 2 || i3 == -1) {
                    setReportedScreenState(3);
                    blockScreenOff();
                    Slog.m72d(this.mTag, "mWindowManagerPolicy.screenTurningOff()");
                    this.mWindowManagerPolicy.screenTurningOff(this.mDisplayId, this.mPendingScreenOffUnblocker);
                    unblockScreenOff();
                } else if (this.mPendingScreenOffUnblocker != null) {
                    return false;
                }
            }
            if (!z && this.mPowerState.getScreenState() != i && readyToUpdateDisplayState()) {
                Trace.traceCounter(131072L, "ScreenState", i);
                SystemProperties.set("debug.tracing.screen_state", String.valueOf(i));
                this.mPowerState.setScreenState(i);
                noteScreenState(i);
            }
        }
        if (z2 && this.mReportedScreenStateToPolicy != 0 && !this.mScreenOffBecauseOfProximity) {
            setReportedScreenState(0);
            unblockScreenOn();
            Slog.m72d(this.mTag, "mWindowManagerPolicy.screenTurnedOff()");
            this.mWindowManagerPolicy.screenTurnedOff(this.mDisplayId, this.mIsInTransition);
        } else if (!z2 && this.mReportedScreenStateToPolicy == 3) {
            unblockScreenOff();
            Slog.m72d(this.mTag, "mWindowManagerPolicy.screenTurnedOff()(transitional)");
            this.mWindowManagerPolicy.screenTurnedOff(this.mDisplayId, this.mIsInTransition);
            setReportedScreenState(0);
        }
        if (!z2 && ((i2 = this.mReportedScreenStateToPolicy) == 0 || i2 == -1)) {
            setReportedScreenState(1);
            if (this.mPowerState.getColorFadeLevel() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                blockScreenOn();
            } else {
                unblockScreenOn();
            }
            Slog.m72d(this.mTag, "mWindowManagerPolicy.screenTurningOn() +");
            this.mWindowManagerPolicy.screenTurningOn(this.mDisplayId, this.mPendingScreenOnUnblocker);
            Slog.m72d(this.mTag, "mWindowManagerPolicy.screenTurningOn() -");
        }
        return this.mPendingScreenOnUnblocker == null;
    }

    public final void setReportedScreenState(int i) {
        Trace.traceCounter(131072L, "ReportedScreenStateToPolicy", i);
        this.mReportedScreenStateToPolicy = i;
    }

    public final void loadAmbientLightSensor() {
        DisplayDeviceConfig.SensorData ambientLightSensor = this.mDisplayDeviceConfig.getAmbientLightSensor();
        Sensor findSensor = SensorUtils.findSensor(this.mSensorManager, ambientLightSensor.type, ambientLightSensor.name, (this.mDisplayId == 0 || this.mIsCoverDisplay) ? 65601 : 0);
        this.mLightSensor = findSensor;
        if (this.mDisplayId == 0 && findSensor == null) {
            this.mLightSensor = this.mSensorManager.getDefaultSensor(65604);
        }
    }

    public final void loadScreenOffBrightnessSensor() {
        DisplayDeviceConfig.SensorData screenOffBrightnessSensor = this.mDisplayDeviceConfig.getScreenOffBrightnessSensor();
        this.mScreenOffBrightnessSensor = SensorUtils.findSensor(this.mSensorManager, screenOffBrightnessSensor.type, screenOffBrightnessSensor.name, 0);
    }

    public final void loadProximitySensor() {
        int i = this.mDisplayId;
        int i2 = (i == 0 || (i == 1 && PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY)) ? 8 : 0;
        DisplayDeviceConfig.SensorData proximitySensor = this.mDisplayDeviceConfig.getProximitySensor();
        Sensor findSensor = SensorUtils.findSensor(this.mSensorManager, proximitySensor.type, proximitySensor.name, i2);
        this.mProximitySensor = findSensor;
        if (findSensor != null) {
            this.mProximityThreshold = Math.min(findSensor.getMaximumRange(), 5.0f);
            this.mIsEarsenseProximity = this.mProximitySensor.getName().contains("Palm") || this.mProximitySensor.getName().contains("Ear Hover");
            this.mIsProximitySensorOnFoldingSide = this.mProximitySensor.semIsOnFoldingSide();
        }
    }

    public final float clampScreenBrightnessForFinal(float f) {
        if (Float.isNaN(f)) {
            f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        return MathUtils.constrain(f, this.mHbmController.getCurrentBrightnessMin(), this.mScreenExtendedBrightnessRangeMaximum);
    }

    public final float clampScreenBrightness(float f) {
        if (Float.isNaN(f)) {
            f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        return MathUtils.constrain(f, this.mHbmController.getCurrentBrightnessMin(), this.mHbmController.getCurrentBrightnessMax());
    }

    public final boolean isValidBrightnessValue(float f) {
        return f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f <= this.mScreenExtendedBrightnessRangeMaximum;
    }

    public final void animateScreenBrightness(float f, float f2, float f3, float f4) {
        String str;
        String str2 = this.mTag;
        StringBuilder sb = new StringBuilder();
        sb.append("Animating brightness: target=");
        sb.append(PowerManagerUtil.brightnessToString(f));
        if (f != f2) {
            str = ", sdrTarget=" + PowerManagerUtil.brightnessToString(f2);
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(String.format(", rate=%.3f", Float.valueOf(f3)));
        sb.append(Float.isNaN(f4) ? "" : String.format(", rateAtHbm=%.3f", Float.valueOf(f4)));
        sb.append(", reason=");
        sb.append(this.mBrightnessReasonTemp.changesToString());
        Slog.m72d(str2, sb.toString());
        if (this.mBrightnessReasonTemp.hasLoggableChanges(this.mBrightnessReason)) {
            this.mPowerHistorian.onBrightnessReasonChanged(this.mBrightnessReasonTemp.changesToString());
        }
        if (this.mScreenBrightnessRampAnimator.animateTo(f, f2, f3, f4)) {
            if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN && this.mOnBrightnessAnimationConsumer != null && this.mScreenBrightnessRampAnimator.isAnimating() && !this.mBrightnessAnimationConsumerInvoked) {
                this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdRefreshRate);
                this.mBrightnessAnimationConsumerInvoked = true;
                this.mOnBrightnessAnimationConsumer.accept(Boolean.TRUE);
            }
            Trace.traceCounter(131072L, "TargetScreenBrightness", (int) f);
            SystemProperties.set("debug.tracing.screen_brightness", String.valueOf(f));
            noteScreenBrightness(f);
        }
    }

    public final void animateScreenStateChange(int i, boolean z) {
        int i2;
        Slog.m72d(this.mTag, "animateScreenStateChange: target=" + Display.stateToString(i) + ", mIsEnabled=" + this.mIsEnabled);
        if (this.mColorFadeEnabled && (this.mColorFadeOnAnimator.isStarted() || this.mColorFadeOffAnimator.isStarted())) {
            if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY || i != 2) {
                return;
            }
            this.mPendingScreenOff = false;
            if (this.mColorFadeOffAnimator.isStarted()) {
                Slog.m72d(this.mTag, "animateScreenStateChange: mColorFadeOffAnimator.cancel()");
                this.mColorFadeOffAnimator.cancel();
            }
        }
        if (this.mDisplayBlanksAfterDozeConfig && Display.isDozeState(this.mPowerState.getScreenState()) && !Display.isDozeState(i)) {
            this.mPowerState.prepareColorFade(this.mContext, this.mColorFadeFadesConfig ? 2 : 0);
            ObjectAnimator objectAnimator = this.mColorFadeOffAnimator;
            if (objectAnimator != null) {
                objectAnimator.end();
            }
            setScreenState(1, i != 1);
        }
        if (this.mPendingScreenOff && i != 1) {
            setScreenState(1);
            this.mPendingScreenOff = false;
            this.mPowerState.dismissColorFadeResources();
        }
        if (i == 2) {
            if (this.mIsSupportedAodMode && PowerManagerUtil.SEC_FEATURE_AOD_DISABLE_CLOCK_TRANSITION && this.mPowerState.getScreenState() == 4) {
                setScreenState(3);
            }
            if (this.mNeedPrepareColorFade && this.mPowerRequest.lastWakeUpReason == 9) {
                if (this.mPowerState.getColorFadeLevel() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON && this.mPowerState.prepareColorFade(this.mContext, 2)) {
                    Slog.m72d(this.mTag, "draw ColorFade due to unfolding");
                    this.mPowerState.setColorFadeLevel(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                }
                this.mNeedPrepareColorFade = false;
            }
            if (setScreenState(2)) {
                AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
                if (automaticBrightnessController != null && this.mAutoBrightnessEnabled && !automaticBrightnessController.isAmbientLuxValid() && this.mPowerState.getScreenState() == 2 && Float.isNaN(this.mPowerRequest.screenBrightnessOverride) && !isLightSensorCovered() && (!this.mIsCoverDisplay || this.mDualScreenPolicy == 1)) {
                    Slog.m72d(this.mTag, "animateScreenStateChange is returned because lux is not yet valid!");
                    return;
                } else {
                    this.mPowerState.setColorFadeLevel(1.0f);
                    this.mPowerState.dismissColorFade();
                    return;
                }
            }
            return;
        }
        if (i == 3) {
            if (!(this.mScreenBrightnessRampAnimator.isAnimating() && this.mPowerState.getScreenState() == 2) && setScreenState(3)) {
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 4) {
            if (!this.mScreenBrightnessRampAnimator.isAnimating() || this.mPowerState.getScreenState() == 4) {
                if (this.mPowerState.getScreenState() != 4) {
                    if (!setScreenState(3)) {
                        return;
                    } else {
                        setScreenState(4);
                    }
                }
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 6) {
            if (!this.mScreenBrightnessRampAnimator.isAnimating() || this.mPowerState.getScreenState() == 6) {
                if (this.mPowerState.getScreenState() != 6) {
                    if (!setScreenState(2)) {
                        return;
                    } else {
                        setScreenState(6);
                    }
                }
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        this.mPendingScreenOff = true;
        if (!this.mColorFadeEnabled) {
            this.mPowerState.setColorFadeLevel(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        }
        if (this.mPowerState.getColorFadeLevel() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            setScreenState(1);
            this.mPendingScreenOff = false;
            this.mPowerState.dismissColorFadeResources();
            return;
        }
        if (this.mColorFadeFadesConfig) {
            i2 = 2;
        } else {
            i2 = SAMSUNG_UX_COLOR_FADE_OFF_EFFECT_ENABLED ? 3 : 1;
        }
        if (z && this.mPowerState.prepareColorFade(this.mContext, i2) && this.mPowerState.getScreenState() != 1) {
            if (i2 == 3) {
                this.mColorFadeOffAnimator.setInterpolator(COLOR_FADE_PATH_INTERPOLATOR);
            } else {
                this.mColorFadeOffAnimator.setInterpolator(COLOR_FADE_DEFAULT_INTERPOLATOR);
            }
            this.mColorFadeOffAnimator.start();
            return;
        }
        this.mPowerState.prepareColorFade(this.mContext, 2);
        this.mColorFadeOffAnimator.end();
    }

    public final void setProximitySensorEnabled(boolean z) {
        if (z) {
            if (this.mProximitySensorEnabled) {
                return;
            }
            this.mProximitySensorEnabled = true;
            this.mIgnoreProximityUntilChanged = false;
            Slog.m72d(this.mTag, "setProximitySensorEnabled::registerListener");
            this.mSensorManager.registerListener(this.mProximitySensorListener, this.mProximitySensor, 3, this.mHandler);
            return;
        }
        if (this.mProximitySensorEnabled) {
            this.mProximitySensorEnabled = false;
            this.mProximity = -1;
            this.mIgnoreProximityUntilChanged = false;
            this.mPendingProximity = -1;
            this.mHandler.removeMessages(2);
            Slog.m72d(this.mTag, "setProximitySensorEnabled::unregisterListener");
            this.mSensorManager.unregisterListener(this.mProximitySensorListener);
            clearPendingProximityDebounceTime();
        }
    }

    public final void handleProximitySensorEvent(long j, boolean z) {
        if (this.mProximitySensorEnabled) {
            int i = this.mPendingProximity;
            if (i != 0 || z) {
                boolean z2 = true;
                if (i == 1 && z) {
                    return;
                }
                this.mHandler.removeMessages(2);
                if (z) {
                    this.mPendingProximity = 1;
                    setPendingProximityDebounceTime(0 + j);
                    int i2 = this.mPowerRequest.coverType;
                    if (i2 != 8 && i2 != 15 && i2 != 16 && i2 != 17) {
                        z2 = false;
                    }
                    int i3 = this.mSensorPositiveDebounceDelay;
                    if (i3 == -1) {
                        i3 = 0;
                    }
                    setPendingProximityDebounceTime(j + i3 + ((this.mIsEarsenseProximity && z2) ? 50 : 0));
                } else {
                    this.mPendingProximity = 0;
                    setPendingProximityDebounceTime(j + (this.mSensorNegativeDebounceDelay != -1 ? r9 : 50));
                }
                debounceProximitySensor();
            }
        }
    }

    public final void debounceProximitySensor() {
        if (!this.mProximitySensorEnabled || this.mPendingProximity == -1 || this.mPendingProximityDebounceTime < 0) {
            return;
        }
        if (this.mPendingProximityDebounceTime <= this.mClock.uptimeMillis()) {
            if (this.mProximity != this.mPendingProximity) {
                this.mIgnoreProximityUntilChanged = false;
                Slog.m76i(this.mTag, "No longer ignoring proximity [" + this.mPendingProximity + "]");
            }
            this.mProximity = this.mPendingProximity;
            updatePowerState();
            clearPendingProximityDebounceTime();
            return;
        }
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2), this.mPendingProximityDebounceTime);
    }

    public final void clearPendingProximityDebounceTime() {
        if (this.mPendingProximityDebounceTime >= 0) {
            this.mPendingProximityDebounceTime = -1L;
            this.mCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxDebounce);
        }
    }

    public final void setPendingProximityDebounceTime(long j) {
        if (this.mPendingProximityDebounceTime < 0) {
            this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxDebounce);
        }
        this.mPendingProximityDebounceTime = j;
    }

    public final void sendOnStateChangedWithWakelock() {
        if (this.mOnStateChangedPending) {
            return;
        }
        this.mOnStateChangedPending = true;
        this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
        this.mHandler.post(this.mOnStateChangedRunnable);
    }

    public final void logDisplayPolicyChanged(int i) {
        LogMaker logMaker = new LogMaker(1696);
        logMaker.setType(6);
        logMaker.setSubtype(i);
        MetricsLogger.action(logMaker);
    }

    public final void handleSettingsChange(boolean z) {
        AutomaticBrightnessController automaticBrightnessController;
        this.mPendingScreenBrightnessSetting = getScreenBrightnessSetting();
        this.mPendingAutoBrightnessAdjustment = getAutoBrightnessAdjustmentSetting();
        this.mGameAutoBrightnessLocked = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "game_autobrightness_lock", 0, -2) != 0;
        if (z) {
            setCurrentScreenBrightness(this.mPendingScreenBrightnessSetting);
            updateAutoBrightnessAdjustment();
            if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL && (automaticBrightnessController = this.mAutomaticBrightnessController) != null) {
                automaticBrightnessController.resetShortTermModel();
            }
        }
        if (!PowerManagerUtil.SHIP_BUILD && this.mIsCoverDisplay) {
            this.mCoverDisplayDemoEnabled = getCoverDisplayDemoSetting();
        }
        this.mPrevScreenBrightness = this.mCurrentScreenBrightnessSetting;
        String format = String.format(" sb: %.3f abAdj: %.3f sbLock: %s", Float.valueOf(this.mPendingScreenBrightnessSetting), Float.valueOf(this.mPendingAutoBrightnessAdjustment), Boolean.valueOf(this.mGameAutoBrightnessLocked));
        String str = this.mTag;
        StringBuilder sb = new StringBuilder();
        sb.append("[api] handleSettingsChange:");
        sb.append(z ? " userSwitch" : "");
        sb.append(format);
        Slog.m72d(str, sb.toString());
        sendUpdatePowerState();
    }

    public final void handleBrightnessModeChange() {
        final int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), this.mScreenBrightnessModeSettingName, 0, -2);
        this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController.this.lambda$handleBrightnessModeChange$7(intForUser);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleBrightnessModeChange$7(int i) {
        boolean z = this.mUseAutoBrightness;
        boolean z2 = i == 1;
        this.mUseAutoBrightness = z2;
        if (z != z2) {
            if (!z2 && this.mAutomaticBrightnessController != null) {
                this.mPowerHistorian.onAutoBrightnessEvent("ShortTermModel: reset data, manual");
                this.mAutomaticBrightnessController.resetShortTermModel();
            }
            updateLastBrightnessSettingChangedTime();
            if (PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY) {
                postBrightnessModeChangeRunnable();
            }
        }
        Slog.m72d(this.mTag, "[api] handleBrightnessModeChange: mUseAutoBrightness=" + this.mUseAutoBrightness);
        updatePowerState();
    }

    public final float getAutoBrightnessAdjustmentSetting() {
        float floatForUser = Settings.System.getFloatForUser(this.mContext.getContentResolver(), "screen_auto_brightness_adj", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -2);
        return Float.isNaN(floatForUser) ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : clampAutoBrightnessAdjustment(floatForUser);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getScreenBrightnessSetting() {
        float brightness = this.mBrightnessSetting.getBrightness();
        if (Float.isNaN(brightness)) {
            Slog.m72d(this.mTag, "getScreenBrightnessSetting: default: " + this.mScreenBrightnessDefault);
            brightness = this.mScreenBrightnessDefault;
        }
        return clampAbsoluteBrightness(brightness);
    }

    public final void loadNitBasedBrightnessSetting() {
        if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
            float brightnessNitsForDefaultDisplay = this.mBrightnessSetting.getBrightnessNitsForDefaultDisplay();
            if (brightnessNitsForDefaultDisplay >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                float convertToFloatScale = convertToFloatScale(brightnessNitsForDefaultDisplay);
                if (isValidBrightnessValue(convertToFloatScale)) {
                    this.mBrightnessSetting.setBrightness(convertToFloatScale);
                    this.mCurrentScreenBrightnessSetting = convertToFloatScale;
                    return;
                }
            }
        }
        this.mCurrentScreenBrightnessSetting = getScreenBrightnessSetting();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightness(float f) {
        this.mBrightnessSetting.setBrightness(f);
        if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
            float convertToNits = convertToNits(f);
            if (convertToNits >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                this.mBrightnessSetting.setBrightnessNitsForDefaultDisplay(convertToNits);
            }
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onBootCompleted() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(15), this.mClock.uptimeMillis());
    }

    public final boolean updateScreenBrightnessSetting(float f) {
        float clampAbsoluteBrightness = clampAbsoluteBrightness(f);
        if (!isValidBrightnessValue(clampAbsoluteBrightness) || clampAbsoluteBrightness == this.mCurrentScreenBrightnessSetting) {
            return false;
        }
        setCurrentScreenBrightness(clampAbsoluteBrightness);
        setBrightness(clampAbsoluteBrightness);
        return true;
    }

    public final void setCurrentScreenBrightness(float f) {
        if (f != this.mCurrentScreenBrightnessSetting) {
            Slog.m72d(this.mTag, "setCurrentScreenBrightness : " + f);
            this.mCurrentScreenBrightnessSetting = f;
            postBrightnessChangeRunnable();
        }
    }

    public final void putAutoBrightnessAdjustmentSetting(float f) {
        if (this.mDisplayId == 0) {
            this.mAutoBrightnessAdjustment = f;
            Settings.System.putFloatForUser(this.mContext.getContentResolver(), "screen_auto_brightness_adj", f, -2);
        }
    }

    public final boolean updateAutoBrightnessAdjustment() {
        if (Float.isNaN(this.mPendingAutoBrightnessAdjustment)) {
            return false;
        }
        float f = this.mAutoBrightnessAdjustment;
        float f2 = this.mPendingAutoBrightnessAdjustment;
        if (f == f2) {
            this.mPendingAutoBrightnessAdjustment = Float.NaN;
            return false;
        }
        this.mAutoBrightnessAdjustment = f2;
        this.mPendingAutoBrightnessAdjustment = Float.NaN;
        this.mTemporaryAutoBrightnessAdjustment = Float.NaN;
        return true;
    }

    public final boolean updateUserSetScreenBrightness() {
        if (!Float.isNaN(this.mPendingScreenBrightnessSetting)) {
            float f = this.mPendingScreenBrightnessSetting;
            if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                if (this.mCurrentScreenBrightnessSetting == f) {
                    this.mPendingScreenBrightnessSetting = Float.NaN;
                    this.mTemporaryScreenBrightness = Float.NaN;
                    return false;
                }
                setCurrentScreenBrightness(f);
                this.mLastUserSetScreenBrightness = this.mPendingScreenBrightnessSetting;
                this.mPendingScreenBrightnessSetting = Float.NaN;
                this.mTemporaryScreenBrightness = Float.NaN;
                return true;
            }
        }
        return false;
    }

    public final void notifyBrightnessTrackerChanged(float f, boolean z, boolean z2, boolean z3, boolean z4) {
        AutomaticBrightnessController automaticBrightnessController;
        float convertToAdjustedNits = convertToAdjustedNits(f);
        if (z4 || (automaticBrightnessController = this.mAutomaticBrightnessController) == null || automaticBrightnessController.isInIdleMode() || !z3) {
            return;
        }
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            if (this.mAdaptiveBrightnessLongtermModelBuilder == null) {
                return;
            }
        } else if (this.mBrightnessTracker == null) {
            return;
        }
        if (!this.mUseAutoBrightness || convertToAdjustedNits < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            return;
        }
        if ((z && !this.mAutomaticBrightnessController.hasValidAmbientLux()) || this.mAutomaticBrightnessController.isHbmLux()) {
            z = false;
        }
        boolean z5 = z;
        if (BrightnessSynchronizer.floatEquals(this.mLastNotifiedBrightness, f)) {
            return;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mPowerRequest;
        float f2 = displayPowerRequest.screenBrightnessScaleFactor;
        if (f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f2 == 1.0f) {
            this.mLastNotifiedBrightness = f;
            float f3 = displayPowerRequest.lowPowerMode ? displayPowerRequest.screenLowPowerBrightnessFactor : 1.0f;
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                this.mAdaptiveBrightnessLongtermModelBuilder.notifyBrightnessChanged(convertToAdjustedNits, z5, f3, z2, this.mAutomaticBrightnessController.isDefaultConfig(), this.mUniqueDisplayId, this.mAutomaticBrightnessController.getBrightnessSpline());
            } else {
                this.mBrightnessTracker.notifyBrightnessChanged(convertToAdjustedNits, z5, f3, z2, this.mAutomaticBrightnessController.isDefaultConfig(), this.mUniqueDisplayId, this.mAutomaticBrightnessController.getLastSensorValues(), this.mAutomaticBrightnessController.getLastSensorTimestamps());
            }
        }
    }

    public final float convertToNits(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return -1.0f;
        }
        return automaticBrightnessController.convertToNits(f);
    }

    public final float convertToAdjustedNits(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return -1.0f;
        }
        return automaticBrightnessController.convertToAdjustedNits(f);
    }

    public final float convertToFloatScale(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return Float.NaN;
        }
        return automaticBrightnessController.convertToFloatScale(f);
    }

    public final void updatePendingProximityRequestsLocked() {
        this.mWaitingForNegativeProximity |= this.mPendingWaitForNegativeProximityLocked;
        this.mPendingWaitForNegativeProximityLocked = false;
        if (this.mIgnoreProximityUntilChanged) {
            this.mWaitingForNegativeProximity = false;
        }
    }

    public final void ignoreProximitySensorUntilChangedInternal() {
        if (this.mIgnoreProximityUntilChanged || this.mProximity != 1) {
            return;
        }
        this.mIgnoreProximityUntilChanged = true;
        Slog.m76i(this.mTag, "Ignoring proximity");
        updatePowerState();
    }

    public final void sendOnProximityPositiveWithWakelock() {
        this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        this.mHandler.post(this.mOnProximityPositiveRunnable);
        this.mOnProximityPositiveMessages++;
    }

    public final void sendOnProximityNegativeWithWakelock() {
        this.mOnProximityNegativeMessages++;
        this.mCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        this.mHandler.post(this.mOnProximityNegativeRunnable);
    }

    public final boolean readyToUpdateDisplayState() {
        return this.mDisplayId == 0 || this.mBootCompleted;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void dump(final PrintWriter printWriter) {
        synchronized (this.mLock) {
            Slog.m72d(this.mTag, "dump");
            printWriter.println();
            printWriter.println("Display Power Controller:");
            printWriter.println("  mDisplayId=" + this.mDisplayId);
            printWriter.println("  mLeadDisplayId=" + this.mLeadDisplayId);
            printWriter.println("  mLightSensor=" + this.mLightSensor);
            printWriter.println("  mDisplayBrightnessFollowers=" + this.mDisplayBrightnessFollowers);
            printWriter.println();
            printWriter.println("Display Power Controller Locked State:");
            printWriter.println("  mDisplayReadyLocked=" + this.mDisplayReadyLocked);
            printWriter.println("  mPendingRequestLocked=" + this.mPendingRequestLocked);
            printWriter.println("  mPendingRequestChangedLocked=" + this.mPendingRequestChangedLocked);
            printWriter.println("  mPendingWaitForNegativeProximityLocked=" + this.mPendingWaitForNegativeProximityLocked);
            printWriter.println("  mPendingUpdatePowerStateLocked=" + this.mPendingUpdatePowerStateLocked);
        }
        printWriter.println();
        printWriter.println("Display Power Controller Configuration:");
        printWriter.println("  mScreenBrightnessRangeDefault=" + this.mScreenBrightnessDefault);
        printWriter.println("  mScreenBrightnessDozeConfig=" + this.mScreenBrightnessDozeConfig);
        printWriter.println("  mScreenBrightnessDimConfig=" + this.mScreenBrightnessDimConfig);
        printWriter.println("  mUseSoftwareAutoBrightnessConfig=" + this.mUseSoftwareAutoBrightnessConfig);
        printWriter.println("  mAllowAutoBrightnessWhileDozingConfig=" + this.mAllowAutoBrightnessWhileDozingConfig);
        printWriter.println("  mPersistBrightnessNitsForDefaultDisplay=" + this.mPersistBrightnessNitsForDefaultDisplay);
        printWriter.println("  mSkipScreenOnBrightnessRamp=" + this.mSkipScreenOnBrightnessRamp);
        printWriter.println("  mColorFadeFadesConfig=" + this.mColorFadeFadesConfig);
        printWriter.println("  mColorFadeEnabled=" + this.mColorFadeEnabled);
        synchronized (this.mCachedBrightnessInfo) {
            printWriter.println("  mCachedBrightnessInfo.brightness=" + this.mCachedBrightnessInfo.brightness.value);
            printWriter.println("  mCachedBrightnessInfo.adjustedBrightness=" + this.mCachedBrightnessInfo.adjustedBrightness.value);
            printWriter.println("  mCachedBrightnessInfo.brightnessMin=" + this.mCachedBrightnessInfo.brightnessMin.value);
            printWriter.println("  mCachedBrightnessInfo.brightnessMax=" + this.mCachedBrightnessInfo.brightnessMax.value);
            printWriter.println("  mCachedBrightnessInfo.hbmMode=" + this.mCachedBrightnessInfo.hbmMode.value);
            printWriter.println("  mCachedBrightnessInfo.hbmTransitionPoint=" + this.mCachedBrightnessInfo.hbmTransitionPoint.value);
            printWriter.println("  mCachedBrightnessInfo.brightnessMaxReason =" + this.mCachedBrightnessInfo.brightnessMaxReason.value);
        }
        printWriter.println("  mDisplayBlanksAfterDozeConfig=" + this.mDisplayBlanksAfterDozeConfig);
        printWriter.println("  mBrightnessBucketsInDozeConfig=" + this.mBrightnessBucketsInDozeConfig);
        printWriter.println("  --SEC_PMS");
        printWriter.println("  AUTO_BRIGHTNESS_TYPE=" + PowerManagerUtil.AUTO_BRIGHTNESS_TYPE);
        printWriter.println("  USE_SEC_LONG_TERM_MODEL=" + PowerManagerUtil.USE_SEC_LONG_TERM_MODEL);
        printWriter.println("  USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL=" + PowerManagerUtil.USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL);
        printWriter.println("  extraDim mExtraDimStrength= " + this.mExtraDimStrength);
        printWriter.println("  extraDim mExtraDimIsActive= " + this.mExtraDimIsActive);
        this.mHandler.runWithScissors(new Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController.this.lambda$dump$8(printWriter);
            }
        }, 1000L);
    }

    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public final void lambda$dump$8(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Display Power Controller Thread State:");
        printWriter.println("  mPowerRequest=" + this.mPowerRequest);
        printWriter.println("  mUnfinishedBusiness=" + this.mUnfinishedBusiness);
        printWriter.println("  mWaitingForNegativeProximity=" + this.mWaitingForNegativeProximity);
        printWriter.println("  mProximitySensor=" + this.mProximitySensor);
        printWriter.println("  mProximitySensorEnabled=" + this.mProximitySensorEnabled);
        printWriter.println("  mProximityThreshold=" + this.mProximityThreshold);
        printWriter.println("  mProximity=" + proximityToString(this.mProximity));
        printWriter.println("  mPendingProximity=" + proximityToString(this.mPendingProximity));
        printWriter.println("  mPendingProximityDebounceTime=" + TimeUtils.formatUptime(this.mPendingProximityDebounceTime));
        printWriter.println("  mScreenOffBecauseOfProximity=" + this.mScreenOffBecauseOfProximity);
        printWriter.println("  mLastUserSetScreenBrightness=" + this.mLastUserSetScreenBrightness);
        printWriter.println("  mPendingScreenBrightnessSetting=" + this.mPendingScreenBrightnessSetting);
        printWriter.println("  mTemporaryScreenBrightness=" + this.mTemporaryScreenBrightness);
        printWriter.println("  mBrightnessToFollow=" + this.mBrightnessToFollow);
        printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
        printWriter.println("  mBrightnessReason=" + this.mBrightnessReason);
        printWriter.println("  mTemporaryAutoBrightnessAdjustment=" + this.mTemporaryAutoBrightnessAdjustment);
        printWriter.println("  mPendingAutoBrightnessAdjustment=" + this.mPendingAutoBrightnessAdjustment);
        printWriter.println("  mAppliedAutoBrightness=" + this.mAppliedAutoBrightness);
        printWriter.println("  mAppliedDimming=" + this.mAppliedDimming);
        printWriter.println("  mAppliedLowPower=" + this.mAppliedLowPower);
        printWriter.println("  mAppliedThrottling=" + this.mAppliedThrottling);
        printWriter.println("  mAppliedScreenBrightnessOverride=" + this.mAppliedScreenBrightnessOverride);
        printWriter.println("  mAppliedTemporaryBrightness=" + this.mAppliedTemporaryBrightness);
        printWriter.println("  mAppliedTemporaryAutoBrightnessAdjustment=" + this.mAppliedTemporaryAutoBrightnessAdjustment);
        printWriter.println("  mAppliedBrightnessBoost=" + this.mAppliedBrightnessBoost);
        printWriter.println("  mDozing=" + this.mDozing);
        printWriter.println("  mSkipRampState=" + skipRampStateToString(this.mSkipRampState));
        printWriter.println("  mScreenOnBlockStartRealTime=" + this.mScreenOnBlockStartRealTime);
        printWriter.println("  mScreenOffBlockStartRealTime=" + this.mScreenOffBlockStartRealTime);
        printWriter.println("  mPendingScreenOnUnblocker=" + this.mPendingScreenOnUnblocker);
        printWriter.println("  mPendingScreenOffUnblocker=" + this.mPendingScreenOffUnblocker);
        printWriter.println("  mPendingScreenOff=" + this.mPendingScreenOff);
        printWriter.println("  mReportedToPolicy=" + reportedToPolicyToString(this.mReportedScreenStateToPolicy));
        printWriter.println("  mIsRbcActive=" + this.mIsRbcActive);
        printWriter.println("  mOnStateChangePending=" + this.mOnStateChangedPending);
        printWriter.println("  mOnProximityPositiveMessages=" + this.mOnProximityPositiveMessages);
        printWriter.println("  mOnProximityNegativeMessages=" + this.mOnProximityNegativeMessages);
        if (this.mScreenBrightnessRampAnimator != null) {
            printWriter.println("  mScreenBrightnessRampAnimator.isAnimating()=" + this.mScreenBrightnessRampAnimator.isAnimating());
        }
        if (this.mColorFadeOnAnimator != null) {
            printWriter.println("  mColorFadeOnAnimator.isStarted()=" + this.mColorFadeOnAnimator.isStarted());
        }
        if (this.mColorFadeOffAnimator != null) {
            printWriter.println("  mColorFadeOffAnimator.isStarted()=" + this.mColorFadeOffAnimator.isStarted());
        }
        DisplayPowerState displayPowerState = this.mPowerState;
        if (displayPowerState != null) {
            displayPowerState.dump(printWriter);
        }
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.dump(printWriter);
            dumpBrightnessEvents(printWriter);
        }
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController;
        if (screenOffBrightnessSensorController != null) {
            screenOffBrightnessSensorController.dump(printWriter);
        }
        HighBrightnessModeController highBrightnessModeController = this.mHbmController;
        if (highBrightnessModeController != null) {
            highBrightnessModeController.dump(printWriter);
        }
        BrightnessThrottler brightnessThrottler = this.mBrightnessThrottler;
        if (brightnessThrottler != null) {
            brightnessThrottler.dump(printWriter);
        }
        printWriter.println();
        printWriter.println("  mLastBrightnessConfigurationTime=" + this.mLastBrightnessConfigurationTime);
        printWriter.println("  mPendingForceUpdateAb=" + this.mPendingForceUpdateAb);
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL && this.mAdaptiveBrightnessLongtermModelBuilder != null) {
            printWriter.println();
            this.mAdaptiveBrightnessLongtermModelBuilder.dump(printWriter);
        }
        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
            printWriter.println("  mDualScreenPolicy=" + this.mDualScreenPolicy);
        }
        printWriter.println("  SEC_FEATURE_EARLY_WAKEUP=true");
        EarlyWakeUpManager earlyWakeUpManager = this.mEarlyWakeUpManager;
        if (earlyWakeUpManager != null) {
            earlyWakeUpManager.dump(printWriter);
        }
        printWriter.println();
        printWriter.println("  USE_LONG_RAMP_RATE_FOR_NON_HBM=true");
        printWriter.println("  SEC_FEATURE_BRIGHTNESS_CONTROL_BY_EXTRA_DIM=false");
    }

    public static String proximityToString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? Integer.toString(i) : "Positive" : "Negative" : "Unknown";
    }

    public static String reportedToPolicyToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "REPORTED_TO_POLICY_SCREEN_ON" : "REPORTED_TO_POLICY_SCREEN_TURNING_ON" : "REPORTED_TO_POLICY_SCREEN_OFF";
    }

    public static String skipRampStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "RAMP_STATE_SKIP_AUTOBRIGHT" : "RAMP_STATE_SKIP_INITIAL" : "RAMP_STATE_SKIP_NONE";
    }

    public final void dumpBrightnessEvents(PrintWriter printWriter) {
        int size = this.mBrightnessEventRingBuffer.size();
        if (size < 1) {
            printWriter.println("No Automatic Brightness Adjustments");
            return;
        }
        printWriter.println("Automatic Brightness Adjustments Last " + size + " Events: ");
        BrightnessEvent[] brightnessEventArr = (BrightnessEvent[]) this.mBrightnessEventRingBuffer.toArray();
        for (int i = 0; i < this.mBrightnessEventRingBuffer.size(); i++) {
            printWriter.println("  " + brightnessEventArr[i].toString());
        }
    }

    public static float clampAbsoluteBrightness(float f) {
        return MathUtils.constrain(f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
    }

    public static float clampAutoBrightnessAdjustment(float f) {
        return MathUtils.constrain(f, -1.0f, 1.0f);
    }

    public final void noteScreenState(int i) {
        int i2;
        FrameworkStatsLog.write(FrameworkStatsLog.SCREEN_STATE_CHANGED_V2, i, this.mDisplayStatsId);
        IBatteryStats iBatteryStats = this.mBatteryStats;
        if (iBatteryStats != null) {
            try {
                if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                    if (!(PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && (((i2 = this.mDisplayId) == 0 && this.mDualScreenPolicy == 1) || (i2 == 1 && this.mDualScreenPolicy == 0)))) {
                        Slog.m72d(this.mTag, "noteDualScreenState: State=" + Display.stateToString(i) + ", dualScreenPolicy=" + this.mDualScreenPolicy);
                        this.mBatteryStats.noteDualScreenState(i, this.mDisplayId, this.mDualScreenPolicy);
                    }
                } else {
                    iBatteryStats.noteScreenState(i);
                }
            } catch (RemoteException unused) {
            }
        }
        if (!this.mIsDisplayInternal || this.mHqmDataDispatcher == null) {
            return;
        }
        int i3 = this.mDisplayId;
        if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
            i3 = !this.mDisplayDeviceConfig.isFirstDisplay() ? 1 : 0;
        }
        this.mHqmDataDispatcher.noteScreenState(i, i3);
    }

    public final void noteScreenBrightness(float f) {
        IBatteryStats iBatteryStats = this.mBatteryStats;
        if (iBatteryStats != null) {
            try {
                if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                    iBatteryStats.noteDualScreenBrightness(BrightnessSynchronizer.brightnessFloatToInt(f), this.mDisplayId, this.mDualScreenPolicy);
                } else {
                    iBatteryStats.noteScreenBrightness(BrightnessSynchronizer.brightnessFloatToInt(f));
                }
            } catch (RemoteException unused) {
            }
        }
        if (!this.mIsDisplayInternal || this.mHqmDataDispatcher == null) {
            return;
        }
        int i = this.mDisplayId;
        if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
            i = !this.mDisplayDeviceConfig.isFirstDisplay() ? 1 : 0;
        }
        this.mHqmDataDispatcher.noteScreenBrightness(f, i);
    }

    public final void reportStats(float f) {
        if (this.mLastStatsBrightness == f) {
            return;
        }
        synchronized (this.mCachedBrightnessInfo) {
            MutableFloat mutableFloat = this.mCachedBrightnessInfo.hbmTransitionPoint;
            if (mutableFloat == null) {
                return;
            }
            float f2 = mutableFloat.value;
            boolean z = f > f2;
            boolean z2 = this.mLastStatsBrightness > f2;
            if (z || z2) {
                this.mLastStatsBrightness = f;
                this.mHandler.removeMessages(13);
                if (z != z2) {
                    logHbmBrightnessStats(f, this.mDisplayStatsId);
                    return;
                }
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = 13;
                obtainMessage.arg1 = Float.floatToIntBits(f);
                obtainMessage.arg2 = this.mDisplayStatsId;
                this.mHandler.sendMessageAtTime(obtainMessage, this.mClock.uptimeMillis() + 500);
            }
        }
    }

    public final void logHbmBrightnessStats(float f, int i) {
        synchronized (this.mHandler) {
            FrameworkStatsLog.write(FrameworkStatsLog.DISPLAY_HBM_BRIGHTNESS_CHANGED, i, f);
        }
    }

    public final int nitsToRangeIndex(float f) {
        int i = 0;
        while (true) {
            float[] fArr = BRIGHTNESS_RANGE_BOUNDARIES;
            if (i >= fArr.length) {
                return 38;
            }
            if (f < fArr[i]) {
                return BRIGHTNESS_RANGE_INDEX[i];
            }
            i++;
        }
    }

    public final void logBrightnessEvent(BrightnessEvent brightnessEvent, float f) {
        int modifier = brightnessEvent.getReason().getModifier();
        int flags = brightnessEvent.getFlags();
        boolean z = f == brightnessEvent.getHbmMax();
        float convertToAdjustedNits = convertToAdjustedNits(brightnessEvent.getBrightness());
        float powerFactor = brightnessEvent.isLowPowerModeSet() ? brightnessEvent.getPowerFactor() : -1.0f;
        int rbcStrength = brightnessEvent.isRbcEnabled() ? brightnessEvent.getRbcStrength() : -1;
        float convertToAdjustedNits2 = brightnessEvent.getHbmMode() == 0 ? -1.0f : convertToAdjustedNits(brightnessEvent.getHbmMax());
        float convertToAdjustedNits3 = brightnessEvent.getThermalMax() == 1.0f ? -1.0f : convertToAdjustedNits(brightnessEvent.getThermalMax());
        if (this.mIsDisplayInternal) {
            FrameworkStatsLog.write(FrameworkStatsLog.DISPLAY_BRIGHTNESS_CHANGED, convertToAdjustedNits(brightnessEvent.getInitialBrightness()), convertToAdjustedNits, brightnessEvent.getLux(), brightnessEvent.getPhysicalDisplayId(), brightnessEvent.wasShortTermModelActive(), powerFactor, rbcStrength, convertToAdjustedNits2, convertToAdjustedNits3, brightnessEvent.isAutomaticBrightnessEnabled(), 1, convertBrightnessReasonToStatsEnum(brightnessEvent.getReason().getReason()), nitsToRangeIndex(convertToAdjustedNits), z, brightnessEvent.getHbmMode() == 1, brightnessEvent.getHbmMode() == 2, (modifier & 2) > 0, this.mBrightnessThrottler.getBrightnessMaxReason(), (modifier & 1) > 0, brightnessEvent.isRbcEnabled(), (flags & 2) > 0, (flags & 4) > 0, (flags & 8) > 0, (flags & 16) > 0, (flags & 32) > 0);
        }
    }

    public final class DisplayControllerHandler extends Handler {
        public DisplayControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 2:
                    DisplayPowerController.this.debounceProximitySensor();
                    break;
                case 3:
                    if (DisplayPowerController.this.mPendingScreenOnUnblocker == message.obj) {
                        DisplayPowerController.this.unblockScreenOn();
                        DisplayPowerController.this.updatePowerState();
                        break;
                    }
                    break;
                case 4:
                    if (DisplayPowerController.this.mPendingScreenOffUnblocker == message.obj) {
                        DisplayPowerController.this.unblockScreenOff();
                        DisplayPowerController.this.updatePowerState();
                        break;
                    }
                    break;
                case 5:
                    DisplayPowerController.this.mBrightnessConfiguration = (BrightnessConfiguration) message.obj;
                    DisplayPowerController.this.mShouldResetShortTermModel = message.arg1 == 1;
                    if (message.arg2 > 0) {
                        DisplayPowerController.this.mResetBrightnessConfiguration = true;
                    }
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 6:
                    DisplayPowerController.this.mTemporaryScreenBrightness = Float.intBitsToFloat(message.arg1);
                    if (message.arg2 > 0) {
                        Slog.m72d(DisplayPowerController.this.mTag, "[api] ForceSlowChange is requested from DisplayManager");
                        DisplayPowerController.this.mForceSlowChange = true;
                    }
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 7:
                    DisplayPowerController.this.mTemporaryAutoBrightnessAdjustment = Float.intBitsToFloat(message.arg1);
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 8:
                    DisplayPowerController.this.ignoreProximitySensorUntilChangedInternal();
                    break;
                case 9:
                    DisplayPowerController.this.cleanupHandlerThreadAfterStop();
                    break;
                case 10:
                    if (!DisplayPowerController.this.mStopped) {
                        DisplayPowerController.this.handleSettingsChange(false);
                        break;
                    }
                    break;
                case 11:
                    DisplayPowerController.this.handleRbcChanged();
                    break;
                case 12:
                    if (DisplayPowerController.this.mPowerState != null) {
                        DisplayPowerController.this.reportStats(DisplayPowerController.this.mPowerState.getScreenBrightness());
                        break;
                    }
                    break;
                case 13:
                    DisplayPowerController.this.logHbmBrightnessStats(Float.intBitsToFloat(message.arg1), message.arg2);
                    break;
                case 14:
                    DisplayPowerController.this.handleOnSwitchUser(message.arg1);
                    break;
                case 15:
                    DisplayPowerController.this.mBootCompleted = true;
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 16:
                    DisplayPowerController.this.mSeamlessAodReady = true;
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 17:
                    DisplayPowerController.this.updatePowerState();
                    break;
                case 18:
                    DisplayPowerController.this.restartAdaptiveBrightnessLongtermModelBuilderInternal(true);
                    break;
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            Slog.m72d(DisplayPowerController.this.mTag, "[api] SettingsObserver: onChange: " + uri);
            if (uri.equals(Settings.System.getUriFor(DisplayPowerController.this.mScreenBrightnessModeSettingName))) {
                DisplayPowerController.this.handleBrightnessModeChange();
            } else {
                DisplayPowerController.this.handleSettingsChange(false);
            }
        }
    }

    public final class ScreenOnUnblocker implements WindowManagerPolicy.ScreenOnListener {
        public ScreenOnUnblocker() {
        }

        @Override // com.android.server.policy.WindowManagerPolicy.ScreenOnListener
        public void onScreenOn() {
            Slog.m72d(DisplayPowerController.this.mTag, "[api] WindowManagerPolicy.ScreenOnListener : called onScreenOn()");
            DisplayPowerController.this.mHandler.sendMessageAtTime(DisplayPowerController.this.mHandler.obtainMessage(3, this), DisplayPowerController.this.mClock.uptimeMillis());
        }
    }

    public final class ScreenOffUnblocker implements WindowManagerPolicy.ScreenOffListener {
        public ScreenOffUnblocker() {
        }

        @Override // com.android.server.policy.WindowManagerPolicy.ScreenOffListener
        public void onScreenOff() {
            Slog.m72d(DisplayPowerController.this.mTag, "[api] WindowManagerPolicy.ScreenOffListener : called onScreenOff()");
            DisplayPowerController.this.mHandler.sendMessageAtTime(DisplayPowerController.this.mHandler.obtainMessage(4, this), DisplayPowerController.this.mClock.uptimeMillis());
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAutoBrightnessLoggingEnabled(boolean z) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.setLoggingEnabled(z);
        }
    }

    public String getSuspendBlockerUnfinishedBusinessId(int i) {
        return "[" + i + "]unfinished business";
    }

    public String getSuspendBlockerOnStateChangedId(int i) {
        return "[" + i + "]on state changed";
    }

    public String getSuspendBlockerProxPositiveId(int i) {
        return "[" + i + "]prox positive";
    }

    public String getSuspendBlockerProxNegativeId(int i) {
        return "[" + i + "]prox negative";
    }

    public String getSuspendBlockerProxDebounceId(int i) {
        return "[" + i + "]prox debounce";
    }

    public String getSuspendBlockerIdRefreshRate(int i) {
        return "[" + i + "]vrr ramp animation";
    }

    public String getSuspendBlockerIdEarlyWakeup(int i) {
        return "[" + i + "]early wakeup";
    }

    class Injector {
        public Clock getClock() {
            return new Clock() { // from class: com.android.server.display.DisplayPowerController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.DisplayPowerController.Clock
                public final long uptimeMillis() {
                    return SystemClock.uptimeMillis();
                }
            };
        }

        public DisplayPowerState getDisplayPowerState(DisplayBlanker displayBlanker, ColorFade colorFade, int i, int i2) {
            return new DisplayPowerState(displayBlanker, colorFade, i, i2);
        }

        public RampAnimator.DualRampAnimator getDualRampAnimator(DisplayPowerState displayPowerState, FloatProperty floatProperty, FloatProperty floatProperty2) {
            return new RampAnimator.DualRampAnimator(displayPowerState, floatProperty, floatProperty2);
        }

        public AutomaticBrightnessController getAutomaticBrightnessController(AutomaticBrightnessController.Callbacks callbacks, Looper looper, SensorManager sensorManager, Sensor sensor, BrightnessMappingStrategy brightnessMappingStrategy, int i, float f, float f2, float f3, int i2, int i3, long j, long j2, boolean z, HysteresisLevels hysteresisLevels, HysteresisLevels hysteresisLevels2, HysteresisLevels hysteresisLevels3, HysteresisLevels hysteresisLevels4, Context context, HighBrightnessModeController highBrightnessModeController, BrightnessThrottler brightnessThrottler, BrightnessMappingStrategy brightnessMappingStrategy2, int i4, int i5, float f4, float f5, HysteresisLevels hysteresisLevels5, HysteresisLevels hysteresisLevels6) {
            return new AutomaticBrightnessController(callbacks, looper, sensorManager, sensor, brightnessMappingStrategy, i, f, f2, f3, i2, i3, j, j2, z, hysteresisLevels, hysteresisLevels2, hysteresisLevels3, hysteresisLevels4, context, highBrightnessModeController, brightnessThrottler, brightnessMappingStrategy2, i4, i5, f4, f5, hysteresisLevels5, hysteresisLevels6);
        }

        public BrightnessMappingStrategy getInteractiveModeBrightnessMapper(Resources resources, DisplayDeviceConfig displayDeviceConfig, DisplayWhiteBalanceController displayWhiteBalanceController) {
            return BrightnessMappingStrategy.create(resources, displayDeviceConfig, displayWhiteBalanceController);
        }

        public ScreenOffBrightnessSensorController getScreenOffBrightnessSensorController(SensorManager sensorManager, Sensor sensor, Handler handler, ScreenOffBrightnessSensorController.Clock clock, int[] iArr, BrightnessMappingStrategy brightnessMappingStrategy) {
            return new ScreenOffBrightnessSensorController(sensorManager, sensor, handler, clock, iArr, brightnessMappingStrategy);
        }

        public AdaptiveBrightnessLongtermModelBuilder getAdaptiveBrightnessLongtermModelBuilder(Context context, AdaptiveBrightnessLongtermModelBuilder.Injector injector, BrightnessMappingStrategy brightnessMappingStrategy) {
            return new AdaptiveBrightnessLongtermModelBuilder(context, injector, brightnessMappingStrategy);
        }

        public HqmDataDispatcher getHqmDataDispatcher() {
            return HqmDataDispatcher.getInstance();
        }
    }

    public class CachedBrightnessInfo {
        public MutableFloat brightness = new MutableFloat(Float.NaN);
        public MutableFloat adjustedBrightness = new MutableFloat(Float.NaN);
        public MutableFloat brightnessMin = new MutableFloat(Float.NaN);
        public MutableFloat brightnessMax = new MutableFloat(Float.NaN);
        public MutableInt hbmMode = new MutableInt(0);
        public MutableFloat hbmTransitionPoint = new MutableFloat(Float.POSITIVE_INFINITY);
        public MutableInt brightnessMaxReason = new MutableInt(0);

        public boolean checkAndSetFloat(MutableFloat mutableFloat, float f) {
            if (mutableFloat.value == f) {
                return false;
            }
            mutableFloat.value = f;
            return true;
        }

        public boolean checkAndSetInt(MutableInt mutableInt, int i) {
            if (mutableInt.value == i) {
                return false;
            }
            mutableInt.value = i;
            return true;
        }
    }

    public final class ShutdownReceiver extends BroadcastReceiver {
        public ShutdownReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (DisplayPowerController.this.mInteractiveModeBrightnessMapper == null || !"android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction()) || !DisplayPowerController.this.mAppliedForceDimming || DisplayPowerController.this.mUseAutoBrightness || Float.isNaN(DisplayPowerController.this.mLastScreenBrightnessSettingBeforeForceDim)) {
                return;
            }
            Slog.m72d(DisplayPowerController.this.mTag, "Restore low battery force dim (manual brightness)");
            DisplayPowerController.this.updateLastBrightnessSettingChangedTime();
            DisplayPowerController.this.mAppliedForceDimming = false;
            DisplayPowerController.this.mBrightnessSetting.setBrightness(DisplayPowerController.this.mLastScreenBrightnessSettingBeforeForceDim);
        }
    }

    public final boolean isProximitySensorValidState() {
        if (this.mPowerRequest.coverClosed) {
            return false;
        }
        return !PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY || (this.mDisplayId == 0 && this.mDualScreenPolicy != 1) || (this.mIsCoverDisplay && !this.mIsProximitySensorOnFoldingSide && this.mDualScreenPolicy == 1);
    }

    public final boolean isLightSensorCovered() {
        return this.mPowerRequest.coverClosed;
    }

    public final float getScreenBrightnessSettingOnBootPhase() {
        float f;
        if (this.mDisplayId == 0) {
            f = BrightnessSynchronizer.brightnessIntToFloat(Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness", -1, -2));
            if (Float.isNaN(f)) {
                Slog.m72d(this.mTag, "getScreenBrightnessSettingOnBootPhase: default: " + this.mScreenBrightnessDefault);
                f = this.mScreenBrightnessDefault;
            }
        } else if (this.mIsCoverDisplay) {
            f = BrightnessSynchronizer.brightnessIntToFloat(Settings.System.getIntForUser(this.mContext.getContentResolver(), "sub_screen_brightness", this.mContext.getResources().getInteger(R.integer.config_triplePressOnStemPrimaryBehavior), -2));
            if (Float.isNaN(f)) {
                f = this.mScreenBrightnessDefault;
            }
        } else {
            f = this.mScreenBrightnessDefault;
        }
        this.mBrightnessSetting.setBrightness(f);
        return f;
    }

    public final int getTransitionTimeWithHbm(float f, float f2, float f3, float f4) {
        float abs;
        float f5;
        if (f > 1.0f && f2 <= 1.0f) {
            f5 = f - 1.0f;
            abs = 1.0f - f2;
        } else if (1.0f >= f2 || f2 >= f) {
            Slog.m74e(this.mTag, "getTransitionTimeWithHbm: rateAtHbm: " + f4 + " currentBrightness: " + f + " targetBrightness: " + f2);
            abs = Math.abs(f - f2);
            f5 = 0.0f;
        } else {
            f5 = f - f2;
            abs = 0.0f;
        }
        return ((int) Math.round((f5 / f4) * 1000.0d)) + ((int) Math.round((abs / f3) * 1000.0d));
    }

    public final void putAutoBrightnessTransitionTime(float f, float f2, float f3) {
        int i;
        if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            float currentValue = this.mScreenBrightnessRampAnimator.getCurrentValue();
            if (!Float.isNaN(f2)) {
                i = getTransitionTimeWithHbm(currentValue, f3, f, f2);
            } else {
                i = (int) Math.round((Math.abs(currentValue - f3) / f) * 1000.0d);
            }
        } else {
            i = 0;
        }
        Slog.m72d(this.mTag, "putAutoBrightnessTransitionTime: transitionTime=" + i);
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "auto_brightness_transition_time", i, -2);
    }

    public final float getFinalBrightness(float f, int i) {
        this.mPassRampAnimation = false;
        float f2 = this.mPowerRequest.screenBrightnessScaleFactor;
        float f3 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        if (f2 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f2 != 1.0f) {
            f = clampScreenBrightnessForFinal(f * f2);
            this.mBrightnessReasonTemp.addModifier(16, f);
        }
        if (this.mPowerRequest.hbmBlock && f > 1.0f) {
            this.mBrightnessReasonTemp.addModifier(256, 1.0f);
            f = 1.0f;
        }
        float f4 = this.mPowerRequest.minBrightness;
        if (f4 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f < f4) {
            this.mBrightnessReasonTemp.addModifier(32, f4);
            f = f4;
        }
        float f5 = this.mPowerRequest.maxBrightness;
        if (f5 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f > f5) {
            this.mBrightnessReasonTemp.addModifier(32, f5);
            f = f5;
        }
        int i2 = this.mPowerRequest.brightnessLimitByCover;
        if (i2 != -1 && f > i2) {
            f = i2;
            this.mBrightnessReasonTemp.addModifier(128, f);
        }
        if (this.mAppliedAutoBrightness) {
            float f6 = this.mPowerRequest.autoBrightnessUpperLimit;
            if (f6 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f > f6) {
                this.mBrightnessReasonTemp.addModifier(64, f6);
                f = f6;
            }
            float f7 = this.mPowerRequest.autoBrightnessLowerLimit;
            if (f7 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && !this.mAppliedDimming && !this.mAppliedForceDimming && f < f7) {
                this.mBrightnessReasonTemp.addModifier(64, f7);
                f = f7;
            }
        }
        if (isFakeAodAvailable() && Display.isDozeState(i) && f > 0.38f) {
            this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, 0.38f);
            f = 0.38f;
        }
        if (this.mPowerRequest.forceLcdBacklightOffEnabled) {
            this.mBrightnessReasonTemp.addModifier(1024, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } else {
            f3 = f;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mPowerRequest;
        if ((displayPowerRequest.isOutdoorMode || displayPowerRequest.lcdFlashMode) && displayPowerRequest.policy == 3) {
            f3 = Math.max(this.mScreenExtendedBrightnessRangeMaximum, 1.0f);
            this.mBrightnessReasonTemp.addModifier(512, f3);
            this.mIsOutdoorModeEnabled = true;
            boolean z = this.mPowerRequest.lcdFlashMode;
            this.mLcdFlashModeEnabled = z;
            if (z) {
                this.mPassRampAnimation = true;
            }
        } else if (this.mIsOutdoorModeEnabled && displayPowerRequest.policy != 0) {
            this.mIsOutdoorModeEnabled = false;
            if (this.mLcdFlashModeEnabled) {
                this.mLcdFlashModeEnabled = false;
                this.mPassRampAnimation = true;
            }
        }
        if (!PowerManagerUtil.SHIP_BUILD && this.mIsCoverDisplay && this.mCoverDisplayDemoEnabled && this.mPowerRequest.policy == 3) {
            f3 = Math.max(this.mScreenExtendedBrightnessRangeMaximum, 1.0f);
            this.mBrightnessReasonTemp.addModifier(16384, f3);
        }
        if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN && this.mPowerRequest.screenCurtainEnabled) {
            this.mPassRampAnimation = true;
            f3 = clampScreenBrightnessForFinal(Math.min(f3, BrightnessSynchronizer.brightnessIntToFloat(49)));
            this.mBrightnessReasonTemp.addModifier(32768, f3);
        }
        boolean z2 = this.mLastCoverClosedState;
        boolean z3 = this.mPowerRequest.coverClosed;
        if (z2 != z3) {
            this.mLastCoverClosedState = z3;
            if (!z3) {
                this.mPassRampAnimation = true;
            }
        }
        return f3;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public int convertToBrightness(float f) {
        BrightnessMappingStrategy brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
        if (brightnessMappingStrategy != null) {
            return brightnessMappingStrategy.convertToBrightness(f);
        }
        return -1;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getAdaptiveBrightness(float f) {
        BrightnessMappingStrategy brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
        if (brightnessMappingStrategy != null) {
            return brightnessMappingStrategy.getBrightness(f);
        }
        return -1.0f;
    }

    public final void updateLastBrightnessSettingChangedTime() {
        sLastScreenBrightnessSettingChangedTime = this.mClock.uptimeMillis();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public long getLastUserSetScreenBrightnessTime() {
        return sLastScreenBrightnessSettingChangedTime;
    }

    public final void postBrightnessModeChangeRunnable() {
        this.mHandler.post(this.mOnBrightnessModeChangeRunnable);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onScreenBrightnessSettingTimeChanged() {
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setActualDisplayState(int i) {
        synchronized (this.mLock) {
            if (i != this.mActualDisplayState) {
                Slog.m72d(this.mTag, "setActualDisplayState: " + Display.stateToString(this.mActualDisplayState) + " -> " + Display.stateToString(i));
                this.mActualDisplayState = i;
                sendUpdatePowerStateLocked();
            }
        }
    }

    public final boolean isFakeAodAvailable() {
        if (PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI) {
            return true;
        }
        return PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY && this.mDualScreenPolicy == 1;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public String getAmbientBrightnessInfo(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            return automaticBrightnessController.getAmbientBrightnessInfo(f);
        }
        return null;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void injectLux(SensorEvent sensorEvent) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.injectLux(sensorEvent);
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void doShortTermReset() {
        if (this.mAutomaticBrightnessController != null) {
            this.mPowerHistorian.onAutoBrightnessEvent("ShortTermModel: shell cmd");
            this.mAutomaticBrightnessController.resetShortTermModel();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTestModeEnabled(boolean z) {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            adaptiveBrightnessLongtermModelBuilder.setTestModeEnabled(z);
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void addBrightnessWeights(float f, float f2, float f3, float f4) {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            adaptiveBrightnessLongtermModelBuilder.addBrightnessWeightDirectly(f, f2, f3, f4);
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void restartAdaptiveBrightnessLongtermModelBuilderFromBnr() {
        this.mHandler.obtainMessage(18).sendToTarget();
    }

    public void restartAdaptiveBrightnessLongtermModelBuilderInternal(boolean z) {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder == null || !adaptiveBrightnessLongtermModelBuilder.isStarted()) {
            return;
        }
        this.mAdaptiveBrightnessLongtermModelBuilder.restartAdaptiveBrightnessStatsTracker(z);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void clearAdaptiveBrightnessLongtermModelBuilder() {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            adaptiveBrightnessLongtermModelBuilder.clearBrightnessEvents();
        }
    }

    public final boolean getCoverDisplayDemoSetting() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "cover_screen_demo_mode", 0, -2) > 0;
    }

    public final class SeamlessAodReadyListener implements AODManager.AODChangeListener {
        public SeamlessAodReadyListener() {
        }

        public void readyToScreenTurningOn() {
            Slog.m72d(DisplayPowerController.this.mTag, "[api] AODManager.AODChangeListener : Received readyToScreenTurningOn().");
            Message obtainMessage = DisplayPowerController.this.mHandler.obtainMessage(16);
            obtainMessage.setAsynchronous(true);
            DisplayPowerController.this.mHandler.sendMessage(obtainMessage);
        }
    }

    public final class EarlyWakeUpManager {
        public boolean mAppliedLocked;
        public boolean mEarlyDisplayEnabled;
        public boolean mEarlyLightSensorEnabled;
        public final Handler mHandler;
        public final HandlerThread mHandlerThread;
        public boolean mHoldingSuspendBlocker;
        public boolean mIsRequestInvalidated;
        public long mLastEnableRequestedTime;
        public final Object mEarlyWakeUpLock = new Object();
        public boolean mEarlyLightSensorReadyLocked = true;
        public boolean mEarlyDisplayReadyLocked = true;
        public final Runnable mEarlyLightSensorReadyListener = new Runnable() { // from class: com.android.server.display.DisplayPowerController.EarlyWakeUpManager.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (EarlyWakeUpManager.this.mEarlyWakeUpLock) {
                    EarlyWakeUpManager.this.mEarlyLightSensorReadyLocked = true;
                    EarlyWakeUpManager.this.updateSuspendBlockerLocked();
                }
            }
        };

        public EarlyWakeUpManager() {
            HandlerThread handlerThread = new HandlerThread(DisplayPowerController.this.mTag, -4);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            this.mHandler = new EarlyWakeUpHandler(handlerThread.getLooper());
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0034 A[Catch: all -> 0x006b, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000c, B:10:0x0015, B:12:0x0019, B:18:0x0028, B:20:0x0030, B:22:0x0034, B:24:0x0050, B:26:0x005f, B:27:0x0062, B:28:0x0057, B:29:0x0064, B:30:0x0069), top: B:3:0x0003 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void update(boolean z, int i) {
            boolean z2;
            synchronized (this.mEarlyWakeUpLock) {
                long uptimeMillis = SystemClock.uptimeMillis();
                if ((i == 2 || this.mIsRequestInvalidated) ? false : true) {
                    boolean z3 = this.mAppliedLocked && !isLastRequestExpired(uptimeMillis);
                    if (z || z3) {
                        if (z) {
                            this.mLastEnableRequestedTime = uptimeMillis;
                            resetEnableRequestTimeout();
                        }
                        z2 = true;
                        if (z2 != this.mAppliedLocked) {
                            Slog.m72d(DisplayPowerController.this.mTag, "[ew] " + z2);
                            if (z2) {
                                setEarlyLightSensorEnabledLocked(true);
                                setEarlyDisplayEnabledLocked(true, i);
                            } else {
                                setEarlyLightSensorEnabledLocked(false);
                                setEarlyDisplayEnabledLocked(false, i);
                            }
                            if (!z2) {
                                clearEnableRequestTimeout();
                            }
                            this.mAppliedLocked = z2;
                        }
                        this.mIsRequestInvalidated = false;
                        updateSuspendBlockerLocked();
                    }
                }
                z2 = false;
                if (z2 != this.mAppliedLocked) {
                }
                this.mIsRequestInvalidated = false;
                updateSuspendBlockerLocked();
            }
        }

        public boolean isEarlyLightSensorEnabled() {
            return this.mEarlyLightSensorEnabled;
        }

        public final void setEarlyLightSensorEnabledLocked(boolean z) {
            if (supportEarlyLightSensorEnableLocked()) {
                if ((!z || isCandidateForAutoBrightness()) && z != this.mEarlyLightSensorEnabled) {
                    this.mEarlyLightSensorEnabled = z;
                    this.mEarlyLightSensorReadyLocked = false;
                    DisplayPowerController.this.mAutomaticBrightnessController.setEarlyLightSensorEnabled(z, this.mEarlyLightSensorReadyListener);
                }
            }
        }

        public final void setEarlyDisplayEnabledLocked(final boolean z, int i) {
            if (z == this.mEarlyDisplayEnabled) {
                return;
            }
            if (!z || supportEarlyDisplayEnableLocked(i)) {
                this.mEarlyDisplayEnabled = z;
                this.mEarlyDisplayReadyLocked = false;
                this.mHandler.post(new Runnable() { // from class: com.android.server.display.DisplayPowerController.EarlyWakeUpManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        DisplayPowerController.this.mBlanker.setDisplayStateLimitForEarlyWakeUp(DisplayPowerController.this.mDisplayId, z ? 2 : 0);
                        synchronized (EarlyWakeUpManager.this.mEarlyWakeUpLock) {
                            EarlyWakeUpManager.this.mEarlyDisplayReadyLocked = true;
                            EarlyWakeUpManager.this.updateSuspendBlockerLocked();
                        }
                    }
                });
            }
        }

        public final boolean supportEarlyLightSensorEnableLocked() {
            return DisplayPowerController.this.mAutomaticBrightnessController != null;
        }

        public final boolean supportEarlyDisplayEnableLocked(int i) {
            return (Display.isDozeState(i) || PowerManagerUtil.SECURITY_FINGERPRINT_IN_DISPLAY || PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD_LIVE_CLOCK) ? false : true;
        }

        public final boolean isCandidateForAutoBrightness() {
            return DisplayPowerController.this.mUseAutoBrightness && DisplayPowerController.this.mProximity != 1;
        }

        public final boolean isLastRequestExpired(long j) {
            return j >= this.mLastEnableRequestedTime + 3000;
        }

        public final void clearEnableRequestTimeout() {
            Slog.m72d(DisplayPowerController.this.mTag, "[ew] clearEnableRequestTimeout -");
            this.mHandler.removeMessages(1);
        }

        public final void resetEnableRequestTimeout() {
            Slog.m72d(DisplayPowerController.this.mTag, "[ew] resetEnableRequestTimeout +");
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageAtTime(1, this.mLastEnableRequestedTime + 3000);
        }

        public final void updateSuspendBlockerLocked() {
            if ((!this.mAppliedLocked && this.mEarlyDisplayReadyLocked && this.mEarlyLightSensorReadyLocked) ? false : true) {
                if (this.mHoldingSuspendBlocker) {
                    return;
                }
                Slog.m72d(DisplayPowerController.this.mTag, "[ew] acquireSuspendBlocker: +");
                DisplayPowerController.this.mCallbacks.acquireSuspendBlocker(DisplayPowerController.this.mSuspendBlockerIdEarlyWakeup);
                this.mHoldingSuspendBlocker = true;
                return;
            }
            if (this.mHoldingSuspendBlocker) {
                Slog.m72d(DisplayPowerController.this.mTag, "[ew] releaseSuspendBlocker: -");
                this.mHoldingSuspendBlocker = false;
                DisplayPowerController.this.mCallbacks.releaseSuspendBlocker(DisplayPowerController.this.mSuspendBlockerIdEarlyWakeup);
            }
        }

        public void invalidateCurrentRequest() {
            this.mIsRequestInvalidated = true;
        }

        public void dump(PrintWriter printWriter) {
            synchronized (this.mEarlyWakeUpLock) {
                printWriter.println();
                printWriter.println("[ew] EarlyWakeUpManager:");
                printWriter.println("  mAppliedLocked=" + this.mAppliedLocked);
                printWriter.println("  mEarlyLightSensorEnabled=" + this.mEarlyLightSensorEnabled);
                printWriter.println("  mEarlyDisplayEnabled=" + this.mEarlyDisplayEnabled);
                printWriter.println("  mLastEnableRequestedTime=" + this.mLastEnableRequestedTime);
                printWriter.println("  now=" + SystemClock.uptimeMillis());
                printWriter.println("  mHoldingSuspendBlocker=" + this.mHoldingSuspendBlocker);
                printWriter.println("    mEarlyLightSensorReadyLocked=" + this.mEarlyLightSensorReadyLocked);
                printWriter.println("    mEarlyDisplayReadyLocked=" + this.mEarlyDisplayReadyLocked);
            }
        }

        public final class EarlyWakeUpHandler extends Handler {
            public EarlyWakeUpHandler(Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                Slog.m72d(DisplayPowerController.this.mTag, "[ew] MSG_EARLY_WAKEUP_TIMEOUT");
                DisplayPowerController.this.sendUpdatePowerState();
            }
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getLastAutomaticScreenBrightness() {
        return this.mLastAutomaticScreenBrightness;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getCurrentScreenBrightness() {
        return this.mCurrentScreenBrightnessSetting;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public int[] getBrightnessLearningMaxLimitCount() {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            return adaptiveBrightnessLongtermModelBuilder.getBrightnessLearningMaxLimitCount();
        }
        return null;
    }

    @Override // com.android.server.display.AutomaticBrightnessController.Callbacks
    public void onUserPointAdded(float f, float f2) {
        HqmDataDispatcher hqmDataDispatcher = this.mHqmDataDispatcher;
        if (hqmDataDispatcher != null) {
            hqmDataDispatcher.sendBrightnessAdjustmentEventAsync(f, this.mPrevScreenBrightness, f2);
        }
    }

    @Override // com.android.server.display.AutomaticBrightnessController.Callbacks
    public void onShortTermReset() {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            adaptiveBrightnessLongtermModelBuilder.notifyShortTermResetValid();
        } else {
            sendUpdatePowerState();
        }
    }

    @Override // com.android.server.display.AutomaticBrightnessController.Callbacks
    public void onAmbientLuxChanged(float f) {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder;
        if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL || (adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder) == null) {
            return;
        }
        adaptiveBrightnessLongtermModelBuilder.notifyAmbientLuxChanged(f);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setRampSpeedToFollower(float f, float f2) {
        this.mFollowerRampSpeed = f;
        this.mFollowerRampSpeedAtHbm = f2;
    }

    public final BrightnessDynamicRampRatePair getBrightnessDynamicRampRatePair(float f, float f2) {
        float f3;
        if (f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            f = 0.004f;
        }
        float currentValue = this.mScreenBrightnessRampAnimator.getCurrentValue();
        if (currentValue <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            currentValue = 0.004f;
        }
        float f4 = this.mLastAmbientLux;
        if (f2 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            f2 = 0.9f;
        }
        if (f4 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            f4 = 0.9f;
        }
        int i = SystemProperties.getInt("sys.display.transition.weight", DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION);
        int i2 = SystemProperties.getInt("sys.display.transition.weight.hbm", 15);
        if (f < currentValue) {
            float dynamicRampRate = getDynamicRampRate(i, f, currentValue, f4, f2, MAX_AUTO_BRIGHTNESS_TRANSITION_TIME);
            f3 = currentValue > 1.0f ? getDynamicRampRate(i2, f, currentValue, f4, f2, 30000) : Float.NaN;
            r10 = dynamicRampRate;
        } else {
            f3 = Float.NaN;
        }
        return new BrightnessDynamicRampRatePair(r10, f3);
    }

    public final float getDynamicRampRate(int i, float f, float f2, float f3, float f4, int i2) {
        double min = Math.min(calculateTransitionTime(i, f, f2, f3, f4), i2);
        if (min > 0.0d) {
            return ((float) (1.0d / min)) * 1000.0f;
        }
        return Float.NaN;
    }

    public final double calculateTransitionTime(int i, float f, float f2, float f3, float f4) {
        return (i / Math.log10((f2 / f) * (f3 / f4))) * 1000.0d;
    }

    public class BrightnessDynamicRampRatePair {
        public float brightnessRampRateDynamic;
        public float brightnessRampRateDynamicAtHbm;

        public BrightnessDynamicRampRatePair(float f, float f2) {
            this.brightnessRampRateDynamic = f;
            this.brightnessRampRateDynamicAtHbm = f2;
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setHdrRampRate(float f, float f2) {
        this.mMoreFastRampRate = f;
    }
}
