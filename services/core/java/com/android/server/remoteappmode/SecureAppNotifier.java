package com.android.server.remoteappmode;

import android.util.ArrayMap;

import com.samsung.android.remoteappmode.ISecureAppChangedListener;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SecureAppNotifier {
    public final Map mSecureAppChangedListeners = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
