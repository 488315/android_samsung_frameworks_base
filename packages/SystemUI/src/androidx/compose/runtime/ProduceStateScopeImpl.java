package androidx.compose.runtime;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ProduceStateScopeImpl<T> implements ProduceStateScope<T>, MutableState<T> {
    public final /* synthetic */ MutableState $$delegate_0;
    public final CoroutineContext coroutineContext;

    public ProduceStateScopeImpl(MutableState<T> mutableState, CoroutineContext coroutineContext) {
        this.coroutineContext = coroutineContext;
        this.$$delegate_0 = mutableState;
    }

    @Override // androidx.compose.runtime.MutableState
    public final Object component1() {
        return this.$$delegate_0.component1();
    }

    @Override // androidx.compose.runtime.MutableState
    public final Function1 component2() {
        return this.$$delegate_0.component2();
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public final CoroutineContext getCoroutineContext() {
        return this.coroutineContext;
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return this.$$delegate_0.getValue();
    }

    @Override // androidx.compose.runtime.MutableState
    public final void setValue(Object obj) {
        this.$$delegate_0.setValue(obj);
    }
}
