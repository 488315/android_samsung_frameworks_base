package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

final class NotificationIconContainerStatusBarViewModel$isolatedIcon$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public NotificationIconContainerStatusBarViewModel$isolatedIcon$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationIconContainerStatusBarViewModel$isolatedIcon$1 notificationIconContainerStatusBarViewModel$isolatedIcon$1 = new NotificationIconContainerStatusBarViewModel$isolatedIcon$1((Continuation) obj3);
        notificationIconContainerStatusBarViewModel$isolatedIcon$1.L$0 = (String) obj;
        notificationIconContainerStatusBarViewModel$isolatedIcon$1.L$1 = (NotificationIconsViewData) obj2;
        return notificationIconContainerStatusBarViewModel$isolatedIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        NotificationIconsViewData notificationIconsViewData = (NotificationIconsViewData) this.L$1;
        Object obj2 = null;
        if (str == null) {
            return null;
        }
        Iterator it = notificationIconsViewData.visibleIcons.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((NotificationIconInfo) next).notifKey, str)) {
                obj2 = next;
                break;
            }
        }
        return (NotificationIconInfo) obj2;
    }
}
