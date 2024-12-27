package com.android.systemui.util.leak;

import java.util.Collection;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class TrackedObjects {
    private final WeakHashMap<Class<?>, TrackedClass<?>> mTrackedClasses = new WeakHashMap<>();
    private final TrackedCollections mTrackedCollections;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class TrackedClass<T> extends AbstractCollection<T> {
        final WeakIdentityHashMap<T, Void> instances;

        public /* synthetic */ TrackedClass(int i) {
            this();
        }

        @Override // com.android.systemui.util.leak.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.instances.isEmpty();
        }

        @Override // com.android.systemui.util.leak.AbstractCollection, java.util.Collection
        public int size() {
            return this.instances.size();
        }

        public void track(T t) {
            this.instances.put(t, null);
        }

        private TrackedClass() {
            this.instances = new WeakIdentityHashMap<>();
        }
    }

    public TrackedObjects(TrackedCollections trackedCollections) {
        this.mTrackedCollections = trackedCollections;
    }

    public static boolean isTrackedObject(Collection<?> collection) {
        return collection instanceof TrackedClass;
    }

    public synchronized <T> void track(T t) {
        try {
            Class<?> cls = t.getClass();
            TrackedClass<?> trackedClass = this.mTrackedClasses.get(cls);
            if (trackedClass == null) {
                trackedClass = new TrackedClass<>(0);
                this.mTrackedClasses.put(cls, trackedClass);
            }
            trackedClass.track(t);
            this.mTrackedCollections.track(trackedClass, cls.getName());
        } catch (Throwable th) {
            throw th;
        }
    }
}
