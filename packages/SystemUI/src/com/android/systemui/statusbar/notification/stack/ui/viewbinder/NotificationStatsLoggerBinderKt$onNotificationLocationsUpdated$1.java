package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationStackScrollLayout $this_onNotificationLocationsUpdated;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1(NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
        super(2, continuation);
        this.$this_onNotificationLocationsUpdated = notificationStackScrollLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1 notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1 = new NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1(this.$this_onNotificationLocationsUpdated, continuation);
        notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1.L$0 = obj;
        return notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1 notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1 = new NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1(producerScope);
            NotificationStackScrollLayout notificationStackScrollLayout = this.$this_onNotificationLocationsUpdated;
            notificationStackScrollLayout.getClass();
            int i2 = NotificationsLiveDataStoreRefactor.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            notificationStackScrollLayout.mLocationsChangedListener = notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1;
            final NotificationStackScrollLayout notificationStackScrollLayout2 = this.$this_onNotificationLocationsUpdated;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayout2;
                    notificationStackScrollLayout3.getClass();
                    int i3 = NotificationsLiveDataStoreRefactor.$r8$clinit;
                    RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                    notificationStackScrollLayout3.mLocationsChangedListener = null;
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
