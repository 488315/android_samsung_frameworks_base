package androidx.compose.foundation.lazy;

import androidx.compose.foundation.gestures.snapping.LazyListSnapLayoutInfoProviderKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes.dex */
public final class LazyListBeyondBoundsState implements LazyLayoutBeyondBoundsState {
    public final int beyondBoundsItemCount;
    public final LazyListState state;

    public LazyListBeyondBoundsState(LazyListState lazyListState, int i) {
        this.state = lazyListState;
        this.beyondBoundsItemCount = i;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getFirstPlacedIndex() {
        return Math.max(0, this.state.scrollPosition.getIndex() - this.beyondBoundsItemCount);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final boolean getHasVisibleItems() {
        return !((LazyListMeasureResult) this.state.getLayoutInfo()).visibleItemsInfo.isEmpty();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getItemCount() {
        return ((LazyListMeasureResult) this.state.getLayoutInfo()).totalItemsCount;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getLastPlacedIndex() {
        return Math.min(getItemCount() - 1, ((LazyListItemInfo) CollectionsKt___CollectionsKt.last(((LazyListMeasureResult) this.state.getLayoutInfo()).visibleItemsInfo)).getIndex() + this.beyondBoundsItemCount);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int itemsPerViewport() {
        LazyListState lazyListState = this.state;
        if (((LazyListMeasureResult) lazyListState.getLayoutInfo()).visibleItemsInfo.isEmpty()) {
            return 0;
        }
        int singleAxisViewportSize = LazyListSnapLayoutInfoProviderKt.getSingleAxisViewportSize(lazyListState.getLayoutInfo()) / LazyListLayoutInfoKt.visibleItemsAverageSize(lazyListState.getLayoutInfo());
        if (singleAxisViewportSize < 1) {
            return 1;
        }
        return singleAxisViewportSize;
    }
}
