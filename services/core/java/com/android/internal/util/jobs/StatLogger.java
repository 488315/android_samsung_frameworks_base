package com.android.internal.util.jobs;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

    public StatLogger(String str, String[] strArr) {
        this.mLock = new Object();
        this.mNextTickTime = SystemClock.elapsedRealtime() + 1000;
        this.mStatsTag = str;
        int length = strArr.length;
        this.SIZE = length;
        this.mCountStats = new int[length];
        this.mDurationStats = new long[length];
        this.mCallsPerSecond = new int[length];
        this.mMaxCallsPerSecond = new int[length];
        this.mDurationPerSecond = new long[length];
        this.mMaxDurationPerSecond = new long[length];
        this.mMaxDurationStats = new long[length];
        this.mLabels = strArr;
    }

    public StatLogger(String[] strArr) {
        this(null, strArr);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                if (TextUtils.isEmpty(this.mStatsTag)) {
                    indentingPrintWriter.println("Stats:");
                } else {
                    indentingPrintWriter.println(this.mStatsTag + ":");
                }
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.SIZE; i++) {
                    int i2 = this.mCountStats[i];
                    double d = this.mDurationStats[i] / 1000.0d;
                    indentingPrintWriter.println(String.format("%s: count=%d, total=%.1fms, avg=%.3fms, max calls/s=%d max dur/s=%.1fms max time=%.1fms", this.mLabels[i], Integer.valueOf(i2), Double.valueOf(d), Double.valueOf(i2 == 0 ? 0.0d : d / i2), Integer.valueOf(this.mMaxCallsPerSecond[i]), Double.valueOf(this.mMaxDurationPerSecond[i] / 1000.0d), Double.valueOf(this.mMaxDurationStats[i] / 1000.0d)));
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        dump(new IndentingPrintWriter(printWriter, "  ").setIndent(str));
    }

    public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(j);
                for (int i = 0; i < this.mLabels.length; i++) {
                    long start2 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, i);
                    protoOutputStream.write(1138166333442L, this.mLabels[i]);
                    protoOutputStream.write(1120986464259L, this.mCountStats[i]);
                    protoOutputStream.write(1112396529668L, this.mDurationStats[i]);
                    protoOutputStream.write(1120986464261L, this.mMaxCallsPerSecond[i]);
                    protoOutputStream.write(1112396529670L, this.mMaxDurationPerSecond[i]);
                    protoOutputStream.write(1112396529671L, this.mMaxDurationStats[i]);
                    protoOutputStream.end(start2);
                }
                protoOutputStream.end(start);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public long getTime() {
        return SystemClock.uptimeNanos() / 1000;
    }

    public long logDurationStat(int i, long j) {
        synchronized (this.mLock) {
            try {
                long time = getTime() - j;
                if (i < 0 || i >= this.SIZE) {
                    Slog.wtf(TAG, "Invalid event ID: " + i);
                    return time;
                }
                int[] iArr = this.mCountStats;
                iArr[i] = iArr[i] + 1;
                long[] jArr = this.mDurationStats;
                jArr[i] = jArr[i] + time;
                long[] jArr2 = this.mMaxDurationStats;
                if (jArr2[i] < time) {
                    jArr2[i] = time;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime > this.mNextTickTime) {
                    int[] iArr2 = this.mMaxCallsPerSecond;
                    int i2 = iArr2[i];
                    int[] iArr3 = this.mCallsPerSecond;
                    int i3 = iArr3[i];
                    if (i2 < i3) {
                        iArr2[i] = i3;
                    }
                    long[] jArr3 = this.mMaxDurationPerSecond;
                    long j2 = jArr3[i];
                    long[] jArr4 = this.mDurationPerSecond;
                    long j3 = jArr4[i];
                    if (j2 < j3) {
                        jArr3[i] = j3;
                    }
                    iArr3[i] = 0;
                    jArr4[i] = 0;
                    this.mNextTickTime = elapsedRealtime + 1000;
                }
                int[] iArr4 = this.mCallsPerSecond;
                iArr4[i] = iArr4[i] + 1;
                long[] jArr5 = this.mDurationPerSecond;
                jArr5[i] = jArr5[i] + time;
                return time;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
