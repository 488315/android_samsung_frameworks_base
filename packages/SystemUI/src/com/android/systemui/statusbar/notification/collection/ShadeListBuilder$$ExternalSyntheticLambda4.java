package com.android.systemui.statusbar.notification.collection;

import java.util.Comparator;

public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda4 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        NotificationEntry notificationEntry2 = (NotificationEntry) obj2;
        notificationEntry.getClass();
        int rank = notificationEntry.mRanking.getRank();
        notificationEntry2.getClass();
        int compare = Integer.compare(rank, notificationEntry2.mRanking.getRank());
        return compare != 0 ? compare : Long.compare(notificationEntry.mSbn.getNotification().getWhen(), notificationEntry2.mSbn.getNotification().getWhen()) * (-1);
    }
}
