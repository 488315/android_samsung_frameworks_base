package com.android.server.location.injector;

import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class ScreenInteractiveHelper {
  public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

  public interface ScreenInteractiveChangedListener {
    void onScreenInteractiveChanged(boolean z);
  }

  public abstract boolean isInteractive();

  public final void addListener(ScreenInteractiveChangedListener screenInteractiveChangedListener) {
    this.mListeners.add(screenInteractiveChangedListener);
  }

  public final void removeListener(
      ScreenInteractiveChangedListener screenInteractiveChangedListener) {
    this.mListeners.remove(screenInteractiveChangedListener);
  }

  public final void notifyScreenInteractiveChanged(boolean z) {
    Log.d("LocationManagerService", "screen interactive is now " + z);
    Iterator it = this.mListeners.iterator();
    while (it.hasNext()) {
      ((ScreenInteractiveChangedListener) it.next()).onScreenInteractiveChanged(z);
    }
  }
}
