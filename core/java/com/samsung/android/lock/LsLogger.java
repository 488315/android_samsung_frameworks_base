package com.samsung.android.lock;

import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;

import java.util.LinkedList;
import java.util.Queue;

public final class LsLogger {
    private static final int ACCUM_TIME_MS = 2000;
    private static final int MAX_LINES = 100;
    private static final String TAG = "LsLogger";
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final SparseArray<Logger> mLoggers = new SparseArray<>();

    public static void addLog(LsLogType type, String msg) {
        synchronized (mLoggers) {
            Logger logger = getLogger(type);
            logger.add(msg);
        }
    }

    private static Logger getLogger(LsLogType type) {
        int id = ArrayUtils.indexOf(LsLogType.LIST, type);
        Logger logger = mLoggers.get(id);
        if (logger == null) {
            Logger logger2 = new Logger(type);
            logger2.start();
            mLoggers.put(id, logger2);
            Log.d(TAG, "Loggers=" + mLoggers.size());
            return logger2;
        }
        return logger;
    }

    public static boolean setLogger(LsLogType type, Logger logger) {
        int id = ArrayUtils.indexOf(LsLogType.LIST, type);
        mLoggers.put(id, logger);
        return true;
    }

    private static class Logger extends Thread {
        private static final Object mQueueLock = new Object();
        private Queue<String> mLogQ = new LinkedList();
        private LsLogType mLogType;
        private Queue<String> mSavQ;

        public Logger(LsLogType type) {
            this.mLogType = null;
            this.mLogType = type;
        }

        private void preventBOFLocked(Queue<String> logQ) {
            if (logQ.size() >= 100) {
                Log.e(LsLogger.TAG, "Log buffer reached the limit! Clearing the buffer...");
                logQ.clear();
                logQ.add(LsUtil.makeLog("Unfortunately buffer cleared to prevent overflow!"));
            }
        }

        public void add(String msg) {
            synchronized (mQueueLock) {
                preventBOFLocked(this.mLogQ);
                this.mLogQ.add(msg);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                Log.d(LsLogger.TAG, "Accumulating...");
                try {
                    Thread.sleep(2000L);
                    synchronized (mQueueLock) {
                        this.mSavQ = this.mLogQ;
                        this.mLogQ = new LinkedList();
                    }
                    Log.d(LsLogger.TAG, "Saving...");
                    LsLogFile.saveFile(this.mLogType, this.mSavQ);
                    this.mSavQ.clear();
                    this.mSavQ = null;
                    synchronized (mQueueLock) {
                        if (this.mLogQ.isEmpty()) {
                            break;
                        } else {
                            Log.d(LsLogger.TAG, "Back to accumulate!");
                        }
                    }
                } catch (InterruptedException e) {
                    Log.e(LsLogger.TAG, "Logger interrupted!");
                    return;
                }
            }
            synchronized (LsLogger.mLoggers) {
                LsLogger.setLogger(this.mLogType, null);
            }
            if (this.mLogType == LsLogType.SUMMARY) {
                LsLogFile.show();
            }
            Log.d(LsLogger.TAG, "Save Finished!");
        }
    }
}
