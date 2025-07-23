package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyGridBeyondBoundsState implements LazyLayoutBeyondBoundsState {
    public final LazyGridState state;

    public LazyGridBeyondBoundsState(LazyGridState lazyGridState) {
        this.state = lazyGridState;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getFirstPlacedIndex() {
        return this.state.scrollPosition.getIndex();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final boolean getHasVisibleItems() {
        return !((LazyGridMeasureResult) this.state.getLayoutInfo()).visibleItemsInfo.isEmpty();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getItemCount() {
        return ((LazyGridMeasureResult) this.state.getLayoutInfo()).totalItemsCount;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int getLastPlacedIndex() {
        return ((LazyGridMeasuredItem) ((LazyGridItemInfo) CollectionsKt___CollectionsKt.last(((LazyGridMeasureResult) this.state.getLayoutInfo()).visibleItemsInfo))).index;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsState
    public final int itemsPerViewport() {
        LazyGridState lazyGridState = this.state;
        if (((LazyGridMeasureResult) lazyGridState.getLayoutInfo()).visibleItemsInfo.isEmpty()) {
            return 0;
        }
        LazyGridLayoutInfo layoutInfo = lazyGridState.getLayoutInfo();
        int m160getViewportSizeYbymL2g = ((int) (((LazyGridMeasureResult) layoutInfo).orientation == Orientation.Vertical ? ((LazyGridMeasureResult) layoutInfo).m160getViewportSizeYbymL2g() & 4294967295L : ((LazyGridMeasureResult) layoutInfo).m160getViewportSizeYbymL2g() >> 32)) / LazyGridLayoutInfoKt.visibleLinesAverageMainAxisSize(lazyGridState.getLayoutInfo());
        if (m160getViewportSizeYbymL2g < 1) {
            return 1;
        }
        return m160getViewportSizeYbymL2g;
    }
}
