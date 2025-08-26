package com.samsung.android.lock;

import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes6.dex */
public final class LsLogger {
    private static final int ACCUM_TIME_MS = 2000;
    private static final int MAX_LINES = 200;
    private static final String TAG = "LsLogger";
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final SparseArray<Logger> mLoggers = new SparseArray<>();

    public static void addLog(LsLogType lsLogType, String str) {
        synchronized (mLoggers) {
            getLogger(lsLogType).add(str);
        }
    }

    private static Logger getLogger(LsLogType lsLogType) {
        int iIndexOf = ArrayUtils.indexOf(LsLogType.LIST, lsLogType);
        SparseArray<Logger> sparseArray = mLoggers;
        Logger logger = sparseArray.get(iIndexOf);
        if (logger != null) {
            return logger;
        }
        Logger logger2 = new Logger(lsLogType);
        logger2.start();
        sparseArray.put(iIndexOf, logger2);
        Log.d(TAG, "Loggers=" + sparseArray.size());
        return logger2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean setLogger(LsLogType lsLogType, Logger logger) {
        mLoggers.put(ArrayUtils.indexOf(LsLogType.LIST, lsLogType), logger);
        return true;
    }

    private static class Logger extends Thread {
        private static final Object mQueueLock = new Object();
        private Queue<String> mLogQ = new LinkedList();
        private LsLogType mLogType;
        private Queue<String> mSavQ;

        public Logger(LsLogType lsLogType) {
            this.mLogType = lsLogType;
        }

        private void preventBOFLocked(Queue<String> queue) {
            if (queue.size() >= 200) {
                Log.e(LsLogger.TAG, "Log buffer reached the limit! Clearing the buffer...");
                queue.clear();
                queue.add(LsUtil.makeLog("Unfortunately buffer cleared to prevent overflow!"));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void add(String str) {
            synchronized (mQueueLock) {
                preventBOFLocked(this.mLogQ);
                this.mLogQ.add(str);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException, IOException {
            while (true) {
                Log.d(LsLogger.TAG, "Accumulating...");
                try {
                    Thread.sleep(2000L);
                    Object obj = mQueueLock;
                    synchronized (obj) {
                        this.mSavQ = this.mLogQ;
                        this.mLogQ = new LinkedList();
                    }
                    Log.d(LsLogger.TAG, "Saving...");
                    LsLogFile.saveFile(this.mLogType, this.mSavQ);
                    this.mSavQ.clear();
                    this.mSavQ = null;
                    synchronized (obj) {
                        if (this.mLogQ.isEmpty()) {
                            break;
                        } else {
                            Log.d(LsLogger.TAG, "Back to accumulate!");
                        }
                    }
                } catch (InterruptedException unused) {
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
