package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LazyGridMeasuredItem implements LazyGridItemInfo, LazyLayoutMeasuredItem {
    public final int afterContentPadding;
    public final LazyLayoutItemAnimator animator;
    public final int beforeContentPadding;
    public int column;
    public final long constraints;
    public final Object contentType;
    public final int crossAxisSize;
    public final int index;
    public final boolean isVertical;
    public final Object key;
    public final int lane;
    public final LayoutDirection layoutDirection;
    public int mainAxisLayoutSize;
    public final int mainAxisSize;
    public final int mainAxisSizeWithSpacings;
    public int maxMainAxisOffset;
    public int minMainAxisOffset;
    public boolean nonScrollableItem;
    public long offset;
    public final List placeables;
    public final boolean reverseLayout;
    public int row;
    public final long size;
    public final int span;
    public final long visualOffset;

    public /* synthetic */ LazyGridMeasuredItem(int i, Object obj, boolean z, int i2, int i3, boolean z2, LayoutDirection layoutDirection, int i4, int i5, List list, long j, Object obj2, LazyLayoutItemAnimator lazyLayoutItemAnimator, long j2, int i6, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, obj, z, i2, i3, z2, layoutDirection, i4, i5, list, j, obj2, lazyLayoutItemAnimator, j2, i6, i7);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    /* renamed from: getConstraints-msEJaDk */
    public final long mo153getConstraintsmsEJaDk() {
        return this.constraints;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getIndex() {
        return this.index;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final Object getKey() {
        return this.key;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getLane() {
        return this.lane;
    }

    /* renamed from: getMainAxis--gyyYBs$1, reason: not valid java name */
    public final int m162getMainAxisgyyYBs$1(long j) {
        long j2;
        if (this.isVertical) {
            IntOffset.Companion companion = IntOffset.Companion;
            j2 = j & 4294967295L;
        } else {
            IntOffset.Companion companion2 = IntOffset.Companion;
            j2 = j >> 32;
        }
        return (int) j2;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getMainAxisSizeWithSpacings() {
        return this.mainAxisSizeWithSpacings;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    /* renamed from: getOffset-Bjo55l4 */
    public final long mo155getOffsetBjo55l4(int i) {
        return this.offset;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final Object getParentData(int i) {
        return ((Placeable) this.placeables.get(i)).getParentData();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getPlaceablesCount() {
        return this.placeables.size();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getSpan() {
        return this.span;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final boolean isVertical() {
        return this.isVertical;
    }

    public final void place(Placeable.PlacementScope placementScope, boolean z) {
        GraphicsLayer graphicsLayer;
        if (this.mainAxisLayoutSize == Integer.MIN_VALUE) {
            InlineClassHelperKt.throwIllegalArgumentException("position() should be called first");
        }
        int size = this.placeables.size();
        for (int i = 0; i < size; i++) {
            Placeable placeable = (Placeable) this.placeables.get(i);
            int i2 = this.minMainAxisOffset;
            boolean z2 = this.isVertical;
            int i3 = i2 - (z2 ? placeable.height : placeable.width);
            int i4 = this.maxMainAxisOffset;
            long j = this.offset;
            LazyLayoutItemAnimation animation = this.animator.getAnimation(i, this.key);
            if (animation != null) {
                if (z) {
                    animation.lookaheadOffset = j;
                } else {
                    long j2 = animation.lookaheadOffset;
                    LazyLayoutItemAnimation.Companion.getClass();
                    long jM853plusqkQi6aY = IntOffset.m853plusqkQi6aY(!IntOffset.m851equalsimpl0(j2, LazyLayoutItemAnimation.NotInitialized) ? animation.lookaheadOffset : j, ((IntOffset) ((SnapshotMutableStateImpl) animation.placementDelta$delegate).getValue()).packedValue);
                    if ((m162getMainAxisgyyYBs$1(j) <= i3 && m162getMainAxisgyyYBs$1(jM853plusqkQi6aY) <= i3) || (m162getMainAxisgyyYBs$1(j) >= i4 && m162getMainAxisgyyYBs$1(jM853plusqkQi6aY) >= i4)) {
                        animation.cancelPlacementAnimation();
                    }
                    j = jM853plusqkQi6aY;
                }
                graphicsLayer = animation.layer;
            } else {
                graphicsLayer = null;
            }
            if (this.reverseLayout) {
                IntOffset.Companion companion = IntOffset.Companion;
                int i5 = z2 ? (int) (j >> 32) : (this.mainAxisLayoutSize - ((int) (j >> 32))) - (z2 ? placeable.height : placeable.width);
                j = ((z2 ? (this.mainAxisLayoutSize - ((int) (j & 4294967295L))) - (z2 ? placeable.height : placeable.width) : (int) (j & 4294967295L)) & 4294967295L) | (i5 << 32);
            }
            long jM853plusqkQi6aY2 = IntOffset.m853plusqkQi6aY(j, this.visualOffset);
            if (!z && animation != null) {
                animation.finalOffset = jM853plusqkQi6aY2;
            }
            if (z2) {
                if (graphicsLayer != null) {
                    placementScope.getClass();
                    Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                    placeable.mo625placeAtf8xVGno(IntOffset.m853plusqkQi6aY(jM853plusqkQi6aY2, placeable.apparentToRealOffset), 0.0f, graphicsLayer);
                } else {
                    Placeable.PlacementScope.m631placeWithLayeraW9wM$default(placementScope, placeable, jM853plusqkQi6aY2, null, 6);
                }
            } else if (graphicsLayer != null) {
                Placeable.PlacementScope.m630placeRelativeWithLayeraW9wM$default(placementScope, placeable, jM853plusqkQi6aY2, graphicsLayer);
            } else {
                Placeable.PlacementScope.m629placeRelativeWithLayeraW9wM$default(placementScope, placeable, jM853plusqkQi6aY2);
            }
        }
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final void position(int i, int i2, int i3, int i4) {
        position(i, i2, i3, i4, -1, -1);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final void setNonScrollableItem() {
        this.nonScrollableItem = true;
    }

    private LazyGridMeasuredItem(int i, Object obj, boolean z, int i2, int i3, boolean z2, LayoutDirection layoutDirection, int i4, int i5, List<? extends Placeable> list, long j, Object obj2, LazyLayoutItemAnimator<LazyGridMeasuredItem> lazyLayoutItemAnimator, long j2, int i6, int i7) {
        long j3;
        this.index = i;
        this.key = obj;
        this.isVertical = z;
        this.crossAxisSize = i2;
        this.reverseLayout = z2;
        this.layoutDirection = layoutDirection;
        this.beforeContentPadding = i4;
        this.afterContentPadding = i5;
        this.placeables = list;
        this.visualOffset = j;
        this.contentType = obj2;
        this.animator = lazyLayoutItemAnimator;
        this.constraints = j2;
        this.lane = i6;
        this.span = i7;
        this.mainAxisLayoutSize = Integer.MIN_VALUE;
        int size = list.size();
        int iMax = 0;
        for (int i8 = 0; i8 < size; i8++) {
            Placeable placeable = list.get(i8);
            iMax = Math.max(iMax, this.isVertical ? placeable.height : placeable.width);
        }
        this.mainAxisSize = iMax;
        int i9 = i3 + iMax;
        this.mainAxisSizeWithSpacings = i9 >= 0 ? i9 : 0;
        if (this.isVertical) {
            j3 = (iMax & 4294967295L) | (this.crossAxisSize << 32);
            IntSize.Companion companion = IntSize.Companion;
        } else {
            j3 = (this.crossAxisSize & 4294967295L) | (iMax << 32);
            IntSize.Companion companion2 = IntSize.Companion;
        }
        this.size = j3;
        IntOffset.Companion.getClass();
        this.offset = 0L;
        this.row = -1;
        this.column = -1;
    }

    public final void position(int i, int i2, int i3, int i4, int i5, int i6) {
        long j;
        boolean z = this.isVertical;
        int i7 = z ? i4 : i3;
        this.mainAxisLayoutSize = i7;
        if (!z) {
            i3 = i4;
        }
        if (z && this.layoutDirection == LayoutDirection.Rtl) {
            i2 = (i3 - i2) - this.crossAxisSize;
        }
        if (z) {
            j = (i & 4294967295L) | (i2 << 32);
            IntOffset.Companion companion = IntOffset.Companion;
        } else {
            j = (i2 & 4294967295L) | (i << 32);
            IntOffset.Companion companion2 = IntOffset.Companion;
        }
        this.offset = j;
        this.row = i5;
        this.column = i6;
        this.minMainAxisOffset = -this.beforeContentPadding;
        this.maxMainAxisOffset = i7 + this.afterContentPadding;
    }
}
