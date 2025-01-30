package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GroupMembershipManagerImpl implements GroupMembershipManager {
    public final List getChildren(ListEntry listEntry) {
        if (listEntry instanceof GroupEntry) {
            return ((GroupEntry) listEntry).mUnmodifiableChildren;
        }
        if (isGroupSummary(listEntry.getRepresentativeEntry())) {
            return listEntry.getRepresentativeEntry().getParent().mUnmodifiableChildren;
        }
        return null;
    }

    public final NotificationEntry getGroupSummary(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return null;
        }
        if ((notificationEntry.getParent() == GroupEntry.ROOT_ENTRY) || notificationEntry.getParent() == null) {
            return null;
        }
        return notificationEntry.getParent().mSummary;
    }

    public final boolean isGroupSummary(NotificationEntry notificationEntry) {
        return getGroupSummary(notificationEntry) == notificationEntry;
    }
}
