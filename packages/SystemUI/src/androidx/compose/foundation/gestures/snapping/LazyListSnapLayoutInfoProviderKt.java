package androidx.compose.foundation.gestures.snapping;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.LazyListLayoutInfo;
import androidx.compose.foundation.lazy.LazyListMeasureResult;

/* loaded from: classes.dex */
public abstract class LazyListSnapLayoutInfoProviderKt {
    public static final int getSingleAxisViewportSize(LazyListLayoutInfo lazyListLayoutInfo) {
        Orientation orientation = ((LazyListMeasureResult) lazyListLayoutInfo).orientation;
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListLayoutInfo;
        return (int) (orientation == Orientation.Vertical ? lazyListMeasureResult.m152getViewportSizeYbymL2g() & 4294967295L : lazyListMeasureResult.m152getViewportSizeYbymL2g() >> 32);
    }
}
