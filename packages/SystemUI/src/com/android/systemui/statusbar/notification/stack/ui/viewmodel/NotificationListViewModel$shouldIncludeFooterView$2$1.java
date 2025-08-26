package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
final class NotificationListViewModel$shouldIncludeFooterView$2$1 extends SuspendLambda implements Function6 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    /* synthetic */ boolean Z$4;
    int label;

    public NotificationListViewModel$shouldIncludeFooterView$2$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
        boolean zBooleanValue5 = ((Boolean) obj5).booleanValue();
        NotificationListViewModel$shouldIncludeFooterView$2$1 notificationListViewModel$shouldIncludeFooterView$2$1 = new NotificationListViewModel$shouldIncludeFooterView$2$1((Continuation) obj6);
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$0 = zBooleanValue;
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$1 = zBooleanValue2;
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$2 = zBooleanValue3;
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$3 = zBooleanValue4;
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$4 = zBooleanValue5;
        return notificationListViewModel$shouldIncludeFooterView$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return !this.Z$0 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITH_ANIMATION : !this.Z$1 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITH_ANIMATION : this.Z$2 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITHOUT_ANIMATION : this.Z$3 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITH_ANIMATION : this.Z$4 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITH_ANIMATION : NotificationListViewModel.VisibilityChange.APPEAR_WITH_ANIMATION;
    }
}
