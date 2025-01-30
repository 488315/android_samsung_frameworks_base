package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
