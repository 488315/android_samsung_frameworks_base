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
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            KeyguardRepository keyguardRepository;
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            Set set;
            MutableSharedFlow mutableSharedFlow;
            StatusBarStateController statusBarStateController;
            keyguardRepository = OriginalUnseenKeyguardCoordinator.this.keyguardRepository;
            if (!((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardRepository).keyguardStateController).mShowing) {
                statusBarStateController = OriginalUnseenKeyguardCoordinator.this.statusBarStateController;
                if (statusBarStateController.isExpanded()) {
                    return;
                }
            }
            keyguardCoordinatorLogger = OriginalUnseenKeyguardCoordinator.this.logger;
            keyguardCoordinatorLogger.logUnseenAdded(notificationEntry.mKey, notificationEntry.mSbn.getPostTime());
            set = OriginalUnseenKeyguardCoordinator.this.unseenNotifications;
            set.add(notificationEntry);
            mutableSharedFlow = OriginalUnseenKeyguardCoordinator.this.unseenEntryAdded;
            mutableSharedFlow.tryEmit(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            Set set;
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            MutableSharedFlow mutableSharedFlow;
            set = OriginalUnseenKeyguardCoordinator.this.unseenNotifications;
            if (set.remove(notificationEntry)) {
                keyguardCoordinatorLogger = OriginalUnseenKeyguardCoordinator.this.logger;
                keyguardCoordinatorLogger.logUnseenRemoved(notificationEntry.mKey);
                mutableSharedFlow = OriginalUnseenKeyguardCoordinator.this.unseenEntryRemoved;
                mutableSharedFlow.tryEmit(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryUpdated(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
            KeyguardRepository keyguardRepository;
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            Set set;
            MutableSharedFlow mutableSharedFlow;
            StatusBarStateController statusBarStateController;
            keyguardRepository = OriginalUnseenKeyguardCoordinator.this.keyguardRepository;
            if (!((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardRepository).keyguardStateController).mShowing) {
                statusBarStateController = OriginalUnseenKeyguardCoordinator.this.statusBarStateController;
                if (statusBarStateController.isExpanded()) {
                    return;
                }
            }
            keyguardCoordinatorLogger = OriginalUnseenKeyguardCoordinator.this.logger;
            keyguardCoordinatorLogger.logUnseenUpdated(notificationEntry.mKey, updateSource, notificationEntry.mSbn.getPostTime());
            if (updateSource != UpdateSource.App) {
                return;
            }
            set = OriginalUnseenKeyguardCoordinator.this.unseenNotifications;
            set.add(notificationEntry);
            mutableSharedFlow = OriginalUnseenKeyguardCoordinator.this.unseenEntryAdded;
            mutableSharedFlow.tryEmit(notificationEntry);
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
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            SeenNotificationsInteractor seenNotificationsInteractor;
            keyguardCoordinatorLogger = OriginalUnseenKeyguardCoordinator.this.logger;
            keyguardCoordinatorLogger.logProviderHasFilteredOutSeenNotifs(this.hasFilteredAnyNotifs);
            seenNotificationsInteractor = OriginalUnseenKeyguardCoordinator.this.seenNotificationsInteractor;
            seenNotificationsInteractor.notificationListRepository.hasFilteredOutSeenNotifications.updateState(null, Boolean.valueOf(this.hasFilteredAnyNotifs));
            this.hasFilteredAnyNotifs = false;
        }

        public final void setHasFilteredAnyNotifs(boolean z) {
            this.hasFilteredAnyNotifs = z;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z;
            KeyguardRepository keyguardRepository;
            Set set;
            boolean shouldIgnoreUnseenCheck;
            boolean z2;
            z = OriginalUnseenKeyguardCoordinator.this.unseenFilterEnabled;
            boolean z3 = true;
            if (z) {
                keyguardRepository = OriginalUnseenKeyguardCoordinator.this.keyguardRepository;
                if (((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardRepository).keyguardStateController).mShowing) {
                    set = OriginalUnseenKeyguardCoordinator.this.unseenNotifications;
                    if (!set.contains(notificationEntry)) {
                        PipelineEntry pipelineEntry = notificationEntry.mAttachState.parent;
                        GroupEntry groupEntry = pipelineEntry instanceof GroupEntry ? (GroupEntry) pipelineEntry : null;
                        if (!Intrinsics.areEqual(groupEntry != null ? groupEntry.mSummary : null, notificationEntry)) {
                            shouldIgnoreUnseenCheck = OriginalUnseenKeyguardCoordinator.this.shouldIgnoreUnseenCheck(notificationEntry);
                            if (!shouldIgnoreUnseenCheck) {
                                z2 = true;
                                if (!this.hasFilteredAnyNotifs && !z2) {
                                    z3 = false;
                                }
                                this.hasFilteredAnyNotifs = z3;
                                return z2;
                            }
                        }
                    }
                }
            }
            z2 = false;
            if (!this.hasFilteredAnyNotifs) {
                z3 = false;
            }
            this.hasFilteredAnyNotifs = z3;
            return z2;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        Object collectLatest = FlowKt.collectLatest(StatusBarStateControllerExtKt.getExpansionChanges(this.statusBarStateController), new OriginalUnseenKeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object markHeadsUpNotificationsAsSeen(Continuation continuation) {
        Stream allEntries = ((HeadsUpManagerImpl) this.headsUpManager).getAllEntries();
        final OriginalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0 originalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0 = new OriginalUnseenKeyguardCoordinator$$ExternalSyntheticLambda0();
        Stream filter = allEntries.filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
            }
        });
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Unit markHeadsUpNotificationsAsSeen$lambda$3;
                markHeadsUpNotificationsAsSeen$lambda$3 = OriginalUnseenKeyguardCoordinator.markHeadsUpNotificationsAsSeen$lambda$3(OriginalUnseenKeyguardCoordinator.this, (NotificationEntry) obj);
                return markHeadsUpNotificationsAsSeen$lambda$3;
            }
        };
        filter.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$sam$java_util_function_Consumer$0
            @Override // java.util.function.Consumer
            public final /* synthetic */ void accept(Object obj) {
                Function1.this.mo779invoke(obj);
            }
        });
        Object collect = HeadsUpManagerExtKt.getHeadsUpEvents(this.headsUpManager).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$markHeadsUpNotificationsAsSeen$4
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Pair<NotificationEntry, Boolean> pair, Continuation continuation2) {
                KeyguardCoordinatorLogger keyguardCoordinatorLogger;
                Set set;
                NotificationEntry notificationEntry = (NotificationEntry) pair.component1();
                if (((Boolean) pair.component2()).booleanValue()) {
                    keyguardCoordinatorLogger = OriginalUnseenKeyguardCoordinator.this.logger;
                    keyguardCoordinatorLogger.logUnseenHun(notificationEntry.mKey);
                    set = OriginalUnseenKeyguardCoordinator.this.unseenNotifications;
                    set.remove(notificationEntry);
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit markHeadsUpNotificationsAsSeen$lambda$3(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, NotificationEntry notificationEntry) {
        originalUnseenKeyguardCoordinator.unseenNotifications.remove(notificationEntry);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldIgnoreUnseenCheck(NotificationEntry notificationEntry) {
        boolean isMediaNotification;
        notificationEntry.getClass();
        int i = NotificationBundleUi.$r8$clinit;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow == null) {
            isMediaNotification = false;
        } else {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            isMediaNotification = expandableNotificationRow.getEntryLegacy().mSbn.getNotification().isMediaNotification();
        }
        return isMediaNotification || notificationEntry.mSbn.isOngoing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotifications(Continuation continuation) {
        final Flow flow = this.keyguardTransitionInteractor.transitions;
        Object collectLatest = FlowKt.collectLatest(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = r5.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r5 == r6) goto L3e
                        r5 = r3
                        goto L3f
                    L3e:
                        r5 = 0
                    L3f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3(this, null)), new OriginalUnseenKeyguardCoordinator$trackSeenNotifications$2(this, new LinkedHashSet(), null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileLocked(Set<NotificationEntry> set, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLocked$2(this, set, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileLockedAndNotDozing(Set<NotificationEntry> set, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2(this, set, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileUnlocked(Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2(this, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackUnseenFilterSettingChanges(Continuation continuation) {
        Object collectLatest = FlowKt.collectLatest(unseenFeatureEnabled(), new OriginalUnseenKeyguardCoordinator$trackUnseenFilterSettingChanges$2(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    private final Flow unseenFeatureEnabled() {
        return this.seenNotificationsInteractor.isLockScreenShowOnlyUnseenNotificationsEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addFinalizeFilter(this.unseenNotifFilter);
        notifPipeline.addCollectionListener(this.collectionListener);
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new OriginalUnseenKeyguardCoordinator$attach$1(this, null), 7);
        this.dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("notificationListInteractor.hasFilteredOutSeenNotifications.value=" + this.seenNotificationsInteractor.hasFilteredOutSeenNotifications.getValue());
        asIndenting.println("unseen notifications:");
        asIndenting.increaseIndent();
        Iterator<NotificationEntry> it = this.unseenNotifications.iterator();
        while (it.hasNext()) {
            asIndenting.println(it.next().mKey);
        }
        asIndenting.decreaseIndent();
    }

    public final NotifFilter getUnseenNotifFilter() {
        return this.unseenNotifFilter;
    }

    public static /* synthetic */ void getUnseenNotifFilter$annotations() {
    }
}
