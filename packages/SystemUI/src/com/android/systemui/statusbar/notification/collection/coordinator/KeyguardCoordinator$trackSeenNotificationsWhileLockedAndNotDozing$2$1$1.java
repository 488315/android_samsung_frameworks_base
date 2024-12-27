package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
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

final class KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    public KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1(NotificationEntry notificationEntry, Set<NotificationEntry> set, KeyguardCoordinator keyguardCoordinator, Map<NotificationEntry, Job> map, Continuation continuation) {
        super(2, continuation);
        this.$entry = notificationEntry;
        this.$notificationsSeenWhileLocked = set;
        this.this$0 = keyguardCoordinator;
        this.$trackingJobsByEntry = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1(this.$entry, this.$notificationsSeenWhileLocked, this.this$0, this.$trackingJobsByEntry, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object invokeSuspend$trackSeenDurationThreshold;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
            KeyguardCoordinator keyguardCoordinator = this.this$0;
            Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
            NotificationEntry notificationEntry = this.$entry;
            this.label = 1;
            invokeSuspend$trackSeenDurationThreshold = KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$trackSeenDurationThreshold(set, keyguardCoordinator, map, notificationEntry, this);
            if (invokeSuspend$trackSeenDurationThreshold == coroutineSingletons) {
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
        return ((KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
