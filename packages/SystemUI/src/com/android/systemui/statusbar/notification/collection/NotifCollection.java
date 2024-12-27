package com.android.systemui.statusbar.notification.collection;

import android.app.NotificationChannel;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
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
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingAppliedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.NamedListenerSet;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifCollection implements Dumpable, PipelineDumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAmDispatchingToOtherCode;
    public boolean mAttached;
    public final Executor mBgExecutor;
    public ShadeListBuilder.AnonymousClass1 mBuildListener;
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
    public final NamedListenerSet mNotifCollectionListeners;
    public final AnonymousClass1 mNotifHandler;
    public final Map mNotificationSet;
    public final Collection mReadOnlyNotificationSet;
    public final NotifCollection$$ExternalSyntheticLambda1 mRebuildListRunnable;
    public final IStatusBarService mStatusBarService;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.collection.NotifCollection$1, reason: invalid class name */
    public final class AnonymousClass1 implements NotificationListener.NotificationHandler {
        public AnonymousClass1() {
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
            NotifCollection$$ExternalSyntheticLambda1 notifCollection$$ExternalSyntheticLambda1 = notifCollection.mRebuildListRunnable;
            if (!handler.hasCallbacks(notifCollection$$ExternalSyntheticLambda1)) {
                handler.postDelayed(notifCollection$$ExternalSyntheticLambda1, 1000L);
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
                throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Ranking map doesn't contain key: ", key));
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
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            NotificationLockscreenUserManager notificationLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class);
            ongoingActivityDataHelper.getClass();
            OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
            LinkedList<OngoingActivityData> linkedList = new LinkedList();
            linkedList.addAll(OngoingActivityDataHelper.mOngoingActivityLists);
            linkedList.addAll(OngoingActivityDataHelper.hiddenOngoingActivityDataList);
            for (OngoingActivityData ongoingActivityData : linkedList) {
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityData));
            }
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
            NotifCollection.this.mClock.uptimeMillis();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DismissedByUserStatsCreator {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            NotificationEntry notificationEntry2 = null;
            if (((ArrayMap) NotifCollection.this.mNotificationSet).get(notificationEntry.mKey) == notificationEntry) {
                final int i2 = 0;
                final int i3 = 0;
                if (((ArrayMap) NotifCollection.this.mNotificationSet).values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i4 = i2;
                        String str = groupKey;
                        NotificationEntry notificationEntry3 = (NotificationEntry) obj;
                        switch (i4) {
                        }
                        return Objects.equals(notificationEntry3.mSbn.getGroupKey(), str);
                    }
                }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        NotificationEntry notificationEntry3 = (NotificationEntry) obj;
                        switch (i3) {
                            case 0:
                                return !notificationEntry3.mSbn.getNotification().isGroupSummary();
                            default:
                                return notificationEntry3.mSbn.getNotification().isGroupSummary();
                        }
                    }
                }).count() == 1) {
                    final String groupKey2 = notificationEntry.mSbn.getGroupKey();
                    final int i4 = 1;
                    final int i5 = 1;
                    NotificationEntry notificationEntry3 = (NotificationEntry) ((ArrayMap) NotifCollection.this.mNotificationSet).values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i42 = i4;
                            String str = groupKey2;
                            NotificationEntry notificationEntry32 = (NotificationEntry) obj;
                            switch (i42) {
                            }
                            return Objects.equals(notificationEntry32.mSbn.getGroupKey(), str);
                        }
                    }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda6
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            NotificationEntry notificationEntry32 = (NotificationEntry) obj;
                            switch (i5) {
                                case 0:
                                    return !notificationEntry32.mSbn.getNotification().isGroupSummary();
                                default:
                                    return notificationEntry32.mSbn.getNotification().isGroupSummary();
                            }
                        }
                    }).findFirst().orElse(null);
                    if (notificationEntry3 != null && (!((NotificationDismissibilityProviderImpl) NotifCollection.this.mDismissibilityProvider).nonDismissableEntryKeys.contains(notificationEntry3.mKey))) {
                        notificationEntry2 = notificationEntry3;
                    }
                }
            }
            this.mSummaryToDismiss = notificationEntry2;
            this.mLabel = "<FutureDismissal@" + Integer.toHexString(hashCode()) + " entry=" + NotificationUtils.logKey(notificationEntry) + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(i) + " summary=" + NotificationUtils.logKey(notificationEntry2) + ">";
        }
    }

    static {
        TimeUnit.SECONDS.toMillis(5L);
    }

    /* JADX WARN: Type inference failed for: r3v9, types: [com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda1] */
    public NotifCollection(IStatusBarService iStatusBarService, SystemClock systemClock, NotifPipelineFlags notifPipelineFlags, NotifCollectionLogger notifCollectionLogger, Handler handler, Executor executor, LogBufferEulogizer logBufferEulogizer, DumpManager dumpManager, NotificationDismissibilityProvider notificationDismissibilityProvider) {
        ArrayMap arrayMap = new ArrayMap();
        this.mNotificationSet = arrayMap;
        this.mReadOnlyNotificationSet = Collections.unmodifiableCollection(arrayMap.values());
        this.mFutureDismissals = new HashMap();
        this.mNotifCollectionListeners = new NamedListenerSet();
        this.mLifetimeExtenders = new ArrayList();
        this.mDismissInterceptors = new ArrayList();
        this.mEventQueue = new ArrayDeque();
        this.mRebuildListRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                NotifCollection notifCollection = NotifCollection.this;
                ShadeListBuilder.AnonymousClass1 anonymousClass1 = notifCollection.mBuildListener;
                if (anonymousClass1 != null) {
                    Collection collection = notifCollection.mReadOnlyNotificationSet;
                    Assert.isMainThread();
                    ArrayList arrayList = new ArrayList(collection);
                    ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
                    shadeListBuilder.mPendingEntries = arrayList;
                    shadeListBuilder.mLogger.logOnBuildList("asynchronousUpdate");
                    shadeListBuilder.rebuildListIfBefore(1);
                }
            }
        };
        this.mAttached = false;
        this.mNotifHandler = new AnonymousClass1();
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
        return (!notificationEntry.mSbn.getGroupKey().equals(str) || notificationEntry.mSbn.getNotification().isGroupSummary() || hasFlag(notificationEntry, 2) || hasFlag(notificationEntry, 4096) || hasFlag(notificationEntry, 32) || (notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().isImportantConversation()) || notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED || (notificationEntry.mSbn.getNotification().semFlags & 262144) != 0 || (notificationEntry.mSbn.getNotification().semFlags & 131072) != 0 || notificationEntry.mRanking.getChannel().isImportantConversation()) ? false : true;
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
            ((NotifDismissInterceptor) it.next()).cancelDismissInterception(notificationEntry);
        }
        this.mAmDispatchingToOtherCode = false;
        ((ArrayList) notificationEntry.mDismissInterceptors).clear();
    }

    public final void cancelLifetimeExtension$1(NotificationEntry notificationEntry) {
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

    public final void dismissAllNotifications(int i, boolean z) {
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
            if (!userIdMatches(notificationEntry, i) || !notificationEntry.isClearable() || hasFlag(notificationEntry, 4096) || notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED) {
                if (z && userIdMatches(notificationEntry, i)) {
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if ((expandableNotificationRow == null ? true : expandableNotificationRow.canViewBeDismissed$1()) && !hasFlag(notificationEntry, 4096) && notificationEntry.mDismissState != NotificationEntry.DismissState.DISMISSED) {
                    }
                }
                updateDismissInterceptors(notificationEntry);
                if (((ArrayList) notificationEntry.mDismissInterceptors).size() > 0) {
                    notifCollectionLogger.logNotifClearAllDismissalIntercepted(notificationEntry, size, arrayList.size());
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
        final int size = list.size();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            final NotificationEntry notificationEntry = (NotificationEntry) ((Pair) list.get(i)).first;
            final DismissedByUserStats dismissedByUserStats = (DismissedByUserStats) ((Pair) list.get(i)).second;
            Objects.requireNonNull(dismissedByUserStats);
            NotificationEntry notificationEntry2 = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(notificationEntry.mKey);
            NotifCollectionLogger notifCollectionLogger = this.mLogger;
            if (notificationEntry2 == null) {
                notifCollectionLogger.logDismissNonExistentNotif(notificationEntry, i, size);
            } else if (notificationEntry != notificationEntry2) {
                String str = notificationEntry2.mKey;
                String str2 = notificationEntry.mKey;
                if (!str2.equals(str)) {
                    IllegalStateException illegalStateException = new IllegalStateException("Invalid entry: different stored and dismissed entries for " + NotificationUtils.logKey(notificationEntry) + " (" + i + "/" + size + ") dismissed=@" + Integer.toHexString(notificationEntry.hashCode()) + " stored=@" + Integer.toHexString(notificationEntry2.hashCode()));
                    this.mEulogizer.record(illegalStateException);
                    throw illegalStateException;
                }
                notifCollectionLogger.logAlreadyDismissedNotification(str2);
            } else {
                NotificationEntry.DismissState dismissState = notificationEntry.mDismissState;
                if (dismissState == NotificationEntry.DismissState.DISMISSED) {
                    notifCollectionLogger.logDismissAlreadyDismissedNotif(notificationEntry, i, size);
                } else {
                    if (dismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                        notifCollectionLogger.logDismissAlreadyParentDismissedNotif(notificationEntry, i, size);
                    }
                    updateDismissInterceptors(notificationEntry);
                    if (((ArrayList) notificationEntry.mDismissInterceptors).size() > 0) {
                        notifCollectionLogger.logNotifDismissedIntercepted(notificationEntry, i, size);
                    } else {
                        arrayList.add(notificationEntry);
                        if (!notificationEntry.isCanceled()) {
                            final int i2 = i;
                            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotifCollection notifCollection = NotifCollection.this;
                                    NotificationEntry notificationEntry3 = notificationEntry;
                                    DismissedByUserStats dismissedByUserStats2 = dismissedByUserStats;
                                    int i3 = i2;
                                    int i4 = size;
                                    notifCollection.getClass();
                                    try {
                                        notifCollection.mStatusBarService.onNotificationClear(notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier(), notificationEntry3.mSbn.getKey(), dismissedByUserStats2.dismissalSurface, dismissedByUserStats2.dismissalSentiment, dismissedByUserStats2.notificationVisibility);
                                    } catch (RemoteException e) {
                                        notifCollection.mLogger.logRemoteExceptionOnNotificationClear(notificationEntry3, i3, i4, e);
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

    public final void dismissOngoingActivityNotification(String str) {
        Assert.isMainThread();
        ArrayList arrayList = new ArrayList(this.mReadOnlyNotificationSet);
        Log.i("NotifCollection", "Swipe L/R. sbnId = " + str + ", initialEntryCount = " + arrayList.size());
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(size);
            String key = notificationEntry.mSbn.getKey();
            Log.i("NotifCollection", "Swipe L/R. entrySbnId = " + key);
            if (key.equals(str)) {
                Log.i("NotifCollection", "Swipe L/R. call dismissNotification");
                dismissNotification(notificationEntry, new DismissedByUserStats(3, 1, NotificationVisibility.obtain(notificationEntry.mKey, notificationEntry.mRanking.getRank(), 1, true, NotificationLogger.getNotificationLocation(notificationEntry))));
                Log.i("NotifCollection", "Swipe L/R. dismissNotification done");
            }
        }
    }

    public final void dispatchEvents() {
        Trace.beginSection("NotifCollection.dispatchEvents");
        this.mAmDispatchingToOtherCode = true;
        while (!((ArrayDeque) this.mEventQueue).isEmpty()) {
            NotifEvent notifEvent = (NotifEvent) ((ArrayDeque) this.mEventQueue).remove();
            NamedListenerSet namedListenerSet = this.mNotifCollectionListeners;
            notifEvent.getClass();
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice(notifEvent.traceName);
            }
            try {
                Iterator<NamedListenerSet.NamedListener> namedIterator = namedListenerSet.namedIterator();
                while (namedIterator.hasNext()) {
                    NamedListenerSet.NamedListener next = namedIterator.next();
                    String name = next.getName();
                    Object listener = next.getListener();
                    isEnabled = Trace.isEnabled();
                    if (isEnabled) {
                        TraceUtilsKt.beginSlice(name);
                    }
                    try {
                        notifEvent.dispatchToListener((NotifCollectionListener) listener);
                        Unit unit = Unit.INSTANCE;
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    } finally {
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mAmDispatchingToOtherCode = false;
        Trace.endSection();
    }

    public final void dispatchEventsAndRebuildList(String str) {
        Trace.beginSection("NotifCollection.dispatchEventsAndRebuildList");
        Handler handler = this.mMainHandler;
        NotifCollection$$ExternalSyntheticLambda1 notifCollection$$ExternalSyntheticLambda1 = this.mRebuildListRunnable;
        if (handler.hasCallbacks(notifCollection$$ExternalSyntheticLambda1)) {
            handler.removeCallbacks(notifCollection$$ExternalSyntheticLambda1);
        }
        dispatchEvents();
        ShadeListBuilder.AnonymousClass1 anonymousClass1 = this.mBuildListener;
        if (anonymousClass1 != null) {
            Collection collection = this.mReadOnlyNotificationSet;
            Assert.isMainThread();
            ArrayList arrayList = new ArrayList(collection);
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
        arrayList.sort(Comparator.comparing(new NotifCollection$$ExternalSyntheticLambda3()));
        printWriter.println("\tNotifCollection unsorted/unfiltered notifications: " + arrayList.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            ListDumper.dumpEntry((ListEntry) arrayList.get(i), Integer.toString(i), "\t\t", sb, false, false);
        }
        printWriter.println(sb.toString());
        NotifCollectionInconsistencyTracker notifCollectionInconsistencyTracker = this.mInconsistencyTracker;
        notifCollectionInconsistencyTracker.getClass();
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        Set set = notifCollectionInconsistencyTracker.notificationsWithoutRankings;
        asIndenting.append("notificationsWithoutRankings").append((CharSequence) ": ").println(set.size());
        asIndenting.increaseIndent();
        try {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                asIndenting.println(it.next());
            }
            asIndenting.decreaseIndent();
            Set set2 = notifCollectionInconsistencyTracker.missingNotifications;
            asIndenting.append("missingNotifications").append((CharSequence) ": ").println(set2.size());
            asIndenting.increaseIndent();
            try {
                Iterator it2 = set2.iterator();
                while (it2.hasNext()) {
                    asIndenting.println(it2.next());
                }
            } finally {
            }
        } finally {
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
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            int size2 = arrayList.size();
            notifCollectionLogger = this.mLogger;
            if (i >= size2) {
                break;
            }
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(i);
            NotificationEntry notificationEntry2 = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(notificationEntry.mKey);
            if (notificationEntry2 == null) {
                notifCollectionLogger.logLocallyDismissNonExistentNotif(notificationEntry, i, size);
            } else if (notificationEntry2 != notificationEntry) {
                notifCollectionLogger.logLocallyDismissMismatchedEntry(i, size, notificationEntry, notificationEntry2);
            }
            NotificationEntry.DismissState dismissState = notificationEntry.mDismissState;
            NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.DISMISSED;
            if (dismissState == dismissState2) {
                notifCollectionLogger.logLocallyDismissAlreadyDismissedNotif(notificationEntry, i, size);
            } else if (dismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                notifCollectionLogger.logLocallyDismissAlreadyParentDismissedNotif(notificationEntry, i, size);
            }
            notificationEntry.setDismissState(dismissState2);
            notifCollectionLogger.logLocallyDismissed(notificationEntry, i, size);
            boolean isCanceled = notificationEntry.isCanceled();
            String str = notificationEntry.mKey;
            if (isCanceled) {
                notifCollectionLogger.logCanceledNotification(str);
                hashSet.add(notificationEntry);
            } else if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry3 : ((ArrayMap) this.mNotificationSet).values()) {
                    if (shouldAutoDismissChildren(notificationEntry3, notificationEntry.mSbn.getGroupKey())) {
                        NotificationEntry.DismissState dismissState3 = notificationEntry3.mDismissState;
                        if (dismissState3 == NotificationEntry.DismissState.DISMISSED) {
                            notifCollectionLogger.logLocallyDismissAlreadyDismissedChild(i, size, notificationEntry3, notificationEntry);
                        } else if (dismissState3 == NotificationEntry.DismissState.PARENT_DISMISSED) {
                            notifCollectionLogger.logLocallyDismissAlreadyParentDismissedChild(i, size, notificationEntry3, notificationEntry);
                        }
                        notificationEntry3.setDismissState(NotificationEntry.DismissState.PARENT_DISMISSED);
                        notifCollectionLogger.logLocallyDismissedChild(i, size, notificationEntry3, notificationEntry);
                        if (notificationEntry3.isCanceled()) {
                            notifCollectionLogger.logCanceledNotification(str);
                            hashSet.add(notificationEntry3);
                        }
                    }
                }
            }
            i++;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry4 = (NotificationEntry) it.next();
            notifCollectionLogger.logLocallyDismissedAlreadyCanceledEntry(notificationEntry4);
            tryRemoveNotification(notificationEntry4);
        }
    }

    public final void postNotification(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(statusBarNotification.getKey());
        NotifCollectionLogger notifCollectionLogger = this.mLogger;
        if (notificationEntry == null) {
            NotificationEntry notificationEntry2 = new NotificationEntry(statusBarNotification, ranking, this.mClock.uptimeMillis());
            ((ArrayDeque) this.mEventQueue).add(new InitEntryEvent(notificationEntry2));
            ((ArrayDeque) this.mEventQueue).add(new BindEntryEvent(notificationEntry2, statusBarNotification));
            ((ArrayMap) this.mNotificationSet).put(statusBarNotification.getKey(), notificationEntry2);
            notifCollectionLogger.logNotifPosted(notificationEntry2);
            ((ArrayDeque) this.mEventQueue).add(new EntryAddedEvent(notificationEntry2));
            return;
        }
        NotificationEntry.DismissState dismissState = notificationEntry.mDismissState;
        NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.NOT_DISMISSED;
        if (dismissState == dismissState2) {
            notifCollectionLogger.logCancelLocalDismissalNotDismissedNotif(notificationEntry);
        } else {
            notificationEntry.setDismissState(dismissState2);
            if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry3 : ((ArrayMap) this.mNotificationSet).values()) {
                    if (notificationEntry3.mSbn.getGroupKey().equals(notificationEntry.mSbn.getGroupKey()) && notificationEntry3.mDismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                        notificationEntry3.setDismissState(NotificationEntry.DismissState.NOT_DISMISSED);
                    }
                }
            }
        }
        cancelLifetimeExtension$1(notificationEntry);
        cancelDismissInterception(notificationEntry);
        notificationEntry.mCancellationReason = -1;
        notificationEntry.setSbn(statusBarNotification);
        ((ArrayDeque) this.mEventQueue).add(new BindEntryEvent(notificationEntry, statusBarNotification));
        if (notificationEntry.mIsReaded) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(), notificationEntry.mKey, " isReaded = false", "NotifCollection");
            notificationEntry.mIsReaded = false;
        }
        notifCollectionLogger.logNotifUpdated(notificationEntry);
        ((ArrayDeque) this.mEventQueue).add(new EntryUpdatedEvent(notificationEntry, true));
    }

    public final boolean tryRemoveNotification(NotificationEntry notificationEntry) {
        NotificationEntry notificationEntry2 = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(notificationEntry.mKey);
        if (notificationEntry2 == null) {
            Log.wtf("NotifCollection", "TRY REMOVE non-existent notification " + NotificationUtils.logKey(notificationEntry));
            return false;
        }
        LogBufferEulogizer logBufferEulogizer = this.mEulogizer;
        if (notificationEntry2 != notificationEntry) {
            IllegalStateException illegalStateException = new IllegalStateException("Mismatched stored and tryRemoved entries for key " + NotificationUtils.logKey(notificationEntry) + ": stored=@" + Integer.toHexString(notificationEntry2.hashCode()) + " tryRemoved=@" + Integer.toHexString(notificationEntry.hashCode()));
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
        boolean z2 = i == 1 || i == 2;
        NotifCollectionLogger notifCollectionLogger = this.mLogger;
        if (z || z2) {
            cancelLifetimeExtension$1(notificationEntry);
        } else {
            ((ArrayList) notificationEntry.mLifetimeExtenders).clear();
            this.mAmDispatchingToOtherCode = true;
            Iterator it = ((ArrayList) this.mLifetimeExtenders).iterator();
            while (it.hasNext()) {
                NotifLifetimeExtender notifLifetimeExtender = (NotifLifetimeExtender) it.next();
                if (notifLifetimeExtender.maybeExtendLifetime(notificationEntry, notificationEntry.mCancellationReason)) {
                    notifCollectionLogger.logLifetimeExtended(notificationEntry, notifLifetimeExtender);
                    ((ArrayList) notificationEntry.mLifetimeExtenders).add(notifLifetimeExtender);
                }
            }
            this.mAmDispatchingToOtherCode = false;
        }
        if (((ArrayList) notificationEntry.mLifetimeExtenders).size() > 0) {
            return false;
        }
        notifCollectionLogger.logNotifReleased(notificationEntry);
        ArrayMap arrayMap = (ArrayMap) this.mNotificationSet;
        String str = notificationEntry.mKey;
        arrayMap.remove(str);
        cancelDismissInterception(notificationEntry);
        ((ArrayDeque) this.mEventQueue).add(new EntryRemovedEvent(notificationEntry, notificationEntry.mCancellationReason));
        ((ArrayDeque) this.mEventQueue).add(new CleanUpEntryEvent(notificationEntry));
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

    public final void updateDismissInterceptors(NotificationEntry notificationEntry) {
        ((ArrayList) notificationEntry.mDismissInterceptors).clear();
        this.mAmDispatchingToOtherCode = true;
        Iterator it = ((ArrayList) this.mDismissInterceptors).iterator();
        while (it.hasNext()) {
            NotifDismissInterceptor notifDismissInterceptor = (NotifDismissInterceptor) it.next();
            if (notifDismissInterceptor.shouldInterceptDismissal(notificationEntry)) {
                ((ArrayList) notificationEntry.mDismissInterceptors).add(notifDismissInterceptor);
            }
        }
        this.mAmDispatchingToOtherCode = false;
    }
}
