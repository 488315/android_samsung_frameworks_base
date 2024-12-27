package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4 keyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4 = new KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4(continuation);
        keyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4.L$0 = obj;
        return keyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean bool = Boolean.FALSE;
            this.label = 1;
            if (flowCollector.emit(bool, this) == coroutineSingletons) {
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
