package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ranges.RangesKt___RangesKt;

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
        float fCalculateScrollDistance = this.defaultBringIntoViewSpec.calculateScrollDistance(f, f2, f3);
        boolean z = false;
        if (f <= 0.0f ? f + f2 <= 0.0f : f + f2 > f3) {
            z = true;
        }
        float fAbs = Math.abs(fCalculateScrollDistance);
        PagerState pagerState = this.pagerState;
        if (fAbs == 0.0f || !z) {
            if (Math.abs(pagerState.firstVisiblePageOffset) < 1.0E-6d) {
                return 0.0f;
            }
            float pageSizeWithSpacing$foundation_release = pagerState.firstVisiblePageOffset * (-1.0f);
            if (((Boolean) ((SnapshotMutableStateImpl) pagerState.isLastScrollForwardState).getValue()).booleanValue()) {
                pageSizeWithSpacing$foundation_release += pagerState.getPageSizeWithSpacing$foundation_release();
            }
            return RangesKt___RangesKt.coerceIn(pageSizeWithSpacing$foundation_release, -f3, f3);
        }
        float pageSizeWithSpacing$foundation_release2 = pagerState.firstVisiblePageOffset * (-1);
        while (fCalculateScrollDistance > 0.0f && pageSizeWithSpacing$foundation_release2 < fCalculateScrollDistance) {
            pageSizeWithSpacing$foundation_release2 += pagerState.getPageSizeWithSpacing$foundation_release();
        }
        while (fCalculateScrollDistance < 0.0f && pageSizeWithSpacing$foundation_release2 > fCalculateScrollDistance) {
            pageSizeWithSpacing$foundation_release2 -= pagerState.getPageSizeWithSpacing$foundation_release();
        }
        return pageSizeWithSpacing$foundation_release2;
    }
}
