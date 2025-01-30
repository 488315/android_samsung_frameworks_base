package com.android.server.location.injector;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class DeviceIdleHelper {
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public interface DeviceIdleListener {
        void onDeviceIdleChanged(boolean z);
    }

    public abstract boolean isDeviceIdle();

    public abstract void registerInternal();

    public abstract void unregisterInternal();

    public final synchronized void addListener(DeviceIdleListener deviceIdleListener) {
        if (this.mListeners.add(deviceIdleListener) && this.mListeners.size() == 1) {
            registerInternal();
        }
    }

    public final synchronized void removeListener(DeviceIdleListener deviceIdleListener) {
        if (this.mListeners.remove(deviceIdleListener) && this.mListeners.isEmpty()) {
            unregisterInternal();
        }
    }

    public final void notifyDeviceIdleChanged() {
        boolean isDeviceIdle = isDeviceIdle();
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((DeviceIdleListener) it.next()).onDeviceIdleChanged(isDeviceIdle);
        }
    }
}
