package com.android.systemui.statusbar.notification.collection.coalescer;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.UseElapsedRealtimeForCreationTime;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda3;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class GroupCoalescer implements Dumpable, PipelineDumpable {
    public final Map mBatches;
    public final SystemClock mClock;
    public final Map mCoalescedEvents;
    public final GroupCoalescer$$ExternalSyntheticLambda0 mEventComparator;
    public NotifCollection.AnonymousClass1 mHandler;
    public final AnonymousClass1 mListener;
    public final GroupCoalescerLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final long mMaxGroupLingerDuration;
    public final long mMinGroupLingerDuration;

    /* renamed from: -$$Nest$mapplyRanking, reason: not valid java name */
    public static void m2967$$Nest$mapplyRanking(GroupCoalescer groupCoalescer, NotificationListenerService.RankingMap rankingMap) {
        for (CoalescedEvent coalescedEvent : ((ArrayMap) groupCoalescer.mCoalescedEvents).values()) {
            NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
            if (rankingMap.getRanking(coalescedEvent.key, ranking)) {
                coalescedEvent.ranking = ranking;
            } else {
                GroupCoalescerLogger groupCoalescerLogger = groupCoalescer.mLogger;
                groupCoalescerLogger.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                GroupCoalescerLogger$$ExternalSyntheticLambda0 groupCoalescerLogger$$ExternalSyntheticLambda0 = new GroupCoalescerLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = groupCoalescerLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = coalescedEvent.key;
                logBuffer.commit(logMessageObtain);
            }
        }
    }

    /* renamed from: -$$Nest$mmaybeEmitBatch, reason: not valid java name */
    public static void m2968$$Nest$mmaybeEmitBatch(GroupCoalescer groupCoalescer, StatusBarNotification statusBarNotification) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) ((ArrayMap) groupCoalescer.mCoalescedEvents).get(statusBarNotification.getKey());
        EventBatch eventBatch = (EventBatch) ((ArrayMap) groupCoalescer.mBatches).get(statusBarNotification.getGroupKey());
        int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
        long jUptimeMillis = groupCoalescer.mClock.uptimeMillis();
        GroupCoalescerLogger groupCoalescerLogger = groupCoalescer.mLogger;
        if (coalescedEvent == null) {
            if (eventBatch == null || jUptimeMillis - eventBatch.mCreatedTimestamp < groupCoalescer.mMaxGroupLingerDuration) {
                return;
            }
            String key = statusBarNotification.getKey();
            groupCoalescerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            GroupCoalescerLogger$$ExternalSyntheticLambda0 groupCoalescerLogger$$ExternalSyntheticLambda0 = new GroupCoalescerLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = groupCoalescerLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = key;
            logMessageImpl.str2 = eventBatch.mGroupKey;
            logBuffer.commit(logMessageObtain);
            groupCoalescer.emitBatch(eventBatch);
            return;
        }
        String key2 = statusBarNotification.getKey();
        EventBatch eventBatch2 = coalescedEvent.batch;
        Objects.requireNonNull(eventBatch2);
        groupCoalescerLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        GroupCoalescerLogger$$ExternalSyntheticLambda0 groupCoalescerLogger$$ExternalSyntheticLambda02 = new GroupCoalescerLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer2 = groupCoalescerLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("GroupCoalescer", logLevel2, groupCoalescerLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
        logMessageImpl2.str1 = key2;
        logMessageImpl2.str2 = eventBatch2.mGroupKey;
        logBuffer2.commit(logMessageObtain2);
        EventBatch eventBatch3 = coalescedEvent.batch;
        Objects.requireNonNull(eventBatch3);
        groupCoalescer.emitBatch(eventBatch3);
    }

    public GroupCoalescer(DelayableExecutor delayableExecutor, SystemClock systemClock, GroupCoalescerLogger groupCoalescerLogger) {
        this(delayableExecutor, systemClock, groupCoalescerLogger, 200L, 500L);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
        long jUptimeMillis = this.mClock.uptimeMillis();
        printWriter.println();
        printWriter.println("Coalesced notifications:");
        int i2 = 0;
        for (EventBatch eventBatch : ((ArrayMap) this.mBatches).values()) {
            printWriter.println("   Batch " + eventBatch.mGroupKey + ":");
            printWriter.println("       Created " + (jUptimeMillis - eventBatch.mCreatedTimestamp) + "ms ago");
            ArrayList arrayList = (ArrayList) eventBatch.mMembers;
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                printWriter.println("       " + ((CoalescedEvent) obj).key);
                i2++;
            }
        }
        if (i2 != ((ArrayMap) this.mCoalescedEvents).size()) {
            printWriter.println("    ERROR: batches contain " + ((ArrayMap) this.mCoalescedEvents).size() + " events but am tracking " + ((ArrayMap) this.mCoalescedEvents).size() + " total events");
            printWriter.println("    All tracked events:");
            Iterator it = ((ArrayMap) this.mCoalescedEvents).values().iterator();
            while (it.hasNext()) {
                CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("        "), ((CoalescedEvent) it.next()).key, printWriter);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mHandler, "handler");
    }

    public final void emitBatch(EventBatch eventBatch) {
        int i = 0;
        ArrayMap arrayMap = (ArrayMap) this.mBatches;
        String str = eventBatch.mGroupKey;
        if (eventBatch != arrayMap.get(str)) {
            throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Cannot emit out-of-date batch ", str));
        }
        if (((ArrayList) eventBatch.mMembers).isEmpty()) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Batch ", str, " cannot be empty"));
        }
        Runnable runnable = eventBatch.mCancelShortTimeout;
        if (runnable != null) {
            runnable.run();
            eventBatch.mCancelShortTimeout = null;
        }
        ((ArrayMap) this.mBatches).remove(str);
        ArrayList arrayList = new ArrayList(eventBatch.mMembers);
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
            ((ArrayMap) this.mCoalescedEvents).remove(coalescedEvent.key);
            coalescedEvent.batch = null;
        }
        arrayList.sort(this.mEventComparator);
        int i3 = UseElapsedRealtimeForCreationTime.$r8$clinit;
        long jUptimeMillis = this.mClock.uptimeMillis() - eventBatch.mCreatedTimestamp;
        int size2 = ((ArrayList) eventBatch.mMembers).size();
        GroupCoalescerLogger groupCoalescerLogger = this.mLogger;
        groupCoalescerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        GroupCoalescerLogger$$ExternalSyntheticLambda0 groupCoalescerLogger$$ExternalSyntheticLambda0 = new GroupCoalescerLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = groupCoalescerLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = size2;
        logMessageImpl.long1 = jUptimeMillis;
        logBuffer.commit(logMessageObtain);
        NotifCollection.AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.getClass();
        int i4 = NotifCollection.$r8$clinit;
        NotifCollection notifCollection = NotifCollection.this;
        notifCollection.getClass();
        Assert.isMainThread();
        String groupKey = ((CoalescedEvent) arrayList.get(0)).sbn.getGroupKey();
        int size3 = arrayList.size();
        NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
        notifCollectionLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(9);
        LogBuffer logBuffer2 = notifCollectionLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
        logMessageImpl2.str1 = NotificationUtils.logKey(groupKey);
        logMessageImpl2.int1 = size3;
        logBuffer2.commit(logMessageObtain2);
        int size4 = arrayList.size();
        while (i < size4) {
            Object obj2 = arrayList.get(i);
            i++;
            CoalescedEvent coalescedEvent2 = (CoalescedEvent) obj2;
            notifCollection.postNotification(coalescedEvent2.sbn, coalescedEvent2.ranking);
        }
        notifCollection.dispatchEventsAndRebuildList("onNotificationGroupPosted");
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer$1] */
    public GroupCoalescer(DelayableExecutor delayableExecutor, SystemClock systemClock, GroupCoalescerLogger groupCoalescerLogger, long j, long j2) {
        this.mCoalescedEvents = new ArrayMap();
        this.mBatches = new ArrayMap();
        this.mListener = new NotificationListener.NotificationHandler() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer.1
            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                GroupCoalescer.this.mHandler.onNotificationChannelModified(str, userHandle, notificationChannel, i);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
                final GroupCoalescer groupCoalescer = GroupCoalescer.this;
                GroupCoalescer.m2968$$Nest$mmaybeEmitBatch(groupCoalescer, statusBarNotification);
                GroupCoalescer.m2967$$Nest$mapplyRanking(groupCoalescer, rankingMap);
                if (((ArrayMap) groupCoalescer.mCoalescedEvents).containsKey(statusBarNotification.getKey())) {
                    throw new IllegalStateException("Notification has already been coalesced: " + statusBarNotification.getKey());
                }
                if (!statusBarNotification.isGroup()) {
                    groupCoalescer.mHandler.onNotificationPosted(statusBarNotification, rankingMap);
                    return;
                }
                String groupKey = statusBarNotification.getGroupKey();
                EventBatch eventBatch = (EventBatch) ((ArrayMap) groupCoalescer.mBatches).get(groupKey);
                if (eventBatch == null) {
                    int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
                    eventBatch = new EventBatch(groupCoalescer.mClock.uptimeMillis(), groupKey);
                    ((ArrayMap) groupCoalescer.mBatches).put(groupKey, eventBatch);
                }
                final EventBatch eventBatch2 = eventBatch;
                String key = statusBarNotification.getKey();
                int size = ((ArrayList) eventBatch2.mMembers).size();
                String key2 = statusBarNotification.getKey();
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                if (!rankingMap.getRanking(key2, ranking)) {
                    throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Ranking map does not contain key ", key2));
                }
                CoalescedEvent coalescedEvent = new CoalescedEvent(key, size, statusBarNotification, ranking, eventBatch2);
                ((ArrayMap) groupCoalescer.mCoalescedEvents).put(coalescedEvent.key, coalescedEvent);
                ((ArrayList) eventBatch2.mMembers).add(coalescedEvent);
                Runnable runnable = eventBatch2.mCancelShortTimeout;
                if (runnable != null) {
                    runnable.run();
                }
                eventBatch2.mCancelShortTimeout = groupCoalescer.mMainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        GroupCoalescer groupCoalescer2 = groupCoalescer;
                        EventBatch eventBatch3 = eventBatch2;
                        groupCoalescer2.getClass();
                        eventBatch3.mCancelShortTimeout = null;
                        groupCoalescer2.emitBatch(eventBatch3);
                    }
                }, groupCoalescer.mMinGroupLingerDuration);
                String key3 = statusBarNotification.getKey();
                GroupCoalescerLogger groupCoalescerLogger2 = groupCoalescer.mLogger;
                groupCoalescerLogger2.getClass();
                LogLevel logLevel = LogLevel.INFO;
                GroupCoalescerLogger$$ExternalSyntheticLambda0 groupCoalescerLogger$$ExternalSyntheticLambda0 = new GroupCoalescerLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = groupCoalescerLogger2.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = key3;
                logBuffer.commit(logMessageObtain);
                groupCoalescer.mHandler.onNotificationRankingUpdate(rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                GroupCoalescer groupCoalescer = GroupCoalescer.this;
                GroupCoalescer.m2967$$Nest$mapplyRanking(groupCoalescer, rankingMap);
                groupCoalescer.mHandler.onNotificationRankingUpdate(rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                GroupCoalescer groupCoalescer = GroupCoalescer.this;
                GroupCoalescer.m2968$$Nest$mmaybeEmitBatch(groupCoalescer, statusBarNotification);
                GroupCoalescer.m2967$$Nest$mapplyRanking(groupCoalescer, rankingMap);
                groupCoalescer.mHandler.onNotificationRemoved(statusBarNotification, rankingMap, i);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationsInitialized() {
                GroupCoalescer.this.mHandler.onNotificationsInitialized();
            }
        };
        this.mEventComparator = new GroupCoalescer$$ExternalSyntheticLambda0();
        this.mMainExecutor = delayableExecutor;
        this.mClock = systemClock;
        this.mLogger = groupCoalescerLogger;
        this.mMinGroupLingerDuration = j;
        this.mMaxGroupLingerDuration = j2;
    }
}
