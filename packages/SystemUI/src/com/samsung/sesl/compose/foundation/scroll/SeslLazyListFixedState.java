package com.samsung.sesl.compose.foundation.scroll;

import androidx.compose.foundation.lazy.LazyListMeasureResult;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.ui.unit.IntSize;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            arrayList.add(Float.valueOf(((Number) this.calculateItemHeight.mo779invoke(Integer.valueOf(it.nextInt()))).floatValue()));
        }
        int size = arrayList.size();
        float f = 0.0f;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            f += ((Number) obj).floatValue();
        }
        return f;
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
        long m151getViewportSizeYbymL2g = ((LazyListMeasureResult) this.scrollState.getLayoutInfo()).m151getViewportSizeYbymL2g();
        IntSize.Companion companion = IntSize.Companion;
        return (int) (m151getViewportSizeYbymL2g & 4294967295L);
    }

    public final float maxScrollOffset(int i) {
        float calculateContentSize = calculateContentSize(((LazyListMeasureResult) this.scrollState.getLayoutInfo()).totalItemsCount - 1) - i;
        if (calculateContentSize < 0.0f) {
            return 0.0f;
        }
        return calculateContentSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a8  */
    @Override // com.samsung.sesl.compose.foundation.scroll.SeslScrollableState
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object scrollTo(float r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            int r0 = r7.getViewPortSize()
            int r1 = r7.getViewPortSize()
            float r1 = r7.maxScrollOffset(r1)
            float r1 = r1 * r8
            androidx.compose.foundation.lazy.LazyListState r8 = r7.scrollState
            androidx.compose.foundation.lazy.LazyListScrollPosition r2 = r8.scrollPosition
            int r2 = r2.getIndex()
            int r2 = r2 + (-1)
            float r2 = r7.calculateContentSize(r2)
            androidx.compose.foundation.lazy.LazyListScrollPosition r3 = r8.scrollPosition
            int r3 = r3.getScrollOffset()
            float r3 = (float) r3
            float r2 = r2 + r3
            float r2 = r1 - r2
            float r3 = java.lang.Math.abs(r2)
            float r0 = (float) r0
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 > 0) goto L3e
            kotlin.coroutines.jvm.internal.ContinuationImpl r9 = (kotlin.coroutines.jvm.internal.ContinuationImpl) r9
            java.lang.Object r7 = androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r8, r2, r9)
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r7 != r8) goto L3a
            goto Laa
        L3a:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            goto Laa
        L3e:
            androidx.compose.foundation.lazy.LazyListLayoutInfo r0 = r8.getLayoutInfo()
            androidx.compose.foundation.lazy.LazyListMeasureResult r0 = (androidx.compose.foundation.lazy.LazyListMeasureResult) r0
            int r0 = r0.totalItemsCount
            int r0 = r0 + (-1)
            r2 = 0
            if (r0 < 0) goto L76
            r4 = r1
            r3 = r2
        L4d:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            kotlin.jvm.functions.Function1 r6 = r7.calculateItemHeight
            java.lang.Object r5 = r6.mo779invoke(r5)
            java.lang.Number r5 = (java.lang.Number) r5
            float r5 = r5.floatValue()
            int r6 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r6 >= 0) goto L70
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            int r0 = (int) r4
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            kotlin.Pair r1 = new kotlin.Pair
            r1.<init>(r7, r0)
            goto L84
        L70:
            float r4 = r4 - r5
            if (r3 == r0) goto L76
            int r3 = r3 + 1
            goto L4d
        L76:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r2)
            int r0 = (int) r1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            kotlin.Pair r1 = new kotlin.Pair
            r1.<init>(r7, r0)
        L84:
            java.lang.Object r7 = r1.component1()
            java.lang.Number r7 = (java.lang.Number) r7
            int r7 = r7.intValue()
            java.lang.Object r0 = r1.component2()
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            kotlin.coroutines.jvm.internal.SuspendLambda r9 = (kotlin.coroutines.jvm.internal.SuspendLambda) r9
            java.lang.Object r7 = r8.scrollToItem(r7, r0, r9)
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r7 != r8) goto La3
            goto La5
        La3:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        La5:
            if (r7 != r8) goto La8
            goto Laa
        La8:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        Laa:
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r7 != r8) goto Laf
            return r7
        Laf:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.scroll.SeslLazyListFixedState.scrollTo(float, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
