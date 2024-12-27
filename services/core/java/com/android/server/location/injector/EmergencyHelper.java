package com.android.server.location.injector;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EmergencyHelper {
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public interface EmergencyStateChangedListener {
        void onStateChanged();
    }

    public final void dispatchEmergencyStateChanged() {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((EmergencyStateChangedListener) it.next()).onStateChanged();
        }
    }

    public abstract boolean isInEmergency(long j);
}
