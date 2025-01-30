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
import android.hardware.SensorManager;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManagerInternal;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerExecutor;
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
import android.util.MutableBoolean;
import android.util.MutableFloat;
import android.util.MutableInt;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.app.IBatteryStats;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
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
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.DisplayBrightnessController;
import com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.display.state.DisplayStateController;
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
public final class DisplayPowerController2 implements AutomaticBrightnessController.Callbacks, DisplayPowerControllerInterface {
    public static final float[] BRIGHTNESS_RANGE_BOUNDARIES;
    public static final int DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION;
    public static final int MAX_AUTO_BRIGHTNESS_TRANSITION_TIME;
    public static final float RATE_FROM_DOZE_TO_ON;
    public static boolean SAMSUNG_UX_COLOR_FADE_OFF_EFFECT_ENABLED = true;
    public final AdaptiveBrightnessLongtermModelBuilder mAdaptiveBrightnessLongtermModelBuilder;
    public AODManagerInternal mAodManagerInternal;
    public boolean mAppliedDimming;
    public boolean mAppliedForceDimming;
    public boolean mAppliedLowPower;
    public boolean mAppliedThrottling;
    public boolean mAutoBrightnessEnabled;
    public AutomaticBrightnessController mAutomaticBrightnessController;
    public final AutomaticBrightnessStrategy mAutomaticBrightnessStrategy;
    public boolean mAwakenFromDozingInAutoBrightness;
    public boolean mBatteryLevelCritical;
    public final IBatteryStats mBatteryStats;
    public final DisplayBlanker mBlanker;
    public boolean mBootCompleted;
    public boolean mBrightnessAnimationConsumerInvoked;
    public final boolean mBrightnessBucketsInDozeConfig;
    public boolean mBrightnessChangedByUser;
    public RingBuffer mBrightnessEventRingBuffer;
    public long mBrightnessRampDecreaseMaxTimeMillis;
    public long mBrightnessRampIncreaseMaxTimeMillis;
    public float mBrightnessRampRateFastDecrease;
    public float mBrightnessRampRateFastIncrease;
    public float mBrightnessRampRateHdrDecrease;
    public float mBrightnessRampRateHdrIncrease;
    public float mBrightnessRampRateSlowDecrease;
    public float mBrightnessRampRateSlowIncrease;
    public final BrightnessThrottler mBrightnessThrottler;
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
    public final boolean mDisplayBlanksAfterDozeConfig;
    public final DisplayBrightnessController mDisplayBrightnessController;
    public DisplayDevice mDisplayDevice;
    public DisplayDeviceConfig mDisplayDeviceConfig;
    public final int mDisplayId;
    public final DisplayPowerProximityStateController mDisplayPowerProximityStateController;
    public boolean mDisplayReadyLocked;
    public final DisplayStateController mDisplayStateController;
    public int mDisplayStatsId;
    public boolean mDozing;
    public EarlyWakeUpManager mEarlyWakeUpManager;
    public final boolean mEarlyWakeupEnabled;
    public float mFollowerRampSpeed;
    public float mFollowerRampSpeedAtHbm;
    public boolean mForceSlowChange;
    public boolean mFreezeBrightnessMode;
    public int mFreezeBrightnessModeSelector;
    public final DisplayControllerHandler mHandler;
    public final HighBrightnessModeController mHbmController;
    public final HighBrightnessModeMetadata mHighBrightnessModeMetadata;
    public HqmDataDispatcher mHqmDataDispatcher;
    public BrightnessMappingStrategy mIdleModeBrightnessMapper;
    public float mInitialAutoBrightness;
    public boolean mInitialAutoBrightnessUpdated;
    public final Injector mInjector;
    public BrightnessMappingStrategy mInteractiveModeBrightnessMapper;
    public final boolean mIsCoverDisplay;
    public boolean mIsDisplayInternal;
    public boolean mIsEnabled;
    public boolean mIsInTransition;
    public boolean mIsRbcActive;
    public boolean mIsSupportedAodMode;
    public long mLastBatteryLevelCriticalTime;
    public long mLastBrightnessConfigurationTime;
    public final BrightnessEvent mLastBrightnessEvent;
    public boolean mLastCoverClosedState;
    public float mLastScreenBrightnessSettingBeforeForceDim;
    public Sensor mLightSensor;
    public final LogicalDisplay mLogicalDisplay;
    public final float mMoreFastRampRate;
    public boolean mNeedPrepareColorFade;
    public float[] mNitsRange;
    public Consumer mOnBrightnessAnimationConsumer;
    public final Runnable mOnBrightnessChangeRunnable;
    public final Runnable mOnBrightnessModeChangeRunnable;
    public boolean mPendingEarlyWakeUpRequestLocked;
    public boolean mPendingForceSlowChangeLocked;
    public boolean mPendingForceUpdateAb;
    public boolean mPendingRequestChangedLocked;
    public DisplayManagerInternal.DisplayPowerRequest mPendingRequestLocked;
    public boolean mPendingScreenOff;
    public ScreenOffUnblocker mPendingScreenOffUnblocker;
    public ScreenOnUnblocker mPendingScreenOnUnblocker;
    public boolean mPendingTransitionOffInDualCase;
    public boolean mPendingUpdatePowerStateLocked;
    public DisplayManagerInternal.DisplayPowerRequest mPowerRequest;
    public DisplayPowerState mPowerState;
    public boolean mResetBrightnessConfiguration;
    public final float mScreenBrightnessDimConfig;
    public final float mScreenBrightnessDozeConfig;
    public final float mScreenBrightnessMinimumDimAmount;
    public String mScreenBrightnessModeSettingName;
    public RampAnimator.DualRampAnimator mScreenBrightnessRampAnimator;
    public final float mScreenExtendedBrightnessRangeMaximum;
    public long mScreenOffBlockStartRealTime;
    public Sensor mScreenOffBrightnessSensor;
    public ScreenOffBrightnessSensorController mScreenOffBrightnessSensorController;
    public long mScreenOnBlockStartRealTime;
    public SeamlessAodReadyListener mSeamlessAodReadyListener;
    public final SensorManager mSensorManager;
    public final SettingsObserver mSettingsObserver;
    public ShutdownReceiver mShutdownReceiver;
    public final boolean mSkipScreenOnBrightnessRamp;
    public boolean mStopped;
    public final String mTag;
    public final BrightnessEvent mTempBrightnessEvent;
    public String mThermalBrightnessThrottlingDataId;
    public String mUniqueDisplayId;
    public boolean mUseSoftwareAutoBrightnessConfig;
    public boolean mWaitingAutoBrightnessFromDoze;
    public final WakelockController mWakelockController;
    public final WindowManagerPolicy mWindowManagerPolicy;
    public static final PathInterpolator COLOR_FADE_PATH_INTERPOLATOR = new PathInterpolator(0.45f, 0.18f, 0.35f, 1.0f);
    public static final AccelerateDecelerateInterpolator COLOR_FADE_DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    public static final int[] BRIGHTNESS_RANGE_INDEX = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
    public static long sLastScreenBrightnessSettingChangedTime = -1;
    public final Object mLock = new Object();
    public int mLeadDisplayId = -1;
    public final CachedBrightnessInfo mCachedBrightnessInfo = new CachedBrightnessInfo();
    public int mReportedScreenStateToPolicy = -1;
    public final BrightnessReason mBrightnessReason = new BrightnessReason();
    public final BrightnessReason mBrightnessReasonTemp = new BrightnessReason();
    public float mLastStatsBrightness = RATE_FROM_DOZE_TO_ON;
    public final RingBuffer mRbcEventRingBuffer = new RingBuffer(BrightnessEvent.class, 20);
    public int mSkipRampState = 0;
    public SparseArray mDisplayBrightnessFollowers = new SparseArray();
    public int mDualScreenPolicy = -1;
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
    public final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() { // from class: com.android.server.display.DisplayPowerController2.4
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            PowerManagerUtil.sCurrentScreenOffProfiler.noteCfAnimationStart();
            Slog.m72d(DisplayPowerController2.this.mTag, "ColorFade: onAnimationStart");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PowerManagerUtil.sCurrentScreenOffProfiler.noteCfAnimationEnd();
            Slog.m72d(DisplayPowerController2.this.mTag, "ColorFade: onAnimationEnd");
            DisplayPowerController2.this.sendUpdatePowerState();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
            Slog.m72d(DisplayPowerController2.this.mTag, "ColorFade: onAnimationRepeat");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            Slog.m72d(DisplayPowerController2.this.mTag, "ColorFade: onAnimationCancel");
        }
    };
    public Runnable mBrightnessAnimationEndRunnable = new Runnable() { // from class: com.android.server.display.DisplayPowerController2.5
        @Override // java.lang.Runnable
        public void run() {
            if (!DisplayPowerController2.this.mBrightnessAnimationConsumerInvoked || DisplayPowerController2.this.mScreenBrightnessRampAnimator.isAnimating()) {
                return;
            }
            DisplayPowerController2.this.mBrightnessAnimationConsumerInvoked = false;
            DisplayPowerController2.this.mOnBrightnessAnimationConsumer.accept(Boolean.FALSE);
            DisplayPowerController2.this.mWakelockController.releaseWakelock(6);
        }
    };
    public final RampAnimator.Listener mRampAnimatorListener = new RampAnimator.Listener() { // from class: com.android.server.display.DisplayPowerController2.6
        @Override // com.android.server.display.RampAnimator.Listener
        public void onAnimationEnd() {
            if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN) {
                DisplayPowerController2.this.mHandler.removeCallbacks(DisplayPowerController2.this.mBrightnessAnimationEndRunnable);
                DisplayPowerController2.this.mHandler.postDelayed(DisplayPowerController2.this.mBrightnessAnimationEndRunnable, 200L);
            }
            DisplayPowerController2.this.sendUpdatePowerState();
            DisplayPowerController2.this.mHandler.sendMessageAtTime(DisplayPowerController2.this.mHandler.obtainMessage(10), DisplayPowerController2.this.mClock.uptimeMillis());
        }
    };
    public final Runnable mCleanListener = new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda4
        @Override // java.lang.Runnable
        public final void run() {
            DisplayPowerController2.this.sendUpdatePowerState();
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

    static {
        BRIGHTNESS_RANGE_BOUNDARIES = new float[]{RATE_FROM_DOZE_TO_ON, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 60.0f, 70.0f, 80.0f, 90.0f, 100.0f, 200.0f, 300.0f, 400.0f, 500.0f, 600.0f, 700.0f, 800.0f, 900.0f, 1000.0f, 1200.0f, 1400.0f, 1600.0f, 1800.0f, 2000.0f, 2250.0f, 2500.0f, 2750.0f, 3000.0f};
        RATE_FROM_DOZE_TO_ON = PowerManagerUtil.SEC_FEATURE_FULLSCREEN_AOD ? 0.03f : RATE_FROM_DOZE_TO_ON;
        DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION = 180;
        MAX_AUTO_BRIGHTNESS_TRANSITION_TIME = 60000;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DisplayPowerController2(Context context, Injector injector, DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks, Handler handler, SensorManager sensorManager, DisplayBlanker displayBlanker, LogicalDisplay logicalDisplay, BrightnessTracker brightnessTracker, BrightnessSetting brightnessSetting, Runnable runnable, HighBrightnessModeMetadata highBrightnessModeMetadata, boolean z, Runnable runnable2, Consumer consumer) {
        BrightnessMappingStrategy brightnessMappingStrategy;
        this.mScreenBrightnessModeSettingName = "screen_brightness_mode";
        Injector injector2 = injector != null ? injector : new Injector();
        this.mInjector = injector2;
        this.mClock = injector2.getClock();
        this.mLogicalDisplay = logicalDisplay;
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        this.mDisplayId = displayIdLocked;
        this.mSensorManager = sensorManager;
        DisplayControllerHandler displayControllerHandler = new DisplayControllerHandler(handler.getLooper());
        this.mHandler = displayControllerHandler;
        this.mDisplayDeviceConfig = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig();
        this.mIsEnabled = logicalDisplay.isEnabledLocked();
        this.mIsInTransition = logicalDisplay.isInTransitionLocked();
        this.mIsDisplayInternal = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked().type == 1;
        WakelockController wakelockController = injector2.getWakelockController(displayIdLocked, displayPowerCallbacks);
        this.mWakelockController = wakelockController;
        DisplayPowerProximityStateController displayPowerProximityStateController = injector2.getDisplayPowerProximityStateController(wakelockController, this.mDisplayDeviceConfig, displayControllerHandler.getLooper(), new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$new$0();
            }
        }, displayIdLocked, sensorManager);
        this.mDisplayPowerProximityStateController = displayPowerProximityStateController;
        this.mHighBrightnessModeMetadata = highBrightnessModeMetadata;
        this.mDisplayStateController = new DisplayStateController(displayPowerProximityStateController, displayIdLocked);
        AutomaticBrightnessStrategy automaticBrightnessStrategy = new AutomaticBrightnessStrategy(context, displayIdLocked);
        this.mAutomaticBrightnessStrategy = automaticBrightnessStrategy;
        String str = "DisplayPowerController2[" + displayIdLocked + "]";
        this.mTag = str;
        this.mThermalBrightnessThrottlingDataId = logicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        this.mDisplayDevice = logicalDisplay.getPrimaryDisplayDeviceLocked();
        String uniqueId = logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId();
        this.mUniqueDisplayId = uniqueId;
        this.mDisplayStatsId = uniqueId.hashCode();
        this.mLastBrightnessEvent = new BrightnessEvent(displayIdLocked);
        this.mTempBrightnessEvent = new BrightnessEvent(displayIdLocked);
        Object[] objArr = 0;
        if (displayIdLocked == 0 || (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && displayIdLocked == 1)) {
            this.mBatteryStats = BatteryStatsService.getService();
        } else {
            this.mBatteryStats = null;
        }
        this.mSettingsObserver = new SettingsObserver(displayControllerHandler);
        this.mCallbacks = displayPowerCallbacks;
        this.mWindowManagerPolicy = (WindowManagerPolicy) LocalServices.getService(WindowManagerPolicy.class);
        this.mAodManagerInternal = (AODManagerInternal) LocalServices.getService(AODManagerInternal.class);
        this.mBlanker = displayBlanker;
        this.mContext = context;
        this.mOnBrightnessChangeRunnable = runnable;
        if (displayIdLocked == 0 && consumer != null) {
            this.mOnBrightnessAnimationConsumer = consumer;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Resources resources = context.getResources();
        this.mScreenBrightnessDozeConfig = BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(4));
        this.mScreenBrightnessDimConfig = BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(3));
        this.mScreenBrightnessMinimumDimAmount = resources.getFloat(R.dimen.config_minScrollbarTouchTarget);
        float f = BrightnessUtils.sScreenExtendedBrightnessRangeMaximum;
        this.mScreenExtendedBrightnessRangeMaximum = f;
        boolean z2 = PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && displayIdLocked == 1;
        this.mIsCoverDisplay = z2;
        if (z2) {
            this.mScreenBrightnessModeSettingName = "sub_screen_brightness_mode";
        }
        loadBrightnessRampRates();
        this.mMoreFastRampRate = f;
        this.mBrightnessRampRateHdrIncrease = 0.3f;
        this.mBrightnessRampRateHdrDecrease = 0.9f;
        this.mSkipScreenOnBrightnessRamp = false;
        this.mHbmController = createHbmControllerLocked();
        this.mBrightnessThrottler = createBrightnessThrottlerLocked();
        Injector injector3 = injector2;
        this.mDisplayBrightnessController = new DisplayBrightnessController(context, null, displayIdLocked, logicalDisplay.getDisplayInfoLocked().brightnessDefault, brightnessSetting, new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$new$1();
            }
        }, new HandlerExecutor(displayControllerHandler));
        saveBrightnessInfo(getScreenBrightnessSetting());
        loadNitsRange(resources);
        this.mCdsi = null;
        setUpAutoBrightness(resources, handler);
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL && (brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper) != null && displayIdLocked == 0) {
            this.mBrightnessTracker = null;
            this.mAdaptiveBrightnessLongtermModelBuilder = injector3.getAdaptiveBrightnessLongtermModelBuilder(context, null, brightnessMappingStrategy);
        } else {
            this.mBrightnessTracker = brightnessTracker;
            this.mAdaptiveBrightnessLongtermModelBuilder = null;
        }
        this.mColorFadeEnabled = !ActivityManager.isLowRamDeviceStatic();
        this.mColorFadeFadesConfig = resources.getBoolean(R.bool.config_am_disablePssProfiling);
        this.mDisplayBlanksAfterDozeConfig = resources.getBoolean(R.bool.config_defaultAdasGnssLocationEnabled);
        this.mBrightnessBucketsInDozeConfig = resources.getBoolean(R.bool.config_defaultBatteryPercentageSetting);
        this.mBootCompleted = z;
        if (this.mIsDisplayInternal) {
            this.mShutdownReceiver = new ShutdownReceiver();
            displayControllerHandler.post(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayPowerController2.this.lambda$new$2();
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
        automaticBrightnessStrategy.setUseAutoBrightness(Settings.System.getIntForUser(context.getContentResolver(), this.mScreenBrightnessModeSettingName, 0, -2) == 1);
        Slog.m72d(str, "Create new DPC2 instance, mDisplayId=" + displayIdLocked + " AutomaticBrightnessController=" + this.mAutomaticBrightnessController + " mInteractiveModeBrightnessMapper=" + this.mInteractiveModeBrightnessMapper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(this.mShutdownReceiver, intentFilter, null, this.mHandler);
    }

    public final void applyReduceBrightColorsSplineAdjustment() {
        this.mHandler.obtainMessage(9).sendToTarget();
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
        return this.mDisplayPowerProximityStateController.isProximitySensorAvailable();
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
        this.mHandler.sendMessage(this.mHandler.obtainMessage(12, Integer.valueOf(i)));
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x0041 A[Catch: all -> 0x0051, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0008, B:9:0x000a, B:11:0x0014, B:13:0x0029, B:15:0x002d, B:17:0x0031, B:18:0x0034, B:20:0x0038, B:22:0x003c, B:24:0x0041, B:26:0x0048, B:27:0x004d, B:28:0x004f, B:30:0x001d, B:32:0x0023), top: B:3:0x0003 }] */
    @Override // com.android.server.display.DisplayPowerControllerInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestPowerState(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z) {
        synchronized (this.mLock) {
            if (this.mStopped) {
                return true;
            }
            boolean pendingWaitForNegativeProximityLocked = this.mDisplayPowerProximityStateController.setPendingWaitForNegativeProximityLocked(z);
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest2 = this.mPendingRequestLocked;
            if (displayPowerRequest2 == null) {
                this.mPendingRequestLocked = new DisplayManagerInternal.DisplayPowerRequest(displayPowerRequest);
            } else {
                if (!displayPowerRequest2.equals(displayPowerRequest)) {
                    this.mPendingRequestLocked.copyFrom(displayPowerRequest);
                }
                if (displayPowerRequest.forceSlowChange && !this.mPendingForceSlowChangeLocked) {
                    this.mPendingForceSlowChangeLocked = true;
                    pendingWaitForNegativeProximityLocked = true;
                }
                if (displayPowerRequest.earlyWakeUp && !this.mPendingEarlyWakeUpRequestLocked) {
                    this.mPendingEarlyWakeUpRequestLocked = true;
                    pendingWaitForNegativeProximityLocked = true;
                }
                if (pendingWaitForNegativeProximityLocked) {
                    this.mDisplayReadyLocked = false;
                    if (!this.mPendingRequestChangedLocked) {
                        this.mPendingRequestChangedLocked = true;
                        sendUpdatePowerStateLocked();
                    }
                }
                return this.mDisplayReadyLocked;
            }
            pendingWaitForNegativeProximityLocked = true;
            if (displayPowerRequest.forceSlowChange) {
                this.mPendingForceSlowChangeLocked = true;
                pendingWaitForNegativeProximityLocked = true;
            }
            if (displayPowerRequest.earlyWakeUp) {
                this.mPendingEarlyWakeUpRequestLocked = true;
                pendingWaitForNegativeProximityLocked = true;
            }
            if (pendingWaitForNegativeProximityLocked) {
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
            Slog.wtf(this.mTag, "Display Device is null in DisplayPowerController2 for display: " + this.mLogicalDisplay.getDisplayIdLocked());
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
        this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$onDisplayChanged$3(primaryDisplayDeviceLocked, uniqueId, displayDeviceConfig, str, displayTokenLocked, displayDeviceInfoLocked, highBrightnessModeMetadata, i2, i3, isEnabledLocked, isInTransitionLocked, z);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0066  */
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
            this.mDisplayPowerProximityStateController.notifyDisplayDeviceChanged(displayDeviceConfig);
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
            lambda$new$0();
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
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(7), this.mClock.uptimeMillis());
            AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
            if (automaticBrightnessController != null) {
                automaticBrightnessController.stop();
            }
            this.mDisplayBrightnessController.stop();
            this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        }
    }

    public final void loadFromDisplayDeviceConfig(IBinder iBinder, DisplayDeviceInfo displayDeviceInfo, HighBrightnessModeMetadata highBrightnessModeMetadata) {
        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && this.mIsDisplayInternal) {
            reloadReduceBrightColours();
            this.mHbmController.setHighBrightnessModeMetadata(highBrightnessModeMetadata);
            this.mHbmController.resetHbmData(displayDeviceInfo.width, displayDeviceInfo.height, iBinder, displayDeviceInfo.uniqueId, this.mDisplayDeviceConfig.getHighBrightnessModeData(), new HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController2.2
                @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
                public float getHdrBrightnessFromSdr(float f, float f2) {
                    return DisplayPowerController2.this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
                }
            });
            return;
        }
        loadBrightnessRampRates();
        loadNitsRange(this.mContext.getResources());
        setUpAutoBrightness(this.mContext.getResources(), this.mHandler);
        reloadReduceBrightColours();
        RampAnimator.DualRampAnimator dualRampAnimator = this.mScreenBrightnessRampAnimator;
        if (dualRampAnimator != null) {
            dualRampAnimator.setAnimationTimeLimits(this.mBrightnessRampIncreaseMaxTimeMillis, this.mBrightnessRampDecreaseMaxTimeMillis);
        }
        this.mHbmController.setHighBrightnessModeMetadata(highBrightnessModeMetadata);
        this.mHbmController.resetHbmData(displayDeviceInfo.width, displayDeviceInfo.height, iBinder, displayDeviceInfo.uniqueId, this.mDisplayDeviceConfig.getHighBrightnessModeData(), new HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController2.3
            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
            public float getHdrBrightnessFromSdr(float f, float f2) {
                return DisplayPowerController2.this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
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
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(displayPowerState, floatProperty, RATE_FROM_DOZE_TO_ON, 1.0f);
            this.mColorFadeOnAnimator = ofFloat;
            ofFloat.setDuration(160L);
            this.mColorFadeOnAnimator.addListener(this.mAnimatorListener);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mPowerState, floatProperty, 1.0f, RATE_FROM_DOZE_TO_ON);
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
        float convertToAdjustedNits = this.mDisplayBrightnessController.convertToAdjustedNits(this.mPowerState.getScreenBrightness());
        if (convertToAdjustedNits >= RATE_FROM_DOZE_TO_ON) {
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = this.mAdaptiveBrightnessLongtermModelBuilder;
                if (adaptiveBrightnessLongtermModelBuilder != null) {
                    adaptiveBrightnessLongtermModelBuilder.start(convertToAdjustedNits);
                }
            } else {
                this.mBrightnessTracker.start(convertToAdjustedNits);
            }
        }
        this.mDisplayBrightnessController.registerBrightnessSettingChangeListener(new BrightnessSetting.BrightnessSettingListener() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda8
            @Override // com.android.server.display.BrightnessSetting.BrightnessSettingListener
            public final void onBrightnessChanged(float f) {
                DisplayPowerController2.this.lambda$initialize$4(f);
            }
        });
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
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(8, Float.valueOf(f)), this.mClock.uptimeMillis());
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x018e  */
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
                if (f >= RATE_FROM_DOZE_TO_ON) {
                    float convertToFloatScale = this.mInteractiveModeBrightnessMapper.convertToFloatScale(f);
                    if (convertToFloatScale != Float.NaN) {
                        f3 = convertToFloatScale;
                        AutomaticBrightnessController automaticBrightnessController2 = this.mInjector.getAutomaticBrightnessController(this, handler.getLooper(), this.mSensorManager, this.mLightSensor, this.mInteractiveModeBrightnessMapper, integer, RATE_FROM_DOZE_TO_ON, 1.0f, fraction, integer2, 50, autoBrightnessBrighteningLightDebounce, autoBrightnessDarkeningLightDebounce, z2, hysteresisLevels3, null, hysteresisLevels4, null, this.mContext, this.mHbmController, this.mBrightnessThrottler, this.mIdleModeBrightnessMapper, this.mDisplayDeviceConfig.getAmbientHorizonShort(), this.mDisplayDeviceConfig.getAmbientHorizonLong(), f2, f3, hysteresisLevels, hysteresisLevels2);
                        this.mAutomaticBrightnessController = automaticBrightnessController2;
                        this.mDisplayBrightnessController.setAutomaticBrightnessController(automaticBrightnessController2);
                        this.mAutomaticBrightnessStrategy.setAutomaticBrightnessController(this.mAutomaticBrightnessController);
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
                AutomaticBrightnessController automaticBrightnessController22 = this.mInjector.getAutomaticBrightnessController(this, handler.getLooper(), this.mSensorManager, this.mLightSensor, this.mInteractiveModeBrightnessMapper, integer, RATE_FROM_DOZE_TO_ON, 1.0f, fraction, integer2, 50, autoBrightnessBrighteningLightDebounce, autoBrightnessDarkeningLightDebounce, z2, hysteresisLevels3, null, hysteresisLevels4, null, this.mContext, this.mHbmController, this.mBrightnessThrottler, this.mIdleModeBrightnessMapper, this.mDisplayDeviceConfig.getAmbientHorizonShort(), this.mDisplayDeviceConfig.getAmbientHorizonLong(), f2, f3, hysteresisLevels, hysteresisLevels2);
                this.mAutomaticBrightnessController = automaticBrightnessController22;
                this.mDisplayBrightnessController.setAutomaticBrightnessController(automaticBrightnessController22);
                this.mAutomaticBrightnessStrategy.setAutomaticBrightnessController(this.mAutomaticBrightnessController);
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
        this.mDisplayPowerProximityStateController.cleanup();
        this.mHbmController.stop();
        this.mBrightnessThrottler.stop();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mWakelockController.releaseAll();
        DisplayPowerState displayPowerState = this.mPowerState;
        reportStats(displayPowerState != null ? displayPowerState.getScreenBrightness() : RATE_FROM_DOZE_TO_ON);
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

    /* renamed from: updatePowerState, reason: merged with bridge method [inline-methods] */
    public final void lambda$new$0() {
        Trace.traceBegin(131072L, "DisplayPowerController#updatePowerState");
        updatePowerStateInternal();
        Trace.traceEnd(131072L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:228:0x040c, code lost:
    
        if (r43.mWaitingAutoBrightnessFromDoze == false) goto L257;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x040e, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:637:0x042e, code lost:
    
        if (r43.mWaitingAutoBrightnessFromDoze == false) goto L257;
     */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0494  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x04a3 A[LOOP:0: B:245:0x049d->B:247:0x04a3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:251:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x04d4  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0506  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x056c  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x057e  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0637  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x06d2  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x06d9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:363:0x06fc  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x070d  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0774  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x077d  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x0782  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x078c  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0795  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x07a7  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x07be A[LOOP:1: B:416:0x07b8->B:418:0x07be, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:422:0x07d4  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x07fa A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:429:0x080c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:437:0x0839 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0848 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:449:0x08f9  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x0904  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x093e  */
    /* JADX WARN: Removed duplicated region for block: B:460:0x095b  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x0968 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:466:0x098e  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x09a4  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x09b8  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x09bd  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x09c6  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x09d1  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x09f8  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x0a05  */
    /* JADX WARN: Removed duplicated region for block: B:506:0x0a1a  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0a27  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x0a37 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:519:0x0a3e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:531:0x0a50  */
    /* JADX WARN: Removed duplicated region for block: B:542:0x0a87  */
    /* JADX WARN: Removed duplicated region for block: B:545:0x0a94  */
    /* JADX WARN: Removed duplicated region for block: B:547:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:548:0x0a89  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x0a83  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x0991  */
    /* JADX WARN: Removed duplicated region for block: B:557:0x095d  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x0909  */
    /* JADX WARN: Removed duplicated region for block: B:566:0x0807  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x079b  */
    /* JADX WARN: Removed duplicated region for block: B:569:0x0789  */
    /* JADX WARN: Removed duplicated region for block: B:570:0x0779  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x06d4  */
    /* JADX WARN: Removed duplicated region for block: B:593:0x06a9  */
    /* JADX WARN: Removed duplicated region for block: B:596:0x060a  */
    /* JADX WARN: Removed duplicated region for block: B:608:0x0822  */
    /* JADX WARN: Removed duplicated region for block: B:613:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:615:0x04f7  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:620:0x0496  */
    /* JADX WARN: Removed duplicated region for block: B:622:0x0488  */
    /* JADX WARN: Removed duplicated region for block: B:634:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:636:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:641:0x03b1  */
    /* JADX WARN: Removed duplicated region for block: B:642:0x0363  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePowerStateInternal() {
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        int i2;
        boolean z4;
        boolean z5;
        float f;
        float f2;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        float f3;
        float f4;
        boolean z10;
        float f5;
        int i3;
        int i4;
        boolean z11;
        float f6;
        int i5;
        int i6;
        boolean z12;
        float f7;
        int i7;
        int i8;
        boolean saveBrightnessInfo;
        float f8;
        int i9;
        boolean z13;
        BrightnessMappingStrategy brightnessMappingStrategy;
        boolean z14;
        boolean z15;
        boolean z16;
        int i10;
        AutomaticBrightnessController automaticBrightnessController;
        BrightnessEvent brightnessEvent;
        RingBuffer ringBuffer;
        int i11;
        int i12;
        boolean z17;
        boolean z18;
        boolean z19;
        boolean z20;
        boolean z21;
        float f9;
        boolean z22;
        boolean z23;
        boolean z24;
        float f10;
        float f11;
        int i13;
        float f12;
        float f13;
        float f14;
        AutomaticBrightnessController automaticBrightnessController2;
        boolean z25;
        boolean z26;
        boolean z27;
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest;
        boolean z28;
        boolean z29;
        AutomaticBrightnessController automaticBrightnessController3;
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController;
        boolean z30;
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
                this.mDisplayPowerProximityStateController.updatePendingProximityRequestsLocked();
                this.mForceSlowChange = this.mPendingForceSlowChangeLocked;
                this.mPendingForceSlowChangeLocked = false;
                z = this.mPendingEarlyWakeUpRequestLocked;
                this.mPendingEarlyWakeUpRequestLocked = false;
                this.mPendingRequestChangedLocked = false;
                i = 3;
                z2 = true;
            } else if (this.mPendingRequestChangedLocked) {
                int i14 = displayPowerRequest3.policy;
                displayPowerRequest3.copyFrom(displayPowerRequest2);
                this.mDisplayPowerProximityStateController.updatePendingProximityRequestsLocked();
                this.mForceSlowChange |= this.mPendingForceSlowChangeLocked;
                this.mPendingForceSlowChangeLocked = false;
                z = this.mPendingEarlyWakeUpRequestLocked | false;
                this.mPendingEarlyWakeUpRequestLocked = false;
                this.mPendingRequestChangedLocked = false;
                this.mDisplayReadyLocked = false;
                i = i14;
                z2 = false;
            } else {
                i = displayPowerRequest3.policy;
                z = false;
                z2 = false;
            }
            boolean z31 = !this.mDisplayReadyLocked;
            SparseArray clone = this.mDisplayBrightnessFollowers.clone();
            int updateDisplayState = this.mDisplayStateController.updateDisplayState(this.mPowerRequest, this.mIsEnabled, this.mIsInTransition);
            ScreenOffBrightnessSensorController screenOffBrightnessSensorController2 = this.mScreenOffBrightnessSensorController;
            if (screenOffBrightnessSensorController2 != null) {
                screenOffBrightnessSensorController2.setLightSensorEnabled(this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness() && this.mIsEnabled && (updateDisplayState == 1 || (updateDisplayState == 3 && !this.mDisplayBrightnessController.isAllowAutoBrightnessWhileDozingConfig())) && this.mLeadDisplayId == -1);
            }
            if (z2) {
                initialize(readyToUpdateDisplayState() ? updateDisplayState : 0);
            }
            if (this.mEarlyWakeupEnabled && (earlyWakeUpManager = this.mEarlyWakeUpManager) != null) {
                earlyWakeUpManager.update(z, this.mActualDisplayState);
            }
            int i15 = this.mDualScreenPolicy;
            int i16 = this.mPowerRequest.dualScreenPolicy;
            if (i15 != i16) {
                this.mDualScreenPolicy = i16;
                this.mNeedPrepareColorFade = i16 == 0;
                this.mPendingTransitionOffInDualCase = i16 == 0 && i15 == -1;
                if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                    noteScreenState(updateDisplayState);
                }
            }
            if (this.mPendingTransitionOffInDualCase && this.mIsInTransition) {
                this.mDisplayStateController.setPerformScreenOffTransition(true);
                this.mPendingTransitionOffInDualCase = false;
            }
            int screenState = this.mPowerState.getScreenState();
            animateScreenStateChange(updateDisplayState, this.mDisplayStateController.shouldPerformScreenOffTransition());
            int screenState2 = this.mPowerState.getScreenState();
            boolean updateUserSetScreenBrightness = this.mDisplayBrightnessController.updateUserSetScreenBrightness();
            DisplayBrightnessState updateBrightness = this.mDisplayBrightnessController.updateBrightness(this.mPowerRequest, screenState2);
            float brightness = updateBrightness.getBrightness();
            float brightness2 = updateBrightness.getBrightness();
            this.mBrightnessReasonTemp.set(updateBrightness.getBrightnessReason());
            boolean z32 = screenState2 == 3 || screenState2 == 4;
            int i17 = this.mPowerRequest.policy;
            boolean z33 = i17 == 1;
            boolean z34 = (i == 1 && i17 == 3 && Display.isOnState(screenState)) || (Display.isDozeState(screenState) && Display.isOnState(screenState2));
            if (this.mIsSupportedAodMode && Float.isNaN(brightness) && ((z32 || this.mPendingScreenOnByAodReady || z33) && !PowerManagerUtil.isFakeAodAvailable(this.mDualScreenPolicy))) {
                brightness = this.mLastOriginalTarget;
                this.mBrightnessReasonTemp.setReason(11, brightness);
                brightness2 = brightness;
            }
            if (this.mResetBrightnessConfiguration) {
                if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                    clearAdaptiveBrightnessLongtermModelBuilder();
                    restartAdaptiveBrightnessLongtermModelBuilderInternal(false);
                    onShortTermReset();
                }
                boolean z35 = this.mResetBrightnessConfiguration;
                this.mResetBrightnessConfiguration = false;
                z3 = z35;
            } else {
                z3 = false;
            }
            boolean isShortTermModelActive = this.mAutomaticBrightnessStrategy.isShortTermModelActive();
            this.mAutomaticBrightnessStrategy.setAutoBrightnessState(screenState2, this.mDisplayBrightnessController.isAllowAutoBrightnessWhileDozingConfig(), brightness, this.mBrightnessReasonTemp.getReason(), this.mPowerRequest.policy, this.mDisplayBrightnessController.getLastUserSetScreenBrightness(), updateUserSetScreenBrightness, isLightSensorCovered(), z3, this.mDualScreenPolicy);
            if (this.mAutoBrightnessEnabled != this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                this.mAutoBrightnessEnabled = this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled();
                if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() && (this.mDozing || i == 1)) {
                    this.mInitialAutoBrightnessUpdated = false;
                    if (Display.isDozeState(screenState) || Display.isOnState(screenState)) {
                        this.mWaitingAutoBrightnessFromDoze = true;
                    }
                }
            }
            boolean z36 = Float.isNaN(brightness) && (this.mAutomaticBrightnessStrategy.getAutoBrightnessAdjustmentChanged() || updateUserSetScreenBrightness);
            this.mHbmController.setAutoBrightnessEnabled((this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness() || (this.mEarlyWakeupEnabled && this.mEarlyWakeUpManager.isEarlyLightSensorEnabled())) ? 1 : 2);
            float currentBrightness = this.mDisplayBrightnessController.getCurrentBrightness();
            if (Float.isNaN(brightness)) {
                if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                    float automaticScreenBrightness = this.mAutomaticBrightnessStrategy.getAutomaticScreenBrightness(this.mTempBrightnessEvent);
                    if (BrightnessUtils.isValidBrightnessValue(automaticScreenBrightness) || automaticScreenBrightness == RATE_FROM_DOZE_TO_ON) {
                        brightness2 = this.mAutomaticBrightnessController.getRawAutomaticScreenBrightness();
                        brightness = clampScreenBrightness(automaticScreenBrightness);
                        float ambientLux = this.mAutomaticBrightnessController.getAmbientLux();
                        if (brightness != this.mLastAutomaticScreenBrightness) {
                            this.mLastAutomaticScreenBrightness = brightness;
                            z30 = true;
                        } else {
                            z30 = false;
                        }
                        z4 = this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness() && !this.mAutomaticBrightnessStrategy.getAutoBrightnessAdjustmentChanged() && z30;
                        if (z4 && (PowerManagerUtil.USE_PERSONAL_AUTO_BRIGHTNESS_V3 || PowerManagerUtil.USE_PERSONAL_AUTO_BRIGHTNESS_V4)) {
                            BrightnessDynamicRampRatePair brightnessDynamicRampRatePair = getBrightnessDynamicRampRatePair(brightness, ambientLux);
                            f = brightnessDynamicRampRatePair.brightnessRampRateDynamic;
                            f2 = brightnessDynamicRampRatePair.brightnessRampRateDynamicAtHbm;
                        } else {
                            f = Float.NaN;
                            f2 = Float.NaN;
                        }
                        i2 = this.mAutomaticBrightnessStrategy.getAutoBrightnessAdjustmentReasonsFlags();
                        z5 = currentBrightness != BrightnessUtils.clampAbsoluteBrightness(brightness);
                        this.mLastAmbientLux = ambientLux;
                        this.mAwakenFromDozingInAutoBrightness = false;
                        this.mBrightnessReasonTemp.setReason(4, brightness);
                        ScreenOffBrightnessSensorController screenOffBrightnessSensorController3 = this.mScreenOffBrightnessSensorController;
                        if (screenOffBrightnessSensorController3 != null) {
                            screenOffBrightnessSensorController3.setLightSensorEnabled(false);
                        }
                        if (Float.isNaN(brightness) && Display.isDozeState(screenState2) && !PowerManagerUtil.isFakeAodAvailable(this.mDualScreenPolicy)) {
                            brightness2 = this.mScreenBrightnessDozeConfig;
                            brightness = clampScreenBrightness(brightness2);
                            this.mBrightnessReasonTemp.setReason(3, brightness);
                        }
                        if (Float.isNaN(brightness) && this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() && (screenOffBrightnessSensorController = this.mScreenOffBrightnessSensorController) != null) {
                            brightness = screenOffBrightnessSensorController.getAutomaticScreenBrightness();
                            if (BrightnessUtils.isValidBrightnessValue(brightness)) {
                                brightness2 = brightness;
                            } else {
                                float clampScreenBrightness = clampScreenBrightness(brightness);
                                boolean z37 = this.mDisplayBrightnessController.getCurrentBrightness() != clampScreenBrightness;
                                this.mBrightnessReasonTemp.setReason(9, clampScreenBrightness);
                                z5 = z37;
                                brightness2 = brightness;
                                brightness = clampScreenBrightness;
                            }
                        }
                        if (Float.isNaN(brightness)) {
                            currentBrightness = brightness2;
                        } else {
                            brightness = clampScreenBrightness(currentBrightness);
                            if (brightness != currentBrightness) {
                                z5 = true;
                            }
                            this.mBrightnessReasonTemp.setReason(1, brightness);
                            if (this.mAppliedForceDimming && !Float.isNaN(this.mLastScreenBrightnessSettingBeforeForceDim)) {
                                brightness = this.mLastScreenBrightnessSettingBeforeForceDim;
                                this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_FORCE, brightness);
                            }
                        }
                        z6 = this.mFreezeBrightnessMode;
                        z7 = this.mPowerRequest.freezeBrightnessMode;
                        if (z6 != z7) {
                            if (z7) {
                                if (this.mScreenBrightnessRampAnimator.getCurrentValue() <= 1.0f) {
                                    this.mFreezeBrightnessModeSelector = 1;
                                    Slog.m72d(this.mTag, "NON_HBM_FREEZE_MODE");
                                } else {
                                    this.mFreezeBrightnessModeSelector = 2;
                                    Slog.m72d(this.mTag, "HBM_FREEZE_MODE");
                                }
                            }
                            this.mFreezeBrightnessMode = this.mPowerRequest.freezeBrightnessMode;
                        }
                        z8 = this.mBatteryLevelCritical;
                        z9 = this.mPowerRequest.batteryLevelCritical;
                        if (z8 == z9) {
                            if (z9) {
                                f3 = f;
                                this.mLastBatteryLevelCriticalTime = this.mClock.uptimeMillis();
                            } else {
                                f3 = f;
                            }
                            this.mBatteryLevelCritical = this.mPowerRequest.batteryLevelCritical;
                        } else {
                            f3 = f;
                        }
                        if (this.mIsDisplayInternal && screenState2 == 2) {
                            displayPowerRequest = this.mPowerRequest;
                            if (displayPowerRequest.policy == 3 && (!this.mIsCoverDisplay || PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY)) {
                                z28 = !this.mBatteryLevelCritical && this.mLastBatteryLevelCriticalTime > sLastScreenBrightnessSettingChangedTime && !displayPowerRequest.isPowered && (!this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() || ((automaticBrightnessController3 = this.mAutomaticBrightnessController) != null && automaticBrightnessController3.isAmbientLuxValid()));
                                if (!z28 && !this.mAppliedForceDimming) {
                                    this.mAppliedForceDimming = true;
                                    if (this.mBrightnessReasonTemp.mReason == 4) {
                                        this.mLastScreenBrightnessSettingBeforeForceDim = brightness;
                                    } else {
                                        this.mLastScreenBrightnessSettingBeforeForceDim = this.mDisplayBrightnessController.getCurrentBrightness();
                                    }
                                    if (!z34) {
                                    }
                                    z29 = false;
                                } else {
                                    if (!z28 && this.mAppliedForceDimming) {
                                        this.mAppliedForceDimming = false;
                                        if (!this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                                            brightness = this.mLastScreenBrightnessSettingBeforeForceDim;
                                            this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_FORCE, brightness);
                                            z5 = true;
                                        }
                                        if (!z34) {
                                        }
                                    }
                                    z29 = false;
                                }
                                if (this.mAppliedForceDimming || brightness <= RATE_FROM_DOZE_TO_ON) {
                                    f4 = brightness;
                                    z10 = z29;
                                } else {
                                    float max = Math.max(Math.min(brightness - this.mScreenBrightnessMinimumDimAmount, this.mScreenBrightnessDimConfig), RATE_FROM_DOZE_TO_ON);
                                    this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_USE_QUOTA, max);
                                    f4 = max;
                                    z10 = z29;
                                    z5 = true;
                                }
                                if (!this.mBrightnessThrottler.isThrottled()) {
                                    this.mTempBrightnessEvent.setThermalMax(this.mBrightnessThrottler.getBrightnessCap());
                                    f5 = Math.min(f4, this.mBrightnessThrottler.getBrightnessCap());
                                    this.mBrightnessReasonTemp.addModifier(8, f5);
                                    if (this.mAppliedThrottling) {
                                        z27 = true;
                                    } else {
                                        z27 = true;
                                        z4 = false;
                                    }
                                    this.mAppliedThrottling = z27;
                                } else {
                                    if (this.mAppliedThrottling) {
                                        this.mAppliedThrottling = false;
                                    }
                                    f5 = f4;
                                }
                                AutomaticBrightnessController automaticBrightnessController4 = this.mAutomaticBrightnessController;
                                float ambientLux2 = automaticBrightnessController4 != null ? RATE_FROM_DOZE_TO_ON : automaticBrightnessController4.getAmbientLux();
                                boolean z38 = z4;
                                i3 = 0;
                                while (i3 < clone.size()) {
                                    ((DisplayPowerControllerInterface) clone.valueAt(i3)).setBrightnessToFollow(currentBrightness, this.mDisplayBrightnessController.convertToNits(currentBrightness), ambientLux2);
                                    i3++;
                                    f2 = f2;
                                }
                                float f15 = f2;
                                if (z5) {
                                    i4 = 2;
                                } else {
                                    i4 = 2;
                                    if (this.mPowerRequest.policy != 2) {
                                        z11 = this.mDisplayBrightnessController.updateScreenBrightnessSetting(f5);
                                        if (this.mPowerRequest.policy == i4) {
                                            if (f5 > RATE_FROM_DOZE_TO_ON) {
                                                f5 = Math.max(Math.min(f5 - this.mScreenBrightnessMinimumDimAmount, this.mScreenBrightnessDimConfig), RATE_FROM_DOZE_TO_ON);
                                                z26 = true;
                                                this.mBrightnessReasonTemp.addModifier(1, f5);
                                            } else {
                                                z26 = true;
                                            }
                                            if (!this.mAppliedDimming) {
                                                z38 = false;
                                            }
                                            this.mAppliedDimming = z26;
                                        } else if (this.mAppliedDimming) {
                                            this.mAppliedDimming = false;
                                            z38 = false;
                                        }
                                        if (this.mLastOriginalTarget != f5) {
                                            this.mLastOriginalTarget = f5;
                                        }
                                        if (!this.mPowerRequest.lowPowerMode && !this.mAwakenFromDozingInAutoBrightness && !Display.isDozeState(screenState2)) {
                                            if (f5 > RATE_FROM_DOZE_TO_ON) {
                                                f5 = Math.max(f5 * Math.min(this.mPowerRequest.screenLowPowerBrightnessFactor, 1.0f), RATE_FROM_DOZE_TO_ON);
                                                this.mBrightnessReasonTemp.addModifier(2, f5);
                                            }
                                            if (this.mAppliedLowPower) {
                                                z25 = true;
                                            } else {
                                                z25 = true;
                                                z38 = false;
                                            }
                                            this.mAppliedLowPower = z25;
                                        } else if (this.mAppliedLowPower) {
                                            this.mAppliedLowPower = false;
                                            z38 = false;
                                        }
                                        float finalBrightness = getFinalBrightness(f5, screenState2);
                                        if (this.mForceSlowChange) {
                                            Slog.m72d(this.mTag, "ForceSlowChange is requested, set slowChange as true");
                                            z38 = true;
                                        }
                                        this.mHbmController.onBrightnessChanged(finalBrightness, f4, this.mBrightnessThrottler.getBrightnessMaxReason());
                                        boolean z39 = this.mBrightnessReasonTemp.getReason() != 7 || this.mAutomaticBrightnessStrategy.isTemporaryAutoBrightnessAdjustmentApplied();
                                        if (!this.mPendingScreenOff) {
                                            if (this.mSkipScreenOnBrightnessRamp) {
                                                i11 = 2;
                                                if (screenState2 == 2) {
                                                    int i18 = this.mSkipRampState;
                                                    if (i18 == 0 && this.mDozing) {
                                                        this.mInitialAutoBrightness = finalBrightness;
                                                        this.mSkipRampState = 1;
                                                    } else if (i18 == 1 && this.mUseSoftwareAutoBrightnessConfig && !BrightnessSynchronizer.floatEquals(finalBrightness, this.mInitialAutoBrightness)) {
                                                        i11 = 2;
                                                        this.mSkipRampState = 2;
                                                    } else {
                                                        i11 = 2;
                                                        if (this.mSkipRampState == 2) {
                                                            this.mSkipRampState = 0;
                                                        }
                                                    }
                                                } else {
                                                    this.mSkipRampState = 0;
                                                }
                                                boolean z40 = !(screenState2 == i11 || this.mSkipRampState == 0) || this.mDisplayPowerProximityStateController.shouldSkipRampBecauseOfProximityChangeToNegative();
                                                boolean z41 = !this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() && (!this.mInitialAutoBrightnessUpdated || z3) && !this.mWaitingAutoBrightnessFromDoze;
                                                boolean z42 = !Display.isDozeState(screenState2) && this.mBrightnessReason.mReason == 11;
                                                boolean isDozeState = Display.isDozeState(screenState2);
                                                boolean z43 = PowerManagerUtil.SEC_FEATURE_AOD_BRIGHTNESS_ANIM && isDozeState;
                                                if (Display.isDozeState(screenState2)) {
                                                    i12 = screenState2;
                                                } else {
                                                    i12 = screenState2;
                                                    if (this.mBrightnessBucketsInDozeConfig) {
                                                        i6 = i;
                                                        z17 = true;
                                                        if (this.mColorFadeEnabled || this.mPowerState.getColorFadeLevel() != 1.0f) {
                                                            z18 = z31;
                                                            z19 = false;
                                                        } else {
                                                            z18 = z31;
                                                            z19 = true;
                                                        }
                                                        float clampScreenBrightnessForFinal = clampScreenBrightnessForFinal(finalBrightness);
                                                        f6 = f4;
                                                        i5 = i2;
                                                        if (this.mHbmController.getHighBrightnessMode() == 2) {
                                                            boolean z44 = (this.mBrightnessReasonTemp.getModifier() & 2) != 0;
                                                            boolean z45 = !this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() || ((automaticBrightnessController2 = this.mAutomaticBrightnessController) != null && automaticBrightnessController2.isAmbientLuxValid());
                                                            if (!z44 && z45) {
                                                                f9 = this.mHbmController.getHdrBrightnessValue();
                                                                z21 = z11;
                                                                this.mBrightnessReasonTemp.addModifier(4, f9);
                                                                float f16 = this.mPowerRequest.maxBrightness;
                                                                if (f9 >= f16) {
                                                                    this.mBrightnessReasonTemp.addModifier(32, f16);
                                                                    z20 = z36;
                                                                    f9 = f16;
                                                                } else {
                                                                    z20 = z36;
                                                                }
                                                                f7 = finalBrightness;
                                                                float screenBrightness = this.mPowerState.getScreenBrightness();
                                                                float sdrScreenBrightness = this.mPowerState.getSdrScreenBrightness();
                                                                boolean shouldEnableMoreFastRampRateCase = shouldEnableMoreFastRampRateCase();
                                                                boolean shouldEnableHdrRampRateCase = shouldEnableHdrRampRateCase();
                                                                z22 = this.mBrightnessReasonTemp.getReason() != 10;
                                                                if (!this.mWaitingAutoBrightnessFromDoze || z34) {
                                                                    z23 = z22;
                                                                    z24 = true;
                                                                } else {
                                                                    z23 = z22;
                                                                    z24 = false;
                                                                }
                                                                boolean z46 = z24;
                                                                boolean z47 = Math.abs(BrightnessSynchronizer.brightnessFloatToInt(this.mScreenBrightnessRampAnimator.getCurrentValue()) - BrightnessSynchronizer.brightnessFloatToInt(f9)) > 1 && !this.mScreenBrightnessRampAnimator.isAnimating();
                                                                if (BrightnessUtils.isValidBrightnessValue(f9) || (f9 == screenBrightness && clampScreenBrightnessForFinal == sdrScreenBrightness)) {
                                                                    f10 = 0.0f;
                                                                    f11 = Float.NaN;
                                                                } else if (z40 || z17 || !z19 || ((z39 && !this.mForceSlowChange) || this.mPassRampAnimation || z41 || z42 || z43 || ((this.mBrightnessReasonTemp.getReason() == 6 && this.mBrightnessReasonTemp.isStartBrightnessChanged(this.mBrightnessReason) && !shouldEnableMoreFastRampRateCase && !this.mAppliedDimming) || z10 || z47))) {
                                                                    animateScreenBrightness(f9, clampScreenBrightnessForFinal, RATE_FROM_DOZE_TO_ON, RATE_FROM_DOZE_TO_ON);
                                                                    f11 = 0.0f;
                                                                    f10 = 0.0f;
                                                                } else {
                                                                    boolean z48 = f9 > screenBrightness;
                                                                    if (z48 && z38) {
                                                                        f12 = this.mBrightnessRampRateSlowIncrease;
                                                                    } else if (z48 && !z38) {
                                                                        f12 = this.mBrightnessRampRateFastIncrease;
                                                                    } else if (!z48 && z38) {
                                                                        f12 = !Float.isNaN(f3) ? f3 : this.mBrightnessRampRateSlowDecrease;
                                                                        if (shouldEnableMoreFastRampRateCase) {
                                                                            f13 = f15;
                                                                        } else {
                                                                            f12 = this.mMoreFastRampRate;
                                                                            f13 = Float.NaN;
                                                                        }
                                                                        if (isDozeState) {
                                                                            f12 = 0.35f;
                                                                        }
                                                                        if (z46) {
                                                                            f10 = f12;
                                                                        } else {
                                                                            f10 = z48 ? RATE_FROM_DOZE_TO_ON : RATE_FROM_DOZE_TO_ON;
                                                                        }
                                                                        if (shouldEnableHdrRampRateCase) {
                                                                            if (z48) {
                                                                                f10 = this.mBrightnessRampRateHdrIncrease;
                                                                            } else {
                                                                                f10 = this.mBrightnessRampRateHdrDecrease;
                                                                            }
                                                                        }
                                                                        if (z23) {
                                                                            f14 = f13;
                                                                        } else {
                                                                            float f17 = this.mFollowerRampSpeed;
                                                                            f14 = this.mFollowerRampSpeedAtHbm;
                                                                            f10 = f17;
                                                                        }
                                                                        if (this.mScreenBrightnessRampAnimator.getTarget() != f9) {
                                                                            animateScreenBrightness(f9, clampScreenBrightnessForFinal, f10, f14);
                                                                        }
                                                                        f11 = f14;
                                                                    } else {
                                                                        f12 = this.mBrightnessRampRateFastDecrease;
                                                                    }
                                                                    f15 = Float.NaN;
                                                                    if (shouldEnableMoreFastRampRateCase) {
                                                                    }
                                                                    if (isDozeState) {
                                                                    }
                                                                    if (z46) {
                                                                    }
                                                                    if (shouldEnableHdrRampRateCase) {
                                                                    }
                                                                    if (z23) {
                                                                    }
                                                                    if (this.mScreenBrightnessRampAnimator.getTarget() != f9) {
                                                                    }
                                                                    f11 = f14;
                                                                }
                                                                for (i13 = 0; i13 < clone.size(); i13++) {
                                                                    ((DisplayPowerControllerInterface) clone.valueAt(i13)).setRampSpeedToFollower(f10, f11);
                                                                }
                                                                if (!this.mScreenBrightnessRampAnimator.isAnimating() && this.mForceSlowChange) {
                                                                    this.mForceSlowChange = false;
                                                                }
                                                                i7 = 32;
                                                                i8 = i12;
                                                                notifyBrightnessTrackerChanged(f7, z20, isShortTermModelActive, this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled(), z39);
                                                                if ((!this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness() || z10) && !this.mInitialAutoBrightnessUpdated) {
                                                                    this.mInitialAutoBrightnessUpdated = true;
                                                                    if (this.mWaitingAutoBrightnessFromDoze) {
                                                                        this.mWaitingAutoBrightnessFromDoze = false;
                                                                    }
                                                                }
                                                                if (z21 && !z20 && (this.mDisplayId == 0 || this.mIsCoverDisplay)) {
                                                                    putAutoBrightnessTransitionTime(f10, f11, f9);
                                                                }
                                                                saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f9);
                                                                z12 = z18;
                                                            } else {
                                                                z21 = z11;
                                                                String str = this.mTag;
                                                                z20 = z36;
                                                                StringBuilder sb = new StringBuilder();
                                                                f7 = finalBrightness;
                                                                sb.append("pending hdr: low power: ");
                                                                sb.append(z44);
                                                                sb.append(" brightness ready: ");
                                                                sb.append(z45);
                                                                Slog.m72d(str, sb.toString());
                                                            }
                                                        } else {
                                                            z20 = z36;
                                                            z21 = z11;
                                                            f7 = finalBrightness;
                                                        }
                                                        f9 = clampScreenBrightnessForFinal;
                                                        float screenBrightness2 = this.mPowerState.getScreenBrightness();
                                                        float sdrScreenBrightness2 = this.mPowerState.getSdrScreenBrightness();
                                                        boolean shouldEnableMoreFastRampRateCase2 = shouldEnableMoreFastRampRateCase();
                                                        boolean shouldEnableHdrRampRateCase2 = shouldEnableHdrRampRateCase();
                                                        if (this.mBrightnessReasonTemp.getReason() != 10) {
                                                        }
                                                        if (this.mWaitingAutoBrightnessFromDoze) {
                                                        }
                                                        z23 = z22;
                                                        z24 = true;
                                                        boolean z462 = z24;
                                                        if (Math.abs(BrightnessSynchronizer.brightnessFloatToInt(this.mScreenBrightnessRampAnimator.getCurrentValue()) - BrightnessSynchronizer.brightnessFloatToInt(f9)) > 1) {
                                                        }
                                                        if (BrightnessUtils.isValidBrightnessValue(f9)) {
                                                        }
                                                        f10 = 0.0f;
                                                        f11 = Float.NaN;
                                                        while (i13 < clone.size()) {
                                                        }
                                                        if (!this.mScreenBrightnessRampAnimator.isAnimating()) {
                                                            this.mForceSlowChange = false;
                                                        }
                                                        i7 = 32;
                                                        i8 = i12;
                                                        notifyBrightnessTrackerChanged(f7, z20, isShortTermModelActive, this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled(), z39);
                                                        if (!this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness()) {
                                                        }
                                                        this.mInitialAutoBrightnessUpdated = true;
                                                        if (this.mWaitingAutoBrightnessFromDoze) {
                                                        }
                                                        if (z21) {
                                                            putAutoBrightnessTransitionTime(f10, f11, f9);
                                                        }
                                                        saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f9);
                                                        z12 = z18;
                                                    }
                                                }
                                                i6 = i;
                                                z17 = false;
                                                if (this.mColorFadeEnabled) {
                                                }
                                                z18 = z31;
                                                z19 = false;
                                                float clampScreenBrightnessForFinal2 = clampScreenBrightnessForFinal(finalBrightness);
                                                f6 = f4;
                                                i5 = i2;
                                                if (this.mHbmController.getHighBrightnessMode() == 2) {
                                                }
                                                f9 = clampScreenBrightnessForFinal2;
                                                float screenBrightness22 = this.mPowerState.getScreenBrightness();
                                                float sdrScreenBrightness22 = this.mPowerState.getSdrScreenBrightness();
                                                boolean shouldEnableMoreFastRampRateCase22 = shouldEnableMoreFastRampRateCase();
                                                boolean shouldEnableHdrRampRateCase22 = shouldEnableHdrRampRateCase();
                                                if (this.mBrightnessReasonTemp.getReason() != 10) {
                                                }
                                                if (this.mWaitingAutoBrightnessFromDoze) {
                                                }
                                                z23 = z22;
                                                z24 = true;
                                                boolean z4622 = z24;
                                                if (Math.abs(BrightnessSynchronizer.brightnessFloatToInt(this.mScreenBrightnessRampAnimator.getCurrentValue()) - BrightnessSynchronizer.brightnessFloatToInt(f9)) > 1) {
                                                }
                                                if (BrightnessUtils.isValidBrightnessValue(f9)) {
                                                }
                                                f10 = 0.0f;
                                                f11 = Float.NaN;
                                                while (i13 < clone.size()) {
                                                }
                                                if (!this.mScreenBrightnessRampAnimator.isAnimating()) {
                                                }
                                                i7 = 32;
                                                i8 = i12;
                                                notifyBrightnessTrackerChanged(f7, z20, isShortTermModelActive, this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled(), z39);
                                                if (!this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness()) {
                                                }
                                                this.mInitialAutoBrightnessUpdated = true;
                                                if (this.mWaitingAutoBrightnessFromDoze) {
                                                }
                                                if (z21) {
                                                }
                                                saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f9);
                                                z12 = z18;
                                            }
                                            i11 = 2;
                                            if (screenState2 == i11) {
                                            }
                                            if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                                            }
                                            if (Display.isDozeState(screenState2)) {
                                            }
                                            boolean isDozeState2 = Display.isDozeState(screenState2);
                                            if (PowerManagerUtil.SEC_FEATURE_AOD_BRIGHTNESS_ANIM) {
                                            }
                                            if (Display.isDozeState(screenState2)) {
                                            }
                                            i6 = i;
                                            z17 = false;
                                            if (this.mColorFadeEnabled) {
                                            }
                                            z18 = z31;
                                            z19 = false;
                                            float clampScreenBrightnessForFinal22 = clampScreenBrightnessForFinal(finalBrightness);
                                            f6 = f4;
                                            i5 = i2;
                                            if (this.mHbmController.getHighBrightnessMode() == 2) {
                                            }
                                            f9 = clampScreenBrightnessForFinal22;
                                            float screenBrightness222 = this.mPowerState.getScreenBrightness();
                                            float sdrScreenBrightness222 = this.mPowerState.getSdrScreenBrightness();
                                            boolean shouldEnableMoreFastRampRateCase222 = shouldEnableMoreFastRampRateCase();
                                            boolean shouldEnableHdrRampRateCase222 = shouldEnableHdrRampRateCase();
                                            if (this.mBrightnessReasonTemp.getReason() != 10) {
                                            }
                                            if (this.mWaitingAutoBrightnessFromDoze) {
                                            }
                                            z23 = z22;
                                            z24 = true;
                                            boolean z46222 = z24;
                                            if (Math.abs(BrightnessSynchronizer.brightnessFloatToInt(this.mScreenBrightnessRampAnimator.getCurrentValue()) - BrightnessSynchronizer.brightnessFloatToInt(f9)) > 1) {
                                            }
                                            if (BrightnessUtils.isValidBrightnessValue(f9)) {
                                            }
                                            f10 = 0.0f;
                                            f11 = Float.NaN;
                                            while (i13 < clone.size()) {
                                            }
                                            if (!this.mScreenBrightnessRampAnimator.isAnimating()) {
                                            }
                                            i7 = 32;
                                            i8 = i12;
                                            notifyBrightnessTrackerChanged(f7, z20, isShortTermModelActive, this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled(), z39);
                                            if (!this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness()) {
                                            }
                                            this.mInitialAutoBrightnessUpdated = true;
                                            if (this.mWaitingAutoBrightnessFromDoze) {
                                            }
                                            if (z21) {
                                            }
                                            saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f9);
                                            z12 = z18;
                                        } else {
                                            f6 = f4;
                                            i5 = i2;
                                            i6 = i;
                                            z12 = z31;
                                            f7 = finalBrightness;
                                            i7 = 32;
                                            i8 = screenState2;
                                            saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting());
                                        }
                                        if (saveBrightnessInfo && !z39) {
                                            lambda$new$1();
                                        }
                                        if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason) || i5 != 0) {
                                            f8 = f7;
                                            String str2 = this.mTag;
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append("Brightness [");
                                            sb2.append(f8);
                                            sb2.append("] reason changing to: '");
                                            i9 = i5;
                                            sb2.append(this.mBrightnessReasonTemp.toString(i9));
                                            sb2.append("', previous reason: '");
                                            sb2.append(this.mBrightnessReason);
                                            sb2.append("'.");
                                            Slog.m78v(str2, sb2.toString());
                                            this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                                        } else {
                                            if (this.mBrightnessReasonTemp.getReason() == 1 && updateUserSetScreenBrightness) {
                                                String str3 = this.mTag;
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append("Brightness [");
                                                f8 = f7;
                                                sb3.append(f8);
                                                sb3.append("] manual adjustment.");
                                                Slog.m78v(str3, sb3.toString());
                                            } else {
                                                f8 = f7;
                                            }
                                            i9 = i5;
                                        }
                                        this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                                        this.mTempBrightnessEvent.setBrightness(f8);
                                        this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                                        this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                                        this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                                        this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                                        BrightnessEvent brightnessEvent2 = this.mTempBrightnessEvent;
                                        int flags = brightnessEvent2.getFlags() | (this.mIsRbcActive ? 1 : 0);
                                        if (!this.mPowerRequest.lowPowerMode) {
                                            i7 = 0;
                                        }
                                        brightnessEvent2.setFlags(flags | i7);
                                        BrightnessEvent brightnessEvent3 = this.mTempBrightnessEvent;
                                        ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal = this.mCdsi;
                                        brightnessEvent3.setRbcStrength(colorDisplayServiceInternal != null ? colorDisplayServiceInternal.getReduceBrightColorsStrength() : -1);
                                        this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                                        this.mTempBrightnessEvent.setWasShortTermModelActive(isShortTermModelActive);
                                        this.mTempBrightnessEvent.setDisplayBrightnessStrategyName(updateBrightness.getDisplayBrightnessStrategyName());
                                        this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness());
                                        boolean z49 = this.mTempBrightnessEvent.getReason().getReason() != 7 && this.mLastBrightnessEvent.getReason().getReason() == 7;
                                        z13 = this.mLastBrightnessEvent.isRbcEnabled() != this.mTempBrightnessEvent.isRbcEnabled();
                                        if ((!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent) && !z49) || i9 != 0) {
                                            this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                                            this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                                            brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                                            brightnessEvent.setAdjustmentFlags(i9);
                                            brightnessEvent.setFlags(brightnessEvent.getFlags() | (!updateUserSetScreenBrightness ? 8 : 0));
                                            Slog.m76i(this.mTag, brightnessEvent.toString(false));
                                            if (!updateUserSetScreenBrightness || brightnessEvent.getReason().getReason() != 7) {
                                                logBrightnessEvent(brightnessEvent, f6);
                                            }
                                            ringBuffer = this.mBrightnessEventRingBuffer;
                                            if (ringBuffer != null) {
                                                ringBuffer.append(brightnessEvent);
                                            }
                                            if (z13) {
                                                this.mRbcEventRingBuffer.append(brightnessEvent);
                                            }
                                        }
                                        brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                                        if (brightnessMappingStrategy != null && (brightnessMappingStrategy instanceof BrightnessMappingStrategy.PhysicalMappingStrategy)) {
                                            updateForceUpdateAbJob();
                                        }
                                        z14 = (this.mPendingScreenOnUnblocker == null || (this.mColorFadeEnabled && (this.mColorFadeOnAnimator.isStarted() || this.mColorFadeOffAnimator.isStarted())) || !this.mPowerState.waitUntilClean(this.mCleanListener) || this.mPendingScreenOnByAodReady) ? false : true;
                                        z15 = (z14 || this.mScreenBrightnessRampAnimator.isAnimating()) ? false : true;
                                        if (z14 && i8 != 1 && this.mReportedScreenStateToPolicy == 1) {
                                            setReportedScreenState(2);
                                            this.mWindowManagerPolicy.screenTurnedOn(this.mDisplayId);
                                        }
                                        if (!z15) {
                                            this.mWakelockController.acquireWakelock(5);
                                        }
                                        boolean z50 = this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() || ((automaticBrightnessController = this.mAutomaticBrightnessController) != null && automaticBrightnessController.isAmbientLuxValid());
                                        if (z14 && z12 && z50) {
                                            synchronized (this.mLock) {
                                                if (!this.mPendingRequestChangedLocked) {
                                                    this.mDisplayReadyLocked = true;
                                                }
                                            }
                                            sendOnStateChangedWithWakelock();
                                        }
                                        if (z15) {
                                            z16 = false;
                                        } else {
                                            if (i8 == 1) {
                                                PowerManagerUtil.ScreenOffProfiler screenOffProfiler = PowerManagerUtil.sCurrentScreenOffProfiler;
                                                if (screenOffProfiler.isStarted() && !screenOffProfiler.mSaved && this.mPowerRequest.lastGoToSleepReason != 3) {
                                                    screenOffProfiler.mSaved = true;
                                                    z16 = false;
                                                    Slog.m72d(this.mTag, screenOffProfiler.toString(false));
                                                    PowerManagerUtil.sScreenOffProfilers.append(new PowerManagerUtil.ScreenOffProfiler(screenOffProfiler));
                                                    this.mWakelockController.releaseWakelock(5);
                                                }
                                            }
                                            z16 = false;
                                            this.mWakelockController.releaseWakelock(5);
                                        }
                                        this.mDozing = i8 == 2 ? true : z16;
                                        i10 = this.mPowerRequest.policy;
                                        if (i6 == i10) {
                                            logDisplayPolicyChanged(i10);
                                            return;
                                        }
                                        return;
                                    }
                                }
                                z11 = false;
                                if (this.mPowerRequest.policy == i4) {
                                }
                                if (this.mLastOriginalTarget != f5) {
                                }
                                if (!this.mPowerRequest.lowPowerMode) {
                                }
                                if (this.mAppliedLowPower) {
                                }
                                float finalBrightness2 = getFinalBrightness(f5, screenState2);
                                if (this.mForceSlowChange) {
                                }
                                this.mHbmController.onBrightnessChanged(finalBrightness2, f4, this.mBrightnessThrottler.getBrightnessMaxReason());
                                if (this.mBrightnessReasonTemp.getReason() != 7) {
                                }
                                if (!this.mPendingScreenOff) {
                                }
                                if (saveBrightnessInfo) {
                                    lambda$new$1();
                                }
                                if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                                }
                                f8 = f7;
                                String str22 = this.mTag;
                                StringBuilder sb22 = new StringBuilder();
                                sb22.append("Brightness [");
                                sb22.append(f8);
                                sb22.append("] reason changing to: '");
                                i9 = i5;
                                sb22.append(this.mBrightnessReasonTemp.toString(i9));
                                sb22.append("', previous reason: '");
                                sb22.append(this.mBrightnessReason);
                                sb22.append("'.");
                                Slog.m78v(str22, sb22.toString());
                                this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                                this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                                this.mTempBrightnessEvent.setBrightness(f8);
                                this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                                this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                                this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                                this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                                BrightnessEvent brightnessEvent22 = this.mTempBrightnessEvent;
                                int flags2 = brightnessEvent22.getFlags() | (this.mIsRbcActive ? 1 : 0);
                                if (!this.mPowerRequest.lowPowerMode) {
                                }
                                brightnessEvent22.setFlags(flags2 | i7);
                                BrightnessEvent brightnessEvent32 = this.mTempBrightnessEvent;
                                ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal2 = this.mCdsi;
                                brightnessEvent32.setRbcStrength(colorDisplayServiceInternal2 != null ? colorDisplayServiceInternal2.getReduceBrightColorsStrength() : -1);
                                this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                                this.mTempBrightnessEvent.setWasShortTermModelActive(isShortTermModelActive);
                                this.mTempBrightnessEvent.setDisplayBrightnessStrategyName(updateBrightness.getDisplayBrightnessStrategyName());
                                this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness());
                                if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                                }
                                if (this.mLastBrightnessEvent.isRbcEnabled() != this.mTempBrightnessEvent.isRbcEnabled()) {
                                }
                                if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                                    this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                                    this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                                    brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                                    brightnessEvent.setAdjustmentFlags(i9);
                                    brightnessEvent.setFlags(brightnessEvent.getFlags() | (!updateUserSetScreenBrightness ? 8 : 0));
                                    Slog.m76i(this.mTag, brightnessEvent.toString(false));
                                    if (!updateUserSetScreenBrightness) {
                                    }
                                    logBrightnessEvent(brightnessEvent, f6);
                                    ringBuffer = this.mBrightnessEventRingBuffer;
                                    if (ringBuffer != null) {
                                    }
                                    if (z13) {
                                    }
                                    brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                                    if (brightnessMappingStrategy != null) {
                                        updateForceUpdateAbJob();
                                    }
                                    if (this.mPendingScreenOnUnblocker == null) {
                                    }
                                    if (z14) {
                                    }
                                    if (z14) {
                                        setReportedScreenState(2);
                                        this.mWindowManagerPolicy.screenTurnedOn(this.mDisplayId);
                                    }
                                    if (!z15) {
                                    }
                                    if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                                    }
                                    if (z14) {
                                        synchronized (this.mLock) {
                                        }
                                    }
                                    if (z15) {
                                    }
                                    this.mDozing = i8 == 2 ? true : z16;
                                    i10 = this.mPowerRequest.policy;
                                    if (i6 == i10) {
                                    }
                                }
                                this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                                this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                                brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                                brightnessEvent.setAdjustmentFlags(i9);
                                brightnessEvent.setFlags(brightnessEvent.getFlags() | (!updateUserSetScreenBrightness ? 8 : 0));
                                Slog.m76i(this.mTag, brightnessEvent.toString(false));
                                if (!updateUserSetScreenBrightness) {
                                }
                                logBrightnessEvent(brightnessEvent, f6);
                                ringBuffer = this.mBrightnessEventRingBuffer;
                                if (ringBuffer != null) {
                                }
                                if (z13) {
                                }
                                brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                                if (brightnessMappingStrategy != null) {
                                }
                                if (this.mPendingScreenOnUnblocker == null) {
                                }
                                if (z14) {
                                }
                                if (z14) {
                                }
                                if (!z15) {
                                }
                                if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                                }
                                if (z14) {
                                }
                                if (z15) {
                                }
                                this.mDozing = i8 == 2 ? true : z16;
                                i10 = this.mPowerRequest.policy;
                                if (i6 == i10) {
                                }
                            }
                        }
                        f4 = brightness;
                        z10 = false;
                        if (!this.mBrightnessThrottler.isThrottled()) {
                        }
                        AutomaticBrightnessController automaticBrightnessController42 = this.mAutomaticBrightnessController;
                        if (automaticBrightnessController42 != null) {
                        }
                        boolean z382 = z4;
                        i3 = 0;
                        while (i3 < clone.size()) {
                        }
                        float f152 = f2;
                        if (z5) {
                        }
                        z11 = false;
                        if (this.mPowerRequest.policy == i4) {
                        }
                        if (this.mLastOriginalTarget != f5) {
                        }
                        if (!this.mPowerRequest.lowPowerMode) {
                        }
                        if (this.mAppliedLowPower) {
                        }
                        float finalBrightness22 = getFinalBrightness(f5, screenState2);
                        if (this.mForceSlowChange) {
                        }
                        this.mHbmController.onBrightnessChanged(finalBrightness22, f4, this.mBrightnessThrottler.getBrightnessMaxReason());
                        if (this.mBrightnessReasonTemp.getReason() != 7) {
                        }
                        if (!this.mPendingScreenOff) {
                        }
                        if (saveBrightnessInfo) {
                        }
                        if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                        }
                        f8 = f7;
                        String str222 = this.mTag;
                        StringBuilder sb222 = new StringBuilder();
                        sb222.append("Brightness [");
                        sb222.append(f8);
                        sb222.append("] reason changing to: '");
                        i9 = i5;
                        sb222.append(this.mBrightnessReasonTemp.toString(i9));
                        sb222.append("', previous reason: '");
                        sb222.append(this.mBrightnessReason);
                        sb222.append("'.");
                        Slog.m78v(str222, sb222.toString());
                        this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                        this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                        this.mTempBrightnessEvent.setBrightness(f8);
                        this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                        this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                        this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                        this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                        BrightnessEvent brightnessEvent222 = this.mTempBrightnessEvent;
                        int flags22 = brightnessEvent222.getFlags() | (this.mIsRbcActive ? 1 : 0);
                        if (!this.mPowerRequest.lowPowerMode) {
                        }
                        brightnessEvent222.setFlags(flags22 | i7);
                        BrightnessEvent brightnessEvent322 = this.mTempBrightnessEvent;
                        ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal22 = this.mCdsi;
                        brightnessEvent322.setRbcStrength(colorDisplayServiceInternal22 != null ? colorDisplayServiceInternal22.getReduceBrightColorsStrength() : -1);
                        this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                        this.mTempBrightnessEvent.setWasShortTermModelActive(isShortTermModelActive);
                        this.mTempBrightnessEvent.setDisplayBrightnessStrategyName(updateBrightness.getDisplayBrightnessStrategyName());
                        this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness());
                        if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                        }
                        if (this.mLastBrightnessEvent.isRbcEnabled() != this.mTempBrightnessEvent.isRbcEnabled()) {
                        }
                        if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                        }
                        this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                        this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                        brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                        brightnessEvent.setAdjustmentFlags(i9);
                        brightnessEvent.setFlags(brightnessEvent.getFlags() | (!updateUserSetScreenBrightness ? 8 : 0));
                        Slog.m76i(this.mTag, brightnessEvent.toString(false));
                        if (!updateUserSetScreenBrightness) {
                        }
                        logBrightnessEvent(brightnessEvent, f6);
                        ringBuffer = this.mBrightnessEventRingBuffer;
                        if (ringBuffer != null) {
                        }
                        if (z13) {
                        }
                        brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                        if (brightnessMappingStrategy != null) {
                        }
                        if (this.mPendingScreenOnUnblocker == null) {
                        }
                        if (z14) {
                        }
                        if (z14) {
                        }
                        if (!z15) {
                        }
                        if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                        }
                        if (z14) {
                        }
                        if (z15) {
                        }
                        this.mDozing = i8 == 2 ? true : z16;
                        i10 = this.mPowerRequest.policy;
                        if (i6 == i10) {
                        }
                    } else {
                        brightness = this.mLastOriginalTarget;
                        this.mBrightnessReasonTemp.setReason(11, brightness);
                        if (this.mDozing) {
                            this.mAwakenFromDozingInAutoBrightness = true;
                        }
                        brightness2 = brightness;
                    }
                }
            } else {
                brightness = clampScreenBrightnessForFinal(brightness);
            }
            i2 = 0;
            z4 = false;
            z5 = false;
            f = Float.NaN;
            f2 = Float.NaN;
            if (Float.isNaN(brightness)) {
                brightness2 = this.mScreenBrightnessDozeConfig;
                brightness = clampScreenBrightness(brightness2);
                this.mBrightnessReasonTemp.setReason(3, brightness);
            }
            if (Float.isNaN(brightness)) {
                brightness = screenOffBrightnessSensorController.getAutomaticScreenBrightness();
                if (BrightnessUtils.isValidBrightnessValue(brightness)) {
                }
            }
            if (Float.isNaN(brightness)) {
            }
            z6 = this.mFreezeBrightnessMode;
            z7 = this.mPowerRequest.freezeBrightnessMode;
            if (z6 != z7) {
            }
            z8 = this.mBatteryLevelCritical;
            z9 = this.mPowerRequest.batteryLevelCritical;
            if (z8 == z9) {
            }
            if (this.mIsDisplayInternal) {
                displayPowerRequest = this.mPowerRequest;
                if (displayPowerRequest.policy == 3) {
                    if (this.mBatteryLevelCritical) {
                    }
                    if (!z28) {
                    }
                    if (!z28) {
                        this.mAppliedForceDimming = false;
                        if (!this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                        }
                        if (!z34) {
                        }
                    }
                    z29 = false;
                    if (this.mAppliedForceDimming) {
                    }
                    f4 = brightness;
                    z10 = z29;
                    if (!this.mBrightnessThrottler.isThrottled()) {
                    }
                    AutomaticBrightnessController automaticBrightnessController422 = this.mAutomaticBrightnessController;
                    if (automaticBrightnessController422 != null) {
                    }
                    boolean z3822 = z4;
                    i3 = 0;
                    while (i3 < clone.size()) {
                    }
                    float f1522 = f2;
                    if (z5) {
                    }
                    z11 = false;
                    if (this.mPowerRequest.policy == i4) {
                    }
                    if (this.mLastOriginalTarget != f5) {
                    }
                    if (!this.mPowerRequest.lowPowerMode) {
                    }
                    if (this.mAppliedLowPower) {
                    }
                    float finalBrightness222 = getFinalBrightness(f5, screenState2);
                    if (this.mForceSlowChange) {
                    }
                    this.mHbmController.onBrightnessChanged(finalBrightness222, f4, this.mBrightnessThrottler.getBrightnessMaxReason());
                    if (this.mBrightnessReasonTemp.getReason() != 7) {
                    }
                    if (!this.mPendingScreenOff) {
                    }
                    if (saveBrightnessInfo) {
                    }
                    if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
                    }
                    f8 = f7;
                    String str2222 = this.mTag;
                    StringBuilder sb2222 = new StringBuilder();
                    sb2222.append("Brightness [");
                    sb2222.append(f8);
                    sb2222.append("] reason changing to: '");
                    i9 = i5;
                    sb2222.append(this.mBrightnessReasonTemp.toString(i9));
                    sb2222.append("', previous reason: '");
                    sb2222.append(this.mBrightnessReason);
                    sb2222.append("'.");
                    Slog.m78v(str2222, sb2222.toString());
                    this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                    this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
                    this.mTempBrightnessEvent.setBrightness(f8);
                    this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                    this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                    this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
                    this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
                    BrightnessEvent brightnessEvent2222 = this.mTempBrightnessEvent;
                    int flags222 = brightnessEvent2222.getFlags() | (this.mIsRbcActive ? 1 : 0);
                    if (!this.mPowerRequest.lowPowerMode) {
                    }
                    brightnessEvent2222.setFlags(flags222 | i7);
                    BrightnessEvent brightnessEvent3222 = this.mTempBrightnessEvent;
                    ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal222 = this.mCdsi;
                    brightnessEvent3222.setRbcStrength(colorDisplayServiceInternal222 != null ? colorDisplayServiceInternal222.getReduceBrightColorsStrength() : -1);
                    this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                    this.mTempBrightnessEvent.setWasShortTermModelActive(isShortTermModelActive);
                    this.mTempBrightnessEvent.setDisplayBrightnessStrategyName(updateBrightness.getDisplayBrightnessStrategyName());
                    this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness());
                    if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
                    }
                    if (this.mLastBrightnessEvent.isRbcEnabled() != this.mTempBrightnessEvent.isRbcEnabled()) {
                    }
                    if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
                    }
                    this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                    this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                    brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
                    brightnessEvent.setAdjustmentFlags(i9);
                    brightnessEvent.setFlags(brightnessEvent.getFlags() | (!updateUserSetScreenBrightness ? 8 : 0));
                    Slog.m76i(this.mTag, brightnessEvent.toString(false));
                    if (!updateUserSetScreenBrightness) {
                    }
                    logBrightnessEvent(brightnessEvent, f6);
                    ringBuffer = this.mBrightnessEventRingBuffer;
                    if (ringBuffer != null) {
                    }
                    if (z13) {
                    }
                    brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
                    if (brightnessMappingStrategy != null) {
                    }
                    if (this.mPendingScreenOnUnblocker == null) {
                    }
                    if (z14) {
                    }
                    if (z14) {
                    }
                    if (!z15) {
                    }
                    if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                    }
                    if (z14) {
                    }
                    if (z15) {
                    }
                    this.mDozing = i8 == 2 ? true : z16;
                    i10 = this.mPowerRequest.policy;
                    if (i6 == i10) {
                    }
                }
            }
            f4 = brightness;
            z10 = false;
            if (!this.mBrightnessThrottler.isThrottled()) {
            }
            AutomaticBrightnessController automaticBrightnessController4222 = this.mAutomaticBrightnessController;
            if (automaticBrightnessController4222 != null) {
            }
            boolean z38222 = z4;
            i3 = 0;
            while (i3 < clone.size()) {
            }
            float f15222 = f2;
            if (z5) {
            }
            z11 = false;
            if (this.mPowerRequest.policy == i4) {
            }
            if (this.mLastOriginalTarget != f5) {
            }
            if (!this.mPowerRequest.lowPowerMode) {
            }
            if (this.mAppliedLowPower) {
            }
            float finalBrightness2222 = getFinalBrightness(f5, screenState2);
            if (this.mForceSlowChange) {
            }
            this.mHbmController.onBrightnessChanged(finalBrightness2222, f4, this.mBrightnessThrottler.getBrightnessMaxReason());
            if (this.mBrightnessReasonTemp.getReason() != 7) {
            }
            if (!this.mPendingScreenOff) {
            }
            if (saveBrightnessInfo) {
            }
            if (this.mBrightnessReasonTemp.equals(this.mBrightnessReason)) {
            }
            f8 = f7;
            String str22222 = this.mTag;
            StringBuilder sb22222 = new StringBuilder();
            sb22222.append("Brightness [");
            sb22222.append(f8);
            sb22222.append("] reason changing to: '");
            i9 = i5;
            sb22222.append(this.mBrightnessReasonTemp.toString(i9));
            sb22222.append("', previous reason: '");
            sb22222.append(this.mBrightnessReason);
            sb22222.append("'.");
            Slog.m78v(str22222, sb22222.toString());
            this.mBrightnessReason.set(this.mBrightnessReasonTemp);
            this.mTempBrightnessEvent.setTime(System.currentTimeMillis());
            this.mTempBrightnessEvent.setBrightness(f8);
            this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
            this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
            this.mTempBrightnessEvent.setHbmMax(this.mHbmController.getCurrentBrightnessMax());
            this.mTempBrightnessEvent.setHbmMode(this.mHbmController.getHighBrightnessMode());
            BrightnessEvent brightnessEvent22222 = this.mTempBrightnessEvent;
            int flags2222 = brightnessEvent22222.getFlags() | (this.mIsRbcActive ? 1 : 0);
            if (!this.mPowerRequest.lowPowerMode) {
            }
            brightnessEvent22222.setFlags(flags2222 | i7);
            BrightnessEvent brightnessEvent32222 = this.mTempBrightnessEvent;
            ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal2222 = this.mCdsi;
            brightnessEvent32222.setRbcStrength(colorDisplayServiceInternal2222 != null ? colorDisplayServiceInternal2222.getReduceBrightColorsStrength() : -1);
            this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
            this.mTempBrightnessEvent.setWasShortTermModelActive(isShortTermModelActive);
            this.mTempBrightnessEvent.setDisplayBrightnessStrategyName(updateBrightness.getDisplayBrightnessStrategyName());
            this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness());
            if (this.mTempBrightnessEvent.getReason().getReason() != 7) {
            }
            if (this.mLastBrightnessEvent.isRbcEnabled() != this.mTempBrightnessEvent.isRbcEnabled()) {
            }
            if (!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent)) {
            }
            this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
            this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
            brightnessEvent = new BrightnessEvent(this.mTempBrightnessEvent);
            brightnessEvent.setAdjustmentFlags(i9);
            brightnessEvent.setFlags(brightnessEvent.getFlags() | (!updateUserSetScreenBrightness ? 8 : 0));
            Slog.m76i(this.mTag, brightnessEvent.toString(false));
            if (!updateUserSetScreenBrightness) {
            }
            logBrightnessEvent(brightnessEvent, f6);
            ringBuffer = this.mBrightnessEventRingBuffer;
            if (ringBuffer != null) {
            }
            if (z13) {
            }
            brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
            if (brightnessMappingStrategy != null) {
            }
            if (this.mPendingScreenOnUnblocker == null) {
            }
            if (z14) {
            }
            if (z14) {
            }
            if (!z15) {
            }
            if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
            }
            if (z14) {
            }
            if (z15) {
            }
            this.mDozing = i8 == 2 ? true : z16;
            i10 = this.mPowerRequest.policy;
            if (i6 == i10) {
            }
        }
    }

    public final boolean shouldEnableMoreFastRampRateCase() {
        if (!this.mBrightnessReasonTemp.hasModifier(1) && this.mBrightnessReason.hasModifier(1)) {
            return true;
        }
        BrightnessReason brightnessReason = this.mBrightnessReasonTemp;
        return (brightnessReason.mReason == 4 && this.mBrightnessReason.mReason != 4) || brightnessReason.isReasonChanged(this.mBrightnessReason, 6);
    }

    public final boolean shouldEnableHdrRampRateCase() {
        return this.mBrightnessReasonTemp.isModifierChanged(this.mBrightnessReason, 4);
    }

    @Override // com.android.server.display.AutomaticBrightnessController.Callbacks
    public void updateBrightness() {
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void ignoreProximitySensorUntilChanged() {
        this.mDisplayPowerProximityStateController.ignoreProximitySensorUntilChanged();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, boolean z) {
        Slog.m72d(this.mTag, "setBrightnessConfiguration: " + brightnessConfiguration + " shouldResetShortTermModel: " + z + " (" + Debug.getCallers(5) + ")");
        this.mHandler.obtainMessage(4, z ? 1 : 0, brightnessConfiguration == null ? 1 : 0, brightnessConfiguration).sendToTarget();
        this.mPendingForceUpdateAb = false;
        if (brightnessConfiguration == null || !"sec-backup".equals(brightnessConfiguration.getDescription())) {
            this.mBrightnessChangedByUser = false;
        }
        this.mLastBrightnessConfigurationTime = SystemClock.elapsedRealtime();
        this.mHandler.removeMessages(15);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(15), ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryBrightness(float f) {
        this.mHandler.obtainMessage(5, Float.floatToIntBits(f), 0).sendToTarget();
        if (f >= RATE_FROM_DOZE_TO_ON) {
            updateLastBrightnessSettingChangedTime();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryBrightnessForSlowChange(float f, boolean z) {
        this.mHandler.obtainMessage(5, Float.floatToIntBits(f), z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mHandler.obtainMessage(6, Float.floatToIntBits(f), 0).sendToTarget();
        updateLastBrightnessSettingChangedTime();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public BrightnessInfo getBrightnessInfo() {
        BrightnessInfo brightnessInfo;
        synchronized (this.mCachedBrightnessInfo) {
            CachedBrightnessInfo cachedBrightnessInfo = this.mCachedBrightnessInfo;
            brightnessInfo = new BrightnessInfo(cachedBrightnessInfo.brightness.value, cachedBrightnessInfo.adjustedBrightness.value, cachedBrightnessInfo.brightnessMin.value, cachedBrightnessInfo.brightnessMax.value, cachedBrightnessInfo.hbmMode.value, cachedBrightnessInfo.hbmTransitionPoint.value, cachedBrightnessInfo.brightnessMaxReason.value, cachedBrightnessInfo.isAnimating.value);
        }
        return brightnessInfo;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onBootCompleted() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(13), this.mClock.uptimeMillis());
    }

    public final boolean saveBrightnessInfo(float f) {
        return saveBrightnessInfo(f, f);
    }

    public final boolean saveBrightnessInfo(float f, float f2) {
        boolean checkAndSetBoolean;
        synchronized (this.mCachedBrightnessInfo) {
            float min = Math.min(this.mHbmController.getCurrentBrightnessMin(), this.mBrightnessThrottler.getBrightnessCap());
            float min2 = Math.min(this.mHbmController.getCurrentBrightnessMax(), this.mBrightnessThrottler.getBrightnessCap());
            CachedBrightnessInfo cachedBrightnessInfo = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat = cachedBrightnessInfo.checkAndSetFloat(cachedBrightnessInfo.brightness, f);
            boolean z = false;
            CachedBrightnessInfo cachedBrightnessInfo2 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat2 = checkAndSetFloat | false | cachedBrightnessInfo2.checkAndSetFloat(cachedBrightnessInfo2.adjustedBrightness, f2);
            CachedBrightnessInfo cachedBrightnessInfo3 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat3 = checkAndSetFloat2 | cachedBrightnessInfo3.checkAndSetFloat(cachedBrightnessInfo3.brightnessMin, min);
            CachedBrightnessInfo cachedBrightnessInfo4 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat4 = checkAndSetFloat3 | cachedBrightnessInfo4.checkAndSetFloat(cachedBrightnessInfo4.brightnessMax, min2);
            CachedBrightnessInfo cachedBrightnessInfo5 = this.mCachedBrightnessInfo;
            boolean checkAndSetInt = checkAndSetFloat4 | cachedBrightnessInfo5.checkAndSetInt(cachedBrightnessInfo5.hbmMode, this.mHbmController.getHighBrightnessMode());
            CachedBrightnessInfo cachedBrightnessInfo6 = this.mCachedBrightnessInfo;
            boolean checkAndSetFloat5 = checkAndSetInt | cachedBrightnessInfo6.checkAndSetFloat(cachedBrightnessInfo6.hbmTransitionPoint, this.mHbmController.getTransitionPoint());
            CachedBrightnessInfo cachedBrightnessInfo7 = this.mCachedBrightnessInfo;
            boolean checkAndSetInt2 = checkAndSetFloat5 | cachedBrightnessInfo7.checkAndSetInt(cachedBrightnessInfo7.brightnessMaxReason, this.mBrightnessThrottler.getBrightnessMaxReason());
            CachedBrightnessInfo cachedBrightnessInfo8 = this.mCachedBrightnessInfo;
            MutableBoolean mutableBoolean = cachedBrightnessInfo8.isAnimating;
            RampAnimator.DualRampAnimator dualRampAnimator = this.mScreenBrightnessRampAnimator;
            checkAndSetBoolean = checkAndSetInt2 | cachedBrightnessInfo8.checkAndSetBoolean(mutableBoolean, dualRampAnimator != null && dualRampAnimator.isAnimating());
            if (checkAndSetBoolean) {
                String str = this.mTag;
                StringBuilder sb = new StringBuilder();
                sb.append("saveBrightnessInfo: brt:");
                sb.append(f);
                sb.append(" adjBrt:");
                sb.append(f2);
                sb.append(" min:");
                sb.append(min);
                sb.append(" max:");
                sb.append(min2);
                sb.append(" hbm:");
                sb.append(this.mHbmController.getHighBrightnessMode());
                sb.append(" tp:");
                sb.append(this.mHbmController.getTransitionPoint());
                sb.append(" throttler:");
                sb.append(this.mBrightnessThrottler.getBrightnessMaxReason());
                sb.append(" isAnimating:");
                RampAnimator.DualRampAnimator dualRampAnimator2 = this.mScreenBrightnessRampAnimator;
                if (dualRampAnimator2 != null && dualRampAnimator2.isAnimating()) {
                    z = true;
                }
                sb.append(z);
                Slog.m72d(str, sb.toString());
            }
        }
        return checkAndSetBoolean;
    }

    /* renamed from: postBrightnessChangeRunnable, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1() {
        this.mHandler.post(this.mOnBrightnessChangeRunnable);
    }

    public final HighBrightnessModeController createHbmControllerLocked() {
        DisplayDeviceConfig displayDeviceConfig = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig();
        IBinder displayTokenLocked = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayTokenLocked();
        String uniqueId = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId();
        DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = displayDeviceConfig != null ? displayDeviceConfig.getHighBrightnessModeData() : null;
        DisplayInfo displayInfoLocked = this.mLogicalDisplay.getDisplayInfoLocked();
        return new HighBrightnessModeController(this.mHandler, displayInfoLocked.logicalWidth, displayInfoLocked.logicalHeight, displayTokenLocked, uniqueId, Math.min(((PowerManager) this.mContext.getSystemService(PowerManager.class)).getBrightnessConstraint(0), this.mScreenBrightnessDimConfig), this.mScreenExtendedBrightnessRangeMaximum, highBrightnessModeData, new HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController2.7
            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
            public float getHdrBrightnessFromSdr(float f, float f2) {
                return DisplayPowerController2.this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
            }
        }, new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$createHbmControllerLocked$5();
            }
        }, this.mHighBrightnessModeMetadata, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createHbmControllerLocked$5() {
        sendUpdatePowerState();
        lambda$new$1();
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.update();
        }
    }

    public final BrightnessThrottler createBrightnessThrottlerLocked() {
        return new BrightnessThrottler(this.mHandler, new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$createBrightnessThrottlerLocked$6();
            }
        }, this.mUniqueDisplayId, this.mLogicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId, this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig().getThermalBrightnessThrottlingDataMapByThrottlingId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createBrightnessThrottlerLocked$6() {
        sendUpdatePowerState();
        lambda$new$1();
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
            if (z2 && !this.mDisplayPowerProximityStateController.isScreenOffBecauseOfProximity()) {
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
        if (z2 && this.mReportedScreenStateToPolicy != 0 && !this.mDisplayPowerProximityStateController.isScreenOffBecauseOfProximity()) {
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
            if (this.mPowerState.getColorFadeLevel() == RATE_FROM_DOZE_TO_ON) {
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
        if (this.mDisplayId == 0 || this.mIsCoverDisplay) {
            this.mLightSensor = this.mSensorManager.getDefaultSensor(65601);
        }
        if (this.mDisplayId == 0 && this.mLightSensor == null) {
            this.mLightSensor = this.mSensorManager.getDefaultSensor(65604);
        }
    }

    public final void loadScreenOffBrightnessSensor() {
        DisplayDeviceConfig.SensorData screenOffBrightnessSensor = this.mDisplayDeviceConfig.getScreenOffBrightnessSensor();
        this.mScreenOffBrightnessSensor = SensorUtils.findSensor(this.mSensorManager, screenOffBrightnessSensor.type, screenOffBrightnessSensor.name, 0);
    }

    public final float clampScreenBrightnessForFinal(float f) {
        if (Float.isNaN(f)) {
            f = RATE_FROM_DOZE_TO_ON;
        }
        return MathUtils.constrain(f, this.mHbmController.getCurrentBrightnessMin(), this.mScreenExtendedBrightnessRangeMaximum);
    }

    public final float clampScreenBrightness(float f) {
        if (Float.isNaN(f)) {
            f = RATE_FROM_DOZE_TO_ON;
        }
        return MathUtils.constrain(f, this.mHbmController.getCurrentBrightnessMin(), this.mHbmController.getCurrentBrightnessMax());
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
                this.mWakelockController.acquireWakelock(6);
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
                if (this.mPowerState.getColorFadeLevel() == RATE_FROM_DOZE_TO_ON && this.mPowerState.prepareColorFade(this.mContext, 2)) {
                    Slog.m72d(this.mTag, "draw ColorFade due to unfolding");
                    this.mPowerState.setColorFadeLevel(RATE_FROM_DOZE_TO_ON);
                }
                this.mNeedPrepareColorFade = false;
            }
            if (setScreenState(2)) {
                if (this.mAutomaticBrightnessController != null && this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() && !this.mAutomaticBrightnessController.isAmbientLuxValid() && this.mPowerState.getScreenState() == 2 && Float.isNaN(this.mPowerRequest.screenBrightnessOverride) && !isLightSensorCovered() && (!this.mIsCoverDisplay || this.mDualScreenPolicy == 1)) {
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
            this.mPowerState.setColorFadeLevel(RATE_FROM_DOZE_TO_ON);
        }
        if (this.mPowerState.getColorFadeLevel() == RATE_FROM_DOZE_TO_ON) {
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

    public final void sendOnStateChangedWithWakelock() {
        if (this.mWakelockController.acquireWakelock(4)) {
            this.mHandler.post(this.mWakelockController.getOnStateChangedRunnable());
        }
    }

    public final void logDisplayPolicyChanged(int i) {
        LogMaker logMaker = new LogMaker(1696);
        logMaker.setType(6);
        logMaker.setSubtype(i);
        MetricsLogger.action(logMaker);
    }

    public final void handleSettingsChange(boolean z) {
        AutomaticBrightnessController automaticBrightnessController;
        DisplayBrightnessController displayBrightnessController = this.mDisplayBrightnessController;
        displayBrightnessController.setPendingScreenBrightness(displayBrightnessController.getScreenBrightnessSetting());
        this.mAutomaticBrightnessStrategy.updatePendingAutoBrightnessAdjustments(z);
        this.mAutomaticBrightnessStrategy.updateGameAutoBrightnessLock();
        if (z) {
            DisplayBrightnessController displayBrightnessController2 = this.mDisplayBrightnessController;
            displayBrightnessController2.setAndNotifyCurrentScreenBrightness(displayBrightnessController2.getPendingScreenBrightness());
            if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL && (automaticBrightnessController = this.mAutomaticBrightnessController) != null) {
                automaticBrightnessController.resetShortTermModel();
            }
        }
        if (!PowerManagerUtil.SHIP_BUILD && this.mIsCoverDisplay) {
            this.mCoverDisplayDemoEnabled = getCoverDisplayDemoSetting();
        }
        this.mPrevScreenBrightness = this.mDisplayBrightnessController.getCurrentBrightness();
        String format = String.format(" sb: %.3f abAdj: %.3f sbLock: %s", Float.valueOf(this.mDisplayBrightnessController.getPendingScreenBrightness()), Float.valueOf(this.mAutomaticBrightnessStrategy.getPendingAutoBrightnessAdjustment()), Boolean.valueOf(this.mAutomaticBrightnessStrategy.isGameAutoBrightnessLocked()));
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
        this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$handleBrightnessModeChange$7(intForUser);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleBrightnessModeChange$7(int i) {
        boolean shouldUseAutoBrightness = this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness();
        this.mAutomaticBrightnessStrategy.setUseAutoBrightness(i == 1);
        if (shouldUseAutoBrightness != this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness()) {
            if (!this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness() && this.mAutomaticBrightnessController != null) {
                this.mPowerHistorian.onAutoBrightnessEvent("ShortTermModel: reset data, manual");
                this.mAutomaticBrightnessController.resetShortTermModel();
            }
            updateLastBrightnessSettingChangedTime();
            if (PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY) {
                postBrightnessModeChangeRunnable();
            }
        }
        Slog.m72d(this.mTag, "[api] handleBrightnessModeChange: shouldUseAutoBrightness= " + this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness());
        lambda$new$0();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getScreenBrightnessSetting() {
        return this.mDisplayBrightnessController.getScreenBrightnessSetting();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightness(float f) {
        this.mDisplayBrightnessController.setBrightness(f);
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
        if (f2 < RATE_FROM_DOZE_TO_ON) {
            this.mDisplayBrightnessController.setBrightnessToFollow(Float.valueOf(f));
        } else {
            float convertToFloatScale = this.mDisplayBrightnessController.convertToFloatScale(f2);
            if (BrightnessUtils.isValidBrightnessValue(convertToFloatScale)) {
                this.mDisplayBrightnessController.setBrightnessToFollow(Float.valueOf(convertToFloatScale));
            } else {
                this.mDisplayBrightnessController.setBrightnessToFollow(Float.valueOf(f));
            }
        }
        sendUpdatePowerState();
    }

    public final void notifyBrightnessTrackerChanged(float f, boolean z, boolean z2, boolean z3, boolean z4) {
        AutomaticBrightnessController automaticBrightnessController;
        AutomaticBrightnessController automaticBrightnessController2;
        float convertToAdjustedNits = this.mDisplayBrightnessController.convertToAdjustedNits(f);
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
        if (!this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness() || convertToAdjustedNits < RATE_FROM_DOZE_TO_ON) {
            return;
        }
        if ((z && ((automaticBrightnessController2 = this.mAutomaticBrightnessController) == null || !automaticBrightnessController2.hasValidAmbientLux())) || this.mAutomaticBrightnessController.isHbmLux()) {
            z = false;
        }
        boolean z5 = z;
        if (BrightnessSynchronizer.floatEquals(this.mLastNotifiedBrightness, f)) {
            return;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mPowerRequest;
        float f2 = displayPowerRequest.screenBrightnessScaleFactor;
        if (f2 < RATE_FROM_DOZE_TO_ON || f2 == 1.0f) {
            this.mLastNotifiedBrightness = f;
            float f3 = displayPowerRequest.lowPowerMode ? displayPowerRequest.screenLowPowerBrightnessFactor : 1.0f;
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                this.mAdaptiveBrightnessLongtermModelBuilder.notifyBrightnessChanged(convertToAdjustedNits, z5, f3, z2, this.mAutomaticBrightnessController.isDefaultConfig(), this.mUniqueDisplayId, this.mAutomaticBrightnessController.getBrightnessSpline());
            } else {
                this.mBrightnessTracker.notifyBrightnessChanged(convertToAdjustedNits, z5, f3, z2, this.mAutomaticBrightnessController.isDefaultConfig(), this.mUniqueDisplayId, this.mAutomaticBrightnessController.getLastSensorValues(), this.mAutomaticBrightnessController.getLastSensorTimestamps());
            }
        }
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
            this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda2
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
            this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayPowerControllerInterface.this.setBrightnessToFollow(Float.NaN, -1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                }
            }, this.mClock.uptimeMillis());
        }
        this.mDisplayBrightnessFollowers.clear();
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
            printWriter.println("  mPendingUpdatePowerStateLocked=" + this.mPendingUpdatePowerStateLocked);
        }
        printWriter.println();
        printWriter.println("Display Power Controller Configuration:");
        printWriter.println("  mScreenBrightnessDozeConfig=" + this.mScreenBrightnessDozeConfig);
        printWriter.println("  mScreenBrightnessDimConfig=" + this.mScreenBrightnessDimConfig);
        printWriter.println("  mUseSoftwareAutoBrightnessConfig=" + this.mUseSoftwareAutoBrightnessConfig);
        printWriter.println("  mSkipScreenOnBrightnessRamp=" + this.mSkipScreenOnBrightnessRamp);
        printWriter.println("  mColorFadeFadesConfig=" + this.mColorFadeFadesConfig);
        printWriter.println("  mColorFadeEnabled=" + this.mColorFadeEnabled);
        printWriter.println("  mIsDisplayInternal=" + this.mIsDisplayInternal);
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
        this.mHandler.runWithScissors(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$dump$10(printWriter);
            }
        }, 1000L);
    }

    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public final void lambda$dump$10(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Display Power Controller Thread State:");
        printWriter.println("  mPowerRequest=" + this.mPowerRequest);
        printWriter.println("  mBrightnessReason=" + this.mBrightnessReason);
        printWriter.println("  mAppliedDimming=" + this.mAppliedDimming);
        printWriter.println("  mAppliedLowPower=" + this.mAppliedLowPower);
        printWriter.println("  mAppliedThrottling=" + this.mAppliedThrottling);
        printWriter.println("  mDozing=" + this.mDozing);
        printWriter.println("  mSkipRampState=" + skipRampStateToString(this.mSkipRampState));
        printWriter.println("  mScreenOnBlockStartRealTime=" + this.mScreenOnBlockStartRealTime);
        printWriter.println("  mScreenOffBlockStartRealTime=" + this.mScreenOffBlockStartRealTime);
        printWriter.println("  mPendingScreenOnUnblocker=" + this.mPendingScreenOnUnblocker);
        printWriter.println("  mPendingScreenOffUnblocker=" + this.mPendingScreenOffUnblocker);
        printWriter.println("  mPendingScreenOff=" + this.mPendingScreenOff);
        printWriter.println("  mReportedToPolicy=" + reportedToPolicyToString(this.mReportedScreenStateToPolicy));
        printWriter.println("  mIsRbcActive=" + this.mIsRbcActive);
        this.mAutomaticBrightnessStrategy.dump(new IndentingPrintWriter(printWriter, "    "));
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
        dumpRbcEvents(printWriter);
        HighBrightnessModeController highBrightnessModeController = this.mHbmController;
        if (highBrightnessModeController != null) {
            highBrightnessModeController.dump(printWriter);
        }
        BrightnessThrottler brightnessThrottler = this.mBrightnessThrottler;
        if (brightnessThrottler != null) {
            brightnessThrottler.dump(printWriter);
        }
        printWriter.println();
        printWriter.println();
        WakelockController wakelockController = this.mWakelockController;
        if (wakelockController != null) {
            wakelockController.dumpLocal(printWriter);
        }
        printWriter.println();
        DisplayBrightnessController displayBrightnessController = this.mDisplayBrightnessController;
        if (displayBrightnessController != null) {
            displayBrightnessController.dump(printWriter);
        }
        printWriter.println();
        DisplayStateController displayStateController = this.mDisplayStateController;
        if (displayStateController != null) {
            displayStateController.dumpsys(printWriter);
        }
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

    public final void dumpRbcEvents(PrintWriter printWriter) {
        int size = this.mRbcEventRingBuffer.size();
        if (size < 1) {
            printWriter.println("No Reduce Bright Colors Adjustments");
            return;
        }
        printWriter.println("Reduce Bright Colors Adjustments Last " + size + " Events: ");
        BrightnessEvent[] brightnessEventArr = (BrightnessEvent[]) this.mRbcEventRingBuffer.toArray();
        for (int i = 0; i < this.mRbcEventRingBuffer.size(); i++) {
            printWriter.println("  " + brightnessEventArr[i]);
        }
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
                this.mHandler.removeMessages(11);
                if (z != z2) {
                    logHbmBrightnessStats(f, this.mDisplayStatsId);
                    return;
                }
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = 11;
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
        float convertToAdjustedNits = this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getBrightness());
        float powerFactor = brightnessEvent.isLowPowerModeSet() ? brightnessEvent.getPowerFactor() : -1.0f;
        int rbcStrength = brightnessEvent.isRbcEnabled() ? brightnessEvent.getRbcStrength() : -1;
        float convertToAdjustedNits2 = brightnessEvent.getHbmMode() == 0 ? -1.0f : this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getHbmMax());
        float convertToAdjustedNits3 = brightnessEvent.getThermalMax() == 1.0f ? -1.0f : this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getThermalMax());
        if (this.mIsDisplayInternal) {
            FrameworkStatsLog.write(FrameworkStatsLog.DISPLAY_BRIGHTNESS_CHANGED, this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getInitialBrightness()), convertToAdjustedNits, brightnessEvent.getLux(), brightnessEvent.getPhysicalDisplayId(), brightnessEvent.wasShortTermModelActive(), powerFactor, rbcStrength, convertToAdjustedNits2, convertToAdjustedNits3, brightnessEvent.isAutomaticBrightnessEnabled(), 1, convertBrightnessReasonToStatsEnum(brightnessEvent.getReason().getReason()), nitsToRangeIndex(convertToAdjustedNits), z, brightnessEvent.getHbmMode() == 1, brightnessEvent.getHbmMode() == 2, (modifier & 2) > 0, this.mBrightnessThrottler.getBrightnessMaxReason(), (modifier & 1) > 0, brightnessEvent.isRbcEnabled(), (flags & 2) > 0, (flags & 4) > 0, (flags & 8) > 0, (flags & 16) > 0, (flags & 32) > 0);
        }
    }

    public final boolean readyToUpdateDisplayState() {
        return this.mDisplayId == 0 || this.mBootCompleted || this.mIsCoverDisplay;
    }

    public final class DisplayControllerHandler extends Handler {
        public DisplayControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z = false;
            switch (message.what) {
                case 1:
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 2:
                    if (DisplayPowerController2.this.mPendingScreenOnUnblocker == message.obj) {
                        DisplayPowerController2.this.unblockScreenOn();
                        DisplayPowerController2.this.lambda$new$0();
                        break;
                    }
                    break;
                case 3:
                    if (DisplayPowerController2.this.mPendingScreenOffUnblocker == message.obj) {
                        DisplayPowerController2.this.unblockScreenOff();
                        DisplayPowerController2.this.lambda$new$0();
                        break;
                    }
                    break;
                case 4:
                    BrightnessConfiguration brightnessConfiguration = (BrightnessConfiguration) message.obj;
                    DisplayPowerController2.this.mAutomaticBrightnessStrategy.setBrightnessConfiguration(brightnessConfiguration, message.arg1 == 1);
                    if (message.arg2 > 0) {
                        DisplayPowerController2.this.mResetBrightnessConfiguration = true;
                    }
                    if (DisplayPowerController2.this.mBrightnessTracker != null) {
                        BrightnessTracker brightnessTracker = DisplayPowerController2.this.mBrightnessTracker;
                        if (brightnessConfiguration != null && brightnessConfiguration.shouldCollectColorSamples()) {
                            z = true;
                        }
                        brightnessTracker.setShouldCollectColorSample(z);
                    }
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 5:
                    DisplayPowerController2.this.mDisplayBrightnessController.setTemporaryBrightness(Float.valueOf(Float.intBitsToFloat(message.arg1)));
                    if (message.arg2 > 0) {
                        Slog.m72d(DisplayPowerController2.this.mTag, "[api] ForceSlowChange is requested from DisplayManager");
                        DisplayPowerController2.this.mForceSlowChange = true;
                    }
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 6:
                    DisplayPowerController2.this.mAutomaticBrightnessStrategy.setTemporaryAutoBrightnessAdjustment(Float.intBitsToFloat(message.arg1));
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 7:
                    DisplayPowerController2.this.cleanupHandlerThreadAfterStop();
                    break;
                case 8:
                    if (!DisplayPowerController2.this.mStopped) {
                        DisplayPowerController2.this.handleSettingsChange(false);
                        break;
                    }
                    break;
                case 9:
                    DisplayPowerController2.this.handleRbcChanged();
                    break;
                case 10:
                    if (DisplayPowerController2.this.mPowerState != null) {
                        DisplayPowerController2.this.reportStats(DisplayPowerController2.this.mPowerState.getScreenBrightness());
                        break;
                    }
                    break;
                case 11:
                    DisplayPowerController2.this.logHbmBrightnessStats(Float.intBitsToFloat(message.arg1), message.arg2);
                    break;
                case 12:
                    DisplayPowerController2.this.handleOnSwitchUser(message.arg1);
                    break;
                case 13:
                    DisplayPowerController2.this.mBootCompleted = true;
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 14:
                    DisplayPowerController2.this.mSeamlessAodReady = true;
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 15:
                    DisplayPowerController2.this.lambda$new$0();
                    break;
                case 16:
                    DisplayPowerController2.this.restartAdaptiveBrightnessLongtermModelBuilderInternal(true);
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
            Slog.m72d(DisplayPowerController2.this.mTag, "[api] SettingsObserver: onChange: " + uri);
            if (uri.equals(Settings.System.getUriFor(DisplayPowerController2.this.mScreenBrightnessModeSettingName))) {
                DisplayPowerController2.this.handleBrightnessModeChange();
            } else {
                DisplayPowerController2.this.handleSettingsChange(false);
            }
        }
    }

    public final class ScreenOnUnblocker implements WindowManagerPolicy.ScreenOnListener {
        public ScreenOnUnblocker() {
        }

        @Override // com.android.server.policy.WindowManagerPolicy.ScreenOnListener
        public void onScreenOn() {
            Slog.m72d(DisplayPowerController2.this.mTag, "[api] WindowManagerPolicy.ScreenOnListener : called onScreenOn()");
            DisplayPowerController2.this.mHandler.sendMessageAtTime(DisplayPowerController2.this.mHandler.obtainMessage(2, this), DisplayPowerController2.this.mClock.uptimeMillis());
        }
    }

    public final class ScreenOffUnblocker implements WindowManagerPolicy.ScreenOffListener {
        public ScreenOffUnblocker() {
        }

        @Override // com.android.server.policy.WindowManagerPolicy.ScreenOffListener
        public void onScreenOff() {
            Slog.m72d(DisplayPowerController2.this.mTag, "[api] WindowManagerPolicy.ScreenOffListener : called onScreenOff()");
            DisplayPowerController2.this.mHandler.sendMessageAtTime(DisplayPowerController2.this.mHandler.obtainMessage(3, this), DisplayPowerController2.this.mClock.uptimeMillis());
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAutoBrightnessLoggingEnabled(boolean z) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.setLoggingEnabled(z);
        }
    }

    class Injector {
        public Clock getClock() {
            return new Clock() { // from class: com.android.server.display.DisplayPowerController2$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.DisplayPowerController2.Clock
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

        public WakelockController getWakelockController(int i, DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks) {
            return new WakelockController(i, displayPowerCallbacks);
        }

        public DisplayPowerProximityStateController getDisplayPowerProximityStateController(WakelockController wakelockController, DisplayDeviceConfig displayDeviceConfig, Looper looper, Runnable runnable, int i, SensorManager sensorManager) {
            return new DisplayPowerProximityStateController(wakelockController, displayDeviceConfig, looper, runnable, i, sensorManager, null);
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
        public MutableBoolean isAnimating = new MutableBoolean(false);

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

        public boolean checkAndSetBoolean(MutableBoolean mutableBoolean, boolean z) {
            if (mutableBoolean.value == z) {
                return false;
            }
            mutableBoolean.value = z;
            return true;
        }
    }

    public final class ShutdownReceiver extends BroadcastReceiver {
        public ShutdownReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (DisplayPowerController2.this.mInteractiveModeBrightnessMapper == null || !"android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction()) || !DisplayPowerController2.this.mAppliedForceDimming || DisplayPowerController2.this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness() || Float.isNaN(DisplayPowerController2.this.mLastScreenBrightnessSettingBeforeForceDim)) {
                return;
            }
            Slog.m72d(DisplayPowerController2.this.mTag, "Restore low battery force dim (manual brightness)");
            DisplayPowerController2.this.updateLastBrightnessSettingChangedTime();
            DisplayPowerController2.this.mAppliedForceDimming = false;
            DisplayPowerController2.this.mDisplayBrightnessController.setBrightness(DisplayPowerController2.this.mLastScreenBrightnessSettingBeforeForceDim);
        }
    }

    public final boolean isLightSensorCovered() {
        return this.mPowerRequest.coverClosed;
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
        if (f > RATE_FROM_DOZE_TO_ON) {
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
        float f3 = RATE_FROM_DOZE_TO_ON;
        if (f2 >= RATE_FROM_DOZE_TO_ON && f2 != 1.0f) {
            f = clampScreenBrightnessForFinal(f * f2);
            this.mBrightnessReasonTemp.addModifier(16, f);
        }
        if (this.mPowerRequest.hbmBlock && f > 1.0f) {
            this.mBrightnessReasonTemp.addModifier(256, 1.0f);
            f = 1.0f;
        }
        float f4 = this.mPowerRequest.minBrightness;
        if (f4 >= RATE_FROM_DOZE_TO_ON && f < f4) {
            this.mBrightnessReasonTemp.addModifier(32, f4);
            f = f4;
        }
        float f5 = this.mPowerRequest.maxBrightness;
        if (f5 >= RATE_FROM_DOZE_TO_ON && f > f5) {
            this.mBrightnessReasonTemp.addModifier(32, f5);
            f = f5;
        }
        int i2 = this.mPowerRequest.brightnessLimitByCover;
        if (i2 != -1 && f > i2) {
            f = i2;
            this.mBrightnessReasonTemp.addModifier(128, f);
        }
        if (this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness()) {
            float f6 = this.mPowerRequest.autoBrightnessUpperLimit;
            if (f6 >= RATE_FROM_DOZE_TO_ON && f > f6) {
                this.mBrightnessReasonTemp.addModifier(64, f6);
                f = f6;
            }
            float f7 = this.mPowerRequest.autoBrightnessLowerLimit;
            if (f7 >= RATE_FROM_DOZE_TO_ON && !this.mAppliedDimming && !this.mAppliedForceDimming && f < f7) {
                this.mBrightnessReasonTemp.addModifier(64, f7);
                f = f7;
            }
        }
        if (this.mFreezeBrightnessMode) {
            int i3 = this.mFreezeBrightnessModeSelector;
            if (i3 != 1) {
                if (i3 == 2 && f <= 1.0f) {
                    f = BrightnessSynchronizer.brightnessIntToFloat(256);
                    this.mBrightnessReasonTemp.addModifier(65536, f);
                }
            } else if (f > 1.0f) {
                this.mBrightnessReasonTemp.addModifier(65536, 1.0f);
                f = 1.0f;
            }
        }
        if (PowerManagerUtil.isFakeAodAvailable(this.mDualScreenPolicy) && Display.isDozeState(i) && f > 0.38f) {
            this.mBrightnessReasonTemp.addModifier(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, 0.38f);
            f = 0.38f;
        }
        if (this.mPowerRequest.forceLcdBacklightOffEnabled) {
            this.mBrightnessReasonTemp.addModifier(1024, RATE_FROM_DOZE_TO_ON);
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
    public void setActualDisplayState(final int i) {
        this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.display.DisplayPowerController2$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                DisplayPowerController2.this.lambda$setActualDisplayState$11(i);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActualDisplayState$11(int i) {
        if (i != this.mActualDisplayState) {
            Slog.m72d(this.mTag, "setActualDisplayState: " + Display.stateToString(this.mActualDisplayState) + " -> " + Display.stateToString(i));
            this.mActualDisplayState = i;
            lambda$new$0();
        }
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
        this.mHandler.obtainMessage(16).sendToTarget();
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
            Slog.m72d(DisplayPowerController2.this.mTag, "[api] AODManager.AODChangeListener : Received readyToScreenTurningOn().");
            Message obtainMessage = DisplayPowerController2.this.mHandler.obtainMessage(14);
            obtainMessage.setAsynchronous(true);
            DisplayPowerController2.this.mHandler.sendMessage(obtainMessage);
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
        public final Runnable mEarlyLightSensorReadyListener = new Runnable() { // from class: com.android.server.display.DisplayPowerController2.EarlyWakeUpManager.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (EarlyWakeUpManager.this.mEarlyWakeUpLock) {
                    EarlyWakeUpManager.this.mEarlyLightSensorReadyLocked = true;
                    EarlyWakeUpManager.this.updateSuspendBlockerLocked();
                }
            }
        };

        public EarlyWakeUpManager() {
            HandlerThread handlerThread = new HandlerThread(DisplayPowerController2.this.mTag, -4);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            this.mHandler = new EarlyWakeUpHandler(handlerThread.getLooper());
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x003c A[Catch: all -> 0x0073, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000c, B:8:0x0010, B:12:0x001d, B:14:0x0021, B:20:0x0030, B:22:0x0038, B:24:0x003c, B:26:0x0058, B:28:0x0067, B:29:0x006a, B:30:0x005f, B:31:0x006c, B:32:0x0071), top: B:3:0x0003 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void update(boolean z, int i) {
            boolean z2;
            synchronized (this.mEarlyWakeUpLock) {
                long uptimeMillis = SystemClock.uptimeMillis();
                if ((i == 2 || this.mIsRequestInvalidated || DisplayPowerController2.this.mIsInTransition) ? false : true) {
                    boolean z3 = this.mAppliedLocked && !isLastRequestExpired(uptimeMillis);
                    if (z || z3) {
                        if (z) {
                            this.mLastEnableRequestedTime = uptimeMillis;
                            resetEnableRequestTimeout();
                        }
                        z2 = true;
                        if (z2 != this.mAppliedLocked) {
                            Slog.m72d(DisplayPowerController2.this.mTag, "[ew] " + z2);
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
                    DisplayPowerController2.this.mAutomaticBrightnessController.setEarlyLightSensorEnabled(z, this.mEarlyLightSensorReadyListener);
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
                this.mHandler.post(new Runnable() { // from class: com.android.server.display.DisplayPowerController2.EarlyWakeUpManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        DisplayPowerController2.this.mBlanker.setDisplayStateLimitForEarlyWakeUp(DisplayPowerController2.this.mDisplayId, z ? 2 : 0);
                        synchronized (EarlyWakeUpManager.this.mEarlyWakeUpLock) {
                            EarlyWakeUpManager.this.mEarlyDisplayReadyLocked = true;
                            EarlyWakeUpManager.this.updateSuspendBlockerLocked();
                        }
                    }
                });
            }
        }

        public final boolean supportEarlyLightSensorEnableLocked() {
            return DisplayPowerController2.this.mAutomaticBrightnessController != null;
        }

        public final boolean supportEarlyDisplayEnableLocked(int i) {
            return (Display.isDozeState(i) || PowerManagerUtil.SECURITY_FINGERPRINT_IN_DISPLAY || PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD_LIVE_CLOCK) ? false : true;
        }

        public final boolean isCandidateForAutoBrightness() {
            return DisplayPowerController2.this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness() && !DisplayPowerController2.this.mDisplayPowerProximityStateController.isProximityPositive();
        }

        public final boolean isLastRequestExpired(long j) {
            return j >= this.mLastEnableRequestedTime + 3000;
        }

        public final void clearEnableRequestTimeout() {
            Slog.m72d(DisplayPowerController2.this.mTag, "[ew] clearEnableRequestTimeout -");
            this.mHandler.removeMessages(1);
        }

        public final void resetEnableRequestTimeout() {
            Slog.m72d(DisplayPowerController2.this.mTag, "[ew] resetEnableRequestTimeout +");
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageAtTime(1, this.mLastEnableRequestedTime + 3000);
        }

        public final void updateSuspendBlockerLocked() {
            if ((!this.mAppliedLocked && this.mEarlyDisplayReadyLocked && this.mEarlyLightSensorReadyLocked) ? false : true) {
                if (this.mHoldingSuspendBlocker) {
                    return;
                }
                Slog.m72d(DisplayPowerController2.this.mTag, "[ew] acquireSuspendBlocker: +");
                DisplayPowerController2.this.mWakelockController.acquireWakelock(7);
                this.mHoldingSuspendBlocker = true;
                return;
            }
            if (this.mHoldingSuspendBlocker) {
                Slog.m72d(DisplayPowerController2.this.mTag, "[ew] releaseSuspendBlocker: -");
                this.mHoldingSuspendBlocker = false;
                DisplayPowerController2.this.mWakelockController.releaseWakelock(7);
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
                Slog.m72d(DisplayPowerController2.this.mTag, "[ew] MSG_EARLY_WAKEUP_TIMEOUT");
                DisplayPowerController2.this.sendUpdatePowerState();
            }
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getLastAutomaticScreenBrightness() {
        return this.mLastAutomaticScreenBrightness;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getCurrentScreenBrightness() {
        return this.mDisplayBrightnessController.getCurrentBrightness();
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
        if (f <= RATE_FROM_DOZE_TO_ON) {
            f = 0.004f;
        }
        float currentValue = this.mScreenBrightnessRampAnimator.getCurrentValue();
        if (currentValue <= RATE_FROM_DOZE_TO_ON) {
            currentValue = 0.004f;
        }
        float f4 = this.mLastAmbientLux;
        if (f2 <= RATE_FROM_DOZE_TO_ON) {
            f2 = 0.9f;
        }
        if (f4 <= RATE_FROM_DOZE_TO_ON) {
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
        this.mBrightnessRampRateHdrIncrease = f;
        this.mBrightnessRampRateHdrDecrease = f2;
    }
}
