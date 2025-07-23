package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.NoSuchElementException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PersistentVectorIterator<T> extends AbstractListIterator<T> {
    public final Object[] tail;
    public final TrieIterator trieIterator;

    public PersistentVectorIterator(Object[] objArr, T[] tArr, int i, int i2, int i3) {
        super(i, i2);
        this.tail = tArr;
        int i4 = (i2 - 1) & (-32);
        this.trieIterator = new TrieIterator(objArr, i > i4 ? i4 : i, i4, i3);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator, java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (this.trieIterator.hasNext()) {
            this.index++;
            return this.trieIterator.next();
        }
        Object[] objArr = this.tail;
        int i = this.index;
        this.index = i + 1;
        return objArr[i - this.trieIterator.size];
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i = this.index;
        TrieIterator trieIterator = this.trieIterator;
        int i2 = trieIterator.size;
        if (i <= i2) {
            this.index = i - 1;
            return trieIterator.previous();
        }
        Object[] objArr = this.tail;
        int i3 = i - 1;
        this.index = i3;
        return objArr[i3 - i2];
    }
}
