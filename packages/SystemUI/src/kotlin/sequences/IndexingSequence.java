package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class IndexingSequence implements Sequence {
    public final Sequence sequence;

    /* renamed from: kotlin.sequences.IndexingSequence$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int index;
        public final Iterator iterator;

        public AnonymousClass1(IndexingSequence indexingSequence) {
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

    public IndexingSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new AnonymousClass1(this);
    }
}
