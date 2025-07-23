package androidx.compose.runtime;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DynamicValueHolder<T> implements ValueHolder<T> {
    public final MutableState state;

    public DynamicValueHolder(MutableState<T> mutableState) {
        this.state = mutableState;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DynamicValueHolder) && Intrinsics.areEqual(this.state, ((DynamicValueHolder) obj).state);
    }

    public final int hashCode() {
        return this.state.hashCode();
    }

    @Override // androidx.compose.runtime.ValueHolder
    public final Object readValue(PersistentCompositionLocalMap persistentCompositionLocalMap) {
        return this.state.getValue();
    }

    public final String toString() {
        return "DynamicValueHolder(state=" + this.state + ')';
    }
}
