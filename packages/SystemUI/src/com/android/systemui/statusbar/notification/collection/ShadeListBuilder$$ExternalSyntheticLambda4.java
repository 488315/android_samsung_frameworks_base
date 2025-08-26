package com.android.systemui.statusbar.notification.collection;

import java.util.Comparator;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda4 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        NotificationEntry notificationEntry2 = (NotificationEntry) obj2;
        int i = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
        notificationEntry.getClass();
        int rank = notificationEntry.mRanking.getRank();
        notificationEntry2.getClass();
        int iCompare = Integer.compare(rank, notificationEntry2.mRanking.getRank());
        return iCompare != 0 ? iCompare : Long.compare(notificationEntry.mSbn.getNotification().getWhen(), notificationEntry2.mSbn.getNotification().getWhen()) * (-1);
    }
}
