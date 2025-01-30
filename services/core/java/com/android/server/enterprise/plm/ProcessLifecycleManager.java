package com.android.server.enterprise.plm;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import com.android.server.enterprise.plm.context.KnoxZtContext;
import com.android.server.enterprise.plm.context.PeripheralContext;
import com.android.server.enterprise.plm.impl.BindServiceImpl;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class ProcessLifecycleManager {
  public static volatile ProcessLifecycleManager sInstance;
  public final ProcessStateTracker mStateTracker;

  public static ProcessLifecycleManager getInstance(Context context) {
    if (sInstance == null) {
      synchronized (ProcessLifecycleManager.class) {
        if (sInstance == null) {
          sInstance = new ProcessLifecycleManager(context);
        }
      }
    }
    return sInstance;
  }

  public ProcessLifecycleManager(Context context) {
    HandlerThread handlerThread = new HandlerThread("ProcessLifecycleManager");
    handlerThread.start();
    Looper looper = handlerThread.getLooper();
    ArrayList arrayList = new ArrayList();
    arrayList.add(
        new ProcessAdapter(
            looper, context, new BindServiceImpl(context, new PeripheralContext(context))));
    arrayList.add(
        new ProcessAdapter(
            looper, context, new BindServiceImpl(context, new KnoxZtContext(context))));
    this.mStateTracker = new ProcessStateTracker(looper, context, arrayList);
  }

  public void start(StartReason startReason) {
    this.mStateTracker.start(startReason);
  }
}
