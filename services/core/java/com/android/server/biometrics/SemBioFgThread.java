package com.android.server.biometrics;

import android.os.Handler;
import com.android.server.ServiceThread;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public class SemBioFgThread extends ServiceThread implements Executor {
  public static SemBioFgThread sInstance;

  public SemBioFgThread() {
    super("biometrics.fg", -2, true);
  }

  public static SemBioFgThread get() {
    if (sInstance == null) {
      synchronized (SemBioFgThread.class) {
        if (sInstance == null) {
          SemBioFgThread semBioFgThread = new SemBioFgThread();
          sInstance = semBioFgThread;
          semBioFgThread.start();
        }
      }
    }
    return sInstance;
  }

  @Override // java.util.concurrent.Executor
  public void execute(Runnable runnable) {
    if (post(runnable)) {
      return;
    }
    throw new RejectedExecutionException(getThreadHandler() + " is shutting down");
  }

  public Handler getHandler() {
    return getThreadHandler();
  }

  public boolean post(Runnable runnable) {
    return getThreadHandler().post(runnable);
  }
}
