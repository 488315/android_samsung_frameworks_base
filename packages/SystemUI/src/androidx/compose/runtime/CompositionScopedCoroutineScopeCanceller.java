package androidx.compose.runtime;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CompositionScopedCoroutineScopeCanceller implements RememberObserver {
    public final CoroutineScope coroutineScope;

    public CompositionScopedCoroutineScopeCanceller(CoroutineScope coroutineScope) {
        this.coroutineScope = coroutineScope;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope instanceof RememberedCoroutineScope) {
            ((RememberedCoroutineScope) coroutineScope).cancelIfCreated();
        } else {
            CoroutineScopeKt.cancel(coroutineScope, new LeftCompositionCancellationException());
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope instanceof RememberedCoroutineScope) {
            ((RememberedCoroutineScope) coroutineScope).cancelIfCreated();
        } else {
            CoroutineScopeKt.cancel(coroutineScope, new LeftCompositionCancellationException());
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
    }
}
