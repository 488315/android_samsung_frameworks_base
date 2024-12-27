package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.ui.AnimatableEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationIconContainerStatusBarViewModel$isolatedIcon$2 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;

    public NotificationIconContainerStatusBarViewModel$isolatedIcon$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj2).floatValue();
        NotificationIconContainerStatusBarViewModel$isolatedIcon$2 notificationIconContainerStatusBarViewModel$isolatedIcon$2 = new NotificationIconContainerStatusBarViewModel$isolatedIcon$2((Continuation) obj3);
        notificationIconContainerStatusBarViewModel$isolatedIcon$2.L$0 = (WithPrev) obj;
        notificationIconContainerStatusBarViewModel$isolatedIcon$2.F$0 = floatValue;
        return notificationIconContainerStatusBarViewModel$isolatedIcon$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WithPrev withPrev = (WithPrev) this.L$0;
        float f = this.F$0;
        NotificationIconInfo notificationIconInfo = (NotificationIconInfo) withPrev.component1();
        NotificationIconInfo notificationIconInfo2 = (NotificationIconInfo) withPrev.component2();
        boolean z = false;
        if (!Intrinsics.areEqual(notificationIconInfo2 != null ? notificationIconInfo2.notifKey : null, notificationIconInfo != null ? notificationIconInfo.notifKey : null) && ((notificationIconInfo2 == null || notificationIconInfo == null) && f == 0.0f)) {
            z = true;
        }
        return new AnimatableEvent(notificationIconInfo2, z);
    }
}
