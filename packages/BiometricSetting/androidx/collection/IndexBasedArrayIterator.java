package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: IndexBasedArrayIterator.kt */
/* loaded from: classes.dex */
public abstract class IndexBasedArrayIterator<T> implements Iterator<T> {
    private boolean canRemove;
    private int index;
    private int size;

    public IndexBasedArrayIterator(int i) {
        this.size = i;
    }

    protected abstract T elementAt(int i);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.size;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T elementAt = elementAt(this.index);
        this.index++;
        this.canRemove = true;
        return elementAt;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.canRemove) {
            throw new IllegalStateException("Call next() before removing an element.".toString());
        }
        int i = this.index - 1;
        this.index = i;
        removeAt(i);
        this.size--;
        this.canRemove = false;
    }

    protected abstract void removeAt(int i);
}
