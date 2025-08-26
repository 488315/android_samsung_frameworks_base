package androidx.compose.foundation.lazy;

import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes.dex */
public final class LazyListScrollScopeKt$LazyLayoutScrollScope$1 implements LazyLayoutScrollScope, ScrollScope {
    public final /* synthetic */ ScrollScope $$delegate_0;
    public final /* synthetic */ LazyListState $state;

    public LazyListScrollScopeKt$LazyLayoutScrollScope$1(ScrollScope scrollScope, LazyListState lazyListState) {
        this.$state = lazyListState;
        this.$$delegate_0 = scrollScope;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int calculateDistanceTo(int i) {
        Object obj;
        LazyListLayoutInfo layoutInfo = this.$state.getLayoutInfo();
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) layoutInfo;
        if (!lazyListMeasureResult.visibleItemsInfo.isEmpty()) {
            int firstVisibleItemIndex = getFirstVisibleItemIndex();
            if (i > getLastVisibleItemIndex() || firstVisibleItemIndex > i) {
                return ((i - getFirstVisibleItemIndex()) * LazyListLayoutInfoKt.visibleItemsAverageSize(layoutInfo)) - getFirstVisibleItemScrollOffset();
            }
            List list = lazyListMeasureResult.visibleItemsInfo;
            int size = list.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    obj = null;
                    break;
                }
                obj = list.get(i2);
                if (((LazyListItemInfo) obj).getIndex() == i) {
                    break;
                }
                i2++;
            }
            LazyListItemInfo lazyListItemInfo = (LazyListItemInfo) obj;
            if (lazyListItemInfo != null) {
                return lazyListItemInfo.getOffset();
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
        return ((LazyListMeasureResult) this.$state.getLayoutInfo()).totalItemsCount;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getLastVisibleItemIndex() {
        LazyListItemInfo lazyListItemInfo = (LazyListItemInfo) CollectionsKt___CollectionsKt.lastOrNull(((LazyListMeasureResult) this.$state.getLayoutInfo()).visibleItemsInfo);
        if (lazyListItemInfo != null) {
            return lazyListItemInfo.getIndex();
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
