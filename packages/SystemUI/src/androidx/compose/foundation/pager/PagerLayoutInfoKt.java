package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.Orientation;

/* loaded from: classes.dex */
public abstract class PagerLayoutInfoKt {
    public static final int getMainAxisViewportSize(PagerLayoutInfo pagerLayoutInfo) {
        Orientation orientation = ((PagerMeasureResult) pagerLayoutInfo).orientation;
        PagerMeasureResult pagerMeasureResult = (PagerMeasureResult) pagerLayoutInfo;
        return (int) (orientation == Orientation.Vertical ? pagerMeasureResult.m180getViewportSizeYbymL2g() & 4294967295L : pagerMeasureResult.m180getViewportSizeYbymL2g() >> 32);
    }
}
