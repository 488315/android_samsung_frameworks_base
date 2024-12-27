package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SeekbarDefaults$Track$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State $isDragging$delegate;
    final /* synthetic */ MutableState $trackHeight$delegate;
    final /* synthetic */ MutableState $trackRound$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekbarDefaults$Track$1$1(State state, MutableState mutableState, MutableState mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$isDragging$delegate = state;
        this.$trackHeight$delegate = mutableState;
        this.$trackRound$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeekbarDefaults$Track$1$1(this.$isDragging$delegate, this.$trackHeight$delegate, this.$trackRound$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeekbarDefaults$Track$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState mutableState = this.$trackHeight$delegate;
        State state = this.$isDragging$delegate;
        SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
        mutableState.setValue(Float.valueOf(((Boolean) state.getValue()).booleanValue() ? 13.0f : 6.0f));
        this.$trackRound$delegate.setValue(Float.valueOf(((Number) this.$trackHeight$delegate.getValue()).floatValue() / 2));
        return Unit.INSTANCE;
    }
}
