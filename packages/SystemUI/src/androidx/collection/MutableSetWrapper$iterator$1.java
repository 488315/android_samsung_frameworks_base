package androidx.collection;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableSetWrapper$iterator$1 implements Iterator, KMappedMarker {
    public int current = -1;
    public final SequenceBuilderIterator iterator;
    public final /* synthetic */ MutableSetWrapper this$0;

    public MutableSetWrapper$iterator$1(MutableSetWrapper mutableSetWrapper) {
        this.this$0 = mutableSetWrapper;
        this.iterator = SequencesKt__SequenceBuilderKt.iterator(new MutableSetWrapper$iterator$1$iterator$1(mutableSetWrapper, this, null));
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.iterator.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        int i = this.current;
        if (i != -1) {
            this.this$0.parent.removeElementAt(i);
            this.current = -1;
        }
    }
}
