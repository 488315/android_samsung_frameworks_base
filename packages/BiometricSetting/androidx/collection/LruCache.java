package androidx.collection;

import androidx.collection.internal.Lock;
import androidx.collection.internal.LruHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LruCache.kt */
/* loaded from: classes.dex */
public class LruCache<K, V> {
    private int hitCount;
    private final Lock lock;
    private final LruHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int size;

    public LruCache(int i) {
        this.maxSize = i;
        if (!(i > 0)) {
            throw new IllegalArgumentException("maxSize <= 0".toString());
        }
        this.map = new LruHashMap<>();
        this.lock = new Lock();
    }

    public final V get(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        synchronized (this.lock) {
            V v = this.map.get(key);
            if (v != null) {
                this.hitCount++;
                return v;
            }
            this.missCount++;
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0078 A[Catch: all -> 0x009f, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:13:0x0027, B:15:0x002c, B:17:0x0034, B:21:0x003d, B:23:0x0041, B:25:0x004a, B:27:0x0054, B:31:0x0072, B:33:0x0078, B:39:0x005d, B:40:0x0062, B:42:0x006e, B:48:0x0093, B:49:0x009e), top: B:12:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0076 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final V put(K key, V value) {
        V put;
        Object next;
        Map.Entry entry;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        synchronized (this.lock) {
            this.size++;
            put = this.map.put(key, value);
            if (put != null) {
                this.size--;
            }
        }
        int i = this.maxSize;
        while (true) {
            synchronized (this.lock) {
                if (!(this.size >= 0 && (!this.map.isEmpty() || this.size == 0))) {
                    throw new IllegalStateException("LruCache.sizeOf() is reporting inconsistent results!".toString());
                }
                if (this.size <= i || this.map.isEmpty()) {
                    break;
                }
                Set<Map.Entry<K, V>> entries = this.map.getEntries();
                if (entries instanceof List) {
                    List list = (List) entries;
                    if (list.isEmpty()) {
                        next = null;
                        entry = (Map.Entry) next;
                        if (entry == null) {
                            Object key2 = entry.getKey();
                            Object value2 = entry.getValue();
                            this.map.remove(key2);
                            int i2 = this.size;
                            Intrinsics.checkNotNullParameter(value2, "value");
                            this.size = i2 - 1;
                        }
                    } else {
                        next = list.get(0);
                        entry = (Map.Entry) next;
                        if (entry == null) {
                        }
                    }
                } else {
                    Iterator<T> it = entries.iterator();
                    if (it.hasNext()) {
                        next = it.next();
                        entry = (Map.Entry) next;
                        if (entry == null) {
                        }
                    }
                    next = null;
                    entry = (Map.Entry) next;
                    if (entry == null) {
                    }
                }
            }
        }
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
}
