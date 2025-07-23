package androidx.compose.runtime;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CompositionDataImpl {
    public final Composition composition;

    public CompositionDataImpl(Composition composition) {
        this.composition = composition;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof CompositionDataImpl) {
            return Intrinsics.areEqual(this.composition, ((CompositionDataImpl) obj).composition);
        }
        return false;
    }

    public final int hashCode() {
        return this.composition.hashCode() * 31;
    }
}
