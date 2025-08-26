package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableOrderedSetWrapper;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.ui.layout.SubcomposeSlotReusePolicy;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

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
            MutableOrderedSetWrapper.AnonymousClass1 anonymousClass1 = (MutableOrderedSetWrapper.AnonymousClass1) it;
            if (!anonymousClass1.hasNext()) {
                return;
            }
            Object next = anonymousClass1.next();
            Object contentType = this.factory.getContentType(next);
            int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(contentType);
            int i = iFindKeyIndex >= 0 ? mutableObjectIntMap.values[iFindKeyIndex] : 0;
            if (i == 7) {
                slotIdsSet.remove(next);
            } else {
                mutableObjectIntMap.set(i + 1, contentType);
            }
        }
    }
}
