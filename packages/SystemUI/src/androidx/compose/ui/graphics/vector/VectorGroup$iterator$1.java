package androidx.compose.ui.graphics.vector;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorGroup$iterator$1 implements Iterator<VectorNode>, KMappedMarker {
    public final Iterator it;

    public VectorGroup$iterator$1(VectorGroup vectorGroup) {
        this.it = vectorGroup.children.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.it.hasNext();
    }

    @Override // java.util.Iterator
    public final VectorNode next() {
        return (VectorNode) this.it.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
