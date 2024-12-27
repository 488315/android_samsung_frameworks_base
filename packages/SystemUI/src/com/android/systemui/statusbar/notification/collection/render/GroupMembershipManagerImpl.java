package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;

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
