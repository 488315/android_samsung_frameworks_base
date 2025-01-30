package com.android.systemui.statusbar.notification.collection;

import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda2 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        NotificationEntry notificationEntry2 = (NotificationEntry) obj2;
        notificationEntry.getClass();
        int rank = notificationEntry.mRanking.getRank();
        notificationEntry2.getClass();
        int compare = Integer.compare(rank, notificationEntry2.mRanking.getRank());
        return compare != 0 ? compare : Long.compare(notificationEntry.mSbn.getNotification().when, notificationEntry2.mSbn.getNotification().when) * (-1);
    }
}
