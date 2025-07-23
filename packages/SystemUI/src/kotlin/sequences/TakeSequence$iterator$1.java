package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TakeSequence$iterator$1 implements Iterator, KMappedMarker {
    public final Iterator iterator;
    public int left;

    public TakeSequence$iterator$1(TakeSequence takeSequence) {
        this.left = takeSequence.count;
        this.iterator = takeSequence.sequence.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.left > 0 && this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.left;
        if (i == 0) {
            throw new NoSuchElementException();
        }
        this.left = i - 1;
        return this.iterator.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
