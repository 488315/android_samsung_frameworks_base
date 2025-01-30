package com.android.server.backup.restore;

import android.content.pm.IPackageDeleteObserver;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class RestoreDeleteObserver extends IPackageDeleteObserver.Stub {
  public final AtomicBoolean mDone = new AtomicBoolean();

  public void reset() {
    synchronized (this.mDone) {
      this.mDone.set(false);
    }
  }

  public void waitForCompletion() {
    synchronized (this.mDone) {
      while (!this.mDone.get()) {
        try {
          this.mDone.wait();
        } catch (InterruptedException unused) {
        }
      }
    }
  }

  public void packageDeleted(String str, int i) {
    synchronized (this.mDone) {
      this.mDone.set(true);
      this.mDone.notifyAll();
    }
  }
}
