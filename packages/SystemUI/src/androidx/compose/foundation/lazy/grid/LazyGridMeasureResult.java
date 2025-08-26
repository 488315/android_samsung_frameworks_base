package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.snapping.LazyGridSnapLayoutInfoProviderKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class LazyGridMeasureResult implements LazyGridLayoutInfo, MeasureResult {
    public final int afterContentPadding;
    public final boolean canScrollForward;
    public final float consumedScroll;
    public final CoroutineScope coroutineScope;
    public final Density density;
    public final LazyGridMeasuredLine firstVisibleLine;
    public final int firstVisibleLineScrollOffset;
    public final int mainAxisItemSpacing;
    public final MeasureResult measureResult;
    public final Orientation orientation;
    public final Function1 prefetchInfoRetriever;
    public final boolean remeasureNeeded;
    public final boolean reverseLayout;
    public final float scrollBackAmount;
    public final int slotsPerLine;
    public final int totalItemsCount;
    public final int viewportEndOffset;
    public final int viewportStartOffset;
    public final List visibleItemsInfo;

    public LazyGridMeasureResult(LazyGridMeasuredLine lazyGridMeasuredLine, int i, boolean z, float f, MeasureResult measureResult, float f2, boolean z2, CoroutineScope coroutineScope, Density density, int i2, Function1 function1, List<LazyGridMeasuredItem> list, int i3, int i4, int i5, boolean z3, Orientation orientation, int i6, int i7) {
        this.firstVisibleLine = lazyGridMeasuredLine;
        this.firstVisibleLineScrollOffset = i;
        this.canScrollForward = z;
        this.consumedScroll = f;
        this.measureResult = measureResult;
        this.scrollBackAmount = f2;
        this.remeasureNeeded = z2;
        this.coroutineScope = coroutineScope;
        this.density = density;
        this.slotsPerLine = i2;
        this.prefetchInfoRetriever = function1;
        this.visibleItemsInfo = list;
        this.viewportStartOffset = i3;
        this.viewportEndOffset = i4;
        this.totalItemsCount = i5;
        this.reverseLayout = z3;
        this.orientation = orientation;
        this.afterContentPadding = i6;
        this.mainAxisItemSpacing = i7;
    }

    public final LazyGridMeasureResult copyWithScrollDeltaWithoutRemeasure(int i, boolean z) {
        LazyGridMeasuredLine lazyGridMeasuredLine;
        int i2;
        List list;
        int i3;
        boolean z2;
        boolean z3;
        char c;
        long j;
        int i4;
        if (this.remeasureNeeded || this.visibleItemsInfo.isEmpty() || (lazyGridMeasuredLine = this.firstVisibleLine) == null || (i2 = this.firstVisibleLineScrollOffset - i) < 0 || i2 >= lazyGridMeasuredLine.mainAxisSizeWithSpacings) {
            return null;
        }
        LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) CollectionsKt___CollectionsKt.first(this.visibleItemsInfo);
        LazyGridMeasuredItem lazyGridMeasuredItem2 = (LazyGridMeasuredItem) CollectionsKt___CollectionsKt.last(this.visibleItemsInfo);
        if (lazyGridMeasuredItem.nonScrollableItem || lazyGridMeasuredItem2.nonScrollableItem) {
            return null;
        }
        int i5 = this.viewportEndOffset;
        int i6 = this.viewportStartOffset;
        Orientation orientation = this.orientation;
        if (i < 0) {
            if (Math.min((LazyGridSnapLayoutInfoProviderKt.offsetOnMainAxis(lazyGridMeasuredItem, orientation) + lazyGridMeasuredItem.mainAxisSizeWithSpacings) - i6, (LazyGridSnapLayoutInfoProviderKt.offsetOnMainAxis(lazyGridMeasuredItem2, orientation) + lazyGridMeasuredItem2.mainAxisSizeWithSpacings) - i5) <= (-i)) {
                return null;
            }
        } else if (Math.min(i6 - LazyGridSnapLayoutInfoProviderKt.offsetOnMainAxis(lazyGridMeasuredItem, orientation), i5 - LazyGridSnapLayoutInfoProviderKt.offsetOnMainAxis(lazyGridMeasuredItem2, orientation)) <= i) {
            return null;
        }
        List list2 = this.visibleItemsInfo;
        int size = list2.size();
        int i7 = 0;
        while (i7 < size) {
            LazyGridMeasuredItem lazyGridMeasuredItem3 = (LazyGridMeasuredItem) list2.get(i7);
            if (lazyGridMeasuredItem3.nonScrollableItem) {
                list = list2;
                i3 = i7;
            } else {
                long j2 = lazyGridMeasuredItem3.offset;
                boolean z4 = lazyGridMeasuredItem3.isVertical;
                char c2 = ' ';
                IntOffset.Companion companion = IntOffset.Companion;
                list = list2;
                boolean z5 = true;
                i3 = i7;
                lazyGridMeasuredItem3.offset = ((z4 ? (int) (j2 >> 32) : ((int) (j2 >> 32)) + i) << 32) | ((z4 ? ((int) (j2 & 4294967295L)) + i : (int) (j2 & 4294967295L)) & 4294967295L);
                if (z) {
                    int size2 = lazyGridMeasuredItem3.placeables.size();
                    int i8 = 0;
                    while (i8 < size2) {
                        LazyLayoutItemAnimation animation = lazyGridMeasuredItem3.animator.getAnimation(i8, lazyGridMeasuredItem3.key);
                        if (animation != null) {
                            boolean z6 = z5;
                            z3 = z4;
                            long j3 = animation.rawOffset;
                            if (z3) {
                                z2 = z6;
                                j = j3;
                                i4 = (int) (j >> c2);
                            } else {
                                z2 = z6;
                                j = j3;
                                i4 = ((int) (j >> c2)) + i;
                            }
                            c = c2;
                            animation.rawOffset = ((z3 ? ((int) (j & 4294967295L)) + i : (int) (j & 4294967295L)) & 4294967295L) | (i4 << c);
                        } else {
                            z2 = z5;
                            z3 = z4;
                            c = c2;
                        }
                        i8++;
                        c2 = c;
                        z5 = z2;
                        z4 = z3;
                    }
                }
            }
            i7 = i3 + 1;
            list2 = list;
        }
        return new LazyGridMeasureResult(this.firstVisibleLine, i2, this.canScrollForward || i > 0, i, this.measureResult, this.scrollBackAmount, this.remeasureNeeded, this.coroutineScope, this.density, this.slotsPerLine, this.prefetchInfoRetriever, this.visibleItemsInfo, this.viewportStartOffset, this.viewportEndOffset, this.totalItemsCount, this.reverseLayout, this.orientation, this.afterContentPadding, this.mainAxisItemSpacing);
    }

    @Override // androidx.compose.ui.layout.MeasureResult
    public final Map getAlignmentLines() {
        return this.measureResult.getAlignmentLines();
    }

    @Override // androidx.compose.ui.layout.MeasureResult
    public final int getHeight() {
        return this.measureResult.getHeight();
    }

    @Override // androidx.compose.ui.layout.MeasureResult
    public final Function1 getRulers() {
        return this.measureResult.getRulers();
    }

    /* renamed from: getViewportSize-YbymL2g, reason: not valid java name */
    public final long m161getViewportSizeYbymL2g() {
        MeasureResult measureResult = this.measureResult;
        long width = (measureResult.getWidth() << 32) | (measureResult.getHeight() & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return width;
    }

    @Override // androidx.compose.ui.layout.MeasureResult
    public final int getWidth() {
        return this.measureResult.getWidth();
    }

    @Override // androidx.compose.ui.layout.MeasureResult
    public final void placeChildren() {
        this.measureResult.placeChildren();
    }
}
