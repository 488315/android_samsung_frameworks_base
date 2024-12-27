package com.android.server.notification;

import android.util.Slog;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
