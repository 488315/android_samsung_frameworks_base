package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarStateControllerExtKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalismPrototype$V2;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManagerExtKt;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

@CoordinatorScope
public final class KeyguardCoordinator implements Coordinator, Dumpable {
    private static final long SEEN_TIMEOUT;
    private static final String TAG = "KeyguardCoordinator";
    private final AmbientState ambientState;
    private final CoroutineDispatcher bgDispatcher;
    private final DumpManager dumpManager;
    private final HeadsUpManager headsUpManager;
    private final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    private final KeyguardRepository keyguardRepository;
    private final KeyguardTransitionRepository keyguardTransitionRepository;
    private final KeyguardCoordinatorLogger logger;
    private final CoroutineScope scope;
    private final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    private final SecureSettings secureSettings;
    private final SeenNotificationsInteractor seenNotificationsInteractor;
    private final StatusBarStateController statusBarStateController;
    private boolean unseenFilterEnabled;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Set<NotificationEntry> unseenNotifications = new LinkedHashSet();
    private final MutableSharedFlow unseenEntryAdded = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
    private final MutableSharedFlow unseenEntryRemoved = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
    private final KeyguardCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            KeyguardRepository keyguardRepository;
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            Set set;
            MutableSharedFlow mutableSharedFlow;
            StatusBarStateController statusBarStateController;
            keyguardRepository = KeyguardCoordinator.this.keyguardRepository;
            if (!((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardRepository).keyguardStateController).mShowing) {
                statusBarStateController = KeyguardCoordinator.this.statusBarStateController;
                if (statusBarStateController.isExpanded()) {
                    return;
                }
            }
            keyguardCoordinatorLogger = KeyguardCoordinator.this.logger;
            keyguardCoordinatorLogger.logUnseenAdded(notificationEntry.mKey);
            set = KeyguardCoordinator.this.unseenNotifications;
            set.add(notificationEntry);
            mutableSharedFlow = KeyguardCoordinator.this.unseenEntryAdded;
            mutableSharedFlow.tryEmit(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            Set set;
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            MutableSharedFlow mutableSharedFlow;
            set = KeyguardCoordinator.this.unseenNotifications;
            if (set.remove(notificationEntry)) {
                keyguardCoordinatorLogger = KeyguardCoordinator.this.logger;
                keyguardCoordinatorLogger.logUnseenRemoved(notificationEntry.mKey);
                mutableSharedFlow = KeyguardCoordinator.this.unseenEntryRemoved;
                mutableSharedFlow.tryEmit(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            KeyguardRepository keyguardRepository;
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            Set set;
            MutableSharedFlow mutableSharedFlow;
            StatusBarStateController statusBarStateController;
            keyguardRepository = KeyguardCoordinator.this.keyguardRepository;
            if (!((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardRepository).keyguardStateController).mShowing) {
                statusBarStateController = KeyguardCoordinator.this.statusBarStateController;
                if (statusBarStateController.isExpanded()) {
                    return;
                }
            }
            keyguardCoordinatorLogger = KeyguardCoordinator.this.logger;
            keyguardCoordinatorLogger.logUnseenUpdated(notificationEntry.mKey);
            set = KeyguardCoordinator.this.unseenNotifications;
            set.add(notificationEntry);
            mutableSharedFlow = KeyguardCoordinator.this.unseenEntryAdded;
            mutableSharedFlow.tryEmit(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
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
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };
    private final NotifPromoter unseenNotifPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenNotifPromoter$1
        {
            super("KeyguardCoordinator-unseen");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalismPrototype$V2.$r8$clinit;
            Flags.notificationMinimalismPrototype();
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_minimalism_prototype to be enabled.");
            return false;
        }
    };
    private final NotifSectioner topOngoingSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$topOngoingSectioner$1
        {
            super("TopOngoing", 14);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalismPrototype$V2.$r8$clinit;
            Flags.notificationMinimalismPrototype();
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_minimalism_prototype to be enabled.");
            return false;
        }
    };
    private final NotifSectioner topUnseenSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$topUnseenSectioner$1
        {
            super("TopUnseen", 15);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalismPrototype$V2.$r8$clinit;
            Flags.notificationMinimalismPrototype();
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_minimalism_prototype to be enabled.");
            return false;
        }
    };
    private final NotifFilter unseenNotifFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenNotifFilter$1
        private boolean hasFilteredAnyNotifs;

        {
            super("KeyguardCoordinator-unseen");
        }

