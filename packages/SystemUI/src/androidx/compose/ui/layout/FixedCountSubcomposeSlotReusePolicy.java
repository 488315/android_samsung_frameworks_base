package androidx.compose.ui.layout;

import androidx.collection.MutableOrderedScatterSet;
import androidx.compose.ui.layout.SubcomposeSlotReusePolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FixedCountSubcomposeSlotReusePolicy implements SubcomposeSlotReusePolicy {
    public final int maxSlotsToRetainForReuse;

    public FixedCountSubcomposeSlotReusePolicy(int i) {
        this.maxSlotsToRetainForReuse = i;
    }

    @Override // androidx.compose.ui.layout.SubcomposeSlotReusePolicy
    public final boolean areCompatible(Object obj, Object obj2) {
        return true;
    }

    @Override // androidx.compose.ui.layout.SubcomposeSlotReusePolicy
    public final void getSlotsToRetain(SubcomposeSlotReusePolicy.SlotIdsSet slotIdsSet) {
        int i;
        MutableOrderedScatterSet mutableOrderedScatterSet = slotIdsSet.set;
        int i2 = mutableOrderedScatterSet._size;
        int i3 = this.maxSlotsToRetainForReuse;
        if (i2 > i3) {
            long[] jArr = mutableOrderedScatterSet.nodes;
            int i4 = mutableOrderedScatterSet.head;
            while (i4 != Integer.MAX_VALUE && (i = mutableOrderedScatterSet._size) > i3 && i != 0) {
                int i5 = (int) (jArr[i4] & 2147483647L);
                mutableOrderedScatterSet.removeElementAt(i4);
                i4 = i5;
            }
        }
    }
}
