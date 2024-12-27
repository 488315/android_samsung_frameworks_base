package com.android.systemui.keyguard;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

final class KeyguardVisibilityMonitor$registerMonitor$4 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardVisibilityMonitor this$0;

    public KeyguardVisibilityMonitor$registerMonitor$4(KeyguardVisibilityMonitor keyguardVisibilityMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardVisibilityMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardVisibilityMonitor$registerMonitor$4(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardVisibilityMonitor$registerMonitor$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final KeyguardVisibilityMonitor keyguardVisibilityMonitor = this.this$0;
            KeyguardTransitionInteractor$special$$inlined$filter$3 keyguardTransitionInteractor$special$$inlined$filter$3 = keyguardVisibilityMonitor.interactor.finishedKeyguardTransitionStep;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$4.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    TransitionStep transitionStep = (TransitionStep) obj2;
                    String str = "keyguardState changed: " + transitionStep.from + " -> " + transitionStep.to;
                    int i2 = KeyguardVisibilityMonitor.$r8$clinit;
                    KeyguardVisibilityMonitor.this.getClass();
                    Log.d("KeyguardVisible", str);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (keyguardTransitionInteractor$special$$inlined$filter$3.collect(flowCollector, this) == coroutineSingletons) {
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
