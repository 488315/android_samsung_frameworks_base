package com.android.server.notification;

import android.util.Slog;

import java.util.Comparator;

public final class GlobalSortKeyComparator implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        NotificationRecord notificationRecord = (NotificationRecord) obj;
        NotificationRecord notificationRecord2 = (NotificationRecord) obj2;
        String str = notificationRecord.mGlobalSortKey;
        if (str == null) {
            Slog.wtf("GlobalSortComp", "Missing left global sort key: " + notificationRecord);
            return 1;
        }
        String str2 = notificationRecord2.mGlobalSortKey;
        if (str2 != null) {
            return str.compareTo(str2);
        }
        Slog.wtf("GlobalSortComp", "Missing right global sort key: " + notificationRecord2);
        return -1;
    }
}
