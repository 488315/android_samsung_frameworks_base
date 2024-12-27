package com.android.server.display;

import android.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessChangeEvent;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManagerInternal;
import android.metrics.LogMaker;
import android.net.Uri;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.GLES20;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AtomicFile;
import android.util.MathUtils;
import android.util.MutableBoolean;
import android.util.MutableFloat;
import android.util.MutableInt;
import android.util.SparseArray;
import android.util.Spline;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.PathInterpolator;

import com.android.internal.app.IBatteryStats;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.BatteryStatsService;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.display.AdaptiveBrightnessAdvancedSettings.PackageRemovedReceiver;
import com.android.server.display.AdaptiveBrightnessAdvancedSettings.SettingsObserver;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.BrightnessReason;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.DisplayBrightnessController;
import com.android.server.display.brightness.clamper.BrightnessClamperController;
import com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda3;
import com.android.server.display.brightness.clamper.HdrClamper;
import com.android.server.display.brightness.strategy.AutoBrightnessFallbackStrategy;
import com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2;
import com.android.server.display.brightness.strategy.OffloadBrightnessStrategy;
import com.android.server.display.config.DisplayBrightnessMappingConfig;
import com.android.server.display.config.EvenDimmerBrightnessData;
import com.android.server.display.config.HysteresisLevels;
import com.android.server.display.config.SecHysteresisLevels;
import com.android.server.display.config.SensorData;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.state.DisplayStateController;
import com.android.server.display.utils.DebugUtils;
import com.android.server.display.utils.SensorUtils;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.HqmDataDispatcher;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

import com.samsung.android.aod.AODManager;
import com.samsung.android.aod.AODManagerInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;

