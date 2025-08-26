package com.android.internal.os;

import android.os.StrictMode;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class KernelCpuSpeedReader {
    private static final String TAG = "KernelCpuSpeedReader";
    private final long[] mDeltaSpeedTimesMs;
    private final long mJiffyMillis = 1000 / Os.sysconf(OsConstants._SC_CLK_TCK);
    private final long[] mLastSpeedTimesMs;
    private final int mNumSpeedSteps;
    private final String mProcFile;

    public KernelCpuSpeedReader(int i, int i2) {
        this.mProcFile = String.format("/sys/devices/system/cpu/cpu%d/cpufreq/stats/time_in_state", Integer.valueOf(i));
        this.mNumSpeedSteps = i2;
        this.mLastSpeedTimesMs = new long[i2];
        this.mDeltaSpeedTimesMs = new long[i2];
    }

    public long[] readDelta() {
        String line;
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(this.mProcFile));
                try {
                    TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
                    for (int i = 0; i < this.mLastSpeedTimesMs.length && (line = bufferedReader.readLine()) != null; i++) {
                        simpleStringSplitter.setString(line);
                        simpleStringSplitter.next();
                        long j = Long.parseLong(simpleStringSplitter.next()) * this.mJiffyMillis;
                        long[] jArr = this.mLastSpeedTimesMs;
                        long j2 = jArr[i];
                        if (j < j2) {
                            this.mDeltaSpeedTimesMs[i] = j;
                        } else {
                            this.mDeltaSpeedTimesMs[i] = j - j2;
                        }
                        jArr[i] = j;
                    }
                    bufferedReader.close();
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                Slog.e(TAG, "Failed to read cpu-freq: " + e.getMessage());
                Arrays.fill(this.mDeltaSpeedTimesMs, 0L);
            }
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            return this.mDeltaSpeedTimesMs;
        } catch (Throwable th3) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th3;
        }
    }

    public long[] readAbsolute() {
        String line;
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        long[] jArr = new long[this.mNumSpeedSteps];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.mProcFile));
            try {
                TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
                for (int i = 0; i < this.mNumSpeedSteps && (line = bufferedReader.readLine()) != null; i++) {
                    simpleStringSplitter.setString(line);
                    simpleStringSplitter.next();
                    jArr[i] = Long.parseLong(simpleStringSplitter.next()) * this.mJiffyMillis;
                }
                bufferedReader.close();
                return jArr;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Slog.e(TAG, "Failed to read cpu-freq: " + e.getMessage());
            Arrays.fill(jArr, 0L);
            return jArr;
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }
}
