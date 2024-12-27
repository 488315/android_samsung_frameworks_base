package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2 notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2 = new NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2(continuation);
        notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2.L$0 = obj;
        return notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Float f = new Float(0.0f);
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
