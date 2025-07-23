package androidx.compose.runtime;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DisposableEffectImpl implements RememberObserver {
    public final Function1 effect;
    public DisposableEffectResult onDispose;

    public DisposableEffectImpl(Function1 function1) {
        this.effect = function1;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        DisposableEffectResult disposableEffectResult = this.onDispose;
        if (disposableEffectResult != null) {
            disposableEffectResult.dispose();
        }
        this.onDispose = null;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
        this.onDispose = (DisposableEffectResult) this.effect.mo779invoke(EffectsKt.InternalDisposableEffectScope);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
    }
}
