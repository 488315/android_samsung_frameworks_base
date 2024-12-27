package com.android.server.display;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.ExynosDisplaySolutionManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserManager;
import android.provider.Settings;
import android.util.EventLog;
import android.util.MathUtils;
import android.util.SparseArray;
import android.view.Display;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.config.DisplayBrightnessMappingConfig;
import com.android.server.display.config.HysteresisLevels;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.power.HqmDataDispatcher;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

import com.samsung.android.displayquality.SemDisplayQualityManager;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AutomaticBrightnessController {
    public final IActivityTaskManager mActivityTaskManager;
    public float mAmbientBrighteningThreshold;
    public final HysteresisLevels mAmbientBrightnessThresholds;
    public final HysteresisLevels mAmbientBrightnessThresholdsIdle;
    public final HysteresisLevels mAmbientBrightnessThresholdsTouchHigh;
    public final HysteresisLevels mAmbientBrightnessThresholdsTouchLow;
    public float mAmbientDarkeningThreshold;
    public final int mAmbientLightHorizonLong;
    public final int mAmbientLightHorizonShort;
    public final AmbientLightRingBuffer mAmbientLightRingBuffer;
    public boolean mAmbientLuxValid;
    public final long mBrighteningLightDebounceConfig;
    public final long mBrighteningLightDebounceConfigIdle;
    public float mBrightnessAdjustmentSampleOldBrightness;
    public float mBrightnessAdjustmentSampleOldLux;
    public boolean mBrightnessAdjustmentSamplePending;
    public final SparseArray mBrightnessMappingStrategyMap;
    public final BrightnessRangeController mBrightnessRangeController;
    public final BrightnessThrottler mBrightnessThrottler;
    public final Callbacks mCallbacks;
    public final RealClock mClock;
    public final Context mContext;
    public BrightnessMappingStrategy mCurrentBrightnessMapper;
    public int mCurrentLightSensorRate;
    public final long mDarkeningLightDebounceConfig;
    public final long mDarkeningLightDebounceConfigIdle;
    public final SemDisplaySolutionManager mDisplaySolutionManager;
    public final float mDozeScaleFactor;
    public float mFastAmbientLux;
    public boolean mForceUpdateBrightness;
    public int mForegroundAppCategory;
    public String mForegroundAppPackageName;
    public final AutomaticBrightnessHandler mHandler;
    public final int mInitialLightSensorRate;
    public SensorEvent mInjectedLuxEvent;
    public final Injector mInjector;
    public final AnonymousClass1 mIntentReceiver;
    public boolean mIsBrightnessThrottled;
    public final boolean mIsCameraLightTypeSupported;
    public boolean mIsLightSensorPositionUpdated;
    public boolean mIsRearLightSensor;
    public final boolean mIsUnderDisplayLightSensor;
    public float mLastAmbientLuxOfFrontLightSensor;
    public float mLastObservedLux;
    public long mLastObservedLuxTime;
    public SensorEvent mLastRealLuxEventDuringInjection;
    public float mLastSentAmbientLux;
    public final Sensor mLightSensor;
    public final Sensor mLightSensorCct;
    public long mLightSensorEnableTime;
    public boolean mLightSensorEnabled;
    public final AnonymousClass4 mLightSensorListener;
    public long mLightSensorPositionUpdatedTime;
    public final int mLightSensorWarmUpTimeConfig;
    public boolean mLoggingEnabled;
    public boolean mMinLuxUpdated;
    public long mNonInteractiveTime;
    public final int mNormalLightSensorRate;
    public final AnonymousClass2 mOnProximityChangedRunnable;
    public final PackageManager mPackageManager;
    public final ShortTermModel mPausedShortTermModel;
    public int mPendingForegroundAppCategory;
    public String mPendingForegroundAppPackageName;
    public int mPendingProximity;
    public final PowerHistorian mPowerHistorian;
    public float mPreThresholdBrightness;
    public float mPreThresholdLux;
    public int mProximity;
    public final Sensor mProximitySensor;
    public boolean mProximitySensorEnabled;
    public final AnonymousClass4 mProximitySensorListener;
    public final float mProximityThreshold;
    public int mRecentLightSamples;
    public final boolean mResetAmbientLuxAfterWarmUpConfig;
    public final float mScreenBrightnessRangeMaximum;
    public final float mScreenBrightnessRangeMinimum;
    public final SensorManager mSensorManager;
    public long mSetScreenBrightnessByUserTime;
    public final ShortTermModel mShortTermModel;
    public float mSlowAmbientLux;
    public final boolean mUseLightSensorBlockingPrevention;
    public final int mWeightingIntercept;
    public int mBrightnessThresoldsType = 0;
    public float mAmbientLux = 500.0f;
    public float mLoggedAmbientLux = 500.0f;
    public boolean mLastHBM = false;
    public float mScreenAutoBrightness = Float.NaN;
    public float mRawScreenAutoBrightness = Float.NaN;
    public int mDisplayPolicy = 0;
    public int mDisplayState = 0;
    public int mState = 2;
    public int mDualScreenPolicy = -1;
    public boolean mShouldApplyEarlyWakeUp = false;
    public Runnable mPendingEarlyLightSensorReadyListener = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.AutomaticBrightnessController$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AutomaticBrightnessController this$0;

        public /* synthetic */ AnonymousClass2(
                AutomaticBrightnessController automaticBrightnessController, int i) {
            this.$r8$classId = i;
            this.this$0 = automaticBrightnessController;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ComponentName componentName;
            int i;
            switch (this.$r8$classId) {
                case 0:
                    try {
                        ActivityTaskManager.RootTaskInfo focusedRootTaskInfo =
                                this.this$0.mActivityTaskManager.getFocusedRootTaskInfo();
                        if (focusedRootTaskInfo != null
                                && (componentName = focusedRootTaskInfo.topActivity) != null) {
                            String packageName = componentName.getPackageName();
                            String str = this.this$0.mForegroundAppPackageName;
                            if (str != null && str.equals(packageName)) {
                                break;
                            } else {
                                AutomaticBrightnessController automaticBrightnessController =
                                        this.this$0;
                                automaticBrightnessController.mPendingForegroundAppPackageName =
                                        packageName;
                                automaticBrightnessController.mPendingForegroundAppCategory = -1;
                                try {
                                    this.this$0.mPendingForegroundAppCategory =
                                            automaticBrightnessController.mPackageManager
                                                    .getApplicationInfo(packageName, 4194304)
                                                    .category;
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                                this.this$0.mHandler.sendEmptyMessage(5);
                                break;
                            }
                        }
                    } catch (RemoteException unused2) {
                        return;
                    }
                    break;
                default:
                    AutomaticBrightnessController automaticBrightnessController2 = this.this$0;
                    if (automaticBrightnessController2.mProximitySensorEnabled
                            && (i = automaticBrightnessController2.mPendingProximity) != -1) {
                        automaticBrightnessController2.mProximity = i;
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AmbientLightRingBuffer {
        public int mCapacity;
        public Clock mClock;
        public int mCount;
        public int mEnd;
        public float mLastLux;
        public long mLastTime;
        public float[] mRingLux;
        public long[] mRingTime;
        public int mStart;

        public final float getLux(int i) {
            try {
                float[] fArr = this.mRingLux;
                if (i >= this.mCount || i < 0) {
                    throw new ArrayIndexOutOfBoundsException(i);
                }
                int i2 = i + this.mStart;
                int i3 = this.mCapacity;
                if (i2 >= i3) {
                    i2 -= i3;
                }
                return fArr[i2];
            } catch (NullPointerException unused) {
                Slog.e("AutomaticBrightnessController", "Catch nullpointer exception: getLux");
                int i4 = this.mCapacity;
                float[] fArr2 = new float[i4];
                this.mRingLux = fArr2;
                long[] jArr = new long[i4];
                this.mRingTime = jArr;
                float f = this.mLastLux;
                fArr2[0] = f;
                jArr[0] = this.mLastTime;
                this.mStart = 0;
                this.mEnd = 1;
                this.mCount = 1;
                return f;
            }
        }

        public final long getTime(int i) {
            long[] jArr = this.mRingTime;
            if (i >= this.mCount || i < 0) {
                throw new ArrayIndexOutOfBoundsException(i);
            }
            int i2 = i + this.mStart;
            int i3 = this.mCapacity;
            if (i2 >= i3) {
                i2 -= i3;
            }
            return jArr[i2];
        }

        public final void prune(long j) {
            if (this.mCount == 0) {
                return;
            }
            while (true) {
                int i = this.mCount;
                if (i <= 1) {
                    break;
                }
                int i2 = this.mStart + 1;
                int i3 = this.mCapacity;
                if (i2 >= i3) {
                    i2 -= i3;
                }
                if (this.mRingTime[i2] > j) {
                    break;
                }
                this.mStart = i2;
                this.mCount = i - 1;
            }
            long[] jArr = this.mRingTime;
            int i4 = this.mStart;
            if (jArr[i4] < j) {
                jArr[i4] = j;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            int i = 0;
            while (true) {
                int i2 = this.mCount;
                if (i >= i2) {
                    sb.append(']');
                    return sb.toString();
                }
                int i3 = i + 1;
                long time = i3 < i2 ? getTime(i3) : this.mClock.getSensorEventScaleTime();
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(getLux(i));
                sb.append(" / ");
                sb.append(time - getTime(i));
                sb.append("ms");
                i = i3;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AutomaticBrightnessHandler extends Handler {
        public AutomaticBrightnessHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            AutomaticBrightnessController automaticBrightnessController =
                    AutomaticBrightnessController.this;
            switch (i) {
                case 1:
                    long sensorEventScaleTime =
                            automaticBrightnessController.mClock.getSensorEventScaleTime();
                    automaticBrightnessController.mAmbientLightRingBuffer.prune(
                            sensorEventScaleTime
                                    - automaticBrightnessController.mAmbientLightHorizonLong);
                    automaticBrightnessController.updateAmbientLux(sensorEventScaleTime);
                    break;
                case 2:
                    if (automaticBrightnessController.mBrightnessAdjustmentSamplePending) {
                        automaticBrightnessController.mBrightnessAdjustmentSamplePending = false;
                        if (automaticBrightnessController.mAmbientLuxValid) {
                            float f = automaticBrightnessController.mScreenAutoBrightness;
                            if (f >= FullScreenMagnificationGestureHandler.MAX_SCALE
                                    || f == -1.0f) {
                                if (automaticBrightnessController.mLoggingEnabled) {
                                    Slog.d(
                                            "AutomaticBrightnessController",
                                            "Auto-brightness adjustment changed by user: lux="
                                                    + automaticBrightnessController.mAmbientLux
                                                    + ", brightness="
                                                    + automaticBrightnessController
                                                            .mScreenAutoBrightness
                                                    + ", ring="
                                                    + automaticBrightnessController
                                                            .mAmbientLightRingBuffer);
                                }
                                EventLog.writeEvent(
                                        35000,
                                        Float.valueOf(
                                                automaticBrightnessController
                                                        .mBrightnessAdjustmentSampleOldLux),
                                        Float.valueOf(
                                                automaticBrightnessController
                                                        .mBrightnessAdjustmentSampleOldBrightness),
                                        Float.valueOf(automaticBrightnessController.mAmbientLux),
                                        Float.valueOf(
                                                automaticBrightnessController
                                                        .mScreenAutoBrightness));
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    automaticBrightnessController.mShortTermModel.mIsValid = false;
                    Slog.d("AutomaticBrightnessController", "ShortTermModel: invalidate user data");
                    break;
                case 4:
                    if (automaticBrightnessController.mLoggingEnabled) {
                        Slog.d(
                                "AutomaticBrightnessController",
                                "Attempting to update foreground app");
                    }
                    automaticBrightnessController.mInjector.getClass();
                    BackgroundThread.getHandler()
                            .post(new AnonymousClass2(automaticBrightnessController, 0));
                    break;
                case 5:
                    if (automaticBrightnessController.mLoggingEnabled) {
                        StringBuilder sb =
                                new StringBuilder("Updating foreground app: packageName=");
                        sb.append(automaticBrightnessController.mPendingForegroundAppPackageName);
                        sb.append(", category=");
                        BatteryService$$ExternalSyntheticOutline0.m(
                                sb,
                                automaticBrightnessController.mPendingForegroundAppCategory,
                                "AutomaticBrightnessController");
                    }
                    automaticBrightnessController.mForegroundAppPackageName =
                            automaticBrightnessController.mPendingForegroundAppPackageName;
                    automaticBrightnessController.mPendingForegroundAppPackageName = null;
                    automaticBrightnessController.mForegroundAppCategory =
                            automaticBrightnessController.mPendingForegroundAppCategory;
                    automaticBrightnessController.mPendingForegroundAppCategory = -1;
                    automaticBrightnessController.updateAutoBrightness(true);
                    break;
                case 6:
                    automaticBrightnessController.updateAutoBrightness(true);
                    break;
                case 7:
                    automaticBrightnessController.mPausedShortTermModel.mIsValid = false;
                    Slog.d("AutomaticBrightnessController", "ShortTermModel: invalidate user data");
                    break;
                case 8:
                    if (!automaticBrightnessController.mAmbientLuxValid) {
                        Slog.e(
                                "AutomaticBrightnessController",
                                "Light sensor update is timed out!");
                        automaticBrightnessController.mAmbientLuxValid = true;
                        automaticBrightnessController.updateAutoBrightness(true);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callbacks {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Clock {
        long getSensorEventScaleTime();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RealClock implements Clock {
        public final boolean mOffloadControlsDozeBrightness;

        public RealClock(boolean z) {
            this.mOffloadControlsDozeBrightness = z;
        }

        @Override // com.android.server.display.AutomaticBrightnessController.Clock
        public final long getSensorEventScaleTime() {
            return this.mOffloadControlsDozeBrightness
                    ? SystemClock.elapsedRealtime()
                    : SystemClock.uptimeMillis();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ShortTermModel {
        public float mAnchor = -1.0f;
        public float mBrightness = Float.NaN;
        public boolean mIsValid = false;

        /* renamed from: -$$Nest$mmaybeReset, reason: not valid java name */
        public static boolean m435$$Nest$mmaybeReset(ShortTermModel shortTermModel, float f) {
            if (!shortTermModel.mIsValid) {
                float f2 = shortTermModel.mAnchor;
                if (f2 != -1.0f) {
                    AutomaticBrightnessController automaticBrightnessController =
                            AutomaticBrightnessController.this;
                    BrightnessMappingStrategy brightnessMappingStrategy =
                            automaticBrightnessController.mCurrentBrightnessMapper;
                    BrightnessConfiguration brightnessConfiguration =
                            brightnessMappingStrategy.getBrightnessConfiguration();
                    if (brightnessConfiguration != null) {
                        if (!Float.isNaN(
                                brightnessConfiguration.getShortTermModelLowerLuxMultiplier())) {
                            brightnessConfiguration.getShortTermModelLowerLuxMultiplier();
                        }
                        if (!Float.isNaN(
                                brightnessConfiguration.getShortTermModelUpperLuxMultiplier())) {
                            brightnessConfiguration.getShortTermModelUpperLuxMultiplier();
                        }
                    }
                    float max = Math.max(30.0f, 0.6f * f2);
                    float f3 = f2 - max;
                    float f4 = f2 + max;
                    if (f3 >= f || f > f4) {
                        android.util.Slog.d(
                                "BrightnessMappingStrategy",
                                "ShortTermModel: reset data, ambient lux is "
                                        + f
                                        + "("
                                        + f3
                                        + ", "
                                        + f4
                                        + ")");
                        float f5 = shortTermModel.mAnchor;
                        HysteresisLevels hysteresisLevels =
                                automaticBrightnessController.mAmbientBrightnessThresholds;
                        float darkeningThreshold = hysteresisLevels.getDarkeningThreshold(f5);
                        float brighteningThreshold =
                                hysteresisLevels.getBrighteningThreshold(shortTermModel.mAnchor);
                        float f6 = automaticBrightnessController.mAmbientLux;
                        if (f6 <= darkeningThreshold || f6 >= brighteningThreshold) {
                            String str =
                                    "ShortTermModel: reset data, ambient lux is "
                                            + automaticBrightnessController.mAmbientLux
                                            + " ,hysteresis("
                                            + darkeningThreshold
                                            + ", "
                                            + brighteningThreshold
                                            + ")";
                            Slog.d("AutomaticBrightnessController", str);
                            PowerHistorian powerHistorian =
                                    automaticBrightnessController.mPowerHistorian;
                            powerHistorian.getClass();
                            powerHistorian.addRecord(3, new PowerHistorian.MessageRecord(str));
                            automaticBrightnessController.resetShortTermModel();
                        }
                    } else {
                        if (brightnessMappingStrategy.mLoggingEnabled) {
                            android.util.Slog.d(
                                    "BrightnessMappingStrategy",
                                    "ShortTermModel: re-validate user data, ambient lux is "
                                            + f3
                                            + " < "
                                            + f
                                            + " < "
                                            + f4);
                        }
                        shortTermModel.mIsValid = true;
                    }
                    return shortTermModel.mIsValid;
                }
            }
            return false;
        }

        public ShortTermModel() {}

        public final String toString() {
            return "mAnchor: "
                    + this.mAnchor
                    + "\n mBrightness: "
                    + this.mBrightness
                    + "\n mIsValid: "
                    + this.mIsValid;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskStackListenerImpl extends TaskStackListener {
        public TaskStackListenerImpl() {}

        public final void onTaskStackChanged() {
            AutomaticBrightnessController.this.mHandler.sendEmptyMessage(4);
        }
    }

    /* JADX WARN: Type inference failed for: r12v3, types: [com.android.server.display.AutomaticBrightnessController$4] */
    /* JADX WARN: Type inference failed for: r12v4, types: [com.android.server.display.AutomaticBrightnessController$4] */
    public AutomaticBrightnessController(
            Injector injector,
            Callbacks callbacks,
            Looper looper,
            SensorManager sensorManager,
            Sensor sensor,
            SparseArray sparseArray,
            int i,
            float f,
            float f2,
            float f3,
            int i2,
            int i3,
            long j,
            long j2,
            long j3,
            long j4,
            boolean z,
            HysteresisLevels hysteresisLevels,
            HysteresisLevels hysteresisLevels2,
            HysteresisLevels hysteresisLevels3,
            HysteresisLevels hysteresisLevels4,
            Context context,
            BrightnessRangeController brightnessRangeController,
            BrightnessThrottler brightnessThrottler,
            int i4,
            int i5,
            float f4,
            float f5,
            DisplayManagerFlags displayManagerFlags,
            HysteresisLevels hysteresisLevels5,
            HysteresisLevels hysteresisLevels6) {
        final int i6 = 1;
        final int i7 = 0;
        this.mIsCameraLightTypeSupported = false;
        new BroadcastReceiver() { // from class:
                                  // com.android.server.display.AutomaticBrightnessController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                long uptimeMillis = SystemClock.uptimeMillis();
                float floatExtra = intent.getFloatExtra("lux", -1.0f);
                AutomaticBrightnessController automaticBrightnessController =
                        AutomaticBrightnessController.this;
                intent.getBooleanExtra("adaptive_brightness_service_start_extra", false);
                automaticBrightnessController.getClass();
                if (floatExtra < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    Slog.d(
                            "AutomaticBrightnessController",
                            "[CAB] lux value is not received lux: "
                                    + floatExtra
                                    + "  action: "
                                    + action);
                    return;
                }
                if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG || Build.IS_ENG) {
                    Slog.d(
                            "AutomaticBrightnessController",
                            "[CAB] onReceive lux: " + floatExtra + "  action: " + action);
                }
                AutomaticBrightnessController.this.handleLightSensorEvent(uptimeMillis, floatExtra);
            }
        };
        this.mProximity = -1;
        this.mPendingProximity = -1;
        this.mPowerHistorian = PowerHistorian.INSTANCE;
        this.mOnProximityChangedRunnable = new AnonymousClass2(this, i6);
        this.mProximitySensorListener =
                new SensorEventListener(
                        this) { // from class:
                                // com.android.server.display.AutomaticBrightnessController.4
                    public final /* synthetic */ AutomaticBrightnessController this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onAccuracyChanged$com$android$server$display$AutomaticBrightnessController$4(
                                    Sensor sensor2, int i8) {}

                    private final void
                            onAccuracyChanged$com$android$server$display$AutomaticBrightnessController$5(
                                    Sensor sensor2, int i8) {}

                    @Override // android.hardware.SensorEventListener
                    public final void onAccuracyChanged(Sensor sensor2, int i8) {
                        int i9 = i7;
                    }

                    @Override // android.hardware.SensorEventListener
                    public final void onSensorChanged(SensorEvent sensorEvent) {
                        switch (i7) {
                            case 0:
                                if (this.this$0.mProximitySensorEnabled) {
                                    SystemClock.uptimeMillis();
                                    float f6 = sensorEvent.values[0];
                                    boolean z2 =
                                            this.this$0.mProximitySensor.getType() != 8
                                                    ? !(this.this$0.mProximitySensor.getType()
                                                                    != 65640
                                                            || f6 < 21.0f)
                                                    : !(f6
                                                                    < FullScreenMagnificationGestureHandler
                                                                            .MAX_SCALE
                                                            || f6
                                                                    >= this.this$0
                                                                            .mProximityThreshold);
                                    Slog.d(
                                            "AutomaticBrightnessController",
                                            "[api] onSensorChanged: proximity: " + z2);
                                    AutomaticBrightnessController automaticBrightnessController =
                                            this.this$0;
                                    if (automaticBrightnessController.mProximitySensorEnabled) {
                                        int i8 = automaticBrightnessController.mPendingProximity;
                                        if (i8 != 0 || z2) {
                                            if (i8 != 1 || !z2) {
                                                if (z2) {
                                                    automaticBrightnessController
                                                                    .mPendingProximity =
                                                            1;
                                                } else {
                                                    automaticBrightnessController
                                                                    .mPendingProximity =
                                                            0;
                                                }
                                                AutomaticBrightnessHandler
                                                        automaticBrightnessHandler =
                                                                automaticBrightnessController
                                                                        .mHandler;
                                                AnonymousClass2 anonymousClass2 =
                                                        automaticBrightnessController
                                                                .mOnProximityChangedRunnable;
                                                automaticBrightnessHandler.removeCallbacks(
                                                        anonymousClass2);
                                                automaticBrightnessHandler.postDelayed(
                                                        anonymousClass2, 200L);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                AutomaticBrightnessController automaticBrightnessController2 =
                                        this.this$0;
                                SensorEvent sensorEvent2 =
                                        automaticBrightnessController2.mInjectedLuxEvent;
                                if (sensorEvent2 != null) {
                                    automaticBrightnessController2
                                                    .mLastRealLuxEventDuringInjection =
                                            sensorEvent;
                                    sensorEvent = sensorEvent2;
                                }
                                automaticBrightnessController2.onSensorChangedInternal(sensorEvent);
                                break;
                        }
                    }
                };
        this.mLightSensorListener =
                new SensorEventListener(
                        this) { // from class:
                                // com.android.server.display.AutomaticBrightnessController.4
                    public final /* synthetic */ AutomaticBrightnessController this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            onAccuracyChanged$com$android$server$display$AutomaticBrightnessController$4(
                                    Sensor sensor2, int i8) {}

                    private final void
                            onAccuracyChanged$com$android$server$display$AutomaticBrightnessController$5(
                                    Sensor sensor2, int i8) {}

                    @Override // android.hardware.SensorEventListener
                    public final void onAccuracyChanged(Sensor sensor2, int i8) {
                        int i9 = i6;
                    }

                    @Override // android.hardware.SensorEventListener
                    public final void onSensorChanged(SensorEvent sensorEvent) {
                        switch (i6) {
                            case 0:
                                if (this.this$0.mProximitySensorEnabled) {
                                    SystemClock.uptimeMillis();
                                    float f6 = sensorEvent.values[0];
                                    boolean z2 =
                                            this.this$0.mProximitySensor.getType() != 8
                                                    ? !(this.this$0.mProximitySensor.getType()
                                                                    != 65640
                                                            || f6 < 21.0f)
                                                    : !(f6
                                                                    < FullScreenMagnificationGestureHandler
                                                                            .MAX_SCALE
                                                            || f6
                                                                    >= this.this$0
                                                                            .mProximityThreshold);
                                    Slog.d(
                                            "AutomaticBrightnessController",
                                            "[api] onSensorChanged: proximity: " + z2);
                                    AutomaticBrightnessController automaticBrightnessController =
                                            this.this$0;
                                    if (automaticBrightnessController.mProximitySensorEnabled) {
                                        int i8 = automaticBrightnessController.mPendingProximity;
                                        if (i8 != 0 || z2) {
                                            if (i8 != 1 || !z2) {
                                                if (z2) {
                                                    automaticBrightnessController
                                                                    .mPendingProximity =
                                                            1;
                                                } else {
                                                    automaticBrightnessController
                                                                    .mPendingProximity =
                                                            0;
                                                }
                                                AutomaticBrightnessHandler
                                                        automaticBrightnessHandler =
                                                                automaticBrightnessController
                                                                        .mHandler;
                                                AnonymousClass2 anonymousClass2 =
                                                        automaticBrightnessController
                                                                .mOnProximityChangedRunnable;
                                                automaticBrightnessHandler.removeCallbacks(
                                                        anonymousClass2);
                                                automaticBrightnessHandler.postDelayed(
                                                        anonymousClass2, 200L);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                AutomaticBrightnessController automaticBrightnessController2 =
                                        this.this$0;
                                SensorEvent sensorEvent2 =
                                        automaticBrightnessController2.mInjectedLuxEvent;
                                if (sensorEvent2 != null) {
                                    automaticBrightnessController2
                                                    .mLastRealLuxEventDuringInjection =
                                            sensorEvent;
                                    sensorEvent = sensorEvent2;
                                }
                                automaticBrightnessController2.onSensorChangedInternal(sensorEvent);
                                break;
                        }
                    }
                };
        this.mInjector = injector;
        boolean isEnabled = displayManagerFlags.mOffloadControlsDozeAutoBrightness.isEnabled();
        injector.getClass();
        RealClock realClock = new RealClock(isEnabled);
        this.mClock = realClock;
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mSensorManager = sensorManager;
        this.mCurrentBrightnessMapper = (BrightnessMappingStrategy) sparseArray.get(0);
        this.mScreenBrightnessRangeMinimum = f;
        this.mScreenBrightnessRangeMaximum = f2;
        this.mLightSensorWarmUpTimeConfig = i;
        this.mDozeScaleFactor = f3;
        this.mNormalLightSensorRate = i2;
        this.mInitialLightSensorRate = i3;
        this.mCurrentLightSensorRate = -1;
        this.mBrighteningLightDebounceConfig = 1000L;
        this.mDarkeningLightDebounceConfig = 2000L;
        this.mBrighteningLightDebounceConfigIdle = j3;
        this.mDarkeningLightDebounceConfigIdle = j4;
        this.mResetAmbientLuxAfterWarmUpConfig = z;
        this.mAmbientLightHorizonLong = i5;
        this.mAmbientLightHorizonShort = i4;
        this.mWeightingIntercept = i5;
        this.mAmbientBrightnessThresholds = hysteresisLevels;
        this.mAmbientBrightnessThresholdsTouchHigh = hysteresisLevels5;
        this.mAmbientBrightnessThresholdsTouchLow = hysteresisLevels6;
        this.mAmbientBrightnessThresholdsIdle = hysteresisLevels3;
        this.mShortTermModel = new ShortTermModel();
        this.mPausedShortTermModel = new ShortTermModel();
        notifyHbmInfo(false);
        this.mHandler = new AutomaticBrightnessHandler(looper);
        long j5 = i2;
        AmbientLightRingBuffer ambientLightRingBuffer = new AmbientLightRingBuffer();
        if (j5 <= 0) {
            throw new IllegalArgumentException("lightSensorRate must be above 0");
        }
        int ceil = (int) Math.ceil((i5 * 1.5f) / j5);
        ambientLightRingBuffer.mCapacity = ceil;
        ambientLightRingBuffer.mRingLux = new float[ceil];
        ambientLightRingBuffer.mRingTime = new long[ceil];
        ambientLightRingBuffer.mClock = realClock;
        this.mAmbientLightRingBuffer = ambientLightRingBuffer;
        this.mLightSensor = sensor;
        if (sensor != null) {
            int type = sensor.getType();
            if (type == 5) {
                this.mLightSensorCct = sensorManager.getDefaultSensor(65587);
            } else if (type == 65601) {
                this.mIsUnderDisplayLightSensor = true;
            } else if (type != 65604) {
                Slog.w("AutomaticBrightnessController", "Not found pre-defined light sensor");
            } else {
                Slog.d("AutomaticBrightnessController", "Using SEM_TYPE_CAMERA_LIGHT for ABC");
                this.mIsCameraLightTypeSupported = true;
            }
        }
        int i8 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
        if (this.mIsCameraLightTypeSupported) {
            this.mBrighteningLightDebounceConfig = 0L;
            this.mDarkeningLightDebounceConfig = 0L;
            this.mAmbientLuxValid = true;
            this.mForceUpdateBrightness = true;
        }
        if (PowerManagerUtil.SEC_FEATURE_TABLET) {
            Sensor defaultSensor = sensorManager.getDefaultSensor(8);
            this.mProximitySensor = defaultSensor;
            if (defaultSensor != null) {
                if (defaultSensor.getName().contains("Palm")
                        || defaultSensor.getName().contains("Ear")) {
                    this.mProximitySensor = null;
                } else {
                    this.mUseLightSensorBlockingPrevention = true;
                    this.mProximityThreshold = Math.min(defaultSensor.getMaximumRange(), 5.0f);
                }
            }
        } else if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
            Sensor defaultSensor2 = sensorManager.getDefaultSensor(65640);
            this.mProximitySensor = defaultSensor2;
            if (defaultSensor2 != null) {
                this.mUseLightSensorBlockingPrevention = true;
            }
        }
        this.mActivityTaskManager = ActivityTaskManager.getService();
        this.mPackageManager = context.getPackageManager();
        new TaskStackListenerImpl();
        this.mForegroundAppPackageName = null;
        this.mPendingForegroundAppPackageName = null;
        this.mForegroundAppCategory = -1;
        this.mPendingForegroundAppCategory = -1;
        this.mBrightnessRangeController = brightnessRangeController;
        this.mBrightnessThrottler = brightnessThrottler;
        this.mBrightnessMappingStrategyMap = sparseArray;
        this.mDisplaySolutionManager =
                (SemDisplaySolutionManager) context.getSystemService("DisplaySolution");
        if (f5 != -1.0f) {
            setScreenBrightnessByUser(f4, this.mCurrentBrightnessMapper.getBrightnessFromNits(f5));
        }
    }

    public final float calculateAmbientLux(long j, long j2) {
        int i;
        long j3 = j;
        if (this.mLoggingEnabled) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m("calculateAmbientLux(", j3, ", ");
            m.append(j2);
            m.append(")");
            Slog.d("AutomaticBrightnessController", m.toString());
        }
        AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
        int i2 = ambientLightRingBuffer.mCount;
        if (i2 == 0) {
            Slog.e(
                    "AutomaticBrightnessController",
                    "calculateAmbientLux: No ambient light readings available");
            return -1.0f;
        }
        int i3 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
        if (this.mIsCameraLightTypeSupported) {
            return ambientLightRingBuffer.getLux(i2 - 1);
        }
        long j4 = j3 - j2;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            i = i2 - 1;
            if (i4 >= i) {
                break;
            }
            i4++;
            if (ambientLightRingBuffer.getTime(i4) > j4) {
                break;
            }
            i5++;
        }
        if (this.mLoggingEnabled) {
            StringBuilder m2 =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i5, "calculateAmbientLux: selected endIndex=", ", point=(");
            m2.append(ambientLightRingBuffer.getTime(i5));
            m2.append(", ");
            m2.append(ambientLightRingBuffer.getLux(i5));
            m2.append(")");
            Slog.d("AutomaticBrightnessController", m2.toString());
        }
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float f2 = 0.0f;
        int i6 = i;
        long j5 = 100;
        while (i6 >= i5) {
            long time = ambientLightRingBuffer.getTime(i6);
            if (i6 == i5 && time < j4) {
                time = j4;
            }
            long j6 = time - j3;
            float f3 = j5;
            int i7 = this.mWeightingIntercept;
            long j7 = j4;
            float f4 = ((f3 * 0.5f) + i7) * f3;
            float f5 = j6;
            float f6 = f4 - (((0.5f * f5) + i7) * f5);
            float lux = ambientLightRingBuffer.getLux(i6);
            if (this.mLoggingEnabled) {
                StringBuilder m3 =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "calculateAmbientLux: [", j6, ", ");
                m3.append(j5);
                m3.append("]: lux=");
                m3.append(lux);
                m3.append(", weight=");
                m3.append(f6);
                Slog.d("AutomaticBrightnessController", m3.toString());
            }
            f += f6;
            f2 += lux * f6;
            i6--;
            j3 = j;
            j5 = j6;
            j4 = j7;
        }
        if (this.mLoggingEnabled) {
            Slog.d(
                    "AutomaticBrightnessController",
                    "calculateAmbientLux: totalWeight=" + f + ", newAmbientLux=" + (f2 / f));
        }
        return f2 / f;
    }

    public final void configure(
            int i,
            BrightnessConfiguration brightnessConfiguration,
            float f,
            boolean z,
            float f2,
            boolean z2,
            int i2,
            int i3,
            boolean z3,
            boolean z4,
            int i4) {
        boolean z5;
        boolean z6;
        boolean lightSensorEnabled;
        boolean z7;
        boolean screenBrightnessByUser;
        this.mState = i;
        boolean brightnessConfiguration2 =
                ((BrightnessMappingStrategy) this.mBrightnessMappingStrategyMap.get(0))
                        .setBrightnessConfiguration(brightnessConfiguration);
        boolean z8 = true;
        ShortTermModel shortTermModel = this.mShortTermModel;
        if (brightnessConfiguration2 || z4) {
            PowerHistorian powerHistorian = this.mPowerHistorian;
            powerHistorian.getClass();
            powerHistorian.addRecord(
                    3,
                    new PowerHistorian.MessageRecord(
                            "ShortTermModel: reset data, change configuration"));
            if (!isInIdleMode() && z3) {
                if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                    this.mCurrentBrightnessMapper.clearUserDataPoints();
                    shortTermModel.mAnchor = -1.0f;
                    shortTermModel.mBrightness = Float.NaN;
                    shortTermModel.mIsValid = false;
                } else {
                    resetShortTermModel();
                }
            }
            z5 = true;
        } else {
            z5 = false;
        }
        int i5 = this.mDisplayPolicy;
        boolean z9 = this.mIsCameraLightTypeSupported;
        if (i5 == i2) {
            z6 = false;
        } else {
            this.mDisplayPolicy = i2;
            if (this.mLoggingEnabled) {
                Slog.d(
                        "AutomaticBrightnessController",
                        "Display policy transitioning from " + i5 + " to " + i2);
            }
            if (i2 != 3 && i2 != 2 && ((i5 == 3 || i5 == 2) && !isInIdleMode())) {
                this.mNonInteractiveTime = SystemClock.elapsedRealtime();
            } else if ((i2 == 3 || i2 == 2) && i5 != 3 && i5 != 2) {
                int i6 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
                if (z9) {
                    this.mForceUpdateBrightness = true;
                }
                if (((BrightnessMappingStrategy) this.mBrightnessMappingStrategyMap.get(0))
                                        .getDefaultConfig()
                                != null
                        && SystemClock.elapsedRealtime() - this.mNonInteractiveTime
                                > ((BrightnessMappingStrategy)
                                                this.mBrightnessMappingStrategyMap.get(0))
                                        .getShortTermModelTimeout()) {
                    shortTermModel.mIsValid = false;
                    Slog.d("AutomaticBrightnessController", "ShortTermModel: invalidate user data");
                }
            }
            z6 = true;
        }
        boolean z10 = z6 | z5;
        this.mDisplayState = i3;
        if (z2) {
            z10 |= this.mCurrentBrightnessMapper.setAutoBrightnessAdjustment(f2);
        }
        boolean z11 = this.mState == 1;
        if (z && z11) {
            if (this.mAmbientLuxValid) {
                float calculateAmbientLux = calculateAmbientLux(SystemClock.uptimeMillis(), 600L);
                if (FullScreenMagnificationGestureHandler.MAX_SCALE <= calculateAmbientLux
                        && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                                || calculateAmbientLux < PowerManagerUtil.HBM_LUX)) {
                    setAmbientLux(calculateAmbientLux);
                } else if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                        && this.mAmbientLux >= PowerManagerUtil.HBM_LUX) {
                    Slog.d(
                            "AutomaticBrightnessController",
                            "setScreenBrightnessByUser: cancel currentLux: " + calculateAmbientLux);
                }
                screenBrightnessByUser = setScreenBrightnessByUser(this.mAmbientLux, f);
                z10 |= screenBrightnessByUser;
            }
            screenBrightnessByUser = false;
            z10 |= screenBrightnessByUser;
        }
        boolean z12 = z || z2;
        AutomaticBrightnessHandler automaticBrightnessHandler = this.mHandler;
        if (z12 && z11) {
            if (this.mBrightnessAdjustmentSamplePending) {
                automaticBrightnessHandler.removeMessages(2);
            } else {
                this.mBrightnessAdjustmentSamplePending = true;
                this.mBrightnessAdjustmentSampleOldLux =
                        this.mAmbientLuxValid ? this.mAmbientLux : -1.0f;
                this.mBrightnessAdjustmentSampleOldBrightness = this.mScreenAutoBrightness;
            }
            automaticBrightnessHandler.sendEmptyMessageDelayed(2, 10000L);
        }
        if (this.mDualScreenPolicy != i4) {
            this.mDualScreenPolicy = i4;
            z10 = true;
        }
        int i7 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
        if (z9) {
            AnonymousClass4 anonymousClass4 = this.mLightSensorListener;
            if (z11 || this.mShouldApplyEarlyWakeUp) {
                if (!this.mLightSensorEnabled) {
                    this.mLightSensorEnabled = true;
                    this.mLightSensorEnableTime = SystemClock.uptimeMillis();
                    int i8 = this.mInitialLightSensorRate;
                    this.mCurrentLightSensorRate = i8;
                    if (z9) {
                        this.mSensorManager.registerListener(
                                anonymousClass4,
                                this.mLightSensor,
                                i8 * 1000,
                                automaticBrightnessHandler);
                    }
                    Slog.d("AutomaticBrightnessController", "setLightSensorEnabledForCAB: true");
                    z7 = true;
                    lightSensorEnabled = z10 | z7;
                }
            } else if (this.mLightSensorEnabled) {
                this.mLightSensorEnabled = false;
                this.mForceUpdateBrightness = false;
                this.mRecentLightSamples = 0;
                AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
                ambientLightRingBuffer.mStart = 0;
                ambientLightRingBuffer.mEnd = 0;
                ambientLightRingBuffer.mCount = 0;
                sendAmbientLux(-1.0f);
                this.mScreenAutoBrightness = Float.NaN;
                if (z9) {
                    this.mSensorManager.unregisterListener(anonymousClass4);
                }
                Slog.d("AutomaticBrightnessController", "setLightSensorEnabledForCAB: false");
            }
            z7 = false;
            lightSensorEnabled = z10 | z7;
        } else {
            lightSensorEnabled = z10 | setLightSensorEnabled(z11 || this.mShouldApplyEarlyWakeUp);
            if (this.mUseLightSensorBlockingPrevention) {
                boolean z13 = this.mLightSensorEnabled;
                AnonymousClass4 anonymousClass42 = this.mProximitySensorListener;
                if (z13) {
                    if (!this.mProximitySensorEnabled) {
                        this.mProximitySensorEnabled = true;
                        Slog.d(
                                "AutomaticBrightnessController",
                                "setProximitySensorEnabled::registerListener");
                        this.mSensorManager.registerListener(
                                anonymousClass42,
                                this.mProximitySensor,
                                3,
                                automaticBrightnessHandler);
                    }
                } else if (this.mProximitySensorEnabled) {
                    this.mProximitySensorEnabled = false;
                    this.mProximity = -1;
                    this.mPendingProximity = -1;
                    automaticBrightnessHandler.removeCallbacks(this.mOnProximityChangedRunnable);
                    Slog.d(
                            "AutomaticBrightnessController",
                            "setProximitySensorEnabled::unregisterListener");
                    this.mSensorManager.unregisterListener(anonymousClass42);
                }
            }
        }
        boolean z14 = this.mIsBrightnessThrottled;
        int i9 = this.mBrightnessThrottler.mBrightnessMaxReason;
        if (z14 != (i9 != 0)) {
            this.mIsBrightnessThrottled = i9 != 0;
        } else {
            z8 = lightSensorEnabled;
        }
        Runnable runnable = this.mPendingEarlyLightSensorReadyListener;
        if (runnable != null) {
            runnable.run();
            this.mPendingEarlyLightSensorReadyListener = null;
        }
        if (z8) {
            updateAutoBrightness(false);
        }
    }

    public float getAmbientLux() {
        return this.mAmbientLux;
    }

    public final float getAutomaticScreenBrightness(BrightnessEvent brightnessEvent) {
        if (brightnessEvent != null) {
            boolean z = this.mAmbientLuxValid;
            brightnessEvent.mLux = z ? this.mAmbientLux : Float.NaN;
            brightnessEvent.mPreThresholdLux = this.mPreThresholdLux;
            brightnessEvent.mPreThresholdBrightness = this.mPreThresholdBrightness;
            brightnessEvent.mRecommendedBrightness = this.mScreenAutoBrightness;
            int i = 0;
            int i2 = (!z ? 2 : 0) | brightnessEvent.mFlags;
            if (Display.isDozeState(this.mDisplayState)
                    && this.mCurrentBrightnessMapper.getMode() != 2) {
                i = 4;
            }
            brightnessEvent.mFlags = i2 | i;
            brightnessEvent.mAutoBrightnessMode = this.mCurrentBrightnessMapper.getMode();
        }
        if (this.mAmbientLuxValid) {
            return (!Display.isDozeState(this.mDisplayState)
                            || this.mCurrentBrightnessMapper.getMode() == 2)
                    ? this.mScreenAutoBrightness
                    : this.mScreenAutoBrightness * this.mDozeScaleFactor;
        }
        return Float.NaN;
    }

    public final float getRawAutomaticScreenBrightness() {
        return this.mRawScreenAutoBrightness;
    }

    public final void handleLightSensorEvent(long j, float f) {
        Trace.traceCounter(131072L, "ALS", (int) f);
        AutomaticBrightnessHandler automaticBrightnessHandler = this.mHandler;
        automaticBrightnessHandler.removeMessages(1);
        AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
        if (ambientLightRingBuffer.mCount == 0 && !this.mIsUnderDisplayLightSensor) {
            int i = this.mCurrentLightSensorRate;
            int i2 = this.mNormalLightSensorRate;
            if (i2 != i) {
                if (this.mLoggingEnabled) {
                    Slog.d(
                            "AutomaticBrightnessController",
                            "adjustLightSensorRate: previousRate="
                                    + this.mCurrentLightSensorRate
                                    + ", currentRate="
                                    + i2);
                }
                this.mCurrentLightSensorRate = i2;
                SensorManager sensorManager = this.mSensorManager;
                AnonymousClass4 anonymousClass4 = this.mLightSensorListener;
                sensorManager.unregisterListener(anonymousClass4);
                this.mSensorManager.registerListener(
                        anonymousClass4, this.mLightSensor, i2 * 1000, automaticBrightnessHandler);
            }
        }
        this.mRecentLightSamples++;
        ambientLightRingBuffer.prune(j - this.mAmbientLightHorizonLong);
        int i3 = ambientLightRingBuffer.mEnd;
        int i4 = ambientLightRingBuffer.mCount;
        int i5 = ambientLightRingBuffer.mCapacity;
        if (i4 == i5) {
            int i6 = i5 * 2;
            float[] fArr = new float[i6];
            long[] jArr = new long[i6];
            int i7 = ambientLightRingBuffer.mStart;
            int i8 = i5 - i7;
            System.arraycopy(ambientLightRingBuffer.mRingLux, i7, fArr, 0, i8);
            System.arraycopy(
                    ambientLightRingBuffer.mRingTime, ambientLightRingBuffer.mStart, jArr, 0, i8);
            int i9 = ambientLightRingBuffer.mStart;
            if (i9 != 0) {
                System.arraycopy(ambientLightRingBuffer.mRingLux, 0, fArr, i8, i9);
                System.arraycopy(
                        ambientLightRingBuffer.mRingTime,
                        0,
                        jArr,
                        i8,
                        ambientLightRingBuffer.mStart);
            }
            ambientLightRingBuffer.mRingLux = fArr;
            ambientLightRingBuffer.mRingTime = jArr;
            int i10 = ambientLightRingBuffer.mCapacity;
            ambientLightRingBuffer.mCapacity = i6;
            ambientLightRingBuffer.mStart = 0;
            i3 = i10;
        }
        ambientLightRingBuffer.mRingTime[i3] = j;
        ambientLightRingBuffer.mRingLux[i3] = f;
        ambientLightRingBuffer.mLastTime = j;
        ambientLightRingBuffer.mLastLux = f;
        int i11 = i3 + 1;
        ambientLightRingBuffer.mEnd = i11;
        if (i11 == ambientLightRingBuffer.mCapacity) {
            ambientLightRingBuffer.mEnd = 0;
        }
        ambientLightRingBuffer.mCount++;
        this.mLastObservedLux = f;
        this.mLastObservedLuxTime = j;
        int i12 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
        if (!this.mIsCameraLightTypeSupported) {
            updateAmbientLux(j);
            return;
        }
        if (!this.mAmbientLuxValid) {
            setAmbientLux(calculateAmbientLux(j, 0L));
            this.mAmbientLuxValid = true;
            if (this.mLoggingEnabled) {
                Slog.d(
                        "AutomaticBrightnessController",
                        "updateAmbientLux: Initializing: mAmbientLightRingBuffer="
                                + ambientLightRingBuffer
                                + ", mAmbientLux="
                                + this.mAmbientLux);
            }
            updateAutoBrightness(true);
        }
        float calculateAmbientLux = calculateAmbientLux(j, 0L);
        if (calculateAmbientLux >= this.mAmbientBrighteningThreshold
                || calculateAmbientLux <= this.mAmbientDarkeningThreshold
                || this.mForceUpdateBrightness) {
            this.mForceUpdateBrightness = false;
            setAmbientLux(calculateAmbientLux);
            if (this.mLoggingEnabled) {
                StringBuilder sb = new StringBuilder("updateAmbientLux: ");
                sb.append(calculateAmbientLux > this.mAmbientLux ? "Brightened" : "Darkened");
                sb.append(": mBrighteningLuxThreshold=");
                sb.append(this.mAmbientBrighteningThreshold);
                sb.append(", mAmbientLightRingBuffer=");
                sb.append(ambientLightRingBuffer);
                sb.append(", mAmbientLux=");
                sb.append(this.mAmbientLux);
                Slog.d("AutomaticBrightnessController", sb.toString());
            }
            updateAutoBrightness(true);
        }
    }

    public final boolean isInIdleMode() {
        return this.mCurrentBrightnessMapper.getMode() == 1;
    }

    public final long nextAmbientLightBrighteningTransition(long j) {
        AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
        for (int i = ambientLightRingBuffer.mCount - 1;
                i >= 0 && ambientLightRingBuffer.getLux(i) >= this.mAmbientBrighteningThreshold;
                i--) {
            j = ambientLightRingBuffer.getTime(i);
        }
        return j
                + (isInIdleMode()
                        ? this.mBrighteningLightDebounceConfigIdle
                        : this.mBrighteningLightDebounceConfig);
    }

    public final long nextAmbientLightDarkeningTransition(long j) {
        AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
        for (int i = ambientLightRingBuffer.mCount - 1;
                i >= 0 && ambientLightRingBuffer.getLux(i) <= this.mAmbientDarkeningThreshold;
                i--) {
            j = ambientLightRingBuffer.getTime(i);
        }
        return j
                + (isInIdleMode()
                        ? this.mDarkeningLightDebounceConfigIdle
                        : this.mDarkeningLightDebounceConfig);
    }

    public final void notifyHbmInfo(boolean z) {
        List users = ((UserManager) this.mContext.getSystemService("user")).getUsers();
        for (int i = 0; i < users.size(); i++) {
            UserInfo userInfo = (UserInfo) users.get(i);
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(),
                    "high_brightness_mode_pms_enter",
                    z ? 1 : 0,
                    userInfo.id);
        }
    }

    public final void onSensorChangedInternal(SensorEvent sensorEvent) {
        boolean z = false;
        if (!this.mAmbientLuxValid) {
            Slog.d(
                    "AutomaticBrightnessController",
                    "onSensorChanged: 1st lux : " + sensorEvent.values[0]);
        }
        if (this.mLightSensorEnabled) {
            this.mClock.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            if (this.mIsUnderDisplayLightSensor) {
                if (fArr[1] > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    this.mMinLuxUpdated = true;
                    this.mRecentLightSamples = 0;
                    AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
                    ambientLightRingBuffer.mStart = 0;
                    ambientLightRingBuffer.mEnd = 0;
                    ambientLightRingBuffer.mCount = 0;
                    Slog.d(
                            "AutomaticBrightnessController",
                            "onSensorChanged: min lux : " + sensorEvent.values[0]);
                }
                if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
                    if (sensorEvent.values[2] > FullScreenMagnificationGestureHandler.MAX_SCALE
                            && this.mDualScreenPolicy == 0) {
                        z = true;
                    }
                    if (this.mIsRearLightSensor != z) {
                        this.mIsLightSensorPositionUpdated = true;
                        this.mLightSensorPositionUpdatedTime = uptimeMillis;
                        this.mIsRearLightSensor = z;
                        Slog.d(
                                "AutomaticBrightnessController",
                                "onSensorChanged: ".concat(z ? "Front -> Rear" : "Rear -> Front"));
                    }
                }
            }
            handleLightSensorEvent(uptimeMillis, f);
        }
    }

    public final void resetShortTermModel() {
        Slog.d("AutomaticBrightnessController", "resetShortTermModel");
        this.mCurrentBrightnessMapper.clearUserDataPoints();
        ShortTermModel shortTermModel = this.mShortTermModel;
        shortTermModel.mAnchor = -1.0f;
        shortTermModel.mBrightness = Float.NaN;
        shortTermModel.mIsValid = false;
        DisplayPowerController displayPowerController = (DisplayPowerController) this.mCallbacks;
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                displayPowerController.mAdaptiveBrightnessLongtermModelBuilder;
        if (adaptiveBrightnessLongtermModelBuilder == null) {
            displayPowerController.sendUpdatePowerState();
        } else {
            android.util.Slog.d(
                    "AdaptiveBrightnessLongtermModelBuilder", "notifyShortTermResetValid()");
            adaptiveBrightnessLongtermModelBuilder.mBgHandler.obtainMessage(7).sendToTarget();
        }
    }

    public final void sendAmbientLux(float f) {
        SemDisplayQualityManager semDisplayQualityManager;
        ExynosDisplaySolutionManager exynosDisplaySolutionManager;
        if (this.mLastSentAmbientLux == f) {
            return;
        }
        Slog.d("AutomaticBrightnessController", "sendAmbientLux(" + f + ")");
        this.mLastSentAmbientLux = f;
        if (PowerManagerUtil.SEC_FEATURE_PAPAYA_DQE
                && (exynosDisplaySolutionManager =
                                (ExynosDisplaySolutionManager)
                                        this.mContext.getSystemService("exynos_display"))
                        != null) {
            exynosDisplaySolutionManager.setDisplayFeature("atc_user", 1, (int) f, (String) null);
        }
        if (PowerManagerUtil.SEC_FEATURE_DISPLAY_QUALITY
                && (semDisplayQualityManager =
                                (SemDisplayQualityManager)
                                        this.mContext.getSystemService("DisplayQuality"))
                        != null) {
            semDisplayQualityManager.enhanceDisplayOutdoorVisibilityByLux((int) f);
        }
        if (this.mDisplaySolutionManager != null) {
            int i = this.mDualScreenPolicy != 1 ? 0 : 1;
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "sendAmbientLux(", ", ");
            int i2 = (int) f;
            m.append(i2);
            m.append(")");
            Slog.d("AutomaticBrightnessController", m.toString());
            this.mDisplaySolutionManager.updateAutoBrightnessLux(i, i2);
        }
    }

    public final void setAmbientLux(float f) {
        AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder;
        if (this.mLoggingEnabled) {
            Slog.d("AutomaticBrightnessController", "setAmbientLux(" + f + ")");
        }
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            Slog.w(
                    "AutomaticBrightnessController",
                    "Ambient lux was negative, ignoring and setting to 0");
            f = 0.0f;
        }
        sendAmbientLux(f);
        this.mAmbientLux = f;
        int i = this.mBrightnessThresoldsType;
        HysteresisLevels hysteresisLevels =
                i != 1
                        ? i != 2
                                ? this.mAmbientBrightnessThresholds
                                : this.mAmbientBrightnessThresholdsTouchLow
                        : this.mAmbientBrightnessThresholdsTouchHigh;
        this.mAmbientBrighteningThreshold = hysteresisLevels.getBrighteningThreshold(f);
        this.mAmbientDarkeningThreshold = hysteresisLevels.getDarkeningThreshold(f);
        this.mBrightnessRangeController.onAmbientLuxChange(this.mAmbientLux);
        if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            DisplayPowerController displayPowerController =
                    (DisplayPowerController) this.mCallbacks;
            displayPowerController.getClass();
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL
                    && (adaptiveBrightnessLongtermModelBuilder =
                                    displayPowerController.mAdaptiveBrightnessLongtermModelBuilder)
                            != null) {
                android.util.Slog.d(
                        "AdaptiveBrightnessLongtermModelBuilder",
                        String.format("notifyAmbientLuxChanged(lux=%f)", Float.valueOf(f)));
                adaptiveBrightnessLongtermModelBuilder
                        .mBgHandler
                        .obtainMessage(5, Float.floatToIntBits(f), 0)
                        .sendToTarget();
            }
        }
        ShortTermModel.m435$$Nest$mmaybeReset(this.mShortTermModel, this.mAmbientLux);
    }

    public final boolean setLightSensorEnabled(boolean z) {
        AutomaticBrightnessHandler automaticBrightnessHandler = this.mHandler;
        AnonymousClass4 anonymousClass4 = this.mLightSensorListener;
        if (z) {
            if (!this.mLightSensorEnabled) {
                this.mLightSensorEnabled = true;
                this.mClock.getClass();
                this.mLightSensorEnableTime = SystemClock.uptimeMillis();
                this.mCurrentLightSensorRate = this.mInitialLightSensorRate;
                Slog.d("AutomaticBrightnessController", "setLightSensorEnabled: true");
                Sensor sensor = this.mLightSensorCct;
                if (sensor == null) {
                    sensor = this.mLightSensor;
                }
                this.mSensorManager.registerListener(
                        anonymousClass4,
                        sensor,
                        this.mCurrentLightSensorRate * 1000,
                        automaticBrightnessHandler);
                automaticBrightnessHandler.sendEmptyMessageDelayed(8, 800L);
                return true;
            }
        } else if (this.mLightSensorEnabled) {
            this.mLightSensorEnabled = false;
            boolean z2 = !this.mResetAmbientLuxAfterWarmUpConfig;
            this.mAmbientLuxValid = z2;
            if (!z2) {
                this.mPreThresholdLux = Float.NaN;
            }
            this.mScreenAutoBrightness = Float.NaN;
            this.mRawScreenAutoBrightness = Float.NaN;
            this.mPreThresholdBrightness = Float.NaN;
            this.mRecentLightSamples = 0;
            AmbientLightRingBuffer ambientLightRingBuffer = this.mAmbientLightRingBuffer;
            ambientLightRingBuffer.mStart = 0;
            ambientLightRingBuffer.mEnd = 0;
            ambientLightRingBuffer.mCount = 0;
            this.mCurrentLightSensorRate = -1;
            sendAmbientLux(-1.0f);
            automaticBrightnessHandler.removeMessages(1);
            automaticBrightnessHandler.removeMessages(8);
            Slog.d("AutomaticBrightnessController", "setLightSensorEnabled: false");
            this.mIsRearLightSensor = false;
            this.mIsLightSensorPositionUpdated = false;
            this.mBrightnessThresoldsType = 0;
            this.mSensorManager.unregisterListener(anonymousClass4);
        }
        return false;
    }

    public final boolean setScreenBrightnessByUser(float f, float f2) {
        if (f == -1.0f || Float.isNaN(f2)) {
            return false;
        }
        this.mCurrentBrightnessMapper.addUserDataPoint(f, f2);
        ShortTermModel shortTermModel = this.mShortTermModel;
        shortTermModel.mAnchor = f;
        shortTermModel.mBrightness = f2;
        shortTermModel.mIsValid = true;
        Slog.d("AutomaticBrightnessController", "ShortTermModel: anchor=" + shortTermModel.mAnchor);
        if (this.mIsUnderDisplayLightSensor) {
            this.mSetScreenBrightnessByUserTime = SystemClock.uptimeMillis();
            Slog.d("AutomaticBrightnessController", "change BrighteningLightDebounceConfig");
        }
        if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_DPUC) {
            float f3 = this.mAmbientLux;
            DisplayPowerController displayPowerController =
                    (DisplayPowerController) this.mCallbacks;
            HqmDataDispatcher hqmDataDispatcher = displayPowerController.mHqmDataDispatcher;
            if (hqmDataDispatcher != null) {
                int i = (int) f3;
                int i2 = (int) (displayPowerController.mPrevScreenBrightness * 255.0f);
                int i3 = (int) (f2 * 255.0f);
                int intForUser =
                        Settings.System.getIntForUser(
                                hqmDataDispatcher.mContext.getContentResolver(),
                                "screen_brightness_mode",
                                0,
                                -2);
                StringBuilder sb = new StringBuilder();
                List<ActivityManager.RunningTaskInfo> runningTasks =
                        ((ActivityManager) hqmDataDispatcher.mContext.getSystemService("activity"))
                                .getRunningTasks(1);
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i2,
                        runningTasks.isEmpty()
                                ? ""
                                : runningTasks.get(0).topActivity.getPackageName(),
                        ",",
                        ",",
                        sb);
                sb.append(hqmDataDispatcher.mBatteryManagerInternal.getBatteryLevel());
                String sb2 = sb.toString();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("LUBS_L", String.valueOf(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jSONObject.put("LUBS_B", String.valueOf(i3));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                try {
                    jSONObject.put("LUBS_M", String.valueOf(intForUser));
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                try {
                    jSONObject.put("LUBS_P", sb2);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                HqmDataDispatcher.DispatchHandler dispatchHandler = hqmDataDispatcher.mHandler;
                dispatchHandler.removeMessages(0);
                String replaceAll =
                        jSONObject.toString().replaceAll("\\{", "").replaceAll("\\}", "");
                Message obtain = Message.obtain(dispatchHandler, 0);
                obtain.obj = replaceAll;
                dispatchHandler.sendMessageDelayed(obtain, 10000);
            }
        }
        return true;
    }

    public final void switchMode(int i, boolean z) {
        if (this.mBrightnessMappingStrategyMap.contains(i)
                && this.mCurrentBrightnessMapper.getMode() != i) {
            Slog.i(
                    "AutomaticBrightnessController",
                    "Switching to mode "
                            + DisplayBrightnessMappingConfig.autoBrightnessModeToString(i));
            if (i == 1 || this.mCurrentBrightnessMapper.getMode() == 1) {
                this.mCurrentBrightnessMapper.getClass();
                this.mCurrentBrightnessMapper.getClass();
                AutomaticBrightnessHandler automaticBrightnessHandler = this.mHandler;
                automaticBrightnessHandler.removeMessages(7);
                this.mClock.getClass();
                automaticBrightnessHandler.sendEmptyMessageAtTime(
                        7,
                        this.mCurrentBrightnessMapper.getShortTermModelTimeout()
                                + SystemClock.uptimeMillis());
                StringBuilder sb = new StringBuilder("mPreviousShortTermModel: ");
                ShortTermModel shortTermModel = this.mPausedShortTermModel;
                sb.append(shortTermModel);
                Slog.i("AutomaticBrightnessController", sb.toString());
                this.mCurrentBrightnessMapper =
                        (BrightnessMappingStrategy) this.mBrightnessMappingStrategyMap.get(i);
                if (shortTermModel != null) {
                    if (!ShortTermModel.m435$$Nest$mmaybeReset(shortTermModel, this.mAmbientLux)) {
                        setScreenBrightnessByUser(
                                shortTermModel.mAnchor, shortTermModel.mBrightness);
                    }
                    shortTermModel.mAnchor = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    shortTermModel.mBrightness = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    shortTermModel.mIsValid = true;
                }
            } else {
                resetShortTermModel();
                this.mCurrentBrightnessMapper =
                        (BrightnessMappingStrategy) this.mBrightnessMappingStrategyMap.get(i);
            }
            if (z) {
                this.mHandler.sendEmptyMessage(6);
            } else {
                updateAutoBrightness(false);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:94:0x021f, code lost:

       if (r10 == false) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0221, code lost:

       if (r3 == false) goto L113;
    */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0215  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateAmbientLux(long r20) {
        /*
            Method dump skipped, instructions count: 700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.AutomaticBrightnessController.updateAmbientLux(long):void");
    }

    public final void updateAutoBrightness(boolean z) {
        if (this.mAmbientLuxValid) {
            float brightness =
                    this.mCurrentBrightnessMapper.getBrightness(
                            this.mForegroundAppPackageName,
                            this.mAmbientLux,
                            this.mForegroundAppCategory);
            this.mRawScreenAutoBrightness = brightness;
            BrightnessRangeController brightnessRangeController = this.mBrightnessRangeController;
            float brightnessIntToFloat =
                    BrightnessSynchronizer.brightnessIntToFloat(
                            BrightnessSynchronizer.brightnessFloatToInt(
                                    MathUtils.constrain(
                                            brightness,
                                            brightnessRangeController.mHbmController.mBrightnessMin,
                                            brightnessRangeController.getCurrentBrightnessMax())));
            float f = this.mScreenAutoBrightness;
            BrightnessSynchronizer.floatEquals(
                    f,
                    MathUtils.constrain(
                            f,
                            brightnessRangeController.mHbmController.mBrightnessMin,
                            brightnessRangeController.getCurrentBrightnessMax()));
            if (this.mScreenAutoBrightness != brightnessIntToFloat
                    || this.mAmbientLux != this.mLoggedAmbientLux) {
                StringBuilder sb = new StringBuilder("updateAutoBrightness : ");
                sb.append(
                        String.format(
                                "%3d",
                                Integer.valueOf(
                                        BrightnessSynchronizer.brightnessFloatToInt(
                                                brightnessIntToFloat))));
                sb.append("(");
                Locale locale = Locale.US;
                sb.append(String.format(locale, "%.2f", Float.valueOf(brightness)));
                sb.append(") ");
                sb.append(String.format("%6.0f", Float.valueOf(this.mAmbientDarkeningThreshold)));
                sb.append(" < ");
                sb.append(String.format(locale, "%8.1f", Float.valueOf(this.mAmbientLux)));
                sb.append(" < ");
                sb.append(String.format("%6.0f", Float.valueOf(this.mAmbientBrighteningThreshold)));
                sb.append(" (adj:");
                sb.append(
                        String.format(
                                locale,
                                "%+.1f",
                                Float.valueOf(
                                        this.mCurrentBrightnessMapper
                                                .getAutoBrightnessAdjustment())));
                sb.append(")");
                sb.append(Float.isNaN(this.mScreenAutoBrightness) ? " (1st)" : "");
                String sb2 = sb.toString();
                Slog.w("AutomaticBrightnessController", "[api] " + sb2);
                PowerHistorian powerHistorian = this.mPowerHistorian;
                powerHistorian.getClass();
                powerHistorian.addRecord(3, new PowerHistorian.MessageRecord(sb2));
                float f2 = this.mAmbientLux;
                this.mLoggedAmbientLux = f2;
                if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM) {
                    boolean z2 = f2 >= ((float) PowerManagerUtil.HBM_LUX);
                    if (this.mLastHBM != z2) {
                        StringBuilder sb3 = new StringBuilder("HBM is ");
                        sb3.append(z2 ? "Enabled" : "Disabled");
                        sb3.append(". mScreenAutoBrightness = ");
                        sb3.append(PowerManagerUtil.brightnessToString(brightnessIntToFloat));
                        Slog.d("AutomaticBrightnessController", sb3.toString());
                        this.mLastHBM = z2;
                        notifyHbmInfo(z2);
                    }
                }
            }
            if (BrightnessSynchronizer.floatEquals(
                    this.mScreenAutoBrightness, brightnessIntToFloat)) {
                return;
            }
            if (this.mLoggingEnabled) {
                Slog.d(
                        "AutomaticBrightnessController",
                        "updateAutoBrightness: mScreenAutoBrightness="
                                + this.mScreenAutoBrightness
                                + ", newScreenAutoBrightness="
                                + brightnessIntToFloat);
            }
            this.mScreenAutoBrightness = brightnessIntToFloat;
            if (this.mIsUnderDisplayLightSensor) {
                this.mSensorManager.setBrightnessHysteresisParameter(
                        new float[] {
                            BrightnessSynchronizer.brightnessFloatToInt(brightnessIntToFloat),
                            this.mAmbientDarkeningThreshold,
                            this.mAmbientLux,
                            this.mAmbientBrighteningThreshold
                        });
            }
            if (z) {
                ((DisplayPowerController) this.mCallbacks).sendUpdatePowerState();
            }
        }
    }
}
