package kotlin.sequences;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SequencesKt___SequencesKt$sortedWith$1 implements Sequence {
    public final /* synthetic */ Comparator $comparator;
    public final /* synthetic */ Sequence $this_sortedWith;

    public SequencesKt___SequencesKt$sortedWith$1(Sequence sequence, Comparator<Object> comparator) {
        this.$this_sortedWith = sequence;
        this.$comparator = comparator;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        List mutableList = SequencesKt___SequencesKt.toMutableList(this.$this_sortedWith);
        CollectionsKt__MutableCollectionsJVMKt.sortWith(mutableList, this.$comparator);
        return mutableList.iterator();
    }
}
