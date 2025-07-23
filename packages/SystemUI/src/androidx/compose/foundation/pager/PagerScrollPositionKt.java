package androidx.compose.foundation.pager;

import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PagerScrollPositionKt {
    public static final long currentAbsoluteScrollOffset(PagerState pagerState) {
        return MathKt__MathJVMKt.roundToLong(pagerState.getCurrentPageOffsetFraction() * pagerState.getPageSizeWithSpacing$foundation_release()) + (pagerState.getCurrentPage() * pagerState.getPageSizeWithSpacing$foundation_release());
    }
}
