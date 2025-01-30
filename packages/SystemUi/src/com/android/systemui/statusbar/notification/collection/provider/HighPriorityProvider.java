package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HighPriorityProvider {
    public final GroupMembershipManager mGroupMembershipManager;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;

    public HighPriorityProvider(PeopleNotificationIdentifier peopleNotificationIdentifier, GroupMembershipManager groupMembershipManager) {
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0057, code lost:
    
        if (r1.mSbn.getNotification().isStyle(android.app.Notification.MessagingStyle.class) == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
    
        if (r2 != false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006e, code lost:
    
        if (((com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r3).isGroupSummary((com.android.systemui.statusbar.notification.collection.NotificationEntry) r7) == false) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isHighPriority(ListEntry listEntry, boolean z) {
        NotificationEntry representativeEntry;
        boolean z2;
        boolean z3;
        if (listEntry == null || (representativeEntry = listEntry.getRepresentativeEntry()) == null) {
            return false;
        }
        if (representativeEntry.mRanking.getImportance() < 3) {
            if (z) {
                if (!(representativeEntry.mRanking.getChannel() != null && representativeEntry.mRanking.getChannel().hasUserSetImportance())) {
                    if (!representativeEntry.mSbn.getNotification().isMediaNotification()) {
                        if (!(((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(representativeEntry) != 0)) {
                        }
                    }
                    z3 = true;
                }
                z3 = false;
            }
            boolean z4 = listEntry instanceof NotificationEntry;
            GroupMembershipManager groupMembershipManager = this.mGroupMembershipManager;
            if (z4) {
            }
            List<NotificationEntry> children = ((GroupMembershipManagerImpl) groupMembershipManager).getChildren(listEntry);
            if (children != null) {
                for (NotificationEntry notificationEntry : children) {
                    if (notificationEntry != listEntry && isHighPriority(notificationEntry, z)) {
                        z2 = true;
                        break;
                    }
                }
            }
            z2 = false;
            if (!z2 && representativeEntry.mSbn.getNotification().semPriority <= 0) {
                return false;
            }
        }
        return true;
    }
}
