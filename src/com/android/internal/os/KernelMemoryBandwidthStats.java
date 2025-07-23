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
        BufferedReader bufferedReader;
        if (this.mStatsDoNotExist) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(mSysfsFile));
            } catch (FileNotFoundException unused) {
                Slog.w(TAG, "No kernel memory bandwidth stats available");
                this.mBandwidthEntries.clear();
                this.mStatsDoNotExist = true;
            } catch (IOException e) {
                Slog.e(TAG, "Failed to read memory bandwidth: " + e.getMessage());
                this.mBandwidthEntries.clear();
            }
            try {
                parseStats(bufferedReader);
                bufferedReader.close();
                StrictMode.setThreadPolicy(allowThreadDiskReads);
                long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                if (uptimeMillis2 > 100) {
                    Slog.w(TAG, "Reading memory bandwidth file took " + uptimeMillis2 + "ms");
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th3;
        }
    }

    public void parseStats(BufferedReader bufferedReader) throws IOException {
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
        this.mBandwidthEntries.clear();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return;
            }
            simpleStringSplitter.setString(readLine);
            simpleStringSplitter.next();
            int i = 0;
            do {
                long j = i;
                int indexOfKey = this.mBandwidthEntries.indexOfKey(j);
                if (indexOfKey >= 0) {
                    LongSparseLongArray longSparseLongArray = this.mBandwidthEntries;
                    longSparseLongArray.put(j, longSparseLongArray.valueAt(indexOfKey) + (Long.parseLong(simpleStringSplitter.next()) / 1000000));
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
