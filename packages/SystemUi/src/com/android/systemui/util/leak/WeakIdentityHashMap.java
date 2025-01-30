package com.android.systemui.util.leak;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WeakIdentityHashMap {
    public final HashMap mMap = new HashMap();
    public final ReferenceQueue mRefQueue = new ReferenceQueue();

    public final void cleanUp() {
        while (true) {
            Reference poll = this.mRefQueue.poll();
            if (poll == null) {
                return;
            } else {
                this.mMap.remove(poll);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CmpWeakReference extends WeakReference {
        public final int mHashCode;

        public CmpWeakReference(Object obj) {
            super(obj);
            this.mHashCode = System.identityHashCode(obj);
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            T t = get();
            return t != 0 && (obj instanceof CmpWeakReference) && ((CmpWeakReference) obj).get() == t;
        }

        public final int hashCode() {
            return this.mHashCode;
        }

        public CmpWeakReference(Object obj, ReferenceQueue<Object> referenceQueue) {
            super(obj, referenceQueue);
            this.mHashCode = System.identityHashCode(obj);
        }
    }
}
