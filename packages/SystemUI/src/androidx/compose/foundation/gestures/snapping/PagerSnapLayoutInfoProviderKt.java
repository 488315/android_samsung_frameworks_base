package androidx.compose.foundation.gestures.snapping;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.pager.PagerMeasureResult;
import androidx.compose.foundation.pager.PagerState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PagerSnapLayoutInfoProviderKt {
    public static final float dragGestureDelta(PagerState pagerState) {
        return ((PagerMeasureResult) pagerState.getLayoutInfo()).orientation == Orientation.Horizontal ? Float.intBitsToFloat((int) (pagerState.m180getUpDownDifferenceF1C5BW0$foundation_release() >> 32)) : Float.intBitsToFloat((int) (pagerState.m180getUpDownDifferenceF1C5BW0$foundation_release() & 4294967295L));
    }

    public static final boolean isScrollingForward(PagerState pagerState, float f) {
        boolean z = ((PagerMeasureResult) pagerState.getLayoutInfo()).reverseLayout;
        boolean z2 = (pagerState.isNotGestureAction$foundation_release() ? -f : dragGestureDelta(pagerState)) > 0.0f;
        return (z2 && z) || !(z2 || z);
    }
}
