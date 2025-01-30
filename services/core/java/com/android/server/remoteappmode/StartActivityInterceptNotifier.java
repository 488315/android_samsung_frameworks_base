package com.android.server.remoteappmode;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.samsung.android.remoteappmode.IStartActivityInterceptListener;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes3.dex */
public class StartActivityInterceptNotifier {
  public Context mContext;
  public final Map mStartActivityInterceptListeners = new ArrayMap();

  public StartActivityInterceptNotifier(Context context) {
    this.mContext = context;
  }

  public boolean registerStartActivityInterceptListener(
      IStartActivityInterceptListener iStartActivityInterceptListener, String str) {
    synchronized (this.mStartActivityInterceptListeners) {
      IBinder asBinder = iStartActivityInterceptListener.asBinder();
      try {
        if (str.length() > 100) {
          str = str.substring(0, 100);
        }
        StartActivityInterceptListenerInfo startActivityInterceptListenerInfo =
            new StartActivityInterceptListenerInfo(
                iStartActivityInterceptListener,
                str,
                Binder.getCallingPid(),
                Binder.getCallingUid());
        asBinder.linkToDeath(startActivityInterceptListenerInfo, 0);
        this.mStartActivityInterceptListeners.put(asBinder, startActivityInterceptListenerInfo);
      } catch (RemoteException unused) {
        return false;
      }
    }
    return true;
  }

  public boolean unregisterStartActivityInterceptListener(
      IStartActivityInterceptListener iStartActivityInterceptListener) {
    synchronized (this.mStartActivityInterceptListeners) {
      StartActivityInterceptListenerInfo startActivityInterceptListenerInfo =
          (StartActivityInterceptListenerInfo)
              this.mStartActivityInterceptListeners.remove(
                  iStartActivityInterceptListener.asBinder());
      if (startActivityInterceptListenerInfo == null) {
        return false;
      }
      startActivityInterceptListenerInfo
          .listener
          .asBinder()
          .unlinkToDeath(startActivityInterceptListenerInfo, 0);
      return true;
    }
  }

  public void notify(
      Intent intent,
      ActivityOptions activityOptions,
      ActivityInfo activityInfo,
      int i,
      boolean z,
      ActivityManager.RunningTaskInfo runningTaskInfo,
      int i2) {
    ArrayList<StartActivityInterceptListenerInfo> arrayList;
    Bundle bundle;
    int i3;
    int i4;
    synchronized (this.mStartActivityInterceptListeners) {
      arrayList = new ArrayList(this.mStartActivityInterceptListeners.values());
    }
    for (StartActivityInterceptListenerInfo startActivityInterceptListenerInfo : arrayList) {
      if (activityOptions != null) {
        try {
          bundle = activityOptions.toBundle();
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      } else {
        bundle = null;
      }
      Bundle bundle2 = bundle;
      if (runningTaskInfo != null) {
        i3 = runningTaskInfo.taskId;
        i4 = runningTaskInfo.displayId;
      } else {
        i3 = -1;
        i4 = -1;
      }
      startActivityInterceptListenerInfo.listener.onStartActivityIntercepted(
          intent, bundle2, activityInfo, i, z, i3, i4, i2);
    }
  }

  public class StartActivityInterceptListenerInfo extends ListenerInfo {
    public final IStartActivityInterceptListener listener;

    public StartActivityInterceptListenerInfo(
        IStartActivityInterceptListener iStartActivityInterceptListener,
        String str,
        int i,
        int i2) {
      super(str, i, i2);
      this.listener = iStartActivityInterceptListener;
    }

    @Override // com.android.server.remoteappmode.ListenerInfo, android.os.IBinder.DeathRecipient
    public void binderDied() {
      super.binderDied();
      synchronized (StartActivityInterceptNotifier.this.mStartActivityInterceptListeners) {
        StartActivityInterceptNotifier.this.mStartActivityInterceptListeners.remove(
            this.listener.asBinder());
      }
      this.listener.asBinder().unlinkToDeath(this, 0);
    }
  }
}
