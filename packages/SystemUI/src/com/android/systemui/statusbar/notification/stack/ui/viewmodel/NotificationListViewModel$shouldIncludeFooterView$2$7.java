package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.util.ui.AnimatableEvent;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationListViewModel$shouldIncludeFooterView$2$7 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public NotificationListViewModel$shouldIncludeFooterView$2$7(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationListViewModel$shouldIncludeFooterView$2$7 notificationListViewModel$shouldIncludeFooterView$2$7 = new NotificationListViewModel$shouldIncludeFooterView$2$7((Continuation) obj3);
        notificationListViewModel$shouldIncludeFooterView$2$7.L$0 = (NotificationListViewModel.VisibilityChange) obj;
        notificationListViewModel$shouldIncludeFooterView$2$7.L$1 = (Pair) obj2;
        return notificationListViewModel$shouldIncludeFooterView$2$7.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NotificationListViewModel.VisibilityChange visibilityChange = (NotificationListViewModel.VisibilityChange) this.L$0;
        Pair pair = (Pair) this.L$1;
        return new AnimatableEvent(Boolean.valueOf(visibilityChange.getVisible()), ((Boolean) pair.component1()).booleanValue() && ((Boolean) pair.component2()).booleanValue() && visibilityChange.getCanAnimate());
    }
}
