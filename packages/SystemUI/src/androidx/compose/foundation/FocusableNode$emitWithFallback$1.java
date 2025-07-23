package androidx.compose.foundation;

import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusableNode$emitWithFallback$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DisposableHandle $handler;
    final /* synthetic */ Interaction $interaction;
    final /* synthetic */ MutableInteractionSource $this_emitWithFallback;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FocusableNode$emitWithFallback$1(MutableInteractionSource mutableInteractionSource, Interaction interaction, DisposableHandle disposableHandle, Continuation continuation) {
        super(2, continuation);
        this.$this_emitWithFallback = mutableInteractionSource;
        this.$interaction = interaction;
        this.$handler = disposableHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FocusableNode$emitWithFallback$1(this.$this_emitWithFallback, this.$interaction, this.$handler, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FocusableNode$emitWithFallback$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableInteractionSource mutableInteractionSource = this.$this_emitWithFallback;
            Interaction interaction = this.$interaction;
            this.label = 1;
            if (mutableInteractionSource.emit(interaction, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        DisposableHandle disposableHandle = this.$handler;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        return Unit.INSTANCE;
    }
}
