package com.android.server.location;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import com.android.server.ServiceThread;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class LocationServiceThread extends ServiceThread {
  public static Handler sHandler;
  public static HandlerExecutor sHandlerExecutor;
  public static LocationServiceThread sInstance;

  public LocationServiceThread() {
    super("LocationProviderManagerThread", 0, true);
  }

  public static synchronized void ensureThreadLocked() {
    synchronized (LocationServiceThread.class) {
      if (sInstance == null) {
        LocationServiceThread locationServiceThread = new LocationServiceThread();
        sInstance = locationServiceThread;
        locationServiceThread.start();
        Looper looper = sInstance.getLooper();
        looper.setTraceTag(524288L);
        looper.setSlowLogThresholdMs(100L, 200L);
        sHandler = new Handler(sInstance.getLooper());
        sHandlerExecutor = new HandlerExecutor(sHandler);
      }
    }
  }

  public static Handler getHandler() {
    Handler handler;
    synchronized (LocationServiceThread.class) {
      ensureThreadLocked();
      handler = sHandler;
    }
    return handler;
  }

  public static Executor getExecutor() {
    HandlerExecutor handlerExecutor;
    synchronized (LocationServiceThread.class) {
      ensureThreadLocked();
      handlerExecutor = sHandlerExecutor;
    }
    return handlerExecutor;
  }
}
