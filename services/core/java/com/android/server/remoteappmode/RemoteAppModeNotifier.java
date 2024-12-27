package com.android.server.remoteappmode;

import android.util.ArrayMap;
import com.samsung.android.remoteappmode.IRemoteAppModeListener;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RemoteAppModeNotifier {
    public final Map mRemoteAppModeListeners = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteAppModeListenerInfo extends ListenerInfo {
        public final IRemoteAppModeListener listener;

        public RemoteAppModeListenerInfo(IRemoteAppModeListener iRemoteAppModeListener, String str, int i, int i2) {
            super(i, i2, str);
            this.listener = iRemoteAppModeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (RemoteAppModeNotifier.this.mRemoteAppModeListeners) {
                ((ArrayMap) RemoteAppModeNotifier.this.mRemoteAppModeListeners).remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }
    }
}
