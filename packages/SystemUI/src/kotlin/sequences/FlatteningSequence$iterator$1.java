package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlatteningSequence$iterator$1 implements Iterator, KMappedMarker {
    public Iterator itemIterator;
    public final Iterator iterator;
    public int state;
    public final /* synthetic */ FlatteningSequence this$0;

    public FlatteningSequence$iterator$1(FlatteningSequence flatteningSequence) {
        this.this$0 = flatteningSequence;
        this.iterator = flatteningSequence.sequence.iterator();
    }

    public final boolean ensureItemIterator() {
        Iterator it = this.itemIterator;
        if (it != null && it.hasNext()) {
            this.state = 1;
            return true;
        }
        while (this.iterator.hasNext()) {
            Object next = this.iterator.next();
            FlatteningSequence flatteningSequence = this.this$0;
            Iterator it2 = (Iterator) flatteningSequence.iterator.mo779invoke(flatteningSequence.transformer.mo779invoke(next));
            if (it2.hasNext()) {
                this.itemIterator = it2;
                this.state = 1;
                return true;
            }
        }
        this.state = 2;
        this.itemIterator = null;
        return false;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.state;
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        return ensureItemIterator();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.state;
        if (i == 2) {
            throw new NoSuchElementException();
        }
        if (i == 0 && !ensureItemIterator()) {
            throw new NoSuchElementException();
        }
        this.state = 0;
        Iterator it = this.itemIterator;
        it.getClass();
        return it.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
