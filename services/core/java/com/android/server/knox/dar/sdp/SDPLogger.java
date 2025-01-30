package com.android.server.knox.dar.sdp;

import android.os.SystemProperties;
import android.util.Log;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes2.dex */
public final class SDPLogger {
  public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
  public static Logger mLogger = new Logger();

  public static void enqMessage(String str) {
    synchronized (Logger.getLock()) {
      if (Logger.getStateLocked() == 0) {
        Logger.setStateLocked(1);
        Logger logger = new Logger();
        mLogger = logger;
        logger.setDaemon(true);
        mLogger.start();
      }
      Logger logger2 = mLogger;
      if (logger2 != null) {
        logger2.add(str);
      }
    }
  }

  public class Logger extends Thread {
    public static Queue mSavQ;
    public static final Object cLock = new Object();
    public static final Object qLock = new Object();
    public static Queue mLogQ = new LinkedList();
    public static int mState = 0;
    public static String sFilePath = null;

    public Logger() {}

    public static Object getLock() {
      return cLock;
    }

    public static int getStateLocked() {
      return mState;
    }

    public static void setStateLocked(int i) {
      mState = i;
    }

    public static void preventBOFLocked(Queue queue) {
      if (queue.size() >= 300) {
        SDPLogger.LogE("Log buffer reached the limit! Clearing the buffer...");
        queue.clear();
        queue.add(
            SDPLogUtil.makeDebugMessage(
                "ACLog: Unfortunately buffer cleared to prevent overflow!"));
      }
    }

    public final void add(String str) {
      synchronized (qLock) {
        preventBOFLocked(mLogQ);
        mLogQ.add(str);
      }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      while (true) {
        SDPLogger.LogD("Start accumulating...");
        try {
          Thread.sleep(3000L);
          Object obj = qLock;
          synchronized (obj) {
            mSavQ = mLogQ;
            mLogQ = new LinkedList();
          }
          SDPLogger.LogD("Start saving...");
          Object obj2 = cLock;
          synchronized (obj2) {
            setStateLocked(2);
          }
          SDPLogFile.saveFile(mSavQ);
          mSavQ.clear();
          mSavQ = null;
          synchronized (obj2) {
            synchronized (obj) {
              if (!mLogQ.isEmpty()) {
                SDPLogger.LogD("Back to accumulate!");
                setStateLocked(1);
              } else {
                SDPLogger.LogD("Finished!");
                setStateLocked(0);
                return;
              }
            }
          }
        } catch (InterruptedException unused) {
          SDPLogger.LogE("Logger interrupted!");
          return;
        }
      }
    }
  }

  public static void dump(PrintWriter printWriter) {
    SDPLogFile.dump(printWriter);
  }

  public static void LogD(String str) {
    if (DEBUG) {
      Log.d("SDPLogger", str);
    }
  }

  public static void LogE(String str) {
    Log.e("SDPLogger", str);
  }
}
