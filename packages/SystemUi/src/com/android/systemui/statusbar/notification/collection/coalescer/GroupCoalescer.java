package com.android.systemui.statusbar.notification.collection.coalescer;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.Dumpable;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GroupCoalescer implements Dumpable, PipelineDumpable {
    public final Map mBatches;
    public final SystemClock mClock;
    public final Map mCoalescedEvents;
    public final GroupCoalescer$$ExternalSyntheticLambda0 mEventComparator;
    public NotifCollection.C27901 mHandler;
    public final C27951 mListener;
    public final GroupCoalescerLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final long mMaxGroupLingerDuration;
    public final long mMinGroupLingerDuration;

    /* renamed from: -$$Nest$mapplyRanking, reason: not valid java name */
    public static void m1707$$Nest$mapplyRanking(GroupCoalescer groupCoalescer, NotificationListenerService.RankingMap rankingMap) {
        for (CoalescedEvent coalescedEvent : ((ArrayMap) groupCoalescer.mCoalescedEvents).values()) {
            NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
            if (rankingMap.getRanking(coalescedEvent.key, ranking)) {
                coalescedEvent.ranking = ranking;
            } else {
                GroupCoalescerLogger groupCoalescerLogger = groupCoalescer.mLogger;
                groupCoalescerLogger.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                GroupCoalescerLogger$logMissingRanking$2 groupCoalescerLogger$logMissingRanking$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$logMissingRanking$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("RankingMap is missing an entry for coalesced notification ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = groupCoalescerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$logMissingRanking$2, null);
                obtain.setStr1(coalescedEvent.key);
                logBuffer.commit(obtain);
            }
        }
    }

    /* renamed from: -$$Nest$mmaybeEmitBatch, reason: not valid java name */
    public static void m1708$$Nest$mmaybeEmitBatch(GroupCoalescer groupCoalescer, StatusBarNotification statusBarNotification) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) ((ArrayMap) groupCoalescer.mCoalescedEvents).get(statusBarNotification.getKey());
        EventBatch eventBatch = (EventBatch) ((ArrayMap) groupCoalescer.mBatches).get(statusBarNotification.getGroupKey());
        GroupCoalescerLogger groupCoalescerLogger = groupCoalescer.mLogger;
        if (coalescedEvent != null) {
            String key = statusBarNotification.getKey();
            EventBatch eventBatch2 = coalescedEvent.batch;
            Objects.requireNonNull(eventBatch2);
            groupCoalescerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            GroupCoalescerLogger$logEarlyEmit$2 groupCoalescerLogger$logEarlyEmit$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$logEarlyEmit$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m32m("Modification of notif ", logMessage.getStr1(), " triggered early emit of batched group ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = groupCoalescerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$logEarlyEmit$2, null);
            obtain.setStr1(key);
            obtain.setStr2(eventBatch2.mGroupKey);
            logBuffer.commit(obtain);
            EventBatch eventBatch3 = coalescedEvent.batch;
            Objects.requireNonNull(eventBatch3);
            groupCoalescer.emitBatch(eventBatch3);
            return;
        }
        if (eventBatch != null) {
            ((SystemClockImpl) groupCoalescer.mClock).getClass();
            if (android.os.SystemClock.uptimeMillis() - eventBatch.mCreatedTimestamp >= groupCoalescer.mMaxGroupLingerDuration) {
                String key2 = statusBarNotification.getKey();
                groupCoalescerLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                GroupCoalescerLogger$logMaxBatchTimeout$2 groupCoalescerLogger$logMaxBatchTimeout$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$logMaxBatchTimeout$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return FontProvider$$ExternalSyntheticOutline0.m32m("Modification of notif ", logMessage.getStr1(), " triggered TIMEOUT emit of batched group ", logMessage.getStr2());
                    }
                };
                LogBuffer logBuffer2 = groupCoalescerLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("GroupCoalescer", logLevel2, groupCoalescerLogger$logMaxBatchTimeout$2, null);
                obtain2.setStr1(key2);
                obtain2.setStr2(eventBatch.mGroupKey);
                logBuffer2.commit(obtain2);
                groupCoalescer.emitBatch(eventBatch);
            }
        }
    }

    public GroupCoalescer(DelayableExecutor delayableExecutor, SystemClock systemClock, GroupCoalescerLogger groupCoalescerLogger) {
        this(delayableExecutor, systemClock, groupCoalescerLogger, 200L, 500L);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ((SystemClockImpl) this.mClock).getClass();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        printWriter.println();
        printWriter.println("Coalesced notifications:");
        int i = 0;
        for (EventBatch eventBatch : ((ArrayMap) this.mBatches).values()) {
            printWriter.println("   Batch " + eventBatch.mGroupKey + ":");
            printWriter.println("       Created " + (uptimeMillis - eventBatch.mCreatedTimestamp) + "ms ago");
            Iterator it = ((ArrayList) eventBatch.mMembers).iterator();
            while (it.hasNext()) {
                printWriter.println("       " + ((CoalescedEvent) it.next()).key);
                i++;
            }
        }
        Map map = this.mCoalescedEvents;
        if (i != ((ArrayMap) map).size()) {
            printWriter.println("    ERROR: batches contain " + ((ArrayMap) map).size() + " events but am tracking " + ((ArrayMap) map).size() + " total events");
            printWriter.println("    All tracked events:");
            Iterator it2 = ((ArrayMap) map).values().iterator();
            while (it2.hasNext()) {
                KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("        "), ((CoalescedEvent) it2.next()).key, printWriter);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mHandler, "handler");
    }

    public final void emitBatch(EventBatch eventBatch) {
        ArrayMap arrayMap = (ArrayMap) this.mBatches;
        String str = eventBatch.mGroupKey;
        if (eventBatch != arrayMap.get(str)) {
            throw new IllegalStateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Cannot emit out-of-date batch ", str));
        }
        List list = eventBatch.mMembers;
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Batch ", str, " cannot be empty"));
        }
        ExecutorImpl.ExecutionToken executionToken = eventBatch.mCancelShortTimeout;
        if (executionToken != null) {
            executionToken.run();
            eventBatch.mCancelShortTimeout = null;
        }
        arrayMap.remove(str);
        ArrayList arrayList2 = new ArrayList(list);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            CoalescedEvent coalescedEvent = (CoalescedEvent) it.next();
            ((ArrayMap) this.mCoalescedEvents).remove(coalescedEvent.key);
            coalescedEvent.batch = null;
        }
        arrayList2.sort(this.mEventComparator);
        ((SystemClockImpl) this.mClock).getClass();
        long uptimeMillis = android.os.SystemClock.uptimeMillis() - eventBatch.mCreatedTimestamp;
        int size = arrayList.size();
        GroupCoalescerLogger groupCoalescerLogger = this.mLogger;
        groupCoalescerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        GroupCoalescerLogger$logEmitBatch$2 groupCoalescerLogger$logEmitBatch$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$logEmitBatch$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                long long1 = logMessage.getLong1();
                StringBuilder m92m = AbstractC0950x8906c950.m92m("Emitting batch for group ", str1, " size=", int1, " age=");
                m92m.append(long1);
                m92m.append("ms");
                return m92m.toString();
            }
        };
        LogBuffer logBuffer = groupCoalescerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$logEmitBatch$2, null);
        obtain.setStr1(str);
        obtain.setInt1(size);
        obtain.setLong1(uptimeMillis);
        logBuffer.commit(obtain);
        NotifCollection.C27901 c27901 = this.mHandler;
        c27901.getClass();
        int i = NotifCollection.$r8$clinit;
        NotifCollection notifCollection = NotifCollection.this;
        notifCollection.getClass();
        Assert.isMainThread();
        String groupKey = ((CoalescedEvent) arrayList2.get(0)).sbn.getGroupKey();
        notifCollection.mLogger.logNotifGroupPosted(arrayList2.size(), groupKey);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            CoalescedEvent coalescedEvent2 = (CoalescedEvent) it2.next();
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
                boolean z;
                final GroupCoalescer groupCoalescer = GroupCoalescer.this;
                GroupCoalescer.m1708$$Nest$mmaybeEmitBatch(groupCoalescer, statusBarNotification);
                GroupCoalescer.m1707$$Nest$mapplyRanking(groupCoalescer, rankingMap);
                ArrayMap arrayMap = (ArrayMap) groupCoalescer.mCoalescedEvents;
                if (arrayMap.containsKey(statusBarNotification.getKey())) {
                    throw new IllegalStateException("Notification has already been coalesced: " + statusBarNotification.getKey());
                }
                if (statusBarNotification.isGroup()) {
                    String groupKey = statusBarNotification.getGroupKey();
                    ArrayMap arrayMap2 = (ArrayMap) groupCoalescer.mBatches;
                    final EventBatch eventBatch = (EventBatch) arrayMap2.get(groupKey);
                    if (eventBatch == null) {
                        ((SystemClockImpl) groupCoalescer.mClock).getClass();
                        eventBatch = new EventBatch(android.os.SystemClock.uptimeMillis(), groupKey);
                        arrayMap2.put(groupKey, eventBatch);
                    }
                    String key = statusBarNotification.getKey();
                    ArrayList arrayList = (ArrayList) eventBatch.mMembers;
                    int size = arrayList.size();
                    String key2 = statusBarNotification.getKey();
                    NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                    if (!rankingMap.getRanking(key2, ranking)) {
                        throw new IllegalArgumentException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Ranking map does not contain key ", key2));
                    }
                    CoalescedEvent coalescedEvent = new CoalescedEvent(key, size, statusBarNotification, ranking, eventBatch);
                    arrayMap.put(coalescedEvent.key, coalescedEvent);
                    arrayList.add(coalescedEvent);
                    ExecutorImpl.ExecutionToken executionToken = eventBatch.mCancelShortTimeout;
                    if (executionToken != null) {
                        executionToken.run();
                    }
                    eventBatch.mCancelShortTimeout = groupCoalescer.mMainExecutor.executeDelayed(groupCoalescer.mMinGroupLingerDuration, new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            GroupCoalescer groupCoalescer2 = GroupCoalescer.this;
                            EventBatch eventBatch2 = eventBatch;
                            groupCoalescer2.getClass();
                            eventBatch2.mCancelShortTimeout = null;
                            groupCoalescer2.emitBatch(eventBatch2);
                        }
                    });
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    groupCoalescer.mHandler.onNotificationPosted(statusBarNotification, rankingMap);
                    return;
                }
                String key3 = statusBarNotification.getKey();
                GroupCoalescerLogger groupCoalescerLogger2 = groupCoalescer.mLogger;
                groupCoalescerLogger2.getClass();
                LogLevel logLevel = LogLevel.INFO;
                GroupCoalescerLogger$logEventCoalesced$2 groupCoalescerLogger$logEventCoalesced$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$logEventCoalesced$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("COALESCED: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = groupCoalescerLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("GroupCoalescer", logLevel, groupCoalescerLogger$logEventCoalesced$2, null);
                obtain.setStr1(key3);
                logBuffer.commit(obtain);
                groupCoalescer.mHandler.onNotificationRankingUpdate(rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                GroupCoalescer groupCoalescer = GroupCoalescer.this;
                GroupCoalescer.m1707$$Nest$mapplyRanking(groupCoalescer, rankingMap);
                groupCoalescer.mHandler.onNotificationRankingUpdate(rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                GroupCoalescer groupCoalescer = GroupCoalescer.this;
                GroupCoalescer.m1708$$Nest$mmaybeEmitBatch(groupCoalescer, statusBarNotification);
                GroupCoalescer.m1707$$Nest$mapplyRanking(groupCoalescer, rankingMap);
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