        private final boolean isOnKeyguard() {
            KeyguardRepository keyguardRepository;
            Flags.notificationMinimalismPrototype();
            Flags.notificationMinimalismPrototype();
            keyguardRepository = KeyguardCoordinator.this.keyguardRepository;
            return ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardRepository).keyguardStateController).mShowing;
        }

        public final boolean getHasFilteredAnyNotifs() {
            return this.hasFilteredAnyNotifs;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable
        public void onCleanup() {
            KeyguardCoordinatorLogger keyguardCoordinatorLogger;
            SeenNotificationsInteractor seenNotificationsInteractor;
            keyguardCoordinatorLogger = KeyguardCoordinator.this.logger;
            keyguardCoordinatorLogger.logProviderHasFilteredOutSeenNotifs(this.hasFilteredAnyNotifs);
            seenNotificationsInteractor = KeyguardCoordinator.this.seenNotificationsInteractor;
            seenNotificationsInteractor.notificationListRepository.hasFilteredOutSeenNotifications.updateState(null, Boolean.valueOf(this.hasFilteredAnyNotifs));
            this.hasFilteredAnyNotifs = false;
        }

        public final void setHasFilteredAnyNotifs(boolean z) {
            this.hasFilteredAnyNotifs = z;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z;
            Set set;
            boolean shouldIgnoreUnseenCheck;
            boolean z2;
            z = KeyguardCoordinator.this.unseenFilterEnabled;
            boolean z3 = true;
            if (z && isOnKeyguard()) {
                set = KeyguardCoordinator.this.unseenNotifications;
                if (!set.contains(notificationEntry)) {
                    GroupEntry parent = notificationEntry.getParent();
                    if (!Intrinsics.areEqual(parent != null ? parent.mSummary : null, notificationEntry)) {
                        shouldIgnoreUnseenCheck = KeyguardCoordinator.this.shouldIgnoreUnseenCheck(notificationEntry);
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
            z2 = false;
            if (!this.hasFilteredAnyNotifs) {
                z3 = false;
            }
            this.hasFilteredAnyNotifs = z3;
            return z2;
        }
    };
    private final NotifFilter notifFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$notifFilter$1
        {
            super("KeyguardCoordinator");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
            keyguardNotificationVisibilityProvider = KeyguardCoordinator.this.keyguardNotificationVisibilityProvider;
            return ((KeyguardNotificationVisibilityProviderImpl) keyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry);
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        SEEN_TIMEOUT = DurationKt.toDuration(5, DurationUnit.SECONDS);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$collectionListener$1] */
    public KeyguardCoordinator(CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager, HeadsUpManager headsUpManager, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, KeyguardRepository keyguardRepository, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardCoordinatorLogger keyguardCoordinatorLogger, CoroutineScope coroutineScope, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, SecureSettings secureSettings, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, AmbientState ambientState) {
        this.bgDispatcher = coroutineDispatcher;
        this.dumpManager = dumpManager;
        this.headsUpManager = headsUpManager;
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.keyguardRepository = keyguardRepository;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.logger = keyguardCoordinatorLogger;
        this.scope = coroutineScope;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
        this.secureSettings = secureSettings;
        this.seenNotificationsInteractor = seenNotificationsInteractor;
        this.statusBarStateController = statusBarStateController;
        this.ambientState = ambientState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean anyEntry(ListEntry listEntry, Function1 function1) {
        if (((Boolean) function1.invoke(listEntry.getRepresentativeEntry())).booleanValue()) {
            return true;
        }
        if (listEntry instanceof GroupEntry) {
            List list = ((GroupEntry) listEntry).mUnmodifiableChildren;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((Boolean) function1.invoke(it.next())).booleanValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final void attachUnseenFilter(NotifPipeline notifPipeline) {
        Flags.notificationMinimalismPrototype();
        notifPipeline.addFinalizeFilter(this.unseenNotifFilter);
        notifPipeline.addCollectionListener(this.collectionListener);
        BuildersKt.launch$default(this.scope, null, null, new KeyguardCoordinator$attachUnseenFilter$2(this, null), 3);
        this.dumpManager.registerDumpable(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object clearUnseenNotificationsWhenShadeIsExpanded(Continuation continuation) {
        Object collectLatest = FlowKt.collectLatest(StatusBarStateControllerExtKt.getExpansionChanges(this.statusBarStateController), new KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invalidateListFromFilter(String str) {
        updateSectionHeadersVisibility();
        this.notifFilter.invalidateList(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object markHeadsUpNotificationsAsSeen(Continuation continuation) {
        ((BaseHeadsUpManager) this.headsUpManager).getHeadsUpEntryList().stream().map(new BaseHeadsUpManager$$ExternalSyntheticLambda0()).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$markHeadsUpNotificationsAsSeen$2
            @Override // java.util.function.Predicate
            public final boolean test(NotificationEntry notificationEntry) {
                return notificationEntry.isRowPinned();
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$markHeadsUpNotificationsAsSeen$3
            @Override // java.util.function.Consumer
            public final void accept(NotificationEntry notificationEntry) {
                Set set;
                set = KeyguardCoordinator.this.unseenNotifications;
                set.remove(notificationEntry);
            }
        });
        Object collect = HeadsUpManagerExtKt.getHeadsUpEvents(this.headsUpManager).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$markHeadsUpNotificationsAsSeen$4
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Pair<NotificationEntry, Boolean> pair, Continuation continuation2) {
                KeyguardCoordinatorLogger keyguardCoordinatorLogger;
                Set set;
                NotificationEntry notificationEntry = (NotificationEntry) pair.component1();
                if (((Boolean) pair.component2()).booleanValue()) {
                    keyguardCoordinatorLogger = KeyguardCoordinator.this.logger;
                    keyguardCoordinatorLogger.logUnseenHun(notificationEntry.mKey);
                    set = KeyguardCoordinator.this.unseenNotifications;
                    set.remove(notificationEntry);
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pickOutTopUnseenNotifs(List<? extends ListEntry> list) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationMinimalismPrototype$V2.$r8$clinit;
        Flags.notificationMinimalismPrototype();
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_minimalism_prototype to be enabled.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldIgnoreUnseenCheck(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        return (expandableNotificationRow == null ? false : expandableNotificationRow.mEntry.mSbn.getNotification().isMediaNotification()) || notificationEntry.mSbn.isOngoing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotifications(Continuation continuation) {
        final Flow flow = ((KeyguardTransitionRepositoryImpl) this.keyguardTransitionRepository).transitions;
        Object collectLatest = FlowKt.collectLatest(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackSeenNotifications$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new KeyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2(this, null)), new KeyguardCoordinator$trackSeenNotifications$2(this, new LinkedHashSet(), null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileLocked(Set<NotificationEntry> set, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new KeyguardCoordinator$trackSeenNotificationsWhileLocked$2(this, set, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileLockedAndNotDozing(Set<NotificationEntry> set, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2(this, set, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotificationsWhileUnlocked(Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new KeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2(this, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackUnseenFilterSettingChanges(Continuation continuation) {
        Object collectLatest = FlowKt.collectLatest(unseenFeatureEnabled(), new KeyguardCoordinator$trackUnseenFilterSettingChanges$2(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    private final Flow unseenFeatureEnabled() {
        Flags.notificationMinimalismPrototype();
        Flags.notificationMinimalismPrototype();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardCoordinator$unseenFeatureEnabled$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, -1, "lock_screen_show_only_unseen_notifications"));
        return FlowKt.buffer$default(FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ KeyguardCoordinator this$0;

                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardCoordinator keyguardCoordinator) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardCoordinator;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator r5 = r5.this$0
                        com.android.systemui.util.settings.SecureSettings r5 = com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator.access$getSecureSettings$p(r5)
                        java.lang.String r6 = "lock_screen_show_only_unseen_notifications"
                        r2 = -2
                        r4 = 0
                        int r5 = r5.getIntForUser(r6, r4, r2)
                        if (r5 != r3) goto L47
                        r4 = r3
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r4)
                        r0.label = r3
                        java.lang.Object r5 = r7.emit(r5, r0)
                        if (r5 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenFeatureEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), this.bgDispatcher), -1);
    }

    private final void updateSectionHeadersVisibility() {
        boolean z = false;
        boolean z2 = this.statusBarStateController.getState() == 1;
        boolean z3 = this.sectionHeaderVisibilityProvider.neverShowSectionHeaders;
        if ((!z2 || this.ambientState.isNeedsToExpandLocksNoti()) && !z3) {
            z = true;
        }
        this.sectionHeaderVisibilityProvider.sectionHeadersVisible = z;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        setupInvalidateNotifListCallbacks();
        notifPipeline.addFinalizeFilter(this.notifFilter);
        KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider = this.keyguardNotificationVisibilityProvider;
        ((KeyguardNotificationVisibilityProviderImpl) keyguardNotificationVisibilityProvider).onStateChangedListeners.addIfAbsent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$attach$1
            @Override // java.util.function.Consumer
            public final void accept(String str) {
                KeyguardCoordinator.this.invalidateListFromFilter(str);
            }
        });
        updateSectionHeadersVisibility();
        attachUnseenFilter(notifPipeline);
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

    public final NotifSectioner getTopOngoingSectioner() {
        return this.topOngoingSectioner;
    }

    public final NotifSectioner getTopUnseenSectioner() {
        return this.topUnseenSectioner;
    }

    public final NotifFilter getUnseenNotifFilter$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.unseenNotifFilter;
    }

    public final NotifPromoter getUnseenNotifPromoter$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.unseenNotifPromoter;
    }

    public static /* synthetic */ void getUnseenNotifFilter$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getUnseenNotifPromoter$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    private final void setupInvalidateNotifListCallbacks() {
    }
}
