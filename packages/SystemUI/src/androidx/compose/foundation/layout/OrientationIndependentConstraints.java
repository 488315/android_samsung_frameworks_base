package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;

/* loaded from: classes.dex */
public final class OrientationIndependentConstraints {
    public final long value;

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m117constructorimpl(long j, LayoutOrientation layoutOrientation) {
        LayoutOrientation layoutOrientation2 = LayoutOrientation.Horizontal;
        return ConstraintsKt.Constraints(layoutOrientation == layoutOrientation2 ? Constraints.m825getMinWidthimpl(j) : Constraints.m824getMinHeightimpl(j), layoutOrientation == layoutOrientation2 ? Constraints.m823getMaxWidthimpl(j) : Constraints.m822getMaxHeightimpl(j), layoutOrientation == layoutOrientation2 ? Constraints.m824getMinHeightimpl(j) : Constraints.m825getMinWidthimpl(j), layoutOrientation == layoutOrientation2 ? Constraints.m822getMaxHeightimpl(j) : Constraints.m823getMaxWidthimpl(j));
    }

    /* renamed from: toBoxConstraints-OenEA2s, reason: not valid java name */
    public static final long m119toBoxConstraintsOenEA2s(long j, LayoutOrientation layoutOrientation) {
        return layoutOrientation == LayoutOrientation.Horizontal ? ConstraintsKt.Constraints(Constraints.m825getMinWidthimpl(j), Constraints.m823getMaxWidthimpl(j), Constraints.m824getMinHeightimpl(j), Constraints.m822getMaxHeightimpl(j)) : ConstraintsKt.Constraints(Constraints.m824getMinHeightimpl(j), Constraints.m822getMaxHeightimpl(j), Constraints.m825getMinWidthimpl(j), Constraints.m823getMaxWidthimpl(j));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof OrientationIndependentConstraints) {
            return Constraints.m817equalsimpl0(this.value, ((OrientationIndependentConstraints) obj).value);
        }
        return false;
    }

    public final int hashCode() {
        Constraints.Companion companion = Constraints.Companion;
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return "OrientationIndependentConstraints(value=" + ((Object) Constraints.m826toStringimpl(this.value)) + ')';
    }
}
