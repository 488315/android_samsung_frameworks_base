package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class MergingSequence implements Sequence {
    public final Sequence sequence1;
    public final Sequence sequence2;
    public final Function2 transform;

    /* renamed from: kotlin.sequences.MergingSequence$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public final Iterator iterator1;
        public final Iterator iterator2;

        public AnonymousClass1() {
            this.iterator1 = MergingSequence.this.sequence1.iterator();
            this.iterator2 = MergingSequence.this.sequence2.iterator();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.iterator1.hasNext() && this.iterator2.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            return MergingSequence.this.transform.invoke(this.iterator1.next(), this.iterator2.next());
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public MergingSequence(Sequence sequence, Sequence sequence2, Function2 function2) {
        this.sequence1 = sequence;
        this.sequence2 = sequence2;
        this.transform = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new AnonymousClass1();
    }
}
