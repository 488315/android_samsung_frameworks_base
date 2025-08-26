package androidx.compose.foundation.layout;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.collection.IntIntPair;
import androidx.compose.foundation.layout.FlowLayoutOverflow;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public final class FlowLayoutOverflowState {
    public Measurable collapseMeasurable;
    public Placeable collapsePlaceable;
    public IntIntPair collapseSize;
    public final int minCrossAxisSizeToShowCollapse;
    public final int minLinesToShowCollapse;
    public Measurable seeMoreMeasurable;
    public Placeable seeMorePlaceable;
    public IntIntPair seeMoreSize;
    public final FlowLayoutOverflow.OverflowType type;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FlowLayoutOverflow.OverflowType.values().length];
            try {
                iArr[FlowLayoutOverflow.OverflowType.Visible.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FlowLayoutOverflow.OverflowType.Clip.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FlowLayoutOverflow.OverflowType.ExpandIndicator.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FlowLayoutOverflow.OverflowType.ExpandOrCollapseIndicator.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FlowLayoutOverflowState(FlowLayoutOverflow.OverflowType overflowType, int i, int i2) {
        this.type = overflowType;
        this.minLinesToShowCollapse = i;
        this.minCrossAxisSizeToShowCollapse = i2;
    }

    /* renamed from: ellipsisSize-F35zm-w$foundation_layout, reason: not valid java name */
    public final IntIntPair m106ellipsisSizeF35zmw$foundation_layout(int i, int i2, boolean z) {
        int i3 = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i3 != 1 && i3 != 2) {
            if (i3 != 3) {
                if (i3 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                if (z) {
                    return this.seeMoreSize;
                }
                if (i + 1 < this.minLinesToShowCollapse || i2 < this.minCrossAxisSizeToShowCollapse) {
                    return null;
                }
                return this.collapseSize;
            }
            if (z) {
                return this.seeMoreSize;
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlowLayoutOverflowState)) {
            return false;
        }
        FlowLayoutOverflowState flowLayoutOverflowState = (FlowLayoutOverflowState) obj;
        return this.type == flowLayoutOverflowState.type && this.minLinesToShowCollapse == flowLayoutOverflowState.minLinesToShowCollapse && this.minCrossAxisSizeToShowCollapse == flowLayoutOverflowState.minCrossAxisSizeToShowCollapse;
    }

    public final int hashCode() {
        return Integer.hashCode(this.minCrossAxisSizeToShowCollapse) + ReorderTile$$ExternalSyntheticOutline0.m(this.minLinesToShowCollapse, this.type.hashCode() * 31, 31);
    }

    /* renamed from: setOverflowMeasurables--hBUhpc$foundation_layout, reason: not valid java name */
    public final void m107setOverflowMeasurableshBUhpc$foundation_layout(IntrinsicMeasurable intrinsicMeasurable, IntrinsicMeasurable intrinsicMeasurable2, boolean z, long j) {
        long jM117constructorimpl = OrientationIndependentConstraints.m117constructorimpl(j, z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical);
        if (intrinsicMeasurable != null) {
            int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(jM117constructorimpl);
            int i = FlowLayoutKt.$r8$clinit;
            int iMinIntrinsicWidth = z ? intrinsicMeasurable.minIntrinsicWidth(iM822getMaxHeightimpl) : intrinsicMeasurable.minIntrinsicHeight(iM822getMaxHeightimpl);
            this.seeMoreSize = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iMinIntrinsicWidth, z ? intrinsicMeasurable.minIntrinsicHeight(iMinIntrinsicWidth) : intrinsicMeasurable.minIntrinsicWidth(iMinIntrinsicWidth)));
            this.seeMoreMeasurable = intrinsicMeasurable instanceof Measurable ? (Measurable) intrinsicMeasurable : null;
            this.seeMorePlaceable = null;
        }
        if (intrinsicMeasurable2 != null) {
            int iM822getMaxHeightimpl2 = Constraints.m822getMaxHeightimpl(jM117constructorimpl);
            int i2 = FlowLayoutKt.$r8$clinit;
            int iMinIntrinsicWidth2 = z ? intrinsicMeasurable2.minIntrinsicWidth(iM822getMaxHeightimpl2) : intrinsicMeasurable2.minIntrinsicHeight(iM822getMaxHeightimpl2);
            this.collapseSize = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iMinIntrinsicWidth2, z ? intrinsicMeasurable2.minIntrinsicHeight(iMinIntrinsicWidth2) : intrinsicMeasurable2.minIntrinsicWidth(iMinIntrinsicWidth2)));
            this.collapseMeasurable = intrinsicMeasurable2 instanceof Measurable ? (Measurable) intrinsicMeasurable2 : null;
            this.collapsePlaceable = null;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FlowLayoutOverflowState(type=");
        sb.append(this.type);
        sb.append(", minLinesToShowCollapse=");
        sb.append(this.minLinesToShowCollapse);
        sb.append(", minCrossAxisSizeToShowCollapse=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.minCrossAxisSizeToShowCollapse, ')');
    }
}
