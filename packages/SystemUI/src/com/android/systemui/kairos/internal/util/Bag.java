package com.android.systemui.kairos.internal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes2.dex */
public final class Bag implements Set, KMappedMarker {
    public final /* synthetic */ Set $$delegate_0;
    public final Map intMap;

    private Bag(Map<Object, Integer> map) {
        this.$$delegate_0 = map.keySet();
        this.intMap = map;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        Integer num = (Integer) this.intMap.get(obj);
        if (num != null) {
            this.intMap.put(obj, Integer.valueOf(num.intValue() + 1));
            return false;
        }
        this.intMap.put(obj, 1);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        this.intMap.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        return this.$$delegate_0.contains(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.$$delegate_0.containsAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.$$delegate_0.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.$$delegate_0.iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        Integer num = (Integer) this.intMap.get(obj);
        if (num == null) {
            return false;
        }
        if (num.intValue() <= 1) {
            this.intMap.remove(obj);
            return true;
        }
        this.intMap.put(obj, Integer.valueOf(num.intValue() - 1));
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return this.$$delegate_0.size();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public final String toString() {
        return this.intMap.toString();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }

    public Bag() {
        this(new HashMap());
    }
}
