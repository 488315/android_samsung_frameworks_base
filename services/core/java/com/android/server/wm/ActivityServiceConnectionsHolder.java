package com.android.server.wm;

import android.util.ArraySet;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ActivityServiceConnectionsHolder {
  public final ActivityRecord mActivity;
  public ArraySet mConnections;
  public volatile boolean mIsDisconnecting;

  public ActivityServiceConnectionsHolder(ActivityRecord activityRecord) {
    this.mActivity = activityRecord;
  }

  public void addConnection(Object obj) {
    synchronized (this.mActivity) {
      if (this.mIsDisconnecting) {
        return;
      }
      if (this.mConnections == null) {
        this.mConnections = new ArraySet();
      }
      this.mConnections.add(obj);
    }
  }

  public void removeConnection(Object obj) {
    synchronized (this.mActivity) {
      ArraySet arraySet = this.mConnections;
      if (arraySet == null) {
        return;
      }
      arraySet.remove(obj);
    }
  }

  public boolean isActivityVisible() {
    return this.mActivity.mVisibleForServiceConnection;
  }

  public int getActivityPid() {
    WindowProcessController windowProcessController = this.mActivity.app;
    if (windowProcessController != null) {
      return windowProcessController.getPid();
    }
    return -1;
  }

  public void forEachConnection(Consumer consumer) {
    synchronized (this.mActivity) {
      ArraySet arraySet = this.mConnections;
      if (arraySet != null && !arraySet.isEmpty()) {
        ArraySet arraySet2 = new ArraySet(this.mConnections);
        for (int size = arraySet2.size() - 1; size >= 0; size--) {
          consumer.accept(arraySet2.valueAt(size));
        }
      }
    }
  }

  public void disconnectActivityFromServices() {
    ArraySet arraySet = this.mConnections;
    if (arraySet == null || arraySet.isEmpty() || this.mIsDisconnecting) {
      return;
    }
    this.mIsDisconnecting = true;
    this.mActivity.mAtmService.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityServiceConnectionsHolder$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            ActivityServiceConnectionsHolder.this.lambda$disconnectActivityFromServices$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$disconnectActivityFromServices$0() {
    this.mActivity.mAtmService.mAmInternal.disconnectActivityFromServices(this);
    this.mIsDisconnecting = false;
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.println(str + "activity=" + this.mActivity);
  }

  public String toString() {
    return String.valueOf(this.mConnections);
  }
}
