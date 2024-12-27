package com.android.systemui.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;

public final class ListenerSet<E> implements Collection<E>, IListenerSet<E>, KMappedMarker {
    public static final int $stable = 8;
    private final CopyOnWriteArrayList<E> listeners;

    private ListenerSet(CopyOnWriteArrayList<E> copyOnWriteArrayList) {
        this.listeners = copyOnWriteArrayList;
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
        return this.listeners.addIfAbsent(e);
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
        return this.listeners.iterator();
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

    public ListenerSet() {
        this(new CopyOnWriteArrayList());
    }
}
