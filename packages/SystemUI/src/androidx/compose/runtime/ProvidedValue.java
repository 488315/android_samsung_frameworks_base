package androidx.compose.runtime;

import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ProvidedValue<T> {
    public boolean canOverride = true;
    public final CompositionLocal compositionLocal;
    public final Function1 compute;
    public final boolean explicitNull;
    public final boolean isDynamic;
    public final SnapshotMutationPolicy mutationPolicy;
    public final Object providedValue;
    public final MutableState state;

    public ProvidedValue(CompositionLocal<T> compositionLocal, T t, boolean z, SnapshotMutationPolicy<T> snapshotMutationPolicy, MutableState<T> mutableState, Function1 function1, boolean z2) {
        this.compositionLocal = compositionLocal;
        this.explicitNull = z;
        this.mutationPolicy = snapshotMutationPolicy;
        this.state = mutableState;
        this.compute = function1;
        this.isDynamic = z2;
        this.providedValue = t;
    }

    public final Object getEffectiveValue$runtime_release() {
        if (this.explicitNull) {
            return null;
        }
        MutableState mutableState = this.state;
        if (mutableState != null) {
            return mutableState.getValue();
        }
        Object obj = this.providedValue;
        if (obj != null) {
            return obj;
        }
        ComposerKt.composeRuntimeError("Unexpected form of a provided value");
        throw new KotlinNothingValueException();
    }
}
