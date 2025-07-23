package com.android.systemui.util.leak;

import java.util.Collection;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TrackedObjects {
    private final WeakHashMap<Class<?>, TrackedClass<?>> mTrackedClasses = new WeakHashMap<>();
    private final TrackedCollections mTrackedCollections;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
