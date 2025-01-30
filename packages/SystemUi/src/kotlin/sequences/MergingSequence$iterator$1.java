package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
