package com.android.systemui.util.leak;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class WeakIdentityHashMap<K, V> {
    private final HashMap<WeakReference<K>, V> mMap = new HashMap<>();
    private final ReferenceQueue<Object> mRefQueue = new ReferenceQueue<>();

    private void cleanUp() {
        while (true) {
            Reference<? extends Object> poll = this.mRefQueue.poll();
            if (poll == null) {
                return;
            } else {
                this.mMap.remove(poll);
            }
        }
    }

    public Set<Map.Entry<WeakReference<K>, V>> entrySet() {
        return this.mMap.entrySet();
    }

    public V get(K k) {
        cleanUp();
        return this.mMap.get(new CmpWeakReference(k));
    }

    public boolean isEmpty() {
        cleanUp();
        return this.mMap.isEmpty();
    }

    public void put(K k, V v) {
        cleanUp();
        this.mMap.put(new CmpWeakReference(k, this.mRefQueue), v);
    }

    public int size() {
        cleanUp();
        return this.mMap.size();
    }

    public Collection<V> values() {
        cleanUp();
        return this.mMap.values();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class CmpWeakReference<K> extends WeakReference<K> {
        private final int mHashCode;

        public CmpWeakReference(K k) {
            super(k);
            this.mHashCode = System.identityHashCode(k);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            K k = get();
            return k != null && (obj instanceof CmpWeakReference) && ((CmpWeakReference) obj).get() == k;
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public CmpWeakReference(K k, ReferenceQueue<Object> referenceQueue) {
            super(k, referenceQueue);
            this.mHashCode = System.identityHashCode(k);
        }
    }
}
