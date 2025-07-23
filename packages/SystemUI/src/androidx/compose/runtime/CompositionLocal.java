package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CompositionLocal<T> {
    public final LazyValueHolder defaultValueHolder;

    public /* synthetic */ CompositionLocal(Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0);
    }

    public ValueHolder getDefaultValueHolder$runtime_release() {
        return this.defaultValueHolder;
    }

    public abstract ValueHolder updatedStateOf$runtime_release(ProvidedValue providedValue, ValueHolder valueHolder);

    private CompositionLocal(Function0 function0) {
        this.defaultValueHolder = new LazyValueHolder(function0);
    }
}
