package androidx.compose.foundation.pager;

import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public abstract class PagerScrollPositionKt {
    public static final long currentAbsoluteScrollOffset(PagerState pagerState) {
        return MathKt__MathJVMKt.roundToLong(pagerState.getCurrentPageOffsetFraction() * pagerState.getPageSizeWithSpacing$foundation_release()) + (pagerState.getCurrentPage() * pagerState.getPageSizeWithSpacing$foundation_release());
    }
}
