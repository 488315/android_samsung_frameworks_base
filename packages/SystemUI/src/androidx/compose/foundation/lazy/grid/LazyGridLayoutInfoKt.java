package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.gestures.Orientation;
import java.util.List;

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
            int iVisibleLinesAverageMainAxisSize$lineOf = visibleLinesAverageMainAxisSize$lineOf(z, lazyGridLayoutInfo, i);
            if (iVisibleLinesAverageMainAxisSize$lineOf == -1) {
                i++;
            } else {
                int iMax = 0;
                while (i < list.size() && visibleLinesAverageMainAxisSize$lineOf(z, lazyGridLayoutInfo, i) == iVisibleLinesAverageMainAxisSize$lineOf) {
                    iMax = Math.max(iMax, (int) (z ? ((LazyGridMeasuredItem) ((LazyGridItemInfo) list.get(i))).size & 4294967295L : ((LazyGridMeasuredItem) ((LazyGridItemInfo) list.get(i))).size >> 32));
                    i++;
                }
                i2 += iMax;
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
