package com.android.server.remoteappmode;

import android.util.ArrayMap;

import com.samsung.android.remoteappmode.IRemoteAppModeListener;

import java.util.Map;

public final class RemoteAppModeNotifier {
    public final Map mRemoteAppModeListeners = new ArrayMap();

    public final class RemoteAppModeListenerInfo extends ListenerInfo {
        public final IRemoteAppModeListener listener;

        public RemoteAppModeListenerInfo(
                IRemoteAppModeListener iRemoteAppModeListener, String str, int i, int i2) {
            super(i, i2, str);
            this.listener = iRemoteAppModeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (RemoteAppModeNotifier.this.mRemoteAppModeListeners) {
                ((ArrayMap) RemoteAppModeNotifier.this.mRemoteAppModeListeners)
                        .remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }
    }
}
