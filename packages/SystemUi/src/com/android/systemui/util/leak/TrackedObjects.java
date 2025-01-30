package com.android.systemui.util.leak;

import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TrackedObjects {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TrackedClass extends AbstractCollection {
        public final WeakIdentityHashMap instances = new WeakIdentityHashMap();

        private TrackedClass() {
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            WeakIdentityHashMap weakIdentityHashMap = this.instances;
            weakIdentityHashMap.cleanUp();
            return weakIdentityHashMap.mMap.isEmpty();
        }

        @Override // java.util.Collection
        public final int size() {
            WeakIdentityHashMap weakIdentityHashMap = this.instances;
            weakIdentityHashMap.cleanUp();
            return weakIdentityHashMap.mMap.size();
        }
    }

    public TrackedObjects(TrackedCollections trackedCollections) {
        new WeakHashMap();
    }
}
