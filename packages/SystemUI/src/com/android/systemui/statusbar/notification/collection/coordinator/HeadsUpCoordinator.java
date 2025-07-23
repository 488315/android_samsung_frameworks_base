package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.core.os.CancellationSignal;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor;
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.BundleEntry;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerLogger;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationActionClickManager;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class HeadsUpCoordinator implements Coordinator {
    private static final long BIND_TIMEOUT = 1000;
    private static final long MAX_RANKING_UPDATE_DELAY_MS = 2000;
    private static final String TAG = "HeadsUpCoordinator";
    private final CoroutineScope applicationScope;
    private NotifLifetimeExtender.OnEndLifetimeExtensionCallback mEndLifetimeExtension;
    private final DelayableExecutor mExecutor;
    private final NotifPipelineFlags mFlags;
    private final HeadsUpManager mHeadsUpManager;
    private final HeadsUpViewBinder mHeadsUpViewBinder;
    private final NodeController mIncomingHeaderController;
    private final VisualInterruptionDecisionLogger mInterruptLogger;
    private final LaunchFullScreenIntentProvider mLaunchFullScreenIntentProvider;
    private final HeadsUpCoordinatorLogger mLogger;
    private NotifPipeline mNotifPipeline;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final SystemClock mSystemClock;
    private final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    private final NotifCollection notifCollection;
    private final NotificationActionClickManager notificationActionClickManager;
    private final StatusBarChipsUiEventLogger statusBarChipsUiEventLogger;
    private final StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final ArrayMap<String, Long> mEntriesBindingUntil = new ArrayMap<>();
    private final ArrayMap<String, Long> mEntriesUpdateTimes = new ArrayMap<>();
    private final ArrayMap<String, Long> mFSIUpdateCandidates = new ArrayMap<>();
    private long mNow = -1;
    private final LinkedHashMap<String, PostedEntry> mPostedEntries = new LinkedHashMap<>();
    private final ArrayMap<NotificationEntry, Runnable> mNotifsExtendingLifetime = new ArrayMap<>();
    private final HeadsUpCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new HeadsUpCoordinator$mNotifCollectionListener$1(this);
    private final Consumer<NotificationEntry> mActionPressListener = new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1
        @Override // java.util.function.Consumer
        public final void accept(final NotificationEntry notificationEntry) {
            HeadsUpManager headsUpManager;
            DelayableExecutor delayableExecutor;
            headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = ((HeadsUpManagerImpl) headsUpManager).getHeadsUpEntry(notificationEntry.mKey);
            if (headsUpEntry != null) {
                headsUpEntry.mUserActionMayIndirectlyRemove = true;
            }
            delayableExecutor = HeadsUpCoordinator.this.mExecutor;
            final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1.1
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpCoordinator headsUpCoordinator2 = HeadsUpCoordinator.this;
                    NotificationEntry notificationEntry2 = notificationEntry;
                    notificationEntry2.getClass();
                    headsUpCoordinator2.endNotifLifetimeExtensionIfExtended(notificationEntry2);
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
        public boolean maybeExtendLifetime(final NotificationEntry notificationEntry, final int i) {
            HeadsUpManager headsUpManager;
            boolean isSticky;
            DelayableExecutor delayableExecutor;
            ArrayMap arrayMap;
            HeadsUpManager headsUpManager2;
            ArrayMap arrayMap2;
            DelayableExecutor delayableExecutor2;
            headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            if (((HeadsUpManagerImpl) headsUpManager).canRemoveImmediately(notificationEntry.mKey)) {
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
                        ((HeadsUpManagerImpl) headsUpManager3).removeNotification(notificationEntry.mKey, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "lifetime extension - extended for reason: ", ", isSticky: false"), z);
                    }
                });
                arrayMap = HeadsUpCoordinator.this.mNotifsExtendingLifetime;
                arrayMap.put(notificationEntry, null);
                return true;
            }
            headsUpManager2 = HeadsUpCoordinator.this.mHeadsUpManager;
            long earliestRemovalTime = ((HeadsUpManagerImpl) headsUpManager2).getEarliestRemovalTime(notificationEntry.mKey);
            arrayMap2 = HeadsUpCoordinator.this.mNotifsExtendingLifetime;
            delayableExecutor2 = HeadsUpCoordinator.this.mExecutor;
            final HeadsUpCoordinator headsUpCoordinator2 = HeadsUpCoordinator.this;
            arrayMap2.put(notificationEntry, delayableExecutor2.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$1
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpManager headsUpManager3;
                    headsUpManager3 = HeadsUpCoordinator.this.mHeadsUpManager;
                    ((HeadsUpManagerImpl) headsUpManager3).removeNotification(notificationEntry.mKey, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "cancel lifetime extension - extended for reason: ", ", isSticky: true"), true);
                }
            }, earliestRemovalTime));
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
                public int compare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2) {
                    HeadsUpManager headsUpManager;
                    headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
                    NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
                    NotificationEntry representativeEntry2 = pipelineEntry2.getRepresentativeEntry();
                    HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) headsUpManager;
                    headsUpManagerImpl.getClass();
                    if (representativeEntry == null || representativeEntry2 == null) {
                        return Boolean.compare(representativeEntry == null, representativeEntry2 == null);
                    }
                    HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = headsUpManagerImpl.getHeadsUpEntry(representativeEntry.mKey);
                    HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = headsUpManagerImpl.getHeadsUpEntry(representativeEntry2.mKey);
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
        public boolean isInSection(PipelineEntry pipelineEntry) {
            boolean isGoingToShowHunNoRetract;
            if (BundleUtil.Companion.isClassified(pipelineEntry)) {
                return false;
            }
            isGoingToShowHunNoRetract = HeadsUpCoordinator.this.isGoingToShowHunNoRetract(pipelineEntry);
            return isGoingToShowHunNoRetract;
        }
    };
    private final HeadsUpCoordinator$mOnHeadsUpChangedListener$1 mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public void onHeadsUpAnimatingAwayEnded(NotificationEntry notificationEntry) {
            HeadsUpCoordinator$mNotifPromoter$1 headsUpCoordinator$mNotifPromoter$1;
            headsUpCoordinator$mNotifPromoter$1 = HeadsUpCoordinator.this.mNotifPromoter;
            headsUpCoordinator$mNotifPromoter$1.invalidateList("headsUpAnimatingAwayEnded: " + NotificationUtilsKt.getLogKey(notificationEntry));
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
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

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            HeadsUpCoordinator$mNotifPromoter$1 headsUpCoordinator$mNotifPromoter$1;
            final HeadsUpViewBinder headsUpViewBinder;
            if (z) {
                return;
            }
            headsUpCoordinator$mNotifPromoter$1 = HeadsUpCoordinator.this.mNotifPromoter;
            headsUpCoordinator$mNotifPromoter$1.invalidateList("headsUpEnded: " + NotificationUtilsKt.getLogKey(notificationEntry));
            headsUpViewBinder = HeadsUpCoordinator.this.mHeadsUpViewBinder;
            headsUpViewBinder.abortBindCallback(notificationEntry);
            RowContentBindStage rowContentBindStage = headsUpViewBinder.mStage;
            RowContentBindParams rowContentBindParams = (RowContentBindParams) ((ArrayMap) rowContentBindStage.mContentParams).get(notificationEntry);
            HeadsUpViewBinderLogger headsUpViewBinderLogger = headsUpViewBinder.mLogger;
            if (rowContentBindParams == null) {
                headsUpViewBinderLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 headsUpViewBinderLogger$$ExternalSyntheticLambda0 = new HeadsUpViewBinderLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logBuffer.commit(obtain);
            } else {
                rowContentBindParams.markContentViewsFreeable(4);
                headsUpViewBinderLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 headsUpViewBinderLogger$$ExternalSyntheticLambda02 = new HeadsUpViewBinderLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer2 = headsUpViewBinderLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("HeadsUpViewBinder", logLevel2, headsUpViewBinderLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logBuffer2.commit(obtain2);
                rowContentBindStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder$$ExternalSyntheticLambda1
                    @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
                    public final void onBindFinished(NotificationEntry notificationEntry2) {
                        HeadsUpViewBinderLogger headsUpViewBinderLogger2 = HeadsUpViewBinder.this.mLogger;
                        headsUpViewBinderLogger2.getClass();
                        LogLevel logLevel3 = LogLevel.INFO;
                        HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 headsUpViewBinderLogger$$ExternalSyntheticLambda03 = new HeadsUpViewBinderLogger$$ExternalSyntheticLambda0(5);
                        LogBuffer logBuffer3 = headsUpViewBinderLogger2.buffer;
                        LogMessage obtain3 = logBuffer3.obtain("HeadsUpViewBinder", logLevel3, headsUpViewBinderLogger$$ExternalSyntheticLambda03, null);
                        ((LogMessageImpl) obtain3).str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                        logBuffer3.commit(obtain3);
                    }
                });
            }
            HeadsUpCoordinator.this.endNotifLifetimeExtensionIfExtended(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            LinkedHashMap linkedHashMap;
            linkedHashMap = HeadsUpCoordinator.this.mPostedEntries;
            HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.get(notificationEntry != null ? notificationEntry.mKey : null);
            if (postedEntry != null) {
                postedEntry.setHeadsUpByBriefExpanding(false);
            }
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public /* bridge */ /* synthetic */ void onHeadsUpPinnedModeChanged(boolean z) {
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

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1] */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1] */
    public HeadsUpCoordinator(CoroutineScope coroutineScope, HeadsUpCoordinatorLogger headsUpCoordinatorLogger, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, SystemClock systemClock, NotifCollection notifCollection, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, NotificationActionClickManager notificationActionClickManager, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, NotifPipelineFlags notifPipelineFlags, StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger, NodeController nodeController, DelayableExecutor delayableExecutor) {
        this.applicationScope = coroutineScope;
        this.mLogger = headsUpCoordinatorLogger;
        this.mInterruptLogger = visualInterruptionDecisionLogger;
        this.mSystemClock = systemClock;
        this.notifCollection = notifCollection;
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpViewBinder = headsUpViewBinder;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.notificationActionClickManager = notificationActionClickManager;
        this.mLaunchFullScreenIntentProvider = launchFullScreenIntentProvider;
        this.mFlags = notifPipelineFlags;
        this.statusBarNotificationChipsInteractor = statusBarNotificationChipsInteractor;
        this.statusBarChipsUiEventLogger = statusBarChipsUiEventLogger;
        this.mIncomingHeaderController = nodeController;
        this.mExecutor = delayableExecutor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelHeadsUpBind(NotificationEntry notificationEntry) {
        this.mEntriesBindingUntil.remove(notificationEntry.mKey);
        this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    private final void cleanUpEntryTimes() {
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
                ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(this.mLifetimeExtender, notificationEntry);
            }
        }
    }

    private final NotificationEntry findBestTransferChild(List<NotificationEntry> list, Function1 function1) {
        return (NotificationEntry) SequencesKt___SequencesKt.firstOrNull(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new HeadsUpCoordinator$$ExternalSyntheticLambda0(1)), new HeadsUpCoordinator$$ExternalSyntheticLambda2(function1, 0)), ComparisonsKt__ComparisonsKt.compareBy(new HeadsUpCoordinator$$ExternalSyntheticLambda5(this, 1), new HeadsUpCoordinator$$ExternalSyntheticLambda0(2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findBestTransferChild$lambda$21(NotificationEntry notificationEntry) {
        return !notificationEntry.mSbn.getNotification().isGroupSummary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findBestTransferChild$lambda$22(Function1 function1, NotificationEntry notificationEntry) {
        return function1.mo779invoke(notificationEntry.mKey) != GroupLocation.Detached;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable findBestTransferChild$lambda$23(HeadsUpCoordinator headsUpCoordinator, NotificationEntry notificationEntry) {
        return Boolean.valueOf(!headsUpCoordinator.mPostedEntries.containsKey(notificationEntry.mKey));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable findBestTransferChild$lambda$24(NotificationEntry notificationEntry) {
        return Long.valueOf(-notificationEntry.mSbn.getNotification().getWhen());
    }

    private final NotificationEntry findHeadsUpOverride(List<PostedEntry> list, Function1 function1) {
        PostedEntry postedEntry = (PostedEntry) SequencesKt___SequencesKt.firstOrNull(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new HeadsUpCoordinator$$ExternalSyntheticLambda0(0)), new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findHeadsUpOverride$$inlined$sortedBy$1
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
        if (function1.mo779invoke(entry.mKey) == GroupLocation.Isolated && entry.mSbn.getNotification().getGroupAlertBehavior() == 1) {
            return entry;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findHeadsUpOverride$lambda$17(PostedEntry postedEntry) {
        return !postedEntry.getEntry().mSbn.getNotification().isGroupSummary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<String, GroupLocation> getGroupLocationsByKey(List<? extends PipelineEntry> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PipelineEntry pipelineEntry : list) {
            if (pipelineEntry instanceof NotificationEntry) {
                linkedHashMap.put(((NotificationEntry) pipelineEntry).mKey, GroupLocation.Isolated);
            } else if (pipelineEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    linkedHashMap.put(notificationEntry.mKey, GroupLocation.Summary);
                }
                Iterator it = groupEntry.mUnmodifiableChildren.iterator();
                while (it.hasNext()) {
                    linkedHashMap.put(((NotificationEntry) it.next()).mKey, GroupLocation.Child);
                }
            } else {
                if (!(pipelineEntry instanceof BundleEntry)) {
                    throw new IllegalStateException(("unhandled type " + pipelineEntry).toString());
                }
                linkedHashMap.put(((BundleEntry) pipelineEntry).mKey, GroupLocation.Bundle);
            }
        }
        return linkedHashMap;
    }

    private final void handlePostedEntry(PostedEntry postedEntry, HunMutator hunMutator, String str) {
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
                hunMutator.updateNotification(postedEntry.getKey(), postedEntry.getShouldHeadsUpAgain() ? PinnedStatus.PinnedBySystem : PinnedStatus.NotPinned);
            }
        } else if (postedEntry.isHeadsUpEntry()) {
            hunMutator.removeNotification(postedEntry.getKey(), postedEntry.isPinnedByUser());
        } else {
            cancelHeadsUpBind(postedEntry.getEntry());
        }
    }

    private final boolean isAttemptingToShowHun(PipelineEntry pipelineEntry) {
        return ((HeadsUpManagerImpl) this.mHeadsUpManager).isHeadsUpEntry(pipelineEntry.getKey()) || isEntryBinding(pipelineEntry) || isHeadsUpAnimatingAway(pipelineEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCandidateForFSIReconsideration(NotificationEntry notificationEntry) {
        Long l = this.mFSIUpdateCandidates.get(notificationEntry.mKey);
        if (l != null) {
            if (this.mSystemClock.currentTimeMillis() - l.longValue() <= 2000) {
                return true;
            }
        }
        return false;
    }

    private final boolean isDisqualifiedChild(NotificationEntry notificationEntry) {
        if (notificationEntry.mRanking.getChannel() == null || notificationEntry.mRanking.getChannel().getId() == null) {
            return false;
        }
        return NotificationChannel.SYSTEM_RESERVED_IDS.contains(notificationEntry.mRanking.getChannel().getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isEntryBinding(PipelineEntry pipelineEntry) {
        Long l = this.mEntriesBindingUntil.get(pipelineEntry.getKey());
        return l != null && l.longValue() >= this.mNow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isGoingToShowHunNoRetract(PipelineEntry pipelineEntry) {
        PostedEntry postedEntry = this.mPostedEntries.get(pipelineEntry.getKey());
        if (postedEntry != null ? postedEntry.getCalculateShouldBeHeadsUpNoRetract() : isAttemptingToShowHun(pipelineEntry)) {
            return true;
        }
        PostedEntry postedEntry2 = this.mPostedEntries.get(pipelineEntry.getKey());
        return postedEntry2 != null ? postedEntry2.isHeadsUpByBriefExpanding() : false;
    }

    private final boolean isGoingToShowHunStrict(PipelineEntry pipelineEntry) {
        PostedEntry postedEntry = this.mPostedEntries.get(pipelineEntry.getKey());
        return postedEntry != null ? postedEntry.getCalculateShouldBeHeadsUpStrict() : isAttemptingToShowHun(pipelineEntry);
    }

    private final boolean isHeadsUpAnimatingAway(PipelineEntry pipelineEntry) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
        if (representativeEntry == null || (expandableNotificationRow = representativeEntry.row) == null) {
            return false;
        }
        return expandableNotificationRow.mHeadsupDisappearRunning;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isNewEnoughForRankingUpdate(NotificationEntry notificationEntry) {
        Long l;
        if (this.mEntriesUpdateTimes.containsKey(notificationEntry.mKey) && (l = this.mEntriesUpdateTimes.get(notificationEntry.mKey)) != null) {
            if (this.mSystemClock.currentTimeMillis() - l.longValue() <= 2000) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isSticky(NotificationEntry notificationEntry) {
        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = ((HeadsUpManagerImpl) this.mHeadsUpManager).getHeadsUpEntry(notificationEntry.mKey);
        if (headsUpEntry != null) {
            return headsUpEntry.isSticky();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onBeforeFinalizeFilter$lambda$16(final HeadsUpCoordinator headsUpCoordinator, final List list, HunMutator hunMutator) {
        Object obj;
        boolean z;
        boolean z2;
        PostedEntry postedEntry;
        boolean z3;
        if (headsUpCoordinator.mPostedEntries.isEmpty()) {
            return Unit.INSTANCE;
        }
        Collection<PostedEntry> values = headsUpCoordinator.mPostedEntries.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : values) {
            String groupKey = ((PostedEntry) obj2).getEntry().mSbn.getGroupKey();
            Object obj3 = linkedHashMap.get(groupKey);
            if (obj3 == null) {
                obj3 = new ArrayList();
                linkedHashMap.put(groupKey, obj3);
            }
            ((List) obj3).add(obj2);
        }
        NotifPipeline notifPipeline = headsUpCoordinator.mNotifPipeline;
        Throwable th = null;
        if (notifPipeline == null) {
            notifPipeline = null;
        }
        FilteringSequence filter = SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(notifPipeline.getAllNotifs()), new HeadsUpCoordinator$$ExternalSyntheticLambda2(linkedHashMap, 1));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(filter);
        while (filteringSequence$iterator$1.hasNext()) {
            Object next = filteringSequence$iterator$1.next();
            String groupKey2 = ((NotificationEntry) next).mSbn.getGroupKey();
            Object obj4 = linkedHashMap2.get(groupKey2);
            if (obj4 == null) {
                obj4 = new ArrayList();
                linkedHashMap2.put(groupKey2, obj4);
            }
            ((List) obj4).add(next);
        }
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Map groupLocationsByKey;
                groupLocationsByKey = HeadsUpCoordinator.this.getGroupLocationsByKey(list);
                return groupLocationsByKey;
            }
        });
        headsUpCoordinator.mLogger.logEvaluatingGroups(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str = (String) entry.getKey();
            List<PostedEntry> list2 = (List) entry.getValue();
            List<NotificationEntry> list3 = (List) linkedHashMap2.get(str);
            if (list3 == null) {
                list3 = EmptyList.INSTANCE;
            }
            Iterator it = list3.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = th;
                    break;
                }
                obj = it.next();
                if (((NotificationEntry) obj).mSbn.getNotification().isGroupSummary()) {
                    break;
                }
            }
            NotificationEntry notificationEntry = (NotificationEntry) obj;
            HeadsUpCoordinatorLogger headsUpCoordinatorLogger = headsUpCoordinator.mLogger;
            str.getClass();
            headsUpCoordinatorLogger.logEvaluatingGroup(str, list2.size(), list3.size());
            if (notificationEntry == null) {
                for (PostedEntry postedEntry2 : list2) {
                    postedEntry2.getClass();
                    headsUpCoordinator.handlePostedEntry(postedEntry2, hunMutator, "logical-summary-missing");
                }
            } else if (headsUpCoordinator.isGoingToShowHunStrict(notificationEntry)) {
                NotificationEntry findHeadsUpOverride = headsUpCoordinator.findHeadsUpOverride(list2, new HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3(onBeforeFinalizeFilter$lambda$16$lambda$6(lazy)));
                String str2 = findHeadsUpOverride != null ? "headsUpOverride" : "undefined";
                Map<String, GroupLocation> onBeforeFinalizeFilter$lambda$16$lambda$6 = onBeforeFinalizeFilter$lambda$16$lambda$6(lazy);
                String str3 = notificationEntry.mKey;
                boolean containsKey = onBeforeFinalizeFilter$lambda$16$lambda$6.containsKey(str3);
                if (!containsKey && findHeadsUpOverride == null && (findHeadsUpOverride = headsUpCoordinator.findBestTransferChild(list3, new HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4(onBeforeFinalizeFilter$lambda$16$lambda$6(lazy)))) != null) {
                    str2 = "bestChild";
                }
                if (findHeadsUpOverride == null) {
                    for (PostedEntry postedEntry3 : list2) {
                        postedEntry3.getClass();
                        headsUpCoordinator.handlePostedEntry(postedEntry3, hunMutator, "no-transfer-target");
                    }
                } else if (headsUpCoordinator.isDisqualifiedChild(findHeadsUpOverride)) {
                    VisualInterruptionDecisionLogger visualInterruptionDecisionLogger = headsUpCoordinator.mInterruptLogger;
                    VisualInterruptionDecisionProviderImpl.DecisionImpl decisionImpl = new VisualInterruptionDecisionProviderImpl.DecisionImpl(false, "disqualified-transfer-target");
                    visualInterruptionDecisionLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    VisualInterruptionDecisionLogger$$ExternalSyntheticLambda0 visualInterruptionDecisionLogger$$ExternalSyntheticLambda0 = new VisualInterruptionDecisionLogger$$ExternalSyntheticLambda0(0);
                    LogBuffer logBuffer = visualInterruptionDecisionLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("VisualInterruptionDecisionProvider", logLevel, visualInterruptionDecisionLogger$$ExternalSyntheticLambda0, th);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = "PEEK";
                    logMessageImpl.bool1 = decisionImpl.shouldInterrupt;
                    logMessageImpl.str2 = decisionImpl.logReason;
                    logMessageImpl.str3 = NotificationUtils.logKey(findHeadsUpOverride);
                    logBuffer.commit(obtain);
                    for (PostedEntry postedEntry4 : list2) {
                        postedEntry4.setShouldHeadsUpEver(false);
                        postedEntry4.setShouldHeadsUpAgain(false);
                        headsUpCoordinator.handlePostedEntry(postedEntry4, hunMutator, "disqualified-transfer-target");
                    }
                } else {
                    PostedEntry postedEntry5 = headsUpCoordinator.mPostedEntries.get(str3);
                    notificationEntry.interruption = true;
                    HeadsUpCoordinatorLogger headsUpCoordinatorLogger2 = headsUpCoordinator.mLogger;
                    String str4 = findHeadsUpOverride.mKey;
                    headsUpCoordinatorLogger2.logSummaryMarkedInterrupted(str3, str4);
                    if (containsKey) {
                        z = false;
                        z2 = true;
                        if (postedEntry5 != null) {
                            headsUpCoordinator.mLogger.logPostedEntryWillNotEvaluate(postedEntry5, "attached-summary-transferred");
                        }
                    } else {
                        if (postedEntry5 != null) {
                            postedEntry5.setShouldHeadsUpEver(false);
                            postedEntry = postedEntry5;
                            z = false;
                            z3 = true;
                        } else {
                            z = false;
                            z3 = true;
                            postedEntry = new PostedEntry(notificationEntry, false, false, false, false, false, ((HeadsUpManagerImpl) headsUpCoordinator.mHeadsUpManager).isHeadsUpEntry(str3), headsUpCoordinator.isEntryBinding(notificationEntry), 32, null);
                        }
                        headsUpCoordinator.handlePostedEntry(postedEntry, hunMutator, "detached-summary-remove-heads-up");
                        z2 = z3;
                    }
                    FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list2), new HeadsUpCoordinator$$ExternalSyntheticLambda2(notificationEntry, 2)));
                    boolean z4 = z;
                    while (filteringSequence$iterator$12.hasNext()) {
                        PostedEntry postedEntry6 = (PostedEntry) filteringSequence$iterator$12.next();
                        if (Intrinsics.areEqual(str4, postedEntry6.getKey())) {
                            postedEntry6.setShouldHeadsUpEver(z2);
                            postedEntry6.setShouldHeadsUpAgain(z2);
                            headsUpCoordinator.handlePostedEntry(postedEntry6, hunMutator, "child-heads-up-transfer-target-" + ((Object) str2));
                            z4 = z2;
                        } else {
                            headsUpCoordinator.handlePostedEntry(postedEntry6, hunMutator, "child-heads-up-non-target");
                        }
                    }
                    if (!z4) {
                        headsUpCoordinator.handlePostedEntry(new PostedEntry(findHeadsUpOverride, false, false, true, true, false, ((HeadsUpManagerImpl) headsUpCoordinator.mHeadsUpManager).isHeadsUpEntry(str4), headsUpCoordinator.isEntryBinding(findHeadsUpOverride), 32, null), hunMutator, "non-posted-child-heads-up-transfer-target-" + ((Object) str2));
                    }
                }
            } else {
                for (PostedEntry postedEntry7 : list2) {
                    postedEntry7.getClass();
                    headsUpCoordinator.handlePostedEntry(postedEntry7, hunMutator, "logical-summary-not-heads-up");
                }
            }
            th = null;
        }
        headsUpCoordinator.mPostedEntries.clear();
        headsUpCoordinator.cleanUpEntryTimes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBeforeFinalizeFilter$lambda$16$lambda$15$lambda$13(NotificationEntry notificationEntry, PostedEntry postedEntry) {
        return !Intrinsics.areEqual(postedEntry.getKey(), notificationEntry.mKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBeforeFinalizeFilter$lambda$16$lambda$3(Map map, NotificationEntry notificationEntry) {
        return map.containsKey(notificationEntry.mSbn.getGroupKey());
    }

    private static final Map<String, GroupLocation> onBeforeFinalizeFilter$lambda$16$lambda$6(Lazy lazy) {
        return (Map) lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onBeforeTransformGroups$lambda$1(HeadsUpCoordinator headsUpCoordinator, HunMutator hunMutator) {
        for (PostedEntry postedEntry : CollectionsKt___CollectionsKt.toList(headsUpCoordinator.mPostedEntries.values())) {
            if (!postedEntry.getEntry().mSbn.isGroup()) {
                headsUpCoordinator.handlePostedEntry(postedEntry, hunMutator, "non-group");
                headsUpCoordinator.mPostedEntries.remove(postedEntry.getKey());
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onHeadsUpViewBound(NotificationEntry notificationEntry, boolean z) {
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.mHeadsUpManager;
        HeadsUpManagerImpl.HeadsUpEntry createHeadsUpEntry = headsUpManagerImpl.createHeadsUpEntry(notificationEntry);
        HeadsUpManagerLogger headsUpManagerLogger = headsUpManagerImpl.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        PinnedStatus pinnedStatus = z ? PinnedStatus.PinnedByUser : PinnedStatus.PinnedBySystem;
        createHeadsUpEntry.getClass();
        int i = StatusBarNotifChips.$r8$clinit;
        if (pinnedStatus == PinnedStatus.PinnedByUser) {
            Log.w("BaseHeadsUpManager", "PinnedByUser status not allowed if StatusBarNotifChips is disabled");
            PinnedStatus pinnedStatus2 = PinnedStatus.NotPinned;
        }
        headsUpManagerImpl.mAvalancheController.update(createHeadsUpEntry, new HeadsUpManagerImpl$$ExternalSyntheticLambda1(headsUpManagerImpl, notificationEntry, z, createHeadsUpEntry, pinnedStatus), "showNotification");
        this.mEntriesBindingUntil.remove(notificationEntry.mKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPromotedNotificationChipTapEvent(String str) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarNotifChips.$r8$clinit;
        throw new IllegalStateException("New code path not supported when android.app.ui_rich_ongoing is disabled.");
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
        ((HeadsUpManagerImpl) this.mHeadsUpManager).addListener(this.mOnHeadsUpChangedListener);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        OnBeforeTransformGroupsListener onBeforeTransformGroupsListener = new OnBeforeTransformGroupsListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener
            public final void onBeforeTransformGroups(List<PipelineEntry> list) {
                HeadsUpCoordinator.this.onBeforeTransformGroups();
            }
        };
        ShadeListBuilder shadeListBuilder = notifPipeline.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnBeforeTransformGroupsListeners.addIfAbsent(onBeforeTransformGroupsListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends PipelineEntry> list) {
                HeadsUpCoordinator.this.onBeforeFinalizeFilter(list);
            }
        });
        notifPipeline.addPromoter(this.mNotifPromoter);
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        Consumer<NotificationEntry> consumer = this.mActionPressListener;
        notificationRemoteInputManager.getClass();
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        notificationRemoteInputManager.mActionPressListeners.addIfAbsent(consumer);
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        ((ArrayList) ((HeadsUpManagerImpl) headsUpManager).mCallbacks).add(new HeadsUpCoordinator$attach$4(this));
    }

    public final void bindForAsyncHeadsUp(NotificationEntry notificationEntry) {
        if (notificationEntry != null) {
            LinkedHashMap<String, PostedEntry> linkedHashMap = this.mPostedEntries;
            PostedEntry postedEntry = new PostedEntry(notificationEntry, false, false, true, true, false, false, false, 32, null);
            String str = notificationEntry.mKey;
            linkedHashMap.put(str, postedEntry);
            notificationEntry.mIsHeadsUpByBriefExpanding = true;
            PostedEntry postedEntry2 = this.mPostedEntries.get(str);
            if (postedEntry2 != null) {
                bindForAsyncHeadsUp(postedEntry2);
            }
        }
    }

    public final NotifSectioner getSectioner() {
        return this.sectioner;
    }

    public final void onBeforeFinalizeFilter(final List<? extends PipelineEntry> list) {
        HeadsUpCoordinatorKt.modifyHuns(this.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Unit onBeforeFinalizeFilter$lambda$16;
                onBeforeFinalizeFilter$lambda$16 = HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$16(HeadsUpCoordinator.this, list, (HunMutator) obj);
                return onBeforeFinalizeFilter$lambda$16;
            }
        });
    }

    public final void onBeforeTransformGroups() {
        this.mNow = this.mSystemClock.currentTimeMillis();
        if (this.mPostedEntries.isEmpty()) {
            return;
        }
        HeadsUpCoordinatorKt.modifyHuns(this.mHeadsUpManager, new HeadsUpCoordinator$$ExternalSyntheticLambda5(this, 0));
    }

    public final void setUpdateTime(NotificationEntry notificationEntry, long j) {
        this.mEntriesUpdateTimes.put(notificationEntry.mKey, Long.valueOf(j));
    }

    private final void bindForAsyncHeadsUp(PostedEntry postedEntry) {
        this.mEntriesBindingUntil.put(postedEntry.getKey(), Long.valueOf(this.mNow + 1000));
        final HeadsUpViewBinder headsUpViewBinder = this.mHeadsUpViewBinder;
        final NotificationEntry entry = postedEntry.getEntry();
        final HeadsUpViewBinder.HeadsUpBindCallback headsUpBindCallback = new HeadsUpViewBinder.HeadsUpBindCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$bindForAsyncHeadsUp$2
            @Override // com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder.HeadsUpBindCallback
            public final void onHeadsUpBindFinished(NotificationEntry notificationEntry, boolean z) {
                HeadsUpCoordinator.this.onHeadsUpViewBound(notificationEntry, z);
            }
        };
        RowContentBindStage rowContentBindStage = headsUpViewBinder.mStage;
        ((RowContentBindParams) rowContentBindStage.getStageParams(entry)).requireContentViews(4);
        CancellationSignal requestRebind = rowContentBindStage.requestRebind(entry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry) {
                HeadsUpViewBinder headsUpViewBinder2 = HeadsUpViewBinder.this;
                HeadsUpViewBinderLogger headsUpViewBinderLogger = headsUpViewBinder2.mLogger;
                headsUpViewBinderLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 headsUpViewBinderLogger$$ExternalSyntheticLambda0 = new HeadsUpViewBinderLogger$$ExternalSyntheticLambda0(4);
                LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$$ExternalSyntheticLambda0, null);
                NotificationEntry notificationEntry2 = entry;
                ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logBuffer.commit(obtain);
                ((ArrayMap) headsUpViewBinder2.mOngoingBindCallbacks).remove(notificationEntry2);
                headsUpBindCallback.onHeadsUpBindFinished(notificationEntry, false);
            }
        });
        headsUpViewBinder.abortBindCallback(entry);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = headsUpViewBinder.mLogger;
        headsUpViewBinderLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 headsUpViewBinderLogger$$ExternalSyntheticLambda0 = new HeadsUpViewBinderLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(entry);
        logMessageImpl.bool1 = false;
        logBuffer.commit(obtain);
        ((ArrayMap) headsUpViewBinder.mOngoingBindCallbacks).put(entry, requestRebind);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PostedEntry {
        public static final int $stable = 8;
        private final NotificationEntry entry;
        private boolean isBinding;
        private boolean isHeadsUpByBriefExpanding;
        private boolean isHeadsUpEntry;
        private boolean isPinnedByUser;
        private final String key;
        private boolean shouldHeadsUpAgain;
        private boolean shouldHeadsUpEver;
        private final boolean wasAdded;
        private boolean wasUpdated;

        public PostedEntry(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            this.entry = notificationEntry;
            this.wasAdded = z;
            this.wasUpdated = z2;
            this.shouldHeadsUpEver = z3;
            this.shouldHeadsUpAgain = z4;
            this.isPinnedByUser = z5;
            this.isHeadsUpEntry = z6;
            this.isBinding = z7;
            this.key = notificationEntry.mKey;
        }

        public static /* synthetic */ PostedEntry copy$default(PostedEntry postedEntry, NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, Object obj) {
            if ((i & 1) != 0) {
                notificationEntry = postedEntry.entry;
            }
            if ((i & 2) != 0) {
                z = postedEntry.wasAdded;
            }
            if ((i & 4) != 0) {
                z2 = postedEntry.wasUpdated;
            }
            if ((i & 8) != 0) {
                z3 = postedEntry.shouldHeadsUpEver;
            }
            if ((i & 16) != 0) {
                z4 = postedEntry.shouldHeadsUpAgain;
            }
            if ((i & 32) != 0) {
                z5 = postedEntry.isPinnedByUser;
            }
            if ((i & 64) != 0) {
                z6 = postedEntry.isHeadsUpEntry;
            }
            if ((i & 128) != 0) {
                z7 = postedEntry.isBinding;
            }
            boolean z8 = z6;
            boolean z9 = z7;
            boolean z10 = z4;
            boolean z11 = z5;
            return postedEntry.copy(notificationEntry, z, z2, z3, z10, z11, z8, z9);
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
            return this.isPinnedByUser;
        }

        public final boolean component7() {
            return this.isHeadsUpEntry;
        }

        public final boolean component8() {
            return this.isBinding;
        }

        public final PostedEntry copy(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            return new PostedEntry(notificationEntry, z, z2, z3, z4, z5, z6, z7);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostedEntry)) {
                return false;
            }
            PostedEntry postedEntry = (PostedEntry) obj;
            return Intrinsics.areEqual(this.entry, postedEntry.entry) && this.wasAdded == postedEntry.wasAdded && this.wasUpdated == postedEntry.wasUpdated && this.shouldHeadsUpEver == postedEntry.shouldHeadsUpEver && this.shouldHeadsUpAgain == postedEntry.shouldHeadsUpAgain && this.isPinnedByUser == postedEntry.isPinnedByUser && this.isHeadsUpEntry == postedEntry.isHeadsUpEntry && this.isBinding == postedEntry.isBinding;
        }

        public final boolean getCalculateShouldBeHeadsUpNoRetract() {
            if (isHeadsUpAlready()) {
                return true;
            }
            if (this.shouldHeadsUpEver) {
                return this.wasAdded || this.shouldHeadsUpAgain;
            }
            return false;
        }

        public final boolean getCalculateShouldBeHeadsUpStrict() {
            if (this.shouldHeadsUpEver) {
                return this.wasAdded || this.shouldHeadsUpAgain || isHeadsUpAlready();
            }
            return false;
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
            return Boolean.hashCode(this.isBinding) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.entry.hashCode() * 31, 31, this.wasAdded), 31, this.wasUpdated), 31, this.shouldHeadsUpEver), 31, this.shouldHeadsUpAgain), 31, this.isPinnedByUser), 31, this.isHeadsUpEntry);
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

        public final boolean isPinnedByUser() {
            return this.isPinnedByUser;
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

        public final void setPinnedByUser(boolean z) {
            this.isPinnedByUser = z;
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
            boolean z5 = this.isPinnedByUser;
            boolean z6 = this.isHeadsUpEntry;
            boolean z7 = this.isBinding;
            StringBuilder sb = new StringBuilder("PostedEntry(entry=");
            sb.append(notificationEntry);
            sb.append(", wasAdded=");
            sb.append(z);
            sb.append(", wasUpdated=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z2, ", shouldHeadsUpEver=", z3, ", shouldHeadsUpAgain=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z4, ", isPinnedByUser=", z5, ", isHeadsUpEntry=");
            sb.append(z6);
            sb.append(", isBinding=");
            sb.append(z7);
            sb.append(")");
            return sb.toString();
        }

        public /* synthetic */ PostedEntry(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(notificationEntry, z, z2, z3, z4, (i & 32) != 0 ? false : z5, z6, z7);
        }

        public static /* synthetic */ void isHeadsUpByBriefExpanding$annotations() {
        }
    }
}
