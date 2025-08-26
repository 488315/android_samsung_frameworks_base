package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class IndexingIterable implements Iterable, KMappedMarker {
    public final Function0 iteratorFactory;

    public IndexingIterable(Function0 function0) {
        this.iteratorFactory = function0;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new IndexingIterator((Iterator) this.iteratorFactory.invoke());
    }
}
