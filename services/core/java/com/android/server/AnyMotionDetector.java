package com.android.server;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes.dex */
public class AnyMotionDetector {
    public final float THRESHOLD_ENERGY = 5.0f;
    public final Sensor mAccelSensor;
    public final DeviceIdleCallback mCallback;
    public Vector3 mCurrentGravityVector;
    public final Handler mHandler;
    public final SensorEventListener mListener;
    public final Object mLock;
    public boolean mMeasurementInProgress;
    public final Runnable mMeasurementTimeout;
    public boolean mMeasurementTimeoutIsActive;
    public int mNumSufficientSamples;
    public Vector3 mPreviousGravityVector;
    public final RunningSignalStats mRunningStats;
    public final SensorManager mSensorManager;
    public final Runnable mSensorRestart;
    public boolean mSensorRestartIsActive;
    public int mState;
    public final float mThresholdAngle;
    public final PowerManager.WakeLock mWakeLock;
    public final Runnable mWakelockTimeout;
    public volatile boolean mWakelockTimeoutIsActive;

    public interface DeviceIdleCallback {
        void onAnyMotionResult(int i);
    }

    public AnyMotionDetector(PowerManager powerManager, Handler handler, SensorManager sensorManager, DeviceIdleCallback deviceIdleCallback, float f) {
        Object obj = new Object();
        this.mLock = obj;
        this.mCurrentGravityVector = null;
        this.mPreviousGravityVector = null;
        this.mListener = new SensorEventListener() { // from class: com.android.server.AnyMotionDetector.1
            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                int stopOrientationMeasurementLocked;
                synchronized (AnyMotionDetector.this.mLock) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    float[] fArr = sensorEvent.values;
                    AnyMotionDetector.this.mRunningStats.accumulate(new Vector3(elapsedRealtime, fArr[0], fArr[1], fArr[2]));
                    stopOrientationMeasurementLocked = AnyMotionDetector.this.mRunningStats.getSampleCount() >= AnyMotionDetector.this.mNumSufficientSamples ? AnyMotionDetector.this.stopOrientationMeasurementLocked() : -1;
                }
                if (stopOrientationMeasurementLocked != -1) {
                    AnyMotionDetector.this.mHandler.removeCallbacks(AnyMotionDetector.this.mWakelockTimeout);
                    AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                    AnyMotionDetector.this.mCallback.onAnyMotionResult(stopOrientationMeasurementLocked);
                }
            }
        };
        this.mSensorRestart = new Runnable() { // from class: com.android.server.AnyMotionDetector.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (AnyMotionDetector.this.mLock) {
                    if (AnyMotionDetector.this.mSensorRestartIsActive) {
                        AnyMotionDetector.this.mSensorRestartIsActive = false;
                        AnyMotionDetector.this.startOrientationMeasurementLocked();
                    }
                }
            }
        };
        this.mMeasurementTimeout = new Runnable() { // from class: com.android.server.AnyMotionDetector.3
            @Override // java.lang.Runnable
            public void run() {
                int i;
                synchronized (AnyMotionDetector.this.mLock) {
                    if (AnyMotionDetector.this.mMeasurementTimeoutIsActive) {
                        AnyMotionDetector.this.mMeasurementTimeoutIsActive = false;
                        i = AnyMotionDetector.this.stopOrientationMeasurementLocked();
                    } else {
                        i = -1;
                    }
                }
                if (i != -1) {
                    AnyMotionDetector.this.mHandler.removeCallbacks(AnyMotionDetector.this.mWakelockTimeout);
                    AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                    AnyMotionDetector.this.mCallback.onAnyMotionResult(i);
                }
            }
        };
        this.mWakelockTimeout = new Runnable() { // from class: com.android.server.AnyMotionDetector.4
            @Override // java.lang.Runnable
            public void run() {
                synchronized (AnyMotionDetector.this.mLock) {
                    if (AnyMotionDetector.this.mWakelockTimeoutIsActive) {
                        AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                        AnyMotionDetector.this.stop();
                    }
                }
            }
        };
        synchronized (obj) {
            PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "AnyMotionDetector");
            this.mWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(false);
            this.mHandler = handler;
            this.mSensorManager = sensorManager;
            this.mAccelSensor = sensorManager.getDefaultSensor(1);
            this.mMeasurementInProgress = false;
            this.mMeasurementTimeoutIsActive = false;
            this.mWakelockTimeoutIsActive = false;
            this.mSensorRestartIsActive = false;
            this.mState = 0;
            this.mCallback = deviceIdleCallback;
            this.mThresholdAngle = f;
            this.mRunningStats = new RunningSignalStats();
            this.mNumSufficientSamples = (int) Math.ceil(62.5d);
        }
    }

    public boolean hasSensor() {
        return this.mAccelSensor != null;
    }

    public void checkForAnyMotion() {
        synchronized (this.mLock) {
            if (this.mState != 1) {
                Slog.i("AnyMotionDetector", "[DEEP] INACTIVE to ACTIVE");
                this.mState = 1;
                this.mCurrentGravityVector = null;
                this.mPreviousGravityVector = null;
                this.mWakeLock.acquire();
                this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, this.mWakelockTimeout), 30000L);
                this.mWakelockTimeoutIsActive = true;
                startOrientationMeasurementLocked();
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            if (this.mState == 1) {
                Slog.i("AnyMotionDetector", "[DEEP] ACTIVE to INACTIVE");
                this.mState = 0;
            }
            this.mHandler.removeCallbacks(this.mMeasurementTimeout);
            this.mHandler.removeCallbacks(this.mSensorRestart);
            this.mMeasurementTimeoutIsActive = false;
            this.mSensorRestartIsActive = false;
            if (this.mMeasurementInProgress) {
                this.mMeasurementInProgress = false;
                this.mSensorManager.unregisterListener(this.mListener);
            }
            this.mCurrentGravityVector = null;
            this.mPreviousGravityVector = null;
            if (this.mWakeLock.isHeld()) {
                this.mHandler.removeCallbacks(this.mWakelockTimeout);
                this.mWakelockTimeoutIsActive = false;
                this.mWakeLock.release();
            }
        }
    }

    public final void startOrientationMeasurementLocked() {
        Sensor sensor;
        if (this.mMeasurementInProgress || (sensor = this.mAccelSensor) == null) {
            return;
        }
        if (this.mSensorManager.registerListener(this.mListener, sensor, 40000)) {
            this.mMeasurementInProgress = true;
            this.mRunningStats.reset();
        }
        this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, this.mMeasurementTimeout), 3000L);
        this.mMeasurementTimeoutIsActive = true;
    }

    public final int stopOrientationMeasurementLocked() {
        if (!this.mMeasurementInProgress) {
            return -1;
        }
        this.mHandler.removeCallbacks(this.mMeasurementTimeout);
        this.mMeasurementTimeoutIsActive = false;
        this.mSensorManager.unregisterListener(this.mListener);
        this.mMeasurementInProgress = false;
        this.mPreviousGravityVector = this.mCurrentGravityVector;
        this.mCurrentGravityVector = this.mRunningStats.getRunningAverage();
        if (this.mRunningStats.getSampleCount() == 0) {
            Slog.w("AnyMotionDetector", "No accelerometer data acquired for orientation measurement.");
        }
        int stationaryStatusLocked = getStationaryStatusLocked();
        this.mRunningStats.reset();
        if (stationaryStatusLocked != -1) {
            if (this.mWakeLock.isHeld()) {
                this.mHandler.removeCallbacks(this.mWakelockTimeout);
                this.mWakelockTimeoutIsActive = false;
                this.mWakeLock.release();
            }
            Slog.i("AnyMotionDetector", "[DEEP] ACTIVE to INACTIVE, status = " + stationaryStatusLocked);
            this.mState = 0;
        } else {
            this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, this.mSensorRestart), 5000L);
            this.mSensorRestartIsActive = true;
        }
        return stationaryStatusLocked;
    }

    public final int getStationaryStatusLocked() {
        Vector3 vector3 = this.mPreviousGravityVector;
        if (vector3 == null || this.mCurrentGravityVector == null) {
            return -1;
        }
        float angleBetween = vector3.normalized().angleBetween(this.mCurrentGravityVector.normalized());
        if (angleBetween >= this.mThresholdAngle || this.mRunningStats.getEnergy() >= 5.0f) {
            return (!Float.isNaN(angleBetween) && this.mCurrentGravityVector.timeMillisSinceBoot - this.mPreviousGravityVector.timeMillisSinceBoot > 120000) ? -1 : 1;
        }
        return 0;
    }

    public final class Vector3 {
        public long timeMillisSinceBoot;

        /* renamed from: x */
        public float f1612x;

        /* renamed from: y */
        public float f1613y;

        /* renamed from: z */
        public float f1614z;

        public Vector3(long j, float f, float f2, float f3) {
            this.timeMillisSinceBoot = j;
            this.f1612x = f;
            this.f1613y = f2;
            this.f1614z = f3;
        }

        public float norm() {
            return (float) Math.sqrt(dotProduct(this));
        }

        public Vector3 normalized() {
            float norm = norm();
            return new Vector3(this.timeMillisSinceBoot, this.f1612x / norm, this.f1613y / norm, this.f1614z / norm);
        }

        public float angleBetween(Vector3 vector3) {
            float abs = Math.abs((float) Math.toDegrees(Math.atan2(cross(vector3).norm(), dotProduct(vector3))));
            Slog.d("AnyMotionDetector", "angleBetween: this = " + toString() + ", other = " + vector3.toString() + ", degrees = " + abs);
            return abs;
        }

        public Vector3 cross(Vector3 vector3) {
            long j = vector3.timeMillisSinceBoot;
            float f = this.f1613y;
            float f2 = vector3.f1614z;
            float f3 = this.f1614z;
            float f4 = vector3.f1613y;
            float f5 = vector3.f1612x;
            float f6 = this.f1612x;
            return new Vector3(j, (f * f2) - (f3 * f4), (f3 * f5) - (f2 * f6), (f6 * f4) - (f * f5));
        }

        public String toString() {
            return ((("timeMillisSinceBoot=" + this.timeMillisSinceBoot) + " | x=" + this.f1612x) + ", y=" + this.f1613y) + ", z=" + this.f1614z;
        }

        public float dotProduct(Vector3 vector3) {
            return (this.f1612x * vector3.f1612x) + (this.f1613y * vector3.f1613y) + (this.f1614z * vector3.f1614z);
        }

        public Vector3 times(float f) {
            return new Vector3(this.timeMillisSinceBoot, this.f1612x * f, this.f1613y * f, this.f1614z * f);
        }

        public Vector3 plus(Vector3 vector3) {
            return new Vector3(vector3.timeMillisSinceBoot, vector3.f1612x + this.f1612x, vector3.f1613y + this.f1613y, this.f1614z + vector3.f1614z);
        }

        public Vector3 minus(Vector3 vector3) {
            return new Vector3(vector3.timeMillisSinceBoot, this.f1612x - vector3.f1612x, this.f1613y - vector3.f1613y, this.f1614z - vector3.f1614z);
        }
    }

    public class RunningSignalStats {
        public Vector3 currentVector;
        public float energy;
        public Vector3 previousVector;
        public Vector3 runningSum;
        public int sampleCount;

        public RunningSignalStats() {
            reset();
        }

        public void reset() {
            this.previousVector = null;
            this.currentVector = null;
            this.runningSum = new Vector3(0L, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            this.energy = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            this.sampleCount = 0;
        }

        public void accumulate(Vector3 vector3) {
            if (vector3 == null) {
                return;
            }
            this.sampleCount++;
            this.runningSum = this.runningSum.plus(vector3);
            Vector3 vector32 = this.currentVector;
            this.previousVector = vector32;
            this.currentVector = vector3;
            if (vector32 != null) {
                Vector3 minus = vector3.minus(vector32);
                float f = minus.f1612x;
                float f2 = minus.f1613y;
                float f3 = minus.f1614z;
                this.energy += (f * f) + (f2 * f2) + (f3 * f3);
            }
        }

        public Vector3 getRunningAverage() {
            int i = this.sampleCount;
            if (i > 0) {
                return this.runningSum.times(1.0f / i);
            }
            return null;
        }

        public float getEnergy() {
            return this.energy;
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public String toString() {
            Vector3 vector3 = this.currentVector;
            String vector32 = vector3 == null ? "null" : vector3.toString();
            Vector3 vector33 = this.previousVector;
            return ((("previousVector = " + (vector33 != null ? vector33.toString() : "null")) + ", currentVector = " + vector32) + ", sampleCount = " + this.sampleCount) + ", energy = " + this.energy;
        }
    }
}
