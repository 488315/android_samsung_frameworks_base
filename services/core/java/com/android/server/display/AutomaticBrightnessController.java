package com.android.server.display;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.DisplayManagerInternal;
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
import android.util.Spline;
import android.util.TimeUtils;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.samsung.android.displayquality.SemDisplayQualityManager;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class AutomaticBrightnessController {
  public final String ACTION_CAMERA_ADAPTIVE_BRIGHTNESS;
  public final long LIGHT_SENSOR_UPDATE_TIMEOUT_MILLIS;
  public final String TAG_CAB;
  public IActivityTaskManager mActivityTaskManager;
  public float mAmbientBrighteningThreshold;
  public final HysteresisLevels mAmbientBrightnessThresholds;
  public final HysteresisLevels mAmbientBrightnessThresholdsIdle;
  public final HysteresisLevels mAmbientBrightnessThresholdsTouchHigh;
  public final HysteresisLevels mAmbientBrightnessThresholdsTouchLow;
  public float mAmbientDarkeningThreshold;
  public final int mAmbientLightHorizonLong;
  public final int mAmbientLightHorizonShort;
  public AmbientLightRingBuffer mAmbientLightRingBuffer;
  public float mAmbientLux;
  public boolean mAmbientLuxValid;
  public long mBrighteningLightDebounceConfig;
  public float mBrightnessAdjustmentSampleOldBrightness;
  public float mBrightnessAdjustmentSampleOldLux;
  public boolean mBrightnessAdjustmentSamplePending;
  public int mBrightnessThresoldsType;
  public BrightnessThrottler mBrightnessThrottler;
  public final Callbacks mCallbacks;
  public Clock mClock;
  public Context mContext;
  public BrightnessMappingStrategy mCurrentBrightnessMapper;
  public int mCurrentLightSensorRate;
  public long mDarkeningLightDebounceConfig;
  public boolean mDexDualViewMode;
  public int mDisplayPolicy;
  public final float mDozeScaleFactor;
  public int mDualScreenPolicy;
  public float mFastAmbientLux;
  public boolean mForceUpdateBrightness;
  public int mForegroundAppCategory;
  public String mForegroundAppPackageName;
  public AutomaticBrightnessHandler mHandler;
  public HighBrightnessModeController mHbmController;
  public final BrightnessMappingStrategy mIdleModeBrightnessMapper;
  public final int mInitialLightSensorRate;
  public final Injector mInjector;
  public BroadcastReceiver mIntentReceiver;
  public final BrightnessMappingStrategy mInteractiveModeBrightnessMapper;
  public boolean mIsBrightnessThrottled;
  public boolean mIsCameraLightTypeSupported;
  public boolean mIsLightSensorPositionUpdated;
  public boolean mIsRearLightSensor;
  public boolean mIsUnderDisplayLightSensor;
  public float mLastAmbientLuxOfFrontLightSensor;
  public boolean mLastHBM;
  public float mLastObservedLux;
  public long mLastObservedLuxTime;
  public float mLastSentAmbientLux;
  public final Sensor mLightSensor;
  public Sensor mLightSensorCct;
  public long mLightSensorEnableTime;
  public boolean mLightSensorEnabled;
  public final SensorEventListener mLightSensorListener;
  public long mLightSensorPositionUpdatedTime;
  public int mLightSensorWarmUpTimeConfig;
  public float mLoggedAmbientLux;
  public boolean mLoggingEnabled;
  public boolean mLuxInjected;
  public boolean mMinLuxUpdated;
  public long mNonInteractiveTime;
  public final int mNormalLightSensorRate;
  public final Runnable mOnProximityChangedRunnable;
  public PackageManager mPackageManager;
  public final ShortTermModel mPausedShortTermModel;
  public Runnable mPendingEarlyLightSensorReadyListener;
  public int mPendingForegroundAppCategory;
  public String mPendingForegroundAppPackageName;
  public int mPendingProximity;
  public final PowerHistorian mPowerHistorian;
  public float mPreThresholdBrightness;
  public float mPreThresholdLux;
  public int mProximity;
  public Sensor mProximitySensor;
  public boolean mProximitySensorEnabled;
  public final SensorEventListener mProximitySensorListener;
  public float mProximityThreshold;
  public float mRawScreenAutoBrightness;
  public int mRecentLightSamples;
  public final boolean mResetAmbientLuxAfterWarmUpConfig;
  public float mScreenAutoBrightness;
  public float mScreenBrighteningThreshold;
  public final float mScreenBrightnessRangeMaximum;
  public final float mScreenBrightnessRangeMinimum;
  public final HysteresisLevels mScreenBrightnessThresholds;
  public final HysteresisLevels mScreenBrightnessThresholdsIdle;
  public float mScreenDarkeningThreshold;
  public final SensorManager mSensorManager;
  public long mSetScreenBrightnessByUserTime;
  public final ShortTermModel mShortTermModel;
  public boolean mShouldApplyEarlyWakeUp;
  public float mSlowAmbientLux;
  public int mState;
  public TaskStackListenerImpl mTaskStackListener;
  public boolean mUseLightSensorBlockingPrevention;
  public final int mWeightingIntercept;
  public boolean onCABServiceStart;

  public interface Callbacks {
    void onAmbientLuxChanged(float f);

    void onShortTermReset();

    void onUserPointAdded(float f, float f2);

    void updateBrightness();
  }

  interface Clock {
    long uptimeMillis();
  }

  public static boolean isInteractivePolicy(int i) {
    return i == 3 || i == 2;
  }

  public AutomaticBrightnessController(
      Callbacks callbacks,
      Looper looper,
      SensorManager sensorManager,
      Sensor sensor,
      BrightnessMappingStrategy brightnessMappingStrategy,
      int i,
      float f,
      float f2,
      float f3,
      int i2,
      int i3,
      long j,
      long j2,
      boolean z,
      HysteresisLevels hysteresisLevels,
      HysteresisLevels hysteresisLevels2,
      HysteresisLevels hysteresisLevels3,
      HysteresisLevels hysteresisLevels4,
      Context context,
      HighBrightnessModeController highBrightnessModeController,
      BrightnessThrottler brightnessThrottler,
      BrightnessMappingStrategy brightnessMappingStrategy2,
      int i4,
      int i5,
      float f4,
      float f5,
      HysteresisLevels hysteresisLevels5,
      HysteresisLevels hysteresisLevels6) {
    this(
        new Injector(),
        callbacks,
        looper,
        sensorManager,
        sensor,
        brightnessMappingStrategy,
        i,
        f,
        f2,
        f3,
        i2,
        i3,
        j,
        j2,
        z,
        hysteresisLevels,
        hysteresisLevels2,
        hysteresisLevels3,
        hysteresisLevels4,
        context,
        highBrightnessModeController,
        brightnessThrottler,
        brightnessMappingStrategy2,
        i4,
        i5,
        f4,
        f5,
        hysteresisLevels5,
        hysteresisLevels6);
  }

  public AutomaticBrightnessController(
      Injector injector,
      Callbacks callbacks,
      Looper looper,
      SensorManager sensorManager,
      Sensor sensor,
      BrightnessMappingStrategy brightnessMappingStrategy,
      int i,
      float f,
      float f2,
      float f3,
      int i2,
      int i3,
      long j,
      long j2,
      boolean z,
      HysteresisLevels hysteresisLevels,
      HysteresisLevels hysteresisLevels2,
      HysteresisLevels hysteresisLevels3,
      HysteresisLevels hysteresisLevels4,
      Context context,
      HighBrightnessModeController highBrightnessModeController,
      BrightnessThrottler brightnessThrottler,
      BrightnessMappingStrategy brightnessMappingStrategy2,
      int i4,
      int i5,
      float f4,
      float f5,
      HysteresisLevels hysteresisLevels5,
      HysteresisLevels hysteresisLevels6) {
    this.TAG_CAB = "[CAB] ";
    this.ACTION_CAMERA_ADAPTIVE_BRIGHTNESS = "com.samsung.adaptivebrightnessgo.action.SET_LUX";
    this.LIGHT_SENSOR_UPDATE_TIMEOUT_MILLIS = 800L;
    this.mBrightnessThresoldsType = 0;
    this.mAmbientLux = 500.0f;
    this.mLoggedAmbientLux = 500.0f;
    this.mLastHBM = false;
    this.mScreenAutoBrightness = Float.NaN;
    this.mRawScreenAutoBrightness = Float.NaN;
    this.mDisplayPolicy = 0;
    this.mState = 2;
    this.mDualScreenPolicy = -1;
    this.mDexDualViewMode = false;
    this.mShouldApplyEarlyWakeUp = false;
    this.mPendingEarlyLightSensorReadyListener = null;
    this.mIsCameraLightTypeSupported = false;
    this.mIntentReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.display.AutomaticBrightnessController.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            long uptimeMillis = SystemClock.uptimeMillis();
            float floatExtra = intent.getFloatExtra("lux", -1.0f);
            AutomaticBrightnessController.this.onCABServiceStart =
                intent.getBooleanExtra("adaptive_brightness_service_start_extra", false);
            if (floatExtra < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
              Slog.m72d(
                  "AutomaticBrightnessController",
                  "[CAB] lux value is not received lux: " + floatExtra + "  action: " + action);
              return;
            }
            if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG || Build.IS_ENG) {
              Slog.m72d(
                  "AutomaticBrightnessController",
                  "[CAB] onReceive lux: " + floatExtra + "  action: " + action);
            }
            AutomaticBrightnessController.this.handleLightSensorEvent(uptimeMillis, floatExtra);
          }
        };
    this.mProximity = -1;
    this.mPendingProximity = -1;
    this.mPowerHistorian = PowerHistorian.getInstance();
    this.mOnProximityChangedRunnable =
        new Runnable() { // from class: com.android.server.display.AutomaticBrightnessController.3
          @Override // java.lang.Runnable
          public void run() {
            if (!AutomaticBrightnessController.this.mProximitySensorEnabled
                || AutomaticBrightnessController.this.mPendingProximity == -1) {
              return;
            }
            AutomaticBrightnessController automaticBrightnessController =
                AutomaticBrightnessController.this;
            automaticBrightnessController.mProximity =
                automaticBrightnessController.mPendingProximity;
          }
        };
    this.mProximitySensorListener =
        new SensorEventListener() { // from class:
                                    // com.android.server.display.AutomaticBrightnessController.4
          @Override // android.hardware.SensorEventListener
          public void onAccuracyChanged(Sensor sensor2, int i6) {}

          @Override // android.hardware.SensorEventListener
          public void onSensorChanged(SensorEvent sensorEvent) {
            if (AutomaticBrightnessController.this.mProximitySensorEnabled) {
              long uptimeMillis = SystemClock.uptimeMillis();
              boolean z2 = false;
              float f6 = sensorEvent.values[0];
              if (AutomaticBrightnessController.this.mProximitySensor.getType() != 8
                  ? !(AutomaticBrightnessController.this.mProximitySensor.getType() != 65640
                      || f6 < 21.0f)
                  : !(f6 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON
                      || f6 >= AutomaticBrightnessController.this.mProximityThreshold)) {
                z2 = true;
              }
              Slog.m72d("AutomaticBrightnessController", "[api] onSensorChanged: proximity: " + z2);
              AutomaticBrightnessController.this.handleProximitySensorEvent(uptimeMillis, z2);
            }
          }
        };
    this.mLightSensorListener =
        new SensorEventListener() { // from class:
                                    // com.android.server.display.AutomaticBrightnessController.5
          @Override // android.hardware.SensorEventListener
          public void onAccuracyChanged(Sensor sensor2, int i6) {}

          @Override // android.hardware.SensorEventListener
          public void onSensorChanged(SensorEvent sensorEvent) {
            if (AutomaticBrightnessController.this.mLuxInjected) {
              return;
            }
            AutomaticBrightnessController.this.lambda$injectLux$0(sensorEvent);
          }
        };
    this.mInjector = injector;
    this.mClock = injector.createClock();
    this.mContext = context;
    this.mCallbacks = callbacks;
    this.mSensorManager = sensorManager;
    this.mCurrentBrightnessMapper = brightnessMappingStrategy;
    this.mScreenBrightnessRangeMinimum = f;
    this.mScreenBrightnessRangeMaximum = f2;
    this.mLightSensorWarmUpTimeConfig = i;
    this.mDozeScaleFactor = f3;
    this.mNormalLightSensorRate = i2;
    this.mInitialLightSensorRate = i3;
    this.mCurrentLightSensorRate = -1;
    this.mBrighteningLightDebounceConfig = 1000L;
    this.mDarkeningLightDebounceConfig = 2000L;
    this.mResetAmbientLuxAfterWarmUpConfig = z;
    this.mAmbientLightHorizonLong = i5;
    this.mAmbientLightHorizonShort = i4;
    this.mWeightingIntercept = i5;
    this.mAmbientBrightnessThresholds = hysteresisLevels;
    this.mAmbientBrightnessThresholdsTouchHigh = hysteresisLevels5;
    this.mAmbientBrightnessThresholdsTouchLow = hysteresisLevels6;
    this.mAmbientBrightnessThresholdsIdle = hysteresisLevels3;
    this.mScreenBrightnessThresholds = hysteresisLevels2;
    this.mScreenBrightnessThresholdsIdle = hysteresisLevels4;
    this.mShortTermModel = new ShortTermModel();
    this.mPausedShortTermModel = new ShortTermModel();
    notifyHbmInfo(false);
    this.mHandler = new AutomaticBrightnessHandler(looper);
    this.mAmbientLightRingBuffer = new AmbientLightRingBuffer(i2, i5, this.mClock);
    this.mLightSensor = sensor;
    if (sensor != null) {
      int type = sensor.getType();
      if (type == 5) {
        this.mLightSensorCct = sensorManager.getDefaultSensor(65587);
      } else if (type == 65601) {
        this.mIsUnderDisplayLightSensor = true;
      } else if (type == 65604) {
        Slog.m72d("AutomaticBrightnessController", "Using SEM_TYPE_CAMERA_LIGHT for ABC");
        this.mIsCameraLightTypeSupported = true;
      } else {
        Slog.m79w("AutomaticBrightnessController", "Not found pre-defined light sensor");
      }
    }
    if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED) {
      this.mBrighteningLightDebounceConfig = 0L;
      this.mDarkeningLightDebounceConfig = 0L;
      this.mAmbientLuxValid = true;
      this.mForceUpdateBrightness = true;
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("com.samsung.adaptivebrightnessgo.action.SET_LUX");
      this.mContext.registerReceiver(
          this.mIntentReceiver,
          intentFilter,
          "com.samsung.adaptivebrightnessgo.permission.ADAPTIVE_BRIGHTNESS_PERMISSION",
          null);
    } else if (this.mIsCameraLightTypeSupported) {
      this.mBrighteningLightDebounceConfig = 0L;
      this.mDarkeningLightDebounceConfig = 0L;
      this.mAmbientLuxValid = true;
      this.mForceUpdateBrightness = true;
    }
    if (PowerManagerUtil.SEC_FEATURE_TABLET) {
      Sensor defaultSensor = sensorManager.getDefaultSensor(8);
      this.mProximitySensor = defaultSensor;
      if (defaultSensor != null) {
        if (!defaultSensor.getName().contains("Palm")
            && !this.mProximitySensor.getName().contains("Ear")) {
          this.mUseLightSensorBlockingPrevention = true;
          this.mProximityThreshold = Math.min(this.mProximitySensor.getMaximumRange(), 5.0f);
        } else {
          this.mProximitySensor = null;
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
    this.mPackageManager = this.mContext.getPackageManager();
    this.mTaskStackListener = new TaskStackListenerImpl();
    this.mForegroundAppPackageName = null;
    this.mPendingForegroundAppPackageName = null;
    this.mForegroundAppCategory = -1;
    this.mPendingForegroundAppCategory = -1;
    this.mHbmController = highBrightnessModeController;
    this.mBrightnessThrottler = brightnessThrottler;
    this.mInteractiveModeBrightnessMapper = brightnessMappingStrategy;
    this.mIdleModeBrightnessMapper = brightnessMappingStrategy2;
    switchToInteractiveScreenBrightnessMode();
    setScreenBrightnessByUser(f4, f5);
  }

  public boolean setLoggingEnabled(boolean z) {
    if (this.mLoggingEnabled == z) {
      return false;
    }
    BrightnessMappingStrategy brightnessMappingStrategy = this.mInteractiveModeBrightnessMapper;
    if (brightnessMappingStrategy != null) {
      brightnessMappingStrategy.setLoggingEnabled(z);
    }
    BrightnessMappingStrategy brightnessMappingStrategy2 = this.mIdleModeBrightnessMapper;
    if (brightnessMappingStrategy2 != null) {
      brightnessMappingStrategy2.setLoggingEnabled(z);
    }
    this.mLoggingEnabled = z;
    return true;
  }

  public float getAutomaticScreenBrightness() {
    return getAutomaticScreenBrightness(null);
  }

  public float getAutomaticScreenBrightness(BrightnessEvent brightnessEvent) {
    if (brightnessEvent != null) {
      brightnessEvent.setLux(this.mAmbientLuxValid ? this.mAmbientLux : Float.NaN);
      brightnessEvent.setPreThresholdLux(this.mPreThresholdLux);
      brightnessEvent.setPreThresholdBrightness(this.mPreThresholdBrightness);
      brightnessEvent.setRecommendedBrightness(this.mScreenAutoBrightness);
      brightnessEvent.setFlags(
          brightnessEvent.getFlags()
              | (!this.mAmbientLuxValid ? 2 : 0)
              | (this.mDisplayPolicy == 1 ? 4 : 0)
              | (this.mCurrentBrightnessMapper.isForIdleMode() ? 16 : 0));
    }
    if (!this.mAmbientLuxValid) {
      return Float.NaN;
    }
    if (this.mDisplayPolicy == 1) {
      return this.mScreenAutoBrightness * this.mDozeScaleFactor;
    }
    return this.mScreenAutoBrightness;
  }

  public float getRawAutomaticScreenBrightness() {
    return this.mRawScreenAutoBrightness;
  }

  public boolean hasValidAmbientLux() {
    return this.mAmbientLuxValid;
  }

  public boolean isHbmLux() {
    return this.mAmbientLuxValid
        && PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
        && this.mAmbientLux >= ((float) PowerManagerUtil.HBM_LUX);
  }

  public float getAutomaticScreenBrightnessAdjustment() {
    return this.mCurrentBrightnessMapper.getAutoBrightnessAdjustment();
  }

  public void configure(
      int i,
      BrightnessConfiguration brightnessConfiguration,
      float f,
      boolean z,
      float f2,
      boolean z2,
      int i2,
      boolean z3,
      boolean z4,
      int i3) {
    boolean lightSensorEnabledForCAB;
    this.mState = i;
    boolean z5 = true;
    boolean z6 = i2 == 1;
    boolean brightnessConfiguration2 =
        setBrightnessConfiguration(brightnessConfiguration, z3, z4) | setDisplayPolicy(i2);
    if (z2) {
      brightnessConfiguration2 |= setAutoBrightnessAdjustment(f2);
    }
    boolean z7 = this.mState == 1;
    if (z && z7) {
      brightnessConfiguration2 |= setScreenBrightnessByUser(f);
    }
    boolean z8 = z || z2;
    if (z8 && z7 && !z6) {
      prepareBrightnessAdjustmentSample();
    }
    if (this.mDualScreenPolicy != i3) {
      this.mDualScreenPolicy = i3;
      brightnessConfiguration2 = true;
    }
    this.mDexDualViewMode = false;
    if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED || this.mIsCameraLightTypeSupported) {
      lightSensorEnabledForCAB =
          brightnessConfiguration2
              | setLightSensorEnabledForCAB(z7 || this.mShouldApplyEarlyWakeUp);
    } else {
      lightSensorEnabledForCAB =
          brightnessConfiguration2 | setLightSensorEnabled(z7 || this.mShouldApplyEarlyWakeUp);
      if (this.mUseLightSensorBlockingPrevention) {
        setProximitySensorEnabled(this.mLightSensorEnabled);
      }
    }
    if (this.mIsBrightnessThrottled != this.mBrightnessThrottler.isThrottled()) {
      this.mIsBrightnessThrottled = this.mBrightnessThrottler.isThrottled();
    } else {
      z5 = lightSensorEnabledForCAB;
    }
    Runnable runnable = this.mPendingEarlyLightSensorReadyListener;
    if (runnable != null) {
      runnable.run();
      this.mPendingEarlyLightSensorReadyListener = null;
    }
    if (z5) {
      updateAutoBrightness(false, z8);
    }
  }

  public void stop() {
    setLightSensorEnabled(false);
  }

  public boolean hasUserDataPoints() {
    return this.mCurrentBrightnessMapper.hasUserDataPoints();
  }

  public boolean isDefaultConfig() {
    if (isInIdleMode()) {
      return false;
    }
    return this.mInteractiveModeBrightnessMapper.isDefaultConfig();
  }

  public BrightnessConfiguration getDefaultConfig() {
    return this.mInteractiveModeBrightnessMapper.getDefaultConfig();
  }

  public void update() {
    this.mHandler.sendEmptyMessage(6);
  }

  public float getAmbientLux() {
    return this.mAmbientLux;
  }

  public final boolean setDisplayPolicy(int i) {
    int i2 = this.mDisplayPolicy;
    if (i2 == i) {
      return false;
    }
    this.mDisplayPolicy = i;
    if (this.mLoggingEnabled) {
      Slog.m72d(
          "AutomaticBrightnessController", "Display policy transitioning from " + i2 + " to " + i);
    }
    if (!isInteractivePolicy(i) && isInteractivePolicy(i2) && !isInIdleMode()) {
      this.mNonInteractiveTime = SystemClock.elapsedRealtime();
    } else if (isInteractivePolicy(i) && !isInteractivePolicy(i2)) {
      if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED
          || this.mIsCameraLightTypeSupported) {
        this.mForceUpdateBrightness = true;
      }
      if (getDefaultConfig() != null
          && SystemClock.elapsedRealtime() - this.mNonInteractiveTime
              > this.mInteractiveModeBrightnessMapper.getShortTermModelTimeout()) {
        this.mShortTermModel.invalidate();
      }
    }
    return true;
  }

  public final boolean setScreenBrightnessByUser(float f) {
    if (!this.mAmbientLuxValid) {
      return false;
    }
    float calculateAmbientLux = calculateAmbientLux(SystemClock.uptimeMillis(), 600L);
    if (!isValidUserLux(calculateAmbientLux)) {
      if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
          && this.mAmbientLux >= PowerManagerUtil.HBM_LUX) {
        Slog.m72d(
            "AutomaticBrightnessController",
            "setScreenBrightnessByUser: cancel currentLux: " + calculateAmbientLux);
        return false;
      }
    } else {
      setAmbientLux(calculateAmbientLux);
    }
    return setScreenBrightnessByUser(this.mAmbientLux, f);
  }

  public final boolean setScreenBrightnessByUser(float f, float f2) {
    if (f == -1.0f || f2 == -1.0f) {
      return false;
    }
    this.mCurrentBrightnessMapper.addUserDataPoint(f, f2);
    this.mShortTermModel.setUserBrightness(f, f2);
    if (this.mIsUnderDisplayLightSensor) {
      this.mSetScreenBrightnessByUserTime = SystemClock.uptimeMillis();
      Slog.m72d("AutomaticBrightnessController", "change BrighteningLightDebounceConfig");
    }
    if (!PowerManagerUtil.SEC_FEATURE_HQM_SEND_DPUC) {
      return true;
    }
    this.mCallbacks.onUserPointAdded(this.mAmbientLux, f2);
    return true;
  }

  public void resetShortTermModel() {
    Slog.m72d("AutomaticBrightnessController", "resetShortTermModel");
    this.mCurrentBrightnessMapper.clearUserDataPoints();
    this.mShortTermModel.reset();
    this.mCallbacks.onShortTermReset();
  }

  public boolean setBrightnessConfiguration(
      BrightnessConfiguration brightnessConfiguration, boolean z, boolean z2) {
    if (!this.mInteractiveModeBrightnessMapper.setBrightnessConfiguration(brightnessConfiguration)
        && !z2) {
      return false;
    }
    this.mPowerHistorian.onAutoBrightnessEvent("ShortTermModel: reset data, change configuration");
    if (isInIdleMode() || !z) {
      return true;
    }
    if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
      this.mInteractiveModeBrightnessMapper.clearUserDataPoints();
      this.mShortTermModel.reset();
      return true;
    }
    resetShortTermModel();
    return true;
  }

  public boolean isInIdleMode() {
    return this.mCurrentBrightnessMapper.isForIdleMode();
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println();
    printWriter.println("Automatic Brightness Controller Configuration:");
    printWriter.println(
        "  mScreenBrightnessRangeMinimum="
            + PowerManagerUtil.brightnessToString(this.mScreenBrightnessRangeMinimum));
    printWriter.println(
        "  mScreenBrightnessRangeMaximum="
            + PowerManagerUtil.brightnessToString(this.mScreenBrightnessRangeMaximum));
    printWriter.println("  mState=" + configStateToString(this.mState));
    printWriter.println("  mScreenBrightnessRangeMinimum=" + this.mScreenBrightnessRangeMinimum);
    printWriter.println("  mScreenBrightnessRangeMaximum=" + this.mScreenBrightnessRangeMaximum);
    printWriter.println("  mDozeScaleFactor=" + this.mDozeScaleFactor);
    printWriter.println("  mInitialLightSensorRate=" + this.mInitialLightSensorRate);
    printWriter.println("  mNormalLightSensorRate=" + this.mNormalLightSensorRate);
    printWriter.println("  mLightSensorWarmUpTimeConfig=" + this.mLightSensorWarmUpTimeConfig);
    printWriter.println(
        "  mBrighteningLightDebounceConfig=" + this.mBrighteningLightDebounceConfig);
    printWriter.println("  mDarkeningLightDebounceConfig=" + this.mDarkeningLightDebounceConfig);
    printWriter.println(
        "  mResetAmbientLuxAfterWarmUpConfig=" + this.mResetAmbientLuxAfterWarmUpConfig);
    printWriter.println("  mAmbientLightHorizonLong=" + this.mAmbientLightHorizonLong);
    printWriter.println("  mAmbientLightHorizonShort=" + this.mAmbientLightHorizonShort);
    printWriter.println("  mWeightingIntercept=" + this.mWeightingIntercept);
    printWriter.println("  SEC_FEATURE_SUPPORT_HBM=" + PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM);
    printWriter.println("  HBM_LUX=" + PowerManagerUtil.HBM_LUX);
    printWriter.println();
    printWriter.println("Automatic Brightness Controller State:");
    printWriter.println("  mLightSensor=" + this.mLightSensor);
    printWriter.println("  mLightSensorCct=" + this.mLightSensorCct);
    printWriter.println("  mLightSensorEnabled=" + this.mLightSensorEnabled);
    printWriter.println(
        "  mLightSensorEnableTime=" + TimeUtils.formatUptime(this.mLightSensorEnableTime));
    printWriter.println("  mCurrentLightSensorRate=" + this.mCurrentLightSensorRate);
    printWriter.println("  mAmbientLux=" + this.mAmbientLux);
    printWriter.println("  mAmbientLuxValid=" + this.mAmbientLuxValid);
    printWriter.println("  mLuxInjected=" + this.mLuxInjected);
    printWriter.println("  mPreThesholdLux=" + this.mPreThresholdLux);
    printWriter.println("  mPreThesholdBrightness=" + this.mPreThresholdBrightness);
    printWriter.println("  mAmbientBrighteningThreshold=" + this.mAmbientBrighteningThreshold);
    printWriter.println("  mAmbientDarkeningThreshold=" + this.mAmbientDarkeningThreshold);
    printWriter.println("  mScreenBrighteningThreshold=" + this.mScreenBrighteningThreshold);
    printWriter.println("  mScreenDarkeningThreshold=" + this.mScreenDarkeningThreshold);
    printWriter.println("  mLastObservedLux=" + this.mLastObservedLux);
    printWriter.println(
        "  mLastObservedLuxTime=" + TimeUtils.formatUptime(this.mLastObservedLuxTime));
    printWriter.println("  mRecentLightSamples=" + this.mRecentLightSamples);
    printWriter.println("  mAmbientLightRingBuffer=" + this.mAmbientLightRingBuffer);
    printWriter.println("  mScreenAutoBrightness=" + this.mScreenAutoBrightness);
    printWriter.println(
        "  mDisplayPolicy="
            + DisplayManagerInternal.DisplayPowerRequest.policyToString(this.mDisplayPolicy));
    printWriter.println(
        "  mShortTermModelTimeout(active)="
            + this.mInteractiveModeBrightnessMapper.getShortTermModelTimeout());
    if (this.mIdleModeBrightnessMapper != null) {
      printWriter.println(
          "  mShortTermModelTimeout(idle)="
              + this.mIdleModeBrightnessMapper.getShortTermModelTimeout());
    }
    printWriter.println("  mShortTermModel=");
    this.mShortTermModel.dump(printWriter);
    printWriter.println("  mPausedShortTermModel=");
    this.mPausedShortTermModel.dump(printWriter);
    printWriter.println();
    printWriter.println(
        "  mBrightnessAdjustmentSamplePending=" + this.mBrightnessAdjustmentSamplePending);
    printWriter.println(
        "  mBrightnessAdjustmentSampleOldLux=" + this.mBrightnessAdjustmentSampleOldLux);
    printWriter.println(
        "  mBrightnessAdjustmentSampleOldBrightness="
            + this.mBrightnessAdjustmentSampleOldBrightness);
    printWriter.println("  mForegroundAppPackageName=" + this.mForegroundAppPackageName);
    printWriter.println(
        "  mPendingForegroundAppPackageName=" + this.mPendingForegroundAppPackageName);
    printWriter.println("  mForegroundAppCategory=" + this.mForegroundAppCategory);
    printWriter.println("  mPendingForegroundAppCategory=" + this.mPendingForegroundAppCategory);
    printWriter.println("  Idle mode active=" + this.mCurrentBrightnessMapper.isForIdleMode());
    printWriter.println(
        "  mUseLightSensorBlockingPrevention=" + this.mUseLightSensorBlockingPrevention);
    printWriter.println("  mProximity=" + this.mProximity);
    boolean z = PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI;
    if (z) {
      printWriter.println("  mIsRearLightSensor=" + this.mIsRearLightSensor);
      printWriter.println(
          "  mLastAmbientLuxOfFrontLightSensor=" + this.mLastAmbientLuxOfFrontLightSensor);
    }
    printWriter.println();
    printWriter.println("  mInteractiveMapper=");
    this.mInteractiveModeBrightnessMapper.dump(
        printWriter, this.mHbmController.getNormalBrightnessMax());
    if (this.mIdleModeBrightnessMapper != null) {
      printWriter.println("  mIdleMapper=");
      this.mIdleModeBrightnessMapper.dump(
          printWriter, this.mHbmController.getNormalBrightnessMax());
    }
    printWriter.println();
    printWriter.println("  mAmbientBrightnessThresholds=");
    this.mAmbientBrightnessThresholds.dump(printWriter);
    if (z) {
      printWriter.println("mAmbientBrightnessThresholdsTouchHigh:");
      this.mAmbientBrightnessThresholdsTouchHigh.dump(printWriter);
      printWriter.println("mAmbientBrightnessThresholdsTouchLow:");
      this.mAmbientBrightnessThresholdsTouchLow.dump(printWriter);
    }
    printWriter.println("  mAmbientBrightnessThresholdsIdle=");
    this.mAmbientBrightnessThresholdsIdle.dump(printWriter);
  }

  public float[] getLastSensorValues() {
    return this.mAmbientLightRingBuffer.getAllLuxValues();
  }

  public long[] getLastSensorTimestamps() {
    return this.mAmbientLightRingBuffer.getAllTimestamps();
  }

  public final String configStateToString(int i) {
    return i != 1
        ? i != 2
            ? i != 3 ? String.valueOf(i) : "AUTO_BRIGHTNESS_OFF_DUE_TO_DISPLAY_STATE"
            : "AUTO_BRIGHTNESS_DISABLED"
        : "AUTO_BRIGHTNESS_ENABLED";
  }

  public final boolean setLightSensorEnabled(boolean z) {
    if (z) {
      if (!this.mLightSensorEnabled) {
        this.mLightSensorEnabled = true;
        this.mLightSensorEnableTime = this.mClock.uptimeMillis();
        this.mCurrentLightSensorRate = this.mInitialLightSensorRate;
        Slog.m72d("AutomaticBrightnessController", "setLightSensorEnabled: true");
        Sensor sensor = this.mLightSensorCct;
        if (sensor == null) {
          sensor = this.mLightSensor;
        }
        this.mSensorManager.registerListener(
            this.mLightSensorListener, sensor, this.mCurrentLightSensorRate * 1000, this.mHandler);
        if (this.mDexDualViewMode && PowerManagerUtil.Country.CHINA) {
          handleLightSensorEvent(SystemClock.uptimeMillis(), this.mAmbientLux);
        }
        this.mHandler.sendEmptyMessageDelayed(8, 800L);
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
      this.mAmbientLightRingBuffer.clear();
      this.mCurrentLightSensorRate = -1;
      sendAmbientLux(-1.0f);
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(8);
      Slog.m72d("AutomaticBrightnessController", "setLightSensorEnabled: false");
      this.mIsRearLightSensor = false;
      this.mIsLightSensorPositionUpdated = false;
      this.mBrightnessThresoldsType = 0;
      this.mSensorManager.unregisterListener(this.mLightSensorListener);
    }
    return false;
  }

  public final void handleLightSensorEvent(long j, float f) {
    Trace.traceCounter(131072L, "ALS", (int) f);
    this.mHandler.removeMessages(1);
    if (this.mAmbientLightRingBuffer.size() == 0 && !this.mIsUnderDisplayLightSensor) {
      adjustLightSensorRate(this.mNormalLightSensorRate);
    }
    applyLightSensorMeasurement(j, f);
    if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED || this.mIsCameraLightTypeSupported) {
      updateAmbientLuxCAB(j);
    } else {
      updateAmbientLux(j);
    }
  }

  public final void applyLightSensorMeasurement(long j, float f) {
    this.mRecentLightSamples++;
    this.mAmbientLightRingBuffer.prune(j - this.mAmbientLightHorizonLong);
    this.mAmbientLightRingBuffer.push(j, f);
    this.mLastObservedLux = f;
    this.mLastObservedLuxTime = j;
  }

  public final void adjustLightSensorRate(int i) {
    if (i != this.mCurrentLightSensorRate) {
      if (this.mLoggingEnabled) {
        Slog.m72d(
            "AutomaticBrightnessController",
            "adjustLightSensorRate: previousRate="
                + this.mCurrentLightSensorRate
                + ", currentRate="
                + i);
      }
      this.mCurrentLightSensorRate = i;
      this.mSensorManager.unregisterListener(this.mLightSensorListener);
      this.mSensorManager.registerListener(
          this.mLightSensorListener, this.mLightSensor, i * 1000, this.mHandler);
    }
  }

  public final boolean setAutoBrightnessAdjustment(float f) {
    return this.mCurrentBrightnessMapper.setAutoBrightnessAdjustment(f);
  }

  public final void setAmbientLux(float f) {
    if (this.mLoggingEnabled) {
      Slog.m72d("AutomaticBrightnessController", "setAmbientLux(" + f + ")");
    }
    if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      Slog.m79w(
          "AutomaticBrightnessController", "Ambient lux was negative, ignoring and setting to 0");
      f = 0.0f;
    }
    sendAmbientLux(f);
    this.mAmbientLux = f;
    HysteresisLevels ambientBrightnessThresholds = getAmbientBrightnessThresholds();
    this.mAmbientBrighteningThreshold = ambientBrightnessThresholds.getBrighteningThreshold(f);
    this.mAmbientDarkeningThreshold = ambientBrightnessThresholds.getDarkeningThreshold(f);
    this.mHbmController.onAmbientLuxChange(this.mAmbientLux);
    if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
      this.mCallbacks.onAmbientLuxChanged(f);
    }
    this.mShortTermModel.maybeReset(this.mAmbientLux);
  }

  public final float calculateAmbientLux(long j, long j2) {
    int i;
    long j3;
    long j4 = j;
    if (this.mLoggingEnabled) {
      Slog.m72d("AutomaticBrightnessController", "calculateAmbientLux(" + j4 + ", " + j2 + ")");
    }
    int size = this.mAmbientLightRingBuffer.size();
    if (size == 0) {
      Slog.m74e(
          "AutomaticBrightnessController",
          "calculateAmbientLux: No ambient light readings available");
      return -1.0f;
    }
    if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED || this.mIsCameraLightTypeSupported) {
      return this.mAmbientLightRingBuffer.getLux(size - 1);
    }
    long j5 = j4 - j2;
    int i2 = 0;
    int i3 = 0;
    while (true) {
      i = size - 1;
      if (i2 >= i) {
        break;
      }
      i2++;
      if (this.mAmbientLightRingBuffer.getTime(i2) > j5) {
        break;
      }
      i3++;
    }
    if (this.mLoggingEnabled) {
      Slog.m72d(
          "AutomaticBrightnessController",
          "calculateAmbientLux: selected endIndex="
              + i3
              + ", point=("
              + this.mAmbientLightRingBuffer.getTime(i3)
              + ", "
              + this.mAmbientLightRingBuffer.getLux(i3)
              + ")");
    }
    float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    long j6 = 100;
    float f2 = 0.0f;
    while (i >= i3) {
      long time = this.mAmbientLightRingBuffer.getTime(i);
      if (i == i3 && time < j5) {
        time = j5;
      }
      long j7 = time - j4;
      float calculateWeight = calculateWeight(j7, j6);
      float lux = this.mAmbientLightRingBuffer.getLux(i);
      if (this.mLoggingEnabled) {
        StringBuilder sb = new StringBuilder();
        j3 = j5;
        sb.append("calculateAmbientLux: [");
        sb.append(j7);
        sb.append(", ");
        sb.append(j6);
        sb.append("]: lux=");
        sb.append(lux);
        sb.append(", weight=");
        sb.append(calculateWeight);
        Slog.m72d("AutomaticBrightnessController", sb.toString());
      } else {
        j3 = j5;
      }
      f += calculateWeight;
      f2 += lux * calculateWeight;
      i--;
      j4 = j;
      j5 = j3;
      j6 = j7;
    }
    if (this.mLoggingEnabled) {
      Slog.m72d(
          "AutomaticBrightnessController",
          "calculateAmbientLux: totalWeight=" + f + ", newAmbientLux=" + (f2 / f));
    }
    return f2 / f;
  }

  public final float calculateWeight(long j, long j2) {
    return weightIntegral(j2) - weightIntegral(j);
  }

  public final float weightIntegral(long j) {
    float f = j;
    return f * ((0.5f * f) + this.mWeightingIntercept);
  }

  public final long nextAmbientLightBrighteningTransition(long j) {
    for (int size = this.mAmbientLightRingBuffer.size() - 1;
        size >= 0 && this.mAmbientLightRingBuffer.getLux(size) >= this.mAmbientBrighteningThreshold;
        size--) {
      j = this.mAmbientLightRingBuffer.getTime(size);
    }
    return j + this.mBrighteningLightDebounceConfig;
  }

  public final long nextAmbientLightDarkeningTransition(long j) {
    for (int size = this.mAmbientLightRingBuffer.size() - 1;
        size >= 0 && this.mAmbientLightRingBuffer.getLux(size) <= this.mAmbientDarkeningThreshold;
        size--) {
      j = this.mAmbientLightRingBuffer.getTime(size);
    }
    return j + this.mDarkeningLightDebounceConfig;
  }

  public final void updateAmbientLux() {
    long uptimeMillis = this.mClock.uptimeMillis();
    this.mAmbientLightRingBuffer.prune(uptimeMillis - this.mAmbientLightHorizonLong);
    updateAmbientLux(uptimeMillis);
  }

  /* JADX WARN: Code restructure failed: missing block: B:59:0x01f2, code lost:

     if (r17.mIsRearLightSensor == false) goto L81;
  */
  /* JADX WARN: Code restructure failed: missing block: B:89:0x0221, code lost:

     if (r8 == false) goto L96;
  */
  /* JADX WARN: Code restructure failed: missing block: B:90:0x0223, code lost:

     if (r3 == false) goto L104;
  */
  /* JADX WARN: Removed duplicated region for block: B:85:0x0217  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void updateAmbientLux(long j) {
    boolean z;
    boolean z2;
    float f;
    float f2;
    float f3;
    int i;
    boolean z3 = false;
    if (!this.mAmbientLuxValid || this.mShouldApplyEarlyWakeUp) {
      long j2 = this.mLightSensorWarmUpTimeConfig + this.mLightSensorEnableTime;
      if (j < j2) {
        if (this.mLoggingEnabled) {
          Slog.m72d(
              "AutomaticBrightnessController",
              "updateAmbientLux: Sensor not ready yet: time="
                  + j
                  + ", timeWhenSensorWarmedUp="
                  + j2);
        }
        this.mHandler.sendEmptyMessageAtTime(1, j2);
        return;
      }
      setAmbientLux(calculateAmbientLux(j, this.mAmbientLightHorizonShort));
      this.mHandler.removeMessages(8);
      this.mAmbientLuxValid = true;
      if (this.mLoggingEnabled) {
        Slog.m72d(
            "AutomaticBrightnessController",
            "updateAmbientLux: Initializing: mAmbientLightRingBuffer="
                + this.mAmbientLightRingBuffer
                + ", mAmbientLux="
                + this.mAmbientLux);
      }
      updateAutoBrightness(true, false);
    }
    if (this.mMinLuxUpdated) {
      this.mMinLuxUpdated = false;
      if (j < this.mSetScreenBrightnessByUserTime + 2500 || this.mDisplayPolicy == 2) {
        Slog.m72d("AutomaticBrightnessController", "updateAmbientLux: min lux is ignored");
        return;
      }
      z = true;
    } else {
      z = false;
    }
    if (this.mUseLightSensorBlockingPrevention
        && this.mProximitySensor.getType() == 65640
        && this.mIsLightSensorPositionUpdated) {
      long j3 = this.mLightSensorPositionUpdatedTime + 300;
      if (j < j3) {
        this.mHandler.sendEmptyMessageAtTime(1, j3);
        return;
      }
    }
    long nextAmbientLightBrighteningTransition = nextAmbientLightBrighteningTransition(j);
    long nextAmbientLightDarkeningTransition = nextAmbientLightDarkeningTransition(j);
    this.mSlowAmbientLux = calculateAmbientLux(j, this.mAmbientLightHorizonLong);
    this.mFastAmbientLux = calculateAmbientLux(j, this.mAmbientLightHorizonShort);
    boolean z4 = j < this.mSetScreenBrightnessByUserTime + 2500;
    if (this.mUseLightSensorBlockingPrevention) {
      if (this.mProximitySensor.getType() == 65640) {
        if (this.mIsLightSensorPositionUpdated) {
          this.mSlowAmbientLux = calculateAmbientLux(j, 0L);
          this.mFastAmbientLux = calculateAmbientLux(j, 0L);
          if (this.mIsRearLightSensor) {
            this.mLastAmbientLuxOfFrontLightSensor = this.mAmbientLux;
            Slog.m72d(
                "AutomaticBrightnessController",
                "Last front lux: "
                    + this.mLastAmbientLuxOfFrontLightSensor
                    + ", Current rear lux: "
                    + this.mFastAmbientLux);
            i =
                (Math.max(this.mLastAmbientLuxOfFrontLightSensor, 1.0f)
                                / Math.max(this.mFastAmbientLux, 1.0f)
                            > 5.0f
                        || this.mFastAmbientLux < 50.0f)
                    ? 2
                    : 1;
          } else {
            i = 0;
          }
          if (this.mBrightnessThresoldsType != i) {
            this.mBrightnessThresoldsType = i;
            float f4 = this.mFastAmbientLux;
            if ((f4 <= this.mAmbientDarkeningThreshold && !this.mIsRearLightSensor)
                || f4 >= this.mAmbientBrighteningThreshold) {
              z = true;
            }
            setAmbientLux(this.mAmbientLux);
            StringBuilder sb = new StringBuilder();
            sb.append("updateAmbientLux(");
            sb.append(this.mIsRearLightSensor ? "Rear" : "Front");
            sb.append(", ");
            sb.append(getAmbientBrightnessThresholdsString());
            sb.append("): ");
            sb.append(String.format("%6.0f", Float.valueOf(this.mAmbientDarkeningThreshold)));
            sb.append(" < ");
            sb.append(String.format(Locale.US, "%8.1f", Float.valueOf(this.mAmbientLux)));
            sb.append(" < ");
            sb.append(String.format("%6.0f", Float.valueOf(this.mAmbientBrighteningThreshold)));
            Slog.m72d("AutomaticBrightnessController", "[api] " + sb.toString());
            z3 = false;
          }
          this.mIsLightSensorPositionUpdated = z3;
        }
        z2 = true;
        if (this.mProximity == 1) {}
      } else if (this.mProximity == 1) {
        z2 = true;
      }
      f = this.mSlowAmbientLux;
      f2 = this.mAmbientBrighteningThreshold;
      if (f >= f2 || this.mFastAmbientLux < f2 || nextAmbientLightBrighteningTransition > j || z4) {
        f3 = this.mAmbientDarkeningThreshold;
        if (f <= f3) {
          if (this.mFastAmbientLux <= f3) {
            if (nextAmbientLightDarkeningTransition <= j) {}
          }
        }
      }
      this.mPreThresholdLux = this.mAmbientLux;
      setAmbientLux(this.mFastAmbientLux);
      if (this.mLoggingEnabled) {
        StringBuilder sb2 = new StringBuilder();
        sb2.append("updateAmbientLux: ");
        sb2.append(this.mFastAmbientLux > this.mAmbientLux ? "Brightened" : "Darkened");
        sb2.append(": mAmbientBrighteningThreshold=");
        sb2.append(this.mAmbientBrighteningThreshold);
        sb2.append(", mAmbientDarkeningThreshold=");
        sb2.append(this.mAmbientDarkeningThreshold);
        sb2.append(", mAmbientLightRingBuffer=");
        sb2.append(this.mAmbientLightRingBuffer);
        sb2.append(", mAmbientLux=");
        sb2.append(this.mAmbientLux);
        Slog.m72d("AutomaticBrightnessController", sb2.toString());
      }
      updateAutoBrightness(true, false);
      nextAmbientLightBrighteningTransition = nextAmbientLightBrighteningTransition(j);
      nextAmbientLightDarkeningTransition = nextAmbientLightDarkeningTransition(j);
      long min =
          Math.min(nextAmbientLightDarkeningTransition, nextAmbientLightBrighteningTransition);
      if (min <= j) {
        min = this.mNormalLightSensorRate + j;
      }
      if (this.mLoggingEnabled) {
        Slog.m72d(
            "AutomaticBrightnessController",
            "updateAmbientLux: Scheduling ambient lux update for "
                + min
                + TimeUtils.formatUptime(min));
      }
      this.mHandler.sendEmptyMessageAtTime(1, min);
      return;
    }
    z2 = false;
    f = this.mSlowAmbientLux;
    f2 = this.mAmbientBrighteningThreshold;
    if (f >= f2) {}
    f3 = this.mAmbientDarkeningThreshold;
    if (f <= f3) {}
  }

  public final void updateAutoBrightness(boolean z, boolean z2) {
    if (this.mAmbientLuxValid) {
      float brightness =
          this.mCurrentBrightnessMapper.getBrightness(
              this.mAmbientLux, this.mForegroundAppPackageName, this.mForegroundAppCategory);
      this.mRawScreenAutoBrightness = brightness;
      float brightnessIntToFloat =
          BrightnessSynchronizer.brightnessIntToFloat(
              BrightnessSynchronizer.brightnessFloatToInt(clampScreenBrightness(brightness)));
      float f = this.mScreenAutoBrightness;
      BrightnessSynchronizer.floatEquals(f, clampScreenBrightness(f));
      if (this.mScreenAutoBrightness != brightnessIntToFloat
          || this.mAmbientLux != this.mLoggedAmbientLux) {
        StringBuilder sb = new StringBuilder();
        sb.append("updateAutoBrightness : ");
        sb.append(
            String.format(
                "%3d",
                Integer.valueOf(
                    BrightnessSynchronizer.brightnessFloatToInt(brightnessIntToFloat))));
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
                locale, "%+.1f", Float.valueOf(getAutomaticScreenBrightnessAdjustment())));
        sb.append(")");
        sb.append(Float.isNaN(this.mScreenAutoBrightness) ? " (1st)" : "");
        String sb2 = sb.toString();
        Slog.m72d("AutomaticBrightnessController", "[api] " + sb2);
        this.mPowerHistorian.onAutoBrightnessEvent(sb2);
        float f2 = this.mAmbientLux;
        this.mLoggedAmbientLux = f2;
        if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM) {
          boolean z3 = f2 >= ((float) PowerManagerUtil.HBM_LUX);
          if (this.mLastHBM != z3) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("HBM is ");
            sb3.append(z3 ? "Enabled" : "Disabled");
            sb3.append(". mScreenAutoBrightness = ");
            sb3.append(PowerManagerUtil.brightnessToString(brightnessIntToFloat));
            Slog.m72d("AutomaticBrightnessController", sb3.toString());
            this.mLastHBM = z3;
            notifyHbmInfo(z3);
          }
        }
      }
      if (BrightnessSynchronizer.floatEquals(this.mScreenAutoBrightness, brightnessIntToFloat)) {
        return;
      }
      if (this.mLoggingEnabled) {
        Slog.m72d(
            "AutomaticBrightnessController",
            "updateAutoBrightness: mScreenAutoBrightness="
                + this.mScreenAutoBrightness
                + ", newScreenAutoBrightness="
                + brightnessIntToFloat);
      }
      this.mScreenAutoBrightness = brightnessIntToFloat;
      if (this.mIsUnderDisplayLightSensor) {
        sendAmbientBrightnessThresholdInfo();
      }
      if (z) {
        this.mCallbacks.updateBrightness();
      }
    }
  }

  public final float clampScreenBrightness(float f) {
    return MathUtils.constrain(
        f,
        this.mHbmController.getCurrentBrightnessMin(),
        this.mHbmController.getCurrentBrightnessMax());
  }

  public final void prepareBrightnessAdjustmentSample() {
    if (!this.mBrightnessAdjustmentSamplePending) {
      this.mBrightnessAdjustmentSamplePending = true;
      this.mBrightnessAdjustmentSampleOldLux = this.mAmbientLuxValid ? this.mAmbientLux : -1.0f;
      this.mBrightnessAdjustmentSampleOldBrightness = this.mScreenAutoBrightness;
    } else {
      this.mHandler.removeMessages(2);
    }
    this.mHandler.sendEmptyMessageDelayed(2, 10000L);
  }

  public final void collectBrightnessAdjustmentSample() {
    if (this.mBrightnessAdjustmentSamplePending) {
      this.mBrightnessAdjustmentSamplePending = false;
      if (this.mAmbientLuxValid) {
        float f = this.mScreenAutoBrightness;
        if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON
            || f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          if (this.mLoggingEnabled) {
            Slog.m72d(
                "AutomaticBrightnessController",
                "Auto-brightness adjustment changed by user: lux="
                    + this.mAmbientLux
                    + ", brightness="
                    + this.mScreenAutoBrightness
                    + ", ring="
                    + this.mAmbientLightRingBuffer);
          }
          EventLog.writeEvent(
              35000,
              Float.valueOf(this.mBrightnessAdjustmentSampleOldLux),
              Float.valueOf(this.mBrightnessAdjustmentSampleOldBrightness),
              Float.valueOf(this.mAmbientLux),
              Float.valueOf(this.mScreenAutoBrightness));
        }
      }
    }
  }

  public final void updateForegroundApp() {
    if (this.mLoggingEnabled) {
      Slog.m72d("AutomaticBrightnessController", "Attempting to update foreground app");
    }
    this.mInjector
        .getBackgroundThreadHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.display.AutomaticBrightnessController.2
              @Override // java.lang.Runnable
              public void run() {
                ComponentName componentName;
                try {
                  ActivityTaskManager.RootTaskInfo focusedRootTaskInfo =
                      AutomaticBrightnessController.this.mActivityTaskManager
                          .getFocusedRootTaskInfo();
                  if (focusedRootTaskInfo != null
                      && (componentName = focusedRootTaskInfo.topActivity) != null) {
                    String packageName = componentName.getPackageName();
                    String str = AutomaticBrightnessController.this.mForegroundAppPackageName;
                    if (str == null || !str.equals(packageName)) {
                      AutomaticBrightnessController.this.mPendingForegroundAppPackageName =
                          packageName;
                      AutomaticBrightnessController.this.mPendingForegroundAppCategory = -1;
                      try {
                        AutomaticBrightnessController.this.mPendingForegroundAppCategory =
                            AutomaticBrightnessController.this.mPackageManager.getApplicationInfo(
                                    packageName, 4194304)
                                .category;
                      } catch (PackageManager.NameNotFoundException unused) {
                      }
                      AutomaticBrightnessController.this.mHandler.sendEmptyMessage(5);
                    }
                  }
                } catch (RemoteException unused2) {
                }
              }
            });
  }

  public final void updateForegroundAppSync() {
    if (this.mLoggingEnabled) {
      Slog.m72d(
          "AutomaticBrightnessController",
          "Updating foreground app: packageName="
              + this.mPendingForegroundAppPackageName
              + ", category="
              + this.mPendingForegroundAppCategory);
    }
    this.mForegroundAppPackageName = this.mPendingForegroundAppPackageName;
    this.mPendingForegroundAppPackageName = null;
    this.mForegroundAppCategory = this.mPendingForegroundAppCategory;
    this.mPendingForegroundAppCategory = -1;
    updateAutoBrightness(true, false);
  }

  public void switchToIdleMode() {
    if (this.mIdleModeBrightnessMapper == null || this.mCurrentBrightnessMapper.isForIdleMode()) {
      return;
    }
    Slog.m76i("AutomaticBrightnessController", "Switching to Idle Screen Brightness Mode");
    ShortTermModel shortTermModel = new ShortTermModel();
    shortTermModel.set(
        this.mCurrentBrightnessMapper.getUserLux(),
        this.mCurrentBrightnessMapper.getUserBrightness(),
        true);
    this.mHandler.sendEmptyMessageAtTime(
        7, this.mClock.uptimeMillis() + this.mCurrentBrightnessMapper.getShortTermModelTimeout());
    Slog.m76i(
        "AutomaticBrightnessController", "mPreviousShortTermModel" + this.mPausedShortTermModel);
    this.mCurrentBrightnessMapper = this.mIdleModeBrightnessMapper;
    ShortTermModel shortTermModel2 = this.mPausedShortTermModel;
    if (shortTermModel2 != null && !shortTermModel2.maybeReset(this.mAmbientLux)) {
      setScreenBrightnessByUser(
          this.mPausedShortTermModel.mAnchor, this.mPausedShortTermModel.mBrightness);
    }
    this.mPausedShortTermModel.copyFrom(shortTermModel);
    update();
  }

  public void switchToInteractiveScreenBrightnessMode() {
    if (this.mCurrentBrightnessMapper.isForIdleMode()) {
      Slog.m76i("AutomaticBrightnessController", "Switching to Interactive Screen Brightness Mode");
      ShortTermModel shortTermModel = new ShortTermModel();
      shortTermModel.set(
          this.mCurrentBrightnessMapper.getUserLux(),
          this.mCurrentBrightnessMapper.getUserBrightness(),
          true);
      this.mHandler.removeMessages(7);
      this.mHandler.sendEmptyMessageAtTime(
          7, this.mClock.uptimeMillis() + this.mCurrentBrightnessMapper.getShortTermModelTimeout());
      Slog.m76i(
          "AutomaticBrightnessController",
          "mPreviousShortTermModel" + this.mPausedShortTermModel.toString());
      this.mCurrentBrightnessMapper = this.mInteractiveModeBrightnessMapper;
      if (!this.mPausedShortTermModel.maybeReset(this.mAmbientLux)) {
        setScreenBrightnessByUser(
            this.mPausedShortTermModel.mAnchor, this.mPausedShortTermModel.mBrightness);
      }
      this.mPausedShortTermModel.copyFrom(shortTermModel);
      update();
    }
  }

  public float convertToNits(float f) {
    BrightnessMappingStrategy brightnessMappingStrategy = this.mCurrentBrightnessMapper;
    if (brightnessMappingStrategy != null) {
      return brightnessMappingStrategy.convertToNits(f);
    }
    return -1.0f;
  }

  public float convertToAdjustedNits(float f) {
    BrightnessMappingStrategy brightnessMappingStrategy = this.mCurrentBrightnessMapper;
    if (brightnessMappingStrategy != null) {
      return brightnessMappingStrategy.convertToAdjustedNits(f);
    }
    return -1.0f;
  }

  public float convertToFloatScale(float f) {
    BrightnessMappingStrategy brightnessMappingStrategy = this.mCurrentBrightnessMapper;
    if (brightnessMappingStrategy != null) {
      return brightnessMappingStrategy.convertToFloatScale(f);
    }
    return Float.NaN;
  }

  public void recalculateSplines(boolean z, float[] fArr) {
    this.mCurrentBrightnessMapper.recalculateSplines(z, fArr);
    resetShortTermModel();
    if (z) {
      setScreenBrightnessByUser(getAutomaticScreenBrightness());
    }
  }

  public BrightnessConfiguration getAppliedBackupConfig(
      BrightnessConfiguration brightnessConfiguration) {
    return this.mInteractiveModeBrightnessMapper.getAppliedBackupConfig(brightnessConfiguration);
  }

  public Spline getBrightnessSpline() {
    return this.mInteractiveModeBrightnessMapper.getBrightnessSpline();
  }

  public final void handleSensorTimeout() {
    if (this.mAmbientLuxValid) {
      return;
    }
    Slog.m72d("AutomaticBrightnessController", "Light sensor update is timed out!");
    this.mAmbientLuxValid = true;
    updateAutoBrightness(true, false);
  }

  public boolean setEarlyLightSensorEnabled(boolean z, Runnable runnable) {
    if (z == this.mShouldApplyEarlyWakeUp) {
      return true;
    }
    this.mShouldApplyEarlyWakeUp = z;
    this.mPendingEarlyLightSensorReadyListener = runnable;
    return true;
  }

  public final boolean setLightSensorEnabledForCAB(boolean z) {
    if (z) {
      if (!this.mLightSensorEnabled) {
        this.mLightSensorEnabled = true;
        this.mLightSensorEnableTime = SystemClock.uptimeMillis();
        int i = this.mInitialLightSensorRate;
        this.mCurrentLightSensorRate = i;
        if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED) {
          setCameraSensorEnabled(true);
        } else if (this.mIsCameraLightTypeSupported) {
          this.mSensorManager.registerListener(
              this.mLightSensorListener, this.mLightSensor, i * 1000, this.mHandler);
        }
        Slog.m72d("AutomaticBrightnessController", "setLightSensorEnabledForCAB: true");
        return true;
      }
    } else if (this.mLightSensorEnabled) {
      this.mLightSensorEnabled = false;
      this.mForceUpdateBrightness = false;
      this.mRecentLightSamples = 0;
      this.mAmbientLightRingBuffer.clear();
      sendAmbientLux(-1.0f);
      this.mScreenAutoBrightness = Float.NaN;
      if (PowerManagerUtil.CAMERA_ADAPTIVE_BRIGHTNESS_SUPPORTED) {
        setCameraSensorEnabled(false);
      } else if (this.mIsCameraLightTypeSupported) {
        this.mSensorManager.unregisterListener(this.mLightSensorListener);
      }
      Slog.m72d("AutomaticBrightnessController", "setLightSensorEnabledForCAB: false");
    }
    return false;
  }

  public final void setCameraSensorEnabled(boolean z) {
    Intent intent = new Intent();
    intent.setAction("samsung.intent.action.START_AUTO_BRIGHTNESS_ROOT");
    intent.setComponent(
        new ComponentName(
            "com.samsung.adaptivebrightnessgo", "com.samsung.adaptivebrightnessgo.RootService"));
    try {
      if (z) {
        Slog.m72d("AutomaticBrightnessController", "Starting sABC service");
        this.mContext.startService(intent);
      } else {
        Slog.m72d("AutomaticBrightnessController", "Stoping sABC service");
        this.mContext.stopService(intent);
      }
    } catch (Exception e) {
      Slog.m75e("AutomaticBrightnessController", "Error while calling adaptive brightness", e);
    }
  }

  public final HysteresisLevels getAmbientBrightnessThresholds() {
    int i = this.mBrightnessThresoldsType;
    if (i == 1) {
      return this.mAmbientBrightnessThresholdsTouchHigh;
    }
    if (i == 2) {
      return this.mAmbientBrightnessThresholdsTouchLow;
    }
    return this.mAmbientBrightnessThresholds;
  }

  public final String getAmbientBrightnessThresholdsString() {
    int i = this.mBrightnessThresoldsType;
    return i != 1 ? i != 2 ? "DEFAULT" : "TOUCH_LOW" : "TOUCH_HIGH";
  }

  public final void sendAmbientLux(float f) {
    SemDisplayQualityManager semDisplayQualityManager;
    ExynosDisplaySolutionManager exynosDisplaySolutionManager;
    if (this.mLastSentAmbientLux == f) {
      return;
    }
    Slog.m72d("AutomaticBrightnessController", "sendAmbientLux(" + f + ")");
    this.mLastSentAmbientLux = f;
    if (PowerManagerUtil.SEC_FEATURE_PAPAYA_DQE
        && (exynosDisplaySolutionManager =
                (ExynosDisplaySolutionManager) this.mContext.getSystemService("exynos_display"))
            != null) {
      exynosDisplaySolutionManager.setDisplayFeature("atc_user", 1, (int) f, (String) null);
    }
    if (PowerManagerUtil.SEC_FEATURE_DISPLAY_QUALITY
        && (semDisplayQualityManager =
                (SemDisplayQualityManager) this.mContext.getSystemService("DisplayQuality"))
            != null) {
      semDisplayQualityManager.enhanceDisplayOutdoorVisibilityByLux((int) f);
    }
    if (this.mDualScreenPolicy == 1) {
      PowerManagerUtil.fileWriteInt("/sys/class/lcd/panel1/lux", (int) f);
    } else {
      PowerManagerUtil.fileWriteInt("/sys/class/lcd/panel/lux", (int) f);
    }
  }

  public final void sendAmbientBrightnessThresholdInfo() {
    this.mSensorManager.setBrightnessHysteresisParameter(
        new float[] {
          BrightnessSynchronizer.brightnessFloatToInt(this.mScreenAutoBrightness),
          this.mAmbientDarkeningThreshold,
          this.mAmbientLux,
          this.mAmbientBrighteningThreshold
        });
  }

  public final void updateAmbientLuxCAB(long j) {
    if (!this.mAmbientLuxValid) {
      setAmbientLux(calculateAmbientLux(j, 0L));
      this.mAmbientLuxValid = true;
      if (this.mLoggingEnabled) {
        Slog.m72d(
            "AutomaticBrightnessController",
            "updateAmbientLux: Initializing: mAmbientLightRingBuffer="
                + this.mAmbientLightRingBuffer
                + ", mAmbientLux="
                + this.mAmbientLux);
      }
      updateAutoBrightness(true, false);
    }
    float calculateAmbientLux = calculateAmbientLux(j, 0L);
    if (calculateAmbientLux >= this.mAmbientBrighteningThreshold
        || calculateAmbientLux <= this.mAmbientDarkeningThreshold
        || this.mForceUpdateBrightness) {
      this.mForceUpdateBrightness = false;
      setAmbientLux(calculateAmbientLux);
      if (this.mLoggingEnabled) {
        StringBuilder sb = new StringBuilder();
        sb.append("updateAmbientLux: ");
        sb.append(calculateAmbientLux > this.mAmbientLux ? "Brightened" : "Darkened");
        sb.append(": mBrighteningLuxThreshold=");
        sb.append(this.mAmbientBrighteningThreshold);
        sb.append(", mAmbientLightRingBuffer=");
        sb.append(this.mAmbientLightRingBuffer);
        sb.append(", mAmbientLux=");
        sb.append(this.mAmbientLux);
        Slog.m72d("AutomaticBrightnessController", sb.toString());
      }
      updateAutoBrightness(true, false);
    }
  }

  public boolean isAmbientLuxValid() {
    return this.mAmbientLuxValid;
  }

  public final boolean isValidUserLux(float f) {
    if (DisplayPowerController2.RATE_FROM_DOZE_TO_ON <= f) {
      return !PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM || f < ((float) PowerManagerUtil.HBM_LUX);
    }
    return false;
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

  public String getAmbientBrightnessInfo(float f) {
    float brightness = this.mInteractiveModeBrightnessMapper.getBrightness(f);
    int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(brightness);
    float darkeningThreshold = this.mAmbientBrightnessThresholds.getDarkeningThreshold(f);
    float brighteningThreshold = this.mAmbientBrightnessThresholds.getBrighteningThreshold(f);
    StringBuilder sb = new StringBuilder();
    sb.append("getAmbientBrightnessInfo : ");
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
        String.format(locale, "%+.1f", Float.valueOf(getAutomaticScreenBrightnessAdjustment())));
    sb.append(")");
    return sb.toString();
  }

  public void injectLux(final SensorEvent sensorEvent) {
    if (this.mLightSensorListener == null) {
      Slog.m74e("AutomaticBrightnessController", "injectLux: mLightSensorListener is null");
    } else if (sensorEvent.values[0] >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      this.mLuxInjected = true;
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.AutomaticBrightnessController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              AutomaticBrightnessController.this.lambda$injectLux$0(sensorEvent);
            }
          });
    } else {
      this.mLuxInjected = false;
    }
  }

  public final void setProximitySensorEnabled(boolean z) {
    if (z) {
      if (this.mProximitySensorEnabled) {
        return;
      }
      this.mProximitySensorEnabled = true;
      Slog.m72d("AutomaticBrightnessController", "setProximitySensorEnabled::registerListener");
      this.mSensorManager.registerListener(
          this.mProximitySensorListener, this.mProximitySensor, 3, this.mHandler);
      return;
    }
    if (this.mProximitySensorEnabled) {
      this.mProximitySensorEnabled = false;
      this.mProximity = -1;
      this.mPendingProximity = -1;
      this.mHandler.removeCallbacks(this.mOnProximityChangedRunnable);
      Slog.m72d("AutomaticBrightnessController", "setProximitySensorEnabled::unregisterListener");
      this.mSensorManager.unregisterListener(this.mProximitySensorListener);
    }
  }

  public final void handleProximitySensorEvent(long j, boolean z) {
    if (this.mProximitySensorEnabled) {
      int i = this.mPendingProximity;
      if (i != 0 || z) {
        if (i == 1 && z) {
          return;
        }
        if (z) {
          this.mPendingProximity = 1;
        } else {
          this.mPendingProximity = 0;
        }
        this.mHandler.removeCallbacks(this.mOnProximityChangedRunnable);
        this.mHandler.postDelayed(this.mOnProximityChangedRunnable, 200L);
      }
    }
  }

  public class ShortTermModel {
    public float mAnchor;
    public float mBrightness;
    public boolean mIsValid;

    public ShortTermModel() {
      this.mAnchor = -1.0f;
      this.mBrightness = -1.0f;
      this.mIsValid = false;
    }

    public final void reset() {
      this.mAnchor = -1.0f;
      this.mBrightness = -1.0f;
      this.mIsValid = false;
    }

    public final void invalidate() {
      this.mIsValid = false;
      Slog.m72d("AutomaticBrightnessController", "ShortTermModel: invalidate user data");
    }

    public final void setUserBrightness(float f, float f2) {
      this.mAnchor = f;
      this.mBrightness = f2;
      this.mIsValid = true;
      Slog.m72d("AutomaticBrightnessController", "ShortTermModel: anchor=" + this.mAnchor);
    }

    public final boolean maybeReset(float f) {
      if (this.mIsValid || this.mAnchor == -1.0f) {
        return false;
      }
      if (AutomaticBrightnessController.this.mCurrentBrightnessMapper != null
          && AutomaticBrightnessController.this.mCurrentBrightnessMapper.shouldResetShortTermModel(
              f, this.mAnchor)) {
        float darkeningThreshold =
            AutomaticBrightnessController.this.mAmbientBrightnessThresholds.getDarkeningThreshold(
                this.mAnchor);
        float brighteningThreshold =
            AutomaticBrightnessController.this.mAmbientBrightnessThresholds.getBrighteningThreshold(
                this.mAnchor);
        if (AutomaticBrightnessController.this.mAmbientLux <= darkeningThreshold
            || AutomaticBrightnessController.this.mAmbientLux >= brighteningThreshold) {
          String str =
              "ShortTermModel: reset data, ambient lux is "
                  + AutomaticBrightnessController.this.mAmbientLux
                  + " ,hysteresis("
                  + darkeningThreshold
                  + ", "
                  + brighteningThreshold
                  + ")";
          Slog.m72d("AutomaticBrightnessController", str);
          AutomaticBrightnessController.this.mPowerHistorian.onAutoBrightnessEvent(str);
          AutomaticBrightnessController.this.resetShortTermModel();
        }
      } else {
        this.mIsValid = true;
      }
      return this.mIsValid;
    }

    public final void set(float f, float f2, boolean z) {
      this.mAnchor = f;
      this.mBrightness = f2;
      this.mIsValid = z;
    }

    public final void copyFrom(ShortTermModel shortTermModel) {
      this.mAnchor = shortTermModel.mAnchor;
      this.mBrightness = shortTermModel.mBrightness;
      this.mIsValid = shortTermModel.mIsValid;
    }

    public String toString() {
      return " mAnchor: "
          + this.mAnchor
          + "\n mBrightness: "
          + this.mBrightness
          + "\n mIsValid: "
          + this.mIsValid;
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println(this);
    }
  }

  public final class AutomaticBrightnessHandler extends Handler {
    public AutomaticBrightnessHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 1:
          AutomaticBrightnessController.this.updateAmbientLux();
          break;
        case 2:
          AutomaticBrightnessController.this.collectBrightnessAdjustmentSample();
          break;
        case 3:
          AutomaticBrightnessController.this.mShortTermModel.invalidate();
          break;
        case 4:
          AutomaticBrightnessController.this.updateForegroundApp();
          break;
        case 5:
          AutomaticBrightnessController.this.updateForegroundAppSync();
          break;
        case 6:
          AutomaticBrightnessController.this.updateAutoBrightness(true, false);
          break;
        case 7:
          AutomaticBrightnessController.this.mPausedShortTermModel.invalidate();
          break;
        case 8:
          AutomaticBrightnessController.this.handleSensorTimeout();
          break;
      }
    }
  }

  /* renamed from: onSensorChangedInternal, reason: merged with bridge method [inline-methods] */
  public final void lambda$injectLux$0(SensorEvent sensorEvent) {
    boolean z = false;
    if (!this.mAmbientLuxValid) {
      Slog.m72d(
          "AutomaticBrightnessController", "onSensorChanged: 1st lux : " + sensorEvent.values[0]);
    }
    if (this.mLightSensorEnabled) {
      long uptimeMillis = this.mClock.uptimeMillis();
      float[] fArr = sensorEvent.values;
      float f = fArr[0];
      if (this.mIsUnderDisplayLightSensor) {
        if (fArr[1] > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          this.mMinLuxUpdated = true;
          this.mRecentLightSamples = 0;
          this.mAmbientLightRingBuffer.clear();
          Slog.m72d(
              "AutomaticBrightnessController",
              "onSensorChanged: min lux : " + sensorEvent.values[0]);
        }
        if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
          if (sensorEvent.values[2] > DisplayPowerController2.RATE_FROM_DOZE_TO_ON
              && this.mDualScreenPolicy == 0) {
            z = true;
          }
          if (this.mIsRearLightSensor != z) {
            this.mIsLightSensorPositionUpdated = true;
            this.mLightSensorPositionUpdatedTime = uptimeMillis;
            this.mIsRearLightSensor = z;
            StringBuilder sb = new StringBuilder();
            sb.append("onSensorChanged: ");
            sb.append(this.mIsRearLightSensor ? "Front -> Rear" : "Rear -> Front");
            Slog.m72d("AutomaticBrightnessController", sb.toString());
          }
        }
      }
      handleLightSensorEvent(uptimeMillis, f);
    }
  }

  public class TaskStackListenerImpl extends TaskStackListener {
    public TaskStackListenerImpl() {}

    public void onTaskStackChanged() {
      AutomaticBrightnessController.this.mHandler.sendEmptyMessage(4);
    }
  }

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

    public AmbientLightRingBuffer(long j, int i, Clock clock) {
      if (j <= 0) {
        throw new IllegalArgumentException("lightSensorRate must be above 0");
      }
      int ceil = (int) Math.ceil((i * 1.5f) / j);
      this.mCapacity = ceil;
      this.mRingLux = new float[ceil];
      this.mRingTime = new long[ceil];
      this.mClock = clock;
    }

    public float getLux(int i) {
      try {
        return this.mRingLux[offsetOf(i)];
      } catch (NullPointerException unused) {
        Slog.m74e("AutomaticBrightnessController", "Catch nullpointer exception: getLux");
        int i2 = this.mCapacity;
        float[] fArr = new float[i2];
        this.mRingLux = fArr;
        long[] jArr = new long[i2];
        this.mRingTime = jArr;
        float f = this.mLastLux;
        fArr[0] = f;
        jArr[0] = this.mLastTime;
        this.mStart = 0;
        this.mEnd = 1;
        this.mCount = 1;
        return f;
      }
    }

    public float[] getAllLuxValues() {
      int i = this.mCount;
      float[] fArr = new float[i];
      if (i == 0) {
        return fArr;
      }
      int i2 = this.mStart;
      if (i2 < this.mEnd) {
        System.arraycopy(this.mRingLux, i2, fArr, 0, i);
      } else {
        System.arraycopy(this.mRingLux, i2, fArr, 0, this.mCapacity - i2);
        System.arraycopy(this.mRingLux, 0, fArr, this.mCapacity - this.mStart, this.mEnd);
      }
      return fArr;
    }

    public long getTime(int i) {
      return this.mRingTime[offsetOf(i)];
    }

    public long[] getAllTimestamps() {
      int i = this.mCount;
      long[] jArr = new long[i];
      if (i == 0) {
        return jArr;
      }
      int i2 = this.mStart;
      if (i2 < this.mEnd) {
        System.arraycopy(this.mRingTime, i2, jArr, 0, i);
      } else {
        System.arraycopy(this.mRingTime, i2, jArr, 0, this.mCapacity - i2);
        System.arraycopy(this.mRingTime, 0, jArr, this.mCapacity - this.mStart, this.mEnd);
      }
      return jArr;
    }

    public void push(long j, float f) {
      int i = this.mEnd;
      int i2 = this.mCount;
      int i3 = this.mCapacity;
      if (i2 == i3) {
        int i4 = i3 * 2;
        float[] fArr = new float[i4];
        long[] jArr = new long[i4];
        int i5 = this.mStart;
        int i6 = i3 - i5;
        System.arraycopy(this.mRingLux, i5, fArr, 0, i6);
        System.arraycopy(this.mRingTime, this.mStart, jArr, 0, i6);
        int i7 = this.mStart;
        if (i7 != 0) {
          System.arraycopy(this.mRingLux, 0, fArr, i6, i7);
          System.arraycopy(this.mRingTime, 0, jArr, i6, this.mStart);
        }
        this.mRingLux = fArr;
        this.mRingTime = jArr;
        int i8 = this.mCapacity;
        this.mCapacity = i4;
        this.mStart = 0;
        i = i8;
      }
      this.mRingTime[i] = j;
      this.mRingLux[i] = f;
      this.mLastTime = j;
      this.mLastLux = f;
      int i9 = i + 1;
      this.mEnd = i9;
      if (i9 == this.mCapacity) {
        this.mEnd = 0;
      }
      this.mCount++;
    }

    public void prune(long j) {
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

    public int size() {
      return this.mCount;
    }

    public void clear() {
      this.mStart = 0;
      this.mEnd = 0;
      this.mCount = 0;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append('[');
      int i = 0;
      while (true) {
        int i2 = this.mCount;
        if (i < i2) {
          int i3 = i + 1;
          long time = i3 < i2 ? getTime(i3) : this.mClock.uptimeMillis();
          if (i != 0) {
            sb.append(", ");
          }
          sb.append(getLux(i));
          sb.append(" / ");
          sb.append(time - getTime(i));
          sb.append("ms");
          i = i3;
        } else {
          sb.append(']');
          return sb.toString();
        }
      }
    }

    public final int offsetOf(int i) {
      if (i >= this.mCount || i < 0) {
        throw new ArrayIndexOutOfBoundsException(i);
      }
      int i2 = i + this.mStart;
      int i3 = this.mCapacity;
      return i2 >= i3 ? i2 - i3 : i2;
    }
  }

  public class Injector {
    public Handler getBackgroundThreadHandler() {
      return BackgroundThread.getHandler();
    }

    public Clock createClock() {
      return new Clock() { // from class:
                           // com.android.server.display.AutomaticBrightnessController$Injector$$ExternalSyntheticLambda0
        @Override // com.android.server.display.AutomaticBrightnessController.Clock
        public final long uptimeMillis() {
          return SystemClock.uptimeMillis();
        }
      };
    }
  }
}
