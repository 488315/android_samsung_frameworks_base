package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class HideNotificationsBinder$bindHideList$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ SharedFlow $hideListFlow;
    final /* synthetic */ DisplaySwitchNotificationsHiderTracker $hiderTracker;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HideNotificationsBinder$bindHideList$4(DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker, SharedFlow sharedFlow, Continuation continuation) {
        super(2, continuation);
        this.$hiderTracker = displaySwitchNotificationsHiderTracker;
        this.$hideListFlow = sharedFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HideNotificationsBinder$bindHideList$4(this.$hiderTracker, this.$hideListFlow, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HideNotificationsBinder$bindHideList$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = this.$hiderTracker;
            SharedFlow sharedFlow = this.$hideListFlow;
            this.label = 1;
            if (displaySwitchNotificationsHiderTracker.trackNotificationHideTimeWhenVisible(sharedFlow, this) == coroutineSingletons) {
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
