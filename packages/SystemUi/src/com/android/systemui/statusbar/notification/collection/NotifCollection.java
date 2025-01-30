package com.android.systemui.statusbar.notification.collection;

import android.app.NotificationChannel;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Pair;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleEntry;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.notifcollection.BindEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.ChannelChangedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.CleanUpEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryAddedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryRemovedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.InitEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLoggerKt;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingAppliedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$4$$ExternalSyntheticLambda0;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifCollection implements Dumpable, PipelineDumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAmDispatchingToOtherCode;
    public boolean mAttached;
    public final Executor mBgExecutor;
    public ShadeListBuilder.C27931 mBuildListener;
    public final SystemClock mClock;
    public final List mDismissInterceptors;
    public final NotificationDismissibilityProvider mDismissibilityProvider;
    public final DumpManager mDumpManager;
    public final LogBufferEulogizer mEulogizer;
    public final Queue mEventQueue;
    public final HashMap mFutureDismissals;
    public final NotifCollectionInconsistencyTracker mInconsistencyTracker;
    public final List mLifetimeExtenders;
    public final NotifCollectionLogger mLogger;
    public final Handler mMainHandler;
    public final List mNotifCollectionListeners;
    public final C27901 mNotifHandler;
    public final Map mNotificationSet;
    public final Collection mReadOnlyNotificationSet;
    public final NotifCollection$$ExternalSyntheticLambda0 mRebuildListRunnable;
    public final IStatusBarService mStatusBarService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.collection.NotifCollection$1 */
    public final class C27901 implements NotificationListener.NotificationHandler {
        public C27901() {
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            int i2 = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = NotifCollection.this;
            notifCollection.getClass();
            Assert.isMainThread();
            ((ArrayDeque) notifCollection.mEventQueue).add(new ChannelChangedEvent(str, userHandle, notificationChannel, i));
            Trace.beginSection("NotifCollection.dispatchEventsAndAsynchronouslyRebuildList");
            notifCollection.dispatchEvents();
            Handler handler = notifCollection.mMainHandler;
            NotifCollection$$ExternalSyntheticLambda0 notifCollection$$ExternalSyntheticLambda0 = notifCollection.mRebuildListRunnable;
            if (!handler.hasCallbacks(notifCollection$$ExternalSyntheticLambda0)) {
                handler.postDelayed(notifCollection$$ExternalSyntheticLambda0, 1000L);
            }
            Trace.endSection();
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            int i = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = NotifCollection.this;
            notifCollection.getClass();
            Assert.isMainThread();
            String key = statusBarNotification.getKey();
            NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
            if (!rankingMap.getRanking(key, ranking)) {
                throw new IllegalArgumentException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Ranking map doesn't contain key: ", key));
            }
            notifCollection.postNotification(statusBarNotification, ranking);
            notifCollection.applyRanking(rankingMap);
            notifCollection.dispatchEventsAndRebuildList("onNotificationPosted");
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            int i = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = NotifCollection.this;
            notifCollection.getClass();
            Assert.isMainThread();
            ((ArrayDeque) notifCollection.mEventQueue).add(new RankingUpdatedEvent(rankingMap));
            notifCollection.applyRanking(rankingMap);
            notifCollection.dispatchEventsAndRebuildList("onNotificationRankingUpdate");
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            int i2 = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = NotifCollection.this;
            notifCollection.getClass();
            Assert.isMainThread();
            NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
            notifCollectionLogger.logNotifRemoved(statusBarNotification, i);
            NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) notifCollection.mNotificationSet).get(statusBarNotification.getKey());
            if (notificationEntry == null) {
                notifCollectionLogger.logNoNotificationToRemoveWithKey(statusBarNotification, i);
                return;
            }
            notificationEntry.mCancellationReason = i;
            notifCollection.tryRemoveNotification(notificationEntry);
            notifCollection.applyRanking(rankingMap);
            notifCollection.dispatchEventsAndRebuildList("onNotificationRemoved");
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationsInitialized() {
            ((SystemClockImpl) NotifCollection.this.mClock).getClass();
            android.os.SystemClock.uptimeMillis();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DismissedByUserStatsCreator {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FutureDismissal implements Runnable {
        public boolean mDidRun;
        public boolean mDidSystemServerCancel;
        public final NotificationEntry mEntry;
        public final String mLabel;
        public final DismissedByUserStatsCreator mStatsCreator;
        public final NotificationEntry mSummaryToDismiss;

        public /* synthetic */ FutureDismissal(NotifCollection notifCollection, NotificationEntry notificationEntry, int i, OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0 onUserInteractionCallbackImpl$$ExternalSyntheticLambda0) {
            this(notificationEntry, i, (DismissedByUserStatsCreator) onUserInteractionCallbackImpl$$ExternalSyntheticLambda0);
        }

        @Override // java.lang.Runnable
        public final void run() {
            Assert.isMainThread();
            if (this.mDidRun) {
                NotifCollection.this.mLogger.logFutureDismissalDoubleRun(this);
                return;
            }
            this.mDidRun = true;
            NotifCollection.this.mFutureDismissals.remove(this.mEntry.mKey);
            NotificationEntry entry = NotifCollection.this.getEntry(this.mEntry.mKey);
            DismissedByUserStats createDismissedByUserStats = ((OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0) this.mStatsCreator).createDismissedByUserStats(this.mEntry);
            NotificationEntry notificationEntry = this.mSummaryToDismiss;
            if (notificationEntry != null) {
                NotificationEntry entry2 = NotifCollection.this.getEntry(notificationEntry.mKey);
                if (entry2 == this.mSummaryToDismiss) {
                    NotifCollection.this.mLogger.logFutureDismissalDismissing(this, UniversalCredentialUtil.AGENT_SUMMARY);
                    NotifCollection notifCollection = NotifCollection.this;
                    NotificationEntry notificationEntry2 = this.mSummaryToDismiss;
                    notifCollection.dismissNotification(notificationEntry2, ((OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0) this.mStatsCreator).createDismissedByUserStats(notificationEntry2));
                } else {
                    NotifCollection.this.mLogger.logFutureDismissalMismatchedEntry(this, UniversalCredentialUtil.AGENT_SUMMARY, entry2);
                }
            }
            if (this.mDidSystemServerCancel) {
                NotifCollection.this.mLogger.logFutureDismissalAlreadyCancelledByServer(this);
            } else if (entry != this.mEntry) {
                NotifCollection.this.mLogger.logFutureDismissalMismatchedEntry(this, "entry", entry);
            } else {
                NotifCollection.this.mLogger.logFutureDismissalDismissing(this, "entry");
                NotifCollection.this.dismissNotification(this.mEntry, createDismissedByUserStats);
            }
        }

        private FutureDismissal(NotificationEntry notificationEntry, int i, DismissedByUserStatsCreator dismissedByUserStatsCreator) {
            this.mEntry = notificationEntry;
            this.mStatsCreator = dismissedByUserStatsCreator;
            NotifCollection.this.getClass();
            final String groupKey = notificationEntry.mSbn.getGroupKey();
            ArrayMap arrayMap = (ArrayMap) NotifCollection.this.mNotificationSet;
            final int i2 = 1;
            final int i3 = 0;
            if (arrayMap.get(notificationEntry.mKey) == notificationEntry && arrayMap.values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    switch (i3) {
                        case 0:
                            return Objects.equals(((NotificationEntry) obj).mSbn.getGroupKey(), groupKey);
                        default:
                            return Objects.equals(((NotificationEntry) obj).mSbn.getGroupKey(), groupKey);
                    }
                }
            }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    switch (i3) {
                        case 0:
                            return !((NotificationEntry) obj).mSbn.getNotification().isGroupSummary();
                        default:
                            return ((NotificationEntry) obj).mSbn.getNotification().isGroupSummary();
                    }
                }
            }).count() == 1) {
                i3 = 1;
            }
            NotificationEntry notificationEntry2 = null;
            if (i3 != 0) {
                final String groupKey2 = notificationEntry.mSbn.getGroupKey();
                NotificationEntry notificationEntry3 = (NotificationEntry) ((ArrayMap) NotifCollection.this.mNotificationSet).values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i2) {
                            case 0:
                                return Objects.equals(((NotificationEntry) obj).mSbn.getGroupKey(), groupKey2);
                            default:
                                return Objects.equals(((NotificationEntry) obj).mSbn.getGroupKey(), groupKey2);
                        }
                    }
                }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i2) {
                            case 0:
                                return !((NotificationEntry) obj).mSbn.getNotification().isGroupSummary();
                            default:
                                return ((NotificationEntry) obj).mSbn.getNotification().isGroupSummary();
                        }
                    }
                }).findFirst().orElse(null);
                if (notificationEntry3 != null && ((NotificationDismissibilityProviderImpl) NotifCollection.this.mDismissibilityProvider).isDismissable(notificationEntry3)) {
                    notificationEntry2 = notificationEntry3;
                }
            }
            this.mSummaryToDismiss = notificationEntry2;
            this.mLabel = "<FutureDismissal@" + Integer.toHexString(hashCode()) + " entry=" + NotificationUtils.logKey(notificationEntry) + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(i) + " summary=" + NotificationUtils.logKey(notificationEntry2) + ">";
        }
    }

    static {
        TimeUnit.SECONDS.toMillis(5L);
    }

    /* JADX WARN: Type inference failed for: r3v9, types: [com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0] */
    public NotifCollection(IStatusBarService iStatusBarService, SystemClock systemClock, NotifPipelineFlags notifPipelineFlags, NotifCollectionLogger notifCollectionLogger, Handler handler, Executor executor, LogBufferEulogizer logBufferEulogizer, DumpManager dumpManager, NotificationDismissibilityProvider notificationDismissibilityProvider) {
        ArrayMap arrayMap = new ArrayMap();
        this.mNotificationSet = arrayMap;
        this.mReadOnlyNotificationSet = Collections.unmodifiableCollection(arrayMap.values());
        this.mFutureDismissals = new HashMap();
        this.mNotifCollectionListeners = new ArrayList();
        this.mLifetimeExtenders = new ArrayList();
        this.mDismissInterceptors = new ArrayList();
        this.mEventQueue = new ArrayDeque();
        this.mRebuildListRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotifCollection notifCollection = NotifCollection.this;
                ShadeListBuilder.C27931 c27931 = notifCollection.mBuildListener;
                if (c27931 != null) {
                    Assert.isMainThread();
                    ArrayList arrayList = new ArrayList(notifCollection.mReadOnlyNotificationSet);
                    ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
                    shadeListBuilder.mPendingEntries = arrayList;
                    shadeListBuilder.mLogger.logOnBuildList("asynchronousUpdate");
                    shadeListBuilder.rebuildListIfBefore(1);
                }
            }
        };
        this.mAttached = false;
        this.mNotifHandler = new C27901();
        this.mStatusBarService = iStatusBarService;
        this.mClock = systemClock;
        this.mLogger = notifCollectionLogger;
        this.mMainHandler = handler;
        this.mBgExecutor = executor;
        this.mEulogizer = logBufferEulogizer;
        this.mDumpManager = dumpManager;
        this.mInconsistencyTracker = new NotifCollectionInconsistencyTracker(notifCollectionLogger);
        this.mDismissibilityProvider = notificationDismissibilityProvider;
    }

    public static boolean hasFlag(NotificationEntry notificationEntry, int i) {
        return (notificationEntry.mSbn.getNotification().flags & i) != 0;
    }

    public static boolean shouldAutoDismissChildren(NotificationEntry notificationEntry, String str) {
        return (!notificationEntry.mSbn.getGroupKey().equals(str) || notificationEntry.mSbn.getNotification().isGroupSummary() || hasFlag(notificationEntry, 2) || hasFlag(notificationEntry, 4096) || hasFlag(notificationEntry, 32) || (notificationEntry.getChannel() != null && notificationEntry.getChannel().isImportantConversation()) || notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED || notificationEntry.getChannel().isImportantConversation()) ? false : true;
    }

    public static boolean userIdMatches(NotificationEntry notificationEntry, int i) {
        return i == -1 || notificationEntry.mSbn.getUser().getIdentifier() == -1 || notificationEntry.mSbn.getUser().getIdentifier() == i;
    }

    public final void applyRanking(NotificationListenerService.RankingMap rankingMap) {
        ArrayMap arrayMap = null;
        for (NotificationEntry notificationEntry : ((ArrayMap) this.mNotificationSet).values()) {
            if (!notificationEntry.isCanceled()) {
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                String str = notificationEntry.mKey;
                if (rankingMap.getRanking(str, ranking)) {
                    notificationEntry.setRanking(ranking);
                    String overrideGroupKey = ranking.getOverrideGroupKey();
                    if (!Objects.equals(notificationEntry.mSbn.getOverrideGroupKey(), overrideGroupKey)) {
                        notificationEntry.mSbn.setOverrideGroupKey(overrideGroupKey);
                    }
                } else {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                    }
                    arrayMap.put(str, notificationEntry);
                }
            }
        }
        NotifCollectionInconsistencyTracker notifCollectionInconsistencyTracker = this.mInconsistencyTracker;
        notifCollectionInconsistencyTracker.logNewMissingNotifications(rankingMap);
        notifCollectionInconsistencyTracker.maybeLogInconsistentRankings(notifCollectionInconsistencyTracker.notificationsWithoutRankings, arrayMap != null ? arrayMap : MapsKt__MapsKt.emptyMap(), rankingMap);
        Set keySet = arrayMap != null ? arrayMap.keySet() : null;
        if (keySet == null) {
            keySet = EmptySet.INSTANCE;
        }
        notifCollectionInconsistencyTracker.notificationsWithoutRankings = keySet;
        if (arrayMap != null) {
            for (NotificationEntry notificationEntry2 : arrayMap.values()) {
                notificationEntry2.mCancellationReason = 0;
                tryRemoveNotification(notificationEntry2);
            }
        }
        ((ArrayDeque) this.mEventQueue).add(new RankingAppliedEvent());
    }

    public final void cancelDismissInterception(NotificationEntry notificationEntry) {
        this.mAmDispatchingToOtherCode = true;
        Iterator it = ((ArrayList) notificationEntry.mDismissInterceptors).iterator();
        while (it.hasNext()) {
            ((HashSet) BubbleCoordinator.this.mInterceptedDismissalEntries).remove(notificationEntry.mKey);
        }
        this.mAmDispatchingToOtherCode = false;
        ((ArrayList) notificationEntry.mDismissInterceptors).clear();
    }

    public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        this.mAmDispatchingToOtherCode = true;
        Iterator it = ((ArrayList) notificationEntry.mLifetimeExtenders).iterator();
        while (it.hasNext()) {
            ((NotifLifetimeExtender) it.next()).cancelLifetimeExtension(notificationEntry);
        }
        this.mAmDispatchingToOtherCode = false;
        ((ArrayList) notificationEntry.mLifetimeExtenders).clear();
    }

    public final void checkForReentrantCall() {
        if (this.mAmDispatchingToOtherCode) {
            IllegalStateException illegalStateException = new IllegalStateException("Reentrant call detected");
            this.mEulogizer.record(illegalStateException);
            throw illegalStateException;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0089 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dismissAllNotifications(int i, boolean z) {
        boolean z2;
        Assert.isMainThread();
        checkForReentrantCall();
        NotifCollectionLogger notifCollectionLogger = this.mLogger;
        notifCollectionLogger.logDismissAll(i);
        try {
            this.mStatusBarService.onClearAllNotifications(i);
        } catch (RemoteException e) {
            notifCollectionLogger.logRemoteExceptionOnClearAllNotifications(e);
        }
        Assert.isMainThread();
        ArrayList arrayList = new ArrayList(this.mReadOnlyNotificationSet);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(size);
            if (!(userIdMatches(notificationEntry, i) && notificationEntry.isClearable() && !hasFlag(notificationEntry, 4096) && notificationEntry.mDismissState != NotificationEntry.DismissState.DISMISSED)) {
                if (z) {
                    if (userIdMatches(notificationEntry, i)) {
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        if ((expandableNotificationRow == null ? true : expandableNotificationRow.canViewBeDismissed$1()) && !hasFlag(notificationEntry, 4096) && notificationEntry.mDismissState != NotificationEntry.DismissState.DISMISSED) {
                            z2 = true;
                            if (z2) {
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
                updateDismissInterceptors(notificationEntry);
                if (((ArrayList) notificationEntry.mDismissInterceptors).size() > 0) {
                    notifCollectionLogger.logNotifClearAllDismissalIntercepted(notificationEntry);
                }
                arrayList.remove(size);
            }
        }
        locallyDismissNotifications(arrayList);
        dispatchEventsAndRebuildList("dismissAllNotifications");
    }

    public final void dismissNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats) {
        dismissNotifications(List.of(new Pair(notificationEntry, dismissedByUserStats)));
    }

    public final void dismissNotifications(List list) {
        Assert.isMainThread();
        checkForReentrantCall();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            final NotificationEntry notificationEntry = (NotificationEntry) ((Pair) list.get(i)).first;
            final DismissedByUserStats dismissedByUserStats = (DismissedByUserStats) ((Pair) list.get(i)).second;
            Objects.requireNonNull(dismissedByUserStats);
            NotificationEntry notificationEntry2 = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(notificationEntry.mKey);
            NotifCollectionLogger notifCollectionLogger = this.mLogger;
            if (notificationEntry2 == null) {
                notifCollectionLogger.logNonExistentNotifDismissed(notificationEntry);
            } else {
                String str = notificationEntry.mKey;
                if (notificationEntry != notificationEntry2) {
                    if (!str.equals(notificationEntry2.mKey)) {
                        IllegalStateException illegalStateException = new IllegalStateException("Invalid entry: different stored and dismissed entries for " + NotificationUtils.logKey(notificationEntry) + " stored=@" + Integer.toHexString(notificationEntry2.hashCode()));
                        this.mEulogizer.record(illegalStateException);
                        throw illegalStateException;
                    }
                    notifCollectionLogger.logAlreadyDismissedNotification(str);
                } else if (notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED) {
                    notifCollectionLogger.logNotifDismissState(str);
                } else {
                    updateDismissInterceptors(notificationEntry);
                    if (((ArrayList) notificationEntry.mDismissInterceptors).size() > 0) {
                        notifCollectionLogger.logNotifDismissedIntercepted(notificationEntry);
                    } else {
                        arrayList.add(notificationEntry);
                        if (!notificationEntry.isCanceled()) {
                            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotifCollection notifCollection = NotifCollection.this;
                                    NotificationEntry notificationEntry3 = notificationEntry;
                                    DismissedByUserStats dismissedByUserStats2 = dismissedByUserStats;
                                    notifCollection.getClass();
                                    try {
                                        notifCollection.mStatusBarService.onNotificationClear(notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier(), notificationEntry3.mSbn.getKey(), dismissedByUserStats2.dismissalSurface, dismissedByUserStats2.dismissalSentiment, dismissedByUserStats2.notificationVisibility);
                                    } catch (RemoteException e) {
                                        notifCollection.mLogger.logRemoteExceptionOnNotificationClear(notificationEntry3, e);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
        locallyDismissNotifications(arrayList);
        dispatchEventsAndRebuildList("dismissNotifications");
    }

    public final void dispatchEvents() {
        Trace.beginSection("NotifCollection.dispatchEvents");
        this.mAmDispatchingToOtherCode = true;
        while (true) {
            Queue queue = this.mEventQueue;
            if (((ArrayDeque) queue).isEmpty()) {
                this.mAmDispatchingToOtherCode = false;
                Trace.endSection();
                return;
            }
            NotifEvent notifEvent = (NotifEvent) ((ArrayDeque) queue).remove();
            List list = this.mNotifCollectionListeners;
            notifEvent.getClass();
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                notifEvent.dispatchToListener((NotifCollectionListener) arrayList.get(i));
            }
        }
    }

    public final void dispatchEventsAndRebuildList(String str) {
        Trace.beginSection("NotifCollection.dispatchEventsAndRebuildList");
        Handler handler = this.mMainHandler;
        NotifCollection$$ExternalSyntheticLambda0 notifCollection$$ExternalSyntheticLambda0 = this.mRebuildListRunnable;
        if (handler.hasCallbacks(notifCollection$$ExternalSyntheticLambda0)) {
            handler.removeCallbacks(notifCollection$$ExternalSyntheticLambda0);
        }
        dispatchEvents();
        ShadeListBuilder.C27931 c27931 = this.mBuildListener;
        if (c27931 != null) {
            Assert.isMainThread();
            ArrayList arrayList = new ArrayList(this.mReadOnlyNotificationSet);
            ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
            shadeListBuilder.mPendingEntries = arrayList;
            shadeListBuilder.mLogger.logOnBuildList(str);
            shadeListBuilder.rebuildListIfBefore(1);
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Assert.isMainThread();
        ArrayList arrayList = new ArrayList(this.mReadOnlyNotificationSet);
        arrayList.sort(Comparator.comparing(new NotifCollection$$ExternalSyntheticLambda5()));
        printWriter.println("\tNotifCollection unsorted/unfiltered notifications: " + arrayList.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            ListDumper.dumpEntry((ListEntry) arrayList.get(i), Integer.toString(i), "\t\t", sb, false, false);
        }
        printWriter.println(sb.toString());
        NotifCollectionInconsistencyTracker notifCollectionInconsistencyTracker = this.mInconsistencyTracker;
        SideFpsController$$ExternalSyntheticOutline0.m105m("notificationsWithoutRankings: ", notifCollectionInconsistencyTracker.notificationsWithoutRankings.size(), printWriter);
        Iterator it = notifCollectionInconsistencyTracker.notificationsWithoutRankings.iterator();
        while (it.hasNext()) {
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("\t * : ", (String) it.next(), printWriter);
        }
        SideFpsController$$ExternalSyntheticOutline0.m105m("missingNotifications: ", notifCollectionInconsistencyTracker.missingNotifications.size(), printWriter);
        Iterator it2 = notifCollectionInconsistencyTracker.missingNotifications.iterator();
        while (it2.hasNext()) {
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("\t * : ", (String) it2.next(), printWriter);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mNotifCollectionListeners, "notifCollectionListeners");
        pipelineDumper.dump(this.mLifetimeExtenders, "lifetimeExtenders");
        pipelineDumper.dump(this.mDismissInterceptors, "dismissInterceptors");
        pipelineDumper.dump(this.mBuildListener, "buildListener");
    }

    public final NotificationEntry getEntry(String str) {
        return (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(str);
    }

    public final void locallyDismissNotifications(List list) {
        NotifCollectionLogger notifCollectionLogger;
        HashSet hashSet = new HashSet();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            notifCollectionLogger = this.mLogger;
            if (i >= size) {
                break;
            }
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(i);
            notificationEntry.setDismissState(NotificationEntry.DismissState.DISMISSED);
            notifCollectionLogger.logNotifDismissed(notificationEntry);
            boolean isCanceled = notificationEntry.isCanceled();
            String str = notificationEntry.mKey;
            if (isCanceled) {
                notifCollectionLogger.logCanceledNotification(str);
                hashSet.add(notificationEntry);
            } else if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry2 : ((ArrayMap) this.mNotificationSet).values()) {
                    if (shouldAutoDismissChildren(notificationEntry2, notificationEntry.mSbn.getGroupKey())) {
                        notificationEntry2.setDismissState(NotificationEntry.DismissState.PARENT_DISMISSED);
                        notifCollectionLogger.logChildDismissed(notificationEntry2);
                        if (notificationEntry2.isCanceled()) {
                            notifCollectionLogger.logCanceledNotification(str);
                            hashSet.add(notificationEntry2);
                        }
                    }
                }
            }
            i++;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry3 = (NotificationEntry) it.next();
            notifCollectionLogger.logDismissOnAlreadyCanceledEntry(notificationEntry3);
            tryRemoveNotification(notificationEntry3);
        }
    }

    public final void postNotification(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        Map map = this.mNotificationSet;
        NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) map).get(statusBarNotification.getKey());
        NotifCollectionLogger notifCollectionLogger = this.mLogger;
        Queue queue = this.mEventQueue;
        if (notificationEntry == null) {
            ((SystemClockImpl) this.mClock).getClass();
            NotificationEntry notificationEntry2 = new NotificationEntry(statusBarNotification, ranking, android.os.SystemClock.uptimeMillis());
            ((ArrayDeque) queue).add(new InitEntryEvent(notificationEntry2));
            ((ArrayDeque) queue).add(new BindEntryEvent(notificationEntry2, statusBarNotification));
            ((ArrayMap) map).put(statusBarNotification.getKey(), notificationEntry2);
            notifCollectionLogger.logNotifPosted(notificationEntry2);
            ((ArrayDeque) queue).add(new EntryAddedEvent(notificationEntry2));
            return;
        }
        NotificationEntry.DismissState dismissState = notificationEntry.mDismissState;
        NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.NOT_DISMISSED;
        if (dismissState != dismissState2) {
            notificationEntry.setDismissState(dismissState2);
            if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry3 : ((ArrayMap) map).values()) {
                    if (notificationEntry3.mSbn.getGroupKey().equals(notificationEntry.mSbn.getGroupKey()) && notificationEntry3.mDismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                        notificationEntry3.setDismissState(NotificationEntry.DismissState.NOT_DISMISSED);
                    }
                }
            }
        }
        cancelLifetimeExtension(notificationEntry);
        cancelDismissInterception(notificationEntry);
        notificationEntry.mCancellationReason = -1;
        notificationEntry.setSbn(statusBarNotification);
        ((ArrayDeque) queue).add(new BindEntryEvent(notificationEntry, statusBarNotification));
        notifCollectionLogger.logNotifUpdated(notificationEntry);
        ((ArrayDeque) queue).add(new EntryUpdatedEvent(notificationEntry, true));
    }

    public final boolean tryRemoveNotification(NotificationEntry notificationEntry) {
        ArrayMap arrayMap = (ArrayMap) this.mNotificationSet;
        Object obj = arrayMap.get(notificationEntry.mKey);
        LogBufferEulogizer logBufferEulogizer = this.mEulogizer;
        if (obj != notificationEntry) {
            IllegalStateException illegalStateException = new IllegalStateException("No notification to remove with key " + NotificationUtils.logKey(notificationEntry));
            logBufferEulogizer.record(illegalStateException);
            throw illegalStateException;
        }
        if (!notificationEntry.isCanceled()) {
            IllegalStateException illegalStateException2 = new IllegalStateException("Cannot remove notification " + NotificationUtils.logKey(notificationEntry) + ": has not been marked for removal");
            logBufferEulogizer.record(illegalStateException2);
            throw illegalStateException2;
        }
        boolean z = notificationEntry.mDismissState != NotificationEntry.DismissState.NOT_DISMISSED;
        int i = notificationEntry.mCancellationReason;
        boolean z2 = z || (i == 1 || i == 2);
        List list = notificationEntry.mLifetimeExtenders;
        NotifCollectionLogger notifCollectionLogger = this.mLogger;
        if (z2) {
            cancelLifetimeExtension(notificationEntry);
        } else {
            ArrayList arrayList = (ArrayList) list;
            arrayList.clear();
            this.mAmDispatchingToOtherCode = true;
            Iterator it = ((ArrayList) this.mLifetimeExtenders).iterator();
            while (it.hasNext()) {
                NotifLifetimeExtender notifLifetimeExtender = (NotifLifetimeExtender) it.next();
                if (notifLifetimeExtender.maybeExtendLifetime(notificationEntry, notificationEntry.mCancellationReason)) {
                    notifCollectionLogger.logLifetimeExtended(notificationEntry, notifLifetimeExtender);
                    arrayList.add(notifLifetimeExtender);
                }
            }
            this.mAmDispatchingToOtherCode = false;
        }
        if (((ArrayList) list).size() > 0) {
            return false;
        }
        notifCollectionLogger.logNotifReleased(notificationEntry);
        String str = notificationEntry.mKey;
        arrayMap.remove(str);
        cancelDismissInterception(notificationEntry);
        ArrayDeque arrayDeque = (ArrayDeque) this.mEventQueue;
        arrayDeque.add(new EntryRemovedEvent(notificationEntry, notificationEntry.mCancellationReason));
        arrayDeque.add(new CleanUpEntryEvent(notificationEntry));
        FutureDismissal futureDismissal = (FutureDismissal) this.mFutureDismissals.remove(str);
        if (futureDismissal != null) {
            int i2 = notificationEntry.mCancellationReason;
            Assert.isMainThread();
            if (futureDismissal.mDidSystemServerCancel) {
                NotifCollection.this.mLogger.logFutureDismissalDoubleCancelledByServer(futureDismissal);
            } else {
                NotifCollection.this.mLogger.logFutureDismissalGotSystemServerCancel(futureDismissal, i2);
                futureDismissal.mDidSystemServerCancel = true;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0012 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda8] */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda7] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDismissInterceptors(final NotificationEntry notificationEntry) {
        final ArrayList arrayList;
        boolean z;
        ((ArrayList) notificationEntry.mDismissInterceptors).clear();
        this.mAmDispatchingToOtherCode = true;
        Iterator it = ((ArrayList) this.mDismissInterceptors).iterator();
        while (true) {
            boolean z2 = false;
            if (!it.hasNext()) {
                this.mAmDispatchingToOtherCode = false;
                return;
            }
            BubbleCoordinator.C27992 c27992 = (BubbleCoordinator.C27992) it.next();
            BubbleCoordinator bubbleCoordinator = BubbleCoordinator.this;
            boolean isPresent = bubbleCoordinator.mBubblesManagerOptional.isPresent();
            Set set = bubbleCoordinator.mInterceptedDismissalEntries;
            String str = notificationEntry.mKey;
            if (isPresent) {
                final BubblesManager bubblesManager = (BubblesManager) bubbleCoordinator.mBubblesManagerOptional.get();
                bubblesManager.getClass();
                final List attachedNotifChildren = notificationEntry.getAttachedNotifChildren();
                Object obj = null;
                if (attachedNotifChildren != null) {
                    arrayList = new ArrayList();
                    int i = 0;
                    while (true) {
                        ArrayList arrayList2 = (ArrayList) attachedNotifChildren;
                        if (i >= arrayList2.size()) {
                            break;
                        }
                        arrayList.add(bubblesManager.notifToBubbleEntry((NotificationEntry) arrayList2.get(i)));
                        i++;
                    }
                } else {
                    arrayList = null;
                }
                final BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                final ?? r11 = new IntConsumer() { // from class: com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i2) {
                        BubblesManager bubblesManager2 = BubblesManager.this;
                        List list = attachedNotifChildren;
                        NotificationEntry notificationEntry2 = notificationEntry;
                        List list2 = bubblesManager2.mCallbacks;
                        NotificationVisibilityProvider notificationVisibilityProvider = bubblesManager2.mVisibilityProvider;
                        if (i2 < 0) {
                            Iterator it2 = ((ArrayList) list2).iterator();
                            while (it2.hasNext()) {
                                ((BubbleCoordinator.C28003) it2.next()).removeNotification(notificationEntry2, new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) notificationVisibilityProvider).obtain(notificationEntry2)));
                            }
                        } else {
                            Iterator it3 = ((ArrayList) list2).iterator();
                            while (it3.hasNext()) {
                                ((BubbleCoordinator.C28003) it3.next()).removeNotification((NotificationEntry) list.get(i2), new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) notificationVisibilityProvider).obtain((NotificationEntry) list.get(i2))));
                            }
                        }
                    }
                };
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                bubblesImpl.getClass();
                final Executor executor = bubblesManager.mSysuiMainExecutor;
                final ?? r12 = new IntConsumer() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda7
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i2) {
                        executor.execute(new BubbleController$3$$ExternalSyntheticLambda0(i2, 1, r11));
                    }
                };
                try {
                    ShellExecutor shellExecutor = BubbleController.this.mMainExecutor;
                    final ?? r112 = new Supplier() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda8
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                            BubbleEntry bubbleEntry = notifToBubbleEntry;
                            List list = arrayList;
                            IntConsumer intConsumer = r12;
                            bubblesImpl2.getClass();
                            boolean z3 = BubbleController.BUBBLE_BAR_ENABLED;
                            BubbleController bubbleController = BubbleController.this;
                            boolean isSummaryOfBubbles = bubbleController.isSummaryOfBubbles(bubbleEntry);
                            boolean z4 = true;
                            BubbleData bubbleData = bubbleController.mBubbleData;
                            if (isSummaryOfBubbles) {
                                if (list != null) {
                                    for (int i2 = 0; i2 < list.size(); i2++) {
                                        BubbleEntry bubbleEntry2 = (BubbleEntry) list.get(i2);
                                        if (bubbleData.hasAnyBubbleWithKey(bubbleEntry2.getKey())) {
                                            Bubble anyBubbleWithkey = bubbleData.getAnyBubbleWithkey(bubbleEntry2.getKey());
                                            if (anyBubbleWithkey != null) {
                                                anyBubbleWithkey.setSuppressNotification(true);
                                                anyBubbleWithkey.setShowDot(false);
                                            }
                                        } else {
                                            intConsumer.accept(i2);
                                        }
                                    }
                                }
                                intConsumer.accept(-1);
                                String groupKey = bubbleEntry.mSbn.getGroupKey();
                                bubbleData.mSuppressedGroupKeys.put(groupKey, bubbleEntry.getKey());
                                BubbleData.Update update = bubbleData.mStateChange;
                                update.suppressedSummaryChanged = true;
                                update.suppressedSummaryGroup = groupKey;
                                bubbleData.dispatchPendingChanges();
                            } else {
                                Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(bubbleEntry.getKey());
                                if (bubbleInStackWithKey == null || !bubbleEntry.isBubble()) {
                                    bubbleInStackWithKey = bubbleData.getOverflowBubbleWithKey(bubbleEntry.getKey());
                                }
                                if (bubbleInStackWithKey != null) {
                                    bubbleInStackWithKey.setSuppressNotification(true);
                                    bubbleInStackWithKey.setShowDot(false);
                                    BubblesManager.C37394 c37394 = bubbleController.mSysuiProxy;
                                    c37394.getClass();
                                    c37394.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda0(c37394, "BubbleController.handleDismissalInterception", 2));
                                    return Boolean.valueOf(z4);
                                }
                            }
                            z4 = false;
                            return Boolean.valueOf(z4);
                        }
                    };
                    shellExecutor.getClass();
                    final Object[] objArr = (Object[]) Array.newInstance((Class<?>) Boolean.class, 1);
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Object[] objArr2 = objArr;
                            Supplier supplier = r112;
                            CountDownLatch countDownLatch2 = countDownLatch;
                            objArr2[0] = supplier.get();
                            countDownLatch2.countDown();
                        }
                    });
                    try {
                        countDownLatch.await();
                        obj = objArr[0];
                    } catch (InterruptedException unused) {
                    }
                    z = ((Boolean) obj).booleanValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    z = false;
                }
                if (z) {
                    ((HashSet) set).add(str);
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.mLongPressListener = expandableNotificationRow.mLongPressListenerForBubble;
                    }
                    z2 = true;
                    if (!z2) {
                        ((ArrayList) notificationEntry.mDismissInterceptors).add(c27992);
                    }
                }
            }
            ((HashSet) set).remove(str);
            if (!z2) {
            }
        }
    }
}
