package androidx.compose.runtime;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

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
