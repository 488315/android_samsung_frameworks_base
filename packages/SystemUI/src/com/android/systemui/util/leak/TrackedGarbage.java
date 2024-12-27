package com.android.systemui.util.leak;

import android.os.SystemClock;
import android.util.ArrayMap;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class TrackedGarbage {
    private static final long GARBAGE_COLLECTION_DEADLINE_MILLIS = 60000;
    private final HashSet<LeakReference> mGarbage = new HashSet<>();
    private final ReferenceQueue<Object> mRefQueue = new ReferenceQueue<>();
    private final TrackedCollections mTrackedCollections;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class LeakReference extends WeakReference<Object> {
        private final Class<?> clazz;
        private final long createdUptimeMillis;

        public LeakReference(Object obj, ReferenceQueue<Object> referenceQueue) {
            super(obj, referenceQueue);
            this.clazz = obj.getClass();
            this.createdUptimeMillis = SystemClock.uptimeMillis();
        }
    }

    public TrackedGarbage(TrackedCollections trackedCollections) {
        this.mTrackedCollections = trackedCollections;
    }

    private void cleanUp() {
        while (true) {
            Reference<? extends Object> poll = this.mRefQueue.poll();
            if (poll == null) {
                return;
            } else {
                this.mGarbage.remove(poll);
            }
        }
    }

    private boolean isOld(long j, long j2) {
        return j + GARBAGE_COLLECTION_DEADLINE_MILLIS < j2;
    }

    public synchronized int countOldGarbage() {
        int i;
        cleanUp();
        long uptimeMillis = SystemClock.uptimeMillis();
        Iterator<LeakReference> it = this.mGarbage.iterator();
        i = 0;
        while (it.hasNext()) {
            if (isOld(it.next().createdUptimeMillis, uptimeMillis)) {
                i++;
            }
        }
        return i;
    }

    public synchronized void dump(PrintWriter printWriter) {
        try {
            cleanUp();
            long uptimeMillis = SystemClock.uptimeMillis();
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            Iterator<LeakReference> it = this.mGarbage.iterator();
            while (it.hasNext()) {
                LeakReference next = it.next();
                arrayMap.put(next.clazz, Integer.valueOf(((Integer) arrayMap.getOrDefault(next.clazz, 0)).intValue() + 1));
                if (isOld(next.createdUptimeMillis, uptimeMillis)) {
                    arrayMap2.put(next.clazz, Integer.valueOf(((Integer) arrayMap2.getOrDefault(next.clazz, 0)).intValue() + 1));
                }
            }
            for (Map.Entry entry : arrayMap.entrySet()) {
                printWriter.print(((Class) entry.getKey()).getName());
                printWriter.print(": ");
                printWriter.print(entry.getValue());
                printWriter.print(" total, ");
                printWriter.print(arrayMap2.getOrDefault(entry.getKey(), 0));
                printWriter.print(" old");
                printWriter.println();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void track(Object obj) {
        cleanUp();
        this.mGarbage.add(new LeakReference(obj, this.mRefQueue));
        this.mTrackedCollections.track(this.mGarbage, "Garbage");
    }
}
