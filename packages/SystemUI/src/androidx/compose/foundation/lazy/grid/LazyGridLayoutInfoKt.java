package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.gestures.Orientation;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyGridLayoutInfoKt {
    public static final int visibleLinesAverageMainAxisSize(LazyGridLayoutInfo lazyGridLayoutInfo) {
        boolean z = ((LazyGridMeasureResult) lazyGridLayoutInfo).orientation == Orientation.Vertical;
        LazyGridMeasureResult lazyGridMeasureResult = (LazyGridMeasureResult) lazyGridLayoutInfo;
        List list = lazyGridMeasureResult.visibleItemsInfo;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < list.size()) {
            int visibleLinesAverageMainAxisSize$lineOf = visibleLinesAverageMainAxisSize$lineOf(z, lazyGridLayoutInfo, i);
            if (visibleLinesAverageMainAxisSize$lineOf == -1) {
                i++;
            } else {
                int i4 = 0;
                while (i < list.size() && visibleLinesAverageMainAxisSize$lineOf(z, lazyGridLayoutInfo, i) == visibleLinesAverageMainAxisSize$lineOf) {
                    i4 = Math.max(i4, (int) (z ? ((LazyGridMeasuredItem) ((LazyGridItemInfo) list.get(i))).size & 4294967295L : ((LazyGridMeasuredItem) ((LazyGridItemInfo) list.get(i))).size >> 32));
                    i++;
                }
                i2 += i4;
                i3++;
            }
        }
        return (i2 / i3) + lazyGridMeasureResult.mainAxisItemSpacing;
    }

    public static final int visibleLinesAverageMainAxisSize$lineOf(boolean z, LazyGridLayoutInfo lazyGridLayoutInfo, int i) {
        LazyGridMeasureResult lazyGridMeasureResult = (LazyGridMeasureResult) lazyGridLayoutInfo;
        return z ? ((LazyGridMeasuredItem) ((LazyGridItemInfo) lazyGridMeasureResult.visibleItemsInfo.get(i))).row : ((LazyGridMeasuredItem) ((LazyGridItemInfo) lazyGridMeasureResult.visibleItemsInfo.get(i))).column;
    }
}
