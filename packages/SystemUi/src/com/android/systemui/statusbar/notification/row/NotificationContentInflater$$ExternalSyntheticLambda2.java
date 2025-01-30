package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationContentInflater$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationContentInflater f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;
    public final /* synthetic */ NotificationEntry f$2;

    public /* synthetic */ NotificationContentInflater$$ExternalSyntheticLambda2(NotificationContentInflater notificationContentInflater, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationContentInflater;
        this.f$1 = expandableNotificationRow;
        this.f$2 = notificationEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationContentInflater notificationContentInflater = this.f$0;
                ExpandableNotificationRow expandableNotificationRow = this.f$1;
                NotificationEntry notificationEntry = this.f$2;
                notificationContentInflater.getClass();
                expandableNotificationRow.mPrivateLayout.setContractedChild(null);
                ((NotifRemoteViewCacheImpl) notificationContentInflater.mRemoteViewCache).removeCachedView(notificationEntry, 1);
                break;
            case 1:
                NotificationContentInflater notificationContentInflater2 = this.f$0;
                ExpandableNotificationRow expandableNotificationRow2 = this.f$1;
                NotificationEntry notificationEntry2 = this.f$2;
                notificationContentInflater2.getClass();
                expandableNotificationRow2.mPrivateLayout.setExpandedChild(null);
                ((NotifRemoteViewCacheImpl) notificationContentInflater2.mRemoteViewCache).removeCachedView(notificationEntry2, 2);
                break;
            case 2:
                NotificationContentInflater notificationContentInflater3 = this.f$0;
                ExpandableNotificationRow expandableNotificationRow3 = this.f$1;
                NotificationEntry notificationEntry3 = this.f$2;
                notificationContentInflater3.getClass();
                expandableNotificationRow3.mPrivateLayout.setHeadsUpChild(null);
                ((NotifRemoteViewCacheImpl) notificationContentInflater3.mRemoteViewCache).removeCachedView(notificationEntry3, 4);
                NotificationContentView notificationContentView = expandableNotificationRow3.mPrivateLayout;
                notificationContentView.mHeadsUpInflatedSmartReplies = null;
                notificationContentView.mHeadsUpSmartReplyView = null;
                break;
            default:
                NotificationContentInflater notificationContentInflater4 = this.f$0;
                ExpandableNotificationRow expandableNotificationRow4 = this.f$1;
                NotificationEntry notificationEntry4 = this.f$2;
                notificationContentInflater4.getClass();
                expandableNotificationRow4.mPublicLayout.setContractedChild(null);
                ((NotifRemoteViewCacheImpl) notificationContentInflater4.mRemoteViewCache).removeCachedView(notificationEntry4, 8);
                break;
        }
    }
}
