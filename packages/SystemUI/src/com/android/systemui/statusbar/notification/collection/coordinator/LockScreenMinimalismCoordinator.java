package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.collection.BundleEntry;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalism;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class LockScreenMinimalismCoordinator implements Coordinator, Dumpable {
    private static final long HEADS_UP_SEEN_TIMEOUT;
    private static final long SHADE_VISIBLE_SEEN_TIMEOUT;
    private static final String TAG = "LockScreenMinimalismCoordinator";
    private final DumpManager dumpManager;
    private final HeadsUpNotificationInteractor headsUpInteractor;
    private boolean isShadeVisible;
    private final LockScreenMinimalismCoordinatorLogger logger;
    private boolean minimalismEnabled;
    private final CoroutineScope scope;
    private final SeenNotificationsInteractor seenNotificationsInteractor;
    private final ShadeInteractor shadeInteractor;
    private final StatusBarStateController statusBarStateController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Set<NotificationEntry> unseenNotifications = new LinkedHashSet();
    private final LockScreenMinimalismCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            boolean z;
            boolean z2;
            LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger;
            Set set;
            z = LockScreenMinimalismCoordinator.this.minimalismEnabled;
            if (z) {
                z2 = LockScreenMinimalismCoordinator.this.isShadeVisible;
                if (z2) {
                    return;
                }
                lockScreenMinimalismCoordinatorLogger = LockScreenMinimalismCoordinator.this.logger;
                lockScreenMinimalismCoordinatorLogger.logUnseenAdded(notificationEntry.mKey);
                set = LockScreenMinimalismCoordinator.this.unseenNotifications;
                set.add(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            boolean z;
            Set set;
            LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger;
            z = LockScreenMinimalismCoordinator.this.minimalismEnabled;
            if (z) {
                set = LockScreenMinimalismCoordinator.this.unseenNotifications;
                if (set.remove(notificationEntry)) {
                    lockScreenMinimalismCoordinatorLogger = LockScreenMinimalismCoordinator.this.logger;
                    lockScreenMinimalismCoordinatorLogger.logUnseenRemoved(notificationEntry.mKey);
                }
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            boolean z;
            boolean z2;
            LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger;
            Set set;
            z = LockScreenMinimalismCoordinator.this.minimalismEnabled;
            if (z) {
                z2 = LockScreenMinimalismCoordinator.this.isShadeVisible;
                if (z2) {
                    return;
                }
                lockScreenMinimalismCoordinatorLogger = LockScreenMinimalismCoordinator.this.logger;
                lockScreenMinimalismCoordinatorLogger.logUnseenUpdated(notificationEntry.mKey);
                set = LockScreenMinimalismCoordinator.this.unseenNotifications;
                set.add(notificationEntry);
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
    private final NotifPromoter unseenNotifPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$unseenNotifPromoter$1
        {
            super("LockScreenMinimalismCoordinator");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalism.$r8$clinit;
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
            return false;
        }
    };
    private final NotifSectioner topOngoingSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$topOngoingSectioner$1
        {
            super("TopOngoing", 13);
        }

        private static final boolean isInSection$lambda$0(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, NotificationEntry notificationEntry) {
            SeenNotificationsInteractor seenNotificationsInteractor;
            seenNotificationsInteractor = lockScreenMinimalismCoordinator.seenNotificationsInteractor;
            seenNotificationsInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalism.$r8$clinit;
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
            return false;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalism.$r8$clinit;
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
            return false;
        }
    };
    private final NotifSectioner topUnseenSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$topUnseenSectioner$1
        {
            super("TopUnseen", 14);
        }

        private static final boolean isInSection$lambda$0(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, NotificationEntry notificationEntry) {
            SeenNotificationsInteractor seenNotificationsInteractor;
            seenNotificationsInteractor = lockScreenMinimalismCoordinator.seenNotificationsInteractor;
            seenNotificationsInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalism.$r8$clinit;
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
            return false;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = NotificationMinimalism.$r8$clinit;
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
            return false;
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
        DurationUnit durationUnit = DurationUnit.SECONDS;
        SHADE_VISIBLE_SEEN_TIMEOUT = DurationKt.toDuration(0.25d, durationUnit);
        HEADS_UP_SEEN_TIMEOUT = DurationKt.toDuration(0.75d, durationUnit);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$collectionListener$1] */
    public LockScreenMinimalismCoordinator(DumpManager dumpManager, HeadsUpNotificationInteractor headsUpNotificationInteractor, LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger, CoroutineScope coroutineScope, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, ShadeInteractor shadeInteractor) {
        this.dumpManager = dumpManager;
        this.headsUpInteractor = headsUpNotificationInteractor;
        this.logger = lockScreenMinimalismCoordinatorLogger;
        this.scope = coroutineScope;
        this.seenNotificationsInteractor = seenNotificationsInteractor;
        this.statusBarStateController = statusBarStateController;
        this.shadeInteractor = shadeInteractor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean anyEntry(PipelineEntry pipelineEntry, Function1 function1) {
        if (((Boolean) function1.mo779invoke(pipelineEntry.getRepresentativeEntry())).booleanValue()) {
            return true;
        }
        if (!(pipelineEntry instanceof GroupEntry)) {
            return false;
        }
        List list = ((GroupEntry) pipelineEntry).mUnmodifiableChildren;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((Boolean) function1.mo779invoke(it.next())).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object clearUnseenNotificationsWhenShadeIsExpanded(Continuation continuation) {
        Object collectLatest = FlowKt.collectLatest(((ShadeInteractorImpl) this.shadeInteractor).isShadeFullyExpanded, new LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object markHeadsUpNotificationsAsSeen(Continuation continuation) {
        final Flow flow = this.headsUpInteractor.topHeadsUpRowIfPinned;
        Object collectLatest = FlowKt.collectLatest(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ LockScreenMinimalismCoordinator this$0;

                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lockScreenMinimalismCoordinator;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L55
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository r5 = (com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository) r5
                        if (r5 == 0) goto L4b
                        com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator r4 = r4.this$0
                        com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor r4 = com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator.access$getHeadsUpInteractor$p(r4)
                        r4.getClass()
                        com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$HeadsUpEntry r5 = (com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl.HeadsUpEntry) r5
                        com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r5.mEntry
                        java.util.Objects.requireNonNull(r4)
                        java.lang.String r4 = r4.mKey
                        goto L4c
                    L4b:
                        r4 = 0
                    L4c:
                        r0.label = r3
                        java.lang.Object r4 = r6.emit(r4, r0)
                        if (r4 != r1) goto L55
                        return r1
                    L55:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation2);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    private final Flow minimalismFeatureSettingEnabled() {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pickOutTopUnseenNotifs(List<? extends PipelineEntry> list) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationMinimalism.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
    }

    private static final Iterable pickOutTopUnseenNotifs$lambda$2(PipelineEntry pipelineEntry) {
        Iterable iterable;
        if (pipelineEntry instanceof NotificationEntry) {
            iterable = pipelineEntry != null ? Collections.singletonList(pipelineEntry) : EmptyList.INSTANCE;
        } else if (pipelineEntry instanceof GroupEntry) {
            iterable = ((GroupEntry) pipelineEntry).mUnmodifiableChildren;
        } else {
            if (!(pipelineEntry instanceof BundleEntry)) {
                throw new IllegalStateException(("unhandled type of " + pipelineEntry).toString());
            }
            iterable = EmptyList.INSTANCE;
        }
        return iterable;
    }

    private static final boolean pickOutTopUnseenNotifs$lambda$3(NotificationEntry notificationEntry) {
        return notificationEntry.mRanking.getImportance() >= 3;
    }

    private static final boolean pickOutTopUnseenNotifs$lambda$6(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, NotificationEntry notificationEntry) {
        return !ColorizedFgsCoordinator.isRichOngoing(notificationEntry) && lockScreenMinimalismCoordinator.unseenNotifications.contains(notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackLockScreenNotificationMinimalismSettingChanges(Continuation continuation) {
        Object collectLatest = FlowKt.collectLatest(minimalismFeatureSettingEnabled(), new LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2(this, null), continuation);
        return collectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? collectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotifications(Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new LockScreenMinimalismCoordinator$trackSeenNotifications$2(this, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationMinimalism.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.server.notification.notification_minimalism to be enabled.");
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        ActiveNotificationListRepository activeNotificationListRepository = this.seenNotificationsInteractor.notificationListRepository;
        asIndenting.append("SeenNotificationsInteractor").println(":");
        asIndenting.increaseIndent();
        try {
            asIndenting.print("hasFilteredOutSeenNotifications", activeNotificationListRepository.hasFilteredOutSeenNotifications.getValue());
            asIndenting.print("topOngoingNotificationKey", activeNotificationListRepository.topOngoingNotificationKey.getValue());
            asIndenting.print("topUnseenNotificationKey", activeNotificationListRepository.topUnseenNotificationKey.getValue());
            asIndenting.decreaseIndent();
            Set<NotificationEntry> set = this.unseenNotifications;
            asIndenting.append("unseen notifications").append((CharSequence) ": ").println(set.size());
            asIndenting.increaseIndent();
            try {
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    asIndenting.println(((NotificationEntry) it.next()).mKey);
                }
            } finally {
            }
        } finally {
        }
    }

    public final NotifSectioner getTopOngoingSectioner() {
        return this.topOngoingSectioner;
    }

    public final NotifSectioner getTopUnseenSectioner() {
        return this.topUnseenSectioner;
    }

    public final NotifPromoter getUnseenNotifPromoter() {
        return this.unseenNotifPromoter;
    }

    public static /* synthetic */ void getUnseenNotifPromoter$annotations() {
    }
}
