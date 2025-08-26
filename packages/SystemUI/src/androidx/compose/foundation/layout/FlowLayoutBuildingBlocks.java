package androidx.compose.foundation.layout;

import androidx.collection.IntIntPair;
import androidx.compose.foundation.layout.FlowLayoutOverflow;
import androidx.compose.foundation.layout.FlowLayoutOverflowState;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FlowLayoutBuildingBlocks {
    public final long constraints;
    public final int crossAxisSpacing;
    public final int mainAxisSpacing;
    public final int maxItemsInMainAxis;
    public final int maxLines;
    public final FlowLayoutOverflowState overflow;

    public final class WrapEllipsisInfo {
        public final Measurable ellipsis;
        public final long ellipsisSize;
        public boolean placeEllipsisOnLastContentLine;
        public final Placeable placeable;

        public /* synthetic */ WrapEllipsisInfo(Measurable measurable, Placeable placeable, long j, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(measurable, placeable, j, z);
        }

        private WrapEllipsisInfo(Measurable measurable, Placeable placeable, long j, boolean z) {
            this.ellipsis = measurable;
            this.placeable = placeable;
            this.ellipsisSize = j;
            this.placeEllipsisOnLastContentLine = z;
        }

        public /* synthetic */ WrapEllipsisInfo(Measurable measurable, Placeable placeable, long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(measurable, placeable, j, (i & 8) != 0 ? true : z, null);
        }
    }

    public final class WrapInfo {
        public final boolean isLastItemInContainer;
        public final boolean isLastItemInLine;

        /* JADX WARN: Illegal instructions before constructor call */
        public WrapInfo() {
            boolean z = false;
            this(z, z, 3, null);
        }

        public WrapInfo(boolean z, boolean z2) {
            this.isLastItemInLine = z;
            this.isLastItemInContainer = z2;
        }

        public /* synthetic */ WrapInfo(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
        }
    }

    public /* synthetic */ FlowLayoutBuildingBlocks(int i, FlowLayoutOverflowState flowLayoutOverflowState, long j, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, flowLayoutOverflowState, j, i2, i3, i4);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WrapEllipsisInfo getWrapEllipsisInfo(WrapInfo wrapInfo, boolean z, int i, int i2, int i3, int i4) {
        WrapEllipsisInfo wrapEllipsisInfo;
        Measurable measurable;
        IntIntPair intIntPair;
        Placeable placeable;
        if (wrapInfo.isLastItemInContainer) {
            FlowLayoutOverflowState flowLayoutOverflowState = this.overflow;
            flowLayoutOverflowState.getClass();
            int i5 = FlowLayoutOverflowState.WhenMappings.$EnumSwitchMapping$0[flowLayoutOverflowState.type.ordinal()];
            boolean z2 = true;
            if (i5 == 1 || i5 == 2) {
                wrapEllipsisInfo = null;
                if (wrapEllipsisInfo != null) {
                    if (i < 0 || (i4 != 0 && (i3 - ((int) (wrapEllipsisInfo.ellipsisSize >> 32)) < 0 || i4 >= this.maxItemsInMainAxis))) {
                        z2 = false;
                    }
                    wrapEllipsisInfo.placeEllipsisOnLastContentLine = z2;
                    return wrapEllipsisInfo;
                }
            } else {
                if (i5 != 3 && i5 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                if (z) {
                    measurable = flowLayoutOverflowState.seeMoreMeasurable;
                    intIntPair = flowLayoutOverflowState.seeMoreSize;
                    placeable = flowLayoutOverflowState.seeMorePlaceable;
                } else {
                    measurable = (i < flowLayoutOverflowState.minLinesToShowCollapse - 1 || i2 < flowLayoutOverflowState.minCrossAxisSizeToShowCollapse) ? null : flowLayoutOverflowState.collapseMeasurable;
                    intIntPair = flowLayoutOverflowState.collapseSize;
                    placeable = flowLayoutOverflowState.collapsePlaceable;
                }
                Placeable placeable2 = placeable;
                Measurable measurable2 = measurable;
                if (measurable2 != null) {
                    intIntPair.getClass();
                    wrapEllipsisInfo = new WrapEllipsisInfo(measurable2, placeable2, intIntPair.packedValue, false, 8, null);
                }
                if (wrapEllipsisInfo != null) {
                }
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005c, code lost:
    
        if ((((int) (r25 >> 32)) - ((int) (r20 >> 32))) < 0) goto L23;
     */
    /* renamed from: getWrapInfo-OpUlnko, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WrapInfo m104getWrapInfoOpUlnko(boolean z, int i, long j, IntIntPair intIntPair, int i2, int i3, int i4, boolean z2, boolean z3) {
        long j2;
        long j3;
        int i5 = i3 + i4;
        if (intIntPair == null) {
            return new WrapInfo(true, true);
        }
        FlowLayoutOverflowState flowLayoutOverflowState = this.overflow;
        FlowLayoutOverflow.OverflowType overflowType = flowLayoutOverflowState.type;
        FlowLayoutOverflow.OverflowType overflowType2 = FlowLayoutOverflow.OverflowType.Visible;
        long j4 = intIntPair.packedValue;
        if (overflowType != overflowType2 && (i2 >= this.maxLines || ((int) (j & 4294967295L)) - ((int) (j4 & 4294967295L)) < 0)) {
            return new WrapInfo(true, true);
        }
        int i6 = this.mainAxisSpacing;
        int i7 = this.crossAxisSpacing;
        long j5 = this.constraints;
        int i8 = this.maxItemsInMainAxis;
        if (i != 0) {
            if (i >= i8) {
                j2 = 4294967295L;
                j3 = j4;
            } else {
                j2 = 4294967295L;
                j3 = j4;
            }
            return z2 ? new WrapInfo(true, true) : new WrapInfo(true, m104getWrapInfoOpUlnko(z, 0, IntIntPair.m1constructorimpl(Constraints.m823getMaxWidthimpl(j5), (((int) (j & j2)) - i7) - i4), IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(((int) (j3 >> 32)) - i6, (int) (j3 & j2))), i2 + 1, i5, 0, true, false).isLastItemInContainer);
        }
        j2 = 4294967295L;
        j3 = j4;
        int i9 = (int) (j3 & j2);
        int iMax = Math.max(i4, i9) + i3;
        IntIntPair intIntPairM106ellipsisSizeF35zmw$foundation_layout = z3 ? null : flowLayoutOverflowState.m106ellipsisSizeF35zmw$foundation_layout(i2, iMax, z);
        if (intIntPairM106ellipsisSizeF35zmw$foundation_layout == null || (i + 1 < i8 && ((((int) (j >> 32)) - ((int) (j3 >> 32))) - i6) - ((int) (intIntPairM106ellipsisSizeF35zmw$foundation_layout.packedValue >> 32)) >= 0)) {
            return new WrapInfo(false, false);
        }
        if (z3) {
            return new WrapInfo(true, true);
        }
        boolean z4 = m104getWrapInfoOpUlnko(false, 0, IntIntPair.m1constructorimpl(Constraints.m823getMaxWidthimpl(j5), (((int) (j & j2)) - i7) - Math.max(i4, i9)), intIntPairM106ellipsisSizeF35zmw$foundation_layout, i2 + 1, iMax, 0, true, true).isLastItemInContainer;
        return new WrapInfo(z4, z4);
    }

    private FlowLayoutBuildingBlocks(int i, FlowLayoutOverflowState flowLayoutOverflowState, long j, int i2, int i3, int i4) {
        this.maxItemsInMainAxis = i;
        this.overflow = flowLayoutOverflowState;
        this.constraints = j;
        this.maxLines = i2;
        this.mainAxisSpacing = i3;
        this.crossAxisSpacing = i4;
    }
}
