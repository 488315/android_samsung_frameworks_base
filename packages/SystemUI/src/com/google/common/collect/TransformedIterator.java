package com.google.common.collect;

import java.util.Iterator;

/* loaded from: classes4.dex */
public abstract class TransformedIterator implements Iterator {
    public final Iterator backingIterator;

    public TransformedIterator(Iterator<Object> it) {
        it.getClass();
        this.backingIterator = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.backingIterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return transform(this.backingIterator.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.backingIterator.remove();
    }

    public abstract Object transform(Object obj);
}
