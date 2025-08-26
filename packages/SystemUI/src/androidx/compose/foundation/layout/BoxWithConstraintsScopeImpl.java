package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class BoxWithConstraintsScopeImpl implements BoxWithConstraintsScope, BoxScope {
    public final /* synthetic */ BoxScopeInstance $$delegate_0;
    public final long constraints;
    public final Density density;

    public /* synthetic */ BoxWithConstraintsScopeImpl(Density density, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(density, j);
    }

    @Override // androidx.compose.foundation.layout.BoxScope
    public final Modifier align(Modifier modifier, Alignment alignment) {
        return this.$$delegate_0.align(modifier, alignment);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BoxWithConstraintsScopeImpl)) {
            return false;
        }
        BoxWithConstraintsScopeImpl boxWithConstraintsScopeImpl = (BoxWithConstraintsScopeImpl) obj;
        return Intrinsics.areEqual(this.density, boxWithConstraintsScopeImpl.density) && Constraints.m817equalsimpl0(this.constraints, boxWithConstraintsScopeImpl.constraints);
    }

    @Override // androidx.compose.foundation.layout.BoxWithConstraintsScope
    /* renamed from: getMaxHeight-D9Ej5fM */
    public final float mo101getMaxHeightD9Ej5fM() {
        long j = this.constraints;
        if (Constraints.m818getHasBoundedHeightimpl(j)) {
            return this.density.mo55toDpu2uoSUM(Constraints.m822getMaxHeightimpl(j));
        }
        Dp.Companion.getClass();
        return Dp.Infinity;
    }

    @Override // androidx.compose.foundation.layout.BoxWithConstraintsScope
    /* renamed from: getMaxWidth-D9Ej5fM */
    public final float mo102getMaxWidthD9Ej5fM() {
        long j = this.constraints;
        if (Constraints.m819getHasBoundedWidthimpl(j)) {
            return this.density.mo55toDpu2uoSUM(Constraints.m823getMaxWidthimpl(j));
        }
        Dp.Companion.getClass();
        return Dp.Infinity;
    }

    public final int hashCode() {
        int iHashCode = this.density.hashCode() * 31;
        Constraints.Companion companion = Constraints.Companion;
        return Long.hashCode(this.constraints) + iHashCode;
    }

    @Override // androidx.compose.foundation.layout.BoxScope
    public final Modifier matchParentSize(Modifier.Companion companion) {
        return this.$$delegate_0.matchParentSize(companion);
    }

    public final String toString() {
        return "BoxWithConstraintsScopeImpl(density=" + this.density + ", constraints=" + ((Object) Constraints.m826toStringimpl(this.constraints)) + ')';
    }

    private BoxWithConstraintsScopeImpl(Density density, long j) {
        this.density = density;
        this.constraints = j;
        this.$$delegate_0 = BoxScopeInstance.INSTANCE;
    }
}
