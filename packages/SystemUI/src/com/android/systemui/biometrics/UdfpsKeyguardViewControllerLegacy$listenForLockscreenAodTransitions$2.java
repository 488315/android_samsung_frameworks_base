package com.android.systemui.biometrics;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableSharedFlow;

final class UdfpsKeyguardViewControllerLegacy$listenForLockscreenAodTransitions$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    public UdfpsKeyguardViewControllerLegacy$listenForLockscreenAodTransitions$2(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsKeyguardViewControllerLegacy$listenForLockscreenAodTransitions$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsKeyguardViewControllerLegacy$listenForLockscreenAodTransitions$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableSharedFlow transitionValue = this.this$0.transitionInteractor.transitionValue(KeyguardState.AOD);
            final UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForLockscreenAodTransitions$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    UdfpsKeyguardViewControllerLegacy.this.view.onDozeAmountChanged(((Number) obj2).floatValue(), 1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionValue.collect(flowCollector, this) == coroutineSingletons) {
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
