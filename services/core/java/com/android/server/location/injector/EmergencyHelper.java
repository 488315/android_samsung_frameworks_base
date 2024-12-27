package com.android.server.location.injector;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EmergencyHelper {
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
