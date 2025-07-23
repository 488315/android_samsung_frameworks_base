package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromGoneTransitionInteractor$listenForGoneToOccluded$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromGoneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGoneTransitionInteractor$listenForGoneToOccluded$1(FromGoneTransitionInteractor fromGoneTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromGoneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromGoneTransitionInteractor$listenForGoneToOccluded$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGoneTransitionInteractor$listenForGoneToOccluded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromGoneTransitionInteractor fromGoneTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(fromGoneTransitionInteractor.keyguardInteractor.showDismissibleKeyguard, fromGoneTransitionInteractor);
            final FromGoneTransitionInteractor fromGoneTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToOccluded$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ((Number) obj2).longValue();
                    if (!((Boolean) FromGoneTransitionInteractor.this.keyguardInteractor.isKeyguardOccluded.getValue()).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromGoneTransitionInteractor.this, KeyguardState.OCCLUDED, null, null, "Dismissible keyguard with occlusion", continuation, 6);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
