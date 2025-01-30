package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class IndexBasedArrayIterator implements Iterator, KMappedMarker {
    public boolean canRemove;
    public int index;
    public int size;

    public IndexBasedArrayIterator(int i) {
        this.size = i;
    }

    public abstract Object elementAt(int i);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.size;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object elementAt = elementAt(this.index);
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

    public abstract void removeAt(int i);
}
