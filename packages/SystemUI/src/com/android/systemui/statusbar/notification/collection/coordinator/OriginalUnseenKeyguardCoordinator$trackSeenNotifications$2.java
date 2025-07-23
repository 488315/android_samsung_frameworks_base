package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = originalUnseenKeyguardCoordinator;
        this.$notificationsSeenWhileLocked = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2 originalUnseenKeyguardCoordinator$trackSeenNotifications$2 = new OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2(this.this$0, this.$notificationsSeenWhileLocked, continuation);
        originalUnseenKeyguardCoordinator$trackSeenNotifications$2.Z$0 = ((Boolean) obj).booleanValue();
        return originalUnseenKeyguardCoordinator$trackSeenNotifications$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
    
        if (r4 == r0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0077, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0075, code lost:
    
        if (r4 == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L15:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L78
        L19:
            kotlin.ResultKt.throwOnFailure(r5)
            boolean r5 = r4.Z$0
            if (r5 == 0) goto L2d
            com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator r5 = r4.this$0
            java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r1 = r4.$notificationsSeenWhileLocked
            r4.label = r3
            java.lang.Object r4 = com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.access$trackSeenNotificationsWhileLocked(r5, r1, r4)
            if (r4 != r0) goto L78
            goto L77
        L2d:
            java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r5 = r4.$notificationsSeenWhileLocked
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L62
            com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator r5 = r4.this$0
            java.util.Set r5 = com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.access$getUnseenNotifications$p(r5)
            java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r1 = r4.$notificationsSeenWhileLocked
            java.util.Collection r1 = (java.util.Collection) r1
            r5.removeAll(r1)
            com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator r5 = r4.this$0
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger r5 = com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.access$getLogger$p(r5)
            java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r1 = r4.$notificationsSeenWhileLocked
            int r1 = r1.size()
            com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator r3 = r4.this$0
            java.util.Set r3 = com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.access$getUnseenNotifications$p(r3)
            int r3 = r3.size()
            r5.logAllMarkedSeenOnUnlock(r1, r3)
            java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r5 = r4.$notificationsSeenWhileLocked
            r5.clear()
        L62:
            com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator r5 = r4.this$0
            com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter r5 = r5.getUnseenNotifFilter()
            java.lang.String r1 = "keyguard no longer showing"
            r5.invalidateList(r1)
            com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator r5 = r4.this$0
            r4.label = r2
            java.lang.Object r4 = com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.access$trackSeenNotificationsWhileUnlocked(r5, r4)
            if (r4 != r0) goto L78
        L77:
            return r0
        L78:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    public final Object invoke(boolean z, Continuation continuation) {
        return ((OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
