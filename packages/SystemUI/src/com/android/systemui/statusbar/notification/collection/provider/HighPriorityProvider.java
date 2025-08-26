package com.android.systemui.statusbar.notification.collection.provider;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import java.util.List;

/* loaded from: classes3.dex */
public class HighPriorityProvider {
    public final GroupMembershipManager mGroupMembershipManager;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;

    public HighPriorityProvider(PeopleNotificationIdentifier peopleNotificationIdentifier, GroupMembershipManager groupMembershipManager) {
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isHighPriority(PipelineEntry pipelineEntry, boolean z) {
        NotificationEntry representativeEntry;
        if (pipelineEntry == null || (representativeEntry = pipelineEntry.getRepresentativeEntry()) == null) {
            return false;
        }
        if (representativeEntry.mRanking.getImportance() >= 3) {
            return true;
        }
        if (z && ((representativeEntry.mRanking.getChannel() == null || !representativeEntry.mRanking.getChannel().hasUserSetImportance()) && (representativeEntry.mSbn.getNotification().isMediaNotification() || ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(representativeEntry) != 0 || representativeEntry.mSbn.getNotification().isStyle(Notification.MessagingStyle.class)))) {
            return true;
        }
        int i = NotificationBundleUi.$r8$clinit;
        boolean z2 = pipelineEntry instanceof NotificationEntry;
        GroupMembershipManager groupMembershipManager = this.mGroupMembershipManager;
        if (z2) {
            if (((GroupMembershipManagerImpl) groupMembershipManager).isGroupSummary((NotificationEntry) pipelineEntry)) {
                List<NotificationEntry> children = ((GroupMembershipManagerImpl) groupMembershipManager).getChildren(pipelineEntry);
                if (children != null) {
                    for (NotificationEntry notificationEntry : children) {
                        if (notificationEntry != pipelineEntry && isHighPriority(notificationEntry, z)) {
                            return true;
                        }
                    }
                }
            }
        }
        return representativeEntry.mSbn.getNotification().semPriority > 0;
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
