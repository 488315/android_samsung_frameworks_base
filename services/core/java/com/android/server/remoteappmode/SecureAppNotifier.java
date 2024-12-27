package com.android.server.remoteappmode;

import android.util.ArrayMap;

import com.samsung.android.remoteappmode.ISecureAppChangedListener;

import java.util.Map;

public final class SecureAppNotifier {
    public final Map mSecureAppChangedListeners = new ArrayMap();

    public final class SecureAppChangedListenerInfo extends ListenerInfo {
        public final ISecureAppChangedListener listener;

        public SecureAppChangedListenerInfo(
                ISecureAppChangedListener iSecureAppChangedListener, String str, int i, int i2) {
            super(i, i2, str);
            this.listener = iSecureAppChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (SecureAppNotifier.this.mSecureAppChangedListeners) {
                ((ArrayMap) SecureAppNotifier.this.mSecureAppChangedListeners)
                        .remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }
    }
}
