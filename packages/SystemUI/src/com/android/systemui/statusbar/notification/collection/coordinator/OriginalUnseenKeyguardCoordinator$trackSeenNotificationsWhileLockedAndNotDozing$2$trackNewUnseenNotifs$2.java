package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator;
import java.util.Map;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableSharedFlow;

/* loaded from: classes3.dex */
final class OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, Set<NotificationEntry> set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = originalUnseenKeyguardCoordinator;
        this.$trackingJobsByEntry = map;
        this.$notificationsSeenWhileLocked = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2 originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2 = new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2(this.this$0, this.$trackingJobsByEntry, this.$notificationsSeenWhileLocked, continuation);
        originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2.L$0 = obj;
        return originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            MutableSharedFlow mutableSharedFlow = this.this$0.unseenEntryAdded;
            final OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
            final Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
            final Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2.1

                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2$1$2, reason: invalid class name */
                final class AnonymousClass2 extends SuspendLambda implements Function2 {
                    final /* synthetic */ NotificationEntry $entry;
                    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
                    final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
                    int label;
                    final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass2(NotificationEntry notificationEntry, Set<NotificationEntry> set, OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, Continuation continuation) {
                        super(2, continuation);
                        this.$entry = notificationEntry;
                        this.$notificationsSeenWhileLocked = set;
                        this.this$0 = originalUnseenKeyguardCoordinator;
                        this.$trackingJobsByEntry = map;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass2(this.$entry, this.$notificationsSeenWhileLocked, this.this$0, this.$trackingJobsByEntry, continuation);
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
                        return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(NotificationEntry notificationEntry, Continuation continuation) {
                    originalUnseenKeyguardCoordinator.logger.logTrackingLockscreenSeenDuration(notificationEntry);
                    Job job = map.get(notificationEntry);
                    if (job != null) {
                        OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator2 = originalUnseenKeyguardCoordinator;
                        job.cancel(null);
                        originalUnseenKeyguardCoordinator2.logger.logResetSeenOnLockscreen(notificationEntry);
                    }
                    Map<NotificationEntry, Job> map2 = map;
                    map2.put(notificationEntry, CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(notificationEntry, set, originalUnseenKeyguardCoordinator, map2, null), 7));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (mutableSharedFlow.collect(flowCollector, this) == coroutineSingletons) {
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
        return ((OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
