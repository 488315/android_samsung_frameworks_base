package kotlin.collections;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.ListIterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ReversedListReadOnly$listIterator$1 implements ListIterator, KMappedMarker {
    public final ListIterator delegateIterator;
    public final /* synthetic */ ReversedListReadOnly this$0;

    public ReversedListReadOnly$listIterator$1(ReversedListReadOnly reversedListReadOnly, int i) {
        this.this$0 = reversedListReadOnly;
        List list = reversedListReadOnly.delegate;
        if (i >= 0 && i <= reversedListReadOnly.size()) {
            this.delegateIterator = list.listIterator(reversedListReadOnly.size() - i);
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Position index ", " must be in range [");
        m.append(new IntRange(0, reversedListReadOnly.size()));
        m.append("].");
        throw new IndexOutOfBoundsException(m.toString());
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.delegateIterator.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.delegateIterator.hasNext();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return this.delegateIterator.previous();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        ReversedListReadOnly reversedListReadOnly = this.this$0;
        return CollectionsKt__CollectionsKt.getLastIndex(reversedListReadOnly) - this.delegateIterator.previousIndex();
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        return this.delegateIterator.next();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        ReversedListReadOnly reversedListReadOnly = this.this$0;
        return CollectionsKt__CollectionsKt.getLastIndex(reversedListReadOnly) - this.delegateIterator.nextIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
