package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
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
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardCoordinator$trackSeenNotificationsWhileLocked$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLocked$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
        int label;
        final /* synthetic */ KeyguardCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardCoordinator keyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardCoordinator;
            this.$notificationsSeenWhileLocked = set;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$notificationsSeenWhileLocked, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            MutableSharedFlow mutableSharedFlow;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                mutableSharedFlow = this.this$0.unseenEntryRemoved;
                final Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                final KeyguardCoordinator keyguardCoordinator = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator.trackSeenNotificationsWhileLocked.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(NotificationEntry notificationEntry, Continuation continuation) {
                        KeyguardCoordinatorLogger keyguardCoordinatorLogger;
                        if (set.remove(notificationEntry)) {
                            keyguardCoordinatorLogger = keyguardCoordinator.logger;
                            keyguardCoordinatorLogger.logRemoveSeenOnLockscreen(notificationEntry);
                        }
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
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotificationsWhileLocked$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ KeyguardCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KeyguardCoordinator keyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardCoordinator;
            this.$notificationsSeenWhileLocked = set;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, this.$notificationsSeenWhileLocked, continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object trackSeenNotificationsWhileLockedAndNotDozing;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (!this.Z$0) {
                    KeyguardCoordinator keyguardCoordinator = this.this$0;
                    Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                    this.label = 1;
                    trackSeenNotificationsWhileLockedAndNotDozing = keyguardCoordinator.trackSeenNotificationsWhileLockedAndNotDozing(set, this);
                    if (trackSeenNotificationsWhileLockedAndNotDozing == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        public final Object invoke(boolean z, Continuation continuation) {
            return ((AnonymousClass2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$trackSeenNotificationsWhileLocked$2(KeyguardCoordinator keyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
        this.$notificationsSeenWhileLocked = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$trackSeenNotificationsWhileLocked$2 keyguardCoordinator$trackSeenNotificationsWhileLocked$2 = new KeyguardCoordinator$trackSeenNotificationsWhileLocked$2(this.this$0, this.$notificationsSeenWhileLocked, continuation);
        keyguardCoordinator$trackSeenNotificationsWhileLocked$2.L$0 = obj;
        return keyguardCoordinator$trackSeenNotificationsWhileLocked$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        KeyguardRepository keyguardRepository;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.this$0, this.$notificationsSeenWhileLocked, null), 3);
            keyguardRepository = this.this$0.keyguardRepository;
            ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardRepository).isDozing;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, this.$notificationsSeenWhileLocked, null);
            this.label = 1;
            if (FlowKt.collectLatest(readonlyStateFlow, anonymousClass2, this) == coroutineSingletons) {
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
        return ((KeyguardCoordinator$trackSeenNotificationsWhileLocked$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
