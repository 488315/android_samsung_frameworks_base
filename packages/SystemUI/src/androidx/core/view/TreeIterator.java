package androidx.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TreeIterator implements Iterator, KMappedMarker {
    public final Function1 getChildIterator;
    public Iterator iterator;
    public final List stack = new ArrayList();

    public TreeIterator(Iterator<Object> it, Function1 function1) {
        this.getChildIterator = function1;
        this.iterator = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object next = this.iterator.next();
        Iterator it = (Iterator) this.getChildIterator.mo779invoke(next);
        if (it != null && it.hasNext()) {
            ((ArrayList) this.stack).add(this.iterator);
            this.iterator = it;
            return next;
        }
        while (!this.iterator.hasNext() && !((ArrayList) this.stack).isEmpty()) {
            this.iterator = (Iterator) CollectionsKt___CollectionsKt.last(this.stack);
            ArrayList arrayList = (ArrayList) this.stack;
            if (arrayList.isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
            arrayList.remove(arrayList.size() - 1);
        }
        return next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
