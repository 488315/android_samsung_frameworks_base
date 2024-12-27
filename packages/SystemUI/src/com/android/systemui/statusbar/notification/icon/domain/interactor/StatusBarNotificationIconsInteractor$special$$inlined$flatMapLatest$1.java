package com.android.systemui.statusbar.notification.icon.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ NotificationIconsInteractor $iconsInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, NotificationIconsInteractor notificationIconsInteractor) {
        super(3, continuation);
        this.$iconsInteractor$inlined = notificationIconsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1 statusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1 = new StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$iconsInteractor$inlined);
        statusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        statusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return statusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            combine = FlowKt.combine(r3.activeNotificationsInteractor.topLevelRepresentativeNotifications, r3.headsUpNotificationIconInteractor.isolatedNotification, r3.keyguardViewStateRepository.areNotificationsFullyHidden, new NotificationIconsInteractor$filteredNotifSet$1(this.$iconsInteractor$inlined, (r15 & 1) != 0 ? false : true, (r15 & 2) != 0, (r15 & 4) != 0 ? true : ((Boolean) this.L$1).booleanValue(), (r15 & 8) != 0, (r15 & 16) != 0, (r15 & 32) != 0, null));
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
