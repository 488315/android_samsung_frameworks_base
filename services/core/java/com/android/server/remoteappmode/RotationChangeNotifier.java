package com.android.server.remoteappmode;

import android.os.RemoteException;
import android.util.ArrayMap;
import android.view.WindowManagerGlobal;
import com.samsung.android.remoteappmode.IRotationChangeListener;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RotationChangeNotifier {
    public final Map mRotationChangedListeners = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RotationChangedListenerInfo extends ListenerInfo {
        public final IRotationChangeListener listener;
        public RemoteAppRotationWatcher mWatcher;

        public RotationChangedListenerInfo(IRotationChangeListener iRotationChangeListener, String str, int i, int i2) {
            super(i, i2, str);
            this.listener = iRotationChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (RotationChangeNotifier.this.mRotationChangedListeners) {
                ((ArrayMap) RotationChangeNotifier.this.mRotationChangedListeners).remove(this.listener.asBinder());
                try {
                    RemoteAppRotationWatcher remoteAppRotationWatcher = this.mWatcher;
                    if (remoteAppRotationWatcher != null) {
                        remoteAppRotationWatcher.listener = null;
                        WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(remoteAppRotationWatcher);
                    }
                } catch (RemoteException e) {
                    Log.e("RotationChangeNotifier", " binderDied: RemoteException " + e.getMessage());
                }
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }

        public final void registerRotationWatcher(int i) {
            if (this.mWatcher == null) {
                RemoteAppRotationWatcher remoteAppRotationWatcher = new RemoteAppRotationWatcher();
                this.mWatcher = remoteAppRotationWatcher;
                remoteAppRotationWatcher.listener = this.listener;
                remoteAppRotationWatcher.mDisplayId = i;
                WindowManagerGlobal.getWindowManagerService().watchRotation(remoteAppRotationWatcher, i);
            }
        }
    }
}
