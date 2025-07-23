package androidx.compose.animation;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.ui.graphics.TransformOrigin;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Scale {
    public final FiniteAnimationSpec animationSpec;
    public final float scale;
    public final long transformOrigin;

    public /* synthetic */ Scale(float f, long j, FiniteAnimationSpec finiteAnimationSpec, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, j, finiteAnimationSpec);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scale)) {
            return false;
        }
        Scale scale = (Scale) obj;
        return Float.compare(this.scale, scale.scale) == 0 && TransformOrigin.m502equalsimpl0(this.transformOrigin, scale.transformOrigin) && Intrinsics.areEqual(this.animationSpec, scale.animationSpec);
    }

    public final int hashCode() {
        int hashCode = Float.hashCode(this.scale) * 31;
        TransformOrigin.Companion companion = TransformOrigin.Companion;
        return this.animationSpec.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.transformOrigin);
    }

    public final String toString() {
        return "Scale(scale=" + this.scale + ", transformOrigin=" + ((Object) TransformOrigin.m505toStringimpl(this.transformOrigin)) + ", animationSpec=" + this.animationSpec + ')';
    }

    private Scale(float f, long j, FiniteAnimationSpec<Float> finiteAnimationSpec) {
        this.scale = f;
        this.transformOrigin = j;
        this.animationSpec = finiteAnimationSpec;
    }
}
