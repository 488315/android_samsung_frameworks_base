package androidx.compose.animation;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.ui.unit.IntOffset;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Slide {
    public final FiniteAnimationSpec animationSpec;
    public final Function1 slideOffset;

    public Slide(Function1 function1, FiniteAnimationSpec<IntOffset> finiteAnimationSpec) {
        this.slideOffset = function1;
        this.animationSpec = finiteAnimationSpec;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Slide)) {
            return false;
        }
        Slide slide = (Slide) obj;
        return Intrinsics.areEqual(this.slideOffset, slide.slideOffset) && Intrinsics.areEqual(this.animationSpec, slide.animationSpec);
    }

    public final int hashCode() {
        return this.animationSpec.hashCode() + (this.slideOffset.hashCode() * 31);
    }

    public final String toString() {
        return "Slide(slideOffset=" + this.slideOffset + ", animationSpec=" + this.animationSpec + ')';
    }
}
