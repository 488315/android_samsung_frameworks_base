package androidx.compose.runtime;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyValueHolder<T> implements ValueHolder<T> {
    public final Lazy current$delegate;

    public LazyValueHolder(Function0 function0) {
        this.current$delegate = LazyKt__LazyJVMKt.lazy(function0);
    }

    @Override // androidx.compose.runtime.ValueHolder
    public final Object readValue(PersistentCompositionLocalMap persistentCompositionLocalMap) {
        return this.current$delegate.getValue();
    }
}
