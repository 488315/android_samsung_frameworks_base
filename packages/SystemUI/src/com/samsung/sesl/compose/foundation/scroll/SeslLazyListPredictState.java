package com.samsung.sesl.compose.foundation.scroll;

import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import androidx.compose.foundation.lazy.LazyListMeasureResult;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.unit.IntSize;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslLazyListPredictState extends SeslScrollableState {
    public final State averageItemSize$delegate;
    public final LazyListState scrollState;

    public SeslLazyListPredictState(LazyListState lazyListState) {
        super(lazyListState);
        this.scrollState = lazyListState;
        this.averageItemSize$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.samsung.sesl.compose.foundation.scroll.SeslLazyListPredictState$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Iterator it = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((LazyListMeasureResult) SeslLazyListPredictState.this.scrollState.getLayoutInfo()).visibleItemsInfo), new SeslLazyListPredictState$$ExternalSyntheticLambda1()).iterator();
                double d = 0.0d;
                int i = 0;
                do {
                    if (!((TransformingSequence$iterator$1) it).hasNext()) {
                        return Float.valueOf((float) (i == 0 ? Double.NaN : d / i));
                    }
                    d += ((Number) r3.next()).intValue();
                    i++;
                } while (i >= 0);
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        });
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final float getHandleSizeFraction() {
        return getViewPortSize() / (maxScrollOffset$1(getViewPortSize()) + getViewPortSize());
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final float getPositionFraction() {
        LazyListState lazyListState = this.scrollState;
        return ((((Number) this.averageItemSize$delegate.getValue()).floatValue() * lazyListState.scrollPosition.getIndex()) + lazyListState.scrollPosition.getScrollOffset()) / maxScrollOffset$1(getViewPortSize());
    }

    public final int getViewPortSize() {
        long m151getViewportSizeYbymL2g = ((LazyListMeasureResult) this.scrollState.getLayoutInfo()).m151getViewportSizeYbymL2g();
        IntSize.Companion companion = IntSize.Companion;
        return (int) (m151getViewportSizeYbymL2g & 4294967295L);
    }

    public final float maxScrollOffset$1(int i) {
        float floatValue = (((Number) this.averageItemSize$delegate.getValue()).floatValue() * ((LazyListMeasureResult) this.scrollState.getLayoutInfo()).totalItemsCount) - i;
        if (floatValue < 0.0f) {
            return 0.0f;
        }
        return floatValue;
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final Object scrollTo(float f, Continuation continuation) {
        Object scrollToItem;
        int viewPortSize = getViewPortSize();
        float maxScrollOffset$1 = maxScrollOffset$1(getViewPortSize()) * f;
        LazyListState lazyListState = this.scrollState;
        float index = lazyListState.scrollPosition.getIndex();
        State state = this.averageItemSize$delegate;
        float floatValue = maxScrollOffset$1 - ((((Number) state.getValue()).floatValue() * index) + lazyListState.scrollPosition.getScrollOffset());
        if (Math.abs(floatValue) <= viewPortSize) {
            scrollToItem = ScrollExtensionsKt.scrollBy(lazyListState, floatValue, (ContinuationImpl) continuation);
            if (scrollToItem != CoroutineSingletons.COROUTINE_SUSPENDED) {
                scrollToItem = Unit.INSTANCE;
            }
        } else {
            double coerceIn = RangesKt___RangesKt.coerceIn(maxScrollOffset$1, 0.0d, maxScrollOffset$1(viewPortSize));
            double floatValue2 = ((Number) state.getValue()).floatValue();
            int i = (int) (coerceIn / floatValue2);
            if (i < 0) {
                i = 0;
            }
            int i2 = ((LazyListMeasureResult) lazyListState.getLayoutInfo()).totalItemsCount - 1;
            if (i > i2) {
                i = i2;
            }
            int i3 = (int) (coerceIn - (i * floatValue2));
            scrollToItem = lazyListState.scrollToItem(i, i3 >= 0 ? i3 : 0, (SuspendLambda) continuation);
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (scrollToItem != coroutineSingletons) {
                scrollToItem = Unit.INSTANCE;
            }
            if (scrollToItem != coroutineSingletons) {
                scrollToItem = Unit.INSTANCE;
            }
        }
        return scrollToItem == CoroutineSingletons.COROUTINE_SUSPENDED ? scrollToItem : Unit.INSTANCE;
    }
}
