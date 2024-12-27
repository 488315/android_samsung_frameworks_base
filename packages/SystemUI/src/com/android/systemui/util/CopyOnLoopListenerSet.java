package com.android.systemui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CopyOnLoopListenerSet<E> implements Collection<E>, IListenerSet<E>, KMappedMarker {
    public static final int $stable = 8;
    private final ArrayList<E> listeners;

    private CopyOnLoopListenerSet(ArrayList<E> arrayList) {
        this.listeners = arrayList;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // com.android.systemui.util.IListenerSet
    public boolean addIfAbsent(E e) {
        if (this.listeners.contains(e)) {
            return false;
        }
        return this.listeners.add(e);
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.listeners.contains(obj);
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(Collection<? extends Object> collection) {
        return this.listeners.containsAll(collection);
    }

    public int getSize() {
        return this.listeners.size();
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.listeners.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return new ArrayIterator(this.listeners.toArray());
    }

    @Override // java.util.Collection, com.android.systemui.util.IListenerSet, java.util.Set
    public boolean remove(E e) {
        if (e == null) {
            return false;
        }
        return this.listeners.remove(e);
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection, java.util.Set
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Collection, java.util.Set
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        return (T[]) CollectionToArray.toArray(this, tArr);
    }

    public CopyOnLoopListenerSet() {
        this(new ArrayList());
    }
}
