package com.android.systemui.util.leak;

import android.os.SystemClock;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TrackedGarbage {
    public final HashSet mGarbage = new HashSet();
    public final ReferenceQueue mRefQueue = new ReferenceQueue();
    public final TrackedCollections mTrackedCollections;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LeakReference extends WeakReference {
        public final Class clazz;
        public final long createdUptimeMillis;

        public LeakReference(Object obj, ReferenceQueue<Object> referenceQueue) {
            super(obj, referenceQueue);
            this.clazz = obj.getClass();
            this.createdUptimeMillis = SystemClock.uptimeMillis();
        }
    }

    public TrackedGarbage(TrackedCollections trackedCollections) {
        this.mTrackedCollections = trackedCollections;
    }

    public final synchronized int countOldGarbage() {
        int i;
        while (true) {
            Reference poll = this.mRefQueue.poll();
            if (poll == null) {
                break;
            }
            this.mGarbage.remove(poll);
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        Iterator it = this.mGarbage.iterator();
        i = 0;
        while (it.hasNext()) {
            if (((LeakReference) it.next()).createdUptimeMillis + 60000 < uptimeMillis) {
                i++;
            }
        }
        return i;
    }
}
