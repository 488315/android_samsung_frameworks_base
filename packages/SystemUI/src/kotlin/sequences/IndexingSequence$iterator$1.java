package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class IndexingSequence$iterator$1 implements Iterator, KMappedMarker {
    public int index;
    public final Iterator iterator;

    public IndexingSequence$iterator$1(IndexingSequence indexingSequence) {
        this.iterator = indexingSequence.sequence.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.index;
        this.index = i + 1;
        if (i >= 0) {
            return new IndexedValue(i, this.iterator.next());
        }
        CollectionsKt__CollectionsKt.throwIndexOverflow();
        throw null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
