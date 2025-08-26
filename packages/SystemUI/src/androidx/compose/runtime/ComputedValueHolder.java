package androidx.compose.runtime;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ComputedValueHolder<T> implements ValueHolder<T> {
    public final Function1 compute;

    public ComputedValueHolder(Function1 function1) {
        this.compute = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ComputedValueHolder) && Intrinsics.areEqual(this.compute, ((ComputedValueHolder) obj).compute);
    }

    public final int hashCode() {
        return this.compute.hashCode();
    }

    @Override // androidx.compose.runtime.ValueHolder
    public final Object readValue(PersistentCompositionLocalMap persistentCompositionLocalMap) {
        return this.compute.mo781invoke(persistentCompositionLocalMap);
    }

    public final String toString() {
        return "ComputedValueHolder(compute=" + this.compute + ')';
    }
}
