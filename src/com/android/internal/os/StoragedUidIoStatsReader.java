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
        int iAllowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            readAbsoluteInternal(callback);
        } finally {
            StrictMode.setThreadPolicyMask(iAllowThreadDiskReadsMask);
        }
    }

    private void readAbsoluteInternal(Callback callback) throws IOException {
        try {
            BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(new File(sUidIoFile).toPath());
            while (true) {
                try {
                    String line = bufferedReaderNewBufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] strArrSplit = TextUtils.split(line, " ");
                    if (strArrSplit.length != 11) {
                        Slog.e(TAG, "Malformed entry in " + sUidIoFile + ": " + line);
                    } else {
                        try {
                            callback.onUidStorageStats(Integer.parseInt(strArrSplit[0], 10), Long.parseLong(strArrSplit[1], 10), Long.parseLong(strArrSplit[2], 10), Long.parseLong(strArrSplit[3], 10), Long.parseLong(strArrSplit[4], 10), Long.parseLong(strArrSplit[5], 10), Long.parseLong(strArrSplit[6], 10), Long.parseLong(strArrSplit[7], 10), Long.parseLong(strArrSplit[8], 10), Long.parseLong(strArrSplit[9], 10), Long.parseLong(strArrSplit[10], 10));
                        } catch (NumberFormatException e) {
                            Slog.e(TAG, "Could not parse entry in " + sUidIoFile + ": " + e.getMessage());
                        }
                    }
                } finally {
                }
            }
            if (bufferedReaderNewBufferedReader != null) {
                bufferedReaderNewBufferedReader.close();
            }
        } catch (IOException e2) {
            Slog.e(TAG, "Failed to read " + sUidIoFile + ": " + e2.getMessage());
        }
    }
}
