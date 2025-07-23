package kotlin.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractIterator implements Iterator, KMappedMarker {
    public Object nextValue;
    public int state;

    public abstract void computeNext();

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.state;
        if (i == 0) {
            this.state = 3;
            computeNext();
            return this.state == 1;
        }
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        throw new IllegalArgumentException("hasNext called when the iterator is in the FAILED state.");
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.state;
        if (i == 1) {
            this.state = 0;
            return this.nextValue;
        }
        if (i != 2) {
            this.state = 3;
            computeNext();
            if (this.state == 1) {
                this.state = 0;
                return this.nextValue;
            }
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
