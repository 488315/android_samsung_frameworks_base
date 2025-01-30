package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TransformingSequence$iterator$1 implements Iterator, KMappedMarker {
    public final Iterator iterator;
    public final /* synthetic */ TransformingSequence this$0;

    public TransformingSequence$iterator$1(TransformingSequence transformingSequence) {
        this.this$0 = transformingSequence;
        this.iterator = transformingSequence.sequence.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.this$0.transformer.invoke(this.iterator.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
