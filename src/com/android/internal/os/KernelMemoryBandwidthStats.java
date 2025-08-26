package com.android.internal.os;

import android.os.StrictMode;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.LongSparseLongArray;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes5.dex */
public class KernelMemoryBandwidthStats {
    private static final boolean DEBUG = false;
    private static final String TAG = "KernelMemoryBandwidthStats";
    private static final String mSysfsFile = "/sys/kernel/memory_state_time/show_stat";
    protected final LongSparseLongArray mBandwidthEntries = new LongSparseLongArray();
    private boolean mStatsDoNotExist = false;

    public void updateStats() {
        if (this.mStatsDoNotExist) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(mSysfsFile));
                try {
                    parseStats(bufferedReader);
                    bufferedReader.close();
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused) {
                Slog.w(TAG, "No kernel memory bandwidth stats available");
                this.mBandwidthEntries.clear();
                this.mStatsDoNotExist = true;
            } catch (IOException e) {
                Slog.e(TAG, "Failed to read memory bandwidth: " + e.getMessage());
                this.mBandwidthEntries.clear();
            }
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            long jUptimeMillis2 = SystemClock.uptimeMillis() - jUptimeMillis;
            if (jUptimeMillis2 > 100) {
                Slog.w(TAG, "Reading memory bandwidth file took " + jUptimeMillis2 + "ms");
            }
        } catch (Throwable th3) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th3;
        }
    }

    public void parseStats(BufferedReader bufferedReader) throws IOException {
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
        this.mBandwidthEntries.clear();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return;
            }
            simpleStringSplitter.setString(line);
            simpleStringSplitter.next();
            int i = 0;
            do {
                long j = i;
                int iIndexOfKey = this.mBandwidthEntries.indexOfKey(j);
                if (iIndexOfKey >= 0) {
                    LongSparseLongArray longSparseLongArray = this.mBandwidthEntries;
                    longSparseLongArray.put(j, longSparseLongArray.valueAt(iIndexOfKey) + (Long.parseLong(simpleStringSplitter.next()) / 1000000));
                } else {
                    this.mBandwidthEntries.put(j, Long.parseLong(simpleStringSplitter.next()) / 1000000);
                }
                i++;
            } while (simpleStringSplitter.hasNext());
        }
    }

    public LongSparseLongArray getBandwidthEntries() {
        return this.mBandwidthEntries;
    }
}
