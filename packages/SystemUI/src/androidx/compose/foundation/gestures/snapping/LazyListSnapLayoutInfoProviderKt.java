package androidx.compose.foundation.gestures.snapping;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.LazyListLayoutInfo;
import androidx.compose.foundation.lazy.LazyListMeasureResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyListSnapLayoutInfoProviderKt {
    public static final int getSingleAxisViewportSize(LazyListLayoutInfo lazyListLayoutInfo) {
        Orientation orientation = ((LazyListMeasureResult) lazyListLayoutInfo).orientation;
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListLayoutInfo;
        return (int) (orientation == Orientation.Vertical ? lazyListMeasureResult.m151getViewportSizeYbymL2g() & 4294967295L : lazyListMeasureResult.m151getViewportSizeYbymL2g() >> 32);
    }
}
