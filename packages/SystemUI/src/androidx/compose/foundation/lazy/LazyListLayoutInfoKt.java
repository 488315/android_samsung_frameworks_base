package androidx.compose.foundation.lazy;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyListLayoutInfoKt {
    public static final int visibleItemsAverageSize(LazyListLayoutInfo lazyListLayoutInfo) {
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListLayoutInfo;
        List list = lazyListMeasureResult.visibleItemsInfo;
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += ((LazyListItemInfo) list.get(i2)).getSize();
        }
        return (i / list.size()) + lazyListMeasureResult.mainAxisItemSpacing;
    }
}
