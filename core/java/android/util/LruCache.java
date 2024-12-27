package android.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    public void resize(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = maxSize;
        }
        trimToSize(maxSize);
    }

    public final V get(K k) {
        V v;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v2 = this.map.get(k);
            if (v2 != null) {
                this.hitCount++;
                return v2;
            }
            this.missCount++;
            V create = create(k);
            if (create == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                v = (V) this.map.put(k, create);
                if (v != null) {
                    this.map.put(k, v);
                } else {
                    this.size += safeSizeOf(k, create);
                }
            }
            if (v != null) {
                entryRemoved(false, k, create, v);
                return v;
            }
            trimToSize(this.maxSize);
            return create;
        }
    }

    public final V put(K key, V value) {
        V previous;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(key, value);
            previous = this.map.put(key, value);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize);
        return previous;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0062, code lost:

       throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void trimToSize(int r6) {
        /*
            r5 = this;
        L1:
            monitor-enter(r5)
            int r0 = r5.size     // Catch: java.lang.Throwable -> L63
            if (r0 < 0) goto L42
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch: java.lang.Throwable -> L63
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L63
            if (r0 == 0) goto L12
            int r0 = r5.size     // Catch: java.lang.Throwable -> L63
            if (r0 != 0) goto L42
        L12:
            int r0 = r5.size     // Catch: java.lang.Throwable -> L63
            if (r0 > r6) goto L18
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L63
            goto L1f
        L18:
            java.util.Map$Entry r0 = r5.eldest()     // Catch: java.lang.Throwable -> L63
            if (r0 != 0) goto L20
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L63
        L1f:
            return
        L20:
            java.lang.Object r1 = r0.getKey()     // Catch: java.lang.Throwable -> L63
            java.lang.Object r2 = r0.getValue()     // Catch: java.lang.Throwable -> L63
            java.util.LinkedHashMap<K, V> r3 = r5.map     // Catch: java.lang.Throwable -> L63
            r3.remove(r1)     // Catch: java.lang.Throwable -> L63
            int r3 = r5.size     // Catch: java.lang.Throwable -> L63
            int r4 = r5.safeSizeOf(r1, r2)     // Catch: java.lang.Throwable -> L63
            int r3 = r3 - r4
            r5.size = r3     // Catch: java.lang.Throwable -> L63
            int r3 = r5.evictionCount     // Catch: java.lang.Throwable -> L63
            r4 = 1
            int r3 = r3 + r4
            r5.evictionCount = r3     // Catch: java.lang.Throwable -> L63
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L63
            r0 = 0
            r5.entryRemoved(r4, r1, r2, r0)
            goto L1
        L42:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L63
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63
            r1.<init>()     // Catch: java.lang.Throwable -> L63
            java.lang.Class r2 = r5.getClass()     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L63
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L63
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L63
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L63
            throw r0     // Catch: java.lang.Throwable -> L63
        L63:
            r0 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L63
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.util.LruCache.trimToSize(int):void");
    }

    private Map.Entry<K, V> eldest() {
        return this.map.eldest();
    }

    private Map.Entry<K, V> eldest$ravenwood() {
        Iterator<Map.Entry<K, V>> it = this.map.entrySet().iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public final V remove(K key) {
        V previous;
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            previous = this.map.remove(key);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, null);
        }
        return previous;
    }

    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {}

    protected V create(K key) {
        return null;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result < 0) {
            throw new IllegalStateException("Negative size: " + key + "=" + value);
        }
        return result;
    }

    protected int sizeOf(K key, V value) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int hitPercent;
        int accesses = this.hitCount + this.missCount;
        hitPercent = accesses != 0 ? (this.hitCount * 100) / accesses : 0;
        return String.format(
                "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]",
                Integer.valueOf(this.maxSize),
                Integer.valueOf(this.hitCount),
                Integer.valueOf(this.missCount),
                Integer.valueOf(hitPercent));
    }
}
