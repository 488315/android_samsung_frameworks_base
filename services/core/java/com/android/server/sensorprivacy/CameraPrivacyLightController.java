package com.android.server.sensorprivacy;

import android.R;
import android.app.AppOpsManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.hardware.lights.LightsManager;
import android.hardware.lights.LightsRequest;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.SystemClock;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.Pair;
import com.android.server.FgThread;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class CameraPrivacyLightController
    implements AppOpsManager.OnOpActiveChangedListener, SensorEventListener {
  static final double LIGHT_VALUE_MULTIPLIER = 1.0d / Math.log(1.1d);
  public final Set mActivePackages;
  public final Set mActivePhonePackages;
  public long mAlvSum;
  public final ArrayDeque mAmbientLightValues;
  public final AppOpsManager mAppOpsManager;
  public final List mCameraLights;
  public final Context mContext;
  public final int mDayColor;
  public final Object mDelayedUpdateToken;
  public long mElapsedRealTime;
  public long mElapsedTimeStartedReading;
  public final Executor mExecutor;
  public final Handler mHandler;
  public boolean mIsAmbientLightListenerRegistered;
  public int mLastLightColor;
  public final Sensor mLightSensor;
  public final LightsManager mLightsManager;
  public LightsManager.LightsSession mLightsSession;
  public final long mMovingAverageIntervalMillis;
  public final int mNightColor;
  public final long mNightThreshold;
  public final SensorManager mSensorManager;

  @Override // android.hardware.SensorEventListener
  public void onAccuracyChanged(Sensor sensor, int i) {}

  public CameraPrivacyLightController(Context context) {
    this(context, FgThread.get().getLooper());
  }

  public CameraPrivacyLightController(Context context, Looper looper) {
    this.mActivePackages = new ArraySet();
    this.mActivePhonePackages = new ArraySet();
    this.mCameraLights = new ArrayList();
    this.mLightsSession = null;
    this.mIsAmbientLightListenerRegistered = false;
    this.mAmbientLightValues = new ArrayDeque();
    this.mAlvSum = 0L;
    this.mLastLightColor = 0;
    this.mDelayedUpdateToken = new Object();
    this.mElapsedRealTime = -1L;
    this.mContext = context;
    Handler handler = new Handler(looper);
    this.mHandler = handler;
    this.mExecutor = new HandlerExecutor(handler);
    this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    LightsManager lightsManager = (LightsManager) context.getSystemService(LightsManager.class);
    this.mLightsManager = lightsManager;
    this.mSensorManager = (SensorManager) context.getSystemService(SensorManager.class);
    this.mDayColor = context.getColor(R.color.car_accent);
    this.mNightColor = context.getColor(R.color.car_accent_dark);
    this.mMovingAverageIntervalMillis =
        context.getResources().getInteger(R.integer.config_defaultMediaVibrationIntensity);
    this.mNightThreshold =
        (long)
            (Math.log(
                    context
                        .getResources()
                        .getInteger(R.integer.config_defaultMinEmergencyGestureTapDurationMillis))
                * LIGHT_VALUE_MULTIPLIER);
    List<Light> lights = lightsManager.getLights();
    for (int i = 0; i < lights.size(); i++) {
      Light light = lights.get(i);
      if (light.getType() == 9) {
        this.mCameraLights.add(light);
      }
    }
    if (this.mCameraLights.isEmpty()) {
      this.mLightSensor = null;
    } else {
      this.mAppOpsManager.startWatchingActive(
          new String[] {"android:camera", "android:phone_call_camera"}, this.mExecutor, this);
      this.mLightSensor = this.mSensorManager.getDefaultSensor(5);
    }
  }

  public final void addElement(long j, int i) {
    if (this.mAmbientLightValues.isEmpty()) {
      this.mAmbientLightValues.add(
          new Pair(Long.valueOf((j - getCurrentIntervalMillis()) - 1), Integer.valueOf(i)));
    }
    Pair pair = (Pair) this.mAmbientLightValues.peekLast();
    this.mAmbientLightValues.add(new Pair(Long.valueOf(j), Integer.valueOf(i)));
    this.mAlvSum += (j - ((Long) pair.first).longValue()) * ((Integer) pair.second).intValue();
    removeObsoleteData(j);
  }

  public final void removeObsoleteData(long j) {
    while (this.mAmbientLightValues.size() > 1) {
      Pair pair = (Pair) this.mAmbientLightValues.pollFirst();
      Pair pair2 = (Pair) this.mAmbientLightValues.peekFirst();
      if (((Long) pair2.first).longValue() > j - getCurrentIntervalMillis()) {
        this.mAmbientLightValues.addFirst(pair);
        return;
      }
      this.mAlvSum -=
          (((Long) pair2.first).longValue() - ((Long) pair.first).longValue())
              * ((Integer) pair.second).intValue();
    }
  }

  public final long getLiveAmbientLightTotal() {
    if (this.mAmbientLightValues.isEmpty()) {
      return this.mAlvSum;
    }
    long elapsedRealTime = getElapsedRealTime();
    removeObsoleteData(elapsedRealTime);
    return (this.mAlvSum
            - (Math.max(
                    0L,
                    (elapsedRealTime - getCurrentIntervalMillis())
                        - ((Long) ((Pair) this.mAmbientLightValues.peekFirst()).first).longValue())
                * ((Integer) r2.second).intValue()))
        + ((elapsedRealTime
                - ((Long) ((Pair) this.mAmbientLightValues.peekLast()).first).longValue())
            * ((Integer) r3.second).intValue());
  }

  @Override // android.app.AppOpsManager.OnOpActiveChangedListener
  public void onOpActiveChanged(String str, int i, String str2, boolean z) {
    Set set;
    if ("android:camera".equals(str)) {
      set = this.mActivePackages;
    } else if (!"android:phone_call_camera".equals(str)) {
      return;
    } else {
      set = this.mActivePhonePackages;
    }
    if (z) {
      set.add(str2);
    } else {
      set.remove(str2);
    }
    updateLightSession();
  }

  public final void updateLightSession() {
    int i;
    if (Looper.myLooper() != this.mHandler.getLooper()) {
      this.mHandler.post(new CameraPrivacyLightController$$ExternalSyntheticLambda0(this));
      return;
    }
    Set indicatorExemptedPackages = PermissionManager.getIndicatorExemptedPackages(this.mContext);
    boolean z =
        indicatorExemptedPackages.containsAll(this.mActivePackages)
            && indicatorExemptedPackages.containsAll(this.mActivePhonePackages);
    updateSensorListener(z);
    if (z) {
      LightsManager.LightsSession lightsSession = this.mLightsSession;
      if (lightsSession == null) {
        return;
      }
      lightsSession.close();
      this.mLightsSession = null;
      return;
    }
    if (this.mLightSensor != null
        && getLiveAmbientLightTotal() < getCurrentIntervalMillis() * this.mNightThreshold) {
      i = this.mNightColor;
    } else {
      i = this.mDayColor;
    }
    if (this.mLastLightColor != i || this.mLightsSession == null) {
      this.mLastLightColor = i;
      LightsRequest.Builder builder = new LightsRequest.Builder();
      for (int i2 = 0; i2 < this.mCameraLights.size(); i2++) {
        builder.addLight(
            (Light) this.mCameraLights.get(i2), new LightState.Builder().setColor(i).build());
      }
      if (this.mLightsSession == null) {
        this.mLightsSession = this.mLightsManager.openSession(Integer.MAX_VALUE);
      }
      this.mLightsSession.requestLights(builder.build());
    }
  }

  public final void updateSensorListener(boolean z) {
    Sensor sensor;
    if (z && this.mIsAmbientLightListenerRegistered) {
      this.mSensorManager.unregisterListener(this);
      this.mIsAmbientLightListenerRegistered = false;
    }
    if (z || this.mIsAmbientLightListenerRegistered || (sensor = this.mLightSensor) == null) {
      return;
    }
    this.mSensorManager.registerListener(this, sensor, 3, this.mHandler);
    this.mIsAmbientLightListenerRegistered = true;
    this.mElapsedTimeStartedReading = getElapsedRealTime();
  }

  public final long getElapsedRealTime() {
    long j = this.mElapsedRealTime;
    return j == -1 ? SystemClock.elapsedRealtime() : j;
  }

  public void setElapsedRealTime(long j) {
    this.mElapsedRealTime = j;
  }

  @Override // android.hardware.SensorEventListener
  public void onSensorChanged(SensorEvent sensorEvent) {
    addElement(
        TimeUnit.NANOSECONDS.toMillis(sensorEvent.timestamp),
        Math.max(0, (int) (Math.log(sensorEvent.values[0]) * LIGHT_VALUE_MULTIPLIER)));
    updateLightSession();
    this.mHandler.removeCallbacksAndMessages(this.mDelayedUpdateToken);
    this.mHandler.postDelayed(
        new CameraPrivacyLightController$$ExternalSyntheticLambda0(this),
        this.mDelayedUpdateToken,
        this.mMovingAverageIntervalMillis);
  }

  public final long getCurrentIntervalMillis() {
    return Math.min(
        this.mMovingAverageIntervalMillis, getElapsedRealTime() - this.mElapsedTimeStartedReading);
  }
}
