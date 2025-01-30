package com.android.systemui.classifier;

import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TimeLimitedMotionEventBuffer implements List {
    public final long mMaxAgeMs;
    public final List mMotionEvents = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Iter implements ListIterator {
        public final ListIterator mIterator;

        public Iter(TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer, int i) {
            this.mIterator = ((ArrayList) timeLimitedMotionEventBuffer.mMotionEvents).listIterator(i);
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final boolean hasNext() {
            return this.mIterator.hasNext();
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.mIterator.hasPrevious();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final Object next() {
            return (MotionEvent) this.mIterator.next();
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.mIterator.nextIndex();
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            return (MotionEvent) this.mIterator.previous();
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.mIterator.previousIndex();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            this.mIterator.remove();
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    public TimeLimitedMotionEventBuffer(long j) {
        this.mMaxAgeMs = j;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        boolean addAll = ((ArrayList) this.mMotionEvents).addAll(collection);
        ejectOldEvents();
        return addAll;
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        ((ArrayList) this.mMotionEvents).clear();
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return ((ArrayList) this.mMotionEvents).contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.mMotionEvents.containsAll(collection);
    }

    public final void ejectOldEvents() {
        if (((ArrayList) this.mMotionEvents).isEmpty()) {
            return;
        }
        ListIterator listIterator = listIterator();
        long eventTime = ((MotionEvent) ((ArrayList) this.mMotionEvents).get(r1.size() - 1)).getEventTime();
        while (true) {
            Iter iter = (Iter) listIterator;
            if (!iter.hasNext()) {
                return;
            }
            MotionEvent motionEvent = (MotionEvent) iter.next();
            if (eventTime - motionEvent.getEventTime() > this.mMaxAgeMs) {
                iter.remove();
                motionEvent.recycle();
            }
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean equals(Object obj) {
        return ((ArrayList) this.mMotionEvents).equals(obj);
    }

    @Override // java.util.List
    public final Object get(int i) {
        return (MotionEvent) ((ArrayList) this.mMotionEvents).get(i);
    }

    @Override // java.util.List, java.util.Collection
    public final int hashCode() {
        return ((ArrayList) this.mMotionEvents).hashCode();
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return ((ArrayList) this.mMotionEvents).indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return ((ArrayList) this.mMotionEvents).isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return ((ArrayList) this.mMotionEvents).iterator();
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return ((ArrayList) this.mMotionEvents).lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new Iter(this, 0);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        return (MotionEvent) ((ArrayList) this.mMotionEvents).remove(i);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        return ((ArrayList) this.mMotionEvents).removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        return ((ArrayList) this.mMotionEvents).retainAll(collection);
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return ((ArrayList) this.mMotionEvents).size();
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        return ((ArrayList) this.mMotionEvents).subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return ((ArrayList) this.mMotionEvents).toArray();
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new Iter(this, i);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        return ((ArrayList) this.mMotionEvents).remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return ((ArrayList) this.mMotionEvents).toArray(objArr);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        boolean add = ((ArrayList) this.mMotionEvents).add((MotionEvent) obj);
        ejectOldEvents();
        return add;
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }
}
