package androidx.compose.material3;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FadeInFadeOutAnimationItem<T> {
    public final Object key;
    public final Function3 transition;

    public FadeInFadeOutAnimationItem(T t, Function3 function3) {
        this.key = t;
        this.transition = function3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FadeInFadeOutAnimationItem)) {
            return false;
        }
        FadeInFadeOutAnimationItem fadeInFadeOutAnimationItem = (FadeInFadeOutAnimationItem) obj;
        return Intrinsics.areEqual(this.key, fadeInFadeOutAnimationItem.key) && Intrinsics.areEqual(this.transition, fadeInFadeOutAnimationItem.transition);
    }

    public final int hashCode() {
        Object obj = this.key;
        return this.transition.hashCode() + ((obj == null ? 0 : obj.hashCode()) * 31);
    }

    public final String toString() {
        return "FadeInFadeOutAnimationItem(key=" + this.key + ", transition=" + this.transition + ')';
    }
}
