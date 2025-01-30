package com.android.server.biometrics.sensors.face;

import android.os.Handler;
import com.android.server.ServiceThread;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public final class SemFaceMainThread extends ServiceThread implements Executor {
  public static SemFaceMainThread sInstance;

  public SemFaceMainThread() {
    super("biometrics.face", -2, true);
  }

  public static SemFaceMainThread get() {
    if (sInstance == null) {
      synchronized (SemFaceMainThread.class) {
        if (sInstance == null) {
          SemFaceMainThread semFaceMainThread = new SemFaceMainThread();
          sInstance = semFaceMainThread;
          semFaceMainThread.start();
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
