package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OrientationIndependentConstraints {
    public final long value;

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m116constructorimpl(long j, LayoutOrientation layoutOrientation) {
        LayoutOrientation layoutOrientation2 = LayoutOrientation.Horizontal;
        return ConstraintsKt.Constraints(layoutOrientation == layoutOrientation2 ? Constraints.m823getMinWidthimpl(j) : Constraints.m822getMinHeightimpl(j), layoutOrientation == layoutOrientation2 ? Constraints.m821getMaxWidthimpl(j) : Constraints.m820getMaxHeightimpl(j), layoutOrientation == layoutOrientation2 ? Constraints.m822getMinHeightimpl(j) : Constraints.m823getMinWidthimpl(j), layoutOrientation == layoutOrientation2 ? Constraints.m820getMaxHeightimpl(j) : Constraints.m821getMaxWidthimpl(j));
    }

    /* renamed from: toBoxConstraints-OenEA2s, reason: not valid java name */
    public static final long m118toBoxConstraintsOenEA2s(long j, LayoutOrientation layoutOrientation) {
        return layoutOrientation == LayoutOrientation.Horizontal ? ConstraintsKt.Constraints(Constraints.m823getMinWidthimpl(j), Constraints.m821getMaxWidthimpl(j), Constraints.m822getMinHeightimpl(j), Constraints.m820getMaxHeightimpl(j)) : ConstraintsKt.Constraints(Constraints.m822getMinHeightimpl(j), Constraints.m820getMaxHeightimpl(j), Constraints.m823getMinWidthimpl(j), Constraints.m821getMaxWidthimpl(j));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof OrientationIndependentConstraints) {
            return Constraints.m815equalsimpl0(this.value, ((OrientationIndependentConstraints) obj).value);
        }
        return false;
    }

    public final int hashCode() {
        Constraints.Companion companion = Constraints.Companion;
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return "OrientationIndependentConstraints(value=" + ((Object) Constraints.m824toStringimpl(this.value)) + ')';
    }
}
