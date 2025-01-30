package com.android.server.location.injector;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class AppForegroundHelper {
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public interface AppForegroundListener {
        void onAppForegroundChanged(int i, boolean z);
    }

    public static boolean isForeground(int i) {
        return i <= 125;
    }

    public abstract boolean isAppForeground(int i);

    public final void addListener(AppForegroundListener appForegroundListener) {
        this.mListeners.add(appForegroundListener);
    }

    public final void removeListener(AppForegroundListener appForegroundListener) {
        this.mListeners.remove(appForegroundListener);
    }

    public final void notifyAppForeground(int i, boolean z) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((AppForegroundListener) it.next()).onAppForegroundChanged(i, z);
        }
    }
}
