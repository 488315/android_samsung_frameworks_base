package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MutableSceneTransitionLayoutStateImpl$startTransitionImmediately$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $chain;
    final /* synthetic */ TransitionState.Transition $transition;
    int label;
    final /* synthetic */ MutableSceneTransitionLayoutStateImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableSceneTransitionLayoutStateImpl$startTransitionImmediately$1(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, TransitionState.Transition transition, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mutableSceneTransitionLayoutStateImpl;
        this.$transition = transition;
        this.$chain = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MutableSceneTransitionLayoutStateImpl$startTransitionImmediately$1(this.this$0, this.$transition, this.$chain, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MutableSceneTransitionLayoutStateImpl$startTransitionImmediately$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.this$0;
            TransitionState.Transition transition = this.$transition;
            boolean z = this.$chain;
            this.label = 1;
            if (mutableSceneTransitionLayoutStateImpl.startTransition(transition, z, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