public final class DisplayPowerController
        implements AutomaticBrightnessController.Callbacks, DisplayPowerControllerInterface {
    public static final float[] BRIGHTNESS_RANGE_BOUNDARIES;
    public static final int[] BRIGHTNESS_RANGE_INDEX;
    public static final int DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION;
    public static final int MAX_AUTO_BRIGHTNESS_TRANSITION_TIME;
    public static final float RATE_FROM_DOZE_TO_ON;
    public static long sLastScreenBrightnessSettingChangedTime;
    public BrightnessMappingStrategy defaultModeBrightnessMapper;
    public int mActualDisplayState;
    public final AdaptiveBrightnessAdvancedSettings mAdaptiveBrightnessAdvancedSettings;
    public final AdaptiveBrightnessLongtermModelBuilder mAdaptiveBrightnessLongtermModelBuilder;
    public final AnonymousClass2 mAnimatorListener;
    public final AODManagerInternal mAodManagerInternal;
    public boolean mAppliedForceDimming;
    public float mAppliedForceDimmingBrightness;
    public boolean mAutoBrightnessEnabled;
    public AutomaticBrightnessController mAutomaticBrightnessController;
    public final AutomaticBrightnessStrategy2 mAutomaticBrightnessStrategy;
    public boolean mAwakenFromDozingInAutoBrightness;
    public boolean mBatteryLevelCritical;
    public final IBatteryStats mBatteryStats;
    public final DisplayBlanker mBlanker;
    public boolean mBootCompleted;
    public boolean mBrightnessAnimationConsumerInvoked;
    public final AnonymousClass3 mBrightnessAnimationEndRunnable;
    public final boolean mBrightnessBucketsInDozeConfig;
    public final BrightnessClamperController mBrightnessClamperController;
    public RingBuffer mBrightnessEventRingBuffer;
    public long mBrightnessRampDecreaseMaxTimeIdleMillis;
    public long mBrightnessRampDecreaseMaxTimeMillis;
    public long mBrightnessRampIncreaseMaxTimeIdleMillis;
    public long mBrightnessRampIncreaseMaxTimeMillis;
    public float mBrightnessRampRateFastDecrease;
    public float mBrightnessRampRateFastIncrease;
    public float mBrightnessRampRateHdrDecrease;
    public float mBrightnessRampRateHdrIncrease;
    public float mBrightnessRampRateSlowDecrease;
    public float mBrightnessRampRateSlowDecreaseIdle;
    public float mBrightnessRampRateSlowIncrease;
    public float mBrightnessRampRateSlowIncreaseIdle;
    public final BrightnessRangeController mBrightnessRangeController;
    final BrightnessReason mBrightnessReason;
    public final BrightnessReason mBrightnessReasonTemp;
    public final BrightnessThrottler mBrightnessThrottler;
    public final BrightnessTracker mBrightnessTracker;
    public final CachedBrightnessInfo mCachedBrightnessInfo;
    public float mClampedMaxBrightness;
    public final DisplayPowerController$$ExternalSyntheticLambda2 mCleanListener;
    public final DisplayPowerController$$ExternalSyntheticLambda11 mClock;
    public final boolean mColorFadeEnabled;
    public final boolean mColorFadeFadesConfig;
    public ObjectAnimator mColorFadeOffAnimator;
    public ObjectAnimator mColorFadeOnAnimator;
    public final Context mContext;
    public final boolean mDisplayBlanksAfterDozeConfig;
    public final DisplayBrightnessController mDisplayBrightnessController;
    public final SparseArray mDisplayBrightnessFollowers;
    public DisplayDevice mDisplayDevice;
    public DisplayDeviceConfig mDisplayDeviceConfig;
    public final int mDisplayId;
    public DisplayManagerInternal.DisplayOffloadSession mDisplayOffloadSession;
    public final DisplayPowerProximityStateController mDisplayPowerProximityStateController;
    public boolean mDisplayReadyLocked;
    public final DisplayStateController mDisplayStateController;
    public int mDisplayStatsId;
    public final float mDozeScaleFactor;
    public boolean mDozing;
    public int mDualScreenPolicy;
    public final boolean mEarlyWakeUpEnabled;
    public final EarlyWakeUpManager mEarlyWakeUpManager;
    public final int mExtraDimStrength;
    public final DisplayManagerFlags mFlags;
    public float mFollowerRampSpeed;
    public float mFollowerRampSpeedAtHbm;
    public boolean mForceDimSettingEnabled;
    public boolean mForceSlowChange;
    public boolean mFreezeBrightnessMode;
    public int mFreezeBrightnessModeSelector;
    public final DisplayControllerHandler mHandler;
    public HqmDataDispatcher mHqmDataDispatcher;
    public boolean mInitialAutoBrightnessUpdated;
    public final Injector mInjector;
    public final boolean mIsCoverDisplay;
    public boolean mIsDisplayInternal;
    public boolean mIsEnabled;
    public boolean mIsInTransition;
    public boolean mIsOutdoorModeEnabled;
    public final boolean mIsSupportedAodMode;
    public float mLastAmbientLux;
    public float mLastAutomaticScreenBrightness;
    public long mLastBatteryLevelCriticalTime;
    public final BrightnessEvent mLastBrightnessEvent;
    public boolean mLastCoverClosedState;
    public float mLastNotifiedBrightness;
    public float mLastOriginalTarget;
    public float mLastScreenBrightnessSettingBeforeForceDim;
    public float mLastStatsBrightness;
    public boolean mLcdFlashModeEnabled;
    public int mLeadDisplayId;
    public Sensor mLightSensor;
    public final Object mLock;
    public final LogicalDisplay mLogicalDisplay;
    public final float mMoreFastRampRate;
    public boolean mNeedPrepareColorFade;
    public float[] mNitsRange;
    public final Consumer mOnBrightnessAnimationConsumer;
    public final Runnable mOnBrightnessChangeRunnable;
    public final Runnable mOnBrightnessModeChangeRunnable;
    public boolean mPassRampAnimation;
    public boolean mPendingEarlyWakeUpRequestLocked;
    public boolean mPendingForceSlowChangeLocked;
    public boolean mPendingRequestChangedLocked;
    public DisplayManagerInternal.DisplayPowerRequest mPendingRequestLocked;
    public boolean mPendingScreenOff;
    public AnonymousClass4 mPendingScreenOffUnblocker;
    public boolean mPendingScreenOnByAodReady;
    public ScreenOnUnblocker mPendingScreenOnUnblocker;
    public DisplayPowerController$$ExternalSyntheticLambda7
            mPendingScreenOnUnblockerByDisplayOffload;
    public boolean mPendingTransitionOffInDualCase;
    public boolean mPendingUpdatePowerStateLocked;
    public final PowerHistorian mPowerHistorian;
    public DisplayManagerInternal.DisplayPowerRequest mPowerRequest;
    public DisplayPowerState mPowerState;
    public float mPrevScreenBrightness;
    public final AnonymousClass4 mRampAnimatorListener;
    public final RingBuffer mRbcEventRingBuffer;
    public int mReportedScreenStateToPolicy;
    public boolean mResetBrightnessConfiguration;
    public final float mScreenBrightnessDimConfig;
    public final float mScreenBrightnessDozeConfig;
    public final float mScreenBrightnessMinimumDimAmount;
    public final String mScreenBrightnessModeSettingName;
    public RampAnimator.DualRampAnimator mScreenBrightnessRampAnimator;
    public final float mScreenExtendedBrightnessRangeMaximum;
    public long mScreenOffBlockStartRealTime;
    public ScreenOffBrightnessSensorController mScreenOffBrightnessSensorController;
    public long mScreenOnBlockByDisplayOffloadStartRealTime;
    public long mScreenOnBlockStartRealTime;
    public boolean mScreenTurningOnWasBlockedByDisplayOffload;
    public boolean mSeamlessAodReady;
    public final SeamlessAodReadyListener mSeamlessAodReadyListener;
    public final SensorManager mSensorManager;
    public final SettingsObserver mSettingsObserver;
    public final ShutdownReceiver mShutdownReceiver;
    public boolean mStopped;
    public final String mTag;
    public final BrightnessEvent mTempBrightnessEvent;
    public String mThermalBrightnessThrottlingDataId;
    public String mUniqueDisplayId;
    public boolean mUseSoftwareAutoBrightnessConfig;
    public boolean mWaitingAutoBrightnessFromDoze;
    public final WakelockController mWakelockController;
    public final WindowManagerPolicy mWindowManagerPolicy;
    public static final boolean DEBUG = DebugUtils.isDebuggable("DisplayPowerController2");
    public static final boolean SAMSUNG_UX_COLOR_FADE_OFF_EFFECT_ENABLED = true;
    public static final PathInterpolator COLOR_FADE_PATH_INTERPOLATOR =
            new PathInterpolator(0.45f, 0.18f, 0.35f, 1.0f);
    public static final AccelerateDecelerateInterpolator COLOR_FADE_DEFAULT_INTERPOLATOR =
            new AccelerateDecelerateInterpolator();

    /* renamed from: com.android.server.display.DisplayPowerController$4, reason: invalid class name */
    public final class AnonymousClass4 implements WindowManagerPolicy.ScreenOffListener {
        public /* synthetic */ AnonymousClass4() {}

        public void onAnimationEnd() {
            boolean z = CoreRune.FW_VRR_REFRESH_RATE_TOKEN;
            DisplayPowerController displayPowerController = DisplayPowerController.this;
            if (z) {
                displayPowerController.mHandler.removeCallbacks(
                        displayPowerController.mBrightnessAnimationEndRunnable);
                displayPowerController.mHandler.postDelayed(
                        displayPowerController.mBrightnessAnimationEndRunnable, 200L);
            }
            displayPowerController.sendUpdatePowerState();
            DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
            Message obtainMessage = displayControllerHandler.obtainMessage(10);
            displayPowerController.mClock.getClass();
            displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
        }

        public void onScreenOff() {
            DisplayPowerController displayPowerController = DisplayPowerController.this;
            Slog.d(
                    displayPowerController.mTag,
                    "[api] WindowManagerPolicy.ScreenOffListener : called onScreenOff()");
            DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
            Message obtainMessage = displayControllerHandler.obtainMessage(3, this);
            displayPowerController.mClock.getClass();
            displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
        }
    }

    public final class BrightnessDynamicRampRatePair {
        public float brightnessRampRateDynamic;
        public float brightnessRampRateDynamicAtHbm;
    }

    public final class CachedBrightnessInfo {
        public MutableFloat adjustedBrightness;
        public MutableFloat brightness;
        public MutableFloat brightnessMax;
        public MutableInt brightnessMaxReason;
        public MutableFloat brightnessMin;
        public MutableInt hbmMode;
        public MutableFloat hbmTransitionPoint;
        public MutableBoolean isAnimating;

        public static boolean checkAndSetFloat(MutableFloat mutableFloat, float f) {
            if (mutableFloat.value == f) {
                return false;
            }
            mutableFloat.value = f;
            return true;
        }
    }

    interface Clock {}

    public final class DisplayControllerHandler extends Handler {
        public DisplayControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ScreenOffBrightnessSensorController screenOffBrightnessSensorController;
            AutomaticBrightnessController automaticBrightnessController;
            BrightnessTracker brightnessTracker;
            switch (message.what) {
                case 1:
                    DisplayPowerController.this.updatePowerState();
                    return;
                case 2:
                    DisplayPowerController displayPowerController = DisplayPowerController.this;
                    if (displayPowerController.mPendingScreenOnUnblocker == message.obj) {
                        displayPowerController.unblockScreenOn();
                        DisplayPowerController.this.updatePowerState();
                        return;
                    }
                    return;
                case 3:
                    DisplayPowerController displayPowerController2 = DisplayPowerController.this;
                    if (displayPowerController2.mPendingScreenOffUnblocker == message.obj) {
                        displayPowerController2.unblockScreenOff();
                        DisplayPowerController.this.updatePowerState();
                        return;
                    }
                    return;
                case 4:
                    BrightnessConfiguration brightnessConfiguration =
                            (BrightnessConfiguration) message.obj;
                    DisplayPowerController.this.mAutomaticBrightnessStrategy
                            .setBrightnessConfiguration(brightnessConfiguration, message.arg1 == 1);
                    if (message.arg2 > 0) {
                        DisplayPowerController.this.mResetBrightnessConfiguration = true;
                    }
                    BrightnessTracker brightnessTracker2 =
                            DisplayPowerController.this.mBrightnessTracker;
                    if (brightnessTracker2 != null) {
                        if (brightnessConfiguration != null
                                && brightnessConfiguration.shouldCollectColorSamples()) {
                            r2 = true;
                        }
                        brightnessTracker2
                                .mBgHandler
                                .obtainMessage(4, Boolean.valueOf(r2))
                                .sendToTarget();
                    }
                    DisplayPowerController.this.updatePowerState();
                    return;
                case 5:
                    DisplayBrightnessController displayBrightnessController =
                            DisplayPowerController.this.mDisplayBrightnessController;
                    float intBitsToFloat = Float.intBitsToFloat(message.arg1);
                    synchronized (displayBrightnessController.mLock) {
                        displayBrightnessController
                                        .mDisplayBrightnessStrategySelector
                                        .mTemporaryBrightnessStrategy
                                        .mTemporaryScreenBrightness =
                                intBitsToFloat;
                    }
                    if (message.arg2 > 0) {
                        Slog.d(
                                DisplayPowerController.this.mTag,
                                "[api] ForceSlowChange is requested from DisplayManager");
                        DisplayPowerController.this.mForceSlowChange = true;
                    }
                    DisplayPowerController.this.updatePowerState();
                    return;
                case 6:
                    DisplayPowerController.this.mAutomaticBrightnessStrategy
                            .setTemporaryAutoBrightnessAdjustment(
                                    Float.intBitsToFloat(message.arg1));
                    DisplayPowerController.this.updatePowerState();
                    return;
                case 7:
                    DisplayPowerController displayPowerController3 = DisplayPowerController.this;
                    displayPowerController3.mDisplayPowerProximityStateController
                            .setProximitySensorEnabled(false);
                    BrightnessRangeController brightnessRangeController =
                            displayPowerController3.mBrightnessRangeController;
                    HighBrightnessModeController highBrightnessModeController =
                            brightnessRangeController.mHbmController;
                    IBinder iBinder = highBrightnessModeController.mRegisteredDisplayToken;
                    if (iBinder != null) {
                        if (iBinder != null) {
                            highBrightnessModeController.mHdrListener.unregister(iBinder);
                            highBrightnessModeController.mIsHdrLayerPresent = false;
                        }
                        highBrightnessModeController.mRegisteredDisplayToken = null;
                    }
                    HighBrightnessModeController.SettingsObserver settingsObserver =
                            highBrightnessModeController.mSettingsObserver;
                    HighBrightnessModeController highBrightnessModeController2 =
                            HighBrightnessModeController.this;
                    highBrightnessModeController2.mIsBlockedByLowPowerMode = false;
                    if (settingsObserver.mStarted) {
                        highBrightnessModeController2
                                .mContext
                                .getContentResolver()
                                .unregisterContentObserver(settingsObserver);
                        settingsObserver.mStarted = false;
                    }
                    HdrClamper hdrClamper = brightnessRangeController.mHdrClamper;
                    IBinder iBinder2 = hdrClamper.mRegisteredDisplayToken;
                    if (iBinder2 != null) {
                        hdrClamper.mHdrListener.unregister(iBinder2);
                    }
                    BrightnessThrottler brightnessThrottler =
                            displayPowerController3.mBrightnessThrottler;
                    brightnessThrottler.mSkinThermalStatusObserver.stopObserving();
                    brightnessThrottler.mConfigParameterProvider.mDeviceConfig
                            .removeOnPropertiesChangedListener(
                                    brightnessThrottler.mDeviceConfigListener);
                    brightnessThrottler.mBrightnessCap = 1.0f;
                    brightnessThrottler.mBrightnessMaxReason = 0;
                    brightnessThrottler.mThrottlingStatus = -1;
                    BrightnessClamperController brightnessClamperController =
                            displayPowerController3.mBrightnessClamperController;
                    brightnessClamperController.mDeviceConfigParameterProvider.mDeviceConfig
                            .removeOnPropertiesChangedListener(
                                    brightnessClamperController.mOnPropertiesChangedListener);
                    ((ArrayList) brightnessClamperController.mClampers)
                            .forEach(new BrightnessClamperController$$ExternalSyntheticLambda3(2));
                    ((ArrayList) brightnessClamperController.mModifiers)
                            .forEach(new BrightnessClamperController$$ExternalSyntheticLambda3(3));
                    displayPowerController3.mHandler.removeCallbacksAndMessages(null);
                    WakelockController wakelockController =
                            displayPowerController3.mWakelockController;
                    for (int i = 1; i <= 7; i++) {
                        wakelockController.releaseWakelockInternal(i);
                    }
                    wakelockController.getClass();
                    DisplayPowerState displayPowerState = displayPowerController3.mPowerState;
                    displayPowerController3.reportStats(
                            displayPowerState != null
                                    ? displayPowerState.mScreenBrightness
                                    : FullScreenMagnificationGestureHandler.MAX_SCALE);
                    DisplayPowerState displayPowerState2 = displayPowerController3.mPowerState;
                    if (displayPowerState2 != null) {
                        displayPowerState2.mStopped = true;
                        displayPowerState2.mPhotonicModulator.interrupt();
                        displayPowerState2.mColorFadePrepared = false;
                        displayPowerState2.mColorFadeReady = true;
                        final ColorFade colorFade = displayPowerState2.mColorFade;
                        if (colorFade != null) {
                            displayPowerState2.mAsyncDestroyExecutor.execute(
                                    new Runnable() { // from class:
                                                     // com.android.server.display.DisplayPowerState$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            EGLContext eGLContext;
                                            ColorFade colorFade2 = ColorFade.this;
                                            if (ColorFade.DEBUG) {
                                                colorFade2.getClass();
                                                android.util.Slog.d("ColorFade", "destroy");
                                            }
                                            if (colorFade2.mPrepared) {
                                                if (colorFade2.mCreatedResources) {
                                                    colorFade2.attachEglContext();
                                                    try {
                                                        if (colorFade2.mTexNamesGenerated) {
                                                            colorFade2.mTexNamesGenerated = false;
                                                            GLES20.glDeleteTextures(
                                                                    1, colorFade2.mTexNames, 0);
                                                            ColorFade.checkGlErrors(
                                                                    "glDeleteTextures");
                                                        }
                                                        colorFade2.destroyGLShaders();
                                                        GLES20.glDeleteBuffers(
                                                                2, colorFade2.mGLBuffers, 0);
                                                        ColorFade.checkGlErrors("glDeleteBuffers");
                                                        colorFade2.destroyEglSurface();
                                                    } finally {
                                                        colorFade2.detachEglContext();
                                                    }
                                                }
                                                EGLDisplay eGLDisplay = colorFade2.mEglDisplay;
                                                if (eGLDisplay != null
                                                        && (eGLContext = colorFade2.mEglContext)
                                                                != null) {
                                                    EGL14.eglDestroyContext(eGLDisplay, eGLContext);
                                                }
                                                colorFade2.destroySurface();
                                            }
                                        }
                                    });
                        }
                        displayPowerState2.mCleanListener = null;
                        displayPowerState2.mHandler.removeCallbacksAndMessages(null);
                        displayPowerController3.mPowerState = null;
                    }
                    if (!displayPowerController3.mFlags.mRefactorDisplayPowerController.isEnabled()
                            && (screenOffBrightnessSensorController =
                                            displayPowerController3
                                                    .mScreenOffBrightnessSensorController)
                                    != null) {
                        screenOffBrightnessSensorController.setLightSensorEnabled(false);
                    }
                    ShutdownReceiver shutdownReceiver = displayPowerController3.mShutdownReceiver;
                    if (shutdownReceiver != null) {
                        displayPowerController3.mContext.unregisterReceiver(shutdownReceiver);
                        return;
                    }
                    return;
                case 8:
                    DisplayPowerController displayPowerController4 = DisplayPowerController.this;
                    if (displayPowerController4.mStopped) {
                        return;
                    }
                    DisplayPowerController.m460$$Nest$mhandleSettingsChange(
                            displayPowerController4);
                    return;
                case 9:
                    DisplayPowerController displayPowerController5 = DisplayPowerController.this;
                    if (displayPowerController5.mAutomaticBrightnessController == null) {
                        return;
                    }
                    float[] fArr = displayPowerController5.mNitsRange;
                    float[] fArr2 = new float[fArr.length];
                    if (fArr.length <= 0) {
                        throw null;
                    }
                    float f = fArr[0];
                    throw null;
                case 10:
                    DisplayPowerController displayPowerController6 = DisplayPowerController.this;
                    DisplayPowerState displayPowerState3 = displayPowerController6.mPowerState;
                    if (displayPowerState3 != null) {
                        displayPowerController6.reportStats(displayPowerState3.mScreenBrightness);
                        return;
                    }
                    return;
                case 11:
                    DisplayPowerController displayPowerController7 = DisplayPowerController.this;
                    float intBitsToFloat2 = Float.intBitsToFloat(message.arg1);
                    int i2 = message.arg2;
                    synchronized (displayPowerController7.mHandler) {
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.DISPLAY_HBM_BRIGHTNESS_CHANGED,
                                i2,
                                intBitsToFloat2);
                    }
                    return;
                case 12:
                    Object obj = message.obj;
                    float floatValue =
                            obj instanceof Float ? ((Float) obj).floatValue() : Float.NaN;
                    DisplayPowerController displayPowerController8 = DisplayPowerController.this;
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    String str = displayPowerController8.mTag;
                    StringBuilder m =
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i3,
                                    i4,
                                    "Switching user newUserId=",
                                    " userSerial=",
                                    " newBrightness=");
                    m.append(floatValue);
                    Slog.i(str, m.toString());
                    displayPowerController8.handleBrightnessModeChange();
                    if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL
                            && (brightnessTracker = displayPowerController8.mBrightnessTracker)
                                    != null) {
                        if (BrightnessTracker.DEBUG) {
                            android.util.Slog.d(
                                    "BrightnessTracker",
                                    "Used id updated from "
                                            + brightnessTracker.mCurrentUserId
                                            + " to "
                                            + i3);
                        }
                        brightnessTracker.mCurrentUserId = i3;
                    }
                    float clampScreenBrightness =
                            displayPowerController8.clampScreenBrightness(floatValue);
                    float currentBrightnessMax =
                            displayPowerController8.mBrightnessRangeController
                                    .getCurrentBrightnessMax();
                    DisplayBrightnessController displayBrightnessController2 =
                            displayPowerController8.mDisplayBrightnessController;
                    displayBrightnessController2.mBrightnessSetting.mUserSerial = i4;
                    displayBrightnessController2.setBrightness(
                            clampScreenBrightness, currentBrightnessMax);
                    DisplayBrightnessController displayBrightnessController3 =
                            displayPowerController8.mDisplayBrightnessController;
                    synchronized (displayBrightnessController3.mLock) {
                        float f2 = displayBrightnessController3.mCurrentScreenBrightness;
                        r2 = floatValue != f2;
                        if (floatValue != f2) {
                            displayBrightnessController3.mCurrentScreenBrightness = floatValue;
                        }
                    }
                    if (r2) {
                        displayBrightnessController3.mBrightnessChangeExecutor.execute(
                                displayBrightnessController3.mOnBrightnessChangeRunnable);
                    }
                    if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL
                            && (automaticBrightnessController =
                                            displayPowerController8.mAutomaticBrightnessController)
                                    != null) {
                        automaticBrightnessController.resetShortTermModel();
                    }
                    displayPowerController8.sendUpdatePowerState();
                    return;
                case 13:
                    DisplayPowerController displayPowerController9 = DisplayPowerController.this;
                    displayPowerController9.mBootCompleted = true;
                    displayPowerController9.updatePowerState();
                    return;
                case 14:
                    int i5 = message.arg1;
                    r2 = i5 == 1;
                    AutomaticBrightnessController automaticBrightnessController2 =
                            DisplayPowerController.this.mAutomaticBrightnessController;
                    if (automaticBrightnessController2 != null) {
                        automaticBrightnessController2.switchMode(i5, true);
                        DisplayPowerController.this.setAnimatorRampSpeeds(r2);
                    }
                    DisplayPowerController.this.getClass();
                    return;
                case 15:
                    Float.intBitsToFloat(message.arg1);
                    DisplayPowerController.this.getClass();
                    return;
                case 16:
                    DisplayPowerController.this.getClass();
                    return;
                case 17:
                    DisplayBrightnessController displayBrightnessController4 =
                            DisplayPowerController.this.mDisplayBrightnessController;
                    float intBitsToFloat3 = Float.intBitsToFloat(message.arg1);
                    synchronized (displayBrightnessController4.mLock) {
                        OffloadBrightnessStrategy offloadBrightnessStrategy =
                                displayBrightnessController4
                                        .mDisplayBrightnessStrategySelector
                                        .mOffloadBrightnessStrategy;
                        if (offloadBrightnessStrategy != null
                                && !BrightnessSynchronizer.floatEquals(
                                        offloadBrightnessStrategy.mOffloadScreenBrightness,
                                        intBitsToFloat3)) {
                            displayBrightnessController4
                                            .mDisplayBrightnessStrategySelector
                                            .mOffloadBrightnessStrategy
                                            .mOffloadScreenBrightness =
                                    intBitsToFloat3;
                            DisplayPowerController.this.updatePowerState();
                        }
                    }
                    return;
                case 18:
                    DisplayPowerController displayPowerController10 = DisplayPowerController.this;
                    if (displayPowerController10.mDisplayOffloadSession == message.obj) {
                        displayPowerController10.unblockScreenOnByDisplayOffload();
                        DisplayPowerController.this.updatePowerState();
                        return;
                    }
                    return;
                case 19:
                    DisplayPowerController.this
                            .restartAdaptiveBrightnessLongtermModelBuilderInternal(true);
                    return;
                case 20:
                    DisplayPowerController displayPowerController11 = DisplayPowerController.this;
                    displayPowerController11.mSeamlessAodReady = true;
                    displayPowerController11.updatePowerState();
                    return;
                default:
                    return;
            }
        }
    }

    class Injector {}

    public final class ScreenOnUnblocker {
        public ScreenOnUnblocker() {}

        public final void onScreenOn() {
            DisplayPowerController displayPowerController = DisplayPowerController.this;
            Slog.d(
                    displayPowerController.mTag,
                    "[api] WindowManagerPolicy.ScreenOnListener : called onScreenOn()");
            DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
            Message obtainMessage = displayControllerHandler.obtainMessage(2, this);
            displayPowerController.mClock.getClass();
            displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
        }
    }

    public final class SeamlessAodReadyListener implements AODManager.AODChangeListener {
        public SeamlessAodReadyListener() {}

        public final void readyToScreenTurningOn() {
            Slog.d(
                    DisplayPowerController.this.mTag,
                    "[api] AODManager.AODChangeListener : Received readyToScreenTurningOn().");
            Message obtainMessage = DisplayPowerController.this.mHandler.obtainMessage(20);
            obtainMessage.setAsynchronous(true);
            DisplayPowerController.this.mHandler.sendMessage(obtainMessage);
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(DisplayControllerHandler displayControllerHandler) {
            super(displayControllerHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            Slog.d(DisplayPowerController.this.mTag, "[api] SettingsObserver: onChange: " + uri);
            if (uri.equals(
                    Settings.System.getUriFor(
                            DisplayPowerController.this.mScreenBrightnessModeSettingName))) {
                DisplayPowerController displayPowerController = DisplayPowerController.this;
                DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
                DisplayPowerController$$ExternalSyntheticLambda0
                        displayPowerController$$ExternalSyntheticLambda0 =
                                new DisplayPowerController$$ExternalSyntheticLambda0(2, this);
                displayPowerController.mClock.getClass();
                displayControllerHandler.postAtTime(
                        displayPowerController$$ExternalSyntheticLambda0,
                        SystemClock.uptimeMillis());
                return;
            }
            if (!uri.equals(Settings.System.getUriFor("screen_brightness_for_als"))) {
                DisplayPowerController.m460$$Nest$mhandleSettingsChange(
                        DisplayPowerController.this);
                return;
            }
            int intForUser =
                    Settings.System.getIntForUser(
                            DisplayPowerController.this.mContext.getContentResolver(),
                            "screen_brightness_for_als",
                            2,
                            -2);
            Slog.i(
                    DisplayPowerController.this.mTag,
                    "Setting up auto-brightness for preset "
                            + DisplayBrightnessMappingConfig.autoBrightnessPresetToString(
                                    intForUser));
            DisplayPowerController displayPowerController2 = DisplayPowerController.this;
            displayPowerController2.setUpAutoBrightness(
                    displayPowerController2.mContext, displayPowerController2.mHandler);
            DisplayPowerController.this.sendUpdatePowerState();
        }
    }

    public final class ShutdownReceiver extends BroadcastReceiver {
        public ShutdownReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (DisplayPowerController.this.defaultModeBrightnessMapper == null
                    || !"android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                return;
            }
            DisplayPowerController displayPowerController = DisplayPowerController.this;
            if (!displayPowerController.mAppliedForceDimming
                    || displayPowerController.mAutomaticBrightnessStrategy.shouldUseAutoBrightness()
                    || Float.isNaN(
                            DisplayPowerController.this
                                    .mLastScreenBrightnessSettingBeforeForceDim)) {
                return;
            }
            Slog.d(
                    DisplayPowerController.this.mTag,
                    "Restore low battery force dim (manual brightness)");
            DisplayPowerController.this.mClock.getClass();
            DisplayPowerController.sLastScreenBrightnessSettingChangedTime =
                    SystemClock.uptimeMillis();
            DisplayPowerController displayPowerController2 = DisplayPowerController.this;
            displayPowerController2.mAppliedForceDimming = false;
            displayPowerController2.mDisplayBrightnessController.setBrightness(
                    displayPowerController2.mLastScreenBrightnessSettingBeforeForceDim,
                    Math.min(
                            displayPowerController2.mBrightnessRangeController
                                    .getCurrentBrightnessMax(),
                            DisplayPowerController.this.mClampedMaxBrightness));
        }
    }

    /* renamed from: -$$Nest$mhandleSettingsChange, reason: not valid java name */
    public static void m460$$Nest$mhandleSettingsChange(
            DisplayPowerController displayPowerController) {
        float f;
        DisplayBrightnessController displayBrightnessController =
                displayPowerController.mDisplayBrightnessController;
        float screenBrightnessSetting = displayBrightnessController.getScreenBrightnessSetting();
        synchronized (displayBrightnessController.mLock) {
            displayBrightnessController.mPendingScreenBrightness = screenBrightnessSetting;
        }
        displayPowerController.mAutomaticBrightnessStrategy
                .updatePendingAutoBrightnessAdjustments();
        displayPowerController.mAutomaticBrightnessStrategy.updateGameAutoBrightnessLock();
        displayPowerController.mForceDimSettingEnabled =
                Settings.Global.getInt(
                                displayPowerController.mContext.getContentResolver(),
                                "auto_dim_screen",
                                1)
                        != 0;
        displayPowerController.mPrevScreenBrightness =
                displayPowerController.mDisplayBrightnessController.getCurrentBrightness();
        DisplayBrightnessController displayBrightnessController2 =
                displayPowerController.mDisplayBrightnessController;
        synchronized (displayBrightnessController2.mLock) {
            f = displayBrightnessController2.mPendingScreenBrightness;
        }
        Slog.d(
                displayPowerController.mTag,
                "[api] handleSettingsChange:"
                        .concat(
                                String.format(
                                        " sb: %.3f abAdj: %.3f sbLock: %s fd: %s",
                                        Float.valueOf(f),
                                        Float.valueOf(
                                                displayPowerController.mAutomaticBrightnessStrategy
                                                        .getPendingAutoBrightnessAdjustment()),
                                        Boolean.valueOf(
                                                displayPowerController.mAutomaticBrightnessStrategy
                                                        .isGameAutoBrightnessLocked()),
                                        Boolean.valueOf(
                                                displayPowerController.mForceDimSettingEnabled))));
        displayPowerController.sendUpdatePowerState();
    }

    static {
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        BRIGHTNESS_RANGE_BOUNDARIES =
                new float[] {
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    1.0f,
                    2.0f,
                    3.0f,
                    4.0f,
                    5.0f,
                    6.0f,
                    7.0f,
                    8.0f,
                    9.0f,
                    10.0f,
                    20.0f,
                    30.0f,
                    40.0f,
                    50.0f,
                    60.0f,
                    70.0f,
                    80.0f,
                    90.0f,
                    100.0f,
                    200.0f,
                    300.0f,
                    400.0f,
                    500.0f,
                    600.0f,
                    700.0f,
                    800.0f,
                    900.0f,
                    1000.0f,
                    1200.0f,
                    1400.0f,
                    1600.0f,
                    1800.0f,
                    2000.0f,
                    2250.0f,
                    2500.0f,
                    2750.0f,
                    3000.0f
                };
        BRIGHTNESS_RANGE_INDEX =
                new int[] {
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                    22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37
                };
        sLastScreenBrightnessSettingChangedTime = -1L;
        if (PowerManagerUtil.SEC_FEATURE_FULLSCREEN_AOD) {
            f = 0.03f;
        }
        RATE_FROM_DOZE_TO_ON = f;
        DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION = 180;
        MAX_AUTO_BRIGHTNESS_TRANSITION_TIME = 60000;
    }

    public DisplayPowerController(
            Context context,
            DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks,
            Handler handler,
            SensorManager sensorManager,
            DisplayBlanker displayBlanker,
            LogicalDisplay logicalDisplay,
            BrightnessTracker brightnessTracker,
            BrightnessSetting brightnessSetting,
            DisplayManagerService$$ExternalSyntheticLambda23
                    displayManagerService$$ExternalSyntheticLambda23,
            HighBrightnessModeMetadata highBrightnessModeMetadata,
            boolean z,
            DisplayManagerFlags displayManagerFlags,
            DisplayManagerService$$ExternalSyntheticLambda23
                    displayManagerService$$ExternalSyntheticLambda232,
            DisplayManagerService$$ExternalSyntheticLambda8
                    displayManagerService$$ExternalSyntheticLambda8) {
        BrightnessMappingStrategy brightnessMappingStrategy;
        Object obj = new Object();
        this.mLock = obj;
        this.mLeadDisplayId = -1;
        CachedBrightnessInfo cachedBrightnessInfo = new CachedBrightnessInfo();
        cachedBrightnessInfo.brightness = new MutableFloat(Float.NaN);
        cachedBrightnessInfo.adjustedBrightness = new MutableFloat(Float.NaN);
        cachedBrightnessInfo.brightnessMin = new MutableFloat(Float.NaN);
        cachedBrightnessInfo.brightnessMax = new MutableFloat(Float.NaN);
        cachedBrightnessInfo.hbmMode = new MutableInt(0);
        cachedBrightnessInfo.hbmTransitionPoint = new MutableFloat(Float.POSITIVE_INFINITY);
        cachedBrightnessInfo.brightnessMaxReason = new MutableInt(0);
        cachedBrightnessInfo.isAnimating = new MutableBoolean(false);
        this.mCachedBrightnessInfo = cachedBrightnessInfo;
        this.mReportedScreenStateToPolicy = -1;
        this.mBrightnessReason = new BrightnessReason();
        this.mBrightnessReasonTemp = new BrightnessReason();
        this.mLastStatsBrightness = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mRbcEventRingBuffer = new RingBuffer(BrightnessEvent.class, 20);
        this.mDisplayBrightnessFollowers = new SparseArray();
        this.mDualScreenPolicy = -1;
        this.mScreenBrightnessModeSettingName = "screen_brightness_mode";
        this.mLcdFlashModeEnabled = false;
        this.mPassRampAnimation = false;
        this.mIsOutdoorModeEnabled = false;
        this.mSeamlessAodReady = false;
        this.mPendingScreenOnByAodReady = false;
        this.mLastOriginalTarget = Float.NaN;
        this.mLastAutomaticScreenBrightness = Float.NaN;
        this.mLastAmbientLux = Float.NaN;
        this.mActualDisplayState = 0;
        this.mLastNotifiedBrightness = Float.NaN;
        this.mExtraDimStrength = -1;
        this.mPowerHistorian = PowerHistorian.INSTANCE;
        this.mPrevScreenBrightness = Float.NaN;
        this.mAnimatorListener =
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.android.server.display.DisplayPowerController.2
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        Slog.d(DisplayPowerController.this.mTag, "ColorFade: onAnimationCancel");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        PowerManagerUtil.ScreenOffProfiler screenOffProfiler =
                                PowerManagerUtil.sCurrentScreenOffProfiler;
                        screenOffProfiler.getClass();
                        screenOffProfiler.mCfAnimationDuration =
                                (int)
                                        (SystemClock.uptimeMillis()
                                                - screenOffProfiler.mCfAnimationStartTime);
                        if (!screenOffProfiler.mDone) {
                            screenOffProfiler.mDone = true;
                            screenOffProfiler.mGoToSleepDuration =
                                    (int)
                                            ((SystemClock.uptimeMillis()
                                                            - screenOffProfiler.mGoToSleepStartTime)
                                                    + screenOffProfiler.mGoToSleepDiff);
                        }
                        Slog.w(DisplayPowerController.this.mTag, "ColorFade: onAnimationEnd");
                        DisplayPowerController.this.sendUpdatePowerState();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                        Slog.d(DisplayPowerController.this.mTag, "ColorFade: onAnimationRepeat");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        PowerManagerUtil.ScreenOffProfiler screenOffProfiler =
                                PowerManagerUtil.sCurrentScreenOffProfiler;
                        screenOffProfiler.getClass();
                        screenOffProfiler.mCfAnimationStartTime = SystemClock.uptimeMillis();
                        Slog.w(DisplayPowerController.this.mTag, "ColorFade: onAnimationStart");
                    }
                };
        this.mBrightnessAnimationEndRunnable =
                new Runnable() { // from class: com.android.server.display.DisplayPowerController.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayPowerController displayPowerController = DisplayPowerController.this;
                        if (!displayPowerController.mBrightnessAnimationConsumerInvoked
                                || displayPowerController.mScreenBrightnessRampAnimator
                                        .isAnimating()) {
                            return;
                        }
                        DisplayPowerController displayPowerController2 =
                                DisplayPowerController.this;
                        displayPowerController2.mBrightnessAnimationConsumerInvoked = false;
                        displayPowerController2.mOnBrightnessAnimationConsumer.accept(
                                Boolean.FALSE);
                        DisplayPowerController.this.mWakelockController.releaseWakelockInternal(6);
                    }
                };
        this.mRampAnimatorListener = new AnonymousClass4();
        final int i = 0;
        this.mCleanListener =
                new Runnable(
                        this) { // from class:
                                // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                    public final /* synthetic */ DisplayPowerController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        DisplayPowerController displayPowerController = this.f$0;
                        switch (i2) {
                            case 0:
                                displayPowerController.sendUpdatePowerState();
                                break;
                            case 1:
                                displayPowerController.sendUpdatePowerState();
                                displayPowerController.postBrightnessChangeRunnable();
                                break;
                            case 2:
                                displayPowerController.updatePowerState();
                                break;
                            case 3:
                                displayPowerController.sendUpdatePowerState();
                                displayPowerController.postBrightnessChangeRunnable();
                                AutomaticBrightnessController automaticBrightnessController =
                                        displayPowerController.mAutomaticBrightnessController;
                                if (automaticBrightnessController != null) {
                                    automaticBrightnessController.mHandler.sendEmptyMessage(6);
                                    break;
                                }
                                break;
                            case 4:
                                displayPowerController.postBrightnessChangeRunnable();
                                break;
                            default:
                                displayPowerController.getClass();
                                IntentFilter intentFilter = new IntentFilter();
                                intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                                displayPowerController.mContext.registerReceiver(
                                        displayPowerController.mShutdownReceiver,
                                        intentFilter,
                                        null,
                                        displayPowerController.mHandler);
                                break;
                        }
                    }
                };
        this.mFlags = displayManagerFlags;
        this.mInjector = new Injector();
        this.mClock = new DisplayPowerController$$ExternalSyntheticLambda11(1);
        this.mLogicalDisplay = logicalDisplay;
        int i2 = logicalDisplay.mDisplayId;
        this.mDisplayId = i2;
        this.mSensorManager = sensorManager;
        DisplayControllerHandler displayControllerHandler =
                new DisplayControllerHandler(handler.getLooper());
        this.mHandler = displayControllerHandler;
        this.mDisplayDeviceConfig = logicalDisplay.mPrimaryDisplayDevice.getDisplayDeviceConfig();
        this.mIsEnabled = logicalDisplay.mIsEnabled;
        this.mIsInTransition = logicalDisplay.mIsInTransition;
        this.mIsDisplayInternal =
                logicalDisplay.mPrimaryDisplayDevice.getDisplayDeviceInfoLocked().type == 1;
        WakelockController wakelockController = new WakelockController(i2, displayPowerCallbacks);
        this.mWakelockController = wakelockController;
        final int i3 = 2;
        DisplayPowerProximityStateController displayPowerProximityStateController =
                new DisplayPowerProximityStateController(
                        wakelockController,
                        this.mDisplayDeviceConfig,
                        displayControllerHandler.getLooper(),
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                            public final /* synthetic */ DisplayPowerController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i3;
                                DisplayPowerController displayPowerController = this.f$0;
                                switch (i22) {
                                    case 0:
                                        displayPowerController.sendUpdatePowerState();
                                        break;
                                    case 1:
                                        displayPowerController.sendUpdatePowerState();
                                        displayPowerController.postBrightnessChangeRunnable();
                                        break;
                                    case 2:
                                        displayPowerController.updatePowerState();
                                        break;
                                    case 3:
                                        displayPowerController.sendUpdatePowerState();
                                        displayPowerController.postBrightnessChangeRunnable();
                                        AutomaticBrightnessController
                                                automaticBrightnessController =
                                                        displayPowerController
                                                                .mAutomaticBrightnessController;
                                        if (automaticBrightnessController != null) {
                                            automaticBrightnessController.mHandler.sendEmptyMessage(
                                                    6);
                                            break;
                                        }
                                        break;
                                    case 4:
                                        displayPowerController.postBrightnessChangeRunnable();
                                        break;
                                    default:
                                        displayPowerController.getClass();
                                        IntentFilter intentFilter = new IntentFilter();
                                        intentFilter.addAction(
                                                "android.intent.action.ACTION_SHUTDOWN");
                                        displayPowerController.mContext.registerReceiver(
                                                displayPowerController.mShutdownReceiver,
                                                intentFilter,
                                                null,
                                                displayPowerController.mHandler);
                                        break;
                                }
                            }
                        },
                        i2,
                        sensorManager);
        this.mDisplayPowerProximityStateController = displayPowerProximityStateController;
        this.mDisplayStateController =
                new DisplayStateController(displayPowerProximityStateController, i2);
        String m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i2, "DisplayPowerController2[", "]");
        this.mTag = m;
        this.mThermalBrightnessThrottlingDataId =
                logicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        this.mDisplayDevice = displayDevice;
        String str = displayDevice.mUniqueId;
        this.mUniqueDisplayId = str;
        this.mDisplayStatsId = str.hashCode();
        this.mLastBrightnessEvent = new BrightnessEvent(i2);
        this.mTempBrightnessEvent = new BrightnessEvent(i2);
        if (i2 == 0 || (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && i2 == 1)) {
            this.mBatteryStats = BatteryStatsService.getService();
        } else {
            this.mBatteryStats = null;
        }
        this.mSettingsObserver = new SettingsObserver(displayControllerHandler);
        this.mWindowManagerPolicy =
                (WindowManagerPolicy) LocalServices.getService(WindowManagerPolicy.class);
        this.mAodManagerInternal =
                (AODManagerInternal) LocalServices.getService(AODManagerInternal.class);
        this.mBlanker = displayBlanker;
        this.mContext = context;
        this.mOnBrightnessChangeRunnable = displayManagerService$$ExternalSyntheticLambda23;
        if (i2 == 0) {
            this.mOnBrightnessAnimationConsumer = displayManagerService$$ExternalSyntheticLambda8;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Resources resources = context.getResources();
        this.mScreenBrightnessDozeConfig =
                BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(4));
        loadBrightnessRampRates();
        this.mDozeScaleFactor =
                context.getResources()
                        .getFraction(R.fraction.config_screenAutoBrightnessDozeScaleFactor, 1, 1);
        final int i4 = 3;
        Runnable runnable =
                new Runnable(
                        this) { // from class:
                                // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                    public final /* synthetic */ DisplayPowerController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i4;
                        DisplayPowerController displayPowerController = this.f$0;
                        switch (i22) {
                            case 0:
                                displayPowerController.sendUpdatePowerState();
                                break;
                            case 1:
                                displayPowerController.sendUpdatePowerState();
                                displayPowerController.postBrightnessChangeRunnable();
                                break;
                            case 2:
                                displayPowerController.updatePowerState();
                                break;
                            case 3:
                                displayPowerController.sendUpdatePowerState();
                                displayPowerController.postBrightnessChangeRunnable();
                                AutomaticBrightnessController automaticBrightnessController =
                                        displayPowerController.mAutomaticBrightnessController;
                                if (automaticBrightnessController != null) {
                                    automaticBrightnessController.mHandler.sendEmptyMessage(6);
                                    break;
                                }
                                break;
                            case 4:
                                displayPowerController.postBrightnessChangeRunnable();
                                break;
                            default:
                                displayPowerController.getClass();
                                IntentFilter intentFilter = new IntentFilter();
                                intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                                displayPowerController.mContext.registerReceiver(
                                        displayPowerController.mShutdownReceiver,
                                        intentFilter,
                                        null,
                                        displayPowerController.mHandler);
                                break;
                        }
                    }
                };
        float clampAbsoluteBrightness =
                BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(3));
        this.mScreenBrightnessDimConfig = clampAbsoluteBrightness;
        this.mScreenBrightnessMinimumDimAmount =
                resources.getFloat(R.dimen.conversation_avatar_size_group_expanded);
        float f = BrightnessUtils.sScreenExtendedBrightnessRangeMaximum;
        this.mScreenExtendedBrightnessRangeMaximum = f;
        boolean z2 = PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && i2 == 1;
        this.mIsCoverDisplay = z2;
        if (z2) {
            this.mScreenBrightnessModeSettingName = "sub_screen_brightness_mode";
        }
        this.mMoreFastRampRate = f;
        this.mBrightnessRampRateHdrIncrease = 0.3f;
        this.mBrightnessRampRateHdrDecrease = 0.9f;
        DisplayDeviceConfig displayDeviceConfig = this.mDisplayDevice.getDisplayDeviceConfig();
        DisplayDevice displayDevice2 = this.mDisplayDevice;
        IBinder iBinder = displayDevice2.mDisplayToken;
        DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData =
                displayDeviceConfig != null
                        ? displayDeviceConfig.getHighBrightnessModeData()
                        : null;
        DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
        HighBrightnessModeController highBrightnessModeController =
                new HighBrightnessModeController(
                        new HighBrightnessModeController.Injector(),
                        displayControllerHandler,
                        displayInfoLocked.logicalWidth,
                        displayInfoLocked.logicalHeight,
                        iBinder,
                        displayDevice2.mUniqueId,
                        Math.min(
                                ((PowerManager) context.getSystemService(PowerManager.class))
                                        .getBrightnessConstraint(0),
                                clampAbsoluteBrightness),
                        f,
                        highBrightnessModeData,
                        new HighBrightnessModeController
                                .HdrBrightnessDeviceConfig() { // from class:
                                                               // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda12
                            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
                            public final float getHdrBrightnessFromSdr(float f2, float f3) {
                                return DisplayPowerController.this.mDisplayDeviceConfig
                                        .getHdrBrightnessFromSdr(f2);
                            }
                        },
                        runnable,
                        highBrightnessModeMetadata,
                        context);
        DisplayDeviceConfig displayDeviceConfig2 =
                logicalDisplay.mPrimaryDisplayDevice.getDisplayDeviceConfig();
        final int i5 = 1;
        this.mBrightnessThrottler =
                new BrightnessThrottler(
                        new BrightnessThrottler.Injector(),
                        displayControllerHandler,
                        displayControllerHandler,
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                            public final /* synthetic */ DisplayPowerController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i5;
                                DisplayPowerController displayPowerController = this.f$0;
                                switch (i22) {
                                    case 0:
                                        displayPowerController.sendUpdatePowerState();
                                        break;
                                    case 1:
                                        displayPowerController.sendUpdatePowerState();
                                        displayPowerController.postBrightnessChangeRunnable();
                                        break;
                                    case 2:
                                        displayPowerController.updatePowerState();
                                        break;
                                    case 3:
                                        displayPowerController.sendUpdatePowerState();
                                        displayPowerController.postBrightnessChangeRunnable();
                                        AutomaticBrightnessController
                                                automaticBrightnessController =
                                                        displayPowerController
                                                                .mAutomaticBrightnessController;
                                        if (automaticBrightnessController != null) {
                                            automaticBrightnessController.mHandler.sendEmptyMessage(
                                                    6);
                                            break;
                                        }
                                        break;
                                    case 4:
                                        displayPowerController.postBrightnessChangeRunnable();
                                        break;
                                    default:
                                        displayPowerController.getClass();
                                        IntentFilter intentFilter = new IntentFilter();
                                        intentFilter.addAction(
                                                "android.intent.action.ACTION_SHUTDOWN");
                                        displayPowerController.mContext.registerReceiver(
                                                displayPowerController.mShutdownReceiver,
                                                intentFilter,
                                                null,
                                                displayPowerController.mHandler);
                                        break;
                                }
                            }
                        },
                        this.mUniqueDisplayId,
                        logicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId,
                        displayDeviceConfig2.mThermalBrightnessThrottlingDataMapByThrottlingId,
                        displayDeviceConfig2.mTempSensor);
        DisplayDeviceConfig displayDeviceConfig3 = this.mDisplayDeviceConfig;
        DisplayDevice displayDevice3 = this.mDisplayDevice;
        IBinder iBinder2 = displayDevice3.mDisplayToken;
        DisplayDeviceInfo displayDeviceInfoLocked = displayDevice3.getDisplayDeviceInfoLocked();
        NormalBrightnessModeController normalBrightnessModeController =
                new NormalBrightnessModeController();
        normalBrightnessModeController.mMaxBrightnessLimits = new HashMap();
        normalBrightnessModeController.mAmbientLux = Float.MAX_VALUE;
        normalBrightnessModeController.mAutoBrightnessEnabled = false;
        normalBrightnessModeController.mMaxBrightness = 1.0f;
        this.mBrightnessRangeController =
                new BrightnessRangeController(
                        highBrightnessModeController,
                        runnable,
                        displayDeviceConfig3,
                        normalBrightnessModeController,
                        new HdrClamper(
                                new BrightnessRangeController$$ExternalSyntheticLambda4(runnable),
                                new Handler(displayControllerHandler.getLooper()),
                                new HdrClamper.Injector()),
                        displayManagerFlags,
                        iBinder2,
                        displayDeviceInfoLocked);
        final int i6 = 4;
        DisplayBrightnessController displayBrightnessController =
                new DisplayBrightnessController(
                        context,
                        i2,
                        logicalDisplay.getDisplayInfoLocked().brightnessDefault,
                        brightnessSetting,
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                            public final /* synthetic */ DisplayPowerController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i6;
                                DisplayPowerController displayPowerController = this.f$0;
                                switch (i22) {
                                    case 0:
                                        displayPowerController.sendUpdatePowerState();
                                        break;
                                    case 1:
                                        displayPowerController.sendUpdatePowerState();
                                        displayPowerController.postBrightnessChangeRunnable();
                                        break;
                                    case 2:
                                        displayPowerController.updatePowerState();
                                        break;
                                    case 3:
                                        displayPowerController.sendUpdatePowerState();
                                        displayPowerController.postBrightnessChangeRunnable();
                                        AutomaticBrightnessController
                                                automaticBrightnessController =
                                                        displayPowerController
                                                                .mAutomaticBrightnessController;
                                        if (automaticBrightnessController != null) {
                                            automaticBrightnessController.mHandler.sendEmptyMessage(
                                                    6);
                                            break;
                                        }
                                        break;
                                    case 4:
                                        displayPowerController.postBrightnessChangeRunnable();
                                        break;
                                    default:
                                        displayPowerController.getClass();
                                        IntentFilter intentFilter = new IntentFilter();
                                        intentFilter.addAction(
                                                "android.intent.action.ACTION_SHUTDOWN");
                                        displayPowerController.mContext.registerReceiver(
                                                displayPowerController.mShutdownReceiver,
                                                intentFilter,
                                                null,
                                                displayPowerController.mHandler);
                                        break;
                                }
                            }
                        },
                        new HandlerExecutor(displayControllerHandler),
                        displayManagerFlags);
        this.mDisplayBrightnessController = displayBrightnessController;
        this.mBrightnessClamperController =
                new BrightnessClamperController(
                        null,
                        displayControllerHandler,
                        new BrightnessRangeController$$ExternalSyntheticLambda4(runnable),
                        new BrightnessClamperController.DisplayDeviceData(
                                this.mUniqueDisplayId,
                                this.mThermalBrightnessThrottlingDataId,
                                logicalDisplay.mPowerThrottlingDataId,
                                this.mDisplayDeviceConfig),
                        context,
                        displayManagerFlags,
                        sensorManager);
        float screenBrightnessSetting = displayBrightnessController.getScreenBrightnessSetting();
        saveBrightnessInfo(screenBrightnessSetting, screenBrightnessSetting, null);
        AutomaticBrightnessStrategy2 automaticBrightnessStrategy2 =
                displayBrightnessController
                        .mDisplayBrightnessStrategySelector
                        .mAutomaticBrightnessStrategy;
        this.mAutomaticBrightnessStrategy = automaticBrightnessStrategy2;
        loadNitsRange(resources);
        setUpAutoBrightness(context, handler);
        if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL
                || (brightnessMappingStrategy = this.defaultModeBrightnessMapper) == null) {
            this.mBrightnessTracker = brightnessTracker;
            this.mAdaptiveBrightnessLongtermModelBuilder = null;
        } else {
            this.mBrightnessTracker = null;
            this.mAdaptiveBrightnessLongtermModelBuilder =
                    new AdaptiveBrightnessLongtermModelBuilder(context, brightnessMappingStrategy);
        }
        this.mColorFadeEnabled =
                (ActivityManager.isLowRamDeviceStatic() ^ true)
                        && !resources.getBoolean(R.bool.config_dockedStackDividerFreeSnapMode);
        this.mColorFadeFadesConfig =
                resources.getBoolean(R.bool.config_appCompatUserAppAspectRatioSettingsIsEnabled);
        this.mDisplayBlanksAfterDozeConfig =
                resources.getBoolean(R.bool.config_displayWhiteBalanceLightModeAllowed);
        this.mBrightnessBucketsInDozeConfig =
                resources.getBoolean(R.bool.config_dockBigOverlayWindows);
        this.mBootCompleted = z;
        if (this.mIsDisplayInternal) {
            this.mShutdownReceiver = new ShutdownReceiver();
            final int i7 = 5;
            displayControllerHandler.post(
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                        public final /* synthetic */ DisplayPowerController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i7;
                            DisplayPowerController displayPowerController = this.f$0;
                            switch (i22) {
                                case 0:
                                    displayPowerController.sendUpdatePowerState();
                                    break;
                                case 1:
                                    displayPowerController.sendUpdatePowerState();
                                    displayPowerController.postBrightnessChangeRunnable();
                                    break;
                                case 2:
                                    displayPowerController.updatePowerState();
                                    break;
                                case 3:
                                    displayPowerController.sendUpdatePowerState();
                                    displayPowerController.postBrightnessChangeRunnable();
                                    AutomaticBrightnessController automaticBrightnessController =
                                            displayPowerController.mAutomaticBrightnessController;
                                    if (automaticBrightnessController != null) {
                                        automaticBrightnessController.mHandler.sendEmptyMessage(6);
                                        break;
                                    }
                                    break;
                                case 4:
                                    displayPowerController.postBrightnessChangeRunnable();
                                    break;
                                default:
                                    displayPowerController.getClass();
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                                    displayPowerController.mContext.registerReceiver(
                                            displayPowerController.mShutdownReceiver,
                                            intentFilter,
                                            null,
                                            displayPowerController.mHandler);
                                    break;
                            }
                        }
                    });
        }
        this.mOnBrightnessModeChangeRunnable = displayManagerService$$ExternalSyntheticLambda232;
        boolean z3 = this.mIsDisplayInternal;
        this.mEarlyWakeUpEnabled = z3;
        if (z3) {
            this.mEarlyWakeUpManager =
                    new EarlyWakeUpManager(
                            m,
                            i2,
                            new AnonymousClass4(),
                            displayBlanker,
                            this.mAutomaticBrightnessController,
                            wakelockController);
        }
        if (i2 == 0) {
            final AdaptiveBrightnessAdvancedSettings adaptiveBrightnessAdvancedSettings =
                    new AdaptiveBrightnessAdvancedSettings(context, obj, displayControllerHandler);
            this.mAdaptiveBrightnessAdvancedSettings = adaptiveBrightnessAdvancedSettings;
            final AdaptiveBrightnessAdvancedSettings.SettingsObserver settingsObserver =
                    adaptiveBrightnessAdvancedSettings
                    .new SettingsObserver(displayControllerHandler);
            final ContentResolver contentResolver = context.getContentResolver();
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
            intentFilter.addDataScheme("package");
            displayControllerHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.display.AdaptiveBrightnessAdvancedSettings$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AdaptiveBrightnessAdvancedSettings adaptiveBrightnessAdvancedSettings2 =
                                    AdaptiveBrightnessAdvancedSettings.this;
                            ContentResolver contentResolver2 = contentResolver;
                            AdaptiveBrightnessAdvancedSettings.SettingsObserver settingsObserver2 =
                                    settingsObserver;
                            IntentFilter intentFilter2 = intentFilter;
                            adaptiveBrightnessAdvancedSettings2.getClass();
                            contentResolver2.registerContentObserver(
                                    Settings.Global.getUriFor("darkening_rate_ratio"),
                                    false,
                                    settingsObserver2);
                            adaptiveBrightnessAdvancedSettings2.mContext.registerReceiver(
                                    adaptiveBrightnessAdvancedSettings2
                                    .new PackageRemovedReceiver(),
                                    intentFilter2,
                                    null,
                                    adaptiveBrightnessAdvancedSettings2.mHandler);
                        }
                    });
            adaptiveBrightnessAdvancedSettings.handleSettingsChangedLocked();
        }
        boolean z4 =
                PowerManagerUtil.SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE
                        && this.mIsDisplayInternal;
        this.mIsSupportedAodMode = z4;
        if (z4) {
            this.mSeamlessAodReadyListener = new SeamlessAodReadyListener();
        }
        automaticBrightnessStrategy2.setUseAutoBrightness(
                Settings.System.getIntForUser(
                                context.getContentResolver(),
                                this.mScreenBrightnessModeSettingName,
                                0,
                                -2)
                        == 1);
        Slog.d(
                m,
                "Create new DPC instance, mDisplayId="
                        + i2
                        + " AutomaticBrightnessController="
                        + this.mAutomaticBrightnessController
                        + " defaultModeBrightnessMapper="
                        + this.defaultModeBrightnessMapper);
    }

    public static float getDynamicRampRate(float f, float f2, float f3, float f4, int i, int i2) {
        double min = Math.min((i / Math.log10((f3 / f4) * (f2 / f))) * 1000.0d, i2);
        if (min > 0.0d) {
            return ((float) (1.0d / min)) * 1000.0f;
        }
        return Float.NaN;
    }

    public static void logDisplayPolicyChanged(int i) {
        LogMaker logMaker = new LogMaker(1696);
        logMaker.setType(6);
        logMaker.setSubtype(i);
        MetricsLogger.action(logMaker);
    }

    public final void addBrightnessWeights(float f, float f2, float f3, float f4) {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            android.util.Slog.d(
                    "AdaptiveBrightnessLongtermModelBuilder",
                    "addBrightnessWeightDirectly: l:" + f + " b:" + f2 + " d:" + f3 + " c:" + f4);
            adaptiveBrightnessLongtermModelBuilder.mBrightnessMapper.addUserDataPoint(f, f2);
            Spline brightnessSpline =
                    adaptiveBrightnessLongtermModelBuilder.mBrightnessMapper.getBrightnessSpline();
            adaptiveBrightnessLongtermModelBuilder.mLastBrightnessSpline = brightnessSpline;
            AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                    adaptiveBrightnessLongtermModelBuilder.mAdaptiveBrightnessStatsTracker;
            synchronized (adaptiveBrightnessStatsTracker) {
                int i = adaptiveBrightnessStatsTracker.mCurrentUserId;
                if (i == 0) {
                    AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats adaptiveBrightnessStats =
                            adaptiveBrightnessStatsTracker.mAdaptiveBrightnessStats;
                    adaptiveBrightnessStatsTracker.mInjector.getClass();
                    LocalDate.now();
                    adaptiveBrightnessStats
                            .getOrCreateUserStats(i, adaptiveBrightnessStats.mStats)
                            .log(f, f2, f3, brightnessSpline, null, null, false);
                }
            }
        }
    }

    public final void animateScreenBrightness(float f, float f2, float f3, boolean z, float f4) {
        AdaptiveBrightnessAdvancedSettings adaptiveBrightnessAdvancedSettings =
                this.mAdaptiveBrightnessAdvancedSettings;
        int i =
                adaptiveBrightnessAdvancedSettings != null
                        ? adaptiveBrightnessAdvancedSettings.mDarkeningRateRatio
                        : 1;
        StringBuilder sb = new StringBuilder("Animating brightness: target=");
        sb.append(PowerManagerUtil.brightnessToString(f));
        sb.append(f != f2 ? ", sdrTarget=".concat(PowerManagerUtil.brightnessToString(f2)) : "");
        sb.append(String.format(", rate=%.3f", Float.valueOf(f3)));
        sb.append(i != 1 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, ", DR=") : "");
        sb.append(Float.isNaN(f4) ? "" : String.format(", rateAtHbm=%.3f", Float.valueOf(f4)));
        sb.append(", reason=");
        BrightnessReason brightnessReason = this.mBrightnessReasonTemp;
        sb.append(brightnessReason.changesToString());
        String sb2 = sb.toString();
        String str = this.mTag;
        Slog.w(str, sb2);
        BrightnessReason brightnessReason2 = this.mBrightnessReason;
        int i2 = brightnessReason.mReason;
        int i3 = brightnessReason2.mReason;
        if ((i2 != i3 && (i2 == 6 || i3 == 6))
                || (brightnessReason.mModifier != brightnessReason2.mModifier
                        && brightnessReason.hasModifier(112)
                                != brightnessReason2.hasModifier(112))) {
            String changesToString = brightnessReason.changesToString();
            PowerHistorian powerHistorian = this.mPowerHistorian;
            powerHistorian.getClass();
            powerHistorian.addRecord(2, new PowerHistorian.MessageRecord(changesToString));
        }
        RampAnimator.DualRampAnimator dualRampAnimator = this.mScreenBrightnessRampAnimator;
        boolean animationTarget =
                dualRampAnimator.mSecond.setAnimationTarget(f2, f3, f4, z)
                        | dualRampAnimator.mFirst.setAnimationTarget(f, f3, f4, z);
        boolean isAnimating = dualRampAnimator.isAnimating();
        boolean z2 = dualRampAnimator.mAwaitingCallback;
        if (isAnimating != z2) {
            RampAnimator.DualRampAnimator.AnonymousClass1 anonymousClass1 =
                    dualRampAnimator.mAnimationCallback;
            if (isAnimating) {
                dualRampAnimator.mAwaitingCallback = true;
                dualRampAnimator.mChoreographer.postCallback(1, anonymousClass1, null);
            } else if (z2) {
                AnonymousClass4 anonymousClass4 = dualRampAnimator.mListener;
                if (anonymousClass4 != null) {
                    anonymousClass4.onAnimationEnd();
                }
                dualRampAnimator.mChoreographer.removeCallbacks(1, anonymousClass1, null);
                dualRampAnimator.mAwaitingCallback = false;
            }
        }
        if (animationTarget) {
            if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN
                    && this.mOnBrightnessAnimationConsumer != null
                    && this.mScreenBrightnessRampAnimator.isAnimating()
                    && !this.mBrightnessAnimationConsumerInvoked) {
                this.mWakelockController.acquireWakelock(6);
                this.mBrightnessAnimationConsumerInvoked = true;
                this.mOnBrightnessAnimationConsumer.accept(Boolean.TRUE);
            }
            Trace.traceCounter(131072L, "TargetScreenBrightness", (int) f);
            String valueOf = String.valueOf(f);
            try {
                SystemProperties.set("debug.tracing.screen_brightness", valueOf);
            } catch (RuntimeException e) {
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Failed to set a system property:"
                                    + " key=debug.tracing.screen_brightness value=",
                                valueOf,
                                " ");
                m.append(e.getMessage());
                Slog.e(str, m.toString());
            }
            noteScreenBrightness(f);
        }
    }

    public final void animateScreenStateChange(int i, int i2, boolean z) {
        String str =
                "animateScreenStateChange: target="
                        + Display.stateToString(i)
                        + ", mIsEnabled="
                        + this.mIsEnabled;
        String str2 = this.mTag;
        Slog.d(str2, str);
        boolean z2 = this.mColorFadeEnabled;
        if (z2
                && (this.mColorFadeOnAnimator.isStarted()
                        || this.mColorFadeOffAnimator.isStarted())) {
            if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY || i != 2) {
                return;
            }
            this.mPendingScreenOff = false;
            if (this.mColorFadeOffAnimator.isStarted()) {
                Slog.d(str2, "animateScreenStateChange: mColorFadeOffAnimator.cancel()");
                this.mColorFadeOffAnimator.cancel();
            }
        }
        boolean z3 = this.mDisplayBlanksAfterDozeConfig;
        boolean z4 = this.mColorFadeFadesConfig;
        if (z3 && Display.isDozeState(this.mPowerState.mScreenState) && !Display.isDozeState(i)) {
            this.mPowerState.prepareColorFade(this.mContext, z4 ? 2 : 0);
            ObjectAnimator objectAnimator = this.mColorFadeOffAnimator;
            if (objectAnimator != null) {
                objectAnimator.end();
            }
            setScreenState(1, i2, i != 1);
        }
        if (this.mPendingScreenOff && i != 1) {
            setScreenState(1, i2, false);
            this.mPendingScreenOff = false;
            ColorFade colorFade = this.mPowerState.mColorFade;
            if (colorFade != null) {
                colorFade.dismissResources();
            }
        }
        if (i == 2) {
            if (this.mIsSupportedAodMode
                    && PowerManagerUtil.SEC_FEATURE_AOD_DISABLE_CLOCK_TRANSITION
                    && this.mPowerState.mScreenState == 4) {
                setScreenState(3, i2, false);
            }
            if (this.mNeedPrepareColorFade && this.mPowerRequest.lastWakeUpReason == 12) {
                DisplayPowerState displayPowerState = this.mPowerState;
                if (displayPowerState.mColorFadeLevel
                                == FullScreenMagnificationGestureHandler.MAX_SCALE
                        && displayPowerState.prepareColorFade(this.mContext, 2)) {
                    Slog.d(str2, "draw ColorFade due to unfolding");
                    this.mPowerState.setColorFadeLevel(
                            FullScreenMagnificationGestureHandler.MAX_SCALE);
                }
                this.mNeedPrepareColorFade = false;
            }
            if (setScreenState(2, i2, false)) {
                if (this.mAutomaticBrightnessController != null
                        && this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()
                        && !this.mAutomaticBrightnessController.mAmbientLuxValid
                        && this.mPowerState.mScreenState == 2
                        && Float.isNaN(this.mPowerRequest.screenBrightnessOverride)
                        && !this.mPowerRequest.coverClosed
                        && (!this.mIsCoverDisplay || this.mDualScreenPolicy == 1)) {
                    Slog.d(
                            str2,
                            "animateScreenStateChange is returned because lux is not yet valid!");
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
            if (!(this.mScreenBrightnessRampAnimator.isAnimating()
                            && this.mPowerState.mScreenState == 2)
                    && setScreenState(3, i2, false)) {
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 4) {
            if (!this.mScreenBrightnessRampAnimator.isAnimating()
                    || this.mPowerState.mScreenState == 4) {
                if (this.mPowerState.mScreenState != 4) {
                    if (!setScreenState(3, i2, false)) {
                        return;
                    } else {
                        setScreenState(4, i2, false);
                    }
                }
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 6) {
            if (!this.mScreenBrightnessRampAnimator.isAnimating()
                    || this.mPowerState.mScreenState == 6) {
                if (this.mPowerState.mScreenState != 6) {
                    if (!setScreenState(2, i2, false)) {
                        return;
                    } else {
                        setScreenState(6, i2, false);
                    }
                }
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        this.mPendingScreenOff = true;
        if (!z2) {
            this.mPowerState.setColorFadeLevel(FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
        DisplayPowerState displayPowerState2 = this.mPowerState;
        if (displayPowerState2.mColorFadeLevel == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            setScreenState(1, i2, false);
            this.mPendingScreenOff = false;
            ColorFade colorFade2 = this.mPowerState.mColorFade;
            if (colorFade2 != null) {
                colorFade2.dismissResources();
                return;
            }
            return;
        }
        int i3 = !z4 ? SAMSUNG_UX_COLOR_FADE_OFF_EFFECT_ENABLED ? 3 : 1 : 2;
        if (!z
                || !displayPowerState2.prepareColorFade(this.mContext, i3)
                || this.mPowerState.mScreenState == 1) {
            this.mPowerState.prepareColorFade(this.mContext, 2);
            this.mColorFadeOffAnimator.end();
        } else {
            if (i3 == 3) {
                this.mColorFadeOffAnimator.setInterpolator(COLOR_FADE_PATH_INTERPOLATOR);
            } else {
                this.mColorFadeOffAnimator.setInterpolator(COLOR_FADE_DEFAULT_INTERPOLATOR);
            }
            this.mColorFadeOffAnimator.start();
        }
    }

    public final float clampScreenBrightness(float f) {
        if (Float.isNaN(f)) {
            f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        BrightnessRangeController brightnessRangeController = this.mBrightnessRangeController;
        return MathUtils.constrain(
                f,
                brightnessRangeController.mHbmController.mBrightnessMin,
                brightnessRangeController.getCurrentBrightnessMax());
    }

    public final float clampScreenBrightnessForFinal(float f) {
        if (Float.isNaN(f)) {
            f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        return MathUtils.constrain(
                f,
                this.mBrightnessRangeController.mHbmController.mBrightnessMin,
                this.mScreenExtendedBrightnessRangeMaximum);
    }

    public final void clearAdaptiveBrightnessLongtermModelBuilder() {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            android.util.Slog.d(
                    "AdaptiveBrightnessLongtermModelBuilder", "clearBrightnessEvents()");
            synchronized (adaptiveBrightnessLongtermModelBuilder.mEventsLock) {
                try {
                    adaptiveBrightnessLongtermModelBuilder.mEvents.clear();
                    adaptiveBrightnessLongtermModelBuilder.mInjector.getClass();
                    AtomicFile file =
                            AdaptiveBrightnessLongtermModelBuilder.Injector.getFile(
                                    "brightness_events_sec.xml");
                    if (file.exists()) {
                        file.delete();
                    }
                    adaptiveBrightnessLongtermModelBuilder.mInjector.getClass();
                    AtomicFile file2 =
                            AdaptiveBrightnessLongtermModelBuilder.Injector.getFile(
                                    "adaptive_brightness_stats_sec.xml");
                    if (file2.exists()) {
                        file2.delete();
                    }
                } finally {
                }
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            Slog.d(this.mTag, "dump");
            printWriter.println();
            printWriter.println("Display Power Controller:");
            printWriter.println("  mDisplayId=" + this.mDisplayId);
            printWriter.println("  mLeadDisplayId=" + this.mLeadDisplayId);
            printWriter.println("  mLightSensor=" + this.mLightSensor);
            printWriter.println(
                    "  mDisplayBrightnessFollowers=" + this.mDisplayBrightnessFollowers);
            printWriter.println();
            printWriter.println("Display Power Controller Locked State:");
            printWriter.println("  mDisplayReadyLocked=" + this.mDisplayReadyLocked);
            printWriter.println("  mPendingRequestLocked=" + this.mPendingRequestLocked);
            printWriter.println(
                    "  mPendingRequestChangedLocked=" + this.mPendingRequestChangedLocked);
            printWriter.println(
                    "  mPendingUpdatePowerStateLocked=" + this.mPendingUpdatePowerStateLocked);
        }
        printWriter.println();
        printWriter.println("Display Power Controller Configuration:");
        StringBuilder m =
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  mScreenBrightnessDozeConfig="),
                                this.mScreenBrightnessDozeConfig,
                                printWriter,
                                "  mScreenBrightnessDimConfig="),
                        this.mScreenBrightnessDimConfig,
                        printWriter,
                        "  mUseSoftwareAutoBrightnessConfig=");
        m.append(this.mUseSoftwareAutoBrightnessConfig);
        printWriter.println(m.toString());
        printWriter.println("  mSkipScreenOnBrightnessRamp=false");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  mColorFadeFadesConfig="),
                                this.mColorFadeFadesConfig,
                                printWriter,
                                "  mColorFadeEnabled="),
                        this.mColorFadeEnabled,
                        printWriter,
                        "  mIsDisplayInternal="),
                this.mIsDisplayInternal,
                printWriter);
        synchronized (this.mCachedBrightnessInfo) {
            printWriter.println(
                    "  mCachedBrightnessInfo.brightness="
                            + this.mCachedBrightnessInfo.brightness.value);
            printWriter.println(
                    "  mCachedBrightnessInfo.adjustedBrightness="
                            + this.mCachedBrightnessInfo.adjustedBrightness.value);
            printWriter.println(
                    "  mCachedBrightnessInfo.brightnessMin="
                            + this.mCachedBrightnessInfo.brightnessMin.value);
            printWriter.println(
                    "  mCachedBrightnessInfo.brightnessMax="
                            + this.mCachedBrightnessInfo.brightnessMax.value);
            printWriter.println(
                    "  mCachedBrightnessInfo.hbmMode=" + this.mCachedBrightnessInfo.hbmMode.value);
            printWriter.println(
                    "  mCachedBrightnessInfo.hbmTransitionPoint="
                            + this.mCachedBrightnessInfo.hbmTransitionPoint.value);
            printWriter.println(
                    "  mCachedBrightnessInfo.brightnessMaxReason ="
                            + this.mCachedBrightnessInfo.brightnessMaxReason.value);
        }
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  mDisplayBlanksAfterDozeConfig="),
                                this.mDisplayBlanksAfterDozeConfig,
                                printWriter,
                                "  mBrightnessBucketsInDozeConfig="),
                        this.mBrightnessBucketsInDozeConfig,
                        printWriter,
                        "  mDozeScaleFactor=");
        m2.append(this.mDozeScaleFactor);
        printWriter.println(m2.toString());
        printWriter.println("  --SEC_PMS");
        StringBuilder m3 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("  AUTO_BRIGHTNESS_TYPE="),
                                        PowerManagerUtil.AUTO_BRIGHTNESS_TYPE,
                                        printWriter,
                                        "  USE_SEC_LONG_TERM_MODEL="),
                                PowerManagerUtil.USE_SEC_LONG_TERM_MODEL,
                                printWriter,
                                "  USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL="),
                        PowerManagerUtil.USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL,
                        printWriter,
                        "  extraDim mExtraDimStrength= ");
        m3.append(this.mExtraDimStrength);
        printWriter.println(m3.toString());
        printWriter.println("  extraDim mExtraDimIsActive= false");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("  mForceDimSettingEnabled= "),
                this.mForceDimSettingEnabled,
                printWriter);
        this.mHandler.runWithScissors(
                new DisplayPowerController$$ExternalSyntheticLambda7(this, printWriter, 0), 50L);
    }

    public final String getAmbientBrightnessInfo(float f) {
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return null;
        }
        float brightness =
                automaticBrightnessController.mCurrentBrightnessMapper.getBrightness(null, f, -1);
        int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(brightness);
        HysteresisLevels hysteresisLevels =
                automaticBrightnessController.mAmbientBrightnessThresholds;
        float darkeningThreshold = hysteresisLevels.getDarkeningThreshold(f);
        float brighteningThreshold = hysteresisLevels.getBrighteningThreshold(f);
        StringBuilder sb = new StringBuilder("getAmbientBrightnessInfo : ");
        sb.append(String.format("%6d", Integer.valueOf(brightnessFloatToInt)));
        sb.append("(");
        Locale locale = Locale.US;
        sb.append(String.format(locale, "%.2f", Float.valueOf(brightness)));
        sb.append(") ");
        sb.append(String.format("%6.0f", Float.valueOf(darkeningThreshold)));
        sb.append(" < ");
        sb.append(String.format(locale, "%8.1f", Float.valueOf(f)));
        sb.append(" < ");
        sb.append(String.format("%6.0f", Float.valueOf(brighteningThreshold)));
        sb.append(" (adj:");
        sb.append(
                String.format(
                        locale,
                        "%+.1f",
                        Float.valueOf(
                                automaticBrightnessController.mCurrentBrightnessMapper
                                        .getAutoBrightnessAdjustment())));
        sb.append(")");
        return sb.toString();
    }

    public final ParceledListSlice getAmbientBrightnessStats(int i) {
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            Slog.e(this.mTag, "getAmbientBrightnessStats: not supported");
        }
        BrightnessTracker brightnessTracker = this.mBrightnessTracker;
        ArrayList arrayList = null;
        if (brightnessTracker == null) {
            return null;
        }
        AmbientBrightnessStatsTracker ambientBrightnessStatsTracker =
                brightnessTracker.mAmbientBrightnessStatsTracker;
        if (ambientBrightnessStatsTracker != null) {
            synchronized (ambientBrightnessStatsTracker) {
                AmbientBrightnessStatsTracker.AmbientBrightnessStats ambientBrightnessStats =
                        ambientBrightnessStatsTracker.mAmbientBrightnessStats;
                if (((HashMap) ambientBrightnessStats.mStats).containsKey(Integer.valueOf(i))) {
                    arrayList =
                            new ArrayList(
                                    (Collection)
                                            ((HashMap) ambientBrightnessStats.mStats)
                                                    .get(Integer.valueOf(i)));
                }
            }
            if (arrayList != null) {
                return new ParceledListSlice(arrayList);
            }
        }
        return ParceledListSlice.emptyList();
    }

    public final BrightnessDynamicRampRatePair getBrightnessDynamicRampRatePair(float f, float f2) {
        float f3;
        if (f <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f = 0.004f;
        }
        float f4 = this.mScreenBrightnessRampAnimator.mFirst.mCurrentValue;
        if (f4 <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f4 = 0.004f;
        }
        float f5 = this.mLastAmbientLux;
        if (f2 <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f2 = 0.9f;
        }
        if (f5 <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f5 = 0.9f;
        }
        int i =
                SystemProperties.getInt(
                        "sys.display.transition.weight", DEFAULT_WEIGHT_FOR_BRIGHTNESS_TRANSITION);
        int i2 = SystemProperties.getInt("sys.display.transition.weight.hbm", 15);
        if (f < f4) {
            float dynamicRampRate =
                    getDynamicRampRate(f, f4, f5, f2, i, MAX_AUTO_BRIGHTNESS_TRANSITION_TIME);
            f3 = f4 > 1.0f ? getDynamicRampRate(f, f4, f5, f2, i2, 30000) : Float.NaN;
            r9 = dynamicRampRate;
        } else {
            f3 = Float.NaN;
        }
        AdaptiveBrightnessAdvancedSettings adaptiveBrightnessAdvancedSettings =
                this.mAdaptiveBrightnessAdvancedSettings;
        if (adaptiveBrightnessAdvancedSettings != null) {
            float f6 = adaptiveBrightnessAdvancedSettings.mDarkeningRateRatio;
            r9 *= f6;
            f3 *= f6;
        }
        BrightnessDynamicRampRatePair brightnessDynamicRampRatePair =
                new BrightnessDynamicRampRatePair();
        brightnessDynamicRampRatePair.brightnessRampRateDynamic = r9;
        brightnessDynamicRampRatePair.brightnessRampRateDynamicAtHbm = f3;
        return brightnessDynamicRampRatePair;
    }

    public final ParceledListSlice getBrightnessEvents(int i, boolean z) {
        BrightnessChangeEvent[] brightnessChangeEventArr;
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            Slog.e(this.mTag, "getBrightnessEvents: not supported");
        }
        BrightnessTracker brightnessTracker = this.mBrightnessTracker;
        if (brightnessTracker == null) {
            return null;
        }
        synchronized (brightnessTracker.mEventsLock) {
            brightnessChangeEventArr =
                    (BrightnessChangeEvent[]) brightnessTracker.mEvents.toArray();
        }
        BrightnessTracker.Injector injector = brightnessTracker.mInjector;
        UserManager userManager = brightnessTracker.mUserManager;
        injector.getClass();
        int[] profileIds =
                userManager != null ? userManager.getProfileIds(i, false) : new int[] {i};
        HashMap hashMap = new HashMap();
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= profileIds.length) {
                break;
            }
            int i3 = profileIds[i2];
            if (z && i3 == i) {
                z2 = false;
            }
            hashMap.put(Integer.valueOf(i3), Boolean.valueOf(z2));
            i2++;
        }
        ArrayList arrayList = new ArrayList(brightnessChangeEventArr.length);
        for (int i4 = 0; i4 < brightnessChangeEventArr.length; i4++) {
            Boolean bool =
                    (Boolean) hashMap.get(Integer.valueOf(brightnessChangeEventArr[i4].userId));
            if (bool != null) {
                if (bool.booleanValue()) {
                    arrayList.add(new BrightnessChangeEvent(brightnessChangeEventArr[i4], true));
                } else {
                    arrayList.add(brightnessChangeEventArr[i4]);
                }
            }
        }
        return new ParceledListSlice(arrayList);
    }

    public final BrightnessInfo getBrightnessInfo() {
        BrightnessInfo brightnessInfo;
        synchronized (this.mCachedBrightnessInfo) {
            CachedBrightnessInfo cachedBrightnessInfo = this.mCachedBrightnessInfo;
            brightnessInfo =
                    new BrightnessInfo(
                            cachedBrightnessInfo.brightness.value,
                            cachedBrightnessInfo.adjustedBrightness.value,
                            cachedBrightnessInfo.brightnessMin.value,
                            cachedBrightnessInfo.brightnessMax.value,
                            cachedBrightnessInfo.hbmMode.value,
                            cachedBrightnessInfo.hbmTransitionPoint.value,
                            cachedBrightnessInfo.brightnessMaxReason.value,
                            cachedBrightnessInfo.isAnimating.value);
        }
        return brightnessInfo;
    }

    public final float getFinalBrightness(float f, int i) {
        this.mPassRampAnimation = false;
        float f2 = this.mPowerRequest.screenBrightnessScaleFactor;
        float f3 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        BrightnessReason brightnessReason = this.mBrightnessReasonTemp;
        if (f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE && f2 != 1.0f) {
            f = clampScreenBrightnessForFinal(f * f2);
            brightnessReason.addModifier(f, 16);
        }
        if (this.mPowerRequest.hbmBlock && f > 1.0f) {
            brightnessReason.addModifier(1.0f, 256);
            f = 1.0f;
        }
        float f4 = this.mPowerRequest.minBrightness;
        if (f4 >= FullScreenMagnificationGestureHandler.MAX_SCALE && f < f4) {
            brightnessReason.addModifier(f4, 32);
            f = f4;
        }
        float f5 = this.mPowerRequest.maxBrightness;
        if (f5 >= FullScreenMagnificationGestureHandler.MAX_SCALE && f > f5) {
            brightnessReason.addModifier(f5, 32);
            f = f5;
        }
        int i2 = this.mPowerRequest.brightnessLimitByCover;
        if (i2 != -1) {
            float f6 = i2;
            if (f > f6) {
                brightnessReason.addModifier(f6, 128);
                f = f6;
            }
        }
        if (this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness()) {
            float f7 = this.mPowerRequest.autoBrightnessUpperLimit;
            if (f7 >= FullScreenMagnificationGestureHandler.MAX_SCALE && f > f7) {
                brightnessReason.addModifier(f7, 64);
                f = f7;
            }
            float f8 = this.mPowerRequest.autoBrightnessLowerLimit;
            if (f8 >= FullScreenMagnificationGestureHandler.MAX_SCALE
                    && !this.mAppliedForceDimming
                    && f < f8) {
                brightnessReason.addModifier(f8, 64);
                f = f8;
            }
        }
        if (this.mFreezeBrightnessMode) {
            int i3 = this.mFreezeBrightnessModeSelector;
            if (i3 != 1) {
                if (i3 == 2 && f <= 1.0f) {
                    f = BrightnessSynchronizer.brightnessIntToFloat(256);
                    brightnessReason.addModifier(
                            f, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                }
            } else if (f > 1.0f) {
                brightnessReason.addModifier(
                        1.0f, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                f = 1.0f;
            }
        }
        if (PowerManagerUtil.isFakeAodAvailable(this.mDualScreenPolicy)
                && Display.isDozeState(i)
                && f > 0.38f) {
            brightnessReason.addModifier(0.38f, 2048);
            f = 0.38f;
        }
        if (this.mPowerRequest.forceLcdBacklightOffEnabled) {
            brightnessReason.addModifier(FullScreenMagnificationGestureHandler.MAX_SCALE, 1024);
        } else {
            f3 = f;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mPowerRequest;
        if ((displayPowerRequest.isOutdoorMode || displayPowerRequest.lcdFlashMode)
                && displayPowerRequest.policy == 3) {
            f3 = Math.max(this.mScreenExtendedBrightnessRangeMaximum, 1.0f);
            brightnessReason.addModifier(f3, 512);
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
        if (this.mPowerRequest.screenCurtainEnabled) {
            this.mPassRampAnimation = true;
            f3 =
                    clampScreenBrightnessForFinal(
                            Math.min(f3, BrightnessSynchronizer.brightnessIntToFloat(49)));
            brightnessReason.addModifier(f3, 32768);
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

    public final void handleBrightnessModeChange() {
        int intForUser =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(),
                        this.mScreenBrightnessModeSettingName,
                        0,
                        -2);
        AutomaticBrightnessStrategy2 automaticBrightnessStrategy2 =
                this.mAutomaticBrightnessStrategy;
        boolean shouldUseAutoBrightness = automaticBrightnessStrategy2.shouldUseAutoBrightness();
        automaticBrightnessStrategy2.setUseAutoBrightness(intForUser == 1);
        if (shouldUseAutoBrightness != automaticBrightnessStrategy2.shouldUseAutoBrightness()) {
            if (!automaticBrightnessStrategy2.shouldUseAutoBrightness()
                    && this.mAutomaticBrightnessController != null) {
                PowerHistorian powerHistorian = this.mPowerHistorian;
                powerHistorian.getClass();
                powerHistorian.addRecord(
                        3, new PowerHistorian.MessageRecord("ShortTermModel: reset data, manual"));
                this.mAutomaticBrightnessController.resetShortTermModel();
            }
            this.mClock.getClass();
            sLastScreenBrightnessSettingChangedTime = SystemClock.uptimeMillis();
            if (PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY) {
                this.mHandler.post(this.mOnBrightnessModeChangeRunnable);
            }
        }
        Slog.d(
                this.mTag,
                "[api] handleBrightnessModeChange: shouldUseAutoBrightness= "
                        + automaticBrightnessStrategy2.shouldUseAutoBrightness());
    }

    public final void initialize(int i) {
        boolean z = this.mColorFadeEnabled;
        ColorFade colorFade =
                z
                        ? new ColorFade(
                                this.mDisplayId,
                                (DisplayManagerInternal)
                                        LocalServices.getService(DisplayManagerInternal.class))
                        : null;
        this.mInjector.getClass();
        DisplayPowerState displayPowerState =
                new DisplayPowerState(
                        this.mBlanker,
                        colorFade,
                        this.mDisplayId,
                        i,
                        BackgroundThread.getExecutor());
        this.mPowerState = displayPowerState;
        if (z) {
            DisplayPowerState.AnonymousClass1 anonymousClass1 = DisplayPowerState.COLOR_FADE_LEVEL;
            ObjectAnimator ofFloat =
                    ObjectAnimator.ofFloat(
                            displayPowerState,
                            anonymousClass1,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            1.0f);
            this.mColorFadeOnAnimator = ofFloat;
            ofFloat.setDuration(160L);
            ObjectAnimator objectAnimator = this.mColorFadeOnAnimator;
            AnonymousClass2 anonymousClass2 = this.mAnimatorListener;
            objectAnimator.addListener(anonymousClass2);
            ObjectAnimator ofFloat2 =
                    ObjectAnimator.ofFloat(
                            this.mPowerState,
                            anonymousClass1,
                            1.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE);
            this.mColorFadeOffAnimator = ofFloat2;
            ofFloat2.setDuration(320L);
            this.mColorFadeOffAnimator.addListener(anonymousClass2);
        }
        this.mScreenBrightnessRampAnimator =
                new RampAnimator.DualRampAnimator(
                        this.mPowerState,
                        DisplayPowerState.SCREEN_BRIGHTNESS_FLOAT,
                        DisplayPowerState.SCREEN_SDR_BRIGHTNESS_FLOAT);
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        setAnimatorRampSpeeds(
                automaticBrightnessController != null
                        && automaticBrightnessController.isInIdleMode());
        this.mScreenBrightnessRampAnimator.mListener = this.mRampAnimatorListener;
        noteScreenState(this.mPowerState.mScreenState, 1);
        noteScreenBrightness(this.mPowerState.mScreenBrightness);
        this.mHqmDataDispatcher = HqmDataDispatcher.HqmDataDispatcherHolder.INSTANCE;
        float f = this.mPowerState.mScreenBrightness;
        DisplayBrightnessController displayBrightnessController = this.mDisplayBrightnessController;
        float convertToAdjustedNits = displayBrightnessController.convertToAdjustedNits(f);
        if (convertToAdjustedNits >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                        this.mAdaptiveBrightnessLongtermModelBuilder;
                if (adaptiveBrightnessLongtermModelBuilder != null) {
                    android.util.Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Start");
                    adaptiveBrightnessLongtermModelBuilder
                            .mBgHandler
                            .obtainMessage(0, Float.valueOf(convertToAdjustedNits))
                            .sendToTarget();
                }
            } else {
                BrightnessTracker brightnessTracker = this.mBrightnessTracker;
                brightnessTracker.getClass();
                if (BrightnessTracker.DEBUG) {
                    android.util.Slog.d("BrightnessTracker", "Start");
                }
                brightnessTracker.mCurrentUserId = ActivityManager.getCurrentUser();
                brightnessTracker
                        .mBgHandler
                        .obtainMessage(0, Float.valueOf(convertToAdjustedNits))
                        .sendToTarget();
            }
        }
        DisplayPowerController$$ExternalSyntheticLambda10
                displayPowerController$$ExternalSyntheticLambda10 =
                        new DisplayPowerController$$ExternalSyntheticLambda10(this);
        displayBrightnessController.mBrightnessSettingListener =
                displayPowerController$$ExternalSyntheticLambda10;
        BrightnessSetting brightnessSetting = displayBrightnessController.mBrightnessSetting;
        if (brightnessSetting.mListeners.contains(
                displayPowerController$$ExternalSyntheticLambda10)) {
            android.util.Slog.wtf("BrightnessSetting", "Duplicate Listener added");
        }
        brightnessSetting.mListeners.add(displayPowerController$$ExternalSyntheticLambda10);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uriFor = Settings.System.getUriFor("screen_auto_brightness_adj");
        SettingsObserver settingsObserver = this.mSettingsObserver;
        contentResolver.registerContentObserver(uriFor, false, settingsObserver, -1);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor(this.mScreenBrightnessModeSettingName),
                        false,
                        settingsObserver,
                        -1);
        if (this.mFlags.mAutoBrightnessModesFlagState.isEnabled()) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("screen_brightness_for_als"),
                            false,
                            settingsObserver,
                            -2);
        }
        if (this.mIsDisplayInternal) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("game_autobrightness_lock"),
                            false,
                            settingsObserver,
                            -1);
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("auto_dim_screen"), false, settingsObserver, -1);
        handleBrightnessModeChange();
    }

    public final void loadBrightnessRampRates() {
        DisplayDeviceConfig displayDeviceConfig = this.mDisplayDeviceConfig;
        this.mBrightnessRampRateFastDecrease = displayDeviceConfig.mBrightnessRampFastDecrease;
        this.mBrightnessRampRateFastIncrease = displayDeviceConfig.mBrightnessRampFastIncrease;
        this.mBrightnessRampRateSlowDecrease = displayDeviceConfig.mBrightnessRampSlowDecrease;
        this.mBrightnessRampRateSlowIncrease = displayDeviceConfig.mBrightnessRampSlowIncrease;
        this.mBrightnessRampRateSlowDecreaseIdle =
                displayDeviceConfig.mBrightnessRampSlowDecreaseIdle;
        this.mBrightnessRampRateSlowIncreaseIdle =
                displayDeviceConfig.mBrightnessRampSlowIncreaseIdle;
        this.mBrightnessRampDecreaseMaxTimeMillis =
                displayDeviceConfig.mBrightnessRampDecreaseMaxMillis;
        this.mBrightnessRampIncreaseMaxTimeMillis =
                displayDeviceConfig.mBrightnessRampIncreaseMaxMillis;
        this.mBrightnessRampDecreaseMaxTimeIdleMillis =
                displayDeviceConfig.mBrightnessRampDecreaseMaxIdleMillis;
        this.mBrightnessRampIncreaseMaxTimeIdleMillis =
                displayDeviceConfig.mBrightnessRampIncreaseMaxIdleMillis;
    }

    public final void loadNitsRange(Resources resources) {
        DisplayDeviceConfig displayDeviceConfig = this.mDisplayDeviceConfig;
        if (displayDeviceConfig != null) {
            EvenDimmerBrightnessData evenDimmerBrightnessData =
                    displayDeviceConfig.mEvenDimmerBrightnessData;
            if ((evenDimmerBrightnessData != null
                            ? evenDimmerBrightnessData.mNits
                            : displayDeviceConfig.mNits)
                    != null) {
                this.mNitsRange =
                        evenDimmerBrightnessData != null
                                ? evenDimmerBrightnessData.mNits
                                : displayDeviceConfig.mNits;
                return;
            }
        }
        Slog.w(this.mTag, "Screen brightness nits configuration is unavailable; falling back");
        this.mNitsRange =
                BrightnessMappingStrategy.getFloatArray(resources.obtainTypedArray(17236304));
    }

    public final void logBrightnessEvent(BrightnessEvent brightnessEvent, float f) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5 = brightnessEvent.mReason.mModifier;
        int i6 = brightnessEvent.mFlags;
        boolean z2 = f == brightnessEvent.mHbmMax;
        float f2 = brightnessEvent.mBrightness;
        DisplayBrightnessController displayBrightnessController = this.mDisplayBrightnessController;
        float convertToAdjustedNits = displayBrightnessController.convertToAdjustedNits(f2);
        int i7 = brightnessEvent.mFlags;
        float f3 = (i7 & 32) != 0 ? brightnessEvent.mPowerFactor : -1.0f;
        int i8 = (i7 & 1) != 0 ? brightnessEvent.mRbcStrength : -1;
        float convertToAdjustedNits2 =
                brightnessEvent.mHbmMode == 0
                        ? -1.0f
                        : displayBrightnessController.convertToAdjustedNits(
                                brightnessEvent.mHbmMax);
        float f4 = brightnessEvent.mThermalMax;
        float convertToAdjustedNits3 =
                f4 == 1.0f ? -1.0f : displayBrightnessController.convertToAdjustedNits(f4);
        if (this.mIsDisplayInternal) {
            float convertToAdjustedNits4 =
                    displayBrightnessController.convertToAdjustedNits(
                            brightnessEvent.mInitialBrightness);
            float f5 = brightnessEvent.mLux;
            String str = brightnessEvent.mPhysicalDisplayId;
            boolean z3 = brightnessEvent.mWasShortTermModelActive;
            boolean z4 = brightnessEvent.mAutomaticBrightnessEnabled;
            switch (brightnessEvent.mReason.mReason) {
                case 1:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                case 3:
                    i2 = 3;
                    i = i2;
                    break;
                case 4:
                    i2 = 4;
                    i = i2;
                    break;
                case 5:
                    i2 = 5;
                    i = i2;
                    break;
                case 6:
                    i2 = 6;
                    i = i2;
                    break;
                case 7:
                    i2 = 7;
                    i = i2;
                    break;
                case 8:
                    i2 = 8;
                    i = i2;
                    break;
                case 9:
                    i2 = 9;
                    i = i2;
                    break;
                case 10:
                    i2 = 10;
                    i = i2;
                    break;
                default:
                    i = 0;
                    break;
            }
            int i9 = 0;
            while (true) {
                float[] fArr = BRIGHTNESS_RANGE_BOUNDARIES;
                if (i9 >= fArr.length) {
                    i3 = 38;
                } else if (convertToAdjustedNits < fArr[i9]) {
                    i3 = BRIGHTNESS_RANGE_INDEX[i9];
                } else {
                    i9++;
                }
            }
            int i10 = i3;
            int i11 = brightnessEvent.mHbmMode;
            if (i11 == 1) {
                i4 = 2;
                z = true;
            } else {
                i4 = 2;
                z = false;
            }
            FrameworkStatsLog.write(
                    FrameworkStatsLog.DISPLAY_BRIGHTNESS_CHANGED,
                    convertToAdjustedNits4,
                    convertToAdjustedNits,
                    f5,
                    str,
                    z3,
                    f3,
                    i8,
                    convertToAdjustedNits2,
                    convertToAdjustedNits3,
                    z4,
                    1,
                    i,
                    i10,
                    z2,
                    z,
                    i11 == i4,
                    (i5 & 2) > 0,
                    this.mBrightnessClamperController.getBrightnessMaxReason(),
                    (i5 & 1) > 0,
                    (brightnessEvent.mFlags & 1) != 0,
                    (i6 & 2) > 0,
                    (i6 & 4) > 0,
                    (i6 & 8) > 0,
                    brightnessEvent.mAutoBrightnessMode == 1,
                    (i6 & 32) > 0);
        }
    }

    public final void noteScreenBrightness(float f) {
        HqmDataDispatcher hqmDataDispatcher;
        IBatteryStats iBatteryStats = this.mBatteryStats;
        int i = this.mDisplayId;
        if (iBatteryStats != null) {
            try {
                if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                    iBatteryStats.noteDualScreenBrightness(
                            BrightnessSynchronizer.brightnessFloatToInt(f),
                            i,
                            this.mDualScreenPolicy);
                } else {
                    this.mBatteryStats.noteScreenBrightness(
                            this.mFlags.mBrightnessIntRangeUserPerceptionFlagState.isEnabled()
                                    ? BrightnessSynchronizer.brightnessFloatToIntSetting(
                                            this.mContext, f)
                                    : BrightnessSynchronizer.brightnessFloatToInt(f));
                }
            } catch (RemoteException unused) {
            }
        }
        if (!this.mIsDisplayInternal || (hqmDataDispatcher = this.mHqmDataDispatcher) == null) {
            return;
        }
        if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
            i = !this.mDisplayDeviceConfig.mIsFirstDisplay ? 1 : 0;
        }
        hqmDataDispatcher.noteScreenBrightness(f, i);
    }

    public final void noteScreenState(int i, int i2) {
        HqmDataDispatcher hqmDataDispatcher;
        FrameworkStatsLog.write(
                FrameworkStatsLog.SCREEN_STATE_CHANGED_V2, i, this.mDisplayStatsId, i2);
        IBatteryStats iBatteryStats = this.mBatteryStats;
        int i3 = this.mDisplayId;
        if (iBatteryStats != null) {
            try {
                if (!PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                    iBatteryStats.noteScreenState(i);
                } else if (!PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
                        || ((i3 != 0 || this.mDualScreenPolicy != 1)
                                && (i3 != 1 || this.mDualScreenPolicy != 0))) {
                    Slog.d(
                            this.mTag,
                            "noteDualScreenState: State="
                                    + Display.stateToString(i)
                                    + ", dualScreenPolicy="
                                    + this.mDualScreenPolicy);
                    this.mBatteryStats.noteDualScreenState(i, i3, this.mDualScreenPolicy);
                }
            } catch (RemoteException unused) {
            }
        }
        if (!this.mIsDisplayInternal || (hqmDataDispatcher = this.mHqmDataDispatcher) == null) {
            return;
        }
        boolean z = PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY;
        if (z) {
            i3 = !this.mDisplayDeviceConfig.mIsFirstDisplay ? 1 : 0;
        }
        if (i3 == 0 || (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && i3 == 1)) {
            HqmDataDispatcher.DisplayStat displayStat = hqmDataDispatcher.getDisplayStat(i3);
            boolean z2 = displayStat.mScreenState == 2;
            boolean z3 = i == 2;
            HqmDataDispatcher.Timer timer = displayStat.mScreenOnTimer;
            if (!z2 && z3) {
                displayStat.mScreenOnCount++;
                timer.getClass();
                timer.startTimeMillis = SystemClock.uptimeMillis();
            } else if (z2 && !z3) {
                float f = displayStat.mScreenOnDuration;
                timer.getClass();
                displayStat.mScreenOnDuration =
                        (long)
                                (((float)
                                                ((SystemClock.uptimeMillis()
                                                                - timer.startTimeMillis)
                                                        / 1000.0d))
                                        + f);
                int i4 = displayStat.mCurrentBrightnessRange;
                if (i4 != -1) {
                    long[] jArr = displayStat.mBrightnessDuration;
                    float f2 = jArr[i4];
                    displayStat.mScreenBrightnessTimers[i4].getClass();
                    jArr[i4] =
                            (long)
                                    (((float)
                                                    ((SystemClock.uptimeMillis()
                                                                    - r13.startTimeMillis)
                                                            / 1000.0d))
                                            + f2);
                    displayStat.mCurrentBrightnessRange = -1;
                }
                if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST
                        && displayStat.mScreenBrightnessHighestStarted) {
                    float f3 = displayStat.mScreenBrightnessHighestDuration;
                    displayStat.mScreenBrightnessHighestTimer.getClass();
                    displayStat.mScreenBrightnessHighestDuration =
                            (long)
                                    (((float)
                                                    ((SystemClock.uptimeMillis()
                                                                    - r6.startTimeMillis)
                                                            / 1000.0d))
                                            + f3);
                    displayStat.mScreenBrightnessHighestStarted = false;
                }
            }
            displayStat.mScreenState = i;
            if (z && i == 2) {
                hqmDataDispatcher.noteScreenBrightness(hqmDataDispatcher.mGlobalBrightness, i3);
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyBrightnessTrackerChanged(
            float r16, boolean r17, boolean r18, boolean r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayPowerController.notifyBrightnessTrackerChanged(float,"
                    + " boolean, boolean, boolean, boolean, boolean):void");
    }

    public final void onDisplayChanged(
            final HighBrightnessModeMetadata highBrightnessModeMetadata, int i) {
        this.mLeadDisplayId = i;
        LogicalDisplay logicalDisplay = this.mLogicalDisplay;
        final DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        if (displayDevice == null) {
            String str =
                    "Display Device is null in DisplayPowerController2 for display: "
                            + logicalDisplay.mDisplayId;
            int i2 = Slog.$r8$clinit;
            android.util.Slog.wtf(this.mTag, str);
            return;
        }
        final DisplayDeviceConfig displayDeviceConfig = displayDevice.getDisplayDeviceConfig();
        final IBinder iBinder = displayDevice.mDisplayToken;
        final DisplayDeviceInfo displayDeviceInfoLocked =
                displayDevice.getDisplayDeviceInfoLocked();
        DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
        final int i3 = displayInfoLocked.logicalWidth;
        final int i4 = displayInfoLocked.logicalHeight;
        final boolean z = logicalDisplay.mIsEnabled;
        final boolean z2 = logicalDisplay.mIsInTransition;
        DisplayDevice displayDevice2 = logicalDisplay.mPrimaryDisplayDevice;
        final boolean z3 =
                displayDevice2 != null && displayDevice2.getDisplayDeviceInfoLocked().type == 1;
        final String str2 = logicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        final String str3 = logicalDisplay.mPowerThrottlingDataId;
        final String str4 = displayDevice.mUniqueId;
        Runnable runnable = new Runnable() { // from class:
                    // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 298
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda1.run():void");
                    }
                };
        this.mClock.getClass();
        this.mHandler.postAtTime(runnable, SystemClock.uptimeMillis());
    }

    public final void postBrightnessChangeRunnable() {
        Runnable runnable = this.mOnBrightnessChangeRunnable;
        DisplayControllerHandler displayControllerHandler = this.mHandler;
        if (displayControllerHandler.hasCallbacks(runnable)) {
            return;
        }
        displayControllerHandler.post(this.mOnBrightnessChangeRunnable);
    }

    public final void putAutoBrightnessTransitionTime(float f, float f2, float f3) {
        int i;
        float abs;
        float f4 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        String str = this.mTag;
        if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
            float f5 = this.mScreenBrightnessRampAnimator.mFirst.mCurrentValue;
            if (!Float.isNaN(f2)) {
                if (f5 > 1.0f && f3 <= 1.0f) {
                    f4 = f5 - 1.0f;
                    abs = 1.0f - f3;
                } else if (1.0f >= f3 || f3 >= f5) {
                    Slog.e(
                            str,
                            "getTransitionTimeWithHbm: rateAtHbm: "
                                    + f2
                                    + " currentBrightness: "
                                    + f5
                                    + " targetBrightness: "
                                    + f3);
                    abs = Math.abs(f5 - f3);
                } else {
                    float f6 = f5 - f3;
                    abs = 0.0f;
                    f4 = f6;
                }
                i =
                        ((int) Math.round((f4 / f2) * 1000.0d))
                                + ((int) Math.round((abs / f) * 1000.0d));
            } else {
                i = (int) Math.round((Math.abs(f5 - f3) / f) * 1000.0d);
            }
        } else {
            i = 0;
        }
        Slog.d(str, "putAutoBrightnessTransitionTime: transitionTime=" + i);
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(), "auto_brightness_transition_time", i, -2);
    }

    public final void reportStats(float f) {
        if (this.mLastStatsBrightness == f) {
            return;
        }
        synchronized (this.mCachedBrightnessInfo) {
            try {
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
                        int i = this.mDisplayStatsId;
                        synchronized (this.mHandler) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.DISPLAY_HBM_BRIGHTNESS_CHANGED, i, f);
                        }
                        return;
                    }
                    Message obtainMessage = this.mHandler.obtainMessage();
                    obtainMessage.what = 11;
                    obtainMessage.arg1 = Float.floatToIntBits(f);
                    obtainMessage.arg2 = this.mDisplayStatsId;
                    DisplayControllerHandler displayControllerHandler = this.mHandler;
                    this.mClock.getClass();
                    displayControllerHandler.sendMessageAtTime(
                            obtainMessage, SystemClock.uptimeMillis() + 500);
                }
            } finally {
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean requestPowerState(
            android.hardware.display.DisplayManagerInternal.DisplayPowerRequest r6, boolean r7) {
        /*
            r5 = this;
            boolean r0 = com.android.server.display.DisplayPowerController.DEBUG
            if (r0 == 0) goto L20
            java.lang.String r0 = r5.mTag
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "requestPowerState: "
            r1.<init>(r2)
            r1.append(r6)
            java.lang.String r2 = ", waitForNegativeProximity="
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            com.android.server.power.Slog.d(r0, r1)
        L20:
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.mStopped     // Catch: java.lang.Throwable -> L2a
            r2 = 1
            if (r1 == 0) goto L2c
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L2a
            return r2
        L2a:
            r5 = move-exception
            goto L83
        L2c:
            com.android.server.display.DisplayPowerProximityStateController r1 = r5.mDisplayPowerProximityStateController     // Catch: java.lang.Throwable -> L2a
            java.lang.Object r3 = r1.mLock     // Catch: java.lang.Throwable -> L2a
            monitor-enter(r3)     // Catch: java.lang.Throwable -> L2a
            r4 = 0
            if (r7 == 0) goto L3f
            boolean r7 = r1.mPendingWaitForNegativeProximityLocked     // Catch: java.lang.Throwable -> L3d
            if (r7 != 0) goto L3f
            r1.mPendingWaitForNegativeProximityLocked = r2     // Catch: java.lang.Throwable -> L3d
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L3d
            r7 = r2
            goto L41
        L3d:
            r5 = move-exception
            goto L81
        L3f:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L3d
            r7 = r4
        L41:
            android.hardware.display.DisplayManagerInternal$DisplayPowerRequest r1 = r5.mPendingRequestLocked     // Catch: java.lang.Throwable -> L2a
            if (r1 != 0) goto L4e
            android.hardware.display.DisplayManagerInternal$DisplayPowerRequest r7 = new android.hardware.display.DisplayManagerInternal$DisplayPowerRequest     // Catch: java.lang.Throwable -> L2a
            r7.<init>(r6)     // Catch: java.lang.Throwable -> L2a
            r5.mPendingRequestLocked = r7     // Catch: java.lang.Throwable -> L2a
        L4c:
            r7 = r2
            goto L5a
        L4e:
            boolean r1 = r1.equals(r6)     // Catch: java.lang.Throwable -> L2a
            if (r1 != 0) goto L5a
            android.hardware.display.DisplayManagerInternal$DisplayPowerRequest r7 = r5.mPendingRequestLocked     // Catch: java.lang.Throwable -> L2a
            r7.copyFrom(r6)     // Catch: java.lang.Throwable -> L2a
            goto L4c
        L5a:
            boolean r1 = r6.forceSlowChange     // Catch: java.lang.Throwable -> L2a
            if (r1 == 0) goto L65
            boolean r1 = r5.mPendingForceSlowChangeLocked     // Catch: java.lang.Throwable -> L2a
            if (r1 != 0) goto L65
            r5.mPendingForceSlowChangeLocked = r2     // Catch: java.lang.Throwable -> L2a
            r7 = r2
        L65:
            boolean r6 = r6.earlyWakeUp     // Catch: java.lang.Throwable -> L2a
            if (r6 == 0) goto L70
            boolean r6 = r5.mPendingEarlyWakeUpRequestLocked     // Catch: java.lang.Throwable -> L2a
            if (r6 != 0) goto L70
            r5.mPendingEarlyWakeUpRequestLocked = r2     // Catch: java.lang.Throwable -> L2a
            r7 = r2
        L70:
            if (r7 == 0) goto L7d
            r5.mDisplayReadyLocked = r4     // Catch: java.lang.Throwable -> L2a
            boolean r6 = r5.mPendingRequestChangedLocked     // Catch: java.lang.Throwable -> L2a
            if (r6 != 0) goto L7d
            r5.mPendingRequestChangedLocked = r2     // Catch: java.lang.Throwable -> L2a
            r5.sendUpdatePowerStateLocked()     // Catch: java.lang.Throwable -> L2a
        L7d:
            boolean r5 = r5.mDisplayReadyLocked     // Catch: java.lang.Throwable -> L2a
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L2a
            return r5
        L81:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L3d
            throw r5     // Catch: java.lang.Throwable -> L2a
        L83:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L2a
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayPowerController.requestPowerState(android.hardware.display.DisplayManagerInternal$DisplayPowerRequest,"
                    + " boolean):boolean");
    }

    public final void restartAdaptiveBrightnessLongtermModelBuilderInternal(final boolean z) {
        boolean z2;
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                this.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder != null) {
            synchronized (adaptiveBrightnessLongtermModelBuilder.mDataCollectionLock) {
                z2 = adaptiveBrightnessLongtermModelBuilder.mStarted;
            }
            if (z2) {
                final AdaptiveBrightnessLongtermModelBuilder
                        adaptiveBrightnessLongtermModelBuilder2 =
                                this.mAdaptiveBrightnessLongtermModelBuilder;
                adaptiveBrightnessLongtermModelBuilder2.mBgHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AdaptiveBrightnessLongtermModelBuilder
                                        adaptiveBrightnessLongtermModelBuilder3 =
                                                AdaptiveBrightnessLongtermModelBuilder.this;
                                boolean z3 = z;
                                adaptiveBrightnessLongtermModelBuilder3
                                        .readAdaptiveBrightnessStats();
                                if (z3) {
                                    AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                                            adaptiveBrightnessLongtermModelBuilder3
                                                    .mAdaptiveBrightnessStatsTracker;
                                    synchronized (adaptiveBrightnessStatsTracker) {
                                        AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats
                                                adaptiveBrightnessStats =
                                                        adaptiveBrightnessStatsTracker
                                                                .mAdaptiveBrightnessStats;
                                        if ((((HashMap) adaptiveBrightnessStats.mStats)
                                                                .containsKey(0)
                                                        ? (AdaptiveBrightnessWeightStats)
                                                                ((HashMap)
                                                                                adaptiveBrightnessStats
                                                                                        .mStats)
                                                                        .get(0)
                                                        : null)
                                                == null) {
                                            adaptiveBrightnessStats
                                                    .getOrCreateUserStats(
                                                            0, adaptiveBrightnessStats.mStats)
                                                    .setMaxWeight();
                                        } else {
                                            android.util.Slog.e(
                                                    "AdaptiveBrightnessStatsTracker",
                                                    "setMaxWeight: shoudn't be here");
                                        }
                                    }
                                }
                                adaptiveBrightnessLongtermModelBuilder3
                                        .stopAdaptiveBrightnessStatsTracker();
                                adaptiveBrightnessLongtermModelBuilder3
                                        .startAdaptiveBrightnessStatsTracker();
                            }
                        });
            }
        }
    }

    public final boolean saveBrightnessInfo(
            float f, float f2, DisplayBrightnessState displayBrightnessState) {
        float f3;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        synchronized (this.mCachedBrightnessInfo) {
            if (displayBrightnessState != null) {
                try {
                    f3 = displayBrightnessState.mMaxBrightness;
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                f3 = 1.0f;
            }
            float max =
                    Math.max(
                            displayBrightnessState != null
                                    ? displayBrightnessState.mMinBrightness
                                    : 1.0f,
                            Math.min(
                                    this.mBrightnessRangeController.mHbmController.mBrightnessMin,
                                    f3));
            float min = Math.min(this.mBrightnessRangeController.getCurrentBrightnessMax(), f3);
            boolean checkAndSetFloat =
                    CachedBrightnessInfo.checkAndSetFloat(this.mCachedBrightnessInfo.brightness, f)
                            | CachedBrightnessInfo.checkAndSetFloat(
                                    this.mCachedBrightnessInfo.adjustedBrightness, f2)
                            | CachedBrightnessInfo.checkAndSetFloat(
                                    this.mCachedBrightnessInfo.brightnessMin, max)
                            | CachedBrightnessInfo.checkAndSetFloat(
                                    this.mCachedBrightnessInfo.brightnessMax, min);
            CachedBrightnessInfo cachedBrightnessInfo = this.mCachedBrightnessInfo;
            MutableInt mutableInt = cachedBrightnessInfo.hbmMode;
            HighBrightnessModeController highBrightnessModeController =
                    this.mBrightnessRangeController.mHbmController;
            int i = highBrightnessModeController.mHbmMode;
            boolean z5 = false;
            if (mutableInt.value != i) {
                mutableInt.value = i;
                z = true;
            } else {
                z = false;
            }
            boolean checkAndSetFloat2 =
                    checkAndSetFloat
                            | z
                            | CachedBrightnessInfo.checkAndSetFloat(
                                    cachedBrightnessInfo.hbmTransitionPoint,
                                    highBrightnessModeController.deviceSupportsHbm()
                                            ? highBrightnessModeController.mHbmData.transitionPoint
                                            : Float.POSITIVE_INFINITY);
            MutableInt mutableInt2 = this.mCachedBrightnessInfo.brightnessMaxReason;
            int brightnessMaxReason = this.mBrightnessClamperController.getBrightnessMaxReason();
            if (mutableInt2.value != brightnessMaxReason) {
                mutableInt2.value = brightnessMaxReason;
                z2 = true;
            } else {
                z2 = false;
            }
            boolean z6 = checkAndSetFloat2 | z2;
            MutableBoolean mutableBoolean = this.mCachedBrightnessInfo.isAnimating;
            RampAnimator.DualRampAnimator dualRampAnimator = this.mScreenBrightnessRampAnimator;
            boolean z7 = dualRampAnimator != null && dualRampAnimator.isAnimating();
            if (mutableBoolean.value != z7) {
                mutableBoolean.value = z7;
                z3 = true;
            } else {
                z3 = false;
            }
            z4 = z6 | z3;
            if (z4) {
                String str = this.mTag;
                StringBuilder sb = new StringBuilder("saveBrightnessInfo: brt:");
                sb.append(f);
                sb.append(" adjBrt:");
                sb.append(f2);
                sb.append(" min:");
                sb.append(max);
                sb.append(" max:");
                sb.append(min);
                sb.append(" hbm:");
                sb.append(this.mBrightnessRangeController.mHbmController.mHbmMode);
                sb.append(" tp:");
                HighBrightnessModeController highBrightnessModeController2 =
                        this.mBrightnessRangeController.mHbmController;
                sb.append(
                        highBrightnessModeController2.deviceSupportsHbm()
                                ? highBrightnessModeController2.mHbmData.transitionPoint
                                : Float.POSITIVE_INFINITY);
                sb.append(" throttler:");
                sb.append(this.mBrightnessThrottler.mBrightnessMaxReason);
                sb.append(" isAnimating:");
                RampAnimator.DualRampAnimator dualRampAnimator2 =
                        this.mScreenBrightnessRampAnimator;
                if (dualRampAnimator2 != null && dualRampAnimator2.isAnimating()) {
                    z5 = true;
                }
                sb.append(z5);
                Slog.d(str, sb.toString());
            }
        }
        return z4;
    }

    public final void sendOnStateChangedWithWakelock() {
        WakelockController wakelockController = this.mWakelockController;
        if (wakelockController.acquireWakelock(4)) {
            wakelockController.getClass();
            this.mHandler.post(
                    new WakelockController$$ExternalSyntheticLambda0(wakelockController, 0));
        }
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
        DisplayControllerHandler displayControllerHandler = this.mHandler;
        Message obtainMessage = displayControllerHandler.obtainMessage(1);
        this.mClock.getClass();
        displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
    }

    public final void setAnimatorRampSpeeds(boolean z) {
        if (this.mScreenBrightnessRampAnimator == null) {
            return;
        }
        if (this.mFlags.mAdaptiveToneImprovements1.isEnabled() && z) {
            RampAnimator.DualRampAnimator dualRampAnimator = this.mScreenBrightnessRampAnimator;
            long j = this.mBrightnessRampIncreaseMaxTimeIdleMillis;
            long j2 = this.mBrightnessRampDecreaseMaxTimeIdleMillis;
            dualRampAnimator.mFirst.setAnimationTimeLimits(j, j2);
            dualRampAnimator.mSecond.setAnimationTimeLimits(j, j2);
            return;
        }
        RampAnimator.DualRampAnimator dualRampAnimator2 = this.mScreenBrightnessRampAnimator;
        long j3 = this.mBrightnessRampIncreaseMaxTimeMillis;
        long j4 = this.mBrightnessRampDecreaseMaxTimeMillis;
        dualRampAnimator2.mFirst.setAnimationTimeLimits(j3, j4);
        dualRampAnimator2.mSecond.setAnimationTimeLimits(j3, j4);
    }

    public final void setBrightnessConfiguration(
            BrightnessConfiguration brightnessConfiguration, boolean z) {
        Slog.d(
                this.mTag,
                "setBrightnessConfiguration: "
                        + brightnessConfiguration
                        + " shouldResetShortTermModel: "
                        + z
                        + " ("
                        + Debug.getCallers(5)
                        + ")");
        this.mHandler
                .obtainMessage(
                        4,
                        z ? 1 : 0,
                        brightnessConfiguration == null ? 1 : 0,
                        brightnessConfiguration)
                .sendToTarget();
    }

    public final void setBrightnessToFollow(float f, float f2, float f3, boolean z) {
        this.mBrightnessRangeController.onAmbientLuxChange(f3);
        DisplayBrightnessController displayBrightnessController = this.mDisplayBrightnessController;
        if (f2 == -1.0f) {
            displayBrightnessController.setBrightnessToFollow(z, f);
        } else {
            float brightnessFromNits = displayBrightnessController.getBrightnessFromNits(f2);
            if (BrightnessUtils.isValidBrightnessValue(brightnessFromNits)) {
                displayBrightnessController.setBrightnessToFollow(z, brightnessFromNits);
            } else {
                displayBrightnessController.setBrightnessToFollow(z, f);
            }
        }
        sendUpdatePowerState();
    }

    public final int setFreezeBrightnessMode(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mFreezeBrightnessMode != z) {
                    Slog.d(this.mTag, "setFreezeBrightnessMode: " + z);
                    this.mFreezeBrightnessMode = z;
                    if (z) {
                        if (this.mScreenBrightnessRampAnimator.mFirst.mCurrentValue <= 1.0f) {
                            this.mFreezeBrightnessModeSelector = 1;
                            Slog.d(this.mTag, "NON_HBM_FREEZE_MODE");
                        } else {
                            this.mFreezeBrightnessModeSelector = 2;
                            Slog.d(this.mTag, "HBM_FREEZE_MODE");
                        }
                    }
                }
                if (!this.mFreezeBrightnessMode) {
                    return 0;
                }
                return this.mFreezeBrightnessModeSelector;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setRampSpeedToFollower(float f, float f2) {
        this.mFollowerRampSpeed = f;
        this.mFollowerRampSpeedAtHbm = f2;
    }

    public final void setReportedScreenState(int i) {
        Trace.traceCounter(131072L, "ReportedScreenStateToPolicy", i);
        this.mReportedScreenStateToPolicy = i;
        if (i == 2) {
            this.mScreenTurningOnWasBlockedByDisplayOffload = false;
        }
    }

    public final boolean setScreenState(int i, int i2, boolean z) {
        int i3;
        boolean z2 = i == 1;
        boolean z3 = i == 2;
        boolean z4 = this.mPowerState.mScreenState != i;
        String str = this.mTag;
        if (z3 && z4 && !this.mScreenTurningOnWasBlockedByDisplayOffload) {
            DisplayManagerInternal.DisplayOffloadSession displayOffloadSession =
                    this.mDisplayOffloadSession;
            if (this.mPendingScreenOnUnblockerByDisplayOffload == null
                    && displayOffloadSession != null) {
                this.mScreenTurningOnWasBlockedByDisplayOffload = true;
                Trace.asyncTraceBegin(131072L, "Screen on blocked by displayoffload", 0);
                this.mScreenOnBlockByDisplayOffloadStartRealTime = SystemClock.elapsedRealtime();
                DisplayPowerController$$ExternalSyntheticLambda7
                        displayPowerController$$ExternalSyntheticLambda7 =
                                new DisplayPowerController$$ExternalSyntheticLambda7(
                                        this, displayOffloadSession, 1);
                this.mPendingScreenOnUnblockerByDisplayOffload =
                        displayPowerController$$ExternalSyntheticLambda7;
                if (displayOffloadSession.blockScreenOn(
                        displayPowerController$$ExternalSyntheticLambda7)) {
                    Slog.i(str, "Blocking screen on for offloading.");
                } else {
                    this.mPendingScreenOnUnblockerByDisplayOffload = null;
                    Slog.w(
                            str,
                            "Tried blocking screen on for offloading but failed. So, end trace"
                                + " after "
                                    + (SystemClock.elapsedRealtime()
                                            - this.mScreenOnBlockByDisplayOffloadStartRealTime)
                                    + " ms.");
                    Trace.asyncTraceEnd(131072L, "Screen on blocked by displayoffload", 0);
                }
            }
        } else if (!z3 && this.mScreenTurningOnWasBlockedByDisplayOffload) {
            unblockScreenOnByDisplayOffload();
            this.mScreenTurningOnWasBlockedByDisplayOffload = false;
        }
        int i4 = this.mDisplayId;
        DisplayPowerProximityStateController displayPowerProximityStateController =
                this.mDisplayPowerProximityStateController;
        WindowManagerPolicy windowManagerPolicy = this.mWindowManagerPolicy;
        if (z4 || this.mReportedScreenStateToPolicy == -1) {
            int i5 = this.mPowerState.mScreenState;
            boolean z5 = i5 == 3 || i5 == 4;
            if (this.mIsSupportedAodMode) {
                if (z5 && i == 2 && !this.mSeamlessAodReady) {
                    Slog.d(str, "setScreenState(): mSeamlessAodReady : false AOD");
                    if (!this.mPendingScreenOnByAodReady) {
                        this.mAodManagerInternal.screenTurningOn(this.mSeamlessAodReadyListener);
                        this.mPendingScreenOnByAodReady = true;
                    }
                    return false;
                }
                this.mSeamlessAodReady = false;
                this.mPendingScreenOnByAodReady = false;
            }
            if (z2 && !displayPowerProximityStateController.mScreenOffBecauseOfProximity) {
                int i6 = this.mReportedScreenStateToPolicy;
                if (i6 == 2 || i6 == -1) {
                    setReportedScreenState(3);
                    if (this.mPendingScreenOffUnblocker == null) {
                        Trace.asyncTraceBegin(131072L, "Screen off blocked", 0);
                        this.mPendingScreenOffUnblocker = new AnonymousClass4();
                        this.mScreenOffBlockStartRealTime = SystemClock.elapsedRealtime();
                        Slog.i(str, "Blocking screen off");
                    }
                    Slog.d(str, "mWindowManagerPolicy.screenTurningOff()");
                    ((PhoneWindowManager) windowManagerPolicy)
                            .screenTurningOff(i4, this.mPendingScreenOffUnblocker);
                    unblockScreenOff();
                } else if (this.mPendingScreenOffUnblocker != null) {
                    return false;
                }
            }
            if (!z
                    && z4
                    && ((i4 == 0 || this.mBootCompleted || this.mIsCoverDisplay)
                            && this.mPendingScreenOffUnblocker == null
                            && this.mPendingScreenOnUnblockerByDisplayOffload == null)) {
                Trace.traceCounter(131072L, "ScreenState", i);
                String valueOf = String.valueOf(i);
                try {
                    SystemProperties.set("debug.tracing.screen_state", valueOf);
                } catch (RuntimeException e) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Failed to set a system property:"
                                        + " key=debug.tracing.screen_state value=",
                                    valueOf,
                                    " ");
                    m.append(e.getMessage());
                    Slog.e(str, m.toString());
                }
                DisplayPowerState displayPowerState = this.mPowerState;
                if (displayPowerState.mScreenState != i) {
                    if (DisplayPowerState.DEBUG) {
                        Slog.w(
                                "DisplayPowerState",
                                "setScreenState: state="
                                        + Display.stateToString(i)
                                        + "; reason="
                                        + Display.stateReasonToString(i2));
                    }
                    displayPowerState.mScreenState = i;
                    displayPowerState.mScreenReady = false;
                    displayPowerState.scheduleScreenUpdate();
                }
                noteScreenState(i, i2);
            }
        }
        if (z2
                && this.mReportedScreenStateToPolicy != 0
                && !displayPowerProximityStateController.mScreenOffBecauseOfProximity) {
            setReportedScreenState(0);
            unblockScreenOn();
            Slog.d(str, "mWindowManagerPolicy.screenTurnedOff()");
            ((PhoneWindowManager) windowManagerPolicy).screenTurnedOff(i4, this.mIsInTransition);
        } else if (!z2 && this.mReportedScreenStateToPolicy == 3) {
            unblockScreenOff();
            Slog.d(str, "mWindowManagerPolicy.screenTurnedOff()(transitional)");
            ((PhoneWindowManager) windowManagerPolicy).screenTurnedOff(i4, this.mIsInTransition);
            setReportedScreenState(0);
        }
        if (!z2 && ((i3 = this.mReportedScreenStateToPolicy) == 0 || i3 == -1)) {
            setReportedScreenState(1);
            if (this.mPowerState.mColorFadeLevel
                    != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                unblockScreenOn();
            } else if (this.mPendingScreenOnUnblocker == null) {
                Trace.asyncTraceBegin(131072L, "Screen on blocked", 0);
                this.mPendingScreenOnUnblocker = new ScreenOnUnblocker();
                this.mScreenOnBlockStartRealTime = SystemClock.elapsedRealtime();
                Slog.i(str, "Blocking screen on until initial contents have been drawn.");
                PowerManagerUtil.ScreenOnProfiler screenOnProfiler =
                        PowerManagerUtil.sCurrentScreenOnProfiler;
                screenOnProfiler.getClass();
                screenOnProfiler.mWmsStartTime = SystemClock.uptimeMillis();
            }
            Slog.d(str, "mWindowManagerPolicy.screenTurningOn() +");
            ((PhoneWindowManager) windowManagerPolicy)
                    .screenTurningOn(i4, this.mPendingScreenOnUnblocker);
            Slog.d(str, "mWindowManagerPolicy.screenTurningOn() -");
        }
        return this.mPendingScreenOnUnblocker == null
                && this.mPendingScreenOnUnblockerByDisplayOffload == null;
    }

    public final void setUpAutoBrightness(Context context, Handler handler) {
        float f;
        float f2;
        AutoBrightnessFallbackStrategy autoBrightnessFallbackStrategy;
        BrightnessMappingStrategy create;
        boolean z = this.mDisplayDeviceConfig.mAutoBrightnessAvailable;
        this.mUseSoftwareAutoBrightnessConfig = z;
        if (z) {
            SparseArray sparseArray = new SparseArray();
            DisplayDeviceConfig displayDeviceConfig = this.mDisplayDeviceConfig;
            this.mInjector.getClass();
            BrightnessMappingStrategy create2 =
                    BrightnessMappingStrategy.create(context, displayDeviceConfig, 0);
            this.defaultModeBrightnessMapper = create2;
            sparseArray.append(0, create2);
            if (context.getResources().getBoolean(R.bool.config_enableNightMode)
                    && (create =
                                    BrightnessMappingStrategy.create(
                                            context, this.mDisplayDeviceConfig, 1))
                            != null) {
                sparseArray.append(1, create);
            }
            BrightnessMappingStrategy create3 =
                    BrightnessMappingStrategy.create(context, this.mDisplayDeviceConfig, 2);
            DisplayManagerFlags displayManagerFlags = this.mFlags;
            if (displayManagerFlags.mAutoBrightnessModesFlagState.isEnabled() && create3 != null) {
                sparseArray.put(2, create3);
            }
            AutomaticBrightnessController automaticBrightnessController =
                    this.mAutomaticBrightnessController;
            if (automaticBrightnessController != null) {
                automaticBrightnessController.mCurrentBrightnessMapper.getClass();
                AutomaticBrightnessController automaticBrightnessController2 =
                        this.mAutomaticBrightnessController;
                automaticBrightnessController2.mCurrentBrightnessMapper.getClass();
                f2 =
                        automaticBrightnessController2.mCurrentBrightnessMapper.convertToNits(
                                FullScreenMagnificationGestureHandler.MAX_SCALE);
                f = 0.0f;
            } else {
                f = -1.0f;
                f2 = -1.0f;
            }
            BrightnessMappingStrategy brightnessMappingStrategy = this.defaultModeBrightnessMapper;
            if (brightnessMappingStrategy == null) {
                this.mUseSoftwareAutoBrightnessConfig = false;
                return;
            }
            if (brightnessMappingStrategy
                    instanceof BrightnessMappingStrategy.PhysicalMappingStrategy) {
                PowerManagerUtil.USE_SEC_LONG_TERM_MODEL = true;
            } else {
                PowerManagerUtil.USE_SEC_LONG_TERM_MODEL = false;
            }
            DisplayDeviceConfig displayDeviceConfig2 = this.mDisplayDeviceConfig;
            HysteresisLevels hysteresisLevels = displayDeviceConfig2.mAmbientBrightnessHysteresis;
            HysteresisLevels hysteresisLevels2 = displayDeviceConfig2.mScreenBrightnessHysteresis;
            HysteresisLevels hysteresisLevels3 =
                    displayDeviceConfig2.mAmbientBrightnessIdleHysteresis;
            HysteresisLevels hysteresisLevels4 =
                    displayDeviceConfig2.mScreenBrightnessIdleHysteresis;
            SecHysteresisLevels secHysteresisLevels =
                    displayDeviceConfig2.mAmbientBrightnessThresholdsTouchHigh;
            SecHysteresisLevels secHysteresisLevels2 =
                    displayDeviceConfig2.mAmbientBrightnessThresholdsTouchLow;
            long j = displayDeviceConfig2.mAutoBrightnessBrighteningLightDebounce;
            long j2 = displayDeviceConfig2.mAutoBrightnessDarkeningLightDebounce;
            long j3 = displayDeviceConfig2.mAutoBrightnessBrighteningLightDebounceIdle;
            long j4 = displayDeviceConfig2.mAutoBrightnessDarkeningLightDebounceIdle;
            boolean z2 =
                    context.getResources().getBoolean(R.bool.config_autoPowerModeUseMotionSensor);
            int integer = context.getResources().getInteger(R.integer.config_networkAvoidBadWifi);
            int integer2 =
                    context.getResources()
                            .getInteger(R.integer.config_cameraLaunchGestureSensorType);
            if (50 > integer2) {
                Slog.w(
                        this.mTag,
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                integer2,
                                "Expected config_autoBrightnessInitialLightSensorRate (50) to be"
                                    + " less than or equal to config_autoBrightnessLightSensorRate"
                                    + " (",
                                ")."));
            }
            int i = this.mDisplayId;
            if (i == 0 || this.mIsCoverDisplay) {
                this.mLightSensor = this.mSensorManager.getDefaultSensor(65601);
            }
            if (i == 0 && this.mLightSensor == null) {
                this.mLightSensor = this.mSensorManager.getDefaultSensor(65604);
            }
            BrightnessTracker brightnessTracker = this.mBrightnessTracker;
            if (brightnessTracker != null && i == 0) {
                brightnessTracker
                        .mBgHandler
                        .obtainMessage(5, 0, 0, this.mLightSensor)
                        .sendToTarget();
            }
            AutomaticBrightnessController automaticBrightnessController3 =
                    this.mAutomaticBrightnessController;
            if (automaticBrightnessController3 != null) {
                automaticBrightnessController3.setLightSensorEnabled(false);
            }
            Looper looper = handler.getLooper();
            SensorManager sensorManager = this.mSensorManager;
            Sensor sensor = this.mLightSensor;
            Context context2 = this.mContext;
            DisplayDeviceConfig displayDeviceConfig3 = this.mDisplayDeviceConfig;
            AutomaticBrightnessController automaticBrightnessController4 =
                    new AutomaticBrightnessController(
                            new AutomaticBrightnessController.Injector(),
                            this,
                            looper,
                            sensorManager,
                            sensor,
                            sparseArray,
                            integer,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            1.0f,
                            this.mDozeScaleFactor,
                            integer2,
                            50,
                            j,
                            j2,
                            j3,
                            j4,
                            z2,
                            hysteresisLevels,
                            hysteresisLevels2,
                            hysteresisLevels3,
                            hysteresisLevels4,
                            context2,
                            this.mBrightnessRangeController,
                            this.mBrightnessThrottler,
                            displayDeviceConfig3.mAmbientHorizonShort,
                            displayDeviceConfig3.mAmbientHorizonLong,
                            f,
                            f2,
                            this.mFlags,
                            secHysteresisLevels,
                            secHysteresisLevels2);
            this.mAutomaticBrightnessController = automaticBrightnessController4;
            SensorManager sensorManager2 = this.mSensorManager;
            DisplayDeviceConfig displayDeviceConfig4 = this.mDisplayDeviceConfig;
            BrightnessMappingStrategy brightnessMappingStrategy2 = this.defaultModeBrightnessMapper;
            boolean z3 = this.mIsEnabled;
            int i2 = this.mLeadDisplayId;
            DisplayBrightnessController displayBrightnessController =
                    this.mDisplayBrightnessController;
            displayBrightnessController.setAutomaticBrightnessController(
                    automaticBrightnessController4);
            synchronized (displayBrightnessController.mLock) {
                autoBrightnessFallbackStrategy =
                        displayBrightnessController
                                .mDisplayBrightnessStrategySelector
                                .mAutoBrightnessFallbackStrategy;
            }
            if (autoBrightnessFallbackStrategy != null) {
                autoBrightnessFallbackStrategy.setupAutoBrightnessFallbackSensor(
                        sensorManager2,
                        displayDeviceConfig4,
                        this.mHandler,
                        brightnessMappingStrategy2,
                        z3,
                        i2);
            }
            this.mBrightnessEventRingBuffer = new RingBuffer(BrightnessEvent.class, 100);
            if (displayManagerFlags.mRefactorDisplayPowerController.isEnabled()) {
                return;
            }
            ScreenOffBrightnessSensorController screenOffBrightnessSensorController =
                    this.mScreenOffBrightnessSensorController;
            if (screenOffBrightnessSensorController != null) {
                screenOffBrightnessSensorController.setLightSensorEnabled(false);
                this.mScreenOffBrightnessSensorController = null;
            }
            SensorManager sensorManager3 = this.mSensorManager;
            SensorData sensorData = this.mDisplayDeviceConfig.mScreenOffBrightnessSensor;
            Sensor findSensor =
                    sensorData != null
                            ? SensorUtils.findSensor(
                                    sensorManager3, sensorData.type, sensorData.name, 0)
                            : null;
            int[] iArr = this.mDisplayDeviceConfig.mScreenOffBrightnessSensorValueToLux;
            if (findSensor == null || iArr == null) {
                return;
            }
            this.mScreenOffBrightnessSensorController =
                    new ScreenOffBrightnessSensorController(
                            this.mSensorManager,
                            findSensor,
                            this.mHandler,
                            new DisplayPowerController$$ExternalSyntheticLambda11(0),
                            iArr,
                            this.defaultModeBrightnessMapper);
        }
    }

    public final boolean shouldEnableHdrRampRateCase() {
        BrightnessReason brightnessReason = this.mBrightnessReason;
        BrightnessReason brightnessReason2 = this.mBrightnessReasonTemp;
        return (brightnessReason2.mModifier == brightnessReason.mModifier
                        || brightnessReason2.hasModifier(4) == brightnessReason.hasModifier(4))
                ? false
                : true;
    }

    public final boolean shouldEnableMoreFastRampRateCase() {
        BrightnessReason brightnessReason = this.mBrightnessReasonTemp;
        if (!brightnessReason.hasModifier(1) && this.mBrightnessReason.hasModifier(1)) {
            return true;
        }
        int i = brightnessReason.mReason;
        if (i == 4 && this.mBrightnessReason.mReason != 4) {
            return true;
        }
        int i2 = this.mBrightnessReason.mReason;
        return i != i2 && (i == 6 || i2 == 6);
    }

    public final void unblockScreenOff() {
        if (this.mPendingScreenOffUnblocker != null) {
            this.mPendingScreenOffUnblocker = null;
            Slog.i(
                    this.mTag,
                    "Unblocked screen off after "
                            + (SystemClock.elapsedRealtime() - this.mScreenOffBlockStartRealTime)
                            + " ms");
            Trace.asyncTraceEnd(131072L, "Screen off blocked", 0);
        }
    }

    public final void unblockScreenOn() {
        if (this.mPendingScreenOnUnblocker != null) {
            this.mPendingScreenOnUnblocker = null;
            Slog.i(
                    this.mTag,
                    "Unblocked screen on after "
                            + (SystemClock.elapsedRealtime() - this.mScreenOnBlockStartRealTime)
                            + " ms");
            Trace.asyncTraceEnd(131072L, "Screen on blocked", 0);
            PowerManagerUtil.ScreenOnProfiler screenOnProfiler =
                    PowerManagerUtil.sCurrentScreenOnProfiler;
            screenOnProfiler.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            screenOnProfiler.mWmsDone = true;
            if (screenOnProfiler.mWmsStartTime == 0) {
                screenOnProfiler.mWmsStartTime = screenOnProfiler.mWakeUpStartTime;
            }
            screenOnProfiler.mWmsDuration = (int) (uptimeMillis - screenOnProfiler.mWmsStartTime);
            if (screenOnProfiler.mFrameDone) {
                screenOnProfiler.noteWakeupEnd(false);
            }
        }
    }

    public final void unblockScreenOnByDisplayOffload() {
        if (this.mPendingScreenOnUnblockerByDisplayOffload == null) {
            return;
        }
        this.mPendingScreenOnUnblockerByDisplayOffload = null;
        Slog.i(
                this.mTag,
                "Unblocked screen on for offloading after "
                        + (SystemClock.elapsedRealtime()
                                - this.mScreenOnBlockByDisplayOffloadStartRealTime)
                        + " ms");
        Trace.asyncTraceEnd(131072L, "Screen on blocked by displayoffload", 0);
    }

    public final void updatePowerState() {
        Trace.traceBegin(131072L, "DisplayPowerController#updatePowerState");
        updatePowerStateInternal();
        Trace.traceEnd(131072L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:193:0x0425, code lost:

       if (r4.mDisplayBrightnessStrategyName.equals("FallbackBrightnessStrategy") != false) goto L248;
    */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x0597, code lost:

       if (r42.mWaitingAutoBrightnessFromDoze == false) goto L341;
    */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0599, code lost:

       r2 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:637:0x05bb, code lost:

       if (r42.mWaitingAutoBrightnessFromDoze == false) goto L341;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePowerStateInternal() {
        /*
            Method dump skipped, instructions count: 3103
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayPowerController.updatePowerStateInternal():void");
    }
}
