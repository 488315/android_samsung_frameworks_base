package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2 sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2 = new SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2(continuation);
        sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2.L$0 = obj;
        return sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Float f = new Float(1.0f);
            this.label = 1;
            if (flowCollector.emit(f, this) == coroutineSingletons) {
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
