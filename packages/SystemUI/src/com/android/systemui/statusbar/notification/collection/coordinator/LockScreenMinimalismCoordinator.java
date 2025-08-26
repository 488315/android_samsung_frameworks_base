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
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.collection.BundleEntry;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalism;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

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
            if (!this.this$0.minimalismEnabled || this.this$0.isShadeVisible) {
                return;
            }
            this.this$0.logger.logUnseenAdded(notificationEntry.mKey);
            this.this$0.unseenNotifications.add(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            if (this.this$0.minimalismEnabled && this.this$0.unseenNotifications.remove(notificationEntry)) {
                this.this$0.logger.logUnseenRemoved(notificationEntry.mKey);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            if (!this.this$0.minimalismEnabled || this.this$0.isShadeVisible) {
                return;
            }
            this.this$0.logger.logUnseenUpdated(notificationEntry.mKey);
            this.this$0.unseenNotifications.add(notificationEntry);
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
            lockScreenMinimalismCoordinator.seenNotificationsInteractor.getClass();
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
            lockScreenMinimalismCoordinator.seenNotificationsInteractor.getClass();
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$attach$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 implements OnBeforeTransformGroupsListener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener
        public final void onBeforeTransformGroups(List<? extends PipelineEntry> list) {
            LockScreenMinimalismCoordinator.this.pickOutTopUnseenNotifs(list);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$attach$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenMinimalismCoordinator.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator = LockScreenMinimalismCoordinator.this;
                this.label = 1;
                if (lockScreenMinimalismCoordinator.trackLockScreenNotificationMinimalismSettingChanges(this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2, reason: invalid class name and case insensitive filesystem */
    final class C10692 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public C10692(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10692 c10692 = LockScreenMinimalismCoordinator.this.new C10692(continuation);
            c10692.Z$0 = ((Boolean) obj).booleanValue();
            return c10692;
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
                long j = LockScreenMinimalismCoordinator.SHADE_VISIBLE_SEEN_TIMEOUT;
                this.Z$0 = z2;
                this.label = 1;
                if (DelayKt.m3469delayVtjQ1oo(j, this) == coroutineSingletons) {
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
            LockScreenMinimalismCoordinator.this.isShadeVisible = z;
            if (z) {
                LockScreenMinimalismCoordinator.this.logger.logShadeVisible(LockScreenMinimalismCoordinator.this.unseenNotifications.size());
                LockScreenMinimalismCoordinator.this.unseenNotifications.clear();
            } else {
                LockScreenMinimalismCoordinator.this.logger.logShadeHidden();
            }
            return Unit.INSTANCE;
        }

        public final Object invoke(boolean z, Continuation continuation) {
            return ((C10692) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invokeSuspend$lambda$1(String str, NotificationEntry notificationEntry) {
            return Intrinsics.areEqual(notificationEntry.mKey, str);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = LockScreenMinimalismCoordinator.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            final String str;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                String str2 = (String) this.L$0;
                boolean z = false;
                if (str2 == null) {
                    LockScreenMinimalismCoordinator.this.logger.logTopHeadsUpRow(null, false);
                } else {
                    Set set = LockScreenMinimalismCoordinator.this.unseenNotifications;
                    if (!(set instanceof Collection) || !set.isEmpty()) {
                        Iterator it = set.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (Intrinsics.areEqual(((NotificationEntry) it.next()).mKey, str2)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    LockScreenMinimalismCoordinator.this.logger.logTopHeadsUpRow(str2, z);
                    if (z) {
                        long j = LockScreenMinimalismCoordinator.HEADS_UP_SEEN_TIMEOUT;
                        this.L$0 = str2;
                        this.label = 1;
                        if (DelayKt.m3469delayVtjQ1oo(j, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        str = str2;
                    }
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) this.L$0;
            ResultKt.throwOnFailure(obj);
            Set set2 = LockScreenMinimalismCoordinator.this.unseenNotifications;
            final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    return Boolean.valueOf(LockScreenMinimalismCoordinator.AnonymousClass3.invokeSuspend$lambda$1(str, (NotificationEntry) obj2));
                }
            };
            LockScreenMinimalismCoordinator.this.logger.logHunHasBeenSeen(str, set2.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$sam$java_util_function_Predicate$0
                @Override // java.util.function.Predicate
                public final /* synthetic */ boolean test(Object obj2) {
                    return ((Boolean) function1.mo781invoke(obj2)).booleanValue();
                }
            }));
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(String str, Continuation continuation) {
            return ((AnonymousClass3) create(str, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2, reason: invalid class name and case insensitive filesystem */
    final class C10702 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public C10702(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10702 c10702 = LockScreenMinimalismCoordinator.this.new C10702(continuation);
            c10702.Z$0 = ((Boolean) obj).booleanValue();
            return c10702;
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
                if (z != LockScreenMinimalismCoordinator.this.minimalismEnabled) {
                    LockScreenMinimalismCoordinator.this.minimalismEnabled = z;
                    LockScreenMinimalismCoordinator.this.unseenNotifications.clear();
                    LockScreenMinimalismCoordinator.this.getUnseenNotifPromoter().invalidateList("unseen setting changed");
                }
                LockScreenMinimalismCoordinator.this.logger.logTrackingUnseen(z);
                if (z) {
                    LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator = LockScreenMinimalismCoordinator.this;
                    this.label = 1;
                    if (lockScreenMinimalismCoordinator.trackSeenNotifications(this) == coroutineSingletons) {
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
            return ((C10702) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$trackSeenNotifications$2, reason: invalid class name and case insensitive filesystem */
    final class C10712 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$trackSeenNotifications$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ LockScreenMinimalismCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, Continuation continuation) {
                super(2, continuation);
                this.this$0 = lockScreenMinimalismCoordinator;
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
                    LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator = this.this$0;
                    this.label = 1;
                    if (lockScreenMinimalismCoordinator.clearUnseenNotificationsWhenShadeIsExpanded(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$trackSeenNotifications$2$2, reason: invalid class name and collision with other inner class name */
        final class C04932 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ LockScreenMinimalismCoordinator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04932(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, Continuation continuation) {
                super(2, continuation);
                this.this$0 = lockScreenMinimalismCoordinator;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04932(this.this$0, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator = this.this$0;
                    this.label = 1;
                    if (lockScreenMinimalismCoordinator.markHeadsUpNotificationsAsSeen(this) == coroutineSingletons) {
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
                return ((C04932) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        public C10712(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10712 c10712 = LockScreenMinimalismCoordinator.this.new C10712(continuation);
            c10712.L$0 = obj;
            return c10712;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(LockScreenMinimalismCoordinator.this, null), 7);
            return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C04932(LockScreenMinimalismCoordinator.this, null), 7);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C10712) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
        if (((Boolean) function1.mo781invoke(pipelineEntry.getRepresentativeEntry())).booleanValue()) {
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
            if (((Boolean) function1.mo781invoke(it.next())).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object clearUnseenNotificationsWhenShadeIsExpanded(Continuation continuation) {
        Object objCollectLatest = FlowKt.collectLatest(((ShadeInteractorImpl) this.shadeInteractor).isShadeFullyExpanded, new C10692(null), continuation);
        return objCollectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object markHeadsUpNotificationsAsSeen(Continuation continuation) {
        final Flow flow = this.headsUpInteractor.topHeadsUpRowIfPinned;
        Object objCollectLatest = FlowKt.collectLatest(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    String str;
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
                        HeadsUpRowRepository headsUpRowRepository = (HeadsUpRowRepository) obj;
                        if (headsUpRowRepository != null) {
                            this.this$0.headsUpInteractor.getClass();
                            NotificationEntry notificationEntry = ((HeadsUpManagerImpl.HeadsUpEntry) headsUpRowRepository).mEntry;
                            Objects.requireNonNull(notificationEntry);
                            str = notificationEntry.mKey;
                        } else {
                            str = null;
                        }
                        anonymousClass1.label = 1;
                        if (flowCollector.emit(str, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation2);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AnonymousClass3(null), continuation);
        return objCollectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollectLatest : Unit.INSTANCE;
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
        Iterable iterableSingletonList;
        if (pipelineEntry instanceof NotificationEntry) {
            iterableSingletonList = pipelineEntry != null ? Collections.singletonList(pipelineEntry) : EmptyList.INSTANCE;
        } else if (pipelineEntry instanceof GroupEntry) {
            iterableSingletonList = ((GroupEntry) pipelineEntry).mUnmodifiableChildren;
        } else {
            if (!(pipelineEntry instanceof BundleEntry)) {
                throw new IllegalStateException(("unhandled type of " + pipelineEntry).toString());
            }
            iterableSingletonList = EmptyList.INSTANCE;
        }
        return iterableSingletonList;
    }

    private static final boolean pickOutTopUnseenNotifs$lambda$3(NotificationEntry notificationEntry) {
        return notificationEntry.mRanking.getImportance() >= 3;
    }

    private static final boolean pickOutTopUnseenNotifs$lambda$6(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, NotificationEntry notificationEntry) {
        return !ColorizedFgsCoordinator.isRichOngoing(notificationEntry) && lockScreenMinimalismCoordinator.unseenNotifications.contains(notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackLockScreenNotificationMinimalismSettingChanges(Continuation continuation) {
        Object objCollectLatest = FlowKt.collectLatest(minimalismFeatureSettingEnabled(), new C10702(null), continuation);
        return objCollectLatest == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollectLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object trackSeenNotifications(Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new C10712(null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
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
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        ActiveNotificationListRepository activeNotificationListRepository = this.seenNotificationsInteractor.notificationListRepository;
        indentingPrintWriterAsIndenting.append("SeenNotificationsInteractor").println(":");
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            indentingPrintWriterAsIndenting.print("hasFilteredOutSeenNotifications", activeNotificationListRepository.hasFilteredOutSeenNotifications.getValue());
            indentingPrintWriterAsIndenting.print("topOngoingNotificationKey", activeNotificationListRepository.topOngoingNotificationKey.getValue());
            indentingPrintWriterAsIndenting.print("topUnseenNotificationKey", activeNotificationListRepository.topUnseenNotificationKey.getValue());
            indentingPrintWriterAsIndenting.decreaseIndent();
            Set<NotificationEntry> set = this.unseenNotifications;
            indentingPrintWriterAsIndenting.append("unseen notifications").append((CharSequence) ": ").println(set.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            try {
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    indentingPrintWriterAsIndenting.println(((NotificationEntry) it.next()).mKey);
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
