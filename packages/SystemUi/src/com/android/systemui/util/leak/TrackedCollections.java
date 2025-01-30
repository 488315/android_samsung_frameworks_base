package com.android.systemui.util.leak;

import android.os.SystemClock;
import com.android.systemui.util.leak.WeakIdentityHashMap;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TrackedCollections {
    public final WeakIdentityHashMap mCollections = new WeakIdentityHashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CollectionState {
        public int halfwayCount;
        public int lastCount;
        public long startUptime;
        public String tag;

        public /* synthetic */ CollectionState(int i) {
            this();
        }

        public static float ratePerHour(int i, int i2, long j, long j2) {
            if (j >= j2 || i < 0 || i2 < 0) {
                return Float.NaN;
            }
            return ((i2 - i) / (j2 - j)) * 60.0f * 60000.0f;
        }

        public final void dump(PrintWriter printWriter) {
            long uptimeMillis = SystemClock.uptimeMillis();
            String str = this.tag;
            long j = this.startUptime;
            printWriter.format("%s: %.2f (start-30min) / %.2f (30min-now) / %.2f (start-now) (growth rate in #/hour); %d (current size)", str, Float.valueOf(ratePerHour(0, this.halfwayCount, j, j + 1800000)), Float.valueOf(ratePerHour(this.halfwayCount, this.lastCount, this.startUptime + 1800000, uptimeMillis)), Float.valueOf(ratePerHour(0, this.lastCount, this.startUptime, uptimeMillis)), Integer.valueOf(this.lastCount));
        }

        private CollectionState() {
            this.halfwayCount = -1;
            this.lastCount = -1;
        }
    }

    public final synchronized void dump(PrintWriter printWriter, LeakDetector$$ExternalSyntheticLambda0 leakDetector$$ExternalSyntheticLambda0) {
        for (Map.Entry entry : this.mCollections.mMap.entrySet()) {
            Collection collection = (Collection) ((WeakReference) entry.getKey()).get();
            if (collection != null && leakDetector$$ExternalSyntheticLambda0.test(collection)) {
                ((CollectionState) entry.getValue()).dump(printWriter);
                printWriter.println();
            }
        }
    }

    public final synchronized void track(Collection collection, String str) {
        WeakIdentityHashMap weakIdentityHashMap = this.mCollections;
        weakIdentityHashMap.cleanUp();
        CollectionState collectionState = (CollectionState) weakIdentityHashMap.mMap.get(new WeakIdentityHashMap.CmpWeakReference(collection));
        if (collectionState == null) {
            collectionState = new CollectionState(0);
            collectionState.tag = str;
            collectionState.startUptime = SystemClock.uptimeMillis();
            WeakIdentityHashMap weakIdentityHashMap2 = this.mCollections;
            weakIdentityHashMap2.cleanUp();
            weakIdentityHashMap2.mMap.put(new WeakIdentityHashMap.CmpWeakReference(collection, weakIdentityHashMap2.mRefQueue), collectionState);
        }
        if (collectionState.halfwayCount == -1 && SystemClock.uptimeMillis() - collectionState.startUptime > 1800000) {
            collectionState.halfwayCount = collectionState.lastCount;
        }
        collectionState.lastCount = collection.size();
        SystemClock.uptimeMillis();
    }
}
