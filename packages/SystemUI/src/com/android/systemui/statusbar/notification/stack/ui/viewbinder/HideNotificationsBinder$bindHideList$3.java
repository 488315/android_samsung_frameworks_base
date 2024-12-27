package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;

final class HideNotificationsBinder$bindHideList$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ SharedFlow $hideListFlow;
    final /* synthetic */ DisplaySwitchNotificationsHiderTracker $hiderTracker;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HideNotificationsBinder$bindHideList$3(DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker, SharedFlow sharedFlow, Continuation continuation) {
        super(2, continuation);
        this.$hiderTracker = displaySwitchNotificationsHiderTracker;
        this.$hideListFlow = sharedFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HideNotificationsBinder$bindHideList$3(this.$hiderTracker, this.$hideListFlow, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HideNotificationsBinder$bindHideList$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = this.$hiderTracker;
            SharedFlow sharedFlow = this.$hideListFlow;
            this.label = 1;
            displaySwitchNotificationsHiderTracker.getClass();
            Object collect = sharedFlow.collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker$trackNotificationHideTime$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker2 = DisplaySwitchNotificationsHiderTracker.this;
                    if (booleanValue) {
                        displaySwitchNotificationsHiderTracker2.latencyTracker.onActionStart(26);
                    } else {
                        displaySwitchNotificationsHiderTracker2.latencyTracker.onActionEnd(26);
                    }
                    return Unit.INSTANCE;
                }
            }, this);
            if (collect != coroutineSingletons) {
                collect = Unit.INSTANCE;
            }
            if (collect == coroutineSingletons) {
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
