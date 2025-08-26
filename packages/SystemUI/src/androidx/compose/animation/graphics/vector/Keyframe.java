package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.Easing;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Keyframe<T> {
    public final float fraction;
    public final Easing interpolator;
    public final Object value;

    public Keyframe(float f, T t, Easing easing) {
        this.fraction = f;
        this.value = t;
        this.interpolator = easing;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Keyframe)) {
            return false;
        }
        Keyframe keyframe = (Keyframe) obj;
        return Float.compare(this.fraction, keyframe.fraction) == 0 && Intrinsics.areEqual(this.value, keyframe.value) && Intrinsics.areEqual(this.interpolator, keyframe.interpolator);
    }

    public final int hashCode() {
        int iHashCode = Float.hashCode(this.fraction) * 31;
        Object obj = this.value;
        return this.interpolator.hashCode() + ((iHashCode + (obj == null ? 0 : obj.hashCode())) * 31);
    }

    public final String toString() {
        return "Keyframe(fraction=" + this.fraction + ", value=" + this.value + ", interpolator=" + this.interpolator + ')';
    }
}
