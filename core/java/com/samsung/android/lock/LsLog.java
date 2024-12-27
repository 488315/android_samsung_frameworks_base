package com.samsung.android.lock;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;

import java.io.PrintWriter;

public final class LsLog {
    private static final String TAG = "LsLog";
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static int mSecurityDebugLevel = 0;
    private static final SparseIntArray mFailureCount = new SparseIntArray();

    private LsLog() {}

    public static void enroll(String msg) {
        enroll(msg, false);
    }

    public static void enroll(String msg, boolean upload) {
        add(LsLogType.ENROLL, msg);
        if (upload) {
            LsLogFile.upload(LsLogType.ENROLL);
        }
    }

    public static void verify(String msg) {
        verify(msg, false);
    }

    public static void verify(String msg, boolean upload) {
        add(LsLogType.VERIFY, msg);
        if (upload) {
            LsLogFile.upload(LsLogType.VERIFY);
        }
    }

    public static void keyErr(String msg) {
        keyErr(msg, false);
    }

    public static void keyErr(String msg, boolean upload) {
        add(LsLogType.KEYERR, msg);
        if (upload) {
            LsLogFile.upload(LsLogType.KEYERR);
        }
    }

    public static void restore(String msg) {
        restore(msg, false);
    }

    public static void restore(String msg, boolean upload) {
        add(LsLogType.RESTORE, msg);
        if (upload) {
            LsLogFile.upload(LsLogType.RESTORE);
        }
    }

    public static void unknown(String msg) {
        unknown(msg, false);
    }

    public static void unknown(String msg, boolean upload) {
        add(LsLogType.UNKNOWN, msg);
        if (upload) {
            LsLogFile.upload(LsLogType.UNKNOWN);
        }
    }

    public static void summary(String msg) {
        LsLogger.addLog(LsLogType.SUMMARY, LsUtil.makeLog(msg));
        Log.w(TAG, "!@ " + msg);
    }

    public static void enrollRequest(int userId, int pid, String pkg) {
        LsLogEnroll.request(userId, pid, pkg);
    }

    public static void enrollBegin(int userId) {
        LsLogEnroll.begin(userId, new Throwable());
    }

    public static void enrollUpdate(int type, int slot, long protector, byte[] salt) {
        LsLogEnroll.update(type, slot, protector, salt);
    }

    public static void enrollFinish(int response, String msg) {
        LsLogEnroll.finish(response, msg);
    }

    public static void verifyRequest(int userId, int pid, String pkg) {
        LsLogVerify.request(userId, pid, pkg);
    }

    public static void verifyBegin(int userId) {
        LsLogVerify.begin(userId, new Throwable());
    }

    public static void verifyUpdate(int type, int slot, long protector, byte[] salt) {
        LsLogVerify.update(type, slot, protector, salt);
    }

    public static void verifyFinish(int response, long timeout, String msg) {
        LsLogVerify.finish(response, timeout, msg);
    }

    public static void d(LsLogType type, String msg) {
        add(type, msg);
    }

    public static void e(LsLogType type, String msg) {
        add(type, msg);
        LsLogFile.upload(type);
    }

    public static void i(LsLogType type, String msg) {
        add(type, msg);
    }

    private static void add(LsLogType type, String msg) {
        LsLogger.addLog(type, LsUtil.makeLog(msg));
        Log.w("LsLog." + type, msg);
    }

    public static void prepare() {
        LsLogFile.prepare();
        LsLogSummary.prefetchData();
    }

    public static void tryUpload(Context context) {
        LsLogUploader.tryUpload(context);
    }

    public static void migrate(int ver) {
        LsLogFile.migrate(ver);
    }

    public static void show() {
        if (mSecurityDebugLevel >= 1) {
            LsLogFile.show();
        }
    }

    public static void dump(PrintWriter pw) {
        LsLogFile.dump(pw);
    }

    public static void setSecurityDebugLevel(int level) {
        Log.d(TAG, "setSecurityDebugLevel " + level);
        mSecurityDebugLevel = level;
        if (level >= 1) {
            LsLogFile.reset(LsLogType.SUMMARY);
        }
    }

    public static synchronized void setFailureCount(int userId, int count) {
        synchronized (LsLog.class) {
            Log.d(TAG, "User " + userId + " setFailureCount = " + count);
            mFailureCount.put(userId, count);
        }
    }

    public static synchronized int getFailureCount(int userId) {
        int i;
        synchronized (LsLog.class) {
            i = mFailureCount.get(userId);
        }
        return i;
    }
}
