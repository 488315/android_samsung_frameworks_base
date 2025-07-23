package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.unit.IntOffset;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyGridScrollScopeKt$LazyLayoutScrollScope$1 implements LazyLayoutScrollScope, ScrollScope {
    public final /* synthetic */ ScrollScope $$delegate_0;
    public final /* synthetic */ LazyGridState $state;

    public LazyGridScrollScopeKt$LazyLayoutScrollScope$1(ScrollScope scrollScope, LazyGridState lazyGridState) {
        this.$state = lazyGridState;
        this.$$delegate_0 = scrollScope;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int calculateDistanceTo(int i) {
        Integer num;
        Object obj;
        LazyGridState lazyGridState = this.$state;
        LazyGridLayoutInfo layoutInfo = lazyGridState.getLayoutInfo();
        LazyGridMeasureResult lazyGridMeasureResult = (LazyGridMeasureResult) layoutInfo;
        if (!lazyGridMeasureResult.visibleItemsInfo.isEmpty()) {
            int firstVisibleItemIndex = getFirstVisibleItemIndex();
            if (i > getLastVisibleItemIndex() || firstVisibleItemIndex > i) {
                int i2 = ((LazyGridMeasureResult) ((SnapshotMutableStateImpl) lazyGridState.layoutInfoState).getValue()).slotsPerLine;
                return (((((i2 - 1) * (i < getFirstVisibleItemIndex() ? -1 : 1)) + (i - getFirstVisibleItemIndex())) / i2) * LazyGridLayoutInfoKt.visibleLinesAverageMainAxisSize(layoutInfo)) - getFirstVisibleItemScrollOffset();
            }
            List list = lazyGridMeasureResult.visibleItemsInfo;
            int size = list.size();
            int i3 = 0;
            while (true) {
                num = null;
                if (i3 >= size) {
                    obj = null;
                    break;
                }
                obj = list.get(i3);
                if (((LazyGridMeasuredItem) ((LazyGridItemInfo) obj)).index == i) {
                    break;
                }
                i3++;
            }
            LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) obj;
            if (lazyGridMeasureResult.orientation == Orientation.Vertical) {
                if (lazyGridItemInfo != null) {
                    long j = ((LazyGridMeasuredItem) lazyGridItemInfo).offset;
                    IntOffset.Companion companion = IntOffset.Companion;
                    num = Integer.valueOf((int) (j & 4294967295L));
                }
            } else if (lazyGridItemInfo != null) {
                long j2 = ((LazyGridMeasuredItem) lazyGridItemInfo).offset;
                IntOffset.Companion companion2 = IntOffset.Companion;
                num = Integer.valueOf((int) (j2 >> 32));
            }
            if (num != null) {
                return num.intValue();
            }
        }
        return 0;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getFirstVisibleItemIndex() {
        return this.$state.scrollPosition.getIndex();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getFirstVisibleItemScrollOffset() {
        return this.$state.scrollPosition.getScrollOffset();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getItemCount() {
        return ((LazyGridMeasureResult) this.$state.getLayoutInfo()).totalItemsCount;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getLastVisibleItemIndex() {
        LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) CollectionsKt___CollectionsKt.lastOrNull(((LazyGridMeasureResult) this.$state.getLayoutInfo()).visibleItemsInfo);
        if (lazyGridItemInfo != null) {
            return ((LazyGridMeasuredItem) lazyGridItemInfo).index;
        }
        return 0;
    }

    @Override // androidx.compose.foundation.gestures.ScrollScope
    public final float scrollBy(float f) {
        return this.$$delegate_0.scrollBy(f);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final void snapToItem(int i, int i2) {
        this.$state.snapToItemIndexInternal$foundation_release(i, i2);
    }
}
