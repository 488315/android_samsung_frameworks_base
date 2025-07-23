package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return Intrinsics.areEqual(this.density, boxWithConstraintsScopeImpl.density) && Constraints.m815equalsimpl0(this.constraints, boxWithConstraintsScopeImpl.constraints);
    }

    @Override // androidx.compose.foundation.layout.BoxWithConstraintsScope
    /* renamed from: getMaxHeight-D9Ej5fM */
    public final float mo100getMaxHeightD9Ej5fM() {
        long j = this.constraints;
        if (Constraints.m816getHasBoundedHeightimpl(j)) {
            return this.density.mo54toDpu2uoSUM(Constraints.m820getMaxHeightimpl(j));
        }
        Dp.Companion.getClass();
        return Dp.Infinity;
    }

    @Override // androidx.compose.foundation.layout.BoxWithConstraintsScope
    /* renamed from: getMaxWidth-D9Ej5fM */
    public final float mo101getMaxWidthD9Ej5fM() {
        long j = this.constraints;
        if (Constraints.m817getHasBoundedWidthimpl(j)) {
            return this.density.mo54toDpu2uoSUM(Constraints.m821getMaxWidthimpl(j));
        }
        Dp.Companion.getClass();
        return Dp.Infinity;
    }

    public final int hashCode() {
        int hashCode = this.density.hashCode() * 31;
        Constraints.Companion companion = Constraints.Companion;
        return Long.hashCode(this.constraints) + hashCode;
    }

    @Override // androidx.compose.foundation.layout.BoxScope
    public final Modifier matchParentSize(Modifier.Companion companion) {
        return this.$$delegate_0.matchParentSize(companion);
    }

    public final String toString() {
        return "BoxWithConstraintsScopeImpl(density=" + this.density + ", constraints=" + ((Object) Constraints.m824toStringimpl(this.constraints)) + ')';
    }

    private BoxWithConstraintsScopeImpl(Density density, long j) {
        this.density = density;
        this.constraints = j;
        this.$$delegate_0 = BoxScopeInstance.INSTANCE;
    }
}
