package com.android.server.remoteappmode;

import android.util.ArrayMap;

import com.samsung.android.remoteappmode.IStartActivityInterceptListener;

import java.util.Map;

public final class StartActivityInterceptNotifier {
    public final Map mStartActivityInterceptListeners = new ArrayMap();

    public final class StartActivityInterceptListenerInfo extends ListenerInfo {
        public final IStartActivityInterceptListener listener;

        public StartActivityInterceptListenerInfo(
                IStartActivityInterceptListener iStartActivityInterceptListener,
                String str,
                int i,
                int i2) {
            super(i, i2, str);
            this.listener = iStartActivityInterceptListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (StartActivityInterceptNotifier.this.mStartActivityInterceptListeners) {
                ((ArrayMap) StartActivityInterceptNotifier.this.mStartActivityInterceptListeners)
                        .remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }
    }
}
