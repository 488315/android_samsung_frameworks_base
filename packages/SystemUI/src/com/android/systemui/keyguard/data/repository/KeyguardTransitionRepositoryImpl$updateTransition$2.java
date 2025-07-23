package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.TransitionState;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardTransitionRepositoryImpl$updateTransition$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ TransitionState $state;
    final /* synthetic */ UUID $transitionId;
    final /* synthetic */ float $value;
    int label;
    final /* synthetic */ KeyguardTransitionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionRepositoryImpl$updateTransition$2(KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, UUID uuid, float f, TransitionState transitionState, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionRepositoryImpl;
        this.$transitionId = uuid;
        this.$value = f;
        this.$state = transitionState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardTransitionRepositoryImpl$updateTransition$2(this.this$0, this.$transitionId, this.$value, this.$state, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionRepositoryImpl$updateTransition$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.withContextMutex.unlock(null);
            KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = this.this$0;
            UUID uuid = this.$transitionId;
            float f = this.$value;
            TransitionState transitionState = this.$state;
            this.label = 1;
            if (KeyguardTransitionRepositoryImpl.access$updateTransitionInternal(keyguardTransitionRepositoryImpl, uuid, f, transitionState) == coroutineSingletons) {
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
