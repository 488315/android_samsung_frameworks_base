package com.android.internal.os;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/* loaded from: classes5.dex */
public class StoragedUidIoStatsReader {
    private static final String TAG = "StoragedUidIoStatsReader";
    private static String sUidIoFile = "/proc/uid_io/stats";

    public interface Callback {
        void onUidStorageStats(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10);
    }

    public StoragedUidIoStatsReader() {
    }

    public StoragedUidIoStatsReader(String str) {
        sUidIoFile = str;
    }

    public void readAbsolute(Callback callback) {
        int allowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            readAbsoluteInternal(callback);
        } finally {
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    private void readAbsoluteInternal(Callback callback) {
        try {
            BufferedReader newBufferedReader = Files.newBufferedReader(new File(sUidIoFile).toPath());
            while (true) {
                try {
                    String readLine = newBufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    String[] split = TextUtils.split(readLine, " ");
                    if (split.length != 11) {
                        Slog.e(TAG, "Malformed entry in " + sUidIoFile + ": " + readLine);
                    } else {
                        try {
                            callback.onUidStorageStats(Integer.parseInt(split[0], 10), Long.parseLong(split[1], 10), Long.parseLong(split[2], 10), Long.parseLong(split[3], 10), Long.parseLong(split[4], 10), Long.parseLong(split[5], 10), Long.parseLong(split[6], 10), Long.parseLong(split[7], 10), Long.parseLong(split[8], 10), Long.parseLong(split[9], 10), Long.parseLong(split[10], 10));
                        } catch (NumberFormatException e) {
                            Slog.e(TAG, "Could not parse entry in " + sUidIoFile + ": " + e.getMessage());
                        }
                    }
                } finally {
                }
            }
            if (newBufferedReader != null) {
                newBufferedReader.close();
            }
        } catch (IOException e2) {
            Slog.e(TAG, "Failed to read " + sUidIoFile + ": " + e2.getMessage());
        }
    }
}
