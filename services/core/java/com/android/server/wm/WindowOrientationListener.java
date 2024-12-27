package com.android.server.wm;

import android.R;
import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.rotationresolver.RotationResolverInternal;
import android.util.Slog;
import android.view.Surface;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0;
import com.android.server.wm.WindowOrientationListener;
import java.io.PrintWriter;
import java.util.Set;

public abstract class WindowOrientationListener {
    public static final boolean LOG = SystemProperties.getBoolean("debug.orientation.log", false);
    public final Context mContext;
    public final int mDefaultRotation;
    public final Sensor mDeviceInfoSensor;
    public boolean mEnabled;
    public final Handler mHandler;
    OrientationJudge mOrientationJudge;
    public final int mRate;
    RotationResolverInternal mRotationResolverService;
    public final Sensor mSensor;
    public final SensorManager mSensorManager;
    public int mCurrentRotation = -1;
    public final Object mLock = new Object();
    public int mTableMode = -1;
    public int mProposedTableMode = -1;
    public int mLastSensorRotation = -1;

    public final class AccelSensorJudge extends OrientationJudge {
        public boolean mAccelerating;
        public long mAccelerationTimestampNanos;
        public boolean mFlat;
        public long mFlatTimestampNanos;
        public long mLastFilteredTimestampNanos;
        public float mLastFilteredX;
        public float mLastFilteredY;
        public float mLastFilteredZ;
        public boolean mOverhead;
        public int mPredictedRotation;
        public long mPredictedRotationTimestampNanos;
        public int mProposedRotation;
        public long mSwingTimestampNanos;
        public boolean mSwinging;
        public int mTiltHistoryIndex;
        public boolean mTouched;
        public final int[][] mTiltToleranceConfig = {new int[]{-25, 70}, new int[]{-25, 65}, new int[]{-25, 60}, new int[]{-25, 65}};
        public long mTouchEndedTimestampNanos = Long.MIN_VALUE;
        public final float[] mTiltHistory = new float[200];
        public final long[] mTiltHistoryTimestampNanos = new long[200];

        public AccelSensorJudge(Context context) {
            int[] intArray = context.getResources().getIntArray(R.array.config_notificationFallbackVibeWaveform);
            if (intArray.length != 8) {
                Slog.wtf("WindowOrientationListener", "config_autoRotationTiltTolerance should have exactly 8 elements");
                return;
            }
            for (int i = 0; i < 4; i++) {
                int i2 = i * 2;
                int i3 = intArray[i2];
                int i4 = intArray[i2 + 1];
                if (i3 < -90 || i3 > i4 || i4 > 90) {
                    Slog.wtf("WindowOrientationListener", "config_autoRotationTiltTolerance contains invalid range: min=" + i3 + ", max=" + i4);
                } else {
                    int[] iArr = this.mTiltToleranceConfig[i];
                    iArr[0] = i3;
                    iArr[1] = i4;
                }
            }
        }

