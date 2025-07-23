package androidx.compose.animation;

import androidx.compose.animation.core.FiniteAnimationSpec;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Fade {
    public final float alpha;
    public final FiniteAnimationSpec animationSpec;

    public Fade(float f, FiniteAnimationSpec<Float> finiteAnimationSpec) {
        this.alpha = f;
        this.animationSpec = finiteAnimationSpec;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Fade)) {
            return false;
        }
        Fade fade = (Fade) obj;
        return Float.compare(this.alpha, fade.alpha) == 0 && Intrinsics.areEqual(this.animationSpec, fade.animationSpec);
    }

    public final int hashCode() {
        return this.animationSpec.hashCode() + (Float.hashCode(this.alpha) * 31);
    }

    public final String toString() {
        return "Fade(alpha=" + this.alpha + ", animationSpec=" + this.animationSpec + ')';
    }
}
