package com.android.server.notification;

import java.util.Comparator;

public final class NotificationTimeComparator implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(
                        ((NotificationRecord) obj).mRankingTimeMs,
                        ((NotificationRecord) obj2).mRankingTimeMs)
                * (-1);
    }
}
