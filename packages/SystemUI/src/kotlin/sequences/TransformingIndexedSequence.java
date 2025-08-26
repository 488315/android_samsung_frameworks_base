package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class TransformingIndexedSequence implements Sequence {
    public final Sequence sequence;
    public final Function2 transformer;

    /* renamed from: kotlin.sequences.TransformingIndexedSequence$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int index;
        public final Iterator iterator;

        public AnonymousClass1() {
            this.iterator = TransformingIndexedSequence.this.sequence.iterator();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            Function2 function2 = TransformingIndexedSequence.this.transformer;
            int i = this.index;
            this.index = i + 1;
            if (i >= 0) {
                return function2.invoke(Integer.valueOf(i), this.iterator.next());
            }
            CollectionsKt__CollectionsKt.throwIndexOverflow();
            throw null;
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public TransformingIndexedSequence(Sequence sequence, Function2 function2) {
        this.sequence = sequence;
        this.transformer = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new AnonymousClass1();
    }
}
