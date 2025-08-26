package androidx.compose.ui.layout;

import androidx.compose.ui.unit.Constraints;

/* loaded from: classes.dex */
public final class DefaultIntrinsicMeasurable implements Measurable {
    public final IntrinsicMeasurable measurable;
    public final IntrinsicMinMax minMax;
    public final IntrinsicWidthHeight widthHeight;

    public DefaultIntrinsicMeasurable(IntrinsicMeasurable intrinsicMeasurable, IntrinsicMinMax intrinsicMinMax, IntrinsicWidthHeight intrinsicWidthHeight) {
        this.measurable = intrinsicMeasurable;
        this.minMax = intrinsicMinMax;
        this.widthHeight = intrinsicWidthHeight;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final Object getParentData() {
        return this.measurable.getParentData();
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicHeight(int i) {
        return this.measurable.maxIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicWidth(int i) {
        return this.measurable.maxIntrinsicWidth(i);
    }

    @Override // androidx.compose.ui.layout.Measurable
    /* renamed from: measure-BRTryo0, reason: not valid java name */
    public final Placeable mo610measureBRTryo0(long j) {
        IntrinsicWidthHeight intrinsicWidthHeight = this.widthHeight;
        IntrinsicWidthHeight intrinsicWidthHeight2 = IntrinsicWidthHeight.Width;
        IntrinsicMinMax intrinsicMinMax = this.minMax;
        IntrinsicMeasurable intrinsicMeasurable = this.measurable;
        if (intrinsicWidthHeight == intrinsicWidthHeight2) {
            return new FixedSizeIntrinsicsPlaceable(intrinsicMinMax == IntrinsicMinMax.Max ? intrinsicMeasurable.maxIntrinsicWidth(Constraints.m822getMaxHeightimpl(j)) : intrinsicMeasurable.minIntrinsicWidth(Constraints.m822getMaxHeightimpl(j)), Constraints.m818getHasBoundedHeightimpl(j) ? Constraints.m822getMaxHeightimpl(j) : 32767);
        }
        return new FixedSizeIntrinsicsPlaceable(Constraints.m819getHasBoundedWidthimpl(j) ? Constraints.m823getMaxWidthimpl(j) : 32767, intrinsicMinMax == IntrinsicMinMax.Max ? intrinsicMeasurable.maxIntrinsicHeight(Constraints.m823getMaxWidthimpl(j)) : intrinsicMeasurable.minIntrinsicHeight(Constraints.m823getMaxWidthimpl(j)));
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicHeight(int i) {
        return this.measurable.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicWidth(int i) {
        return this.measurable.minIntrinsicWidth(i);
    }
}
