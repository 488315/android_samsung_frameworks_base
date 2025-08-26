package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class AbstractMap$keys$1$iterator$1 implements Iterator, KMappedMarker {
    public final /* synthetic */ Iterator $entryIterator;

    public AbstractMap$keys$1$iterator$1(Iterator<? extends Map.Entry<Object, Object>> it) {
        this.$entryIterator = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.$entryIterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return ((Map.Entry) this.$entryIterator.next()).getKey();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
