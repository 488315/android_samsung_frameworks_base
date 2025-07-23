package kotlin.collections;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReversedListReadOnly extends AbstractList {
    public final List delegate;

    public ReversedListReadOnly(List<Object> list) {
        this.delegate = list;
    }

    @Override // java.util.List
    public final Object get(int i) {
        List list = this.delegate;
        if (i >= 0 && i <= CollectionsKt__CollectionsKt.getLastIndex(this)) {
            return list.get(CollectionsKt__CollectionsKt.getLastIndex(this) - i);
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Element index ", " must be in range [");
        m.append(new IntRange(0, CollectionsKt__CollectionsKt.getLastIndex(this)));
        m.append("].");
        throw new IndexOutOfBoundsException(m.toString());
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.delegate.size();
    }

    @Override // kotlin.collections.AbstractList, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new ReversedListReadOnly$listIterator$1(this, 0);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return new ReversedListReadOnly$listIterator$1(this, 0);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        return new ReversedListReadOnly$listIterator$1(this, i);
    }
}
