package com.android.server;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AnyMotionDetector {
    public static final boolean DEBUG = Log.isLoggable("AnyMotionDetector", 3);
    public final Sensor mAccelSensor;
    public final DeviceIdleCallback mCallback;
    public Vector3 mCurrentGravityVector;
    public final Handler mHandler;
    public final AnonymousClass1 mListener;
    public final Object mLock;
    public boolean mMeasurementInProgress;
    public final AnonymousClass2 mMeasurementTimeout;
    public boolean mMeasurementTimeoutIsActive;
    public final int mNumSufficientSamples;
    public Vector3 mPreviousGravityVector;
    public final RunningSignalStats mRunningStats;
    public final SensorManager mSensorManager;
    public final AnonymousClass2 mSensorRestart;
    public boolean mSensorRestartIsActive;
    public int mState;
    public final float mThresholdAngle;
    public final PowerManager.WakeLock mWakeLock;
    public final AnonymousClass2 mWakelockTimeout;
    public volatile boolean mWakelockTimeoutIsActive;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DeviceIdleCallback {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RunningSignalStats {
        public Vector3 currentVector;
        public float energy;
        public Vector3 previousVector;
        public Vector3 runningSum;
        public int sampleCount;

        public final void accumulate(Vector3 vector3) {
            this.sampleCount++;
            Vector3 vector32 = this.runningSum;
            vector32.getClass();
            float f = vector32.x;
            float f2 = vector3.x;
            float f3 = f + f2;
            float f4 = vector32.y;
            float f5 = vector3.y;
            float f6 = vector32.z;
            float f7 = vector3.z;
            this.runningSum = new Vector3(f3, f4 + f5, f6 + f7, vector3.timeMillisSinceBoot);
            Vector3 vector33 = this.currentVector;
            this.previousVector = vector33;
            this.currentVector = vector3;
            if (vector33 != null) {
                float f8 = f2 - vector33.x;
                float f9 = f5 - vector33.y;
                float f10 = f7 - vector33.z;
                float f11 = (f10 * f10) + (f9 * f9) + (f8 * f8);
                this.energy += f11;
                if (AnyMotionDetector.DEBUG) {
                    Slog.i(
                            "AnyMotionDetector",
                            "Accumulated vector "
                                    + this.currentVector.toString()
                                    + ", runningSum = "
                                    + this.runningSum.toString()
                                    + ", incrementalEnergy = "
                                    + f11
                                    + ", energy = "
                                    + this.energy);
                }
            }
        }

        public final void reset() {
            this.previousVector = null;
            this.currentVector = null;
            this.runningSum =
                    new Vector3(
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            0L);
            this.energy = FullScreenMagnificationGestureHandler.MAX_SCALE;
            this.sampleCount = 0;
        }

        public final String toString() {
            Vector3 vector3 = this.currentVector;
            String vector32 = vector3 == null ? "null" : vector3.toString();
            Vector3 vector33 = this.previousVector;
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                            "previousVector = ",
                                            vector33 != null ? vector33.toString() : "null"),
                                    ", currentVector = ",
                                    vector32),
                            ", sampleCount = ");
            m.append(this.sampleCount);
            StringBuilder m2 =
                    Preconditions$$ExternalSyntheticOutline0.m(m.toString(), ", energy = ");
            m2.append(this.energy);
            return m2.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Vector3 {
        public final long timeMillisSinceBoot;
        public final float x;
        public final float y;
        public final float z;

        public Vector3(float f, float f2, float f3, long j) {
            this.timeMillisSinceBoot = j;
            this.x = f;
            this.y = f2;
            this.z = f3;
        }

        public final String toString() {
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            "timeMillisSinceBoot=" + this.timeMillisSinceBoot, " | x=");
            m.append(this.x);
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(m.toString(), ", y=");
            m2.append(this.y);
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(m2.toString(), ", z=");
            m3.append(this.z);
            return m3.toString();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0205  */
    /* renamed from: -$$Nest$mstopOrientationMeasurementLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int m38$$Nest$mstopOrientationMeasurementLocked(
            com.android.server.AnyMotionDetector r18) {
        /*
            Method dump skipped, instructions count: 544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.AnyMotionDetector.m38$$Nest$mstopOrientationMeasurementLocked(com.android.server.AnyMotionDetector):int");
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.AnyMotionDetector$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.AnyMotionDetector$2] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.AnyMotionDetector$2] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.server.AnyMotionDetector$2] */
    public AnyMotionDetector(
            PowerManager powerManager,
            Handler handler,
            SensorManager sensorManager,
            DeviceIdleCallback deviceIdleCallback,
            float f) {
        Object obj = new Object();
        this.mLock = obj;
        this.mCurrentGravityVector = null;
        this.mPreviousGravityVector = null;
        this.mListener =
                new SensorEventListener() { // from class: com.android.server.AnyMotionDetector.1
                    @Override // android.hardware.SensorEventListener
                    public final void onAccuracyChanged(Sensor sensor, int i) {}

                    @Override // android.hardware.SensorEventListener
                    public final void onSensorChanged(SensorEvent sensorEvent) {
                        int m38$$Nest$mstopOrientationMeasurementLocked;
                        synchronized (AnyMotionDetector.this.mLock) {
                            try {
                                long elapsedRealtime = SystemClock.elapsedRealtime();
                                float[] fArr = sensorEvent.values;
                                AnyMotionDetector.this.mRunningStats.accumulate(
                                        new Vector3(fArr[0], fArr[1], fArr[2], elapsedRealtime));
                                AnyMotionDetector anyMotionDetector = AnyMotionDetector.this;
                                m38$$Nest$mstopOrientationMeasurementLocked =
                                        anyMotionDetector.mRunningStats.sampleCount
                                                        >= anyMotionDetector.mNumSufficientSamples
                                                ? AnyMotionDetector
                                                        .m38$$Nest$mstopOrientationMeasurementLocked(
                                                                anyMotionDetector)
                                                : -1;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (m38$$Nest$mstopOrientationMeasurementLocked != -1) {
                            AnyMotionDetector anyMotionDetector2 = AnyMotionDetector.this;
                            anyMotionDetector2.mHandler.removeCallbacks(
                                    anyMotionDetector2.mWakelockTimeout);
                            AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                            ((DeviceIdleController) AnyMotionDetector.this.mCallback)
                                    .onAnyMotionResult(m38$$Nest$mstopOrientationMeasurementLocked);
                        }
                    }
                };
        final int i = 0;
        this.mSensorRestart =
                new Runnable(this) { // from class: com.android.server.AnyMotionDetector.2
                    public final /* synthetic */ AnyMotionDetector this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2;
                        switch (i) {
                            case 0:
                                synchronized (this.this$0.mLock) {
                                    AnyMotionDetector anyMotionDetector = this.this$0;
                                    if (anyMotionDetector.mSensorRestartIsActive) {
                                        anyMotionDetector.mSensorRestartIsActive = false;
                                        anyMotionDetector.startOrientationMeasurementLocked();
                                    }
                                }
                                return;
                            case 1:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        AnyMotionDetector anyMotionDetector2 = this.this$0;
                                        if (anyMotionDetector2.mMeasurementTimeoutIsActive) {
                                            anyMotionDetector2.mMeasurementTimeoutIsActive = false;
                                            if (AnyMotionDetector.DEBUG) {
                                                Slog.i(
                                                        "AnyMotionDetector",
                                                        "mMeasurementTimeout. Failed to collect"
                                                            + " sufficient accel data within 3000"
                                                            + " ms. Stopping orientation"
                                                            + " measurement.");
                                            }
                                            i2 =
                                                    AnyMotionDetector
                                                            .m38$$Nest$mstopOrientationMeasurementLocked(
                                                                    this.this$0);
                                        } else {
                                            i2 = -1;
                                        }
                                    } finally {
                                    }
                                }
                                if (i2 != -1) {
                                    AnyMotionDetector anyMotionDetector3 = this.this$0;
                                    anyMotionDetector3.mHandler.removeCallbacks(
                                            anyMotionDetector3.mWakelockTimeout);
                                    this.this$0.mWakelockTimeoutIsActive = false;
                                    ((DeviceIdleController) this.this$0.mCallback)
                                            .onAnyMotionResult(i2);
                                    return;
                                }
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (this.this$0.mWakelockTimeoutIsActive) {
                                            this.this$0.mWakelockTimeoutIsActive = false;
                                            this.this$0.stop();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        this.mMeasurementTimeout =
                new Runnable(this) { // from class: com.android.server.AnyMotionDetector.2
                    public final /* synthetic */ AnyMotionDetector this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22;
                        switch (i2) {
                            case 0:
                                synchronized (this.this$0.mLock) {
                                    AnyMotionDetector anyMotionDetector = this.this$0;
                                    if (anyMotionDetector.mSensorRestartIsActive) {
                                        anyMotionDetector.mSensorRestartIsActive = false;
                                        anyMotionDetector.startOrientationMeasurementLocked();
                                    }
                                }
                                return;
                            case 1:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        AnyMotionDetector anyMotionDetector2 = this.this$0;
                                        if (anyMotionDetector2.mMeasurementTimeoutIsActive) {
                                            anyMotionDetector2.mMeasurementTimeoutIsActive = false;
                                            if (AnyMotionDetector.DEBUG) {
                                                Slog.i(
                                                        "AnyMotionDetector",
                                                        "mMeasurementTimeout. Failed to collect"
                                                            + " sufficient accel data within 3000"
                                                            + " ms. Stopping orientation"
                                                            + " measurement.");
                                            }
                                            i22 =
                                                    AnyMotionDetector
                                                            .m38$$Nest$mstopOrientationMeasurementLocked(
                                                                    this.this$0);
                                        } else {
                                            i22 = -1;
                                        }
                                    } finally {
                                    }
                                }
                                if (i22 != -1) {
                                    AnyMotionDetector anyMotionDetector3 = this.this$0;
                                    anyMotionDetector3.mHandler.removeCallbacks(
                                            anyMotionDetector3.mWakelockTimeout);
                                    this.this$0.mWakelockTimeoutIsActive = false;
                                    ((DeviceIdleController) this.this$0.mCallback)
                                            .onAnyMotionResult(i22);
                                    return;
                                }
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (this.this$0.mWakelockTimeoutIsActive) {
                                            this.this$0.mWakelockTimeoutIsActive = false;
                                            this.this$0.stop();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i3 = 2;
        this.mWakelockTimeout =
                new Runnable(this) { // from class: com.android.server.AnyMotionDetector.2
                    public final /* synthetic */ AnyMotionDetector this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22;
                        switch (i3) {
                            case 0:
                                synchronized (this.this$0.mLock) {
                                    AnyMotionDetector anyMotionDetector = this.this$0;
                                    if (anyMotionDetector.mSensorRestartIsActive) {
                                        anyMotionDetector.mSensorRestartIsActive = false;
                                        anyMotionDetector.startOrientationMeasurementLocked();
                                    }
                                }
                                return;
                            case 1:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        AnyMotionDetector anyMotionDetector2 = this.this$0;
                                        if (anyMotionDetector2.mMeasurementTimeoutIsActive) {
                                            anyMotionDetector2.mMeasurementTimeoutIsActive = false;
                                            if (AnyMotionDetector.DEBUG) {
                                                Slog.i(
                                                        "AnyMotionDetector",
                                                        "mMeasurementTimeout. Failed to collect"
                                                            + " sufficient accel data within 3000"
                                                            + " ms. Stopping orientation"
                                                            + " measurement.");
                                            }
                                            i22 =
                                                    AnyMotionDetector
                                                            .m38$$Nest$mstopOrientationMeasurementLocked(
                                                                    this.this$0);
                                        } else {
                                            i22 = -1;
                                        }
                                    } finally {
                                    }
                                }
                                if (i22 != -1) {
                                    AnyMotionDetector anyMotionDetector3 = this.this$0;
                                    anyMotionDetector3.mHandler.removeCallbacks(
                                            anyMotionDetector3.mWakelockTimeout);
                                    this.this$0.mWakelockTimeoutIsActive = false;
                                    ((DeviceIdleController) this.this$0.mCallback)
                                            .onAnyMotionResult(i22);
                                    return;
                                }
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (this.this$0.mWakelockTimeoutIsActive) {
                                            this.this$0.mWakelockTimeoutIsActive = false;
                                            this.this$0.stop();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        boolean z = DEBUG;
        if (z) {
            Slog.d("AnyMotionDetector", "AnyMotionDetector instantiated.");
        }
        synchronized (obj) {
            try {
                PowerManager.WakeLock newWakeLock =
                        powerManager.newWakeLock(1, "AnyMotionDetector");
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
                RunningSignalStats runningSignalStats = new RunningSignalStats();
                runningSignalStats.reset();
                this.mRunningStats = runningSignalStats;
                int ceil = (int) Math.ceil(62.5d);
                this.mNumSufficientSamples = ceil;
                if (z) {
                    Slog.d("AnyMotionDetector", "mNumSufficientSamples = " + ceil);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startOrientationMeasurementLocked() {
        Sensor sensor;
        if (DEBUG) {
            StringBuilder sb =
                    new StringBuilder("startOrientationMeasurementLocked: mMeasurementInProgress=");
            sb.append(this.mMeasurementInProgress);
            sb.append(", (mAccelSensor != null)=");
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "AnyMotionDetector", sb, this.mAccelSensor != null);
        }
        if (this.mMeasurementInProgress || (sensor = this.mAccelSensor) == null) {
            return;
        }
        if (this.mSensorManager.registerListener(this.mListener, sensor, 40000)) {
            this.mMeasurementInProgress = true;
            this.mRunningStats.reset();
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(Message.obtain(handler, this.mMeasurementTimeout), 3000L);
        this.mMeasurementTimeoutIsActive = true;
    }

    public final void stop() {
        synchronized (this.mLock) {
            try {
                if (this.mState == 1) {
                    this.mState = 0;
                    if (DEBUG) {
                        Slog.d("AnyMotionDetector", "Moved from STATE_ACTIVE to STATE_INACTIVE.");
                    }
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
