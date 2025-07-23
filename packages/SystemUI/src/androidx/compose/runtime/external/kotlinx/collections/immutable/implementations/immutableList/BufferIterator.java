package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.NoSuchElementException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BufferIterator<T> extends AbstractListIterator<T> {
    public final Object[] buffer;

    public BufferIterator(T[] tArr, int i, int i2) {
        super(i, i2);
        this.buffer = tArr;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator, java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        return objArr[i];
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.buffer;
        int i = this.index - 1;
        this.index = i;
        return objArr[i];
    }
}
