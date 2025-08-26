package androidx.compose.foundation.lazy;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class LazyListMeasureResult implements LazyListLayoutInfo, MeasureResult {
    public final int afterContentPadding;
    public final boolean canScrollForward;
    public final long childConstraints;
    public final float consumedScroll;
    public final CoroutineScope coroutineScope;
    public final Density density;
    public final LazyListMeasuredItem firstVisibleItem;
    public final int firstVisibleItemScrollOffset;
    public final int mainAxisItemSpacing;
    public final MeasureResult measureResult;
    public final Orientation orientation;
    public final boolean remeasureNeeded;
    public final boolean reverseLayout;
    public final float scrollBackAmount;
    public final int totalItemsCount;
    public final int viewportEndOffset;
    public final int viewportStartOffset;
    public final List visibleItemsInfo;

    public /* synthetic */ LazyListMeasureResult(LazyListMeasuredItem lazyListMeasuredItem, int i, boolean z, float f, MeasureResult measureResult, float f2, boolean z2, CoroutineScope coroutineScope, Density density, long j, List list, int i2, int i3, int i4, boolean z3, Orientation orientation, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyListMeasuredItem, i, z, f, measureResult, f2, z2, coroutineScope, density, j, list, i2, i3, i4, z3, orientation, i5, i6);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final LazyListMeasureResult copyWithScrollDeltaWithoutRemeasure(int i, boolean z) {
        LazyListMeasuredItem lazyListMeasuredItem;
        int i2;
        boolean z2;
        int i3;
        List list;
        int i4;
        int i5;
        int i6 = 1;
        if (this.remeasureNeeded || this.visibleItemsInfo.isEmpty() || (lazyListMeasuredItem = this.firstVisibleItem) == null || (i2 = this.firstVisibleItemScrollOffset - i) < 0 || i2 >= lazyListMeasuredItem.mainAxisSizeWithSpacings) {
            return null;
        }
        LazyListMeasuredItem lazyListMeasuredItem2 = (LazyListMeasuredItem) CollectionsKt___CollectionsKt.first(this.visibleItemsInfo);
        LazyListMeasuredItem lazyListMeasuredItem3 = (LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(this.visibleItemsInfo);
        if (lazyListMeasuredItem2.nonScrollableItem || lazyListMeasuredItem3.nonScrollableItem) {
            return null;
        }
        int i7 = this.viewportEndOffset;
        int i8 = this.viewportStartOffset;
        if (i < 0) {
            if (Math.min((lazyListMeasuredItem2.offset + lazyListMeasuredItem2.mainAxisSizeWithSpacings) - i8, (lazyListMeasuredItem3.offset + lazyListMeasuredItem3.mainAxisSizeWithSpacings) - i7) <= (-i)) {
                return null;
            }
        } else if (Math.min(i8 - lazyListMeasuredItem2.offset, i7 - lazyListMeasuredItem3.offset) <= i) {
            return null;
        }
        List list2 = this.visibleItemsInfo;
        int size = list2.size();
        int i9 = 0;
        while (i9 < size) {
            LazyListMeasuredItem lazyListMeasuredItem4 = (LazyListMeasuredItem) list2.get(i9);
            if (!lazyListMeasuredItem4.nonScrollableItem) {
                lazyListMeasuredItem4.offset += i;
                int[] iArr = lazyListMeasuredItem4.placeableOffsets;
                int length = iArr.length;
                int i10 = 0;
                while (true) {
                    z2 = lazyListMeasuredItem4.isVertical;
                    if (i10 >= length) {
                        break;
                    }
                    int i11 = i10 & 1;
                    if ((z2 && i11 != 0) || (!z2 && i11 == 0)) {
                        iArr[i10] = iArr[i10] + i;
                    }
                    i10 += i6;
                }
                if (z) {
                    int size2 = lazyListMeasuredItem4.placeables.size();
                    int i12 = 0;
                    while (i12 < size2) {
                        LazyLayoutItemAnimation animation = lazyListMeasuredItem4.animator.getAnimation(i12, lazyListMeasuredItem4.key);
                        if (animation != null) {
                            long j = animation.rawOffset;
                            if (z2) {
                                IntOffset.Companion companion = IntOffset.Companion;
                                i3 = i6;
                                list = list2;
                                i4 = (int) (j >> 32);
                                i5 = ((int) (j & 4294967295L)) + i;
                            } else {
                                i3 = i6;
                                list = list2;
                                IntOffset.Companion companion2 = IntOffset.Companion;
                                i4 = ((int) (j >> 32)) + i;
                                i5 = (int) (j & 4294967295L);
                            }
                            animation.rawOffset = (i5 & 4294967295L) | (i4 << 32);
                        } else {
                            i3 = i6;
                            list = list2;
                        }
                        i12++;
                        i6 = i3;
                        list2 = list;
                    }
                }
            }
            i9++;
            i6 = i6;
            list2 = list2;
        }
        return new LazyListMeasureResult(this.firstVisibleItem, i2, (this.canScrollForward || i > 0) ? i6 : 0, i, this.measureResult, this.scrollBackAmount, this.remeasureNeeded, this.coroutineScope, this.density, this.childConstraints, this.visibleItemsInfo, this.viewportStartOffset, this.viewportEndOffset, this.totalItemsCount, this.reverseLayout, this.orientation, this.afterContentPadding, this.mainAxisItemSpacing, null);
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
    public final long m152getViewportSizeYbymL2g() {
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

    private LazyListMeasureResult(LazyListMeasuredItem lazyListMeasuredItem, int i, boolean z, float f, MeasureResult measureResult, float f2, boolean z2, CoroutineScope coroutineScope, Density density, long j, List<LazyListMeasuredItem> list, int i2, int i3, int i4, boolean z3, Orientation orientation, int i5, int i6) {
        this.firstVisibleItem = lazyListMeasuredItem;
        this.firstVisibleItemScrollOffset = i;
        this.canScrollForward = z;
        this.consumedScroll = f;
        this.measureResult = measureResult;
        this.scrollBackAmount = f2;
        this.remeasureNeeded = z2;
        this.coroutineScope = coroutineScope;
        this.density = density;
        this.childConstraints = j;
        this.visibleItemsInfo = list;
        this.viewportStartOffset = i2;
        this.viewportEndOffset = i3;
        this.totalItemsCount = i4;
        this.reverseLayout = z3;
        this.orientation = orientation;
        this.afterContentPadding = i5;
        this.mainAxisItemSpacing = i6;
    }
}
