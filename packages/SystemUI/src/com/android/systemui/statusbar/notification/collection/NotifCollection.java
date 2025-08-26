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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
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
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLoggerKt;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingAppliedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.NamedListenerSet;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public class NotifCollection implements Dumpable, PipelineDumpable {
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

    /* renamed from: com.android.systemui.statusbar.notification.collection.NotifCollection$1, reason: invalid class name */
    public class AnonymousClass1 implements NotificationListener.NotificationHandler {
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
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            int i2 = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = NotifCollection.this;
            notifCollection.getClass();
            Assert.isMainThread();
            NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(6);
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda3, null);
            String key = statusBarNotification.getKey();
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = key != null ? key.replace("\n", "") : null;
            logMessageImpl.int1 = i;
            logBuffer.commit(logMessageObtain);
            NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) notifCollection.mNotificationSet).get(statusBarNotification.getKey());
            if (notificationEntry != null) {
                notificationEntry.mCancellationReason = i;
                notifCollection.tryRemoveNotification(notificationEntry);
                notifCollection.applyRanking(rankingMap);
                notifCollection.dispatchEventsAndRebuildList("onNotificationRemoved");
                return;
            }
            LogMessage logMessageObtain2 = logBuffer.obtain("NotifCollection", LogLevel.ERROR, new NotifCollectionLogger$$ExternalSyntheticLambda3(3), null);
            String key2 = statusBarNotification.getKey();
            String strReplace = key2 != null ? key2.replace("\n", "") : null;
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.str1 = strReplace;
            logMessageImpl2.int1 = i;
            logBuffer.commit(logMessageObtain2);
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationsInitialized() {
            int i = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = NotifCollection.this;
            notifCollection.getClass();
            int i2 = UseElapsedRealtimeForCreationTime.$r8$clinit;
            notifCollection.mClock.uptimeMillis();
        }
    }

    public interface DismissedByUserStatsCreator {
    }

    public class FutureDismissal implements Runnable {
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
                NotifCollectionLogger notifCollectionLogger = NotifCollection.this.mLogger;
                notifCollectionLogger.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(27);
                LogBuffer logBuffer = notifCollectionLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = this.mLabel;
                logBuffer.commit(logMessageObtain);
                return;
            }
            this.mDidRun = true;
            NotifCollection.this.mFutureDismissals.remove(this.mEntry.mKey);
            NotificationEntry entry = NotifCollection.this.getEntry(this.mEntry.mKey);
            DismissedByUserStats dismissedByUserStatsCreateDismissedByUserStats = ((OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0) this.mStatsCreator).createDismissedByUserStats(this.mEntry);
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
            if (!this.mDidSystemServerCancel) {
                if (entry != this.mEntry) {
                    NotifCollection.this.mLogger.logFutureDismissalMismatchedEntry(this, "entry", entry);
                    return;
                } else {
                    NotifCollection.this.mLogger.logFutureDismissalDismissing(this, "entry");
                    NotifCollection.this.dismissNotification(this.mEntry, dismissedByUserStatsCreateDismissedByUserStats);
                    return;
                }
            }
            NotifCollectionLogger notifCollectionLogger2 = NotifCollection.this.mLogger;
            notifCollectionLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(5);
            LogBuffer logBuffer2 = notifCollectionLogger2.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) logMessageObtain2).str1 = this.mLabel;
            logBuffer2.commit(logMessageObtain2);
        }

        private FutureDismissal(NotificationEntry notificationEntry, int i, DismissedByUserStatsCreator dismissedByUserStatsCreator) {
            this.mEntry = notificationEntry;
            this.mStatsCreator = dismissedByUserStatsCreator;
            int i2 = NotifCollection.$r8$clinit;
            NotificationEntry notificationEntryFetchSummaryToDismiss = NotifCollection.this.fetchSummaryToDismiss(notificationEntry);
            this.mSummaryToDismiss = notificationEntryFetchSummaryToDismiss;
            this.mLabel = "<FutureDismissal@" + Integer.toHexString(hashCode()) + " entry=" + NotificationUtils.logKey(notificationEntry) + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(i) + " summary=" + NotificationUtils.logKey(notificationEntryFetchSummaryToDismiss) + ">";
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
                NotifCollection notifCollection = this.f$0;
                ShadeListBuilder.AnonymousClass1 anonymousClass1 = notifCollection.mBuildListener;
                if (anonymousClass1 != null) {
                    anonymousClass1.onBuildList(notifCollection.mReadOnlyNotificationSet, "asynchronousUpdate");
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

    public static boolean hasSemFlag(NotificationEntry notificationEntry, int i) {
        return (notificationEntry.mSbn.getNotification().semFlags & i) != 0;
    }

    public static boolean shouldAutoDismissChildren(NotificationEntry notificationEntry, String str) {
        if (!notificationEntry.mSbn.getGroupKey().equals(str) || notificationEntry.mSbn.getNotification().isGroupSummary() || hasFlag(notificationEntry, 4096) || hasFlag(notificationEntry, 32)) {
            return false;
        }
        return ((notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().isImportantConversation()) || notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED || hasSemFlag(notificationEntry, NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) || hasSemFlag(notificationEntry, 1048576) || hasSemFlag(notificationEntry, 2097152) || hasSemFlag(notificationEntry, 4194304) || notificationEntry.mRanking.getChannel().isImportantConversation()) ? false : true;
    }

    public static boolean userIdMatches(NotificationEntry notificationEntry, int i) {
        return i == -1 || notificationEntry.mSbn.getUser().getIdentifier() == -1 || notificationEntry.mSbn.getUser().getIdentifier() == i;
    }

    public final void applyRanking(NotificationListenerService.RankingMap rankingMap) {
        Set setKeySet;
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
        NotifCollection$$ExternalSyntheticLambda11 notifCollection$$ExternalSyntheticLambda11 = notifCollectionInconsistencyTracker.collectedKeySetAccessor;
        if (notifCollection$$ExternalSyntheticLambda11 == null) {
            notifCollection$$ExternalSyntheticLambda11 = null;
        }
        final Set setKeySet2 = ((ArrayMap) ((Map) notifCollection$$ExternalSyntheticLambda11.f$0)).keySet();
        NotifCollection$$ExternalSyntheticLambda11 notifCollection$$ExternalSyntheticLambda112 = notifCollectionInconsistencyTracker.coalescedKeySetAccessor;
        final Set set = (Set) (notifCollection$$ExternalSyntheticLambda112 != null ? notifCollection$$ExternalSyntheticLambda112 : null).invoke();
        final int i = 0;
        final int i2 = 1;
        Set<String> set2 = SequencesKt___SequencesKt.toSet(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(ArraysKt___ArraysKt.asSequence(rankingMap.getOrderedKeys()), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i3 = i;
                Set set3 = setKeySet2;
                String str2 = (String) obj;
                switch (i3) {
                }
                return Boolean.valueOf(!set3.contains(str2));
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i3 = i2;
                Set set3 = set;
                String str2 = (String) obj;
                switch (i3) {
                }
                return Boolean.valueOf(!set3.contains(str2));
            }
        }));
        notifCollectionInconsistencyTracker.maybeLogMissingNotifications(notifCollectionInconsistencyTracker.missingNotifications, set2);
        notifCollectionInconsistencyTracker.missingNotifications = set2;
        notifCollectionInconsistencyTracker.maybeLogInconsistentRankings(notifCollectionInconsistencyTracker.notificationsWithoutRankings, arrayMap != null ? arrayMap : MapsKt__MapsKt.emptyMap(), rankingMap);
        if (arrayMap == null || (setKeySet = arrayMap.keySet()) == null) {
            setKeySet = EmptySet.INSTANCE;
        }
        notifCollectionInconsistencyTracker.notificationsWithoutRankings = setKeySet;
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
        ArrayList arrayList = (ArrayList) notificationEntry.mDismissInterceptors;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((NotifDismissInterceptor) obj).cancelDismissInterception(notificationEntry);
        }
        this.mAmDispatchingToOtherCode = false;
        ((ArrayList) notificationEntry.mDismissInterceptors).clear();
    }

    public final void cancelLifetimeExtension$1(NotificationEntry notificationEntry) {
        this.mAmDispatchingToOtherCode = true;
        ArrayList arrayList = (ArrayList) notificationEntry.mLifetimeExtenders;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((NotifLifetimeExtender) obj).cancelLifetimeExtension(notificationEntry);
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
        notifCollectionLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = notifCollectionLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityData mediaData = OngoingActivityDataHelper.getMediaData();
        try {
            this.mStatusBarService.onClearAllNotifications(i, mediaData != null ? mediaData.mNotificationEntry.mIsPlayingMediaOngoingActivity.booleanValue() : false);
        } catch (RemoteException e) {
            LogMessage logMessageObtain2 = logBuffer.obtain("NotifCollection", LogLevel.WTF, new NotifCollectionLogger$$ExternalSyntheticLambda0(5), null);
            ((LogMessageImpl) logMessageObtain2).str1 = e.toString();
            logBuffer.commit(logMessageObtain2);
        }
        Assert.isMainThread();
        ArrayList arrayList = new ArrayList(this.mReadOnlyNotificationSet);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(size);
            if ((!userIdMatches(notificationEntry, i) || !notificationEntry.isClearable() || hasFlag(notificationEntry, 4096) || notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED) && (!z || notificationEntry == null || notificationEntry.row == null || !userIdMatches(notificationEntry, i) || !notificationEntry.row.canViewBeDismissed$1() || hasFlag(notificationEntry, 4096) || notificationEntry.mDismissState == NotificationEntry.DismissState.DISMISSED)) {
                updateDismissInterceptors(notificationEntry);
                if (((ArrayList) notificationEntry.mDismissInterceptors).size() > 0) {
                    int size2 = arrayList.size();
                    LogMessage logMessageObtain3 = logBuffer.obtain("NotifCollection", LogLevel.INFO, new NotifCollectionLogger$$ExternalSyntheticLambda0(22), null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain3;
                    logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
                    logMessageImpl.int1 = size;
                    logMessageImpl.int2 = size2;
                    logBuffer.commit(logMessageObtain3);
                }
                arrayList.remove(size);
            }
        }
        locallyDismissNotifications(arrayList);
        dispatchEventsAndRebuildList("dismissAllNotifications");
    }

    public final void dismissNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats) {
        dismissNotifications(List.of(new EntryWithDismissStats(notificationEntry, dismissedByUserStats, notificationEntry.mKey, notificationEntry.hashCode())));
    }

    public final void dismissNotifications(List list) {
        final int i;
        NotificationEntry notificationEntry;
        String strLogKey;
        NotificationEntry notificationEntryFetchSummaryToDismiss;
        Assert.isMainThread();
        checkForReentrantCall();
        HashSet hashSet = new HashSet(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            EntryWithDismissStats entryWithDismissStats = (EntryWithDismissStats) it.next();
            int i2 = NotificationBundleUi.$r8$clinit;
            NotificationEntry notificationEntry2 = entryWithDismissStats.entry;
            if (notificationEntry2 != null) {
                hashSet.add(notificationEntry2);
            }
        }
        ArrayList arrayList = new ArrayList(list.size() + 1);
        Iterator it2 = list.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            EntryWithDismissStats entryWithDismissStats2 = (EntryWithDismissStats) it2.next();
            arrayList.add(entryWithDismissStats2);
            int i3 = NotificationBundleUi.$r8$clinit;
            NotificationEntry notificationEntry3 = entryWithDismissStats2.entry;
            if (notificationEntry3 != null && (notificationEntryFetchSummaryToDismiss = fetchSummaryToDismiss(notificationEntry3)) != null && !hashSet.contains(notificationEntryFetchSummaryToDismiss)) {
                DismissedByUserStats dismissedByUserStats = entryWithDismissStats2.stats;
                int i4 = dismissedByUserStats.dismissalSurface;
                int rank = notificationEntryFetchSummaryToDismiss.mRanking.getRank();
                int i5 = dismissedByUserStats.notificationVisibility.count;
                String str = notificationEntryFetchSummaryToDismiss.mKey;
                arrayList.add(new EntryWithDismissStats(notificationEntryFetchSummaryToDismiss, new DismissedByUserStats(i4, dismissedByUserStats.dismissalSentiment, NotificationVisibility.obtain(str, rank, i5, false)), str, notificationEntryFetchSummaryToDismiss.hashCode()));
            }
        }
        final int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList();
        for (i = 0; i < arrayList.size(); i++) {
            String str2 = ((EntryWithDismissStats) arrayList.get(i)).key;
            int i6 = ((EntryWithDismissStats) arrayList.get(i)).entryHashCode;
            final DismissedByUserStats dismissedByUserStats2 = ((EntryWithDismissStats) arrayList.get(i)).stats;
            Objects.requireNonNull(dismissedByUserStats2);
            final NotificationEntry notificationEntry4 = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(str2);
            NotifCollectionLogger notifCollectionLogger = this.mLogger;
            if (notificationEntry4 == null) {
                notifCollectionLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(8);
                LogBuffer logBuffer = notifCollectionLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = NotificationUtils.logKey(str2);
                logMessageImpl.int1 = i;
                logMessageImpl.int2 = size;
                logBuffer.commit(logMessageObtain);
            } else if (i6 == notificationEntry4.hashCode()) {
                NotificationEntry.DismissState dismissState = notificationEntry4.mDismissState;
                if (dismissState == NotificationEntry.DismissState.DISMISSED) {
                    notifCollectionLogger.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda02 = new NotifCollectionLogger$$ExternalSyntheticLambda0(23);
                    LogBuffer logBuffer2 = notifCollectionLogger.buffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda02, null);
                    String strLogKey2 = NotificationUtils.logKey(notificationEntry4);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                    logMessageImpl2.str1 = strLogKey2;
                    logMessageImpl2.int1 = i;
                    logMessageImpl2.int2 = size;
                    logBuffer2.commit(logMessageObtain2);
                } else {
                    if (dismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                        notifCollectionLogger.getClass();
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda03 = new NotifCollectionLogger$$ExternalSyntheticLambda0(6);
                        LogBuffer logBuffer3 = notifCollectionLogger.buffer;
                        LogMessage logMessageObtain3 = logBuffer3.obtain("NotifCollection", logLevel3, notifCollectionLogger$$ExternalSyntheticLambda03, null);
                        LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
                        logMessageImpl3.str1 = NotificationUtils.logKey(notificationEntry4);
                        logMessageImpl3.int1 = i;
                        logMessageImpl3.int2 = size;
                        PipelineEntry pipelineEntry = notificationEntry4.mAttachState.parent;
                        String str3 = "(null)";
                        if (pipelineEntry instanceof GroupEntry) {
                            GroupEntry groupEntry = pipelineEntry instanceof GroupEntry ? (GroupEntry) pipelineEntry : null;
                            if (groupEntry != null && (notificationEntry = groupEntry.mSummary) != null && (strLogKey = NotificationUtils.logKey(notificationEntry)) != null) {
                                str3 = strLogKey;
                            }
                        }
                        logMessageImpl3.str2 = str3;
                        logBuffer3.commit(logMessageObtain3);
                    }
                    updateDismissInterceptors(notificationEntry4);
                    if (((ArrayList) notificationEntry4.mDismissInterceptors).size() > 0) {
                        notifCollectionLogger.getClass();
                        LogLevel logLevel4 = LogLevel.INFO;
                        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda04 = new NotifCollectionLogger$$ExternalSyntheticLambda0(12);
                        LogBuffer logBuffer4 = notifCollectionLogger.buffer;
                        LogMessage logMessageObtain4 = logBuffer4.obtain("NotifCollection", logLevel4, notifCollectionLogger$$ExternalSyntheticLambda04, null);
                        String strLogKey3 = NotificationUtils.logKey(notificationEntry4);
                        LogMessageImpl logMessageImpl4 = (LogMessageImpl) logMessageObtain4;
                        logMessageImpl4.str1 = strLogKey3;
                        logMessageImpl4.int1 = i;
                        logMessageImpl4.int2 = size;
                        logBuffer4.commit(logMessageObtain4);
                    } else {
                        arrayList2.add(notificationEntry4);
                        if (!notificationEntry4.isCanceled()) {
                            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotifCollection notifCollection = this.f$0;
                                    NotificationEntry notificationEntry5 = notificationEntry4;
                                    DismissedByUserStats dismissedByUserStats3 = dismissedByUserStats2;
                                    int i7 = i;
                                    int i8 = size;
                                    int i9 = NotifCollection.$r8$clinit;
                                    notifCollection.getClass();
                                    try {
                                        notifCollection.mStatusBarService.onNotificationClear(notificationEntry5.mSbn.getPackageName(), notificationEntry5.mSbn.getUser().getIdentifier(), notificationEntry5.mSbn.getKey(), dismissedByUserStats3.dismissalSurface, dismissedByUserStats3.dismissalSentiment, dismissedByUserStats3.notificationVisibility);
                                    } catch (RemoteException e) {
                                        NotifCollectionLogger notifCollectionLogger2 = notifCollection.mLogger;
                                        notifCollectionLogger2.getClass();
                                        LogLevel logLevel5 = LogLevel.WTF;
                                        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda05 = new NotifCollectionLogger$$ExternalSyntheticLambda0(18);
                                        LogBuffer logBuffer5 = notifCollectionLogger2.buffer;
                                        LogMessage logMessageObtain5 = logBuffer5.obtain("NotifCollection", logLevel5, notifCollectionLogger$$ExternalSyntheticLambda05, null);
                                        LogMessageImpl logMessageImpl5 = (LogMessageImpl) logMessageObtain5;
                                        logMessageImpl5.str1 = NotificationUtilsKt.getLogKey(notificationEntry5);
                                        logMessageImpl5.int1 = i7;
                                        logMessageImpl5.int2 = i8;
                                        logMessageImpl5.str2 = e.toString();
                                        logBuffer5.commit(logMessageObtain5);
                                    }
                                }
                            });
                        }
                    }
                }
            } else {
                if (!str2.equals(notificationEntry4.mKey)) {
                    StringBuilder sb = new StringBuilder("Invalid entry: different stored and dismissed entries for ");
                    sb.append(str2.replace("\n", ""));
                    sb.append(" (");
                    ViewPager$$ExternalSyntheticOutline0.m(sb, i, "/", size, ") dismissed=@");
                    sb.append(Integer.toHexString(i6));
                    sb.append(" stored=@");
                    sb.append(Integer.toHexString(notificationEntry4.hashCode()));
                    IllegalStateException illegalStateException = new IllegalStateException(sb.toString());
                    this.mEulogizer.record(illegalStateException);
                    throw illegalStateException;
                }
                notifCollectionLogger.getClass();
                LogLevel logLevel5 = LogLevel.INFO;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda05 = new NotifCollectionLogger$$ExternalSyntheticLambda0(21);
                LogBuffer logBuffer5 = notifCollectionLogger.buffer;
                LogMessage logMessageObtain5 = logBuffer5.obtain("NotifCollection", logLevel5, notifCollectionLogger$$ExternalSyntheticLambda05, null);
                ((LogMessageImpl) logMessageObtain5).str1 = str2;
                logBuffer5.commit(logMessageObtain5);
            }
        }
        locallyDismissNotifications(arrayList2);
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
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice(notifEvent.traceName);
            }
            try {
                Iterator<NamedListenerSet.NamedListener> itNamedIterator = namedListenerSet.namedIterator();
                while (itNamedIterator.hasNext()) {
                    NamedListenerSet.NamedListener next = itNamedIterator.next();
                    String name = next.getName();
                    Object listener = next.getListener();
                    zIsEnabled = Trace.isEnabled();
                    if (zIsEnabled) {
                        TraceUtilsKt.beginSlice(name);
                    }
                    try {
                        notifEvent.dispatchToListener((NotifCollectionListener) listener);
                        Unit unit = Unit.INSTANCE;
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    } finally {
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                if (zIsEnabled) {
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
            anonymousClass1.onBuildList(this.mReadOnlyNotificationSet, str);
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
            ListDumper.dumpEntry((PipelineEntry) arrayList.get(i), Integer.toString(i), "\t\t", sb, false, false);
        }
        printWriter.println(sb.toString());
        NotifCollectionInconsistencyTracker notifCollectionInconsistencyTracker = this.mInconsistencyTracker;
        notifCollectionInconsistencyTracker.getClass();
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        Set set = notifCollectionInconsistencyTracker.notificationsWithoutRankings;
        indentingPrintWriterAsIndenting.append("notificationsWithoutRankings").append((CharSequence) ": ").println(set.size());
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
            indentingPrintWriterAsIndenting.decreaseIndent();
            Set set2 = notifCollectionInconsistencyTracker.missingNotifications;
            indentingPrintWriterAsIndenting.append("missingNotifications").append((CharSequence) ": ").println(set2.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            try {
                Iterator it2 = set2.iterator();
                while (it2.hasNext()) {
                    indentingPrintWriterAsIndenting.println(it2.next());
                }
            } finally {
                indentingPrintWriterAsIndenting.decreaseIndent();
            }
        } finally {
            indentingPrintWriterAsIndenting.decreaseIndent();
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mNotifCollectionListeners, "notifCollectionListeners");
        pipelineDumper.dump(this.mLifetimeExtenders, "lifetimeExtenders");
        pipelineDumper.dump(this.mDismissInterceptors, "dismissInterceptors");
        pipelineDumper.dump(this.mBuildListener, "buildListener");
    }

    public final NotificationEntry fetchSummaryToDismiss(NotificationEntry notificationEntry) {
        final String groupKey = notificationEntry.mSbn.getGroupKey();
        if (((ArrayMap) this.mNotificationSet).get(notificationEntry.mKey) == notificationEntry) {
            final int i = 0;
            final int i2 = 0;
            if (((ArrayMap) this.mNotificationSet).values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    int i3 = i;
                    String str = groupKey;
                    NotificationEntry notificationEntry2 = (NotificationEntry) obj;
                    switch (i3) {
                        case 0:
                            int i4 = NotifCollection.$r8$clinit;
                            break;
                        default:
                            int i5 = NotifCollection.$r8$clinit;
                            break;
                    }
                    return Objects.equals(notificationEntry2.mSbn.getGroupKey(), str);
                }
            }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) obj;
                    switch (i2) {
                        case 0:
                            int i3 = NotifCollection.$r8$clinit;
                            return !notificationEntry2.mSbn.getNotification().isGroupSummary();
                        default:
                            int i4 = NotifCollection.$r8$clinit;
                            return notificationEntry2.mSbn.getNotification().isGroupSummary();
                    }
                }
            }).count() == 1) {
                final String groupKey2 = notificationEntry.mSbn.getGroupKey();
                final int i3 = 1;
                final int i4 = 1;
                NotificationEntry notificationEntry2 = (NotificationEntry) ((ArrayMap) this.mNotificationSet).values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i32 = i3;
                        String str = groupKey2;
                        NotificationEntry notificationEntry22 = (NotificationEntry) obj;
                        switch (i32) {
                            case 0:
                                int i42 = NotifCollection.$r8$clinit;
                                break;
                            default:
                                int i5 = NotifCollection.$r8$clinit;
                                break;
                        }
                        return Objects.equals(notificationEntry22.mSbn.getGroupKey(), str);
                    }
                }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        NotificationEntry notificationEntry22 = (NotificationEntry) obj;
                        switch (i4) {
                            case 0:
                                int i32 = NotifCollection.$r8$clinit;
                                return !notificationEntry22.mSbn.getNotification().isGroupSummary();
                            default:
                                int i42 = NotifCollection.$r8$clinit;
                                return notificationEntry22.mSbn.getNotification().isGroupSummary();
                        }
                    }
                }).findFirst().orElse(null);
                if (notificationEntry2 != null) {
                    NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl = (NotificationDismissibilityProviderImpl) this.mDismissibilityProvider;
                    if (!notificationDismissibilityProviderImpl.nonDismissableEntryKeys.contains(notificationEntry2.mKey)) {
                        return notificationEntry2;
                    }
                }
            }
        }
        return null;
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
                notifCollectionLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(12);
                LogBuffer logBuffer = notifCollectionLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda3, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
                logMessageImpl.int1 = i;
                logMessageImpl.int2 = size;
                logBuffer.commit(logMessageObtain);
            } else if (notificationEntry2 != notificationEntry) {
                notifCollectionLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda32 = new NotifCollectionLogger$$ExternalSyntheticLambda3(15);
                LogBuffer logBuffer2 = notifCollectionLogger.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda32, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.str1 = NotificationUtils.logKey(notificationEntry);
                logMessageImpl2.int1 = i;
                logMessageImpl2.int2 = size;
                logMessageImpl2.str2 = Integer.toHexString(notificationEntry.hashCode());
                logMessageImpl2.str3 = Integer.toHexString(notificationEntry2.hashCode());
                logBuffer2.commit(logMessageObtain2);
            }
            NotificationEntry.DismissState dismissState = notificationEntry.mDismissState;
            NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.DISMISSED;
            if (dismissState == dismissState2) {
                notifCollectionLogger.getClass();
                LogLevel logLevel3 = LogLevel.INFO;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(11);
                LogBuffer logBuffer3 = notifCollectionLogger.buffer;
                LogMessage logMessageObtain3 = logBuffer3.obtain("NotifCollection", logLevel3, notifCollectionLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
                logMessageImpl3.str1 = NotificationUtils.logKey(notificationEntry);
                logMessageImpl3.int1 = i;
                logMessageImpl3.int2 = size;
                logBuffer3.commit(logMessageObtain3);
            } else if (dismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                notifCollectionLogger.getClass();
                LogLevel logLevel4 = LogLevel.INFO;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda02 = new NotifCollectionLogger$$ExternalSyntheticLambda0(4);
                LogBuffer logBuffer4 = notifCollectionLogger.buffer;
                LogMessage logMessageObtain4 = logBuffer4.obtain("NotifCollection", logLevel4, notifCollectionLogger$$ExternalSyntheticLambda02, null);
                LogMessageImpl logMessageImpl4 = (LogMessageImpl) logMessageObtain4;
                logMessageImpl4.str1 = NotificationUtils.logKey(notificationEntry);
                logMessageImpl4.int1 = i;
                logMessageImpl4.int2 = size;
                logBuffer4.commit(logMessageObtain4);
            }
            Objects.requireNonNull(dismissState2);
            notificationEntry.mDismissState = dismissState2;
            notifCollectionLogger.getClass();
            LogLevel logLevel5 = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda03 = new NotifCollectionLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer5 = notifCollectionLogger.buffer;
            LogMessage logMessageObtain5 = logBuffer5.obtain("NotifCollection", logLevel5, notifCollectionLogger$$ExternalSyntheticLambda03, null);
            LogMessageImpl logMessageImpl5 = (LogMessageImpl) logMessageObtain5;
            logMessageImpl5.str1 = NotificationUtils.logKey(notificationEntry);
            logMessageImpl5.int1 = i;
            logMessageImpl5.int2 = size;
            logBuffer5.commit(logMessageObtain5);
            boolean zIsCanceled = notificationEntry.isCanceled();
            String str = notificationEntry.mKey;
            if (zIsCanceled) {
                LogMessage logMessageObtain6 = logBuffer5.obtain("NotifCollection", logLevel5, new NotifCollectionLogger$$ExternalSyntheticLambda3(16), null);
                ((LogMessageImpl) logMessageObtain6).str1 = str;
                logBuffer5.commit(logMessageObtain6);
                hashSet.add(notificationEntry);
            } else if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry3 : ((ArrayMap) this.mNotificationSet).values()) {
                    if (shouldAutoDismissChildren(notificationEntry3, notificationEntry.mSbn.getGroupKey())) {
                        NotificationEntry.DismissState dismissState3 = notificationEntry3.mDismissState;
                        if (dismissState3 == NotificationEntry.DismissState.DISMISSED) {
                            LogMessage logMessageObtain7 = logBuffer5.obtain("NotifCollection", LogLevel.INFO, new NotifCollectionLogger$$ExternalSyntheticLambda3(0), null);
                            LogMessageImpl logMessageImpl6 = (LogMessageImpl) logMessageObtain7;
                            logMessageImpl6.str1 = NotificationUtils.logKey(notificationEntry3);
                            logMessageImpl6.str2 = NotificationUtils.logKey(notificationEntry);
                            logMessageImpl6.int1 = i;
                            logMessageImpl6.int2 = size;
                            logBuffer5.commit(logMessageObtain7);
                        } else if (dismissState3 == NotificationEntry.DismissState.PARENT_DISMISSED) {
                            LogMessage logMessageObtain8 = logBuffer5.obtain("NotifCollection", LogLevel.INFO, new NotifCollectionLogger$$ExternalSyntheticLambda0(0), null);
                            LogMessageImpl logMessageImpl7 = (LogMessageImpl) logMessageObtain8;
                            logMessageImpl7.str1 = NotificationUtils.logKey(notificationEntry3);
                            logMessageImpl7.str2 = NotificationUtils.logKey(notificationEntry);
                            logMessageImpl7.int1 = i;
                            logMessageImpl7.int2 = size;
                            logBuffer5.commit(logMessageObtain8);
                        }
                        NotificationEntry.DismissState dismissState4 = NotificationEntry.DismissState.PARENT_DISMISSED;
                        Objects.requireNonNull(dismissState4);
                        notificationEntry3.mDismissState = dismissState4;
                        LogMessage logMessageObtain9 = logBuffer5.obtain("NotifCollection", LogLevel.DEBUG, new NotifCollectionLogger$$ExternalSyntheticLambda3(17), null);
                        LogMessageImpl logMessageImpl8 = (LogMessageImpl) logMessageObtain9;
                        logMessageImpl8.str1 = NotificationUtils.logKey(notificationEntry3);
                        logMessageImpl8.str2 = NotificationUtils.logKey(notificationEntry);
                        logMessageImpl8.int1 = i;
                        logMessageImpl8.int2 = size;
                        logBuffer5.commit(logMessageObtain9);
                        if (notificationEntry3.isCanceled()) {
                            LogMessage logMessageObtain10 = logBuffer5.obtain("NotifCollection", LogLevel.INFO, new NotifCollectionLogger$$ExternalSyntheticLambda3(16), null);
                            ((LogMessageImpl) logMessageObtain10).str1 = str;
                            logBuffer5.commit(logMessageObtain10);
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
            notifCollectionLogger.getClass();
            LogLevel logLevel6 = LogLevel.DEBUG;
            NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda33 = new NotifCollectionLogger$$ExternalSyntheticLambda3(13);
            LogBuffer logBuffer6 = notifCollectionLogger.buffer;
            LogMessage logMessageObtain11 = logBuffer6.obtain("NotifCollection", logLevel6, notifCollectionLogger$$ExternalSyntheticLambda33, null);
            ((LogMessageImpl) logMessageObtain11).str1 = NotificationUtilsKt.getLogKey(notificationEntry4);
            logBuffer6.commit(logMessageObtain11);
            tryRemoveNotification(notificationEntry4);
        }
    }

    public final void postNotification(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) this.mNotificationSet).get(statusBarNotification.getKey());
        NotifCollectionLogger notifCollectionLogger = this.mLogger;
        if (notificationEntry == null) {
            int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
            NotificationEntry notificationEntry2 = new NotificationEntry(statusBarNotification, ranking, this.mClock.uptimeMillis());
            ((ArrayDeque) this.mEventQueue).add(new InitEntryEvent(notificationEntry2));
            ((ArrayDeque) this.mEventQueue).add(new BindEntryEvent(notificationEntry2, statusBarNotification));
            ((ArrayMap) this.mNotificationSet).put(statusBarNotification.getKey(), notificationEntry2);
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(29);
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtils.logKey(notificationEntry2);
            logBuffer.commit(logMessageObtain);
            ((ArrayDeque) this.mEventQueue).add(new EntryAddedEvent(notificationEntry2));
            return;
        }
        UpdateSource updateSource = statusBarNotification.getPostTime() == notificationEntry.mSbn.getPostTime() ? UpdateSource.SystemServer : UpdateSource.App;
        NotificationEntry.DismissState dismissState = notificationEntry.mDismissState;
        NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.NOT_DISMISSED;
        if (dismissState == dismissState2) {
            notifCollectionLogger.getClass();
            LogLevel logLevel2 = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(7);
            LogBuffer logBuffer2 = notifCollectionLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtils.logKey(notificationEntry);
            logBuffer2.commit(logMessageObtain2);
        } else {
            Objects.requireNonNull(dismissState2);
            notificationEntry.mDismissState = dismissState2;
            if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry3 : ((ArrayMap) this.mNotificationSet).values()) {
                    if (notificationEntry3.mSbn.getGroupKey().equals(notificationEntry.mSbn.getGroupKey()) && notificationEntry3.mDismissState == NotificationEntry.DismissState.PARENT_DISMISSED) {
                        NotificationEntry.DismissState dismissState3 = NotificationEntry.DismissState.NOT_DISMISSED;
                        Objects.requireNonNull(dismissState3);
                        notificationEntry3.mDismissState = dismissState3;
                    }
                }
            }
        }
        cancelLifetimeExtension$1(notificationEntry);
        cancelDismissInterception(notificationEntry);
        notificationEntry.mCancellationReason = -1;
        notificationEntry.setSbn(statusBarNotification);
        ((ArrayDeque) this.mEventQueue).add(new BindEntryEvent(notificationEntry, statusBarNotification));
        if (notificationEntry.isInsignificant() && notificationEntry.mKey.contains("com.android.systemui")) {
            notificationEntry.mIsReaded = true;
        } else if (notificationEntry.mIsReaded) {
            notificationEntry.mIsReaded = false;
        }
        notifCollectionLogger.getClass();
        LogLevel logLevel3 = LogLevel.INFO;
        NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda32 = new NotifCollectionLogger$$ExternalSyntheticLambda3(4);
        LogBuffer logBuffer3 = notifCollectionLogger.buffer;
        LogMessage logMessageObtain3 = logBuffer3.obtain("NotifCollection", logLevel3, notifCollectionLogger$$ExternalSyntheticLambda32, null);
        ((LogMessageImpl) logMessageObtain3).str1 = NotificationUtils.logKey(notificationEntry);
        logBuffer3.commit(logMessageObtain3);
        ((ArrayDeque) this.mEventQueue).add(new EntryUpdatedEvent(notificationEntry, updateSource));
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
            ArrayList arrayList = (ArrayList) this.mLifetimeExtenders;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                NotifLifetimeExtender notifLifetimeExtender = (NotifLifetimeExtender) obj;
                if (notifLifetimeExtender.maybeExtendLifetime(notificationEntry, notificationEntry.mCancellationReason)) {
                    notifCollectionLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(1);
                    LogBuffer logBuffer = notifCollectionLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
                    logMessageImpl.str2 = notifLifetimeExtender.getName();
                    logBuffer.commit(logMessageObtain);
                    ((ArrayList) notificationEntry.mLifetimeExtenders).add(notifLifetimeExtender);
                }
            }
            this.mAmDispatchingToOtherCode = false;
        }
        if (((ArrayList) notificationEntry.mLifetimeExtenders).size() > 0) {
            return false;
        }
        notifCollectionLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(14);
        LogBuffer logBuffer2 = notifCollectionLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtils.logKey(notificationEntry);
        logBuffer2.commit(logMessageObtain2);
        ArrayMap arrayMap = (ArrayMap) this.mNotificationSet;
        String str = notificationEntry.mKey;
        arrayMap.remove(str);
        cancelDismissInterception(notificationEntry);
        ((ArrayDeque) this.mEventQueue).add(new EntryRemovedEvent(notificationEntry, notificationEntry.mCancellationReason));
        ((ArrayDeque) this.mEventQueue).add(new CleanUpEntryEvent(notificationEntry));
        FutureDismissal futureDismissal = (FutureDismissal) this.mFutureDismissals.remove(str);
        if (futureDismissal != null) {
            int i3 = notificationEntry.mCancellationReason;
            Assert.isMainThread();
            if (futureDismissal.mDidSystemServerCancel) {
                NotifCollectionLogger notifCollectionLogger2 = NotifCollection.this.mLogger;
                notifCollectionLogger2.getClass();
                LogLevel logLevel3 = LogLevel.WARNING;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda02 = new NotifCollectionLogger$$ExternalSyntheticLambda0(19);
                LogBuffer logBuffer3 = notifCollectionLogger2.buffer;
                LogMessage logMessageObtain3 = logBuffer3.obtain("NotifCollection", logLevel3, notifCollectionLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) logMessageObtain3).str1 = futureDismissal.mLabel;
                logBuffer3.commit(logMessageObtain3);
                return true;
            }
            NotifCollectionLogger notifCollectionLogger3 = NotifCollection.this.mLogger;
            notifCollectionLogger3.getClass();
            LogLevel logLevel4 = LogLevel.DEBUG;
            NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda03 = new NotifCollectionLogger$$ExternalSyntheticLambda0(24);
            LogBuffer logBuffer4 = notifCollectionLogger3.buffer;
            LogMessage logMessageObtain4 = logBuffer4.obtain("NotifCollection", logLevel4, notifCollectionLogger$$ExternalSyntheticLambda03, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain4;
            logMessageImpl2.str1 = futureDismissal.mLabel;
            logMessageImpl2.int1 = i3;
            logBuffer4.commit(logMessageObtain4);
            futureDismissal.mDidSystemServerCancel = true;
        }
        return true;
    }

    public final void updateDismissInterceptors(NotificationEntry notificationEntry) {
        ((ArrayList) notificationEntry.mDismissInterceptors).clear();
        this.mAmDispatchingToOtherCode = true;
        ArrayList arrayList = (ArrayList) this.mDismissInterceptors;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            NotifDismissInterceptor notifDismissInterceptor = (NotifDismissInterceptor) obj;
            if (notifDismissInterceptor.shouldInterceptDismissal(notificationEntry)) {
                ((ArrayList) notificationEntry.mDismissInterceptors).add(notifDismissInterceptor);
            }
        }
        this.mAmDispatchingToOtherCode = false;
    }
}
