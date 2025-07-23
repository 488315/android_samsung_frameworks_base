package androidx.compose.foundation.pager;

import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PagerBeyondBoundsState implements LazyLayoutBeyondBoundsState {
    public final int beyondViewportPageCount;
    public final PagerState state;

    public PagerBeyondBoundsState(PagerState pagerState, int i) {
        this.state = pagerState;
        this.beyondViewportPageCount = i;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getFirstPlacedIndex() {
        return Math.max(0, this.state.firstVisiblePage - this.beyondViewportPageCount);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final boolean getHasVisibleItems() {
        return !((PagerMeasureResult) this.state.getLayoutInfo()).visiblePagesInfo.isEmpty();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getItemCount() {
        return this.state.getPageCount();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getLastPlacedIndex() {
        return Math.min(r0.getPageCount() - 1, ((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.last(((PagerMeasureResult) this.state.getLayoutInfo()).visiblePagesInfo))).index + this.beyondViewportPageCount);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int itemsPerViewport() {
        PagerState pagerState = this.state;
        if (((PagerMeasureResult) pagerState.getLayoutInfo()).visiblePagesInfo.size() == 0) {
            return 0;
        }
        int mainAxisViewportSize = PagerLayoutInfoKt.getMainAxisViewportSize(pagerState.getLayoutInfo()) / (((PagerMeasureResult) pagerState.getLayoutInfo()).pageSize + ((PagerMeasureResult) pagerState.getLayoutInfo()).pageSpacing);
        if (mainAxisViewportSize < 1) {
            return 1;
        }
        return mainAxisViewportSize;
    }
}
