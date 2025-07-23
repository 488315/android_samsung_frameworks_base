package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MergingSequence$iterator$1 implements Iterator, KMappedMarker {
    public final Iterator iterator1;
    public final Iterator iterator2;
    public final /* synthetic */ MergingSequence this$0;

    public MergingSequence$iterator$1(MergingSequence mergingSequence) {
        this.this$0 = mergingSequence;
        this.iterator1 = mergingSequence.sequence1.iterator();
        this.iterator2 = mergingSequence.sequence2.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.iterator1.hasNext() && this.iterator2.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.this$0.transform.invoke(this.iterator1.next(), this.iterator2.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
