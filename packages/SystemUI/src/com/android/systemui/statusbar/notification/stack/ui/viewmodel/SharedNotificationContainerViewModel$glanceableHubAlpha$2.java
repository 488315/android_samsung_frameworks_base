package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SharedNotificationContainerViewModel$glanceableHubAlpha$2 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public SharedNotificationContainerViewModel$glanceableHubAlpha$2(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        float floatValue = ((Number) obj5).floatValue();
        SharedNotificationContainerViewModel$glanceableHubAlpha$2 sharedNotificationContainerViewModel$glanceableHubAlpha$2 = new SharedNotificationContainerViewModel$glanceableHubAlpha$2((Continuation) obj6);
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$0 = booleanValue;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$1 = booleanValue2;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$2 = booleanValue3;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.F$0 = floatValue;
        return sharedNotificationContainerViewModel$glanceableHubAlpha$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            boolean z3 = this.Z$2;
            float f = this.F$0;
            if ((z || z3) && !z2) {
                Float f2 = new Float(0.0f);
                this.label = 1;
                if (flowCollector.emit(f2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else if (z) {
                Float f3 = new Float(f);
                this.label = 2;
                if (flowCollector.emit(f3, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                Float f4 = new Float(1.0f);
                this.label = 3;
                if (flowCollector.emit(f4, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
