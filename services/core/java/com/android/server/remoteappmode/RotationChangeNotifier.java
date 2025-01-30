package com.android.server.remoteappmode;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.samsung.android.remoteappmode.IRotationChangeListener;
import java.util.Map;

/* loaded from: classes3.dex */
public class RotationChangeNotifier {
    public static final String TAG = "RotationChangeNotifier";
    public Context mContext;
    public final Map mRotationChangedListeners = new ArrayMap();

    public RotationChangeNotifier(Context context) {
        this.mContext = context;
    }

    public boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) {
        synchronized (this.mRotationChangedListeners) {
            IBinder asBinder = iRotationChangeListener.asBinder();
            try {
                if (str.length() > 100) {
                    str = str.substring(0, 100);
                }
                RotationChangedListenerInfo rotationChangedListenerInfo = new RotationChangedListenerInfo(iRotationChangeListener, str, Binder.getCallingPid(), Binder.getCallingUid(), i);
                rotationChangedListenerInfo.registerRotationWatcher(i);
                asBinder.linkToDeath(rotationChangedListenerInfo, 0);
                this.mRotationChangedListeners.put(asBinder, rotationChangedListenerInfo);
            } catch (RemoteException unused) {
                return false;
            }
        }
        return true;
    }

    public boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) {
        synchronized (this.mRotationChangedListeners) {
            RotationChangedListenerInfo rotationChangedListenerInfo = (RotationChangedListenerInfo) this.mRotationChangedListeners.remove(iRotationChangeListener.asBinder());
            if (rotationChangedListenerInfo == null) {
                return false;
            }
            rotationChangedListenerInfo.unregisterRotationWatcher();
            rotationChangedListenerInfo.listener.asBinder().unlinkToDeath(rotationChangedListenerInfo, 0);
            return true;
        }
    }

    public class RotationChangedListenerInfo extends ListenerInfo {
        public IRotationChangeListener listener;
        public RemoteAppRotationWatcher mWatcher;

        public RotationChangedListenerInfo(IRotationChangeListener iRotationChangeListener, String str, int i, int i2, int i3) {
            super(str, i, i2);
            this.listener = iRotationChangeListener;
        }

        @Override // com.android.server.remoteappmode.ListenerInfo, android.os.IBinder.DeathRecipient
        public void binderDied() {
            super.binderDied();
            synchronized (RotationChangeNotifier.this.mRotationChangedListeners) {
                RotationChangeNotifier.this.mRotationChangedListeners.remove(this.listener.asBinder());
                try {
                    unregisterRotationWatcher();
                } catch (RemoteException e) {
                    Log.m82e(RotationChangeNotifier.TAG, " binderDied: RemoteException " + e.getMessage());
                }
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }

        public void registerRotationWatcher(int i) {
            if (this.mWatcher == null) {
                RemoteAppRotationWatcher remoteAppRotationWatcher = new RemoteAppRotationWatcher();
                this.mWatcher = remoteAppRotationWatcher;
                remoteAppRotationWatcher.setRotationChangeListener(i, this.listener);
            }
        }

        public void unregisterRotationWatcher() {
            RemoteAppRotationWatcher remoteAppRotationWatcher = this.mWatcher;
            if (remoteAppRotationWatcher == null) {
                return;
            }
            remoteAppRotationWatcher.removeRotationChangeListenr();
        }
    }
}
