package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PagerBringIntoViewSpec implements BringIntoViewSpec {
    public final BringIntoViewSpec defaultBringIntoViewSpec;
    public final PagerState pagerState;

    public PagerBringIntoViewSpec(PagerState pagerState, BringIntoViewSpec bringIntoViewSpec) {
        this.pagerState = pagerState;
        this.defaultBringIntoViewSpec = bringIntoViewSpec;
    }

    @Override // androidx.compose.foundation.gestures.BringIntoViewSpec
    public final float calculateScrollDistance(float f, float f2, float f3) {
        float calculateScrollDistance = this.defaultBringIntoViewSpec.calculateScrollDistance(f, f2, f3);
        boolean z = false;
        if (f <= 0.0f ? f + f2 <= 0.0f : f + f2 > f3) {
            z = true;
        }
        float abs = Math.abs(calculateScrollDistance);
        PagerState pagerState = this.pagerState;
        if (abs == 0.0f || !z) {
            if (Math.abs(pagerState.firstVisiblePageOffset) < 1.0E-6d) {
                return 0.0f;
            }
            float f4 = pagerState.firstVisiblePageOffset * (-1.0f);
            if (((Boolean) ((SnapshotMutableStateImpl) pagerState.isLastScrollForwardState).getValue()).booleanValue()) {
                f4 += pagerState.getPageSizeWithSpacing$foundation_release();
            }
            return RangesKt___RangesKt.coerceIn(f4, -f3, f3);
        }
        float f5 = pagerState.firstVisiblePageOffset * (-1);
        while (calculateScrollDistance > 0.0f && f5 < calculateScrollDistance) {
            f5 += pagerState.getPageSizeWithSpacing$foundation_release();
        }
        while (calculateScrollDistance < 0.0f && f5 > calculateScrollDistance) {
            f5 -= pagerState.getPageSizeWithSpacing$foundation_release();
        }
        return f5;
    }
}
