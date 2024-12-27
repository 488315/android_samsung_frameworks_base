package com.android.systemui.util.leak;

import android.os.SystemClock;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class TrackedCollections {
    private static final long HALFWAY_DELAY = 1800000;
    private static final long MILLIS_IN_MINUTE = 60000;
    private final WeakIdentityHashMap<Collection<?>, CollectionState> mCollections = new WeakIdentityHashMap<>();

    class CollectionState {
        int halfwayCount;
        int lastCount;
        long lastUptime;
        long startUptime;
        String tag;

        public /* synthetic */ CollectionState(int i) {
            this();
        }

        private float ratePerHour(long j, int i, long j2, int i2) {
            if (j >= j2 || i < 0 || i2 < 0) {
                return Float.NaN;
            }
            return ((i2 - i) / (j2 - j)) * 60.0f * 60000.0f;
        }

        public void dump(PrintWriter printWriter) {
            long uptimeMillis = SystemClock.uptimeMillis();
            String str = this.tag;
            long j = this.startUptime;
            printWriter.format("%s: %.2f (start-30min) / %.2f (30min-now) / %.2f (start-now) (growth rate in #/hour); %d (current size)", str, Float.valueOf(ratePerHour(j, 0, j + TrackedCollections.HALFWAY_DELAY, this.halfwayCount)), Float.valueOf(ratePerHour(this.startUptime + TrackedCollections.HALFWAY_DELAY, this.halfwayCount, uptimeMillis, this.lastCount)), Float.valueOf(ratePerHour(this.startUptime, 0, uptimeMillis, this.lastCount)), Integer.valueOf(this.lastCount));
        }

        private CollectionState() {
            this.halfwayCount = -1;
            this.lastCount = -1;
        }
    }

    public synchronized void dump(PrintWriter printWriter, Predicate<Collection<?>> predicate) {
        try {
            for (Map.Entry<WeakReference<Collection<?>>, CollectionState> entry : this.mCollections.entrySet()) {
                Collection<?> collection = entry.getKey().get();
                if (predicate != null) {
                    if (collection != null && predicate.test(collection)) {
                    }
                }
                entry.getValue().dump(printWriter);
                printWriter.println();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void track(Collection<?> collection, String str) {
        try {
            CollectionState collectionState = this.mCollections.get(collection);
            if (collectionState == null) {
                collectionState = new CollectionState(0);
                collectionState.tag = str;
                collectionState.startUptime = SystemClock.uptimeMillis();
                this.mCollections.put(collection, collectionState);
            }
            if (collectionState.halfwayCount == -1 && SystemClock.uptimeMillis() - collectionState.startUptime > HALFWAY_DELAY) {
                collectionState.halfwayCount = collectionState.lastCount;
            }
            collectionState.lastCount = collection.size();
            collectionState.lastUptime = SystemClock.uptimeMillis();
        } catch (Throwable th) {
            throw th;
        }
    }
}
