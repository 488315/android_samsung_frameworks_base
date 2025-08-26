package com.android.systemui.kairos.internal.store;

import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.util.ConcurrentNullableHashMap;
import com.android.systemui.kairos.internal.util.NullValue;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.markers.KMutableMap;

/* loaded from: classes2.dex */
public final class ConcurrentHashMapK implements MutableMapK, Map, KMutableMap {
    public final ConcurrentNullableHashMap storage;

    public final class Factory implements MutableMapK.Factory {
        @Override // com.android.systemui.kairos.internal.store.MutableMapK.Factory
        public final MutableMapK create(Integer num) {
            return new ConcurrentHashMapK(num != null ? new ConcurrentNullableHashMap(num.intValue()) : new ConcurrentNullableHashMap());
        }
    }

    public ConcurrentHashMapK(ConcurrentNullableHashMap concurrentNullableHashMap) {
        this.storage = concurrentNullableHashMap;
    }

    @Override // java.util.Map
    public final void clear() {
        this.storage.clear();
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.storage.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.storage.containsValue(obj);
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.storage.entries;
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return this.storage.get(obj);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.storage.inner.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.storage.getKeys();
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        return this.storage.put(obj, obj2);
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        this.storage.putAll(map);
    }

    @Override // com.android.systemui.kairos.internal.store.MutableMapK
    public final MapK readOnlyCopy() {
        CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 = new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.storage.inner.entrySet());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1.$this_asSequence$inlined) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            NullValue nullValue = NullValue.INSTANCE;
            if (key == nullValue) {
                key = null;
            }
            if (value == nullValue) {
                value = null;
            }
            Pair pair = new Pair(key, value);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return MapHolder.m2586boximpl(linkedHashMap);
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        return this.storage.remove(obj);
    }

    @Override // java.util.Map
    public final int size() {
        return this.storage.getSize();
    }

    @Override // java.util.Map
    public final Collection values() {
        return this.storage.getValues();
    }
}
