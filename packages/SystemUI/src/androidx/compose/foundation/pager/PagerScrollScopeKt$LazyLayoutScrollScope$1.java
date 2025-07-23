package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PagerScrollScopeKt$LazyLayoutScrollScope$1 implements LazyLayoutScrollScope, ScrollScope {
    public final /* synthetic */ ScrollScope $$delegate_0;
    public final /* synthetic */ PagerState $state;

    public PagerScrollScopeKt$LazyLayoutScrollScope$1(ScrollScope scrollScope, PagerState pagerState) {
        this.$state = pagerState;
        this.$$delegate_0 = scrollScope;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int calculateDistanceTo(int i) {
        PagerState pagerState = this.$state;
        return (int) (RangesKt___RangesKt.coerceIn(PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState) + MathKt__MathJVMKt.roundToInt(((pagerState.getPageSizeWithSpacing$foundation_release() * (i - pagerState.getCurrentPage())) - (pagerState.getCurrentPageOffsetFraction() * pagerState.getPageSizeWithSpacing$foundation_release())) + 0), pagerState.minScrollOffset, pagerState.maxScrollOffset) - PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState));
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getFirstVisibleItemIndex() {
        return this.$state.firstVisiblePage;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getFirstVisibleItemScrollOffset() {
        return this.$state.firstVisiblePageOffset;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getItemCount() {
        return this.$state.getPageCount();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final int getLastVisibleItemIndex() {
        return ((MeasuredPage) ((PageInfo) CollectionsKt___CollectionsKt.last(((PagerMeasureResult) this.$state.getLayoutInfo()).visiblePagesInfo))).index;
    }

    @Override // androidx.compose.foundation.gestures.ScrollScope
    public final float scrollBy(float f) {
        return this.$$delegate_0.scrollBy(f);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope
    public final void snapToItem(int i, int i2) {
        this.$state.snapToItem$foundation_release(i2 / r1.getPageSizeWithSpacing$foundation_release(), true, i);
    }
}
