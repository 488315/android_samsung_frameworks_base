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
final class FromDozingTransitionInteractor$listenForDozingToDreaming$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDozingTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDozingTransitionInteractor$listenForDozingToDreaming$1(FromDozingTransitionInteractor fromDozingTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromDozingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDozingTransitionInteractor$listenForDozingToDreaming$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDozingTransitionInteractor$listenForDozingToDreaming$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromDozingTransitionInteractor fromDozingTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(fromDozingTransitionInteractor.keyguardInteractor.isAbleToDream, fromDozingTransitionInteractor, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0(0));
            final FromDozingTransitionInteractor fromDozingTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToDreaming$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ((Boolean) obj2).getClass();
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromDozingTransitionInteractor.this, KeyguardState.DREAMING, null, null, "isAbleToDream", continuation, 6);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
