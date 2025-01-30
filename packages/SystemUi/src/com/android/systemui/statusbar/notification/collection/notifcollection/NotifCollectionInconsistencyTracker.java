package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifCollectionInconsistencyTracker {
    public boolean attached;
    public Function0 coalescedKeySetAccessor;
    public Function0 collectedKeySetAccessor;
    public final NotifCollectionLogger logger;
    public Set missingNotifications;
    public Set notificationsWithoutRankings;

    public NotifCollectionInconsistencyTracker(NotifCollectionLogger notifCollectionLogger) {
        this.logger = notifCollectionLogger;
        EmptySet emptySet = EmptySet.INSTANCE;
        this.notificationsWithoutRankings = emptySet;
        this.missingNotifications = emptySet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void logNewMissingNotifications(NotificationListenerService.RankingMap rankingMap) {
        Function0 function0 = this.collectedKeySetAccessor;
        if (function0 == null) {
            function0 = null;
        }
        final Set set = (Set) function0.invoke();
        Function0 function02 = this.coalescedKeySetAccessor;
        final Set set2 = (Set) (function02 != null ? function02 : null).invoke();
        FilteringSequence filter = SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(ArraysKt___ArraysKt.asSequence(rankingMap.getOrderedKeys()), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$logNewMissingNotifications$newMissingNotifications$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(!set.contains((String) obj));
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$logNewMissingNotifications$newMissingNotifications$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(!set2.contains((String) obj));
            }
        });
        Set linkedHashSet = new LinkedHashSet();
        Iterator it = filter.iterator();
        while (true) {
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = (FilteringSequence$iterator$1) it;
            if (!filteringSequence$iterator$1.hasNext()) {
                break;
            } else {
                linkedHashSet.add(filteringSequence$iterator$1.next());
            }
        }
        int size = linkedHashSet.size();
        if (size == 0) {
            linkedHashSet = EmptySet.INSTANCE;
        } else if (size == 1) {
            linkedHashSet = Collections.singleton(linkedHashSet.iterator().next());
        }
        maybeLogMissingNotifications(this.missingNotifications, linkedHashSet);
        this.missingNotifications = linkedHashSet;
    }

    public final void maybeLogInconsistentRankings(Set<String> set, Map<String, NotificationEntry> map, NotificationListenerService.RankingMap rankingMap) {
        if ((set.isEmpty() && map.isEmpty()) || Intrinsics.areEqual(set, map.keySet())) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            String str2 = ArraysKt___ArraysKt.contains(rankingMap.getOrderedKeys(), str) ? map.containsKey(str) ^ true ? str : null : null;
            if (str2 != null) {
                arrayList.add(str2);
            }
        }
        List sorted = CollectionsKt___CollectionsKt.sorted(arrayList);
        boolean z = !sorted.isEmpty();
        NotifCollectionLogger notifCollectionLogger = this.logger;
        if (z) {
            int size = map.size();
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$logRecoveredRankings$2 notifCollectionLogger$logRecoveredRankings$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logRecoveredRankings$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return "Ranking update now contains rankings for " + logMessage.getInt1() + " previously inconsistent entries: " + logMessage.getStr1();
                }
            };
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logRecoveredRankings$2, null);
            obtain.setInt1(size);
            obtain.setInt1(sorted.size());
            obtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(sorted, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logRecoveredRankings$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String logKey = NotificationUtils.logKey((String) obj);
                    return logKey != null ? logKey : "null";
                }
            }, 31));
            logBuffer.commit(obtain);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<String, NotificationEntry> entry : map.entrySet()) {
            String key = entry.getKey();
            NotificationEntry value = entry.getValue();
            if (!(!set.contains(key))) {
                value = null;
            }
            if (value != null) {
                arrayList2.add(value);
            }
        }
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$maybeLogInconsistentRankings$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(((NotificationEntry) obj).mKey, ((NotificationEntry) obj2).mKey);
            }
        });
        if (!sortedWith.isEmpty()) {
            int size2 = map.size();
            notifCollectionLogger.getClass();
            LogLevel logLevel2 = LogLevel.WARNING;
            NotifCollectionLogger$logMissingRankings$2 notifCollectionLogger$logMissingRankings$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logMissingRankings$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    int int1 = logMessage.getInt1();
                    int int2 = logMessage.getInt2();
                    String str1 = logMessage.getStr1();
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Ranking update is missing ranking for ", int1, " entries (", int2, " new): ");
                    m45m.append(str1);
                    return m45m.toString();
                }
            };
            LogBuffer logBuffer2 = notifCollectionLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$logMissingRankings$2, null);
            obtain2.setInt1(size2);
            obtain2.setInt2(sortedWith.size());
            obtain2.setStr1(CollectionsKt___CollectionsKt.joinToString$default(sortedWith, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logMissingRankings$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String logKey = NotificationUtilsKt.getLogKey((NotificationEntry) obj);
                    return logKey != null ? logKey : "null";
                }
            }, 31));
            logBuffer2.commit(obtain2);
            LogMessage obtain3 = logBuffer2.obtain("NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logMissingRankings$4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Ranking map contents: ", ((LogMessage) obj).getStr1());
                }
            }, null);
            String[] orderedKeys = rankingMap.getOrderedKeys();
            ArrayList arrayList3 = new ArrayList(orderedKeys.length);
            for (String str3 : orderedKeys) {
                String logKey = NotificationUtils.logKey(str3);
                if (logKey == null) {
                    logKey = "null";
                }
                arrayList3.add(logKey);
            }
            obtain3.setStr1(arrayList3.toString());
            logBuffer2.commit(obtain3);
        }
    }

    public final void maybeLogMissingNotifications(Set<String> set, Set<String> set2) {
        if ((set.isEmpty() && set2.isEmpty()) || Intrinsics.areEqual(set, set2)) {
            return;
        }
        List sorted = CollectionsKt___CollectionsKt.sorted(SetsKt___SetsKt.minus(set, set2));
        boolean z = !sorted.isEmpty();
        NotifCollectionLogger notifCollectionLogger = this.logger;
        if (z) {
            int size = set2.size();
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$logFoundNotifications$2 notifCollectionLogger$logFoundNotifications$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFoundNotifications$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    int int1 = logMessage.getInt1();
                    int int2 = logMessage.getInt2();
                    String str1 = logMessage.getStr1();
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Collection missing ", int1, " entries in ranking update. Just found ", int2, ": ");
                    m45m.append(str1);
                    return m45m.toString();
                }
            };
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFoundNotifications$2, null);
            obtain.setInt1(size);
            obtain.setInt2(sorted.size());
            obtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(sorted, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFoundNotifications$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String logKey = NotificationUtils.logKey((String) obj);
                    return logKey != null ? logKey : "null";
                }
            }, 31));
            logBuffer.commit(obtain);
        }
        List sorted2 = CollectionsKt___CollectionsKt.sorted(SetsKt___SetsKt.minus(set2, set));
        if (!sorted2.isEmpty()) {
            int size2 = set2.size();
            notifCollectionLogger.getClass();
            LogLevel logLevel2 = LogLevel.WARNING;
            NotifCollectionLogger$logMissingNotifications$2 notifCollectionLogger$logMissingNotifications$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logMissingNotifications$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    int int1 = logMessage.getInt1();
                    int int2 = logMessage.getInt2();
                    String str1 = logMessage.getStr1();
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Collection missing ", int1, " entries in ranking update. Just lost ", int2, ": ");
                    m45m.append(str1);
                    return m45m.toString();
                }
            };
            LogBuffer logBuffer2 = notifCollectionLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$logMissingNotifications$2, null);
            obtain2.setInt1(size2);
            obtain2.setInt2(sorted2.size());
            obtain2.setStr1(CollectionsKt___CollectionsKt.joinToString$default(sorted2, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logMissingNotifications$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String logKey = NotificationUtils.logKey((String) obj);
                    return logKey != null ? logKey : "null";
                }
            }, 31));
            logBuffer2.commit(obtain2);
        }
    }
}
