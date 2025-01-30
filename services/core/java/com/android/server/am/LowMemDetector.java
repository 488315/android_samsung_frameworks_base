package com.android.server.am;

import android.os.SemSystemProperties;

/* loaded from: classes.dex */
public final class LowMemDetector {
  public final ActivityManagerService mAm;
  public boolean mAvailable;
  public final LowMemThread mLowMemThread;
  public final Object mPressureStateLock = new Object();
  public int mPressureState = 0;

  private native int init();

  /* JADX INFO: Access modifiers changed from: private */
  public native int waitForPressure();

  public LowMemDetector(ActivityManagerService activityManagerService) {
    boolean z = SemSystemProperties.getInt("ro.debuggable", 0) == 1;
    this.mAm = activityManagerService;
    LowMemThread lowMemThread = new LowMemThread();
    this.mLowMemThread = lowMemThread;
    if (z || init() != 0) {
      this.mAvailable = false;
    } else {
      this.mAvailable = true;
      lowMemThread.start();
    }
  }

  public boolean isAvailable() {
    return this.mAvailable;
  }

  public int getMemFactor() {
    int i;
    synchronized (this.mPressureStateLock) {
      i = this.mPressureState;
    }
    return i;
  }

  public final class LowMemThread extends Thread {
    public LowMemThread() {}

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      while (true) {
        int waitForPressure = LowMemDetector.this.waitForPressure();
        if (waitForPressure == -1) {
          LowMemDetector.this.mAvailable = false;
          return;
        } else {
          synchronized (LowMemDetector.this.mPressureStateLock) {
            LowMemDetector.this.mPressureState = waitForPressure;
          }
        }
      }
    }
  }
}
