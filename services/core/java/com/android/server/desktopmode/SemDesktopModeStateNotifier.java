package com.android.server.desktopmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IDesktopModeListener;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SemDesktopModeStateNotifier {
  static final int REGISTER_LIMIT = 20;
  public static final String TAG = "[DMS]" + SemDesktopModeStateNotifier.class.getSimpleName();
  public final Context mContext;
  public final Injector mInjector;
  public final Object mInnerLock = new Object();
  public final Map mListeners = new ArrayMap();

  public SemDesktopModeStateNotifier(Context context, Injector injector) {
    this.mContext = context;
    this.mInjector = injector;
  }

  public boolean registerDesktopModeListener(
      IDesktopModeListener iDesktopModeListener, String str) {
    synchronized (this.mInnerLock) {
      IBinder asBinder = iDesktopModeListener.asBinder();
      try {
        ListenerInfo listenerInfo =
            new ListenerInfo(
                iDesktopModeListener,
                trimName(str, 100),
                this.mInjector.binderGetCallingPid(),
                this.mInjector.binderGetCallingUid());
        enforceRegisterLimitLocked(listenerInfo, this.mListeners, 20);
        asBinder.linkToDeath(listenerInfo, 0);
        this.mListeners.put(asBinder, listenerInfo);
      } catch (RemoteException e) {
        Log.m9e(TAG, "Failed to register DesktopModeListener", e);
        return false;
      }
    }
    return true;
  }

  public static String trimName(String str, int i) {
    return str.length() > i ? str.substring(0, i) : str;
  }

  public boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) {
    synchronized (this.mInnerLock) {
      ListenerInfo listenerInfo =
          (ListenerInfo) this.mListeners.remove(iDesktopModeListener.asBinder());
      if (listenerInfo == null) {
        return false;
      }
      listenerInfo.listener.asBinder().unlinkToDeath(listenerInfo, 0);
      return true;
    }
  }

  public void notifyDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
    Iterator it = getListenersCopy().iterator();
    while (it.hasNext()) {
      try {
        ((ListenerInfo) it.next()).listener.onDesktopModeStateChanged(semDesktopModeState);
      } catch (RemoteException e) {
        Log.m9e(TAG, "Failed to notify DesktopModeListener", e);
      }
    }
    if (semDesktopModeState.state == 40) {
      sendBroadcast(semDesktopModeState);
    }
  }

  public final List getListenersCopy() {
    ArrayList arrayList;
    synchronized (this.mInnerLock) {
      arrayList = new ArrayList(this.mListeners.values());
    }
    return arrayList;
  }

  public final void sendBroadcast(SemDesktopModeState semDesktopModeState) {
    String str;
    boolean z = semDesktopModeState.enabled == 4;
    Context context = this.mContext;
    if (z) {
      str = UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE;
    } else {
      str = UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE;
    }
    context.sendBroadcastAsUser(
        new Intent(str)
            .putExtra("android.app.extra.DISPLAY_TYPE", semDesktopModeState.getDisplayType())
            .addFlags(16777216),
        UserHandle.ALL);
    this.mContext.sendBroadcastAsUser(
        new Intent(
                z
                    ? "android.app.action.ENTER_KNOX_DESKTOP_MODE"
                    : "android.app.action.EXIT_KNOX_DESKTOP_MODE")
            .putExtra("android.app.extra.DISPLAY_TYPE", semDesktopModeState.getDisplayType())
            .addFlags(16777216),
        UserHandle.ALL);
  }

  public final void enforceRegisterLimitLocked(ListenerInfo listenerInfo, Map map, int i) {
    if (i <= 0) {
      throw new IllegalArgumentException("Limit must be greater than 0");
    }
    if (listenerInfo.uid == 1000) {
      return;
    }
    int i2 = 0;
    for (ListenerInfo listenerInfo2 : map.values()) {
      if (listenerInfo2.pid == listenerInfo.pid
          && listenerInfo2.uid == listenerInfo.uid
          && (i2 = i2 + 1) == i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Only ");
        sb.append(i);
        sb.append(" listener");
        sb.append(i == 1 ? "" : "s");
        sb.append(" per pid/uid is allowed");
        throw new SecurityException(sb.toString());
      }
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println(
        "Current " + SemDesktopModeStateNotifier.class.getSimpleName() + " state:");
    indentingPrintWriter.increaseIndent();
    synchronized (this.mInnerLock) {
      indentingPrintWriter.println("mListeners (" + this.mListeners.size() + "):");
      indentingPrintWriter.increaseIndent();
      Iterator it = this.mListeners.values().iterator();
      while (it.hasNext()) {
        indentingPrintWriter.println((ListenerInfo) it.next());
      }
      indentingPrintWriter.decreaseIndent();
    }
    indentingPrintWriter.decreaseIndent();
  }

  public class ListenerInfo implements IBinder.DeathRecipient {
    public final IDesktopModeListener listener;
    public final String name;
    public final int pid;
    public final int uid;

    public ListenerInfo(IDesktopModeListener iDesktopModeListener, String str, int i, int i2) {
      this.name = str;
      this.pid = i;
      this.uid = i2;
      this.listener = iDesktopModeListener;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      if (DesktopModeFeature.DEBUG) {
        Log.m11v(SemDesktopModeStateNotifier.TAG, "binderDied(): " + this);
      }
      synchronized (SemDesktopModeStateNotifier.this.mInnerLock) {
        SemDesktopModeStateNotifier.this.mListeners.remove(this.listener.asBinder());
      }
      this.listener.asBinder().unlinkToDeath(this, 0);
    }

    public String toString() {
      return getClass().getSimpleName()
          + "(name="
          + this.name
          + ", pid="
          + this.pid
          + ", uid="
          + this.uid
          + ")";
    }
  }
}
