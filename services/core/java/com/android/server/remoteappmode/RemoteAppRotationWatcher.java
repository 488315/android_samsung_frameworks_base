package com.android.server.remoteappmode;

import android.view.IRotationWatcher;
import android.view.WindowManagerGlobal;
import com.samsung.android.remoteappmode.IRotationChangeListener;

/* loaded from: classes3.dex */
public class RemoteAppRotationWatcher extends IRotationWatcher.Stub {
  public IRotationChangeListener listener;
  public int mDisplayId = -1;

  public void onRotationChanged(int i) {
    IRotationChangeListener iRotationChangeListener = this.listener;
    if (iRotationChangeListener != null) {
      iRotationChangeListener.onRotationChanged(this.mDisplayId, i);
    }
  }

  public void setRotationChangeListener(int i, IRotationChangeListener iRotationChangeListener) {
    this.listener = iRotationChangeListener;
    this.mDisplayId = i;
    WindowManagerGlobal.getWindowManagerService().watchRotation(this, i);
  }

  public void removeRotationChangeListenr() {
    this.listener = null;
    WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this);
  }
}
