package com.android.server.knox.dar.sdp;

import android.os.SystemProperties;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

public abstract class SDPLogger {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static Logger mLogger = new Logger();

    public final class Logger extends Thread {
        public static Queue mSavQ;
        public static final Object cLock = new Object();
        public static final Object qLock = new Object();
        public static Queue mLogQ = new LinkedList();
        public static int mState = 0;

        /* renamed from: -$$Nest$madd, reason: not valid java name */
        public static void m634$$Nest$madd(Logger logger, String str) {
            logger.getClass();
            synchronized (qLock) {
                LinkedList linkedList = (LinkedList) mLogQ;
                if (linkedList.size() >= 300) {
                    boolean z = SDPLogger.DEBUG;
                    Log.e("SDPLogger", "Log buffer reached the limit! Clearing the buffer...");
                    linkedList.clear();
                    linkedList.add(
                            SDPLogUtil.makeDebugMessage(
                                    "ACLog: Unfortunately buffer cleared to prevent overflow!"));
                }
                ((LinkedList) mLogQ).add(str);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:70:0x0167, code lost:

           if (com.android.server.knox.dar.sdp.SDPLogger.DEBUG == false) goto L78;
        */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x0169, code lost:

           android.util.Log.d("SDPLogger", "Finished!");
        */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x016e, code lost:

           com.android.server.knox.dar.sdp.SDPLogger.Logger.mState = 0;
        */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x0173, code lost:

           return;
        */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 390
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.knox.dar.sdp.SDPLogger.Logger.run():void");
        }
    }

    public static void enqMessage(String str) {
        synchronized (Logger.cLock) {
            try {
                if (Logger.mState == 0) {
                    Logger.mState = 1;
                    Logger logger = new Logger();
                    mLogger = logger;
                    logger.setDaemon(true);
                    mLogger.start();
                }
                Logger logger2 = mLogger;
                if (logger2 != null) {
                    Logger.m634$$Nest$madd(logger2, str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
