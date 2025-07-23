package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.snapping.SnapPosition;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PagerMeasureResult implements PagerLayoutInfo, MeasureResult {
    public final int afterContentPadding;
    public final int beyondViewportPageCount;
    public final boolean canScrollForward;
    public final CoroutineScope coroutineScope;
    public final MeasuredPage currentPage;
    public final float currentPageOffsetFraction;
    public final List extraPagesAfter;
    public final List extraPagesBefore;
    public final MeasuredPage firstVisiblePage;
    public final int firstVisiblePageScrollOffset;
    public final MeasureResult measureResult;
    public final Orientation orientation;
    public final int pageSize;
    public final int pageSpacing;
    public final boolean remeasureNeeded;
    public final boolean reverseLayout;
    public final SnapPosition snapPosition;
    public final int viewportEndOffset;
    public final int viewportStartOffset;
    public final List visiblePagesInfo;

    public PagerMeasureResult(List<MeasuredPage> list, int i, int i2, int i3, Orientation orientation, int i4, int i5, boolean z, int i6, MeasuredPage measuredPage, MeasuredPage measuredPage2, float f, int i7, boolean z2, SnapPosition snapPosition, MeasureResult measureResult, boolean z3, List<MeasuredPage> list2, List<MeasuredPage> list3, CoroutineScope coroutineScope) {
        this.visiblePagesInfo = list;
        this.pageSize = i;
        this.pageSpacing = i2;
        this.afterContentPadding = i3;
        this.orientation = orientation;
        this.viewportStartOffset = i4;
        this.viewportEndOffset = i5;
        this.reverseLayout = z;
        this.beyondViewportPageCount = i6;
        this.firstVisiblePage = measuredPage;
        this.currentPage = measuredPage2;
        this.currentPageOffsetFraction = f;
        this.firstVisiblePageScrollOffset = i7;
        this.canScrollForward = z2;
        this.snapPosition = snapPosition;
        this.measureResult = measureResult;
        this.remeasureNeeded = z3;
        this.extraPagesBefore = list2;
        this.extraPagesAfter = list3;
        this.coroutineScope = coroutineScope;
    }

    public final PagerMeasureResult copyWithScrollDeltaWithoutRemeasure(int i) {
        int i2;
        int i3;
        int i4 = this.pageSize + this.pageSpacing;
        if (!this.remeasureNeeded && !this.visiblePagesInfo.isEmpty() && this.firstVisiblePage != null && (i3 = (i2 = this.firstVisiblePageScrollOffset) - i) >= 0 && i3 < i4) {
            float f = i4 != 0 ? i / i4 : 0.0f;
            float f2 = this.currentPageOffsetFraction;
            float f3 = f2 - f;
            if (this.currentPage != null && f3 < 0.5f && f3 > -0.5f) {
                MeasuredPage measuredPage = (MeasuredPage) CollectionsKt___CollectionsKt.first(this.visiblePagesInfo);
                MeasuredPage measuredPage2 = (MeasuredPage) CollectionsKt___CollectionsKt.last(this.visiblePagesInfo);
                int i5 = this.viewportEndOffset;
                int i6 = this.viewportStartOffset;
                if (i >= 0 ? Math.min(i6 - measuredPage.offset, i5 - measuredPage2.offset) > i : Math.min((measuredPage.offset + i4) - i6, (measuredPage2.offset + i4) - i5) > (-i)) {
                    List list = this.visiblePagesInfo;
                    int size = list.size();
                    for (int i7 = 0; i7 < size; i7++) {
                        ((MeasuredPage) list.get(i7)).applyScrollDelta(i);
                    }
                    List list2 = this.extraPagesBefore;
                    int size2 = list2.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        ((MeasuredPage) list2.get(i8)).applyScrollDelta(i);
                    }
                    List list3 = this.extraPagesAfter;
                    int size3 = list3.size();
                    for (int i9 = 0; i9 < size3; i9++) {
                        ((MeasuredPage) list3.get(i9)).applyScrollDelta(i);
                    }
                    return new PagerMeasureResult(this.visiblePagesInfo, this.pageSize, this.pageSpacing, this.afterContentPadding, this.orientation, this.viewportStartOffset, this.viewportEndOffset, this.reverseLayout, this.beyondViewportPageCount, this.firstVisiblePage, this.currentPage, f2 - f, i2 - i, this.canScrollForward || i > 0, this.snapPosition, this.measureResult, this.remeasureNeeded, this.extraPagesBefore, this.extraPagesAfter, this.coroutineScope);
                }
            }
        }
        return null;
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
    public final long m179getViewportSizeYbymL2g() {
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

    public PagerMeasureResult(List list, int i, int i2, int i3, Orientation orientation, int i4, int i5, boolean z, int i6, MeasuredPage measuredPage, MeasuredPage measuredPage2, float f, int i7, boolean z2, SnapPosition snapPosition, MeasureResult measureResult, boolean z3, List list2, List list3, CoroutineScope coroutineScope, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, i, i2, i3, orientation, i4, i5, z, i6, measuredPage, measuredPage2, f, i7, z2, snapPosition, measureResult, z3, (i8 & 131072) != 0 ? EmptyList.INSTANCE : list2, (i8 & 262144) != 0 ? EmptyList.INSTANCE : list3, coroutineScope);
    }
}
