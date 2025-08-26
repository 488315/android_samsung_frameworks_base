package com.android.systemui.coverlauncher.utils.badge;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class BadgeItem {
    public final String mInfo;
    public final List mNotificationItems;
    public int mTotalCount;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        int iIndexOf = ((ArrayList) this.mNotificationItems).indexOf(notificationItem);
        NotificationItem notificationItem2 = iIndexOf == -1 ? null : (NotificationItem) ((ArrayList) this.mNotificationItems).get(iIndexOf);
        if (notificationItem2 == null) {
            boolean zAdd = ((ArrayList) this.mNotificationItems).add(notificationItem);
            if (zAdd) {
                this.mTotalCount += notificationItem.count;
            }
            return zAdd;
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
