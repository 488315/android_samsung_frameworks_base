package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class HeadsUpCoordinator implements Coordinator {
    private static final long BIND_TIMEOUT = 1000;
    private static final long MAX_RANKING_UPDATE_DELAY_MS = 2000;
    private static final String TAG = "HeadsUpCoordinator";
    private NotifLifetimeExtender.OnEndLifetimeExtensionCallback mEndLifetimeExtension;
    private final DelayableExecutor mExecutor;
    private final NotifPipelineFlags mFlags;
    private final HeadsUpManager mHeadsUpManager;
    private final HeadsUpViewBinder mHeadsUpViewBinder;
    private final NodeController mIncomingHeaderController;
    private final LaunchFullScreenIntentProvider mLaunchFullScreenIntentProvider;
    private final HeadsUpCoordinatorLogger mLogger;
    private NotifPipeline mNotifPipeline;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final SystemClock mSystemClock;
    private final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final ArrayMap<String, Long> mEntriesBindingUntil = new ArrayMap<>();
    private final ArrayMap<String, Long> mEntriesUpdateTimes = new ArrayMap<>();
    private final ArrayMap<String, Long> mFSIUpdateCandidates = new ArrayMap<>();
    private long mNow = -1;
    private final LinkedHashMap<String, PostedEntry> mPostedEntries = new LinkedHashMap<>();
    private final ArrayMap<NotificationEntry, Runnable> mNotifsExtendingLifetime = new ArrayMap<>();
    private final HeadsUpCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider;
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider2;
            SystemClock systemClock;
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider3;
            LinkedHashMap linkedHashMap;
            SystemClock systemClock2;
            LaunchFullScreenIntentProvider launchFullScreenIntentProvider;
            visualInterruptionDecisionProvider = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
            NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision = visualInterruptionDecisionProvider.makeUnloggedFullScreenIntentDecision(notificationEntry);
            visualInterruptionDecisionProvider2 = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
            visualInterruptionDecisionProvider2.logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
            if (makeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
                launchFullScreenIntentProvider = HeadsUpCoordinator.this.mLaunchFullScreenIntentProvider;
                launchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
            } else if (makeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
                HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
                systemClock = headsUpCoordinator.mSystemClock;
                headsUpCoordinator.addForFSIReconsideration(notificationEntry, systemClock.currentTimeMillis());
            }
            visualInterruptionDecisionProvider3 = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
            boolean shouldInterrupt = visualInterruptionDecisionProvider3.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt();
            linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
            linkedHashMap.put(notificationEntry.mKey, new HeadsUpCoordinator.PostedEntry(notificationEntry, true, false, shouldInterrupt, true, false, false));
            HeadsUpCoordinator headsUpCoordinator2 = HeadsUpCoordinator.this;
            systemClock2 = headsUpCoordinator2.mSystemClock;
            headsUpCoordinator2.setUpdateTime(notificationEntry, systemClock2.currentTimeMillis());
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryCleanUp(NotificationEntry notificationEntry) {
            HeadsUpViewBinder headsUpViewBinder;
            headsUpViewBinder = HeadsUpCoordinator.this.mHeadsUpViewBinder;
            headsUpViewBinder.abortBindCallback(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            LinkedHashMap linkedHashMap;
            ArrayMap arrayMap;
            HeadsUpManager headsUpManager;
            NotificationRemoteInputManager notificationRemoteInputManager;
            HeadsUpManager headsUpManager2;
            linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
            String str = notificationEntry.mKey;
            linkedHashMap.remove(str);
            arrayMap = HeadsUpCoordinator.this.mEntriesUpdateTimes;
            arrayMap.remove(str);
            HeadsUpCoordinator.this.cancelHeadsUpBind(notificationEntry);
            headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            if (((BaseHeadsUpManager) headsUpManager).isHeadsUpEntry(str)) {
                notificationRemoteInputManager = HeadsUpCoordinator.this.mRemoteInputManager;
                boolean z = notificationRemoteInputManager.isSpinning(str) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY;
                headsUpManager2 = HeadsUpCoordinator.this.mHeadsUpManager;
                ((BaseHeadsUpManager) headsUpManager2).removeNotification$1(str, z);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onRankingApplied() {
            NotifPipeline notifPipeline;
            boolean isNewEnoughForRankingUpdate;
            boolean isCandidateForFSIReconsideration;
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider;
            LinkedHashMap linkedHashMap;
            HeadsUpCoordinatorLogger headsUpCoordinatorLogger;
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider2;
            HeadsUpCoordinatorLogger headsUpCoordinatorLogger2;
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider3;
            LaunchFullScreenIntentProvider launchFullScreenIntentProvider;
            ArrayMap arrayMap;
            HeadsUpCoordinatorLogger headsUpCoordinatorLogger3;
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider4;
            ArrayMap arrayMap2;
            notifPipeline = HeadsUpCoordinator.this.mNotifPipeline;
            if (notifPipeline == null) {
                notifPipeline = null;
            }
            for (NotificationEntry notificationEntry : notifPipeline.getAllNotifs()) {
                isNewEnoughForRankingUpdate = HeadsUpCoordinator.this.isNewEnoughForRankingUpdate(notificationEntry);
                if (isNewEnoughForRankingUpdate && !notificationEntry.interruption) {
                    isCandidateForFSIReconsideration = HeadsUpCoordinator.this.isCandidateForFSIReconsideration(notificationEntry);
                    String str = notificationEntry.mKey;
                    if (isCandidateForFSIReconsideration) {
                        visualInterruptionDecisionProvider2 = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
                        NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision = visualInterruptionDecisionProvider2.makeUnloggedFullScreenIntentDecision(notificationEntry);
                        if (makeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
                            headsUpCoordinatorLogger2 = HeadsUpCoordinator.this.mLogger;
                            headsUpCoordinatorLogger2.logEntryUpdatedToFullScreen(str, makeUnloggedFullScreenIntentDecision.getLogReason());
                            visualInterruptionDecisionProvider3 = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
                            visualInterruptionDecisionProvider3.logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
                            launchFullScreenIntentProvider = HeadsUpCoordinator.this.mLaunchFullScreenIntentProvider;
                            launchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
                            arrayMap = HeadsUpCoordinator.this.mFSIUpdateCandidates;
                            arrayMap.remove(str);
                        } else if (!makeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
                            headsUpCoordinatorLogger3 = HeadsUpCoordinator.this.mLogger;
                            headsUpCoordinatorLogger3.logEntryDisqualifiedFromFullScreen(str, makeUnloggedFullScreenIntentDecision.getLogReason());
                            visualInterruptionDecisionProvider4 = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
                            visualInterruptionDecisionProvider4.logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
                            arrayMap2 = HeadsUpCoordinator.this.mFSIUpdateCandidates;
                            arrayMap2.remove(str);
                        }
                    }
                    visualInterruptionDecisionProvider = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
                    VisualInterruptionDecisionProvider.Decision makeUnloggedHeadsUpDecision = visualInterruptionDecisionProvider.makeUnloggedHeadsUpDecision(notificationEntry);
                    boolean shouldInterrupt = makeUnloggedHeadsUpDecision.getShouldInterrupt();
                    linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
                    HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.get(str);
                    if ((postedEntry != null ? postedEntry.getShouldHeadsUpEver() : false) != shouldInterrupt) {
                        headsUpCoordinatorLogger = HeadsUpCoordinator.this.mLogger;
                        headsUpCoordinatorLogger.logEntryUpdatedByRanking(str, shouldInterrupt, makeUnloggedHeadsUpDecision.getLogReason());
                        onEntryUpdated(notificationEntry);
                    }
                }
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(final NotificationEntry notificationEntry) {
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider;
            final boolean shouldHunAgain;
            HeadsUpManager headsUpManager;
            final boolean isEntryBinding;
            LinkedHashMap linkedHashMap;
            SystemClock systemClock;
            HeadsUpManager headsUpManager2;
            visualInterruptionDecisionProvider = HeadsUpCoordinator.this.mVisualInterruptionDecisionProvider;
            final boolean shouldInterrupt = visualInterruptionDecisionProvider.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt();
            shouldHunAgain = HeadsUpCoordinator.this.shouldHunAgain(notificationEntry);
            headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            String str = notificationEntry.mKey;
            final boolean isHeadsUpEntry = ((BaseHeadsUpManager) headsUpManager).isHeadsUpEntry(str);
            isEntryBinding = HeadsUpCoordinator.this.isEntryBinding(notificationEntry);
            final boolean z = notificationEntry.mIsHeadsUpByBriefExpanding;
            linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
            HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.compute(str, new BiFunction() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1$onEntryUpdated$posted$1
                @Override // java.util.function.BiFunction
                public final HeadsUpCoordinator.PostedEntry apply(String str2, HeadsUpCoordinator.PostedEntry postedEntry2) {
                    if (postedEntry2 != null) {
                        boolean z2 = shouldInterrupt;
                        boolean z3 = z;
                        boolean z4 = shouldHunAgain;
                        boolean z5 = isHeadsUpEntry;
                        boolean z6 = isEntryBinding;
                        postedEntry2.setWasUpdated(true);
                        postedEntry2.setShouldHeadsUpEver(postedEntry2.getShouldHeadsUpEver() || z2 || z3);
                        postedEntry2.setShouldHeadsUpAgain(postedEntry2.getShouldHeadsUpAgain() || z4);
                        postedEntry2.setHeadsUpEntry(z5);
                        postedEntry2.setBinding(z6);
                    } else {
                        postedEntry2 = new HeadsUpCoordinator.PostedEntry(NotificationEntry.this, false, true, shouldInterrupt || z, shouldHunAgain, isHeadsUpEntry, isEntryBinding);
                    }
                    return postedEntry2;
                }
            });
            if (postedEntry != null && !postedEntry.getShouldHeadsUpEver()) {
                if (postedEntry.isHeadsUpEntry()) {
                    headsUpManager2 = HeadsUpCoordinator.this.mHeadsUpManager;
                    ((BaseHeadsUpManager) headsUpManager2).removeNotification$1(postedEntry.getKey(), false);
                } else if (postedEntry.isBinding()) {
                    HeadsUpCoordinator.this.cancelHeadsUpBind(postedEntry.getEntry());
                }
            }
            HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            systemClock = headsUpCoordinator.mSystemClock;
            headsUpCoordinator.setUpdateTime(notificationEntry, systemClock.currentTimeMillis());
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
    private final Consumer<NotificationEntry> mActionPressListener = new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1
        @Override // java.util.function.Consumer
        public final void accept(final NotificationEntry notificationEntry) {
            HeadsUpManager headsUpManager;
            DelayableExecutor delayableExecutor;
            headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            Intrinsics.checkNotNull(notificationEntry);
            BaseHeadsUpManager baseHeadsUpManager = (BaseHeadsUpManager) headsUpManager;
            baseHeadsUpManager.getClass();
            BaseHeadsUpManager.HeadsUpEntry headsUpEntry = baseHeadsUpManager.getHeadsUpEntry(notificationEntry.mKey);
            if (headsUpEntry != null) {
                headsUpEntry.mUserActionMayIndirectlyRemove = true;
            }
            delayableExecutor = HeadsUpCoordinator.this.mExecutor;
            final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1.1
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpCoordinator.this.endNotifLifetimeExtensionIfExtended(notificationEntry);
                }
            });
        }
    };
    private final HeadsUpCoordinator$mLifetimeExtender$1 mLifetimeExtender = new NotifLifetimeExtender() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public void cancelLifetimeExtension(NotificationEntry notificationEntry) {
            ArrayMap arrayMap;
            arrayMap = HeadsUpCoordinator.this.mNotifsExtendingLifetime;
            Runnable runnable = (Runnable) arrayMap.remove(notificationEntry);
            if (runnable != null) {
                runnable.run();
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public String getName() {
            return "HeadsUpCoordinator";
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public boolean maybeExtendLifetime(final NotificationEntry notificationEntry, int i) {
            HeadsUpManager headsUpManager;
            boolean isSticky;
            DelayableExecutor delayableExecutor;
            ArrayMap arrayMap;
            HeadsUpManager headsUpManager2;
            ArrayMap arrayMap2;
            DelayableExecutor delayableExecutor2;
            headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            String str = notificationEntry.mKey;
            if (headsUpManager.canRemoveImmediately(str)) {
                return false;
            }
            isSticky = HeadsUpCoordinator.this.isSticky(notificationEntry);
            if (!isSticky) {
                delayableExecutor = HeadsUpCoordinator.this.mExecutor;
                final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationRemoteInputManager notificationRemoteInputManager;
                        HeadsUpManager headsUpManager3;
                        notificationRemoteInputManager = HeadsUpCoordinator.this.mRemoteInputManager;
                        boolean z = notificationRemoteInputManager.isSpinning(notificationEntry.mKey) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY;
                        headsUpManager3 = HeadsUpCoordinator.this.mHeadsUpManager;
                        ((BaseHeadsUpManager) headsUpManager3).removeNotification$1(notificationEntry.mKey, z);
                    }
                });
                arrayMap = HeadsUpCoordinator.this.mNotifsExtendingLifetime;
                arrayMap.put(notificationEntry, null);
                return true;
            }
            headsUpManager2 = HeadsUpCoordinator.this.mHeadsUpManager;
            BaseHeadsUpManager baseHeadsUpManager = (BaseHeadsUpManager) headsUpManager2;
            BaseHeadsUpManager.HeadsUpEntry headsUpEntry = (BaseHeadsUpManager.HeadsUpEntry) baseHeadsUpManager.mHeadsUpEntryMap.get(str);
            long max = headsUpEntry != null ? Math.max(0L, headsUpEntry.mEarliestRemovalTime - baseHeadsUpManager.mSystemClock.elapsedRealtime()) : 0L;
            arrayMap2 = HeadsUpCoordinator.this.mNotifsExtendingLifetime;
            delayableExecutor2 = HeadsUpCoordinator.this.mExecutor;
            final HeadsUpCoordinator headsUpCoordinator2 = HeadsUpCoordinator.this;
            arrayMap2.put(notificationEntry, delayableExecutor2.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$1
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpManager headsUpManager3;
                    headsUpManager3 = HeadsUpCoordinator.this.mHeadsUpManager;
                    ((BaseHeadsUpManager) headsUpManager3).removeNotification$1(notificationEntry.mKey, true);
                }
            }, max));
            return true;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public void setCallback(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback) {
            HeadsUpCoordinator.this.mEndLifetimeExtension = onEndLifetimeExtensionCallback;
        }
    };
    private final HeadsUpCoordinator$mNotifPromoter$1 mNotifPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1
        {
            super("HeadsUpCoordinator");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            boolean isGoingToShowHunNoRetract;
            isGoingToShowHunNoRetract = HeadsUpCoordinator.this.isGoingToShowHunNoRetract(notificationEntry);
            return isGoingToShowHunNoRetract;
        }
    };
    private final NotifSectioner sectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1
        {
            super("HeadsUp", 7);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            return new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1$getComparator$1
                {
                    super("HeadsUp");
                }

                @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
                public int compare(ListEntry listEntry, ListEntry listEntry2) {
                    HeadsUpManager headsUpManager;
                    headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
                    NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
                    NotificationEntry representativeEntry2 = listEntry2.getRepresentativeEntry();
                    BaseHeadsUpManager baseHeadsUpManager = (BaseHeadsUpManager) headsUpManager;
                    baseHeadsUpManager.getClass();
                    if (representativeEntry == null || representativeEntry2 == null) {
                        return Boolean.compare(representativeEntry == null, representativeEntry2 == null);
                    }
                    BaseHeadsUpManager.HeadsUpEntry headsUpEntry = baseHeadsUpManager.getHeadsUpEntry(representativeEntry.mKey);
                    BaseHeadsUpManager.HeadsUpEntry headsUpEntry2 = baseHeadsUpManager.getHeadsUpEntry(representativeEntry2.mKey);
                    if (headsUpEntry == null || headsUpEntry2 == null) {
                        return Boolean.compare(headsUpEntry == null, headsUpEntry2 == null);
                    }
                    return headsUpEntry.compareTo(headsUpEntry2);
                }
            };
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            boolean isGoingToShowHunNoRetract;
            isGoingToShowHunNoRetract = HeadsUpCoordinator.this.isGoingToShowHunNoRetract(listEntry);
            return isGoingToShowHunNoRetract;
        }
    };
    private final HeadsUpCoordinator$mOnHeadsUpChangedListener$1 mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public void onHeadsUpPinned(NotificationEntry notificationEntry) {
            LinkedHashMap linkedHashMap;
            HeadsUpCoordinator$mNotifPromoter$1 headsUpCoordinator$mNotifPromoter$1;
            if (notificationEntry != null ? notificationEntry.mIsHeadsUpByBriefExpanding : false) {
                linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
                HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.get(notificationEntry != null ? notificationEntry.mKey : null);
                if (postedEntry != null) {
                    postedEntry.setHeadsUpByBriefExpanding(true);
                }
                headsUpCoordinator$mNotifPromoter$1 = HeadsUpCoordinator.this.mNotifPromoter;
                headsUpCoordinator$mNotifPromoter$1.invalidateList("headsUpFromBrief: " + NotificationUtilsKt.getLogKey(notificationEntry));
            }
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            HeadsUpCoordinator$mNotifPromoter$1 headsUpCoordinator$mNotifPromoter$1;
            HeadsUpViewBinder headsUpViewBinder;
            if (z) {
                return;
            }
            headsUpCoordinator$mNotifPromoter$1 = HeadsUpCoordinator.this.mNotifPromoter;
            headsUpCoordinator$mNotifPromoter$1.invalidateList("headsUpEnded: " + NotificationUtilsKt.getLogKey(notificationEntry));
            headsUpViewBinder = HeadsUpCoordinator.this.mHeadsUpViewBinder;
            headsUpViewBinder.unbindHeadsUpView(notificationEntry);
            HeadsUpCoordinator.this.endNotifLifetimeExtensionIfExtended(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            LinkedHashMap linkedHashMap;
            linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
            HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.get(notificationEntry != null ? notificationEntry.mKey : null);
            if (postedEntry == null) {
                return;
            }
            postedEntry.setHeadsUpByBriefExpanding(false);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public /* bridge */ /* synthetic */ void onHeadsUpPinnedModeChanged(boolean z) {
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1] */
    public HeadsUpCoordinator(HeadsUpCoordinatorLogger headsUpCoordinatorLogger, SystemClock systemClock, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, NotifPipelineFlags notifPipelineFlags, NodeController nodeController, DelayableExecutor delayableExecutor) {
        this.mLogger = headsUpCoordinatorLogger;
        this.mSystemClock = systemClock;
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpViewBinder = headsUpViewBinder;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mLaunchFullScreenIntentProvider = launchFullScreenIntentProvider;
        this.mFlags = notifPipelineFlags;
        this.mIncomingHeaderController = nodeController;
        this.mExecutor = delayableExecutor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelHeadsUpBind(NotificationEntry notificationEntry) {
        this.mEntriesBindingUntil.remove(notificationEntry.mKey);
        this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cleanUpEntryTimes() {
        long currentTimeMillis = this.mSystemClock.currentTimeMillis() - 2000;
        ArraySet arraySet = new ArraySet();
        for (Map.Entry<String, Long> entry : this.mEntriesUpdateTimes.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            if (value == null || currentTimeMillis > value.longValue()) {
                arraySet.add(key);
            }
        }
        this.mEntriesUpdateTimes.removeAll(arraySet);
        ArraySet arraySet2 = new ArraySet();
        for (Map.Entry<String, Long> entry2 : this.mFSIUpdateCandidates.entrySet()) {
            String key2 = entry2.getKey();
            Long value2 = entry2.getValue();
            if (value2 == null || currentTimeMillis > value2.longValue()) {
                arraySet2.add(key2);
            }
        }
        this.mFSIUpdateCandidates.removeAll(arraySet2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void endNotifLifetimeExtensionIfExtended(NotificationEntry notificationEntry) {
        if (this.mNotifsExtendingLifetime.containsKey(notificationEntry)) {
            Runnable remove = this.mNotifsExtendingLifetime.remove(notificationEntry);
            if (remove != null) {
                remove.run();
            }
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = this.mEndLifetimeExtension;
            if (onEndLifetimeExtensionCallback != null) {
                ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(notificationEntry, this.mLifetimeExtender);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NotificationEntry findBestTransferChild(List<NotificationEntry> list, final Function1 function1) {
        return (NotificationEntry) SequencesKt___SequencesKt.firstOrNull(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(NotificationEntry notificationEntry) {
                return Boolean.valueOf(!notificationEntry.mSbn.getNotification().isGroupSummary());
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(NotificationEntry notificationEntry) {
                return Boolean.valueOf(Function1.this.invoke(notificationEntry.mKey) != GroupLocation.Detached);
            }
        }), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(NotificationEntry notificationEntry) {
                LinkedHashMap linkedHashMap;
                linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
                return Boolean.valueOf(!linkedHashMap.containsKey(notificationEntry.mKey));
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$4
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(NotificationEntry notificationEntry) {
                return Long.valueOf(-notificationEntry.mSbn.getNotification().getWhen());
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NotificationEntry findHeadsUpOverride(List<PostedEntry> list, Function1 function1) {
        PostedEntry postedEntry = (PostedEntry) SequencesKt___SequencesKt.firstOrNull(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findHeadsUpOverride$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(HeadsUpCoordinator.PostedEntry postedEntry2) {
                return Boolean.valueOf(!postedEntry2.getEntry().mSbn.getNotification().isGroupSummary());
            }
        }), new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findHeadsUpOverride$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(-((HeadsUpCoordinator.PostedEntry) t).getEntry().mSbn.getNotification().getWhen()), Long.valueOf(-((HeadsUpCoordinator.PostedEntry) t2).getEntry().mSbn.getNotification().getWhen()));
            }
        }));
        if (postedEntry == null) {
            return null;
        }
        NotificationEntry entry = postedEntry.getEntry();
        if (function1.invoke(entry.mKey) == GroupLocation.Isolated && entry.mSbn.getNotification().getGroupAlertBehavior() == 1) {
            return entry;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<String, GroupLocation> getGroupLocationsByKey(List<? extends ListEntry> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ListEntry listEntry : list) {
            if (listEntry instanceof NotificationEntry) {
                linkedHashMap.put(((NotificationEntry) listEntry).mKey, GroupLocation.Isolated);
            } else {
                if (!(listEntry instanceof GroupEntry)) {
                    throw new IllegalStateException(("unhandled type " + listEntry).toString());
                }
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    linkedHashMap.put(notificationEntry.mKey, GroupLocation.Summary);
                }
                Iterator it = groupEntry.mUnmodifiableChildren.iterator();
                while (it.hasNext()) {
                    linkedHashMap.put(((NotificationEntry) it.next()).mKey, GroupLocation.Child);
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handlePostedEntry(PostedEntry postedEntry, HunMutator hunMutator, String str) {
        this.mLogger.logPostedEntryWillEvaluate(postedEntry, str);
        if (postedEntry.getWasAdded()) {
            if (postedEntry.getShouldHeadsUpEver()) {
                bindForAsyncHeadsUp(postedEntry);
                return;
            }
            return;
        }
        if (!postedEntry.isHeadsUpAlready()) {
            if (postedEntry.getShouldHeadsUpEver() && postedEntry.getShouldHeadsUpAgain()) {
                bindForAsyncHeadsUp(postedEntry);
                return;
            }
            return;
        }
        if (postedEntry.getShouldHeadsUpEver()) {
            if (postedEntry.isHeadsUpEntry()) {
                hunMutator.updateNotification(postedEntry.getKey(), postedEntry.getShouldHeadsUpAgain());
            }
        } else if (postedEntry.isHeadsUpEntry()) {
            hunMutator.removeNotification(postedEntry.getKey(), false);
        } else {
            cancelHeadsUpBind(postedEntry.getEntry());
        }
    }

    private final boolean isAttemptingToShowHun(ListEntry listEntry) {
        return ((BaseHeadsUpManager) this.mHeadsUpManager).isHeadsUpEntry(listEntry.getKey()) || isEntryBinding(listEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCandidateForFSIReconsideration(NotificationEntry notificationEntry) {
        Long l = this.mFSIUpdateCandidates.get(notificationEntry.mKey);
        if (l == null) {
            return false;
        }
        return this.mSystemClock.currentTimeMillis() - l.longValue() <= 2000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isEntryBinding(ListEntry listEntry) {
        Long l = this.mEntriesBindingUntil.get(listEntry.getKey());
        return l != null && l.longValue() >= this.mNow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isGoingToShowHunNoRetract(ListEntry listEntry) {
        PostedEntry postedEntry = this.mPostedEntries.get(listEntry.getKey());
        if (!(postedEntry != null ? postedEntry.getCalculateShouldBeHeadsUpNoRetract() : isAttemptingToShowHun(listEntry))) {
            PostedEntry postedEntry2 = this.mPostedEntries.get(listEntry.getKey());
            if (!(postedEntry2 != null ? postedEntry2.isHeadsUpByBriefExpanding() : false)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isGoingToShowHunStrict(ListEntry listEntry) {
        PostedEntry postedEntry = this.mPostedEntries.get(listEntry.getKey());
        return postedEntry != null ? postedEntry.getCalculateShouldBeHeadsUpStrict() : isAttemptingToShowHun(listEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isNewEnoughForRankingUpdate(NotificationEntry notificationEntry) {
        Long l;
        if (this.mEntriesUpdateTimes.containsKey(notificationEntry.mKey) && (l = this.mEntriesUpdateTimes.get(notificationEntry.mKey)) != null) {
            return this.mSystemClock.currentTimeMillis() - l.longValue() <= 2000;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isSticky(NotificationEntry notificationEntry) {
        BaseHeadsUpManager.HeadsUpEntry headsUpEntry = ((BaseHeadsUpManager) this.mHeadsUpManager).getHeadsUpEntry(notificationEntry.mKey);
        if (headsUpEntry != null) {
            return headsUpEntry.isSticky();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onHeadsUpViewBound(NotificationEntry notificationEntry) {
        ((BaseHeadsUpManager) this.mHeadsUpManager).showNotification(notificationEntry);
        this.mEntriesBindingUntil.remove(notificationEntry.mKey);
        if (notificationEntry.row.mIsPinned) {
            NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            notificationColorPicker.getClass();
            notificationEntry.row.applyHeadsUpBackground(NotificationColorPicker.isCustom(expandableNotificationRow));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldHunAgain(NotificationEntry notificationEntry) {
        return !notificationEntry.interruption || (notificationEntry.mSbn.getNotification().flags & 8) == 0;
    }

    public final void addForFSIReconsideration(NotificationEntry notificationEntry, long j) {
        this.mFSIUpdateCandidates.put(notificationEntry.mKey, Long.valueOf(j));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        ((BaseHeadsUpManager) this.mHeadsUpManager).addListener(this.mOnHeadsUpChangedListener);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        OnBeforeTransformGroupsListener onBeforeTransformGroupsListener = new OnBeforeTransformGroupsListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener
            public final void onBeforeTransformGroups(List<? extends ListEntry> list) {
                HeadsUpCoordinator.this.onBeforeTransformGroups(list);
            }
        };
        ShadeListBuilder shadeListBuilder = notifPipeline.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnBeforeTransformGroupsListeners.addIfAbsent(onBeforeTransformGroupsListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends ListEntry> list) {
                HeadsUpCoordinator.this.onBeforeFinalizeFilter(list);
            }
        });
        notifPipeline.addPromoter(this.mNotifPromoter);
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        notificationRemoteInputManager.mActionPressListeners.addIfAbsent(this.mActionPressListener);
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        ((ArrayList) ((BaseHeadsUpManager) headsUpManager).mCallbacks).add(new HeadsUpCoordinator$attach$3(this));
    }

    public final void bindForAsyncHeadsUp(NotificationEntry notificationEntry) {
        if (notificationEntry != null) {
            LinkedHashMap<String, PostedEntry> linkedHashMap = this.mPostedEntries;
            String str = notificationEntry.mKey;
            linkedHashMap.put(str, new PostedEntry(notificationEntry, false, false, true, true, false, false));
            notificationEntry.mIsHeadsUpByBriefExpanding = true;
            PostedEntry postedEntry = this.mPostedEntries.get(str);
            if (postedEntry != null) {
                bindForAsyncHeadsUp(postedEntry);
            }
        }
    }

    public final NotifSectioner getSectioner() {
        return this.sectioner;
    }

    public final void onBeforeFinalizeFilter(final List<? extends ListEntry> list) {
        HeadsUpCoordinatorKt.modifyHuns(this.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            private static final Map<String, GroupLocation> invoke$lambda$2(Lazy lazy) {
                return (Map) lazy.getValue();
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((HunMutator) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code restructure failed: missing block: B:52:0x017c, code lost:
            
                r8 = r6.findBestTransferChild(r9, new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4(invoke$lambda$2(r2)));
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void invoke(com.android.systemui.statusbar.notification.collection.coordinator.HunMutator r24) {
                /*
                    Method dump skipped, instructions count: 656
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1.invoke(com.android.systemui.statusbar.notification.collection.coordinator.HunMutator):void");
            }
        });
    }

    public final void onBeforeTransformGroups(List<? extends ListEntry> list) {
        this.mNow = this.mSystemClock.currentTimeMillis();
        if (this.mPostedEntries.isEmpty()) {
            return;
        }
        HeadsUpCoordinatorKt.modifyHuns(this.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeTransformGroups$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((HunMutator) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(HunMutator hunMutator) {
                LinkedHashMap linkedHashMap;
                LinkedHashMap linkedHashMap2;
                linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
                List<HeadsUpCoordinator.PostedEntry> list2 = CollectionsKt___CollectionsKt.toList(linkedHashMap.values());
                HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
                for (HeadsUpCoordinator.PostedEntry postedEntry : list2) {
                    if (!postedEntry.getEntry().mSbn.isGroup()) {
                        headsUpCoordinator.handlePostedEntry(postedEntry, hunMutator, "non-group");
                        linkedHashMap2 = headsUpCoordinator.mPostedEntries;
                        linkedHashMap2.remove(postedEntry.getKey());
                    }
                }
            }
        });
    }

    public final void setUpdateTime(NotificationEntry notificationEntry, long j) {
        this.mEntriesUpdateTimes.put(notificationEntry.mKey, Long.valueOf(j));
    }

    private final void bindForAsyncHeadsUp(PostedEntry postedEntry) {
        this.mEntriesBindingUntil.put(postedEntry.getKey(), Long.valueOf(this.mNow + 1000));
        this.mHeadsUpViewBinder.bindHeadsUpView(postedEntry.getEntry(), new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$bindForAsyncHeadsUp$2
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry) {
                HeadsUpCoordinator.this.onHeadsUpViewBound(notificationEntry);
            }
        });
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PostedEntry {
        public static final int $stable = 8;
        private final NotificationEntry entry;
        private boolean isBinding;
        private boolean isHeadsUpByBriefExpanding;
        private boolean isHeadsUpEntry;
        private final String key;
        private boolean shouldHeadsUpAgain;
        private boolean shouldHeadsUpEver;
        private final boolean wasAdded;
        private boolean wasUpdated;

        public PostedEntry(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.entry = notificationEntry;
            this.wasAdded = z;
            this.wasUpdated = z2;
            this.shouldHeadsUpEver = z3;
            this.shouldHeadsUpAgain = z4;
            this.isHeadsUpEntry = z5;
            this.isBinding = z6;
            this.key = notificationEntry.mKey;
        }

        public static /* synthetic */ PostedEntry copy$default(PostedEntry postedEntry, NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, Object obj) {
            if ((i & 1) != 0) {
                notificationEntry = postedEntry.entry;
            }
            if ((i & 2) != 0) {
                z = postedEntry.wasAdded;
            }
            boolean z7 = z;
            if ((i & 4) != 0) {
                z2 = postedEntry.wasUpdated;
            }
            boolean z8 = z2;
            if ((i & 8) != 0) {
                z3 = postedEntry.shouldHeadsUpEver;
            }
            boolean z9 = z3;
            if ((i & 16) != 0) {
                z4 = postedEntry.shouldHeadsUpAgain;
            }
            boolean z10 = z4;
            if ((i & 32) != 0) {
                z5 = postedEntry.isHeadsUpEntry;
            }
            boolean z11 = z5;
            if ((i & 64) != 0) {
                z6 = postedEntry.isBinding;
            }
            return postedEntry.copy(notificationEntry, z7, z8, z9, z10, z11, z6);
        }

        public final NotificationEntry component1() {
            return this.entry;
        }

        public final boolean component2() {
            return this.wasAdded;
        }

        public final boolean component3() {
            return this.wasUpdated;
        }

        public final boolean component4() {
            return this.shouldHeadsUpEver;
        }

        public final boolean component5() {
            return this.shouldHeadsUpAgain;
        }

        public final boolean component6() {
            return this.isHeadsUpEntry;
        }

        public final boolean component7() {
            return this.isBinding;
        }

        public final PostedEntry copy(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            return new PostedEntry(notificationEntry, z, z2, z3, z4, z5, z6);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostedEntry)) {
                return false;
            }
            PostedEntry postedEntry = (PostedEntry) obj;
            return Intrinsics.areEqual(this.entry, postedEntry.entry) && this.wasAdded == postedEntry.wasAdded && this.wasUpdated == postedEntry.wasUpdated && this.shouldHeadsUpEver == postedEntry.shouldHeadsUpEver && this.shouldHeadsUpAgain == postedEntry.shouldHeadsUpAgain && this.isHeadsUpEntry == postedEntry.isHeadsUpEntry && this.isBinding == postedEntry.isBinding;
        }

        public final boolean getCalculateShouldBeHeadsUpNoRetract() {
            return isHeadsUpAlready() || (this.shouldHeadsUpEver && (this.wasAdded || this.shouldHeadsUpAgain));
        }

        public final boolean getCalculateShouldBeHeadsUpStrict() {
            return this.shouldHeadsUpEver && (this.wasAdded || this.shouldHeadsUpAgain || isHeadsUpAlready());
        }

        public final NotificationEntry getEntry() {
            return this.entry;
        }

        public final String getKey() {
            return this.key;
        }

        public final boolean getShouldHeadsUpAgain() {
            return this.shouldHeadsUpAgain;
        }

        public final boolean getShouldHeadsUpEver() {
            return this.shouldHeadsUpEver;
        }

        public final boolean getWasAdded() {
            return this.wasAdded;
        }

        public final boolean getWasUpdated() {
            return this.wasUpdated;
        }

        public int hashCode() {
            return Boolean.hashCode(this.isBinding) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.entry.hashCode() * 31, 31, this.wasAdded), 31, this.wasUpdated), 31, this.shouldHeadsUpEver), 31, this.shouldHeadsUpAgain), 31, this.isHeadsUpEntry);
        }

        public final boolean isBinding() {
            return this.isBinding;
        }

        public final boolean isHeadsUpAlready() {
            return this.isHeadsUpEntry || this.isBinding;
        }

        public final boolean isHeadsUpByBriefExpanding() {
            return this.isHeadsUpByBriefExpanding;
        }

        public final boolean isHeadsUpEntry() {
            return this.isHeadsUpEntry;
        }

        public final void setBinding(boolean z) {
            this.isBinding = z;
        }

        public final void setHeadsUpByBriefExpanding(boolean z) {
            this.isHeadsUpByBriefExpanding = z;
        }

        public final void setHeadsUpEntry(boolean z) {
            this.isHeadsUpEntry = z;
        }

        public final void setShouldHeadsUpAgain(boolean z) {
            this.shouldHeadsUpAgain = z;
        }

        public final void setShouldHeadsUpEver(boolean z) {
            this.shouldHeadsUpEver = z;
        }

        public final void setWasUpdated(boolean z) {
            this.wasUpdated = z;
        }

        public String toString() {
            NotificationEntry notificationEntry = this.entry;
            boolean z = this.wasAdded;
            boolean z2 = this.wasUpdated;
            boolean z3 = this.shouldHeadsUpEver;
            boolean z4 = this.shouldHeadsUpAgain;
            boolean z5 = this.isHeadsUpEntry;
            boolean z6 = this.isBinding;
            StringBuilder sb = new StringBuilder("PostedEntry(entry=");
            sb.append(notificationEntry);
            sb.append(", wasAdded=");
            sb.append(z);
            sb.append(", wasUpdated=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z2, ", shouldHeadsUpEver=", z3, ", shouldHeadsUpAgain=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z4, ", isHeadsUpEntry=", z5, ", isBinding=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, z6, ")");
        }

        public static /* synthetic */ void isHeadsUpByBriefExpanding$annotations() {
        }
    }
}
