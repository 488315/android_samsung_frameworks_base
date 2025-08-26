package androidx.compose.foundation.lazy;

import java.util.List;

/* loaded from: classes.dex */
public abstract class LazyListLayoutInfoKt {
    public static final int visibleItemsAverageSize(LazyListLayoutInfo lazyListLayoutInfo) {
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListLayoutInfo;
        List list = lazyListMeasureResult.visibleItemsInfo;
        int size = list.size();
        int size2 = 0;
        for (int i = 0; i < size; i++) {
            size2 += ((LazyListItemInfo) list.get(i)).getSize();
        }
        return (size2 / list.size()) + lazyListMeasureResult.mainAxisItemSpacing;
    }
}
