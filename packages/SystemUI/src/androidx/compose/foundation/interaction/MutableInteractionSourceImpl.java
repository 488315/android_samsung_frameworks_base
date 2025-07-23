package androidx.compose.foundation.interaction;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MutableInteractionSourceImpl implements MutableInteractionSource {
    public final SharedFlowImpl interactions = SharedFlowKt.MutableSharedFlow$default(0, 16, BufferOverflow.DROP_OLDEST, 1);

    @Override // androidx.compose.foundation.interaction.MutableInteractionSource
    public final Object emit(Interaction interaction, Continuation continuation) {
        Object emit = this.interactions.emit(interaction, continuation);
        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.interaction.InteractionSource
    public final SharedFlowImpl getInteractions() {
        return this.interactions;
    }

    @Override // androidx.compose.foundation.interaction.MutableInteractionSource
    public final boolean tryEmit(Interaction interaction) {
        return this.interactions.tryEmit(interaction);
    }
}
