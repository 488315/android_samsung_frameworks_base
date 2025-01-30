package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.telecom.Logging.Session;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

/* compiled from: ZygoteInit.java */
/* loaded from: classes5.dex */
class ZygoteInitThreadPool {
  private static final String TAG = "ZygoteInitThreadPool";
  private boolean isSystemServer;
  private static int PARALLEL_THREAD_COUNT = SystemProperties.getInt("persist.zit.threadcount", 3);
  private static int TIMEOUT = SystemProperties.getInt("persist.zit.timeout", 30000);
  private static boolean DEBUG = SystemProperties.getBoolean("persist.zit.debug", false);
  private static boolean hasStarted = false;
  ArrayList<String> preload_class_list = new ArrayList<>();
  ArrayList<String> each_preload_list =
      new ArrayList<>(
          Arrays.asList(
              "android.graphics.Typeface",
              "com.android.internal.telephony.GsmAlphabet",
              "android.icu.text.AnyTransliterator"));
  Vector<Thread> threads = new Vector<>();

  ZygoteInitThreadPool(boolean SystemServer) {
    Log.m98i(
        TAG,
        "Set ZygoteInitThreadPool as "
            + PARALLEL_THREAD_COUNT
            + " + "
            + this.each_preload_list.size()
            + " threads");
    this.isSystemServer = SystemServer;
  }

  int preparePreloadClassList(BufferedReader br) throws IOException {
    Process.setThreadPriority(-16);
    int count = 0;
    while (true) {
      String line = br.readLine();
      if (line == null) {
        break;
      }
      String line2 = line.trim();
      if (!line2.startsWith("#") && !line2.equals("")) {
        count++;
        this.preload_class_list.add(line2);
      }
    }
    Iterator<String> it = this.each_preload_list.iterator();
    while (it.hasNext()) {
      String each_preload_entry = it.next();
      this.preload_class_list.remove(each_preload_entry);
    }
    return count;
  }

