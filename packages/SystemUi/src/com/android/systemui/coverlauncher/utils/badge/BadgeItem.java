package com.android.systemui.coverlauncher.utils.badge;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BadgeItem {
    public final String mInfo;
    public final List mNotificationItems = new ArrayList();
    public int mTotalCount;

    public BadgeItem(String str) {
        this.mInfo = str;
    }

    public final boolean addOrUpdateNotificationItem(NotificationItem notificationItem) {
        ArrayList arrayList = (ArrayList) this.mNotificationItems;
        int indexOf = arrayList.indexOf(notificationItem);
        NotificationItem notificationItem2 = indexOf == -1 ? null : (NotificationItem) arrayList.get(indexOf);
        if (notificationItem2 == null) {
            boolean add = arrayList.add(notificationItem);
            if (add) {
                this.mTotalCount += notificationItem.count;
            }
            return add;
        }
        int i = notificationItem2.count;
        int i2 = notificationItem.count;
        if (i == i2) {
            return false;
        }
        this.mTotalCount = (this.mTotalCount - i) + i2;
        notificationItem2.count = i2;
        return true;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof BadgeItem) {
            return this.mInfo.equals(((BadgeItem) obj).mInfo);
        }
        return false;
    }

    public final String toString() {
        return "info=" + this.mInfo + ", count=" + this.mTotalCount;
    }
}
