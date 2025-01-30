package androidx.collection;

import androidx.collection.internal.Lock;
import androidx.collection.internal.LruHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LruCache {
    public int hitCount;
    public final Lock lock;
    public final LruHashMap map;
    public final int maxSize;
    public int missCount;
    public int size;

    public LruCache(int i) {
        this.maxSize = i;
        if (!(i > 0)) {
            throw new IllegalArgumentException("maxSize <= 0".toString());
        }
        this.map = new LruHashMap(0, 0.75f);
        this.lock = new Lock();
    }

    public final Object get(Object obj) {
        synchronized (this.lock) {
            Object obj2 = this.map.map.get(obj);
            if (obj2 != null) {
                this.hitCount++;
                return obj2;
            }
            this.missCount++;
            return null;
        }
    }

    public final Object put(Object obj, Object obj2) {
        Object put;
        synchronized (this.lock) {
            this.size++;
            put = this.map.map.put(obj, obj2);
            if (put != null) {
                this.size--;
            }
            Unit unit = Unit.INSTANCE;
        }
        trimToSize(this.maxSize);
        return put;
    }

    public final String toString() {
        String str;
        synchronized (this.lock) {
            int i = this.hitCount;
            int i2 = this.missCount + i;
            str = "LruCache[maxSize=" + this.maxSize + ",hits=" + this.hitCount + ",misses=" + this.missCount + ",hitRate=" + (i2 != 0 ? (i * 100) / i2 : 0) + "%]";
        }
        return str;
    }

    public final void trimToSize(int i) {
        while (true) {
            synchronized (this.lock) {
                if (!(this.size >= 0 && (!this.map.map.isEmpty() || this.size == 0))) {
                    throw new IllegalStateException("LruCache.sizeOf() is reporting inconsistent results!".toString());
                }
                if (this.size <= i || this.map.map.isEmpty()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) CollectionsKt___CollectionsKt.firstOrNull(this.map.map.entrySet());
                if (entry == null) {
                    return;
                }
                Object key = entry.getKey();
                entry.getValue();
                this.map.map.remove(key);
                this.size--;
            }
        }
    }
}
