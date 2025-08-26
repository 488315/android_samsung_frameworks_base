package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes3.dex */
final class OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
    int label;
    final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1(NotificationEntry notificationEntry, Set<NotificationEntry> set, OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, Continuation continuation) {
        super(2, continuation);
        this.$entry = notificationEntry;
        this.$notificationsSeenWhileLocked = set;
        this.this$0 = originalUnseenKeyguardCoordinator;
        this.$trackingJobsByEntry = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1(this.$entry, this.$notificationsSeenWhileLocked, this.this$0, this.$trackingJobsByEntry, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
            OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
            Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
            NotificationEntry notificationEntry = this.$entry;
            this.label = 1;
            if (OriginalUnseenKeyguardCoordinator.C10752.invokeSuspend$trackSeenDurationThreshold(set, originalUnseenKeyguardCoordinator, map, notificationEntry, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
