package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HighPriorityProvider {
    public final GroupMembershipManager mGroupMembershipManager;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;

    public HighPriorityProvider(PeopleNotificationIdentifier peopleNotificationIdentifier, GroupMembershipManager groupMembershipManager) {
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0061, code lost:
    
        if (((com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r3).isGroupSummary((com.android.systemui.statusbar.notification.collection.NotificationEntry) r6) == false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isHighPriority(com.android.systemui.statusbar.notification.collection.ListEntry r6, boolean r7) {
        /*
            r5 = this;
            r0 = 0
            if (r6 != 0) goto L4
            return r0
        L4:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r6.getRepresentativeEntry()
            if (r1 != 0) goto Lb
            return r0
        Lb:
            android.service.notification.NotificationListenerService$Ranking r2 = r1.mRanking
            int r2 = r2.getImportance()
            r3 = 3
            if (r2 >= r3) goto L8f
            if (r7 == 0) goto L51
            android.service.notification.NotificationListenerService$Ranking r2 = r1.mRanking
            android.app.NotificationChannel r2 = r2.getChannel()
            if (r2 == 0) goto L2b
            android.service.notification.NotificationListenerService$Ranking r2 = r1.mRanking
            android.app.NotificationChannel r2 = r2.getChannel()
            boolean r2 = r2.hasUserSetImportance()
            if (r2 == 0) goto L2b
            goto L51
        L2b:
            android.service.notification.StatusBarNotification r2 = r1.mSbn
            android.app.Notification r2 = r2.getNotification()
            boolean r2 = r2.isMediaNotification()
            if (r2 != 0) goto L8f
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier r2 = r5.mPeopleNotificationIdentifier
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl r2 = (com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl) r2
            int r2 = r2.getPeopleNotificationType(r1)
            if (r2 == 0) goto L42
            goto L8f
        L42:
            android.service.notification.StatusBarNotification r2 = r1.mSbn
            android.app.Notification r2 = r2.getNotification()
            java.lang.Class<android.app.Notification$MessagingStyle> r3 = android.app.Notification.MessagingStyle.class
            boolean r2 = r2.isStyle(r3)
            if (r2 == 0) goto L51
            goto L8f
        L51:
            boolean r2 = r6 instanceof com.android.systemui.statusbar.notification.collection.NotificationEntry
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r3 = r5.mGroupMembershipManager
            if (r2 == 0) goto L64
            r2 = r6
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r2
            r4 = r3
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl r4 = (com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r4
            boolean r2 = r4.isGroupSummary(r2)
            if (r2 != 0) goto L64
            goto L85
        L64:
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl r3 = (com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r3
            java.util.List r2 = r3.getChildren(r6)
            if (r2 == 0) goto L85
            java.util.Iterator r2 = r2.iterator()
        L70:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L85
            java.lang.Object r3 = r2.next()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r3
            if (r3 == r6) goto L70
            boolean r3 = r5.isHighPriority(r3, r7)
            if (r3 == 0) goto L70
            goto L8f
        L85:
            android.service.notification.StatusBarNotification r5 = r1.mSbn
            android.app.Notification r5 = r5.getNotification()
            int r5 = r5.semPriority
            if (r5 <= 0) goto L90
        L8f:
            r0 = 1
        L90:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider.isHighPriority(com.android.systemui.statusbar.notification.collection.ListEntry, boolean):boolean");
    }

    public final boolean isHighPriorityConversation(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry == null || ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(representativeEntry) == 0) {
            return false;
        }
        if (representativeEntry.mRanking.getImportance() >= 3) {
            return true;
        }
        if (listEntry instanceof GroupEntry) {
            return ((GroupEntry) listEntry).mUnmodifiableChildren.stream().anyMatch(new HighPriorityProvider$$ExternalSyntheticLambda0());
        }
        return false;
    }
}
