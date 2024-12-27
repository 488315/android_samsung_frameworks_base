package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

final class KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
        final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
        int label;
        final /* synthetic */ KeyguardCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KeyguardCoordinator keyguardCoordinator, Map<NotificationEntry, Job> map, Set<NotificationEntry> set, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardCoordinator;
            this.$trackingJobsByEntry = map;
            this.$notificationsSeenWhileLocked = set;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$trackingJobsByEntry, this.$notificationsSeenWhileLocked, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardCoordinator keyguardCoordinator = this.this$0;
                Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
                Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                this.label = 1;
                if (KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$trackNewUnseenNotifs(keyguardCoordinator, map, set, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
        int label;
        final /* synthetic */ KeyguardCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(KeyguardCoordinator keyguardCoordinator, Map<NotificationEntry, Job> map, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardCoordinator;
            this.$trackingJobsByEntry = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, this.$trackingJobsByEntry, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardCoordinator keyguardCoordinator = this.this$0;
                Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
                this.label = 1;
                if (KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$stopTrackingRemovedNotifs(keyguardCoordinator, map, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2(KeyguardCoordinator keyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
        this.$notificationsSeenWhileLocked = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object invokeSuspend$stopTrackingRemovedNotifs(final com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator r4, final java.util.Map<com.android.systemui.statusbar.notification.collection.NotificationEntry, kotlinx.coroutines.Job> r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L44
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.MutableSharedFlow r6 = com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator.access$getUnseenEntryRemoved$p(r4)
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$2 r2 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$2
            r2.<init>()
            r0.label = r3
            java.lang.Object r4 = r6.collect(r2, r0)
            if (r4 != r1) goto L44
            return r1
        L44:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$stopTrackingRemovedNotifs(com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object invokeSuspend$trackNewUnseenNotifs(com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator r4, java.util.Map<com.android.systemui.statusbar.notification.collection.NotificationEntry, kotlinx.coroutines.Job> r5, java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2 r7 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2
            r2 = 0
            r7.<init>(r4, r5, r6, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$trackNewUnseenNotifs(com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator, java.util.Map, java.util.Set, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object invokeSuspend$trackSeenDurationThreshold(java.util.Set<com.android.systemui.statusbar.notification.collection.NotificationEntry> r6, com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator r7, java.util.Map<com.android.systemui.statusbar.notification.collection.NotificationEntry, kotlinx.coroutines.Job> r8, com.android.systemui.statusbar.notification.collection.NotificationEntry r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1
            if (r0 == 0) goto L13
            r0 = r10
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L42
            if (r2 != r3) goto L3a
            java.lang.Object r6 = r0.L$3
            r9 = r6
            com.android.systemui.statusbar.notification.collection.NotificationEntry r9 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r9
            java.lang.Object r6 = r0.L$2
            r8 = r6
            java.util.Map r8 = (java.util.Map) r8
            java.lang.Object r6 = r0.L$1
            r7 = r6
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator r7 = (com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator) r7
            java.lang.Object r6 = r0.L$0
            java.util.Set r6 = (java.util.Set) r6
            kotlin.ResultKt.throwOnFailure(r10)
            goto L67
        L3a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L42:
            kotlin.ResultKt.throwOnFailure(r10)
            boolean r10 = r6.remove(r9)
            if (r10 == 0) goto L52
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger r10 = com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator.access$getLogger$p(r7)
            r10.logResetSeenOnLockscreen(r9)
        L52:
            long r4 = com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator.access$getSEEN_TIMEOUT$cp()
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r8
            r0.L$3 = r9
            r0.label = r3
            java.lang.Object r10 = kotlinx.coroutines.DelayKt.m2546delayVtjQ1oo(r4, r0)
            if (r10 != r1) goto L67
            return r1
        L67:
            r6.add(r9)
            r8.remove(r9)
            com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger r6 = com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator.access$getLogger$p(r7)
            r6.logSeenOnLockscreen(r9)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$trackSeenDurationThreshold(java.util.Set, com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator, java.util.Map, com.android.systemui.statusbar.notification.collection.NotificationEntry, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2 keyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2 = new KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2(this.this$0, this.$notificationsSeenWhileLocked, continuation);
        keyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.L$0 = obj;
        return keyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        KeyguardCoordinatorLogger keyguardCoordinatorLogger;
        Set<NotificationEntry> set;
        Set<NotificationEntry> set2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        keyguardCoordinatorLogger = this.this$0.logger;
        set = this.this$0.unseenNotifications;
        keyguardCoordinatorLogger.logTrackingLockscreenSeenDuration(set);
        set2 = this.this$0.unseenNotifications;
        Set<NotificationEntry> set3 = this.$notificationsSeenWhileLocked;
        KeyguardCoordinator keyguardCoordinator = this.this$0;
        for (NotificationEntry notificationEntry : set2) {
            linkedHashMap.put(notificationEntry, BuildersKt.launch$default(coroutineScope, null, null, new KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1(notificationEntry, set3, keyguardCoordinator, linkedHashMap, null), 3));
        }
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, linkedHashMap, this.$notificationsSeenWhileLocked, null), 3);
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, linkedHashMap, null), 3);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
