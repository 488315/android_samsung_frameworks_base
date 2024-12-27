package com.android.server.vibrator;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.modules.expresslog.Histogram;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.vibrator.VibrationStats;
import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibratorFrameworkStatsLogger {
    public final Handler mHandler;
    public long mLastVibrationReportedLogUptime;
    public final long mVibrationReportedLogIntervalMillis;
    public final long mVibrationReportedQueueMaxSize;
    public static final Histogram sVibrationParamRequestLatencyHistogram = new Histogram("vibrator.value_vibration_param_request_latency", new Histogram.UniformOptions(20, FullScreenMagnificationGestureHandler.MAX_SCALE, 100.0f));
    public static final Histogram sVibrationParamScaleHistogram = new Histogram("vibrator.value_vibration_param_scale", new Histogram.UniformOptions(20, FullScreenMagnificationGestureHandler.MAX_SCALE, 2.0f));
    public static final Histogram sAdaptiveHapticScaleHistogram = new Histogram("vibrator.value_vibration_adaptive_haptic_scale", new Histogram.UniformOptions(20, FullScreenMagnificationGestureHandler.MAX_SCALE, 2.0f));
    public final Object mLock = new Object();
    public final VibratorFrameworkStatsLogger$$ExternalSyntheticLambda0 mConsumeVibrationStatsQueueRunnable = new Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            VibrationStats.StatsInfo statsInfo;
            boolean z;
            VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = VibratorFrameworkStatsLogger.this;
            synchronized (vibratorFrameworkStatsLogger.mLock) {
                try {
                    statsInfo = (VibrationStats.StatsInfo) ((ArrayDeque) vibratorFrameworkStatsLogger.mVibrationStatsQueue).poll();
                    z = !((ArrayDeque) vibratorFrameworkStatsLogger.mVibrationStatsQueue).isEmpty();
                    if (statsInfo != null) {
                        vibratorFrameworkStatsLogger.mLastVibrationReportedLogUptime = SystemClock.uptimeMillis();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (statsInfo == null) {
                Slog.w("VibratorFrameworkStatsLogger", "Unexpected vibration metric flush with empty queue. Ignoring.");
            } else {
                if (statsInfo.mIsWritten) {
                    Slog.wtf("VibrationStats", "Writing same vibration stats multiple times for uid=" + statsInfo.uid);
                }
                statsInfo.mIsWritten = true;
                FrameworkStatsLog.write_non_chained(FrameworkStatsLog.VIBRATION_REPORTED, statsInfo.uid, null, statsInfo.vibrationType, statsInfo.usage, statsInfo.status, statsInfo.endedBySameUid, statsInfo.endedByUsage, statsInfo.interruptedUsage, statsInfo.repeatCount, statsInfo.totalDurationMillis, statsInfo.vibratorOnMillis, statsInfo.startLatencyMillis, statsInfo.endLatencyMillis, statsInfo.halComposeCount, statsInfo.halComposePwleCount, statsInfo.halOnCount, statsInfo.halOffCount, statsInfo.halPerformCount, statsInfo.halSetAmplitudeCount, statsInfo.halSetExternalControlCount, statsInfo.halSupportedCompositionPrimitivesUsed, statsInfo.halSupportedEffectsUsed, statsInfo.halUnsupportedCompositionPrimitivesUsed, statsInfo.halUnsupportedEffectsUsed, statsInfo.halCompositionSize, statsInfo.halPwleSize, statsInfo.adaptiveScale);
            }
            if (z) {
                vibratorFrameworkStatsLogger.mHandler.postDelayed(vibratorFrameworkStatsLogger.mConsumeVibrationStatsQueueRunnable, vibratorFrameworkStatsLogger.mVibrationReportedLogIntervalMillis);
            }
        }
    };
    public final Queue mVibrationStatsQueue = new ArrayDeque();

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda0] */
    public VibratorFrameworkStatsLogger(Handler handler, int i, int i2) {
        this.mHandler = handler;
        this.mVibrationReportedLogIntervalMillis = i;
        this.mVibrationReportedQueueMaxSize = i2;
    }

    public final void writeVibrationReportedAsync(VibrationStats.StatsInfo statsInfo) {
        int size;
        boolean z;
        long max;
        synchronized (this.mLock) {
            try {
                size = ((ArrayDeque) this.mVibrationStatsQueue).size();
                z = size == 0;
                if (size < this.mVibrationReportedQueueMaxSize) {
                    ((ArrayDeque) this.mVibrationStatsQueue).offer(statsInfo);
                }
                max = Math.max(0L, (this.mLastVibrationReportedLogUptime + this.mVibrationReportedLogIntervalMillis) - SystemClock.uptimeMillis());
            } catch (Throwable th) {
                throw th;
            }
        }
        if (size + 1 == 200) {
            Slog.w("VibratorFrameworkStatsLogger", " Approaching vibration metrics queue limit, events might be dropped.");
        }
        if (z) {
            this.mHandler.postDelayed(this.mConsumeVibrationStatsQueueRunnable, max);
        }
    }
}
