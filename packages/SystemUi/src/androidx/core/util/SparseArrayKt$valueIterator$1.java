package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
