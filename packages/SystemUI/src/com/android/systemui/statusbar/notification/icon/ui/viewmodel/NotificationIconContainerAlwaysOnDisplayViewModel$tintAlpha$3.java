package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    int label;

    public NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj2).floatValue();
        NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3 notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3 = new NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3((Continuation) obj3);
        notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3.F$0 = floatValue;
        notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3.F$1 = floatValue2;
        return notificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Float(this.F$0 + this.F$1);
    }
}
