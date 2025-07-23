package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TakeWhileSequence$iterator$1 implements Iterator, KMappedMarker {
    public final Iterator iterator;
    public Object nextItem;
    public int nextState = -1;
    public final /* synthetic */ TakeWhileSequence this$0;

    public TakeWhileSequence$iterator$1(TakeWhileSequence takeWhileSequence) {
        this.this$0 = takeWhileSequence;
        this.iterator = takeWhileSequence.sequence.iterator();
    }

    public final void calcNext$2() {
        if (this.iterator.hasNext()) {
            Object next = this.iterator.next();
            if (((Boolean) this.this$0.predicate.mo779invoke(next)).booleanValue()) {
                this.nextState = 1;
                this.nextItem = next;
                return;
            }
        }
        this.nextState = 0;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.nextState == -1) {
            calcNext$2();
        }
        return this.nextState == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.nextState == -1) {
            calcNext$2();
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
