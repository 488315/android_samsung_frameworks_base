package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SparseArrayKt$valueIterator$1 implements Iterator, KMappedMarker {
    public final /* synthetic */ SparseArray $this_valueIterator;
    public int index;

    public SparseArrayKt$valueIterator$1(SparseArray<Object> sparseArray) {
        this.$this_valueIterator = sparseArray;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.$this_valueIterator.size();
    }

    @Override // java.util.Iterator
    public final Object next() {
        SparseArray sparseArray = this.$this_valueIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseArray.valueAt(i);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
