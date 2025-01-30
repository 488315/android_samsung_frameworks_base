package com.samsung.android.app;

import android.app.ActivityManager;
import android.os.Process;
import android.os.RemoteException;
import android.util.Slog;
import android.view.View;
import dalvik.system.VMDebug;

/* loaded from: classes5.dex */
public class AbnormalUsage {
  private static final long RUNTIME_USED_LIMIT = 450000;
  private static final long RUNTIME_USED_WARM_LIMIT = 380000;
  private static final String TAG = "AbnormalUsage";
  private static final long VIEW_COUNT_LIMIT = 2200;
  private static final long VIEW_COUNT_PERIOD = 8;
  private static final long VIEW_COUNT_WARM_LIMIT = 300;
  private int mCurrStopCount;

  public AbnormalUsage() {
    this.mCurrStopCount = 0;
    this.mCurrStopCount = 0;
  }

  public void checkAbnormalUsage() {
    checkViewUsage();
  }

  private void checkViewUsage() {
    this.mCurrStopCount++;
    long allocatedMemory =
        (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
    long cnt = 0;
    boolean excessiveUsage = false;
    if ((allocatedMemory >= RUNTIME_USED_WARM_LIMIT && (this.mCurrStopCount & 7) == 1)
        || allocatedMemory >= RUNTIME_USED_LIMIT) {
      cnt = VMDebug.countInstancesOfClass(View.class, false);
      if (cnt > VIEW_COUNT_WARM_LIMIT) {
        excessiveUsage = true;
      }
    }
    if (cnt > VIEW_COUNT_LIMIT || (excessiveUsage && allocatedMemory > RUNTIME_USED_LIMIT)) {
      try {
        ActivityManager.getService().reportAbnormalUsage(Process.myPid(), 1);
      } catch (RemoteException e) {
        Slog.m115e(TAG, "ViewCount: report abnormal resource usage: " + e.getMessage());
      }
      Slog.m115e(
          TAG,
          "report abnormal resource usage: PID "
              + Process.myPid()
              + " view count : "
              + cnt
              + " memory usage : "
              + allocatedMemory
              + " stop count : "
              + this.mCurrStopCount);
    }
  }
}
