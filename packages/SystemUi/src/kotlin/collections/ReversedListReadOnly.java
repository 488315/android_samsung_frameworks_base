package kotlin.collections;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.util.List;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReversedListReadOnly extends AbstractList {
    public final List delegate;

    public ReversedListReadOnly(List<Object> list) {
        this.delegate = list;
    }

    @Override // java.util.List
    public final Object get(int i) {
        List list = this.delegate;
        IntRange intRange = new IntRange(0, CollectionsKt__CollectionsKt.getLastIndex(this));
        if (intRange.first <= i && i <= intRange.last) {
            return list.get(CollectionsKt__CollectionsKt.getLastIndex(this) - i);
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("Element index ", i, " must be in range [");
        m1m.append(new IntRange(0, CollectionsKt__CollectionsKt.getLastIndex(this)));
        m1m.append("].");
        throw new IndexOutOfBoundsException(m1m.toString());
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.delegate.size();
    }
}
