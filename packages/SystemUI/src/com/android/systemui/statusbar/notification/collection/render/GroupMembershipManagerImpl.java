package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GroupMembershipManagerImpl implements GroupMembershipManager {
    public final List getChildren(ListEntry listEntry) {
        GroupEntry parent;
        if (listEntry instanceof GroupEntry) {
            return ((GroupEntry) listEntry).mUnmodifiableChildren;
        }
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry == null || !isGroupSummary(representativeEntry) || (parent = representativeEntry.getParent()) == null) {
            return null;
        }
        return parent.mUnmodifiableChildren;
    }

    public final NotificationEntry getGroupSummary(NotificationEntry notificationEntry) {
        if (notificationEntry == null || notificationEntry.getParent() == GroupEntry.ROOT_ENTRY || notificationEntry.getParent() == null) {
            return null;
        }
        return notificationEntry.getParent().mSummary;
    }

    public final boolean isGroupSummary(NotificationEntry notificationEntry) {
        return notificationEntry.getParent() != null && notificationEntry.getParent().mSummary == notificationEntry;
    }
}
