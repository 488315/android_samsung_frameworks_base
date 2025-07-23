package com.samsung.android.lock;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import java.io.PrintWriter;

/* loaded from: classes6.dex */
public final class LsLog {
    private static final String TAG = "LsLog";
    private static int mSecurityDebugLevel;
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final SparseIntArray mFailureCount = new SparseIntArray();

    private LsLog() {
    }

    public static void enroll(String str) {
        enroll(str, false);
    }

    public static void enroll(String str, boolean z) {
        add(LsLogType.ENROLL, str);
        if (z) {
            LsLogFile.upload(LsLogType.ENROLL);
        }
    }

    public static void verify(String str) {
        verify(str, false);
    }

    public static void verify(String str, boolean z) {
        add(LsLogType.VERIFY, str);
        if (z) {
            LsLogFile.upload(LsLogType.VERIFY);
        }
    }

    public static void keyErr(String str) {
        keyErr(str, false);
    }

    public static void keyErr(String str, boolean z) {
        add(LsLogType.KEYERR, str);
        if (z) {
            LsLogFile.upload(LsLogType.KEYERR);
        }
    }

    public static void restore(String str) {
        restore(str, false);
    }

    public static void restore(String str, boolean z) {
        add(LsLogType.RESTORE, str);
        if (z) {
            LsLogFile.upload(LsLogType.RESTORE);
        }
    }

    public static void events(String str) {
        events(str, false);
    }

    public static void events(String str, boolean z) {
        add(LsLogType.EVENTS, str);
        if (z) {
            LsLogFile.upload(LsLogType.EVENTS);
        }
    }

    public static void unknown(String str) {
        unknown(str, false);
    }

    public static void unknown(String str, boolean z) {
        add(LsLogType.UNKNOWN, str);
        if (z) {
            LsLogFile.upload(LsLogType.UNKNOWN);
        }
    }

    public static void summary(String str) {
        LsLogger.addLog(LsLogType.SUMMARY, LsUtil.makeLog(str));
        Log.w(TAG, "!@ " + str);
    }

    public static void setCurIds(long j, long j2) {
        LsLogSummary.setCurIds(j, j2);
    }

    public static void addDetailsInfo() {
        LsLogSummary.addRecentHistory();
        LsLogSummary.addAutoAnalisys();
    }

    public static void enrollRequest(int i, int i2, String str) {
        LsLogEnroll.request(i, i2, str);
    }

    public static void enrollBegin(int i) {
        LsLogEnroll.begin(i, new Throwable());
    }

    public static void enrollUpdate(int i, int i2, long j, byte[] bArr) {
        LsLogEnroll.update(i, i2, j, bArr);
    }

    public static void enrollFinish(int i, String str) {
        LsLogEnroll.finish(i, str);
    }

    public static void verifyRequest(int i, int i2, String str) {
        LsLogVerify.request(i, i2, str);
    }

    public static void verifyBegin(int i) {
        LsLogVerify.begin(i, new Throwable());
    }

    public static void verifyUpdate(int i, int i2, long j, byte[] bArr) {
        LsLogVerify.update(i, i2, j, bArr);
    }

    public static void verifyFinish(int i, long j, String str) {
        LsLogVerify.finish(i, j, str);
    }

    public static void d(String str, String str2) {
        add(LsLogType.UNKNOWN, str + " " + str2);
    }

    public static void e(String str, String str2) {
        add(LsLogType.UNKNOWN, str + " " + str2);
        LsLogFile.upload(LsLogType.UNKNOWN);
    }

    private static void add(LsLogType lsLogType, String str) {
        LsLogger.addLog(lsLogType, LsUtil.makeLog(str));
        Log.w("LsLog." + lsLogType, str);
    }

    public static void prepare() {
        LsLogFile.prepare();
        LsLogSummary.prefetchData();
    }

    public static void tryUpload(Context context) {
        LsLogUploader.tryUpload(context);
    }

    public static void migrate(int i) {
        LsLogFile.migrate(i);
    }

    public static void show() {
        if (mSecurityDebugLevel >= 1) {
            LsLogFile.show();
        }
    }

    public static void dump(PrintWriter printWriter) {
        LsLogFile.dump(printWriter);
    }

    public static void setSecurityDebugLevel(int i) {
        Log.d(TAG, "setSecurityDebugLevel " + i);
        mSecurityDebugLevel = i;
        if (i >= 1) {
            LsLogFile.reset(LsLogType.SUMMARY);
        }
    }

    public static synchronized void setFailureCount(int i, int i2) {
        synchronized (LsLog.class) {
            Log.w(TAG, "User " + i + " setFailureCount = " + i2);
            mFailureCount.put(i, i2);
        }
    }

    public static synchronized int getFailureCount(int i) {
        int i2;
        synchronized (LsLog.class) {
            i2 = mFailureCount.get(i);
        }
        return i2;
    }
}
