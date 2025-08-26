package kotlin.collections;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;

/* loaded from: classes4.dex */
public class ReversedListReadOnly extends AbstractList {
    public final List delegate;

    /* renamed from: kotlin.collections.ReversedListReadOnly$listIterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements ListIterator, KMappedMarker {
        public final ListIterator delegateIterator;

        public AnonymousClass1(int i) {
            List list = ReversedListReadOnly.this.delegate;
            if (i >= 0 && i <= ReversedListReadOnly.this.size()) {
                this.delegateIterator = list.listIterator(ReversedListReadOnly.this.size() - i);
                return;
            }
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Position index ", " must be in range [");
            sbM.append(new IntRange(0, ReversedListReadOnly.this.size()));
            sbM.append("].");
            throw new IndexOutOfBoundsException(sbM.toString());
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
            ReversedListReadOnly reversedListReadOnly = ReversedListReadOnly.this;
            return CollectionsKt__CollectionsKt.getLastIndex(reversedListReadOnly) - this.delegateIterator.previousIndex();
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            return this.delegateIterator.next();
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            ReversedListReadOnly reversedListReadOnly = ReversedListReadOnly.this;
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

    public ReversedListReadOnly(List<Object> list) {
        this.delegate = list;
    }

    @Override // java.util.List
    public final Object get(int i) {
        List list = this.delegate;
        if (i >= 0 && i <= CollectionsKt__CollectionsKt.getLastIndex(this)) {
            return list.get(CollectionsKt__CollectionsKt.getLastIndex(this) - i);
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Element index ", " must be in range [");
        sbM.append(new IntRange(0, CollectionsKt__CollectionsKt.getLastIndex(this)));
        sbM.append("].");
        throw new IndexOutOfBoundsException(sbM.toString());
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.delegate.size();
    }

    @Override // kotlin.collections.AbstractList, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new AnonymousClass1(0);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return new AnonymousClass1(0);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        return new AnonymousClass1(i);
    }
}
