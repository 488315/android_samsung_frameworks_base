package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FilteringSequence$iterator$1 implements Iterator, KMappedMarker {
    public final Iterator iterator;
    public Object nextItem;
    public int nextState = -1;
    public final /* synthetic */ FilteringSequence this$0;

    public FilteringSequence$iterator$1(FilteringSequence filteringSequence) {
        this.this$0 = filteringSequence;
        this.iterator = filteringSequence.sequence.iterator();
    }

    public final void calcNext() {
        while (this.iterator.hasNext()) {
            Object next = this.iterator.next();
            if (((Boolean) this.this$0.predicate.invoke(next)).booleanValue() == this.this$0.sendWhen) {
                this.nextItem = next;
                this.nextState = 1;
                return;
            }
        }
        this.nextState = 0;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.nextState == -1) {
            calcNext();
        }
        return this.nextState == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        Object obj = this.nextItem;
        this.nextItem = null;
        this.nextState = -1;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
