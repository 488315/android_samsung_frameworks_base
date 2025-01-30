package com.google.protobuf;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnmodifiableLazyStringList extends AbstractList implements LazyStringList, RandomAccess {
    public final LazyStringList list;

    public UnmodifiableLazyStringList(LazyStringList lazyStringList) {
        this.list = lazyStringList;
    }

    @Override // com.google.protobuf.LazyStringList
    public final void add(ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        return (String) this.list.get(i);
    }

    @Override // com.google.protobuf.LazyStringList
    public final Object getRaw(int i) {
        return this.list.getRaw(i);
    }

    @Override // com.google.protobuf.LazyStringList
    public final List getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new Iterator(this) { // from class: com.google.protobuf.UnmodifiableLazyStringList.2
            public final Iterator iter;

            {
                this.iter = this.list.iterator();
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override // java.util.Iterator
            public final Object next() {
                return (String) this.iter.next();
            }

            @Override // java.util.Iterator
            public final void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        return new ListIterator(this, i) { // from class: com.google.protobuf.UnmodifiableLazyStringList.1
            public final ListIterator iter;

            {
                this.iter = this.list.listIterator(i);
            }

            @Override // java.util.ListIterator
            public final void add(Object obj) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override // java.util.ListIterator
            public final boolean hasPrevious() {
                return this.iter.hasPrevious();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final Object next() {
                return (String) this.iter.next();
            }

            @Override // java.util.ListIterator
            public final int nextIndex() {
                return this.iter.nextIndex();
            }

            @Override // java.util.ListIterator
            public final Object previous() {
                return (String) this.iter.previous();
            }

            @Override // java.util.ListIterator
            public final int previousIndex() {
                return this.iter.previousIndex();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator
            public final void set(Object obj) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.list.size();
    }

    @Override // com.google.protobuf.LazyStringList
    public final LazyStringList getUnmodifiableView() {
        return this;
    }
}
