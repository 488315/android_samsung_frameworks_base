package com.android.systemui.coverlauncher.utils.badge;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BadgeItem {
    public final String mInfo;
    public final List mNotificationItems;
    public int mTotalCount;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BadgeItem(String str) {
        this.mInfo = str;
        ArrayList arrayList = new ArrayList();
        this.mNotificationItems = arrayList;
        arrayList.clear();
    }

    public final boolean addOrUpdateNotificationItem(NotificationItem notificationItem) {
        int indexOf = ((ArrayList) this.mNotificationItems).indexOf(notificationItem);
        NotificationItem notificationItem2 = indexOf == -1 ? null : (NotificationItem) ((ArrayList) this.mNotificationItems).get(indexOf);
        if (notificationItem2 == null) {
            boolean add = ((ArrayList) this.mNotificationItems).add(notificationItem);
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
        if (!(obj instanceof BadgeItem)) {
            return false;
        }
        return Intrinsics.areEqual(this.mInfo, ((BadgeItem) obj).mInfo);
    }

    public final String toString() {
        return "info=" + this.mInfo + ", count=" + this.mTotalCount;
    }
}