  boolean parallelPreloadTimeout() {
    for (int i = 0; i < this.each_preload_list.size(); i++) {
      final int s = i;
      Thread thread =
          new Thread(
              new Runnable() { // from class:
                               // com.android.internal.os.ZygoteInitThreadPool$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  ZygoteInitThreadPool.this.lambda$parallelPreloadTimeout$0(s);
                }
              },
              this.each_preload_list.get(i));
      this.threads.add(thread);
      thread.start();
    }
    int size = this.preload_class_list.size() / PARALLEL_THREAD_COUNT;
    int i2 = 0;
    while (true) {
      int i3 = PARALLEL_THREAD_COUNT;
      if (i2 < i3) {
        final int start = i2 * size;
        final int end = i2 == i3 + (-1) ? this.preload_class_list.size() : start + size;
        Thread thread2 =
            new Thread(
                new Runnable() { // from class:
                                 // com.android.internal.os.ZygoteInitThreadPool$$ExternalSyntheticLambda1
                  @Override // java.lang.Runnable
                  public final void run() {
                    ZygoteInitThreadPool.this.lambda$parallelPreloadTimeout$1(start, end);
                  }
                },
                start + " ~ " + end);
        this.threads.add(thread2);
        thread2.start();
        i2++;
      } else {
        int i4 = TIMEOUT;
        WaitUntilAllFinishTimeout(i4);
        return false;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$parallelPreloadTimeout$0(int s) {
    preloadInternal(this.each_preload_list.get(s));
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x004d, code lost:

     android.util.Log.m96e(com.android.internal.os.ZygoteInitThreadPool.TAG, "!@Time out! kill itself! (" + r17 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + r16);
     r0 = r16.threads.iterator();
  */
  /* JADX WARN: Code restructure failed: missing block: B:14:0x0077, code lost:

     if (r0.hasNext() == false) goto L42;
  */
  /* JADX WARN: Code restructure failed: missing block: B:15:0x0079, code lost:

     r10 = r0.next();
     android.util.Log.m96e(com.android.internal.os.ZygoteInitThreadPool.TAG, "*******Backtrace of Thread: Preload for " + r10.getName() + " *******");
     r11 = r10.getStackTrace();
     r12 = r11.length;
     r13 = 0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x00a5, code lost:

     if (r13 >= r12) goto L45;
  */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x00a7, code lost:

     r14 = r11[r13];
     android.util.Log.m96e(com.android.internal.os.ZygoteInitThreadPool.TAG, "    " + r14.toString());
  */
  /* JADX WARN: Code restructure failed: missing block: B:19:0x00c4, code lost:

     r13 = r13 + 1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:41:0x00f4, code lost:

     if (r8 >= 0) goto L22;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void WaitUntilAllFinishTimeout(int timeout) {
    int curTotalSleep = timeout;
    while (true) {
      try {
        try {
          try {
            if (checkIfAllFinished()) {
              break;
            }
            if (DEBUG) {
              Log.m94d(TAG, "sleep for " + curTotalSleep + " / " + timeout + " = " + this);
            }
            Thread.sleep(20);
            if (curTotalSleep < 0) {
              break;
            } else {
              curTotalSleep -= 20;
            }
          } catch (ConcurrentModificationException cme) {
            Log.m97e(TAG, "Zygote init thread throws Exception", cme);
            if (curTotalSleep < 0) {}
            return;
          }
        } catch (InterruptedException ie) {
          Log.m97e(TAG, "Zygote init thread has been Interrupted", ie);
        }
      } finally {
        if (curTotalSleep < 0) {
          SystemProperties.set("persist.zit.try", "false");
          Process.killProcess(Process.myPid());
          System.exit(10);
        }
        Log.m94d(TAG, "Done preloading");
      }
    }
  }

  private boolean checkIfAllFinished() {
    if (hasStarted) {
      return this.threads.size() == 0;
    }
    Log.m94d(TAG, "Any ZIT threads haven't been started!");
    return false;
  }

  private void preloadInternal(String line) {
    Process.setThreadPriority(-16);
    hasStarted = true;
    long startTime = SystemClock.uptimeMillis();
    Trace.traceBegin(16384L, line);
    try {
      if (DEBUG) {
        Log.m100v(TAG, "Preloading " + line + Session.TRUNCATE_STRING);
      }
      Class.forName(line, true, null);
    } catch (ClassNotFoundException e) {
      Log.m102w(TAG, "Class not found for preloading: " + line);
    } catch (UnsatisfiedLinkError e2) {
      Log.m102w(TAG, "Problem preloading " + line + ": " + e2);
    } catch (Throwable t) {
      Log.m97e(TAG, "Error preloading " + line + MediaMetrics.SEPARATOR, t);
      if (t instanceof Error) {
        throw ((Error) t);
      }
      if (t instanceof RuntimeException) {
        throw ((RuntimeException) t);
      }
      throw new RuntimeException(t);
    }
    Trace.traceEnd(16384L);
    if (this.isSystemServer) {
      Log.m102w(
          TAG, "Completed : " + line + " took " + (SystemClock.uptimeMillis() - startTime) + "ms ");
    }
    this.threads.remove(Thread.currentThread());
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Removed duplicated region for block: B:26:0x00f9  */
  /* JADX WARN: Removed duplicated region for block: B:33:0x0107  */
  /* renamed from: preloadInternal, reason: merged with bridge method [inline-methods] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void lambda$parallelPreloadTimeout$1(int start, int end) {
    int i;
    Process.setThreadPriority(-16);
    String threadName = start + " ~ " + end;
    long startTime = SystemClock.uptimeMillis();
    boolean z = true;
    hasStarted = true;
    int i2 = start;
    while (i2 < end) {
      String line = this.preload_class_list.get(i2);
      Thread.currentThread()
          .setName(threadName + " (preloading: " + line + NavigationBarInflaterView.KEY_CODE_END);
      Trace.traceBegin(16384L, line);
      try {
        long beginTime = SystemClock.uptimeMillis();
        if (DEBUG) {
          try {
            Log.m100v(TAG, "Preloading " + line + Session.TRUNCATE_STRING);
          } catch (ClassNotFoundException e) {
            i = i2;
            Log.m102w(TAG, "Class not found for preloading: " + line);
            Trace.traceEnd(16384L);
            i2 = i + 1;
            z = true;
          } catch (UnsatisfiedLinkError e2) {
            e = e2;
            i = i2;
            Log.m102w(TAG, "Problem preloading " + line + ": " + e);
            Trace.traceEnd(16384L);
            i2 = i + 1;
            z = true;
          } catch (Throwable th) {
            t = th;
            Log.m97e(TAG, "Error preloading " + line + MediaMetrics.SEPARATOR, t);
            if (!(t instanceof Error)) {}
          }
        }
        Class.forName(line, z, null);
        i = i2;
        long timeToLoad = SystemClock.uptimeMillis() - beginTime;
        if (timeToLoad >= 50) {
          try {
            Log.m100v(
                TAG, "Class " + line + " was preloaded took to much time: " + timeToLoad + "ms");
          } catch (ClassNotFoundException e3) {
            Log.m102w(TAG, "Class not found for preloading: " + line);
            Trace.traceEnd(16384L);
            i2 = i + 1;
            z = true;
          } catch (UnsatisfiedLinkError e4) {
            e = e4;
            Log.m102w(TAG, "Problem preloading " + line + ": " + e);
            Trace.traceEnd(16384L);
            i2 = i + 1;
            z = true;
          } catch (Throwable th2) {
            t = th2;
            Log.m97e(TAG, "Error preloading " + line + MediaMetrics.SEPARATOR, t);
            if (!(t instanceof Error)) {
              throw ((Error) t);
            }
            if (t instanceof RuntimeException) {
              throw ((RuntimeException) t);
            }
            throw new RuntimeException(t);
          }
        }
      } catch (ClassNotFoundException e5) {
        i = i2;
      } catch (UnsatisfiedLinkError e6) {
        e = e6;
        i = i2;
      } catch (Throwable th3) {
        t = th3;
      }
      Trace.traceEnd(16384L);
      i2 = i + 1;
      z = true;
    }
    if (this.isSystemServer) {
      Log.m102w(
          TAG,
          "Completed : "
              + threadName
              + " took "
              + (SystemClock.uptimeMillis() - startTime)
              + "ms ");
    }
    this.threads.remove(Thread.currentThread());
  }

  public String toString() {
    StringBuffer buf = new StringBuffer("!@*******UNFINISHED PRELOAD CLASSES : {  ");
    for (int i = 0; i < this.threads.size(); i++) {
      if (i != 0) {
        buf.append(" / ");
      }
      buf.append(this.threads.get(i).getName());
    }
    return buf.append("  }*******").toString();
  }
}
