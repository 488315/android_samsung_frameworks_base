package com.android.internal.util;

import android.hardware.scontext.SContextConstants;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import java.io.PrintWriter;

/* loaded from: classes5.dex */
public class StatLogger {
    private static final String TAG = "StatLogger";
    private final int SIZE;
    private final int[] mCallsPerSecond;
    private final int[] mCountStats;
    private final long[] mDurationPerSecond;
    private final long[] mDurationStats;
    private final String[] mLabels;
    private final Object mLock;
    private final int[] mMaxCallsPerSecond;
    private final long[] mMaxDurationPerSecond;
    private final long[] mMaxDurationStats;
    private long mNextTickTime;
    private final String mStatsTag;

    public StatLogger(String[] eventLabels) {
        this(null, eventLabels);
    }

    public StatLogger(String statsTag, String[] eventLabels) {
        this.mLock = new Object();
        this.mNextTickTime = SystemClock.elapsedRealtime() + 1000;
        this.mStatsTag = statsTag;
        this.SIZE = eventLabels.length;
        this.mCountStats = new int[this.SIZE];
        this.mDurationStats = new long[this.SIZE];
        this.mCallsPerSecond = new int[this.SIZE];
        this.mMaxCallsPerSecond = new int[this.SIZE];
        this.mDurationPerSecond = new long[this.SIZE];
        this.mMaxDurationPerSecond = new long[this.SIZE];
        this.mMaxDurationStats = new long[this.SIZE];
        this.mLabels = eventLabels;
    }

    public long getTime() {
        return SystemClock.uptimeNanos() / 1000;
    }

    public long logDurationStat(int eventId, long start) {
        synchronized (this.mLock) {
            long duration = getTime() - start;
            if (eventId >= 0 && eventId < this.SIZE) {
                int[] iArr = this.mCountStats;
                iArr[eventId] = iArr[eventId] + 1;
                long[] jArr = this.mDurationStats;
                jArr[eventId] = jArr[eventId] + duration;
                if (this.mMaxDurationStats[eventId] < duration) {
                    this.mMaxDurationStats[eventId] = duration;
                }
                long nowRealtime = SystemClock.elapsedRealtime();
                if (nowRealtime > this.mNextTickTime) {
                    if (this.mMaxCallsPerSecond[eventId] < this.mCallsPerSecond[eventId]) {
                        this.mMaxCallsPerSecond[eventId] = this.mCallsPerSecond[eventId];
                    }
                    if (this.mMaxDurationPerSecond[eventId] < this.mDurationPerSecond[eventId]) {
                        this.mMaxDurationPerSecond[eventId] = this.mDurationPerSecond[eventId];
                    }
                    this.mCallsPerSecond[eventId] = 0;
                    this.mDurationPerSecond[eventId] = 0;
                    this.mNextTickTime = 1000 + nowRealtime;
                }
                int[] iArr2 = this.mCallsPerSecond;
                iArr2[eventId] = iArr2[eventId] + 1;
                long[] jArr2 = this.mDurationPerSecond;
                jArr2[eventId] = jArr2[eventId] + duration;
                return duration;
            }
            Slog.wtf(TAG, "Invalid event ID: " + eventId);
            return duration;
        }
    }

    public void dump(PrintWriter pw, String prefix) {
        dump(new android.util.IndentingPrintWriter(pw, "  ").setIndent(prefix));
    }

    public void dump(android.util.IndentingPrintWriter pw) {
        synchronized (this.mLock) {
            if (!TextUtils.isEmpty(this.mStatsTag)) {
                pw.println(this.mStatsTag + ":");
            } else {
                pw.println("Stats:");
            }
            pw.increaseIndent();
            for (int i = 0; i < this.SIZE; i++) {
                int count = this.mCountStats[i];
                double durationMs = this.mDurationStats[i] / 1000.0d;
                pw.println(
                        String.format(
                                "%s: count=%d, total=%.1fms, avg=%.3fms, max calls/s=%d max"
                                    + " dur/s=%.1fms max time=%.1fms",
                                this.mLabels[i],
                                Integer.valueOf(count),
                                Double.valueOf(durationMs),
                                Double.valueOf(
                                        count == 0
                                                ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                                                : durationMs / count),
                                Integer.valueOf(this.mMaxCallsPerSecond[i]),
                                Double.valueOf(this.mMaxDurationPerSecond[i] / 1000.0d),
                                Double.valueOf(this.mMaxDurationStats[i] / 1000.0d)));
            }
            pw.decreaseIndent();
        }
    }

    public void dumpProto(ProtoOutputStream proto, long fieldId) {
        synchronized (this.mLock) {
            long outer = proto.start(fieldId);
            for (int i = 0; i < this.mLabels.length; i++) {
                long inner = proto.start(2246267895809L);
                proto.write(1120986464257L, i);
                proto.write(1138166333442L, this.mLabels[i]);
                proto.write(1120986464259L, this.mCountStats[i]);
                proto.write(1112396529668L, this.mDurationStats[i]);
                proto.write(1120986464261L, this.mMaxCallsPerSecond[i]);
                proto.write(1112396529670L, this.mMaxDurationPerSecond[i]);
                proto.write(1112396529671L, this.mMaxDurationStats[i]);
                proto.end(inner);
            }
            proto.end(outer);
        }
    }
}
