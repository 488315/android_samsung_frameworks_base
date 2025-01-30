package com.android.server.location.injector;

import android.location.util.identity.CallerIdentity;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class AppOpsHelper {
  public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
  public final CopyOnWriteArrayList mMockLocationListeners = new CopyOnWriteArrayList();

  public interface LocationAppOpListener {
    void onAppOpsChanged(String str);
  }

  public interface MockLocationAppOpListener {
    void onMockLocationAppOpsChanged();
  }

  public abstract boolean checkMockLocationAccess(String str, int i);

  public abstract boolean checkOpNoThrow(int i, CallerIdentity callerIdentity);

  public abstract void finishOp(int i, CallerIdentity callerIdentity);

  public abstract boolean noteOp(int i, CallerIdentity callerIdentity);

  public abstract boolean noteOpNoThrow(int i, CallerIdentity callerIdentity);

  public abstract void setSystemAlertWindowOpIfNeed(String str, int i);

  public abstract boolean startOpNoThrow(int i, CallerIdentity callerIdentity);

  public final void notifyAppOpChanged(String str) {
    Iterator it = this.mListeners.iterator();
    while (it.hasNext()) {
      ((LocationAppOpListener) it.next()).onAppOpsChanged(str);
    }
  }

  public final void addListener(LocationAppOpListener locationAppOpListener) {
    this.mListeners.add(locationAppOpListener);
  }

  public final void notifyMockLocationAppOpChanged(String str) {
    Iterator it = this.mMockLocationListeners.iterator();
    while (it.hasNext()) {
      ((MockLocationAppOpListener) it.next()).onMockLocationAppOpsChanged();
    }
  }

  public void addMockLocationListener(MockLocationAppOpListener mockLocationAppOpListener) {
    this.mMockLocationListeners.add(mockLocationAppOpListener);
  }
}
