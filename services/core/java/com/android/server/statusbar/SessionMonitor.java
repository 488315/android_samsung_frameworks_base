package com.android.server.statusbar;

import android.app.StatusBarManager;
import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.ISessionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class SessionMonitor {
  public final Context mContext;
  public final Map mSessionToListeners = new HashMap();

  public SessionMonitor(Context context) {
    this.mContext = context;
    Iterator it = StatusBarManager.ALL_SESSIONS.iterator();
    while (it.hasNext()) {
      this.mSessionToListeners.put(
          Integer.valueOf(((Integer) it.next()).intValue()), new HashSet());
    }
  }

  public void registerSessionListener(int i, ISessionListener iSessionListener) {
    requireListenerPermissions(i);
    synchronized (this.mSessionToListeners) {
      Iterator it = StatusBarManager.ALL_SESSIONS.iterator();
      while (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        if ((i & intValue) != 0) {
          ((Set) this.mSessionToListeners.get(Integer.valueOf(intValue))).add(iSessionListener);
        }
      }
    }
  }

  public void unregisterSessionListener(int i, ISessionListener iSessionListener) {
    synchronized (this.mSessionToListeners) {
      Iterator it = StatusBarManager.ALL_SESSIONS.iterator();
      while (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        if ((i & intValue) != 0) {
          ((Set) this.mSessionToListeners.get(Integer.valueOf(intValue))).remove(iSessionListener);
        }
      }
    }
  }

  public void onSessionStarted(int i, InstanceId instanceId) {
    requireSetterPermissions(i);
    if (!isValidSessionType(i)) {
      Log.e("SessionMonitor", "invalid onSessionStarted sessionType=" + i);
      return;
    }
    synchronized (this.mSessionToListeners) {
      for (ISessionListener iSessionListener :
          (Set) this.mSessionToListeners.get(Integer.valueOf(i))) {
        try {
          iSessionListener.onSessionStarted(i, instanceId);
        } catch (RemoteException e) {
          Log.e(
              "SessionMonitor", "unable to send session start to listener=" + iSessionListener, e);
        }
      }
    }
  }

  public void onSessionEnded(int i, InstanceId instanceId) {
    requireSetterPermissions(i);
    if (!isValidSessionType(i)) {
      Log.e("SessionMonitor", "invalid onSessionEnded sessionType=" + i);
      return;
    }
    synchronized (this.mSessionToListeners) {
      for (ISessionListener iSessionListener :
          (Set) this.mSessionToListeners.get(Integer.valueOf(i))) {
        try {
          iSessionListener.onSessionEnded(i, instanceId);
        } catch (RemoteException e) {
          Log.e("SessionMonitor", "unable to send session end to listener=" + iSessionListener, e);
        }
      }
    }
  }

  public final boolean isValidSessionType(int i) {
    return StatusBarManager.ALL_SESSIONS.contains(Integer.valueOf(i));
  }

  public final void requireListenerPermissions(int i) {
    if ((i & 1) != 0) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.MANAGE_BIOMETRIC", "StatusBarManagerService.SessionMonitor");
    }
    if ((i & 2) != 0) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.MANAGE_BIOMETRIC", "StatusBarManagerService.SessionMonitor");
    }
  }

  public final void requireSetterPermissions(int i) {
    if ((i & 1) != 0) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_KEYGUARD", "StatusBarManagerService.SessionMonitor");
    }
    if ((i & 2) != 0) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.STATUS_BAR_SERVICE", "StatusBarManagerService.SessionMonitor");
    }
  }
}
