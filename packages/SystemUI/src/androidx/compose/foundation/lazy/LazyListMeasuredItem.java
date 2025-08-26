package androidx.compose.foundation.lazy;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LazyListMeasuredItem implements LazyListItemInfo, LazyLayoutMeasuredItem {
    public final int afterContentPadding;
    public final LazyLayoutItemAnimator animator;
    public final int beforeContentPadding;
    public final long constraints;
    public final Object contentType;
    public final int crossAxisSize;
    public final Alignment.Horizontal horizontalAlignment;
    public final int index;
    public final boolean isVertical;
    public final Object key;
    public final LayoutDirection layoutDirection;
    public int mainAxisLayoutSize;
    public final int mainAxisSizeWithSpacings;
    public int maxMainAxisOffset;
    public int minMainAxisOffset;
    public boolean nonScrollableItem;
    public int offset;
    public final int[] placeableOffsets;
    public final List placeables;
    public final boolean reverseLayout;
    public final int size;
    public final int spacing;
    public final int span;
    public final Alignment.Vertical verticalAlignment;
    public final long visualOffset;

    public /* synthetic */ LazyListMeasuredItem(int i, List list, boolean z, Alignment.Horizontal horizontal, Alignment.Vertical vertical, LayoutDirection layoutDirection, boolean z2, int i2, int i3, int i4, long j, Object obj, Object obj2, LazyLayoutItemAnimator lazyLayoutItemAnimator, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, list, z, horizontal, vertical, layoutDirection, z2, i2, i3, i4, j, obj, obj2, lazyLayoutItemAnimator, j2);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    /* renamed from: getConstraints-msEJaDk, reason: not valid java name */
    public final long mo153getConstraintsmsEJaDk() {
        return this.constraints;
    }

    @Override // androidx.compose.foundation.lazy.LazyListItemInfo, androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getIndex() {
        return this.index;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final Object getKey() {
        return this.key;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getLane() {
        return 0;
    }

    /* renamed from: getMainAxis--gyyYBs, reason: not valid java name */
    public final int m154getMainAxisgyyYBs(long j) {
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

    @Override // androidx.compose.foundation.lazy.LazyListItemInfo
    public final int getOffset() {
        return this.offset;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    /* renamed from: getOffset-Bjo55l4, reason: not valid java name */
    public final long mo155getOffsetBjo55l4(int i) {
        int[] iArr = this.placeableOffsets;
        long j = (iArr[r5 + 1] & 4294967295L) | (iArr[i * 2] << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return j;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final Object getParentData(int i) {
        return ((Placeable) this.placeables.get(i)).getParentData();
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final int getPlaceablesCount() {
        return this.placeables.size();
    }

    @Override // androidx.compose.foundation.lazy.LazyListItemInfo
    public final int getSize() {
        return this.size;
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
        Placeable.PlacementScope placementScope2;
        long j;
        if (this.mainAxisLayoutSize == Integer.MIN_VALUE) {
            InlineClassHelperKt.throwIllegalArgumentException("position() should be called first");
        }
        int size = this.placeables.size();
        int i = 0;
        while (i < size) {
            Placeable placeable = (Placeable) this.placeables.get(i);
            int i2 = this.minMainAxisOffset;
            boolean z2 = this.isVertical;
            int i3 = i2 - (z2 ? placeable.height : placeable.width);
            int i4 = this.maxMainAxisOffset;
            long jMo155getOffsetBjo55l4 = mo155getOffsetBjo55l4(i);
            LazyLayoutItemAnimation animation = this.animator.getAnimation(i, this.key);
            if (animation != null) {
                if (z) {
                    animation.lookaheadOffset = jMo155getOffsetBjo55l4;
                } else {
                    long j2 = animation.lookaheadOffset;
                    LazyLayoutItemAnimation.Companion.getClass();
                    if (!IntOffset.m851equalsimpl0(j2, LazyLayoutItemAnimation.NotInitialized)) {
                        jMo155getOffsetBjo55l4 = animation.lookaheadOffset;
                    }
                    long jM853plusqkQi6aY = IntOffset.m853plusqkQi6aY(jMo155getOffsetBjo55l4, ((IntOffset) ((SnapshotMutableStateImpl) animation.placementDelta$delegate).getValue()).packedValue);
                    if ((m154getMainAxisgyyYBs(jMo155getOffsetBjo55l4) <= i3 && m154getMainAxisgyyYBs(jM853plusqkQi6aY) <= i3) || (m154getMainAxisgyyYBs(jMo155getOffsetBjo55l4) >= i4 && m154getMainAxisgyyYBs(jM853plusqkQi6aY) >= i4)) {
                        animation.cancelPlacementAnimation();
                    }
                    jMo155getOffsetBjo55l4 = jM853plusqkQi6aY;
                }
                graphicsLayer = animation.layer;
            } else {
                graphicsLayer = null;
            }
            if (this.reverseLayout) {
                IntOffset.Companion companion = IntOffset.Companion;
                if (z2) {
                    j = (((int) (jMo155getOffsetBjo55l4 >> 32)) << 32) | (4294967295L & ((this.mainAxisLayoutSize - ((int) (jMo155getOffsetBjo55l4 & 4294967295L))) - (z2 ? placeable.height : placeable.width)));
                } else {
                    j = (((int) (jMo155getOffsetBjo55l4 & 4294967295L)) & 4294967295L) | (((this.mainAxisLayoutSize - ((int) (jMo155getOffsetBjo55l4 >> 32))) - (z2 ? placeable.height : placeable.width)) << 32);
                }
                jMo155getOffsetBjo55l4 = j;
            }
            long jM853plusqkQi6aY2 = IntOffset.m853plusqkQi6aY(jMo155getOffsetBjo55l4, this.visualOffset);
            if (!z && animation != null) {
                animation.finalOffset = jM853plusqkQi6aY2;
            }
            if (!z2) {
                placementScope2 = placementScope;
                if (graphicsLayer != null) {
                    Placeable.PlacementScope.m630placeRelativeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY2, graphicsLayer);
                } else {
                    Placeable.PlacementScope.m629placeRelativeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY2);
                }
            } else if (graphicsLayer != null) {
                placementScope.getClass();
                Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                placeable.mo625placeAtf8xVGno(IntOffset.m853plusqkQi6aY(jM853plusqkQi6aY2, placeable.apparentToRealOffset), 0.0f, graphicsLayer);
                placementScope2 = placementScope;
            } else {
                placementScope2 = placementScope;
                Placeable.PlacementScope.m631placeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY2, null, 6);
            }
            i++;
            placementScope = placementScope2;
        }
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final void position(int i, int i2, int i3, int i4) {
        position(i, i3, i4);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem
    public final void setNonScrollableItem() {
        this.nonScrollableItem = true;
    }

    private LazyListMeasuredItem(int i, List<? extends Placeable> list, boolean z, Alignment.Horizontal horizontal, Alignment.Vertical vertical, LayoutDirection layoutDirection, boolean z2, int i2, int i3, int i4, long j, Object obj, Object obj2, LazyLayoutItemAnimator<LazyListMeasuredItem> lazyLayoutItemAnimator, long j2) {
        this.index = i;
        this.placeables = list;
        this.isVertical = z;
        this.horizontalAlignment = horizontal;
        this.verticalAlignment = vertical;
        this.layoutDirection = layoutDirection;
        this.reverseLayout = z2;
        this.beforeContentPadding = i2;
        this.afterContentPadding = i3;
        this.spacing = i4;
        this.visualOffset = j;
        this.key = obj;
        this.contentType = obj2;
        this.animator = lazyLayoutItemAnimator;
        this.constraints = j2;
        this.span = 1;
        this.mainAxisLayoutSize = Integer.MIN_VALUE;
        int size = list.size();
        int i5 = 0;
        int iMax = 0;
        for (int i6 = 0; i6 < size; i6++) {
            Placeable placeable = list.get(i6);
            boolean z3 = this.isVertical;
            i5 += z3 ? placeable.height : placeable.width;
            iMax = Math.max(iMax, !z3 ? placeable.height : placeable.width);
        }
        this.size = i5;
        int i7 = i5 + this.spacing;
        this.mainAxisSizeWithSpacings = i7 >= 0 ? i7 : 0;
        this.crossAxisSize = iMax;
        this.placeableOffsets = new int[this.placeables.size() * 2];
    }

    public final void position(int i, int i2, int i3) {
        int i4;
        this.offset = i;
        boolean z = this.isVertical;
        this.mainAxisLayoutSize = z ? i3 : i2;
        List list = this.placeables;
        int size = list.size();
        for (int i5 = 0; i5 < size; i5++) {
            Placeable placeable = (Placeable) list.get(i5);
            int i6 = i5 * 2;
            int[] iArr = this.placeableOffsets;
            if (z) {
                Alignment.Horizontal horizontal = this.horizontalAlignment;
                if (horizontal == null) {
                    InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null horizontalAlignment when isVertical == true");
                    throw new KotlinNothingValueException();
                }
                iArr[i6] = horizontal.align(placeable.width, i2, this.layoutDirection);
                iArr[i6 + 1] = i;
                i4 = placeable.height;
            } else {
                iArr[i6] = i;
                int i7 = i6 + 1;
                Alignment.Vertical vertical = this.verticalAlignment;
                if (vertical == null) {
                    InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null verticalAlignment when isVertical == false");
                    throw new KotlinNothingValueException();
                }
                iArr[i7] = ((BiasAlignment.Vertical) vertical).align(placeable.height, i3);
                i4 = placeable.width;
            }
            i += i4;
        }
        this.minMainAxisOffset = -this.beforeContentPadding;
        this.maxMainAxisOffset = this.mainAxisLayoutSize + this.afterContentPadding;
    }
}
