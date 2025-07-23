package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AbstractMap$values$1$iterator$1 implements Iterator, KMappedMarker {
    public final /* synthetic */ Iterator $entryIterator;

    public AbstractMap$values$1$iterator$1(Iterator<? extends Map.Entry<Object, Object>> it) {
        this.$entryIterator = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.$entryIterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return ((Map.Entry) this.$entryIterator.next()).getValue();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
