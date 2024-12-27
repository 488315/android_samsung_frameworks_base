package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.UserInfo;
import android.util.SparseArray;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public class HideNotifsForOtherUsersCoordinator implements Coordinator {
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private final NotifFilter mFilter = new NotifFilter("NotCurrentUserFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return !((NotificationLockscreenUserManagerImpl) HideNotifsForOtherUsersCoordinator.this.mLockscreenUserManager).isCurrentProfile(notificationEntry.mSbn.getUser().getIdentifier());
        }
    };
    private final NotificationLockscreenUserManager.UserChangedListener mUserChangedListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator.2
        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public void onCurrentProfilesChanged(SparseArray<UserInfo> sparseArray) {
            StringBuilder sb = new StringBuilder("onCurrentProfilesChanged: user=");
            sb.append(((NotificationLockscreenUserManagerImpl) HideNotifsForOtherUsersCoordinator.this.mLockscreenUserManager).mCurrentUserId);
            sb.append(" profiles={");
            for (int i = 0; i < sparseArray.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(sparseArray.keyAt(i));
            }
            sb.append("}");
            HideNotifsForOtherUsersCoordinator.this.mFilter.invalidateList(sb.toString());
        }

        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public /* bridge */ /* synthetic */ void onUserChanged(int i) {
        }

        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public /* bridge */ /* synthetic */ void onUserRemoved(int i) {
        }
    };

    public HideNotifsForOtherUsersCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.mLockscreenUserManager = notificationLockscreenUserManager;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mFilter);
        ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).addUserChangedListener(this.mUserChangedListener);
    }
}
