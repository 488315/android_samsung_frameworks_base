package com.android.server.power;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.TimeUtils;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class WirelessChargerDetector {
    public static final double MOVEMENT_ANGLE_COS_THRESHOLD = Math.cos(0.08726646259971647d);
    public boolean mAtRest;
    public boolean mDetectionInProgress;
    public long mDetectionStartTime;
    public float mFirstSampleX;
    public float mFirstSampleY;
    public float mFirstSampleZ;
    public final Sensor mGravitySensor;
    public final Handler mHandler;
    public float mLastSampleX;
    public float mLastSampleY;
    public float mLastSampleZ;
    public int mMovingSamples;
    public boolean mMustUpdateRestPosition;
    public boolean mPoweredWirelessly;
    public float mRestX;
    public float mRestY;
    public float mRestZ;
    public final SensorManager mSensorManager;
    public final PowerManagerService.SuspendBlockerImpl mSuspendBlocker;
    public int mTotalSamples;
    public final Object mLock = new Object();
    public final AnonymousClass1 mListener =
            new SensorEventListener() { // from class:
                                        // com.android.server.power.WirelessChargerDetector.1
                @Override // android.hardware.SensorEventListener
                public final void onAccuracyChanged(Sensor sensor, int i) {}

                @Override // android.hardware.SensorEventListener
                public final void onSensorChanged(SensorEvent sensorEvent) {
                    synchronized (WirelessChargerDetector.this.mLock) {
                        WirelessChargerDetector wirelessChargerDetector =
                                WirelessChargerDetector.this;
                        float[] fArr = sensorEvent.values;
                        WirelessChargerDetector.m829$$Nest$mprocessSampleLocked(
                                wirelessChargerDetector, fArr[0], fArr[1], fArr[2]);
                    }
                }
            };
    public final AnonymousClass2 mSensorTimeout =
            new Runnable() { // from class: com.android.server.power.WirelessChargerDetector.2
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (WirelessChargerDetector.this.mLock) {
                        WirelessChargerDetector.m828$$Nest$mfinishDetectionLocked(
                                WirelessChargerDetector.this);
                    }
                }
            };

    /* renamed from: -$$Nest$mfinishDetectionLocked, reason: not valid java name */
    public static void m828$$Nest$mfinishDetectionLocked(
            WirelessChargerDetector wirelessChargerDetector) {
        if (wirelessChargerDetector.mDetectionInProgress) {
            wirelessChargerDetector.mSensorManager.unregisterListener(
                    wirelessChargerDetector.mListener);
            wirelessChargerDetector.mHandler.removeCallbacks(
                    wirelessChargerDetector.mSensorTimeout);
            if (wirelessChargerDetector.mMustUpdateRestPosition) {
                wirelessChargerDetector.mAtRest = false;
                wirelessChargerDetector.mRestX = FullScreenMagnificationGestureHandler.MAX_SCALE;
                wirelessChargerDetector.mRestY = FullScreenMagnificationGestureHandler.MAX_SCALE;
                wirelessChargerDetector.mRestZ = FullScreenMagnificationGestureHandler.MAX_SCALE;
                if (wirelessChargerDetector.mTotalSamples < 3) {
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "Wireless charger detector is broken.  Only received "),
                            wirelessChargerDetector.mTotalSamples,
                            " samples from the gravity sensor but we need at least 3 and we expect"
                                + " to see about 16 on average.",
                            "WirelessChargerDetector");
                } else if (wirelessChargerDetector.mMovingSamples == 0) {
                    wirelessChargerDetector.mAtRest = true;
                    wirelessChargerDetector.mRestX = wirelessChargerDetector.mLastSampleX;
                    wirelessChargerDetector.mRestY = wirelessChargerDetector.mLastSampleY;
                    wirelessChargerDetector.mRestZ = wirelessChargerDetector.mLastSampleZ;
                }
                wirelessChargerDetector.mMustUpdateRestPosition = false;
            }
            wirelessChargerDetector.mDetectionInProgress = false;
            wirelessChargerDetector.mSuspendBlocker.release();
        }
    }

    /* renamed from: -$$Nest$mprocessSampleLocked, reason: not valid java name */
    public static void m829$$Nest$mprocessSampleLocked(
            WirelessChargerDetector wirelessChargerDetector, float f, float f2, float f3) {
        if (wirelessChargerDetector.mDetectionInProgress) {
            wirelessChargerDetector.mLastSampleX = f;
            wirelessChargerDetector.mLastSampleY = f2;
            wirelessChargerDetector.mLastSampleZ = f3;
            int i = wirelessChargerDetector.mTotalSamples + 1;
            wirelessChargerDetector.mTotalSamples = i;
            if (i == 1) {
                wirelessChargerDetector.mFirstSampleX = f;
                wirelessChargerDetector.mFirstSampleY = f2;
                wirelessChargerDetector.mFirstSampleZ = f3;
            } else if (hasMoved(
                    wirelessChargerDetector.mFirstSampleX,
                    wirelessChargerDetector.mFirstSampleY,
                    wirelessChargerDetector.mFirstSampleZ,
                    f,
                    f2,
                    f3)) {
                wirelessChargerDetector.mMovingSamples++;
            }
            if (wirelessChargerDetector.mAtRest
                    && hasMoved(
                            wirelessChargerDetector.mRestX,
                            wirelessChargerDetector.mRestY,
                            wirelessChargerDetector.mRestZ,
                            f,
                            f2,
                            f3)) {
                wirelessChargerDetector.mAtRest = false;
                wirelessChargerDetector.mRestX = FullScreenMagnificationGestureHandler.MAX_SCALE;
                wirelessChargerDetector.mRestY = FullScreenMagnificationGestureHandler.MAX_SCALE;
                wirelessChargerDetector.mRestZ = FullScreenMagnificationGestureHandler.MAX_SCALE;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.power.WirelessChargerDetector$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.power.WirelessChargerDetector$2] */
    public WirelessChargerDetector(
            SensorManager sensorManager,
            PowerManagerService.SuspendBlockerImpl suspendBlockerImpl,
            Handler handler) {
        this.mSensorManager = sensorManager;
        this.mSuspendBlocker = suspendBlockerImpl;
        this.mHandler = handler;
        this.mGravitySensor = sensorManager.getDefaultSensor(9);
    }

    public static boolean hasMoved(float f, float f2, float f3, float f4, float f5, float f6) {
        double d = (f3 * f6) + (f2 * f5) + (f * f4);
        double sqrt = Math.sqrt((f3 * f3) + (f2 * f2) + (f * f));
        double sqrt2 = Math.sqrt((f6 * f6) + (f5 * f5) + (f4 * f4));
        return sqrt < 8.806650161743164d
                || sqrt > 10.806650161743164d
                || sqrt2 < 8.806650161743164d
                || sqrt2 > 10.806650161743164d
                || d < (sqrt * sqrt2) * MOVEMENT_ANGLE_COS_THRESHOLD;
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println();
                printWriter.println("Wireless Charger Detector State:");
                printWriter.println("  mGravitySensor=" + this.mGravitySensor);
                printWriter.println("  mPoweredWirelessly=" + this.mPoweredWirelessly);
                printWriter.println("  mAtRest=" + this.mAtRest);
                printWriter.println(
                        "  mRestX="
                                + this.mRestX
                                + ", mRestY="
                                + this.mRestY
                                + ", mRestZ="
                                + this.mRestZ);
                StringBuilder sb = new StringBuilder("  mDetectionInProgress=");
                sb.append(this.mDetectionInProgress);
                printWriter.println(sb.toString());
                StringBuilder sb2 = new StringBuilder("  mDetectionStartTime=");
                long j = this.mDetectionStartTime;
                sb2.append(j == 0 ? "0 (never)" : TimeUtils.formatUptime(j));
                printWriter.println(sb2.toString());
                printWriter.println("  mMustUpdateRestPosition=" + this.mMustUpdateRestPosition);
                printWriter.println("  mTotalSamples=" + this.mTotalSamples);
                printWriter.println("  mMovingSamples=" + this.mMovingSamples);
                printWriter.println(
                        "  mFirstSampleX="
                                + this.mFirstSampleX
                                + ", mFirstSampleY="
                                + this.mFirstSampleY
                                + ", mFirstSampleZ="
                                + this.mFirstSampleZ);
                printWriter.println(
                        "  mLastSampleX="
                                + this.mLastSampleX
                                + ", mLastSampleY="
                                + this.mLastSampleY
                                + ", mLastSampleZ="
                                + this.mLastSampleZ);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startDetectionLocked() {
        Sensor sensor;
        if (this.mDetectionInProgress
                || (sensor = this.mGravitySensor) == null
                || !this.mSensorManager.registerListener(this.mListener, sensor, 50000)) {
            return;
        }
        this.mSuspendBlocker.acquire("unknown");
        this.mDetectionInProgress = true;
        this.mDetectionStartTime = SystemClock.uptimeMillis();
        this.mTotalSamples = 0;
        this.mMovingSamples = 0;
        Handler handler = this.mHandler;
        Message obtain = Message.obtain(handler, this.mSensorTimeout);
        obtain.setAsynchronous(true);
        handler.sendMessageDelayed(obtain, 800L);
    }
}
