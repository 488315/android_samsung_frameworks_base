package com.android.server.remoteappmode;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.samsung.android.remoteappmode.IRemoteAppModeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class RemoteAppModeNotifier {
  public Context mContext;
  public final Map mRemoteAppModeListeners = new ArrayMap();

  public RemoteAppModeNotifier(Context context) {
    this.mContext = context;
  }

  public boolean registerRemoteAppModeListener(
      IRemoteAppModeListener iRemoteAppModeListener, String str) {
    synchronized (this.mRemoteAppModeListeners) {
      IBinder asBinder = iRemoteAppModeListener.asBinder();
      try {
        if (str.length() > 100) {
          str = str.substring(0, 100);
        }
        RemoteAppModeListenerInfo remoteAppModeListenerInfo =
            new RemoteAppModeListenerInfo(
                iRemoteAppModeListener, str, Binder.getCallingPid(), Binder.getCallingUid());
        asBinder.linkToDeath(remoteAppModeListenerInfo, 0);
        this.mRemoteAppModeListeners.put(asBinder, remoteAppModeListenerInfo);
      } catch (RemoteException unused) {
        return false;
      }
    }
    return true;
  }

  public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) {
    synchronized (this.mRemoteAppModeListeners) {
      RemoteAppModeListenerInfo remoteAppModeListenerInfo =
          (RemoteAppModeListenerInfo)
              this.mRemoteAppModeListeners.remove(iRemoteAppModeListener.asBinder());
      if (remoteAppModeListenerInfo == null) {
        return false;
      }
      remoteAppModeListenerInfo.listener.asBinder().unlinkToDeath(remoteAppModeListenerInfo, 0);
      return true;
    }
  }

  public void notifyRemoteAppModeStateChanged(boolean z) {
    ArrayList arrayList;
    synchronized (this.mRemoteAppModeListeners) {
      arrayList = new ArrayList(this.mRemoteAppModeListeners.values());
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      try {
        ((RemoteAppModeListenerInfo) it.next()).listener.onRemoteAppModeStateChanged(z);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  public class RemoteAppModeListenerInfo extends ListenerInfo {
    public final IRemoteAppModeListener listener;

    public RemoteAppModeListenerInfo(
        IRemoteAppModeListener iRemoteAppModeListener, String str, int i, int i2) {
      super(str, i, i2);
      this.listener = iRemoteAppModeListener;
    }

    @Override // com.android.server.remoteappmode.ListenerInfo, android.os.IBinder.DeathRecipient
    public void binderDied() {
      super.binderDied();
      synchronized (RemoteAppModeNotifier.this.mRemoteAppModeListeners) {
        RemoteAppModeNotifier.this.mRemoteAppModeListeners.remove(this.listener.asBinder());
      }
      this.listener.asBinder().unlinkToDeath(this, 0);
    }
  }
}
