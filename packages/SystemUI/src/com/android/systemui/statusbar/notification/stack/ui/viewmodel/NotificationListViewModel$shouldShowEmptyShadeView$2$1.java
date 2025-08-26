package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class NotificationListViewModel$shouldShowEmptyShadeView$2$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public NotificationListViewModel$shouldShowEmptyShadeView$2$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        NotificationListViewModel$shouldShowEmptyShadeView$2$1 notificationListViewModel$shouldShowEmptyShadeView$2$1 = new NotificationListViewModel$shouldShowEmptyShadeView$2$1((Continuation) obj4);
        notificationListViewModel$shouldShowEmptyShadeView$2$1.Z$0 = zBooleanValue;
        notificationListViewModel$shouldShowEmptyShadeView$2$1.Z$1 = zBooleanValue2;
        return notificationListViewModel$shouldShowEmptyShadeView$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = false;
        if (!z && !z2) {
            z3 = true;
        }
        return Boolean.valueOf(z3);
    }
}
