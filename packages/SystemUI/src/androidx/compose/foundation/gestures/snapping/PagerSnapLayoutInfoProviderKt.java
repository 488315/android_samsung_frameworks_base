package androidx.compose.foundation.gestures.snapping;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.pager.PagerMeasureResult;
import androidx.compose.foundation.pager.PagerState;

/* loaded from: classes.dex */
public abstract class PagerSnapLayoutInfoProviderKt {
    public static final float dragGestureDelta(PagerState pagerState) {
        return ((PagerMeasureResult) pagerState.getLayoutInfo()).orientation == Orientation.Horizontal ? Float.intBitsToFloat((int) (pagerState.m181getUpDownDifferenceF1C5BW0$foundation_release() >> 32)) : Float.intBitsToFloat((int) (pagerState.m181getUpDownDifferenceF1C5BW0$foundation_release() & 4294967295L));
    }

    public static final boolean isScrollingForward(PagerState pagerState, float f) {
        boolean z = ((PagerMeasureResult) pagerState.getLayoutInfo()).reverseLayout;
        boolean z2 = (pagerState.isNotGestureAction$foundation_release() ? -f : dragGestureDelta(pagerState)) > 0.0f;
        return (z2 && z) || !(z2 || z);
    }
}
