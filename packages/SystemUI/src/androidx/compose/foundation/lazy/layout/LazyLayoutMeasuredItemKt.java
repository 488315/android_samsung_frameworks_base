package androidx.compose.foundation.lazy.layout;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutMeasuredItemKt {
    public static final LazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 LazyLayoutMeasuredItemIndexComparator = new LazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0();

    public static final List updatedVisibleItems(int i, int i2, List list, List list2) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList2 = new ArrayList(list2);
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            LazyLayoutMeasuredItem lazyLayoutMeasuredItem = (LazyLayoutMeasuredItem) arrayList.get(i3);
            int index = lazyLayoutMeasuredItem.getIndex();
            if (i <= index && index <= i2) {
                arrayList2.add(lazyLayoutMeasuredItem);
            }
        }
        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList2, LazyLayoutMeasuredItemIndexComparator);
        return arrayList2;
    }
}
