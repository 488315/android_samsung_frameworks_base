package androidx.collection;

import androidx.collection.internal.Lock;
import androidx.collection.internal.LruHashMap;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;

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
            RuntimeHelpersKt.throwIllegalArgumentException("maxSize <= 0");
            throw null;
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
        Object objPut;
        synchronized (this.lock) {
            try {
                this.size++;
                objPut = this.map.map.put(obj, obj2);
                if (objPut != null) {
                    this.size--;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        trimToSize(this.maxSize);
        return objPut;
    }

    public final Object remove(Object obj) {
        Object objRemove;
        synchronized (this.lock) {
            try {
                objRemove = this.map.map.remove(obj);
                if (objRemove != null) {
                    this.size--;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return objRemove;
    }

    public final String toString() {
        String str;
        synchronized (this.lock) {
            try {
                int i = this.hitCount;
                int i2 = this.missCount + i;
                str = "LruCache[maxSize=" + this.maxSize + ",hits=" + this.hitCount + ",misses=" + this.missCount + ",hitRate=" + (i2 != 0 ? (i * 100) / i2 : 0) + "%]";
            } catch (Throwable th) {
                throw th;
            }
        }
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0052, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005a, code lost:
    
        throw new java.lang.IllegalStateException("LruCache.sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void trimToSize(int i) {
        while (true) {
            synchronized (this.lock) {
                try {
                    if (this.size < 0 || (this.map.map.isEmpty() && this.size != 0)) {
                        break;
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
