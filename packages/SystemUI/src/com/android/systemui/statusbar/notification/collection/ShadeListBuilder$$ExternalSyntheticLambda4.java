package com.android.systemui.statusbar.notification.collection;

import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
