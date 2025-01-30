package com.android.server.vibrator;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.vibrator.VibrationStats;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes3.dex */
public class VibratorFrameworkStatsLogger {
    public final Runnable mConsumeVibrationStatsQueueRunnable;
    public final Handler mHandler;
    public long mLastVibrationReportedLogUptime;
    public final Object mLock;
    public final long mVibrationReportedLogIntervalMillis;
    public final long mVibrationReportedQueueMaxSize;
    public Queue mVibrationStatsQueue;

    public VibratorFrameworkStatsLogger(Handler handler) {
        this(handler, 10, 300);
    }

    public VibratorFrameworkStatsLogger(Handler handler, int i, int i2) {
        this.mLock = new Object();
        this.mConsumeVibrationStatsQueueRunnable = new Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VibratorFrameworkStatsLogger.this.lambda$new$0();
            }
        };
        this.mVibrationStatsQueue = new ArrayDeque();
        this.mHandler = handler;
        this.mVibrationReportedLogIntervalMillis = i;
        this.mVibrationReportedQueueMaxSize = i2;
    }

    public void writeVibratorStateOnAsync(final int i, final long j) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                FrameworkStatsLog.write_non_chained(84, i, (String) null, 1, j);
            }
        });
    }

    public void writeVibratorStateOffAsync(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FrameworkStatsLog.write_non_chained(84, i, (String) null, 0, 0);
            }
        });
    }

    public void writeVibrationReportedAsync(VibrationStats.StatsInfo statsInfo) {
        int size;
        boolean z;
        long max;
        synchronized (this.mLock) {
            size = this.mVibrationStatsQueue.size();
            z = size == 0;
            if (size < this.mVibrationReportedQueueMaxSize) {
                this.mVibrationStatsQueue.offer(statsInfo);
            }
            max = Math.max(0L, (this.mLastVibrationReportedLogUptime + this.mVibrationReportedLogIntervalMillis) - SystemClock.uptimeMillis());
        }
        if (size + 1 == 200) {
            Slog.w("VibratorFrameworkStatsLogger", " Approaching vibration metrics queue limit, events might be dropped.");
        }
        if (z) {
            this.mHandler.postDelayed(this.mConsumeVibrationStatsQueueRunnable, max);
        }
    }

    /* renamed from: writeVibrationReportedFromQueue, reason: merged with bridge method [inline-methods] */
    public final void lambda$new$0() {
        VibrationStats.StatsInfo statsInfo;
        boolean z;
        synchronized (this.mLock) {
            statsInfo = (VibrationStats.StatsInfo) this.mVibrationStatsQueue.poll();
            z = !this.mVibrationStatsQueue.isEmpty();
            if (statsInfo != null) {
                this.mLastVibrationReportedLogUptime = SystemClock.uptimeMillis();
            }
        }
        if (statsInfo == null) {
            Slog.w("VibratorFrameworkStatsLogger", "Unexpected vibration metric flush with empty queue. Ignoring.");
        } else {
            statsInfo.writeVibrationReported();
        }
        if (z) {
            this.mHandler.postDelayed(this.mConsumeVibrationStatsQueueRunnable, this.mVibrationReportedLogIntervalMillis);
        }
    }
}
