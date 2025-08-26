package com.samsung.sesl.compose.foundation.scroll;

import androidx.compose.foundation.lazy.LazyListItemInfo;
import androidx.compose.foundation.lazy.LazyListMeasureResult;
import androidx.compose.foundation.lazy.LazyListState;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* loaded from: classes4.dex */
public final class SeslLazyListState extends SeslScrollableState {
    public final LazyListState scrollState;

    public SeslLazyListState(LazyListState lazyListState) {
        super(lazyListState);
        this.scrollState = lazyListState;
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final float getHandleSizeFraction() {
        LazyListState lazyListState = this.scrollState;
        if (((LazyListMeasureResult) lazyListState.getLayoutInfo()).totalItemsCount == 0) {
            return 0.0f;
        }
        return (!((LazyListMeasureResult) lazyListState.getLayoutInfo()).visibleItemsInfo.isEmpty() ? ((LazyListItemInfo) CollectionsKt___CollectionsKt.last(r2.visibleItemsInfo)).getIndex() - ((LazyListItemInfo) CollectionsKt___CollectionsKt.first(r2.visibleItemsInfo)).getIndex() : 0) / r0.totalItemsCount;
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final float getPositionFraction() {
        LazyListState lazyListState = this.scrollState;
        if (!lazyListState.getCanScrollForward()) {
            return 1.0f;
        }
        if (lazyListState.getCanScrollBackward()) {
            return lazyListState.scrollPosition.getIndex() / (((LazyListMeasureResult) lazyListState.getLayoutInfo()).totalItemsCount - 1);
        }
        return 0.0f;
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final Object scrollTo(float f, Continuation continuation) {
        Object objScrollToItem = this.scrollState.scrollToItem((int) (f * (((LazyListMeasureResult) r1.getLayoutInfo()).totalItemsCount - 1)), 0, (SuspendLambda) continuation);
        return objScrollToItem == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToItem : Unit.INSTANCE;
    }
}
