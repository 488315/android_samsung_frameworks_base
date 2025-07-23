package com.android.systemui.kairos.internal.util;

import com.android.systemui.kairos.internal.store.NoValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ConcurrentNullableHashMap extends AbstractMutableMap implements ConcurrentMap {
    public final ConcurrentNullableHashMap$entries$1 entries;
    public final ConcurrentHashMap inner;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.kairos.internal.util.ConcurrentNullableHashMap$entries$1] */
    private ConcurrentNullableHashMap(ConcurrentHashMap<Object, Object> concurrentHashMap) {
        this.inner = concurrentHashMap;
        this.entries = new AbstractMutableSet() { // from class: com.android.systemui.kairos.internal.util.ConcurrentNullableHashMap$entries$1
            public final Set wrapped;

            {
                this.wrapped = ConcurrentNullableHashMap.this.inner.entrySet();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean add(Object obj) {
                return this.wrapped.add(new ConcurrentNullableHashMap$entries$1$add$e$1((Map.Entry) obj, ConcurrentNullableHashMap.this));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final /* bridge */ boolean contains(Object obj) {
                if (TypeIntrinsics.isMutableMapEntry(obj)) {
                    return super.contains((Map.Entry) obj);
                }
                return false;
            }

            @Override // kotlin.collections.AbstractMutableSet
            public final int getSize() {
                return this.wrapped.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public final Iterator iterator() {
                return new ConcurrentNullableHashMap$entries$1$iterator$1(this.wrapped.iterator(), ConcurrentNullableHashMap.this);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final /* bridge */ boolean remove(Object obj) {
                if (TypeIntrinsics.isMutableMapEntry(obj)) {
                    return super.remove((Map.Entry) obj);
                }
                return false;
            }
        };
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.inner.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        return concurrentHashMap.containsKey(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        Object obj2 = concurrentHashMap.get(obj);
        if (obj2 == null || obj2 == NullValue.INSTANCE) {
            return null;
        }
        return obj2;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final Set getEntries() {
        return this.entries;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean isEmpty() {
        return this.inner.isEmpty();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        if (obj2 == null) {
            obj2 = NullValue.INSTANCE;
        }
        Object put = concurrentHashMap.put(obj, obj2);
        if (put == null || put == NullValue.INSTANCE) {
            return null;
        }
        return put;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public final Object putIfAbsent(Object obj, Object obj2) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        if (obj2 == null) {
            obj2 = NullValue.INSTANCE;
        }
        Object putIfAbsent = concurrentHashMap.putIfAbsent(obj, obj2);
        if (putIfAbsent == null || putIfAbsent == NullValue.INSTANCE) {
            return null;
        }
        return putIfAbsent;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public final boolean remove(Object obj, Object obj2) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NoValue.INSTANCE;
        }
        if (obj2 == null) {
            obj2 = NoValue.INSTANCE;
        }
        return concurrentHashMap.remove(obj, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        if (obj2 == null) {
            obj2 = NullValue.INSTANCE;
        }
        if (obj3 == null) {
            obj3 = NullValue.INSTANCE;
        }
        return concurrentHashMap.replace(obj, obj2, obj3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        Object remove = concurrentHashMap.remove(obj);
        if (remove == null || remove == NullValue.INSTANCE) {
            return null;
        }
        return remove;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public final Object replace(Object obj, Object obj2) {
        ConcurrentHashMap concurrentHashMap = this.inner;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        if (obj2 == null) {
            obj2 = NullValue.INSTANCE;
        }
        Object replace = concurrentHashMap.replace(obj, obj2);
        if (replace == null || replace == NullValue.INSTANCE) {
            return null;
        }
        return replace;
    }

    public ConcurrentNullableHashMap() {
        this((ConcurrentHashMap<Object, Object>) new ConcurrentHashMap());
    }

    public ConcurrentNullableHashMap(int i) {
        this((ConcurrentHashMap<Object, Object>) new ConcurrentHashMap(i));
    }
}
