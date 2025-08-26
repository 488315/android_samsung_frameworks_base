package com.samsung.sesl.compose.foundation.scroll;

import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import androidx.compose.foundation.lazy.LazyListMeasureResult;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.ui.unit.IntSize;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* loaded from: classes4.dex */
public final class SeslLazyListFixedState extends SeslScrollableState {
    public final Function1 calculateItemHeight;
    public final LazyListState scrollState;

    public SeslLazyListFixedState(LazyListState lazyListState, Function1 function1) {
        super(lazyListState);
        this.scrollState = lazyListState;
        this.calculateItemHeight = function1;
    }

    public final float calculateContentSize(int i) {
        int i2 = 0;
        IntRange intRange = new IntRange(0, i);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(intRange, 10));
        IntProgressionIterator it = intRange.iterator();
        while (it.hasNext) {
            arrayList.add(Float.valueOf(((Number) this.calculateItemHeight.mo781invoke(Integer.valueOf(it.nextInt()))).floatValue()));
        }
        int size = arrayList.size();
        float fFloatValue = 0.0f;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            fFloatValue += ((Number) obj).floatValue();
        }
        return fFloatValue;
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final float getHandleSizeFraction() {
        return getViewPortSize() / (maxScrollOffset(getViewPortSize()) + getViewPortSize());
    }

    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    public final float getPositionFraction() {
        LazyListState lazyListState = this.scrollState;
        return (calculateContentSize(lazyListState.scrollPosition.getIndex() - 1) + lazyListState.scrollPosition.getScrollOffset()) / maxScrollOffset(getViewPortSize());
    }

    public final int getViewPortSize() {
        long jM152getViewportSizeYbymL2g = ((LazyListMeasureResult) this.scrollState.getLayoutInfo()).m152getViewportSizeYbymL2g();
        IntSize.Companion companion = IntSize.Companion;
        return (int) (jM152getViewportSizeYbymL2g & 4294967295L);
    }

    public final float maxScrollOffset(int i) {
        float fCalculateContentSize = calculateContentSize(((LazyListMeasureResult) this.scrollState.getLayoutInfo()).totalItemsCount - 1) - i;
        if (fCalculateContentSize < 0.0f) {
            return 0.0f;
        }
        return fCalculateContentSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a8  */
    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object scrollTo(float f, Continuation continuation) {
        Pair pair;
        Object objScrollToItem;
        CoroutineSingletons coroutineSingletons;
        int viewPortSize = getViewPortSize();
        float fMaxScrollOffset = maxScrollOffset(getViewPortSize()) * f;
        LazyListState lazyListState = this.scrollState;
        float fCalculateContentSize = fMaxScrollOffset - (calculateContentSize(lazyListState.scrollPosition.getIndex() - 1) + lazyListState.scrollPosition.getScrollOffset());
        if (Math.abs(fCalculateContentSize) <= viewPortSize) {
            objScrollToItem = ScrollExtensionsKt.scrollBy(lazyListState, fCalculateContentSize, (ContinuationImpl) continuation);
            if (objScrollToItem != CoroutineSingletons.COROUTINE_SUSPENDED) {
                objScrollToItem = Unit.INSTANCE;
            }
        } else {
            int i = ((LazyListMeasureResult) lazyListState.getLayoutInfo()).totalItemsCount - 1;
            if (i >= 0) {
                float f2 = fMaxScrollOffset;
                int i2 = 0;
                while (true) {
                    float fFloatValue = ((Number) this.calculateItemHeight.mo781invoke(Integer.valueOf(i2))).floatValue();
                    if (f2 >= fFloatValue) {
                        f2 -= fFloatValue;
                        if (i2 == i) {
                            break;
                        }
                        i2++;
                    } else {
                        pair = new Pair(Integer.valueOf(i2), Integer.valueOf((int) f2));
                        break;
                    }
                }
                pair = new Pair(0, Integer.valueOf((int) fMaxScrollOffset));
                objScrollToItem = lazyListState.scrollToItem(((Number) pair.component1()).intValue(), ((Number) pair.component2()).intValue(), (SuspendLambda) continuation);
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (objScrollToItem != coroutineSingletons) {
                    objScrollToItem = Unit.INSTANCE;
                }
                if (objScrollToItem != coroutineSingletons) {
                    objScrollToItem = Unit.INSTANCE;
                }
            } else {
                pair = new Pair(0, Integer.valueOf((int) fMaxScrollOffset));
                objScrollToItem = lazyListState.scrollToItem(((Number) pair.component1()).intValue(), ((Number) pair.component2()).intValue(), (SuspendLambda) continuation);
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (objScrollToItem != coroutineSingletons) {
                }
                if (objScrollToItem != coroutineSingletons) {
                }
            }
        }
        return objScrollToItem == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToItem : Unit.INSTANCE;
    }
}
