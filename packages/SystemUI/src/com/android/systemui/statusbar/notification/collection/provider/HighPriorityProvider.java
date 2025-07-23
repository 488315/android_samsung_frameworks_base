package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class HighPriorityProvider {
    public final GroupMembershipManager mGroupMembershipManager;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;

    public HighPriorityProvider(PeopleNotificationIdentifier peopleNotificationIdentifier, GroupMembershipManager groupMembershipManager) {
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0064, code lost:
    
        if (((com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r2).isGroupSummary((com.android.systemui.statusbar.notification.collection.NotificationEntry) r5) == false) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isHighPriority(com.android.systemui.statusbar.notification.collection.PipelineEntry r5, boolean r6) {
        /*
            r4 = this;
            if (r5 != 0) goto L4
            goto L93
        L4:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r5.getRepresentativeEntry()
            if (r0 != 0) goto Lc
            goto L93
        Lc:
            android.service.notification.NotificationListenerService$Ranking r1 = r0.mRanking
            int r1 = r1.getImportance()
            r2 = 3
            if (r1 >= r2) goto L95
            if (r6 == 0) goto L52
            android.service.notification.NotificationListenerService$Ranking r1 = r0.mRanking
            android.app.NotificationChannel r1 = r1.getChannel()
            if (r1 == 0) goto L2c
            android.service.notification.NotificationListenerService$Ranking r1 = r0.mRanking
            android.app.NotificationChannel r1 = r1.getChannel()
            boolean r1 = r1.hasUserSetImportance()
            if (r1 == 0) goto L2c
            goto L52
        L2c:
            android.service.notification.StatusBarNotification r1 = r0.mSbn
            android.app.Notification r1 = r1.getNotification()
            boolean r1 = r1.isMediaNotification()
            if (r1 != 0) goto L95
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier r1 = r4.mPeopleNotificationIdentifier
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl r1 = (com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl) r1
            int r1 = r1.getPeopleNotificationType(r0)
            if (r1 == 0) goto L43
            goto L95
        L43:
            android.service.notification.StatusBarNotification r1 = r0.mSbn
            android.app.Notification r1 = r1.getNotification()
            java.lang.Class<android.app.Notification$MessagingStyle> r2 = android.app.Notification.MessagingStyle.class
            boolean r1 = r1.isStyle(r2)
            if (r1 == 0) goto L52
            goto L95
        L52:
            int r1 = com.android.systemui.statusbar.notification.shared.NotificationBundleUi.$r8$clinit
            boolean r1 = r5 instanceof com.android.systemui.statusbar.notification.collection.NotificationEntry
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r2 = r4.mGroupMembershipManager
            if (r1 == 0) goto L67
            r1 = r5
            com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r1
            r3 = r2
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl r3 = (com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r3
            boolean r1 = r3.isGroupSummary(r1)
            if (r1 != 0) goto L67
            goto L88
        L67:
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl r2 = (com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl) r2
            java.util.List r1 = r2.getChildren(r5)
            if (r1 == 0) goto L88
            java.util.Iterator r1 = r1.iterator()
        L73:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L88
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r2
            if (r2 == r5) goto L73
            boolean r2 = r4.isHighPriority(r2, r6)
            if (r2 == 0) goto L73
            goto L95
        L88:
            android.service.notification.StatusBarNotification r4 = r0.mSbn
            android.app.Notification r4 = r4.getNotification()
            int r4 = r4.semPriority
            if (r4 <= 0) goto L93
            goto L95
        L93:
            r4 = 0
            return r4
        L95:
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider.isHighPriority(com.android.systemui.statusbar.notification.collection.PipelineEntry, boolean):boolean");
    }

    public final boolean isHighPriorityConversation(PipelineEntry pipelineEntry) {
        NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
        if (representativeEntry == null || ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(representativeEntry) == 0) {
            return false;
        }
        if (representativeEntry.mRanking.getImportance() >= 3) {
            return true;
        }
        if (pipelineEntry instanceof GroupEntry) {
            return ((GroupEntry) pipelineEntry).mUnmodifiableChildren.stream().anyMatch(new HighPriorityProvider$$ExternalSyntheticLambda0());
        }
        return false;
    }
}
