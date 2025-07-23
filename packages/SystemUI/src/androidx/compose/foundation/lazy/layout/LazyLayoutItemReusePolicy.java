package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableOrderedSetWrapper$iterator$1;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.ui.layout.SubcomposeSlotReusePolicy;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutItemReusePolicy implements SubcomposeSlotReusePolicy {
    public final MutableObjectIntMap countPerType = ObjectIntMapKt.mutableObjectIntMapOf();
    public final LazyLayoutItemContentFactory factory;

    public LazyLayoutItemReusePolicy(LazyLayoutItemContentFactory lazyLayoutItemContentFactory) {
        this.factory = lazyLayoutItemContentFactory;
    }

    @Override // androidx.compose.ui.layout.SubcomposeSlotReusePolicy
    public final boolean areCompatible(Object obj, Object obj2) {
        LazyLayoutItemContentFactory lazyLayoutItemContentFactory = this.factory;
        return Intrinsics.areEqual(lazyLayoutItemContentFactory.getContentType(obj), lazyLayoutItemContentFactory.getContentType(obj2));
    }

    @Override // androidx.compose.ui.layout.SubcomposeSlotReusePolicy
    public final void getSlotsToRetain(SubcomposeSlotReusePolicy.SlotIdsSet slotIdsSet) {
        MutableObjectIntMap mutableObjectIntMap = this.countPerType;
        mutableObjectIntMap.clear();
        Iterator it = slotIdsSet.iterator();
        while (true) {
            MutableOrderedSetWrapper$iterator$1 mutableOrderedSetWrapper$iterator$1 = (MutableOrderedSetWrapper$iterator$1) it;
            if (!mutableOrderedSetWrapper$iterator$1.hasNext()) {
                return;
            }
            Object next = mutableOrderedSetWrapper$iterator$1.next();
            Object contentType = this.factory.getContentType(next);
            int findKeyIndex = mutableObjectIntMap.findKeyIndex(contentType);
            int i = findKeyIndex >= 0 ? mutableObjectIntMap.values[findKeyIndex] : 0;
            if (i == 7) {
                slotIdsSet.remove(next);
            } else {
                mutableObjectIntMap.set(i + 1, contentType);
            }
        }
    }
}
