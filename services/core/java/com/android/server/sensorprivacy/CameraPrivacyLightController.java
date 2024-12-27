package com.android.server.sensorprivacy;

import android.R;
import android.app.AppOpsManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
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

import com.android.internal.util.ArrayUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public final class CameraPrivacyLightController
        implements AppOpsManager.OnOpActiveChangedListener, SensorEventListener {
    public static final double LIGHT_VALUE_MULTIPLIER = 1.0d / Math.log(1.1d);
    public final AppOpsManager mAppOpsManager;
    public final int[] mColors;
    public final Context mContext;
    public long mElapsedTimeStartedReading;
    public final Executor mExecutor;
    public final Handler mHandler;
    public final Sensor mLightSensor;
    public final LightsManager mLightsManager;
    public final long mMovingAverageIntervalMillis;
    public final SensorManager mSensorManager;
    public final long[] mThresholds;
    public final Set mActivePackages = new ArraySet();
    public final Set mActivePhonePackages = new ArraySet();
    public final List mCameraLights = new ArrayList();
    public LightsManager.LightsSession mLightsSession = null;
    public boolean mIsAmbientLightListenerRegistered = false;
    public final ArrayDeque mAmbientLightValues = new ArrayDeque();
    public long mAlvSum = 0;
    public int mLastLightColor = 0;
    public final Object mDelayedUpdateToken = new Object();
    public long mElapsedRealTime = -1;

    public CameraPrivacyLightController(Context context, Looper looper) {
        int[] intArray =
                context.getResources().getIntArray(R.array.config_roundedCornerBottomRadiusArray);
        this.mColors = intArray;
        if (ArrayUtils.isEmpty(intArray)) {
            this.mHandler = null;
            this.mExecutor = null;
            this.mContext = null;
            this.mAppOpsManager = null;
            this.mLightsManager = null;
            this.mSensorManager = null;
            this.mLightSensor = null;
            this.mMovingAverageIntervalMillis = 0L;
            this.mThresholds = null;
            return;
        }
        this.mContext = context;
        Handler handler = new Handler(looper);
        this.mHandler = handler;
        this.mExecutor = new HandlerExecutor(handler);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mLightsManager = (LightsManager) context.getSystemService(LightsManager.class);
        this.mSensorManager = (SensorManager) context.getSystemService(SensorManager.class);
        this.mMovingAverageIntervalMillis =
                context.getResources()
                        .getInteger(R.integer.config_defaultNotificationVibrationIntensity);
        int[] intArray2 =
                context.getResources()
                        .getIntArray(R.array.config_roundedCornerBottomRadiusAdjustmentArray);
        if (intArray2.length != intArray.length - 1) {
            StringBuilder sb =
                    new StringBuilder(
                            "There must be exactly one more color than thresholds. Found ");
            sb.append(intArray.length);
            sb.append(" colors and ");
            throw new IllegalStateException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            intArray2.length, sb, " thresholds."));
        }
        this.mThresholds = new long[intArray2.length];
        for (int i = 0; i < intArray2.length; i++) {
            this.mThresholds[i] = (long) (Math.log(intArray2[i]) * LIGHT_VALUE_MULTIPLIER);
        }
        List<Light> lights = this.mLightsManager.getLights();
        for (int i2 = 0; i2 < lights.size(); i2++) {
            Light light = lights.get(i2);
            if (light.getType() == 9) {
                this.mCameraLights.add(light);
            }
        }
        if (this.mCameraLights.isEmpty()) {
            this.mLightSensor = null;
        } else {
            this.mAppOpsManager.startWatchingActive(
                    new String[] {"android:camera", "android:phone_call_camera"},
                    this.mExecutor,
                    this);
            this.mLightSensor = this.mSensorManager.getDefaultSensor(5);
        }
    }

    public final long getCurrentIntervalMillis() {
        long j = this.mMovingAverageIntervalMillis;
        long j2 = this.mElapsedRealTime;
        if (j2 == -1) {
            j2 = SystemClock.elapsedRealtime();
        }
        return Math.min(j, j2 - this.mElapsedTimeStartedReading);
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {}

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public final void onOpActiveChanged(String str, int i, String str2, boolean z) {
        Set set;
        if ("android:camera".equals(str)) {
            set = this.mActivePackages;
        } else if (!"android:phone_call_camera".equals(str)) {
            return;
        } else {
            set = this.mActivePhonePackages;
        }
        if (z) {
            ((ArraySet) set).add(str2);
        } else {
            ((ArraySet) set).remove(str2);
        }
        updateLightSession();
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        long millis = TimeUnit.NANOSECONDS.toMillis(sensorEvent.timestamp);
        int max = Math.max(0, (int) (Math.log(sensorEvent.values[0]) * LIGHT_VALUE_MULTIPLIER));
        if (this.mAmbientLightValues.isEmpty()) {
            this.mAmbientLightValues.add(
                    new Pair(
                            Long.valueOf((millis - getCurrentIntervalMillis()) - 1),
                            Integer.valueOf(max)));
        }
        Pair pair = (Pair) this.mAmbientLightValues.peekLast();
        this.mAmbientLightValues.add(new Pair(Long.valueOf(millis), Integer.valueOf(max)));
        this.mAlvSum =
                ((millis - ((Long) pair.first).longValue()) * ((Integer) pair.second).intValue())
                        + this.mAlvSum;
        removeObsoleteData(millis);
        updateLightSession();
        this.mHandler.removeCallbacksAndMessages(this.mDelayedUpdateToken);
        this.mHandler.postDelayed(
                new CameraPrivacyLightController$$ExternalSyntheticLambda0(this),
                this.mDelayedUpdateToken,
                this.mMovingAverageIntervalMillis);
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

    public void setElapsedRealTime(long j) {
        this.mElapsedRealTime = j;
    }

    public final void updateLightSession() {
        long longValue;
        int i;
        Sensor sensor;
        if (Looper.myLooper() != this.mHandler.getLooper()) {
            this.mHandler.post(new CameraPrivacyLightController$$ExternalSyntheticLambda0(this));
            return;
        }
        Set indicatorExemptedPackages =
                PermissionManager.getIndicatorExemptedPackages(this.mContext);
        boolean z =
                indicatorExemptedPackages.containsAll(this.mActivePackages)
                        && indicatorExemptedPackages.containsAll(this.mActivePhonePackages);
        if (z && this.mIsAmbientLightListenerRegistered) {
            this.mSensorManager.unregisterListener(this);
            this.mIsAmbientLightListenerRegistered = false;
        }
        if (!z && !this.mIsAmbientLightListenerRegistered && (sensor = this.mLightSensor) != null) {
            this.mSensorManager.registerListener(this, sensor, 3, this.mHandler);
            this.mIsAmbientLightListenerRegistered = true;
            long j = this.mElapsedRealTime;
            if (j == -1) {
                j = SystemClock.elapsedRealtime();
            }
            this.mElapsedTimeStartedReading = j;
        }
        if (z) {
            LightsManager.LightsSession lightsSession = this.mLightsSession;
            if (lightsSession == null) {
                return;
            }
            lightsSession.close();
            this.mLightsSession = null;
            return;
        }
        if (this.mLightSensor != null) {
            if (this.mAmbientLightValues.isEmpty()) {
                longValue = this.mAlvSum;
            } else {
                long j2 = this.mElapsedRealTime;
                if (j2 == -1) {
                    j2 = SystemClock.elapsedRealtime();
                }
                removeObsoleteData(j2);
                Pair pair = (Pair) this.mAmbientLightValues.peekFirst();
                longValue =
                        ((j2
                                                - ((Long)
                                                                ((Pair)
                                                                                this
                                                                                        .mAmbientLightValues
                                                                                        .peekLast())
                                                                        .first)
                                                        .longValue())
                                        * ((Integer) r5.second).intValue())
                                + (this.mAlvSum
                                        - (Math.max(
                                                        0L,
                                                        (j2 - getCurrentIntervalMillis())
                                                                - ((Long) pair.first).longValue())
                                                * ((Integer) pair.second).intValue()));
            }
            long currentIntervalMillis = getCurrentIntervalMillis();
            int i2 = 0;
            while (true) {
                long[] jArr = this.mThresholds;
                if (i2 >= jArr.length) {
                    int[] iArr = this.mColors;
                    i = iArr[iArr.length - 1];
                    break;
                } else {
                    if (longValue < jArr[i2] * currentIntervalMillis) {
                        i = this.mColors[i2];
                        break;
                    }
                    i2++;
                }
            }
        } else {
            int[] iArr2 = this.mColors;
            i = iArr2[iArr2.length - 1];
        }
        if (this.mLastLightColor != i || this.mLightsSession == null) {
            this.mLastLightColor = i;
            LightsRequest.Builder builder = new LightsRequest.Builder();
            for (int i3 = 0; i3 < ((ArrayList) this.mCameraLights).size(); i3++) {
                builder.addLight(
                        (Light) ((ArrayList) this.mCameraLights).get(i3),
                        new LightState.Builder().setColor(i).build());
            }
            if (this.mLightsSession == null) {
                this.mLightsSession = this.mLightsManager.openSession(Integer.MAX_VALUE);
            }
            this.mLightsSession.requestLights(builder.build());
        }
    }
}