        public static float remainingMS(long j, long j2) {
            return j >= j2 ? FullScreenMagnificationGestureHandler.MAX_SCALE : (j2 - j) * 1.0E-6f;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void dumpLocked(PrintWriter printWriter, String str) {
            printWriter.println(str + "AccelSensorJudge");
            String str2 = str + "  ";
            StringBuilder m = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str2, "mProposedRotation="), this.mProposedRotation, printWriter, str2, "mPredictedRotation="), this.mPredictedRotation, printWriter, str2, "mLastFilteredX=");
            m.append(this.mLastFilteredX);
            printWriter.println(m.toString());
            printWriter.println(str2 + "mLastFilteredY=" + this.mLastFilteredY);
            printWriter.println(str2 + "mLastFilteredZ=" + this.mLastFilteredZ);
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos() - this.mLastFilteredTimestampNanos;
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str2, "mLastFilteredTimestampNanos=");
            m2.append(this.mLastFilteredTimestampNanos);
            m2.append(" (");
            m2.append(elapsedRealtimeNanos * 1.0E-6f);
            m2.append("ms ago)");
            printWriter.println(m2.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("mTiltHistory={last: ");
            int nextTiltHistoryIndexLocked = nextTiltHistoryIndexLocked(this.mTiltHistoryIndex);
            sb.append(nextTiltHistoryIndexLocked >= 0 ? this.mTiltHistory[nextTiltHistoryIndexLocked] : Float.NaN);
            sb.append("}");
            printWriter.println(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append("mFlat=");
            StringBuilder m3 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb2, this.mFlat, printWriter, str2, "mSwinging="), this.mSwinging, printWriter, str2, "mAccelerating="), this.mAccelerating, printWriter, str2, "mOverhead="), this.mOverhead, printWriter, str2, "mTouched=");
            m3.append(this.mTouched);
            printWriter.println(m3.toString());
            printWriter.print(str2 + "mTiltToleranceConfig=[");
            for (int i = 0; i < 4; i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                printWriter.print("[");
                printWriter.print(this.mTiltToleranceConfig[i][0]);
                printWriter.print(", ");
                printWriter.print(this.mTiltToleranceConfig[i][1]);
                printWriter.print("]");
            }
            printWriter.println("]");
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final int getProposedRotationLocked() {
            return this.mProposedRotation;
        }

        public final int nextTiltHistoryIndexLocked(int i) {
            if (i == 0) {
                i = 200;
            }
            int i2 = i - 1;
            if (this.mTiltHistoryTimestampNanos[i2] != Long.MIN_VALUE) {
                return i2;
            }
            return -1;
        }

        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventCallback
        public final void onSensorAdditionalInfo(SensorAdditionalInfo sensorAdditionalInfo) {
        }

        /* JADX WARN: Code restructure failed: missing block: B:166:0x00cd, code lost:
        
            android.util.Slog.v("WindowOrientationListener", "Resetting orientation listener.");
         */
        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onSensorChanged(android.hardware.SensorEvent r26) {
            /*
                Method dump skipped, instructions count: 1011
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowOrientationListener.AccelSensorJudge.onSensorChanged(android.hardware.SensorEvent):void");
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void onTouchEndLocked(long j) {
            this.mTouched = false;
            this.mTouchEndedTimestampNanos = j;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void onTouchStartLocked() {
            this.mTouched = true;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void resetLocked() {
            this.mLastFilteredTimestampNanos = Long.MIN_VALUE;
            this.mProposedRotation = -1;
            this.mFlatTimestampNanos = Long.MIN_VALUE;
            this.mFlat = false;
            this.mSwingTimestampNanos = Long.MIN_VALUE;
            this.mSwinging = false;
            this.mAccelerationTimestampNanos = Long.MIN_VALUE;
            this.mAccelerating = false;
            this.mOverhead = false;
            this.mPredictedRotation = -1;
            this.mPredictedRotationTimestampNanos = Long.MIN_VALUE;
            this.mTiltHistoryTimestampNanos[0] = Long.MIN_VALUE;
            this.mTiltHistoryIndex = 1;
        }
    }

    public abstract class OrientationJudge extends SensorEventCallback {
        public abstract void dumpLocked(PrintWriter printWriter, String str);

        public abstract int getProposedRotationLocked();

        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener2
        public final void onFlushCompleted(Sensor sensor) {
        }

        public abstract void onTouchEndLocked(long j);

        public abstract void onTouchStartLocked();

        public abstract void resetLocked();
    }

    public final class OrientationSensorJudge extends OrientationJudge {
        public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
        public WindowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0 mCancelRotationResolverRequest;
        public long mLastRotationResolutionTimeStamp;
        public boolean mRotationEvaluationScheduled;
        public long mRotationMemorizationTimeoutMillis;
        public long mRotationResolverTimeoutMillis;
        public boolean mTouching;
        public boolean mUnfoldEventDetected;
        public long mTouchEndedTimestampNanos = Long.MIN_VALUE;
        public int mProposedRotation = -1;
        public int mDesiredRotation = -1;
        public int mLastRotationResolution = -1;
        public int mCurrentCallbackId = 0;
        public final AnonymousClass2 mRotationEvaluator = new Runnable() { // from class: com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.2
            @Override // java.lang.Runnable
            public final void run() {
                int evaluateRotationChangeLocked;
                synchronized (WindowOrientationListener.this.mLock) {
                    OrientationSensorJudge orientationSensorJudge = OrientationSensorJudge.this;
                    orientationSensorJudge.mRotationEvaluationScheduled = false;
                    evaluateRotationChangeLocked = orientationSensorJudge.evaluateRotationChangeLocked();
                }
                if (evaluateRotationChangeLocked >= -1) {
                    WindowOrientationListener.this.onProposedRotationChanged(evaluateRotationChangeLocked);
                }
            }
        };

        public OrientationSensorJudge() {
            DeviceConfig.addOnPropertiesChangedListener("window_manager", ActivityThread.currentApplication().getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wm.WindowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda1
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    WindowOrientationListener.OrientationSensorJudge orientationSensorJudge = WindowOrientationListener.OrientationSensorJudge.this;
                    orientationSensorJudge.getClass();
                    Set keyset = properties.getKeyset();
                    if (keyset.contains("rotation_resolver_timeout_millis") || keyset.contains("rotation_memorization_timeout_millis")) {
                        orientationSensorJudge.mRotationResolverTimeoutMillis = DeviceConfig.getLong("window_manager", "rotation_resolver_timeout_millis", 700L);
                        orientationSensorJudge.mRotationMemorizationTimeoutMillis = DeviceConfig.getLong("window_manager", "rotation_memorization_timeout_millis", 3000L);
                    }
                }
            });
            this.mRotationResolverTimeoutMillis = DeviceConfig.getLong("window_manager", "rotation_resolver_timeout_millis", 700L);
            this.mRotationMemorizationTimeoutMillis = DeviceConfig.getLong("window_manager", "rotation_memorization_timeout_millis", 3000L);
            this.mActivityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void dumpLocked(PrintWriter printWriter, String str) {
            printWriter.println(str + "OrientationSensorJudge");
            String str2 = str + "  ";
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, "mDesiredRotation=");
            m.append(Surface.rotationToString(this.mDesiredRotation));
            printWriter.println(m.toString());
            printWriter.println(str2 + "mProposedRotation=" + Surface.rotationToString(this.mProposedRotation));
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("mTouching=");
            StringBuilder m2 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb, this.mTouching, printWriter, str2, "mTouchEndedTimestampNanos=");
            m2.append(this.mTouchEndedTimestampNanos);
            printWriter.println(m2.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append("mLastRotationResolution=");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(sb2, this.mLastRotationResolution, printWriter);
        }

        public final int evaluateRotationChangeLocked() {
            if (this.mRotationEvaluationScheduled) {
                WindowOrientationListener.this.mHandler.removeCallbacks(this.mRotationEvaluator);
                this.mRotationEvaluationScheduled = false;
            }
            if (this.mDesiredRotation == this.mProposedRotation) {
                return -1;
            }
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            if (!this.mUnfoldEventDetected && (this.mTouching || elapsedRealtimeNanos < this.mTouchEndedTimestampNanos + 500000000)) {
                scheduleRotationEvaluationIfNecessaryLocked(elapsedRealtimeNanos);
                return -1;
            }
            int i = this.mDesiredRotation;
            this.mProposedRotation = i;
            return i;
        }

        public final void finalizeRotation(int i) {
            int evaluateRotationChangeLocked;
            synchronized (WindowOrientationListener.this.mLock) {
                this.mDesiredRotation = i;
                evaluateRotationChangeLocked = evaluateRotationChangeLocked();
            }
            if (evaluateRotationChangeLocked >= -1) {
                this.mLastRotationResolution = evaluateRotationChangeLocked;
                this.mLastRotationResolutionTimeStamp = SystemClock.uptimeMillis();
                WindowOrientationListener.this.onProposedRotationChanged(evaluateRotationChangeLocked);
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final int getProposedRotationLocked() {
            return this.mProposedRotation;
        }

        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventCallback
        public final void onSensorAdditionalInfo(SensorAdditionalInfo sensorAdditionalInfo) {
            if (sensorAdditionalInfo.type == 268435460) {
                int i = (int) sensorAdditionalInfo.floatValues[0];
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "onSensorAdditionalInfo, unfolding_info: ", "WindowOrientationListener");
                boolean z = i == 1;
                if (this.mUnfoldEventDetected != z) {
                    this.mUnfoldEventDetected = z;
                    DeviceIdleController$$ExternalSyntheticOutline0.m("onUnfoldEventChanged : ", "WindowOrientationListener", z);
                }
            }
        }

        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            String str;
            WindowProcessController windowProcessController;
            ApplicationInfo applicationInfo;
            int i;
            int i2;
            int i3 = 0;
            if (sensorEvent.sensor.getType() == 27) {
                WindowOrientationListener.this.mLastSensorRotation = (int) sensorEvent.values[0];
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("onSensorChanged device orientation: "), WindowOrientationListener.this.mLastSensorRotation, "WindowOrientationListener");
                WindowOrientationListener windowOrientationListener = WindowOrientationListener.this;
                if (windowOrientationListener.mTableMode == 0 && windowOrientationListener.mProposedTableMode != 0 && (i2 = windowOrientationListener.mLastSensorRotation) >= 0 && i2 != 2) {
                    windowOrientationListener.mProposedTableMode = 0;
                    Slog.d("WindowOrientationListener", "onTableModeChanged: Moving");
                }
            }
            if (sensorEvent.sensor.getType() == 65649) {
                float[] fArr = sensorEvent.values;
                if (fArr[0] == 3.0f) {
                    int i4 = (int) fArr[1];
                    if (i4 >= 0) {
                        WindowOrientationListener windowOrientationListener2 = WindowOrientationListener.this;
                        if (windowOrientationListener2.mTableMode != i4) {
                            windowOrientationListener2.mTableMode = i4;
                            Slog.d("WindowOrientationListener", "onSensorChanged device info: " + WindowOrientationListener.this.mTableMode + ", " + sensorEvent.values[2] + ", " + sensorEvent.values[3]);
                        }
                    }
                    WindowOrientationListener windowOrientationListener3 = WindowOrientationListener.this;
                    int i5 = windowOrientationListener3.mTableMode;
                    if (i5 == 1 && windowOrientationListener3.mProposedTableMode != 1) {
                        windowOrientationListener3.mProposedTableMode = 1;
                        Slog.d("WindowOrientationListener", "onTableModeChanged: Table");
                        return;
                    } else {
                        if (i5 != 0 || (i = windowOrientationListener3.mLastSensorRotation) < 0 || i == 2 || windowOrientationListener3.mProposedTableMode != 1) {
                            return;
                        }
                        windowOrientationListener3.mProposedTableMode = 0;
                        Slog.d("WindowOrientationListener", "onTableModeChanged: Moving");
                        return;
                    }
                }
                return;
            }
            final int i6 = (int) sensorEvent.values[0];
            if (i6 < -1 || i6 > 3) {
                return;
            }
            long j = sensorEvent.timestamp;
            if (i6 == 0) {
                i3 = 1;
            } else if (i6 == 1) {
                i3 = 2;
            } else if (i6 == 2) {
                i3 = 3;
            } else if (i6 == 3) {
                i3 = 4;
            }
            FrameworkStatsLog.write(FrameworkStatsLog.DEVICE_ROTATED, j, i3, 2);
            if (!WindowOrientationListener.this.isRotationResolverEnabled()) {
                finalizeRotation(i6);
                return;
            }
            if (DisplayRotation.this.mService.isKeyguardShowingAndNotOccluded()) {
                if (this.mLastRotationResolution == -1 || SystemClock.uptimeMillis() - this.mLastRotationResolutionTimeStamp >= this.mRotationMemorizationTimeoutMillis) {
                    finalizeRotation(WindowOrientationListener.this.mDefaultRotation);
                    return;
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Reusing the last rotation resolution: "), this.mLastRotationResolution, "WindowOrientationListener");
                    finalizeRotation(this.mLastRotationResolution);
                    return;
                }
            }
            WindowOrientationListener windowOrientationListener4 = WindowOrientationListener.this;
            if (windowOrientationListener4.mRotationResolverService == null) {
                windowOrientationListener4.mRotationResolverService = (RotationResolverInternal) LocalServices.getService(RotationResolverInternal.class);
                if (WindowOrientationListener.this.mRotationResolverService == null) {
                    finalizeRotation(i6);
                    return;
                }
            }
            ActivityTaskManagerInternal activityTaskManagerInternal = this.mActivityTaskManagerInternal;
            if (activityTaskManagerInternal == null || (windowProcessController = ActivityTaskManagerService.this.mTopApp) == null || (applicationInfo = windowProcessController.mInfo) == null || (str = applicationInfo.packageName) == null) {
                str = null;
            }
            String str2 = str;
            this.mCurrentCallbackId++;
            WindowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0 windowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0 = this.mCancelRotationResolverRequest;
            if (windowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0 != null) {
                WindowOrientationListener.this.mHandler.removeCallbacks(windowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0);
            }
            final CancellationSignal cancellationSignal = new CancellationSignal();
            ?? r2 = new Runnable() { // from class: com.android.server.wm.WindowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    cancellationSignal.cancel();
                }
            };
            this.mCancelRotationResolverRequest = r2;
            WindowOrientationListener.this.mHandler.postDelayed(r2, this.mRotationResolverTimeoutMillis);
            WindowOrientationListener.this.mRotationResolverService.resolveRotation(new RotationResolverInternal.RotationResolverCallbackInternal() { // from class: com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.1
                public final int mCallbackId;

                {
                    this.mCallbackId = OrientationSensorJudge.this.mCurrentCallbackId;
                }

                public final void finalizeRotationIfFresh(int i7) {
                    int i8 = this.mCallbackId;
                    OrientationSensorJudge orientationSensorJudge = OrientationSensorJudge.this;
                    int i9 = orientationSensorJudge.mCurrentCallbackId;
                    if (i8 != i9) {
                        Slog.d("WindowOrientationListener", DualAppManagerService$$ExternalSyntheticOutline0.m(i8, i9, "An outdated callback received [", " vs. ", "]. Ignoring it."));
                    } else {
                        WindowOrientationListener.this.mHandler.removeCallbacks(orientationSensorJudge.mCancelRotationResolverRequest);
                        OrientationSensorJudge.this.finalizeRotation(i7);
                    }
                }

                public final void onFailure(int i7) {
                    finalizeRotationIfFresh(i6);
                }

                public final void onSuccess(int i7) {
                    finalizeRotationIfFresh(i7);
                }
            }, str2, i6, WindowOrientationListener.this.mCurrentRotation, this.mRotationResolverTimeoutMillis, cancellationSignal);
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void onTouchEndLocked(long j) {
            this.mTouching = false;
            this.mTouchEndedTimestampNanos = j;
            if (this.mDesiredRotation != this.mProposedRotation) {
                scheduleRotationEvaluationIfNecessaryLocked(SystemClock.elapsedRealtimeNanos());
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void onTouchStartLocked() {
            this.mTouching = true;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public final void resetLocked() {
            this.mProposedRotation = -1;
            this.mDesiredRotation = -1;
            this.mTouching = false;
            this.mTouchEndedTimestampNanos = Long.MIN_VALUE;
            if (this.mRotationEvaluationScheduled) {
                WindowOrientationListener.this.mHandler.removeCallbacks(this.mRotationEvaluator);
                this.mRotationEvaluationScheduled = false;
            }
        }

        public final void scheduleRotationEvaluationIfNecessaryLocked(long j) {
            if (this.mRotationEvaluationScheduled || this.mDesiredRotation == this.mProposedRotation) {
                if (WindowOrientationListener.LOG) {
                    Slog.d("WindowOrientationListener", "scheduleRotationEvaluationLocked: ignoring, an evaluation is already scheduled or is unnecessary.");
                    return;
                }
                return;
            }
            if (this.mTouching && !this.mUnfoldEventDetected) {
                if (WindowOrientationListener.LOG) {
                    Slog.d("WindowOrientationListener", "scheduleRotationEvaluationLocked: ignoring, user is still touching the screen.");
                    return;
                }
                return;
            }
            if (j >= this.mTouchEndedTimestampNanos + 500000000) {
                if (WindowOrientationListener.LOG) {
                    Slog.d("WindowOrientationListener", "scheduleRotationEvaluationLocked: ignoring, already past the next possible time of rotation.");
                }
            } else {
                WindowOrientationListener.this.mHandler.postDelayed(this.mRotationEvaluator, (long) Math.ceil((r2 - j) * 1.0E-6f));
                this.mRotationEvaluationScheduled = true;
            }
        }
    }

    public WindowOrientationListener(Context context, Handler handler, int i) {
        this.mContext = context;
        this.mHandler = handler;
        Sensor sensor = null;
        this.mDeviceInfoSensor = null;
        this.mDefaultRotation = i;
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensorManager = sensorManager;
        this.mRate = 1;
        for (Sensor sensor2 : sensorManager.getSensorList(27)) {
            if (!sensor2.isWakeUpSensor()) {
                sensor = sensor2;
            }
        }
        this.mSensor = sensor;
        if (sensor != null) {
            this.mOrientationJudge = new OrientationSensorJudge();
        }
        Sensor defaultSensor = this.mSensorManager.getDefaultSensor(65649);
        this.mDeviceInfoSensor = defaultSensor;
        if (defaultSensor != null) {
            Slog.d("WindowOrientationListener", "supports device_common_info");
        }
        if (this.mOrientationJudge == null) {
            Sensor defaultSensor2 = this.mSensorManager.getDefaultSensor(1);
            this.mSensor = defaultSensor2;
            if (defaultSensor2 != null) {
                this.mOrientationJudge = new AccelSensorJudge(context);
            }
        }
    }

    public final void enable$1() {
        synchronized (this.mLock) {
            try {
                if (this.mSensor == null) {
                    Slog.w("WindowOrientationListener", "Cannot detect sensors. Not enabled");
                    return;
                }
                if (this.mEnabled) {
                    return;
                }
                if (LOG) {
                    Slog.d("WindowOrientationListener", "WindowOrientationListener enabled clearCurrentRotation=true");
                }
                this.mOrientationJudge.resetLocked();
                if (this.mSensor.getType() == 1) {
                    this.mSensorManager.registerListener(this.mOrientationJudge, this.mSensor, this.mRate, 100000, this.mHandler);
                } else {
                    Sensor sensor = this.mDeviceInfoSensor;
                    if (sensor != null) {
                        this.mTableMode = -1;
                        this.mProposedTableMode = -1;
                        this.mLastSensorRotation = -1;
                        this.mSensorManager.registerListener(this.mOrientationJudge, sensor, 3);
                    }
                    this.mSensorManager.registerListener(this.mOrientationJudge, this.mSensor, this.mRate, this.mHandler);
                }
                this.mEnabled = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getProposedRotation() {
        synchronized (this.mLock) {
            try {
                if (!this.mEnabled) {
                    return -1;
                }
                return this.mOrientationJudge.getProposedRotationLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract boolean isRotationResolverEnabled();

    public abstract void onProposedRotationChanged(int i);
}
