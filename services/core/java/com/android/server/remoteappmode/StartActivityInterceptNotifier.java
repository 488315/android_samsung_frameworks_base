package com.android.server.remoteappmode;

import android.util.ArrayMap;
import com.samsung.android.remoteappmode.IStartActivityInterceptListener;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StartActivityInterceptNotifier {
    public final Map mStartActivityInterceptListeners = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StartActivityInterceptListenerInfo extends ListenerInfo {
        public final IStartActivityInterceptListener listener;

        public StartActivityInterceptListenerInfo(IStartActivityInterceptListener iStartActivityInterceptListener, String str, int i, int i2) {
            super(i, i2, str);
            this.listener = iStartActivityInterceptListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (StartActivityInterceptNotifier.this.mStartActivityInterceptListeners) {
                ((ArrayMap) StartActivityInterceptNotifier.this.mStartActivityInterceptListeners).remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }
    }
}
