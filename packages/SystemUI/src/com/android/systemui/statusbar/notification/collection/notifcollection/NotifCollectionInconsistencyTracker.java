package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda11;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* loaded from: classes3.dex */
public final class NotifCollectionInconsistencyTracker {
    public boolean attached;
    public NotifCollection$$ExternalSyntheticLambda11 coalescedKeySetAccessor;
    public NotifCollection$$ExternalSyntheticLambda11 collectedKeySetAccessor;
    public final NotifCollectionLogger logger;
    public Set missingNotifications;
    public Set notificationsWithoutRankings;

    public NotifCollectionInconsistencyTracker(NotifCollectionLogger notifCollectionLogger) {
        this.logger = notifCollectionLogger;
        EmptySet emptySet = EmptySet.INSTANCE;
        this.notificationsWithoutRankings = emptySet;
        this.missingNotifications = emptySet;
    }

    public final void maybeLogInconsistentRankings(Set<String> set, Map<String, NotificationEntry> map, NotificationListenerService.RankingMap rankingMap) {
        if ((set.isEmpty() && map.isEmpty()) || set.equals(map.keySet())) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            String str2 = ArraysKt___ArraysKt.contains(rankingMap.getOrderedKeys(), str) ? !map.containsKey(str) ? str : null : null;
            if (str2 != null) {
                arrayList.add(str2);
            }
        }
        List listSorted = CollectionsKt___CollectionsKt.sorted(arrayList);
        boolean zIsEmpty = listSorted.isEmpty();
        NotifCollectionLogger notifCollectionLogger = this.logger;
        if (!zIsEmpty) {
            int size = map.size();
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(16);
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = size;
            logMessageImpl.int1 = listSorted.size();
            logMessageImpl.str1 = CollectionsKt___CollectionsKt.joinToString$default(listSorted, null, null, null, new NotifCollectionLogger$$ExternalSyntheticLambda0(17), 31);
            logBuffer.commit(logMessageObtain);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<String, NotificationEntry> entry : map.entrySet()) {
            String key = entry.getKey();
            NotificationEntry value = entry.getValue();
            if (set.contains(key)) {
                value = null;
            }
            if (value != null) {
                arrayList2.add(value);
            }
        }
        List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$maybeLogInconsistentRankings$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(((NotificationEntry) obj).mKey, ((NotificationEntry) obj2).mKey);
            }
        });
        if (listSortedWith.isEmpty()) {
            return;
        }
        int size2 = map.size();
        notifCollectionLogger.getClass();
        LogLevel logLevel2 = LogLevel.WARNING;
        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda02 = new NotifCollectionLogger$$ExternalSyntheticLambda0(13);
        LogBuffer logBuffer2 = notifCollectionLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
        logMessageImpl2.int1 = size2;
        logMessageImpl2.int2 = listSortedWith.size();
        logMessageImpl2.str1 = CollectionsKt___CollectionsKt.joinToString$default(listSortedWith, null, null, null, new NotifCollectionLogger$$ExternalSyntheticLambda0(14), 31);
        logBuffer2.commit(logMessageObtain2);
        LogMessage logMessageObtain3 = logBuffer2.obtain("NotifCollection", LogLevel.DEBUG, new NotifCollectionLogger$$ExternalSyntheticLambda0(15), null);
        String[] orderedKeys = rankingMap.getOrderedKeys();
        ArrayList arrayList3 = new ArrayList(orderedKeys.length);
        for (String str3 : orderedKeys) {
            String strLogKey = NotificationUtils.logKey(str3);
            if (strLogKey == null) {
                strLogKey = "null";
            }
            arrayList3.add(strLogKey);
        }
        ((LogMessageImpl) logMessageObtain3).str1 = arrayList3.toString();
        logBuffer2.commit(logMessageObtain3);
    }

    public final void maybeLogMissingNotifications(Set<String> set, Set<String> set2) {
        if ((set.isEmpty() && set2.isEmpty()) || set.equals(set2)) {
            return;
        }
        List listSorted = CollectionsKt___CollectionsKt.sorted(SetsKt___SetsKt.minus((Set) set, (Iterable) set2));
        boolean zIsEmpty = listSorted.isEmpty();
        NotifCollectionLogger notifCollectionLogger = this.logger;
        if (!zIsEmpty) {
            int size = set2.size();
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(25);
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = size;
            logMessageImpl.int2 = listSorted.size();
            logMessageImpl.str1 = CollectionsKt___CollectionsKt.joinToString$default(listSorted, null, null, null, new NotifCollectionLogger$$ExternalSyntheticLambda0(26), 31);
            logBuffer.commit(logMessageObtain);
        }
        List listSorted2 = CollectionsKt___CollectionsKt.sorted(SetsKt___SetsKt.minus((Set) set2, (Iterable) set));
        if (listSorted2.isEmpty()) {
            return;
        }
        int size2 = set2.size();
        notifCollectionLogger.getClass();
        LogLevel logLevel2 = LogLevel.WARNING;
        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda02 = new NotifCollectionLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer2 = notifCollectionLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
        logMessageImpl2.int1 = size2;
        logMessageImpl2.int2 = listSorted2.size();
        logMessageImpl2.str1 = CollectionsKt___CollectionsKt.joinToString$default(listSorted2, null, null, null, new NotifCollectionLogger$$ExternalSyntheticLambda0(10), 31);
        logBuffer2.commit(logMessageObtain2);
    }
}
