package com.android.server.display.mode;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Temperature;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.sysprop.SurfaceFlingerProperties;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBar;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda1;
import com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholdPoint;
import com.android.server.display.config.RefreshRateData;
import com.android.server.display.config.SensorData;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.mode.RefreshRateModeManager.AnonymousClass1;
import com.android.server.display.mode.RefreshRateModeManager.ModeSettingsObserver;
import com.android.server.display.utils.AmbientFilter$WeightedMovingAverageAmbientFilter;
import com.android.server.display.utils.AmbientFilterFactory;
import com.android.server.display.utils.DeviceConfigParsingUtils;
import com.android.server.display.utils.RollingBuffer;
import com.android.server.display.utils.SensorUtils;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

import com.samsung.android.core.SystemHistory;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public final class DisplayModeDirector {
    public boolean mAlwaysRespectAppRequest;
    public final AppRequestObserver mAppRequestObserver;
    public SparseArray mAppSupportedModesByDisplay;
    public BrightnessObserver mBrightnessObserver;
    public final DeviceConfigParameterProvider mConfigParameterProvider;
    public final Context mContext;
    public DisplayDeviceConfig mDefaultDisplayDeviceConfig;
    public SparseArray mDefaultModeByDisplay;
    public DisplayManagerService.DesiredDisplayModeSpecsObserver mDesiredDisplayModeSpecsListener;
    public final DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
    public SparseArray mDisplayDeviceConfigByDisplay;
    public final DisplayManagerService$$ExternalSyntheticLambda1 mDisplayDeviceConfigProvider;
    public final DisplayManagerFlags mDisplayManagerFlags;
    public final DisplayObserver mDisplayObserver;
    public final DisplayModeDirectorHandler mHandler;
    public final HbmObserver mHbmObserver;
    public final Injector mInjector;
    public final boolean mIsDisplayResolutionRangeVotingEnabled;
    public final boolean mIsDisplaysRefreshRatesSynchronizationEnabled;
    public final boolean mIsExternalDisplayLimitModeEnabled;
    public final boolean mIsUserPreferredModeVoteEnabled;
    public final Object mLock;
    public boolean mLoggingEnabled;
    public int mModeSwitchingType;
    public final RefreshRateModeManager mRefreshRateModeManager;
    public final ProximitySensorObserver mSensorObserver;
    public final SettingsObserver mSettingsObserver;
    public final SkinThermalStatusObserver mSkinThermalStatusObserver;
    public SparseArray mSupportedModesByDisplay;
    public final boolean mSupportsFrameRateOverride;
    public final SystemRequestObserver mSystemRequestObserver;
    public final UdfpsObserver mUdfpsObserver;
    public final VotesStatsReporter mVotesStatsReporter;
    public final VotesStorage mVotesStorage;

    public final class AppRequestObserver {
        public final boolean mIgnorePreferredRefreshRate;

        public AppRequestObserver(DisplayManagerFlags displayManagerFlags) {
            this.mIgnorePreferredRefreshRate =
                    displayManagerFlags.mIgnoreAppPreferredRefreshRate.isEnabled()
                            || CoreRune.FW_VRR_DISCRETE;
        }

        public final Display.Mode findModeLocked(int i, int i2, float f) {
            int i3 = 0;
            DisplayModeDirector displayModeDirector = DisplayModeDirector.this;
            Display.Mode mode = null;
            if (i2 != 0) {
                Display.Mode[] modeArr =
                        (Display.Mode[]) displayModeDirector.mAppSupportedModesByDisplay.get(i);
                if (modeArr == null) {
                    return null;
                }
                int length = modeArr.length;
                while (i3 < length) {
                    Display.Mode mode2 = modeArr[i3];
                    if (mode2.getModeId() == i2) {
                        return mode2;
                    }
                    i3++;
                }
                return null;
            }
            if (f == FullScreenMagnificationGestureHandler.MAX_SCALE
                    || this.mIgnorePreferredRefreshRate) {
                return null;
            }
            Display.Mode[] modeArr2 =
                    (Display.Mode[]) displayModeDirector.mAppSupportedModesByDisplay.get(i);
            Display.Mode mode3 = (Display.Mode) displayModeDirector.mDefaultModeByDisplay.get(i);
            while (true) {
                if (i3 >= modeArr2.length) {
                    break;
                }
                if (modeArr2[i3].matches(mode3.getPhysicalWidth(), mode3.getPhysicalHeight(), f)) {
                    mode = modeArr2[i3];
                    break;
                }
                i3++;
            }
            if (mode != null) {
                return mode;
            }
            Slog.e(
                    "DisplayModeDirector",
                    "Couldn't find a mode for the requestedRefreshRate: "
                            + f
                            + " on Display: "
                            + i);
            return mode;
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x002d, code lost:

           if (r1.max == com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L11;
        */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0032  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x003f  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x003c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setAppRequest(int r4, int r5, float r6, float r7, float r8) {
            /*
                r3 = this;
                com.android.server.display.mode.DisplayModeDirector r0 = com.android.server.display.mode.DisplayModeDirector.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                android.view.Display$Mode r5 = r3.findModeLocked(r4, r5, r6)     // Catch: java.lang.Throwable -> L92
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L92
                r0 = 0
                int r1 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                r2 = 0
                if (r1 > 0) goto L17
                int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
                if (r1 <= 0) goto L15
                goto L17
            L15:
                r1 = r2
                goto L30
            L17:
                int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
                if (r1 <= 0) goto L1c
                goto L1e
            L1c:
                r8 = 2139095040(0x7f800000, float:Infinity)
            L1e:
                android.view.SurfaceControl$RefreshRateRange r1 = new android.view.SurfaceControl$RefreshRateRange
                r1.<init>(r7, r8)
                float r7 = r1.min
                int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r7 != 0) goto L30
                float r7 = r1.max
                int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r7 != 0) goto L30
                goto L15
            L30:
                if (r1 == 0) goto L3c
                float r7 = r1.min
                float r8 = r1.max
                com.android.server.display.mode.RefreshRateVote$RenderVote r1 = new com.android.server.display.mode.RefreshRateVote$RenderVote
                r1.<init>(r7, r8)
                goto L3d
            L3c:
                r1 = r2
            L3d:
                if (r5 == 0) goto L59
                boolean r6 = r5.isSynthetic()
                if (r6 == 0) goto L4f
                float r6 = r5.getRefreshRate()
                com.android.server.display.mode.RequestedRefreshRateVote r7 = new com.android.server.display.mode.RequestedRefreshRateVote
                r7.<init>(r6)
                goto L68
            L4f:
                float r6 = r5.getRefreshRate()
                com.android.server.display.mode.BaseModeRefreshRateVote r7 = new com.android.server.display.mode.BaseModeRefreshRateVote
                r7.<init>(r6)
                goto L68
            L59:
                int r7 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                if (r7 == 0) goto L67
                boolean r7 = r3.mIgnorePreferredRefreshRate
                if (r7 == 0) goto L67
                com.android.server.display.mode.RequestedRefreshRateVote r7 = new com.android.server.display.mode.RequestedRefreshRateVote
                r7.<init>(r6)
                goto L68
            L67:
                r7 = r2
            L68:
                if (r5 == 0) goto L77
                int r6 = r5.getPhysicalWidth()
                int r5 = r5.getPhysicalHeight()
                com.android.server.display.mode.SizeVote r2 = new com.android.server.display.mode.SizeVote
                r2.<init>(r6, r5, r6, r5)
            L77:
                com.android.server.display.mode.DisplayModeDirector r5 = com.android.server.display.mode.DisplayModeDirector.this
                com.android.server.display.mode.VotesStorage r5 = r5.mVotesStorage
                r6 = 7
                r5.updateVote(r4, r6, r1)
                com.android.server.display.mode.DisplayModeDirector r5 = com.android.server.display.mode.DisplayModeDirector.this
                com.android.server.display.mode.VotesStorage r5 = r5.mVotesStorage
                r6 = 8
                r5.updateVote(r4, r6, r7)
                com.android.server.display.mode.DisplayModeDirector r3 = com.android.server.display.mode.DisplayModeDirector.this
                com.android.server.display.mode.VotesStorage r3 = r3.mVotesStorage
                r5 = 9
                r3.updateVote(r4, r5, r2)
                return
            L92:
                r3 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L92
                throw r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.AppRequestObserver.setAppRequest(int,"
                        + " int, float, float, float):void");
        }
    }

    public class BrightnessObserver implements DisplayManager.DisplayListener {
        public AmbientFilter$WeightedMovingAverageAmbientFilter mAmbientFilter;
        public final Context mContext;
        public final Handler mHandler;
        public float[] mHighAmbientBrightnessThresholds;
        public float[] mHighDisplayBrightnessThresholds;
        public SparseArray mHighZoneRefreshRateForThermals;
        public SurfaceControl.IdleScreenRefreshRateConfig mIdleScreenRefreshRateConfig;
        public final Injector mInjector;
        public Sensor mLightSensor;
        public String mLightSensorName;
        public String mLightSensorType;
        public boolean mLoggingEnabled;
        public float[] mLowAmbientBrightnessThresholds;
        public float[] mLowDisplayBrightnessThresholds;
        public SparseArray mLowZoneRefreshRateForThermals;
        public int mRefreshRateInHighZone;
        public int mRefreshRateInLowZone;
        public Sensor mRegisteredLightSensor;
        public SensorManager mSensorManager;
        public boolean mShouldObserveAmbientHighChange;
        public boolean mShouldObserveAmbientLowChange;
        public boolean mShouldObserveDisplayHighChange;
        public boolean mShouldObserveDisplayLowChange;
        public boolean mThermalRegistered;
        public final boolean mVsyncLowLightBlockingVoteEnabled;
        public final LightSensorEventListener mLightSensorListener = new LightSensorEventListener();
        public float mAmbientLux = -1.0f;
        public float mBrightness = Float.NaN;
        public final AnonymousClass1 mThermalListener =
                new IThermalEventListener
                        .Stub() { // from class:
                                  // com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.1
                    public final void notifyThrottling(Temperature temperature) {
                        int status = temperature.getStatus();
                        synchronized (DisplayModeDirector.this.mLock) {
                            BrightnessObserver brightnessObserver = BrightnessObserver.this;
                            if (brightnessObserver.mThermalStatus != status) {
                                brightnessObserver.mThermalStatus = status;
                            }
                            brightnessObserver.onBrightnessChangedLocked();
                        }
                    }
                };
        public int mDefaultDisplayState = 0;
        public boolean mRefreshRateChangeable = false;
        public boolean mLowPowerModeEnabled = false;
        public int mThermalStatus = 0;

        public final class LightSensorEventListener implements SensorEventListener {
            public final AnonymousClass1 mInjectSensorEventRunnable =
                    new Runnable() { // from class:
                                     // com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            LightSensorEventListener.this.processSensorData(
                                    SystemClock.uptimeMillis());
                            LightSensorEventListener lightSensorEventListener =
                                    LightSensorEventListener.this;
                            float f = lightSensorEventListener.mLastSensorData;
                            BrightnessObserver brightnessObserver = BrightnessObserver.this;
                            if (!LightSensorEventListener.isDifferentZone(
                                    f,
                                    brightnessObserver.mAmbientLux,
                                    brightnessObserver.mLowAmbientBrightnessThresholds)) {
                                LightSensorEventListener lightSensorEventListener2 =
                                        LightSensorEventListener.this;
                                float f2 = lightSensorEventListener2.mLastSensorData;
                                BrightnessObserver brightnessObserver2 = BrightnessObserver.this;
                                if (!LightSensorEventListener.isDifferentZone(
                                        f2,
                                        brightnessObserver2.mAmbientLux,
                                        brightnessObserver2.mHighAmbientBrightnessThresholds)) {
                                    return;
                                }
                            }
                            LightSensorEventListener lightSensorEventListener3 =
                                    LightSensorEventListener.this;
                            BrightnessObserver.this.mHandler.postDelayed(
                                    lightSensorEventListener3.mInjectSensorEventRunnable, 250L);
                        }
                    };
            public float mLastSensorData;
            public boolean mLoggingEnabled;
            public long mTimestamp;

            /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$LightSensorEventListener$1] */
            public LightSensorEventListener() {}

            public static boolean isDifferentZone(float f, float f2, float[] fArr) {
                for (float f3 : fArr) {
                    if (f <= f3 && f2 > f3) {
                        return true;
                    }
                    if (f > f3 && f2 <= f3) {
                        return true;
                    }
                }
                return false;
            }

            @Override // android.hardware.SensorEventListener
            public final void onAccuracyChanged(Sensor sensor, int i) {}

            @Override // android.hardware.SensorEventListener
            public final void onSensorChanged(SensorEvent sensorEvent) {
                AmbientFilter$WeightedMovingAverageAmbientFilter
                        ambientFilter$WeightedMovingAverageAmbientFilter;
                this.mLastSensorData = sensorEvent.values[0];
                if (this.mLoggingEnabled) {
                    Slog.d("DisplayModeDirector", "On sensor changed: " + this.mLastSensorData);
                }
                if (CoreRune.FW_VRR_SEAMLESS) {
                    BrightnessObserver brightnessObserver = BrightnessObserver.this;
                    float f = this.mLastSensorData;
                    brightnessObserver.mAmbientLux = f;
                    DisplayModeDirector.this.mRefreshRateModeManager.onLightSensorChanged(f);
                    return;
                }
                float f2 = this.mLastSensorData;
                BrightnessObserver brightnessObserver2 = BrightnessObserver.this;
                boolean isDifferentZone =
                        isDifferentZone(
                                f2,
                                brightnessObserver2.mAmbientLux,
                                brightnessObserver2.mLowAmbientBrightnessThresholds);
                float f3 = this.mLastSensorData;
                BrightnessObserver brightnessObserver3 = BrightnessObserver.this;
                boolean isDifferentZone2 =
                        isDifferentZone(
                                f3,
                                brightnessObserver3.mAmbientLux,
                                brightnessObserver3.mHighAmbientBrightnessThresholds);
                if (((isDifferentZone && this.mLastSensorData < BrightnessObserver.this.mAmbientLux)
                                || (isDifferentZone2
                                        && this.mLastSensorData
                                                > BrightnessObserver.this.mAmbientLux))
                        && (ambientFilter$WeightedMovingAverageAmbientFilter =
                                        BrightnessObserver.this.mAmbientFilter)
                                != null) {
                    RollingBuffer rollingBuffer =
                            ambientFilter$WeightedMovingAverageAmbientFilter.mBuffer;
                    rollingBuffer.mCount = 0;
                    rollingBuffer.mStart = 0;
                    rollingBuffer.mEnd = 0;
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                this.mTimestamp = System.currentTimeMillis();
                AmbientFilter$WeightedMovingAverageAmbientFilter
                        ambientFilter$WeightedMovingAverageAmbientFilter2 =
                                BrightnessObserver.this.mAmbientFilter;
                if (ambientFilter$WeightedMovingAverageAmbientFilter2 != null) {
                    ambientFilter$WeightedMovingAverageAmbientFilter2.addValue(
                            uptimeMillis, this.mLastSensorData);
                }
                BrightnessObserver.this.mHandler.removeCallbacks(this.mInjectSensorEventRunnable);
                processSensorData(uptimeMillis);
                if ((isDifferentZone && this.mLastSensorData > BrightnessObserver.this.mAmbientLux)
                        || (isDifferentZone2
                                && this.mLastSensorData < BrightnessObserver.this.mAmbientLux)) {
                    BrightnessObserver.this.mHandler.postDelayed(
                            this.mInjectSensorEventRunnable, 250L);
                }
                if (DisplayModeDirector.this.mDisplayManagerFlags.mIdleScreenRefreshRateTimeout
                        .isEnabled()) {
                    BrightnessObserver brightnessObserver4 = BrightnessObserver.this;
                    float f4 = brightnessObserver4.mAmbientLux;
                    synchronized (DisplayModeDirector.this.mLock) {
                        DisplayDeviceConfig displayDeviceConfig =
                                DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
                        if (displayDeviceConfig != null
                                && !displayDeviceConfig.mIdleScreenRefreshRateTimeoutLuxThresholds
                                        .isEmpty()) {
                            int i = -1;
                            for (IdleScreenRefreshRateTimeoutLuxThresholdPoint
                                    idleScreenRefreshRateTimeoutLuxThresholdPoint :
                                            DisplayModeDirector.this
                                                    .mDefaultDisplayDeviceConfig
                                                    .mIdleScreenRefreshRateTimeoutLuxThresholds) {
                                if (idleScreenRefreshRateTimeoutLuxThresholdPoint.lux.intValue()
                                        <= f4) {
                                    i =
                                            idleScreenRefreshRateTimeoutLuxThresholdPoint.timeout
                                                    .intValue();
                                }
                            }
                            SurfaceControl.IdleScreenRefreshRateConfig idleScreenRefreshRateConfig =
                                    brightnessObserver4.mIdleScreenRefreshRateConfig;
                            if (idleScreenRefreshRateConfig == null
                                    || i != idleScreenRefreshRateConfig.timeoutMillis) {
                                brightnessObserver4.mIdleScreenRefreshRateConfig =
                                        new SurfaceControl.IdleScreenRefreshRateConfig(i);
                                synchronized (DisplayModeDirector.this.mLock) {
                                    DisplayModeDirector.this
                                            .notifyDesiredDisplayModeSpecsChangedLocked();
                                }
                            }
                        }
                        brightnessObserver4.mIdleScreenRefreshRateConfig = null;
                    }
                }
            }

            public final void processSensorData(long j) {
                BrightnessObserver brightnessObserver = BrightnessObserver.this;
                AmbientFilter$WeightedMovingAverageAmbientFilter
                        ambientFilter$WeightedMovingAverageAmbientFilter =
                                brightnessObserver.mAmbientFilter;
                if (ambientFilter$WeightedMovingAverageAmbientFilter != null) {
                    brightnessObserver.mAmbientLux =
                            ambientFilter$WeightedMovingAverageAmbientFilter.getEstimate(j);
                } else {
                    brightnessObserver.mAmbientLux = this.mLastSensorData;
                }
                synchronized (DisplayModeDirector.this.mLock) {
                    BrightnessObserver.this.onBrightnessChangedLocked();
                }
            }
        }

        /* renamed from: -$$Nest$monDeviceConfigLowBrightnessThresholdsChanged, reason: not valid java name */
        public static void m484$$Nest$monDeviceConfigLowBrightnessThresholdsChanged(
                BrightnessObserver brightnessObserver, float[] fArr, float[] fArr2) {
            DisplayDeviceConfig displayDeviceConfig;
            brightnessObserver.getClass();
            if (fArr == null || fArr2 == null || fArr.length != fArr2.length) {
                synchronized (DisplayModeDirector.this.mLock) {
                    displayDeviceConfig = DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
                }
                brightnessObserver.mLowDisplayBrightnessThresholds =
                        brightnessObserver.loadBrightnessThresholds(
                                new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                        brightnessObserver, 3),
                                new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                        displayDeviceConfig, 3),
                                R.array.config_reduceBrightColorsCoefficients,
                                displayDeviceConfig,
                                false,
                                new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                        0));
                brightnessObserver.mLowAmbientBrightnessThresholds =
                        brightnessObserver.loadBrightnessThresholds(
                                new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                        brightnessObserver, 4),
                                new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                        displayDeviceConfig, 4),
                                R.array.config_mainBuiltInDisplayWaterfallCutout,
                                displayDeviceConfig,
                                false,
                                new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                        1));
            } else {
                brightnessObserver.mLowDisplayBrightnessThresholds = fArr;
                brightnessObserver.mLowAmbientBrightnessThresholds = fArr2;
            }
            brightnessObserver.restartObserver();
        }

        /* renamed from: -$$Nest$mreloadLightSensor, reason: not valid java name */
        public static void m485$$Nest$mreloadLightSensor(
                BrightnessObserver brightnessObserver, DisplayDeviceConfig displayDeviceConfig) {
            if (displayDeviceConfig != null) {
                brightnessObserver.getClass();
                SensorData sensorData = displayDeviceConfig.mAmbientLightSensor;
                if (sensorData != null) {
                    brightnessObserver.mLightSensorType = sensorData.type;
                    brightnessObserver.mLightSensorName = sensorData.name;
                    brightnessObserver.restartObserver();
                }
            }
            if (brightnessObserver.mLightSensorName == null
                    && brightnessObserver.mLightSensorType == null) {
                brightnessObserver.mLightSensorType =
                        brightnessObserver
                                .mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .display_rotation_camera_compat_toast_in_multi_window);
                brightnessObserver.mLightSensorName = "";
            }
            brightnessObserver.restartObserver();
        }

        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$1] */
        public BrightnessObserver(
                Context context,
                DisplayManagerService.DisplayManagerHandler displayManagerHandler,
                Injector injector,
                DisplayManagerFlags displayManagerFlags) {
            this.mContext = context;
            this.mHandler = displayManagerHandler;
            this.mInjector = injector;
            updateBlockingZoneThresholds(null, false);
            this.mRefreshRateInHighZone =
                    context.getResources()
                            .getInteger(R.integer.config_lowPowerStandbyNonInteractiveTimeout);
            this.mVsyncLowLightBlockingVoteEnabled =
                    displayManagerFlags.mVsyncLowLightVote.isEnabled();
        }

        public static boolean hasValidThreshold(float[] fArr) {
            for (float f : fArr) {
                if (f >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    return true;
                }
            }
            return false;
        }

        public final void dumpLocked(PrintWriter printWriter) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    KillPolicyManager$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    KillPolicyManager$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                            .m$1(
                                                                                                                    printWriter,
                                                                                                                    "  BrightnessObserver",
                                                                                                                    "    mAmbientLux:"
                                                                                                                        + " "),
                                                                                                    this
                                                                                                            .mAmbientLux,
                                                                                                    printWriter,
                                                                                                    "    mBrightness:"
                                                                                                        + " "),
                                                                                    this
                                                                                            .mBrightness,
                                                                                    printWriter,
                                                                                    "    mDefaultDisplayState:"
                                                                                        + " "),
                                                                    this.mDefaultDisplayState,
                                                                    printWriter,
                                                                    "    mLowPowerModeEnabled: "),
                                                    this.mLowPowerModeEnabled,
                                                    printWriter,
                                                    "    mRefreshRateChangeable: "),
                                            this.mRefreshRateChangeable,
                                            printWriter,
                                            "    mShouldObserveDisplayLowChange: "),
                                    this.mShouldObserveDisplayLowChange,
                                    printWriter,
                                    "    mShouldObserveAmbientLowChange: "),
                            this.mShouldObserveAmbientLowChange,
                            printWriter,
                            "    mRefreshRateInLowZone: "),
                    this.mRefreshRateInLowZone,
                    printWriter);
            for (float f : this.mLowDisplayBrightnessThresholds) {
                printWriter.println("    mDisplayLowBrightnessThreshold: " + f);
            }
            for (float f2 : this.mLowAmbientBrightnessThresholds) {
                printWriter.println("    mAmbientLowBrightnessThreshold: " + f2);
            }
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("    mShouldObserveDisplayHighChange: "),
                                    this.mShouldObserveDisplayHighChange,
                                    printWriter,
                                    "    mShouldObserveAmbientHighChange: "),
                            this.mShouldObserveAmbientHighChange,
                            printWriter,
                            "    mRefreshRateInHighZone: "),
                    this.mRefreshRateInHighZone,
                    printWriter);
            for (float f3 : this.mHighDisplayBrightnessThresholds) {
                printWriter.println("    mDisplayHighBrightnessThresholds: " + f3);
            }
            for (float f4 : this.mHighAmbientBrightnessThresholds) {
                printWriter.println("    mAmbientHighBrightnessThresholds: " + f4);
            }
            printWriter.println("    mRegisteredLightSensor: " + this.mRegisteredLightSensor);
            printWriter.println("    mLightSensor: " + this.mLightSensor);
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            this.mLightSensorName,
                            "    mLightSensorType: ",
                            new StringBuilder("    mLightSensorName: ")),
                    this.mLightSensorType,
                    printWriter);
            LightSensorEventListener lightSensorEventListener = this.mLightSensorListener;
            StringBuilder m =
                    KillPolicyManager$$ExternalSyntheticOutline0.m(
                            new StringBuilder("    mLastSensorData: "),
                            lightSensorEventListener.mLastSensorData,
                            printWriter,
                            "    mTimestamp: ");
            m.append(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
                            .format(new Date(lightSensorEventListener.mTimestamp)));
            printWriter.println(m.toString());
            if (this.mAmbientFilter != null) {
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "    ");
                AmbientFilter$WeightedMovingAverageAmbientFilter
                        ambientFilter$WeightedMovingAverageAmbientFilter = this.mAmbientFilter;
                indentingPrintWriter.println(
                        "  " + ambientFilter$WeightedMovingAverageAmbientFilter.mTag);
                indentingPrintWriter.println("    mLoggingEnabled=false");
                indentingPrintWriter.println(
                        "    mHorizon="
                                + ambientFilter$WeightedMovingAverageAmbientFilter.mHorizon);
                indentingPrintWriter.println(
                        "    mBuffer=" + ambientFilter$WeightedMovingAverageAmbientFilter.mBuffer);
                indentingPrintWriter.println(
                        "    mIntercept="
                                + ambientFilter$WeightedMovingAverageAmbientFilter.mIntercept);
            }
        }

        public float[] getHighAmbientBrightnessThresholds() {
            return this.mHighAmbientBrightnessThresholds;
        }

        public float[] getHighDisplayBrightnessThresholds() {
            return this.mHighDisplayBrightnessThresholds;
        }

        public SurfaceControl.IdleScreenRefreshRateConfig getIdleScreenRefreshRateConfig() {
            return this.mIdleScreenRefreshRateConfig;
        }

        public float[] getLowAmbientBrightnessThresholds() {
            return this.mLowAmbientBrightnessThresholds;
        }

        public float[] getLowDisplayBrightnessThresholds() {
            return this.mLowDisplayBrightnessThresholds;
        }

        public int getRefreshRateInHighZone() {
            return this.mRefreshRateInHighZone;
        }

        public int getRefreshRateInLowZone() {
            return this.mRefreshRateInLowZone;
        }

        public final boolean hasLowLightVrrConfig() {
            DisplayDeviceConfig displayDeviceConfig;
            synchronized (DisplayModeDirector.this.mLock) {
                displayDeviceConfig = DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
            }
            return this.mVsyncLowLightBlockingVoteEnabled
                    && displayDeviceConfig != null
                    && (displayDeviceConfig.mVrrSupportEnabled || CoreRune.FW_VRR_DISCRETE)
                    && !displayDeviceConfig.mRefreshRateData.lowLightBlockingZoneSupportedModes
                            .isEmpty();
        }

        /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:4:0x000c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final float[] loadBrightnessThresholds(
                java.util.concurrent.Callable r1,
                java.util.concurrent.Callable r2,
                int r3,
                com.android.server.display.DisplayDeviceConfig r4,
                boolean r5,
                java.util.function.Function r6) {
            /*
                r0 = this;
                if (r5 == 0) goto L9
                java.lang.Object r1 = r1.call()     // Catch: java.lang.Exception -> L9
                float[] r1 = (float[]) r1     // Catch: java.lang.Exception -> L9
                goto La
            L9:
                r1 = 0
            La:
                if (r1 != 0) goto L33
                if (r4 != 0) goto L22
                android.content.Context r0 = r0.mContext     // Catch: java.lang.Exception -> L20
                android.content.res.Resources r0 = r0.getResources()     // Catch: java.lang.Exception -> L20
                int[] r0 = r0.getIntArray(r3)     // Catch: java.lang.Exception -> L20
                java.lang.Object r0 = r6.apply(r0)     // Catch: java.lang.Exception -> L20
                float[] r0 = (float[]) r0     // Catch: java.lang.Exception -> L20
            L1e:
                r1 = r0
                goto L33
            L20:
                r0 = move-exception
                goto L29
            L22:
                java.lang.Object r0 = r2.call()     // Catch: java.lang.Exception -> L20
                float[] r0 = (float[]) r0     // Catch: java.lang.Exception -> L20
                goto L1e
            L29:
                java.lang.String r2 = "DisplayModeDirector"
                java.lang.String r3 = "Unexpectedly failed to load display brightness threshold"
                android.util.Slog.e(r2, r3)
                r0.printStackTrace()
            L33:
                return r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.loadBrightnessThresholds(java.util.concurrent.Callable,"
                        + " java.util.concurrent.Callable, int,"
                        + " com.android.server.display.DisplayDeviceConfig, boolean,"
                        + " java.util.function.Function):float[]");
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002d  */
        /* JADX WARN: Removed duplicated region for block: B:5:0x0017  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void loadRefreshRateInHighZone(
                com.android.server.display.DisplayDeviceConfig r4, boolean r5) {
            /*
                r3 = this;
                r0 = -1
                if (r5 == 0) goto L14
                com.android.server.display.mode.DisplayModeDirector r5 = com.android.server.display.mode.DisplayModeDirector.this     // Catch: java.lang.Exception -> L14
                com.android.server.display.feature.DeviceConfigParameterProvider r5 = r5.mConfigParameterProvider     // Catch: java.lang.Exception -> L14
                android.provider.DeviceConfigInterface r5 = r5.mDeviceConfig     // Catch: java.lang.Exception -> L14
                java.lang.String r1 = "refresh_rate_in_high_zone"
                java.lang.String r2 = "display_manager"
                int r5 = r5.getInt(r2, r1, r0)     // Catch: java.lang.Exception -> L14
                goto L15
            L14:
                r5 = r0
            L15:
                if (r5 != r0) goto L29
                if (r4 != 0) goto L27
                android.content.Context r5 = r3.mContext
                android.content.res.Resources r5 = r5.getResources()
                r0 = 17694899(0x10e00b3, float:2.6081783E-38)
                int r5 = r5.getInteger(r0)
                goto L29
            L27:
                int r5 = r4.mDefaultHighBlockingZoneRefreshRate
            L29:
                if (r4 != 0) goto L2d
                r4 = 0
                goto L3e
            L2d:
                java.lang.String r0 = r4.mHighBlockingZoneThermalMapId
                if (r0 != 0) goto L34
                java.lang.String r0 = "default"
            L34:
                java.util.Map r4 = r4.mRefreshRateThrottlingMap
                java.util.HashMap r4 = (java.util.HashMap) r4
                java.lang.Object r4 = r4.get(r0)
                android.util.SparseArray r4 = (android.util.SparseArray) r4
            L3e:
                r3.mHighZoneRefreshRateForThermals = r4
                r3.mRefreshRateInHighZone = r5
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.loadRefreshRateInHighZone(com.android.server.display.DisplayDeviceConfig,"
                        + " boolean):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002d  */
        /* JADX WARN: Removed duplicated region for block: B:5:0x0017  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void loadRefreshRateInLowZone(
                com.android.server.display.DisplayDeviceConfig r4, boolean r5) {
            /*
                r3 = this;
                r0 = -1
                if (r5 == 0) goto L14
                com.android.server.display.mode.DisplayModeDirector r5 = com.android.server.display.mode.DisplayModeDirector.this     // Catch: java.lang.Exception -> L14
                com.android.server.display.feature.DeviceConfigParameterProvider r5 = r5.mConfigParameterProvider     // Catch: java.lang.Exception -> L14
                android.provider.DeviceConfigInterface r5 = r5.mDeviceConfig     // Catch: java.lang.Exception -> L14
                java.lang.String r1 = "refresh_rate_in_zone"
                java.lang.String r2 = "display_manager"
                int r5 = r5.getInt(r2, r1, r0)     // Catch: java.lang.Exception -> L14
                goto L15
            L14:
                r5 = r0
            L15:
                if (r5 != r0) goto L29
                if (r4 != 0) goto L27
                android.content.Context r5 = r3.mContext
                android.content.res.Resources r5 = r5.getResources()
                r0 = 17694846(0x10e007e, float:2.6081634E-38)
                int r5 = r5.getInteger(r0)
                goto L29
            L27:
                int r5 = r4.mDefaultLowBlockingZoneRefreshRate
            L29:
                if (r4 != 0) goto L2d
                r4 = 0
                goto L3e
            L2d:
                java.lang.String r0 = r4.mLowBlockingZoneThermalMapId
                if (r0 != 0) goto L34
                java.lang.String r0 = "default"
            L34:
                java.util.Map r4 = r4.mRefreshRateThrottlingMap
                java.util.HashMap r4 = (java.util.HashMap) r4
                java.lang.Object r4 = r4.get(r0)
                android.util.SparseArray r4 = (android.util.SparseArray) r4
            L3e:
                r3.mLowZoneRefreshRateForThermals = r4
                r3.mRefreshRateInLowZone = r5
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.loadRefreshRateInLowZone(com.android.server.display.DisplayDeviceConfig,"
                        + " boolean):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00ba  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0105 A[EDGE_INSN: B:57:0x0105->B:58:0x0105 BREAK  A[LOOP:0: B:25:0x00b5->B:43:0x0102], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0109  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onBrightnessChangedLocked() {
            /*
                Method dump skipped, instructions count: 323
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.onBrightnessChangedLocked():void");
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == 0) {
                Display display = ((RealInjector) this.mInjector).getDisplayManager().getDisplay(0);
                if (display != null) {
                    setDefaultDisplayState(display.getState());
                }
                if (CoreRune.FW_VRR_SEAMLESS) {
                    return;
                }
                Display display2 =
                        ((RealInjector) this.mInjector).getDisplayManager().getDisplay(i);
                BrightnessInfo brightnessInfo =
                        display2 != null ? display2.getBrightnessInfo() : null;
                float f = brightnessInfo != null ? brightnessInfo.adjustedBrightness : Float.NaN;
                synchronized (DisplayModeDirector.this.mLock) {
                    try {
                        if (!BrightnessSynchronizer.floatEquals(f, this.mBrightness)) {
                            this.mBrightness = f;
                            onBrightnessChangedLocked();
                        }
                    } finally {
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {}

        public void onLowPowerModeEnabledLocked(boolean z) {
            if (this.mLowPowerModeEnabled != z) {
                this.mLowPowerModeEnabled = z;
                updateSensorStatus();
                if (z) {
                    DisplayModeDirector.this.mVotesStorage.updateVote(-1, 3, null);
                    DisplayModeDirector.this.mVotesStorage.updateVote(-1, 21, null);
                }
            }
        }

        public void onRefreshRateSettingChangedLocked(float f, float f2) {
            boolean z = f2 - f > 1.0f && f2 > 60.0f;
            if (this.mRefreshRateChangeable != z) {
                this.mRefreshRateChangeable = z;
                updateSensorStatus();
                if (z) {
                    return;
                }
                DisplayModeDirector.this.mVotesStorage.updateVote(-1, 3, null);
                DisplayModeDirector.this.mVotesStorage.updateVote(-1, 21, null);
            }
        }

        public final void restartObserver() {
            if (this.mRefreshRateInLowZone > 0 || hasLowLightVrrConfig()) {
                this.mShouldObserveDisplayLowChange =
                        hasValidThreshold(this.mLowDisplayBrightnessThresholds);
                this.mShouldObserveAmbientLowChange =
                        hasValidThreshold(this.mLowAmbientBrightnessThresholds);
            } else {
                this.mShouldObserveDisplayLowChange = false;
                this.mShouldObserveAmbientLowChange = false;
            }
            if (this.mRefreshRateInHighZone > 0) {
                this.mShouldObserveDisplayHighChange =
                        hasValidThreshold(this.mHighDisplayBrightnessThresholds);
                this.mShouldObserveAmbientHighChange =
                        hasValidThreshold(this.mHighAmbientBrightnessThresholds);
            } else {
                this.mShouldObserveDisplayHighChange = false;
                this.mShouldObserveAmbientHighChange = false;
            }
            boolean z = CoreRune.FW_VRR_SEAMLESS;
            if (z) {
                this.mShouldObserveAmbientLowChange = true;
            }
            if (this.mShouldObserveAmbientLowChange || this.mShouldObserveAmbientHighChange) {
                Sensor findSensor =
                        SensorUtils.findSensor(
                                this.mSensorManager,
                                this.mLightSensorType,
                                this.mLightSensorName,
                                5);
                if (z
                        && this.mSensorManager != null
                        && (this.mLightSensorType.isEmpty() || findSensor == null)) {
                    List<Sensor> sensorList = this.mSensorManager.getSensorList(65614);
                    if (sensorList.isEmpty()) {
                        Slog.w("DisplayModeDirector", "Failed get SEM_TYPE_LIGHT_SEAMLESS");
                    } else {
                        findSensor = sensorList.get(0);
                    }
                }
                if (findSensor != null && findSensor != this.mLightSensor) {
                    this.mAmbientFilter =
                            AmbientFilterFactory.createBrightnessFilter(
                                    this.mContext.getResources(), "DisplayModeDirector");
                    this.mLightSensor = findSensor;
                }
            } else {
                this.mAmbientFilter = null;
                this.mLightSensor = null;
            }
            updateSensorStatus();
            synchronized (DisplayModeDirector.this.mLock) {
                onBrightnessChangedLocked();
            }
        }

        public void setDefaultDisplayState(int i) {
            if (this.mLoggingEnabled) {
                Slog.d(
                        "DisplayModeDirector",
                        "setDefaultDisplayState: mDefaultDisplayState = "
                                + this.mDefaultDisplayState
                                + ", state = "
                                + i);
            }
            if (this.mDefaultDisplayState != i) {
                this.mDefaultDisplayState = i;
                updateSensorStatus();
            }
        }

        public final void unregisterSensorListener() {
            LightSensorEventListener lightSensorEventListener = this.mLightSensorListener;
            BrightnessObserver.this.mHandler.removeCallbacks(
                    lightSensorEventListener.mInjectSensorEventRunnable);
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
            this.mRegisteredLightSensor = null;
            if (this.mLoggingEnabled) {
                Slog.d("DisplayModeDirector", "updateSensorStatus: unregisterListener");
            }
            if (CoreRune.FW_VRR_SEAMLESS) {
                this.mAmbientLux = -1.0f;
                DisplayModeDirector.this.mRefreshRateModeManager.onLightSensorChanged(-1.0f);
            }
        }

        public final void updateBlockingZoneThresholds(
                DisplayDeviceConfig displayDeviceConfig, boolean z) {
            loadRefreshRateInHighZone(displayDeviceConfig, z);
            loadRefreshRateInLowZone(displayDeviceConfig, z);
            this.mLowDisplayBrightnessThresholds =
                    loadBrightnessThresholds(
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                    this, 6),
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                    displayDeviceConfig, 6),
                            R.array.config_reduceBrightColorsCoefficients,
                            displayDeviceConfig,
                            z,
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                    0));
            float[] loadBrightnessThresholds =
                    loadBrightnessThresholds(
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                    this, 7),
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                    displayDeviceConfig, 7),
                            R.array.config_mainBuiltInDisplayWaterfallCutout,
                            displayDeviceConfig,
                            z,
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                    1));
            this.mLowAmbientBrightnessThresholds = loadBrightnessThresholds;
            if (this.mLowDisplayBrightnessThresholds.length != loadBrightnessThresholds.length) {
                throw new RuntimeException(
                        "display low brightness threshold array and ambient brightness threshold"
                            + " array have different length: displayBrightnessThresholds="
                                + Arrays.toString(this.mLowDisplayBrightnessThresholds)
                                + ", ambientBrightnessThresholds="
                                + Arrays.toString(this.mLowAmbientBrightnessThresholds));
            }
            this.mHighDisplayBrightnessThresholds =
                    loadBrightnessThresholds(
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                    this, 1),
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                    displayDeviceConfig, 1),
                            R.array.vendor_cross_profile_apps,
                            displayDeviceConfig,
                            z,
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                    0));
            float[] loadBrightnessThresholds2 =
                    loadBrightnessThresholds(
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                    this, 2),
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                    displayDeviceConfig, 2),
                            R.array.unloggable_phone_numbers,
                            displayDeviceConfig,
                            z,
                            new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                    1));
            this.mHighAmbientBrightnessThresholds = loadBrightnessThresholds2;
            if (this.mHighDisplayBrightnessThresholds.length == loadBrightnessThresholds2.length) {
                return;
            }
            throw new RuntimeException(
                    "display high brightness threshold array and ambient brightness threshold array"
                        + " have different length: displayBrightnessThresholds="
                            + Arrays.toString(this.mHighDisplayBrightnessThresholds)
                            + ", ambientBrightnessThresholds="
                            + Arrays.toString(this.mHighAmbientBrightnessThresholds));
        }

        public final void updateSensorStatus() {
            boolean z;
            if (this.mSensorManager == null || this.mLightSensorListener == null) {
                return;
            }
            if (this.mLoggingEnabled) {
                Slog.d(
                        "DisplayModeDirector",
                        "updateSensorStatus: mShouldObserveAmbientLowChange = "
                                + this.mShouldObserveAmbientLowChange
                                + ", mShouldObserveAmbientHighChange = "
                                + this.mShouldObserveAmbientHighChange);
                StringBuilder sb = new StringBuilder("updateSensorStatus: mLowPowerModeEnabled = ");
                sb.append(this.mLowPowerModeEnabled);
                sb.append(", mRefreshRateChangeable = ");
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "DisplayModeDirector", sb, this.mRefreshRateChangeable);
            }
            boolean z2 = true;
            if ((this.mShouldObserveAmbientLowChange || this.mShouldObserveAmbientHighChange)
                    && this.mDefaultDisplayState == 2
                    && (((CoreRune.FW_VRR_POLICY && RefreshRateConfig.getInstance().unsupportedNS())
                                    || !this.mLowPowerModeEnabled)
                            && this.mRefreshRateChangeable)) {
                Sensor sensor = this.mRegisteredLightSensor;
                if (sensor != this.mLightSensor) {
                    if (sensor != null) {
                        unregisterSensorListener();
                    }
                    this.mSensorManager.registerListener(
                            this.mLightSensorListener, this.mLightSensor, 250000, this.mHandler);
                    this.mRegisteredLightSensor = this.mLightSensor;
                    if (this.mLoggingEnabled) {
                        Slog.d("DisplayModeDirector", "updateSensorStatus: registerListener");
                    }
                }
                if (this.mLowZoneRefreshRateForThermals != null
                        || this.mHighZoneRefreshRateForThermals != null) {
                    z = true;
                    if (!z && !this.mThermalRegistered) {
                        Injector injector = this.mInjector;
                        AnonymousClass1 anonymousClass1 = this.mThermalListener;
                        ((RealInjector) injector).getClass();
                        IThermalService asInterface =
                                IThermalService.Stub.asInterface(
                                        ServiceManager.getService("thermalservice"));
                        if (asInterface != null) {
                            try {
                                asInterface.registerThermalEventListenerWithType(
                                        anonymousClass1, 3);
                            } catch (RemoteException e) {
                                Slog.e(
                                        "DisplayModeDirector",
                                        "Failed to register thermal status listener",
                                        e);
                            }
                            this.mThermalRegistered = z2;
                            return;
                        }
                        Slog.w(
                                "DisplayModeDirector",
                                "Could not observe thermal status. Service not available");
                        z2 = false;
                        this.mThermalRegistered = z2;
                        return;
                    }
                    if (z && this.mThermalRegistered) {
                        Injector injector2 = this.mInjector;
                        AnonymousClass1 anonymousClass12 = this.mThermalListener;
                        ((RealInjector) injector2).getClass();
                        IThermalService asInterface2 =
                                IThermalService.Stub.asInterface(
                                        ServiceManager.getService("thermalservice"));
                        if (asInterface2 == null) {
                            Slog.w(
                                    "DisplayModeDirector",
                                    "Could not unregister thermal status. Service not available");
                        }
                        try {
                            asInterface2.unregisterThermalEventListener(anonymousClass12);
                        } catch (RemoteException e2) {
                            Slog.e(
                                    "DisplayModeDirector",
                                    "Failed to unregister thermal status listener",
                                    e2);
                        }
                        this.mThermalRegistered = false;
                        synchronized (DisplayModeDirector.this.mLock) {
                            this.mThermalStatus = 0;
                        }
                        return;
                    }
                }
            } else {
                unregisterSensorListener();
            }
            z = false;
            if (!z) {}
            if (z) {}
        }
    }

    public final class DesiredDisplayModeSpecs {
        public boolean allowGroupSwitching;
        public final SurfaceControl.RefreshRateRanges appRequest;
        public int baseModeId;
        public SurfaceControl.IdleScreenRefreshRateConfig mIdleScreenRefreshRateConfig;
        public final SurfaceControl.RefreshRateRanges primary;

        public DesiredDisplayModeSpecs() {
            this.primary = new SurfaceControl.RefreshRateRanges();
            this.appRequest = new SurfaceControl.RefreshRateRanges();
        }

        public DesiredDisplayModeSpecs(
                int i,
                boolean z,
                SurfaceControl.RefreshRateRanges refreshRateRanges,
                SurfaceControl.RefreshRateRanges refreshRateRanges2,
                SurfaceControl.IdleScreenRefreshRateConfig idleScreenRefreshRateConfig) {
            this.baseModeId = i;
            this.allowGroupSwitching = z;
            this.primary = refreshRateRanges;
            this.appRequest = refreshRateRanges2;
            this.mIdleScreenRefreshRateConfig = idleScreenRefreshRateConfig;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DesiredDisplayModeSpecs)) {
                return false;
            }
            DesiredDisplayModeSpecs desiredDisplayModeSpecs = (DesiredDisplayModeSpecs) obj;
            return this.baseModeId == desiredDisplayModeSpecs.baseModeId
                    && this.allowGroupSwitching == desiredDisplayModeSpecs.allowGroupSwitching
                    && this.primary.equals(desiredDisplayModeSpecs.primary)
                    && this.appRequest.equals(desiredDisplayModeSpecs.appRequest)
                    && Objects.equals(
                            this.mIdleScreenRefreshRateConfig,
                            desiredDisplayModeSpecs.mIdleScreenRefreshRateConfig);
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.baseModeId),
                    Boolean.valueOf(this.allowGroupSwitching),
                    this.primary,
                    this.appRequest,
                    this.mIdleScreenRefreshRateConfig);
        }

        public final String toString() {
            return String.format(
                    "baseModeId=%d allowGroupSwitching=%b primary=%s appRequest=%s"
                        + " idleScreenRefreshRateConfig=%s",
                    Integer.valueOf(this.baseModeId),
                    Boolean.valueOf(this.allowGroupSwitching),
                    this.primary.toString(),
                    this.appRequest.toString(),
                    String.valueOf(this.mIdleScreenRefreshRateConfig));
        }
    }

    public final class DeviceConfigDisplaySettings
            implements DeviceConfig.OnPropertiesChangedListener {
        public DeviceConfigDisplaySettings() {}

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            int i;
            int i2;
            float f =
                    DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getFloat(
                            "display_manager", "peak_refresh_rate_default", -1.0f);
            DisplayModeDirector.this
                    .mHandler
                    .obtainMessage(3, f == -1.0f ? null : Float.valueOf(f))
                    .sendToTarget();
            float[] displayBrightnessThresholdsIntToFloat =
                    DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "peak_refresh_rate_brightness_thresholds"));
            float[] ambientBrightnessThresholdsIntToFloat =
                    DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "peak_refresh_rate_ambient_thresholds"));
            int i3 =
                    DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                            "display_manager", "refresh_rate_in_zone", -1);
            DisplayModeDirector.this
                    .mHandler
                    .obtainMessage(
                            2,
                            new Pair(
                                    displayBrightnessThresholdsIntToFloat,
                                    ambientBrightnessThresholdsIntToFloat))
                    .sendToTarget();
            DisplayModeDirector.this.mHandler.obtainMessage(4, i3, 0).sendToTarget();
            float[] displayBrightnessThresholdsIntToFloat2 =
                    DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "fixed_refresh_rate_high_display_brightness_thresholds"));
            float[] ambientBrightnessThresholdsIntToFloat2 =
                    DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "fixed_refresh_rate_high_ambient_brightness_thresholds"));
            int i4 =
                    DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                            "display_manager", "refresh_rate_in_high_zone", -1);
            DisplayModeDirector.this
                    .mHandler
                    .obtainMessage(
                            6,
                            new Pair(
                                    displayBrightnessThresholdsIntToFloat2,
                                    ambientBrightnessThresholdsIntToFloat2))
                    .sendToTarget();
            DisplayModeDirector.this.mHandler.obtainMessage(5, i4, 0).sendToTarget();
            synchronized (DisplayModeDirector.this.mLock) {
                DisplayModeDirector displayModeDirector = DisplayModeDirector.this;
                DisplayDeviceConfig displayDeviceConfig =
                        displayModeDirector.mDefaultDisplayDeviceConfig;
                try {
                    i =
                            displayModeDirector.mConfigParameterProvider.mDeviceConfig.getInt(
                                    "display_manager", "refresh_rate_in_hbm_sunlight", -1);
                } catch (NullPointerException unused) {
                    i = -1;
                }
                if (i == -1) {
                    i =
                            displayDeviceConfig == null
                                    ? DisplayModeDirector.this
                                            .mContext
                                            .getResources()
                                            .getInteger(R.integer.config_downloadDataDirSize)
                                    : displayDeviceConfig
                                            .mRefreshRateData
                                            .defaultRefreshRateInHbmSunlight;
                }
                DisplayModeDirector.this.mHandler.obtainMessage(7, i, 0).sendToTarget();
                DisplayModeDirector displayModeDirector2 = DisplayModeDirector.this;
                DisplayDeviceConfig displayDeviceConfig2 =
                        displayModeDirector2.mDefaultDisplayDeviceConfig;
                try {
                    i2 =
                            displayModeDirector2.mConfigParameterProvider.mDeviceConfig.getInt(
                                    "display_manager", "refresh_rate_in_hbm_hdr", -1);
                } catch (NullPointerException unused2) {
                    i2 = -1;
                }
                if (i2 == -1) {
                    i2 =
                            displayDeviceConfig2 == null
                                    ? DisplayModeDirector.this
                                            .mContext
                                            .getResources()
                                            .getInteger(
                                                    R.integer
                                                            .config_downloadDataDirLowSpaceThreshold)
                                    : displayDeviceConfig2
                                            .mRefreshRateData
                                            .defaultRefreshRateInHbmHdr;
                }
                DisplayModeDirector.this.mHandler.obtainMessage(8, i2, 0).sendToTarget();
            }
        }
    }

    public final class DisplayModeDirectorHandler extends Handler {
        public DisplayModeDirectorHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            DisplayDeviceConfig displayDeviceConfig;
            int i = 0;
            switch (message.what) {
                case 1:
                    ((DisplayManagerService.DesiredDisplayModeSpecsObserver) message.obj)
                            .onDesiredDisplayModeSpecsChanged();
                    return;
                case 2:
                    Pair pair = (Pair) message.obj;
                    BrightnessObserver.m484$$Nest$monDeviceConfigLowBrightnessThresholdsChanged(
                            DisplayModeDirector.this.mBrightnessObserver,
                            (float[]) pair.first,
                            (float[]) pair.second);
                    return;
                case 3:
                    Float f = (Float) message.obj;
                    SettingsObserver settingsObserver = DisplayModeDirector.this.mSettingsObserver;
                    synchronized (DisplayModeDirector.this.mLock) {
                        try {
                            if (f == null) {
                                settingsObserver.setDefaultPeakRefreshRate(
                                        DisplayModeDirector.this.mDefaultDisplayDeviceConfig,
                                        false);
                            } else if (settingsObserver.mDefaultPeakRefreshRate != f.floatValue()) {
                                settingsObserver.mDefaultPeakRefreshRate = f.floatValue();
                            }
                            settingsObserver.updateRefreshRateSettingLocked();
                        } finally {
                        }
                    }
                    return;
                case 4:
                    int i2 = message.arg1;
                    BrightnessObserver brightnessObserver =
                            DisplayModeDirector.this.mBrightnessObserver;
                    if (i2 == -1) {
                        synchronized (DisplayModeDirector.this.mLock) {
                            brightnessObserver.loadRefreshRateInLowZone(
                                    DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
                        }
                        brightnessObserver.restartObserver();
                        return;
                    }
                    if (i2 != brightnessObserver.mRefreshRateInLowZone) {
                        brightnessObserver.mRefreshRateInLowZone = i2;
                        brightnessObserver.restartObserver();
                        return;
                    }
                    return;
                case 5:
                    int i3 = message.arg1;
                    BrightnessObserver brightnessObserver2 =
                            DisplayModeDirector.this.mBrightnessObserver;
                    if (i3 == -1) {
                        synchronized (DisplayModeDirector.this.mLock) {
                            brightnessObserver2.loadRefreshRateInHighZone(
                                    DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
                        }
                        brightnessObserver2.restartObserver();
                        return;
                    }
                    if (i3 != brightnessObserver2.mRefreshRateInHighZone) {
                        brightnessObserver2.mRefreshRateInHighZone = i3;
                        brightnessObserver2.restartObserver();
                        return;
                    }
                    return;
                case 6:
                    Pair pair2 = (Pair) message.obj;
                    BrightnessObserver brightnessObserver3 =
                            DisplayModeDirector.this.mBrightnessObserver;
                    float[] fArr = (float[]) pair2.first;
                    float[] fArr2 = (float[]) pair2.second;
                    brightnessObserver3.getClass();
                    if (fArr == null || fArr2 == null || fArr.length != fArr2.length) {
                        synchronized (DisplayModeDirector.this.mLock) {
                            displayDeviceConfig =
                                    DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
                        }
                        brightnessObserver3.mHighDisplayBrightnessThresholds =
                                brightnessObserver3.loadBrightnessThresholds(
                                        new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                                brightnessObserver3, 0),
                                        new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                                displayDeviceConfig, 0),
                                        R.array.vendor_cross_profile_apps,
                                        displayDeviceConfig,
                                        false,
                                        new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                                0));
                        brightnessObserver3.mHighAmbientBrightnessThresholds =
                                brightnessObserver3.loadBrightnessThresholds(
                                        new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(
                                                brightnessObserver3, 5),
                                        new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(
                                                displayDeviceConfig, 5),
                                        R.array.unloggable_phone_numbers,
                                        displayDeviceConfig,
                                        false,
                                        new DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2(
                                                1));
                    } else {
                        brightnessObserver3.mHighDisplayBrightnessThresholds = fArr;
                        brightnessObserver3.mHighAmbientBrightnessThresholds = fArr2;
                    }
                    brightnessObserver3.restartObserver();
                    return;
                case 7:
                    int i4 = message.arg1;
                    HbmObserver hbmObserver = DisplayModeDirector.this.mHbmObserver;
                    if (i4 != hbmObserver.mRefreshRateInHbmSunlight) {
                        hbmObserver.mRefreshRateInHbmSunlight = i4;
                        int[] copyKeys = hbmObserver.mHbmMode.copyKeys();
                        if (copyKeys != null) {
                            int length = copyKeys.length;
                            while (i < length) {
                                hbmObserver.recalculateVotesForDisplay(copyKeys[i]);
                                i++;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 8:
                    int i5 = message.arg1;
                    HbmObserver hbmObserver2 = DisplayModeDirector.this.mHbmObserver;
                    if (i5 != hbmObserver2.mRefreshRateInHbmHdr) {
                        hbmObserver2.mRefreshRateInHbmHdr = i5;
                        int[] copyKeys2 = hbmObserver2.mHbmMode.copyKeys();
                        if (copyKeys2 != null) {
                            int length2 = copyKeys2.length;
                            while (i < length2) {
                                hbmObserver2.recalculateVotesForDisplay(copyKeys2[i]);
                                i++;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public final class DisplayObserver implements DisplayManager.DisplayListener {
        public final int mExternalDisplayPeakHeight;
        public final int mExternalDisplayPeakRefreshRate;
        public final int mExternalDisplayPeakWidth;
        public final Set mExternalDisplaysConnected = new HashSet();
        public final Handler mHandler;
        public final boolean mRefreshRateSynchronizationEnabled;
        public final VotesStorage mVotesStorage;

        public DisplayObserver(
                Context context,
                DisplayManagerService.DisplayManagerHandler displayManagerHandler,
                VotesStorage votesStorage) {
            this.mHandler = displayManagerHandler;
            this.mVotesStorage = votesStorage;
            this.mExternalDisplayPeakRefreshRate =
                    context.getResources()
                            .getInteger(R.integer.config_longPressOnStemPrimaryBehavior);
            this.mExternalDisplayPeakWidth =
                    context.getResources()
                            .getInteger(R.integer.config_lowBatteryAutoTriggerDefaultLevel);
            this.mExternalDisplayPeakHeight =
                    context.getResources().getInteger(R.integer.config_longPressOnPowerDurationMs);
            this.mRefreshRateSynchronizationEnabled =
                    context.getResources()
                            .getBoolean(R.bool.config_sensorPrivacyRequiresAuthentication);
        }

        public final DisplayInfo getDisplayInfo(int i) {
            DisplayInfo displayInfo = new DisplayInfo();
            Display display =
                    ((RealInjector) DisplayModeDirector.this.mInjector)
                            .getDisplayManager()
                            .getDisplay(i);
            if (display == null ? false : display.getDisplayInfo(displayInfo)) {
                return displayInfo;
            }
            return null;
        }

        public final boolean isExternalDisplayLimitModeEnabled() {
            if (this.mExternalDisplayPeakWidth > 0
                    && this.mExternalDisplayPeakHeight > 0
                    && this.mExternalDisplayPeakRefreshRate > 0) {
                DisplayModeDirector displayModeDirector = DisplayModeDirector.this;
                if (displayModeDirector.mIsExternalDisplayLimitModeEnabled
                        && displayModeDirector.mIsDisplayResolutionRangeVotingEnabled
                        && displayModeDirector.mIsUserPreferredModeVoteEnabled) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
            updateDisplayDeviceConfig(i);
            DisplayInfo displayInfo = getDisplayInfo(i);
            updateDisplayModes(i, displayInfo);
            updateLayoutLimitedFrameRate(i, displayInfo);
            updateUserSettingDisplayPreferredSize(displayInfo);
            if (displayInfo != null
                    && displayInfo.type == 2
                    && isExternalDisplayLimitModeEnabled()) {
                VotesStorage votesStorage = this.mVotesStorage;
                int i2 = displayInfo.displayId;
                int i3 = this.mExternalDisplayPeakWidth;
                int i4 = this.mExternalDisplayPeakHeight;
                float f = this.mExternalDisplayPeakRefreshRate;
                votesStorage.updateVote(
                        i2,
                        15,
                        new CombinedVote(
                                List.of(
                                        new SizeVote(i3, i4, 0, 0),
                                        new RefreshRateVote.PhysicalVote(
                                                FullScreenMagnificationGestureHandler.MAX_SCALE, f),
                                        new DisableRefreshRateSwitchingVote(
                                                FullScreenMagnificationGestureHandler.MAX_SCALE
                                                        == f))));
            }
            if (displayInfo != null
                    && displayInfo.type == 2
                    && this.mRefreshRateSynchronizationEnabled) {
                DisplayModeDirector displayModeDirector = DisplayModeDirector.this;
                if (displayModeDirector.mIsDisplaysRefreshRatesSynchronizationEnabled) {
                    synchronized (displayModeDirector.mLock) {
                        try {
                            ((HashSet) this.mExternalDisplaysConnected)
                                    .add(Integer.valueOf(displayInfo.displayId));
                            if (((HashSet) this.mExternalDisplaysConnected).size() == 1) {
                                this.mVotesStorage.updateVote(
                                        -1, 14, Vote.forPhysicalRefreshRates(59.0f, 61.0f));
                            }
                        } finally {
                        }
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            updateDisplayDeviceConfig(i);
            DisplayInfo displayInfo = getDisplayInfo(i);
            updateDisplayModes(i, displayInfo);
            updateLayoutLimitedFrameRate(i, displayInfo);
            updateUserSettingDisplayPreferredSize(displayInfo);
            if (CoreRune.FW_VRR_POLICY && i == 0) {
                DisplayModeDirector.this.mRefreshRateModeManager.updateDefaultDisplayToken();
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
            synchronized (DisplayModeDirector.this.mLock) {
                DisplayModeDirector.this.mSupportedModesByDisplay.remove(i);
                DisplayModeDirector.this.mAppSupportedModesByDisplay.remove(i);
                DisplayModeDirector.this.mDefaultModeByDisplay.remove(i);
                DisplayModeDirector.this.mDisplayDeviceConfigByDisplay.remove(i);
                SettingsObserver settingsObserver = DisplayModeDirector.this.mSettingsObserver;
                if (settingsObserver.mPeakRefreshRatePhysicalLimitEnabled) {
                    DisplayModeDirector.this.mVotesStorage.updateVote(i, 10, null);
                }
                DisplayModeDirector.this.mVotesStorage.updateVote(i, 13, null);
                DisplayModeDirector.this.mVotesStorage.updateVote(i, 5, null);
                DisplayModeDirector.this.mVotesStorage.updateVote(i, 0, null);
            }
            updateLayoutLimitedFrameRate(i, null);
            if (DisplayModeDirector.this.mIsUserPreferredModeVoteEnabled) {
                this.mVotesStorage.updateVote(i, 6, null);
            }
            if (isExternalDisplayLimitModeEnabled()) {
                this.mVotesStorage.updateVote(i, 15, null);
            }
            if (this.mRefreshRateSynchronizationEnabled) {
                DisplayModeDirector displayModeDirector = DisplayModeDirector.this;
                if (displayModeDirector.mIsDisplaysRefreshRatesSynchronizationEnabled) {
                    synchronized (displayModeDirector.mLock) {
                        try {
                            if (((HashSet) this.mExternalDisplaysConnected)
                                    .contains(Integer.valueOf(i))) {
                                ((HashSet) this.mExternalDisplaysConnected)
                                        .remove(Integer.valueOf(i));
                                if (((HashSet) this.mExternalDisplaysConnected).size() == 0) {
                                    this.mVotesStorage.updateVote(-1, 14, null);
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        }

        public final void updateDisplayDeviceConfig(int i) {
            DisplayDeviceConfig displayDeviceConfig =
                    DisplayModeDirector.this.mDisplayDeviceConfigProvider.getDisplayDeviceConfig(i);
            synchronized (DisplayModeDirector.this.mLock) {
                DisplayModeDirector.this.mDisplayDeviceConfigByDisplay.put(i, displayDeviceConfig);
            }
        }

        public final void updateDisplayModes(int i, DisplayInfo displayInfo) {
            boolean z;
            if (displayInfo == null) {
                return;
            }
            synchronized (DisplayModeDirector.this.mLock) {
                try {
                    boolean z2 = true;
                    if (Arrays.equals(
                            (Object[]) DisplayModeDirector.this.mSupportedModesByDisplay.get(i),
                            displayInfo.supportedModes)) {
                        z = false;
                    } else {
                        DisplayModeDirector.this.mSupportedModesByDisplay.put(
                                i, displayInfo.supportedModes);
                        z = true;
                    }
                    if (!Arrays.equals(
                            (Object[]) DisplayModeDirector.this.mAppSupportedModesByDisplay.get(i),
                            displayInfo.appsSupportedModes)) {
                        DisplayModeDirector.this.mAppSupportedModesByDisplay.put(
                                i, displayInfo.appsSupportedModes);
                        z = true;
                    }
                    if (Objects.equals(
                            DisplayModeDirector.this.mDefaultModeByDisplay.get(i),
                            displayInfo.getDefaultMode())) {
                        z2 = z;
                    } else {
                        DisplayModeDirector.this.mDefaultModeByDisplay.put(
                                i, displayInfo.getDefaultMode());
                    }
                    if (z2) {
                        DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
                        SettingsObserver settingsObserver =
                                DisplayModeDirector.this.mSettingsObserver;
                        int i2 = SettingsObserver.$r8$clinit;
                        settingsObserver.updateRefreshRateSettingLocked(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateLayoutLimitedFrameRate(int i, DisplayInfo displayInfo) {
            SurfaceControl.RefreshRateRange refreshRateRange;
            this.mVotesStorage.updateVote(
                    i,
                    17,
                    (displayInfo == null
                                    || (refreshRateRange = displayInfo.layoutLimitedRefreshRate)
                                            == null)
                            ? null
                            : Vote.forPhysicalRefreshRates(
                                    refreshRateRange.min, refreshRateRange.max));
        }

        public final void updateUserSettingDisplayPreferredSize(DisplayInfo displayInfo) {
            Display.Mode mode;
            if (displayInfo == null || !DisplayModeDirector.this.mIsUserPreferredModeVoteEnabled) {
                return;
            }
            if (displayInfo.userPreferredModeId != -1) {
                Display.Mode[] modeArr = displayInfo.supportedModes;
                int length = modeArr.length;
                for (int i = 0; i < length; i++) {
                    mode = modeArr[i];
                    if (mode.getModeId() == displayInfo.userPreferredModeId) {
                        break;
                    }
                }
            }
            mode = null;
            if (mode == null) {
                int i2 = displayInfo.displayId;
                if (DisplayModeDirector.this.mIsUserPreferredModeVoteEnabled) {
                    this.mVotesStorage.updateVote(i2, 6, null);
                    return;
                }
                return;
            }
            VotesStorage votesStorage = this.mVotesStorage;
            int i3 = displayInfo.displayId;
            int physicalWidth = mode.getPhysicalWidth();
            int physicalHeight = mode.getPhysicalHeight();
            votesStorage.updateVote(
                    i3,
                    6,
                    new SizeVote(physicalWidth, physicalHeight, physicalWidth, physicalHeight));
        }
    }

    public final class HbmObserver implements DisplayManager.DisplayListener {
        public final DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
        public DisplayManagerInternal mDisplayManagerInternal;
        public final Handler mHandler;
        public final Injector mInjector;
        public int mRefreshRateInHbmHdr;
        public int mRefreshRateInHbmSunlight;
        public final VotesStorage mVotesStorage;
        public final SparseIntArray mHbmMode = new SparseIntArray();
        public final SparseBooleanArray mHbmActive = new SparseBooleanArray();

        public HbmObserver(
                Injector injector,
                VotesStorage votesStorage,
                Handler handler,
                DeviceConfigDisplaySettings deviceConfigDisplaySettings) {
            this.mInjector = injector;
            this.mVotesStorage = votesStorage;
            this.mHandler = handler;
            this.mDeviceConfigDisplaySettings = deviceConfigDisplaySettings;
        }

        public final void dumpLocked(PrintWriter printWriter) {
            StringBuilder m$1 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            printWriter, "   HbmObserver", "     mHbmMode: ");
            m$1.append(this.mHbmMode);
            printWriter.println(m$1.toString());
            printWriter.println("     mHbmActive: " + this.mHbmActive);
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("     mRefreshRateInHbmSunlight: "),
                            this.mRefreshRateInHbmSunlight,
                            printWriter,
                            "     mRefreshRateInHbmHdr: "),
                    this.mRefreshRateInHbmHdr,
                    printWriter);
        }

        public int getRefreshRateInHbmHdr() {
            return this.mRefreshRateInHbmHdr;
        }

        public int getRefreshRateInHbmSunlight() {
            return this.mRefreshRateInHbmSunlight;
        }

        public final void observe() {
            int i;
            int i2;
            synchronized (DisplayModeDirector.this.mLock) {
                DisplayDeviceConfig displayDeviceConfig =
                        DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
                DeviceConfigDisplaySettings deviceConfigDisplaySettings =
                        this.mDeviceConfigDisplaySettings;
                deviceConfigDisplaySettings.getClass();
                try {
                    i =
                            DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                                    "display_manager", "refresh_rate_in_hbm_hdr", -1);
                } catch (NullPointerException unused) {
                    i = -1;
                }
                if (i == -1) {
                    i =
                            displayDeviceConfig == null
                                    ? DisplayModeDirector.this
                                            .mContext
                                            .getResources()
                                            .getInteger(
                                                    R.integer
                                                            .config_downloadDataDirLowSpaceThreshold)
                                    : displayDeviceConfig
                                            .mRefreshRateData
                                            .defaultRefreshRateInHbmHdr;
                }
                this.mRefreshRateInHbmHdr = i;
                DeviceConfigDisplaySettings deviceConfigDisplaySettings2 =
                        this.mDeviceConfigDisplaySettings;
                deviceConfigDisplaySettings2.getClass();
                try {
                    i2 =
                            DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                                    "display_manager", "refresh_rate_in_hbm_sunlight", -1);
                } catch (NullPointerException unused2) {
                    i2 = -1;
                }
                if (i2 == -1) {
                    i2 =
                            displayDeviceConfig == null
                                    ? DisplayModeDirector.this
                                            .mContext
                                            .getResources()
                                            .getInteger(R.integer.config_downloadDataDirSize)
                                    : displayDeviceConfig
                                            .mRefreshRateData
                                            .defaultRefreshRateInHbmSunlight;
                }
                this.mRefreshRateInHbmSunlight = i2;
            }
            ((RealInjector) this.mInjector).getClass();
            this.mDisplayManagerInternal =
                    (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
            ((RealInjector) this.mInjector)
                    .getDisplayManager()
                    .registerDisplayListener(this, this.mHandler, 10L);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            Display display = ((RealInjector) this.mInjector).getDisplayManager().getDisplay(i);
            BrightnessInfo brightnessInfo = display != null ? display.getBrightnessInfo() : null;
            if (brightnessInfo == null) {
                return;
            }
            int i2 = brightnessInfo.highBrightnessMode;
            boolean z =
                    i2 != 0
                            && brightnessInfo.adjustedBrightness
                                    > brightnessInfo.highBrightnessTransitionPoint;
            if (i2 == this.mHbmMode.get(i) && z == this.mHbmActive.get(i)) {
                return;
            }
            this.mHbmMode.put(i, i2);
            this.mHbmActive.put(i, z);
            recalculateVotesForDisplay(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
            this.mVotesStorage.updateVote(i, 4, null);
            this.mHbmMode.delete(i);
            this.mHbmActive.delete(i);
        }

        public final void recalculateVotesForDisplay(int i) {
            int i2;
            int i3 = 0;
            CombinedVote combinedVote = null;
            if (this.mHbmActive.get(i, false)) {
                int i4 = this.mHbmMode.get(i, 0);
                if (i4 == 1) {
                    int i5 = this.mRefreshRateInHbmSunlight;
                    if (i5 <= 0) {
                        List refreshRateLimitations =
                                this.mDisplayManagerInternal.getRefreshRateLimitations(i);
                        while (true) {
                            if (refreshRateLimitations == null
                                    || i3 >= refreshRateLimitations.size()) {
                                break;
                            }
                            DisplayManagerInternal.RefreshRateLimitation refreshRateLimitation =
                                    (DisplayManagerInternal.RefreshRateLimitation)
                                            refreshRateLimitations.get(i3);
                            if (refreshRateLimitation.type == 1) {
                                SurfaceControl.RefreshRateRange refreshRateRange =
                                        refreshRateLimitation.range;
                                combinedVote =
                                        Vote.forPhysicalRefreshRates(
                                                refreshRateRange.min, refreshRateRange.max);
                                break;
                            }
                            i3++;
                        }
                    } else {
                        float f = i5;
                        combinedVote = Vote.forPhysicalRefreshRates(f, f);
                    }
                } else if (i4 != 2 || (i2 = this.mRefreshRateInHbmHdr) <= 0) {
                    PendingIntentController$$ExternalSyntheticOutline0.m(
                            i4,
                            i,
                            "Unexpected HBM mode ",
                            " for display ID ",
                            "DisplayModeDirector");
                } else {
                    float f2 = i2;
                    combinedVote = Vote.forPhysicalRefreshRates(f2, f2);
                }
            }
            this.mVotesStorage.updateVote(i, 4, combinedVote);
        }
    }

    public interface Injector {
        public static final Uri PEAK_REFRESH_RATE_URI =
                Settings.System.getUriFor("peak_refresh_rate");
        public static final Uri MIN_REFRESH_RATE_URI =
                Settings.System.getUriFor("min_refresh_rate");
    }

    class RealInjector implements Injector {
        public final Context mContext;
        public DisplayManager mDisplayManager;

        public RealInjector(Context context) {
            this.mContext = context;
        }

        public final DisplayManager getDisplayManager() {
            if (this.mDisplayManager == null) {
                this.mDisplayManager =
                        (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
            }
            return this.mDisplayManager;
        }
    }

    final class SettingsObserver extends ContentObserver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Context mContext;
        public float mDefaultPeakRefreshRate;
        public float mDefaultRefreshRate;
        public final AnonymousClass1 mDisplayListener;
        public final Handler mHandler;
        public boolean mIsLowPower;
        public boolean mIsLowPowerMotionSmoothness;
        public final Uri mLowPowerModeMotionSmoothness;
        public final Uri mLowPowerModeSetting;
        public final Uri mMatchContentFrameRateSetting;
        public final Uri mMinRefreshRateSetting;
        public final boolean mPeakRefreshRatePhysicalLimitEnabled;
        public final Uri mPeakRefreshRateSetting;
        public final boolean mVsyncLowPowerVoteEnabled;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.display.mode.DisplayModeDirector$SettingsObserver$1] */
        public SettingsObserver(
                Context context,
                DisplayManagerService.DisplayManagerHandler displayManagerHandler,
                DisplayManagerFlags displayManagerFlags) {
            super(displayManagerHandler);
            this.mPeakRefreshRateSetting = Settings.System.getUriFor("peak_refresh_rate");
            this.mMinRefreshRateSetting = Settings.System.getUriFor("min_refresh_rate");
            this.mLowPowerModeSetting = Settings.Global.getUriFor("low_power");
            this.mLowPowerModeMotionSmoothness =
                    Settings.Global.getUriFor("pms_settings_refresh_rate_enabled");
            this.mMatchContentFrameRateSetting =
                    Settings.Secure.getUriFor("match_content_frame_rate");
            this.mIsLowPower = false;
            this.mIsLowPowerMotionSmoothness = false;
            this.mDisplayListener =
                    new DisplayManager
                            .DisplayListener() { // from class:
                                                 // com.android.server.display.mode.DisplayModeDirector.SettingsObserver.1
                        @Override // android.hardware.display.DisplayManager.DisplayListener
                        public final void onDisplayAdded(int i) {
                            synchronized (DisplayModeDirector.this.mLock) {
                                SettingsObserver.this.updateLowPowerModeAllowedModesLocked();
                            }
                        }

                        @Override // android.hardware.display.DisplayManager.DisplayListener
                        public final void onDisplayChanged(int i) {
                            synchronized (DisplayModeDirector.this.mLock) {
                                SettingsObserver.this.updateLowPowerModeAllowedModesLocked();
                            }
                        }

                        @Override // android.hardware.display.DisplayManager.DisplayListener
                        public final void onDisplayRemoved(int i) {
                            DisplayModeDirector.this.mVotesStorage.updateVote(i, 19, null);
                        }
                    };
            this.mContext = context;
            this.mHandler = displayManagerHandler;
            this.mVsyncLowPowerVoteEnabled = displayManagerFlags.mVsyncLowPowerVote.isEnabled();
            this.mPeakRefreshRatePhysicalLimitEnabled =
                    displayManagerFlags.mPeakRefreshRatePhysicalLimit.isEnabled();
            setRefreshRates(null, false);
        }

        public float getDefaultPeakRefreshRate() {
            return this.mDefaultPeakRefreshRate;
        }

        public float getDefaultRefreshRate() {
            return this.mDefaultRefreshRate;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            synchronized (DisplayModeDirector.this.mLock) {
                try {
                    if (!this.mPeakRefreshRateSetting.equals(uri)
                            && !this.mMinRefreshRateSetting.equals(uri)) {
                        if (!this.mLowPowerModeSetting.equals(uri)
                                && !this.mLowPowerModeMotionSmoothness.equals(uri)) {
                            if (this.mMatchContentFrameRateSetting.equals(uri)) {
                                updateModeSwitchingTypeSettingLocked();
                            }
                        }
                        updateLowPowerModeSettingLocked();
                    }
                    updateRefreshRateSettingLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x001a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setDefaultPeakRefreshRate(
                com.android.server.display.DisplayDeviceConfig r4, boolean r5) {
            /*
                r3 = this;
                r0 = -1082130432(0xffffffffbf800000, float:-1.0)
                if (r5 == 0) goto L15
                com.android.server.display.mode.DisplayModeDirector r5 = com.android.server.display.mode.DisplayModeDirector.this     // Catch: java.lang.Exception -> L15
                com.android.server.display.feature.DeviceConfigParameterProvider r5 = r5.mConfigParameterProvider     // Catch: java.lang.Exception -> L15
                android.provider.DeviceConfigInterface r5 = r5.mDeviceConfig     // Catch: java.lang.Exception -> L15
                java.lang.String r1 = "peak_refresh_rate_default"
                java.lang.String r2 = "display_manager"
                float r5 = r5.getFloat(r2, r1, r0)     // Catch: java.lang.Exception -> L15
                goto L16
            L15:
                r5 = r0
            L16:
                int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
                if (r0 != 0) goto L4a
                boolean r5 = com.samsung.android.rune.CoreRune.FW_VRR_POLICY
                if (r5 == 0) goto L35
                if (r4 != 0) goto L30
                android.content.Context r4 = r3.mContext
                android.content.res.Resources r4 = r4.getResources()
                r5 = 17695013(0x10e0125, float:2.6082102E-38)
                int r4 = r4.getInteger(r5)
            L2d:
                float r4 = (float) r4
                r5 = r4
                goto L4a
            L30:
                com.android.server.display.config.RefreshRateData r4 = r4.mRefreshRateData
                int r4 = r4.defaultPeakRefreshRate
                goto L2d
            L35:
                if (r4 != 0) goto L45
                android.content.Context r4 = r3.mContext
                android.content.res.Resources r4 = r4.getResources()
                r5 = 17694841(0x10e0079, float:2.608162E-38)
                int r4 = r4.getInteger(r5)
                goto L2d
            L45:
                com.android.server.display.config.RefreshRateData r4 = r4.mRefreshRateData
                int r4 = r4.defaultPeakRefreshRate
                goto L2d
            L4a:
                r3.mDefaultPeakRefreshRate = r5
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.SettingsObserver.setDefaultPeakRefreshRate(com.android.server.display.DisplayDeviceConfig,"
                        + " boolean):void");
        }

        public final void setRefreshRates(DisplayDeviceConfig displayDeviceConfig, boolean z) {
            RefreshRateData refreshRateData =
                    displayDeviceConfig == null ? null : displayDeviceConfig.mRefreshRateData;
            setDefaultPeakRefreshRate(displayDeviceConfig, z);
            if (CoreRune.FW_VRR_POLICY) {
                this.mDefaultRefreshRate =
                        refreshRateData == null
                                ? this.mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer.config_supportsNonResizableMultiWindow)
                                : refreshRateData.defaultRefreshRate;
            } else {
                this.mDefaultRefreshRate =
                        refreshRateData == null
                                ? this.mContext
                                        .getResources()
                                        .getInteger(R.integer.config_doublelineClockDefault)
                                : refreshRateData.defaultRefreshRate;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x0031, code lost:

           if (com.android.server.display.mode.RefreshRateController.mRefreshRateTokenController.mRefreshRateTokens.stream().anyMatch(new com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda1(2)) != false) goto L52;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateLowPowerModeAllowedModesLocked() {
            /*
                Method dump skipped, instructions count: 243
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.mode.DisplayModeDirector.SettingsObserver.updateLowPowerModeAllowedModesLocked():void");
        }

        public final void updateLowPowerModeSettingLocked() {
            boolean z = false;
            this.mIsLowPower =
                    Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0;
            boolean z2 =
                    Settings.Global.getInt(
                                    this.mContext.getContentResolver(),
                                    "pms_settings_refresh_rate_enabled",
                                    0)
                            != 0;
            this.mIsLowPowerMotionSmoothness = z2;
            DisplayModeDirector.this.mVotesStorage.updateVote(
                    -1,
                    20,
                    (this.mIsLowPower && z2)
                            ? Vote.forPolicyRate(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE, 60.0f)
                            : null);
            BrightnessObserver brightnessObserver = DisplayModeDirector.this.mBrightnessObserver;
            if (this.mIsLowPower && this.mIsLowPowerMotionSmoothness) {
                z = true;
            }
            brightnessObserver.onLowPowerModeEnabledLocked(z);
            updateLowPowerModeAllowedModesLocked();
        }

        public final void updateModeSwitchingTypeSettingLocked() {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int intForUser =
                    Settings.Secure.getIntForUser(
                            contentResolver,
                            "match_content_frame_rate",
                            DisplayModeDirector.this.mModeSwitchingType,
                            contentResolver.getUserId());
            DisplayModeDirector displayModeDirector = DisplayModeDirector.this;
            if (intForUser != displayModeDirector.mModeSwitchingType) {
                displayModeDirector.mModeSwitchingType = intForUser;
                displayModeDirector.notifyDesiredDisplayModeSpecsChangedLocked();
            }
        }

        public final void updateRefreshRateSettingLocked() {
            for (int i = 0; i < DisplayModeDirector.this.mSupportedModesByDisplay.size(); i++) {
                updateRefreshRateSettingLocked(
                        DisplayModeDirector.this.mSupportedModesByDisplay.keyAt(i));
            }
        }

        public final void updateRefreshRateSettingLocked(float f, float f2, float f3, int i) {
            if (this.mPeakRefreshRatePhysicalLimitEnabled) {
                DisplayModeDirector.this.mVotesStorage.updateVote(
                        i,
                        10,
                        f2 == FullScreenMagnificationGestureHandler.MAX_SCALE
                                ? null
                                : Vote.forPhysicalRefreshRates(
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        Math.max(f, f2)));
            }
            DisplayModeDirector.this.mVotesStorage.updateVote(
                    i,
                    13,
                    f2 == FullScreenMagnificationGestureHandler.MAX_SCALE
                            ? null
                            : new RefreshRateVote.RenderVote(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    Math.max(f, f2)));
            DisplayModeDirector.this.mVotesStorage.updateVote(
                    i, 5, new RefreshRateVote.RenderVote(f, Float.POSITIVE_INFINITY));
            DisplayModeDirector.this.mVotesStorage.updateVote(
                    -1,
                    0,
                    f3 != FullScreenMagnificationGestureHandler.MAX_SCALE
                            ? new RefreshRateVote.RenderVote(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE, f3)
                            : null);
            if (f2 == FullScreenMagnificationGestureHandler.MAX_SCALE
                    && f3 == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                Slog.e(
                        "DisplayModeDirector",
                        "Default and peak refresh rates are both 0. One of them should be set to a"
                            + " valid value.");
                f2 = f;
            } else if (f2 == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f2 = f3;
            } else if (f3 != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f2 = Math.min(f3, f2);
            }
            if (i == 0) {
                DisplayModeDirector.this.mBrightnessObserver.onRefreshRateSettingChangedLocked(
                        f, f2);
            }
        }

        public final void updateRefreshRateSettingLocked(int i) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            if (!DisplayModeDirector.this.mSupportedModesByDisplay.contains(i)) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        i,
                        "Cannot update refresh rate setting: no supported modes for display ",
                        "DisplayModeDirector");
                return;
            }
            float m483$$Nest$mgetMaxRefreshRateLocked =
                    DisplayModeDirector.m483$$Nest$mgetMaxRefreshRateLocked(
                            DisplayModeDirector.this, i);
            float floatForUser =
                    Settings.System.getFloatForUser(
                            contentResolver,
                            "min_refresh_rate",
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            contentResolver.getUserId());
            if (Float.isInfinite(floatForUser)) {
                floatForUser = m483$$Nest$mgetMaxRefreshRateLocked;
            }
            float floatForUser2 =
                    Settings.System.getFloatForUser(
                            contentResolver,
                            "peak_refresh_rate",
                            this.mDefaultPeakRefreshRate,
                            contentResolver.getUserId());
            if (!Float.isInfinite(floatForUser2)) {
                m483$$Nest$mgetMaxRefreshRateLocked = floatForUser2;
            }
            updateRefreshRateSettingLocked(
                    floatForUser, m483$$Nest$mgetMaxRefreshRateLocked, this.mDefaultRefreshRate, i);
        }
    }

    public final class UdfpsObserver extends IUdfpsRefreshRateRequestCallback.Stub {
        public final SparseBooleanArray mUdfpsRefreshRateEnabled = new SparseBooleanArray();
        public final SparseBooleanArray mAuthenticationPossible = new SparseBooleanArray();

        public UdfpsObserver() {}

        public final void dumpLocked(PrintWriter printWriter) {
            printWriter.println("  UdfpsObserver");
            printWriter.println("    mUdfpsRefreshRateEnabled: ");
            for (int i = 0; i < this.mUdfpsRefreshRateEnabled.size(); i++) {
                printWriter.println(
                        "      Display "
                                + this.mUdfpsRefreshRateEnabled.keyAt(i)
                                + ": "
                                + (this.mUdfpsRefreshRateEnabled.valueAt(i)
                                        ? "enabled"
                                        : "disabled"));
            }
            printWriter.println("    mAuthenticationPossible: ");
            for (int i2 = 0; i2 < this.mAuthenticationPossible.size(); i2++) {
                printWriter.println(
                        "      Display "
                                + this.mAuthenticationPossible.keyAt(i2)
                                + ": "
                                + (this.mAuthenticationPossible.valueAt(i2)
                                        ? "possible"
                                        : "impossible"));
            }
        }

        public final void onAuthenticationPossible(int i, boolean z) {
            synchronized (DisplayModeDirector.this.mLock) {
                this.mAuthenticationPossible.put(i, z);
                updateVoteLocked(i, 16, z);
            }
        }

        public final void onRequestDisabled(int i) {
            synchronized (DisplayModeDirector.this.mLock) {
                this.mUdfpsRefreshRateEnabled.put(i, false);
                updateVoteLocked(i, 24, false);
            }
        }

        public final void onRequestEnabled(int i) {
            synchronized (DisplayModeDirector.this.mLock) {
                this.mUdfpsRefreshRateEnabled.put(i, true);
                updateVoteLocked(i, 24, true);
            }
        }

        public final void updateVoteLocked(int i, int i2, boolean z) {
            CombinedVote combinedVote;
            if (z) {
                float m483$$Nest$mgetMaxRefreshRateLocked =
                        DisplayModeDirector.m483$$Nest$mgetMaxRefreshRateLocked(
                                DisplayModeDirector.this, i);
                combinedVote =
                        Vote.forPhysicalRefreshRates(
                                m483$$Nest$mgetMaxRefreshRateLocked,
                                m483$$Nest$mgetMaxRefreshRateLocked);
            } else {
                combinedVote = null;
            }
            DisplayModeDirector.this.mVotesStorage.updateVote(i, i2, combinedVote);
        }
    }

    /* renamed from: -$$Nest$mgetMaxRefreshRateLocked, reason: not valid java name */
    public static float m483$$Nest$mgetMaxRefreshRateLocked(
            DisplayModeDirector displayModeDirector, int i) {
        Display.Mode[] modeArr =
                (Display.Mode[]) displayModeDirector.mSupportedModesByDisplay.get(i);
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        for (Display.Mode mode : modeArr) {
            if (mode.getRefreshRate() > f) {
                f = mode.getRefreshRate();
            }
        }
        return f;
    }

    public DisplayModeDirector(
            Context context,
            DisplayManagerService.DisplayManagerHandler displayManagerHandler,
            DisplayManagerFlags displayManagerFlags,
            DisplayManagerService$$ExternalSyntheticLambda1
                    displayManagerService$$ExternalSyntheticLambda1) {
        RealInjector realInjector = new RealInjector(context);
        Object obj = new Object();
        this.mLock = obj;
        this.mDisplayDeviceConfigByDisplay = new SparseArray();
        this.mModeSwitchingType = (!CoreRune.FW_VRR_POLICY || CoreRune.FW_VRR_SEAMLESS) ? 1 : 0;
        this.mIsDisplayResolutionRangeVotingEnabled =
                displayManagerFlags.mExternalDisplayLimitModeState.isEnabled();
        this.mIsUserPreferredModeVoteEnabled =
                displayManagerFlags.mExternalDisplayLimitModeState.isEnabled();
        this.mIsExternalDisplayLimitModeEnabled =
                displayManagerFlags.mExternalDisplayLimitModeState.isEnabled();
        this.mIsDisplaysRefreshRatesSynchronizationEnabled =
                displayManagerFlags.mExternalDisplayLimitModeState.isEnabled();
        displayManagerFlags.isBackUpSmoothDisplayAndForcePeakRefreshRateEnabled();
        this.mDisplayManagerFlags = displayManagerFlags;
        this.mDisplayDeviceConfigProvider = displayManagerService$$ExternalSyntheticLambda1;
        this.mContext = context;
        this.mHandler = new DisplayModeDirectorHandler(displayManagerHandler.getLooper());
        this.mInjector = realInjector;
        boolean isEnabled = displayManagerFlags.mRefreshRateVotingTelemetry.isEnabled();
        Optional ofNullable =
                Optional.ofNullable(
                        SurfaceFlingerProperties.tryParseBoolean(
                                SystemProperties.get(
                                        "ro.surface_flinger.enable_frame_rate_override")));
        Boolean bool = Boolean.TRUE;
        VotesStatsReporter votesStatsReporter =
                new VotesStatsReporter(
                        ((Boolean) ofNullable.orElse(bool)).booleanValue(), isEnabled);
        this.mVotesStatsReporter = votesStatsReporter;
        this.mSupportedModesByDisplay = new SparseArray();
        this.mAppSupportedModesByDisplay = new SparseArray();
        this.mDefaultModeByDisplay = new SparseArray();
        this.mAppRequestObserver = new AppRequestObserver(displayManagerFlags);
        this.mConfigParameterProvider =
                new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
        DeviceConfigDisplaySettings deviceConfigDisplaySettings = new DeviceConfigDisplaySettings();
        this.mDeviceConfigDisplaySettings = deviceConfigDisplaySettings;
        this.mSettingsObserver =
                new SettingsObserver(context, displayManagerHandler, displayManagerFlags);
        this.mBrightnessObserver =
                new BrightnessObserver(
                        context, displayManagerHandler, realInjector, displayManagerFlags);
        this.mDefaultDisplayDeviceConfig = null;
        this.mUdfpsObserver = new UdfpsObserver();
        VotesStorage votesStorage =
                new VotesStorage(
                        new DisplayModeDirector$$ExternalSyntheticLambda0(this),
                        votesStatsReporter);
        this.mVotesStorage = votesStorage;
        this.mDisplayObserver = new DisplayObserver(context, displayManagerHandler, votesStorage);
        this.mSensorObserver = new ProximitySensorObserver(votesStorage, realInjector);
        this.mSkinThermalStatusObserver =
                new SkinThermalStatusObserver(
                        realInjector, votesStorage, BackgroundThread.getHandler());
        this.mHbmObserver =
                new HbmObserver(
                        realInjector,
                        votesStorage,
                        BackgroundThread.getHandler(),
                        deviceConfigDisplaySettings);
        if (displayManagerFlags.mRestrictDisplayModes.isEnabled()) {
            this.mSystemRequestObserver = new SystemRequestObserver(votesStorage);
        } else {
            this.mSystemRequestObserver = null;
        }
        this.mAlwaysRespectAppRequest = false;
        this.mSupportsFrameRateOverride =
                ((Boolean)
                                Optional.ofNullable(
                                                SurfaceFlingerProperties.tryParseBoolean(
                                                        SystemProperties.get(
                                                                "ro.surface_flinger.enable_frame_rate_override")))
                                        .orElse(bool))
                        .booleanValue();
        RefreshRateModeManager refreshRateModeManager = new RefreshRateModeManager();
        refreshRateModeManager.mDisplayToken = new AtomicReference();
        refreshRateModeManager.mModeSettingsObserver = null;
        refreshRateModeManager.mDisplayStateListener = refreshRateModeManager.new AnonymousClass1();
        refreshRateModeManager.mContext = context;
        refreshRateModeManager.mHandler = displayManagerHandler;
        refreshRateModeManager.mLock = obj;
        boolean z = CoreRune.FW_VRR_REFRESH_RATE_TOKEN;
        RefreshRateTokenController refreshRateTokenController =
                z ? new RefreshRateTokenController(obj) : null;
        RefreshRateController.mDm = this;
        RefreshRateController.mContext = context;
        RefreshRateController.mHandler = displayManagerHandler;
        RefreshRateController.mVotesStorage = votesStorage;
        if (z) {
            RefreshRateController.mRefreshRateTokenController = refreshRateTokenController;
        }
        if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            RefreshRateController.mDisplayModeDirectorHistory =
                    new SystemHistory(25, "RefreshRateModeManager");
        }
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
            refreshRateModeManager.mModeSettingsObserver =
                    refreshRateModeManager.new ModeSettingsObserver(context, displayManagerHandler);
        }
        RefreshRateModeManager.addControllerByDisplayToken(null);
        this.mRefreshRateModeManager = refreshRateModeManager;
    }

    public static String switchingTypeToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "Unknown SwitchingType ")
                                        : "SWITCHING_TYPE_RENDER_FRAME_RATE_ONLY"
                                : "SWITCHING_TYPE_ACROSS_AND_WITHIN_GROUPS"
                        : "SWITCHING_TYPE_WITHIN_GROUPS"
                : "SWITCHING_TYPE_NONE";
    }

    public final void defaultDisplayDeviceUpdated(DisplayDeviceConfig displayDeviceConfig) {
        int i;
        int i2;
        synchronized (this.mLock) {
            this.mDefaultDisplayDeviceConfig = displayDeviceConfig;
            this.mSettingsObserver.setRefreshRates(displayDeviceConfig, true);
            this.mBrightnessObserver.updateBlockingZoneThresholds(displayDeviceConfig, true);
            BrightnessObserver.m485$$Nest$mreloadLightSensor(
                    this.mBrightnessObserver, displayDeviceConfig);
            HbmObserver hbmObserver = this.mHbmObserver;
            DeviceConfigDisplaySettings deviceConfigDisplaySettings =
                    hbmObserver.mDeviceConfigDisplaySettings;
            deviceConfigDisplaySettings.getClass();
            try {
                i =
                        DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                                "display_manager", "refresh_rate_in_hbm_hdr", -1);
            } catch (NullPointerException unused) {
                i = -1;
            }
            if (i == -1) {
                i =
                        displayDeviceConfig == null
                                ? DisplayModeDirector.this
                                        .mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer.config_downloadDataDirLowSpaceThreshold)
                                : displayDeviceConfig.mRefreshRateData.defaultRefreshRateInHbmHdr;
            }
            hbmObserver.mRefreshRateInHbmHdr = i;
            DeviceConfigDisplaySettings deviceConfigDisplaySettings2 =
                    hbmObserver.mDeviceConfigDisplaySettings;
            deviceConfigDisplaySettings2.getClass();
            try {
                i2 =
                        DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                                "display_manager", "refresh_rate_in_hbm_sunlight", -1);
            } catch (NullPointerException unused2) {
                i2 = -1;
            }
            if (i2 == -1) {
                i2 =
                        displayDeviceConfig == null
                                ? DisplayModeDirector.this
                                        .mContext
                                        .getResources()
                                        .getInteger(R.integer.config_downloadDataDirSize)
                                : displayDeviceConfig
                                        .mRefreshRateData
                                        .defaultRefreshRateInHbmSunlight;
            }
            hbmObserver.mRefreshRateInHbmSunlight = i2;
        }
    }

    public final void dump(PrintWriter printWriter) {
        int i;
        printWriter.println("DisplayModeDirector");
        synchronized (this.mLock) {
            try {
                printWriter.println("  mSupportedModesByDisplay:");
                for (int i2 = 0; i2 < this.mSupportedModesByDisplay.size(); i2++) {
                    printWriter.println(
                            "    "
                                    + this.mSupportedModesByDisplay.keyAt(i2)
                                    + " -> "
                                    + Arrays.toString(
                                            (Display.Mode[])
                                                    this.mSupportedModesByDisplay.valueAt(i2)));
                }
                printWriter.println("  mAppSupportedModesByDisplay:");
                for (int i3 = 0; i3 < this.mAppSupportedModesByDisplay.size(); i3++) {
                    printWriter.println(
                            "    "
                                    + this.mAppSupportedModesByDisplay.keyAt(i3)
                                    + " -> "
                                    + Arrays.toString(
                                            (Display.Mode[])
                                                    this.mAppSupportedModesByDisplay.valueAt(i3)));
                }
                printWriter.println("  mDefaultModeByDisplay:");
                for (int i4 = 0; i4 < this.mDefaultModeByDisplay.size(); i4++) {
                    printWriter.println(
                            "    "
                                    + this.mDefaultModeByDisplay.keyAt(i4)
                                    + " -> "
                                    + ((Display.Mode) this.mDefaultModeByDisplay.valueAt(i4)));
                }
                printWriter.println(
                        "  mModeSwitchingType: " + switchingTypeToString(this.mModeSwitchingType));
                printWriter.println("  mAlwaysRespectAppRequest: " + this.mAlwaysRespectAppRequest);
                SettingsObserver settingsObserver = this.mSettingsObserver;
                settingsObserver.getClass();
                AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                        printWriter,
                                        "  SettingsObserver",
                                        "    mDefaultRefreshRate: "),
                                settingsObserver.mDefaultRefreshRate,
                                printWriter,
                                "    mDefaultPeakRefreshRate: "),
                        settingsObserver.mDefaultPeakRefreshRate,
                        printWriter);
                AppRequestObserver appRequestObserver = this.mAppRequestObserver;
                appRequestObserver.getClass();
                printWriter.println("  AppRequestObserver");
                printWriter.println(
                        "    mIgnorePreferredRefreshRate: "
                                + appRequestObserver.mIgnorePreferredRefreshRate);
                this.mBrightnessObserver.dumpLocked(printWriter);
                this.mUdfpsObserver.dumpLocked(printWriter);
                this.mHbmObserver.dumpLocked(printWriter);
                if (CoreRune.FW_VRR_POLICY) {
                    this.mRefreshRateModeManager.dumpLocked(printWriter);
                } else {
                    this.mSkinThermalStatusObserver.dumpLocked(printWriter);
                }
            } finally {
            }
        }
        this.mVotesStorage.dump(printWriter);
        ProximitySensorObserver proximitySensorObserver = this.mSensorObserver;
        proximitySensorObserver.getClass();
        printWriter.println("  SensorObserver");
        synchronized (proximitySensorObserver.mSensorObserverLock) {
            try {
                printWriter.println("    mIsProxActive=" + proximitySensorObserver.mIsProxActive);
                printWriter.println("    mDozeStateByDisplay:");
                for (i = 0; i < proximitySensorObserver.mDozeStateByDisplay.size(); i++) {
                    printWriter.println(
                            "      "
                                    + proximitySensorObserver.mDozeStateByDisplay.keyAt(i)
                                    + " -> "
                                    + proximitySensorObserver.mDozeStateByDisplay.valueAt(i));
                }
            } finally {
            }
        }
    }

    public BrightnessObserver getBrightnessObserver() {
        return this.mBrightnessObserver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c5 A[Catch: all -> 0x008f, TryCatch #0 {all -> 0x008f, blocks: (B:4:0x0009, B:8:0x0027, B:13:0x004d, B:22:0x0060, B:24:0x0064, B:15:0x0092, B:17:0x0096, B:19:0x00b8, B:25:0x00bb, B:27:0x00da, B:31:0x00e2, B:35:0x00ef, B:40:0x015d, B:41:0x00fb, B:44:0x0105, B:47:0x010d, B:49:0x0137, B:51:0x013d, B:52:0x015b, B:61:0x0169, B:62:0x01b5, B:65:0x01b7, B:72:0x01c5, B:76:0x01e5, B:79:0x01ef, B:81:0x01ff, B:82:0x0217, B:83:0x025a, B:86:0x01cd, B:88:0x01d6, B:90:0x01e2, B:94:0x025c, B:95:0x0277), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01ff A[Catch: all -> 0x008f, TryCatch #0 {all -> 0x008f, blocks: (B:4:0x0009, B:8:0x0027, B:13:0x004d, B:22:0x0060, B:24:0x0064, B:15:0x0092, B:17:0x0096, B:19:0x00b8, B:25:0x00bb, B:27:0x00da, B:31:0x00e2, B:35:0x00ef, B:40:0x015d, B:41:0x00fb, B:44:0x0105, B:47:0x010d, B:49:0x0137, B:51:0x013d, B:52:0x015b, B:61:0x0169, B:62:0x01b5, B:65:0x01b7, B:72:0x01c5, B:76:0x01e5, B:79:0x01ef, B:81:0x01ff, B:82:0x0217, B:83:0x025a, B:86:0x01cd, B:88:0x01d6, B:90:0x01e2, B:94:0x025c, B:95:0x0277), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01d6 A[Catch: all -> 0x008f, TryCatch #0 {all -> 0x008f, blocks: (B:4:0x0009, B:8:0x0027, B:13:0x004d, B:22:0x0060, B:24:0x0064, B:15:0x0092, B:17:0x0096, B:19:0x00b8, B:25:0x00bb, B:27:0x00da, B:31:0x00e2, B:35:0x00ef, B:40:0x015d, B:41:0x00fb, B:44:0x0105, B:47:0x010d, B:49:0x0137, B:51:0x013d, B:52:0x015b, B:61:0x0169, B:62:0x01b5, B:65:0x01b7, B:72:0x01c5, B:76:0x01e5, B:79:0x01ef, B:81:0x01ff, B:82:0x0217, B:83:0x025a, B:86:0x01cd, B:88:0x01d6, B:90:0x01e2, B:94:0x025c, B:95:0x0277), top: B:3:0x0009 }] */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.server.display.mode.VoteSummary, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r1v35, types: [java.lang.Object, java.util.List] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v14, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r5v15, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.server.display.mode.VoteSummary] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs
            getDesiredDisplayModeSpecs(int r27) {
        /*
            Method dump skipped, instructions count: 635
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.mode.DisplayModeDirector.getDesiredDisplayModeSpecs(int):com.android.server.display.mode.DisplayModeDirector$DesiredDisplayModeSpecs");
    }

    public DesiredDisplayModeSpecs getDesiredDisplayModeSpecsWithInjectedFpsSettings(
            float f, float f2, float f3) {
        DesiredDisplayModeSpecs desiredDisplayModeSpecs;
        synchronized (this.mLock) {
            SettingsObserver settingsObserver = this.mSettingsObserver;
            int i = SettingsObserver.$r8$clinit;
            settingsObserver.updateRefreshRateSettingLocked(f, f2, f3, 0);
            desiredDisplayModeSpecs = getDesiredDisplayModeSpecs(0);
        }
        return desiredDisplayModeSpecs;
    }

    public DisplayObserver getDisplayObserver() {
        return this.mDisplayObserver;
    }

    public HbmObserver getHbmObserver() {
        return this.mHbmObserver;
    }

    public final int getModeSwitchingType() {
        int i;
        synchronized (this.mLock) {
            i = this.mModeSwitchingType;
        }
        return i;
    }

    public SettingsObserver getSettingsObserver() {
        return this.mSettingsObserver;
    }

    public UdfpsObserver getUdpfsObserver() {
        return this.mUdfpsObserver;
    }

    public Vote getVote(int i, int i2) {
        return (Vote) this.mVotesStorage.getVotes(i).get(i2);
    }

    public void injectAppSupportedModesByDisplay(SparseArray sparseArray) {
        this.mAppSupportedModesByDisplay = sparseArray;
    }

    public void injectBrightnessObserver(BrightnessObserver brightnessObserver) {
        this.mBrightnessObserver = brightnessObserver;
    }

    public void injectDefaultModeByDisplay(SparseArray sparseArray) {
        this.mDefaultModeByDisplay = sparseArray;
    }

    public void injectDisplayDeviceConfigByDisplay(SparseArray sparseArray) {
        this.mDisplayDeviceConfigByDisplay = sparseArray;
    }

    public void injectSupportedModesByDisplay(SparseArray sparseArray) {
        this.mSupportedModesByDisplay = sparseArray;
    }

    public void injectVotesByDisplay(SparseArray sparseArray) {
        this.mVotesStorage.injectVotesByDisplay(sparseArray);
    }

    public final boolean isVrrSupportedLocked(int i) {
        DisplayDeviceConfig displayDeviceConfig =
                (DisplayDeviceConfig) this.mDisplayDeviceConfigByDisplay.get(i);
        return displayDeviceConfig != null
                && (displayDeviceConfig.mVrrSupportEnabled || CoreRune.FW_VRR_DISCRETE);
    }

    public final void notifyDesiredDisplayModeSpecsChangedLocked() {
        if (this.mDesiredDisplayModeSpecsListener != null) {
            DisplayModeDirectorHandler displayModeDirectorHandler = this.mHandler;
            if (displayModeDirectorHandler.hasMessages(1)) {
                return;
            }
            displayModeDirectorHandler
                    .obtainMessage(1, this.mDesiredDisplayModeSpecsListener)
                    .sendToTarget();
        }
    }

    public final void onBootCompleted() {
        StatusBarManagerService statusBarManagerService;
        UdfpsObserver udfpsObserver = this.mUdfpsObserver;
        ((RealInjector) DisplayModeDirector.this.mInjector).getClass();
        StatusBarManagerInternal statusBarManagerInternal =
                (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
        if (statusBarManagerInternal == null
                || DisplayModeDirector.this
                        .mContext
                        .getResources()
                        .getBoolean(R.bool.config_keepDreamingWhenUnplugging)) {
            return;
        }
        StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
        synchronized (StatusBarManagerService.this.mLock) {
            statusBarManagerService = StatusBarManagerService.this;
            statusBarManagerService.mUdfpsRefreshRateRequestCallback = udfpsObserver;
        }
        IStatusBar iStatusBar = statusBarManagerService.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.setUdfpsRefreshRateCallback(udfpsObserver);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void onUserSwitching() {
        synchronized (this.mLock) {
            try {
                SettingsObserver settingsObserver = this.mSettingsObserver;
                int i = SettingsObserver.$r8$clinit;
                settingsObserver.updateRefreshRateSettingLocked();
                updateRefreshRateSettingsOnUserSwitching();
                if (CoreRune.FW_VRR_FOR_SUB_DISPLAY) {
                    updateRefreshRateSettingsCoverOnUserSwitching();
                }
                this.mSettingsObserver.updateLowPowerModeSettingLocked();
                this.mSettingsObserver.updateModeSwitchingTypeSettingLocked();
                if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
                    this.mRefreshRateModeManager.getController().updateRefreshRateModeLocked(false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void start(SensorManager sensorManager) {
        if (CoreRune.FW_VRR_FOR_SUB_DISPLAY) {
            RefreshRateModeManager refreshRateModeManager = this.mRefreshRateModeManager;
            refreshRateModeManager.getClass();
            DisplayAddress.Physical fromPhysicalDisplayId =
                    DisplayAddress.fromPhysicalDisplayId(
                            DisplayManagerGlobal.getInstance().getPrimaryPhysicalDisplayId());
            RefreshRateController controller = refreshRateModeManager.getController();
            IBinder displayToken = SurfaceControl.getDisplayToken(fromPhysicalDisplayId);
            controller.getClass();
            RefreshRateController.sPrimaryDisplayToken = displayToken;
        }
        if (CoreRune.FW_VRR_POLICY) {
            this.mRefreshRateModeManager.updateDefaultDisplayToken();
        }
        DisplayObserver displayObserver = this.mDisplayObserver;
        ((RealInjector) DisplayModeDirector.this.mInjector)
                .getDisplayManager()
                .registerDisplayListener(
                        displayObserver,
                        (DisplayManagerService.DisplayManagerHandler) displayObserver.mHandler);
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        SparseArray sparseArray3 = new SparseArray();
        for (Display display :
                ((RealInjector) DisplayModeDirector.this.mInjector)
                        .getDisplayManager()
                        .getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED")) {
            int displayId = display.getDisplayId();
            DisplayInfo displayInfo = displayObserver.getDisplayInfo(displayId);
            sparseArray.put(displayId, displayInfo.supportedModes);
            sparseArray2.put(displayId, displayInfo.appsSupportedModes);
            sparseArray3.put(displayId, displayInfo.getDefaultMode());
        }
        DisplayDeviceConfig displayDeviceConfig =
                DisplayModeDirector.this.mDisplayDeviceConfigProvider.getDisplayDeviceConfig(0);
        synchronized (DisplayModeDirector.this.mLock) {
            try {
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    DisplayModeDirector.this.mSupportedModesByDisplay.put(
                            sparseArray.keyAt(i), (Display.Mode[]) sparseArray.valueAt(i));
                    DisplayModeDirector.this.mAppSupportedModesByDisplay.put(
                            sparseArray2.keyAt(i), (Display.Mode[]) sparseArray2.valueAt(i));
                    DisplayModeDirector.this.mDefaultModeByDisplay.put(
                            sparseArray3.keyAt(i), (Display.Mode) sparseArray3.valueAt(i));
                }
                DisplayModeDirector.this.mDisplayDeviceConfigByDisplay.put(0, displayDeviceConfig);
            } finally {
            }
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ContentResolver contentResolver = settingsObserver.mContext.getContentResolver();
        ((RealInjector) DisplayModeDirector.this.mInjector).getClass();
        contentResolver.registerContentObserver(
                Injector.PEAK_REFRESH_RATE_URI, false, settingsObserver, 0);
        ((RealInjector) DisplayModeDirector.this.mInjector).getClass();
        contentResolver.registerContentObserver(
                Injector.MIN_REFRESH_RATE_URI, false, settingsObserver, 0);
        contentResolver.registerContentObserver(
                settingsObserver.mLowPowerModeSetting, false, settingsObserver, 0);
        contentResolver.registerContentObserver(
                settingsObserver.mLowPowerModeMotionSmoothness, false, settingsObserver, 0);
        contentResolver.registerContentObserver(
                settingsObserver.mMatchContentFrameRateSetting, false, settingsObserver);
        ((RealInjector) DisplayModeDirector.this.mInjector)
                .getDisplayManager()
                .registerDisplayListener(
                        settingsObserver.mDisplayListener,
                        (DisplayManagerService.DisplayManagerHandler) settingsObserver.mHandler);
        float f =
                DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getFloat(
                        "display_manager", "peak_refresh_rate_default", -1.0f);
        if (f != -1.0f) {
            settingsObserver.mDefaultPeakRefreshRate = f;
        }
        synchronized (DisplayModeDirector.this.mLock) {
            settingsObserver.updateRefreshRateSettingLocked();
            settingsObserver.updateLowPowerModeSettingLocked();
            settingsObserver.updateModeSwitchingTypeSettingLocked();
        }
        boolean z = CoreRune.FW_VRR_POLICY;
        if (z) {
            final RefreshRateModeManager refreshRateModeManager2 = this.mRefreshRateModeManager;
            refreshRateModeManager2.getClass();
            if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
                RefreshRateModeManager.ModeSettingsObserver modeSettingsObserver =
                        refreshRateModeManager2.mModeSettingsObserver;
                ContentResolver contentResolver2 =
                        modeSettingsObserver.mContext.getContentResolver();
                contentResolver2.registerContentObserver(
                        modeSettingsObserver.mRefreshRateModeSetting,
                        false,
                        modeSettingsObserver,
                        -1);
                if (CoreRune.FW_VRR_FOR_SUB_DISPLAY) {
                    contentResolver2.registerContentObserver(
                            modeSettingsObserver.mSubRefreshRateModeSetting,
                            false,
                            modeSettingsObserver,
                            -1);
                }
                synchronized (RefreshRateModeManager.this.mLock) {
                    RefreshRateModeManager.this.getController().updateRefreshRateModeLocked(false);
                }
            }
            if (CoreRune.FW_VRR_SEAMLESS) {
                Slog.d("RefreshRateModeManager", "registerBrightnessAndStateListener");
                DisplayManagerInternal displayManagerInternal =
                        (DisplayManagerInternal)
                                LocalServices.getService(DisplayManagerInternal.class);
                displayManagerInternal.registerDisplayBrightnessListener(
                        new DisplayManagerInternal
                                .DisplayBrightnessListener() { // from class:
                                                               // com.android.server.display.mode.RefreshRateModeManager$$ExternalSyntheticLambda2
                            public final void onChanged(float f2) {
                                RefreshRateModeManager refreshRateModeManager3 =
                                        RefreshRateModeManager.this;
                                refreshRateModeManager3.getClass();
                                int brightnessFloatToInt =
                                        BrightnessSynchronizer.brightnessFloatToInt(f2);
                                refreshRateModeManager3.getController().getClass();
                                if (RefreshRateController.getBrightnessZone(
                                                RefreshRateController.mBrightness.getAndSet(
                                                        brightnessFloatToInt))
                                        == RefreshRateController.getBrightnessZone(
                                                brightnessFloatToInt)) {
                                    return;
                                }
                                refreshRateModeManager3.mHandler.post(
                                        new RefreshRateModeManager$$ExternalSyntheticLambda0(
                                                refreshRateModeManager3, 1));
                            }
                        });
                RefreshRateController controller2 = refreshRateModeManager2.getController();
                int brightnessFloatToInt =
                        BrightnessSynchronizer.brightnessFloatToInt(
                                displayManagerInternal.getCurrentScreenBrightness());
                controller2.getClass();
                RefreshRateController.getBrightnessZone(
                        RefreshRateController.mBrightness.getAndSet(brightnessFloatToInt));
                RefreshRateController.getBrightnessZone(brightnessFloatToInt);
                displayManagerInternal.registerDisplayStateListener(
                        refreshRateModeManager2.mDisplayStateListener);
            }
            Slog.d("RefreshRateModeManager", "registerWirelessChargeReceiver");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            refreshRateModeManager2.mContext.registerReceiver(
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.display.mode.RefreshRateModeManager.2
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("android.intent.action.BATTERY_CHANGED"
                                    .equals(intent.getAction())) {
                                boolean z2 = intent.getIntExtra("plugged", 0) == 4;
                                synchronized (RefreshRateModeManager.this.mLock) {
                                    RefreshRateModeManager.this.getController().getClass();
                                    if (RefreshRateController.mIsWirelessCharging.getAndSet(z2)
                                            != z2) {
                                        RefreshRateModeManager.this
                                                .getController()
                                                .onBrightnessChangedLocked();
                                    }
                                }
                            }
                        }
                    },
                    intentFilter);
        }
        if (!z || CoreRune.FW_VRR_SEAMLESS) {
            BrightnessObserver brightnessObserver = this.mBrightnessObserver;
            brightnessObserver.mSensorManager = sensorManager;
            Display display2 =
                    ((RealInjector) brightnessObserver.mInjector).getDisplayManager().getDisplay(0);
            BrightnessInfo brightnessInfo = display2 != null ? display2.getBrightnessInfo() : null;
            brightnessObserver.mBrightness =
                    brightnessInfo != null ? brightnessInfo.adjustedBrightness : Float.NaN;
            float[] displayBrightnessThresholdsIntToFloat =
                    DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "peak_refresh_rate_brightness_thresholds"));
            float[] ambientBrightnessThresholdsIntToFloat =
                    DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "peak_refresh_rate_ambient_thresholds"));
            if (displayBrightnessThresholdsIntToFloat != null
                    && ambientBrightnessThresholdsIntToFloat != null
                    && displayBrightnessThresholdsIntToFloat.length
                            == ambientBrightnessThresholdsIntToFloat.length) {
                brightnessObserver.mLowDisplayBrightnessThresholds =
                        displayBrightnessThresholdsIntToFloat;
                brightnessObserver.mLowAmbientBrightnessThresholds =
                        ambientBrightnessThresholdsIntToFloat;
            }
            float[] displayBrightnessThresholdsIntToFloat2 =
                    DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "fixed_refresh_rate_high_display_brightness_thresholds"));
            float[] ambientBrightnessThresholdsIntToFloat2 =
                    DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(
                            DisplayModeDirector.this.mConfigParameterProvider.getIntArrayProperty(
                                    "fixed_refresh_rate_high_ambient_brightness_thresholds"));
            if (displayBrightnessThresholdsIntToFloat2 != null
                    && ambientBrightnessThresholdsIntToFloat2 != null
                    && displayBrightnessThresholdsIntToFloat2.length
                            == ambientBrightnessThresholdsIntToFloat2.length) {
                brightnessObserver.mHighDisplayBrightnessThresholds =
                        displayBrightnessThresholdsIntToFloat2;
                brightnessObserver.mHighAmbientBrightnessThresholds =
                        ambientBrightnessThresholdsIntToFloat2;
            }
            int i2 =
                    DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                            "display_manager", "refresh_rate_in_zone", -1);
            if (i2 != -1) {
                brightnessObserver.mRefreshRateInLowZone = i2;
            }
            int i3 =
                    DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig.getInt(
                            "display_manager", "refresh_rate_in_high_zone", -1);
            if (i3 != -1) {
                brightnessObserver.mRefreshRateInHighZone = i3;
            }
            brightnessObserver.restartObserver();
            DeviceConfigDisplaySettings deviceConfigDisplaySettings =
                    DisplayModeDirector.this.mDeviceConfigDisplaySettings;
            DisplayModeDirector.this.mConfigParameterProvider.mDeviceConfig
                    .addOnPropertiesChangedListener(
                            "display_manager",
                            BackgroundThread.getExecutor(),
                            deviceConfigDisplaySettings);
            ((RealInjector) brightnessObserver.mInjector)
                    .getDisplayManager()
                    .registerDisplayListener(brightnessObserver, brightnessObserver.mHandler, 12L);
        }
        this.mSensorObserver.observe();
        this.mHbmObserver.observe();
        if (!z) {
            this.mSkinThermalStatusObserver.observe();
        }
        synchronized (this.mLock) {
            notifyDesiredDisplayModeSpecsChangedLocked();
        }
    }

    public final void updateRefreshRateSettingsCoverOnUserSwitching() {
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_refresh_rate_enabled",
                                0)
                        != 0) {
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(), "refresh_rate_mode_cover", 0, -2);
            return;
        }
        int intForUser =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "pms_override_refresh_rate_mode_cover",
                        -1,
                        -2);
        if (intForUser != -1) {
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(), "refresh_rate_mode_cover", intForUser, -2);
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(),
                    "pms_override_refresh_rate_mode_cover",
                    -1,
                    -2);
        }
    }

    public final void updateRefreshRateSettingsOnUserSwitching() {
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_refresh_rate_enabled",
                                0)
                        != 0) {
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(), "refresh_rate_mode", 0, -2);
            return;
        }
        int intForUser =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "pms_override_refresh_rate_mode",
                        -1,
                        -2);
        if (intForUser != -1) {
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(), "refresh_rate_mode", intForUser, -2);
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(), "pms_override_refresh_rate_mode", -1, -2);
        }
    }
}
