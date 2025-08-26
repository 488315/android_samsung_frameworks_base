package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.StatusBarStateControllerExtKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerExtKt;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.YieldKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class OriginalUnseenKeyguardCoordinator implements Coordinator, Dumpable {
    private static final long SEEN_TIMEOUT;
    private static final String TAG = "OriginalUnseenKeyguardCoordinator";
    private final DumpManager dumpManager;
    private final HeadsUpManager headsUpManager;
    private final KeyguardRepository keyguardRepository;
    private final KeyguardTransitionInteractor keyguardTransitionInteractor;
    private final KeyguardCoordinatorLogger logger;
    private final SceneInteractor sceneInteractor;
    private final CoroutineScope scope;
    private final SeenNotificationsInteractor seenNotificationsInteractor;
    private final StatusBarStateController statusBarStateController;
    private boolean unseenFilterEnabled;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Set<NotificationEntry> unseenNotifications = new LinkedHashSet();
    private final MutableSharedFlow unseenEntryAdded = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
    private final MutableSharedFlow unseenEntryRemoved = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
    private final OriginalUnseenKeyguardCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            if (((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) this.this$0.keyguardRepository).keyguardStateController).mShowing || !this.this$0.statusBarStateController.isExpanded()) {
                this.this$0.logger.logUnseenAdded(notificationEntry.mKey, notificationEntry.mSbn.getPostTime());
                this.this$0.unseenNotifications.add(notificationEntry);
                this.this$0.unseenEntryAdded.tryEmit(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            if (this.this$0.unseenNotifications.remove(notificationEntry)) {
                this.this$0.logger.logUnseenRemoved(notificationEntry.mKey);
                this.this$0.unseenEntryRemoved.tryEmit(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryUpdated(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
            if (((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) this.this$0.keyguardRepository).keyguardStateController).mShowing || !this.this$0.statusBarStateController.isExpanded()) {
                this.this$0.logger.logUnseenUpdated(notificationEntry.mKey, updateSource, notificationEntry.mSbn.getPostTime());
                if (updateSource != UpdateSource.App) {
                    return;
                }
                this.this$0.unseenNotifications.add(notificationEntry);
                this.this$0.unseenEntryAdded.tryEmit(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        @Deprecated
        public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };
    private final NotifFilter unseenNotifFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$unseenNotifFilter$1
        private boolean hasFilteredAnyNotifs;

        {
            super("OriginalUnseenKeyguardCoordinator");
        }

        public final boolean getHasFilteredAnyNotifs() {
            return this.hasFilteredAnyNotifs;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable
        public void onCleanup() {
            this.this$0.logger.logProviderHasFilteredOutSeenNotifs(this.hasFilteredAnyNotifs);
            this.this$0.seenNotificationsInteractor.notificationListRepository.hasFilteredOutSeenNotifications.updateState(null, Boolean.valueOf(this.hasFilteredAnyNotifs));
            this.hasFilteredAnyNotifs = false;
        }

        public final void setHasFilteredAnyNotifs(boolean z) {
            this.hasFilteredAnyNotifs = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:4:0x000a  */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z;
            boolean z2 = true;
            if (this.this$0.unseenFilterEnabled && ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) this.this$0.keyguardRepository).keyguardStateController).mShowing && !this.this$0.unseenNotifications.contains(notificationEntry)) {
                PipelineEntry pipelineEntry = notificationEntry.mAttachState.parent;
                GroupEntry groupEntry = pipelineEntry instanceof GroupEntry ? (GroupEntry) pipelineEntry : null;
                z = (Intrinsics.areEqual(groupEntry != null ? groupEntry.mSummary : null, notificationEntry) || this.this$0.shouldIgnoreUnseenCheck(notificationEntry)) ? false : true;
            }
            if (!this.hasFilteredAnyNotifs && !z) {
                z2 = false;
            }
            this.hasFilteredAnyNotifs = z2;
            return z;
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$attach$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return OriginalUnseenKeyguardCoordinator.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = OriginalUnseenKeyguardCoordinator.this;
                this.label = 1;
                if (originalUnseenKeyguardCoordinator.trackUnseenFilterSettingChanges(this) == coroutineSingletons) {
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
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = OriginalUnseenKeyguardCoordinator.this.new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            boolean z;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                boolean z2 = this.Z$0;
                this.Z$0 = z2;
                this.label = 1;
                if (YieldKt.yield(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                z = z2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                z = this.Z$0;
                ResultKt.throwOnFailure(obj);
            }
            if (z) {
                OriginalUnseenKeyguardCoordinator.this.logger.logShadeExpanded();
                OriginalUnseenKeyguardCoordinator.this.unseenNotifications.clear();
            }
            return Unit.INSTANCE;
        }

        public final Object invoke(boolean z, Continuation continuation) {
            return ((AnonymousClass2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2, reason: invalid class name and case insensitive filesystem */
    final class C10732 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
        /* synthetic */ boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10732(Set<NotificationEntry> set, Continuation continuation) {
            super(2, continuation);
            this.$notificationsSeenWhileLocked = set;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10732 c10732 = OriginalUnseenKeyguardCoordinator.this.new C10732(this.$notificationsSeenWhileLocked, continuation);
            c10732.Z$0 = ((Boolean) obj).booleanValue();
            return c10732;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x002a, code lost:
        
            if (r5.trackSeenNotificationsWhileLocked(r1, r4) == r0) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0075, code lost:
        
            if (r5.trackSeenNotificationsWhileUnlocked(r4) == r0) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0077, code lost:
        
            return r0;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.Z$0) {
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = OriginalUnseenKeyguardCoordinator.this;
                    Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                    this.label = 1;
                } else {
                    if (!this.$notificationsSeenWhileLocked.isEmpty()) {
                        OriginalUnseenKeyguardCoordinator.this.unseenNotifications.removeAll(this.$notificationsSeenWhileLocked);
                        OriginalUnseenKeyguardCoordinator.this.logger.logAllMarkedSeenOnUnlock(this.$notificationsSeenWhileLocked.size(), OriginalUnseenKeyguardCoordinator.this.unseenNotifications.size());
                        this.$notificationsSeenWhileLocked.clear();
                    }
                    OriginalUnseenKeyguardCoordinator.this.getUnseenNotifFilter().invalidateList("keyguard no longer showing");
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator2 = OriginalUnseenKeyguardCoordinator.this;
                    this.label = 2;
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        public final Object invoke(boolean z, Continuation continuation) {
            return ((C10732) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLocked$2, reason: invalid class name and case insensitive filesystem */
    final class C10742 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLocked$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
            int label;
            final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
                super(2, continuation);
                this.this$0 = originalUnseenKeyguardCoordinator;
                this.$notificationsSeenWhileLocked = set;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, this.$notificationsSeenWhileLocked, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    MutableSharedFlow mutableSharedFlow = this.this$0.unseenEntryRemoved;
                    final Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                    final OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.trackSeenNotificationsWhileLocked.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(NotificationEntry notificationEntry, Continuation continuation) {
                            if (set.remove(notificationEntry)) {
                                originalUnseenKeyguardCoordinator.logger.logRemoveSeenOnLockscreen(notificationEntry);
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

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLocked$2$2, reason: invalid class name and collision with other inner class name */
        final class C04952 extends SuspendLambda implements Function2 {
            final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04952(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
                super(2, continuation);
                this.this$0 = originalUnseenKeyguardCoordinator;
                this.$notificationsSeenWhileLocked = set;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04952 c04952 = new C04952(this.this$0, this.$notificationsSeenWhileLocked, continuation);
                c04952.Z$0 = ((Boolean) obj).booleanValue();
                return c04952;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (!this.Z$0) {
                        OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                        Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                        this.label = 1;
                        if (originalUnseenKeyguardCoordinator.trackSeenNotificationsWhileLockedAndNotDozing(set, this) == coroutineSingletons) {
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
                return ((C04952) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10742(Set<NotificationEntry> set, Continuation continuation) {
            super(2, continuation);
            this.$notificationsSeenWhileLocked = set;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10742 c10742 = OriginalUnseenKeyguardCoordinator.this.new C10742(this.$notificationsSeenWhileLocked, continuation);
            c10742.L$0 = obj;
            return c10742;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(OriginalUnseenKeyguardCoordinator.this, this.$notificationsSeenWhileLocked, null), 7);
                ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) OriginalUnseenKeyguardCoordinator.this.keyguardRepository).isDozing;
                C04952 c04952 = new C04952(OriginalUnseenKeyguardCoordinator.this, this.$notificationsSeenWhileLocked, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlyStateFlow, c04952, this) == coroutineSingletons) {
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
            return ((C10742) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2, reason: invalid class name and case insensitive filesystem */
    final class C10752 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$2, reason: invalid class name and collision with other inner class name */
        final class C04962 extends SuspendLambda implements Function2 {
            final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
            final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
            int label;
            final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04962(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, Set<NotificationEntry> set, Continuation continuation) {
                super(2, continuation);
                this.this$0 = originalUnseenKeyguardCoordinator;
                this.$trackingJobsByEntry = map;
                this.$notificationsSeenWhileLocked = set;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04962(this.this$0, this.$trackingJobsByEntry, this.$notificationsSeenWhileLocked, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                    Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
                    Set<NotificationEntry> set = this.$notificationsSeenWhileLocked;
                    this.label = 1;
                    if (C10752.invokeSuspend$trackNewUnseenNotifs(originalUnseenKeyguardCoordinator, map, set, this) == coroutineSingletons) {
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
                return ((C04962) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<NotificationEntry, Job> $trackingJobsByEntry;
            int label;
            final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, Continuation continuation) {
                super(2, continuation);
                this.this$0 = originalUnseenKeyguardCoordinator;
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
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                    Map<NotificationEntry, Job> map = this.$trackingJobsByEntry;
                    this.label = 1;
                    if (C10752.invokeSuspend$stopTrackingRemovedNotifs(originalUnseenKeyguardCoordinator, map, this) == coroutineSingletons) {
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
        public C10752(Set<NotificationEntry> set, Continuation continuation) {
            super(2, continuation);
            this.$notificationsSeenWhileLocked = set;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final Object invokeSuspend$stopTrackingRemovedNotifs(final OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, final Map<NotificationEntry, Job> map, Continuation continuation) {
            OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1 originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1;
            if (continuation instanceof OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1) {
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1 = (OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1) continuation;
                int i = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1.label = i - Integer.MIN_VALUE;
                } else {
                    originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1 = new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1(continuation);
                }
            }
            Object obj = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableSharedFlow mutableSharedFlow = originalUnseenKeyguardCoordinator.unseenEntryRemoved;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(NotificationEntry notificationEntry, Continuation continuation2) {
                        Job jobRemove = map.remove(notificationEntry);
                        if (jobRemove != null) {
                            OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator2 = originalUnseenKeyguardCoordinator;
                            jobRemove.cancel(null);
                            originalUnseenKeyguardCoordinator2.logger.logStopTrackingLockscreenSeenDuration(notificationEntry);
                        }
                        return Unit.INSTANCE;
                    }
                };
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1.label = 1;
                if (mutableSharedFlow.collect(flowCollector, originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final Object invokeSuspend$trackNewUnseenNotifs(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, Set<NotificationEntry> set, Continuation continuation) {
            OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1 originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1;
            if (continuation instanceof OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1) {
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1 = (OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1) continuation;
                int i = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1.label = i - Integer.MIN_VALUE;
                } else {
                    originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1 = new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1(continuation);
                }
            }
            Object obj = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2 originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2 = new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2(originalUnseenKeyguardCoordinator, map, set, null);
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1.label = 1;
                if (CoroutineScopeKt.coroutineScope(originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$2, originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final Object invokeSuspend$trackSeenDurationThreshold(Set<NotificationEntry> set, OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Map<NotificationEntry, Job> map, NotificationEntry notificationEntry, Continuation continuation) {
            OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1 originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1;
            if (continuation instanceof OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1) {
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1 = (OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1) continuation;
                int i = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.label = i - Integer.MIN_VALUE;
                } else {
                    originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1 = new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1(continuation);
                }
            }
            Object obj = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                if (set.remove(notificationEntry)) {
                    originalUnseenKeyguardCoordinator.logger.logResetSeenOnLockscreen(notificationEntry);
                }
                long j = OriginalUnseenKeyguardCoordinator.SEEN_TIMEOUT;
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$0 = set;
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$1 = originalUnseenKeyguardCoordinator;
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$2 = map;
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$3 = notificationEntry;
                originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.label = 1;
                if (DelayKt.m3469delayVtjQ1oo(j, originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                notificationEntry = (NotificationEntry) originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$3;
                map = (Map) originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$2;
                originalUnseenKeyguardCoordinator = (OriginalUnseenKeyguardCoordinator) originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$1;
                set = (Set) originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackSeenDurationThreshold$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            set.add(notificationEntry);
            map.remove(notificationEntry);
            originalUnseenKeyguardCoordinator.logger.logSeenOnLockscreen(notificationEntry);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10752 c10752 = OriginalUnseenKeyguardCoordinator.this.new C10752(this.$notificationsSeenWhileLocked, continuation);
            c10752.L$0 = obj;
            return c10752;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws IOException {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            OriginalUnseenKeyguardCoordinator.this.logger.logTrackingLockscreenSeenDuration(OriginalUnseenKeyguardCoordinator.this.unseenNotifications);
            Set<NotificationEntry> set = OriginalUnseenKeyguardCoordinator.this.unseenNotifications;
            Set<NotificationEntry> set2 = this.$notificationsSeenWhileLocked;
            OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = OriginalUnseenKeyguardCoordinator.this;
            for (NotificationEntry notificationEntry : set) {
                linkedHashMap.put(notificationEntry, CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$1$1(notificationEntry, set2, originalUnseenKeyguardCoordinator, linkedHashMap, null), 7));
            }
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C04962(OriginalUnseenKeyguardCoordinator.this, linkedHashMap, this.$notificationsSeenWhileLocked, null), 7);
            return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(OriginalUnseenKeyguardCoordinator.this, linkedHashMap, null), 7);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C10752) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2, reason: invalid class name and case insensitive filesystem */
    final class C10762 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Continuation continuation) {
                super(2, continuation);
                this.this$0 = originalUnseenKeyguardCoordinator;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                    this.label = 1;
                    if (originalUnseenKeyguardCoordinator.clearUnseenNotificationsWhenShadeIsExpanded(this) == coroutineSingletons) {
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
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2$2, reason: invalid class name and collision with other inner class name */
        final class C04972 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04972(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Continuation continuation) {
                super(2, continuation);
                this.this$0 = originalUnseenKeyguardCoordinator;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04972(this.this$0, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                    this.label = 1;
                    if (originalUnseenKeyguardCoordinator.markHeadsUpNotificationsAsSeen(this) == coroutineSingletons) {
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
                return ((C04972) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        public C10762(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10762 c10762 = OriginalUnseenKeyguardCoordinator.this.new C10762(continuation);
            c10762.L$0 = obj;
            return c10762;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(OriginalUnseenKeyguardCoordinator.this, null), 7);
            return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C04972(OriginalUnseenKeyguardCoordinator.this, null), 7);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C10762) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackUnseenFilterSettingChanges$2, reason: invalid class name and case insensitive filesystem */
    final class C10772 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public C10772(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10772 c10772 = OriginalUnseenKeyguardCoordinator.this.new C10772(continuation);
            c10772.Z$0 = ((Boolean) obj).booleanValue();
            return c10772;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                boolean z = this.Z$0;
                if (z != OriginalUnseenKeyguardCoordinator.this.unseenFilterEnabled) {
                    OriginalUnseenKeyguardCoordinator.this.unseenFilterEnabled = z;
                    OriginalUnseenKeyguardCoordinator.this.getUnseenNotifFilter().invalidateList("unseen setting changed");
                }
                if (z) {
                    OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = OriginalUnseenKeyguardCoordinator.this;
                    this.label = 1;
                    if (originalUnseenKeyguardCoordinator.trackSeenNotifications(this) == coroutineSingletons) {
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
            return ((C10772) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        SEEN_TIMEOUT = DurationKt.toDuration(5, DurationUnit.SECONDS);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$collectionListener$1] */
    public OriginalUnseenKeyguardCoordinator(DumpManager dumpManager, HeadsUpManager headsUpManager, KeyguardRepository keyguardRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardCoordinatorLogger keyguardCoordinatorLogger, CoroutineScope coroutineScope, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, SceneInteractor sceneInteractor) {
        this.dumpManager = dumpManager;
        this.headsUpManager = headsUpManager;
        this.keyguardRepository = keyguardRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.logger = keyguardCoordinatorLogger;
        this.scope = coroutineScope;
        this.seenNotificationsInteractor = seenNotificationsInteractor;
        this.statusBarStateController = statusBarStateController;
        this.sceneInteractor = sceneInteractor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object clearUnseenNotificationsWhenShadeIsExpanded(Continuation continuation) {
        Object objCollectLatest = FlowKt.collectLatest(StatusBarStateControllerExtKt.getExpansionChanges(this.statusBarStateController), new AnonymousClass2(null), continuation);
        return objCollectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object markHeadsUpNotificationsAsSeen(Continuation continuation) {
        Stream allEntries = ((HeadsUpManagerImpl) this.headsUpManager).getAllEntries();
        final OriginalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0 originalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0 = new OriginalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0();
        Stream streamFilter = allEntries.filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) originalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0.mo781invoke(obj)).booleanValue();
            }
        });
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return OriginalUnseenKeyguardCoordinator.markHeadsUpNotificationsAsSeen$lambda$3(this.f$0, (NotificationEntry) obj);
            }
        };
        streamFilter.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$sam$java_util_function_Consumer$0
            @Override // java.util.function.Consumer
            public final /* synthetic */ void accept(Object obj) {
                function1.mo781invoke(obj);
            }
        });
        Object objCollect = HeadsUpManagerExtKt.getHeadsUpEvents(this.headsUpManager).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator.markHeadsUpNotificationsAsSeen.4
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Pair<NotificationEntry, Boolean> pair, Continuation continuation2) {
                NotificationEntry notificationEntry = (NotificationEntry) pair.component1();
                if (((Boolean) pair.component2()).booleanValue()) {
                    OriginalUnseenKeyguardCoordinator.this.logger.logUnseenHun(notificationEntry.mKey);
                    OriginalUnseenKeyguardCoordinator.this.unseenNotifications.remove(notificationEntry);
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit markHeadsUpNotificationsAsSeen$lambda$3(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, NotificationEntry notificationEntry) {
        originalUnseenKeyguardCoordinator.unseenNotifications.remove(notificationEntry);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldIgnoreUnseenCheck(NotificationEntry notificationEntry) {
        boolean zIsMediaNotification;
        notificationEntry.getClass();
        int i = NotificationBundleUi.$r8$clinit;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow == null) {
            zIsMediaNotification = false;
        } else {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            zIsMediaNotification = expandableNotificationRow.getEntryLegacy().mSbn.getNotification().isMediaNotification();
        }
        return zIsMediaNotification || notificationEntry.mSbn.isOngoing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotifications(Continuation continuation) {
        final Flow flow = this.keyguardTransitionInteractor.transitions;
        Object objCollectLatest = FlowKt.collectLatest(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        FlowCollector flowCollector = this.$this_unsafeFlow;
                        Boolean boolValueOf = Boolean.valueOf(((TransitionStep) obj).to != KeyguardState.GONE);
                        anonymousClass1.label = 1;
                        if (flowCollector.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation2);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3(this, null)), new C10732(new LinkedHashSet(), null), continuation);
        return objCollectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileLocked(Set<NotificationEntry> set, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new C10742(set, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileLockedAndNotDozing(Set<NotificationEntry> set, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new C10752(set, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileUnlocked(Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new C10762(null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackUnseenFilterSettingChanges(Continuation continuation) {
        Object objCollectLatest = FlowKt.collectLatest(unseenFeatureEnabled(), new C10772(null), continuation);
        return objCollectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollectLatest : Unit.INSTANCE;
    }

    private final Flow unseenFeatureEnabled() {
        return this.seenNotificationsInteractor.isLockScreenShowOnlyUnseenNotificationsEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addFinalizeFilter(this.unseenNotifFilter);
        notifPipeline.addCollectionListener(this.collectionListener);
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
        this.dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("notificationListInteractor.hasFilteredOutSeenNotifications.value=" + this.seenNotificationsInteractor.hasFilteredOutSeenNotifications.getValue());
        indentingPrintWriterAsIndenting.println("unseen notifications:");
        indentingPrintWriterAsIndenting.increaseIndent();
        Iterator<NotificationEntry> it = this.unseenNotifications.iterator();
        while (it.hasNext()) {
            indentingPrintWriterAsIndenting.println(it.next().mKey);
        }
        indentingPrintWriterAsIndenting.decreaseIndent();
    }

    public final NotifFilter getUnseenNotifFilter() {
        return this.unseenNotifFilter;
    }

    public static /* synthetic */ void getUnseenNotifFilter$annotations() {
    }
}
