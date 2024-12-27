package com.android.server.utils;

import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SnapshotCache extends Watcher {
    public static final WeakHashMap sCaches = new WeakHashMap();
    public volatile boolean mSealed;
    public volatile Object mSnapshot;
    public final Object mSource;
    public final Statistics mStatistics;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Auto extends SnapshotCache {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ Auto() {
            this.$r8$classId = 1;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Auto(Object obj, Watchable watchable, String str, int i) {
            super(obj, watchable, str);
            this.$r8$classId = i;
        }

        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            switch (this.$r8$classId) {
                case 0:
                    return (Snappable) ((Snappable) this.mSource).snapshot();
                default:
                    throw new UnsupportedOperationException("cannot snapshot a sealed snaphot");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Statistics {
        public final AtomicInteger mReused = new AtomicInteger(0);
        public final AtomicInteger mRebuilt = new AtomicInteger(0);
    }

    public SnapshotCache() {
        this.mSnapshot = null;
        this.mSealed = false;
        this.mSource = null;
        this.mSealed = true;
        this.mStatistics = null;
    }

    public SnapshotCache(Object obj, Watchable watchable, String str) {
        this.mSnapshot = null;
        this.mSealed = false;
        this.mSource = obj;
        watchable.registerObserver(this);
        if (str == null) {
            this.mStatistics = null;
        } else {
            this.mStatistics = new Statistics();
            sCaches.put(this, null);
        }
    }

    public abstract Object createSnapshot();

    @Override // com.android.server.utils.Watcher
    public final void onChange(Watchable watchable) {
        if (this.mSealed) {
            throw new IllegalStateException("attempt to change a sealed object");
        }
        this.mSnapshot = null;
    }

    public final Object snapshot() {
        Object obj = this.mSnapshot;
        if (obj != null) {
            Statistics statistics = this.mStatistics;
            if (statistics != null) {
                statistics.mReused.incrementAndGet();
            }
        } else {
            obj = createSnapshot();
            this.mSnapshot = obj;
            Statistics statistics2 = this.mStatistics;
            if (statistics2 != null) {
                statistics2.mRebuilt.incrementAndGet();
            }
        }
        return obj;
    }
}
