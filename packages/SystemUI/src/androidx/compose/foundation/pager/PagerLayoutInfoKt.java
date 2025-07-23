package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.Orientation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PagerLayoutInfoKt {
    public static final int getMainAxisViewportSize(PagerLayoutInfo pagerLayoutInfo) {
        Orientation orientation = ((PagerMeasureResult) pagerLayoutInfo).orientation;
        PagerMeasureResult pagerMeasureResult = (PagerMeasureResult) pagerLayoutInfo;
        return (int) (orientation == Orientation.Vertical ? pagerMeasureResult.m179getViewportSizeYbymL2g() & 4294967295L : pagerMeasureResult.m179getViewportSizeYbymL2g() >> 32);
    }
}
