package com.android.server.permission.jarjar.kotlin.ranges;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class IntProgressionIterator implements Iterator {
    public final int finalElement;
    public boolean hasNext;
    public int next;
    public final int step;

    public IntProgressionIterator(int i, int i2, int i3) {
        this.step = i3;
        this.finalElement = i2;
        boolean z = false;
        if (i3 <= 0 ? i >= i2 : i <= i2) {
            z = true;
        }
        this.hasNext = z;
        this.next = z ? i : i2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.hasNext;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.next;
        if (i != this.finalElement) {
            this.next = this.step + i;
        } else {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            this.hasNext = false;
        }
        return Integer.valueOf(i);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException(
                "Operation is not supported for read-only collection");
    }
}
